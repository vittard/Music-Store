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
import manager.UserManager;

/**
 * Servlet implementation class EditAccount
 */
@WebServlet("/EditAccount")
public class EditAccount extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditAccount() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		if (session.getAttribute("user")!=null) { // c'è un utente loggato
			User user = (User) session.getAttribute("user");

				String function = request.getParameter("fun");

				if (function.equals("account")) { // Modifico i parametri dell'utente
					String name = request.getParameter("name");
					String surname = request.getParameter("surname");
					String password = request.getParameter("password");
					String email = request.getParameter("email");

					if (isValid(name) && isValid(surname) && isValid(password) && isValid(email)) {

						DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
						UserManager userManager = new UserManager(dmcp);

						int userId = user.getId();
						try {
							String passwordDB = userManager.getUserPassword(userId);
							
							User newUser = new User(user.getId(), name, surname, email, user.getType());
							
							if (passwordDB.equals(password)) { // controllo che l'utente inserisca la password corretta
								userManager.doUpdate(newUser);
								session.setAttribute("user", newUser);
								request.setAttribute("message", "Account modificato con successo");
							} else {
								request.setAttribute("message", "La password inserita è errata");
							}
							
							RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyAccount");
							rd.forward(request, response);							
							
						} catch (SQLException e) {
							e.printStackTrace();
							request.setAttribute("message", "Impossibile modificare l'indirizzo");
							request.setAttribute("error", true);
							
							RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyAccount");
							rd.forward(request, response);	
						}



					} else {
						request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
						request.setAttribute("error", true);

						RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyAccount");
						rd.forward(request, response);	
					}


				} else if (function.contentEquals("password")) { // Modifico la password dell'utente

					String oldPassword = request.getParameter("oldPassword");
					String newPassword = request.getParameter("newPassword");
					String confirmPassword = request.getParameter("confirmPassword");
					
					if (isValid(oldPassword) && isValid(newPassword) && isValid(confirmPassword)) {
						DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
						UserManager userManager = new UserManager(dmcp);
						try {
							String passwordDB = userManager.getUserPassword(user.getId());
							if (passwordDB.equals(oldPassword) && newPassword!=null && newPassword.length() >= 8 && newPassword.equals(confirmPassword)) {
								userManager.setPassword(user, newPassword);
								request.setAttribute("message", "Password modificata con successo");
							} else {
								request.setAttribute("message", "Impossibile modificare la password, controllare i parametri inseriti");
								request.setAttribute("error", true);
							}
							RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyAccount");
							rd.forward(request, response);	
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							request.setAttribute("message", "Impossibile modificare la password");
							request.setAttribute("error", true);
							
							RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyAccount");
							rd.forward(request, response);
						}
					}

				} else { // L'utente ha modificato 'fun'

				}

		} else {
			
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private boolean isValid(String s) {
		if ( s!=null && !s.equals("") ) {
			if ( !s.contains("<") || s.contains(">") || s.contains("$") || s.contains("?") ) {
				return true;
			}
		}
		return false;
	}

}
