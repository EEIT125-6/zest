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

	public int getProductPrice() {

		return getProduct().getProduct_price();
	}

	public int getProductId() {
		return getProduct().getProduct_id();
	}

	public String getProductShop() {
		return getProduct().getProduct_shop();
	}

	public String getProductName() {
		return getProduct().getProduct_name();
	}

	public String getProductPicture() {

		return getProduct().getProduct_picture();
	}

	public void setProduct(ProductInfoBean product) {
		this.product = product;
	}

	public Integer getProductQuantity() {

		return getProduct().getProduct_quantity();
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public Integer getQuantity() {
		return quantity;
	}
}
