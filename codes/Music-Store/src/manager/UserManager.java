package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import manager.DriverManagerConnectionPool;
import manager.UserManager;
import entities.User;

/**
 * This class is used for get details connection of user from database
 * @author Vittorio
 *
 */
public class UserManager implements ModelManager<User>{

	private static final String TABLE_NAME = "music_store.user"; //il nome della tabella degli utenti e' 'user'
	private DriverManagerConnectionPool dmcp = null; //non capisco dove viene usato dmcp

	/**
	 * This is the Constructor
	 * @param dmcp this param is a type of DriverManagerConncetionPool, that contains the paramters for database connection
	 */
	public UserManager(DriverManagerConnectionPool dmcp) {
		this.dmcp=dmcp;
	}


	@Override
	public User doRetrieveByKey(String key) throws SQLException {


		if (key != null) {

			User user = null;

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="SELECT * FROM " + UserManager.TABLE_NAME + 
					" WHERE id=?";		

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, key);

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setSurname(rs.getString("surname"));
					user.setEmail(rs.getString("email"));
					user.setType(rs.getInt("type"));

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

			return user;
		}
		return null;
	}

	@Override
	public Collection<User> doRetrieveAll(String orderBy) throws SQLException {


		if (orderBy != null) {

			ArrayList<User> users = new ArrayList<User>();

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="SELECT * FROM " + UserManager.TABLE_NAME;		

			if(orderBy != null && !orderBy.equals("")) 
				insertSQL += " ORDER BY " + orderBy;

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {
					User user = new User();

					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setSurname(rs.getString("surname"));
					user.setEmail(rs.getString("email"));
					user.setType(rs.getInt("type"));


					users.add(user);
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

			return users;	
		}
		return null;

	}

	@Override
	public void doSave(User user) throws SQLException {
		doSave(user, null);
	}

	@Override
	public void doUpdate(User user) throws SQLException {
		if (user != null && user.getName()!=null ) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="UPDATE "+ UserManager.TABLE_NAME +" SET name = ?, surname = ?, email = ?, type = ? WHERE (id = ?)";


			if (user != null) {

				try {
					connection = dmcp.getConnection();
					preparedStatement = connection.prepareStatement(insertSQL);
					preparedStatement.setString(1, user.getName());
					preparedStatement.setString(2,  user.getSurname());
					preparedStatement.setString(3, user.getEmail());
					preparedStatement.setInt(4, user.getType());
					preparedStatement.setInt(5, user.getId());

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
	public void doDelete(User user) throws SQLException {
		if (user != null ) {
			String id = String.valueOf(user.getId());
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="DELETE FROM "+ UserManager.TABLE_NAME +" WHERE id=?";	

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

	}

	public void doSave(User user, String password) throws SQLException {

		if ( user != null || password.equals("") || password!=null ) {

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL = "INSERT INTO " + UserManager.TABLE_NAME
					+ " (name, surname, email, password, type) VALUES (?, ?, ?, ?, ?)";

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, user.getName());
				preparedStatement.setString(2, user.getSurname());
				preparedStatement.setString(3, user.getEmail());
				preparedStatement.setString(4, password);
				preparedStatement.setInt(5, user.getType());



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

	/**
	 * 
	 * @param email, email corrispondente all'utente da cercare nel database
	 * @return l'utente, nel caso in cui esistam, un utente null nel caso in cui non esista
	 * @throws SQLException eccezione del database
	 */
	public User doRetriveByEmailAndPassword(String email, String password) throws SQLException {
		if (email != null) {

			User user = new User();

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="SELECT * FROM " + UserManager.TABLE_NAME + 
					" WHERE email=? and password=?";		

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, email);
				preparedStatement.setString(2, password);

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {

					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setSurname(rs.getString("surname"));
					user.setEmail(rs.getString("email"));
					user.setType(rs.getInt("type"));

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

			return user;
		}
		return null;
	}

	/**
	 * Metodo utilizzabile per ottenere la password dell'utente
	 * @param userId -> corrisponde all'id dell'utente
	 * @return --> null in caso di problemi, la password dell'utente in caso di successo
	 * @throws SQLException --> eccezione di SQL da gestire
	 */
	public String getUserPassword(int userId) throws SQLException {
		String password = null;
		if (userId > -1) {

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="SELECT password FROM " + UserManager.TABLE_NAME + 
					" WHERE id=?";		

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setInt(1, userId);

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {

					password = rs.getString("password");

				}


			} finally { // Chiudo le connessioni
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} finally {
					if (connection != null)
						connection.close();
				}
			}

		}
		return password;
	}
	
	public boolean setPassword(User user, String password) throws SQLException {
		
		if (password!=null && !password.equals("") && user!=null) {
			String insertSQL = "UPDATE " + UserManager.TABLE_NAME + " SET password = ? WHERE (id = ?)";
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				
				preparedStatement.setString(1, password);
				preparedStatement.setInt(2, user.getId() );

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
		
		return false;
	}
	
	public User doRetrieveByEmail(String email) throws SQLException {


		if (email != null) {

			User user = null;

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="SELECT * FROM " + UserManager.TABLE_NAME + 
					" WHERE email=?";		

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, email);

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setSurname(rs.getString("surname"));
					user.setEmail(rs.getString("email"));
					user.setType(rs.getInt("type"));

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

			return user;
		}
		return null;
	}

}
