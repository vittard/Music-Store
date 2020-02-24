package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Cart;
import entities.User;
import manager.CartManager;
import manager.DriverManagerConnectionPool;
import manager.UserManager;

/**
 * Servlet implementation class Sigin
 */
@WebServlet("/Sigin")
public class Sigin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sigin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String confirm = request.getParameter("confirmPassword");
		
		if (email.contentEquals("") || password.equals("") || name.equals("") || surname.equals("") ) {
			response.getWriter().append("<h1>I parametri non possono essere vuoti!</h1>");
			String url = response.encodeURL("SiginPage");
			response.sendRedirect(url);
		} else if (confirm.equals(password)) {
			
			User user = new User(-1, name, surname, email, 0);
			
			DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
			UserManager userManager = new UserManager( dmcp );
			try {
				userManager.doSave(user, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				user = userManager.doRetriveByEmailAndPassword(email, password);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Cart cart = new Cart();
			cart.setId(user.getId());
			
			CartManager cartManager = new CartManager(dmcp);
			try {
				cartManager.doSave(cart);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String url = response.encodeURL("LoginPage");
			response.sendRedirect(url);
			
		} else {
			String url = response.encodeURL("SiginPage");
			response.sendRedirect(url);
		}
		
		
		response.getWriter().append("email: " + email + "<br>password: " + password + "<br>confirm: " + confirm + "<br>name: " + name + "<br>surname: " + surname);	}

}
