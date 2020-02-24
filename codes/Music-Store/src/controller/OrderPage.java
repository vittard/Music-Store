package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Address;
import entities.Order;
import entities.Product;
import entities.User;
import manager.AddressManager;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.OrderManager;
import manager.ProductManager;

/**
 * Servlet implementation class Prova
 */
@WebServlet("/Order")
public class OrderPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public OrderPage() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
		HttpSession session = request.getSession();
		if (session.getAttribute("user")!=null) {
			User user = (User) session.getAttribute("user");
			LoginManager manager = new LoginManager(user);
			if (manager.checkClient()) {
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
				
				OrderManager orderManager= new OrderManager(dmcp);
				
				String id = request.getParameter("id");
		    	if ( id != null ) {
				Order order = null;
				try {
					
					order = orderManager.doRetrieveByKey(id);
					if ( order.getUserId() == user.getId() ) {
						request.setAttribute("order",order);
						String id_address= String.valueOf(order.getAddressId());
						AddressManager addressManager= new AddressManager(dmcp);
						Address address;
						address = addressManager.doRetrieveByKey(id_address);
						request.setAttribute("address",address);
					} else {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
						rd.forward(request, response);
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
				
				ProductManager productManager= new ProductManager(dmcp);
				HashMap<Product, Integer> products = productManager.doRetrieveAllByOrder(order, null);
				request.setAttribute("products",products);
			
			
				
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/order.jsp");
				rd.forward(request, response);
		    	}
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
				rd.forward(request, response);
			}
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
