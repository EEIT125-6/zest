package xun.dao;

import java.util.List;

import webUser.model.WebUserData;
import xun.model.BoardBean;
import xun.model.StoreBean;
import xun.model.ProductInfoBean;

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
	
	List<StoreBean> getNamestoreandPrice(String stname , Integer price);
	
	List<StoreBean> getNamestoreandStar(String stname , Float star);
	
	List<StoreBean> getNamestoreandPriceandStar(String stname, Integer price, Float star);
	
	List<StoreBean> getFullstore(Integer id);
	
	List<StoreBean> getAdvertisementstore();
	
	List<StoreBean> getAdvertisementphotostore();
	
	List<BoardBean> getComment(Integer stid);
	
	List<ProductInfoBean> getProductInfoBeans(Integer stid);
	
	List<StoreBean> getStorebyClassandPrice(String sclass , Integer price);
	
	List<StoreBean> getStorebyClassandStar(String sclass, Float star);
	
	List<StoreBean> getStorebyClassandStarandPrice(String sclass, Integer price, Float star);
	
	Integer setStorePrice(Integer price,Integer id);
	
	Integer setStoreRealPrice(Integer realprice,Integer id);
	
	Integer setStoreStar(Float avgStar,Integer id);
	
	List<String> getSclassCategory();
	
	String getSclassCategoryTag();
	
	Integer setClickCount(Integer stid);
	
	List<String> getRenameStore(Integer stid);
	
	void storeOffShelf(Integer stid);
	
	List<StoreBean> guessYouLike(String sclass);
	
	/* 取得全部商店列表 By George017 2021/01/19 */
	List<StoreBean> getAllStore();
	
	List<StoreBean> getMemberAllStore(WebUserData webUserData);
	
	/* 下架/上架商店(利用回傳值確認是否成功) By George017 2021/01/23 */
	public Integer storeChange(Integer stid, String newStatus);
}
