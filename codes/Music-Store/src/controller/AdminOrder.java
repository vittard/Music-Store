package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Address;
import entities.Order;
import entities.Product;
import entities.User;
import manager.AddressManager;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.OrderManager;
import manager.ProductManager;

/**
 * Servlet implementation class AdminOrder
 */
@WebServlet("/AdminOrder")
public class AdminOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminOrder() {
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
			if (manager.checkOrderAdmin()) { // if user is an Order Manager
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();

				OrderManager orderManager= new OrderManager(dmcp);

				String id = request.getParameter("id");
				if ( id != null && !id.equals("")) { // parameter Name is correct
					Order order = null;
					Address address = null;
					HashMap<Product, Integer> products = null;
					try { // try connection

						order = orderManager.doRetrieveByKey(id);
						if ( order != null ) {
							AddressManager addressManager = new AddressManager(dmcp);
							address = addressManager.doRetrieveByKey(String.valueOf(order.getAddressId()));
							System.out.println(address);
							
							
							ProductManager productManager = new ProductManager(dmcp);
							products = productManager.doRetrieveAllByOrder(order, null);
							
							System.out.println(products);
						}

					} catch (SQLException e) { //connection problem
						request.setAttribute("message", "Problemi di connessione con il database, riprova tra un minuto");
						request.setAttribute("error", true);
						e.printStackTrace();
					}


					if (order != null && address!=null && products!=null) {
						request.setAttribute("order",order);
						request.setAttribute("address",address);
						request.setAttribute("products", products);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/order.jsp?id=" + id);
						rd.forward(request, response);
					} else {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminOrders");
						rd.forward(request, response);
					}
				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminOrders");
					rd.forward(request, response);
				}

			} else { //this user isn't an CatalogManager
				request.setAttribute("message", "Devi essere uno Gestore Utenti per accedere a quella pagina");
				request.setAttribute("error", true);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
				rd.forward(request, response);
			}
		} else { // there aren't an user in the session
			request.setAttribute("message", "Devi essere loggato per accedere a quella pagina");
			request.setAttribute("error", true);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
			rd.forward(request, response);
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
