package xun.service;

import java.util.List;

import xun.model.ProductInfoBean;
import xun.model.StoreBean;

public interface ProductService {
	Integer save(ProductInfoBean pi);
	
	Integer updateProduct(ProductInfoBean pi);
	
	Integer deleteProduct(ProductInfoBean pi);
	
	ProductInfoBean get(Integer productid);
	
	Integer deleteALLProduct(StoreBean sb);
	
	List<ProductInfoBean> getStoreProduct(StoreBean sb);
	
	Integer getLastProductId();
	
	void productOffShelf(Integer productid);
	
	void productReOnShelf(Integer productid);
	
	void productRemoveByStore(Integer productid);
	
	/* 取出所有商品列表 By George017 2021/01/25 */
	List<ProductInfoBean> getAllProduct();
	
	/* 取出所有此帳號的商店的商品列表 By George017 2021/01/25 */
	List<ProductInfoBean> getAllProductByUserId(String userId);
	
	/* 下架/上架商品(利用回傳值確認是否成功) By George017 2021/01/25 */
	public Integer productChange(Integer productId, String status);
}
