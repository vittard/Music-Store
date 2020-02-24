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
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.ProductManager;
import manager.CategoryManager;
import manager.UserManager;

/**
 * Servlet implementation class RemoveServlet
 */
@WebServlet("/AdminRemoveYes")
public class AdminRemoveYes extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminRemoveYes() {
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
				
				
				if ( objectClass.equals("Category") ) {
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					CategoryManager categoryManager = new CategoryManager(dmcp);
					Category category = new Category();
					category.setName(id);
					try {
						categoryManager.doDelete(category);
						request.setAttribute("message", "Categoria:<br>" + category.getName() + "<br>Rimossa con successo");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminCategories");
						rd.forward(request, response);
					} catch (SQLException e) {
						request.setAttribute("message", "Non è stato possibile rimuovere la categoria");
						request.setAttribute("error", true);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminCategories");
						rd.forward(request, response);
					}
				} else if ( objectClass.equals("Product") ) {
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					ProductManager productManager = new ProductManager(dmcp);
					Product product = new Product();
					product.setId(Integer.valueOf(id));
					try {
						productManager.doDelete(product);
						request.setAttribute("message", "Prodotto " + "Rimosso con successo");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminProducts");
						rd.forward(request, response);
					} catch (SQLException e) {
						request.setAttribute("message", "Non è stato possibile rimuovere l'indirizzo");
						request.setAttribute("error", true);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminProducts");
						rd.forward(request, response);
					}
				} else if ( objectClass.equals("User") ) {
					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					UserManager userManager = new UserManager(dmcp);
					User userRemove = new User(); 
					userRemove.setId(Integer.valueOf(id));
					try {
						userManager.doDelete(userRemove);
						request.setAttribute("message", "Account " + "Rimosso con successo");
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminUsers");
						rd.forward(request, response);
					} catch (SQLException e) {
						request.setAttribute("message", "Non è stato possibile rimuovere l'account");
						request.setAttribute("error", true);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminUsers");
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
