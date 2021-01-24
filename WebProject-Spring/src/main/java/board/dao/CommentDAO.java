//package board.dao;
//
//import java.util.List;
//
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
////import org.hibernate.Transaction;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import xun.model.BoardBean;
//
//@Repository
//public class CommentDAO{
//	
//	@Autowired
//	SessionFactory factory ;
//	
//	@SuppressWarnings("unchecked")
//	public BoardBean selectComment(String name) throws Exception {
//		BoardBean cb = new BoardBean();
//		String hql = "FROM BoardBean WHERE name = :boardname";
//		
//		Session session = factory.getCurrentSession();
//		
//		Query<BoardBean> query = session.createQuery(hql);
//		query.setParameter("boardname", name);
//		List<BoardBean> list = query.getResultList();
//		
//		if (list.size() == 1) {
//			cb = list.get(0);
//		} else {
//			cb = null;
//		}
//
//		return cb;
//	}
//	
//	public int updateComment(BoardBean cb) {
//		int count = 0;
//		Session session = factory.getCurrentSession();
//		session.saveOrUpdate(cb);
//		count++;
//		
//		return count;
//	}
//	
//	public int deleteComment(Integer id) {
//		int result = 0;
//		Session session=factory.getCurrentSession();
//		/* 取得要刪除的物件 */
//		BoardBean deleteBean = session.get(BoardBean.class, id);
//		/* 執行刪除 */
//		session.delete(deleteBean);
//		result++;
//		return result;	
//	}
//	
//	public int insertComment(BoardBean cb){
//		int count =0;
//		Session session = factory.getCurrentSession();
//		session.save(cb);
//		count++;
//		
//		return count;
//	}
//}
