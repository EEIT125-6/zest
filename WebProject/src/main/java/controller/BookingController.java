package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.BookingBean;
import service.BookingService;
import webUser.model.WebUserData;

 
@Controller
@RequestMapping("/booking")
@SessionAttributes({"userFullData", "reg_booking"})
public class BookingController {
	/* 寄送Email相關資訊 */
	/* 寄件者使用的SMTP Mail Server，有單日發信上限 */
	final static String mailHost = "smtp.gmail.com";
	/* TLS用port，不啟用TLS則需參考Email服務商的說明 */
	final static Integer mailPort = 587;
	/* 寄件者email帳號 */
	final static String mailUser = "projectzesteeit1256@gmail.com";
	/* 寄件者密碼或應用程式密碼 */
	final static String mailPassword = "EEIT1256PZest";
	
	@Autowired
	BookingService service;
	
	//檢查訂位日期
	public boolean checkDate(String bookingdate) {

	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Date today = Date.valueOf(df.format(System.currentTimeMillis()));
	
	Date booking = Date.valueOf(bookingdate);
	System.out.println(today);
	System.out.println(bookingdate);
	System.out.println(booking.before(today));
	System.out.println(booking.equals(today));
		
	if (booking.before(today) || booking.equals(today)) {
		return false;
	}
		return true;
}
	//檢查取消日期是否在訂位日前一天
	public boolean checkCancelBooking(String bookingdate) {
	Date booking = Date.valueOf(bookingdate);
	LocalDate today = LocalDate.now().plusDays(1);     
	Date deadline = Date.valueOf(today);
    System.out.println("隔天:"+deadline);          
		
	if (booking.compareTo(deadline)<=0) {
		return false;
	}
	
	return true;
}
	
