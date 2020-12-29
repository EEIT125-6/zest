package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import model.CartItemBean;
import xun.model.ProductInfoBean;
import webUser.model.WebUserData;

@Repository
public class CartDAOImpl implements CartDAO {

//	@Autowired
//	CartItemBean product;
	SessionFactory sessionFactory;

	@Autowired
	public void setFactory(SessionFactory factory) {
		this.sessionFactory = factory;
	}

	


	@Override
	public CartItemBean getCartByUser(String inputId) {
		System.out.println("DAOLayerIdCheck="+inputId);
		CartItemBean CIB = new CartItemBean();
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
	@SuppressWarnings("unchecked")
	public List<CartItemBean> getCartList() {
		String hql = "FROM CartItem";
		List<CartItemBean> list = sessionFactory.getCurrentSession().createQuery(hql).getResultList();
		return list;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ProductInfoBean> getProductList() {
		String hql = "FROM ProdcutInfo";
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
	public List<CartItemBean> find(int id) {
		String hql = "FROM CartItem";
		List<CartItemBean> list = sessionFactory.getCurrentSession().createNamedQuery(hql).getResultList();
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
