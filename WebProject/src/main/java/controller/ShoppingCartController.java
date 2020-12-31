package controller;

import java.sql.SQLException;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import model.CartItemBean;
import xun.model.ProductInfoBean;
import webUser.model.WebUserData;
import service.CartService;


@Controller
@RequestMapping("/controller")
@SessionAttributes({ "userFullData" })
public class ShoppingCartController {

	@Autowired
	CartService service;;
	SessionFactory session;

	@GetMapping("/checkMemberStatus")// 使用者按下購物車立刻在session中檢查會員身分 
	public String checkMemberStatus(Model model) {
		WebUserData usdt = (WebUserData) model.getAttribute("userFullData");
		String inputAccount = (usdt == null) ? "" : usdt.getUserId();
		Integer checkMemberStatusResult = 0;
		try {
			checkMemberStatusResult = service.checkAccountExist(inputAccount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (checkMemberStatusResult == 1) {
			return myCartRedirector();
		} else if (checkMemberStatusResult == 0) {
			return "/webUser/WebUserLogin";
		} else {
			return "Index1";
		}
	}

	@GetMapping(value = "/mallRedirector") // 跳轉至購物商城 V
	public String mallRedirector(Model model) {
		System.out.println("Model="+model);
		List<ProductInfoBean> list = service.getProductList(); // 呼叫service層執行業務邏輯運算
		model.addAttribute("product", list); // 將回傳結果加入attribute中
		return "product/mall"; // webappconfig中已導入 prefix suffix (.jsp)
	}

	@GetMapping(value = "/myCartRedirector") // 跳轉至個人購物車 V
	public String myCartRedirector() {		
		return "cart/cart"; // webappconfig中已導入 prefix suffix (.jsp)
	}

	
	@GetMapping(value = "/itemremove") // 移除個人購物車中選定項目
	@SuppressWarnings("unchecked")
	public String itemRemover(@RequestParam (value="id") String id, HttpSession session) {
		System.out.println(id);
		List<ProductInfoBean> list = (List<ProductInfoBean>) session.getAttribute("products");
		int index = isExisting(id, session);
		if(index>=0) {
			list.remove(index);
			
		}else {
			return "cart/cart";
		}
		session.setAttribute("products", list);
		return "cart/cart";
	}

	
	@GetMapping(value = "/itemadd") // 加入選定項目進入購物車
	@SuppressWarnings("unchecked")
	public String itemAdder(@RequestParam (value = "id") String id, HttpSession session) {
		System.out.println("id="+id);
		if (session.getAttribute("products") == null) {
			List<ProductInfoBean> list = new ArrayList<ProductInfoBean>();
			System.out.println("HelloWorld");
			list.add(this.service.find(id).get(0)); // 尋找商城內特定商品方法
			System.out.println("this.service.find(id)="+this.service.find(id));
			session.setAttribute("products", list);
		} else {
			List<ProductInfoBean> list = (List<ProductInfoBean>) session.getAttribute("products");
			System.out.println(session.getAttribute("products"));
			for(ProductInfoBean p : list) {
				if(p.getProduct_id() == Integer.parseInt(id)) {
					return "cart/cart";
				}
			}
			list.add(this.service.find(id).get(0));
			System.out.println("AAAAAAAAAAAAAAAAAAAAA" + list);
			session.setAttribute("products", list);
		}
		return "cart/cart";

	}

	@SuppressWarnings("unchecked") // 檢查選擇商品是否存在於購物車內
	private static Integer isExisting(String inputid, HttpSession session) { 
		List<ProductInfoBean> list = (List<ProductInfoBean>) session.getAttribute("products");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getProduct_id() == Integer.parseInt(inputid)) {
				return i;
			}
		}
		return -1;
	}

}
