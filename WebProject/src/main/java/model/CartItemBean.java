package model;

import java.io.Serializable;

import java.time.OffsetDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import xun.model.ProductInfoBean;
//import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "CartItem")
public class CartItemBean implements Serializable {
	private static final long serialVersionUID = 1L;	
	
	@Id
	@Column
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userId", columnDefinition = ("char(7)"))
	private String product_User;//購買人資訊
	@Column
	@ManyToOne
	@JoinColumn(name="ProductInfo",columnDefinition=("nvarchar(50)"))
	ProductInfoBean product_Info;//產品資訊
	@Column(name="PurchaseQuantity", columnDefinition=("nvarchar(50)"))
	private String product_Quantity;//購買數量
	@Column(name="PurchaseTime",columnDefinition=("TIMESTAMP WITH TIME ZONE"))
	private OffsetDateTime purchase_Time;//交易時間帶時區
	@Column(name="PurchasePayment", columnDefinition=("nvarchar(10)"))
	private Boolean purchase_Payment;//付款狀態
	@Column(name="PurchaseTrans", columnDefinition=("nvarchar(10)"))
	private String purchase_Trans; //運送狀態
	
	public CartItemBean(String product_User, ProductInfoBean product_Info, String product_Quantity,
			OffsetDateTime purchase_Time, Boolean purchase_Payment, String purchase_Trans) {
		super();
		this.product_User = product_User;
		this.product_Info = product_Info;
		this.product_Quantity = product_Quantity;
		this.purchase_Time = purchase_Time;
		this.purchase_Payment = purchase_Payment;
		this.purchase_Trans = purchase_Trans;
	}

	@Transient
	public String getProduct_User() {
		return product_User;
	}
	@Transient
	public void setProduct_User(String product_User) {
		this.product_User = product_User;
	}
	@Transient
	public ProductInfoBean getProduct_Info() {
		return product_Info;
	}
	@Transient
	public void setProduct_Info(ProductInfoBean product_Info) {
		this.product_Info = product_Info;
	}
	@Transient
	public String getProduct_Quantity() {
		return product_Quantity;
	}
	@Transient
	public void setProduct_Quantity(String product_Quantity) {
		this.product_Quantity = product_Quantity;
	}
	@Transient
	public OffsetDateTime getPurchase_Time() {
		return purchase_Time;
	}
	@Transient
	public void setPurchase_Time(OffsetDateTime purchase_Time) {
		this.purchase_Time = purchase_Time;
	}
	@Transient
	public Boolean getPurchase_Payment() {
		return purchase_Payment;
	}
	@Transient
	public void setPurchase_Payment(Boolean purchase_Payment) {
		this.purchase_Payment = purchase_Payment;
	}
	@Transient
	public String getPurchase_Trans() {
		return purchase_Trans;
	}
	@Transient
	public void setPurchase_Trans(String purchase_Trans) {
		this.purchase_Trans = purchase_Trans;
	}
	
	public CartItemBean(List<ProductInfoBean> list, int id) {
		super();
	}
	
	

	}
	
	

