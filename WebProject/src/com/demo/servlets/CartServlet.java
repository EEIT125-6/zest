package com.demo.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.entities.Item;
import com.demo.entities.Product;

@WebServlet("/cart")
public class CartServlet extends HttpServlet { // 購物邏輯處理頁面

	private static final long serialVersionUID = 1L;

	public CartServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		System.out.println(action);
		if (action == null) {
			DisplayCart(request, response);
		} else {
			if (action.equalsIgnoreCase("buy")) {
				buy(request, response);
			} else if (action.equalsIgnoreCase("remove")) {
				System.out.println("action=" + action);
				remove(request, response);
			}
		}
	}

	protected void DisplayCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("cart/index.jsp").forward(request, response);
	}

	protected void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Item> cart = (List<Item>) session.getAttribute("Cart");
		int index = isExisting(Integer.parseInt(request.getParameter("id")), cart);
		System.out.println("檢查index是否存在=" + index);
		cart.remove(index);
		session.setAttribute("Cart", cart);
		response.sendRedirect("Cart");
	}

	protected void buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Product product = new Product(Integer.parseInt(request.getParameter("id")), request.getParameter("picture"),
				request.getParameter("shop"), request.getParameter("name"),
				Integer.parseInt(request.getParameter("price")), Integer.parseInt(request.getParameter("quantity")));
		HttpSession session = request.getSession();
		if (session.getAttribute("Cart") == null) {
			List<Item> cart = new ArrayList<Item>();
			cart.add(new Item(product, 1));
			System.out.println(request.getSession().getAttribute("Cart"));
			session.setAttribute("Cart", cart);
			

		} else {

			List<Item> cart = (List<Item>) session.getAttribute("Cart");
			int index = isExisting(Integer.parseInt(request.getParameter("id")), cart);
			System.out.println("Incoming ID=" + Integer.parseInt(request.getParameter("id")));
			System.out.println("index inside cart=" + index);
			if (index == -1) {
				cart.add(new Item(product, 1));
				System.out.println(request.getSession().getAttribute("Cart"));
			} else {
				int quantity = cart.get(index).getProductQuantity() + 1;
				cart.get(index).setQuantity(quantity);
			}
			session.setAttribute("Cart", cart);
		}
		response.sendRedirect("Cart");
	}

	private int isExisting(Integer id, List<Item> cart) {
		for (int i = 0; i < cart.size(); i++) {
			System.out.println("id檢查用=" + id);
			System.out.println("i值檢查用=" + i);
			System.out.println("抓來的cart值=" + cart.get(i).getProduct().getProductId());
			int test = cart.get(i).getProduct().getProductId();
			if (test == id) {

				return i;
			}
		}
		return -1;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

}
