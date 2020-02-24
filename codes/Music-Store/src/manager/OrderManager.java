package manager;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import entities.Order;
import entities.Product;
import entities.User;
public class OrderManager implements ModelManager<Order>{

	private static final String TABLE_NAME = "music_store.order";
	private DriverManagerConnectionPool dmcp = null;

	public OrderManager(DriverManagerConnectionPool dmcp) {
		this.dmcp=dmcp;
	}

	@Override
	public Order doRetrieveByKey(String code) throws SQLException {

		Order order = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + OrderManager.TABLE_NAME + 
				" WHERE id=?";	

		if (code != null) {		


			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, code);

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {

					order = new Order();
					order.setId(rs.getInt("id"));
					order.setState(rs.getString("state"));
					order.setDate(rs.getDate("date"));
					order.setUserId(rs.getInt("userId"));
					order.setAddressId(rs.getInt("addressId"));

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

			return order;
		}
		return null;
	}

	@Override
	public Collection<Order> doRetrieveAll(String orderBy) throws SQLException {

		ArrayList<Order> orders = new ArrayList<Order>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + OrderManager.TABLE_NAME;	

		if(orderBy != null) 
			insertSQL += " ORDER BY " + orderBy;

		try {
			connection = dmcp.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);

			ResultSet rs = preparedStatement.executeQuery();


			while(rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setState(rs.getString("state"));
				order.setDate(rs.getDate("date"));
				order.setUserId(rs.getInt("userId"));
				order.setAddressId(rs.getInt("addressId"));
				orders.add(order);
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

		return orders;
	}

	@Override
	public void doSave(Order order) throws SQLException {

		Connection connection = null;

		if(order != null && order.getState() != "") {

			PreparedStatement preparedStatement = null;

			String insertSQL = "INSERT INTO " + OrderManager.TABLE_NAME
					+ " ( state, date, userID, addressID) VALUES ( ?, ?, ?, ?)";


			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, order.getState());
				preparedStatement.setString(2, order.getDate().toString());
				preparedStatement.setInt(3, order.getUserId());
				preparedStatement.setInt(4, order.getAddressId());


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
	public void doUpdate(Order order) throws SQLException {

		if (order != null && order.getId()!= 0 ) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="UPDATE "+ OrderManager.TABLE_NAME +" SET state = ?, date = ?, userId = ?, addressId = ? WHERE (id = ?)";

			int id = order.getId();
			String state = order.getState();
			Date date = order.getDate();
			int userId = order.getUserId();
			int addressId = order.getAddressId();


			if ( id < 0 || state != null || date != null || userId < 0 || addressId < 0) {

				try {
					connection = dmcp.getConnection();
					preparedStatement = connection.prepareStatement(insertSQL);
					preparedStatement.setString(1, state);
					preparedStatement.setString(2,  date.toString());
					preparedStatement.setInt(3, userId);
					preparedStatement.setInt(4, addressId);
					preparedStatement.setInt(5, id);

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
	public void doDelete(Order product) throws SQLException {
		String id = Integer.toString( product.getId() );

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="DELETE FROM " + OrderManager.TABLE_NAME + " WHERE id=?";

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

	public Collection<Order> doRetrieveAllByUser (User user, String orderBy) throws SQLException {
		String userId = String.valueOf(user.getId());

		ArrayList<Order> orders = new ArrayList<Order>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + OrderManager.TABLE_NAME + " WHERE userID = ?";	

		if(orderBy != null) 
			insertSQL += " ORDER BY " + orderBy;

		try {
			connection = dmcp.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, userId);

			ResultSet rs = preparedStatement.executeQuery();


			while(rs.next()) {
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setState(rs.getString("state"));
				order.setDate(rs.getDate("date"));
				order.setUserId(rs.getInt("userId"));
				order.setAddressId(rs.getInt("addressId"));
				orders.add(order);
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

		return orders;
	}

	public boolean addProduct(Order order, Product product, int quantity) throws SQLException {
		boolean flag = false;
		if (product != null) {

			int id = product.getId();
			String name = product.getName();
			String description = product.getDescription();
			if (description.equals("") || description == null) {
				description = " ";
			}
			double price = product.getPrice();
			double shippingPrice = product.getShippingPrice();
			String imagePath = product.getImagePath();
			int disponibility = product.getDisponibility();

			if (id >= 0 && name!=null && description != null && price >= 0 && shippingPrice >= 0 && imagePath != null && disponibility >= 0) {
				
				Connection connection = null;
				PreparedStatement preparedStatement = null;

				try {


					String insertSQL = "INSERT INTO music_store.order_product (orderId, productId, quantity) VALUES (?, ?, ?)";
					connection = dmcp.getConnection();
					preparedStatement = connection.prepareStatement(insertSQL);
					preparedStatement.setInt(1, order.getId());
					preparedStatement.setInt(2, product.getId());
					preparedStatement.setInt(3, quantity);
					
					int rs = preparedStatement.executeUpdate();
					if (rs>0) {
						flag = true;
					}
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
		return flag;
	}
}