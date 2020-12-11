package orange.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import orange.dao.CommentDAO;
import model.BoardBean;
import orange.util.HibernateUtils;


public class CommentService {

	SessionFactory factory = HibernateUtils.getSessionFactory();
	CommentDAO dao = new CommentDAO();
	
	public int insertComment(BoardBean cb) {
		int count = 0;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.insertComment(cb);
			count++;
			tx.commit();
		} catch(Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return count;
	}
	
	public int deleteComment(Integer id) {
		int count = 0;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.deleteComment(id);
			count++;
			tx.commit();
		} catch(Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}		
		return count;
	}

	public int updateComment(BoardBean cb) {
		int count = 0;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.updateComment(cb);
			count++;
			tx.commit();
		} catch(Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}	
		return count;
	}
	
	public BoardBean selectComment(String name) {
		BoardBean cb = new BoardBean();
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			cb = dao.selectComment(name);
			tx.commit();
		} catch(Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return cb;
	}
}
