package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;

import model.StoreBean;
import service.StoreService;
import service.impl.StoreServiceImpl;

/**
 * Servlet implementation class StoreBannerServlet
 */
@WebServlet("/StoreBannerServlet")
public class StoreBannerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreBannerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		//HttpSession hsession = request.getSession();
		
		Integer id = Integer.parseInt((String) request.getSession(true).getAttribute("id"));
		String stname = (String) request.getSession(true).getAttribute("stname");
		String photourl = null;
		String bannerurl = (String) request.getSession(true).getAttribute("bannerurl");
		
		
		System.out.println("id:   "+id+"  Stname:  "+stname+"   photourl:   "+bannerurl);
		
		StoreService ss = new StoreServiceImpl();
		
		
		StoreBean sb = new StoreBean(id, stname, bannerurl, photourl);
		
		ss.bannerStore(sb);
		
		System.out.println("id:   "+sb.getId()+"  Stname:  "+sb.getStname()+"   photourl:   "+sb.getBannerurl());
		
		String url = request.getContextPath()+"/detailStore.jsp";
		request.getSession().setAttribute("stname", stname);
		String newurl = response.encodeRedirectURL(url);
		response.sendRedirect(newurl);
	}

}
