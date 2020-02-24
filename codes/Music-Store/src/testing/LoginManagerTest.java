package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import entities.User;
import manager.LoginManager;

public class LoginManagerTest {

	@Test
	public void test_Client() {
		
		User u= new User(1,"Emanuele", "Galati", "e.galati@music-store.it", 0);
		LoginManager l = new LoginManager(u);
	
		
		assertEquals(true, l.checkClient());
		
		
		
	}
	
	@Test
	public void test_OrderManager() {
		
		User u= new User(2,"Vittorio", "Ardolino", "v.ardolino@music-store.it", 1);
		LoginManager l = new LoginManager(u);
	
		
		assertEquals(true, l.checkOrderAdmin());
		
		
		
	}
	
	@Test
	public void test_CatalogManager() {
		
		User u= new User(3,"Nicola", "Buzzo", "n.buzzo@music-store.it", 2);
		LoginManager l = new LoginManager(u);
	
		
		assertEquals(true, l.checkCatalogAdmin());
		
		
		
	}
	
	@Test
	public void test_UserAdmin() {
		
		User u= new User(4,"Giovanni", "Galati", "g.galati@music-store.it", 3);
		LoginManager l = new LoginManager(u);
	
		
		assertEquals(true, l.checkUserAdmin());
		
		
		
	}

}


