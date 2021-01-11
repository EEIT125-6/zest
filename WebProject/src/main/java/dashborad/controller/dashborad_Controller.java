package dashborad.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class dashborad_Controller {

	@GetMapping("/dashborad_order")
	public String dashborad_order(
	
			) {
		return "dashborad-orderAnalysis";
	}
	
	@GetMapping("/dashborad_book")
	public String dashborad_book(
	
			) {
		return "dashborad-bookAnalysis";
	}
	
	@GetMapping("/dashborad_comment")
	public String dashborad_comment(
	
			) {
		return "dashborad-commentAnalysis";
	}
	
	@GetMapping("/dashborad_user")
	public String dashborad_user(
	
			) {
		return "dashborad-userAnalysis";
	}
	
}
