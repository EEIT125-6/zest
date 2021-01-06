package xun.controller.HB.refDelete;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xun.model.StoreBean;
import xun.service.StoreService;
import xun.service.impl.StoreServiceImpl;

/**
 * Servlet implementation class StoreGetNamestore
 */
//@WebServlet("/StoreGetNamestore")
public class StoreGetNamestore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreGetNamestore() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String stname = request.getParameter("nsrch");
		StoreService ss = new StoreServiceImpl();
		List<StoreBean> list =ss.getNamestore(stname);
		request.setAttribute("Results", list);
		RequestDispatcher rd = request.getRequestDispatcher("SimpleStore.jsp");
		rd.forward(request, response);
	}

}
