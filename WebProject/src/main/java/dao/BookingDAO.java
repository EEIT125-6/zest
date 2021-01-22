package dao;

import java.util.List;
import model.BookingBean;

public interface BookingDAO {

	//新增insert
	int insertBooking(BookingBean bookingData);

	//刪除delete
	int cancelBooking(BookingBean bean);

	//查詢query
	List<BookingBean> findBooking(String user_id);

	//更新update
	int updateBooking(BookingBean bean);
	
	//check BookingNo
	boolean checkBooking(String bookingNo);
	
	//查詢單筆
	BookingBean singleBooking(String bookingNo);
	
	//管理員查詢
	List<BookingBean> allBooking();
	
	//查詢目前總訂位數
	int nowBooking(String bookingdate, String time, String restaurant);

	//查詢最大可訂位數
	int maxBooking(String stname);
	
	//設定座位數
	int insertSeat(Integer storeId,Integer seating);
}
