package testing;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import entities.Cart;
import entities.Product;
import entities.User;
import manager.CartManager;
import manager.DriverManagerConnectionPool;
import manager.ProductManager;

public class CartManagerTest {

	@Test
	public void test() throws SQLException {


		Cart c = new Cart();

		ProductManager pm= new ProductManager(new DriverManagerConnectionPool());

		Product p1=(Product)pm.doRetrieveByKey("5");
		Product p2=(Product)pm.doRetrieveByKey("7");

		c.setId(6);
		c.addProduct(p1);
		c.addProduct(p2);
		c.addProduct(p1);

		CartManager cm = new CartManager(new DriverManagerConnectionPool());
		
		//doRetrieveByKey
		Cart c1 = (Cart)cm.doRetrieveByKey("6");

		assertEquals(true,c.equals(c1));

		//doSave 

		Cart add= new Cart();
		add.setId(23);

		cm.doSave(add);

		Cart add1 = (Cart)cm.doRetrieveByKey("23");

		assertEquals(true, add.equals(add1));


		//doDelete
		Cart c3 = (Cart)cm.doRetrieveByKey("3");
		cm.doDelete(c3);
		Cart cEl = (Cart)cm.doRetrieveByKey("3");
		assertNull(cEl);


		//doRetrieveByUser 

		User user = new User(6, "Libera", "Trentino", "LiberaTrentino@dayrep.com", 0);
		Cart ad = cm.doRetrieveByUser(user);

		assertEquals(true, ad.getId() == user.getId());
		
		//addProduct 

		Cart cart = cm.doRetrieveByKey("5");
		Product product = pm.doRetrieveByKey("7");

		assertEquals(true, cm.addProduct(cart, product));		


		//removeProduct

		Cart cartRemove = cm.doRetrieveByKey("5");
		Product productRemove = pm.doRetrieveByKey("3");

		assertEquals(true,cm.removeProduct(cartRemove, productRemove));
		
		//setQuantity 
		
		Cart c5 = cm.doRetrieveByKey("9");
		Product p5= pm.doRetrieveByKey("15");
		
		Cart c6 = cm.doRetrieveByKey("5");
		Product p6= pm.doRetrieveByKey("7");
		
		assertEquals(true,cm.setQuantity(c5, p5, 10));

		assertEquals(true,cm.addProduct(c6, p6));

	}
}


