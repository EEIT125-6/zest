package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import xun.model.StoreBean;
import java.io.Serializable;

@Entity
@Table(name = "bookingData")
public class BookingData implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="storeId")
	private StoreBean storeBean;
	private Integer seating;
	
	
	public BookingData( StoreBean storeBean,Integer seating ) {
		super();
		this.storeBean = storeBean;
		this.seating = seating;
	}
	public BookingData() {
		super();
	}
	
	public StoreBean getStoreBean() {
		return storeBean;
	}
	public void setStoreBean(StoreBean storeBean) {
		this.storeBean = storeBean;
	}
	public Integer getSeating() {
		return seating;
	}
	public void setSeating(Integer seating) {
		this.seating = seating;
	}

}
