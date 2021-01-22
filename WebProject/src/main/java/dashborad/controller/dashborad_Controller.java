package dashborad.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import board.service.BoardService;
import model.BookingBean;
import model.CartItemBean;
import service.BookingService;
import service.CartService;
import util.GlobalService;
import webUser.controller.UserInfoController;
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
import xun.model.BoardBean;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.StoreService;

@Controller
@SessionAttributes({
	"cartYearList",
	"userYearList",
	"willingList",
	"fervorList",
	"identityList",
	"genderList",
	"userFullData",
	"managedUserData"
})
public class dashborad_Controller {
	/* By Mimicker0903 */
	/* Store Service */
	@Autowired
	StoreService ss;
	
	/* By George017 2021/01/20 */
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
	
	/* Booking Service */
	@Autowired
	BookingService bks;
	
	/* Board Service */
	@Autowired
	BoardService bds;
	
	/* Cart Service */
	@Autowired
	CartService cts;
	
	//去管理員後台目錄
	@GetMapping("/adminBack")
	public String adminBack(
			) {
		return "adminBack";
	}
	
	//去商家後台目錄
	@GetMapping("/storeBack")
	public String storeBack(
			) {
		return "storeBack";
	}
	
	//以下管理員統計資料//
	@GetMapping("/dashborad_order")
	public String dashborad_order(Model model) {
		List<String> cartYearList = getCartYearList();
		model.addAttribute("cartYearList", cartYearList);
		return "dashborad-orderAnalysis";
	}
	
	@GetMapping("/dashborad_book")
	public String dashborad_book(
	
			) {
		return "dashborad-bookAnalysis";
	}
	
	@GetMapping("/dashborad_comment")
	public String dashborad_comment(
	
			) {
		return "dashborad-commentAnalysis";
	}
	
	@GetMapping("/dashborad_user")
	public String dashborad_user(Model model) {
		List<String> cartYearList = getCartYearList();
		List<String> userYearList = getUserYearList();
		model.addAttribute("cartYearList", cartYearList);
		model.addAttribute("userYearList", userYearList);
		return "dashborad-userAnalysis";
	}
	//以上管理員統計資料//
	
	//以下管理員管理資料//
	@GetMapping("/adminStore")
	public String adminStore(
			Model model
			) {
		return "adminAdminSystem-Store";
	}
	
	@GetMapping("/adminProduct")
	public String adminProduct(
			Model model
			) {
		return "adminAdminSystem-Product";
	}
	
	@GetMapping("/adminAccount")
	public String adminAccount(Model model) {
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
		return "adminAdminSystem-Account";
	}
	
