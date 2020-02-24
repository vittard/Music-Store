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
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.UserManager;

/**
 * Servlet implementation class AdminCatalog
 */
@WebServlet("/AdminUsers")
public class AdminUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminUsers() {
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
			if (manager.checkUserAdmin()) {
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
				UserManager userManager = new UserManager(dmcp);

				ArrayList<User> users= null;

				int nextUser = 0;
				
				try {
					Collection<User> c = userManager.doRetrieveAll( "id limit "+ min + ", " + 10 );
					users = new ArrayList<User>(c);
					
					
					nextUser = ( new ArrayList<User>( userManager.doRetrieveAll( "id limit "+ (min+10) + ", " + 1 ) ).size() );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				request.setAttribute("users", users);
				request.setAttribute("page", number);
				request.setAttribute("min", min);
				
				if (nextUser > 0) {
					request.setAttribute("next", "next");
				} else {
					if ( (request.getAttribute("next")) != null ) {
						request.removeAttribute("next");
					}
				}
				

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/View/admin/users.jsp?page="+number);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
