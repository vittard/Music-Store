/**
 * 
 */
package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import entities.Address;
import entities.User;

/**
 * @author Vittorio
 *
 */
public class AddressManager implements ModelManager<Address> {

	private static final String TABLE_NAME = "music_store.address";
	private DriverManagerConnectionPool dmcp = null;

	public AddressManager(DriverManagerConnectionPool dmcp) {
		this.dmcp=dmcp;
	}

	@Override
	public Address doRetrieveByKey(String key) throws SQLException {


		Address address = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + AddressManager.TABLE_NAME + 
				" WHERE id=?";	

		if (key != null) {		


			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, key);

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {

					address = new Address();
					address.setId(rs.getInt("id"));
					address.setName(rs.getString("name"));
					address.setSurname(rs.getString("surname"));
					address.setStreet(rs.getString("street"));
					address.setCity(rs.getString("city"));
					address.setProvince(rs.getString("province"));
					address.setZipCode(rs.getString("zipCode"));
					address.setState(rs.getString("state"));
					address.setPhone(rs.getString("phone"));
					address.setUserId(rs.getInt("userID"));

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


			return address;
		}
		return null;


	}

	@Override
	public Collection<Address> doRetrieveAll(String orderBy) throws SQLException {

		ArrayList<Address> addresses = new ArrayList<Address>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + AddressManager.TABLE_NAME;	

		if(orderBy != null) 
			insertSQL += " ORDER BY " + orderBy;

		try {
			connection = dmcp.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);

			ResultSet rs = preparedStatement.executeQuery();


			while(rs.next()) {
				Address address = new Address();

				address.setId(rs.getInt("id"));
				address.setName(rs.getString("name"));
				address.setSurname(rs.getString("surname"));
				address.setStreet(rs.getString("street"));
				address.setCity(rs.getString("city"));
				address.setProvince(rs.getString("province"));
				address.setZipCode(rs.getString("zipCode"));
				address.setState(rs.getString("state"));
				address.setPhone(rs.getString("phone"));
				address.setUserId(rs.getInt("userID"));



				addresses.add(address);
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

		return addresses;



	}

	@Override
	public void doSave(Address address) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;


		String insertSQL = "INSERT INTO " + AddressManager.TABLE_NAME
				+ " (name, surname, street, city , province, zipCode, state, phone, userID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		if (address != null && address.getName() != "" && address.getSurname() != "" && address.getStreet() != "" && address.getCity() != "" && address.getProvince() != "" && address.getZipCode() != "" && address.getState() != "" && address.getUserId() > -1) {


			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);

				preparedStatement.setString(1, address.getName());
				preparedStatement.setString(2, address.getSurname());
				preparedStatement.setString(3, address.getStreet());
				preparedStatement.setString(4, address.getCity());
				preparedStatement.setString(5, address.getProvince());
				preparedStatement.setString(6, address.getZipCode());
				preparedStatement.setString(7, address.getState());
				preparedStatement.setString(8, address.getPhone());
				preparedStatement.setInt(9, address.getUserId());


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
	public void doUpdate(Address address) throws SQLException {


		if (address != null && address.getName()!=null ) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="UPDATE "+ AddressManager.TABLE_NAME +" SET name = ?, surname = ?, street = ?,city = ?, province = ?, zipCode = ?, state = ? , phone = ? , userId = ? WHERE (id = ?)";

			int id = address.getId();
			String name = address.getName();
			String surname = address.getSurname();
			String street = address.getStreet();
			String city = address.getCity();
			String province = address.getProvince();
			String ziCode = address.getZipCode();
			String state = address.getState();
			String phone = address.getPhone();
			int userId = address.getUserId();


			if ( id > -1 && name != null && surname != null &&  street != null && city != null && province != null&& ziCode != null&& state != null && userId > -1) {

				try {
					connection = dmcp.getConnection();
					preparedStatement = connection.prepareStatement(insertSQL);
					preparedStatement.setString(1, name);
					preparedStatement.setString(2,  surname);
					preparedStatement.setString(3, street);
					preparedStatement.setString(4, city);
					preparedStatement.setString(5,  province);
					preparedStatement.setString(6, ziCode);
					preparedStatement.setString(7,  state);
					preparedStatement.setString(8,  phone);
					preparedStatement.setInt(9, userId);
					preparedStatement.setInt(10, id);

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
	public void doDelete(Address address) throws SQLException {

		String id = Integer.toString( address.getId() );

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="DELETE FROM " + AddressManager.TABLE_NAME + " WHERE id=?";	
		System.out.println("Id del prodotto =" + id);

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

	public Collection<Address> doRetrieveByUser (User user, String orderBy) throws SQLException {
		ArrayList<Address> addresses = new ArrayList<Address>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + AddressManager.TABLE_NAME + 
				" WHERE userID = ?";	

		int userId = user.getId();

		if(orderBy != null) 
			insertSQL += " ORDER BY " + orderBy;

		try {
			connection = dmcp.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, userId);

			ResultSet rs = preparedStatement.executeQuery();


			while(rs.next()) {
				Address address = new Address();

				address.setId(rs.getInt("id"));
				address.setName(rs.getString("name"));
				address.setSurname(rs.getString("surname"));
				address.setStreet(rs.getString("street"));
				address.setCity(rs.getString("city"));
				address.setProvince(rs.getString("province"));
				address.setZipCode(rs.getString("zipCode"));
				address.setState(rs.getString("state"));
				address.setPhone(rs.getString("phone"));
				address.setUserId(rs.getInt("userID"));



				addresses.add(address);
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

		return addresses;
	}


}
