package xun.dao;

import java.util.List;

import xun.model.BoardBean;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;

public interface StoreDao {

	boolean isDup(String stname);
	
	List<StoreBean> getSinglest(String stname);
	
	int save(StoreBean sb);
	
	int updateStore(StoreBean sb);
	
	int deleteStore(StoreBean sb);
	
	void photoStore(StoreBean sb);
	
	void bannerStore(StoreBean sb);
	
	StoreBean get(Integer id);
	
	List<StoreBean> getClassstore(String sclass);
	
	List<StoreBean> getNamestore(String stname);
	
	List<StoreBean> getFullstore(Integer id);
	
	List<StoreBean> getAdvertisementstore();
	
	List<StoreBean> getAdvertisementphotostore();
	
	List<BoardBean> getComment(Integer stid);
	
	List<ProductInfoBean> getProductInfoBeans(Integer stid);
	
	List<StoreBean> getStorebyClassandPrice(String sclass , Integer price);
	
	Integer setStorePrice(Integer price,Integer id);
	
}
