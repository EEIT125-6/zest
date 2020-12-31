package dao;

import java.util.List;
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
	
	//查詢
	@SuppressWarnings("unchecked")
	@Override
	public List <BookingBean> findBooking(String phone) {
		String hql = "FROM BookingBean b where b.phone= :phone";
		Session session = factory.getCurrentSession();
		Query<BookingBean> query = session.createQuery(hql);
		List<BookingBean> list = query.setParameter("phone", phone).getResultList();
		System.out.println(list.size()+"筆");
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
	
	//update
	@Override
	public int updateBooking(BookingBean bean) {
		int count = 0;
		Session session = factory.getCurrentSession();
		session.saveOrUpdate(bean);
		count++;
		return count;
	}
	
	//check BookingNo
	@SuppressWarnings("unchecked")
	@Override
	public boolean checkBooking(String bookingNo) {
		
		String hql ="from BookingBean b where b.bookingNo = '" + bookingNo + "'";
		Session session = factory.getCurrentSession();
		Query<BookingBean> query = session.createQuery(hql);
		List <BookingBean> list=query.getResultList();
		if (list.size()==0) {
			return false;
		}
		return true;
	}
}
