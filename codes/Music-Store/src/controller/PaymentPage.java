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

import entities.Payment;
import entities.User;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.PaymentManager;

/**
 * Servlet implementation class PaymentPage
 */
@WebServlet("/Payment")
public class PaymentPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaymentPage() {
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
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();

				PaymentManager paymentManager= new PaymentManager(dmcp);

				String id = request.getParameter("id");
				if ( id != null ) {
					Payment payment = null;
					try {

						payment = paymentManager.doRetrieveByKey(id);
						request.setAttribute("payment",payment);

					} catch (SQLException e) {

						e.printStackTrace();
					}


					if (user.getId() == payment.getUserId()) {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/payment.jsp?id=" + id);
						rd.forward(request, response);
					} else {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyPayments");
						rd.forward(request, response);
					}
				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/payment.jsp");
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