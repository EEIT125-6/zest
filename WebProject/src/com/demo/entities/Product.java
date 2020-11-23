package com.demo.entities;

import java.io.Serializable;

public class Product implements Serializable { // 資料庫內項目資料型態
	private static final long serialVersionUID = 1L;

	private Integer productId;
	private String productName;
	private Integer productPrice;
	private String productPicture;
	private String productShop;
	private Integer productQuantity;

	/*
	 * private Integer product_id; private String product_name; private Integer
	 * product_price; private String product_picture; private String product_shop;
	 */

	public Product() {
	}

	public Product(Integer productId, String productPicture, String productShop, String productName,
			Integer productPrice,Integer productQuantity) {
		this.productId = productId;
		this.productName = productName;
		this.productPrice = productPrice;
		this.productPicture = productPicture;
		this.productShop = productShop;
		this.productQuantity = productQuantity;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer product_id) {
		this.productId = product_id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String product_name) {
		this.productName = product_name;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer product_price) {
		this.productPrice = product_price;
	}

	public String getProductPicture() {
		return productPicture;
	}

	public void setProductPicture(String product_picture) {
		this.productPicture = product_picture;
	}

	public String getProductShop() {
		return productShop;
	}

	public void setProductShop(String product_shop) {
		this.productShop = product_shop;
	}
	
	public Integer getProductQuantity() {
		return productQuantity;
	}
	
	public void setProductQuantity(Integer product_quantity) {
		this.productQuantity = product_quantity;
	}

	
}