package controller;

import java.io.IOException;

import service.CartServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/controller")
public class Controller {
	
	public Controller() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
}
