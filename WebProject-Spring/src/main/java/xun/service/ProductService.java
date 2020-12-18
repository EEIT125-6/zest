package xun.service;

import xun.model.ProductInfoBean;

public interface ProductService {
	Integer save(ProductInfoBean pi);
	
	Integer updateProduct(ProductInfoBean pi);
	
	Integer deleteProduct(ProductInfoBean pi);
}
