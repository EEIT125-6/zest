//package board.controller;
//
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.Date;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
////import org.hibernate.Session;
////import org.hibernate.SessionFactory;
////import org.hibernate.Transaction;
////import org.hibernate.query.Query;
//
//import model.BoardBean;
//import orange.service.CommentService;
////import orange.util.HibernateUtils;
//
////@WebServlet("/orange/CommentServlet")
//public class CommentServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//	public CommentServlet() {
//        super();
//    }
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.setCharacterEncoding("UTF-8");
//		response.setContentType("text/html; charset=UTF-8");
//	      
//	      try {
//	    	  
////	    	  ctxt = new InitialContext();  //載入context.xml系統預設
////	    	  ds = ( DataSource ) ctxt.lookup("java:comp/env/jdbc/EmployeeDB");
////			conn = ds.getConnection();
//					
//			
//			if(request.getParameter("submit") != null) { //導向不同頁面
//				packProcess(request, response);}        //判斷使用的方法
//			
//			if(request.getParameter("comfirm") != null)
//			insertProcess(request, response);
//			
//			if(request.getParameter("select") != null)
//				selectProcess(request, response);
//			
//			if(request.getParameter("update") != null)
//				updateProcess(request, response);
//			
//			if(request.getParameter("delete") != null)
//				deleteProcess(request, response);
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} 
//		
//	}
//	
//	private void packProcess (HttpServletRequest request,    
//            HttpServletResponse response) throws SQLException, IOException, ServletException {
//		response.setContentType("text/html; charset=UTF-8");
//		
//														//取值，做成Bean
//		String name = request.getParameter("name");
//		Integer stars = Integer.parseInt(request.getParameter("stars"));//這個就叫轉型		
//		Date utilDate = new Date();
//		Date date = utilDate;
//		String comment = request.getParameter("comment");
//		String photo = request.getParameter("photo");
//		
//		BoardBean commentBean = new BoardBean(name,stars,date,comment,photo);
//		request.getSession(true).setAttribute("commentBean", commentBean);
//		//呼叫session，把Bean放到session
//		
//		ServletContext servletContext = getServletContext();  //把context組態資訊帶進來。
//	      RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/orange/DisplayInsert.jsp");
//	      requestDispatcher.forward(request, response);  //forward            //導向此JSP檔
//	      
//		System.out.println(request.getSession(true).getAttribute("commentBean"));
//	}
//
//	
//	
//	private void insertProcess (HttpServletRequest request,
//            HttpServletResponse response) throws SQLException, IOException, ServletException {
//		
//		
//		
//		BoardBean commentBean = (BoardBean)request.getSession(true).getAttribute("commentBean");
//		CommentService commentService = new CommentService();
//		if(commentService.insertComment(commentBean)>0)
//			System.out.println("success");
//		else
//			 System.out.println("fail"); 
//	
//		ServletContext servletContext = getServletContext();
//	    RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/orange/Thanks.jsp");
//	    requestDispatcher.forward(request, response);
//		}
//	
//		private void selectProcess(HttpServletRequest request, HttpServletResponse response)
//				throws SQLException, IOException, ServletException {
//
//			String name = request.getParameter("param");
//			System.out.println("test" + name);
//
//			CommentService commentService = new CommentService();
//			BoardBean commentBean = commentService.selectComment(name);
//
//			System.out.println("success");
//			System.out.println("test2" + commentBean.getName());
//
//			request.getSession(true).setAttribute("commentBean", commentBean);
//
////			ServletContext servletContext = getServletContext();
////			RequestDispatcher requestDispatcher = servletContext
////					.getRequestDispatcher(request.getContextPath() + "/orange/DisPlayComment.jsp");
////			requestDispatcher.forward(request, response); // 跳轉到其它後端 class
//			request.getRequestDispatcher("DisPlayComment.jsp").forward(request, response);
//		}
//	
//	private void updateProcess (HttpServletRequest request,
//            HttpServletResponse response) throws SQLException, IOException, ServletException {
//			
//		response.setContentType("text/html; charset=UTF-8");
//		
//		Integer id = Integer.parseInt(request.getParameter("id"));
//		String name = request.getParameter("name");
//		Integer stars = Integer.parseInt(request.getParameter("stars"));//這個就叫轉型
//		Date utilDate = new Date();
//		Date date = utilDate;
//		String comment = request.getParameter("comment");
//		String photo = request.getParameter("photo");
//		
//		BoardBean commentBean = new BoardBean(name,stars,date,comment,photo);
//		commentBean.setBoardid(id);
//		CommentService commentService = new CommentService();
//		if(commentService.updateComment(commentBean)>0)
//			System.out.println("success");
//		
//		
//		ServletContext servletContext = getServletContext();
//	      RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/orange/Thanks.jsp");
//	      requestDispatcher.forward(request, response);
//		
//		
//	}
//	
//	private void deleteProcess (HttpServletRequest request,
//            HttpServletResponse response) throws SQLException, IOException, ServletException {
//			
//		Integer id = Integer.parseInt(request.getParameter("id"));
////		String name = request.getParameter("name");
//		
//		CommentService commentService = new CommentService();
//		if(commentService.deleteComment(id)>0)
//			System.out.println("success");
//		
//		ServletContext servletContext = getServletContext();
//	      RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/orange/Thanks.jsp");
//	      requestDispatcher.forward(request, response);
//	}
//}
