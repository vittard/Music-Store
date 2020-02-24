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

import entities.Address;
import entities.Payment;
import entities.User;
import manager.AddressManager;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.PaymentManager;

/**
 * Servlet implementation class RemovePage
 */
@WebServlet("/Remove")
public class RemovePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RemovePage() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user")!=null) {
			User user = (User) session.getAttribute("user");
			LoginManager manager = new LoginManager(user);
			if (manager.checkClient()) {
				String objectClass = request.getParameter("type");
				String id = request.getParameter("id");
				String previousPage = request.getParameter("prev");


				if ( objectClass.equals("Payment") ) {
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					PaymentManager paymentManager = new PaymentManager(dmcp);
					Payment payment = null;
					try {
						payment = paymentManager.doRetrieveByKey(id);
						request.setAttribute("objectClass", "Payment");
						request.setAttribute("object", payment);
						request.setAttribute("previous", previousPage);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/remove.jsp");
						rd.forward(request, response);
					} catch (SQLException e1) {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/"+previousPage);
						rd.forward(request, response);
					}

				} else if( objectClass.equals("User") ) {

					request.setAttribute("objectClass", "User");
					request.setAttribute("object", user);
					request.setAttribute("previous", previousPage);
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/remove.jsp");
					rd.forward(request, response);

				} else if( objectClass.equals("Address") ) {
					
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					AddressManager addressManager = new AddressManager(dmcp);
					
					Address address = null;
					
					try {
						address = addressManager.doRetrieveByKey(id);
						request.setAttribute("objectClass", "Address");
						request.setAttribute("object", address);
						request.setAttribute("previous", previousPage);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/remove.jsp");
						rd.forward(request, response);
					} catch (SQLException e) {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/"+previousPage);
						rd.forward(request, response);
					}

				}
				else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/"+previousPage);
					rd.forward(request, response);
				}

			}
		} RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
