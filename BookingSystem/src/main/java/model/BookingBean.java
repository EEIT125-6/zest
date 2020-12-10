package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="booking")
public class BookingBean {
	@Id
	@Column(nullable = false,unique = true,columnDefinition = "char(5)")
	private String bookingNo;
	private String user_id;
	private String bookingdate;
	private String time;
	private Integer number;
	private String restaurant;
	private String name;
	private String phone;
	private String mail;
	private String purpose;
	private String needs;
	private Integer status;

	public BookingBean() {

	}

	public BookingBean(String pBookingNo, String pUser_id, String pBookingdate, String pTime, Integer pNumber,
			String pRestaurant, String pName, String pPhone, String pMail, String pPurpose, String pNeeds,Integer pStatus) {
		this.bookingNo = pBookingNo;
		this.user_id = pUser_id;
		this.bookingdate = pBookingdate;
		this.time = pTime;
		this.number = pNumber;
		this.restaurant = pRestaurant;
		this.name = pName;
		this.phone = pPhone;
		this.mail = pMail;
		this.purpose = pPurpose;
		this.needs = pNeeds;
		this.status=pStatus;
	}
	

	public BookingBean(String bookingNo, String bookingdate, String time, Integer number, String restaurant,
			String name, String phone, String mail, String needs) {
		super();
		this.bookingNo = bookingNo;
		this.bookingdate = bookingdate;
		this.time = time;
		this.number = number;
		this.restaurant = restaurant;
		this.name = name;
		this.phone = phone;
		this.mail = mail;
		this.needs = needs;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
