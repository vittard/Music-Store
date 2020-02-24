package testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import entities.Product;

public class ProductTest {

	@Test
	public void funzioneTest() {
		
		Product p = new Product(0,"Gibson SG", "Body in acero", 899.99, 0, 19.99, "https://musicalstudio.net/wp-content/uploads/19010838.jpg");
		
		int id = 5;
		String name = "Eko Aire Standard";
		String description = "CHITARRA ELETTRICA NATURALE";
		double price = 379.00;
		int disponibility = 300;
		double shippingPrice = 10.00;
		String imagePath = "https://musicalstudio.net/wp-content/uploads/0201Q512.jpg";
		
		p.setId(id);
		p.setName(name);
		p.setDescription(description);
		p.setPrice(price);
		p.setDisponibility(disponibility);
		p.setShippingPrice(shippingPrice);
		p.setImagePath(imagePath);
		
		
		assertEquals(5, p.getId());
		assertEquals("Eko Aire Standard", p.getName());
		assertEquals("CHITARRA ELETTRICA NATURALE", p.getDescription());
		assertEquals(379.00, p.getPrice(), 0.0);
		assertEquals(300, p.getDisponibility());
		assertEquals(10.00, p.getShippingPrice(), 0.0);
		assertEquals("https://musicalstudio.net/wp-content/uploads/0201Q512.jpg", p.getImagePath());
		
		
	}
		
}
