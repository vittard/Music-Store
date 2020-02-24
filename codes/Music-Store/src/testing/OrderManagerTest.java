package testing;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import entities.Order;
import entities.Product;
import entities.User;
import manager.DriverManagerConnectionPool;
import manager.OrderManager;
import manager.ProductManager;

public class OrderManagerTest {

	@Test
	public void test() throws SQLException {

		Date date = Date.valueOf("2020-03-02"); 
		Order o= new Order(10, "Consegnato", date, 44, 21);
		Date date1 = Date.valueOf("2020-06-22"); 
		Order o2= new Order(8, "In Transito", date1, 44, 16);


		OrderManager om = new OrderManager(new DriverManagerConnectionPool());

		//doRetrieveByKey
		Order o1 = om.doRetrieveByKey("10");

		assertEquals(true,o.equals(o1));

		//doRetrieveAll
		ArrayList<Order> list = (ArrayList<Order>) om.doRetrieveAll("id");

		assertEquals(true, list.get(7).equals(o2));
		assertEquals(true,list.get(9).equals(o));

		//doSave
		Date date2 = Date.valueOf("2019-02-28");
		Order add= new Order(22, "Attesa", date2,39, 65);
		om.doSave(add);

		Order add1 = (Order)om.doRetrieveByKey("22");

		assertEquals(true, add.equals(add1));

		//doUpdate
		Date date3=Date.valueOf("2020-03-02");
		Order o3 = new Order(21,"Attesa", date3,44,22);
		om.doUpdate(o3);

		Order o4 = (Order)om.doRetrieveByKey("21");

		assertEquals(true, o4.equals(o3));

		//doDelete
		Order a5 = (Order)om.doRetrieveByKey("1");
		om.doDelete(a5);
		Order aEl = (Order)om.doRetrieveByKey("1");
		assertNull(aEl);

		//doRetrieveAllByUser


		User user = new User(44, "Frida", "Pagnotto", "fridapagnotto@jourrapide.com",0);

		ArrayList<Order> c = new ArrayList<Order> ( om.doRetrieveAllByUser(user, "id") );


		for(Order order : c) {
			assertEquals(true,order.getUserId() == user.getId());
		}

		//addProduct
		
		ProductManager pm = new ProductManager(new DriverManagerConnectionPool());
		Product pb=pm.doRetrieveByKey("16");
		Order ordine = om.doRetrieveByKey("11");
		
		System.out.println(om.addProduct(ordine, pb, 6));
		
	
	
	}
}
