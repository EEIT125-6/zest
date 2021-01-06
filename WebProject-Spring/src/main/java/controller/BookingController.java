package controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import model.BookingBean;
import service.BookingService;

@SessionAttributes({"reg_booking"})
@Controller
@RequestMapping("/booking")
public class BookingController {
	
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
			
			BookingBean reg_booking =  new BookingBean(bookingNo,"", bookingdate, time, number, restaurant,name,phone,mail,purpose,needs,1);		  
			model.addAttribute("reg_booking",reg_booking);
			
			return "/booking/DisplayBooking";
		}
			
		return "forward:/booking/"+restaurant;
	}
	//增
	@PostMapping("/confirm")
	public String insert(Model model) {
		
		BookingBean bookingData = (BookingBean)model.getAttribute("reg_booking");
		
		if (service.insertBooking(bookingData)>0)
		System.out.println("Get some SQL commands done!");
		  
		return "redirect:/booking/Thanks";

	}
	
	@GetMapping("/Thanks")
	public String thanks() {
		return "/booking/Thanks";
	}
	@GetMapping("/Page1")
	public String jump() {
		return "/booking/Page1";
	}
	
	
	//查
	@PostMapping("/select")
	public String query(Model model,@RequestParam(value="phone") String phone) {
		
		List<BookingBean> bean = service.findBooking(phone);

	    model.addAttribute("booking",bean);
	    model.getAttribute("booking");
	    
		return "booking/queryResult";
		
	}
	//刪
//	@PostMapping("/cancel")
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
			BookingBean bean=new BookingBean(bookingNo,"",bookingdate,time,number,restaurant,name,phone,mail,purpose,needs,0);	
			count=service.updateBooking(bean);
			System.out.println(count);	
			if(count==1)
				return "redirect:/booking/cancelResult";
			else {
				System.out.println("訂位取消未成功。。。");
			}
			return "redirect:/booking/Page1";
		}
		ra.addFlashAttribute("line","已過取消期限");
		return "redirect:/booking/Page1";	
	}
	//改
	@PostMapping(value="/confirmUpd",params = "confirmUpd")
	public String update(Model model,
			@RequestParam(value="bookingNo") String bookingNo,
			@RequestParam(value="restaurant") String restaurant,
			@RequestParam(value="bookingdate") String bookingdate,
			@RequestParam(value="time") String time,
			@RequestParam(value="number") Integer number,
			@RequestParam(value="name") String name,
			@RequestParam(value="phone") String phone,
			@RequestParam(value="mail") String mail,
			@RequestParam(value="needs") String needs,
			@RequestParam(value="purpose") String purpose) {
		
		if(checkCancelBooking(bookingdate)==true) {
		
		int count=0;
		BookingBean bean=new BookingBean(bookingNo,"", bookingdate,time,number,restaurant,name,phone,mail,purpose,needs,1);
		count=service.updateBooking(bean);
		System.out.println(count);
		if(count==1)
			return "redirect:/booking/updateResult2";
		else {
			System.out.println("訂位資料更新失敗！");
		}
		return "redirect:/booking/Page1";
		}
		return "redirect:/booking/Page1";
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


