package controller;

import java.sql.SQLException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;
//import ecpay.payment.integration.domain.AioCheckOutWebATM;
import model.CartDetailBean;
import model.OrderDetailBean;
import xun.model.ProductInfoBean;
import webUser.model.WebUserData;
import service.CartService;

@Controller
@RequestMapping("/controller")
@SessionAttributes({ "userFullData", "cart", "order" })
public class ShoppingCartController {
	
	@Autowired
	CartService service;

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
			return myCartRedirector(model);
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
	public String myCartRedirector(Model model) {
		WebUserData wus = (WebUserData) model.getAttribute("userFullData");
		OrderDetailBean list = service.getOrderByUserId(wus);
		model.addAttribute("cart", list.getCDB());
		model.addAttribute("order", list);
		return "cart/cart"; // webappconfig中已導入 prefix suffix (.jsp)
	}

	@GetMapping(value = "/itemremove") // 移除個人購物車中選定項目
	public @ResponseBody List<CartDetailBean> itemRemover(@RequestParam(value = "id") Integer id, Model model) {
		OrderDetailBean orderAttribute = (OrderDetailBean) model.getAttribute("order");
		Set<CartDetailBean> list = orderAttribute.getCDB();

		for (CartDetailBean k : list) {
			if (k.getProduct().getProduct_id() == id) {
				service.delete(k);
			}
		}

		return null;
	}

	@PostMapping(value = "/checkout") // 導向至購物車結帳頁面
	@SuppressWarnings("unused")
	public String checkOuter(@RequestParam String purchaseInfo, @RequestParam String totalValue, Model model) {
		System.out.println("checkout function initialized");
		System.out.println("id=" + purchaseInfo);
		System.out.println("totalValue=" + totalValue);
		String[] allItemQuantity = purchaseInfo.split(",");
		Map<Integer, Integer> list = new HashMap<Integer, Integer>();
		for (String s : allItemQuantity) {
			s.split(":");
			list.put(Integer.parseInt(s.split(":")[0]), Integer.parseInt(s.split(":")[1]));
		}

		if (list == null) {
			return "cart/cart";
		} else {
			model.addAttribute("itemQuantity", list);
			model.addAttribute("itemTotalValue", totalValue);
			return "checkout/checkout";
		}
	}

	@GetMapping(value = "/clearCart")
	// 購物車完全清除
	public @ResponseBody List<CartDetailBean> cartClearer(Model model) {
		System.out.println("cartClearer initialized");
		OrderDetailBean orderAttribute = (OrderDetailBean) model.getAttribute("order");
		if (orderAttribute == null) { // 如果購物車為空，則不作動
			return null;
		} else {
			Set<CartDetailBean> myCart = orderAttribute.getCDB();
			service.deleteAll(myCart);
			model.addAttribute("order", myCart);
		}

		return null;

	}

	@GetMapping(value = "/itemadd")
	// 加入商品至購物車
	public @ResponseBody List<CartDetailBean> itemAdder(@RequestParam(value = "id") Integer id, Model model) {
		System.out.println("AAAAAAAAAAAAAAAAAA  itemAdder initialized");
		OrderDetailBean orderAttribute = (OrderDetailBean) model.getAttribute("order");
		ProductInfoBean productInfo = service.findProductInfoBeanById(id);
		if (model.getAttribute("order") == null) { // 如果沒有訂單物件，新建一個OrderDetailBean物件後再新建一個CartItemBean物件，並且將選定商品加入CartItemBean物件中資料再寫入OrderDetailBean中
			System.out.println(model.getAttribute("order"));
			OrderDetailBean odb = new OrderDetailBean((WebUserData) model.getAttribute("userFullData"));
			service.save(odb);
			CartDetailBean cdb = new CartDetailBean(productInfo, 1, odb);
			service.save(cdb);
			model.addAttribute("order", odb);
			System.out.println("cartList is=" + odb);
			return null;
		}

		// 如有找到OrderDetailBean物件則遍歷中搜尋是否有找到相同商品id
		// 找到就不動作並提示使用者商品已存在於購物車
		Set<CartDetailBean> list = orderAttribute.getCDB();
		if (list != null) {
			for (CartDetailBean p : list) {
				if (p.getProduct().getProduct_id() == id) {
					System.out.println("list is=" + list);
					return null;
				}
			}
		}
		// 如果OrderItemBean中沒有找到對應id商品，則向內加入商品id
		service.save(new CartDetailBean(productInfo, 1, orderAttribute));
		System.out.println("list is=" + list);
		return null;
	}

	@PostMapping("/findCurrentMemberData") // 結帳時(checkout.jsp)自動帶入會員資料用
	public @ResponseBody Map<String, String> CurrentMemberDataRedirect(Model model) {
		Map<String, String> remap = service.findCurrentMemberData(model);
		return remap;
	}

	@PostMapping("/creditCardCheckOut")
	public @ResponseBody String genAioCheckOutAll(Model model) {
		AllInOne all = new AllInOne("");
		AioCheckOutALL obj = new AioCheckOutALL();
//		String amount=(String)model.getAttribute("totalValue");
		System.out.println("amount="+(String)model.getAttribute("totalValue"));
		obj.setMerchantTradeNo("zol89rydhey2564tt895");
		obj.setMerchantTradeDate("2021/01/24 22:40:18");
		obj.setTotalAmount("350");
		obj.setTradeDesc("ZEST CheckOut");
		obj.setItemName("myFood");
		obj.setReturnURL("http://211.23.128.214:5000");
		obj.setNeedExtraPaidInfo("N");
		String form = all.aioCheckOut(obj, null);
		return form;
	}

	public String getTime() {
		Calendar currentTime = Calendar.getInstance();

		StringBuffer sb = new StringBuffer();

		sb.append(currentTime.get(Calendar.YEAR)).append(" 年 ");

		sb.append(currentTime.get(Calendar.MONTH) + 1).append(" 月 "); // 沒有補0的月份

		sb.append(currentTime.get(Calendar.DAY_OF_MONTH)).append(" 日 ");

		sb.append(currentTime.get(Calendar.HOUR_OF_DAY)).append(" 時 ");

		sb.append(currentTime.get(Calendar.MINUTE)).append(" 分 ");

		return sb.toString();
	}
}