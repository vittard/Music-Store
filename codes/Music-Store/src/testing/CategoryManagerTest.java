package testing;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import entities.Category;
import entities.Product;
import manager.CategoryManager;
import manager.DriverManagerConnectionPool;
import manager.ProductManager;

public class CategoryManagerTest {


	@Test
	public void test() throws SQLException{

		Category c = new Category("Chitarre", 5); 
		Category c2 = new Category("Batteria Elettronica", 8); 
		CategoryManager cm = new CategoryManager(new DriverManagerConnectionPool());

		//doRetrieveByKey
		Category c1 = cm.doRetrieveByKey("Chitarre");

		assertEquals(true,c.equals(c1));

		//doRetrieveAll
		ArrayList<Category> list = (ArrayList<Category>) cm.doRetrieveAll("id");

		assertEquals(true, list.get(7).equals(c2));
		assertEquals(true,list.get(4).equals(c));

		//doSave
		Category c3= new Category("Piano", 14);
		cm.doSave(c3);

		Category c4 = (Category)cm.doRetrieveByKey("Piano");

		assertEquals(true, c3.equals(c4));

		//doUpdate
		Category c5 = new Category("Flauto", 14);
		cm.doUpdate(c5);

		Category c6 = (Category)cm.doRetrieveByKey("Flauto");

		assertEquals(true, c5.equals(c6));

		//doDelete
		Category c7 = (Category)cm.doRetrieveByKey("Flauto");
		cm.doDelete(c7);
		Category aEl = (Category)cm.doRetrieveByKey("Flauto");
		assertNull(aEl);

		//doRetrieveAllByProduct
		ArrayList<Category> ct;
		ProductManager pm = new ProductManager(new DriverManagerConnectionPool());
		Product p= pm.doRetrieveByKey("5");
		
		ct = new ArrayList<Category> ( cm.doRetrieveAllByProduct(p, "category.id") );
		
		Category category1 = new Category("Chitarre",5);
		Category category2 = new Category("Chitarra Elettrica",6);
		
		for (Category category : ct) {
			assertEquals(true, ( category.equals(category1) || category.equals(category2) ) );
		}
	}

}
