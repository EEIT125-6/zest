package xun.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import javax.xml.ws.soap.AddressingFeature.Responses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


import xun.model.BoardBean;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.StoreService;
import xun.service.impl.StoreServiceImpl;

@Controller
@SessionAttributes({"Results","Comments","Products","id","stname1"})
public class StoreSpringController {
	
	
	ServletContext context;	
	
	@Autowired
	StoreService ss;
	
	@Autowired
	public void setContext(ServletContext context) {
		this.context = context;
	}

	
	@RequestMapping(value={"/InsertStoreServlet"},method = RequestMethod.POST)
	
	public String InsertStore(Model model,
			@RequestParam(value = "stname") String  stname , 
			@RequestParam(value = "sclass") String sclass,
			@RequestParam(value = "saddress") String saddress,
			@RequestParam(value = "stitd") String stitd,
			@RequestParam(value = "stitddt") String stitddt,
			@RequestParam(value = "tel") String tel			
			) {
		Integer id = null;
		Map<String,String> errorMsg = new HashMap<String,String>();
//		request.setAttribute("error", errorMsg);
		model.addAttribute("errorMsg", errorMsg);
		
		if(ss.isDup(stname)) {
			errorMsg.put("stname","商店名稱重複請改名");
			return "Insert";
		}
		if(stitd.length()>49) {
			errorMsg.put("stitd","簡介字數過多請修改");
			return "Insert";
		}
		Integer testid = null;
		try {
			StoreBean sb = new StoreBean(id,stname, sclass, saddress, stitd,stitddt, tel);
//			session.setAttribute("sb", sb);
			model.addAttribute("sb", sb);
			ss.save(sb);
			testid = sb.getId();
			System.out.println("testID:"+testid);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(errorMsg.isEmpty()) {
			List<StoreBean> list =ss.getFullstore(testid);
			model.addAttribute("Results", list);
			return "detailStore";
		}else {
			return "Insert";
		}
//		return "detailStore";
	}
	
	@RequestMapping("/StoreBannerServlet")
	public String StoreBanner(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
//			HttpSession session,
			@RequestParam Integer id,
			@RequestParam String stname,
			@RequestParam String photourl,
			@RequestParam String bannerurl
			) throws IOException {
			id = Integer.parseInt(String.valueOf(request.getSession(true).getAttribute("id")));
			stname = (String) request.getSession(true).getAttribute("stname");
			photourl = null;
			bannerurl = (String) request.getSession(true).getAttribute("bannerurl");
			
			StoreBean sb = new StoreBean(id, stname, bannerurl, photourl);
			ss.bannerStore(sb);
			request.getSession().setAttribute("stname", stname);
			request.getSession().setAttribute("id", id);
			
			String url ="StoreGetFullstore";
			String newurl = response.encodeRedirectURL(url);
			response.sendRedirect(newurl);
				return null;
	}
	
	@RequestMapping(value = "/StoreDeleteServlet"
//			,method = Responses.ALL
			)
	public String DeleteStore(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam Integer id
			) throws IOException {
//		Map<String, String> errorMsg = new HashMap<String, String>();
		
		
		id =Integer.parseInt(request.getParameter("id"));
		
		
		StoreBean sb = new StoreBean(id);
		ss.deleteStore(sb);
		String url = request.getContextPath()+"/exDeleteStore.jsp";
		String newurl = response.encodeRedirectURL(url);
		response.sendRedirect(newurl);
		return null;
	}
	
	
	@GetMapping("/StoreGetClassstore")
	public String ClassStore( Model model,
//			HttpServletRequest request,
//			HttpServletResponse response,
			@RequestParam("sclass") String sclass
			) {



		List<StoreBean> list =ss.getClassstore(sclass);
		model.addAttribute("Results", list);
		return "SimpleStore"; 
	}
	
	
	@RequestMapping("/StoreGetFullstore")
	public String FullStore(Model model,
			HttpServletRequest request,
			HttpServletResponse response
//			@RequestParam Integer id
			) {
		if(request.getParameter("id")!=null) {			
			Integer id1 = Integer.parseInt(request.getParameter("id"));
			request.getSession(true).setAttribute("id", id1);
		}
			Integer id = (Integer) request.getSession(true).getAttribute("id");
			
			request.getSession(true).setAttribute("restname", request.getParameter("stname"));
			List<StoreBean> list =ss.getFullstore(id);
			model.addAttribute("Results", list);
		
			
			List<BoardBean> list2 = ss.getComment(id);
//			request.getSession(true).setAttribute("Comments", list2);
			model.addAttribute("Comments", list2);
			
			List<ProductInfoBean> listPIB = ss.getProductInfoBeans(id);
			request.getSession().setAttribute("Products", listPIB);
			model.addAttribute("Products", listPIB);
				return "detailStore";
	}
	
	
//	@GetMapping("/")
//	public String IndexStore(Model model) {
//		List<StoreBean> list_ADP = ss.getAdvertisementphotostore();
////		List<StoreBean> list_ADP_random = ss.getAdvertisementphotostore();
////		Collections.shuffle(list_ADP_random);     //轉移到Service
////		List<StoreBean> list_ADP = list_ADP_random.subList(0, 6);
//		model.addAttribute("ADP", list_ADP);
//		
//		List<StoreBean> list_AD = ss.getAdvertisementstore();
////		List<StoreBean> list_AD_randeom = ss.getAdvertisementstore();
////		Collections.shuffle(list_AD_randeom);
////		List<StoreBean> list_AD = list_AD_randeom.subList(0, 4);
//		model.addAttribute("AD", list_AD);
//		return "Index1";
//	}
	
	@RequestMapping("/StoreGetNamestore")
	public String GetNamestore(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam ("nsrch") String stname
			) {
		
//		String stname = request.getParameter("nsrch");

		List<StoreBean> list =ss.getNamestore(stname);
		model.addAttribute("Results", list);
		return "SimpleStore";
	}
	
	
	@RequestMapping("/StorePhotoServlet")
	public String StorePhoto(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
//			HttpSession session,
			@RequestParam Integer id,
			@RequestParam String stname,
			@RequestParam String photourl,
			@RequestParam String bannerurl
			) throws IOException {
			id = Integer.parseInt(String.valueOf(request.getSession(true).getAttribute("id")));
			stname = (String) request.getSession(true).getAttribute("stname");
			photourl = (String) request.getSession(true).getAttribute("photourl");
			bannerurl = null;
			
			StoreBean sb = new StoreBean(id, stname, bannerurl, photourl);
			ss.photoStore(sb);
			
				return "detailStore";
	}
	
	@RequestMapping("/StoreUpdateServlet")
	public String UpdateStore(Model model,
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam("stname") String stname,
			@RequestParam("sclass") String sclass,
			@RequestParam("saddress") String saddress,
			@RequestParam("stitd") String stitd,
			@RequestParam("tel") String tel,
			@RequestParam("stitddt") String stitddt,
			@RequestParam("id") Integer id
			) {
		Map<String, String> errorMsg = new HashMap<String, String>();
		
		if (stname == null || stname.trim().length() ==0) {
			errorMsg.put("stname","商店名稱不能為空白");
		}
		if (!errorMsg.isEmpty()) {
//			RequestDispatcher rd = request.getRequestDispatcher("Update.jsp");
//			rd.forward(request, response);
			return "Update";
		}
		if(stitd.length()>49) {
			errorMsg.put("stitd","簡介字數過多請修改");
//			RequestDispatcher rd = request.getRequestDispatcher("Update.jsp");
//			rd.forward(request, response);
			return "Update";
		}
		
		if(ss.isDup(stname) && !stname.equals((String)request.getSession(true).getAttribute("restname"))) {
			errorMsg.put("stname","商店名稱重複請改名");
//			RequestDispatcher rd = request.getRequestDispatcher("Update.jsp");
//			rd.forward(request, response);
			return "Update";
		}
		
		StoreBean sb = new StoreBean(id, stname, sclass, saddress, stitd, stitddt, tel);

		ss.updateStore(sb);
		model.addAttribute("id", id);
//		request.getSession().setAttribute("id", id);
		model.addAttribute("stname1", stname);
//		request.getSession().setAttribute("stname1", stname);
		
		return "exUpdate";
	}
	
	
}
