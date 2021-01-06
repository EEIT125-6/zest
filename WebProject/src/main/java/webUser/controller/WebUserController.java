package webUser.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import webUser.model.CityInfo;
import webUser.model.FoodFervor;
import webUser.model.Gender;
import webUser.model.UserIdentity;
import webUser.model.UserWilling;
import webUser.model.WebUserData;
import webUser.service.FervorService;
import webUser.service.GenderService;
import webUser.service.IdentityService;
import webUser.service.LocationService;
import webUser.service.WebUserService;
import webUser.service.WillingService;

@SessionAttributes({
		"registerEmail", 
		"checkCode", 
		"willingList", 
		"identityList", 
		"fervorList", 
		"genderList", 
		"cityInfoList",
		"reg_webUser",
		"userFullData",
		"managedUserData",
		"selfData"})
@Controller
public class WebUserController {
	/* ServletContext */
	@Autowired
	ServletContext context;
	
	/* WebUserData Service */
	@Autowired
	WebUserService wus;

	/* UserWilling Service */
	@Autowired
	WillingService wis;

	/* Identity Service */
	@Autowired
	IdentityService ids;

	/* FoodFervor Service */
	@Autowired
	FervorService fvs;

	/* Gender Service */
	@Autowired
	GenderService gds;

	/* Location Service */
	@Autowired
	LocationService lcs;

	/* Today */
	final LocalDate today = LocalDate.now();
	
	/* Default Account List */
	final String[] defaultAccounts = {"WebAdmin", "TestUser", "TestBoss"};
	
	/* Default Project Physical Address */
//  final String defaultAddress = "C:/JavaMVCWorkspace/WebProject/src/main/webapp/WEB-INF/views";
	final String defaultAddress = "H:/MVCWorkspace/WebProject/src/main/webapp/WEB-INF/views";
	
	/* 簽到用生日時顯示字串 */
	final String birthday = "今天對您是特別的一日，今日登入讓您獲得 10 枚橙幣！";	
	/* 簽到用生日當月時顯示字串 */
	final String birthMonth = "這個月對您是特別的一個月，今日登入讓您獲得了 1 枚橙幣！";
	
	/* 傳送表單所必需的資料 */
	@GetMapping(value = "/WebUserRegisterForm")
	public String doCreateRegisterForm(Model model) {
		
		/* 取得下拉選單、單選、多選所需的固定資料 */
		List<UserWilling> willingList = wis.getUserWillingList();
		List<UserIdentity> identityList = ids.getIdentityList();
		List<FoodFervor> fervorList = fvs.getFoodFervorList();
		List<Gender> genderList = gds.getGenderList();
		List<CityInfo> cityInfoList = lcs.getLocationInfoList();
		
		/* 移除管理員選項 */
		identityList.remove(0);	
		
		/* 設定入Model中 */
		model.addAttribute("willingList", willingList);
		model.addAttribute("identityList", identityList);
		model.addAttribute("fervorList", fervorList);
		model.addAttribute("genderList", genderList);
		model.addAttribute("cityInfoList", cityInfoList);
		
		/* 前往註冊畫面 */
		return "WebUserRegisterForm";
	}

