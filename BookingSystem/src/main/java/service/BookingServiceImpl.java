package service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import dao.BookingDAO_Impl;
import dao.BookingDAO;
import model.BookingBean;
import utils.HibernateUtils;


public class BookingServiceImpl implements BookingService {
	
	SessionFactory factory=HibernateUtils.getSessionFactory();
	
	BookingDAO dao=new BookingDAO_Impl();
	
	//新增insert
	@Override
	public int insertBooking(BookingBean bookingData) {
	  	int count = 0;
	  	
		Session session = factory.getCurrentSession();
		Transaction tx=null;
		
		try {
			tx=session.beginTransaction();
			count=dao.insertBooking(bookingData);
			tx.commit();
			
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
		}
		return count;
	  }
	
	//刪除delete
	@Override
	public  int cancelBooking(BookingBean bean) {
		int count = 0;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.cancelBooking(bean);
			count++;
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return count;
	}
	//查詢query
	@Override
	public List <BookingBean> findBooking(String phone) {
		List<BookingBean> list = new ArrayList<>();
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			list =dao.findBooking(phone);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}
	
	//查詢單筆
		@Override
		public BookingBean singleBooking(String bookingNo) {
			Session session = factory.getCurrentSession();
			Transaction tx = null;
			BookingBean bean =null;
			try {
				tx = session.beginTransaction();
				bean=dao.singleBooking(bookingNo);
				tx.commit();
			} catch (Exception e) {
				if (tx != null) {
					tx.rollback();
				}
				e.printStackTrace();
			}
			return bean;
		}
	
	//更新update
	@Override
	public int updateBooking(BookingBean bean) {
		int count = 0;
		Session session = factory.getCurrentSession();
		Transaction tx=null;
		try {
			tx=session.beginTransaction();
			dao.updateBooking(bean);
			count++;
			tx.commit();
		} catch (Exception e) {
			if (tx!=null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return count;
	}
	
	//檢查BookingNo
	@Override
	public boolean checkBooking(String bookingNo) {
		boolean result=false;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			result=dao.checkBooking(bookingNo);
			tx.commit();
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return result;
	}
		
}
