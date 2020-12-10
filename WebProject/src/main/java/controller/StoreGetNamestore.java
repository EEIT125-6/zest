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

@WebServlet("/StoreGetNamestore")
public class StoreGetNamestore extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StoreGetNamestore() {
        super();
    }

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
