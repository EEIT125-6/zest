package xun.service.impl;



import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xun.dao.ProductDao;
import xun.model.ProductInfoBean;
import xun.service.ProductService;

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
	
	
}
