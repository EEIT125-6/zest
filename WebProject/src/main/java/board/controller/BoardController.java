package board.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import board.service.BoardService;
import xun.model.BoardBean;
import xun.model.StoreBean;
import xun.service.StoreService;

@Controller
@SessionAttributes({"boardBean","userFullData"})
public class BoardController {
	
	@Autowired // 自動注入參數到servicec或dao
	BoardService boardService;
	@Autowired
	StoreService storeService;

	@PostMapping("/insertboard")
	public @ResponseBody Map<String,Object> insertboard(@RequestParam(value = "name") String name, @RequestParam(value = "comment") String comment,
			@RequestParam(value = "photo", required = false) String photo, @RequestParam(value = "star") Integer star,
			@RequestParam(value = "storeId") Integer storeId) {
		Map<String,Object> map = new HashMap<String,Object>();
		StoreBean storebean = storeService.get(storeId);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		BoardBean boardBean = new BoardBean(null, name, star, c.getTime(), comment, photo, 0, storebean);
		if (boardService.insertBoard(boardBean) > 0) {
			map.put("message", "success");
			map.put("boardBean", boardBean);
		}else {
			map.put("message", "fail");
		}
		return map;
	}

	@GetMapping("/selectboard")
	public String selectboard(@RequestParam(value = "boardid") int boardid, Model model // 有model，前端${ .....}才可以取值
	) {
		// Bean取得Service的方法，裡面的參數值
		BoardBean boardBean = boardService.getBoardById(boardid);
		// model取得add屬性(bean)
		System.out.println(boardBean);
		model.addAttribute("boardBean", boardBean);
		return "/orange/DisPlayComment";
	}

	@GetMapping("orange/ShowComment")
	public String Show() {
		return "/orange/ShowComment";
	}
	
	@GetMapping("/updateboard")
	public String updateboard(@RequestParam(value = "id") Integer id, @RequestParam(value = "name") String name,
			@RequestParam(value = "comment") String comment,
			@RequestParam(value = "photo", required = false) String photo, @RequestParam(value = "star") Integer star,
			@RequestParam(value = "storeId") Integer storeId,@RequestParam(value = "status",defaultValue = "0")Integer status, Model model) {
		Date utilDate = new Date();
		Date date = utilDate;

		StoreBean storebean = storeService.get(storeId);

		BoardBean boardBean = new BoardBean(id, name, star, date, comment, photo,status, storebean);
		System.out.println(boardBean.getStar());
		if (boardService.updateBoard(boardBean) > 0)
			model.addAttribute("message", "success");
		System.out.println("out");
		return "/orange/Thanks";
	}
	
	@GetMapping("/deleteboard")
	public String deleteboard(@RequestParam(value = "boardid") Integer boardid, Model model) {
		System.out.println("!!!" + boardid);
		BoardBean de =boardService.getBoardById(boardid);
		de.setStatus(1);
		if (boardService.updateBoard(de)> 0)
			model.addAttribute("message", "success");
		return "/orange/Thanks";
	}
}
