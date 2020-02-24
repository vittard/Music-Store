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
 * Servlet implementation class EditAddress
 */
@WebServlet("/EditAddress")
public class EditAddress extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditAddress() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String street = request.getParameter("street");
		String city = request.getParameter("city");
		String province = request.getParameter("province");
		String cap = request.getParameter("zipCode");
		String state = request.getParameter("state");
		String phone = request.getParameter("phone");
		String userId = request.getParameter("userId");
		String id = request.getParameter("id");
		
		if (isValid(name) && isValid(surname) && isValid(street) && isValid(city) && isValid(province) && isValid(cap) && isValid(state) && isValid(id) && isValid(userId)) {
			Address address = new Address();
			address.setName(name);
			address.setSurname(surname);
			address.setStreet(street);
			address.setCity(city);
			address.setProvince(province);
			address.setZipCode(cap);
			address.setState(state);
			address.setUserId( Integer.parseInt(userId) );
			address.setId( Integer.parseInt(id) );
			
			
			if (isValid(phone))
				address.setPhone(phone);
			
			DriverManagerConnectionPool dmcp = new DriverManagerConnectionPool();
			AddressManager manager = new AddressManager(dmcp);
			try {
				manager.doUpdate(address);
				request.setAttribute("message", "Indirizzo modifcato con successo");
			} catch (SQLException e) {
				e.printStackTrace();
				request.setAttribute("message", "Impossibile modificare l'indirizzo");
				request.setAttribute("error", true);
			}
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Address?id=" + address.getId());
			rd.forward(request, response);
			
		} else {
			request.setAttribute("message", "Alcuni dei parametri insierti non sono validi");
			request.setAttribute("error", true);
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/Address?id=" + id);
			rd.forward(request, response);
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
