package testing;

import entities.Address;

import static org.junit.Assert.assertEquals;

import org.junit.Test;



public class AddressTest {

	@Test
	public void funzioneTest(){
		
		Address a1 = new Address(01, "Mario", "Rossi", "Via Mantegna 14", "Caserta", "CA", "81100", "San Marino", "3334672392", 110);
		
		int id = 11;
		String name = "Nicola";
		String surname = "Buzzo";
		String street = "Via Iotti 23";
		String province = "Napoli";
		String zipCode = "80099";
		String state = "Italia";
		String phone = "3495462028";
		int userID = 990;
		
		a1.setId(id);
		a1.setName(name);
		a1.setSurname(surname);
		a1.setStreet(street);
		a1.setProvince(province);
		a1.setZipCode(zipCode);
		a1.setState(state);
		a1.setPhone(phone);
		a1.setUserId(userID);
		
		assertEquals(11, a1.getId());
		assertEquals("Nicola", a1.getName());
		assertEquals("Buzzo", a1.getSurname());
		assertEquals("Via Iotti 23", a1.getStreet());
		assertEquals("Napoli", a1.getProvince());
		assertEquals("80099", a1.getZipCode());
		assertEquals("Italia", a1.getState());
		assertEquals("3495462028", a1.getPhone());
		assertEquals(990, a1.getUserId());

	}
	
}
