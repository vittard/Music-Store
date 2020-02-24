package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Order;

import java.util.ArrayList;

import manager.DriverManagerConnectionPool;
import manager.OrderManager;

/**
 * Servlet implementation class Search
 */
@WebServlet("/Search")
public class SearchOrders extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchOrders() {
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
		String query = request.getParameter("q");
		System.out.println(query);
		DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
		OrderManager orderManager = new OrderManager(dmcp);
		try {
			ArrayList<Order> orders = new ArrayList<Order>(orderManager.doRetrieveAll(null));
			String output = "";
			for (Order order : orders) {
				if (order.getState().toLowerCase().contains(query.toLowerCase()) || String.valueOf(order.getId()).toLowerCase().contains(query.toLowerCase()) ) {
					output = output + "<p onclick=\"selectResultInSearch(this)\">" + order.getState() + "</p>";;
				}
			}
			output = output + "";
			
			response.setContentType("text/html");
			response.getWriter().write(output);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
