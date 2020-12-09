package controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import model.BoardBean;
import utils.HibernateUtils;



public class AAA {
	
	public static void main(String args[]) {
//		BoardBean bob1 = findByPrimaryKey(1);
	System.out.println(findByPrimaryKey(1));
	}
	public static BoardBean findByPrimaryKey(Integer key) {
		SessionFactory factory = HibernateUtils.getSessionFactory();
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		BoardBean bob = null;
		try{
			bob = session.get(BoardBean.class, key);
			tx.commit();
			
		} catch(Exception ex){
			System.out.println(ex);
			if (tx != null) 
				tx.rollback();
		} finally{
			if (session != null)
			session.close();
		}
		return bob;
	}
	
}
