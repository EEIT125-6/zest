package student.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("studentBean")
public class StudentController {
	
	@PostMapping("/insertstudent")
	public String insert(){
	
		return"/orange/Student2";
		
	}
}
