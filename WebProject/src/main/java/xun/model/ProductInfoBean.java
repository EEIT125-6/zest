package xun.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ProductInfo")
public class ProductInfoBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer product_id;
	String product_name;
	String product_shop;
	Integer product_price;
	String product_picture;
	Integer product_quantity;
	String product_status;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Store_Id")
	private StoreBean storebean;
	
	public ProductInfoBean() {
		super();
	}

	
	
	public String getProduct_status() {
		return product_status;
	}
	public void setProduct_status(String product_status) {
		this.product_status = product_status;
	}



	public ProductInfoBean(Integer product_id, String product_name, String product_shop, Integer product_price,
			String product_picture, Integer product_quantity) {
		super();
		this.product_id = product_id;
		this.product_name = product_name;
		this.product_shop = product_shop;
		this.product_price = product_price;
		this.product_picture = product_picture;
		this.product_quantity = product_quantity;

	}

	public Integer getProduct_id() {
		return product_id;
	}

	public void setProduct_id(Integer product_id) {
		this.product_id = product_id;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getProduct_shop() {
		return product_shop;
	}

	public void setProduct_shop(String product_shop) {
		this.product_shop = product_shop;
	}

	public Integer getProduct_price() {
		return product_price;
	}

	public void setProduct_price(Integer product_price) {
		this.product_price = product_price;
	}

	public String getProduct_picture() {
		return product_picture;
	}

	public void setProduct_picture(String product_picture) {
		this.product_picture = product_picture;
	}

	public Integer getProduct_quantity() {
		return product_quantity;
	}

	public void setProduct_quantity(Integer product_quantity) {
		this.product_quantity = product_quantity;
	}

	public StoreBean getStorebean() {
		return storebean;
	}

	public void setStorebean(StoreBean storebean) {
		this.storebean = storebean;
	}

	@Override
	public String toString() {
		return "ProductInfoBean [product_id=" + product_id + ", product_name=" + product_name + ", product_shop="
				+ product_shop + ", product_price=" + product_price + ", product_picture=" + product_picture
				+ ", product_quantity=" + product_quantity + ", storebean=" + storebean + "]";
	}
}
