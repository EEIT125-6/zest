package xun.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import xun.model.StoreBean;
import xun.service.StoreService;
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
		model.addAttribute("Ad", list_AD);
		return "Index1";
	}
	@GetMapping("/xun")
	public String homepage(
			Model model
			) {
		List<StoreBean> list_ADP = ss.getAdvertisementphotostore();
		List<StoreBean> list_AD = ss.getAdvertisementstore();
		model.addAttribute("ADP", list_ADP);
		model.addAttribute("Ad", list_AD);
		return "Index1";
	}
	
	//顯示圖片用
	@GetMapping("/photo/{path}")
	public ResponseEntity<byte[]>  getPhoto(
			@PathVariable("path") String path
			){
		System.out.println("開始產生圖片");
		byte[] body = null;
		ResponseEntity<byte[]> re = null;
		HttpHeaders headers = new HttpHeaders();
		headers.setCacheControl(CacheControl.noCache().getHeaderValue());
		
		body = fileToByteArray(path);
		
		return new ResponseEntity<byte[]>(body,headers,HttpStatus.OK);		
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
