package controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.BookingBean;
import service.BookingService;
import service.BookingServiceImpl;

@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	 private static final String CHARSET_CODE = "UTF-8";
	 
	 BookingService service = new BookingServiceImpl();
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    request.setCharacterEncoding(CHARSET_CODE);
	    response.setContentType(CONTENT_TYPE);  
	  
			   if (request.getParameter("next")!=null)
			     gotoSubmitProcess(request, response);
			   else if (request.getParameter("confirm")!=null)
			     gotoConfirmProcess(request, response);
			   else if (request.getParameter("select")!=null) {
				 gotoQuery(request, response);
			   }else if (request.getParameter("cancel")!=null) {
				 gotoCancel(request, response);
			   } else if (request.getParameter("confirmUpd")!=null) {
				 gotoConfirmUpd(request, response);
			   }		  
		  }
	
	//確定更改
	public void gotoConfirmUpd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 String bookingNo=request.getParameter("bookingNo");
		 String restaurant=request.getParameter("restaurant");
		 String bookingdate=request.getParameter("bookingdate");
		 String time=request.getParameter("time");
		 Integer number=Integer.parseInt(request.getParameter("number"));
		 String name=request.getParameter("name");
		 String phone=request.getParameter("phone");
		 String mail=request.getParameter("mail");
		 String needs=request.getParameter("needs");

		 System.out.println(bookingNo);
		 System.out.println(restaurant);
		 System.out.println(bookingdate);
		 System.out.println(time);
		 System.out.println(number);
		 System.out.println(name);
		 System.out.println(phone);
		 System.out.println(mail);
		 System.out.println(needs);
		  
		  BookingService service=new BookingServiceImpl();
		  int count=0;
		  BookingBean bean=new BookingBean(bookingNo,bookingdate,time,number,restaurant,name,phone,mail,needs);
		  count=service.updateBooking(bean);
		  System.out.println(count);
		  if(count==1)
			  request.getRequestDispatcher("booking/updateResult2.jsp").forward(request,response);
		  else {
			  System.out.println("訂位資料更新失敗！");
		}
	}
	//刪除
		public void gotoCancel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{  
			String bookingNo=request.getParameter("bookingNo");
			String restaurant=request.getParameter("restaurant");
			String bookingdate=request.getParameter("bookingdate");
			String time=request.getParameter("time");
			Integer number=Integer.parseInt(request.getParameter("number"));
			String name=request.getParameter("name");
			String phone=request.getParameter("phone");
			String mail=request.getParameter("mail");
			String needs=request.getParameter("needs");
//			String purpose=request.getParameter("purpose");
//			Integer status=Integer.parseInt(request.getParameter("status"));
//			String user_id=request.getParameter("user_id");
			BookingService service=new BookingServiceImpl();
			int count = 0;
			BookingBean bean=new BookingBean(bookingNo,bookingdate,time,number,restaurant,name,phone,mail,needs);	
			count=service.updateBooking(bean);
			System.out.println(count);	
			if(count==1)
				  request.getRequestDispatcher("./booking/cancelResult.jsp").forward(request,response);
			else {
				  System.out.println("訂位取消未成功。。。");
			}
				
		  }
	//查詢
	public void gotoQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {  
			String phone = request.getParameter("phone");
			System.out.println(phone);
			
			BookingService service=new BookingServiceImpl();
			List<BookingBean> bean = service.findBooking(phone);

		    request.getSession(true).setAttribute("booking",bean);
		    request.getSession(true).getAttribute("booking");
		    request.getRequestDispatcher("booking/queryResult.jsp").forward(request,response);
		    
	  }
	
	//新增
	 public void gotoSubmitProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 String bookingNo="";
		 String user_id;
		 String bookingdate;
		 String time;
		 Integer number;
		 String restaurant;
		 String name;
		 String phone;
		 String mail;
		 String purpose;
		 String needs;
		 Integer status;
	   
		 String[]str = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V",
				"W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
		
		boolean isExist = false;
		do {
			bookingNo="";
			for(int i=1;i<=5;i++) {
				bookingNo += str[(int)(Math.random()*36)];
			}
			BookingService service=new BookingServiceImpl();
			isExist=service.checkBooking(bookingNo);
		} while(isExist);
		System.out.println(bookingNo);
		
		user_id ="";
		bookingdate = request.getParameter("bookingdate").trim();
		time = request.getParameter("time");
		number = Integer.parseInt(request.getParameter("number"));
//		restaurant = request.getParameter("").trim();
		name = request.getParameter("name").trim();
		phone = request.getParameter("phone").trim();
		restaurant ="";
		mail = request.getParameter("email").trim();
		purpose = request.getParameter("purpose").trim();
		needs = request.getParameter("needs").trim();
		status=1;
		
		System.out.println(bookingNo);
		System.out.println(bookingdate);
		System.out.println(time);
		System.out.println(number);
		System.out.println(name);
		System.out.println(phone);
		System.out.println(mail);
		System.out.println(purpose);
		System.out.println(needs);
		
	    BookingBean reg_booking =  new BookingBean(bookingNo,user_id, bookingdate, time, number, restaurant,name,phone,mail,purpose,needs,status);
	  
	    request.getSession(true).setAttribute("reg_booking",reg_booking);
	    request.getRequestDispatcher("booking/DisplayBooking.jsp").forward(request,response);
	  }
	 
	 //確認新增
	  public void gotoConfirmProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	  {
	      BookingBean bookingData = (BookingBean)request.getSession(true).getAttribute("reg_booking");
	      if (service.insertBooking(bookingData)>0){
	          System.out.println("Get some SQL commands done!");
	          request.getSession(true).invalidate();
	          request.getRequestDispatcher("booking/Thanks.jsp").forward(request,response);
	        }
	  }
}
