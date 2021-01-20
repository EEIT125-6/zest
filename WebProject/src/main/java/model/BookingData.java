package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import xun.model.StoreBean;

@Entity
@Table(name = "bookingData")
public class BookingData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookingId;
	private Integer seating;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="storeId")
	private StoreBean storeBean;
	
	
	public BookingData(Integer bookingId, Integer seating, StoreBean storeBean) {
		super();
		this.bookingId = bookingId;
		this.seating = seating;
		this.storeBean = storeBean;
	}
	public BookingData() {
		super();
	}
	public Integer getBookingId() {
		return bookingId;
	}
	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}
	public Integer getSeating() {
		return seating;
	}
	public void setSeating(Integer seating) {
		this.seating = seating;
	}
	public StoreBean getStoreBean() {
		return storeBean;
	}
	public void setStoreBean(StoreBean storeBean) {
		this.storeBean = storeBean;
	}


}
