package xun.controller;


import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import webUser.model.WebUserData;
import webUser.service.WebUserService;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.ProductService;
import xun.service.StoreService;
import xun.service.TraceService;
import xun.util.GlobalService;
import xun.validators.StoreInsertVaildators;

@Controller
@SessionAttributes({"id","restname","userFullData"})
public class StoreCUD_Controller {

	
	@Autowired
	StoreService ss;
	
	@Autowired
	ProductService ps;
	
	@Autowired
	WebUserService ws;
	
	@Autowired
	TraceService ts;
	
//	@GetMapping("/Insert") 暫時作廢
	public String InsertPage(
			Model model
//			,@RequestParam(value = "userId",required = false) String userId
			) throws SQLException {
		StoreBean storeBean = new StoreBean();
//		WebUserData user = ws.getWebUserDataById(userId);
//		storeBean.setWebUserData(user);
		model.addAttribute("storeBean", storeBean);
		return "Insert";
	}
	
//	@PostMapping("/InsertStore")
	public String InsertStore(
			@ModelAttribute("storeBean") StoreBean storeBean,
			Model model,
			BindingResult result
			) {
//		檢查
		StoreInsertVaildators validator =  new StoreInsertVaildators();
		System.out.println(storeBean.getStname());
		validator.validate(storeBean, result);
		if(ss.isDup(storeBean.getStname())) {
			result.rejectValue("stname", "","商家名稱重複");
			return "Insert";
		}
		if (result.hasErrors()) {
			System.out.println("Result是有錯誤的 應該要返回");
			return "Insert";
		}

//		寫入商家主人資訊
		WebUserData userFullData = (WebUserData) model.getAttribute("userFullData");
//		WebUserData wud = new WebUserData();
//		wud = ws.getWebUserDataById(userFullData);
		storeBean.setWebUserData(userFullData);

//		新增
		ss.save(storeBean);
//		將新登入的商家的ID取出 並送到查詢詳細商家的Controller 做印出
		Integer NewStoreId = storeBean.getId();
		String NewStoreName = storeBean.getStname();
		model.addAttribute("id", NewStoreId);
		model.addAttribute("stname", NewStoreName);
		return "redirect:/StoreGetFullstore";
		
	}
	

