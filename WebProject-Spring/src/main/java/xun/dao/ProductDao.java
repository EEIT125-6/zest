package xun.dao;

import xun.model.ProductInfoBean;

public interface ProductDao {
	
	Integer save(ProductInfoBean pi);
	
	Integer updateProduct(ProductInfoBean pi);
	
	Integer deleteProduct(ProductInfoBean pi);
	
}
