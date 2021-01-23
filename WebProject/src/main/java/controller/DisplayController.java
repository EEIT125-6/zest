package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.BookingBean;
import service.BookingService;
import webUser.model.WebUserData;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.StoreService;

@Controller
@SessionAttributes({"userFullData"})
public class DisplayController {
	
	@Autowired
	StoreService ss;
	
	@Autowired
	BookingService service;
	
	@GetMapping("/booking/DisplayController")
	public String display(Model model,@RequestParam(value="key") String bookingNo) {
		BookingBean bean = service.singleBooking(bookingNo);
		model.addAttribute("bean", bean);
		
		return "booking/updateResult";
		
	}
	
	@GetMapping("/booking/Display")
	public String displayDetail(Model model,@RequestParam(value="key") String bookingNo) {
		BookingBean bean = service.singleBooking(bookingNo);
		model.addAttribute("bean", bean);

		return "booking/queryResult";

	}
	
	@GetMapping("/booking/updateResult")
	public String fail() {
		return "booking/updateResult";
	}
	
	@GetMapping("/booking/Index1")
	public String home() {
		return "redirect:/";
	}
	
	@GetMapping("/booking/{restaurant}")
	public String second(Model model, @PathVariable("restaurant") String restaurant,RedirectAttributes ra) {
		System.out.println(restaurant);
		ra.addFlashAttribute("restaurant",restaurant);
		WebUserData user_id = (WebUserData) model.getAttribute("userFullData");
		if (user_id != null) {
			return "booking/bookingForm";
		}
		return "WebUserLogin";
	}
	
	@PostMapping("/booking/{restaurant}")
	public String secondd(@PathVariable("restaurant") String restaurant,RedirectAttributes ra) {
		System.out.println(restaurant);
		ra.addFlashAttribute("restaurant",restaurant);
		return "booking/bookingForm";
		
	}
	
	@GetMapping("/newBookingTime")
	public String bookingTimePage(Model model,@RequestParam(value = "id") Integer stid) {
		ProductInfoBean productInfoBean = new ProductInfoBean();
		StoreBean sb = ss.get(stid);
		productInfoBean.setProduct_shop(sb.getStname());
		model.addAttribute("stid", stid);
		model.addAttribute("productInfoBean", productInfoBean);
		return "newBookingTime";
		
	}
	
	@PostMapping("/newBookingTime")
	public String bookingTime(Model model
							,@RequestParam(value="stid") Integer stid
							,@RequestParam(value = "seating" , required = false) Integer seats) {
		int count=0;
		System.out.println(stid);
		System.out.println(seats);
		count = service.insertSeat(stid,seats);
		if (count==1) {
			System.out.println("新增訂位數成功");
		}
		StoreBean sb = ss.get(stid);
		Integer NewStoreId = sb.getId();
		String NewStoreName = sb.getStname();
		model.addAttribute("id", NewStoreId);
		model.addAttribute("stname", NewStoreName);
		return "redirect:/StoreGetFullstore";
		
	}
}
