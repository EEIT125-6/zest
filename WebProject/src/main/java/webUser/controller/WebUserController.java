package webUser.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

@SessionAttributes(
		{"registerEmail", 
		"checkCode", 
		"willingList", 
		"identityList", 
		"fervorList", 
		"genderList", 
		"cityInfoList",
		"reg_webUser",
		"userFullData"})
@Controller
@RequestMapping("/webUser")
public class WebUserController {
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

	/* ServletContext */
	@Autowired
	ServletContext context;

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
			RedirectAttributes redirectAttributes) 
	{
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
			case -1:
				reg_webUser.setStatus("inactive");
				break;
			case 0:
				reg_webUser.setStatus("active");
				break;
			case 1:
				reg_webUser.setStatus("inactive");
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
		submitMessage = doCheckRegisterInput(
				reg_webUser, 
				model);
		
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
				Model model
			) 
	{
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
				&& (reg_webUser.getAccountLv().getLv() == -1 
				|| reg_webUser.getAccountLv().getLv() == 1)) {
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
			SessionStatus sessionStatus
			) 
	{
		/* 清空SessionAttribute */
		sessionStatus.setComplete();
		/* 返回註冊畫面 */
		return "redirect:/webUser/WebUserRegisterForm";
	}
	
	/* 執行登入檢查 */
	@PostMapping(value = "/controller/WebUserLogin", produces="application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doLoginCheck(
			Model model,
			@RequestParam(value = "account", defaultValue="") String account,
			@RequestParam(value = "password", defaultValue="") String password
			) 
	{
		/* 宣告欲回傳的參數 */
		Map<String, String> map = new HashMap<>();
		Integer inputCheckResult = -1;
		Integer accountCheckResult = -3;
		String loginMessage = "";
		WebUserData userFullData = new WebUserData();
		
		/* 預防性後端檢查，正常時回傳1 */
		inputCheckResult = doCheckLoginInput(account, password);
		
		if (inputCheckResult == 1) {
			/* 調用服務裡的方法 */
			try {
				/* 檢查登入 */
				accountCheckResult = wus.checkWebUserLogin(account, password);
				/* 存取使用者個人資料 */
				userFullData = wus.getWebUserData(account);
			} catch (SQLException sqlE) {
				String loginMessageTmp = sqlE.getMessage();
				loginMessage = loginMessageTmp.split(":")[1];
			}
		} else {
			switch(inputCheckResult) {
				case 0:
					loginMessage = "帳號不可為空白";
					break;
				case -1:
					loginMessage = "帳號長度不足";
					break;
				case -2:
					loginMessage = "帳號長度過長";
					break;
				case -3:
					loginMessage = "帳號不可以數字開頭";
					break;
				case -4:
					loginMessage = "帳號不符合格式";
					break;
				case 2:
					loginMessage = "密碼不可為空白";
					break;
				case 3:
					loginMessage = "密碼長度不足，至少需8個字元";
					break;
				case 4:
					loginMessage = "密碼長度過長，最多僅20個字元";
					break;
				case 5:
					loginMessage = "密碼不可以數字開頭";
					break;
				case 6:
					loginMessage = "密碼不符合格式";
					break;
			}
		}
		
		if (accountCheckResult == 1) {
			loginMessage = "歡迎 " + userFullData.getNickname() + " ！";
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
			SessionStatus sessionStatus
			) 
	{
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		String account = userData.getAccount();
		String logoutMessage = "謝謝您的使用，" + account + "!";
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
			SessionStatus sessionStatus
			)
	{
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
				deleteResult = wus.quitWebUserData(quitUserData);
			} catch (SQLException sqlE) {
				String quitMessageTmp = sqlE.getMessage();
				quitMessage = quitMessageTmp.split(":")[1];
			}
		}
		
		/* 成功變更 */
		if (deleteResult == 1) {
			quitMessage = "感謝您的使用，" + quitUserData.getAccount() + "！我們有緣再見...";
			/* 清空SessionAttribute */
			sessionStatus.setComplete();
			redirectPage = "/webUser/WebUserRegisterForm";
		} 
		/* 將物件quitMessage以"quitMessage"的名稱放入flashAttribute中 */
		redirectAttributes.addFlashAttribute("quitMessage", quitMessage);
		/* 將物件redirectPag以"redirectPag"的名稱放入flashAttribute中 */
		redirectAttributes.addFlashAttribute("redirectPag", redirectPage);
		/* 導向棄用結束畫面 */
		return "redirect:/webUser/WebUserQuitResult";
	}
	
	/* 以Ajax取回使用者個人資料 */
	@PostMapping(value = "/controller/DisplaySelfData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> doDisplaySelfData(
			Model model
			) 
	{
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
			@RequestParam(value = "confirmPassword") String confirmPassword 
			) 
	{	
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
			Model model) 
	{			
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
			@RequestParam(value = "newAddr2", required = false, defaultValue="") String newAddr2
			) 
	{
		/* 宣告參數 */
		Map<String, String> map = new HashMap<>();
		String updateResultMessage = "";
		Integer updateResult = -1;
		
		/* 取出sessionAttribute裡的使用者資料物件 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		
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
					oldAddr2
					);
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
		} else if (updateResult != 1 && updateResultMessage.equals("")) {
			updateResultMessage = "修改失敗";
		}
		
		map.put("resultCode", updateResult.toString());
		map.put("resultMessage", updateResultMessage);
		
		return map;
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
	
	/* 前往棄用結束畫面 */
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
	
	/* 使用者註冊資料檢查 */
	public String doCheckRegisterInput(
			WebUserData reg_webUser, 
			Model model) 
	{
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
				inputIsOk = true;
				break;
			default:
				inputIsOk = false;
				break;
		}
		submitMessage = (inputIsOk) ? "" : "帳號身分錯誤";

		/* 檢查帳號 */
		if (inputIsOk) {
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

				if (accountCheckResult == 0) {
					inputIsOk = true;
				} else if (accountCheckResult == 1) {
					submitMessage = "帳號已存在，請挑選別的名稱作為帳號";
					inputIsOk = false;
				}
			}
		}
		
		/* 檢查密碼 */
		if (inputIsOk) {
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
			} else {
				inputIsOk = true;
			}
		}
		
		/* 中文姓氏 */
		if (inputIsOk) {
			if (firstName.equals("")) {
				submitMessage = "姓氏不可為空白";
				inputIsOk = false;
			} else if (firstName.length() > 3) {
				submitMessage = "姓氏長度過長，最多僅3個字元";
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
					submitMessage = "姓氏中含有非中文";
					inputIsOk = false;
				} else {
					inputIsOk = true;
				}
			}
		}
		
		/* 中文名字 */
		if (inputIsOk) {
			if (lastName.equals("")) {
				submitMessage = "名字不可為空白";
				inputIsOk = false;
			} else if (lastName.length() > 3) {
				submitMessage = "名字長度過長，最多僅3個字元";
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
					submitMessage = "名字中含有非中文";
					inputIsOk = false;
				} else {
					inputIsOk = true;
				}
			}
		}
		
		/* 稱呼 */
		if (inputIsOk) {
			if (nickname.equals("") && lastName.equals("")) {
				submitMessage = "稱呼不可為空白";
				inputIsOk = false;
			} else if (nickname.equals("") && !lastName.equals("")) {
				nickname = lastName;
				inputIsOk = true;
			} else if (nickname.length() > 20){
				submitMessage = "稱呼長度過長";
				inputIsOk = false;
			} else {
				Integer nicknameCheckResult = -1;
				
				/* 調用服務裡的方法 */
				try {
					nicknameCheckResult = wus.checkNicknameExist(nickname);
				} catch (SQLException sqlE) {
					submitMessage = sqlE.getMessage();
					inputIsOk = false;
				}
				
				if (nicknameCheckResult == 0) {
					inputIsOk = true;
				} else if (nicknameCheckResult == 1){
					submitMessage = "稱呼已存在，請挑選別的名稱作為稱呼";
					inputIsOk = false;
				}
			}
		}
		
		/* 生理性別 */
		if (inputIsOk) {
			switch(genderCode) {
				case "M":
				case "W":
				case "N":
					inputIsOk = true;
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
		
		/* 偏好食物 */
		if (inputIsOk) {
			if (fervor.equals("")) {
				submitMessage = "偏好食物不可為空白";
				inputIsOk = false;
			} else if (fervor.length() > 50){
				submitMessage = "偏好食物長度過長";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		/* email */
		if (inputIsOk) {
			if (email.equals("")) {
				submitMessage = "信箱資訊不可為空白";
				inputIsOk = false;
			} else if(email.indexOf("@") == -1 || email.split("@").length > 2 || email.indexOf(" ") != -1) {
				submitMessage = "信箱資訊格式錯誤";
				inputIsOk = false;
			} else {
				Integer emailCheckResult = -1;
				
				/* 調用服務裡的方法 */
				try {
					emailCheckResult = wus.checkEmailExist(email);
				} catch (SQLException sqlE) {
					submitMessage = sqlE.getMessage();
					inputIsOk = false;
				}
				
				if (emailCheckResult == 0) {
					inputIsOk = true;
				} else if (emailCheckResult == 1){
					submitMessage = "該聯絡信箱已被註冊，請挑選別的聯絡信箱";
					inputIsOk = false;
				}
			}
		}
		
		/* getEmail */
		if (inputIsOk) {
			switch(willingCode) {
				case "Y":
				case "N":
					inputIsOk = true;
					break;
				default:
					submitMessage = "接收促銷/優惠訊息設定異常";
					inputIsOk = false;
					break;
			}
		}
		
		/* phone */
		if (inputIsOk) {
			if (phone.equals("")) {
				submitMessage = "連絡電話不可為空白";
				inputIsOk = false;
			} else if(phone.length() < 9 || phone.indexOf(" ") != -1) {
				submitMessage = "連絡電話格式錯誤";
				inputIsOk = false;
			} else if (!phone.matches("[0]{1}[2-9]{1}[0-9]{7,9}")) {
				submitMessage = "連絡電話格式錯誤";
				inputIsOk = false;
			} else if (phone.substring(0, 2).equals("09") && phone.length() != 10) {
				submitMessage = "行動電話格式錯誤";
				inputIsOk = false;
			} else if (!phone.substring(0, 2).equals("09") && phone.length() == 10) {
				submitMessage = "室內電話格式錯誤";
				inputIsOk = false;
			} else {
				Integer phoneCheckResult = -1;
				
				/* 調用服務裡的方法 */
				try {
					phoneCheckResult = wus.checkPhoneExist(phone);
				} catch (SQLException sqlE) {
					submitMessage = sqlE.getMessage();
					inputIsOk = false;
				}
				
				if (phoneCheckResult == 0) {
					inputIsOk = true;
				} else if (phoneCheckResult == 1){
					submitMessage = "該聯絡電話已被註冊，請輸入別的聯絡電話";
					inputIsOk = false;
				}
			}
		}
		
		/* 居住區域 */
		if (inputIsOk) {
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
					inputIsOk = true;
					break;
				default:
					submitMessage = "居住區域設定異常";
					inputIsOk = false;
					break;
			}
		}
		
		/* 生活地點一 */
		if (inputIsOk) {
			if (addr0.equals("")) {
				submitMessage = "生活地點一不可為空白";
				inputIsOk = false;
			} else if (addr0.length() > 65) {
				submitMessage = "生活地點一超過長度限制";
				inputIsOk = false;
			} else if (addr0.equals(addr1) || addr0.equals(addr2)) {
				submitMessage = "生活地點重複填寫";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		/* 生活地點二 */
		if (inputIsOk) {
			if (addr1.length() > 65) {
				submitMessage = "生活地點二超過長度限制";
				inputIsOk = false;
			} else if (addr1.equals(addr0) || (addr1.equals(addr2) && !addr2.equals(""))) {
				submitMessage = "生活地點重複填寫";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		/* 生活地點三 */
		if (inputIsOk) {
			if (addr2.length() > 65) {
				submitMessage = "生活地點三超過長度限制";
				inputIsOk = false;
			} else if (addr2.equals(addr0) || (addr2.equals(addr1) && !addr1.equals(""))) {
				submitMessage = "生活地點重複填寫";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		return submitMessage;
	}
	
	/* 使用者登入資料檢查 */
	public Integer doCheckLoginInput(String account, String password) {
		/* 傳回參數宣告 */
		Integer inputCheckResult = 1;
		
		/* 使用者帳號 */
		if (account.equals("")) {
			inputCheckResult = 0;
		} else if(account.length() < 8 || account.length() > 20) {
			inputCheckResult = -1;
		} else if (account.matches("[1-9]{1}.")) {
			inputCheckResult = -2;
		} else if (!account.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
			inputCheckResult = -3;
		} 
		
		/* 使用者密碼 */
		if (password.equals("")) {
			inputCheckResult = 2;
		} else if (password.length() < 8) {
			inputCheckResult = 3;
		} else if (password.length() > 20) {
			inputCheckResult = 4;
		} else if (password.matches("[1-9]{1}.")) {
			inputCheckResult = 5;
		} else if (!password.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
			inputCheckResult = 6;
		} 
		
		return inputCheckResult;
	}
	
	/* 使用者棄用帳戶時的輸入檢查 */
	public String doCheckQuitInput(WebUserData userData) {
		String checkInputResult = "";
		
		/* 檢查JavaBean物件 */
		if (userData == null) {
			checkInputResult = "您似乎沒有登入本系統！請登入後再試一次";
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
			} else if (checkResult2 == 1) {
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
		} else if (userData.getStatus().equals("quit") || userData.getStatus().equals("inactive")) {
			updateResultMessage = "本帳號無法使用此功能";
		} else {
			/* 檢查密碼 */
			String oldPassword = userData.getPassword();
			if (!password.equals(confirmPassword)) {
				updateResultMessage = "密碼與確認密碼不一致！";
			} else if (password.equals(oldPassword)){
				updateResultMessage = "密碼未修改！";
			} else if (password.equals("")) {
				updateResultMessage = "密碼不可為空白";
			} else if (password.length() < 8 || password.length() > 20) {
				updateResultMessage = "密碼長度不符格式，僅接受8~20個字元";
			} else if (password.matches("[1-9]{1}.")) {
				updateResultMessage = "密碼不可以數字開頭";
			} else if (!password.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
				updateResultMessage = "密碼不符合格式";
			} else {
				String userId = userData.getUserId();
				Integer checkIdResult = -1;
				
				try {
					checkIdResult = wus.checkUserIdExist(userId);
				} catch(SQLException sqlE) {
					String quitMessageTmp = sqlE.getMessage();
					updateResultMessage = quitMessageTmp.split(":")[1];
				}
				
				if (checkIdResult != 1) {
					updateResultMessage = "使用者身分異常!請確定您已經登入本系統";
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
			String oldAddr2
			) 
	{
		String updateResultMessage = "";
		Integer count = 0;
		
		/* 檢查JavaBean物件 */
		if (updatedUserData == null) {
			updateResultMessage = "未登入系統，請登入後再進行操作！";
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
		
		/* 檢查姓氏 */
		if (updateResultMessage.equals("")) {
			if (newFirstName.equals("")) {
				updateResultMessage = "姓氏不可為空白";
			} else if (newFirstName.length() > 3) {
				updateResultMessage = "姓氏長度過長，最多僅3個字元";
			} else {
				Integer charCountBegin = 0;
				Boolean firstNameIsOk = true;
				/* 16進位表示 */
				Integer charChineseWordCountBegin = 0x4e00;
				Integer charChineseWordCountEnd = 0x9fff;
				
				for (Integer charIndex = charCountBegin; charIndex < newFirstName.length(); charIndex++) {
					int firstNameChar = newFirstName.charAt(charIndex);
					
					if (firstNameChar < charChineseWordCountBegin || firstNameChar > charChineseWordCountEnd) {
						firstNameIsOk = false;
					}
					if (!firstNameIsOk) {
						break;
					}
				}
				
				if (!firstNameIsOk) {
					updateResultMessage = "姓氏中含有非中文";
				} else if (newFirstName.equals(oldFirstName)){
					count++;
				}
			}
		}
		
		/* 檢查名字 */
		if (updateResultMessage.equals("")) {
			if (newLastName.equals("")) {
				updateResultMessage = "名字不可為空白";
			} else if (newLastName.length() > 3) {
				updateResultMessage = "名字長度過長，最多僅3個字元";
			} else {
				Integer charCountBegin = 0;
				Boolean lastNameIsOk = true;
				/* 16進位表示 */
				Integer charChineseWordCountBegin = 0x4e00;
				Integer charChineseWordCountEnd = 0x9fff;
				
				for (Integer charIndex = charCountBegin; charIndex < newLastName.length(); charIndex++) {
					int lastNameChar = newLastName.charAt(charIndex);
					
					if (lastNameChar < charChineseWordCountBegin || lastNameChar > charChineseWordCountEnd) {
						lastNameIsOk = false;
					}
					if (!lastNameIsOk) {
						break;
					}
				}
				
				if (!lastNameIsOk) {
					updateResultMessage = "名字中含有非中文";
				} else if (newLastName.equals(oldLastName)){
					count++;
				}
			}
		}
		
		/* 檢查稱呼 */
		if (updateResultMessage.equals("")) {
			if (newNickname.equals("") && newLastName.equals("")) {
				updateResultMessage = "稱呼不可為空白";
			} else if (newNickname.equals("") && !newLastName.equals("")) {
				newNickname = newLastName;
			} else if (newNickname.length() > 20){
				updateResultMessage = "稱呼長度過長";
			} else if (newNickname.equals(oldNickname)){
				count++;
			} else {
				Integer nicknameCheckResult = -1;
				
				/* 調用服務裡的方法 */
				try {
					nicknameCheckResult = wus.checkNicknameExist(newNickname);
				} catch (SQLException sqlE) {
					updateResultMessage = sqlE.getMessage();
				}
				
				if (nicknameCheckResult == 1){
					updateResultMessage = "稱呼已存在，請挑選別的名稱作為稱呼";
				}
			}
		}
		
		/* 檢查偏好食物 */
		if (updateResultMessage.equals("")) {
			if (fervor.equals("")) {
				updateResultMessage = "偏好食物不可為空白";
			} else if (fervor.length() > 50){
				updateResultMessage = "偏好食物長度過長";
			} else if (fervor.equals(oldFervor)) {
				count++;
			}
		}
		
		System.out.println("newEmail is "+newEmail);
		/* 檢查email */
		if (updateResultMessage.equals("")) {
			if (newEmail.equals("")) {
				updateResultMessage = "信箱資訊不可為空白";
			} else if(newEmail.indexOf("@") == -1 || newEmail.split("@").length > 2 || newEmail.indexOf(" ") != -1) {
				updateResultMessage = "信箱資訊格式錯誤";
			} else if (newEmail.equals(oldEmail)) {
				count++;
			} else {
				Integer emailCheckResult = -1;
				
				/* 調用服務裡的方法 */
				try {
					emailCheckResult = wus.checkEmailExist(newEmail);
				} catch (SQLException sqlE) {
					updateResultMessage = sqlE.getMessage();
				}
				
				if (emailCheckResult == 1){
					updateResultMessage = "該聯絡信箱已被註冊，請挑選別的聯絡信箱";
				} 
			}
		}
		
		/* 檢查聯絡電話 */
		if (updateResultMessage.equals("")) {
			if (newPhone.equals("")) {
				updateResultMessage = "連絡電話不可為空白";
			} else if(newPhone.length() < 9 || newPhone.indexOf(" ") != -1) {
				updateResultMessage = "連絡電話格式錯誤";
			} else if (!newPhone.matches("[0]{1}[2-9]{1}[0-9]{7,9}")) {
				updateResultMessage = "連絡電話格式錯誤";
			} else if (newPhone.substring(0, 2).equals("09") && newPhone.length() != 10) {
				updateResultMessage = "行動電話格式錯誤";
			} else if (!newPhone.substring(0, 2).equals("09") && newPhone.length() == 10) {
				updateResultMessage = "室內電話格式錯誤";
			} else if (newPhone.equals(oldPhone)) {
				count++;
			} else {
				Integer phoneCheckResult = -1;
				
				/* 調用服務裡的方法 */
				try {
					phoneCheckResult = wus.checkPhoneExist(newPhone);
				} catch (SQLException sqlE) {
					updateResultMessage = sqlE.getMessage();
				}
				
				if (phoneCheckResult == 1){
					updateResultMessage = "該聯絡電話已被註冊，請輸入別的聯絡電話";
				}
			}
		}
		
		/* 檢查接收促銷/優惠訊息 */
		if (updateResultMessage.equals("")) {
			if (getEmail.equals(oldGetEmail)) {
				count++;
			} else if (!getEmail.equals("Y") && !getEmail.equals("N")){ 
				updateResultMessage = "接收促銷/優惠訊息輸入異常";
			}
		}
		
		/* 檢查區住區域 */
		if (updateResultMessage.equals("")) {
			if (locationCode == oldLocationCode) {
				count++;
			} else {	
				switch(locationCode) {
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
						updateResultMessage = "居住區域設定異常";
						break;
				}
			}
		}
		
		/* 檢查地點一 */
		if (updateResultMessage.equals("")) {
			if (newAddr0.equals("")) {
				updateResultMessage = "生活地點一不可為空白";
			} else if (newAddr0.length() > 65) {
				updateResultMessage = "生活地點一超過長度限制";
			} else if (newAddr0.equals(newAddr1) || newAddr0.equals(newAddr2)) {
				updateResultMessage = "生活地點重複填寫";
			} else if (newAddr0.equals(oldAddr0)) {
				count++;
			}
		}
		
		/* 檢查地點二 */
		if (updateResultMessage.equals("")) {
			if (newAddr1.length() > 65) {
				updateResultMessage = "生活地點二超過長度限制";
			} else if (newAddr1.equals(newAddr0) || (newAddr1.equals(newAddr2) && !newAddr2.equals(""))) {
				updateResultMessage = "生活地點重複填寫";
			} else if (newAddr1.equals(oldAddr1)) {
				count++;
			}
		}
		
		/* 檢查地點三 */
		if (updateResultMessage.equals("")) {
			if (newAddr2.length() > 65) {
				updateResultMessage = "生活地點三超過長度限制";
			} else if (newAddr2.equals(newAddr0) || (newAddr2.equals(newAddr1) && !newAddr1.equals(""))) {
				updateResultMessage = "生活地點重複填寫";
			} else if (newAddr2.equals(oldAddr2)) {
				count++;
			}
		}
		
		/* 結算有效變動項目 */
		if (count == 11) {
			updateResultMessage = "沒有輸入任何有效的修改內容，請重新操作";
		}
		
		return updateResultMessage;
	}
}
