package testing;

import java.sql.Date;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;


import org.junit.Test;


import entities.Payment;

public class PaymentTest {
	
	@Test
		
	public void funzioneTest() throws ParseException {
		
	String d = "2025-02-21";
	Date date = Date.valueOf(d); 
	
	
	
	Payment p = new Payment(1, "4716642618957225", "Priscilla", "Marcelo", date, 120, 4);
	
	String d1 = "2021-07-01";
	Date date1 = Date.valueOf(d1); 
	
	
	int id = 4;
	String cardNumber = "5135082669663390";
	String name = "Mirella";
	String surname = "Boni";
	Date expiryDate = date1;
	int cvv = 905;
	int userId = 7;
	
	p.setId(id);
	p.setCardNumber(cardNumber);
	p.setName(name);
	p.setSurname(surname);
	p.setExpiryDate((java.sql.Date) expiryDate);
	p.setCvv(cvv);
	p.setUserId(userId);
	
	assertEquals(4, p.getId());
	assertEquals("5135082669663390", p.getCardNumber());
	assertEquals("Mirella", p.getName());
	assertEquals("Boni", p.getSurname());
	assertEquals(date1, p.getExpiryDate());
	assertEquals(905, p.getCvv());
	assertEquals(7, p.getUserId());
	

	}

}
