package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class GlobalService {
	public static String SYSTEM_PHOTO_PATH;
	
	/* Today */
	public static LocalDate getToday() {
		return LocalDate.now();
	}
	
	public static String getUploadProductPhotoPath() {
		List<String> photourl =	Arrays.asList(
				"C:\\JavaMVCWorkspace\\WebProject\\src\\main\\webapp\\WEB-INF\\views\\images\\productInfo\\images\\"
				,"C:\\ProjectGithub\\zest\\WebProject\\src\\main\\webapp\\WEB-INF\\views\\images\\productInfo\\images\\"
				,"H:\\MVCWorkspace\\WebProject\\src\\main\\webapp\\WEB-INF\\views\\images\\productInfo\\images\\"
				,"C:\\Users\\Tony Chi\\Desktop\\Programming\\JAVA Stuff\\AdvancedWork\\For Rehersal\\src\\main\\webapp\\WEB-INF\\views\\images\\productInfo\\images\\"
				);
		for(String pUrl : photourl) {
			File pUF = new File(pUrl);
			if(pUF.exists()) {
				return pUrl;			
			}else {
				String failUrl = "C:/photoTemp";
				File fail = new File(failUrl);
				if(!fail.exists()) {
						fail.mkdirs();
				failUrl = failUrl + "/";
				return failUrl;
				}
			}
		}
		return null;	
	}
	
	public static String getUploadStorePhotoPath() {
		List<String> photourl =	Arrays.asList(
				"C:\\JavaMVCWorkspace\\WebProject\\src\\main\\webapp\\Images\\"
				,"C:\\ProjectGithub\\zest\\WebProject\\src\\main\\webapp\\Images\\"
				,"H:\\MVCWorkspace\\WebProject\\src\\main\\webapp\\Images\\"
				,"C:\\Users\\Tony Chi\\Desktop\\Programming\\JAVA Stuff\\AdvancedWork\\For Rehersal\\src\\main\\webapp\\Images\\"
				);
		for(String pUrl : photourl) {
			File pUF = new File(pUrl);
			if(pUF.exists()) {
				return pUrl;			
			}else {
				String failUrl = "C:/photoTemp";
				File fail = new File(failUrl);
				if(!fail.exists()) {
						fail.mkdirs();
				failUrl = failUrl + "/";
				return failUrl;
				}
			}
		}
		return null;	
	}
	
	/* 取得UserIcon的實體路徑 By George017 */
	public static String getUploadUserIconPath() {
		List<String> photourl =	Arrays.asList(
				"C:/JavaMVCWorkspace/WebProject/src/main/webapp/WEB-INF/views"
				,"C:/ProjectGithub/zest/WebProject/src/main/webapp/WEB-INF/views"
				,"H:/MVCWorkspace/WebProject/src/main/webapp/WEB-INF/views"
				,"C:/Users/Tony Chi/Desktop/Programming/JAVA Stuff/AdvancedWork/For Rehersal/src/main/webapp/WEB-INF/views"
				);
		for(String pUrl : photourl) {
			File pUF = new File(pUrl);
			if(pUF.exists()) {
				return pUrl;			
			}else {
				String failUrl = "C:/photoTemp";
				File fail = new File(failUrl);
				if(!fail.exists()) {
						fail.mkdirs();
				failUrl = failUrl + "/";
				return failUrl;
				}
			}
		}
		return null;	
	}
	
	/* 圖像檔案處理 */
	public static Map<String, String> doUpdatePic(String oldIconUrl, String newIconUrl, CommonsMultipartFile pic) {
		/* 變數宣告 */
		Map<String, String> map = new HashMap<>();
		String oldIconPath = (oldIconUrl.equals("")) ? "" : GlobalService.getUploadUserIconPath() + oldIconUrl;
		String newIconPath = GlobalService.getUploadUserIconPath() + newIconUrl;
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
	public static Boolean doDeleteOldIcon(String oldIconPath) throws Exception{
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
				/* 存在則先刪除舊有的暫存檔 */
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
	public static Boolean doCreateNewIcon(String newIconPath, CommonsMultipartFile pic) throws Exception{
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
	public static Boolean doWritePicIntoFile(File picFile, CommonsMultipartFile pic) throws Exception{
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
	
	/* 寫入Cookie */
	public static void doWriteUserCookie(HttpServletRequest request, HttpServletResponse response, String account, String password, Boolean remember) {
		Cookie cookieAccount = new Cookie("ckAccount", account);
		Cookie cookiePassword = new Cookie("ckPassword", password);
		Cookie cookieRemember = new Cookie("ckRemember", remember.toString());
		/* 是否要記住帳密 */
		if (remember) {
			// Cookie的存活期: 七天
			cookieAccount.setMaxAge(7 * 24 * 60 * 60);       
			cookieAccount.setPath(request.getContextPath());
			cookiePassword.setMaxAge(7 * 24 * 60 * 60);       
			cookiePassword.setPath(request.getContextPath());
			cookieRemember.setMaxAge(7 * 24 * 60 * 60);       
			cookieRemember.setPath(request.getContextPath());
		} else {
			// Cookie的存活期: 0，立刻刪除
			cookieAccount.setMaxAge(0);       
			cookieAccount.setPath(request.getContextPath());
			cookiePassword.setMaxAge(0);       
			cookiePassword.setPath(request.getContextPath());
			cookieRemember.setMaxAge(0);       
			cookieRemember.setPath(request.getContextPath());
		}
		response.addCookie(cookieAccount);
		response.addCookie(cookiePassword);
		response.addCookie(cookieRemember);
	}
	
	/* 移除Cookie */
	public static void doRemoveUserCookie(HttpServletRequest request, HttpServletResponse response, String account, String password, Boolean remember) {
		// Cookie的存活期: 0，立刻刪除
		Cookie cookieAccount = new Cookie("ckAccount", account);
		Cookie cookiePassword = new Cookie("ckPassword", password);
		Cookie cookieRemember = new Cookie("ckRemember", remember.toString());
		cookieAccount.setMaxAge(0);
		cookieAccount.setPath(request.getContextPath());
		cookiePassword.setMaxAge(0);
		cookiePassword.setPath(request.getContextPath());
		cookieRemember.setMaxAge(0);
		cookieRemember.setPath(request.getContextPath());
		response.addCookie(cookieAccount);
		response.addCookie(cookiePassword);
		response.addCookie(cookieRemember);
	}
	
	/* 統一檢查密碼方法 */
	public static String doCheckPassword(String password) {
		String submitMessage = "?";
		Boolean inputIsOk = true;
		
		if (password.equals("")) {
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
			submitMessage = "";
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
		} else if (Date.valueOf(birth.toString()).after(Date.valueOf(LocalDate.now().minus(15, ChronoUnit.YEARS)))) {
			message = "未滿15歲，無法申辦本服務";
			inputIsOk = false;
		} else {
			inputIsOk = true;
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
	
	/* 統一檢查居住區域方法 */
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
	
	/* 統一檢查驗證碼方法 */
	public static String doCheckCheckCode(String inputCheckCode, String checkCode, String registerEmail, String email) {
		String message = "";
		if (inputCheckCode.equals("")) {
			message = "驗證碼不可為空白";
		} else if (checkCode == null || registerEmail == null) {
			message = "未產生驗證碼";
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