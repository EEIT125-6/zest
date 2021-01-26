package service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.BookingDAO;
import model.BookingBean;

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
	public List <BookingBean> findBooking(String user_id) {
		List<BookingBean> list = new ArrayList<>();
		list =dao.findBooking(user_id);
			
		return list;
	}
	
	//管理員查詢
	@Override
	public List <BookingBean> allBooking() {
		List<BookingBean> list = new ArrayList<>();
		list =dao.allBooking();
			
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
	
	//檢查剩餘可訂位數
	@Override
	public int showSeating(String bookingdate,String time,String restaurant,String stname) {
		int seating = dao.maxBooking(stname)-dao.nowBooking(bookingdate, time, restaurant);
		return seating;		
	}
	
	//設定座位數
	@Override
	public int insertSeat(Integer storeId,Integer seating) {
		int count = 0;
		dao.insertSeat(storeId,seating);
		count++;
			
		return count;
	}
	
	//刪除delete
	@Override
	public  int cancelBooking(String bookingNo) {
		int count = 0;
		dao.cancelBooking(bookingNo);
		count++;
		
		return count;
	}
}
