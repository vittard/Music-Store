package entities;

public class User {

	private int id;
	private String name;
	private String surname;
	private String email;
	private int type;

	public User() {
	}

	public User(int id, String name, String surname, String email, int type) {

		this.id=id;
		this.name=name;
		this.surname=surname;
		this.email=email;
		this.type=type;

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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	public String getRole() {
		if ( isNull() ) {
			return "Invalid User";
		} else {
			String s = "";
			switch (type) {

			case 0: {
				s = "Client";
				break;
			}

			case 1: {
				s = "Order Manager";
				break;
			}

			case 2: {
				s = "Catalog Manager";
				break;
			}

			case 3: {
				s = "User Manager";
				break;
			}
			
			default: {
				s = "Invalid User";
			}

			}
			return s;
		}
	}

	public boolean isNull() {
		if (name == null && surname == null && email == null && type == 0 && id == 0) {
			return true;
		} else return false;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", type=" + type
				+ "]";
	}
	
	public boolean equals(Object otherObject) {

		if(otherObject == null)
			return false;
		if(getClass() != otherObject.getClass()) 
			return false;
		User other=(User) otherObject;
		return id == other.id && name.equals(other.name) && surname.equals(other.surname) && email.equals(other.email) && type == other.type;

	}

}
