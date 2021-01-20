package xun.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class GlobalService {
	public static String SYSTEM_PHOTO_PATH;
	
	public static String getUploadProductPhotoPath() {
		List<String> photourl =	Arrays.asList(
				
				"C:\\JavaMVCWorkspace\\WebProject\\src\\main\\webapp\\WEB-INF\\views\\images\\productInfo\\images\\"
				,"C:\\Users\\Tony Chi\\Desktop\\Programming\\JAVA Stuff\\AdvancedWork\\For Rehersal\\src\\main\\webapp\\WEB-INF\\views\\images\\productInfo\\images\\"
				,"C:\\ProjectGithub\\zest\\WebProject\\src\\main\\webapp\\WEB-INF\\views\\images\\productInfo\\images\\"
				,"H:\\MVCWorkspace\\WebProject\\src\\main\\webapp\\WEB-INF\\views\\images\\productInfo\\images\\"
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
				"C:\\Users\\Tony Chi\\Desktop\\Programming\\JAVA Stuff\\AdvancedWork\\For Rehersal\\src\\main\\webapp\\Images\\"
//				,"C:\\Users\\Tony Chi\\Desktop\\Programming\\JAVA Stuff\\AdvancedWork\\Zest\\src\\main\\webapp\\Images\\"
				,"C:\\ProjectGithub\\zest\\WebProject\\src\\main\\webapp\\Images\\"
				,"H:\\MVCWorkspace\\WebProject\\src\\main\\webapp\\Images\\"
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
}

