package xun.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xun.dao.ProductDao;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;

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
				+ " WHERE product_id = :id";
		Session session = factory.getCurrentSession();
		
		result = session.createQuery(hql)
				.setParameter("name", pi.getProduct_name())
				.setParameter("shop", pi.getProduct_shop())
				.setParameter("price", pi.getProduct_price())
				.setParameter("pictureurl", pi.getProduct_picture())
				.setParameter("quantity", pi.getProduct_quantity())
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

	@Override
	public ProductInfoBean get(Integer productid) {
		
		return  factory.getCurrentSession().get(ProductInfoBean.class,productid);
	}

	@Override
	public Integer deleteALLProduct(StoreBean sb) {
		String hql = "DELETE ProductInfoBean pib WHERE pib.storebean = :storebean";
		Session session = factory.getCurrentSession();
		Integer result = session.createQuery(hql)
				.setParameter("storebean", sb)
				.executeUpdate();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfoBean> getStoreProduct(StoreBean sb) {
		String hql = "FROM ProductInfoBean pib WHERE pib.storebean = :storebean";
		List<ProductInfoBean> list = factory.getCurrentSession().createQuery(hql) 
				.setParameter("storebean", sb)
				.getResultList();
		return list;
	}

}
