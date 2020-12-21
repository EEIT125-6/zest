package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class Index {

	public String home() {
		return "Index1";
	}
	@GetMapping("booking/{restaurant}")
	public String second(@PathVariable("restaurant") String restaurant,RedirectAttributes ra) {
		System.out.println(restaurant);
		ra.addFlashAttribute("restaurant",restaurant);
		return "booking/bookingForm";
		
	}
}
