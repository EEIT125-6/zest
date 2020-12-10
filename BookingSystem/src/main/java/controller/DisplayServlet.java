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

/**
 * Servlet implementation class DisplayServlet
 */
@WebServlet("/DisplayServlet")
public class DisplayServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String bookingNo = request.getParameter("key");
		BookingService service = new BookingServiceImpl();
		BookingBean bean = service.singleBooking(bookingNo);
		System.out.println(bean.getBookingNo());
		System.out.println(bean.getPhone());
		System.out.println(bean.getName());
		
		request.setAttribute("bean", bean);
		request.getRequestDispatcher("booking/updateResult.jsp").forward(request, response);
		return;
		
	}

}
