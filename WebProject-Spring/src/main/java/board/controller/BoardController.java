package board.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import board.service.BoardService;
import xun.model.BoardBean;
import xun.model.StoreBean;
import xun.service.StoreService;

@Controller
@SessionAttributes("boardBean")
public class BoardController {

	@Autowired // 自動注入參數到servicec或dao
	BoardService boardService;
	@Autowired
	StoreService storeService;

	// <1> 取得括弧裡面( "/") jsp傳回來的路徑
	@GetMapping("/pack") // <2> 取得參數值和名
	public String packboard(@RequestParam(value = "name") String name, @RequestParam(value = "comment") String comment,
			@RequestParam(value = "photo", required = false) String photo, @RequestParam(value = "star") Integer star,
			@RequestParam(value = "storeId") Integer storeId, Model model) {
		System.out.println("========================" + storeId);
		// <3> 方法下執行
		Date utilDate = new Date();
		Date date = utilDate;
		StoreBean storebean = storeService.get(storeId);
		BoardBean boardBean = new BoardBean(null, name, star, date, comment, photo, storebean);
		// <4> 放進model，一起帶到要倒出來的路徑
		// 括弧("取用的名子", 指定的東西 )
		model.addAttribute("boardBean", boardBean);

		// <5> 回傳到指定的路徑
		return "/orange/DisplayInsert";
	}

	@GetMapping("/insertboard")
	public String insertboard(Model model) {
		BoardBean boardBean = (BoardBean) model.getAttribute("boardBean");
		if (boardService.insertBoard(boardBean) > 0)
			model.addAttribute("message", "success");
		else
			model.addAttribute("message", "fail");
		return "/orange/Thanks";
	}

	@GetMapping("/selectboard")
	public String selectboard(@RequestParam(value = "param") String param, Model model // 有model，前端${ .....}才可以取值
	) {
		// Bean取得Service的方法，裡面的參數值
		BoardBean boardBean = boardService.getBoardByName(param);
		// model取得add屬性(bean)
		System.out.println(boardBean);
		model.addAttribute("boardBean", boardBean);
		return "/orange/DisPlayComment";
	}

	@GetMapping("/show")
	public String Show(Model model) {
		return "/orange/ShowComment";
	}

	@GetMapping("/updateboard")
	public String updateboard(@RequestParam(value = "id") Integer id, @RequestParam(value = "name") String name,
			@RequestParam(value = "comment") String comment,
			@RequestParam(value = "photo", required = false) String photo, @RequestParam(value = "star") Integer star,
			@RequestParam(value = "storeId") Integer storeId, Model model) {
		Date utilDate = new Date();
		Date date = utilDate;

		StoreBean storebean = storeService.get(storeId);

		BoardBean boardBean = new BoardBean(id, name, star, date, comment, photo, storebean);
		if (boardService.updateBoard(boardBean) > 0)
			model.addAttribute("message", "success");
		return "/orange/Thanks";
	}

	@GetMapping("/deleteboard")
	public String deleteboard(@RequestParam(value = "update") Integer boardid, Model model) {
		System.out.println("!!!" + boardid);
		if (boardService.deleteBoard(boardid) > 0)
			model.addAttribute("message", "success");
		return "/orange/Thanks";
	}
}
