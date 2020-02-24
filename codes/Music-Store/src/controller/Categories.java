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

import entities.Category;

import manager.CategoryManager;
import manager.DriverManagerConnectionPool;
/**
 * Servlet implementation class Categories
 */
@WebServlet("/Categories")
public class Categories extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Categories() {
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
				DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
				
				CategoryManager categoryManager = new CategoryManager(dmcp);

				ArrayList<Category> category= null;

				int nextCategory = 0;
				
				
				try {
					Collection<Category> c = categoryManager.doRetrieveAll( "name limit "+ min + ", " + 10 );
					category = new ArrayList<Category>(c);
					
					
					nextCategory = ( new ArrayList<Category>( categoryManager.doRetrieveAll( "name limit "+ (min+10) + ", " + 1 ) ).size() );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				request.setAttribute("category", category);
				request.setAttribute("page", number);
				request.setAttribute("min", min);
				
				if (nextCategory > 0) {
					request.setAttribute("next", "next");
				} else {
					if ( (request.getAttribute("next")) != null ) {
						request.removeAttribute("next");
					}
				}
				

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/categories.jsp?page="+number);
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
