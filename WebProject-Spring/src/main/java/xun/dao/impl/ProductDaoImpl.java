package xun.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xun.dao.ProductDao;
import xun.model.ProductInfoBean;

@Repository
public class ProductDaoImpl implements ProductDao{

	@Autowired
	SessionFactory factory;
	
	@Override
	public Integer save(ProductInfoBean pi) {
		Integer count = 0;
		Session session = factory.getCurrentSession();
		session.save(pi);
		count++;
		return count;
	}

	@Override
	public Integer updateProduct(ProductInfoBean pi) {
		Integer result = 0;
		String hql = "Update ProductInfoBean set product_name = :name"
				+ ",product_shop = :shop ,product_price = :price"
				+ ",product_picture = :pictureurl,product_quantity = :quantity"
				+ ",storebean = :storebean WHERE product_id = :id";
		Session session = factory.getCurrentSession();
		
		result = session.createQuery(hql)
				.setParameter("name", pi.getProduct_name())
				.setParameter("shop", pi.getProduct_shop())
				.setParameter("price", pi.getProduct_price())
				.setParameter("pictureurl", pi.getProduct_picture())
				.setParameter("quantity", pi.getProduct_quantity())
				.setParameter("storebean", pi.getStorebean())
				.setParameter("id", pi.getProduct_id())
				.executeUpdate();
		return result;
	}

	@Override
	public Integer deleteProduct(ProductInfoBean pi) {
		Integer result = 0;
		String hql = "DELETE ProductInfoBean pib WHERE pib.product_id = :id";
		
		Session session = factory.getCurrentSession();
		result = session.createQuery(hql)
				.setParameter("id", pi.getProduct_id())
				.executeUpdate();
		return result;
	}

}
