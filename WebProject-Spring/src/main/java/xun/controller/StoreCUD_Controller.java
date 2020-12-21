package xun.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import xun.model.StoreBean;
import xun.service.StoreService;
import xun.validators.StoreInsertVaildators;

@Controller
@SessionAttributes({"id","restname"})
public class StoreCUD_Controller {

	
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
		System.out.println(storeBean.getStname());
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
		ss.deleteStore(storeBean);
		return "exDeleteStore";
	}
	
	
	
	@GetMapping("/UpdatePhoto")
	public String UpdatePhoto(
			Model model,
			@RequestParam Integer id,
			@RequestParam String stname
			) {
		StoreBean sb = ss.get(id);
		model.addAttribute("storeBean", sb);
		model.addAttribute("stname", stname);
		return "testupload";
	}
	
//	@PM
	public String exUpdatePhoto(
			) {
				return null;
		
	}
	
	
	
	@GetMapping("/UpdateBanner")
	public String UpdateBanner(
			Model model
			) {
		
		return "testexuploadbanner";
	}
}
