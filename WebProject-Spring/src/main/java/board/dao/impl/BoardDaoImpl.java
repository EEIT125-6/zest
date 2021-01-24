package board.dao.impl;

import java.util.ArrayList;
import java.util.List;

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
		String hql = "FROM BoardBean bb WHERE bb.boardid = :id";
		try {
			bean = (BoardBean) session.createQuery(hql).setParameter("id", id).getSingleResult();
		} catch (NoResultException e) {
			; // 表示查無紀錄
		}
		return bean;
	}

	@Override
	public BoardBean getBoardByName(String name) {
		BoardBean bean = null;
		Session session = factory.getCurrentSession();
		String hql = "FROM BoardBean bb WHERE bb.name = :name";
		try {
			bean = (BoardBean) session.createQuery(hql).setParameter("name", name).getSingleResult();
		} catch (NoResultException e) {
			; // 表示查無紀錄
		}
		return bean;
	}

	@Override
	public int insertBoard(BoardBean bb) {
		int count = 0;
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
	
	@SuppressWarnings("unchecked")
	public List<BoardBean> getAllcomment(){
		List<BoardBean> list = new ArrayList<>();
		Session session = factory.getCurrentSession();
		String HQL = "FROM BoardBean WHERE status = 0";
		list=session.createQuery(HQL).getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<BoardBean> getMember(String acount){
		List<BoardBean> list = new ArrayList<>();
		Session session = factory.getCurrentSession();
		String HQL = "FROM BoardBean WHERE status = 0 and name=:acount";
		list=session.createQuery(HQL).setParameter("acount", acount).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardBean> getStoreStar(StoreBean sb) {
		String hql = "FROM BoardBean bb WHERE bb.storebean = :storebean";
		List<BoardBean> list = factory.getCurrentSession().createQuery(hql)
				.setParameter("storebean", sb)
				.getResultList();
		return list;
	}
}
