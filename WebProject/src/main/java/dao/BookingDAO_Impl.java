package dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.core.util.SystemClock;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import model.BookingBean;

@Repository
public class BookingDAO_Impl implements BookingDAO {
	
	@Autowired
	SessionFactory factory;
	
	//新增insert
	@Override
	public int insertBooking(BookingBean bookingData) {
	  	int count = 0;
		Session session = factory.getCurrentSession();
		session.save(bookingData);
		count++;
		return count;
		  }
	
	//delete
	@Override
	public  int cancelBooking(BookingBean bean) {
		int count = 0;
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(bean);
		count++;
		return count;
	}
	
	//update
	@Override
	public int updateBooking(BookingBean bean) {
		int count = 0;
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(bean);
		count++;
		return count;
	}
	
	//查詢
	@SuppressWarnings("unchecked")
	@Override
	public List <BookingBean> findBooking(String user_id) {
		String hql = "FROM BookingBean b where b.userData.userId= :user_id";
		Session session = factory.getCurrentSession();
		Query<BookingBean> query = session.createQuery(hql);
		List<BookingBean> list = query.setParameter("user_id", user_id).getResultList();
		System.out.println(list.size()+"筆");
		for (int i = 0; i < list.size(); i++) {
			String date=list.get(i).getBookingdate();
			Date booking = Date.valueOf(date);
			Date today = Date.valueOf(LocalDate.now());        
				
			if (booking.compareTo(today)<0) {
				list.get(i).setStatus(2);
				updateBooking(list.get(i));
			}
		}
		return list;
	}
	
	//管理員查詢
		@SuppressWarnings("unchecked")
		@Override
		public List <BookingBean> allBooking() {
			String hql = "FROM BookingBean";
			Session session = factory.getCurrentSession();
			Query<BookingBean> query = session.createQuery(hql);
			List<BookingBean> list = query.getResultList();
			System.out.println(list.size()+"筆");
			for (int i = 0; i < list.size(); i++) {
				String date=list.get(i).getBookingdate();
				System.out.println("test "+list.get(i).getUser_id().getUserId());
				Date booking = Date.valueOf(date);
				Date today = Date.valueOf(LocalDate.now());        
					
				if (booking.compareTo(today)<0) {
					list.get(i).setStatus(2);
					updateBooking(list.get(i));
				}
			}
			return list;
		}
	
	//查詢單筆
	@SuppressWarnings("unchecked")
	@Override
	public BookingBean singleBooking(String bookingNo) {
		String hql = "FROM BookingBean b where b.bookingNo= :bookingNo";
		Session session = factory.getCurrentSession();
		Query<BookingBean> query = session.createQuery(hql);
		List<BookingBean> list = query.setParameter("bookingNo", bookingNo).getResultList();
		if (list.size()==1) {
			return list.get(0);
		}
		return null;
	}
	
	//check BookingNo
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkBooking(String bookingNo) {
		
		String hql ="from BookingBean b where b.bookingNo = :bookingNo";
		Session session = factory.getCurrentSession();
		Query<BookingBean> query = session.createQuery(hql);
		List <BookingBean> list=query.setParameter("bookingNo", bookingNo).getResultList();
		if (list.size()==0) {
			return false;
		}
		return true;
	}
}
