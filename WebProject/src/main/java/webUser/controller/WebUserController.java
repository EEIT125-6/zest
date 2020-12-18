package webUser.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		"reg_webUser"})
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
			@RequestParam(value = "userLv", required = false, defaultValue = "0") Integer lv,
			@RequestParam(value = "account", required = false, defaultValue = "") String account,
			@RequestParam(value = "password", required = false, defaultValue = "") String password,
			@RequestParam(value = "firstName", required = false, defaultValue = "") String firstName,
			@RequestParam(value = "lastName", required = false, defaultValue = "") String lastName,
			@RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
			@RequestParam(value = "gender", required = false, defaultValue = "N") String genderCode,
			@RequestParam(value = "birth", required = false, defaultValue = "1800-01-01") Date birth,
			@RequestParam(value = "fervorOption", required = false, defaultValue="{7}") List<String> fervorValue,
			@RequestParam(value = "email", required = false, defaultValue = "") String email,
			@RequestParam(value = "inputCheckCode", required = false, defaultValue = "") String inputCheckCode,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "getEmail", required = false, defaultValue = "Y") String willingCode,
			@RequestParam(value = "locationCode", required = false, defaultValue = "0") Integer cityCode,
			@RequestParam(value = "addr0", required = false, defaultValue = "") String addr0,
			@RequestParam(value = "addr1", required = false, defaultValue = "") String addr1,
			@RequestParam(value = "addr2", required = false, defaultValue = "") String addr2,
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
		
		String checkCode = (String) model.getAttribute("checkCode");
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
		submitMessage = doRegisterInputCheck(
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
			} else if (!checkCode.matches("[0-9a-zA-Z]{8}")) {
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
	
	/* 前往顯示註冊資料畫面 */
	@GetMapping(value = "/DisplayWebUserInfo")
	public String doCreateDisplayInfo(Model model) {
		return "webUser/DisplayWebUserInfo";
	}
	
	/* 執行使用者資料送出 */
	@PostMapping(value = "/controller/DisplayWebUserInfo")
	public String doRegisterConfirm(
				SessionStatus sessionStatus,
				RedirectAttributes redirectAttributes,
				Model model,
				@RequestParam(value="register", required=false, defaultValue="取消") String mode
			) 
	{
		System.out.println("Test");
		String destinationUrl = "";
		switch(mode) {
			case "確認":
				/* 取出物件 */
				WebUserData reg_webUser = (WebUserData) model.getAttribute("reg_webUser");
				String registerEmail = (String) model.getAttribute("registerEmail");
				
				/* 設定要顯示的訊息 */
				String insertResultMessage = "";
				/* 宣告欲回傳的參數 */
				Integer insertResult = -1;
				String insertResultPage = "webUser/WebUserRegisterForm";
				
				/* 預防性後端輸入檢查，正常時回傳空字串 */
				insertResultMessage = doRegisterInputCheck(
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
				
				break;
			case "取消":
			default:
				/* 清空SessionAttribute */
				sessionStatus.setComplete();
				/* 返回註冊畫面 */
				destinationUrl = "redirect:/webUser/WebUserRegisterForm";
				break;
		}
		return destinationUrl;
	}
	
	@GetMapping(value = "/WebUserRegisterResult")
	/* 前往註冊結束畫面 */
	public String doGoRegisterResult() {
		return "webUser/WebUserRegisterResult";
	}
	
	@GetMapping(value = "/WebUserLogin")
	/* 前往登入畫面 */
	public String doGoLogin() {
		return "webUser/WebUserLogin";
	}

	/* 使用者註冊資料檢查 */
	public String doRegisterInputCheck(
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
}
