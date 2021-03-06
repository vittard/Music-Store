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
 * Servlet implementation class NewAddress
 */
@WebServlet("/NewAdminCategory")
public class NewAdminCategory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewAdminCategory() {
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
		String name = request.getParameter("name");

		HttpSession session = request.getSession();
		if (session.getAttribute("user")!=null) { // an unser is logged
			User user = (User) session.getAttribute("user");
			LoginManager manager = new LoginManager(user);
			if (manager.checkCatalogAdmin()) { // if user is an Order Manager

				if (isValid(name)) {
					Category category = new Category();
					category.setName(name);

					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					CategoryManager categoryManager = new CategoryManager(dmcp);
					try {
						categoryManager.doSave(category);
						request.setAttribute("message", "Categoria aggiunta con successo");
					} catch (SQLException e) {
						e.printStackTrace();
						request.setAttribute("message", "Impossibile aggiungere la categoria");
						request.setAttribute("error", true);
					}
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminCategories");
					rd.forward(request, response);

				} else {
					request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
					request.setAttribute("error", true);

					RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminCategory");
					rd.forward(request, response);
				}
			} else {	//this user isn't an CatalogManager
				request.setAttribute("message", "Devi essere uno Gestore Catalogo per accedere a quella pagina");
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

	private boolean isValid(String s) {
		if ( s!=null && !s.equals("") ) {
			if ( !s.contains("<") || s.contains(">") || s.contains("$") || s.contains("?") ) {
				return true;
			}
		}
		return false;
	}

}
