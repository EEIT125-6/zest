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
	
	/* 取出所有商品列表(分頁用) By George017 2021/01/29 */
	List<ProductInfoBean> getAllProduct(String selectedParameters, Integer avPage, Integer startPage);
	
	/* 取得查詢到的總筆數 By George017 2021/02/01 */
	Long getProductRecordCounts(String selectedParameters);
	
	/* 取得可查詢到的總頁數 By George017 2021/02/01 */
	Integer getTotalProductRecordCounts(String selectedParameters, Integer avPage);
	
	/* 下架/上架商品(利用回傳值確認是否成功) By George017 2021/01/25 */
	Integer productChange(Integer productId, String status);
}
