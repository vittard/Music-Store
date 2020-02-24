package testing;

import static org.junit.Assert.*;
import org.junit.Test;

import entities.Payment;
import entities.User;
import manager.DriverManagerConnectionPool;
import manager.PaymentManager;


import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
public class PaymentManagerTest {

	@Test
	public void test() throws SQLException{

		
		Date date = Date.valueOf("2022-02-01");

		Payment p= new Payment(1,"4716642618957225", "Priscilla", "Marcelo", date, 120, 4);	

		Date date1 = Date.valueOf("2022-08-01");
		Payment p2= new Payment(2,"5349722266361052", "Vala", "Pisano", date1, 362, 5);	

		PaymentManager pm= new PaymentManager(new DriverManagerConnectionPool());
		
		//doRetrieveByKey
		Payment p1 = pm.doRetrieveByKey("1");

		assertEquals(true,p.equals(p1));
		
		//doRetrieveAll
		ArrayList<Payment> list = (ArrayList<Payment>) pm.doRetrieveAll("id");

		assertEquals(true, list.get(0).equals(p));
		assertEquals(true,list.get(1).equals(p2));
		
		
		//doSave
		
		Date date2 = Date.valueOf("2025-06-03");
		Payment add= new Payment(214, "6321342768350012", "Nunzia", "Barese", date2, 347,12);
		pm.doSave(add);

		Payment add1 = (Payment)pm.doRetrieveByKey("214");

		
		assertEquals(true, add.equals(add1));
		
	
		//doDelete
		Payment p3 = (Payment)pm.doRetrieveByKey("213");
		pm.doDelete(p3);
		Payment aEl = (Payment)pm.doRetrieveByKey("213");
		assertNull(aEl);
		
		//doRetrieveAllByUser
		User user = new User(7, "Mirella", "Boni", "MirellaBoni@teleworm.us", 0);
		ArrayList<Payment> ad = (ArrayList<Payment>) pm.doRetrieveAllByUser(user, "id");

		for(Payment payment : ad) {
			assertEquals(true,payment.getUserId() == user.getId());
			//System.out.println(address.toString());

		}
	}

}
