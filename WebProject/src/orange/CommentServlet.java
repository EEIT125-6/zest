package orange;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/CommentServlet")
public class CommentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DataSource ds = null;
	InitialContext ctxt = null;
	Connection conn = null;

	public CommentServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		try {

			ctxt = new InitialContext();
			ds = (DataSource) ctxt.lookup("java:comp/env/jdbc/zest");
			conn = ds.getConnection();
			CommentDAO commentDAO = new CommentDAO(conn);

			if (request.getParameter("submit") != null) {
				packProcess(request, response);
			}

			if (request.getParameter("comfirm") != null)
				insertProcess(request, response);

			if (request.getParameter("select") != null)
				selectProcess(request, response);

			if (request.getParameter("update") != null)
				updateProcess(request, response);

			if (request.getParameter("delete") != null)
				deleteProcess(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private void packProcess(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		response.setContentType("text/html; charset=UTF-8");

		String name = request.getParameter("name");
		Integer stars = Integer.parseInt(request.getParameter("stars"));// 這個就叫轉型
		Date utilDate = new Date();
		Date date = utilDate;
		String comment = request.getParameter("comment");
		String photo = request.getParameter("photo");

		CommentBean commentBean = new CommentBean(name, stars, date, comment, photo);
		request.getSession(true).setAttribute("commentBean", commentBean);

		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/orange/DisplayInsert.jsp");
		requestDispatcher.forward(request, response);
	}

	private void insertProcess(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {

		CommentBean commentBean = (CommentBean) request.getSession(true).getAttribute("commentBean");
		CommentDAO commentDAO = new CommentDAO(conn);
		if (commentDAO.insertComment(commentBean))
			System.out.println("success");
	}

	private void selectProcess(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		String name = request.getParameter("param");
		System.out.println("test" + name);

		CommentDAO commentDAO = new CommentDAO(conn);
		CommentBean commentBean = commentDAO.selectComment(name);

		System.out.println("test2" + commentBean.getName());

		request.getSession(true).setAttribute("commentBean", commentBean);
		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/orange/DisPlayComment.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private void updateProcess(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		response.setContentType("text/html; charset=UTF-8");

		String name = request.getParameter("name");
		Integer stars = Integer.parseInt(request.getParameter("stars"));// 這個就叫轉型
		Date utilDate = new Date();
		Date date = utilDate;
		String comment = request.getParameter("comment");
		String photo = request.getParameter("photo");

		CommentBean commentBean = new CommentBean(name, stars, date, comment, photo);
		CommentDAO commentDAO = new CommentDAO(conn);
		if (commentDAO.updateComment(commentBean))
			System.out.println("success");

		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/orange/Thanks.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private void deleteProcess(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {

		String name = request.getParameter("name");

		CommentDAO commentDAO = new CommentDAO(conn);
		if (commentDAO.deleteComment(name))
			System.out.println("success");

		ServletContext servletContext = getServletContext();
		RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher("/orange/Thanks.jsp");
		requestDispatcher.forward(request, response);
	}
}