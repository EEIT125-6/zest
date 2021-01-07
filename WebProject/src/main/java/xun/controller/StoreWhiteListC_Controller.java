package xun.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;

import webUser.model.WebUserData;
import xun.model.StoreBean;
import xun.service.StoreService;
import xun.validators.StoreInsertVaildators;

@SessionAttributes({"id","restname","userFullData"})
@Controller
public class StoreWhiteListC_Controller {

	@Autowired
	StoreService ss;
	
	
	@GetMapping("/Insert")
	public String InsertPage(
			Model model
			) {
		StoreBean storeBean = new StoreBean();
		model.addAttribute("storeBean", storeBean);
		return "Insert";
	}
	
	
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
	
	
	@InitBinder
	public void whiteListing(WebDataBinder binder) {
	    binder.setAllowedFields(
	    "stname", 
	    "sclass", 
	    "saddress", 
	    "stitd", 
	    "stitddt", 
	    "tel" 
	    );
	}
}
