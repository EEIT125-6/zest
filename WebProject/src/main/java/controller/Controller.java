package controller;

import java.io.IOException;

import service.CartServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/controller")
public class Controller extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("in servlet");
		String action = request.getParameter("action");
		if (action == null) {
			CartServlet.DisplayCart(request, response);
		} else {
			if (action.equalsIgnoreCase("buy")) {
				CartServlet.buy(request, response);
			} else if (action.equalsIgnoreCase("remove")) {
				System.out.println("action=" + action);
				CartServlet.remove(request, response);
			}
		}
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
