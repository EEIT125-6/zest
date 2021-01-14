package dao;

import java.util.List;
import model.BookingBean;

public interface BookingDAO {

	//新增insert
	int insertBooking(BookingBean bookingData);

	//刪除delete
	int cancelBooking(BookingBean bean);

	//查詢query
	List<BookingBean> findBooking(String phone);

	//更新update
	int updateBooking(BookingBean bean);
	
	//check BookingNo
	boolean checkBooking(String bookingNo);

	BookingBean singleBooking(String bookingNo);

}
