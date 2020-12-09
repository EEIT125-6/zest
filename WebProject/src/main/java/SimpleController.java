import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import javax.servlet.*;
import javax.sql.DataSource;

/**
 * Servlet implementation class SimpleController
 */
@WebServlet("/SimpleController")
public class SimpleController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	 private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	 private static final String CHARSET_CODE = "UTF-8";
	 
	DataSource ds;
//    /**
//     * @see HttpServlet#HttpServlet()
//     */

    public SimpleController() {
        super();
        
    }
    
	public void init(ServletConfig config) throws ServletException {
	
		super.init(config);
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8"); 
		
	    request.setCharacterEncoding(CHARSET_CODE);
	    response.setContentType(CONTENT_TYPE);
	    if (request.getParameter("banner")!=null) {
		     gotoBannerProcess(request, response);}
		else if (request.getParameter("photo")!=null) {
		     gotoPhotoProcess(request, response);}
		else {
		  String nextPage = "/SimpleStore.jsp";
	      ServletContext servletContext = getServletContext();
	      RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(nextPage);
	      requestDispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    request.setCharacterEncoding(CHARSET_CODE);
	    response.setContentType(CONTENT_TYPE);
	    

		doGet(request, response);
	}
	 public void gotoBannerProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 String stname;
		 String id;
		 id = request.getParameter("id");
		 stname = request.getParameter("stname");
		 
		 request.getSession(true).setAttribute("id",id);
		 request.getSession(true).setAttribute("stname",stname);
		 request.getRequestDispatcher("testuploadbanner.jsp").forward(request,response); 
	 }
	 public void gotoPhotoProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		 String stname;
		 String id;
		 id = request.getParameter("id");
		 stname = request.getParameter("stname");
		 
		 request.getSession(true).setAttribute("id", id);
		 request.getSession(true).setAttribute("stname",stname);
		 request.getRequestDispatcher("testupload.jsp").forward(request,response);
	 }
}
