package xun.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.New;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xun.dao.TraceDao;
import xun.model.TraceBean;

@Repository
public class TraceDaoImpl implements TraceDao {

	@Autowired
	SessionFactory factory;
	
	@Override
	public void addTrace(Integer memberId, Integer stId) {
		Session session = factory.getCurrentSession();
		TraceBean tb = new TraceBean();
		tb.setMemberId(memberId);
		tb.setStoreId(stId);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		tb.setTimestamp(timestamp);
		session.save(tb);
	}

	@Override
	public void removeTrace(Integer memberId, Integer stId) {
		Session session = factory.getCurrentSession();
		String hql = "DELETE TraceBean tb WHERE tb.storeId = :storeId And tb.memberId = :memberId";
		session.createQuery(hql).setParameter("storeId", stId)
								.setParameter("memberId", memberId)
								.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String DoITraceThisStore(Integer memberId, Integer stId) {
		Session session = factory.getCurrentSession();
		String hql = "Select storeId From TraceBean WHERE memberId = :memberId";
		List<Integer> list = session.createQuery(hql).setParameter("memberId", memberId).getResultList();
		for (Integer i:list) {
			if (i == stId) {
				return "Yes";
			}
		}
		return "No";
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TraceBean> StoreBeTrace(Integer stId) {
		Session session = factory.getCurrentSession();
		String hql = "From TraceBean tb Where tb.storeId = :storeId";
		List<TraceBean> list = session.createQuery(hql).setParameter("storeId", stId)
								.getResultList();
		return list;
	}

	@Override
	public void removeAllBeTraceStore(Integer stId) {
		Session session =factory.getCurrentSession();
		String hql = "DELETE TraceBean tb WHERE tb.storeId = :stId";
		session.createQuery(hql).setParameter("stId", stId).executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> StoreBeTraceQueryByMemberId(Integer stId) {
		Session session = factory.getCurrentSession();
		String hql = "select memberId From TraceBean tb Where tb.storeId = :storeId";
		List<Integer> list = session.createQuery(hql).setParameter("storeId", stId)
								.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> StoreStMonthTrace(Integer stId) {
		Session session = factory.getCurrentSession();
		List<Integer> Result = new ArrayList<Integer>();
//		String hql = "From TraceBean where timestamp BETWEEN '2021-01-01' AND '2021-12-31'";
//		String hql = "From TraceBean where timestamp BETWEEN '2021-12-01' AND DATEADD(mm,1,'2021-12-01')";
		String hql = "From TraceBean where storeId = :stId AND timestamp BETWEEN :std AND DATEADD(mm,1,:std)";
		for (int i = 1; i < 13; i++) {
			String limitSt;
			if(i<10) {
				limitSt = "2021/0"+String.valueOf(i)+"/01";
			}else {
				limitSt = "2021/"+String.valueOf(i)+"/01";
			}
			List<TraceBean> list = new ArrayList<TraceBean>();
			try {
				list = session.createQuery(hql)
										.setParameter("stId", stId)
										.setParameter("std", new SimpleDateFormat("yyyy/MM/dd").parse(limitSt))
										.getResultList();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		Result.add(list.size());
		}
		
		return Result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TraceBean> StoreStGender(Integer stId) {
		Session session = factory.getCurrentSession();
		String hql = "From TraceBean where storeId = :stId ";
		List<TraceBean> list = session.createQuery(hql).setParameter("stId", stId)
								.getResultList();
		return list;
	}

}
