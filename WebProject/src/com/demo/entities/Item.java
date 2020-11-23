package com.demo.entities;

import java.io.Serializable;

public class Item implements Serializable{	//商城內項目資料
	private static final long serialVersionUID = 1L;

	private Product product;
	private int quantity;

	public Item() {
	}

	public Item(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}
	
	public int getProductPrice() {
		
		return getProduct().getProductPrice();
	}
	
	public int getProductId() {
		return getProduct().getProductId();
	}
	
	public String getProductShop() {
		return getProduct().getProductShop();
	}
	
	public String getProductName() {
		return getProduct().getProductName();
	}
	
	public String getProductPicture() {
		
		return getProduct().getProductPicture();
	}
	
	
	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getProductQuantity() {
		
		return getProduct().getProductQuantity();
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
