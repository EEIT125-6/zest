package webUser.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sun.mail.smtp.SMTPSendFailedException;

import webUser.service.WebUserService;

@Controller
@SessionAttributes({"registerEmail", "checkCode"})
public class UserInfoController {
	/* WebUserData Service */
	@Autowired
	WebUserService wus;
	
	/* 執行註冊流程的相關檢查，並交由Ajax回傳 */
	@PostMapping(value="/webUser/controller/UserInfoController", produces="application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doRegisterCheck(
			@RequestParam(value="inputAccount", required=false, defaultValue="") String account, 
			@RequestParam(value="inputPassword", required=false, defaultValue="") String password,
			@RequestParam(value="inputNickname", required=false, defaultValue="") String nickname,
			@RequestParam(value="inputEmail", required=false, defaultValue="") String email,
			@RequestParam(value="inputPhone", required=false, defaultValue="") String phone,
			@RequestParam(value="inputBirth", required=false, defaultValue="1800-01-01") Date birth,
			@RequestParam(value="register", required=false, defaultValue="undo") String mode,
			Model model) 
	{
		Map<String, String> map = new HashMap<>();
		/* 檢查帳號是否存在 */
		if (mode.equals("checkAccount")) {	
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
			String checkCode = (String) model.getAttribute("checkCode");
			String registerEmail = (String) model.getAttribute("registerEmail");
			
			checkCode = doCreateCheckCode();
			try {
				sendResult = doSendEmail(account, email, checkCode, "submit");
			} catch (Exception e) {
				message = e.getMessage();
			}
			if (sendResult) {		
				message = "驗證碼已寄出，請至您填寫的信箱收信，並將驗證碼複製貼上至指定欄位";
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
			
		}
		
		return map;
	}
	
	/* 執行重設流程的相關檢查，並交由Ajax回傳 */
	public @ResponseBody Map<String, String> doRecoveryCheck() {
		return null;
	}
	
	/* 產生寄送註冊所需的驗證碼 */
	public String doCreateCheckCode() {
		String[] leterSpace = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", 
				"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", 
				"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		
		StringBuilder sb = new StringBuilder();
		sb.append(leterSpace[(int)(Math.random()*(10+26))]);
		sb.append(leterSpace[(int)(Math.random()*(10+26))]);
		sb.append(leterSpace[(int)(Math.random()*(10+26))]);
		sb.append(leterSpace[(int)(Math.random()*(10+26))]);
		sb.append(leterSpace[(int)(Math.random()*(10+26))]);
		sb.append(leterSpace[(int)(Math.random()*(10+26))]);
		sb.append(leterSpace[(int)(Math.random()*(10+26))]);
		sb.append(leterSpace[(int)(Math.random()*(10+26))]);
		
		return sb.toString();
	}
	
	/* 寄送Email */
	public Boolean doSendEmail(String account, String email, String checkCode, String mode) 
			throws Exception {
		Boolean sendResult = false;
		/* 寄件者使用的SMTP Mail Server，有單日發信上限 */
		final String mailHost = "smtp.gmail.com";
		/* TLS用port，不啟用TLS則需參考Email服務商的說明 */
		final Integer mailPort = 587;
		/* 寄件者email帳號 */
		final String mailUser = "@gmail.com";
		/* 寄件者密碼或應用程式密碼 */
		final String mailPassword = "";
		/* 收件者email帳號 */
		String mailObj = email;
		/* email內文 */
		String mailContext = "";
		if (mode.equals("submit")) {
			mailContext = "親愛的 "
								+ account 
								+ " ！<br /><br />" 
								+ "您即將完成本服務的註冊流程，請複製下方的驗證碼以完成帳戶的啟用"
								+ "<br /><br />" 
								+ checkCode;
		} else if (mode.equals("forget")) {
			mailContext = "親愛的 " + account + " ！<br /><br />" 
					+ "請按下方的連結以重設您的帳號資訊"
					+ "<br /><br /><a href=" + checkCode + "></a>本連結將定時失效";
		}
		
		Properties props = new Properties();
		/* SMTP設定 */
		props.put("mail.smtp.auth", "true");
		/* 啟用TLS */
		props.put("mail.smtp.starttls.enable", "true");
		/* 設定寄件者email */
		props.put("mail.smtp.host", mailHost);
		/* 設定寄件所需的port */
		props.put("mail.smtp.port", mailPort);
		
		Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailUser, mailPassword);
			}
		});
		
		try {
			Message message = new MimeMessage(mailSession);
			message.setRecipients(
					Message.RecipientType.TO
					, InternetAddress.parse(mailObj));
			
			/* 設定email主旨 */
			if (mode.equals("submit")) {
				message.setSubject("您的橙皮驗證碼在此");
			} else if (mode.equals("forget")) {
				message.setSubject("您的橙皮重設連結在此");
			}
			/* 設定email內容與編碼 */
			message.setContent(mailContext, "text/html; Charset=UTF-8");
			
			/* 正式送出 */
			Transport.send(message);
			/* 測試用訊息 */
			System.out.println("信件已成功寄出給："+mailObj);
			sendResult = true;
		} catch (AddressException ae) {
			/* email地址例外 */
			System.out.println("信件無法寄出！錯誤資訊為："+ae.getMessage());
			throw new Exception("信件無法寄出！錯誤資訊為："+ae.getMessage());
		} catch (NoSuchProviderException nspe) {
			System.out.println("信件無法寄出！錯誤資訊為："+nspe.getMessage());
			throw new Exception("信件無法寄出！錯誤資訊為："+nspe.getMessage());
		} catch(SMTPSendFailedException smtpsfe) {
			/* 應對免費Mail Server單日發送到達500次時會出現的錯誤 */
			System.out.println("信件無法寄出！錯誤資訊為："+smtpsfe.getMessage());
			throw new Exception("信件無法寄出！錯誤資訊為："+smtpsfe.getMessage());
		} catch (MessagingException me) {
			System.out.println("信件無法寄出！錯誤資訊為："+me.getMessage());
			throw new Exception("信件無法寄出！錯誤資訊為："+me.getMessage());
		} 
		
		return sendResult;
	}
}
