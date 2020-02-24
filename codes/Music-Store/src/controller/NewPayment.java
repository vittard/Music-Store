package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Payment;
import manager.DriverManagerConnectionPool;
import manager.PaymentManager;

/**
 * Servlet implementation class NewAddress
 */
@WebServlet("/NewPayment")
public class NewPayment extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewPayment() {
        super();
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
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String cardNumber = request.getParameter("cardNumber");
		String date = request.getParameter("date");
		String cvv = request.getParameter("cvv");
		String userId = request.getParameter("userId");
		
		if (isValid(name) && isValid(surname) && isValid(cardNumber) && isValid(date) && isValid(cvv) && isValid(userId) && cardNumber.length() == 16 ) {
			Payment payment = new Payment();
			payment.setName(name);
			payment.setSurname(surname);
			payment.setCardNumber(cardNumber);
			payment.setExpiryDate(Date.valueOf(date));
			payment.setCvv(Integer.valueOf(cvv));
			payment.setUserId(Integer.valueOf(userId));
			
			DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
			PaymentManager manager = new PaymentManager(dmcp);
			try {
				manager.doSave(payment);
				request.setAttribute("message", "Metodo di pagamento aggiunto con successo");
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("message", "Impossibile aggiungere il metodo di pagamento");
				request.setAttribute("error", true);
			}
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyPayments");
			rd.forward(request, response);
			
		} else {
			request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
			request.setAttribute("error", true);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Payment");
			rd.forward(request, response);
		}
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
