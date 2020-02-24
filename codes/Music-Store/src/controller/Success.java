package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Cart;
import entities.Order;
import entities.User;
import entities.Product;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.OrderManager;

/**
 * Servlet implementation class Success
 */
@WebServlet("/Success")
public class Success extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Success() {
        super();
        // TODO Auto-generated constructor stub
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
		if (session.getAttribute("user")!=null && session.getAttribute("cart")!=null) {
			User user = (User) session.getAttribute("user");
			Cart cart = (Cart) session.getAttribute("cart");
			
			LoginManager manager = new LoginManager(user);
			if (manager.checkClient()) {
				
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
				
				Order order = new Order();
				order.setState("Attesa");
				GregorianCalendar gDate = new GregorianCalendar();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date date = Date.valueOf( sdf.format(gDate.getTime()) );
				order.setDate( date );
				order.setUserId(user.getId());
				String address = request.getParameter("address");
				int addressId = Integer.parseInt(address);
				order.setAddressId(addressId);
				
				
				try {
					OrderManager orderManager = new OrderManager(dmcp);
					
					if ( cart.getQuantityOfAll() > 0 ) {
						
						orderManager.doSave(order);
						
						ArrayList<Order> orders = new ArrayList<Order>(orderManager.doRetrieveAllByUser(user, "id desc limit 0,1")) ;
						
						HashMap<Product, Integer> products = cart.getProducts();
						
						Iterator<Map.Entry<Product, Integer>> it = products.entrySet().iterator();
						while (it.hasNext()) {
							Map.Entry<Product, Integer> e = it.next();
							Product product = e.getKey();
							Integer quantity = e.getValue();
							orderManager.addProduct(orders.get(0), product, quantity);
							cart.removeProduct(product);
						}
					
					}
					
					
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				request.setAttribute("message", "Order effettuato con successo in data " + sdf.format(gDate.getTime()));

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyOrders");
				rd.forward(request, response);
			} else {
				String url = response.encodeURL("Logout");
				response.sendRedirect(url);
			} 
		} else {
			String url = response.encodeURL("Logout");
			response.sendRedirect(url);
		}	
		
	}

}
