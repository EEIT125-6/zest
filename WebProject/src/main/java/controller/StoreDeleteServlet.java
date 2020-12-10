package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.StoreBean;
import service.StoreService;
import service.impl.StoreServiceImpl;

@WebServlet("/StoreDeleteServlet")
public class StoreDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StoreDeleteServlet() {
        super();
    }

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