	/* 執行註冊資料檢查 */
	@PostMapping(value = "/webUser/controller/WebUserRegisterForm")
	public String doRegisterSubmit(
			Model model,
			@RequestParam(value = "userLv", defaultValue = "0") Integer lv,
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "firstName", defaultValue = "") String firstName,
			@RequestParam(value = "lastName", defaultValue = "") String lastName,
			@RequestParam(value = "nickname", defaultValue = "") String nickname,
			@RequestParam(value = "gender", defaultValue = "N") String genderCode,
			@RequestParam(value = "birth", defaultValue = "1800-01-01") Date birth,
			@RequestParam(value = "fervorOption", defaultValue="{7}") List<String> fervorValue,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "inputCheckCode", defaultValue = "") String inputCheckCode,
			@RequestParam(value = "phone", defaultValue = "") String phone,
			@RequestParam(value = "getEmail", defaultValue = "Y") String willingCode,
			@RequestParam(value = "locationCode", defaultValue = "0") Integer cityCode,
			@RequestParam(value = "addr0", defaultValue = "") String addr0,
			@RequestParam(value = "addr1", defaultValue = "") String addr1,
			@RequestParam(value = "addr2", defaultValue = "") String addr2,
			RedirectAttributes redirectAttributes) {
		
		/* 參數宣告 */
		String submitMessage = "";
		String checkCode = ((String) model.getAttribute("checkCode")).toUpperCase();
		String registerEmail = (String) model.getAttribute("registerEmail");
		
		Map<String, Object> map = doCheckNewWebUserObj(
				model, 
				lv, 
				account, 
				password,
				firstName,
				lastName,
				nickname,
				birth,
				email, 
				phone,
				addr0,
				addr1,
				addr2,
				genderCode,
				willingCode,
				fervorValue,
				cityCode);
		
		WebUserData reg_webUser = (WebUserData) map.get("reg_webUser");
		submitMessage = (String) map.get("submitMessage");
		
		/* 追加檢查checkCode */
		if (submitMessage.equals("")) {
			submitMessage = doCheckCheckCode(inputCheckCode, checkCode, registerEmail, email);
		}
		
		/* 成功 */
		if (submitMessage.equals("")) {
			/* 將物件reg_webUser以"reg_webUser"的名稱放入Session中 */
			model.addAttribute("reg_webUser", reg_webUser);
			/* 移動到顯示使用者輸入資料的畫面 */
			return "redirect:/webUser/DisplayWebUserInfo";
		} else {
			/* 將物件submitMessage以"submitMessage"的名稱放入flashAttribute中 */
			redirectAttributes.addFlashAttribute("submitMessage", submitMessage);
			/* 返回註冊畫面 */
			return "redirect:/WebUserRegisterForm";			
		}
	}

	/* 執行使用者資料送出 */
	@PostMapping(value = "/webUser/controller/DisplayWebUserInfo/confirm")
	public String doInsertWebUserData (
				SessionStatus sessionStatus,
				RedirectAttributes redirectAttributes,
				Model model) {
		
		String destinationUrl = "";
		
		/* 取出物件 */
		WebUserData reg_webUser = (WebUserData) model.getAttribute("reg_webUser");
		String registerEmail = (String) model.getAttribute("registerEmail");
		
		/* 設定要顯示的訊息 */
		String insertResultMessage = "";
		
		/* 宣告欲回傳的參數 */
		Integer insertResult = -1;
		String insertResultPage = "WebUserRegisterForm";
		
		/* 預防性後端輸入檢查，正常時回傳空字串 */
		insertResultMessage = doCheckRegisterInput(
				reg_webUser, 
				model);
		
		/* 追加檢查項目 */
		if (!reg_webUser.getJoinDate().equals(Date.valueOf(LocalDate.now()))) {
			insertResultMessage = "加入時間異常";
		}
		
		if (!reg_webUser.getStatus().equals("active") && reg_webUser.getAccountLv().getLv() == 0) {
			insertResultMessage = "帳號狀態異常";
		} else if (!reg_webUser.getStatus().equals("inactive") 
				&& (reg_webUser.getAccountLv().getLv() == 1)) {
			insertResultMessage = "帳號狀態異常";
		}
		
		if (reg_webUser.getVersion() != 0) {
			insertResultMessage = "帳號資料狀態異常";
		}
		
		if (!reg_webUser.getEmail().equals(registerEmail)) {
			insertResultMessage = "信箱比對不一致";
		}
		
		if (insertResultMessage.equals("")) {
			/* 調用服務裡的方法 */
			try {
				insertResult = wus.insertWebUserData(reg_webUser);
			} catch (SQLException sqlE) {
				insertResultMessage = "發生錯誤！" + sqlE.getMessage();
			}
			
			if (insertResult > 0) {
				insertResultMessage = "恭喜！" + reg_webUser.getAccount() + "，您的帳號已成功建立";
				/* 清空SessionAttribute */
				sessionStatus.setComplete();
				insertResultPage = "WebUserLogin";
			} 
			
			/* 將物件insertResultMessage以"insertResultMessage"的名稱放入flashAttribute中 */
			redirectAttributes.addFlashAttribute("insertResultMessage", insertResultMessage);
			/* 將物件insertResultPage以"insertResultPage"的名稱放入flashAttribute中 */
			redirectAttributes.addFlashAttribute("insertResultPage", insertResultPage);
			/* 前往註冊結束畫面 */
			destinationUrl = "redirect:/webUser/WebUserRegisterResult";
		} else {
			/* 將物件insertResultMessage以"submitMessage"的名稱放入flashAttribute中 */
			redirectAttributes.addFlashAttribute("submitMessage", insertResultMessage);
			/* 返回註冊畫面 */
			destinationUrl = "redirect:/WebUserRegisterForm";
		}		
		
		return destinationUrl;
	}
	
	/* 取消註冊 */
	@GetMapping(value = "/webUser/controller/DisplayWebUserInfo/undo")
	public String doRegisterUndo(
			SessionStatus sessionStatus) {
		/* 清空SessionAttribute */
		sessionStatus.setComplete();
		/* 返回註冊畫面 */
		return "redirect:/WebUserRegisterForm";
	}
	
	/* 執行登入檢查 */
	@PostMapping(value = "/webUser/controller/WebUserLogin", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doLoginCheck(
			Model model,
			@RequestParam(value = "account", defaultValue="") String account,
			@RequestParam(value = "password", defaultValue="") String password) {
		
		/* 宣告欲回傳的參數 */
		Map<String, String> map = new HashMap<>();
		
		String inputCheckResult = "";
		Integer accountCheckResult = -3;
		String loginMessage = "";
		String signInMessage = "";
		
		WebUserData userFullData = new WebUserData();
		
		/* 預防性後端檢查，正常時回傳1 */
		inputCheckResult = doCheckLoginInput(account, password);
		if (inputCheckResult.equals("")) {
			/* 調用服務裡的方法 */
			try {
				/* 檢查登入 */
				accountCheckResult = wus.checkWebUserLogin(account, password);
				/* 存取使用者個人資料 */
				userFullData = wus.getWebUserData(account);
				if (userFullData != null) {
					/* 檢查簽到 */
					Integer checkSignInResult = wus.checkWebUserSignIn(userFullData.getUserId(), Date.valueOf(today)) ;
					/* 0代表未簽到，1代表已簽到 */
					if (checkSignInResult == 0) {
						/* 設定簽到日 */
						userFullData.setSignIn(Date.valueOf(today));
						/* 同月份時每次登入加1幣 */
						/* 生日時每次登入額外加9幣 */
						if ((String.valueOf(today)).split("-")[1].equals((String.valueOf(userFullData.getBirth())).split("-")[1])) {
							userFullData.setZest(userFullData.getZest().add(new BigDecimal("1")));
							if ((String.valueOf(today)).split("-")[2].equals((String.valueOf(userFullData.getBirth())).split("-")[2])) {
								userFullData.setZest(userFullData.getZest().add(new BigDecimal("9")));
								signInMessage = birthday;
							} else {		
								signInMessage = birthMonth;
							}
							/* 執行簽到 */
							Integer runSingInResult = wus.runWebUserSignIn(userFullData);
							/* 執行簽到失敗時 */
							signInMessage = (runSingInResult != 1) ? "" : signInMessage;
							/* 失敗後還原設定 */
							if (runSingInResult != 1) {
								userFullData.setSignIn(null);
								if (signInMessage.equals(birthday)) {
									userFullData.setZest(userFullData.getZest().subtract(new BigDecimal("10")));
								} else if (signInMessage.equals(birthMonth)) {
									userFullData.setZest(userFullData.getZest().subtract(new BigDecimal("1")));
								}
							}
						}
					} else {
						signInMessage = "您今日已經簽到過了！";
					}
				}
			} catch (SQLException sqlE) {
				String loginMessageTmp = sqlE.getMessage();
				loginMessage = (loginMessageTmp.indexOf(":") != -1) ? loginMessageTmp.split(":")[1]: loginMessageTmp;
			}
		} 
		
		if (accountCheckResult == 1) {
			loginMessage = "登入成功！歡迎使用本服務，" + userFullData.getNickname() + " ！";
			/* 將Java Bean物件userFullData以"userFullData"的名稱放入SessionAttributes中 */
			model.addAttribute("userFullData", userFullData);
		} 
		
		map.put("resultCode", accountCheckResult.toString());
		map.put("resultMessage", loginMessage);
		map.put("signInMessage", signInMessage);
		return map;
	}
	
	/* 執行登出 */
	@GetMapping(value = "/webUser/controller/WebUserMain/Logout")
	public String doLogOut(
			Model model,
			RedirectAttributes redirectAttributes,
			SessionStatus sessionStatus,
			HttpSession session) {
		
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		
		String nickname = userData.getNickname();
		String logoutMessage = "謝謝您的使用，" + nickname + "!";
		
		/* 清空SessionAttribute */
		sessionStatus.setComplete();
		/* 無效httpSession */
		session.invalidate();
		/* 將物件insertResultMessage以"insertResultMessage"的名稱放入flashAttribute中 */
		redirectAttributes.addFlashAttribute("logoutMessage", logoutMessage);
		/* 前往登出畫面 */
		return "redirect:/webUser/WebUserLogoutResult";
	}
	
	/* 執行帳號停用 */
	@PostMapping(value = "/webUser/controller/WebUserMain/Quit")
	public String doPersonalQuit(
			Model model,
			RedirectAttributes redirectAttributes,
			SessionStatus sessionStatus) {
		
		/* 宣告要傳回的參數 */
		Integer deleteResult = -1;
		String quitMessage = "";
		String redirectPage = "/webUser/WebUserMain";
		
		WebUserData quitUserData = (WebUserData) model.getAttribute("userFullData");
	
		/* 預防性後端檢查 */
		quitMessage = doCheckQuitInput(quitUserData);
		if (quitMessage.equals("")) {
			Integer quitUserLv = quitUserData.getAccountLv().getLv();
			Boolean runQuit = true;
			/* 調用服務裡的方法 */
			try {
				quitUserData.setVersion(quitUserData.getVersion() + 1);
				quitUserData.setStatus("quit");
				/* 如果為管理員，先檢查是否仍有可登入的管理員帳號 */
				if (quitUserLv == -1) {
					if (wus.checkAdminAccess() - 1 == 0) {
						runQuit = false;
						quitMessage = "無法停用本帳號！系統要求至少需要維持一個可登入的管理員帳號";
					}
				}
				/* 如果本帳號停用後，無管理員可登入系統，則阻止 */
				if (runQuit) {
					/* 執行停用 */
					deleteResult = wus.quitWebUserData(quitUserData);
					/* 寄送Email */
					UserInfoController.doSendEmail(quitUserData.getAccount(), quitUserData.getEmail(), "", "personalQuit");
				}
			} catch (SQLException sqlE) {
				String quitMessageTmp = sqlE.getMessage();
				quitMessage = quitMessageTmp.split(":")[1];
			} catch (Exception e) {
				String quitMessageTmp = e.getMessage();
				quitMessage = quitMessageTmp.split(":")[1];
			}
		}
		
		/* 成功變更 */
		if (deleteResult == 1) {
			quitMessage = "感謝您的使用， " + quitUserData.getNickname() + " ！我們有緣再見...";		
			/* 清空SessionAttribute */
			sessionStatus.setComplete();
			redirectPage = "/";
		} 
		
		/* 將物件quitMessage以"quitMessage"的名稱放入flashAttribute中 */
		redirectAttributes.addFlashAttribute("quitMessage", quitMessage);
		/* 將物件redirectPag以"redirectPag"的名稱放入flashAttribute中 */
		redirectAttributes.addFlashAttribute("redirectPage", redirectPage);
		/* 導向停用結束畫面 */
		return "redirect:/webUser/WebUserQuitResult";
	}
	
	/* 以Ajax取回使用者個人資料 */
	@PostMapping(value = "/webUser/controller/DisplaySelfData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> doDisplaySelfData(
			Model model) {
		
		/* 傳回的參數 */
		Integer getResultCode = -1;
		String getResultMessage = "";
		
		WebUserData selfData = new WebUserData();
		Map<String, Object> map = new HashMap<>();
		
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		
		/* 取出帳號 */
		String account = userData.getAccount();
		
		/* 調用服務裡的方法 */
		try {
			selfData = wus.getWebUserData(account);
		} catch (SQLException sqlE) {
			String getDataMessageTmp = sqlE.getMessage();
			getResultMessage = getDataMessageTmp.split(":")[1];
		}
		
		getResultCode = (getResultMessage.equals("")) ? 1 : 0;
		getResultMessage = (getResultMessage.equals("")) ? "以下是您的個人資料：" : getResultMessage;
		
		map.put("resultCode", getResultCode.toString());
		map.put("resultMessage", getResultMessage);
		map.put("selfData", selfData);
		map.put("birthday", String.valueOf(selfData.getBirth()));
		return map;
	}
	
	/* 準備顯示個人資料畫面 */
	@GetMapping(value = "/webUser/controller/WebUserMain/Modify")
	public String doDisplayOwnUserData() {
		return "redirect:/webUser/DisplayWebUserData";
	}
	
	/* 準備顯示修改密碼畫面 */
	@GetMapping(value = "/webUser/controller/WebUserModifyPassword")
	public String doWebUserModifyPassword() {
		return "redirect:/webUser/WebUserModifyPassword";
	}
	
	/* 執行修改密碼 */
	@PostMapping(value = "/webUser/controller/WebUserModifyPassword")
	public String doUpdateWebUserPassword(
			Model model,
			SessionStatus sessionStatus,
			RedirectAttributes redirectAttributes,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "confirmPassword") String confirmPassword ) {	
		
		/* 宣告參數 */
		String updateResultMessage = "";
		Integer updateResult = -1;
		String destinationUrl = "";
		
		/* 從sessionAttribute中取出物件 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		String oldPassword = userData.getPassword();
		
		/* 預防性後端檢查 */
		String tmpMessage = doCheckUpdatePasswordInput(userData, password, confirmPassword);
		updateResultMessage = (tmpMessage.equals("?")) ? "" : tmpMessage;
		
		/* 成功才執行 */
		if (updateResultMessage.equals("")) {
			/* 調用服務裡的方法 */
			try {
				userData.setVersion(userData.getVersion() + 1);
				userData.setPassword(password);
				updateResult = wus.updateWebUserPassword(userData);
			} catch (SQLException sqlE) {
				updateResultMessage = sqlE.getMessage();
				/* 復原操作 */
				userData.setVersion(userData.getVersion() - 1);
				userData.setPassword(oldPassword);
			}
			
			/* 成功 */
			if (updateResult == 1) {
				/* 清空SessionAttribute */
				sessionStatus.setComplete();
			}
		}
		if (!updateResultMessage.equals("")) {
			if (updateResultMessage.indexOf(":") != -1) {	
				updateResultMessage = updateResultMessage.split(":")[1];
			}
		} else {
			updateResultMessage = userData.getAccount() + "的密碼變更成功！5秒後將返回登入畫面";
		}
		
		/* 將物件updateResultMessage以"updateResultMessage"的名稱放入flashAttribute中 */
		redirectAttributes.addFlashAttribute("updateResultMessage", updateResultMessage);
		
		if (updateResult == 1) {
			/* 導向密碼修改結果畫面 */
			destinationUrl = "redirect:/webUser/WebUserChangeResult";
		} else {
			/* 導向修改個人密碼畫面 */
			destinationUrl = "redirect:/webUser/WebUserModifyPassword";
		}
		
		return destinationUrl;
	}
	
	/* 準備前往修改其他資料畫面 */
	@PostMapping(value = "/webUser/WebUserModifyData")
	public String doCreateWebUserModifyData(
			Model model) {			
		
		/* 設定傳入表單的原資料 */
		WebUserData selfData = new WebUserData();
		String getResultMessage = "";
		
		/* 取得下拉選單、單選、多選所需的固定資料 */
		List<UserWilling> willingList = wis.getUserWillingList();
		List<FoodFervor> fervorList = fvs.getFoodFervorList();
		List<CityInfo> cityInfoList = lcs.getLocationInfoList();
		
		/* 設定入Model中 */
		model.addAttribute("willingList", willingList);
		model.addAttribute("fervorList", fervorList);
		model.addAttribute("cityInfoList", cityInfoList);
		
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		
		/* 取得帳號 */
		String account = userData.getAccount();
		
		/* 調用服務裡的方法 */
		try {
			selfData = wus.getWebUserData(account);
		} catch (SQLException sqlE) {
			String getDataMessageTmp = sqlE.getMessage();
			getResultMessage = getDataMessageTmp.split(":")[1];
		}
		
		if (!getResultMessage.equals("")) {
			return "redirect:/WebUserLogin";
		}
		
		/* 設定入Model中 */
		model.addAttribute("selfData", selfData);
		return "webUser/WebUserModifyData";
	}
	
	/* 執行使用者圖像修改 */
	@PostMapping(value = "/webUser/controller/WebUserModifyIcon", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doUpdateWebUserIcon(Model model,
			@RequestParam(value = "pic", required = false) CommonsMultipartFile picFile) {
		
		/* 宣告參數 */
		Map<String, String> map = new HashMap<>();
		String message = "";
		Boolean updateIconUrlResult = false;
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		/* 更新用物件 */
		WebUserData updatedData = userData;
		
		if (userData == null) {
			message = "使用者未登入！請登入後再進行本操作";
		} else if (userData.getStatus() == "quit" || userData.getStatus() == "inactive") {
			message = "使用者無權進行本動作！";
		} else if (userData.getAccountLv().getLv() != Integer.parseInt(userData.getUserId().substring(0,1)) - 1) {
			message = "使用者驗證失敗！";
		}
		
		if (picFile == null) {
			message = "未上傳任何檔案！";
		}
		
		/* 取出上傳檔案的檔名 */
		String realFileName = picFile.getOriginalFilename();
		/* 取出原有圖示的相對路徑 */
		String oldUrl = userData.getIconUrl();
		/* 取得使用者ID */
		String userId = userData.getUserId();
		/* 組成新圖示的相對路徑 */
		String newIconUrl = "/images/webUser/" + userId + "/" + realFileName;
		
		if (message.equals("")) {
			/* 執行圖片更新 */
			map = doUpdatePic(oldUrl, newIconUrl, picFile);
			/* 圖片更新成功 */
			if (map.get("resultCode").equals("true")) {
				/* 更新DB上的資料 */
				/* 調用服務裡的方法 */
				try {
					updatedData.setIconUrl(newIconUrl);
					updatedData.setVersion(updatedData.getVersion() + 1);
					System.out.println("url is: " + updatedData.getIconUrl() + "version is :" + updatedData.getVersion());
					updateIconUrlResult = (wus.updateWebUserIconUrl(updatedData) == 1) ? true : false;
				} catch (SQLException sqlE) {
					String getDataMessageTmp = sqlE.getMessage();
					message = getDataMessageTmp.split(":")[1];
				}
				/* 更新圖片、更新DB都成功 */
				if (updateIconUrlResult) {
					if (!oldUrl.equals("")) {
						/* 刪除舊檔暫存檔 */
						String oldFilePath = defaultAddress + oldUrl.substring(0, oldUrl.lastIndexOf(".")) + "_tmp" + oldUrl.substring(oldUrl.lastIndexOf("."));
						File deletedOldPic = new File(oldFilePath);
						if (deletedOldPic.exists()) {							
							/* 執行刪除 */
							updateIconUrlResult = deletedOldPic.delete();
						}
						message = (updateIconUrlResult) ? message : "圖示更新成功但移除暫存檔案失敗";
					}
					map.put("resultCode", updateIconUrlResult.toString());
				/* 更新圖片成功但更新DB失敗 */
				} else {
					/* 刪除新增的圖檔 */
					String newFilePath = defaultAddress + newIconUrl;
					File deletedNewPic = new File(newFilePath);
					Boolean killNewPic = deletedNewPic.delete();
					/* 重新命名舊圖檔 */
					if (killNewPic) {
						String oldFilePath = defaultAddress + oldUrl.substring(0, oldUrl.lastIndexOf(".")) + "_tmp" + oldUrl.substring(oldUrl.lastIndexOf("."));
						String delMessage = "";
						try {
							/* 複製檔案 */
							FileUtils.copyFile(new File(oldFilePath), new File(defaultAddress + oldUrl));
							/* 刪除暫存 */
							new File(oldFilePath).delete();
						} catch (IOException ioE) {
							delMessage = ioE.getMessage();
							message += delMessage;
						}
					}
				}
				message = (message.equals("")) ? "圖示已順利更新完成！" : message;
			} else {
				message = map.get("resultMessage");
			}
			map.put("resultMessage", message);
			return map;
		} else {
			map.put("resultCode", updateIconUrlResult.toString());
			map.put("resultMessage", message);
		}
		return map;
	}
	
	/* 執行密碼以外的資料修改 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/webUser/controller/WebUserModifyData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doUpdateWebUserData(
			Model model,
			RedirectAttributes redirectAttributes,
			@RequestParam(value = "newFirstName", required = false, defaultValue="") String newFirstName,
			@RequestParam(value = "newLastName", required = false, defaultValue="") String newLastName,
			@RequestParam(value = "newNickname", required = false, defaultValue="") String newNickname,
			@RequestParam(value = "newFervor", required = false, defaultValue="") String newFervor,
			@RequestParam(value = "newEmail", required = false, defaultValue="") String newEmail,
			@RequestParam(value = "inputCheckCode", required = false, defaultValue="") String inputCheckCode,
			@RequestParam(value = "newPhone", required = false, defaultValue="") String newPhone,
			@RequestParam(value = "newGetEmail", required = false, defaultValue="") String newGetEmail,
			@RequestParam(value = "newLocationCode", required = false, defaultValue="") Integer newLocationCode,
			@RequestParam(value = "newAddr0", required = false, defaultValue="") String newAddr0,
			@RequestParam(value = "newAddr1", required = false, defaultValue="") String newAddr1,
			@RequestParam(value = "newAddr2", required = false, defaultValue="") String newAddr2) {
		
		/* 宣告參數 */
		Map<String, String> map = new HashMap<>();
		String updateResultMessage = "";
		Integer updateResult = -1;
		/* 更新用的同型物件 */
		WebUserData updatedUserData = new WebUserData();
		
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData selfData = (WebUserData) model.getAttribute("selfData");
		String checkCode = (model.getAttribute("checkCode") == null) ? "" : ((String) model.getAttribute("checkCode")).toUpperCase();
		String registerEmail = (model.getAttribute("registerEmail") == null) ? "" : (String) model.getAttribute("registerEmail");
		
		/* 從session取出陣列來繼續完成設定 */
		List<FoodFervor> fervorList = (List<FoodFervor>) model.getAttribute("fervorList");
		List<UserWilling> willingList = (List<UserWilling>) model.getAttribute("willingList");
		List<CityInfo> cityInfoList = (List<CityInfo>) model.getAttribute("cityInfoList");
		
		String fervorTemp = "";
		for (FoodFervor fervorItem: fervorList) {
			for (String fervor: newFervor.split(",")) {
				if (fervor.equals(fervorItem.getFervorCode().toString())) {
					if (!fervorTemp.equals("")) {
						fervorTemp += ",";
					}
					fervorTemp += fervorItem.getFervorItem();
				}
			}
		}
		String fervor = fervorTemp;
		
		UserWilling willingOption = new UserWilling();
		for (UserWilling willingValue: willingList) {
			if (willingValue.getWillingCode().equals(newGetEmail)) {
				willingOption = willingValue;
			}
		}
		
		CityInfo locationInfo = new CityInfo();
		for (CityInfo locationValue: cityInfoList) {
			if (locationValue.getCityCode() == newLocationCode) {
				locationInfo = locationValue;
			}
		}
		
		/* 檢查JavaBean物件 */
		if (selfData == null) {
			updateResultMessage = "未登入系統，請登入後再進行操作！";
		} else if (selfData.getAccountLv().getLv() != Integer.parseInt(selfData.getUserId().substring(0, 1)) - 1) {
			updateResultMessage = "身分驗證失敗，請登入後重試一次！";
		} else if (selfData.getStatus().equals("quit") || selfData.getStatus().equals("inactive")) {
			updateResultMessage = "本帳號無法使用此功能";
		} 
		
		if (updateResultMessage.equals("")) {
			updatedUserData = new WebUserData(
					selfData.getUserId(), 
					selfData.getAccount(), 
					selfData.getPassword(), 
					newFirstName, 
					newLastName, 
					newNickname,
					selfData.getBirth(),
					fervor,
					newEmail,
					newPhone,
					selfData.getJoinDate(),
					newAddr0,
					newAddr1,
					newAddr2,
					selfData.getZest(),
					selfData.getVersion() + 1,
					selfData.getStatus(),
					selfData.getIconUrl(),
					selfData.getSignIn(),
					selfData.getAccountLv(),
					selfData.getGender(),
					willingOption,
					locationInfo);
		}
		
		/* 預防性後端檢查 */
		if (updateResultMessage.equals("")) {
			updateResultMessage = doCheckUpdateDataInput(updatedUserData, selfData).split(",")[1];
		}
		
		/* 追加檢查checkCode */
		if (updateResultMessage.equals("")) {
			if (!newEmail.equals(selfData.getEmail())) {	
				updateResultMessage = doCheckCheckCode(inputCheckCode, checkCode, registerEmail, newEmail);
			}
		}
		
		/* 檢查完畢 */
		if (updateResultMessage.equals("")) {
			
			/* 調用服務裡的方法 */
			try {
				updateResult = wus.updateWebUserData(updatedUserData);
			} catch (SQLException sqlE) {
				updateResultMessage = sqlE.getMessage();
			}
		}
		
		if (!updateResultMessage.equals("")) {
			if (updateResultMessage.indexOf(":") != -1) {	
				updateResultMessage = updateResultMessage.split(":")[1];
			}
		} 
		
		/* 修改成功 */
		if (updateResult == 1) {
			updateResultMessage = "修改成功";
			/* 更新設定值 */
			model.addAttribute("userFullData", updatedUserData);
		} else if (updateResult != 1 && updateResultMessage.equals("")) {
			updateResultMessage = "修改失敗";
		}
		
		map.put("resultCode", updateResult.toString());
		map.put("resultMessage", updateResultMessage);
		return map;
	}
	
	/* 前往搜尋畫面 */
	@GetMapping(value = "/webUser/WebUserSearchForm")
	public String doCreateWebUserSearchForm(Model model) 
	{
		/* 取得下拉選單、單選、多選所需的固定資料 */
		List<UserWilling> willingList = wis.getUserWillingList();
		List<FoodFervor> fervorList = fvs.getFoodFervorList();
		List<CityInfo> cityInfoList = lcs.getLocationInfoList();
		List<UserIdentity> identityList = ids.getIdentityList();
		
		/* 設定入Model中 */
		model.addAttribute("willingList", willingList);
		model.addAttribute("identityList", identityList);
		model.addAttribute("fervorList", fervorList);
		model.addAttribute("cityInfoList", cityInfoList);
		return "webUser/WebUserSearchForm";
	}
	
	/* 準備顯示搜尋畫面 */
	@GetMapping(value = "/webUser/controller/WebUserMain/Search")
	public String doDisplaySearchPage() {
		return "redirect:/webUser/WebUserSearchForm";
	}
	
	/* 回傳符合條件使用者的資料 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/webUser/controller/WebUserSearchForm", produces="application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> doSelectWebUserData(
			Model model,
			@RequestParam(value = "selectedAccount", defaultValue = "?") String selectedAccount,
			@RequestParam(value = "selectedNickname", defaultValue = "?") String selectedNickname,
			@RequestParam(value = "selectedFervor", defaultValue = "?") String selectedFervor,
			@RequestParam(value = "selectedLocationCode", defaultValue = "0") Integer selectedLocationCode,
			@RequestParam(value = "selectedStatus", defaultValue = "?") String selectedStatus,
			@RequestParam(value = "selectedIdentity", defaultValue = "-2") Integer selectedIdentity) {
		
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		Integer getResult = -1;
		String getResultMessage = "";
		
		/* 產生資料陣列 */
		List<WebUserData> userDataList = new ArrayList<>();
		
		/* 從session取出陣列來繼續完成設定 */
		List<FoodFervor> fervorList = (List<FoodFervor>) model.getAttribute("fervorList");
		
		/* 取出使用者身分相關資訊 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		String selectedParameters = "";
		
		/* 檢查身分 */
		if (userData == null) {
			getResultMessage = "無法使用本功能，請確定您已經登入本系統！";
		} else if (userData.getStatus().equals("inactive") || userData.getStatus().equals("quit")) {
			getResultMessage = "本帳號無法使用此功能！";
		}
		
		String fervorTemp = "";
		/* 驗證部分值 */
		if (!selectedFervor.equals("?")) {
			for (String selectedItem: selectedFervor.split(",")) {
				for (FoodFervor fervorItem: fervorList) {
					if (fervorItem.getFervorCode().toString().equals(selectedItem)) {
						if (!fervorTemp.equals("")) {
							fervorTemp += "%";
						}
						fervorTemp += fervorItem.getFervorItem();
					}
				}
			}
		}
		selectedFervor = (fervorTemp.equals("")) ? selectedFervor: fervorTemp;
		
		/* 產生驗證用字串 */
		if (getResultMessage.equals("")) {
			Integer lv = userData.getAccountLv().getLv();	
			String status = "active";
			if (lv == -1) {
				status = userData.getStatus();
			}
			
			if (lv != -1) {	
				selectedParameters = selectedAccount + ":" + selectedNickname + ":" 
						+ selectedFervor + ":" + selectedLocationCode + ":" 
						+ String.valueOf(lv) + ":" + status + ":?:-2";
			} else {
				selectedParameters = selectedAccount + ":" + selectedNickname + ":" 
						+ selectedFervor + ":" + selectedLocationCode + ":" 
						+ String.valueOf(lv) + ":" + status + ":" + selectedStatus + ":" + selectedIdentity.toString();
			}
			
			/* 預防性後端輸入檢查 */
			getResultMessage = doCheckSelectUserDataInput(selectedParameters, userData);
		}
		
		if (getResultMessage.equals("")) {
			/* 調用服務裡的方法 */
			try {
				userDataList = wus.getSelectedWebUserData(selectedParameters);
			} catch (SQLException sqlE) {
				String getDataMessageTmp = sqlE.getMessage();
				getResultMessage = getDataMessageTmp.split(":")[1];
			}
		}
		
		if (userDataList != null) {
			getResult = 1;
			getResultMessage = "查詢到 " + userDataList.size() + " 筆有效的使用者資料";
		} else if (getResultMessage.equals("")) {
			getResult = 0;
			getResultMessage = "無法查詢到任何有效的使用者資料";
		}
		
		map.put("resultCode", getResult.toString());
		map.put("resultMessage", getResultMessage);
		map.put("userDataList", userDataList);
		return map;
	} 
	
	/* 根據帳號顯示對應資料 */
	@GetMapping("/webUser/ManageWebUser/{account}")
	public String doCreateDisplayManagedUserData(
			Model model,
			@PathVariable(value = "account") String account) {
		
		/* 訊息 */
		String operateMessage = "";
		
		/* 連結網址 */
		String destinationUrl = "";
		
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		
		/* 指定的使用者資料 */
		WebUserData managedUserData = new WebUserData();
		
		/* 檢查使用者身分 */
		if (userData == null) {
			operateMessage = "無法使用本功能，請確定您已經登入本系統！";;
		} else if (userData.getAccountLv().getLv() != -1) {
			operateMessage = "本帳號無法使用此功能！";
		} else if (userData.getStatus().equals("inactive") || userData.getStatus().equals("quit")) {
			operateMessage = "本帳號無法使用此功能！";
		}
		
		if (operateMessage.equals("")) {
			/* 調用服務裡的方法 */
			try {
				managedUserData = wus.getWebUserData(account);
			} catch (SQLException sqlE) {
				operateMessage = sqlE.getMessage();
			} 
		}
		
		/* 成功 */
		if (operateMessage.equals("") && managedUserData != null) {
			List<Gender> genderList = gds.getGenderList();
			/* 將物件managedUserData以"managedUserData"的名稱放入Attribute中 */
			model.addAttribute("managedUserData", managedUserData);
			/* 設定入Model中 */
			model.addAttribute("genderList", genderList);
			/* 前往個人資料畫面 */
			destinationUrl = "redirect:/webUser/DisplayManagedUserData";
		} else {
			/* 導回查詢畫面 */
			destinationUrl = "forward:/webUser/WebUserSearchForm";
		}
		
		return destinationUrl;
	}
	
	/* 根據輸入模式執行對應功能 */
	@PostMapping(value = "/webUser/ManageWebUser/{mode}", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doAdminOperate(
			Model model,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "account", required = false, defaultValue = "") String account,
			@RequestParam(value = "status", required = false, defaultValue = "") String status,
			@PathVariable(value = "mode", required = false) String mode) {
		
		/* 宣告參數 */
		Map<String, String> map = new HashMap<>();
		String operateMessage = "";
		Integer operateResult = -1;
		
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		
		/* 預防性後端輸入檢查 */
		operateMessage = doCheckAdminInput(userData, userId, account, status, mode);
		
		/* 特殊情況檢查
		 * 1.自己刪自己
		 * 2.刪預設的3個特定帳號 */
		if (operateMessage.equals("")) {
			if (mode.equals("delete") && userData.getAccount().equals(account)) {
				operateMessage = "無法由操作者刪除操作者自身的帳號!";
			} else if (mode.equals("delete")) {
				for (String defaultAccount:defaultAccounts) {
					if (defaultAccount.equals(account)) {
						operateMessage = "無法刪除系統內建的帳號!";
					}
				}
			}
		}
		
		/* 通過檢查 */
		if(operateMessage.equals("")) {
			switch(mode) {
				case "quit":
					status = "quit";
					Integer quitUserLv = Integer.parseInt(userId)/1000000 + 1;
					Boolean runQuit = true;
					/* 調用服務裡的方法 */
					try {
						/* 如果為管理員，先檢查是否仍有可登入的管理員帳號 */
						if (quitUserLv == -1) {
							if (wus.checkAdminAccess() - 1 == 0) {
								runQuit = false;
								operateMessage = "無法停用本帳號！系統要求至少需要維持一個可登入的管理員帳號";
							}
						}
						/* 如果本帳號停用後，無管理員可登入系統，則阻止 */
						if (runQuit) {
							/* 取得使用者個人資料 */
							WebUserData banedUserData = wus.getWebUserData(account);
							/* 執行停用 */
							operateResult = wus.adminChangeWebUserData(userId, status);
							/* 寄送Email */
							UserInfoController.doSendEmail(banedUserData.getAccount(), banedUserData.getEmail(), "", "adminQuit");
						}
					} catch (SQLException sqlE) {
						operateMessage = sqlE.getMessage();
					} catch (Exception e) {
						String quitMessageTmp = e.getMessage();
						operateMessage = quitMessageTmp.split(":")[1];
					}
					break;
				case "delete":
					/* 調用服務裡的方法 */
					try {
						operateResult = wus.deleteWebUserData(userId);
					} catch (SQLException sqlE) {
						operateMessage = sqlE.getMessage();
					}
					break;
				case "active":
					/* 重新啟用與初次啟用實質上是相同的操作 */
					status = (status.equals("inactive")) ? "active": status;
					status = (status.equals("quit")) ? "active": status;
					/* 調用服務裡的方法 */
					try {
						operateResult = wus.adminChangeWebUserData(userId, status);
					} catch (SQLException sqlE) {
						operateMessage = sqlE.getMessage();
					}
					break;
				default:
					operateMessage = "未提供此功能！";
					break;
			}
		}
		
		/* 成功 */
		operateMessage = (operateResult == 1) ? "順利完成指定的操作！" : operateMessage;
		operateMessage = (operateResult == 0 && operateMessage.equals("")) ? "無法完成指定的操作！" : operateMessage; 
		
		map.put("resultCode", operateResult.toString());
		map.put("resultMessage", operateMessage);
		return map;
	}
	
	/* 執行密碼重設 */
	@PostMapping(value = "/webUser/controller/WebUserResetPassword", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doResetWebUserPassword(
			@RequestParam(value = "inputUserId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "inputPassword", required = false, defaultValue = "") String password) {
		
		Map<String, String> map = new HashMap<>();
		
		/* 宣告參數 */
		Integer resetResult = -3;
		String resetMessage = "";
		
		/* 預防性後端檢查，正常時回傳1 */
		resetMessage = doCheckResetInput(userId, password);
		
		if (resetMessage.equals("")) {
			/* 調用服務裡的方法 */
			try {
				resetResult = wus.resetWebUserPassword(userId, password);
			} catch (SQLException sqlE) {
				String loginMessageTmp = sqlE.getMessage();
				resetMessage = loginMessageTmp.split(":")[1];
			}
		}
		
		map.put("resultCode", resetResult.toString());
		map.put("resultMessage", resetMessage);
		return map;
	}
	
	/* 傳送管理員後台新增表單所必需的資料 */
	@GetMapping(value = "/webUser/WebUserAddForm")
	public String doCreateManagedUserRegisterForm(Model model) {
		
		/* 取得下拉選單、單選、多選所需的固定資料 */
		List<UserWilling> willingList = wis.getUserWillingList();
		List<UserIdentity> identityList = ids.getIdentityList();
		List<FoodFervor> fervorList = fvs.getFoodFervorList();
		List<Gender> genderList = gds.getGenderList();
		List<CityInfo> cityInfoList = lcs.getLocationInfoList();
		
		/* 設定入Model中 */
		model.addAttribute("willingList", willingList);
		model.addAttribute("identityList", identityList);
		model.addAttribute("fervorList", fervorList);
		model.addAttribute("genderList", genderList);
		model.addAttribute("cityInfoList", cityInfoList);
		
		/* 前往註冊畫面 */
		return "webUser/WebUserAddForm";
	}
	
	/* 執行管理員新增 */
	@PostMapping(value = "/webUser/controller/WebUserAddForm", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doAdminInsertWebUser(
			Model model,
			@RequestParam(value = "userLv", defaultValue = "0") Integer lv,
			@RequestParam(value = "account", defaultValue = "") String account,
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "firstName", defaultValue = "") String firstName,
			@RequestParam(value = "lastName", defaultValue = "") String lastName,
			@RequestParam(value = "nickname", defaultValue = "") String nickname,
			@RequestParam(value = "gender", defaultValue = "N") String genderCode,
			@RequestParam(value = "birth", defaultValue = "1800-01-01") Date birth,
			@RequestParam(value = "fervorOption", defaultValue="{7}") List<String> fervorValue,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "phone", defaultValue = "") String phone,
			@RequestParam(value = "willing", defaultValue = "Y") String willingCode,
			@RequestParam(value = "locationCode", defaultValue = "0") Integer cityCode,
			@RequestParam(value = "addr0", defaultValue = "") String addr0,
			@RequestParam(value = "addr1", defaultValue = "") String addr1,
			@RequestParam(value = "addr2", defaultValue = "") String addr2,
			RedirectAttributes redirectAttributes) {
		
		Map<String, String> resultMap = new HashMap<>();
		String resultMessage = "";
		Integer insertResult = -1;
		
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		WebUserData reg_webUser = new WebUserData();
		
		if (userData == null || userData.getAccountLv().getLv() != -1) {
			resultMessage = "使用者未登入或權限不符！";
		} else if (!userData.getStatus().equals("active")) {
			resultMessage = "本帳號無法使用本服務！";
		}
		
		if (resultMessage.equals("")) {	
			Map<String, Object> map = doCheckNewWebUserObj(
					model, 
					lv, 
					account, 
					password,
					firstName,
					lastName,
					nickname,
					birth,
					email, 
					phone,
					addr0,
					addr1,
					addr2,
					genderCode,
					willingCode,
					fervorValue,
					cityCode);
			
			reg_webUser = (WebUserData)map.get("reg_webUser");
			resultMessage = (String) map.get("submitMessage");
		}
		
		/* 成功 */
		if (resultMessage.equals("")) {
			/* 調用服務裡的方法 */
			try {
				insertResult = wus.insertWebUserData(reg_webUser);
			} catch (SQLException sqlE) {
				resultMessage = "發生錯誤！" + sqlE.getMessage();
			}
			
			if (insertResult > 0) {
				resultMessage = "已將 " + reg_webUser.getAccount() + " 的帳號已成功建立";
			} 
		} 
		
		resultMap.put("resultCode", insertResult.toString());
		resultMap.put("resultMessage", resultMessage);
		
		return resultMap;
	}
	
	/* 執行管理員修改圖示 */
	@PostMapping(value = "/webUser/controller/WebUserAdminModifyIcon", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doAdminUpdateWebUserIcon(Model model,
			@RequestParam(value = "pic", required = false) CommonsMultipartFile picFile) 
	{
		/* 宣告參數 */
		Map<String, String> map = new HashMap<>();
		String message = "";
		Boolean updateIconUrlResult = false;
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		WebUserData managedUserData = (WebUserData) model.getAttribute("managedUserData");
		/* 更新用物件 */
		WebUserData updatedData = managedUserData;
		
		/* 檢查JavaBean物件 */
		if (userData == null) {
			message = "未登入系統，請登入後再進行操作！";
		} else if (userData.getAccountLv().getLv() != Integer.parseInt(userData.getUserId().substring(0, 1)) - 1) {
			message = "身分驗證失敗，請登入後重試一次！";
		} else if (userData.getStatus().equals("quit") || userData.getStatus().equals("inactive")) {
			message = "本帳號無法使用此功能";
		} else if (userData.getAccountLv().getLv() != -1) {
			message = "本帳號無法使用此功能";
		} else if (managedUserData.getAccountLv().getLv() != Integer.parseInt(managedUserData.getUserId().substring(0, 1)) - 1) {
			message = "欲操作的帳號無法執行修改，請檢查帳號資料的完整性/正確性";
		} else if (managedUserData.getStatus().equals("quit") || managedUserData.getStatus().equals("inactive")) {
			message = "欲操作的帳號無法執行修改，請先恢復帳號的權限!";
		} 
		
		if (picFile == null) {
			message = "未上傳任何檔案！";
		}
		
		/* 取出上傳檔案的檔名 */
		String realFileName = picFile.getOriginalFilename();
		/* 取出原有圖示的相對路徑 */
		String oldUrl = managedUserData.getIconUrl();
		/* 取得使用者ID */
		String userId = managedUserData.getUserId();
		/* 組成新圖示的相對路徑 */
		String newIconUrl = "/images/webUser/" + userId + "/" + realFileName;
		
		if (message.equals("")) {
			/* 執行圖片更新 */
			map = doUpdatePic(oldUrl, newIconUrl, picFile);
			/* 圖片更新成功 */
			if (map.get("resultCode").equals("true")) {
				/* 更新DB上的資料 */
				/* 調用服務裡的方法 */
				try {
					updatedData.setIconUrl(newIconUrl);
					updatedData.setVersion(updatedData.getVersion() + 1);
					System.out.println("url is: " + updatedData.getIconUrl() + "version is :" + updatedData.getVersion());
					updateIconUrlResult = (wus.updateWebUserIconUrl(updatedData) == 1) ? true : false;
				} catch (SQLException sqlE) {
					String getDataMessageTmp = sqlE.getMessage();
					message = getDataMessageTmp.split(":")[1];
				}
				/* 更新圖片、更新DB都成功 */
				if (updateIconUrlResult) {
					if (!oldUrl.equals("")) {
						/* 刪除舊檔暫存檔 */
						String oldFilePath = defaultAddress + oldUrl.substring(0, oldUrl.lastIndexOf(".")) + "_tmp" + oldUrl.substring(oldUrl.lastIndexOf("."));
						File deletedOldPic = new File(oldFilePath);
						if (deletedOldPic.exists()) {							
							/* 執行刪除 */
							updateIconUrlResult = deletedOldPic.delete();
						}
						message = (updateIconUrlResult) ? message : "圖示更新成功但移除暫存檔案失敗";
					}
					map.put("resultCode", updateIconUrlResult.toString());
				/* 更新圖片成功但更新DB失敗 */
				} else {
					/* 刪除新增的圖檔 */
					String newFilePath = defaultAddress + newIconUrl;
					File deletedNewPic = new File(newFilePath);
					Boolean killNewPic = deletedNewPic.delete();
					/* 重新命名舊圖檔 */
					if (killNewPic) {
						String oldFilePath = defaultAddress + oldUrl.substring(0, oldUrl.lastIndexOf(".")) + "_tmp" + oldUrl.substring(oldUrl.lastIndexOf("."));
						String delMessage = "";
						try {
							/* 複製檔案 */
							FileUtils.copyFile(new File(oldFilePath), new File(defaultAddress + oldUrl));
							/* 刪除暫存 */
							new File(oldFilePath).delete();
						} catch (IOException ioE) {
							delMessage = ioE.getMessage();
							message += delMessage;
						}
					}
				}
				message = (message.equals("")) ? "圖示已順利更新完成！" : message;
			} else {
				message = map.get("resultMessage");
			}
			map.put("resultMessage", message);
			return map;
		} else {
			map.put("resultCode", updateIconUrlResult.toString());
			map.put("resultMessage", message);
		}
		return map;
	}
	
	/* 執行管理員修改 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/webUser/controller/WebUserAdminModifyData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doAdminUpdateWebUser(
			Model model,
			@RequestParam(value = "newPassword", required = false, defaultValue="") String newPassword,
			@RequestParam(value = "newFirstName", required = false, defaultValue="") String newFirstName,
			@RequestParam(value = "newLastName", required = false, defaultValue="") String newLastName,
			@RequestParam(value = "newNickname", required = false, defaultValue="") String newNickname,
			@RequestParam(value = "newGender", required = false, defaultValue="") String newGender,
			@RequestParam(value = "newBirth", defaultValue = "1800-01-01") Date newBirth,
			@RequestParam(value = "newFervor", required = false, defaultValue="") String newFervor,
			@RequestParam(value = "newEmail", required = false, defaultValue="") String newEmail,
			@RequestParam(value = "newPhone", required = false, defaultValue="") String newPhone,
			@RequestParam(value = "newGetEmail", required = false, defaultValue="") String newGetEmail,
			@RequestParam(value = "newLocationCode", required = false, defaultValue="") Integer newLocationCode,
			@RequestParam(value = "newAddr0", required = false, defaultValue="") String newAddr0,
			@RequestParam(value = "newAddr1", required = false, defaultValue="") String newAddr1,
			@RequestParam(value = "newAddr2", required = false, defaultValue="") String newAddr2) {
		
		/* 宣告參數 */
		Map<String, String> map = new HashMap<>();
		String resultMessage = "";
		Integer updateResult = -1;
		/* 更新用的同型物件 */
		WebUserData updatedUserData = new WebUserData();
		
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData managedUserData = (WebUserData) model.getAttribute("managedUserData");
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		
		/* 從session取出陣列來繼續完成設定 */
		List<FoodFervor> fervorList = (List<FoodFervor>) model.getAttribute("fervorList");
		List<UserWilling> willingList = (List<UserWilling>) model.getAttribute("willingList");
		List<CityInfo> cityInfoList = (List<CityInfo>) model.getAttribute("cityInfoList");
		List<Gender> genderList = (List<Gender>) model.getAttribute("genderList");
		
		String fervorTemp = "";
		for (FoodFervor fervorItem: fervorList) {
			for (String fervor: newFervor.split(",")) {
				if (fervor.equals(fervorItem.getFervorCode().toString())) {
					if (!fervorTemp.equals("")) {
						fervorTemp += ",";
					}
					fervorTemp += fervorItem.getFervorItem();
				}
			}
		}
		String fervor = fervorTemp;
		
		UserWilling willingOption = new UserWilling();
		for (UserWilling willingValue: willingList) {
			if (willingValue.getWillingCode().equals(newGetEmail)) {
				willingOption = willingValue;
			}
		}
		
		CityInfo locationInfo = new CityInfo();
		for (CityInfo locationValue: cityInfoList) {
			if (locationValue.getCityCode() == newLocationCode) {
				locationInfo = locationValue;
			}
		}
		
		Gender gender = new Gender();
		for (Gender genderValue: genderList) {
			if (genderValue.getGenderCode().equals(newGender)) {
				gender = genderValue;
			}
		}
		
		/* 檢查JavaBean物件 */
		if (userData == null) {
			resultMessage = "未登入系統，請登入後再進行操作！";
		} else if (userData.getAccountLv().getLv() != Integer.parseInt(userData.getUserId().substring(0, 1)) - 1) {
			resultMessage = "身分驗證失敗，請登入後重試一次！";
		} else if (userData.getStatus().equals("quit") || userData.getStatus().equals("inactive")) {
			resultMessage = "本帳號無法使用此功能";
		} else if (userData.getAccountLv().getLv() != -1) {
			resultMessage = "本帳號無法使用此功能";
		} else if (managedUserData.getAccountLv().getLv() != Integer.parseInt(managedUserData.getUserId().substring(0, 1)) - 1) {
			resultMessage = "欲操作的帳號無法執行修改，請檢查帳號資料的完整性/正確性";
		} else if (managedUserData.getStatus().equals("quit") || managedUserData.getStatus().equals("inactive")) {
			resultMessage = "欲操作的帳號無法執行修改，請先恢復帳號的權限!";
		} 
		
		if (resultMessage.equals("")) {
			updatedUserData = new WebUserData(
				managedUserData.getUserId(), 
				managedUserData.getAccount(), 
				newPassword, 
				newFirstName, 
				newLastName, 
				newNickname,
				newBirth,
				fervor,
				newEmail,
				newPhone,
				managedUserData.getJoinDate(),
				newAddr0,
				newAddr1,
				newAddr2,
				managedUserData.getZest(),
				managedUserData.getVersion() + 1,
				managedUserData.getStatus(),
				managedUserData.getIconUrl(),
				managedUserData.getSignIn(),
				managedUserData.getAccountLv(),
				gender,
				willingOption,
				locationInfo);
		}
		
		Integer count = 0;
		/* 預防性後端檢查 */
		if (resultMessage.equals("")) {
			String tmpMessage = doCheckUpdateDataInput(updatedUserData, managedUserData).split(",")[1];
			resultMessage = (tmpMessage.equals("?")) ? "" : tmpMessage;
			count = Integer.parseInt(doCheckUpdateDataInput(updatedUserData, managedUserData).split(",")[0]);
		}
		
		/* 檢查密碼 */
		if (resultMessage.equals("") || resultMessage.equals("沒有輸入任何有效的修改內容，請重新操作")) {
			String resultTmp = doCheckPassword(newPassword);
			resultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (newPassword.equals(managedUserData.getPassword())) {
				count++;
			}
		}
		
		/* 檢查性別 */
		if (resultMessage.equals("")) {
			String resultTmp = doCheckGender(gender.getGenderCode());
			resultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (gender.getGenderCode().equals(managedUserData.getGender().getGenderCode())) {
				count++;
			}
		}
		
		/* 檢查生日 */
		if (resultMessage.equals("")) {
			String resultTmp = doCheckBirth(newBirth);
			resultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (newBirth.equals(managedUserData.getBirth())) {
				count++;
			}
		}
		
		/* 結算 */
		resultMessage = (count == 14) ? "沒有輸入任何有效的修改內容，請重新操作" : resultMessage;
		
		/* 檢查完畢 */
		if (resultMessage.equals("")) {
			
			/* 調用服務裡的方法 */
			try {
				updateResult = wus.updateWebUserData(updatedUserData);
			} catch (SQLException sqlE) {
				resultMessage = sqlE.getMessage();
			}
		}
		
		if (!resultMessage.equals("")) {
			if (resultMessage.indexOf(":") != -1) {	
				resultMessage = resultMessage.split(":")[1];
			}
		} 
		
		/* 修改成功 */
		if (updateResult == 1) {
			resultMessage = "修改成功";
			/* 更新設定值 */
			model.addAttribute("managedUserData", updatedUserData);
		} else if (updateResult != 1 && resultMessage.equals("")) {
			resultMessage = "修改失敗";
		}
		
		map.put("resultCode", updateResult.toString());
		map.put("resultMessage", resultMessage);
		return map;
	}
	
	/* 前往顯示註冊資料畫面 */
	@GetMapping(value = "/webUser/DisplayWebUserInfo")
	public String doGoDisplayInfo() {
		return "webUser/DisplayWebUserInfo";
	}
	
	/* 前往註冊結束畫面 */
	@GetMapping(value = "/webUser/WebUserRegisterResult")
	public String doGoRegisterResult() {
		return "webUser/WebUserRegisterResult";
	}
	
	/* 前往登入畫面 */
	@GetMapping(value = "/WebUserLogin")
	public String doGoLogin() {
		return "WebUserLogin";
	}
	
	/* 前往忘記密碼畫面 */
	@GetMapping(value = "/WebUserForgetForm")
	public String doGoForget() {		
		return "WebUserForgetForm";
	}
	
	/* 前往登入主畫面 */
	@GetMapping(value = "/webUser/WebUserMain")
	public String doGoWebUserMain() {
		return "webUser/WebUserMain";
	}
	
	/* 前往登出畫面 */
	@GetMapping(value = "/webUser/WebUserLogoutResult")
	public String doGoLogOut() {
		return "webUser/WebUserLogoutResult";
	}
	
	/* 前往停用結束畫面 */
	@GetMapping(value = "/webUser/WebUserQuitResult")
	public String doGoQuitResult() {
		return "webUser/WebUserQuitResult";
	}
	
	/* 前往顯示個人資料畫面 */
	@GetMapping(value = "/webUser/DisplayWebUserData")
	public String doGoDisplayWebUserData() {
		return "webUser/DisplayWebUserData";
	}
	
	/* 前往修改個人密碼畫面 */
	@GetMapping(value = "/webUser/WebUserModifyPassword")
	public String doGoWebUserModifyPassword() {
		return "webUser/WebUserModifyPassword";
	}
	
	/* 前往個人修改結束畫面 */
	@GetMapping(value = "/webUser/WebUserChangeResult")
	public String doGoWebUserChangeResult() {
		return "webUser/WebUserChangeResult";
	}
	
	/* 前往管理員用顯示個人資料畫面 */
	@GetMapping(value = "/webUser/DisplayManagedUserData")
	public String doGoDisplayManagedWebUserData() {
		return "webUser/DisplayManagedUserData";
	}
	
	/* 無輸入任何帳號則返回登入 */
	@GetMapping("/webUser/ManageWebUser")
	public String doGoBackToLogin() {
		return "webUser/WebUserLogin";
	}
	
	/* 前往重設密碼 */
	@GetMapping("/webUser/WebUserResetPassword")
	public String doGoResetPassword() {
		return "webUser/WebUserResetPassword";
	}
	
	/* 使用者註冊資料檢查 */
	public String doCheckRegisterInput(
			WebUserData reg_webUser, 
			Model model) {
		
		/* 傳回參數宣告 */
		String submitMessage = "";
		
		/* 是否符合條件 */
		Boolean inputIsOk = true;
		
		/* 取出參數 */
		String account = reg_webUser.getAccount();
		String password = reg_webUser.getPassword();
		String firstName = reg_webUser.getFirstName();
		String lastName = reg_webUser.getLastName();
		String nickname = reg_webUser.getNickname();
		Date birth = reg_webUser.getBirth();
		String fervor = reg_webUser.getFervor();
		String email = reg_webUser.getEmail();
		String phone = reg_webUser.getPhone();
		String addr0 = reg_webUser.getAddr0();
		String addr1 = reg_webUser.getAddr1();
		String addr2 = reg_webUser.getAddr2();
		
		Integer lv = reg_webUser.getAccountLv().getLv();
		String genderCode = reg_webUser.getGender().getGenderCode();
		String willingCode = reg_webUser.getGetEmail().getWillingCode();
		Integer cityCode = reg_webUser.getLocationInfo().getCityCode();
		
		/* 檢查身分 */
		switch (lv) {
			case -1:
			case 0:
			case 1:
				break;
			default:
				inputIsOk = false;
				break;
		}
		
		submitMessage = (inputIsOk) ? "" : "帳號身分錯誤";
		
		/* 檢查帳號 */
		if (inputIsOk) {
			String resultTmp = doCheckAccount(account, "submit");
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查密碼 */
		if (inputIsOk) {
			String resultTmp = doCheckPassword(password);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查中文姓氏 */
		if (inputIsOk) {
			String resultTmp = doCheckFirstName(firstName);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查中文名字 */
		if (inputIsOk) {
			String resultTmp = doCheckLastName(lastName);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查稱呼 */
		if (inputIsOk) {
			String resultTmp = doCheckNickname(nickname, lastName, "submit", "");
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 生理性別 */
		if (inputIsOk) {
			String resultTmp = doCheckGender(genderCode);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 西元生日 */
		if (inputIsOk) {
			String resultTmp = doCheckBirth(birth);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查偏好食物 */
		if (inputIsOk) {
			String resultTmp = doCheckFervor(fervor);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查Email */
		if (inputIsOk) {
			String resultTmp = doCheckEmail(email, "submit", "");
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查getEmail */
		if (inputIsOk) {
			String resultTmp = doCheckGetEmail(willingCode);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查電話 */
		if (inputIsOk) {
			String resultTmp = doCheckPhone(phone, "submit", "");
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查居住區域 */
		if (inputIsOk) {
			String resultTmp = doCheckCityCode(cityCode, "register");
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查生活地點一 */
		if (inputIsOk) {
			String resultTmp = doCheckAddr0(addr0, addr1, addr2);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查生活地點二 */
		if (inputIsOk) {
			String resultTmp = doCheckAddr1(addr0, addr1, addr2);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查生活地點三 */
		if (inputIsOk) {
			String resultTmp = doCheckAddr2(addr0, addr1, addr2);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		return submitMessage;
	}
	
	/* 使用者登入資料檢查 */
	public String doCheckLoginInput(String account, String password) {
		/* 傳回參數宣告 */
		String message = "";	
		
		/* 檢查帳號 */
		String resultTmp = doCheckAccount(account, "login");
		
		message = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
		
		/* 檢查密碼 */
		if (message.equals("")) {
			resultTmp = doCheckPassword(password);
			message = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
		}
		
		return message;
	}
	
	/* 使用者停用帳戶時的輸入檢查 */
	public String doCheckQuitInput(WebUserData userData) {
		String checkInputResult = "";
		
		/* 檢查JavaBean物件 */
		if (userData == null) {
			checkInputResult = "您似乎沒有登入本系統！請登入後再試一次";
		} else if (userData.getAccountLv().getLv() != Integer.parseInt(userData.getUserId().substring(0, 1)) - 1) {
			checkInputResult = "身分驗證失敗，請登入後重試一次！";
		} else if (userData.getStatus().equals("quit") || userData.getStatus().equals("inactive")) {
			checkInputResult = "本帳號無法使用此功能";
		} else {
			Integer checkResult = -1;
			Integer checkResult2 = -1;
			
			/* 調用服務裡的方法 */
			try {
				checkResult = wus.checkAccountExist(userData.getAccount());
			} catch (SQLException sqlE) {
				String quitMessageTmp = sqlE.getMessage();
				checkInputResult = quitMessageTmp.split(":")[1];
			}
			
			/* 調用服務裡的方法 */
			try {
				checkResult2 = wus.checkUserIdQuit(userData.getUserId());
			} catch (SQLException sqlE) {
				String quitMessageTmp = sqlE.getMessage();
				checkInputResult = quitMessageTmp.split(":")[1];
			}
			
			if (checkResult != 1) {
				checkInputResult = "異常的使用者資訊，請確認您已成功登入本系統！";
			} else if (checkResult2 == 0) {
				checkInputResult = "異常的使用者資訊，本帳號已被停用！";
			} else if (checkResult2 == -1) {
				checkInputResult = "異常的使用者資訊，請確認您已成功登入本系統！";
			}
		}		
		
		return checkInputResult;
	}
	
	/* 使用者修改密碼時的輸入檢查 */
	public String doCheckUpdatePasswordInput(WebUserData userData, String password, String confirmPassword) {
		
		String updateResultMessage = "";
		
		/* 檢查JavaBean物件 */
		if (userData == null) {
			updateResultMessage = "未登入系統，請登入後再進行操作！";
		} else if (userData.getAccountLv().getLv() != Integer.parseInt(userData.getUserId().substring(0, 1)) - 1) {
			updateResultMessage = "身分驗證失敗，請登入後重試一次！";
		} else if (userData.getStatus().equals("quit") || userData.getStatus().equals("inactive")) {
			updateResultMessage = "本帳號無法使用此功能";
		} else {
			/* 檢查密碼 */
			String oldPassword = userData.getPassword();
			
			if (!password.equals(confirmPassword)) {
				updateResultMessage = "密碼與確認密碼不一致！";
			} else if (password.equals(oldPassword)){
				updateResultMessage = "密碼未修改！";
			} else {
				String resultTmp = doCheckPassword(password);
				updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
				
				if (updateResultMessage.equals("")) {	
					String userId = userData.getUserId();
					Integer checkIdResult = -1;
					
					try {
						checkIdResult = wus.checkUserIdExist(userId);
					} catch(SQLException sqlE) {
						String quitMessageTmp = sqlE.getMessage();
						updateResultMessage = quitMessageTmp.split(":")[1];
					}
					updateResultMessage = (checkIdResult != 1) ? "使用者身分異常!請確定您已經登入本系統" : updateResultMessage;
				} 
			}
		}
		
		return updateResultMessage;
	}
	
	/* 使用者修改資料時的輸入檢查 */
	public String doCheckUpdateDataInput(
			WebUserData updatedUserData,
			WebUserData selfData) {
		
		String updateResultMessage = "";
		Integer count = 0;
		
		String oldFirstName = selfData.getFirstName();
		String oldLastName = selfData.getLastName();
		String oldNickname = selfData.getNickname();
		String oldFervor = selfData.getFervor();
		String oldEmail = selfData.getEmail();
		String oldPhone = selfData.getPhone();
		String oldGetEmail = selfData.getGetEmail().getWillingCode();
		Integer oldLocationCode = selfData.getLocationInfo().getCityCode();
		String oldAddr0 = selfData.getAddr0();
		String oldAddr1 = selfData.getAddr1();
		String oldAddr2 = selfData.getAddr2();
		
		String newFirstName = updatedUserData.getFirstName();
		String newLastName = updatedUserData.getLastName();
		String newNickname = updatedUserData.getNickname();
		String fervor = updatedUserData.getFervor();
		String newEmail = updatedUserData.getEmail();
		String newPhone = updatedUserData.getPhone();
		String getEmail = updatedUserData.getGetEmail().getWillingCode();
		Integer locationCode = updatedUserData.getLocationInfo().getCityCode();
		String newAddr0 = updatedUserData.getAddr0();
		String newAddr1 = updatedUserData.getAddr1();
		String newAddr2 = updatedUserData.getAddr2();
		
		/* 檢查中文姓氏 */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckFirstName(newFirstName);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (updateResultMessage.equals("") && newFirstName.equals(oldFirstName)) {
				count++;
			}
		}
		
		/* 檢查中文名字 */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckLastName(newLastName);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (updateResultMessage.equals("") && newLastName.equals(oldLastName)) {
				count++;
			}
		}
		
		/* 檢查稱呼 */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckNickname(newNickname, newLastName, "update", oldNickname);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (updateResultMessage.equals("") && newNickname.equals(oldNickname)) {
				count++;
			}
		}
		
		/* 檢查偏好食物 */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckFervor(fervor);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (fervor.equals(oldFervor) && updateResultMessage.equals("")) {
				count++;
			}
		}
		
		/* 檢查Email */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckEmail(newEmail, "update", oldEmail);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (newEmail.equals(oldEmail) && updateResultMessage.equals("")) {
				count++;
			} 
		}
		
		/* 檢查聯絡電話 */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckPhone(newPhone, "update", oldPhone);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (newPhone.equals(oldPhone) && updateResultMessage.equals("")) {
				count++;
			} 
		}
		
		/* 檢查接收促銷/優惠訊息 */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckGetEmail(getEmail);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (getEmail.equals(oldGetEmail) && updateResultMessage.equals("")) {
				count++;
			} 
		}
		
		/* 檢查區住區域 */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckCityCode(locationCode, "update");
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (locationCode == oldLocationCode && updateResultMessage.equals("")) {
				count++;
			}
		}
		
		/* 檢查生活地點一 */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckAddr0(newAddr0, newAddr1, newAddr2);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (newAddr0.equals(oldAddr0) && updateResultMessage.equals("")) {
				count++;
			}
		}
		
		/* 檢查生活地點二 */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckAddr1(newAddr0, newAddr1, newAddr2);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (newAddr1.equals(oldAddr1) && updateResultMessage.equals("")) {
				count++;
			}
		}
		
		/* 檢查生活地點三 */
		if (updateResultMessage.equals("")) {
			String resultTmp = doCheckAddr2(newAddr0, newAddr1, newAddr2);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (newAddr2.equals(oldAddr2) && updateResultMessage.equals("")) {
				count++;
			}
		}
		
		updateResultMessage = (updateResultMessage.equals("")) ? "?" : updateResultMessage;
		/* 結算有效變動項目 */
		return (count == 11) ? "11,沒有輸入任何有效的修改內容，請重新操作" : count.toString() + "," + updateResultMessage;
	}
	
	public String doCheckSelectUserDataInput(String selectedParameters, WebUserData userData) {
		String checkResult = "";
		
		String selectedAccount = selectedParameters.split(":")[0];
		if (checkResult.equals("")) {
			if (selectedAccount.length() > 20) {
				checkResult = "搜尋的帳號名稱過長！";
			} else if (!selectedAccount.matches("[0-9a-zA-Z]{1,20}") && !selectedAccount.equals("?")) {
				checkResult = "搜尋的帳號含有無效字元！";
			} 
		}
		
		String selectedNickname = selectedParameters.split(":")[1];
		if (checkResult.equals("")) {
			if (selectedNickname.length() > 20) {
				checkResult = "搜尋的稱呼名稱過長！";
			}
		}
		
		Integer selectedLocationCode = Integer.parseInt(selectedParameters.split(":")[3]);
		if (checkResult.equals("")) {
			String resultTmp = doCheckCityCode(selectedLocationCode, "search");
			checkResult = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
		}
		
		if (userData.getAccountLv().getLv() == -1) {
			String selectedStatus = selectedParameters.split(":")[6];
			if (checkResult.equals("")) {
				if (!selectedStatus.equals("?")) {
					switch(selectedStatus) {
						case "active":
						case "quit":
							break;
						default:
							checkResult = "帳號狀態設定異常";
							break;
					}
				}
			}
		}
		
		return checkResult;
	}
	
	public String doCheckAdminInput(WebUserData userData, String userId, String account, String status, String mode) {
		String checkMessage = "";	
		
		/* 檢查使用者身分 */
		if (userData == null) {
			checkMessage = "無法使用本功能，請確定您已經登入本系統！";;
		} else if (userData.getAccountLv().getLv() != Integer.parseInt(userData.getUserId().substring(0, 1)) - 1) {
			checkMessage = "身分驗證失敗，請登入後重試一次！";
		} else if (userData.getAccountLv().getLv() != -1) {
			checkMessage = "本帳號無法使用此功能！";
		} else if (userData.getStatus().equals("inactive") || userData.getStatus().equals("quit")) {
			checkMessage = "本帳號無法使用此功能！";
		}
		
		/* 檢查userId */
		if (checkMessage.equals("")) {
			String resultTmp = doCheckUserId(userId);
			checkMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
		}
		
		/* 檢查帳號 */
		if (checkMessage.equals("")) {
			String resultTmp = doCheckAccount(account, "adminOperate");
			checkMessage= (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
		}
		
		/* 檢查狀態、檢查模式與狀態的匹配 */
		if (checkMessage.equals("")) {
			switch(mode) {
				case "quit":
					if (status.equals(mode) || status.equals("inactive")) {
						checkMessage = "操作模式錯誤";
					} 
					break;
				case "delete":
					break;
				case "active":
				case "reactive":
					if (status.equals("active")) {
						checkMessage = "操作模式錯誤";
					}
					break;
				default:
					checkMessage = "操作模式錯誤";
					break;
			}
		}
		
		return checkMessage;
	}
	
	public String doCheckResetInput(String userId, String password) {
		String checkMessage = "";
		/* 檢查userId */
		checkMessage = doCheckUserId(userId);
		/* 檢查密碼 */
		checkMessage = (checkMessage.equals("")) ? doCheckPassword(password) : checkMessage;
		return checkMessage;
	}
	
	/* 統一檢查userId方法 */
	public String doCheckUserId(String userId) {
		String checkMessage = "";
		
		if (userId.equals("")) {
			checkMessage = "Id不可為空白";
		} else if (userId.length() != 7) {
			checkMessage = "Id長度錯誤";
		} else if (!userId.matches("[0-2]{1}[0-9]{6}")) {
			checkMessage = "Id格式錯誤";
		} else {
			Integer userIdCheckResult = -1;
			/* 調用服務裡的方法 */
			try {
				userIdCheckResult = wus.checkUserIdExist(userId);
			} catch (SQLException sqlE) {
				checkMessage = sqlE.getMessage();
			}
			checkMessage = (userIdCheckResult == 0) ? "Id不存在" : checkMessage;
		}
		
		return checkMessage;
	}
	
	/* 統一檢查帳號方法 */
	public String doCheckAccount(String account, String mode) {
		String submitMessage = "?";
		Boolean inputIsOk = true;
		
		if (account.equals("")) {
			submitMessage = "帳號不可為空白";
			inputIsOk = false;
		} else if (account.length() < 8 || account.length() > 20) {
			submitMessage = "帳號長度不符格式，僅接受8~20個字元";
			inputIsOk = false;
		} else if (account.matches("[1-9]{1}.")) {
			submitMessage = "帳號不可以數字開頭";
			inputIsOk = false;
		} else if (!account.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
			submitMessage = "帳號不符合格式";
			inputIsOk = false;
		} else {
			Integer accountCheckResult = -1;
			/* 調用服務裡的方法 */
			try {
				accountCheckResult = wus.checkAccountExist(account);
			} catch (SQLException sqlE) {
				submitMessage = sqlE.getMessage();
				inputIsOk = false;
			}
			
			if (mode.equals("submit")) {
				if (accountCheckResult == 1) {
					submitMessage = "帳號已存在，請挑選別的名稱作為帳號";
					inputIsOk = false;
				}
			}
		}
		
		return submitMessage + "," + inputIsOk.toString();
	}
	
	/* 統一檢查密碼方法 */
	public String doCheckPassword(String password) {
		String submitMessage = "?";
		Boolean inputIsOk = true;
		
		if (password.equals("")) {
			submitMessage = "密碼不可為空白";
			inputIsOk = false;
		} else if (password.length() < 8 || password.length() > 20) {
			submitMessage = "密碼長度不符格式，僅接受8~20個字元";
			inputIsOk = false;
		} else if (password.matches("[1-9]{1}.")) {
			submitMessage = "密碼不可以數字開頭";
			inputIsOk = false;
		} else if (!password.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
			submitMessage = "密碼不符合格式";
			inputIsOk = false;
		} 
		
		return submitMessage + "," + inputIsOk.toString();
	}
	
	/* 統一檢查姓氏方法 */
	public String doCheckFirstName(String firstName) {
		String message = "?";
		Boolean inputIsOk = true;
		
		if (firstName.equals("")) {
			message = "姓氏不可為空白";
			inputIsOk = false;
		} else if (firstName.length() > 3) {
			message = "姓氏長度過長，最多僅3個字元";
			inputIsOk = false;
		} else {
			Integer charCountBegin = 0;
			Boolean firstNameIsOk = true;
			/* 16進位表示 */
			Integer charChineseWordCountBegin = 0x4e00;
			Integer charChineseWordCountEnd = 0x9fff;
			for (Integer charIndex = charCountBegin; charIndex < firstName.length(); charIndex++) {
				int firstNameChar = firstName.charAt(charIndex);

				if (firstNameChar < charChineseWordCountBegin || firstNameChar > charChineseWordCountEnd) {
					firstNameIsOk = false;
				}
				
				if (!firstNameIsOk) {
					break;
				}
			}
			
			if (!firstNameIsOk) {
				message = "姓氏中含有非中文";
				inputIsOk = false;
			} 
		}
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查名字方法 */
	public String doCheckLastName(String lastName) {
		Boolean inputIsOk = true;
		String message = "?";
		
		if (lastName.equals("")) {
			message = "名字不可為空白";
			inputIsOk = false;
		} else if (lastName.length() > 3) {
			message = "名字長度過長，最多僅3個字元";
			inputIsOk = false;
		} else {
			Integer charCountBegin = 0;
			Boolean lastNameIsOk = true;
			/* 16進位表示 */
			Integer charChineseWordCountBegin = 0x4e00;
			Integer charChineseWordCountEnd = 0x9fff;
			for (Integer charIndex = charCountBegin; charIndex < lastName.length(); charIndex++) {
				int lastNameChar = lastName.charAt(charIndex);
				
				if (lastNameChar < charChineseWordCountBegin || lastNameChar > charChineseWordCountEnd) {
					lastNameIsOk = false;
				}
				
				if (!lastNameIsOk) {
					break;
				}
			}
			if (!lastNameIsOk) {
				message = "名字中含有非中文";
				inputIsOk = false;
			} 
		}
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查稱呼方法 */
	public String doCheckNickname(String nickname, String lastName, String mode, String oldNickname) {
		Boolean inputIsOk = true;
		String message = "?";
		
		if (nickname.equals("") && lastName.equals("")) {
			message = "稱呼不可為空白";
			inputIsOk = false;
		} else if (nickname.equals("") && !lastName.equals("")) {
			nickname = lastName;
		} else if (nickname.length() > 20){
			message = "稱呼長度過長";
			inputIsOk = false;
		} 
		
		if (message.equals("")) {
			Integer nicknameCheckResult = -1;
			/* 調用服務裡的方法 */
			try {
				nicknameCheckResult = wus.checkNicknameExist(nickname);
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
				inputIsOk = false;
			}
			
			if ((nicknameCheckResult == 1 && !mode.equals("update")) || (!nickname.equals(oldNickname) && mode.equals("update") && nicknameCheckResult == 1)) {
				message = "稱呼已存在，請挑選別的名稱作為稱呼";
				inputIsOk = false;
			}
		}
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查生理性別方法 */
	public String doCheckGender(String genderCode) {
		Boolean inputIsOk = true;
		String message = "?";
		switch(genderCode) {
			case "M":
			case "W":
			case "N":
				break;
			default:
				message = "生理性別設定異常";
				inputIsOk = false;
				break;
		}
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查生日方法 */
	public String doCheckBirth(Date birth) {
		Boolean inputIsOk = true;
		String message = "?";
		if (birth == Date.valueOf(LocalDate.now())) {
			message = "生日異常";
			inputIsOk = false;
		} else if (birth == Date.valueOf("1800-01-01")) {
			message = "生日異常";
			inputIsOk = false;
		} else if (Date.valueOf(birth.toString()).after(Date.valueOf(LocalDate.now()))) {
			message = "生日異常";
			inputIsOk = false;
		} else if (Date.valueOf(birth.toString()).after(Date.valueOf(LocalDate.now().minus(18, ChronoUnit.YEARS)))) {
			message = "未滿18歲，無法申辦本服務";
			inputIsOk = false;
		} else {
			inputIsOk = true;
		}
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查偏好食物方法 */
	public String doCheckFervor(String fervor) {
		Boolean inputIsOk = true;
		String message = "?";
		
		if (fervor.equals("")) {
			message = "偏好食物不可為空白";
			inputIsOk = false;
		} else if (fervor.length() > 50){
			message = "偏好食物長度過長";
			inputIsOk = false;
		}
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查Email方法 */
	public String doCheckEmail(String email, String mode, String oldEmail) {
		Boolean inputIsOk = true;
		String message = "?";
		
		if (email.equals("")) {
			message = "信箱資訊不可為空白";
			inputIsOk = false;
		} else if (email.indexOf("@") == -1 || email.split("@").length > 2 || email.indexOf(" ") != -1) {
			message = "信箱資訊格式錯誤";
			inputIsOk = false;
		} else if (email.indexOf("@") == email.length() - 1 || email.lastIndexOf(".") == email.length() - 1) {
			message = "信箱資訊格式錯誤";
			inputIsOk = false;
		} else {
			Integer emailCheckResult = -1;
			/* 調用服務裡的方法 */
			try {
				emailCheckResult = wus.checkEmailExist(email);
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
				inputIsOk = false;
			}
			
			if ((emailCheckResult == 1 && !mode.equals("update")) || (!oldEmail.equals(email) && mode.equals("update") && emailCheckResult == 1)){
				message = "該聯絡信箱已被註冊，請挑選別的聯絡信箱";
				inputIsOk = false;
			} 
		}
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查是否接受促銷資訊方法 */
	public String doCheckGetEmail(String willingCode) {
		Boolean inputIsOk = true;
		String message = "?";
		
		switch(willingCode) {
			case "Y":
			case "N":
					break;
			default:
				message = "接收促銷/優惠訊息設定異常";
				inputIsOk = false;
				break;
		}
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查電話方法 */
	public String doCheckPhone(String phone, String mode, String oldPhone) {
		Boolean inputIsOk = true;
		String message = "?";
		
		if (phone.equals("")) {
			message = "連絡電話不可為空白";
			inputIsOk = false;
		} else if(phone.length() < 9 || phone.indexOf(" ") != -1 || !phone.matches("[0]{1}[2-9]{1}[0-9]{7,9}")) {
			message = "連絡電話格式錯誤";
			inputIsOk = false;
		} else if (phone.substring(0, 2).equals("09") && phone.length() != 10) {
			message = "行動電話格式錯誤";
			inputIsOk = false;
		} else if (!phone.substring(0, 2).equals("09") && phone.length() == 10) {
			message = "室內電話格式錯誤";
			inputIsOk = false;
		} else {
			Integer phoneCheckResult = -1;
			/* 調用服務裡的方法 */
			try {
				phoneCheckResult = wus.checkPhoneExist(phone);
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
				inputIsOk = false;
			}
			
			if ((!mode.equals("update") && phoneCheckResult == 1) || (!phone.equals(oldPhone) && mode.equals("update") && phoneCheckResult == 1)){
				message = "該聯絡電話已被註冊，請輸入別的聯絡電話";
				inputIsOk = false;
			}
		}
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查居住區域方法 */
	public String doCheckCityCode(Integer cityCode, String mode) {
		Boolean inputIsOk = true;
		String message = "?";
		
		switch(cityCode) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
			case 13:
			case 14:
			case 15:
			case 16:
			case 17:
			case 18:
			case 19:
			case 20:
			case 21:
			case 22:
			case 23:
				break;
			default:
				message = "居住區域設定異常";
				inputIsOk = false;
				break;
		}
		if (mode.equals("search") && cityCode == 0) {
			inputIsOk = true;
			message = "?";
		}
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查生活地點一方法 */
	public String doCheckAddr0(String addr0, String addr1, String addr2) {
		Boolean inputIsOk = true;
		String message = "?";
		
		if (addr0.equals("")) {
			message = "生活地點一不可為空白";
			inputIsOk = false;
		} else if (addr0.length() > 65) {
			message = "生活地點一超過長度限制";
			inputIsOk = false;
		} else if (addr0.equals(addr1) || addr0.equals(addr2)) {
			message = "生活地點重複填寫";
			inputIsOk = false;
		} 
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查生活地點二方法 */
	public String doCheckAddr1(String addr0, String addr1, String addr2) {
		Boolean inputIsOk = true;
		String message = "?";
		
		if (addr1.length() > 65) {
			message = "生活地點二超過長度限制";
			inputIsOk = false;
		} else if (addr1.equals(addr0) || (addr1.equals(addr2) && !addr2.equals(""))) {
			message = "生活地點重複填寫";
			inputIsOk = false;
		} 
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查生活地點三方法 */
	public String doCheckAddr2(String addr0, String addr1, String addr2) {
		Boolean inputIsOk = true;
		String message = "?";
		
		if (addr2.length() > 65) {
			message = "生活地點三超過長度限制";
			inputIsOk = false;
		} else if (addr2.equals(addr0) || (addr2.equals(addr1) && !addr1.equals(""))) {
			message = "生活地點重複填寫";
			inputIsOk = false;
		} 
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查驗證碼方法 */
	public String doCheckCheckCode(String inputCheckCode, String checkCode, String registerEmail, String email) {
		String message = "";
		if (inputCheckCode.equals("")) {
			message = "驗證碼不可為空白";
		} else if (checkCode == null || registerEmail == null) {
			message = "未產生驗證碼";
		} else if (!inputCheckCode.equals(checkCode)) {
			message = "驗證碼檢查失敗";
		} else if (!registerEmail.equals(email)) {
			message = "email資訊不吻合";
		} else if (!checkCode.matches("[0-9A-Z]{8}")) {
			message = "驗證碼錯誤";
		}
		return message;
	}
	
	/* 使用者物件初始化 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> doCheckNewWebUserObj(
			Model model,
			Integer lv,
			String account,
			String password,
			String firstName,
			String lastName,
			String nickname,
			Date birth,
			String email,
			String phone,
			String addr0,
			String addr1,
			String addr2,
			String genderCode,
			String willingCode,
			List<String> fervorValue,
			Integer cityCode) {
		
		Map<String, Object> map = new HashMap<>();
		
		String submitMessage= "";
		String fervorTemp = "";
		
		/* 建立物件 */
		WebUserData reg_webUser = new WebUserData(
				"", 
				account, 
				password, 
				firstName, 
				lastName, 
				nickname, 
				birth, 
				"",
				email, 
				phone, 
				Date.valueOf(today), 
				addr0, 
				addr1, 
				addr2, 
				BigDecimal.ZERO, 
				0, 
				"inactive", 
				"",
				null,
				new UserIdentity(), 
				new Gender(), 
				new UserWilling(), 
				new CityInfo());
		
		/* 從session取出陣列來繼續完成設定 */
		List<UserIdentity> identityList = (List<UserIdentity>) model.getAttribute("identityList");
		List<Gender> genderList = (List<Gender>) model.getAttribute("genderList");
		List<FoodFervor> fervorList = (List<FoodFervor>) model.getAttribute("fervorList");
		List<UserWilling> willingList = (List<UserWilling>) model.getAttribute("willingList");
		List<CityInfo> cityInfoList = (List<CityInfo>) model.getAttribute("cityInfoList");
		
		/* 設定物件 */
		switch (lv) {
			case -1:
				reg_webUser.setStatus("inactive");
				break;
			case 0:
				reg_webUser.setStatus("active");
				break;
			case 1:
				reg_webUser.setStatus("inactive");
				break;
			default:
				submitMessage = "帳號身分異常";
				break;
		}
		
		/* 用forEach直到取出符合條件的值來 */
		for (UserIdentity identity : identityList) {
			if (identity.getLv() == lv) {
				/* 將符合條件的值放入物件 */
				reg_webUser.setAccountLv(identity);
			}
		}
		
		for (Gender gender: genderList) {
			if (gender.getGenderCode().equals(genderCode)) {
				/* 將符合條件的值放入物件 */
				reg_webUser.setGender(gender);
			}
		}
		
		for (FoodFervor fervorItem: fervorList) {
			for (String fervor: fervorValue) {
				if (fervor.equals(fervorItem.getFervorCode().toString())) {
					if (!fervorTemp.equals("")) {
						fervorTemp += ",";
					}
					fervorTemp += fervorItem.getFervorItem();
				}
			}
		}
		reg_webUser.setFervor(fervorTemp);
		
		for (UserWilling getEmail: willingList) {
			if (getEmail.getWillingCode().equals(willingCode)) {
				/* 將符合條件的值放入物件 */
				reg_webUser.setGetEmail(getEmail);
			}
		}
		
		for (CityInfo locationInfo: cityInfoList) {
			if (locationInfo.getCityCode().equals(cityCode)) {
				/* 將符合條件的值放入物件 */
				reg_webUser.setLocationInfo(locationInfo);
			}
		}
		
		/* 預防性後端輸入檢查，正常時回傳空字串 */
		submitMessage = (submitMessage.equals("")) ? doCheckRegisterInput(
				reg_webUser, 
				model) 
				: submitMessage;
		
		map.put("reg_webUser", reg_webUser);
		map.put("submitMessage", submitMessage);
		return map;
	}
	
	/* 圖像檔案處理 */
	public Map<String, String> doUpdatePic(String oldIconUrl, String newIconUrl, CommonsMultipartFile pic) {
		/* 變數宣告 */
		Map<String, String> map = new HashMap<>();
		String oldIconPath = (oldIconUrl.equals("")) ? "" : defaultAddress + oldIconUrl;
		String newIconPath = defaultAddress + newIconUrl;
		String message = "";
		Boolean delResult = false;
		Boolean creResult = false;
		/* 如果原本沒有圖檔，就直接新建檔案 */
		if (oldIconPath.equals("")) {
			try {
				creResult = doCreateNewIcon(newIconPath, pic);
			} catch (Exception e) {
				message = e.getMessage();
			}
			
			if (message.equals("") && !creResult) {
				message = "無法新增圖檔！";
			}
		/* 如果原本有圖檔，就需要先刪除後再新增 */	
		} else {
			try {
				delResult = doDeleteOldIcon(oldIconPath);
			} catch (Exception e) {
				message = e.getMessage();
			}
			
			/* 成功才繼續 */
			if (delResult) {
				try {
					creResult = doCreateNewIcon(newIconPath, pic);
				} catch (Exception e) {
					message = e.getMessage();
				}
			/* 失敗 */
			} else {
				message = "無法刪除舊有檔案！";
			}
			
			if (message.equals("") && !creResult) {
				message = "無法新增圖檔！";
			}
		}
		map.put("resultCode", creResult.toString());
		map.put("resultMessage", message);
		return map;
	}
	
	/* 刪除舊檔案 */
	public Boolean doDeleteOldIcon(String oldIconPath) throws Exception{
		/* 參數宣告 */
		Boolean delResult = false;
		Boolean userDirExist = false;
		Boolean picDelResult = false;
		/* 使用者目錄 */
		String userDirPath = oldIconPath.substring(0,oldIconPath.lastIndexOf("/"));
		/* 確認使用者目錄是否已建立 */
		File userDir = new File(userDirPath);
		/* 有建立且為目錄 */
		if (userDir.exists() && userDir.isDirectory()) {
			userDirExist = true;
		/* 未建立則自動建立 */
		} else if (!userDir.exists()) {
			userDirExist = userDir.mkdir();
		/* 有同名檔案但非目錄 */
		} else if (userDir.exists() && !userDir.isDirectory()) {
			/* 先嘗試移除原有檔案 */
			userDirExist = userDir.delete();
			/* 成功後再建立目錄 */
			userDirExist = (userDirExist) ? userDir.mkdir() : userDirExist;
		}
		/* 確認該路徑是否有檔案存在 */
		if (userDirExist) {
			File picFile = new File(oldIconPath);
			if (!picFile.exists()) {
				delResult = false;
			/* 有才執行刪除 */
			} else {
				/* 產生暫存檔檔名 */
				String tempFileName = picFile.getName();
				String finalTempFileName = tempFileName.substring(0, tempFileName.lastIndexOf(".")) + "_tmp" + tempFileName.substring(tempFileName.lastIndexOf("."));
				/* 刪除前先建立備份檔 */
				/* 檢查備份檔是否存在 */
				File tmpOldPic = new File(finalTempFileName);
				/* 存在則先刪除舊有的 */
				if (tmpOldPic.exists()) {
					tmpOldPic.delete();
				}
				/* 複製檔案 */
				FileUtils.copyFile(picFile, tmpOldPic);
				/* 再執行刪除 */
				picDelResult = picFile.delete();
			}
		}
		delResult = (picDelResult) ? true : delResult;
		return delResult;
	}
	
	/* 建立新檔案 */
	public Boolean doCreateNewIcon(String newIconPath, CommonsMultipartFile pic) throws Exception{
		/* 參數宣告 */
		Boolean creResult = false;
		Boolean userDirExist = false;
		Boolean picCreResult = false;
		/* 使用者目錄 */
		String userDirPath = newIconPath.substring(0,newIconPath.lastIndexOf("/"));
		/* 確認使用者目錄是否已建立 */
		File userDir = new File(userDirPath);
		/* 有建立且為目錄 */
		if (userDir.exists() && userDir.isDirectory()) {
			userDirExist = true;
		/* 未建立則自動建立 */
		} else if (!userDir.exists()) {
			userDirExist = userDir.mkdir();
		/* 有同名檔案但非目錄 */
		} else if (userDir.exists() && !userDir.isDirectory()) {
			/* 先嘗試移除原有檔案 */
			userDirExist = userDir.delete();
			/* 成功後再建立目錄 */
			userDirExist = (userDirExist) ? userDir.mkdir() : userDirExist;
		}
		/* 檢查目錄下是否已有該檔案 */
		if (userDirExist) {
			File picFile = new File(newIconPath);
			/* 有則先刪再建 */
			if (picFile.exists()) {
				/* 先刪除 */
				picCreResult = picFile.delete();
				/* 再建立 */
				picCreResult = (picCreResult) ? doWritePicIntoFile(picFile, pic) : picCreResult;
			/* 沒有就直接執行新增 */
			} else {
				picCreResult = doWritePicIntoFile(picFile, pic);
			}
		}
		creResult = (picCreResult) ? true : creResult;
		return creResult;
	}
	
	/* 寫入新檔案 */
	public Boolean doWritePicIntoFile(File picFile, CommonsMultipartFile pic) throws Exception{
		Boolean writeResult = false;
		/* 使用CommonsMultipartFile的getInputStream()取得InputStream */
		try (InputStream is = pic.getInputStream();
			FileOutputStream fos = new FileOutputStream(picFile)) 
		{
			byte[] buffer = new byte[1024]; 
			int length = -1;
			while((length = is.read(buffer)) != -1) {
				fos.write(buffer, 0, length);
			}
			writeResult = true;
		} catch(IOException ioE) {
			throw new Exception(ioE.getMessage());
		}
		return writeResult;
	}
}