	@GetMapping("/Update")
	public String UpdatePage(
			Model model,
			@RequestParam Integer id
			) {
		StoreBean sb = ss.get(id);
		model.addAttribute("storeBean", sb);
		System.out.println(sb);
		return "Update";
	}
	@PostMapping("/StoreUpdate")
	public String Update(
			Model model,
			@ModelAttribute("storeBean") StoreBean storeBean,
			BindingResult result
			) {
//		檢查
		StoreInsertVaildators validator =  new StoreInsertVaildators();

		System.out.println("back    "+storeBean);
		validator.validate(storeBean, result);
		if(ss.isDup(storeBean.getStname()) && !storeBean.getStname().equals((String) model.getAttribute("restname"))) {
			result.rejectValue("stname", "","商家名稱重複");
			return "Update";
		}
		System.out.println(result+"目前的錯誤!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
		if (result.hasErrors()) {
			System.out.println("Result是有錯誤的 應該要返回");
			return "Update";
		}
//		修改

		ss.updateStore(storeBean);

		Integer NewStoreId = storeBean.getId();
		String NewStoreName = storeBean.getStname();
		System.out.println("MODEL.getId():"+NewStoreId+"storeBean.getStname():"+NewStoreName);
		model.addAttribute("id", NewStoreId);
		model.addAttribute("stname", NewStoreName);
		return "redirect:/StoreGetFullstore";
	}

	@PostMapping("/DeleteStore")
	public String DeletePage(
			Model model,
			@RequestParam Integer id,
			@RequestParam String stname
			) {
		StoreBean sb = ss.get(id);
		model.addAttribute("storeBean", sb);
		return "DeleteStore";
	}
	
	@PostMapping("/OffShelfStore")
	public String storeOffShelf(
			Model model,
			@RequestParam Integer id
			) {
//		StoreBean sb = ss.get(id);
//		model.addAttribute("storeBean", sb);
		ss.storeOffShelf(id);
//		ss.getRenameStore(id); //似乎用不到
		//把下架店家的所有商品改成下架狀態
		StoreBean sb = ss.get(id);
		for(ProductInfoBean pp : ps.getStoreProduct(sb)) {
			ps.productOffShelf(pp.getProduct_id());
		}
		//移除所有追蹤狀態
		ts.removeAllBeTraceStore(id);
		return "redirect:/";
	}
	
	@PostMapping("/StoreDelete")
	public String StoreDelete(
			Model model,
			@ModelAttribute("storeBean") StoreBean storeBean
			) {
		ps.deleteALLProduct(storeBean);
		ss.deleteStore(storeBean);
		//移除所有追蹤狀態
		ts.removeAllBeTraceStore(storeBean.getId());
		return "exDeleteStore";
	}
	
	
	
	@GetMapping("/UpdatePhoto")
	public String UpdatePhoto(
			Model model,
			@RequestParam Integer id
			) {
		StoreBean sb = ss.get(id);
		model.addAttribute("storeBean", sb);
//		System.out.println(sb.getStname());
		return "testupload";
	}
	
	
	@PostMapping("/testexupload")
	public String exUpdatePhoto(
			Model model,
			@ModelAttribute("storeBean") StoreBean storeBean,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request
			) {
		MultipartFile file2 = file;
//		String fakePath = "C:\\JavaMVCWorkspace\\WebProject\\src\\main\\webapp\\Images\\";
		String fakePath = GlobalService.getUploadStorePhotoPath();
//		String fakePath = "C:\\ProjectGithub\\";
		
		String filePath = request.getSession().getServletContext().getRealPath("");
		
		String FileName = file.getOriginalFilename().replaceAll("\\s+", "");
		String FileFormat = FileName.split("\\.")[1];
		
		String fakeFilePath =fakePath+FileName;
		
		File writeFile = new File(filePath+"Images\\"+FileName);
		
		FileName = storeBean.getId()+"!_!photo"+storeBean.getStname().replace(" ", "")+"."+FileFormat;
		File fkf = new File(fakePath+""+FileName);
		try {
//			file2.transferTo(writeFile);
			file.transferTo(fkf);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		將檔案路徑和名稱寫進資料庫
		Integer id = storeBean.getId();
		String stname = storeBean.getStname();
		String photourl = "Images/"+FileName;
		String bannerurl = "";
		StoreBean sb1 = new StoreBean(id, stname, bannerurl, photourl);
		ss.photoStore(sb1);
		model.addAttribute("id", id);
		model.addAttribute("stname", stname);
		return "redirect:/StoreGetFullstore";
	}
	
	
	
	@GetMapping("/UpdateBanner")
	public String UpdateBanner(
			Model model,
			@RequestParam Integer id
			) {
		StoreBean sb = ss.get(id);
		model.addAttribute("storeBean", sb);
		return "testuploadbanner";
	}
	
	@PostMapping("/testexuploadbanner")
	public String exUpdateBanner(
			Model model,
			@ModelAttribute("storeBean") StoreBean storeBean,
			@RequestParam("file") MultipartFile file,
			HttpServletRequest request
			) {
		MultipartFile file2 = file;
		String filePath = request.getSession().getServletContext().getRealPath("");
//		String fakePath = "C:\\JavaMVCWorkspace\\WebProject\\src\\main\\webapp\\Images\\";
		String fakePath = GlobalService.getUploadStorePhotoPath();
		
		String FileName = file.getOriginalFilename().replaceAll("\\s+", "");
		String FileFormat = FileName.split("\\.")[1];
		
		String fakeFilePath =fakePath+FileName;
		
		File writeFile = new File(filePath+"Images\\"+FileName);
		
		FileName = storeBean.getId()+"!_banner!"+storeBean.getStname().replace(" ", "")+"."+FileFormat;
		File fkf = new File(fakePath+""+FileName);
		try {
			file.transferTo(fkf);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		將檔案路徑和名稱寫進資料庫
		Integer id = storeBean.getId();
		String stname = storeBean.getStname();
		String photourl = "";
		String bannerurl = "Images/"+FileName;
		StoreBean sb1 = new StoreBean(id, stname, bannerurl, photourl);
		ss.bannerStore(sb1);
		model.addAttribute("id", id);
		model.addAttribute("stname", stname);
		return "redirect:/StoreGetFullstore";
	}
	
	@ModelAttribute("sclassCategory")
	public List<String> getSclassCategory(){
		return ss.getSclassCategory();
	}
}