	//確認資料
	@PostMapping("/next")
	public String submit(Model model,
						@RequestParam(value="bookingdate") String bookingdate,
						@RequestParam(value="time") String time,
						@RequestParam(value="number") Integer number,
						@RequestParam(value="restaurant") String restaurant,
						@RequestParam(value="name") String name,
						@RequestParam(value="phone") String phone,
						@RequestParam(value="mail") String mail,
						@RequestParam(value="purpose") String purpose,
						@RequestParam(value="needs") String needs 
		) {
		System.out.println(bookingdate);
		System.out.println(time);
		System.out.println(number);
		System.out.println(restaurant);
		System.out.println(name);
		System.out.println(phone);
		System.out.println(mail);
		System.out.println(purpose);
		System.out.println(needs);
		
		 String bookingNo="";
		 String[]str = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V",
					"W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
			boolean isExist = false;
			do {
				bookingNo="";
				for(int i=1;i<=5;i++) {
					bookingNo += str[(int)(Math.random()*36)];
				}
				
				isExist=service.checkBooking(bookingNo);
			} while(isExist);
			System.out.println(bookingNo);
		
		if(checkDate(bookingdate)==true) {
			WebUserData user_id=(WebUserData)model.getAttribute("userFullData");
			BookingBean reg_booking =  new BookingBean(bookingNo,user_id, bookingdate, time, number, restaurant,name,phone,mail,purpose,needs,1);		  
			model.addAttribute("reg_booking",reg_booking);
			
			return "/booking/DisplayBooking";
		}
			
		return "forward:/booking/"+restaurant;
	}
	//增
	@PostMapping("/confirm")
	public String insert(Model model) {
		
		BookingBean bookingData = (BookingBean)model.getAttribute("reg_booking");
		
		String mailObj = bookingData.getMail();
		String mailContext = "";  
		if (service.insertBooking(bookingData)>0) {
			
			System.out.println("Get some SQL commands done!");

			
			String bookingNo=bookingData.getBookingNo();
			String restaurant=bookingData.getRestaurant(); 
			String bookingdate=bookingData.getBookingdate();
			String time=bookingData.getTime();
			Integer number=bookingData.getNumber();
			String name=bookingData.getName(); 
			String phone=bookingData.getPhone();
			String mail=bookingData.getMail();
			String purpose=bookingData.getPurpose(); 
			String needs=bookingData.getNeeds();
			
			mailContext = "<b>親愛的 "+ name 
					+ " 先生/女士 您好：<br /><br />" 
					+ "感謝您使用 zest橙皮 訂桌系統，<br />" 
					+ "請確認以下資訊，並閱讀注意事項，祝您有個美好的一天！</b>"+ "<br /><br />" 
					+"(此信件為系統寄出，請勿回覆)" 
					+ "<br /><br />" 
					+"<table  cellspacing='1' cellpadding='1' border='1' width='500px' style='border:8px #FFD382 groove;'>"
					+"<tr><td>訂單編號:</td><td>"+bookingNo
					+"</td></tr><tr><td>餐廳名稱:</td><td>"+restaurant
					+"</td></tr><tr><td>訂位日期:</td>"+bookingdate
					+"</td></tr><tr><td>時間:</td><td>"+time
					+"</td></tr><tr><td>人數:</td><td>"+number
					+"</td></tr><tr><td>姓名:</td><td>"+name
					+"</td></tr><tr><td>手機:</td><td>"+phone
					+"</td></tr><tr><td>e-mail:</td><td>"+mail
					+"</td></tr><tr><td>用餐目的:</td><td>"+purpose
					+"</td></tr><tr><td>特殊需求:</td><td>"+needs
					+"</td></tr></table>"
					+ "<br /><br />" 
					+ "=======================【重要】用餐日前一天＆當天取消，不退還線上預訂之訂金=======================<br /><br />" + 
					"Q. 訂位如何取消我的訂位?<br />" + 
					"A. 本網頁： 請「會員登入」→「查詢訂位」→「取消訂位」<br /><br />" + 
					"Q. 訂位無法到達是否一定要取消？<br />" + 
					"A. 為避免餐廳食材準備及營運上耗損，敬請您於用餐取消時限前(用餐前2天)務必取消訂位，可透過本網頁進行取消。<br /><br />" + 
					"【重要】同一訂位帳號達三次「no show」，訂位系統將自動將此會員帳號列為『拒絕受理訂位』，未來訂位僅能透過電話訂位或現場排隊候位。";
				
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
			message.setSubject("【zest 橙皮 餐廳訂位成功通知】");
			/* 設定email內容與編碼 */
			message.setContent(mailContext, "text/html; Charset=UTF-8");
			/* 正式送出 */
			Transport.send(message);
			/* 測試用訊息 */
			System.out.println("信件已成功寄出給："+mailObj);
		} catch (Exception e) {
			;
		}
		return "redirect:/booking/Thanks";
	}
	
	@GetMapping("/Thanks")
	public String thanks() {
		return "/booking/Thanks";
	}
	
	@GetMapping("/Page1")
	public String jump(Model model) {
		WebUserData user_id = (WebUserData) model.getAttribute("userFullData");
		if (user_id == null) {
			return "WebUserLogin";
		}
		return "/booking/showOrder";
	}

	//ajax查詢
	@SuppressWarnings("unchecked")
	@PostMapping(value ="/order", produces="application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> order(Model model) {
		WebUserData user_id = (WebUserData) model.getAttribute("userFullData");
		List<BookingBean> bean = service.findBooking(user_id.getUserId());

	    model.addAttribute("booking",bean);
	    List<BookingBean> data= (List<BookingBean>) model.getAttribute("booking");
	    Map<String, Object> map = new HashMap<>();
	    map.put("data", data);
		return map;
	}
	
