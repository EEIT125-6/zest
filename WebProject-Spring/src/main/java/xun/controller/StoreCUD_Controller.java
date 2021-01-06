package xun.controller;


import java.io.File;
import java.io.IOException;

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

//import webUser.model.WebUserData;
import xun.model.StoreBean;
import xun.service.ProductService;
import xun.service.StoreService;
import xun.validators.StoreInsertVaildators;

@Controller
@SessionAttributes({"id","restname"})
public class StoreCUD_Controller {

	
	@Autowired
	StoreService ss;
	
	@Autowired
	ProductService ps;
	
	@GetMapping("/Insert")
	public String InsertPage(
			Model model
			) {
		StoreBean storeBean = new StoreBean();
		model.addAttribute("storeBean", storeBean);
		return "Insert";
	}
	
//	@InitBinder
//	public void whiteListing(WebDataBinder binder) {
//	    binder.setAllowedFields(
//	    "author", 
//	    "bookNo", 
//	    "category", 
//	    "price", 
//	    "title", 
//	    "companyId" 
//	    );
//	}
	
	
	@PostMapping("/InsertStore")
	public String InsertStore(
			@ModelAttribute("storeBean") StoreBean storeBean,
			Model model,
			BindingResult result
			) {
//		進行白名單檢查
		
//		String[] suppressedFields = result.getSuppressedFields();
//		if(suppressedFields.length>0) {
//			throw new RuntimeException("有輸入不允許存入的欄位"+
//		StringUtils.arrayToCommaDelimitedString(suppressedFields));
//		}
		
//		檢查
		StoreInsertVaildators validator =  new StoreInsertVaildators();
		System.out.println(storeBean.getStname());
		validator.validate(storeBean, result);
		if(ss.isDup(storeBean.getStname())) {
			result.rejectValue("stname", "","商家名稱重複");
			return "Insert";
		}
		if (result.hasErrors()) {
			return "Insert";
		}
		
////		寫入商家主人資訊
//		WebUserData userFullData = (WebUserData) model.getAttribute("userFullData");
////		WebUserData wud = new WebUserData();
////		wud = ws.getWebUserDataById(userFullData);
//		storeBean.setWebUserData(userFullData);
		
		
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
		if (result.hasErrors()) {
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
	
	@PostMapping("/StoreDelete")
	public String StoreDelete(
			Model model,
			@ModelAttribute("storeBean") StoreBean storeBean
			) {
		ps.deleteALLProduct(storeBean);
		ss.deleteStore(storeBean);
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
		String fakePath = "C:\\ProjectGithub\\zest\\WebProject-Spring\\src\\main\\webapp\\Images\\";
//		String fakePath = "C:\\ProjectGithub\\";
		
		String filePath = request.getSession().getServletContext().getRealPath("");
		String FileName = file.getOriginalFilename().replaceAll("\\s+", "");
		String fakeFilePath =fakePath+FileName;
		
		File writeFile = new File(filePath+"Images\\"+FileName);
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
		String fakePath = "C:\\ProjectGithub\\zest\\WebProject-Spring\\src\\main\\webapp\\Images\\";
		
		String FileName = file.getOriginalFilename().replaceAll("\\s+", "");
		
		String fakeFilePath =fakePath+FileName;
		
		File writeFile = new File(filePath+"Images\\"+FileName);
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
}
