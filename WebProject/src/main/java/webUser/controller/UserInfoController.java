package webUser.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
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
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.sun.mail.smtp.SMTPSendFailedException;

import webUser.model.WebUserData;
import webUser.service.WebUserService;

@Controller
@SessionAttributes({"registerEmail", "checkCode", "recoveryResult", "redirectPage", "userId"})
public class UserInfoController {
	/* ServletContext */
	@Autowired
	ServletContext context;
	
	/* WebUserData Service */
	@Autowired
	WebUserService wus;
	
	/* Web相關資訊 */
	/* 目前使用的IP */
	final static String ipAddress = "http://10.31.25.130";
	/* 目前使用的port */
	final static String ipPort = "8080";
	/* 網站專案名稱 */
	final static String projectName = "WebProject";
	
	/* 寄送Email相關資訊 */
	/* 寄件者使用的SMTP Mail Server，有單日發信上限 */
	final static String mailHost = "smtp.gmail.com";
	/* TLS用port，不啟用TLS則需參考Email服務商的說明 */
	final static Integer mailPort = 587;
	/* 寄件者email帳號 */
	final static String mailUser = "projectzesteeit1256@gmail.com";
	/* 寄件者密碼或應用程式密碼 */
	final static String mailPassword = "EEIT1256PZest";
	
	/* 執行註冊流程的相關檢查，並交由Ajax回傳 */
	@PostMapping(value="/webUser/controller/UserInfoController", produces="application/json; charset=UTF-8")
	public @ResponseBody Map<String, String> doRegisterCheck(
			@RequestParam(value="inputAccount", required=false, defaultValue="") String account, 
			@RequestParam(value="inputPassword", required=false, defaultValue="") String password,
			@RequestParam(value="inputNickname", required=false, defaultValue="") String nickname,
			@RequestParam(value="inputEmail", required=false, defaultValue="") String email,
			@RequestParam(value="inputPhone", required=false, defaultValue="") String phone,
			@RequestParam(value="inputBirth", required=false, defaultValue="") String birth,
			@RequestParam(value="register") String mode,
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
			String checkCode = doCreateCheckCode();
			String registerEmail;
			
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
			/* 宣告欲回傳的參數 */
			Boolean sendResult = false;
			String recoveryUrl = "";
			String recoveryMessage = "";
			/* 可能取回的使用者資料 */
			WebUserData recoveryUserData = new WebUserData();
			
			/* 執行後端檢查 */
			recoveryMessage = doRecoveryInputCheck(account, password, email, phone, birth);
			
			/* 驗證資料是否存在 */
			if (recoveryMessage.equals("")) {
				/* 日期轉型 */
				Date inputBirth = Date.valueOf(birth);
				
				/* 帶入必要資訊驗證 + 選填資訊進行驗證，並從DB取得使用者的必要資訊 */
				try {
					if (account.equals("") && password.equals("")) {
						recoveryUserData = wus.checkRecoveryInfo(email, phone, inputBirth);
					} else if (!account.equals("") && password.equals("")) {
						recoveryUserData = wus.checkRecoveryInfo(account, email, phone, inputBirth);
					} else if (account.equals("") && !password.equals("")) {
						recoveryUserData = wus.checkRecoveryInfoAnother(password, email, phone, inputBirth);
					} else {
						recoveryUserData = wus.checkRecoveryInfo(account, password, email, phone, inputBirth);
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
				String checkCode = doCreateCheckCode();
				String userId = recoveryUserData.getUserId();
				
				/* 產生驗證連結 */
				recoveryUrl = ipAddress + ":" + ipPort + "/" + projectName
						+ "/recovery/RecoveryAccount?ts=" + nowTimeStamp 
						+ "&key=" + checkCode
						+ "&userId=" + userId;
				
				/* 寄送到指定email */
				try {
					sendResult = doSendEmail(account, email, recoveryUrl, "forget");
				} catch (Exception e) {
					recoveryMessage = e.getMessage();
				}
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
			Model model
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
			if (checkCode.equals("")) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的驗證連結";
			} else if (checkCode.length() != 8) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的驗證連結";
			} else if (!checkCode.matches("[0-9a-zA-Z]{8}")) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的驗證連結";
			}
		}
		
