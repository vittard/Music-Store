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
import manager.OrderManager;

/**
 * Servlet implementation class MyOrders
 */
@WebServlet("/MyOrders")
public class MyOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyOrders() {
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
		if (session.getAttribute("user")!=null) {
			User user = (User) session.getAttribute("user");
			
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
			OrderManager manager = new OrderManager(dmcp);
			
			ArrayList<Order> orders = null;
			int nextOrder = -1;
			
			
			try {
				Collection<Order> c = manager.doRetrieveAllByUser( user, "id desc limit "+ min + ", " + 10 );
				orders = new ArrayList<Order>(c);
				
				nextOrder = ( new ArrayList<Order>( manager.doRetrieveAllByUser( user ,  "id desc limit "+ (min+10) + ", " + 1 ) ).size() );
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


			RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/orders.jsp?page="+number);
			rd.forward(request, response);
			
		
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
			rd.forward(request, response);
		}
		
	}

}
