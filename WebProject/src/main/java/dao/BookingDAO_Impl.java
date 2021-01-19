package dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import model.BookingBean;
import model.BookingData;

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
		String hql = "FROM BookingBean b where b.user_id.userId= :user_id";
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
	
	//查詢目前總訂位數
	@SuppressWarnings("unchecked")
	@Override
	public int nowBooking(String bookingdate,String time,String restaurant) {
		String hql = "FROM BookingBean b WHERE b.restaurant= :restaurant AND b.bookingdate= :bookingdate AND b.time= :time";
		Session session = factory.getCurrentSession();
		Query<BookingBean> query = session.createQuery(hql);
		List<BookingBean> list = query.setParameter("bookingdate", bookingdate)
									.setParameter("time", time)
									.setParameter("restaurant", restaurant).getResultList();
		int sum=0;
		int count;
		if (list.size()>=1) {
			for (int i = 0; i < list.size(); i++) {
				count=list.get(i).getNumber();
				sum+=count;
			}
			System.out.println("now="+sum);
			return sum;
		}else {
			return 0;
		}
	}
	
	//查詢最大可訂位數
	@SuppressWarnings("unchecked")
	@Override
	public int maxBooking(String stname) {
		String hql = "FROM BookingData b where b.storeBean.stname= :store";
		Session session = factory.getCurrentSession();
		Query<BookingData> query = session.createQuery(hql);
		List<BookingData> list = query.setParameter("store", stname).getResultList();
		if (list.size()>=1) {
			System.out.println("max="+list.get(0).getSeating());
			return list.get(0).getSeating();
		}
		return 0;
	}
}
