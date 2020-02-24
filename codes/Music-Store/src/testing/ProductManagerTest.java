package testing;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import entities.Cart;
import entities.Category;
import entities.Order;
import entities.Product;
import manager.DriverManagerConnectionPool;
import manager.ProductManager;

public class ProductManagerTest {

	@Test
	public void test() throws SQLException{

		Product p = new Product(16, "YAMAHA YAS280", "SASSOFONO CONTRALTO IN MIb LACCATO ORO", 919.00, 30 , 0.00, "https://www.strumentimusicali.net/images/product/1500x1125/2017/03/30/e5/yamaha-yas280-1.jpg");


		ProductManager pm= new ProductManager(new DriverManagerConnectionPool());



		//doRetrieveByKey
		Product pb=(Product)pm.doRetrieveByKey("16");

		assertEquals(true,p.equals(pb));

		//doRetrieveAll
		ArrayList<Product> list = (ArrayList<Product>) pm.doRetrieveAll("id");

		assertEquals(true, list.get(15).equals(p));

		//doSave
		Product p1 = new Product(17, "CASIO Casiotone CT S200 Black", "PIANOFORTE DIGITALE 61 TASTI NERO", 139.00, 10, 0.00, "https://www.strumentimusicali.net/images/product/1500x1125/2019/10/21/29/casio-cts200black-1.jpg");

		pm.doSave(p1);

		Product p2=(Product)pm.doRetrieveByKey("17");
		assertEquals(true, p2.equals(p1));

		//doUpdate
		Product p3 = new Product(17, "Flauto", "flauto", 20.00, 10, 0.00, "https://www.strumentimusicali.net/images/product/1500x1125/2019/10/21/29/casio-cts200black-1.jpg");
		pm.doUpdate(p3);

		Product p4=(Product)pm.doRetrieveByKey("17");
		assertEquals(true, p3.equals(p4));


		//doDelete
		Product p5 = (Product)pm.doRetrieveByKey("14");
		pm.doDelete(p5);

		Product p6 = pm.doRetrieveByKey("14");

		assertNull(p6);

		//doRetrieveAllByCategory
		ArrayList<Product> products = new ArrayList<Product>();
		Category c= new Category("Bassi",11);

		products= (ArrayList<Product>) pm.doRetrieveAllByCategory(c, null);

		Product product1 = pm.doRetrieveByKey("7");

		Product product2 = pm.doRetrieveByKey("8");

		Product product3 = pm.doRetrieveByKey("9");

		for (Product product: products) {
			assertEquals(true, (product1.equals(product) || product2.equals(product) || product3.equals(product) )); 

		}


		//doRetrieveAllByOrder

		String d = "2019-02-04";
		Date date = Date.valueOf(d); 

		HashMap<Product, Integer> hm = new HashMap<Product, Integer>();
		Order order= new Order(10, "Attesa", date, 44, 21);

		hm= pm.doRetrieveAllByOrder(order, null);

		Iterator<Map.Entry<Product, Integer>>it = hm.entrySet().iterator();
		while(it.hasNext()) {

			Map.Entry<Product, Integer>e = it.next();
			Product prod = e.getKey();
			Product prod2 =  pm.doRetrieveByKey("2");
			assertEquals(true, prod.equals(prod2));


		}


		//doRetrieveAllByCart

		Cart cart = new Cart(); 
		cart.setId(6);
		HashMap<Product, Integer> al = pm.doRetrieveAllByCart(cart);

		Iterator<Map.Entry<Product, Integer>>it1 = al.entrySet().iterator();
		while(it1.hasNext()) {

			Map.Entry<Product, Integer>e = it1.next();
			Product prod = e.getKey();
			Product prod1 =  pm.doRetrieveByKey("5");
			Product prod2 =  pm.doRetrieveByKey("7");
			assertEquals(true, prod.equals(prod1) || prod.equals(prod2));


		}
	}
}

