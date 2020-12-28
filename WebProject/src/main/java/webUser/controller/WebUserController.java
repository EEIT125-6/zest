package webUser.controller;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
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
		"managedUserData"
		})
@Controller
@RequestMapping("/webUser")
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
		return "webUser/WebUserRegisterForm";
	}

	/* 執行註冊資料檢查 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/controller/WebUserRegisterForm")
	public String doRegisterSubmit(Model model,
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
		
		String checkCode = ((String) model.getAttribute("checkCode")).toUpperCase();
		String registerEmail = (String) model.getAttribute("registerEmail");
		
		/* 設定物件 */
		switch (lv) {
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
		
		/* 追加檢查checkCode */
		if (submitMessage.equals("")) {
			if (inputCheckCode.equals("")) {
				submitMessage = "驗證碼不可為空白";
			} else if (checkCode == null || registerEmail == null) {
				submitMessage = "未產生驗證碼";
			} else if (!inputCheckCode.equals(checkCode)) {
				submitMessage = "驗證碼檢查失敗";
			} else if (!registerEmail.equals(email)) {
				submitMessage = "email資訊不吻合";
			} else if (!checkCode.matches("[0-9A-Z]{8}")) {
				submitMessage = "驗證碼錯誤";
			}
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
			return "redirect:/webUser/WebUserRegisterForm";			
		}
	}

	/* 執行使用者資料送出 */
	@PostMapping(value = "/controller/DisplayWebUserInfo/confirm")
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
		String insertResultPage = "webUser/WebUserRegisterForm";
		
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
				insertResultPage = "webUser/WebUserLogin";
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
			destinationUrl = "redirect:/webUser/WebUserRegisterForm";
		}		
		
		return destinationUrl;
	}
	
	/* 取消註冊 */
	@GetMapping(value = "/controller/DisplayWebUserInfo/undo")
	public String doRegisterUndo(
			SessionStatus sessionStatus) {
		/* 清空SessionAttribute */
		sessionStatus.setComplete();
		/* 返回註冊畫面 */
		return "redirect:/webUser/WebUserRegisterForm";
	}
	
	/* 執行登入檢查 */
	@PostMapping(value = "/controller/WebUserLogin", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doLoginCheck(
			Model model,
			@RequestParam(value = "account", defaultValue="") String account,
			@RequestParam(value = "password", defaultValue="") String password) {
		
		/* 宣告欲回傳的參數 */
		Map<String, String> map = new HashMap<>();
		
		String inputCheckResult = "";
		Integer accountCheckResult = -3;
		String loginMessage = "";
		
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
		return map;
	}
	
	/* 執行登出 */
	@GetMapping(value = "/controller/WebUserMain/Logout")
	public String doLogOut(
			Model model,
			RedirectAttributes redirectAttributes,
			SessionStatus sessionStatus) {
		
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		
		String nickname = userData.getNickname();
		String logoutMessage = "謝謝您的使用，" + nickname + "!";
		
		/* 清空SessionAttribute */
		sessionStatus.setComplete();
		/* 將物件insertResultMessage以"insertResultMessage"的名稱放入flashAttribute中 */
		redirectAttributes.addFlashAttribute("logoutMessage", logoutMessage);
		/* 前往登出畫面 */
		return "redirect:/webUser/WebUserLogoutResult";
	}
	
	/* 執行帳號停用 */
	@PostMapping(value = "/controller/WebUserMain/Quit")
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
			/* 調用服務裡的方法 */
			try {
				quitUserData.setVersion(quitUserData.getVersion() + 1);
				quitUserData.setStatus("quit");
				deleteResult = wus.quitWebUserData(quitUserData);
			} catch (SQLException sqlE) {
				String quitMessageTmp = sqlE.getMessage();
				quitMessage = quitMessageTmp.split(":")[1];
			}
		}
		
		/* 成功變更 */
		if (deleteResult == 1) {
			quitMessage = "感謝您的使用，" + quitUserData.getNickname() + "！我們有緣再見...";
			/* 清空SessionAttribute */
			sessionStatus.setComplete();
			redirectPage = "/";
		} 
		
		/* 將物件quitMessage以"quitMessage"的名稱放入flashAttribute中 */
		redirectAttributes.addFlashAttribute("quitMessage", quitMessage);
		/* 將物件redirectPag以"redirectPag"的名稱放入flashAttribute中 */
		redirectAttributes.addFlashAttribute("redirectPag", redirectPage);
		/* 導向停用結束畫面 */
		return "redirect:/webUser/WebUserQuitResult";
	}
	
	/* 以Ajax取回使用者個人資料 */
	@PostMapping(value = "/controller/DisplaySelfData", produces = "application/json; charset=UTF-8")
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
	@GetMapping(value = "/controller/WebUserMain/Modify")
	public String doDisplayOwnUserData() {
		return "redirect:/webUser/DisplayWebUserData";
	}
	
	/* 準備顯示修改密碼畫面 */
	@GetMapping(value = "/controller/WebUserModifyPassword")
	public String doWebUserModifyPassword() {
		return "redirect:/webUser/WebUserModifyPassword";
	}
	
	/* 執行修改密碼 */
	@PostMapping(value = "/controller/WebUserModifyPassword")
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
		updateResultMessage = doCheckUpdatePasswordInput(userData, password, confirmPassword);
		
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
	@PostMapping(value = "/WebUserModifyData")
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
			return "redirect:/webUser/WebUserLogin";
		}
		
		/* 設定入Model中 */
		model.addAttribute("selfData", selfData);
		return "webUser/WebUserModifyData";
	}
	
	/* 執行密碼以外的資料修改 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/controller/WebUserModifyData")
	public @ResponseBody Map<String, String> doUpdateWebUserData(
			Model model,
			RedirectAttributes redirectAttributes,
			@RequestParam(value = "oldFirstName", required = false, defaultValue="") String oldFirstName,
			@RequestParam(value = "newFirstName", required = false, defaultValue="") String newFirstName,
			@RequestParam(value = "oldLastName", required = false, defaultValue="") String oldLastName,
			@RequestParam(value = "newLastName", required = false, defaultValue="") String newLastName,
			@RequestParam(value = "oldNickname", required = false, defaultValue="") String oldNickname,
			@RequestParam(value = "newNickname", required = false, defaultValue="") String newNickname,
			@RequestParam(value = "oldFervor", required = false, defaultValue="") String oldFervor,
			@RequestParam(value = "newFervor", required = false, defaultValue="") String newFervor,
			@RequestParam(value = "oldEmail", required = false, defaultValue="") String oldEmail,
			@RequestParam(value = "newEmail", required = false, defaultValue="") String newEmail,
			@RequestParam(value = "inputCheckCode", required = false, defaultValue="") String inputCheckCode,
			@RequestParam(value = "oldPhone", required = false, defaultValue="") String oldPhone,
			@RequestParam(value = "newPhone", required = false, defaultValue="") String newPhone,
			@RequestParam(value = "oldGetEmail", required = false, defaultValue="") String oldGetEmail,
			@RequestParam(value = "newGetEmail", required = false, defaultValue="") String newGetEmail,
			@RequestParam(value = "oldLocationCode", required = false, defaultValue="") Integer oldLocationCode,
			@RequestParam(value = "newLocationCode", required = false, defaultValue="") Integer newLocationCode,
			@RequestParam(value = "oldAddr0", required = false, defaultValue="") String oldAddr0,
			@RequestParam(value = "newAddr0", required = false, defaultValue="") String newAddr0,
			@RequestParam(value = "oldAddr1", required = false, defaultValue="") String oldAddr1,
			@RequestParam(value = "newAddr1", required = false, defaultValue="") String newAddr1,
			@RequestParam(value = "oldAddr2", required = false, defaultValue="") String oldAddr2,
			@RequestParam(value = "newAddr2", required = false, defaultValue="") String newAddr2) {
		
		/* 宣告參數 */
		Map<String, String> map = new HashMap<>();
		String updateResultMessage = "";
		Integer updateResult = -1;
		
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		String checkCode = ((String) model.getAttribute("checkCode")).toUpperCase();
		String registerEmail = (String) model.getAttribute("registerEmail");
		
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
		
		/* 更新用的同型物件 */
		WebUserData updatedUserData = new WebUserData(
				userData.getUserId(), 
				userData.getAccount(), 
				userData.getPassword(), 
				newFirstName, 
				newLastName, 
				newNickname,
				userData.getBirth(),
				fervor,
				newEmail,
				newPhone,
				userData.getJoinDate(),
				newAddr0,
				newAddr1,
				newAddr2,
				userData.getZest(),
				userData.getVersion() + 1,
				userData.getStatus(),
				userData.getIconUrl(),
				userData.getAccountLv(),
				userData.getGender(),
				willingOption,
				locationInfo);
		
		/* 預防性後端檢查 */
		if (updateResultMessage.equals("")) {
			updateResultMessage = doCheckUpdateDataInput(
					updatedUserData,
					oldFirstName,
					oldLastName,
					oldNickname,
					oldFervor,
					oldEmail,
					oldPhone,
					oldGetEmail,
					oldLocationCode,
					oldAddr0,
					oldAddr1,
					oldAddr2);
		}
		
		/* 追加檢查checkCode */
		if (updateResultMessage.equals("")) {
			if (!newEmail.equals(oldEmail)) {	
				if (inputCheckCode.equals("")) {
					updateResultMessage = "驗證碼不可為空白";
				} else if (checkCode == null || registerEmail == null) {
					updateResultMessage = "未產生驗證碼";
				} else if (!inputCheckCode.equals(checkCode)) {
					updateResultMessage = "驗證碼檢查失敗";
				} else if (!registerEmail.equals(newEmail)) {
					updateResultMessage = "email資訊不吻合";
				} else if (!checkCode.matches("[0-9A-Z]{8}")) {
					updateResultMessage = "驗證碼錯誤";
				}
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
	@GetMapping(value = "/WebUserSearchForm")
	public String doCreateWebUserSearchForm(Model model) 
	{
		/* 取得下拉選單、單選、多選所需的固定資料 */
		List<UserWilling> willingList = wis.getUserWillingList();
		List<FoodFervor> fervorList = fvs.getFoodFervorList();
		List<CityInfo> cityInfoList = lcs.getLocationInfoList();
		
		/* 設定入Model中 */
		model.addAttribute("willingList", willingList);
		model.addAttribute("fervorList", fervorList);
		model.addAttribute("cityInfoList", cityInfoList);
		return "webUser/WebUserSearchForm";
	}
	
	/* 準備顯示搜尋畫面 */
	@GetMapping(value = "/controller/WebUserMain/Search")
	public String doDisplaySearchPage() {
		return "redirect:/webUser/WebUserSearchForm";
	}
	
	/* 回傳所有有效使用者的資料 */
	@PostMapping(value = "/controller/WebUserSearchForm/All", produces="application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> doSelectWebUserAllData(Model model) {
		
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		Integer getResult = -1;
		String getResultMessage = "";
		
		/* 產生資料陣列 */
		List<WebUserData> userDataList = new ArrayList<>();
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		
		/* 檢查身分 */
		if (userData == null) {
			getResultMessage = "無法使用本功能，請確定您已經登入本系統！";
		} else if (userData.getStatus().equals("inactive") || userData.getStatus().equals("quit")) {
			getResultMessage = "本帳號無法使用此功能！";
		}
		
		if (getResultMessage.equals("")) {
			/* 調用服務裡的方法 */
			try {
				userDataList = wus.getAllWebUserData(userData.getAccountLv().getLv(), userData.getStatus());
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
	
	/* 回傳所有有效使用者的資料 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/controller/WebUserSearchForm", produces="application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> doSelectWebUserData(
			Model model,
			@RequestParam(value = "selectedAccount", defaultValue = "?") String selectedAccount,
			@RequestParam(value = "selectedNickname", defaultValue = "?") String selectedNickname,
			@RequestParam(value = "selectedFervor", defaultValue = "?") String selectedFervor,
			@RequestParam(value = "selectedLocationCode", defaultValue = "0") Integer selectedLocationCode,
			@RequestParam(value = "selectedStatus", defaultValue = "?") String selectedStatus) {
		
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
						+ String.valueOf(lv) + ":" + status + ":?";
			} else {
				selectedParameters = selectedAccount + ":" + selectedNickname + ":" 
						+ selectedFervor + ":" + selectedLocationCode + ":" 
						+ String.valueOf(lv) + ":" + status + ":" + selectedStatus;
			}
			
			/* 預防性後端輸入檢查 */
			getResultMessage = doCheckSelectUserDataInput(selectedParameters, userData);
		}
		
		if (getResultMessage.equals("")) {
			/* 調用服務裡的方法 */
			try {
				userDataList = wus.getOtherWebUserData(selectedParameters);
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
	@GetMapping("/ManageWebUser/{account}")
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
			/* 將物件managedUserData以"managedUserData"的名稱放入Attribute中 */
			model.addAttribute("managedUserData", managedUserData);
			/* 前往個人資料畫面 */
			destinationUrl = "redirect:/webUser/DisplayManagedUserData";
		} else {
			/* 導回查詢畫面 */
			destinationUrl = "forward:/webUser/WebUserSearchForm";
		}
		
		return destinationUrl;
	}
	
	/* 根據輸入模式執行對應功能 */
	@PostMapping("/ManageWebUser/{mode}")
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
		
		/* 通過檢查 */
		if(operateMessage.equals("")) {
			switch(mode) {
				case "quit":
					/* 調用服務裡的方法 */
					try {
						operateResult = wus.adminChangeWebUserData(userId, status);
					} catch (SQLException sqlE) {
						operateMessage = sqlE.getMessage();
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
				case "reactive":
					/* 重新啟用與初次啟用實質上是相同的操作 */
					status = (status.equals("reactive")) ? "active": status;
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
	@PostMapping(value = "/controller/WebUserResetPassword", produces = "application/json; charset=UTF-8")
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
	
	/* 傳送表單所必需的資料 */
	@GetMapping(value = "/WebUserAddForm")
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
	
	/* 前往顯示註冊資料畫面 */
	@GetMapping(value = "/DisplayWebUserInfo")
	public String doGoDisplayInfo() {
		return "webUser/DisplayWebUserInfo";
	}
	
	/* 前往註冊結束畫面 */
	@GetMapping(value = "/WebUserRegisterResult")
	public String doGoRegisterResult() {
		return "webUser/WebUserRegisterResult";
	}
	
	/* 前往登入畫面 */
	@GetMapping(value = "/WebUserLogin")
	public String doGoLogin() {
		return "webUser/WebUserLogin";
	}
	
	/* 前往忘記密碼畫面 */
	@GetMapping(value = "/WebUserForgetForm")
	public String doGoForget() {		
		return "webUser/WebUserForgetForm";
	}
	
	/* 前往登入主畫面 */
	@GetMapping(value = "/WebUserMain")
	public String doGoWebUserMain() {
		return "webUser/WebUserMain";
	}
	
	/* 前往登出畫面 */
	@GetMapping(value = "WebUserLogoutResult")
	public String doGoLogOut() {
		return "webUser/WebUserLogoutResult";
	}
	
	/* 前往停用結束畫面 */
	@GetMapping(value = "WebUserQuitResult")
	public String doGoQuitResult() {
		return "webUser/WebUserQuitResult";
	}
	
	/* 前往顯示個人資料畫面 */
	@GetMapping(value = "DisplayWebUserData")
	public String doGoDisplayWebUserData() {
		return "webUser/DisplayWebUserData";
	}
	
	/* 前往修改個人密碼畫面 */
	@GetMapping(value = "WebUserModifyPassword")
	public String doGoWebUserModifyPassword() {
		return "webUser/WebUserModifyPassword";
	}
	
	/* 前往個人修改結束畫面 */
	@GetMapping(value = "WebUserChangeResult")
	public String doGoWebUserChangeResult() {
		return "webUser/WebUserChangeResult";
	}
	
	/* 前往管理員用顯示個人資料畫面 */
	@GetMapping(value = "DisplayManagedUserData")
	public String doGoDisplayManagedWebUserData() {
		return "webUser/DisplayManagedUserData";
	}
	
	/* 無輸入任何帳號則返回登入 */
	@GetMapping("/ManageWebUser")
	public String doGoBackToLogin() {
		return "webUser/WebUserLogin";
	}
	
	/* 前往重設密碼 */
	@GetMapping("/WebUserResetPassword")
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
			switch(genderCode) {
				case "M":
				case "W":
				case "N":
					break;
				default:
					submitMessage = "生理性別設定異常";
					inputIsOk = false;
					break;
			}
		}
		
		/* 西元生日 */
		if (inputIsOk) {
			if (birth == Date.valueOf(LocalDate.now())) {
				submitMessage = "生日異常";
				inputIsOk = false;
			} else if (birth == Date.valueOf("1800-01-01")) {
				submitMessage = "生日異常";
				inputIsOk = false;
			} else if (Date.valueOf(birth.toString()).after(Date.valueOf(LocalDate.now()))) {
				submitMessage = "生日異常";
				inputIsOk = false;
			} else if (Date.valueOf(birth.toString()).after(Date.valueOf(LocalDate.now().minus(18, ChronoUnit.YEARS)))) {
				submitMessage = "未滿18歲，無法申辦本服務";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
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
			String resultTmp = doCheckCityCode(cityCode);
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
			String oldFirstName,
			String oldLastName,
			String oldNickname,
			String oldFervor,
			String oldEmail,
			String oldPhone,
			String oldGetEmail,
			Integer oldLocationCode,
			String oldAddr0,
			String oldAddr1,
			String oldAddr2) {
		
		String updateResultMessage = "";
		Integer count = 0;
		
		/* 檢查JavaBean物件 */
		if (updatedUserData == null) {
			updateResultMessage = "未登入系統，請登入後再進行操作！";
		} else if (updatedUserData.getAccountLv().getLv() != Integer.parseInt(updatedUserData.getUserId().substring(0, 1)) - 1) {
			updateResultMessage = "身分驗證失敗，請登入後重試一次！";
		} else if (updatedUserData.getStatus().equals("quit") || updatedUserData.getStatus().equals("inactive")) {
			updateResultMessage = "本帳號無法使用此功能";
		} 
		
		String newFirstName = (updatedUserData == null) ? "" : updatedUserData.getFirstName();
		String newLastName = (updatedUserData == null) ? "" : updatedUserData.getLastName();
		String newNickname = (updatedUserData == null) ? "" : updatedUserData.getNickname();
		String fervor = (updatedUserData == null) ? "" : updatedUserData.getFervor();
		String newEmail = (updatedUserData == null) ? "" : updatedUserData.getEmail();
		String newPhone = (updatedUserData == null) ? "" : updatedUserData.getPhone();
		String getEmail = (updatedUserData == null) ? "" : updatedUserData.getGetEmail().getWillingCode();
		Integer locationCode = (updatedUserData == null) ? 0 : updatedUserData.getLocationInfo().getCityCode();
		String newAddr0 = (updatedUserData == null) ? "" : updatedUserData.getAddr0();
		String newAddr1 = (updatedUserData == null) ? "" : updatedUserData.getAddr1();
		String newAddr2 = (updatedUserData == null) ? "" : updatedUserData.getAddr2();
		
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
			String resultTmp = doCheckCityCode(locationCode);
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
		
		/* 結算有效變動項目 */
		return (count == 11) ? "沒有輸入任何有效的修改內容，請重新操作" : updateResultMessage;
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
			String resultTmp = doCheckCityCode(selectedLocationCode);
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
	public String doCheckCityCode(Integer cityCode) {
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
}
