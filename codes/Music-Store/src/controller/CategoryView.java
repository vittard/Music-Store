package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Category;
import entities.Product;
import manager.CategoryManager;
import manager.DriverManagerConnectionPool;
import manager.ProductManager;

/**
 * Servlet implementation class CategoryView
 */
@WebServlet("/CategoryView")
public class CategoryView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CategoryView() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
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
		
		String nome=request.getParameter("name");
		
		if ( nome != null ) {

			DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();

			ProductManager productManager = new ProductManager(dmcp);
			CategoryManager categoryManager = new CategoryManager(dmcp);
			
			
			ArrayList<Product> products = null;
			Category category= null;
			int nextProduct = -1;

			try {
				category = categoryManager.doRetrieveByKey(nome);
				System.out.println(category.getName());
				String orderBy = "id asc limit "+ min + ", " + 10;
				products = new ArrayList<Product>(productManager.doRetrieveAllByCategory(category, orderBy));
				System.out.println(products);
				nextProduct = ( new ArrayList<Product>( productManager.doRetrieveAllByCategory( category, "id asc limit "+ (min+10) + ", " + 1 ) ).size() );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			request.setAttribute("products", products);
			request.setAttribute("page", number);
			request.setAttribute("min", min);
			request.setAttribute("categoryName", category.getName());

			if (nextProduct > 0) {
				request.setAttribute("next", "next");
			} else {
				if ( (request.getAttribute("next")) != null ) {
					request.removeAttribute("next");
				}
			}


			RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/category.jsp?page="+number);
			rd.forward(request, response);



		} else {
			request.setAttribute("error", "Impossibile raggiungere il prodotto richiesto");
		}


		RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/category.jsp");
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
