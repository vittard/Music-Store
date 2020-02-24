package entities;

public class Category {

	private String name;
	private int id;

	public Category() {



	}
	public Category(String name, int id) {

		this.name=name;
		this.setId(id);

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "Category [name=" + name + ", id=" + id + "]";
	}
	
	public boolean equals(Object otherObject) {

		if(otherObject == null)
			return false;
		if(getClass() != otherObject.getClass()) 
			return false;
		Category other=(Category) otherObject;
		return name.equals(other.name) && id == other.id  ;
	}


}
