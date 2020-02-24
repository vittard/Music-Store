package testing;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.Test;

import entities.Order;

public class OrderTest {
	
	@Test
	
	public void funzioneTest() {
		
		String d = "2020-03-02";
		Date data = Date.valueOf(d); 
		
		
		Order o = new Order(2, "Pronto alla spedizione", data, 30, 12);
		
		int id = 4;
		
		String d1 = "2020-02-12";
		Date data1 = Date.valueOf(d1); 
		String state = "In attesa";
		int userId = 21;
		int addressId = 10;  
		
		
		o.setId(id);
		o.setState(state);
		o.setDate(data1);
		o.setUserId(userId);
		o.setAddressId(addressId);
		
		
		assertEquals(4, o.getId());
		assertEquals("In attesa", o.getState());
		assertEquals(data1, o.getDate());
		assertEquals(21, o.getUserId());
		assertEquals(10, o.getAddressId());
		
	}
}
