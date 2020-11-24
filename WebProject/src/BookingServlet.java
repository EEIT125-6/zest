import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import booking.bean.BookingBean;


/**
 * Servlet implementation class BookingServlet
 */
@WebServlet("/BookingServlet")
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	 private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	 private static final String CHARSET_CODE = "UTF-8";
	 public void init(ServletConfig config) throws ServletException{
		   super.init(config);
		 }
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	    request.setCharacterEncoding(CHARSET_CODE);
	    response.setContentType(CONTENT_TYPE);  
	    
	    DataSource ds = null;
	    InitialContext ctxt = null;
	    Connection conn = null;
	    try {

		      ctxt = new InitialContext();
		      
		      ds = ( DataSource ) ctxt.lookup("java:comp/env/jdbc/zest");
		      
		      conn = ds.getConnection();
		      
		      BookingDAO bookingDAO = new BookingDAO(conn);
	    
	    
			   if (request.getParameter("next")!=null)
			     gotoSubmitProcess(request, response,bookingDAO);
			   else if (request.getParameter("confirm")!=null)
			     gotoConfirmProcess(request, response,bookingDAO);
			   else if (request.getParameter("select")!=null) {
				 gotoQuery(request, response,bookingDAO);
			   }else if (request.getParameter("cancel")!=null) {
				 gotoCancel(request, response,bookingDAO);
			   }else if (request.getParameter("update")!=null) {
				 gotoUpdate(request, response,bookingDAO);
			   }else if (request.getParameter("confirmUpd")!=null) {
				 gotoConfirmUpd(request, response,bookingDAO);
			   }
		   }catch (NamingException ne) {
			      System.out.println("Naming Service Lookup Exception");  
			    } catch (SQLException e) {
			      System.out.println("Database Connection Error"); 
			    } finally {
			      try {
			        if (conn != null) conn.close();
			      } catch (Exception e) {
			        System.out.println("Connection Pool Error!");
			      }
			    }
		  }
	//更改2
	public void gotoConfirmUpd(HttpServletRequest request, HttpServletResponse response,BookingDAO bookingDAO) throws ServletException, IOException
	{
		  String bookingdate=request.getParameter("bookingdate");
		  String time=request.getParameter("time");
		  String number=request.getParameter("number");
		  String name=request.getParameter("name");
		  String phone=request.getParameter("phone");
		  String mail=request.getParameter("mail");
		  String needs=request.getParameter("needs");
		  System.out.println(bookingdate);
		  System.out.println(time);
		  System.out.println(number);
		  System.out.println(name);
		  System.out.println(phone);
		  System.out.println(mail);
		  System.out.println(needs);
		  
		  if(bookingDAO.updateBooking(bookingdate,time,number,name,phone,mail,needs))
			  request.getRequestDispatcher("booking/updateResult2.jsp").forward(request,response);
		  else {
			  System.out.println("訂位資料更新失敗！");
		}
		  
		  
//		  request.getSession(true).setAttribute("update",update);
//		  booking.bean.BookingBean bean = (BookingBean) request.getSession(true).getAttribute("update");
		  
	}
	//更改1
	public void gotoUpdate(HttpServletRequest request, HttpServletResponse response,BookingDAO bookingDAO) throws ServletException, IOException
	{
		String phone = request.getParameter("phone");
		System.out.println(phone);
		
		BookingBean update=bookingDAO.findBooking(phone);
		request.getSession(true).setAttribute("update", update);
		BookingBean bean=(BookingBean) request.getSession(true).getAttribute("update");
		System.out.println(bean.getBookingNo());
		
		request.getRequestDispatcher("booking/updateResult.jsp").forward(request,response);
	}

	//查詢
	public void gotoQuery(HttpServletRequest request, HttpServletResponse response,BookingDAO bookingDAO) throws ServletException, IOException
	  {  
			String phone = request.getParameter("phone");
			
			System.out.println(phone);
			
			BookingBean booking = bookingDAO.findBooking(phone);
			System.out.println("name"+booking.getName());
			//booking.getName();
			
//			List<BookingBean> aa = bookingDAO.findBooking(phone);
		    request.getSession(true).setAttribute("booking",booking);
		    booking.bean.BookingBean bean = (BookingBean) request.getSession(true).getAttribute("booking");
		    request.getRequestDispatcher("booking/queryResult.jsp").forward(request,response);
	  }
		
	//刪除
	public void gotoCancel(HttpServletRequest request, HttpServletResponse response,BookingDAO bookingDAO) throws ServletException, IOException
	{  
		String phone = request.getParameter("phone");
			
			
		boolean del = bookingDAO.cancelBooking(phone);
		if(del)
			  request.getRequestDispatcher("./booking/cancelResult.jsp").forward(request,response);
		  else {
			  System.out.println("訂位取消未成功。。。");
		}
//			    if (del == null) showError(response, " can not find this phone number" + phone);
//			    else {
//			      del.setDname(dname);
//			      if (deptDAO.updateDept(dept)) showForm(response,dept);
//			      else showError(response," delete failure");
//			    }
			
	  }
	
	//新增
	 public void gotoSubmitProcess(HttpServletRequest request, HttpServletResponse response,BookingDAO bookingDAO) throws ServletException, IOException
	  {
		  String user_id;
		  String bookingNo="";
		  String bookingdate;
		  String time;
		  int number;
		  String restaurant;
		  String name;
		  String phone;
		  String mail;
		  String purpose;
		  String needs;
	   
		String[]str = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V",
				"W","X","Y","Z","0","1","2","3","4","5","6","7","8","9"};
		
		for(int i=1;i<=5;i++) {
			bookingNo += str[(int)(Math.random()*36)];
		}
		System.out.println(bookingNo);
		user_id ="";
		bookingdate = request.getParameter("bookingdate").trim();
		System.out.println(bookingdate);
		time = request.getParameter("time");
		System.out.println(time);
		number = Integer.parseInt(request.getParameter("number"));
		System.out.println(number);
//		restaurant = request.getParameter("").trim();
		restaurant ="";
		name = request.getParameter("name").trim();
		System.out.println(name);
		phone = request.getParameter("phone").trim();
		System.out.println(phone);
		mail = request.getParameter("email").trim();
		System.out.println(mail);
		purpose = request.getParameter("purpose").trim();
		System.out.println(purpose);
		needs = request.getParameter("needs").trim();
		System.out.println(needs);
		
	    BookingBean reg_booking =  new BookingBean(user_id,bookingNo, bookingdate, time, number, restaurant,name,phone,mail,purpose,needs);
	    request.getSession(true).setAttribute("reg_booking",reg_booking);
	    request.getRequestDispatcher("booking/DisplayBooking.jsp").forward(request,response);
	  }
	 
	 //確認新增
	  public void gotoConfirmProcess(HttpServletRequest request, HttpServletResponse response,BookingDAO bookingDAO) throws ServletException, IOException
	  {
	      BookingBean bookingData = (BookingBean)request.getSession(true).getAttribute("reg_booking");
	      if (bookingDAO.insertBooking(bookingData)){
	          System.out.println("Get some SQL commands done!");
	          request.getSession(true).invalidate();
	          request.getRequestDispatcher("booking/Thanks.jsp").forward(request,response);
	        }
	    
	  }

}
