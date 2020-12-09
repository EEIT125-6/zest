package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

//import dao.StoreDao;
import model.StoreBean;
import service.StoreService;
import service.impl.StoreServiceImpl;

/**
 * Servlet implementation class StorePhotoServlet
 */
@WebServlet("/StorePhotoServlet")
public class StorePhotoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StorePhotoServlet() {
        super();
    }
    
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//HttpSession hsession = request.getSession();
		
		Integer id = Integer.parseInt((String) request.getSession(true).getAttribute("id"));
		String stname = (String) request.getSession(true).getAttribute("stname");
		String photourl = (String) request.getSession(true).getAttribute("photourl");
		String bannerurl = null;
		
		StoreService ss = new StoreServiceImpl();
		
		
		StoreBean sb = new StoreBean(id, stname, bannerurl, photourl);
		
		ss.photoStore(sb);
		
		String url = request.getContextPath()+"/detailStore.jsp";

		String newurl = response.encodeRedirectURL(url);
		response.sendRedirect(newurl);
	}

}
