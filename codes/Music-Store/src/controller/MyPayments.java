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

import entities.Payment;
import entities.User;
import manager.DriverManagerConnectionPool;
import manager.PaymentManager;

/**
 * Servlet implementation class MyPayments
 */
@WebServlet("/MyPayments")
public class MyPayments extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MyPayments() {
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
			PaymentManager manager = new PaymentManager(dmcp);

			ArrayList<Payment> payments = null;
			int nextPayment = -1;


			try {
				Collection<Payment> c = manager.doRetrieveAllByUser(user, "id desc limit "+ min + ", " + 10 );
				payments = new ArrayList<Payment>(c);

				nextPayment = ( new ArrayList<Payment>( manager.doRetrieveAllByUser( user ,  "id desc limit "+ (min+10) + ", " + 1 ) ).size() );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("payments", payments);
			request.setAttribute("page", number);
			request.setAttribute("min", min);

			if (nextPayment > 0) {
				request.setAttribute("next", "next");
			} else {
				if ( (request.getAttribute("next")) != null ) {
					request.removeAttribute("next");
				}
			}


			RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/payments.jsp?page="+number);
			rd.forward(request, response);


		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
			rd.forward(request, response);
		}
	}

}
