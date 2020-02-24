package controller;

import java.io.IOException;
import java.sql.SQLException;

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
 * Servlet implementation class EditAdminCategory
 */
@WebServlet("/NewAdminUser")
public class NewAdminUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NewAdminUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("user")!=null) { // an unser is logged
			User user = (User) session.getAttribute("user");
			LoginManager manager = new LoginManager(user);
			if (manager.checkUserAdmin()) { // if user is an User Manager

				// Modifico i parametri dell'utente
				String name = request.getParameter("name");
				String surname = request.getParameter("surname");
				String userPassword = request.getParameter("userPassword");
				String email = request.getParameter("email");
				String type = request.getParameter("typeUser");
				String confirmUserPassword = request.getParameter("confirmUserPassword");
				String adminPassword = request.getParameter("adminPassword");

				System.out.println(type);

				if (isValid(name) && isValid(surname) && isValid(userPassword) && isValid(email) && isValid(type) && isValid(adminPassword) ) {

					DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
					UserManager userManager = new UserManager(dmcp);

					try {
						
						User newUser = new User( 0, name, surname, email, Integer.valueOf(type));

						if (userPassword.equals(confirmUserPassword)) { // controllo che l'utente inserisca la password corretta
							userManager.doSave(newUser, adminPassword);
							session.setAttribute("userdb", newUser);
							request.setAttribute("message", "Account salvato con successo");
						} else {
							request.setAttribute("message", "La password inserita è errata");
						}

						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminUser?id="+newUser.getId());
						rd.forward(request, response);							

					} catch (SQLException e) {
						e.printStackTrace();
						request.setAttribute("message", "Impossibile creare l'utente");
						request.setAttribute("error", true);

						RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminUser");
						rd.forward(request, response);	
					}



				} else {
					request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
					request.setAttribute("error", true);

					RequestDispatcher rd = getServletContext().getRequestDispatcher("/AdminUser");
					rd.forward(request, response);	
				}




			} else {	//this user isn't an CatalogManager
				request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
				request.setAttribute("error", true);

				RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
				rd.forward(request, response);

			}
		} else {	//there isn't an user logged
			request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
			request.setAttribute("error", true);

			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Logout");
			rd.forward(request, response);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean isValid(String s) {
		if ( s!=null && !s.equals("") ) {
			if ( !s.contains("<") && !s.contains(">") && !s.contains("$") && !s.contains("?") ) {
				return true;
			}
		}
		return false;
	}

}
