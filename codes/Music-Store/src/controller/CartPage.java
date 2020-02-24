package controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Cart;
import entities.Product;
import entities.User;

/**
 * Servlet implementation class Carrello
 */
@WebServlet("/Cart")
public class CartPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		Cart cart = (Cart) session.getAttribute("cart");
		
		if (user!=null) { // there is an user autenticated
			
			if (cart!=null) { // there is a cart
				
				HashMap<Product, Integer> products = cart.getProducts();
				request.setAttribute("products", products);
				RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/View/cart.jsp");
				rd.forward(request, response);
				
			} else { // cart not exist
				RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/Logout");
				rd.forward(request, response);
			}
			
		} else { // user not autenticated
			RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/Logout");
			rd.forward(request, response);
		}
}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
