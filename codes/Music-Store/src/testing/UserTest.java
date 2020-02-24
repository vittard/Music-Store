package testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import entities.User;

public class UserTest {

	@Test
	
	public void funzioneTest() {
		
		User u = new User(15, "Giorgio", "Bianchi", "g.bianchi@live.it", 1);
		
		int id = 11;
		String name = "Mario";
		String surname = "Rossi";
		String email = "M.rossi@live.it";
		int type = 0;
		
		u.setId(id);
		u.setName(name);
		u.setSurname(surname);
		u.setEmail(email);
		u.setType(type);
		
		assertEquals(11, u.getId());
		assertEquals("Mario", u.getName());
		assertEquals("Rossi", u.getSurname());
		assertEquals("M.rossi@live.it", u.getEmail());
		assertEquals("Client", u.getRole());
		assertEquals(false, u.isNull());
		
	}
}
