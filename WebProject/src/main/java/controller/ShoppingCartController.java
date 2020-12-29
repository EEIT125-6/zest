package controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import model.CartItemBean;
import xun.model.ProductInfoBean;
import webUser.model.WebUserData;
import service.CartService;
import javax.swing.JOptionPane;

@Controller
@RequestMapping("/controller")
@SessionAttributes({ "userFullData" })
public class ShoppingCartController {

	@Autowired
	CartService service;;
	SessionFactory session;

	@GetMapping("/checkMemberStatus")
//	@ModelAttribute("/checkMemberStatus") // 使用者按下購物車立刻在session中檢查會員身分
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
			return myCartRedirector(model);
		} else if (checkMemberStatusResult == 0) {
//			JOptionPane.showInputDialog(null,"請登入後才能開始購物喔", JOptionPane.WARNING_MESSAGE);
			return "/webUser/WebUserLogin";
		} else {
//			JOptionPane.showInputDialog(null,"系統錯誤，請重新再操作一次喔", JOptionPane.ERROR_MESSAGE);
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

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/myCartRedirector") // 跳轉至個人購物車 V
	public String myCartRedirector(Model model) {
		System.out.println("Model="+model);
		WebUserData usdt = (WebUserData) model.getAttribute("userFullData");
		System.out.println("======checkpoint1======");
		String inputId = usdt.getUserId();
		System.out.println("inputId="+inputId);
		System.out.println("======checkpoint2======");
		List<CartItemBean> list = (List<CartItemBean>) service.getCartByUser(inputId);
		System.out.println("======checkpoint3======");
		model.addAttribute("myproduct", list);
		System.out.println("======checkpoint4======");
		return "cart/cart"; // webappconfig中已導入 prefix suffix (.jsp)
	}

	
	@GetMapping(value = "/itemremove") // 移除個人購物車中選定項目
	@SuppressWarnings("unchecked")
	public String itemRemover(@PathVariable(value = "id") int id, HttpSession session) {
		List<CartItemBean> list = (List<CartItemBean>) session.getAttribute("cart");
		int index = isExisting(id, session);
		list.remove(index);
		session.setAttribute("cart", list);
		return "cart/cart";
	}

	
	@GetMapping(value = "/itemadd") // 加入選定項目進入購物車
	@SuppressWarnings("unchecked")
	public String itemAdder(@PathVariable(value = "id") int id, HttpSession session) {
		if (session.getAttribute("cart") == null) {
			List<CartItemBean> list = new ArrayList<CartItemBean>();
			list.add(new CartItemBean(this.service.find(id), 1)); // 稍後定義尋找購物車內特定商品方法
			session.setAttribute("cart", list);
		} else {
			List<CartItemBean> list = (List<CartItemBean>) session.getAttribute("cart");
			list.add(new CartItemBean(this.service.find(id), 1));
			session.setAttribute("cart", list);
		}
		return "product/mall";

	}

	@SuppressWarnings("unchecked") // 檢查選擇商品是否存在於購物車內
	private static int isExisting(Integer inputid, HttpSession session) { 
		List<CartItemBean> list = (List<CartItemBean>) session.getAttribute("cart");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getProduct_id() == inputid) {
				return i;
			}
		}
		return -1;
	}

}
