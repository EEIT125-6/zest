package dashborad.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
//import org.springframework.web.bind.annotation.PostMapping;

import webUser.model.WebUserData;
import webUser.service.WebUserService;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.model.TraceBean;
import xun.service.StoreService;
import xun.service.TraceService;
import xun.util.sendJavaMail;

@Controller
@SessionAttributes({"userFullData","listAllStore"})
public class dashborad_Controller {
	
	@Autowired
	StoreService ss;
	
	@Autowired
	TraceService ts;
	
	@Autowired
	WebUserService wus;
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
		WebUserData userFullData  = (WebUserData) model.getAttribute("userFullData");
		List<StoreBean> listAllStore= ss.getMemberAllStore(userFullData);
		model.addAttribute("listAllStore", listAllStore);
		System.out.println("+++");
		System.out.println(listAllStore);
		System.out.println("+++");
		return "storeStatistics-storeContent";
	}
	
	@GetMapping("/storeStClick")
	public String storeSingleSt(
			Model model
			,@RequestParam Integer stId
			) {
		model.addAttribute("stId", stId);
		return "storeStatistics-singleStoreStatistics";
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
		StoreBean sb = ss.get(stId);
		model.addAttribute("stname", sb.getStname());
		
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
	
	@GetMapping("/storeAdTrace")
	public String storeAdTrace(
			Model model
			,@RequestParam(value = "id") Integer stId
			) throws SQLException {
		List<Integer> Tracelist = ts.StoreBeTraceQueryByMemberId(stId);
		List<WebUserData> memberList = new ArrayList<WebUserData>();
				
		for(Integer memberId :Tracelist) {
			memberList.add(wus.getWebUserDataById(String.valueOf(memberId)));
		}
		String stname = ss.get(stId).getStname();
		model.addAttribute("stname", stname);
		model.addAttribute("memberList", memberList);
		model.addAttribute("stId", stId);
		System.out.println(memberList);
		System.out.println(memberList.get(0).getGender().getGenderText());
		return "storeAdminSystem-storeTrace";
	}
	
	@PostMapping("/storeAdTraceMail")
	public String storeAdTraceMail(
			Model model
			,@RequestParam String stId
			,@RequestParam String memberNickname
			,@RequestParam String memberEmail
			) {
		model.addAttribute("stId", stId);
		model.addAttribute("memberNickname", memberNickname);
		System.out.println(memberNickname);
		model.addAttribute("memberEmail", memberEmail);
		return "storeTraceMail";
	}
	
	@PostMapping("/storeAdMailSend")
	public String storeAdMailSend(
			Model model
			,@RequestParam String stId
			,@RequestParam String memberEmail
			,@RequestParam String mailSub
			,@RequestParam String mailContext
			) {
		//寄信
			sendJavaMail.goSendMail(memberEmail, mailSub, mailContext);
		//轉跳
		model.addAttribute("stId", stId);
		return "redirect:/storeAdClick";
	}
	
//	@GetMapping("/storeStMonth")
//	public @ResponseBody List<Integer> storeStMonth(
//			Model model
//			,@RequestParam Integer stId
//			) {
//		List<Integer> rs = ts.StoreStMonthTrace(stId);
//		System.out.println(rs);
//		return rs;
//	}
	@GetMapping("/storeStMonth")
	public @ResponseBody Map<String, List<Integer>> storeStMonth(
			Model model
			,@RequestParam Integer stId
			) {
		List<Integer> rs = ts.StoreStMonthTrace(stId);
		System.out.println(rs);
//		-----------------------
		Integer man = 0;
		Integer female = 0;
		List<Integer> rs2 = new ArrayList<Integer>();
		List<TraceBean> list = ts.StoreStGender(stId);
		for (TraceBean tb:list) {//女性 男性
			try {
				if (wus.getWebUserDataById(String.valueOf(tb.getMemberId())).getGender().getGenderText().equals("男性")) {
					man++;
				}else if(wus.getWebUserDataById(String.valueOf(tb.getMemberId())).getGender().getGenderText().equals("女性")) {
					female++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		rs2.add(man);
		rs2.add(female);
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		map.put("d1", rs);
		map.put("d2", rs2);
		return map;
	}
	
	//暫時無用
//	@GetMapping("/storeStGender")
//	public @ResponseBody List<Integer> storeStGender(
//			Model model
//			,@RequestParam Integer stId
//			)  {
//		Integer man = 0;
//		Integer female = 0;
//		List<Integer> rs = new ArrayList<Integer>();
//		List<TraceBean> list = ts.StoreStGender(stId);
//		for (TraceBean tb:list) {//女性 男性
//			try {
//				if (wus.getWebUserDataById(String.valueOf(tb.getMemberId())).getGender().getGenderText().equals("男性")) {
//					man++;
//				}else if(wus.getWebUserDataById(String.valueOf(tb.getMemberId())).getGender().getGenderText().equals("女性")) {
//					female++;
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		rs.add(man);
//		rs.add(female);
//		System.out.println(rs);
//		return rs;
//	}
	
//	觀察用
//	@GetMapping("/storeGetTraceMember")
//	public @ResponseBody List<Map<String, Object>> iWantLearnMore(
//			@RequestParam Integer stId
//			) throws SQLException{
//		List<Integer> Tracelist = ts.StoreBeTraceQueryByMemberId(stId);
//		Map<String, Object> map1 = new HashMap<String, Object>();
//		List<Map<String, Object>> TraceListmap  = new ArrayList<Map<String,Object>>();
//		for(Integer memberId:Tracelist) {
//			WebUserData wud = wus.getWebUserDataById(String.valueOf(memberId));
//			map1.put("memberId", wud.getUserId());
//			map1.put("memberNickname",wud.getNickname());
//			TraceListmap.add(map1);
//		}
//		return TraceListmap;
//	}
	
	//以上商家管理資料//
	
}
