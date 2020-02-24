package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Product;
import manager.DriverManagerConnectionPool;
import manager.ProductManager;

/**
 * Servlet implementation class SearchProduct
 */
@WebServlet("/SearchProduct")
public class SearchProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchProduct() {
		super();
		// TODO Auto-generated constructor stub
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("q");
		if (query!=null) {
			DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
			ProductManager productManager = new ProductManager(dmcp);
			try {
				ArrayList<Product> products = new ArrayList<Product>(productManager.doRetrieveAll(null));
				String output = "";
				for (Product product : products) {
					if (product.getName().toLowerCase().contains(query.toLowerCase())) {
						output = output + "<p onclick=\"selectResultInNav(this)\">" + product.getName() + "</p>";
					} else if (product.getDescription().toLowerCase().contains(query.toLowerCase())) {
						output = output + "<p onclick=\"selectResultInNav(this)\">" + product.getName() + "</p>";
					} else if (product.getDescription().toLowerCase().contains(query.toLowerCase())) {
						output = output + "<p onclick=\"selectResultInNav(this)\">" + product.getName() + "</p>";
					} else if (String.valueOf(product.getId()).toLowerCase().contains(query.toLowerCase())) {
						output = output + "<p onclick=\"selectResultInNav(this)\">" + product.getName() + "</p>";
					}
				}

				response.setContentType("text/html");
				response.getWriter().write(output);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			query = request.getParameter("t");
			DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
			ProductManager productManager = new ProductManager(dmcp);
			try {
				ArrayList<Product> products = new ArrayList<Product>(productManager.doRetrieveAll(null));
				String output = "";
				for (Product product : products) {
					if (product.getName().toLowerCase().contains(query.toLowerCase())) {
						output = output + "<p onclick=\"selectResultInSearch(this)\">" + product.getName() + "</p>";
					} else if (product.getDescription().toLowerCase().contains(query.toLowerCase())) {
						output = output + "<p onclick=\"selectResultInSearch(this)\">" + product.getName() + "</p>";
					} else if (product.getDescription().toLowerCase().contains(query.toLowerCase())) {
						output = output + "<p onclick=\"selectResultInSearch(this)\">" + product.getName() + "</p>";
					} else if (String.valueOf(product.getId()).toLowerCase().contains(query.toLowerCase())) {
						output = output + "<p onclick=\"selectResultInSearch(this)\">" + product.getName() + "</p>";
					}
				}

				response.setContentType("text/html");
				response.getWriter().write(output);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
