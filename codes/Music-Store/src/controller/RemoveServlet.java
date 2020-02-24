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
import manager.UserManager;

/**
 * Servlet implementation class RemoveServlet
 */
@WebServlet("/RemoveYes")
public class RemoveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveServlet() {
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
				
				
				if ( objectClass.equals("Payment") ) {
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					PaymentManager paymentManager = new PaymentManager(dmcp);
					Payment payment = new Payment();
					payment.setId(Integer.valueOf(id));
					try {
						paymentManager.doDelete(payment);
						request.setAttribute("message", "Metodo di pagamento:<br>" + "Rimosso con successo");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyPayments");
						rd.forward(request, response);
					} catch (SQLException e) {
						request.setAttribute("message", "Non è stato possibile rimuovere il metodo di pagamento");
						request.setAttribute("error", true);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyPayments");
						rd.forward(request, response);
					}
				} else if ( objectClass.equals("Address") ) {
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					AddressManager addressManager = new AddressManager(dmcp);
					Address address = new Address();
					address.setId(Integer.valueOf(id));
					try {
						addressManager.doDelete(address);
						request.setAttribute("message", "Indirizzo:<br>" + "Rimosso con successo");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyAddresses");
						rd.forward(request, response);
					} catch (SQLException e) {
						request.setAttribute("message", "Non è stato possibile rimuovere l'indirizzo");
						request.setAttribute("error", true);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyAddresses");
						rd.forward(request, response);
					}
				} else if ( objectClass.equals("User") ) {
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					UserManager userManager = new UserManager(dmcp);
					try {
						userManager.doDelete(user);
						request.setAttribute("message", "Account:<br>" + "Rimosso con successo");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
						rd.forward(request, response);
					} catch (SQLException e) {
						request.setAttribute("message", "Non è stato possibile rimuovere l'account");
						request.setAttribute("error", true);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyAccount");
						rd.forward(request, response);
					}
				}
					
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
