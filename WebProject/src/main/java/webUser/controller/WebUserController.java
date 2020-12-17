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

@Controller
@SessionAttributes({ "registerEmail", "checkCode" })
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
	@GetMapping(value = "/registerForm")
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

	@SuppressWarnings("unchecked")
	@PostMapping(value = "/checkRegisterInfo")
	public String doRegisterSubmit(Model model,
			@RequestParam(value = "userLv", required = false, defaultValue = "0") Integer lv,
			@RequestParam(value = "account", required = false, defaultValue = "") String account,
			@RequestParam(value = "password", required = false, defaultValue = "") String password,
			@RequestParam(value = "firstName", required = false, defaultValue = "") String firstName,
			@RequestParam(value = "lastName", required = false, defaultValue = "") String lastName,
			@RequestParam(value = "nickname", required = false, defaultValue = "") String nickname,
			@RequestParam(value = "gender", required = false, defaultValue = "N") String genderCode,
			@RequestParam(value = "birth", required = false, defaultValue = "1800-01-01") Date birth,
			@RequestParam(value = "fervorValue", required = false, defaultValue = "") String fervor,
			@RequestParam(value = "email", required = false, defaultValue = "") String email,
			@RequestParam(value = "inputCheckCode", required = false, defaultValue = "") String inputCheckCode,
			@RequestParam(value = "phone", required = false, defaultValue = "") String phone,
			@RequestParam(value = "getEmail", required = false, defaultValue = "Y") String willingCode,
			@RequestParam(value = "locationCode", required = false, defaultValue = "") String cityCode,
			@RequestParam(value = "addr0", required = false, defaultValue = "") String addr0,
			@RequestParam(value = "addr1", required = false, defaultValue = "") String addr1,
			@RequestParam(value = "addr2", required = false, defaultValue = "") String addr2) {
		/* 傳回參數宣告 */
		String submitMessage = "";

		/* 建立物件 */
		WebUserData reg_webUser = new WebUserData("", account, password, firstName, lastName, nickname, birth, fervor,
				email, phone, Date.valueOf(today), addr0, addr1, addr2, BigDecimal.ZERO, 0, "inactive", "",
				new UserIdentity(), new FoodFervor(), new Gender(), new UserWilling(), new CityInfo());
		/* 從session取出陣列來繼續完成設定 */
		List<UserIdentity> identityList = (List<UserIdentity>) model.getAttribute("identityList");
		List<Gender> genderList = (List<Gender>) model.getAttribute("genderList");
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
		/* 執行檢查 */
		submitMessage = doRegisterInputCheck(reg_webUser, model, lv, genderCode, inputCheckCode, willingCode, cityCode);

		if (submitMessage.equals("")) {

		}

		return null;
	}

	/* Submit input check */
	public String doRegisterInputCheck(WebUserData reg_webUser, Model model, Integer lv, String genderCode,
			String inputCheckCode, String willingCode, String cityCode) {
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
		
		return submitMessage;
	}
}
