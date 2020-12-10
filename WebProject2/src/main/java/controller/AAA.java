package controller;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.BoardBean;
import model.ProductInfoBean;
import utils.HibernateUtils;



public class AAA {
	
	public static void main(String args[]) {
//		BoardBean bob1 = findByPrimaryKey(1);
	System.out.println(findByPrimaryKey(1,17));
	
	}
	public static List<ProductInfoBean> findByPrimaryKey(Integer key,Integer key2) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		BoardBean bob = null;
		ProductInfoBean pib = null;
		List<ProductInfoBean> list = null;
		try{
//			bob = session.get(BoardBean.class, key);
//			pib = session.get(ProductInfoBean.class,key2);
			String hql = "From ProductInfoBean WHERE Store_Id = :sb";
			list=session.createQuery(hql).setParameter("sb", key2).getResultList();
			
			tx.commit();
			
		} catch(Exception ex){
			System.out.println(ex);
			if (tx != null) 
				tx.rollback();
		} finally{
			if (session != null)
			session.close();
		}
		return list;
	}
	
}
