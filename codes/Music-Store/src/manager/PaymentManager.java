package manager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import entities.Payment;
import entities.User;

public class PaymentManager implements ModelManager<Payment>{

	private static final String TABLE_NAME = "music_store.payment";
	private DriverManagerConnectionPool dmcp = null;

	
	public PaymentManager(DriverManagerConnectionPool dmcp) {
		this.dmcp = dmcp;
	}

	@Override
	public Payment doRetrieveByKey(String code) throws SQLException {


		Payment payement = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + PaymentManager.TABLE_NAME + 
				" WHERE id=?";	

		if (code != null) {		


			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, code);

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {
					payement = new Payment();

					payement.setId(rs.getInt("id"));
					payement.setCardNumber(rs.getString("cardNumber"));
					payement.setName(rs.getString("name"));
					payement.setSurname(rs.getString("surname"));
					payement.setExpiryDate(rs.getDate("expiryDate"));
					payement.setCvv(rs.getInt("cvv"));
					payement.setUserId(rs.getInt("userId"));

				}
			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}


			return payement;
		}
		return null;
	}


	@Override
	public Collection<Payment> doRetrieveAll(String orderBy) throws SQLException {

		ArrayList<Payment> payements = new ArrayList<Payment>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + PaymentManager.TABLE_NAME;	

		if(orderBy != null) 
			insertSQL += " ORDER BY " + orderBy;

		try {
			connection = dmcp.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);

			ResultSet rs = preparedStatement.executeQuery();


			while(rs.next()) {
				Payment payement = new Payment();
				payement.setId(rs.getInt("id"));
				payement.setCardNumber(rs.getString("cardNumber"));
				payement.setName(rs.getString("name"));
				payement.setSurname(rs.getString("surname"));
				payement.setExpiryDate(rs.getDate("expiryDate"));
				payement.setCvv(rs.getInt("cvv"));
				payement.setUserId(rs.getInt("userId"));
				payements.add(payement);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}

		return payements;
	}

	@Override
	public void doSave(Payment payement) throws SQLException {

		Connection connection = null;

		if(payement != null && payement.getCardNumber() != "") {

			PreparedStatement preparedStatement = null;

			String insertSQL = "INSERT INTO " + PaymentManager.TABLE_NAME
					+ " ( cardNumber, name, surname, expiryDate, cvv, userId) VALUES ( ?, ?, ?, ?, ?, ?)";


			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, payement.getCardNumber());
				preparedStatement.setString(2, payement.getName());
				preparedStatement.setString(3, payement.getSurname());
				preparedStatement.setString(4, payement.getExpiryDate().toString());
				preparedStatement.setInt(5, payement.getCvv());
				preparedStatement.setInt(6, payement.getUserId());


				preparedStatement.executeUpdate();

				connection.commit();
			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}
		}		

	}

	@Override
	public void doUpdate(Payment payement) throws SQLException {
		if (payement != null && payement.getId() > -1 ) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="UPDATE "+ PaymentManager.TABLE_NAME +" SET cardNumber = ?, name = ?, surname = ?, expiryDate = ?, cvv = ?, userId = ? WHERE (id = ?)";

			int id = payement.getId();
			String cardNumber = payement.getCardNumber();
			String name = payement.getName();
			String surname = payement.getSurname();
			Date expiryDate = payement.getExpiryDate();
			int cvv = payement.getCvv();
			int userId = payement.getUserId();


			if ( id >= 0 && cardNumber != null && name != null && surname != null &&  expiryDate != null && cvv >= 0 && userId >= 0) {

				try {
					connection = dmcp.getConnection();
					preparedStatement = connection.prepareStatement(insertSQL);
					preparedStatement.setString(1, cardNumber);
					preparedStatement.setString(2, name);
					preparedStatement.setString(3,  surname);
					preparedStatement.setDate(4,  expiryDate);
					preparedStatement.setInt(5, cvv);
					preparedStatement.setInt(6, userId);
					preparedStatement.setInt(7, id);

					preparedStatement.executeUpdate();


					connection.commit();
				} finally {
					try {
						if (preparedStatement != null)
							preparedStatement.close();
					} finally {
						if (connection != null)
							connection.close();
					}
				}		
			}
		}
	}

	@Override
	public void doDelete(Payment payement) throws SQLException {
		String id = Integer.toString( payement.getId() );

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="DELETE FROM " + PaymentManager.TABLE_NAME + " WHERE id=?";

		if (id != null) {

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, id);

				preparedStatement.executeUpdate();


				connection.commit();
			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}     
			}		


		}

	}

	public Collection<Payment> doRetrieveAllByUser (User user, String orderBy) throws SQLException {
		ArrayList<Payment> payements = new ArrayList<Payment>();

		String id = Integer.toString( user.getId() );

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + PaymentManager.TABLE_NAME + " where userId = ? ";
		
		if ( orderBy != null ) 
			insertSQL += " ORDER BY " + orderBy;

		if (id != null) {

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, id);
				
				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {
					Payment payement = new Payment();
					payement.setId(rs.getInt("id"));
					payement.setCardNumber(rs.getString("cardNumber"));
					payement.setName(rs.getString("name"));
					payement.setSurname(rs.getString("surname"));
					payement.setExpiryDate(rs.getDate("expiryDate"));
					payement.setCvv(rs.getInt("cvv"));
					payement.setUserId(rs.getInt("userId"));
					payements.add(payement);
				}

			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}

			return payements;

		}

		return payements;
	}
}


