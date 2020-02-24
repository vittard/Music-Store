package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Category;
import manager.CategoryManager;
import manager.DriverManagerConnectionPool;

/**
 * Servlet implementation class SearchCategories
 */
@WebServlet("/SearchCategories")
public class SearchCategories extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchCategories() {
        super();
        // TODO Auto-generated constructor stub
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
		String query = request.getParameter("q");
		System.out.println(query);
		DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
		CategoryManager categoryManager = new CategoryManager(dmcp);
		try {
			ArrayList<Category> categories = new ArrayList<Category>(categoryManager.doRetrieveAll(null));
			String output = "";
			for (Category order : categories) {
				if (order.getName().toLowerCase().contains(query.toLowerCase()) || String.valueOf(order.getId()).toLowerCase().contains(query.toLowerCase()) ) {
					output = output + "<p onclick=\"selectResultInSearch(this)\">" + order.getName() + "</p>";
				}
			}
			output = output + "";
			
			response.setContentType("text/html");
			response.getWriter().write(output);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
