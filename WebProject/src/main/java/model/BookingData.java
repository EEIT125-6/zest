package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import xun.model.StoreBean;

@Entity
@Table(name = "bookingData")
public class BookingData implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="storeId")
	private StoreBean storeBean;
	private Integer seating;
	
	
	public BookingData(StoreBean storeBean, Integer seating) {
		super();
		this.seating = seating;
		this.storeBean = storeBean;
	}
	public BookingData() {
		super();
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
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}


}
