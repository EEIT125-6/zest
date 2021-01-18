package xun.dao.impl;

import java.util.List;

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

}
