package xun.service.impl;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xun.dao.StoreDao;

import xun.model.BoardBean;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.StoreService;

//整理 By George017 2020-12-26
@Service
public class StoreServiceImpl implements StoreService {

	@Autowired
	StoreDao dao;
	
	@Transactional
	@Override
	public int save(StoreBean sb) {
		int count = 0;
		dao.save(sb);
		count++;
		return count;
	}

	@Transactional
	@Override
	public int updateStore(StoreBean sb) {
		int result = 0;
		dao.updateStore(sb);
		return result;
	}
	
	@Transactional
	@Override
	public List<StoreBean> getSinglest(String stname) {
		List<StoreBean> list = null;
		list = dao.getSinglest(stname);
		return list;
	}

	@Transactional
	@Override
	public int deleteStore(StoreBean sb) {
		int count = 0;
		dao.deleteStore(sb);
		return count;
	}

	@Transactional
	@Override
	public boolean isDup(String stname) {
		boolean result = false;
		result = dao.isDup(stname);
		return result;
	}

	@Transactional
	@Override
	public void photoStore(StoreBean sb) {
		dao.photoStore(sb);
	}

	@Transactional
	@Override
	public void bannerStore(StoreBean sb) {
		dao.bannerStore(sb);
	}

	@Transactional
	@Override
	public List<StoreBean> getClassstore(String sclass) {
		List<StoreBean> list = null;
		list = dao.getClassstore(sclass);
		return list;
	}

	@Transactional
	@Override
	public List<StoreBean> getNamestore(String stname) {
		List<StoreBean> list = dao.getNamestore(stname);
		return list;
	}

	@Transactional
	@Override
	public List<StoreBean> getFullstore(Integer id) {
		List<StoreBean> list = dao.getFullstore(id);
		return list;
		
	}

	@Transactional
	@Override
	public List<StoreBean> getAdvertisementstore() {
		List<StoreBean> list = null;
		List<StoreBean> list2 = dao.getAdvertisementstore();
		Collections.shuffle(list2);
		list = list2.subList(0, 4);
		return list;
	}

	@Transactional
	@Override
	public List<StoreBean> getAdvertisementphotostore() {
		List<StoreBean> list = null;
		List<StoreBean> list1 = dao.getAdvertisementphotostore();
		Collections.shuffle(list1);
		list = list1.subList(0, 6);
		return list;
	}

	@Transactional
	@Override
	public List<BoardBean> getComment(Integer stid) {
		List<BoardBean> list = dao.getComment(stid);
		return list;
	}

	@Transactional
	@Override
	public List<ProductInfoBean> getProductInfoBeans(Integer stid) {
		List<ProductInfoBean> list = dao.getProductInfoBeans(stid);
		return list;
	}

	@Transactional
	@Override
	public StoreBean get(Integer id) {
		return dao.get(id);
	}

	@Transactional
	@Override
	public Integer setStorePrice(Integer price, Integer id) {
		return dao.setStorePrice(price, id);
	}
	
}
