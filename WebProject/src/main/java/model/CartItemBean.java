package model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import webUser.model.WebUserData;
import xun.model.ProductInfoBean;

@Entity
@Table(name = "CartItem")
public class CartItemBean implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer purchase_Id;//交易代號
	@JoinColumn(name = "userId")
	@ManyToOne
	WebUserData product_User;//購買人資訊
	@ManyToOne
	@JoinColumn(name="ProductInformation")
	ProductInfoBean product_Information;//產品資訊
	@Column(name="PurchaseQuantity")
	String product_Quantity;//購買數量
	@Column(name="PurchasePayment")
	Boolean purchase_Payment;//付款狀態
	@Column(name="PurchaseTransport")
	String purchase_Transport; //運送狀態
	@Column(name="PurchaseTime")
	Date purchase_Time; //購買日期
	
	public CartItemBean() {
		super();
	}

	public CartItemBean(WebUserData product_User, ProductInfoBean product_Info, String product_Quantity,
			 Boolean purchase_Payment, String purchase_Trans) {
		super();
		this.product_User = product_User;
		this.product_Information = product_Info;
		this.product_Quantity = product_Quantity;
		this.purchase_Payment = purchase_Payment;
		this.purchase_Transport = purchase_Trans;
	}

	
	public WebUserData getProduct_User() {
		return product_User;
	}
	
	public void setProduct_User(WebUserData product_User) {
		this.product_User = product_User;
	}
	
	public ProductInfoBean getProduct_Info() {
		return product_Information;
	}
	
	public void setProduct_Info(ProductInfoBean product_Info) {
		this.product_Information = product_Info;
	}
	
	public String getProduct_Quantity() {
		return product_Quantity;
	}
	
	public void setProduct_Quantity(String product_Quantity) {
		this.product_Quantity = product_Quantity;
	}
	
	public Boolean getPurchase_Payment() {
		return purchase_Payment;
	}
	
	public void setPurchase_Payment(Boolean purchase_Payment) {
		this.purchase_Payment = purchase_Payment;
	}
	
	public String getPurchase_Trans() {
		return purchase_Transport;
	}
	
	public void setPurchase_Trans(String purchase_Trans) {
		this.purchase_Transport = purchase_Trans;
	}

	public Date getPurchase_Time() {
		return purchase_Time;
	}

	public void setPurchase_Time(Date purchase_Time) {
		this.purchase_Time = purchase_Time;
	}
}