package xun.service.impl;



import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xun.dao.ProductDao;
import xun.model.StoreBean;
import xun.service.ProductService;
import xun.model.ProductInfoBean;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDao pDao;

	@Transactional
	@Override
	public Integer save(ProductInfoBean pi) {
		Integer count = 0;
		count = pDao.save(pi);
		return count;
	}

	@Transactional
	@Override
	public Integer updateProduct(ProductInfoBean pi) {
		Integer count = 0;
		count = pDao.updateProduct(pi);
		return count;
	}
	
	@Transactional
	@Override
	public Integer deleteProduct(ProductInfoBean pi) {
		Integer count = 0;
		count = pDao.deleteProduct(pi);
		return count;
	}

	@Transactional
	@Override
	public ProductInfoBean get(Integer productid) {
		
		return pDao.get(productid);
	}

	@Transactional
	@Override
	public Integer deleteALLProduct(StoreBean sb) {

		return pDao.deleteALLProduct(sb);
	}

	@Transactional
	@Override
	public List<ProductInfoBean> getStoreProduct(StoreBean sb) {
		return pDao.getStoreProduct(sb);
	}
	
	@Transactional
	@Override
	public Integer getLastProductId() {
		return pDao.getLastProductId();
	}

	@Transactional
	@Override
	public void productOffShelf(Integer productid) {
		pDao.productOffShelf(productid);
	}

	@Transactional
	@Override
	public void productReOnShelf(Integer productid) {
		pDao.productReOnShelf(productid);
	}

	@Transactional
	@Override
	public void productRemoveByStore(Integer productid) {
		pDao.productRemoveByStore(productid);
	}

	@Transactional
	@Override
	public List<ProductInfoBean> getAllProduct() {
		return pDao.getAllProduct();
	}	
	
	@Transactional
	@Override
	public List<ProductInfoBean> getAllProductByUserId(String userId) {
		return pDao.getAllProductByUserId(userId);
	}
	
	@Transactional
	@Override
	public Integer productChange(Integer productId, String status) {
		return pDao.productChange(productId, status);
	}
}
