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
import entities.User;
import manager.AddressManager;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;

/**
 * Servlet implementation class Address
 */
@WebServlet("/Address")
public class AddressPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddressPage() {
		super();
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
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();

				AddressManager addressManager= new AddressManager(dmcp);

				String id = request.getParameter("id");
				if ( id != null ) {
					Address address = null;
					try {

						address = addressManager.doRetrieveByKey(id);
						request.setAttribute("address",address);

					} catch (SQLException e) {

						e.printStackTrace();
					}


					if (user.getId() == address.getUserId()) {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/address.jsp?id=" + id);
						rd.forward(request, response);
					} else {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyAddresses");
						rd.forward(request, response);
					}
				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/address.jsp");
					rd.forward(request, response);
				}

			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
				rd.forward(request, response);
			}
		} else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
