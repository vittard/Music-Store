package controller;

import java.io.IOException;
import java.sql.SQLException;

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
import manager.CartManager;
import manager.DriverManagerConnectionPool;
import manager.ProductManager;

/**
 * Servlet implementation class CartAddProduct
 */
@WebServlet("/CartOp")
public class CartOperation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CartOperation() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		Cart cart = (Cart) session.getAttribute("cart");
		
		if (user!=null) { // there is an user autenticated
			
			if (cart!=null) { // there is a cart
			
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
				ProductManager productManager = new ProductManager(dmcp);
				CartManager cartManager = new CartManager(dmcp);
				
				String operation = request.getParameter("operation");
				String id = request.getParameter("id");
				
				if (operation.equals("add")) { //aggiungio un prodotto
					
					Product product;
					try {
						
						product = productManager.doRetrieveByKey(id);
						cartManager.addProduct(cart, product);
						
						
					} catch (SQLException e) { // database problem
						
						e.printStackTrace();
					}
					
				
				} else if (operation.equals("rem")) { //rimuovi un prodotto modificando la quantità
					
					Product product;
					try {
						
						product = productManager.doRetrieveByKey(id);
						cartManager.setQuantity(cart, product, (cart.getQuantityOfProduct(product)-1) );
						
					} catch (SQLException e) { // database problem
						
						e.printStackTrace();
					}
					
				} else if (operation.equals("remAll")) { // rimuovi completamente il prodotto
					
					Product product;
					try {
						
						product = productManager.doRetrieveByKey(id);
						cartManager.removeProduct(cart, product);
						
						
					} catch (SQLException e) { // database problem
						
						e.printStackTrace();
					}
					
				}
				
			} else { // cart not exist
				
			}
			
		} else { // user not exist
			
		}
		
		RequestDispatcher rd = request.getServletContext().getRequestDispatcher("/Cart");
		rd.forward(request, response);
	}

}
