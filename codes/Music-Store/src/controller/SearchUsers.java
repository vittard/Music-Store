package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import manager.DriverManagerConnectionPool;
import manager.UserManager;

/**
 * Servlet implementation class SearchUsers
 */
@WebServlet("/SearchUsers")
public class SearchUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchUsers() {
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
		UserManager userManager = new UserManager(dmcp);
		try {
			ArrayList<User> users = new ArrayList<User>(userManager.doRetrieveAll(null));
			String output = "";
			for (User user : users) {
				if (user.getName().toLowerCase().contains(query.toLowerCase()) || user.getSurname().toLowerCase().contains(query.toLowerCase()) || user.getEmail().toLowerCase().contains(query.toLowerCase()) || String.valueOf(user.getId()).toLowerCase().contains(query.toLowerCase()) || String.valueOf(user.getType()).toLowerCase().contains(query.toLowerCase()) || user.getRole().toLowerCase().contains(query.toLowerCase()) ) {
					output = output + "<p onclick=\"selectResultInSearch(this)\">" + user.getEmail() + "</p>";
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
