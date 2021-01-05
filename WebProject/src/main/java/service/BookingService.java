package service;

import java.util.List;

import model.BookingBean;

public interface BookingService {

	//新增insert
	int insertBooking(BookingBean bookingData);

	//刪除delete
	int cancelBooking(BookingBean bean);

	//查詢query
	List<BookingBean> findBooking(String phone);

	//更新update
	int updateBooking(BookingBean bean);

	//檢查BookingNo
	boolean checkBooking(String bookingNo);

	BookingBean singleBooking(String bookingNo);
}
