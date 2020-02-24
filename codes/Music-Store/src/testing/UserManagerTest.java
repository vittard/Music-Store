package testing;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Test;

import entities.User;
import manager.DriverManagerConnectionPool;
import manager.UserManager;

public class UserManagerTest {

	@Test
	public void test() throws SQLException{

		User user = new User(2, "Emanuele", "Galati", "e.galati@music-store.it",2);
		User user1 = new User(8, "Patrizia", "Capon", "PatriziaCapon@jourrapide.com",0);

		UserManager um = new UserManager(new DriverManagerConnectionPool());

		//doRetrieveByKey
		User user2= um.doRetrieveByKey("2");
		assertEquals(true,user.equals(user2) );

		//doRetrieveAll
		ArrayList<User> list = (ArrayList<User>) um.doRetrieveAll("id");

		assertEquals(true,list.get(1).equals(user));
		assertEquals(true,list.get(7).equals(user1));

		//doUpdate
		User user3 = new User(8, "Giovanni", "Mucciaccia", "g.muciaccia@gmail.com", 0);
		um.doUpdate(user3);

		User user4 = (User)um.doRetrieveByKey("8");
		assertEquals(true, user4.equals(user3));

		//doDelete
		User user5 = (User)um.doRetrieveByKey("8");
		um.doDelete(user5);
		User userEl = (User)um.doRetrieveByKey("8");
		assertNull(userEl);
		
		//doSave
		User user7= new User(217, "Nicola", "Sorrento", "n.sorrento@gmail.com", 0);
		um.doSave(user7, "password");
		User user8 = um.doRetrieveByKey("217");
		assertEquals(true, user7.equals(user8));
		
		//doRetriveByEmailAndPassword
		User user6= (User)um.doRetriveByEmailAndPassword(user.getEmail(), "password" );
		assertEquals(true, user.equals(user6));
		
		//getUserPassword
		String pass= um.getUserPassword(user.getId());
		
		assertEquals(true, pass.equals("password"));
	}

}
