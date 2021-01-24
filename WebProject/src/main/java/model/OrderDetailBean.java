package model;

import java.io.Serializable;
import java.util.Date;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import webUser.model.WebUserData;

@Entity //本類別封裝單筆訂單資料
@Table(name="OrderDetail")
public class OrderDetailBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Integer seqno; //訂單代號
	@OneToMany(mappedBy="orderDetailBean",fetch=FetchType.EAGER)	
	Set<CartDetailBean> CDB;//產品資訊
	@ManyToOne
	WebUserData webUserData; //會員資料
	String deliverAddress; //寄送地址
	Double discount; //折扣額度
	Boolean purchase_Payment;//付款狀態
	String purchase_Transport;//運送狀態
	Date purchase_Time;//購買日期
	String Tag;//顯示訂單狀態
	
	public OrderDetailBean () {
		super();
	}
	
	public WebUserData getWebUserData() {
		return webUserData;
	}
	
	public void setWebUsetData(WebUserData wud) {
		this.webUserData =wud;
	}
	
	public Integer getSeqno() {
		return seqno;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}

	public Set<CartDetailBean> getCDB() {
		return CDB;
	}

	public void setCDB(Set<CartDetailBean> cDB) {
		CDB = cDB;
	}

	public String getDeliverAddress() {
		return deliverAddress;
	}

	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	public Boolean getPurchase_Payment() {
		return purchase_Payment;
	}

	public void setPurchase_Payment(Boolean purchase_Payment) {
		this.purchase_Payment = purchase_Payment;
	}

	public String getPurchase_Transport() {
		return purchase_Transport;
	}

	public void setPurchase_Transport(String purchase_Transport) {
		this.purchase_Transport = purchase_Transport;
	}

	public Date getPurchase_Time() {
		return purchase_Time;
	}

	public void setPurchase_Time(Date purchase_Time) {
		this.purchase_Time = purchase_Time;
	}

	public String getTag() {
		return Tag;
	}

	public void setTag(String tag) {
		Tag = tag;
	}

	public OrderDetailBean(WebUserData webUserData) {
		super();
		this.webUserData = webUserData;
		this.purchase_Payment = false;
	}
}
