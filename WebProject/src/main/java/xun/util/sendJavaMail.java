package xun.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class sendJavaMail {
	/* 寄送Email相關資訊 */
	/* 寄件者使用的SMTP Mail Server，有單日發信上限 */
	final static String mailHost = "smtp.gmail.com";
	/* TLS用port，不啟用TLS則需參考Email服務商的說明 */
	final static Integer mailPort = 587;
	/* 寄件者email帳號 */
	final static String mailUser = "projectzesteeit1256@gmail.com";
	/* 寄件者密碼或應用程式密碼 */
	final static String mailPassword = "EEIT1256PZest";
	
	public static void goSendMail(
			String targetMail
			,String subject
			,String mailContext
			) {
	     // Recipient's email ID needs to be mentioned.
	      String to = targetMail;

	      // Sender's email ID needs to be mentioned
	      String from = "projectzesteeit1256@gmail.com";

	      // Assuming you are sending email from localhost
//	      String host = "localhost";

	      // Get system properties
			Properties props = new Properties();
			/* SMTP設定 */
			props.put("mail.smtp.auth", "true");
			/* 啟用TLS */
			props.put("mail.smtp.starttls.enable", "true");
			/* 設定寄件者email */
			props.put("mail.smtp.host", mailHost);
			/* 設定寄件所需的port */
			props.put("mail.smtp.port", mailPort);

			
	      // Setup mail server
//	      properties.setProperty("mail.smtp.host", host);

	      // Get the default Session object.
	      Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(mailUser, mailPassword);
				}
			});

	      try{
	         // Create a default MimeMessage object.
	         MimeMessage message = new MimeMessage(session);
	         message.setContent(mailContext, "text/html; Charset=UTF-8");

	         // Set From: header field of the header.
	         message.setFrom(new InternetAddress(from));

	         // Set To: header field of the header.
	         message.addRecipient(Message.RecipientType.TO,
	                                  new InternetAddress(to));

	         // Set Subject: header field
	         message.setSubject(subject);

	         // Now set the actual message
	         message.setText(mailContext);

	         // Send message
	         Transport.send(message);
	         System.out.println("Sent message successfully....");
	      }catch (MessagingException mex) {
	         mex.printStackTrace();
	      }

	}
}
