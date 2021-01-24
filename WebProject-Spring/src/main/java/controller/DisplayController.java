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

@Controller
@SessionAttributes({"userFullData"})
public class DisplayController {
	
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
}
