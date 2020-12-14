package xun.controller;

import java.util.HashMap;
import java.util.Map;

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
@SessionAttributes({"id"})
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
//		Integer id = null;
//		檢查
		StoreInsertVaildators validator =  new StoreInsertVaildators();
		System.out.println(storeBean.getStname());
		validator.validate(storeBean, result);
		if(ss.isDup(storeBean.getStname())) {
			result.rejectValue("stname", "","商家名稱重複SPRING");
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
	

}
