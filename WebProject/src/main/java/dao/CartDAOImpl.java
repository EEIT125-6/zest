package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import model.CartDetailBean;
import model.OrderDetailBean;
import xun.model.ProductInfoBean;
import webUser.model.WebUserData;

@Repository
public class CartDAOImpl implements CartDAO {
	SessionFactory sessionFactory;

	@Autowired
	public void setFactory(SessionFactory factory) {
		this.sessionFactory = factory;
	}
	
	@Override
	public void save(CartDetailBean cdb) { //此方法永續化購物車內容
		sessionFactory.getCurrentSession().save(cdb);
	}
	
	@Override
	public void save(OrderDetailBean odb) { //此方法永續化訂單內容
		sessionFactory.getCurrentSession().save(odb);
	}

	@SuppressWarnings("unchecked")
	@Override
	public OrderDetailBean getOrderByUserId(WebUserData wus) { //
	    	String hql = "FROM OrderDetailBean as od WHERE od.webUserData =:wus AND od.purchase_Payment = false";
	    	List<OrderDetailBean> list = sessionFactory.getCurrentSession().createQuery(hql).setParameter("wus",wus).getResultList();
	    	if(list.size()>0) {
	    		return list.get(0);
	        }else {
	        OrderDetailBean odb = new OrderDetailBean(wus);	   
	        	sessionFactory.getCurrentSession().save(odb);
	        	return odb;
	        }  	
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<CartDetailBean> getCartList() {
		String hql = "FROM CartItemBean";
		List<CartDetailBean> list = sessionFactory.getCurrentSession().createQuery(hql).getResultList();
		System.out.println(list);
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ProductInfoBean> getProductList() {
		String hql = "FROM ProductInfoBean";
		List<ProductInfoBean> list = sessionFactory.getCurrentSession().createQuery(hql).getResultList();
		return list;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Integer checkAccountExist(String inputAccount) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :inputAccount";
		/* 取得當前Session */
		Session session = sessionFactory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query.setParameter("inputAccount", inputAccount).getResultList();
		System.out.println("list="+list);
		/* 由size()判結果 */
		checkResult = (list.size() > 0) ? 1 : 0;
		return checkResult;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<CartDetailBean> find(ProductInfoBean id) {
		String hql = "FROM CartDetailBean AS pif WHERE pif.product = :id";
		System.out.println("now inside DAO");
		System.out.println("idAAAAAAAAAAAAAAAAAA id="+id);
		Session session = sessionFactory.getCurrentSession();
		 List<CartDetailBean> list = session.createQuery(hql).setParameter("id", id).getResultList();
		 System.out.println("list inside DAO="+list);
		 return list;
	}

	@Override
	public ProductInfoBean findProductInfoBeanById(Integer id) {
		return sessionFactory.getCurrentSession().get(ProductInfoBean.class,id);
	}

	@Override
	public void deleteAll(Set<CartDetailBean> cdb) {
		for (CartDetailBean i :cdb){
			sessionFactory.getCurrentSession().delete(i);
		}
	}
		
	@Override
	public void delete(CartDetailBean k) {
			sessionFactory.getCurrentSession().delete(k);
	}
}
