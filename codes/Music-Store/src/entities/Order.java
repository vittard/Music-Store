package entities;

import java.sql.Date;


public class Order {

	private int id;
	private String state;
	private Date date;
	private int userId;
	private int addressId;

	public Order() {}
	
	public Order(int id, String state, Date date, int userId, int addressId) {
		this.id = id;
		this.state = state;
		this.date = date;
		this.userId = userId;
		this.addressId = addressId;
	}


	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", state=" + state + ", date=" + date + ", userId=" + userId + ", addressId="
				+ addressId + "]";
	}

	public boolean equals(Object otherObject) {

		if(otherObject == null)
			return false;
		if(getClass() != otherObject.getClass()) 
			return false;
		Order other=(Order) otherObject;
		return id == other.id && state.equals(other.state) && date.toString().equals(other.date.toString()) && userId == other.userId && addressId == other.addressId;

	}




}