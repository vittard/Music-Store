package manager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import manager.DriverManagerConnectionPool;
import manager.CartManager;
import entities.Cart;
import entities.Product;
import entities.User;

public class CartManager implements ModelManager<Cart>{

	private static final String TABLE_NAME = "music_store.cart"; 
	private DriverManagerConnectionPool dmcp = null; 

	public CartManager(DriverManagerConnectionPool dmcp) {
		this.dmcp=dmcp;
	}


	@Override
	public Cart doRetrieveByKey(String key) throws SQLException {


		if (key != null) {

			Cart cart = null;

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL = "SELECT * FROM " + CartManager.TABLE_NAME + " WHERE id = ?"; 		

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setInt(1, Integer.parseInt(key));

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {
					cart = new Cart();
					cart.setId( Integer.valueOf( rs.getString("id") ) );
					ProductManager manager = new ProductManager(dmcp);
					HashMap<Product, Integer> products = manager.doRetrieveAllByCart(cart);
					cart.setProducts(products);
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

			return cart;
		}
		return null;
	}


	@Override
	public synchronized void doSave(Cart cart) throws SQLException {

		if (cart != null) {

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL = "INSERT INTO " + CartManager.TABLE_NAME
					+ " (id) VALUES (?)";

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setInt(1, cart.getId());



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
		}}

	@Override
	public Collection<Cart> doRetrieveAll(String orderBy) throws SQLException {


		if (orderBy != null) {

			ArrayList<Cart> carts = new ArrayList<Cart>();

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="SELECT * FROM " + CartManager.TABLE_NAME;		

			if(orderBy != null && !orderBy.equals("")) 
				insertSQL += " ORDER BY " + orderBy;

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {
					Cart cart = new Cart();

					cart.setId( rs.getInt("id") );

					ProductManager manager = new ProductManager(dmcp);
					HashMap<Product, Integer> products = manager.doRetrieveAllByCart(cart);
					cart.setProducts(products);

					carts.add(cart);
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

			return carts;	
		}
		return null;

	}

	@Override
	public void doDelete(Cart cart) throws SQLException {
		String id = String.valueOf(cart.getId());
		if (id != null) {

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="DELETE FROM "+ CartManager.TABLE_NAME +" WHERE id=?";	

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

	@Override
	public void doUpdate(Cart cart) throws SQLException {
		if (cart != null && cart.getId() > -1 ) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="UPDATE `music_store`.`cart_product` SET `cartId` = ?, `productId` = ?, `quantity` = ? WHERE (`cartId` = ?) and (`productId` = ?)";

			int cartId = cart.getId();


			if ( cartId >= 0 ) {

				try {
					Iterator<Map.Entry<Product, Integer>> it = cart.getProducts().entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry<Product, Integer> e = it.next();
						Product product = e.getKey();
						Integer quantity = e.getValue();

						connection = dmcp.getConnection();
						preparedStatement = connection.prepareStatement(insertSQL);
						preparedStatement.setInt(1, cartId);
						preparedStatement.setInt(2, product.getId());
						preparedStatement.setInt(3, quantity);
						preparedStatement.setInt(4, cartId);
						preparedStatement.setInt(5, product.getId());

						preparedStatement.executeUpdate();


						connection.commit();
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
			}
		}

	}

	public Cart doRetrieveByUser(User user) throws SQLException {


		if (user != null) {

			Cart cart = null;

			Connection connection = null;
			PreparedStatement preparedStatement = null;

			String insertSQL ="SELECT * FROM " + CartManager.TABLE_NAME + 
					" WHERE id=?";		

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setInt(1, user.getId());

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {

					cart = new Cart();
					cart.setId( Integer.valueOf( rs.getString("id") ) );
					ProductManager manager = new ProductManager(dmcp);
					HashMap<Product, Integer> products = manager.doRetrieveAllByCart(cart);
					cart.setProducts(products);
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

			return cart;
		}
		return null;
	}

	public boolean addProduct(Cart cart, Product product) throws SQLException {
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

					if (cart.getProducts().containsKey(product)) {
						String insertSQL = "UPDATE `music_store`.`cart_product` SET `quantity` = ? WHERE (`cartId` = ?) and (`productId` = ?)";
						connection = dmcp.getConnection();
						preparedStatement = connection.prepareStatement(insertSQL);
						preparedStatement.setInt(1, cart.getQuantityOfProduct(product) + 1 );
						preparedStatement.setInt(2, cart.getId());
						preparedStatement.setInt(3, product.getId());

						int rs = preparedStatement.executeUpdate();

						connection.commit();
						if (rs>0) {
							cart.addProduct(product);
							flag = true;
						}

					} else {
						String insertSQL = "INSERT INTO music_store.cart_product (cartId, productId, quantity) VALUES (?, ?, ?);";
						connection = dmcp.getConnection();
						preparedStatement = connection.prepareStatement(insertSQL);
						preparedStatement.setInt(1, cart.getId());
						preparedStatement.setInt(2, product.getId());
						preparedStatement.setInt(3, 1);
						
						
						int rs = preparedStatement.executeUpdate();
						if (rs>0) {
							cart.addProduct(product);
							flag = true;
						}
						connection.commit();
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

			}


		}
		return flag;
	}

	public boolean removeProduct(Cart cart, Product product) throws SQLException {
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

					String insertSQL = "DELETE FROM `music_store`.`cart_product` WHERE (`cartId` = ?) and (`productId` = ?);";
					connection = dmcp.getConnection();
					preparedStatement = connection.prepareStatement(insertSQL);
					preparedStatement.setInt(1, cart.getId());
					preparedStatement.setInt(2, product.getId());

					int rs = preparedStatement.executeUpdate();

					connection.commit();
					if (rs>0) {
						cart.removeProduct(product);
						flag = true;
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

			}


		}
		return flag;
	}

	public boolean setQuantity(Cart cart, Product product, int quantity) throws SQLException {
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

					if (cart.getProducts().containsKey(product)) {
						String insertSQL = "UPDATE music_store.cart_product SET quantity = ? WHERE (cartId = ?) and (productId = ?);";
						connection = dmcp.getConnection();
						preparedStatement = connection.prepareStatement(insertSQL);
						preparedStatement.setInt(1, quantity );
						preparedStatement.setInt(2, cart.getId());
						preparedStatement.setInt(3, product.getId());

						int rs = preparedStatement.executeUpdate();

						connection.commit();
						if (rs>0) {
							cart.setQuantityOfProduct(product, quantity);
							flag = true;
						}
					} else {

						String insertSQL = "INSERT INTO music_store.cart_product (cartId, productId, quantity) VALUES (?, ?, ?)";
						connection = dmcp.getConnection();
						preparedStatement = connection.prepareStatement(insertSQL);
						preparedStatement.setInt(1, cart.getId());
						preparedStatement.setInt(2, product.getId());
						preparedStatement.setInt(3, 1);

						int rs = preparedStatement.executeUpdate();
						
						if (rs>0)
							flag = true;
						connection.commit();
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

			}


		}
		return flag;
	}


}


