package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
}