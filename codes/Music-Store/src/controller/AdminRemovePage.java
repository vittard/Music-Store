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
import entities.Product;
import entities.User;
import manager.CategoryManager;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.ProductManager;
import manager.UserManager;

/**
 * Servlet implementation class RemovePage
 */
@WebServlet("/AdminRemove")
public class AdminRemovePage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminRemovePage() {
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
			if (manager.checkAdmin()) {
				String objectClass = request.getParameter("type");
				String id = request.getParameter("id");
				String previousPage = request.getParameter("prev");


				if ( objectClass.equals("Category") ) {
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					CategoryManager categoryManager = new CategoryManager(dmcp);
					Category category = null;
					try {
						category = categoryManager.doRetrieveByKey(id);
						request.setAttribute("objectClass", "Category");
						request.setAttribute("object", category);
						request.setAttribute("previous", previousPage);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/remove.jsp");
						rd.forward(request, response);
					} catch (SQLException e1) {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/"+previousPage);
						rd.forward(request, response);
					}

				} else if( objectClass.equals("User") ) {
					
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					UserManager userManager = new UserManager(dmcp);
					User userDB = null;
					
					try {
						userDB = userManager.doRetrieveByKey(id);
						request.setAttribute("objectClass", "User");
						request.setAttribute("object", userDB);
						request.setAttribute("previous", previousPage);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/remove.jsp");
						rd.forward(request, response);
					} catch (SQLException e) {
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/"+previousPage);
						rd.forward(request, response);
						e.printStackTrace();
					}
					
					

				} else if( objectClass.equals("Product") ) {
					
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					ProductManager productManager = new ProductManager(dmcp);
					
					Product product = null;
					
					try {
						product = productManager.doRetrieveByKey(id);
						request.setAttribute("objectClass", "Product");
						request.setAttribute("object", product);
						request.setAttribute("previous", previousPage);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/remove.jsp");
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
