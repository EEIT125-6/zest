package model;

import java.io.Serializable;

public class Item implements Serializable { // 商城內項目資料
	private static final long serialVersionUID = 1L;

	private ProductInfoBean product;
	private int quantity;

	public Item() {
	}

	public Item(ProductInfoBean product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public ProductInfoBean getProduct() {
		return product;
	}

	public int getProduct_price() {

		return getProduct().getProduct_price();
	}

	public int getProduct_id() {
		return getProduct().getProduct_id();
	}

	public String getProduct_shop() {
		return getProduct().getProduct_shop();
	}

	public String getProduct_name() {
		return getProduct().getProduct_name();
	}

	public String getProduct_picture() {

		return getProduct().getProduct_picture();
	}

	public void setProduct(ProductInfoBean product) {
		this.product = product;
	}

	public Integer getProduct_quantity() {

		return getProduct().getProduct_quantity();
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
}
