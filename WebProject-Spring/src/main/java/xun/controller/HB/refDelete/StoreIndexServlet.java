package xun.controller.HB.refDelete;

import java.io.IOException;
import java.util.Collections;
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

//@WebServlet("/StoreIndexServlet")
public class StoreIndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public StoreIndexServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		StoreService ss = new StoreServiceImpl();
		List<StoreBean> list_ADP_randeom = ss.getAdvertisementphotostore();
		Collections.shuffle(list_ADP_randeom);
		List<StoreBean> list_ADP = list_ADP_randeom.subList(0, 6);
		request.setAttribute("APD", list_ADP);
		

		
		List<StoreBean> list_AD_randeom = ss.getAdvertisementstore();
		Collections.shuffle(list_AD_randeom);
		List<StoreBean> list_AD = list_AD_randeom.subList(0, 4);
		request.setAttribute("AD", list_AD);		
		
		RequestDispatcher rd = request.getRequestDispatcher("Index1.jsp");
		rd.forward(request, response);
	}



}
