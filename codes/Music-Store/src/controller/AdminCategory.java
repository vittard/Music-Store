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

import entities.Category;
import entities.User;
import manager.CategoryManager;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;

/**
 * Servlet implementation class AdminCategory
 */
@WebServlet("/AdminCategory")
public class AdminCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminCategory() {
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

				CategoryManager categoryManager= new CategoryManager(dmcp);

				String name = request.getParameter("name");
				if ( name != null && !name.contentEquals("")) { // parameter Name is correct
					Category category = null;
					try { // try connection

						category = categoryManager.doRetrieveByKey(name);

					} catch (SQLException e) { //connection problem
						request.setAttribute("message", "Problemi di connessione con il database, riprova tra un minuto");
						request.setAttribute("error", true);
						e.printStackTrace();
					}


					if (category != null) {
						request.setAttribute("category",category);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/category.jsp?name=" + name);
						rd.forward(request, response);
					} else {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminCategories");
						rd.forward(request, response);
					}
				} else {
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/category.jsp");
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
