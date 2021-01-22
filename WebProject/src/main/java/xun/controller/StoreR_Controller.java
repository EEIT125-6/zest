package xun.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import xun.model.TraceBean;
import xun.service.StoreService;
import xun.service.TraceService;
import xun.test.testJAVA;

@Controller
@SessionAttributes({"id","restname","Results"}) //Results存入Session 方便Update時把資料貼上欄位
public class StoreR_Controller {
	
	@Autowired
	StoreService ss;
	
	@Autowired
	TraceService ts;
	
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
		
		StoreBean sb = ss.get(id);
		List<StoreBean> list_ADGuess = ss.guessYouLike(sb.getSclass());
		model.addAttribute("ADP", list_ADGuess);
		
		List<TraceBean> list_beTrace = ts.StoreBeTrace(id);
		if(list_beTrace.size()==0) {
			TraceBean tb11 = new TraceBean();
			tb11.setMemberId(-1);
			list_beTrace.add(tb11);
		}
		
		model.addAttribute("list_beTrace", list_beTrace);
		System.out.println("--------------------------------------------------------------"+list_beTrace.get(0).getMemberId());
		System.out.println("--------------------------------------------------------------"+list_beTrace);
		
		ss.setClickCount(id);
//		System.out.println("*****************************************click"+a);
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
		
		StoreBean sb = ss.get(id);
		List<StoreBean> list_ADGuess = ss.guessYouLike(sb.getSclass());
		model.addAttribute("ADP", list_ADGuess);
		
		
		List<TraceBean> list_beTrace = ts.StoreBeTrace(id);
		if(list_beTrace.size()==0) {
			TraceBean tb11 = new TraceBean();
			tb11.setMemberId(-1);
			list_beTrace.add(tb11);
		}
		model.addAttribute("list_beTrace", list_beTrace);
		System.out.println("--------------------------------------------------------------"+list_beTrace);
		
		ss.setClickCount(id);
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
			@RequestParam(value = "star" , required = false) Float star,
			@RequestParam Integer offset,
			@RequestParam(value = "priceOrder") Integer priceOrder
			) {
//		System.out.println("sclass = "+sclass);
		List<StoreBean> list = new ArrayList<StoreBean>();
//		System.out.println("sclass  R"+sclass);
//		System.out.println("stname  R"+stname);
//		System.out.println("PPPPPPPPPP  :"+priceLimit);
		if (stname.isEmpty()) {
			list = ss.getClassstore(sclass);
			System.out.println("+++++++++++++++++++++++");
//			System.out.println(list);
			System.out.println("priceLimit "+priceLimit);
			System.out.println("star "+star);
			System.out.println("+++++++++++++++++++++++");
			if(priceLimit!=null) {
				if(star!=null) {
					System.out.println("同時跑兩個 好忙");
					list = ss.getStorebyClassandStarandPrice(sclass, priceLimit, star);
				}
				else {
					System.out.println("只跑價格");
					list= ss.getStoreByClassAndPrice(sclass, priceLimit);
				}
			}
			if(star!=null && priceLimit == null) {
				System.out.println("只跑星星");
				list = ss.getStorebyClassandStar(sclass, star);
			}
		}else if(!stname.isEmpty()) {
			list = ss.getNamestore(stname);
			System.out.println("用模糊搜尋再找商家");
			if(priceLimit!=null) {
				if(star!=null) {
					System.out.println("跑模糊又跑兩個 好累喔");
					list = ss.getNamestoreandPriceandStar(stname, priceLimit, star);
				}else {
					System.out.println("模糊 只跑了價格");
					list = ss.getNamestoreandPrice(stname, priceLimit);
				}
			}
			if(star!=null &&priceLimit==null) {
				System.out.println("模糊 只跑了價格");
				list = ss.getNamestoreandStar(stname, star);
			}
		}else {
			System.out.println("what?? how do you do that shit");
		}
//		System.out.println("lastList"+list);
		if (priceOrder == 1) {
			Collections.sort(list, new PriceComparatorA());
		}else if(priceOrder == -1) {
			Collections.sort(list, new PriceComparatorD());
		}else if(priceOrder == 2) {
			Collections.sort(list, new TimePriceComparatorH());
		}else {
			//依照點擊數排序
			Collections.sort(list, new ClickComparator());	
			System.out.println("*************************************************依照點擊數排序依照點擊數排序依照點擊數排序依照點擊數排序");
		}
		
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

class ClickComparator implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		StoreBean t1 = (StoreBean) o1;
		StoreBean t2 = (StoreBean) o2;
		//由多到少
		if(t1.getClick()>t2.getClick()) {
			return -1;
		}else if(t1.getClick()==t2.getClick()) {
			return 0;
		}else {				
			return 1;
		}
	}
}

class PriceComparatorA implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		StoreBean t1 = (StoreBean) o1;
		StoreBean t2 = (StoreBean) o2;
		//由少到多 
		if(t1.getRealprice()>t2.getRealprice()) {
			return 1;
		}else if(t1.getRealprice()==t2.getRealprice()) {
			return 0;
		}else {				
			return -1;
		}
	}
}

class PriceComparatorD implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		StoreBean t1 = (StoreBean) o1;
		StoreBean t2 = (StoreBean) o2;
		//由多到少
		if(t1.getRealprice()>t2.getRealprice()) {
			return -1;
		}else if(t1.getRealprice()==t2.getRealprice()) {
			return 0;
		}else {				
			return 1;
		}
	}
}
class TimePriceComparatorH implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		StoreBean t1 = (StoreBean) o1;
		StoreBean t2 = (StoreBean) o2;
		//由多到少
		if(t1.getTimestamp().before(t2.getTimestamp())) {
			return 1;
		}else if(t1.getTimestamp().equals(t2.getTimestamp())) {
			return 0;
		}else {				
			return -1;
		}
	}
}