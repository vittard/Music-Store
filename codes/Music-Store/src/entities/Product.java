package entities;

public class Product {
	
	
	private int id;
	private String name;
	private String description;
	private double price;
	private int disponibility;
	private double shippingPrice;
	private String imagePath;
	
	public Product() {}
	
	public Product(int id, String name, String description, double price, int disponibility, double shippingPrice, String imagePath) {
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.disponibility = disponibility;
		this.shippingPrice = shippingPrice;
		this.imagePath = imagePath;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public int getDisponibility() {
		return disponibility;
	}


	public void setDisponibility(int disponibility) {
		this.disponibility = disponibility;
	}


	public double getShippingPrice() {
		return shippingPrice;
	}


	public void setShippingPrice(double shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price
				+ ", disponibility=" + disponibility + ", shippingPrice=" + shippingPrice + ", imagePath=" + imagePath
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + disponibility;
		result = prime * result + id;
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(shippingPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (disponibility != other.disponibility)
			return false;
		if (id != other.id)
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (Double.doubleToLongBits(shippingPrice) != Double.doubleToLongBits(other.shippingPrice))
			return false;
		return true;
	}
	
	
}