	//查
	@PostMapping("/select")
	public String query(Model model) {
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		List<BookingBean> bean = service.findBooking(userData.getUserId());
	    model.addAttribute("booking",bean);
		return "booking/showOrder";
	}
	//刪
	@PostMapping(value="/confirmUpd",params = "cancel")
	public String cancel(Model model,
			@RequestParam(value="bookingNo") String bookingNo,
			@RequestParam(value="restaurant") String restaurant,
			@RequestParam(value="bookingdate") String bookingdate,
			@RequestParam(value="time") String time,
			@RequestParam(value="number") Integer number,
			@RequestParam(value="name") String name,
			@RequestParam(value="phone") String phone,
			@RequestParam(value="mail") String mail,
			@RequestParam(value="needs") String needs,
			@RequestParam(value="purpose") String purpose,
			RedirectAttributes ra
		) {
		if(checkCancelBooking(bookingdate)==true) {
			
			int count = 0;
			WebUserData user_id=(WebUserData)model.getAttribute("userFullData");
			BookingBean bean=new BookingBean(bookingNo,user_id,bookingdate,time,number,restaurant,name,phone,mail,purpose,needs,0);	
			count=service.updateBooking(bean);
			System.out.println(count);	
			if(count==1) {
				//↓↓↓ 訂位已取消寄e-mail ↓↓↓
				String mailObj = mail;
				String mailContext = "";  
				mailContext = "<h4>親愛的 "+ name 
						+ " 先生/女士 您好：</h4>" 
						+ "<h2>您下列的訂位已經取消!</h2>"
						+ "我們正在處理您的退款。<br />" 
						+"(此信件為系統寄出，請勿回覆)" 
						+ "<br /><br />" 
						+"<table  cellspacing='1' cellpadding='1' border='1' width='500px' style='border:8px #FFD382 orange;'>"
						+"<tr><td>訂單編號:</td><td>"+bookingNo
						+"</td></tr><tr><td>餐廳名稱:</td><td>"+restaurant
						+"</td></tr><tr><td>訂位日期:</td>"+bookingdate
						+"</td></tr><tr><td>時間:</td><td>"+time
						+"</td></tr><tr><td>人數:</td><td>"+number
						+"</td></tr><tr><td>姓名:</td><td>"+name
						+"</td></tr><tr><td>手機:</td><td>"+phone
						+"</td></tr></table>"
						+ "<br /><br />" 
						+ "=====================【接下來呢？】=======================<br /><br />" + 
						"<b>商品款項將會退回您的銀行帳戶。</b><br /><br />" + 
						"＊如果您是使用信用卡付款，我們會在 2 個工作天將款項退回您的發卡銀行。請聯絡發卡銀行查詢退款何時會存入您的戶口中。<br />" + 
						"＊如果您是透過銀行轉帳付款，我們會根據您提供的銀行帳戶資訊，在 8 個工作天將款項退回您的開戶銀行。<br />"+
						"如果您尚未提供帳戶資訊，請參閱右方的「資訊」。請聯絡發卡銀行查詢退款何時會存入您的戶口中。款項順利退回至您的銀行帳戶後，我們會傳送電子郵件通知您。<br /><br />" + 
						"若您對於退款仍有其他疑問，請聯絡您的銀行。<br /><br /><br />" + 
						"如有任何問題歡迎來電 ：(03)425-7387<br /><br />Zest橙皮 團隊";
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
					message.setSubject("【zest 橙皮 "+bookingdate+"的餐廳訂位已取消】");
					/* 設定email內容與編碼 */
					message.setContent(mailContext, "text/html; Charset=UTF-8");
					/* 正式送出 */
					Transport.send(message);
					/* 測試用訊息 */
					System.out.println("信件已成功寄出給："+mailObj);
				} catch (Exception e) {
					;
				}	
			
				return "redirect:/booking/cancelResult";
			}
			else {
				System.out.println("訂位取消未成功。。。");
			}
			return "redirect:/booking/Page1";
		}
		ra.addFlashAttribute("line","已過取消期限");
		return "redirect:/booking/Page1";
		
		
	}
	//改
	@PostMapping(value = "/confirmUpd", params = "confirmUpd")
	public String update(Model model, @RequestParam(value = "bookingNo") String bookingNo,
			@RequestParam(value = "restaurant") String restaurant,
			@RequestParam(value = "bookingdate") String bookingdate, @RequestParam(value = "time") String time,
			@RequestParam(value = "number") Integer number, @RequestParam(value = "name") String name,
			@RequestParam(value = "phone") String phone, @RequestParam(value = "mail") String mail,
			@RequestParam(value = "needs") String needs, @RequestParam(value = "purpose") String purpose) {

		if (checkCancelBooking(bookingdate) == true) {

			int count = 0;
			WebUserData user_id = (WebUserData) model.getAttribute("userFullData");
			BookingBean bean = new BookingBean(bookingNo, user_id, bookingdate, time, number, restaurant, name, phone,
					mail, purpose, needs, 1);
			count = service.updateBooking(bean);
			System.out.println(count);
			if (count == 1) {
				String mailObj = mail;
				String mailContext = "";

				mailContext = "<b>親愛的 " + name + " 先生/女士 您好：<br /><br />" + "感謝您使用 zest橙皮 訂桌系統，<br />"
						+ "↓↓↓訂位資料更新如下↓↓↓</b><br /><br />" + "(此信件為系統寄出，請勿回覆)" + "<br /><br />"
						+ "<table  cellspacing='1' cellpadding='1' border='1' width='500px' style='border:8px #FFD382 groove;'>"
						+ "<tr><td>訂單編號:</td><td>" + bookingNo + "</td></tr><tr><td>餐廳名稱:</td><td>" + restaurant
						+ "</td></tr><tr><td>訂位日期:</td>" + bookingdate + "</td></tr><tr><td>時間:</td><td>" + time
						+ "</td></tr><tr><td>人數:</td><td>" + number + "</td></tr><tr><td>姓名:</td><td>" + name
						+ "</td></tr><tr><td>手機:</td><td>" + phone + "</td></tr><tr><td>e-mail:</td><td>" + mail
						+ "</td></tr><tr><td>用餐目的:</td><td>" + purpose + "</td></tr><tr><td>特殊需求:</td><td>" + needs
						+ "</td></tr></table>" + "<br /><br />"
						+ "==============【重要】用餐日前一天＆當天取消，不退還線上預訂之訂金==================<br /><br />"
						+ "Q. 訂位如何取消我的訂位?<br />" + "A. 本網頁： 請「會員登入」→「查詢訂位」→「取消訂位」<br /><br />"
						+ "Q. 訂位無法到達是否一定要取消？<br />"
						+ "A. 為避免餐廳食材準備及營運上耗損，敬請您於用餐取消時限前(用餐前2天)務必取消訂位，可透過本網頁進行取消。<br /><br />"
						+ "【重要】同一訂位帳號達三次「no show」，訂位系統將自動將此會員帳號列為『拒絕受理訂位』，未來訂位僅能透過電話訂位或現場排隊候位。";

				Properties props = new Properties();
				/* SMTP設定 */
				props.put("mail.smtp.auth", "true");
				/* 啟用TLS */
				props.put("mail.smtp.starttls.enable", "true");
				/* 設定寄件者email */
				props.put("mail.smtp.host", mailHost);
				/* 設定寄件所需的port */
				props.put("mail.smtp.port", mailPort);

				Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(mailUser, mailPassword);
					}
				});
				try {
					Message message = new MimeMessage(mailSession);
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailObj));
					/* 設定email主旨 */
					message.setSubject("【zest 橙皮 訂位資料更新通知！】");
					/* 設定email內容與編碼 */
					message.setContent(mailContext, "text/html; Charset=UTF-8");
					/* 正式送出 */
					Transport.send(message);
					/* 測試用訊息 */
					System.out.println("信件已成功寄出給：" + mailObj);
				} catch (Exception e) {
					;
				}

				return "redirect:/booking/updateResult2";
			} else {
				System.out.println("訂位資料更新失敗！");
			}
			return "redirect:/booking/showOrder";
		}
		return "redirect:/booking/showOrder";
	}

	@GetMapping("/updateResult2")
	public String good() {
		return "booking/updateResult2";
	}
	@GetMapping("/cancelResult")
	public String good2() {
		return "booking/cancelResult";
	}
}


