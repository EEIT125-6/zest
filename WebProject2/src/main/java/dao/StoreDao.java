package dao;

import model.StoreBean;

public interface StoreDao {

	boolean isDup(String stname);
	
	int save(StoreBean sb);
	
	int updateStore(StoreBean sb);
	
	int deleteStore(StoreBean sb);
	
	void photoStore(StoreBean sb);
	
	void bannerStore(StoreBean sb);
}
