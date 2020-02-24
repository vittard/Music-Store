package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

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
 * Servlet implementation class Checkout
 */
@WebServlet("/Checkout")
public class Checkout extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Checkout() {
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
		if (session.getAttribute("user")!=null) {
			User user = (User) session.getAttribute("user");
			LoginManager manager = new LoginManager(user);
			if (manager.checkClient()) {
				
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
				AddressManager addressManager = new AddressManager(dmcp);
				PaymentManager paymentManager = new PaymentManager(dmcp);
				
				try {
					
					ArrayList<Address> addresses = new ArrayList<Address>(addressManager.doRetrieveByUser(user, "id"));
					ArrayList<Payment> payments = new ArrayList<Payment>(paymentManager.doRetrieveAllByUser(user, "id"));
					request.setAttribute("addresses", addresses);
					request.setAttribute("payments", payments);
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/checkout.jsp");
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
