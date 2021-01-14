package xun.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import xun.model.StoreBean;
import xun.service.StoreService;
import xun.util.GlobalService;
@Controller
public class StoreIndexHomeController {
	
	@Autowired
	ServletContext context;
	
	@Autowired
	StoreService ss;
	@GetMapping("/")
	public String home(
			Model model
			) {
		List<StoreBean> list_ADP = ss.getAdvertisementphotostore();
		List<StoreBean> list_AD = ss.getAdvertisementstore();
		model.addAttribute("ADP", list_ADP);
		model.addAttribute("AD", list_AD);
		return "Index1";
	}
//	-----------------------------------------
//	@GetMapping("/undefind")
//	public @ResponseBody String unPhoto(
//			
//			) {
//		String unPhoto = context.getContextPath()+"/Images/LOGO1-removebg-preview.png";
//		return unPhoto;
//	}
//	-----------------------------------------
	
	
	//顯示圖片用    基本上用不到!
	@GetMapping("/Images12421/{path}.{sbma}")
	public ResponseEntity<byte[]>  getPhoto(
			@PathVariable("path") String path,
			@PathVariable("sbma") String sbma
			){
		System.out.println("開始產生圖片======================================================");
		
		System.out.println(path);
		System.out.println(sbma);
		byte[] body = null;
//		ResponseEntity<byte[]> re = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		String RealPath  = "Images/"+path+"."+sbma;
		System.out.println(RealPath);
		body = fileToByteArray(RealPath);
//		body = fileToByteArray("Images/LOGO1-removebg-preview.png");
		
		System.out.println(path);
		return new ResponseEntity<byte[]>(body,headers,HttpStatus.OK);		
	}
	
	String noImage = "/images/NoImage.png";
	String noImageFemale = "/images/NoImage_Female.jpg";
	String noImageMale = "/images/NoImage_Male.png";
	@GetMapping("/xun/picture/{id}")
	public ResponseEntity<byte[]> getPicture(@PathVariable("id") Integer id) {
		System.out.println("開始產生圖片");
		byte[] body = null;
		ResponseEntity<byte[]> re = null;
		MediaType mediaType = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());

		StoreBean sb = ss.get(id);
//		if (sb == null) {
//			return new ResponseEntity<byte[]>(HttpStatus.NOT_FOUND);
//		}
//		String filename = sb.getBannerurl();
//		if (filename != null) {
//			if (filename.toLowerCase().endsWith("jfif")) {
//				mediaType = MediaType.valueOf(context.getMimeType("dummy.jpeg"));
//			} else {
//				mediaType = MediaType.valueOf(context.getMimeType(filename));
//				headers.setContentType(mediaType);
//			}
//		}
//		Blob blob = sb.getBannerurl();
//		if (blob != null) {
//			body = blobToByteArray(blob);
//		} else {
//			String path = null;
//			if (sb.getBannerurl() == null || sb.getBannerurl().length() == 0) {
//				path = noImageMale;
//			} else if (sb.getBannerurl().equals("M")) {
//				path = noImageMale;
//			} else {
//				path = noImageFemale;
//				;
//			}
//		}
		System.out.println(sb.getPhotourl());
		body = fileToByteArray(sb.getPhotourl());
		re = new ResponseEntity<byte[]>(body, headers, HttpStatus.OK);

		return re;
	}
	
	private byte[] fileToByteArray(String path) {
		byte [] result = null;
		try(InputStream is = context.getResourceAsStream(path);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();) 
		{
			byte[] b = new byte[524288];
			int len = 0;
			while ((len = is.read(b)) != -1) {
				baos.write(b,0,len);
			}
			result = baos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	

	
}
