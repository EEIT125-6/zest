package util;

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

import com.sun.mail.smtp.SMTPSendFailedException;

public class GeneralEmailService {
	
	/* 寄送Email相關資訊 */
	/* 寄件者使用的SMTP Mail Server，有單日發信上限 */
	final static String mailHost = "smtp.gmail.com";
	/* TLS用port，不啟用TLS則需參考Email服務商的說明 */
	final static Integer mailPort = 587;
	/* 寄件者email帳號 */
	final static String mailUser = "projectzesteeit1256@gmail.com";
	/* 寄件者密碼或應用程式密碼 */
	final static String mailPassword = "EE12IT56PZest";
	
	/* 產生寄送註冊所需的驗證碼(大寫英文+數字的組合共8位) */
	public static String doCreateCheckCode() {
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
	
	/* 寄送Email
	 * 一次僅能寄送單一目標
	 * account->使用者帳號，用在email內文中
	 * email->寄送的目標email地址
	 * checkCode->此項有值時為：
	 * 1.註冊或修改資料時的驗證碼
	 * 2.聲請重設帳號(忘記密碼)時產生的驗證連結部分網址
	 * mode->此工具類別正執行的模式(影響內文、標題以及所需的參數)
	 * contextPath->本系統運行的根路徑(產生連結時需要) */
	public static Boolean doSendEmail(String account, String email, String checkCode, String mode, String contextPath) 
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
						+ "<br /><br /><a href=\"" + contextPath + "\">橙皮官方網站</a>";
		} else if (mode.equals("adminQuit")) {
			mailContext = "親愛的 "
						+ account 
						+ " ！<br /><br />" 
						+ "不久前您因故違反了執行本服務的相關條款，因此即日起您的帳號將暫時遭到停權！"
						+ "<br /><br />"
						+ "如果您對這個決定有任何不同的觀點想要申訴，請透過本網站提供的方法聯繫我方處理，謝謝！"
						+ "<br /><br /><a href=\"" + contextPath + "\">橙皮官方網站</a>";
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
						+ "<br /><br /><a href=\"" + contextPath + "\">橙皮官方網站</a>";
		} else if (mode.equals("adminReActive")) {
			mailContext = "親愛的 "
						+ account 
						+ " ！<br /><br />" 
						+ "不久前您申請的 店家/管理員 帳號已經由管理員審核完畢並啟用。您即刻起便可以重新使用本網站的相關服務"
						+ "<br /><br />"
						+ "如果您有其他需要告知的事項，請透過本網站提供的方法聯繫我方處理，謝謝！"
						+ "<br /><br /><a href=\"" + contextPath + "\">橙皮官方網站</a>";
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
			} else if (mode.equals("adminReActive")) {
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
