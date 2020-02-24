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

import entities.User;
import entities.Product;

import manager.DriverManagerConnectionPool;
import manager.LoginManager;

import manager.ProductManager;

/**
 * Servlet implementation class AdminProducts
 */
@WebServlet("/AdminProducts")
public class AdminProducts extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminProducts() {
        super();
        // TODO Auto-generated constructor stub
    }

 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if (session.getAttribute("user")!=null) {
			User user = (User) session.getAttribute("user");
			LoginManager manager = new LoginManager(user);
			if (manager.checkCatalogAdmin()) {
				String page = request.getParameter("page");
				String minimum = (String) request.getParameter("min");
				int number;
				int min;
				if (page!=null) {
					number = Integer.parseInt(page);
					if (number == 1) {
						min = number-1;
					} else {
						min = (Integer.parseInt(minimum)) + 10;
					}
				} else {
					number = 1;
					min = number-1;
				}
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
				
				ProductManager productManager = new ProductManager(dmcp);

				ArrayList<Product> products= null;

				int nextProduct = 0;
				
				
				try {
					Collection<Product> c = productManager.doRetrieveAll( "id asc limit "+ min + ", " + 10 );
					products = new ArrayList<Product>(c);
					
					
					nextProduct = ( new ArrayList<Product>( productManager.doRetrieveAll( "id asc limit "+ (min+10) + ", " + 1 ) ).size() );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				request.setAttribute("products", products);
				request.setAttribute("page", number);
				request.setAttribute("min", min);
				
				if (nextProduct > 0) {
					request.setAttribute("next", "next");
				} else {
					if ( (request.getAttribute("next")) != null ) {
						request.removeAttribute("next");
					}
				}
				

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/products.jsp?page="+number);
				rd.forward(request, response);
			} else {
				String url = response.encodeURL("Logout");
				response.sendRedirect(url);
			} 
		} else {
			String url = response.encodeURL("Logout");
			response.sendRedirect(url);
		}
		
		
	}
				
				
		
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
