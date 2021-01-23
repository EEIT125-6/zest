package util;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class GeneralInputCheckService {
	
	/* 註冊年齡下限*/
	private static final Integer miniAge =15; 
	
	/* Today */
	public static LocalDate doGetToday() {
		return LocalDate.now();
	}
	
	/* 計算年齡 */
	public static Integer doCaculateAge(Date birth) {
		Integer age = -1;
		LocalDate userBirth = birth.toLocalDate();
		LocalDate today = LocalDate.now();
		Period p = Period.between(userBirth, today);
		age = p.getYears();
		return age;
	}
	
	/* 統一檢查userId方法(不含驗證是否存在於DB) */
	public static String doBasicCheckUserId(String userId) {
		String checkMessage = "";
		
		if (userId.equals("")) {
			checkMessage = "Id不可為空白";
		} else if (userId.length() != 7) {
			checkMessage = "Id長度錯誤";
		} else if (!userId.matches("[0-2]{1}[0-9]{6}")) {
			checkMessage = "Id格式錯誤";
		} 
		return checkMessage;
	}
	
	/* 統一檢查帳號方法(不含驗證是否存在於DB)
	 * mode->請求回復時可接受帳號欄位為空 */
	public static String doBasicCheckAccount(String account, String mode) {
		String submitMessage = "?";
		Boolean inputIsOk = true;
		
		if (account.equals("") && mode.equals("recovery")) {
			submitMessage = "";
			inputIsOk = true;
		} else if (account.equals("") && !mode.equals("recovery")) {
			submitMessage = "帳號不可為空白";
			inputIsOk = false;
		} else if (account.length() < 6 || account.length() > 30) {
			submitMessage = "帳號長度不符格式，僅接受6~30個字元";
			inputIsOk = false;
		} else if (account.matches("[1-9]{1}.")) {
			submitMessage = "帳號不可以數字開頭";
			inputIsOk = false;
		} else if (account.substring(0,1).matches("[0-9]{1}")) {
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
		} else if (!account.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
			submitMessage = "帳號不符合格式";
			inputIsOk = false;
		} else if (account.matches("[a-zA-Z]{1}[0-9a-zA-Z]{5,29}")) {
			submitMessage = "?";
			inputIsOk = true;
		} else {
			submitMessage = "無效的輸入密碼";
			inputIsOk = false;
		}
		
		return submitMessage + "," + inputIsOk.toString();
	}
	
	/* 統一檢查密碼方法
	 * mode->請求回復時可接受帳號欄位為空 */
	public static String doCheckPassword(String password, String mode) {
		String submitMessage = "?";
		Boolean inputIsOk = true;
		
		if (password.equals("") && mode.equals("recovery")) {
			submitMessage = "";
			inputIsOk = true;
		} else if (password.equals("") && !mode.equals("recovery")) {
			submitMessage = "密碼不可為空白";
			inputIsOk = false;
		} else if (password.length() < 6 || password.length() > 30) {
			submitMessage = "密碼長度不符格式，僅接受6~30個字元";
			inputIsOk = false;
		} else if (password.matches("[1-9]{1}.")) {
			submitMessage = "密碼不可以數字開頭";
			inputIsOk = false;
		} else if (password.indexOf("&") != -1) {
			submitMessage = "密碼不可以包含&符號";
			inputIsOk = false;
		} else if (password.indexOf("=") != -1) {
			submitMessage = "密碼不可以包含等號";
			inputIsOk = false;
		} else if (password.indexOf("_") != -1) {
			submitMessage = "密碼不可以包含底線";
			inputIsOk = false;
		} else if (password.indexOf("-") != -1) {
			submitMessage = "密碼不可以包含破折號";
			inputIsOk = false;
		} else if (password.indexOf("+") != -1) {
			submitMessage = "密碼不可以包含加號";
			inputIsOk = false;
		} else if (password.indexOf(",") != -1 || password.indexOf("，") != -1) {
			submitMessage = "密碼不可以包含逗號";
			inputIsOk = false;
		} else if (password.indexOf(".") != -1 || password.indexOf("。") != -1) {
			submitMessage = "密碼不可以包含句號";
			inputIsOk = false;
		} else if (password.indexOf("?") != -1 || password.indexOf("？") != -1) {
			submitMessage = "密碼不可以包含問號";
			inputIsOk = false;
		} else if (password.indexOf("<") != -1 || password.indexOf(">") != -1) {
			submitMessage = "密碼不可以包含<、>";
			inputIsOk = false;
		} else if (!password.matches("[a-zA-Z]{1}[0-9a-zA-Z]{5,29}")) {
			submitMessage = "密碼不符合格式";
			inputIsOk = false;
		} else if (password.matches("[a-zA-Z]{1}[0-9a-zA-Z]{5,29}")) {
			submitMessage = "?";
			inputIsOk = true;
		} else {
			submitMessage = "無效的輸入密碼";
			inputIsOk = false;
		}
		
		return submitMessage + "," + inputIsOk.toString();
	}
	
	/* 統一檢查姓氏方法 */
	public static String doCheckFirstName(String firstName) {
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
	public static String doCheckLastName(String lastName) {
		Boolean inputIsOk = true;
		String message = "?";
		
		if (lastName.equals("")) {
			message = "名字不可為空白";
			inputIsOk = false;
		} else if (lastName.length() > 22) {
			message = "名字長度過長，最多僅22個字元";
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
	
	/* 統一檢查稱呼方法(不含驗證是否存在於DB) */
	public static String doBasicCheckNickname(String nickname, String lastName, String oldNickname) {
		Boolean inputIsOk = true;
		String message = "?";
		
		if (nickname.equals("") && lastName.equals("")) {
			message = "稱呼不可為空白";
			inputIsOk = false;
		} else if (nickname.equals("") && !lastName.equals("")) {
			nickname = lastName;
			inputIsOk = true;
		} else if (nickname.length() > 25){
			message = "稱呼長度過長";
			inputIsOk = false;
		} 
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查生理性別方法 */
	public static String doCheckGender(String genderCode) {
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
	public static String doCheckBirth(Date birth) {
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
		} else if (Date.valueOf(birth.toString()).after(Date.valueOf(LocalDate.now().minus(miniAge, ChronoUnit.YEARS)))) {
			message = "未滿 "+miniAge+" 歲，無法申辦本服務";
			inputIsOk = false;
		} 
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查偏好食物方法 */
	public static String doCheckFervor(String fervor) {
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
	
	/* 統一檢查Email方法(不含驗證是否存在於DB) */
	public static String doBasicCheckEmail(String email) {
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
		} 
		
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查是否接受促銷資訊方法 */
	public static String doCheckGetEmail(String willingCode) {
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
	
	/* 統一檢查電話方法(不含驗證是否存在於DB) */
	public static String doBasicCheckPhone(String phone) {
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
		} 
		return message + "," + inputIsOk.toString();
	}
	
	/* 統一檢查居住區域方法
	 * mode->當使用查詢時，本檢查將略過 */
	public static String doCheckCityCode(Integer cityCode, String mode) {
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
	public static String doCheckAddr0(String addr0, String addr1, String addr2) {
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
	public static String doCheckAddr1(String addr0, String addr1, String addr2) {
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
	public static String doCheckAddr2(String addr0, String addr1, String addr2) {
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
	
	/* 統一檢查驗證碼方法
	 * inputCheckCode->使用者在表單上所輸入的驗證碼
	 * checkCode->使用者執行請求驗證碼後，將產生的驗證碼儲存在model.Attribute物件裡
	 * registerEmail->使用者在表單上所輸入的email地址
	 * email->使用者執行請求驗證碼後，將請求時所使用的email儲存在model.Attribute物件裡 */
	public static String doCheckCheckCode(String inputCheckCode) {
		String message = "";
		if (inputCheckCode.equals("")) {
			message = "驗證碼不可為空白";
		} else if (inputCheckCode.length() != 8) {
			message = "驗證碼錯誤";
		} else if (!inputCheckCode.toUpperCase().matches("[0-9A-Z]{8}")) {
			message = "驗證碼錯誤";
		}
		return message;
	}
	
	public static String doCheckCheckCode(String inputCheckCode, String checkCode, String registerEmail, String email) {
		String message = "";
		if (inputCheckCode.equals("")) {
			message = "驗證碼不可為空白";
		} else if (checkCode == null || registerEmail == null) {
			message = "未產生驗證碼";
		} else if (inputCheckCode.length() != 8) {
			message = "驗證碼錯誤";
		} else if (!inputCheckCode.toUpperCase().equals(checkCode.toUpperCase())) {
			message = "驗證碼檢查失敗";
		} else if (!registerEmail.equals(email)) {
			message = "email資訊不吻合";
		} else if (!checkCode.toUpperCase().matches("[0-9A-Z]{8}")) {
			message = "驗證碼錯誤";
		}
		return message;
	}
}
