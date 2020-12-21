package service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.query.Query;
import org.springframework.stereotype.Service;
import dao.BookingDAO_Impl;
import dao.BookingDAO;
import model.BookingBean;
import utils.HibernateUtils;

@Transactional
@Service
public class BookingServiceImpl implements BookingService {
	
	@Autowired
	BookingDAO dao;
	
	//新增insert
	@Override
	public int insertBooking(BookingBean bookingData) {
	  	int count = 0;
	  	count=dao.insertBooking(bookingData);
			
		return count;
	  }
	
	//刪除delete
	@Override
	public  int cancelBooking(BookingBean bean) {
		int count = 0;
		dao.cancelBooking(bean);
		count++;
			
		return count;
	}
	//查詢query
	@Override
	public List <BookingBean> findBooking(String phone) {
		List<BookingBean> list = new ArrayList<>();
		list =dao.findBooking(phone);
			
		return list;
	}
	
	//查詢單筆
	@Override
	public BookingBean singleBooking(String bookingNo) {
		BookingBean bean =null;
		bean=dao.singleBooking(bookingNo);
			
		return bean;
	}
	
	//更新update
	@Override
	public int updateBooking(BookingBean bean) {
		int count = 0;
		dao.updateBooking(bean);
		count++;
			
		return count;
	}
	
	//檢查BookingNo
	@Override
	public boolean checkBooking(String bookingNo) {
		boolean result=false;
		result=dao.checkBooking(bookingNo);		
		
		return result;
	}		
}
