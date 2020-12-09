package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.hibernate.cache.spi.FilterKey;

import model.StoreBean;
import service.StoreService;
import service.impl.StoreServiceImpl;

/**
 * Servlet implementation class StoreGetFullstore
 */
@WebServlet("/StoreGetFullstore")
public class StoreGetFullstore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreGetFullstore() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 下面五行是測試
//		if(request.getSession().getAttribute("id1111") == null) {
//			System.out.println("??");
//		}else {
//			System.out.println("@@@");
//		}
		
		request.setCharacterEncoding("UTF-8");
		Integer id1 = Integer.parseInt(request.getParameter("id"));
		request.getSession(true).setAttribute("id", id1);
		Integer id = (Integer) request.getSession(true).getAttribute("id");

		request.getSession(true).setAttribute("restname", request.getParameter("stname"));
		
		StoreService ss = new StoreServiceImpl();
		List<StoreBean> list =ss.getFullstore(id);
		request.getSession(true).setAttribute("Results", list);
		RequestDispatcher rd = null;
		rd = request.getRequestDispatcher("detailStore.jsp");
		rd.forward(request, response);
	}

}
