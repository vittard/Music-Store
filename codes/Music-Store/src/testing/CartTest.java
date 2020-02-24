package testing;

import org.junit.Test;
import entities.Cart;
import entities.Product;

import static org.junit.Assert.assertEquals;


public class CartTest {

	@Test
	
	public void funzioneTest() {
		//Arrange
		Cart cart = new Cart();
		
		Product p = new Product(5, "Eko Aire Standard", "Chitarra Elettrica Naturale", 379.00, 300, 10.00, "https://musicalstudio.net/wp-content/uploads/0201Q512.jpg");
		Product p1 = new Product(6, "Roland td-17", "Batteria Elettrica", 1599.00, 2, 20.00, "https://musicalstudio.net/wp-content/uploads/05020827.jpg");
		Product p2 = new Product(7, "Eko vjb-200 black", "Basso elettrico", 209.00, 0, 0.00, "https://musicalstudio.net/wp-content/uploads/0301Q522.jpg");
		
		cart.addProduct(p);
		cart.addProduct(p1);
		
		assertEquals(1, cart.getQuantityOfProduct(p1));
		
		cart.addProduct(p2);
		
		assertEquals(3, cart.getQuantityOfAll());
		
		cart.removeProduct(p2);
		cart.removeProduct(p1);
		
		assertEquals(1, cart.getQuantityOfProduct(p));
		assertEquals(1, cart.getQuantityOfAll());
	}
}
