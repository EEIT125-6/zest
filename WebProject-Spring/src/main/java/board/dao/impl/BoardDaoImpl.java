package board.dao.impl;



//import java.util.ArrayList;


import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import board.dao.BoardDao;
import xun.model.*;

@Repository
public class BoardDaoImpl implements BoardDao {
	
	SessionFactory factory;
		
		@Autowired
		public void setFactory(SessionFactory factory) {
			this.factory = factory;
		}
		
		
		@Override
		public BoardBean getBoardById(int id) {
			BoardBean bean = null;
			Session session = factory.getCurrentSession();
			String hql  = "FROM BoardBean bb WHERE bb.boardId = :id";
			try {
				bean = (BoardBean)session.createQuery(hql)
										.setParameter("id", id)
										.getSingleResult();
			} catch(NoResultException e) {
				;  // 表示查無紀錄
			}			
			return bean;			
		}
		
		
		@Override
		public BoardBean getBoardByName(String name) {
			BoardBean bean = null;
			Session session = factory.getCurrentSession();
			String hql  = "FROM BoardBean bb WHERE bb.name = :name";
			try {
				bean = (BoardBean)session.createQuery(hql)
										.setParameter("name", name)
										.getSingleResult();
			} catch(NoResultException e) {
				;  // 表示查無紀錄
			}			
			return bean;			
		}

//		@SuppressWarnings("unchecked")
//		@Override
//		public List<BoardBean> getBoard() {
//			Session session = factory.getCurrentSession();
//			String hql = "FROM BoardBean";
//			List<BoardBean> list = new ArrayList<>();
//			list = session.createQuery(hql).getResultList();
//			return list;
//		}

		@Override
		public int insertBoard(BoardBean bb){
			int count =0;
			Session session = factory.getCurrentSession();
			session.save(bb);
			count++;
			
			return count;
		}
		@Override
		public int updateBoard(BoardBean bb) {
			int count = 0;
			Session session = factory.getCurrentSession();
			session.saveOrUpdate(bb);
			count++;
			
			return count;
		}

		@Override
		public int deleteBoard(Integer id) {
			int result = 0;
			Session session=factory.getCurrentSession();
			/* 取得要刪除的物件 */
			BoardBean deleteBean = session.get(BoardBean.class, id);
			/* 執行刪除 */
			session.delete(deleteBean);
			result++;
			return result;	
		}
}
