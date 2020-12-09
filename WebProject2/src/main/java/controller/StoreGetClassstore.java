package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.StoreBean;
import service.StoreService;
import service.impl.StoreServiceImpl;

/**
 * Servlet implementation class StoreGetClassstore
 */
@WebServlet("/StoreGetClassstore")
public class StoreGetClassstore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreGetClassstore() {
        super();
        // TODO Auto-generated constructor stub
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String sclass = request.getParameter("sclass");
		StoreService ss = new StoreServiceImpl();
		List<StoreBean> list =ss.getClassstore(sclass);
		request.setAttribute("Results", list);
		RequestDispatcher rd = request.getRequestDispatcher("SimpleStore.jsp");
		rd.forward(request, response);
	}






}
