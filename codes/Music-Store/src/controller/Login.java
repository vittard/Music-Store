package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.Cart;
import entities.User;
import manager.CartManager;
//import manager.CartManager;
import manager.DriverManagerConnectionPool;
import manager.LoginManager;
import manager.UserManager;

@WebServlet(name = "login", urlPatterns = { "/Login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		/* String remember = request.getParameter("remember"); */


		if(!(email =="" && password =="")) {

			//Creo le varaibli per i driver manager
			DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
			UserManager userManager = new UserManager( dmcp );
			CartManager cartManager = new CartManager( dmcp );

			//creo l'utente corrispondente ai parametri
			User user = null;
			Cart cart = null;
			try {
				user = userManager.doRetriveByEmailAndPassword(email, password);
				
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println("Problemi di connessione con il database");
			}

			System.out.println(user.getName());

			LoginManager loginManager = new LoginManager(user);

			if (loginManager.checkClient()) {
				
				try {
					cart = cartManager.doRetrieveByKey( String.valueOf( user.getId() ) );
					if (cart == null) {
						Cart newCart = new Cart();
						newCart.setId(user.getId());
						cartManager.doSave(newCart);
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage());
					System.out.println("Problemi di connessione con il database");
				}

				HttpSession oldSession = request.getSession(false); 
				if (oldSession!=null) {
					oldSession.invalidate();
				}


				HttpSession session = request.getSession();
				session.setAttribute("user", user);

				System.out.println("AUTENTICATO cliente: " + user.getName() + " " + user.getSurname());
				session.setAttribute("cart", cart);

				String url = response.encodeURL("Home");
				response.sendRedirect(url);

			}

			else if (loginManager.checkAdmin()) {

				HttpSession session = request.getSession();
				session.setAttribute("user", user);
				System.out.print("AUTENTICATO ");
				if (loginManager.checkCatalogAdmin()) {
					System.out.print("Gestore Catalogo");
				} else if (loginManager.checkUserAdmin()) {
					System.out.print("Gestore Utenti");
				} else if (loginManager.checkOrderAdmin()) {
					System.out.print("Gestore Oridni");
				}
				System.out.println(": " + user.getName() + " " + user.getSurname());
				String url = response.encodeURL("Home");
				response.sendRedirect(url);  //CREARE SERVLET/JSP ORDINIGESTORE o dove direziona
			}



			else {
				response.sendRedirect("LoginPage");
			}

		}


	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
}

