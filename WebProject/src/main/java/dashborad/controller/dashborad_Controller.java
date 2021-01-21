package dashborad.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.bind.annotation.PostMapping;

import webUser.model.WebUserData;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.StoreService;

@Controller
@SessionAttributes({"userFullData","listAllStore"})
public class dashborad_Controller {
	
	@Autowired
	StoreService ss;
	
	
	//去管理員後台目錄
	@GetMapping("/adminBack")
	public String adminBack(
			) {
		return "adminBack";
	}
	//去商家後台目錄
	@GetMapping("/storeBack")
	public String storeBack(
			) {
		return "storeBack";
	}
	
	
	//以下管理員統計資料//
	@GetMapping("/dashborad_order")
	public String dashborad_order(
				
			) {
		return "dashborad-orderAnalysis";
	}
	
	@GetMapping("/dashborad_book")
	public String dashborad_book(
	
			) {
		return "dashborad-bookAnalysis";
	}
	
	@GetMapping("/dashborad_comment")
	public String dashborad_comment(
	
			) {
		return "dashborad-commentAnalysis";
	}
	
	@GetMapping("/dashborad_user")
	public String dashborad_user(
	
			) {
		return "dashborad-userAnalysis";
	}
	//以上管理員統計資料//
	
	
	//以下管理員管理資料//
	
	@GetMapping("/adminStore")
	public String adminStore(
			Model model
			) {
		return "adminAdminSystem-Store";
	}
	
	@GetMapping("/adminProduct")
	public String adminProduct(
			Model model
			) {
		return "adminAdminSystem-Product";
	}
	
	@GetMapping("/adminAccount")
	public String adminAccount(
			Model model
			) {
		return "adminAdminSystem-Account";
	}
	//以上管理員管理資料//
	
	
	//以下商家統計資料//
	
	@GetMapping("/storeSt")
	public String storeSt(
			Model model
			) {
		return "storeStatistics-storeContent";
	}
	//以上商家統計資料//
	
	//以下商家管理資料//
	
	@GetMapping("/storeAd")
	public String storeAd(
			Model model
			) {
		WebUserData userFullData  = (WebUserData) model.getAttribute("userFullData");
		List<StoreBean> listAllStore= ss.getMemberAllStore(userFullData);
		model.addAttribute("listAllStore", listAllStore);
		System.out.println("+++");
		System.out.println(listAllStore);
		System.out.println("+++");
		return "storeAdminSystem-storeContent";
	}
	
	@GetMapping("/storeAdClick")
	public String storeAdClick(
			Model model
			,@RequestParam Integer stId
			) {
		model.addAttribute("id", stId);
		return "storeAdminSystem-storeClick";
	}
	
	@GetMapping("/storeAdProduct")
	public String storeAdClickProduct(
			Model model
			,@RequestParam(value = "id") Integer stId
			) {
		List<ProductInfoBean> list3 = ss.getProductInfoBeans(stId);
		model.addAttribute("Products", list3);
		model.addAttribute("id", stId);
		return "storeAdminSystem-storeClick-product";
	}
	//以上商家管理資料//
	
}
