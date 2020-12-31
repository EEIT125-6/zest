package dao;

import java.sql.SQLException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import model.CartItemBean;
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
	@Transactional
	public CartItemBean getCartByUser(String inputId) {
		System.out.println("DAOLayerIdCheck="+inputId);
		CartItemBean CIB = new CartItemBean(null, 0);
		try {
			CIB = sessionFactory.getCurrentSession().get(CartItemBean.class, inputId);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("CheckCIB="+CIB);
		return CIB;
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<CartItemBean> getCartList() {
		String hql = "FROM CartItemBean";
		List<CartItemBean> list = sessionFactory.getCurrentSession().createQuery(hql).getResultList();
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
	@Transactional
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
	@Transactional
	@SuppressWarnings("unchecked")
	public List<ProductInfoBean> find(String id) {
		String hql = "FROM ProductInfoBean AS pif WHERE pif.product_id = '"+id+"'";
		Session session = sessionFactory.getCurrentSession();
		 Query <ProductInfoBean> query = session.createQuery(hql);
		 List<ProductInfoBean> list = query.getResultList();
		 System.out.println(list);
		 System.out.println("DAO則安低能");
		 return list;
	}




//	@Override
//	public String addProduct(Integer addItem) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//
//
//	@Override
//	public String removeProduct(Integer removeItem) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
}
