package dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.ProductInfoBean;
import utils.HibernateUtils;

public class DAOImplementation implements DAODef {
	private SessionFactory factory = HibernateUtils.getSessionFactory();
	
	Transaction tx = null;

	@SuppressWarnings("unchecked")
	public List<ProductInfoBean> findAll() {
		return (List<ProductInfoBean>)(factory.getCurrentSession().createQuery("FROM ProductInfoBean").getResultList());
	}

	public ProductInfoBean find(int id) {
		return factory.getCurrentSession().get(ProductInfoBean.class,id);
	}
}
