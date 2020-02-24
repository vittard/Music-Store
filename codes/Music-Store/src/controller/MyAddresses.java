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

import entities.Address;
import entities.User;
import manager.AddressManager;
import manager.DriverManagerConnectionPool;

/**
 * Servlet implementation class MyAddresses
 */
@WebServlet("/MyAddresses")
public class MyAddresses extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyAddresses() {
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
			AddressManager manager = new AddressManager(dmcp);

			ArrayList<Address> addresses = null;
			int nextAddress = -1;


			try {
				Collection<Address> c = manager.doRetrieveByUser( user, "id desc limit "+ min + ", " + 10 );
				addresses = new ArrayList<Address>(c);

				nextAddress = ( new ArrayList<Address>( manager.doRetrieveByUser( user ,  "id desc limit "+ (min+10) + ", " + 1 ) ).size() );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("addresses", addresses);
			request.setAttribute("page", number);
			request.setAttribute("min", min);

			if (nextAddress > 0) {
				request.setAttribute("next", "next");
			} else {
				if ( (request.getAttribute("next")) != null ) {
					request.removeAttribute("next");
				}
			}


			RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/addresses.jsp?page="+number);
			rd.forward(request, response);


		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
			rd.forward(request, response);
		}
	}
}
