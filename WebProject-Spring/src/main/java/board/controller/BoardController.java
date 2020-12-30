package board.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import board.service.BoardService;
import xun.model.BoardBean;
import xun.model.StoreBean;
import xun.service.StoreService;

@Controller
@SessionAttributes("boardBean")
public class BoardController {
	@Autowired
	private HttpServletRequest request;
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

	@PostMapping("/insertboard")
	public @ResponseBody Map<String,Object> insertboard(@RequestParam(value = "name") String name, @RequestParam(value = "comment") String comment,
			@RequestParam(value = "photo", required = false) String photo, @RequestParam(value = "star") Integer star,
			@RequestParam(value = "storeId") Integer storeId,Model model) {
		Map<String,Object> map = new HashMap<String,Object>();
		StoreBean storebean = storeService.get(storeId);
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MILLISECOND, 0);
		BoardBean boardBean = new BoardBean(null, name, star, c.getTime(), comment, photo, storebean);
		if (boardService.insertBoard(boardBean) > 0) {
			map.put("message", "success");
			map.put("boardBean", boardBean);
		}else {
			map.put("message", "fail");
		}
		return map;
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

	@PostMapping("/upload")
	public String doUpload(@RequestParam("uploadFile") MultipartFile uploadfiles) throws IOException {
		
		 // 檔案存放服務端的位置
   	 String UPLOADED_FOLDER = request.getServletContext().getRealPath("/upload/");
        File dir = new File(UPLOADED_FOLDER + File.separator + "tmpFiles");
        if (!dir.exists())
            dir.mkdirs();
        // 寫檔案到伺服器
        File serverFile = new File(dir.getAbsolutePath() + File.separator + uploadfiles.getOriginalFilename());
        uploadfiles.transferTo(serverFile);

		return "/orange/Thanks";
	}
}
