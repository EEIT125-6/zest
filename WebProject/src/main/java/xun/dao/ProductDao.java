package xun.dao;

import java.util.List;

import xun.model.ProductInfoBean;
import xun.model.StoreBean;

public interface ProductDao {
	
	Integer save(ProductInfoBean pi);
	
	Integer updateProduct(ProductInfoBean pi);
	
	Integer deleteProduct(ProductInfoBean pi);
	
	ProductInfoBean get(Integer productid);
	
	Integer deleteALLProduct(StoreBean sb);
	
	List<ProductInfoBean> getStoreProduct(StoreBean sb);
	
}
