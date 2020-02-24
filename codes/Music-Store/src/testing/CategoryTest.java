package testing;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import entities.Category;

public class CategoryTest {

	@Test
	
	public void funzioneTest() {
		Category c = new Category("bassi", 11);
		
		String name = "Chitarre";
		int id = 5;
		
		c.setId(id);
		c.setName(name);
		
		assertEquals("Chitarre", c.getName());
		assertEquals(5, c.getId());
	}
}
