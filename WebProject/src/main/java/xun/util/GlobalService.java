package xun.util;

import java.io.File;

public class GlobalService {
	public static String SYSTEM_PHOTO_PATH;
	
	public static String getUploadProductPhotoPath() {
		File productPhotoDir = new File("src/main/webapp/WEB-INF/views/images/productInfo/images");
		if (productPhotoDir.exists()) {
			return productPhotoDir.getAbsolutePath()+"/";
		} else {
			if(productPhotoDir.mkdir()) {
				return productPhotoDir.getAbsolutePath()+"/";
			} else {
				return null;
			}
		}	
	}
	
	public static String getUploadStorePhotoPath() {
		File storePhotoDir = new File("src/main/webapp/Images");
		if (storePhotoDir.exists()) {
			return storePhotoDir.getAbsolutePath()+"/";
		} else {
			if(storePhotoDir.mkdir()) {
				return storePhotoDir.getAbsolutePath()+"/";
			} else {
				return null;
			}
		}
	}
}

