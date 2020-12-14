package xun.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import xun.model.BoardBean;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.StoreService;

@Controller
@SessionAttributes({"id","restname"})
public class StoreR_Controller {
	
	@Autowired
	StoreService ss;
	
	@GetMapping("/StoreGetFullstore")
	public String FullStore(
			Model model,
			@RequestParam String stname,
			@RequestParam Integer id
			) {
		if(id != null) {		
			model.addAttribute("id", id);
		}	
//		送過來有附上ID參數  就把ID參數存入Session 更新下方Session ID
//		先把商家名稱存入 就可以做名稱是否重複的判斷!
		model.addAttribute("restname", stname);
		
		List<StoreBean> list = ss.getFullstore((Integer) model.getAttribute("id"));
		model.addAttribute("Results", list);
		
		List<BoardBean> list2 = ss.getComment((Integer) model.getAttribute("id"));
		model.addAttribute("Comments", list2);
		
		List<ProductInfoBean> list3 = ss.getProductInfoBeans((Integer) model.getAttribute("id"));
		model.addAttribute("Products", list3);
		
		return "detailStore";
	}
	
	@GetMapping("/StoreGetClassstore")
	public String ClassStore(
			Model model,
			@RequestParam String sclass
			) {
		List<StoreBean> list = ss.getClassstore(sclass);
		model.addAttribute("Results", list);
		
		return "SimpleStore";
	}
	
	@GetMapping("/StoreGetNamestore")
	public String GetNameStore(
			Model model,
			@RequestParam(value = "nsrch") String stname
			) {
		List<StoreBean> list = ss.getNamestore(stname);
		model.addAttribute("Results", list);
		return "SimpleStore";
	}
	
}
