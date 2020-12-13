package xun.controller.HB.refDelete;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import xun.model.StoreBean;
import xun.service.StoreService;
import xun.service.impl.StoreServiceImpl;

/**
 * Servlet implementation class StoreDeleteServlet
 */
//@WebServlet("/StoreDeleteServlet")
public class StoreDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreDeleteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession hsession = request.getSession();
		Map<String, String> errorMsg = new HashMap<String, String>();
		hsession.setAttribute("error", errorMsg);
		
		int id =Integer.parseInt(request.getParameter("id"));
		
		StoreService ss = new StoreServiceImpl();
		StoreBean sb = new StoreBean(id);
		ss.deleteStore(sb);
		String url = request.getContextPath()+"/exDeleteStore.jsp";
		String newurl = response.encodeRedirectURL(url);
		response.sendRedirect(newurl);

	}

}