		/* 檢查userId */
		if (infoIsOk) {
			if (userId.equals("")) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的帳號";
			} else if (userId.length() != 7) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的帳號";
			} else if (!userId.matches("[0-2]{1}[0-9]{6}")) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的帳號";
			} else {
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
					recoveryResult = "驗證失敗，該帳號已停用";
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
		
		/* 允許空白 */
		if (account.equals("") || account.length() == 0) {
			checkResult = "";
		} else if (account.length() < 8) {
			checkResult = "帳號長度不足";
		} else if (account.length() > 20) {
			checkResult = "帳號長度過長";
		} else if (account.substring(0,1).matches("[0-9]{1}")) {
			checkResult = "帳號不可以數字開頭";
		} else if (!account.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
			checkResult = "帳號不符合格式";
		} 
		
		if (checkResult.equals("")) {
			/* 允許空白 */
			if (password.equals("") || password.length() == 0) {
				checkResult = "";
			} else if (password.length() < 8) {
				checkResult = "密碼長度不足";
			} else if (password.length() > 20) {
				checkResult = "密碼長度過長";
			} else if (password.substring(0,1).matches("[0-9]{1}")) {
				checkResult = "密碼不可以數字開頭";
			} else if (!password.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
				checkResult = "密碼不符合格式";
			} 
		}
		
		if (checkResult.equals("")) {
			if (email.equals("") || email.length() == 0) {
				checkResult = "信箱資訊不可為空白";
			} else if(email.indexOf("@") == -1 || email.split("@").length > 2 || email.indexOf(" ") != -1) {
				checkResult = "信箱資訊格式錯誤";
			} 
		}
		
		if (checkResult.equals("")) {
			if (phone.equals("") || phone.length() == 0) {
				checkResult = "連絡電話不可為空白";
			} else if(phone.length() < 9 || phone.indexOf(" ") != -1) {
				checkResult = "連絡電話格式錯誤";
			} else if (!phone.matches("[0]{1}[2-9]{1}[0-9]{7,9}")) {
				checkResult = "連絡電話格式錯誤";
			} else if (phone.substring(0, 2).equals("09") && phone.length() != 10) {
				checkResult = "行動電話格式錯誤";
			} else if (!phone.substring(0, 2).equals("09") && phone.length() == 10) {
				checkResult = "室內電話格式錯誤";
			} 
		}
		
		if (checkResult.equals("")) {
			if (birth.equals("") || birth.length() == 0) {
				checkResult = "生日不可為空白";
			} else if (birth.length() > 10 || birth.length() < 8) {
				checkResult = "日期長度錯誤";
			} else {
				Date inputBirth = Date.valueOf(birth);
				if (inputBirth == Date.valueOf(LocalDate.now())) {
					checkResult = "生日異常";
				} else if (Date.valueOf(inputBirth.toString()).after(Date.valueOf(LocalDate.now()))) {
					checkResult = "生日異常";
				} 
			}
		}
		
		return checkResult;
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
	public static Boolean doSendEmail(String account, String email, String checkCode, String mode) 
			throws Exception {
		Boolean sendResult = false;
		
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
		} else if (mode.equals("personalQuit")) {
			mailContext = "親愛的 "
						+ account 
						+ " ！<br /><br />" 
						+ "您不久前執行了停用本服務的操作，我們感到遺憾！"
						+ "<br /><br />"
						+ "如果這個操作您不知情，請透過本網站提供的方法聯繫我方處理，謝謝！"
						+ "<br /><br /><a href=\"" + ipAddress + ":" + ipPort + "/" + projectName + "\">橙皮官方網站</a>";
		} else if (mode.equals("adminQuit")) {
			mailContext = "親愛的 "
						+ account 
						+ " ！<br /><br />" 
						+ "不久前您因故違反了執行本服務的相關條款，因此即日起您的帳號將暫時遭到停權！"
						+ "<br /><br />"
						+ "如果您對這個決定有任何不同的觀點想要申訴，請透過本網站提供的方法聯繫我方處理，謝謝！"
						+ "<br /><br /><a href=\"" + ipAddress + ":" + ipPort + "/" + projectName + "\">橙皮官方網站</a>";
		} else if (mode.equals("forget")) {
			mailContext = "親愛的 " + account + " ！<br /><br />" 
						+ "請按下方的連結以重設您的帳號資訊"
						+ "<br /><br /><a href=\"" + checkCode + "\">重設密碼連結請點我</a>"
						+ "<br /><br />本連結將定時失效，請盡速使用";
		} else if (mode.equals("adminActivate")) {
			mailContext = "親愛的 "
						+ account 
						+ " ！<br /><br />" 
						+ "不久前您申請了帳號恢復服務！目前已經處理完成。您即刻起便可以重新使用本網站的相關服務"
						+ "<br /><br />"
						+ "如果您有其他需要告知的事項，請透過本網站提供的方法聯繫我方處理，謝謝！"
						+ "<br /><br /><a href=\"" + ipAddress + ":" + ipPort + "/" + projectName + "\">橙皮官方網站</a>";
		} else if (mode.equals("adminReactive")) {
			mailContext = "親愛的 "
						+ account 
						+ " ！<br /><br />" 
						+ "不久前您申請的 店家/管理員 帳號已經由管理員審核完畢並啟用。您即刻起便可以重新使用本網站的相關服務"
						+ "<br /><br />"
						+ "如果您有其他需要告知的事項，請透過本網站提供的方法聯繫我方處理，謝謝！"
						+ "<br /><br /><a href=\"" + ipAddress + ":" + ipPort + "/" + projectName + "\">橙皮官方網站</a>";
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
			} else if (mode.equals("personalQuit")) {
				message.setSubject("您已經停用了您的橙皮帳號");
			} else if (mode.equals("adminQuit")) {
				message.setSubject("您的橙皮帳號已遭管理員停用");
			} else if (mode.equals("forget")) {
				message.setSubject("您的橙皮重設連結在此");
			} else if (mode.equals("adminActivate")) {
				message.setSubject("您的橙皮帳號已由管理員啟用");
			} else if (mode.equals("adminReactive")) {
				message.setSubject("您的橙皮帳號已由管理員重新啟用");
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