	/* 新增使用者 */
	@GetMapping(value = "/adminAccountAdd")
	public String doCreateRegisterForm(Model model) {
		
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
		
		/* 前往新增畫面 */
		return "adminAdminSystem-Account-Add";
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
		String realFileName = picFile.getOriginalFilename().replace('<', ' ').replace('>', ' ').trim();
		/* 取出原有圖示的相對路徑 */
		String oldUrl = managedUserData.getIconUrl();
		/* 取得使用者ID */
		String userId = managedUserData.getUserId();
		/* 組成新圖示的相對路徑 */
		String newIconUrl = "/images/webUser/" + userId + "/" + realFileName;
		
		if (message.equals("")) {
			/* 執行圖片更新 */
			map = GlobalService.doUpdatePic(oldUrl, newIconUrl, picFile);
			/* 圖片更新成功 */
			if (map.get("resultCode").equals("true")) {
				/* 更新DB上的資料 */
				/* 調用服務裡的方法 */
				try {
					updatedData.setIconUrl(newIconUrl);
					updatedData.setVersion(updatedData.getVersion() + 1);
					updateIconUrlResult = (wus.updateWebUserData(updatedData) == 1) ? true : false;
				} catch (SQLException sqlE) {
					String getDataMessageTmp = sqlE.getMessage();
					message = getDataMessageTmp.split(":")[1];
				}
				/* 更新圖片、更新DB都成功 */
				if (updateIconUrlResult) {
					if (!oldUrl.equals("")) {
						/* 刪除舊檔暫存檔 */
						String oldFilePath = GlobalService.getUploadUserIconPath() + oldUrl.substring(0, oldUrl.lastIndexOf(".")) + "_tmp" + oldUrl.substring(oldUrl.lastIndexOf("."));
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
					String newFilePath = GlobalService.getUploadUserIconPath() + newIconUrl;
					File deletedNewPic = new File(newFilePath);
					Boolean killNewPic = deletedNewPic.delete();
					/* 重新命名舊圖檔 */
					if (killNewPic) {
						String oldFilePath = GlobalService.getUploadUserIconPath() + oldUrl.substring(0, oldUrl.lastIndexOf(".")) + "_tmp" + oldUrl.substring(oldUrl.lastIndexOf("."));
						String delMessage = "";
						try {
							/* 複製檔案 */
							FileUtils.copyFile(new File(oldFilePath), new File(GlobalService.getUploadUserIconPath() + oldUrl));
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
	
	/* 執行管理員重設圖示 */
	@PostMapping(value = "/webUser/controller/WebUserAdminResetModifyIcon", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doAdminResetWebUserIcon(Model model) {
		/* 宣告參數 */
		Map<String, String> map = new HashMap<>();
		String message = "";
		Boolean resetIconUrlResult = false;
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
		
		/* 取出原有圖示的相對路徑 */
		String oldUrl = managedUserData.getIconUrl();
		String oldIconPath = (oldUrl.equals("")) ? "" : GlobalService.getUploadUserIconPath() + oldUrl;
		
		/* 非預設值才執行刪除舊檔 */
		if (message.equals("") && !oldIconPath.equals("")) {
			/* 執行圖片刪除 */
			try {
				resetIconUrlResult = GlobalService.doDeleteOldIcon(oldIconPath);
				if (resetIconUrlResult) {
					/* 更新DB上的資料 */
					/* 調用服務裡的方法 */
					updatedData.setIconUrl("");
					updatedData.setVersion(updatedData.getVersion() + 1);
					/* 執行DB端更新 */
					resetIconUrlResult = (wus.updateWebUserData(updatedData) == 1) ? true : false;
					/* 更新圖片、更新DB都成功 */
					if (resetIconUrlResult) {
						/* 刪除舊檔暫存檔 */
						String oldFilePath = GlobalService.getUploadUserIconPath() + oldUrl.substring(0, oldUrl.lastIndexOf(".")) + "_tmp" + oldUrl.substring(oldUrl.lastIndexOf("."));
						File deletedOldPic = new File(oldFilePath);
						if (deletedOldPic.exists()) {							
							/* 執行刪除 */
							resetIconUrlResult = deletedOldPic.delete();
						}
						message = (resetIconUrlResult) ? message : "圖示還原成功但移除暫存檔案失敗";
					/* 更新圖片成功但更新DB失敗 */
					} else {
						/* 重新命名舊圖檔 */
						String oldFilePath = GlobalService.getUploadUserIconPath() + oldUrl.substring(0, oldUrl.lastIndexOf(".")) + "_tmp" + oldUrl.substring(oldUrl.lastIndexOf("."));
						String delMessage = "";
						try {
							/* 複製檔案 */
							FileUtils.copyFile(new File(oldFilePath), new File(GlobalService.getUploadUserIconPath() + oldUrl));
							/* 刪除暫存 */
							new File(oldFilePath).delete();
						} catch (IOException ioE) {
							delMessage = ioE.getMessage();
							message += delMessage;
						}
					}
					message = (message.equals("")) ? "圖示已順利還原完成！" : message;
				}
			} catch (Exception e) {
				message = e.getMessage();
			}
		} else if (message.equals("") && oldIconPath.equals("")) {
			message = "無法回復預設值！因為已經為預設圖示";
		}
		/* 將資訊放入map，準備回傳 */
		map.put("resultCode", resetIconUrlResult.toString());
		map.put("resultMessage", message);
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
		
		/* 不允許第三方登入修改密碼 */
		if (resultMessage.equals("") && managedUserData.getPassword() == null && newPassword.equals("")) {
			resultMessage = "第三方登入的帳號無法修改密碼!";
		}
		
		if (resultMessage.equals("")) {
			updatedUserData = new WebUserData(
				managedUserData.getUserId(), 
				managedUserData.getAccount(), 
				newPassword, 
				newFirstName, 
				newLastName, 
				newNickname.trim(),
				newBirth,
				fervor,
				newEmail.trim(),
				newPhone,
				managedUserData.getJoinDate(),
				newAddr0.trim(),
				newAddr1.trim(),
				newAddr2.trim(),
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
			/* 非第三方登入才做密碼檢查 */
			if (managedUserData.getPassword() != null) {
				String resultTmp = GlobalService.doCheckPassword(newPassword);
				resultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
				if (newPassword.equals(managedUserData.getPassword())) {
					count++;
				}
			} else {
				count++;
			}
		}
		
		/* 檢查性別 */
		if (resultMessage.equals("")) {
			String resultTmp = GlobalService.doCheckGender(gender.getGenderCode());
			resultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (gender.getGenderCode().equals(managedUserData.getGender().getGenderCode())) {
				count++;
			}
		}
		
		/* 檢查生日 */
		if (resultMessage.equals("")) {
			String resultTmp = GlobalService.doCheckBirth(newBirth);
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
			destinationUrl = "redirect:/adminAccount-display";
		} else {
			/* 導回查詢畫面 */
			destinationUrl = "forward:/adminAccount";
		}
		
		return destinationUrl;
	}
	
	/* 根據輸入模式執行對應功能 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/webUser/ManageWebUser/{mode}", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doAdminOperate(
			Model model,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			@RequestParam(value = "account", required = false, defaultValue = "") String account,
			@RequestParam(value = "status", required = false, defaultValue = "") String status,
			@PathVariable(value = "mode", required = false) String mode,
			HttpServletRequest request) {
		
		/* 宣告參數 */
		Map<String, String> map = new HashMap<>();
		String operateMessage = "";
		Integer operateResult = -1;
		String contextPath = request.getContextPath();
		
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		
		/* 預防性後端輸入檢查 */
		operateMessage = doCheckAdminInput(userData, userId, account, status, mode);
		
		/* 通過檢查 */
		if(operateMessage.equals("")) {
			switch(mode) {
				case "quit":
					status = "quit";
					Integer quitUserLv = Integer.parseInt(userId)/1000000 + 1;
					Boolean runQuit = true;
					/* 調用服務裡的方法 */
					try {
						/* 如果停用的對象為管理員帳號，先禁止"自己停用自己"的操作，再檢查是否仍有可登入的管理員帳號 */
						if (quitUserLv == -1) {
							if (userData.getAccount().equals(account)) {
								runQuit = false;
								operateMessage = "您無法停用當前正在使用的帳號！";
							} else if (wus.checkAdminAccess() - 1 == 0) {
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
							UserInfoController.doSendEmail(banedUserData.getAccount(), banedUserData.getEmail(), "", "adminQuit", contextPath);
						}
						/* 將被停用的使用者離線 */
						if (operateResult == 1) {
							Map<String, Object> userMap = (Map<String, Object>) context.getAttribute("userMap");
							/* 理論上該Map上至少要有操作的管理員帳號的相對物件，所以為空為異常強況 */
							if (userMap.isEmpty()) {
								operateResult = 0;
								operateMessage = "發生異常！請考慮重新登入本系統或聯絡技術人員";
							} else {
								/* 透過帳號取得Session物件 */
								HttpSession bannedSession = (HttpSession) userMap.get(account);
								/* 檢查是否處於有效階段？ */
								if (bannedSession != null) {
									/* 無效該使用者的Session */
									bannedSession.invalidate();
								}
								/* 沒異常就繼續維持resultCode */
								operateResult = 1;
							}
						}
					} catch (SQLException sqlE) {
						operateMessage = sqlE.getMessage();
					} catch (Exception e) {
						String quitMessageTmp = e.getMessage();
						operateMessage = quitMessageTmp.split(":")[1];
					}
					break;
				case "active":
					/* 重新啟用與初次啟用實質上是相同的操作 */
					Boolean FirstTimeUse = (status.equals("inactive")) ? true : false;
					status = (status.equals("inactive")) ? "active": status;
					status = (status.equals("quit")) ? "active": status;
					/* 調用服務裡的方法 */
					try {
						operateResult = wus.adminChangeWebUserData(userId, status);
						/* 成功才寄送Email */
						if (operateResult == 1) {
							/* 由ID取得使用者資訊 */
							WebUserData activedUserData = wus.getWebUserDataById(userId);
							/* 設定屬於哪種情境 */
							String adMinMode = (FirstTimeUse) ? "adminActivate" : "adminReActive";
							/* 寄送Email */
							Boolean sendResult = UserInfoController.doSendEmail(activedUserData.getAccount(), activedUserData.getEmail(), "", adMinMode, contextPath);
							operateResult = (sendResult) ? 1 : 0;
						}
					} catch (SQLException sqlE) {
						operateMessage = sqlE.getMessage();
					} catch (Exception e) {
						String quitMessageTmp = e.getMessage();
						operateMessage = quitMessageTmp.split(":")[1];
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
	
	/* 前往管理員用顯示個人資料畫面 */
	@GetMapping(value = "/adminAccount-display")
	public String doGoDisplayManagedWebUserData() {
		return "adminAdminSystem-Account-Display";
	}
	//以上管理員管理資料//
	
	//以下商家統計資料//
	@GetMapping("/storeSt")
	public String storeSt(
			Model model
			) {
		return "storeStatistics-storeContent";
	}
	//以上商家統計資料//
	
	//以下商家管理資料//
	@GetMapping("/storeAd")
	public String storeAd(
			Model model
			) {
		WebUserData userFullData  = (WebUserData) model.getAttribute("userFullData");
		List<StoreBean> listAllStore= ss.getMemberAllStore(userFullData);
		model.addAttribute("listAllStore", listAllStore);
		System.out.println("+++");
		System.out.println(listAllStore);
		System.out.println("+++");
		return "storeAdminSystem-storeContent";
	}
	
	@GetMapping("/storeAdClick")
	public String storeAdClick(
			Model model
			,@RequestParam Integer stId
			) {
		model.addAttribute("id", stId);
		return "storeAdminSystem-storeClick";
	}
	
	@GetMapping("/storeAdProduct")
	public String storeAdClickProduct(
			Model model
			,@RequestParam(value = "id") Integer stId
			) {
		List<ProductInfoBean> list3 = ss.getProductInfoBeans(stId);
		model.addAttribute("Products", list3);
		model.addAttribute("id", stId);
		return "storeAdminSystem-storeClick-product";
	}
	//以上商家管理資料//
	
	/* 後臺用資料 By George017 2021/01/20 */
	/* 將使用者資料按縣市區域分組統計 */
	@PostMapping(value = "/controller/localCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getLocationCharData(Model model) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			try {
				/* 取出縣市列表 */
				List<CityInfo> cityInfoList = lcs.getLocationInfoList();
				/* 取回所有使用者列表 */
				List<WebUserData> userList = wus.getAllWebUserData();
				/* 遍歷 */
				for (CityInfo cityInfo: cityInfoList) {
					for (WebUserData user: userList) {
						/* 區域代碼一致 */
						if (user.getLocationInfo().getCityCode() == cityInfo.getCityCode()) {
							if (map.get(cityInfo.getCityName()) == null) {								
								map.put(cityInfo.getCityName(), 1);
							} else {
								int tmp = (int) map.get(cityInfo.getCityName());
								map.put(cityInfo.getCityName(), tmp + 1);
							}
						}
					}
				}
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 將使用者資料按生理性別分組統計 */
	@PostMapping(value = "/controller/genderCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getGenderCharData (Model model) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			try {
				/* 取出生理性別列表 */
				List<Gender> genderList = gds.getGenderList();
				/* 取回所有使用者列表 */
				List<WebUserData> userList = wus.getAllWebUserData();
				/* 遍歷 */
				for (Gender gender: genderList) {
					for (WebUserData user: userList) {
						/* 性別代碼一致 */
						if (user.getGender().getGenderCode().equals(gender.getGenderCode())) {
							if (map.get(gender.getGenderText()) == null) {								
								map.put(gender.getGenderText(), 1);
							} else {
								int tmp = (int) map.get(gender.getGenderText());
								map.put(gender.getGenderText(), tmp + 1);
							}
						}
					}
				}
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 將使用者資料按加入時段分組統計(一次僅會顯示一個年度的資料，預設值為2020) */
	@PostMapping(value = "/controller/joinDateCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getJoinDateCharData (
			Model model, 
			@RequestParam(value = "year", defaultValue = "2020") String year) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			try {
				/* 取回註冊時間列表 */
				List<LocalDate> joinDateList = wus.getAllWebUserJoinDate(year);
				/* 取回該年度所有註冊的使用者列表 */
				List<WebUserData> userList = wus.getAllYearWebUserData(year);
				/* 遍歷 */
				for (LocalDate jDate: joinDateList) {
					for (WebUserData user: userList) {
						/* 年份、月份一致 */
						if (user.getJoinDate().toLocalDate().getYear() == jDate.getYear() && user.getJoinDate().toLocalDate().getMonth() == jDate.getMonth()) {
							if (map.get(String.valueOf(jDate.getYear()) + "-" + jDate.getMonth().toString()) == null) {
								map.put(String.valueOf(jDate.getYear()) + "-" + jDate.getMonth().toString(), 1);
							} else {
								int tmp = (int) map.get(String.valueOf(jDate.getYear()) + "-" + jDate.getMonth().toString());
								map.put(String.valueOf(jDate.getYear()) + "-" + jDate.getMonth().toString(), tmp + 1);
							}
						}
					}
				}
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 將使用者按有無使用過訂位系統來分組統計(used/not used) */
	@PostMapping(value = "/controller/bookingUsageCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBookingUsageCharData(Model model) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		String message = "";
		Integer totalUser = 0;
		
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			try {
				/* 取回所有註冊的使用者列表 */
				List<WebUserData> userList = wus.getAllWebUserData();
				totalUser = userList.size();
				/* 取出所有訂單資料 */
				List<BookingBean> bookingList = bks.allBooking();
				/* 遍歷 */
				for (WebUserData user: userList) {
					for (BookingBean bookingData: bookingList) {
						/* 使用者ID有出現在訂位資料代表有使用過 */
						if (user.getUserId().equals(bookingData.getUser_id().getUserId())) {
							if (map.get(user.getUserId()) == null) {
								map.put(user.getUserId(), 1);
							} else {
								int tmp = (int) map.get(user.getUserId());
								map.put(user.getUserId(), tmp + 1);
							}
						}
					}
				}
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
		}
		resultMap.put("used", map.size());
		resultMap.put("not used", totalUser - map.size());
		message = (message.equals("")) ? "成功" : message;
		
		resultMap.put("message", message);
		return resultMap;
	}
	
	/* 將非取消的訂單資料按用途分組統計 */
	@PostMapping(value = "/controller/bookingGoalCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBookingGoalCharData(Model model) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有訂單資料 */
			List<BookingBean> bookingList = bks.allBooking();
			/* 遍歷 */
			for (BookingBean bookingData: bookingList) {
				/* 非取消 */
				if (bookingData.getStatus() != 0) {
					/* 用途符合時 */
					if (map.get(bookingData.getPurpose()) == null) {
						map.put(bookingData.getPurpose(), 1);
					} else {
						int tmp = (int) map.get(bookingData.getPurpose());
						map.put(bookingData.getPurpose(), tmp + 1);
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 將非取消的訂單資料裡的人數按餐廳分類分組統計(*未排除已下線的商店) */
	@PostMapping(value = "/controller/bookingTypeCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBookingTypeCharData(Model model) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有訂單資料 */
			List<BookingBean> bookingList = bks.allBooking();
			/* 取出所有店家資料 */
			List<StoreBean> storeList = ss.getAllStore();
			/* 遍歷 */
			for (BookingBean bookingData: bookingList) {
				for (StoreBean storeData: storeList) {
					/* 非取消 */
					if (bookingData.getStatus() != 0) {
						/* 餐廳出現在訂位資料中 */
						if (bookingData.getRestaurant().equals(storeData.getStname())) {
							/* 找出餐廳的類型 */
							String storeType = storeData.getSclass();
							if (map.get(storeType) == null) {
								map.put(storeType, bookingData.getNumber());
							} else {
								int tmp = (int) map.get(storeType);
								map.put(storeType, tmp + bookingData.getNumber());
							}
						}
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 查詢各分類的有效評分(將排除空值)，平均數為float型別 */
	@PostMapping(value = "/controller/boardStarCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBoardStarCharData(Model model) {
		Map<String, Object> resultMap = new HashMap<>();
		/* 總分 */
		Map<String, Object> map = new HashMap<>();
		/* 總評分數 */
		Map<String, Object> countMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有評論資料 */
			List<BoardBean> boardList = bds.getAllcomment();
			/* 遍歷 */
			for (BoardBean boardData: boardList) {
				/* 排除部分空值 */
				if (boardData.getContext() != null && boardData.getStar() != null && boardData.getName() != null) {
					/* 取店家類型 */
					String storeType = boardData.getStorebean().getSclass();
					/* 準備累加分數 */
					if (map.get(storeType) == null && countMap.get(storeType) == null) {
						map.put(storeType, boardData.getStar());
						countMap.put(storeType, 1);
						float avg = boardData.getStar() / 1.0f;
						resultMap.put(storeType, avg);
					} else if (map.get(storeType) != null && countMap.get(storeType) != null) {
						int tmp = (int) map.get(storeType);
						map.put(storeType, tmp + boardData.getStar());
						int countTmp = (int) countMap.get(storeType);
						countMap.put(storeType, countTmp + 1);
						float avg = (int) map.get(storeType) /(float) countMap.get(storeType);
						resultMap.put(storeType, avg);
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		resultMap.put("message", message);
		return resultMap;
	}
	
	/* 查詢各分類的有效評分(將排除空值)，統計其留言數 */
	@PostMapping(value = "/controller/boardCountsCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBoardCountsCharData(Model model) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有評論資料 */
			List<BoardBean> boardList = bds.getAllcomment();
			/* 遍歷 */
			for (BoardBean boardData: boardList) {
				/* 排除部分空值 */
				if (boardData.getContext() != null && boardData.getStar() != null && boardData.getName() != null) {
					/* 取店家類型 */
					String storeType = boardData.getStorebean().getSclass();
					/* 準備累加留言數 */
					if (map.get(storeType) == null) {
						map.put(storeType, 1);
					} else {
						int tmp = (int) map.get(storeType);
						map.put(storeType, tmp + 1);
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 查詢個人花費金額，按年齡分組(15歲一個區間，從0開始逐個區間+1，第一個為0~15)，僅計算已付款的 */
	@PostMapping(value = "/controller/avgCostByAge", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getAvgCostByAge(Model model) {
		Map<String, Object> map = new HashMap<>();
		/* 年紀Map */
		Map<String, Object> ageMap = new HashMap<>();
		/* 使用者Map */
		Map<String, Object> userMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有購物車訂單資料 */
			List<CartItemBean> cartList = cts.getCartList();
			/* 遍歷 */
			for (CartItemBean cartData: cartList) {
				/* 僅計算已付款的 */
				if (cartData.getPurchase_Payment()) {
					/* 取出使用者 */
					WebUserData user = cartData.getProduct_User();
					/* 取出購買產品的總價 */
					Integer price = cartData.getProduct_Info().getProduct_price() * Integer.parseInt(cartData.getProduct_Quantity());
					/* 算出年齡 */
					Integer age = caculateAge(user.getBirth());
					/* 進行區間分類 */
					Integer ageRange = age / 15;
					/* 放入ageMap */
					if (ageMap.get(ageRange.toString()) == null) {
						ageMap.put(ageRange.toString(), price);
					} else {
						long tmpL = (long) ageMap.get(ageRange.toString());
						ageMap.put(ageRange.toString(), tmpL + price);
					}
					/* 放入userMap */
					if (userMap.get(ageRange.toString()) == null) {
						userMap.put(ageRange.toString(), 1);
					} else {
						int tmp = (int) userMap.get(ageRange.toString());
						userMap.put(ageRange.toString(), tmp + 1);
					}
					/* 計算平均 */
					String realRange = String.valueOf(ageRange*15 + 0) + "~" + String.valueOf(ageRange*15 + 14);
					if (map.get(realRange) == null) {
						map.put(realRange, price);
					} else {
						long avgCost = (long) map.get(realRange) / (int) userMap.get(ageRange.toString());
						map.put(realRange, avgCost);
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 查詢平均每筆花費金額，按年+月分組(預設為2020)，僅計算已付款的 */
	@PostMapping(value = "/controller/avgCostByMonth", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getAvgCostByMonth(Model model,
			@RequestParam(value = "year", defaultValue = "2020") String year) {
		Map<String, Object> map = new HashMap<>();
		/* 統計Map */
		Map<String, Object> countMap = new HashMap<>();
		/* 統計Map */
		Map<String, Object> totalMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有購物車訂單資料 */
			List<CartItemBean> cartList = cts.getCartList();
			/* 遍歷 */
			for (CartItemBean cartData: cartList) {
				/* 該年度且僅計算已付款的 */
				if (cartData.getPurchase_Payment() && String.valueOf(cartData.getPurchase_Time().toLocalDate().getDayOfYear()).equals(year)) {
					/* 取出購買月份 */
					Integer pMonth = cartData.getPurchase_Time().toLocalDate().getDayOfMonth();
					/* 取出購買產品的總價 */
					Integer price = cartData.getProduct_Info().getProduct_price() * Integer.parseInt(cartData.getProduct_Quantity());
					/* 開始統計 */
					if (countMap.get(pMonth.toString()) == null) {
						countMap.put(pMonth.toString(), 1);
					} else {
						int tmp = (int) countMap.get(pMonth.toString());
						countMap.put(pMonth.toString(), tmp + 1);
					}
					
					if (totalMap.get(pMonth.toString()) == null) {
						totalMap.put(pMonth.toString(), price);
					} else {
						long tmpL = (long) totalMap.get(pMonth.toString());
						totalMap.put(pMonth.toString(), tmpL + price);
					}
					
					if (map.get(pMonth.toString()) == null) {
						map.put(pMonth.toString(), price);
					} else {
						int countTmp = (int) countMap.get(pMonth.toString());
						long totalTmp = (long) totalMap.get(pMonth.toString());
						map.put(pMonth.toString(), totalTmp / countTmp);
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 查詢已付款的購物車清單中，按餐廳分類分組顯示比例，僅計算已付款的 */
	@PostMapping(value = "/controller/buyCountsByType", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBuyCountsByType(Model model) {
		Map<String, Object> map = new HashMap<>();
		/* 各類筆數 */
		Map<String, Object> countMap = new HashMap<>();
		/* 總筆數 */
		Map<String, Object> totalMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有購物車訂單資料 */
			List<CartItemBean> cartList = cts.getCartList();
			/* 遍歷 */
			for (CartItemBean cartData: cartList) {
				/* 僅計算已付款的 */
				if (cartData.getPurchase_Payment()) {
					/* 取店家類型 */
					String storeType = cartData.getProduct_Info().getStorebean().getSclass();
					if (totalMap.get("total") == null) {
						totalMap.put("total", 1);
					} else {
						long tmp = (long) totalMap.get("total");
						totalMap.put("total", tmp + 1);
					}
					if (countMap.get(storeType) == null) {
						countMap.put(storeType, 1);
						long totalTmp = (long) totalMap.get("total");
						map.put(storeType, (float)(1 / totalTmp));
					} else {
						long tmpL = (long) countMap.get(storeType);
						countMap.put(storeType, tmpL + 1);
						long totalTmp = (long) totalMap.get("total");
						map.put(storeType, (float)((long) countMap.get(storeType) / totalTmp));
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 取得留言列表 */
	@PostMapping(value = "/controller/getCommentList", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getCommnetList(
			Model model
			) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 留言列表 */
		List<BoardBean> boardList = new ArrayList<>();
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("") || checkBossIdentity(model).equals("")) {
			/* 確認使用者身分 */
			WebUserData nowUser = (WebUserData) model.getAttribute("userFullData");
			/* 管理員 */
			if (nowUser.getAccountLv().getLv() == -1) {
				boardList = bds.getAllcomment();
				map.put("boardList", boardList);
			/* 店家 */
			} else if (nowUser.getAccountLv().getLv() == 1) {
				List<BoardBean> personalBoardList = new ArrayList<>();
				boardList = bds.getAllcomment();
				/* 遍歷 */
				for (BoardBean boardData: boardList) {
					/* 僅放入自己所擁有的商店 */
					if (boardData.getStorebean().getWebUserData().getUserId().equals(nowUser.getUserId())) {
						personalBoardList.add(boardData);
					}
				}
				map.put("boardList", personalBoardList);
				message = (personalBoardList.size() == 0) ? "您尚未在本平台擁有任何一個商店！" : message;
			} 
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 取得店家列表 */
	@PostMapping(value = "/controller/getStoreList", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getStoreList(
			Model model,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "type", required = false) String type) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 回傳的商家資料 */
		List<StoreBean> storeList = new ArrayList<>();
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("") || checkBossIdentity(model).equals("")) {
			/* 確認使用者身分 */
			WebUserData nowUser = (WebUserData) model.getAttribute("userFullData");
			/* 管理員 */
			if (nowUser.getAccountLv().getLv() == -1) {
				storeList = ss.getAllStore();
			/* 店家 */
			} else if (nowUser.getAccountLv().getLv() == 1) {
				storeList = ss.getMemberAllStore(nowUser);
			}
			/* 有資料才做以下操作 */
			if (storeList != null) {
				switch(status) {
					case "1":
					case "0":
						/* 遍歷 */
						for (int index = 0; index < storeList.size(); index++) {
							if (!storeList.get(index).getStatus().equals(status)) {
								storeList.remove(index);
							}
						}
						break;
					default:
						break;
				}
				switch(type) {
					case "中式":
					case "快餐":
					case "燒肉":
					case "西式":
					case "下午茶":
					case "日式":
						/* 遍歷 */
						for (int index = 0; index < storeList.size(); index++) {
							if (!storeList.get(index).getSclass().equals(type)) {
								storeList.remove(index);
							}
						}
						break;
					default:
						break;
				}
			}
			map.put("storeList", storeList);
		} 
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 刪除店家 */
	@PostMapping(value = "/controller/adminDeleteStore", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> adminDeleteStore(
			Model model,
			@RequestParam("storeId") Integer storeId) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證成功 */
		if (message.equals("")) {
			
		}
		message = (message.equals("")) ? "成功" : message;
		map.put("message", message);
		return map;
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
		} else if (account.length() < 6 || account.length() > 30) {
			submitMessage = "帳號長度不符格式，僅接受6~30個字元";
			inputIsOk = false;
		} else if (account.matches("[1-9]{1}.")) {
			submitMessage = "帳號不可以數字開頭";
			inputIsOk = false;
		} else if (account.indexOf("&") != -1) {
			submitMessage = "帳號不可以包含&符號";
			inputIsOk = false;
		} else if (account.indexOf("=") != -1) {
			submitMessage = "帳號不可以包含等號";
			inputIsOk = false;
		} else if (account.indexOf("_") != -1) {
			submitMessage = "帳號不可以包含底線";
			inputIsOk = false;
		} else if (account.indexOf("-") != -1) {
			submitMessage = "帳號不可以包含破折號";
			inputIsOk = false;
		} else if (account.indexOf("+") != -1) {
			submitMessage = "帳號不可以包含加號";
			inputIsOk = false;
		} else if (account.indexOf(",") != -1 || account.indexOf("，") != -1) {
			submitMessage = "帳號不可以包含逗號";
			inputIsOk = false;
		} else if (account.indexOf(".") != -1 || account.indexOf("。") != -1) {
			submitMessage = "帳號不可以包含句號";
			inputIsOk = false;
		} else if (account.indexOf("?") != -1 || account.indexOf("？") != -1) {
			submitMessage = "帳號不可以包含問號";
			inputIsOk = false;
		} else if (account.indexOf("<") != -1 || account.indexOf(">") != -1) {
			submitMessage = "帳號不可以包含<、>";
			inputIsOk = false;
		} else if (!account.matches("[a-zA-Z]{1}[0-9a-zA-Z]{5,29}")) {
			submitMessage = "帳號不符合格式";
			inputIsOk = false;
		} else if (account.matches("[a-zA-Z]{1}[0-9a-zA-Z]{5,29}")) {
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
		} else {
			submitMessage = "無效的帳號名稱";
			inputIsOk = false;
		}
		
		return submitMessage + "," + inputIsOk.toString();
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
		} else if (nickname.length() > 25){
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
		
		/* 第三方登入者 */
		if (model.getAttribute("extraAccount") != null && model.getAttribute("id_token") != null) {
			inputIsOk = true;
		/* 一般註冊者 */
		} else {
			/* 檢查帳號 */
			if (inputIsOk) {
				String resultTmp = doCheckAccount(account, "submit");
				submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
				inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
			}
		}
		
		/* 第三方登入者 */
		if (model.getAttribute("extraAccount") != null && model.getAttribute("id_token") != null) {
			inputIsOk = true;
		/* 一般註冊者 */
		} else {
			/* 檢查密碼 */
			if (inputIsOk) {
				String resultTmp = GlobalService.doCheckPassword(password);
				submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
				inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
			}
		}
		
		/* 檢查中文姓氏 */
		if (inputIsOk) {
			String resultTmp = GlobalService.doCheckFirstName(firstName);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查中文名字 */
		if (inputIsOk) {
			String resultTmp = GlobalService.doCheckLastName(lastName);
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
			String resultTmp = GlobalService.doCheckGender(genderCode);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 西元生日 */
		if (inputIsOk) {
			String resultTmp = GlobalService.doCheckBirth(birth);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查偏好食物 */
		if (inputIsOk) {
			String resultTmp = GlobalService.doCheckFervor(fervor);
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
			String resultTmp = GlobalService.doCheckGetEmail(willingCode);
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
			String resultTmp = GlobalService.doCheckCityCode(cityCode, "register");
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查生活地點一 */
		if (inputIsOk) {
			String resultTmp = GlobalService.doCheckAddr0(addr0, addr1, addr2);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查生活地點二 */
		if (inputIsOk) {
			String resultTmp = GlobalService.doCheckAddr1(addr0, addr1, addr2);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		/* 檢查生活地點三 */
		if (inputIsOk) {
			String resultTmp = GlobalService.doCheckAddr2(addr0, addr1, addr2);
			submitMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			inputIsOk = Boolean.valueOf(resultTmp.split(",")[1]);
		}
		
		return submitMessage;
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
		
		/* 確認是否為第三方登入 */
		String extraAccount = (String) model.getAttribute("extraAccount");
		String id_token = (String) model.getAttribute("id_token");
		WebUserData reg_webUser;
		
		if (extraAccount == null && id_token == null) {
			/* 建立物件 */
			reg_webUser = new WebUserData(
					"", 
					account.trim(), 
					password.trim(), 
					firstName, 
					lastName, 
					nickname.trim(), 
					birth, 
					"",
					email.trim(), 
					phone, 
					Date.valueOf(GlobalService.getToday()), 
					addr0.trim(), 
					addr1.trim(), 
					addr2.trim(), 
					BigDecimal.ZERO, 
					0, 
					"inactive", 
					"",
					null,
					new UserIdentity(), 
					new Gender(), 
					new UserWilling(), 
					new CityInfo());
		} else if (extraAccount != null && id_token != null) {
			/* 建立物件 */
			reg_webUser = new WebUserData(
					"", 
					extraAccount, 
					null, 
					firstName, 
					lastName, 
					nickname.trim(), 
					birth, 
					"",
					email.trim(), 
					phone, 
					Date.valueOf(GlobalService.getToday()), 
					addr0.trim(), 
					addr1.trim(), 
					addr2.trim(), 
					BigDecimal.ZERO, 
					0, 
					"inactive", 
					"",
					null,
					new UserIdentity(), 
					new Gender(), 
					new UserWilling(), 
					new CityInfo());
		} else {
			/* 建立物件 */
			reg_webUser = new WebUserData();
			submitMessage = "驗證失敗";
		}
		
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
		if (submitMessage.equals("")) {
			submitMessage = (submitMessage.equals("")) ? doCheckRegisterInput(
					reg_webUser, 
					model) 
					: submitMessage;
		}
		
		map.put("reg_webUser", reg_webUser);
		map.put("submitMessage", submitMessage);
		return map;
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
			String resultTmp = GlobalService.doCheckFirstName(newFirstName);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (updateResultMessage.equals("") && newFirstName.equals(oldFirstName)) {
				count++;
			}
		}
		
		/* 檢查中文名字 */
		if (updateResultMessage.equals("")) {
			String resultTmp = GlobalService.doCheckLastName(newLastName);
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
			String resultTmp = GlobalService.doCheckFervor(fervor);
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
			String resultTmp = GlobalService.doCheckGetEmail(getEmail);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (getEmail.equals(oldGetEmail) && updateResultMessage.equals("")) {
				count++;
			} 
		}
		
		/* 檢查區住區域 */
		if (updateResultMessage.equals("")) {
			String resultTmp = GlobalService.doCheckCityCode(locationCode, "update");
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (locationCode == oldLocationCode && updateResultMessage.equals("")) {
				count++;
			}
		}
		
		/* 檢查生活地點一 */
		if (updateResultMessage.equals("")) {
			String resultTmp = GlobalService.doCheckAddr0(newAddr0, newAddr1, newAddr2);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (newAddr0.equals(oldAddr0) && updateResultMessage.equals("")) {
				count++;
			}
		}
		
		/* 檢查生活地點二 */
		if (updateResultMessage.equals("")) {
			String resultTmp = GlobalService.doCheckAddr1(newAddr0, newAddr1, newAddr2);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (newAddr1.equals(oldAddr1) && updateResultMessage.equals("")) {
				count++;
			}
		}
		
		/* 檢查生活地點三 */
		if (updateResultMessage.equals("")) {
			String resultTmp = GlobalService.doCheckAddr2(newAddr0, newAddr1, newAddr2);
			updateResultMessage = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
			if (newAddr2.equals(oldAddr2) && updateResultMessage.equals("")) {
				count++;
			}
		}
		
		updateResultMessage = (updateResultMessage.equals("")) ? "?" : updateResultMessage;
		/* 結算有效變動項目 */
		return (count == 11) ? "11,沒有輸入任何有效的修改內容，請重新操作" : count.toString() + "," + updateResultMessage;
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
	
	/* 取出購物車年份(下拉選單用) */
	private List<String> getCartYearList() {
		/* 取出所有購物車訂單資料 */
		List<String> cartYearList = new ArrayList<>();
		List<CartItemBean> cartList = cts.getCartList();
		if (cartList != null) {
			for (CartItemBean cartData: cartList) {
				cartYearList.add(String.valueOf(cartData.getPurchase_Time().toLocalDate().getDayOfYear()));
			}
			return cartYearList;
		}
		return null;
	}
	
	/* 取出使用者加入年份(下拉選單用) */
	private List<String> getUserYearList() {
		/* 取出所有使用者資料 */
		List<String> userYearList = new ArrayList<>(); 
		List<WebUserData> userList = new ArrayList<>();
		
		try {
			userList = wus.getAllWebUserData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (userList != null) {
			for (WebUserData userData: userList) {
				userYearList.add(String.valueOf(userData.getBirth().toLocalDate().getDayOfYear()));
			}
			return userYearList;
		}
		return null;
	}
	
	/* 驗證管理員身分 */
	private String checkAdminIdentity(Model model) {
		String message = "";
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		if (userData == null) {
			message = "請登入後再執行此動作！";
		} else if (!userData.getStatus().equals("active")) {
			message = "本帳號已被停用，無法使用此功能！";
		} else if (userData.getAccountLv().getLv() != -1) {
			message = "本帳號無法使用此功能！";
		}
		return message;
	}
	
	/* 驗證店家身分 */
	private String checkBossIdentity(Model model) {
		String message = "";
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		if (userData == null) {
			message = "請登入後再執行此動作！";
		} else if (!userData.getStatus().equals("active")) {
			message = "本帳號已被停用，無法使用此功能！";
		} else if (userData.getAccountLv().getLv() != 1) {
			message = "本帳號無法使用此功能！";
		}
		return message;
	}
	
	/* 計算年齡 */
	private Integer caculateAge(Date birth) {
		Integer age = -1;
		LocalDate userBirth = birth.toLocalDate();
		LocalDate today = LocalDate.now();
		Period p = Period.between(userBirth, today);
		age = p.getYears();
		return age;
	}
}
