package webUser.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import webUser.service.WebUserService;

@Controller
public class UserInfoController {
	/* WebUserData Service */
	@Autowired
	WebUserService wus;
	
	/* 執行註冊流程的相關檢查，並交由Ajax回傳 */
	@PostMapping(value="/webUser/controller/UserInfoController", produces="application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> ajaxRegisterCheck(
			@RequestParam(value="inputAccount", required=false, defaultValue="") String account, 
			@RequestParam(value="register", required=false, defaultValue="undo") String mode) 
	{
		Map<String, String> map = new HashMap<>();
		/* 宣告欲回傳的參數 */
		Integer accountCheckResult = -1;
		String message = "";
		
		try {
			accountCheckResult = wus.checkAccountExist(account);
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		
		map.put("resultCode", accountCheckResult.toString());
		map.put("resultMessage", message);
		return map;
	}
}
