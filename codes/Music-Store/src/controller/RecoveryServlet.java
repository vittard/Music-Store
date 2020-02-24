package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.User;
import manager.DriverManagerConnectionPool;
import manager.UserManager;

//for email
import java.util.*; 
import javax.mail.*; 
import javax.mail.PasswordAuthentication;
import javax.mail.internet.*;

/**
 * Servlet implementation class RecoveryServlet
 */
@WebServlet("/Recovery")
public class RecoveryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecoveryServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		
		if ( email != null && email.contentEquals("") ) {
			
			DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
			UserManager mangaer = new UserManager(dmcp);
			try {
				User user = mangaer.doRetrieveByEmail(email);
				if (user != null) {
					String password = generatePassword();
					sendEmail(user, password);
					mangaer.setPassword(user, password);
					request.setAttribute("message", "Nuovi dati di accesso ricevuti sulla email inserita");
					RequestDispatcher rd = getServletContext().getRequestDispatcher("/Login");
					rd.forward(request, response);
				} else {
					request.setAttribute("message", "Non ci sono utenti che corrispondono ai prametri inseriti");
					request.setAttribute("error", true);
				}
			} catch (SQLException e) {
				request.setAttribute("message", "Problemi di connessione con il database, riprova tra un minuto");
				request.setAttribute("error", true);
				e.printStackTrace();
				e.printStackTrace();
			}
			
			
		} else {
			request.setAttribute("message", "inserire una email valida");
			request.setAttribute("error", true);
		}
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/RecoveryPage");
		rd.forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void sendEmail(User user, String userPassword) {
		String username = "worktest636@gmail.com";
		String password = "jknYj5RVQv8vH79";
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("worktest636@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(user.getEmail()));
			message.setSubject("Nuovi dati di accesso");
			message.setText("Caro " + user.getName() + " " + user.getSurname()
					+ "\n password: " + userPassword
					+ "\nCi vediamo su Music-Store");
			Transport.send(message);
			System.out.println("Done");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String generatePassword() {
		String password = "";
		
		for (int i = 0; i<10; i++) {
			Random r = new Random();
			password = password + r.nextInt((9 - 0) + 1);
		}
		
		return password;
	}


}
