package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

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
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
		ProductManager productManager = new ProductManager(dmcp);
		
		Collection<Product> productsCollection = null;
		ArrayList<Product> products = null;
		
		
		HttpSession session = request.getSession();
		if (session.getAttribute("user")!=null) {
			User user = (User) session.getAttribute("user");
			LoginManager manager = new LoginManager(user);
			if (manager.checkClient()) {
				try {
					productsCollection = productManager.doRetrieveAll("id desc limit "+ 0 +", "+ 10 );
					products = new ArrayList<Product>(productsCollection);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("\n\n\n\n databse Problem \n\n\n\n");
				}
				request.setAttribute("products", products);
				RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/View/index.jsp"));
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/View/admin/dashboard.jsp"));
				rd.forward(request, response);
			}
		} else {
			try {
				productsCollection = productManager.doRetrieveAll("id asc limit "+ 0 +", "+ 10 );
				products = new ArrayList<Product>(productsCollection);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("\n\n\n\n databse Problem \n\n\n\n");
			}
			request.setAttribute("products", products);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/View/index.jsp"));
			rd.forward(request, response);
		}
		

		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
