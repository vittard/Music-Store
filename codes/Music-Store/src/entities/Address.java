package entities;

public class Address {


	private int id;
	private String name;
	private String surname;
	private String street;
	private String city;
	private String province;
	private String zipCode;
	private String state;
	private String phone;
	private int userId;

	public Address() {}

	public Address(int id, String name, String surname, String street,String city, String province, String zipCode, String state,
			String phone, int userID) {

		this.id = id;
		this.name = name;
		this.surname = surname;
		this.street = street;
		this.city = city;
		this.province = province;
		this.zipCode = zipCode;
		this.state = state;
		this.phone = phone;
		this.userId = userID;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getUserId() {
		return this.userId;
	}

	public void setUserId( int userId ) { 
		this.userId = userId;
	}

	public String toString() {

		return getClass().getName()+ id + name + surname + street + city + province + zipCode + state + phone + userId; 
	}

	public boolean equals(Object otherObject) {

		if(otherObject == null)
			return false;
		if(getClass() != otherObject.getClass()) 
			return false;
		Address other=(Address) otherObject;
		return id == other.id && name.equals(other.name) && surname.equals(other.surname) && street.equals(other.street) && city.equals(other.city) && province.equals(other.province) && zipCode.equals(other.zipCode) && state.equals(other.state) && phone.equals(other.phone) && userId == other.userId;

	}
}


