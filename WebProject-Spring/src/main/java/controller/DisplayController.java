package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.BookingBean;
import service.BookingService;

@Controller
@RequestMapping("/booking")
public class DisplayController {
	
	@Autowired
	BookingService service;
	
	@GetMapping("/DisplayController")
	public String display(Model model,@RequestParam(value="key") String bookingNo) {
		BookingBean bean = service.singleBooking(bookingNo);
		model.addAttribute("bean", bean);
		
		return "booking/updateResult";
		
	}
}
