package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
		
		Integer id = null;
		String stname = request.getParameter("stname");
		String sclass = request.getParameter("sclass");
		String saddress = request.getParameter("saddress");
		String stitd = request.getParameter("stitd");
		String stitddt = request.getParameter("stitddt");
		String tel = request.getParameter("tel");
		
		StoreService ss = new StoreServiceImpl();
		if(ss.isDup(stname)) {
			errorMsg.put("stname","商店名稱重複請改名");
			RequestDispatcher rd = request.getRequestDispatcher("Insert.jsp");
			rd.forward(request, response);
			return;
		}
		HttpSession session = request.getSession(); 
		Integer testid = null;
		try {
			StoreBean sb = new StoreBean(id,stname, sclass, saddress, stitd,stitddt, tel);
			session.setAttribute("sb", sb);
			ss.save(sb);
			testid = sb.getId();
			System.out.println("testID:"+testid);
		}catch (Exception e) {
			e.printStackTrace();
		}
		if(errorMsg.isEmpty()) {
//			String url = request.getContextPath()+"/exInsert.jsp";
//			String targetURL = response.encodeRedirectURL(url);
//			request.getSession().setAttribute("stname", stname);
//			response.sendRedirect(targetURL);
			
			List<StoreBean> list =ss.getFullstore(testid);
			request.getSession(true).setAttribute("Results", list);
			RequestDispatcher rd = null;
			rd = request.getRequestDispatcher("detailStore.jsp");
			rd.forward(request, response);
		}else {
			RequestDispatcher rd = request.getRequestDispatcher("Insert.jsp");
			rd.forward(request,response);
			return;
		}
	}

}
