package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.StoreBean;
import service.StoreService;
import service.impl.StoreServiceImpl;

/**
 * Servlet implementation class InsertStoreServlet
 */
@WebServlet("/InsertStoreServlet")
public class InsertStoreServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertStoreServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		Map<String,String> errorMsg = new HashMap<String,String>();
		request.setAttribute("error", errorMsg);
		
		
		String stname = request.getParameter("stname");
		String sclass = request.getParameter("sclass");
		String saddress = request.getParameter("saddress");
		String stitd = request.getParameter("stitd");
		String tel = request.getParameter("tel");
		
		StoreService ss = new StoreServiceImpl();
		if(ss.isDup(stname)) {
			errorMsg.put("stname","商店名稱重複請改名");
			RequestDispatcher rd = request.getRequestDispatcher("Insert.jsp");
			rd.forward(request, response);
			return;
		}
		HttpSession session = request.getSession(); 
		try {
			StoreBean sb = new StoreBean(stname, sclass, saddress, stitd, tel);
			session.setAttribute("sb", sb);
			ss.save(sb);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(errorMsg.isEmpty()) {
			String url = request.getContextPath()+"/exInsert.jsp";
			String targetURL = response.encodeRedirectURL(url);
			request.getSession().setAttribute("stname", stname);
			response.sendRedirect(targetURL);
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("Insert.jsp");
			rd.forward(request,response);
			return;
		}
	}

}
