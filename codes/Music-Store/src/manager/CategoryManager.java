package manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import entities.Category;
import entities.Product;

public class CategoryManager implements ModelManager<Category>{

	private static final String TABLE_NAME = "music_store.category";
	private DriverManagerConnectionPool dmcp = null;


	public CategoryManager(DriverManagerConnectionPool dmcp) {
		this.dmcp=dmcp;
	}


	@Override
	public Category doRetrieveByKey(String code) throws SQLException {
		Category category = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + CategoryManager.TABLE_NAME + 
				" WHERE name=?";	

		if (code != null) {		


			try {
				
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, code);

				ResultSet rs = preparedStatement.executeQuery();


				while(rs.next()) {
					category = new Category();
					category.setName(rs.getString("name"));
					category.setId(rs.getInt("id"));

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


			return category;
		}
		return null;
	}


	@Override
	public Collection<Category> doRetrieveAll(String orderBy) throws SQLException {

		ArrayList<Category> categories = new ArrayList<Category>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="SELECT * FROM " + CategoryManager.TABLE_NAME;	

		if(orderBy != null) 
			insertSQL += " ORDER BY " + orderBy;

		try {
			connection = dmcp.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);

			ResultSet rs = preparedStatement.executeQuery();


			while(rs.next()) {
				Category category = new Category();

				category.setName(rs.getString("name"));
				category.setId(rs.getInt("id"));

				categories.add(category);
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

		return categories;

	}


	@Override
	public void doSave(Category category) throws SQLException {

		Connection connection = null;
		PreparedStatement preparedStatement = null;


		String insertSQL = "INSERT INTO " + CategoryManager.TABLE_NAME
		+ " (name) VALUES (?)";

		if (category != null && category.getName() != "" ) {



			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);

				preparedStatement.setString(1, category.getName());

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
	public void doUpdate(Category category) throws SQLException {
		String name = category.getName();
		int id = category.getId();
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="UPDATE " + CategoryManager.TABLE_NAME + " SET name = ? WHERE (id=?)";	
		System.out.println("Nome categoria =" + name);

		if (name != null) {

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, name);
				preparedStatement.setInt(2, id);

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
	public void doDelete(Category category) throws SQLException {
		String name = category.getName() ;

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL ="DELETE FROM " + CategoryManager.TABLE_NAME + " WHERE name=?";	
		System.out.println("Nome della categoria =" + name);

		if (name != null) {

			try {
				connection = dmcp.getConnection();
				preparedStatement = connection.prepareStatement(insertSQL);
				preparedStatement.setString(1, name);

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
	
	public Collection<Category> doRetrieveAllByProduct( Product product, String orderBy ) throws SQLException {

		ArrayList<Category> categories = new ArrayList<Category>();

		Connection connection = null;
		PreparedStatement preparedStatement = null;

		String insertSQL = "SELECT category.*, product.id FROM product inner join category inner join product_category where product.id = productID and category.name = categoryName and product.id = ?";

		if ( orderBy != null ) 
			insertSQL += " ORDER BY " + orderBy;

		try {
			connection = dmcp.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setInt(1, product.getId());

			ResultSet rs = preparedStatement.executeQuery();


			while(rs.next()) {
				Category category = new Category();

				category.setName(rs.getString("name"));
				category.setId(rs.getInt("id"));

				categories.add(category);
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

		return categories;
	}
}
