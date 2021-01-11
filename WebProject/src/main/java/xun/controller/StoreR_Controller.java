package xun.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import xun.model.ProductInfoBean;
import xun.model.BoardBean;
import xun.model.StoreBean;
import xun.service.StoreService;

@Controller
@SessionAttributes({"id","restname","Results"}) //Results存入Session 方便Update時把資料貼上欄位
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
	
	@GetMapping("/StoreGetFullstore/{id}/{stname}")
//	@PostMapping("/StoreGetFullstore")
	public String FullStorePath(
			Model model,
			@PathVariable("id") Integer id,
//			@RequestParam("id") Integer id,
			@PathVariable("stname") String stname
//			@RequestParam String stname
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
//		model.addAttribute("Results", list);
		model.addAttribute("Results", null);
		model.addAttribute("sclass", sclass);
		
		return "SimpleStore";
	}
	
	@GetMapping(value = "/StoreGetClassStoreAjax", produces = "application/json; charset=utf-8")
//	@GetMapping(value = "/StoreGetClassStoreAjax", produces="text/html;charset=UTF-8;")
	public @ResponseBody List<StoreBean> ClassStoreAjax(
//			public @ResponseBody Map<String, String> ClassStoreAjax(
//					public @ResponseBody String ClassStoreAjax(
//			@PathVariable("sclass") String sclass
			@RequestParam String sclass,
			@RequestParam String stname,
			@RequestParam(value = "priceLimit" , required = false) Integer priceLimit,
			@RequestParam Integer offset
//			,@RequestParam Integer stopload
			) {
//		System.out.println("sclass = "+sclass);
		List<StoreBean> list = new ArrayList<StoreBean>();
//		System.out.println("sclass  R"+sclass);
//		System.out.println("stname  R"+stname);
//		System.out.println("PPPPPPPPPP  :"+priceLimit);
		if (stname.isEmpty()) {
			list = ss.getClassstore(sclass);
			System.out.println("+++++++++++++++++++++++");
			System.out.println(list);
			System.out.println("+++++++++++++++++++++++");
			if(priceLimit!=null) {
				System.out.println("有進來");
				list= ss.getStoreByClassAndPrice(sclass, priceLimit);
				System.out.println("應當的結果");
				System.out.println(list);
			}
		}else {
			list = ss.getNamestore(stname);
			
		}
		System.out.println("lastList"+list);
		
		Integer off3 = offset+3;
		if(off3>list.size()) {
			off3 = list.size();
//			stopload = 1;
		}
		if(offset>off3) {
			offset=off3;
		}
		list=list.subList(offset, off3);
		System.out.println("offset:"+offset);
		System.out.println("off3:"+off3);
//		String sa = list.get(1).getStname();
//		System.out.println(sa);
//		Map<String, String> map= new HashMap<String,String>();
//		map.put("sa", sa);
		return list;
	}
	
	
	@GetMapping("/StoreGetNamestore")
	public String GetNameStore(
			Model model,
			@RequestParam(value = "nsrch") String stname
			) {
		List<StoreBean> list = ss.getNamestore(stname);
//		model.addAttribute("Results", list);
		model.addAttribute("Results", null);
		model.addAttribute("stname", stname.replace("<", "").replace(">", ""));
		return "SimpleStore";
	}
	
}
