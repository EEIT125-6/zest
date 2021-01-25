package xun.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xun.service.TraceService;

@Controller
public class TraceCRUD_Controller {
	
	@Autowired
	TraceService ts;
	
	@GetMapping("/addTrace")
	public @ResponseBody void addTrace(
			Model model
			,@RequestParam Integer stId
			,@RequestParam Integer memberId
			) {
		
		ts.addTrace(memberId, stId);
		System.out.println("加入");
	}
	
	@GetMapping("/removeTrace")
	public @ResponseBody void remoceTrace(
			Model model
			,@RequestParam Integer stId
			,@RequestParam Integer memberId
			) {
		ts.removeTrace(memberId, stId);
		System.out.println("退出");
	}
	
}
