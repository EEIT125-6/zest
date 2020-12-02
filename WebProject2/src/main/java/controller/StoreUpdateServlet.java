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
 * Servlet implementation class StoreUpdateServlet
 */
@WebServlet("/StoreUpdateServlet")
public class StoreUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StoreUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession hsession = request.getSession();
		Map<String, String> errorMsg = new HashMap<String, String>();
		hsession.setAttribute("error", errorMsg);

		
		String stname = request.getParameter("stname");
		String sclass = request.getParameter("sclass");
		String saddress = request.getParameter("saddress");
		String stitd = request.getParameter("stitd");
		String tel = request.getParameter("tel");
		int id = Integer.parseInt(request.getParameter("id"));
		
		if (stname == null || stname.trim().length() ==0) {
			errorMsg.put("stname","商店名稱不能為空白");
		}
		if (!errorMsg.isEmpty()) {
			RequestDispatcher rd = request.getRequestDispatcher("Update.jsp");
			rd.forward(request, response);
			return;
		}
		
		StoreService ss = new StoreServiceImpl();
		if(ss.isDup(stname) && !stname.equals((String)request.getSession().getAttribute("restname"))) {
			errorMsg.put("stname","商店名稱重複請改名");
			RequestDispatcher rd = request.getRequestDispatcher("Update.jsp");
			rd.forward(request, response);
			return;
		}
		
		StoreBean sb = new StoreBean(id, stname, sclass, saddress, stitd, tel);
//		int result = 0;
//		result = ss.updateStore(sb);
		ss.updateStore(sb);
//		if(result==1) {
//			hsession.setAttribute("modify", "修改成功");
//		}else{
//			hsession.setAttribute("modify", "修改時發生異常");
//		}
		String url = request.getContextPath()+"/exUpdate.jsp";
		request.getSession().setAttribute("stname", stname);
		String newurl = response.encodeRedirectURL(url);
		response.sendRedirect(newurl);

	}

}
