package service;

import java.util.List;

import model.BookingBean;

public interface BookingService {

	//新增insert
	int insertBooking(BookingBean bookingData);

	//刪除delete
	int cancelBooking(BookingBean bean);

	//查詢query
	List<BookingBean> findBooking(String user_Id);

	//更新update
	int updateBooking(BookingBean bean);

	//檢查BookingNo
	boolean checkBooking(String bookingNo);
	
	//查詢單筆
	BookingBean singleBooking(String bookingNo);

	//管理員查詢
	List<BookingBean> allBooking();
	
	//檢查剩餘可訂位數
	int showSeating(String bookingdate, String time, String restaurant, String stname);
	
	//設定座位數
	int insertSeat(Integer storeId,Integer seating);
}
