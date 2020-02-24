package entities;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart {
	private int id;
	private HashMap<Product, Integer> products;

	public Cart() {
		products = new HashMap<Product, Integer>();
	}

	public HashMap<Product, Integer> getProducts() {
		return products;
	}
	
	public void setProducts( HashMap<Product, Integer> products ) {
		this.products = products;
	}

	public int getQuantityOfProduct(Product product) {
		return products.get(product);
	}

	public int getQuantityOfAll() {
		int sum = 0;
		ArrayList<Integer> values = new ArrayList<Integer> ( products.values() );
		for ( Integer value: values) {
			sum = sum + value;
		}
		return sum;
	}

	public void addProduct(Product product) {
		if (products.containsKey(product)) {
			int quantity = products.get(product) + 1;
			products.put(product, quantity);
		} else {
			products.put(product, 1);
		}
	}


	public void removeProduct(Product product) {
		products.remove(product);
	}
	
	public void setQuantityOfProduct(Product product, int newQuantity) {
		products.put(product, newQuantity);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", products=" + products + ", getClass()=" + getClass() + "]";
	}
	
	public boolean equals(Object otherObject) {

		if(otherObject == null)
			return false;
		if(getClass() != otherObject.getClass()) 
			return false;
		Cart other=(Cart) otherObject;
		
		return id == other.id && products.toString().equals(other.products.toString());

	}
	
	
}