package controller;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import xun.model.ProductInfoBean;
import webUser.model.WebUserData;
import service.CartService;

@Controller
@RequestMapping("/controller")
@SessionAttributes({ "userFullData","cart" })
public class ShoppingCartController {

	@Autowired
	CartService service;;
	SessionFactory session;

	@GetMapping("/checkMemberStatus") // 使用者按下購物車立刻在session中檢查會員身分
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
			return "/WebUserLogin";
		} else {
			return "Index1";
		}
	}

	@GetMapping(value = "/mallRedirector") // 跳轉至購物商城
	public String mallRedirector(Model model) {
		List<ProductInfoBean> list = service.getProductList(); // 呼叫service層執行業務邏輯運算
		model.addAttribute("products", list); // 將回傳結果加入attribute中
		return "product/mall"; // webappconfig中已導入 prefix suffix (.jsp)
	}

	@GetMapping(value = "/myCartRedirector") // 跳轉至個人購物車
	public String myCartRedirector() {
//		model.getAttribute("products");
		System.out.println("Program is now here");
		return "cart/cart"; // webappconfig中已導入 prefix suffix (.jsp)
	}

	@GetMapping(value = "/itemremove") // 移除個人購物車中選定項目
	@SuppressWarnings("unchecked")
	public @ResponseBody List<ProductInfoBean> itemRemover(@RequestParam(value = "id") String id, Model model) {
		System.out.println("itemRemover Initialized");
		System.out.println("this is the incoming id=" + id);
		List<ProductInfoBean> list = (List<ProductInfoBean>) model.getAttribute("cart");
		System.out.println("List check = "+list);
		int index = isExisting(id, model);
		System.out.println("index check="+index);
		if (index >= 0) {
			System.out.println("list before removal is="+list);
			list.remove(index);
			System.out.println("list after removal is ="+list);
		}
		System.out.println(list);
		return list;
	}
	
	@GetMapping(value="/productQuantityKeeper") //商品數量中繼站
	public @ResponseBody String productQuantityKeeper(
			@RequestParam(value="quantities")String id
			,Model model
			) {
		System.out.println("productQuantityKeeper Initialized");
		String productQuantity = (String)model.getAttribute(id);
		return productQuantity;
	}

	@PostMapping(value = "/checkout") // 導向至購物車結帳頁面
	@SuppressWarnings("unused")
	public String checkOuter(
			@RequestParam String purchaseInfo,
			@RequestParam String totalValue,
			Model model) {
		System.out.println("checkout function initialized");
		System.out.println("id="+purchaseInfo);
		System.out.println("totalValue="+totalValue);
		String[] allItemQuantity = purchaseInfo.split(",");
		Map<Integer,Integer> list = new HashMap<Integer,Integer>();
		for(String s:allItemQuantity) {
			s.split(":");
			list.put(Integer.parseInt(s.split(":")[0]), Integer.parseInt(s.split(":")[1]));
		}
		
		if (list == null) {
			return "cart/cart";
		} else {
			model.addAttribute("itemQuantity",list);
			model.addAttribute("itemTotalValue",totalValue);
			return "checkout/checkout";
		}
	}
	
	@GetMapping(value="/clearCart")
	@SuppressWarnings("unchecked") //購物車清除
	public @ResponseBody List<ProductInfoBean> cartClearer(Model model){
		System.out.println("cartClearer initialized");
		List<ProductInfoBean>list = (List<ProductInfoBean>)model.getAttribute("cart");
		list.clear();
		model.addAttribute("cart",list);
		return list;
	}	

	@GetMapping(value = "/itemadd")
	@SuppressWarnings("unchecked") //加入商品至購物車
	public @ResponseBody List<ProductInfoBean> itemAdder(
			@RequestParam(value = "id") String id
			, Model model
			) {
		System.out.println("itemAdder initialized");
			if (model.getAttribute("cart") == null) {
			List<ProductInfoBean> list = new ArrayList<ProductInfoBean>();
			list.add(this.service.find(id).get(0));
			model.addAttribute("cart", list);
			System.out.println("list is="+list);
			return list;
		}
		List<ProductInfoBean> list = (List<ProductInfoBean>) model.getAttribute("cart");
		for (ProductInfoBean p : list) {
			if (p.getProduct_id() == Integer.parseInt(id)) {
				return list;
			}
		}
		list.add(this.service.find(id).get(0));
		model.addAttribute("cart", list);
		System.out.println("list is="+list);
		return list;
	}

//	@GetMapping(value = "/itemadd") // 加入選定項目進入購物車
//	@SuppressWarnings("unchecked")
//	public String itemAdder(@RequestParam (value = "id") String id, HttpSession session) {
//		if (session.getAttribute("products") == null) {
//			List<ProductInfoBean> list = new ArrayList<ProductInfoBean>();
//			list.add(this.service.find(id).get(0)); // 尋找商城內特定商品方法
//			session.setAttribute("products", list);
//		} else {
//			List<ProductInfoBean> list = (List<ProductInfoBean>) session.getAttribute("products");
//			for(ProductInfoBean p : list) {
//				if(p.getProduct_id() == Integer.parseInt(id)) {
//					return "cart/cart";
//				}
//			}
//			list.add(this.service.find(id).get(0));
//			session.setAttribute("products", list);
//		}
//		return "cart/cart";
//
//	}
	@PostMapping("/findCurrentMemberData") // 結帳時(checkout.jsp)自動帶入會員資料用
	public @ResponseBody Map<String, String> CurrentMemberDataRedirect(Model model) {
		Map<String, String> remap = service.findCurrentMemberData(model);
		return remap;
	}

//	@PostMapping("/findItemDetail") //結帳時(checkout.jsp)自動帶入結帳資料用
//	public @ResponseBody Map<String,String> CurrentCartItemDetail(Model model){
//		Map<String,String> remap = service.findItemDetail(model);
//		return remap;
//	}

	@SuppressWarnings("unchecked") // 檢查選擇商品是否存在於購物車內
	private static Integer isExisting(String inputid, Model model) {
		List<ProductInfoBean> list = (List<ProductInfoBean>) model.getAttribute("cart");
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getProduct_id() == Integer.parseInt(inputid)) {
				return i;
			}
		}
		return -1;
	}
}