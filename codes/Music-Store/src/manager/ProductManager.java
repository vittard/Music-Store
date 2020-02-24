package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import manager.DriverManagerConnectionPool;
import entities.Cart;
import entities.Category;
import entities.Order;
import entities.Product;

public class ProductManager implements ModelManager<Product>{

	private static final String TABLE_NAME = "music_store.product";
	private DriverManagerConnectionPool dmcp = null;

	public ProductManager(DriverManagerConnectionPool dmcp) {
		this.dmcp=dmcp;
	}

	@Override
	public Product doRetrieveByKey(String key) throws SQLException {
		Product product = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + ProductManager.TABLE_NAME + 
				" WHERE id=?";	

		if (key != null) {		


			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, key);

				ResultSet rs = preparedStatement.executeQuery();
				
				while(rs.next()) {

					product = new Product();
					product.setId(rs.getInt("id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setPrice(rs.getDouble("price"));
					product.setDisponibility(rs.getInt("disponibility"));
					product.setShippingPrice(rs.getDouble("shippingPrice"));
					product.setImagePath(rs.getString("imagePath"));

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
			return product;
		}
		return null;
	}

	@Override
	public Collection<Product> doRetrieveAll(String order) throws SQLException {

		ArrayList<Product> products = new ArrayList<Product>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + ProductManager.TABLE_NAME;	

		if(order != null) 
			insertSQL += " ORDER BY " + order;

		try {
			connection = dmcp.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);

			ResultSet rs = preparedStatement.executeQuery();


			while(rs.next()) {
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setName(rs.getString("name"));
				product.setDescription(rs.getString("description"));
				product.setPrice(rs.getDouble("price"));
				product.setDisponibility(rs.getInt("disponibility"));
				product.setShippingPrice(rs.getDouble("shippingPrice"));
				product.setImagePath(rs.getString("imagePath"));
				products.add(product);
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

		return products;

	}

	@Override
	public synchronized void doSave(Product product) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;


		String insertSQL = "INSERT INTO " + ProductManager.TABLE_NAME
				+ " (name, description, price, disponibility, shippingPrice, imagePath) VALUES (?, ?, ?, ?, ?, ?)";

		if (product != null && product.getId() > -1 && product.getName() != "" && product.getDescription() != "" && product.getPrice() >-1 && product.getDisponibility() >-1 && product.getShippingPrice() >-1) {



			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);

				preparedStatement.setString(1, product.getName());
				preparedStatement.setString(2, product.getDescription());
				preparedStatement.setDouble(3, product.getPrice());
				preparedStatement.setInt(4, product.getDisponibility());
				preparedStatement.setDouble(5, product.getShippingPrice());
				preparedStatement.setString(6, product.getImagePath());


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
	public void doUpdate(Product product) throws SQLException {
		if (product != null && product.getName()!=null ) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="UPDATE "+ ProductManager.TABLE_NAME +" SET name = ?, description = ?, price = ?, disponibility = ?, shippingPrice = ?, imagePath = ? WHERE (id = ?)";
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

			if ( id > -1 && name != null && description != null && price >-1 && shippingPrice >-1 && imagePath != null && disponibility > -1) {

				try {
					connection = dmcp.getConnection();
					preparedStatement = connection.prepareStatement(insertSQL);
					preparedStatement.setString(1, name);
					preparedStatement.setString(2,  description);
					preparedStatement.setDouble(3, price);
					preparedStatement.setInt(4, disponibility);
					preparedStatement.setDouble(5, shippingPrice);
					preparedStatement.setString(6, imagePath);
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
	public void doDelete( Product product ) throws SQLException {

		String id = Integer.toString( product.getId() );

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="DELETE FROM " + ProductManager.TABLE_NAME + " WHERE Id=?";

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

	public Collection<Product> doRetrieveAllByCategory ( Category category, String orderBy ) {
		ArrayList<Product> products = new ArrayList<Product>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		//select product.*, category.name as category from product inner join category inner join product_category where product.id = productId and category.name = categoryName and category.name = 'Chitarre'
		String insertSQL ="SELECT product.*, category.name as category FROM " + ProductManager.TABLE_NAME
				+ " inner join category inner join product_category where product.id = productID and category.name = categoryName and category.name = ?";

		if(orderBy != null) 
			insertSQL += " ORDER BY " + orderBy;
		
		if (category.getName() != null || category.getName().equals("")) {

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, category.getName());

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {
					Product product = new Product();
					product.setId(rs.getInt("id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setPrice(rs.getDouble("price"));
					product.setDisponibility(rs.getInt("disponibility"));
					product.setShippingPrice(rs.getDouble("shippingPrice"));
					product.setImagePath(rs.getString("imagePath"));
					products.add(product);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (connection != null)
						try {
							connection.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}

		}
		return products;
	}
	
	public HashMap<Product, Integer> doRetrieveAllByOrder ( Order order, String orderBy ) {
		String orderId = String.valueOf(order.getId());
		HashMap<Product, Integer> products = new HashMap<Product, Integer>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		//select product.*, order_product.* from product inner join order_product where id = productId and orderId = 13
		String insertSQL ="SELECT product.*, order_product.quantity FROM " + ProductManager.TABLE_NAME
				+ " inner join order_product where id = productId and orderId = ?";

		if(orderBy != null) 
			insertSQL += " ORDER BY " + orderBy;
		
		if (order.getId()>=0 && order.getState()!=null) {

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, orderId);

				ResultSet rs = preparedStatement.executeQuery();
				
				while(rs.next()) {
					Product product = new Product();
					product.setId(rs.getInt("id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setPrice(rs.getDouble("price"));
					product.setDisponibility(rs.getInt("disponibility"));
					product.setShippingPrice(rs.getDouble("shippingPrice"));
					product.setImagePath(rs.getString("imagePath"));
					products.put(product, rs.getInt("quantity"));
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if (connection != null)
						try {
							connection.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			}

		}
		return products;
	}

	public HashMap<Product, Integer> doRetrieveAllByCart ( Cart cart ) {
		HashMap<Product, Integer> products = new HashMap<Product, Integer>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		//select product.*, category.name as category from product inner join category inner join product_category where product.id = productId and category.name = categoryName and category.name = 'Chitarre'
		String insertSQL ="SELECT product.*, cart_product.cartId, cart_product.quantity FROM " + ProductManager.TABLE_NAME
				+ " inner join cart inner join cart_product where product.id = productId and cart.id = cartId and cart.Id = ?";

		if (cart.getId() >= 0) {

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setInt(1, cart.getId());

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {
					Product product = new Product();
					product.setId(rs.getInt("id"));
					product.setName(rs.getString("name"));
					product.setDescription(rs.getString("description"));
					product.setPrice(rs.getDouble("price"));
					product.setDisponibility(rs.getInt("disponibility"));
					product.setShippingPrice(rs.getDouble("shippingPrice"));
					product.setImagePath(rs.getString("imagePath"));
					products.put(product, rs.getInt("quantity") );
				}

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				try {
					if (preparedStatement != null)
						preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (connection != null)
						try {
							connection.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
				}
			}

		}
		return products;
	}
}
