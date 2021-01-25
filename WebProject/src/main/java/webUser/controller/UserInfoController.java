package webUser.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import util.GeneralEmailService;
import util.GeneralInputCheckService;
import webUser.model.WebUserData;
import webUser.service.WebUserService;

@Controller
@SessionAttributes({"registerEmail", "checkCode", "recoveryResult", "redirectPage", "userId"})
public class UserInfoController {
	
	/* WebUserData Service */
	@Autowired
	WebUserService wus;
	
	/* 執行註冊流程的相關檢查，並交由Ajax回傳 */
	@PostMapping(value="/webUser/controller/UserInfoController", produces="application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doRegisterCheck(
			@RequestParam(value="inputAccount", required=false, defaultValue="") String newAccount, 
			@RequestParam(value="inputPassword", required=false, defaultValue="") String password,
			@RequestParam(value="inputNickname", required=false, defaultValue="") String nickname,
			@RequestParam(value="inputEmail", required=false, defaultValue="") String email,
			@RequestParam(value="inputPhone", required=false, defaultValue="") String phone,
			@RequestParam(value="inputBirth", required=false, defaultValue="") String birth,
			@RequestParam(value="register") String mode,
			HttpServletRequest request,
			Model model) 
	{
		Map<String, String> map = new HashMap<>();
		String contextPath = request.getContextPath();
		
		/* 檢查帳號是否存在 */
		if (mode.equals("checkAccount")) {	
			/* 宣告欲回傳的參數 */
			Integer accountCheckResult = -1;
			String message = "";
			
			try {
				accountCheckResult = wus.checkAccountExist(newAccount);
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
			
			map.put("resultCode", accountCheckResult.toString());
			map.put("resultMessage", message);
			
		/* 檢查稱呼是否存在 */
		} else if (mode.equals("checkNickname")) {
			/* 宣告欲回傳的參數 */
			Integer nicknameCheckResult = -1;
			String message = "";
			
			try {
				nicknameCheckResult = wus.checkNicknameExist(nickname);
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
			
			map.put("resultCode", nicknameCheckResult.toString());
			map.put("resultMessage", message);
			
		/* 檢查Email是否存在 */
		} else if (mode.equals("checkEmail")) {
			/* 宣告欲回傳的參數 */
			Integer emailCheckResult = -1;
			String message = "";
			
			try {
				emailCheckResult = wus.checkEmailExist(email);
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
			
			map.put("resultCode", emailCheckResult.toString());
			map.put("resultMessage", message);
			
		/* 檢查電話是否存在 */
		} else if (mode.equals("checkPhone")) {
			/* 宣告欲回傳的參數 */
			Integer phoneCheckResult = -1;
			String message = "";
			
			try {
				phoneCheckResult = wus.checkPhoneExist(phone);
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
			
			map.put("resultCode", phoneCheckResult.toString());
			map.put("resultMessage", message);
			
		/* 執行寄送驗證碼 */
		} else if (mode.equals("sendCheckCode")) {
			/* 宣告欲回傳的參數 */
			Boolean sendResult = false;
			String message = "";
			String checkCode = GeneralEmailService.doCreateCheckCode();
			String registerEmail;
			
			try {
				sendResult = GeneralEmailService.doSendEmail(newAccount, email, checkCode, "submit", contextPath);
			} catch (Exception e) {
				message = e.getMessage();
			}
			
			if (sendResult) {		
				message = "驗證碼已寄出，請至填寫的信箱收信，將驗證碼貼上";
				sendResult = true;
				/* 將變數賦值 */
				registerEmail = email;
				/* 將產生的checkCode放入session中 */
				model.addAttribute("checkCode", checkCode);
				/* 將獲得驗證碼的email放入session中 */
				model.addAttribute("registerEmail", registerEmail);
			} else if (checkCode == null){
				sendResult = false;
			}
			
			map.put("resultCode", sendResult.toString());
			map.put("resultMessage", message);
			map.put("resultText", checkCode);
		/* 驗證帳號重設資訊 */
		} else if (mode.equals("recovery")) {
			/* 宣告欲回傳的參數 */
			Boolean sendResult = false;
			String recoveryUrl = "";
			String recoveryMessage = "";
			/* 可能取回的使用者資料 */
			WebUserData recoveryUserData = new WebUserData();
			
			/* 執行後端檢查 */
			recoveryMessage = doRecoveryInputCheck(newAccount, password, email, phone, birth);
			
			/* 驗證資料是否存在 */
			if (recoveryMessage.equals("")) {
				/* 日期轉型 */
				Date inputBirth = Date.valueOf(birth);
				
				/* 帶入必要資訊驗證 + 選填資訊進行驗證，並從DB取得使用者的必要資訊 */
				try {
					if (newAccount.equals("") && password.equals("")) {
						recoveryUserData = wus.checkRecoveryInfo(email, phone, inputBirth);
					} else if (!newAccount.equals("") && password.equals("")) {
						recoveryUserData = wus.checkRecoveryInfo(newAccount, email, phone, inputBirth);
					} else if (newAccount.equals("") && !password.equals("")) {
						recoveryUserData = wus.checkRecoveryInfoAnother(password, email, phone, inputBirth);
					} else {
						recoveryUserData = wus.checkRecoveryInfo(newAccount, password, email, phone, inputBirth);
					}
				} catch (SQLException sqlE) {
					recoveryMessage = sqlE.getMessage();
				}
			}
			
			if (recoveryMessage.equals("") && recoveryUserData != null) {
				/* 記錄當下時間 */
				LocalDate nowDate = LocalDate.now();
				LocalTime nowTime = LocalTime.now();
				
				String nowTimeStamp = nowDate.toString() + "_" + nowTime.toString();
				String checkCode = GeneralEmailService.doCreateCheckCode();
				String userId = recoveryUserData.getUserId();
				
				/* 產生驗證連結 */
				recoveryUrl = "http://127.0.0.1:8080" + request.getContextPath()
						+ "/recovery/RecoveryAccount?ts=" + nowTimeStamp 
						+ "&key=" + checkCode
						+ "&userId=" + userId;
				
				System.out.println("--------URL is: "+recoveryUrl);
				
				/* 寄送到指定email */
				try {
					sendResult = GeneralEmailService.doSendEmail(recoveryUserData.getAccount(), email, recoveryUrl, "forget", contextPath);
				} catch (Exception e) {
					recoveryMessage = e.getMessage();
				}
			} else if (recoveryMessage.equals("") && recoveryUserData == null) {
				recoveryMessage = "您似乎未在本服務註冊，或著您使用Google登入";
			}
			
			if (sendResult) {
				recoveryMessage = "重設連結已寄出，請至您填寫的信箱收信";
			}
			
			map.put("resultCode", sendResult.toString());
			map.put("resultMessage", recoveryMessage);
		} 		
		return map;
	}
	
	/* 處理驗證連結 */
	@GetMapping("/recovery/RecoveryAccount")
	public String doRecoveryUrlCheck(
			@RequestParam(value = "ts", required = false, defaultValue = "") String timeRecord,
			@RequestParam(value = "key", required = false, defaultValue = "") String checkCode,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
			Model model,
			HttpServletRequest request
			) 
	{
		/* 參數 */
		Boolean infoIsOk = true;
		String recoveryResult = "";
		String destinationUrl = "";
		
		/* 檢查使用者是否登入 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		if (userData == null) {
			infoIsOk = true;
		} else {
			recoveryResult = "請使用個人管理功能來變更密碼！";
			destinationUrl = "/WebUserMain";
			infoIsOk = false;
		}
		
		/* 檢查時戳 */
		if (timeRecord.equals("")) {
			recoveryResult = "驗證無效";
			infoIsOk = false;
		} else if (timeRecord.indexOf("_") == -1 && timeRecord.split("_").length != 2) {
			recoveryResult = "驗證無效";
			infoIsOk = false;
		} else {
			String createDate = timeRecord.split("_")[0];
			String createTime = timeRecord.split("_")[1];
			LocalDate inputDate;
			
			/* 轉型 */
			try {
				inputDate = LocalDate.parse(createDate);
				/* 驗證連結裡的日期只可能為當日或昨日才有效 */
				if (inputDate.equals(LocalDate.now()) || inputDate.plusDays(1L).equals(LocalDate.now())) {
					infoIsOk = true;
				} else {
					infoIsOk = false;
					recoveryResult = "驗證已無效";
				}
				
				/* 轉型 */
				try {
					LocalTime.parse(createTime);
				} catch (Exception e2) {
					infoIsOk = false;
					recoveryResult = e2.getMessage();
				}
				
				/* 假設有效期為1個小時 */
				/* 唯有晚間11時會遇到跨日問題 */
				if (infoIsOk 
						&& !createTime.split(":")[0].equals("23") 
						&& inputDate.equals(LocalDate.now())) {
					if (LocalTime.parse(createTime).plus(1, ChronoUnit.HOURS).isAfter(LocalTime.now())) {
						infoIsOk = true;
					} else {
						infoIsOk = false;
						recoveryResult = "驗證已無效";
					}
				} else if (infoIsOk 
						&& createTime.split(":")[0].equals("23") 
						&& inputDate.plusDays(1L).equals(LocalDate.now())) {
					if (LocalTime.parse(createTime).plus(1, ChronoUnit.HOURS).isAfter(LocalTime.now())) {
						infoIsOk = true;
					} else {
						infoIsOk = false;
						recoveryResult = "驗證已無效";
					}
				}
			} catch (Exception e1) {
				infoIsOk = false;
				recoveryResult = e1.getMessage();
			}
		}
		
		/* 檢查隨機碼 */
		if (infoIsOk) {
			String resultTmp = GeneralInputCheckService.doCheckCheckCode(checkCode);
			infoIsOk = Boolean.valueOf(resultTmp.split(",")[1]); 
		}
		/* 檢查userId */
		if (infoIsOk) {
			String resultTmp = GeneralInputCheckService.doBasicCheckUserId(userId);
			infoIsOk = Boolean.valueOf(resultTmp.split(",")[1]); 
			if (infoIsOk) {
				/* 驗證有效性 */
				Integer checkIdResult1 = 0;
				Integer checkIdResult2 = 0;
				
				try {
					checkIdResult1 = wus.checkUserIdExist(userId);
					checkIdResult2 = wus.checkUserIdQuit(userId);
				} catch (SQLException sqlE) {
					infoIsOk = false;
					recoveryResult = sqlE.getMessage();
				}
				if (checkIdResult1 == 1 && checkIdResult2 == 1) {
					infoIsOk = true;
				} else if (checkIdResult1 == 1 && checkIdResult2 == 0){
					infoIsOk = false;
					recoveryResult = "驗證失敗，該帳號已停用或尚未啟用";
				} else if (checkIdResult1 == 0) {
					infoIsOk = false;
					recoveryResult = "驗證失敗，無效的帳號";
				}
			}
		}
		
		/* 最終結果 */
		if (recoveryResult.equals("") && infoIsOk) {
			destinationUrl = "/recovery/WebUserResetPassword";
			/* 將物件userId以"userId"的名稱放入Attribute中 */
			model.addAttribute("userId", userId);
		} else if (userData != null){
			destinationUrl = "/WebUserLogin";
		} else {
			destinationUrl = "";
		}
		
		/* 將物件recoveryResult以"recoveryResult"的名稱放入Attribute中 */
		model.addAttribute("recoveryResult", recoveryResult);
		/* 將物件destinationUrl以"destinationUrl"的名稱放入Attribute中 */
		model.addAttribute("redirectPage", destinationUrl);
		return "redirect:/recovery/WebUserCheckRecoveryResult";
	}
	
	/* 前往檢查結果畫面 */
	@GetMapping("/recovery/WebUserCheckRecoveryResult")
	public String doGoRecoveryCheckResult(Model model) {
		return "recovery/WebUserCheckRecoveryResult";
	}
	
	/* 檢查重設模式的輸入 */
	public String doRecoveryInputCheck(String account, String password, String email, String phone, String birth) {
		String checkResult = "";
		
		/* 檢查帳號 */
		String resultTmp = GeneralInputCheckService.doBasicCheckAccount(account, "recovery");
		checkResult = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
		if (checkResult.equals("")) {
			/* 檢查密碼 */
			resultTmp = GeneralInputCheckService.doCheckPassword(password, "recovery");
			checkResult = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
		}
		if (checkResult.equals("")) {
			/* 檢查Email */
			resultTmp = GeneralInputCheckService.doBasicCheckEmail(email);
			checkResult = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
		}
		if (checkResult.equals("")) {
			/* 檢查Phone */
			resultTmp = GeneralInputCheckService.doBasicCheckPhone(phone);
			checkResult = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
		}
		if (checkResult.equals("")) {
			/* 檢查生日 */
			resultTmp = GeneralInputCheckService.doCheckBirth(Date.valueOf(birth));
			checkResult = (resultTmp.split(",")[0].equals("?")) ? "": resultTmp.split(",")[0];
		}
		return checkResult;
	}
}
