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

import entities.Order;
import entities.User;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.OrderManager;

/**
 * Servlet implementation class EditAdminOrder
 */
@WebServlet("/EditAdminOrder")
public class EditAdminOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditAdminOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user")!=null) { // an unser is logged
			User user = (User) session.getAttribute("user");
			LoginManager manager = new LoginManager(user);
			if (manager.checkOrderAdmin()) { // if user is an Order Manager
				
				String state = request.getParameter("state");
				String id = request.getParameter("id");
				
				if ( isValid(state) && isValid(id) ) {
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					OrderManager orderManager = new OrderManager(dmcp);
					

					try {
						Order order = orderManager.doRetrieveByKey(id);
						order.setState(state);
						orderManager.doUpdate(order);
						request.setAttribute("message", "Ordine modifcato con successo");
					} catch (SQLException e) {
						e.printStackTrace();
						request.setAttribute("message", "Impossibile modificare la categoria, riprovare a breve");
						request.setAttribute("error", true);
					}
					
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminOrder?id=" + id);
					rd.forward(request, response);

				} else {
					request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
					request.setAttribute("error", true);

					RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminOrder?id=" + id);
					rd.forward(request, response);
				}

			} else {	//this user isn't an CatalogManager
				request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
				request.setAttribute("error", true);

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
				rd.forward(request, response);
				
			}
		} else {	//there isn't an user logged
			request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
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
	
	private boolean isValid(String s) {
		if ( s!=null && !s.equals("") ) {
			if ( !s.contains("<") && !s.contains(">") && !s.contains("$") && !s.contains("?") ) {
				return true;
			}
		}
		return false;
	}

}
