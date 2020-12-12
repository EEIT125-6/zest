package service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Item;
import model.ProductInfoBean;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

// 購物邏輯處理頁面

	public static void DisplayCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("cart/index.jsp").forward(request, response);
	}

	@SuppressWarnings("unchecked")
	public static void remove(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Item> cart = (List<Item>) session.getAttribute("Cart");
		int index = isExisting(Integer.parseInt(request.getParameter("id")), cart);
		cart.remove(index);
		session.setAttribute("Cart", cart);
		response.sendRedirect("Cart");
	}

	@SuppressWarnings("unchecked")
	public static void buy(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductInfoBean product = new ProductInfoBean(Integer.parseInt(request.getParameter("id")), request.getParameter("name"),
				request.getParameter("shop"), Integer.parseInt(request.getParameter("price")), request.getParameter("name"),
				 Integer.parseInt(request.getParameter("quantity")));
		HttpSession session = request.getSession();
		if (session.getAttribute("Cart") == null) {
			List<Item> cart = new ArrayList<Item>();
			cart.add(new Item(product, 1));
			System.out.println(request.getSession().getAttribute("Cart"));
			session.setAttribute("Cart", cart);

		} else {

			List<Item> cart = (List<Item>) session.getAttribute("Cart");
			int index = isExisting(Integer.parseInt(request.getParameter("id")), cart);
			if (index == -1) {
				cart.add(new Item(product, 1));
			} else {
				int quantity = cart.get(index).getProductQuantity() + 1;
				cart.get(index).setQuantity(quantity);
			}
			session.setAttribute("Cart", cart);
		}
		response.sendRedirect("Cart");
	}

	private static int isExisting(Integer id, List<Item> cart) {
		for (int i = 0; i < cart.size(); i++) {
			int test = cart.get(i).getProduct().getProduct_id();
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
