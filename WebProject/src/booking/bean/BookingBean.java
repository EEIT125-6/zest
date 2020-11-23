package booking.bean;

import java.io.Serializable;

public class BookingBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String user_id;
	private String bookingNo;
	private String bookingdate;
	private String time;
	private int number;
	private String restaurant;
	private String name;
	private String phone;
	private String mail;
	private String purpose;
	private String needs;

	public BookingBean() {

	}

	public BookingBean(String pUser_id, String pBookingNo, String pBookingdate, String pTime, int pNumber,
			String pRestaurant, String pName, String pPhone, String pMail, String pPurpose, String pNeeds) {
		this.user_id = pUser_id;
		this.bookingNo = pBookingNo;
		this.bookingdate = pBookingdate;
		this.time = pTime;
		this.number = pNumber;
		this.restaurant = pRestaurant;
		this.name = pName;
		this.phone = pPhone;
		this.mail = pMail;
		this.purpose = pPurpose;
		this.needs = pNeeds;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public String getBookingdate() {
		return bookingdate;
	}

	public void setBookingdate(String bookingdate) {
		this.bookingdate = bookingdate;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getNeeds() {
		return needs;
	}

	public void setNeeds(String needs) {
		this.needs = needs;
	}

}
