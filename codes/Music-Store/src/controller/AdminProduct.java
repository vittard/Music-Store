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

import entities.Product;
import entities.User;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.ProductManager;

/**
 * Servlet implementation class AdminCategory
 */
@WebServlet("/AdminProduct")
public class AdminProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProduct() {
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
			if (manager.checkCatalogAdmin()) { // if user is an Order Manager
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();

				ProductManager productManager = new ProductManager(dmcp);

				String id = request.getParameter("id");
				if ( id != null && !id.contentEquals("")) { // parameter Name is correct
					Product product = null;
					try { // try connection

						product = productManager.doRetrieveByKey(id);

					} catch (SQLException e) { //connection problem
						request.setAttribute("message", "Problemi di connessione con il database, riprova tra un minuto");
						request.setAttribute("error", true);
						e.printStackTrace();
					}


					if (product != null) {
						request.setAttribute("product",product);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/product.jsp?id=" + id);
						rd.forward(request, response);
					} else {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminProducts");
						rd.forward(request, response);
					}
				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/product.jsp");
					rd.forward(request, response);
				}

			} else { //this user isn't an CatalogManager
				request.setAttribute("message", "Devi essere uno Gestore Catalogo per accedere a quella pagina");
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
