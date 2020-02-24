package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Order;
import entities.User;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.OrderManager;

/**
 * Servlet implementation class AdminOrder
 */
@WebServlet("/AdminOrders")
public class AdminOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminOrders() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		

		HttpSession session = request.getSession();
		if (session.getAttribute("user")!=null) {
			User user = (User) session.getAttribute("user");
			LoginManager manager = new LoginManager(user);
			if (manager.checkOrderAdmin()) {
				String page = request.getParameter("page");
				String minimum = (String) request.getParameter("min");
				int number;
				int min;
				if (page!=null) {
					number = Integer.parseInt(page);
					if (number == 1) {
						min = number-1;
					} else {
						min = (Integer.parseInt(minimum)) + 10;
					}
				} else {
					number = 1;
					min = number-1;
				}
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
				
				OrderManager orderManager = new OrderManager(dmcp);

				ArrayList<Order> orders= null;

				int nextOrder = 0;
				
				
				try {
					Collection<Order> c = orderManager.doRetrieveAll( "id limit "+ min + ", " + 10 );
					orders = new ArrayList<Order>(c);
					
					String contains = request.getParameter("search");
					if (contains!=null && !contains.equals("")) {
						ArrayList<Order> p = new ArrayList<Order>();
						for (Order prod : orders) {
							if ( prod.getState().toLowerCase().contains( contains.toLowerCase() ) || String.valueOf(prod.getId()).toLowerCase().contains( contains.toLowerCase() )  ) {
								p.add(prod);
							} 
						}
						orders = p;
					}
					
					
					nextOrder = ( new ArrayList<Order>( orderManager.doRetrieveAll( "id limit "+ (min+10) + ", " + 1 ) ).size() );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				request.setAttribute("orders", orders);
				request.setAttribute("page", number);
				request.setAttribute("min", min);
				
				if (nextOrder > 0) {
					request.setAttribute("next", "next");
				} else {
					if ( (request.getAttribute("next")) != null ) {
						request.removeAttribute("next");
					}
				}
				

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/orders.jsp?page="+number);
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
