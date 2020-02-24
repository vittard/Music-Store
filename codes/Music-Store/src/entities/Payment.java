package entities;

import java.sql.Date;

public class Payment {

	private int id;
	private String cardNumber;
	private String name;
	private String surname;
	private Date expiryDate;
	private int cvv;
	private int userId;

	public Payment() {}

	public Payment(int id, String cardNumber, String name, String surname, Date expiryDate, int cvv, int userId) {
		this.id = id;
		this.cardNumber = cardNumber;
		this.name = name;
		this.surname = surname;
		this.expiryDate = expiryDate;
		this.cvv = cvv;
		this.userId = userId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
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
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getCvv() {
		return cvv;
	}
	public void setCvv(int cvv) {
		this.cvv = cvv;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Payement [id=" + id + ", cardNumber=" + cardNumber + ", name=" + name + ", surname=" + surname
				+ ", expiryDate=" + expiryDate + ", cvv=" + cvv + ", userId=" + userId + "]";
	}

	@Override
	public boolean equals( Object otherObject ) {
		if (otherObject == null)
			return false;
		else if (getClass() != otherObject.getClass())
			return false;
		Payment other = (Payment)otherObject;
		return id == other.getId() && cardNumber.equals(other.getCardNumber()) && name.equals(other.getName()) && surname.equals(other.getSurname()) && expiryDate.toString().equals(other.getExpiryDate().toString()) && cvv == other.getCvv() && userId == other.getUserId();
		
	}

}