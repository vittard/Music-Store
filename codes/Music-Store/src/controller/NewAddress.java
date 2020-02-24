package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Address;
import manager.AddressManager;
import manager.DriverManagerConnectionPool;

/**
 * Servlet implementation class NewAddress
 */
@WebServlet("/NewAddress")
public class NewAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewAddress() {
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
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		String cap = request.getParameter("zipCode");
		String state = request.getParameter("state");
		String phone = request.getParameter("phone");
		String userId = request.getParameter("userId");
		
		if (isValid(name) && isValid(surname) && isValid(street) && isValid(city) && isValid(province) && isValid(cap) && isValid(state) && isValid(userId)) {
			Address address = new Address();
			address.setName(name);
			address.setSurname(surname);
			address.setStreet(street);
			address.setCity(city);
			address.setProvince(province);
			address.setZipCode(cap);
			address.setState(state);
			address.setUserId( Integer.parseInt(userId) );
			
			
			if (isValid(phone))
				address.setPhone(phone);
			
			DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
			AddressManager manager = new AddressManager(dmcp);
			try {
				manager.doSave(address);
				request.setAttribute("message", "Indirizzo aggiunto con successo");
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("message", "Impossibile aggiungere l'indirizzo");
				request.setAttribute("error", true);
			}
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/MyAddresses");
			rd.forward(request, response);
			
		} else {
			request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
			request.setAttribute("error", true);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Address");
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
