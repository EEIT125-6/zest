package dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import dao.StoreDao;
import model.StoreBean;
import utils.HibernateUtils;

public class StoreHibernateDaoImpl implements StoreDao {

	SessionFactory factory = HibernateUtils.getSessionFactory();
	
	@Override
	public int save(StoreBean sb) {
		int count = 0;
		Session session = factory.getCurrentSession();
		session.save(sb);
		count++;
		return count;
	}

	@Override
	public int updateStore(StoreBean sb) {
//		int count = 0;
		int result = 0;
			String hql = "Update StoreBean set stname = :stname , sclass = :sclass "+
						" , saddress = :saddress , stitd = :stitd , tel = :tel " +
						" WHERE id = :id";
			Session session = factory.getCurrentSession();
			
			result = session.createQuery(hql)
					.setParameter("stname", sb.getStname())
					.setParameter("sclass", sb.getSclass())
					.setParameter("saddress", sb.getSaddress())
					.setParameter("stitd", sb.getStitd())
					.setParameter("tel", sb.getTel())
					.setParameter("id", sb.getId())
					.executeUpdate();
//			session.saveOrUpdate(sb);
//			count++;
		return result;
	}



	@SuppressWarnings("unchecked")
	@Override
	public boolean isDup(String stname) {
		boolean result = false;
		String hql = "FROM StoreBean s WHERE s.stname = :stname1";
		Session session = factory.getCurrentSession();
		Query<StoreBean> query = session.createQuery(hql);
		List<StoreBean> list=(List<StoreBean>) query.setParameter("stname1", stname).getResultList();
		if(list.size()>0) {
			return true;
		}
		return result;
	}

	@Override
	public int deleteStore(StoreBean sb) {
		int result = 0;
		String hql = "DELETE StoreBean sb WHERE sb.id = :id0";
		Session session = factory.getCurrentSession();
		result = session.createQuery(hql)
				.setParameter("id0", sb.getId())
				.executeUpdate();
		return result;
	}

	@Override
	public void photoStore(StoreBean sb) {
		Session session = factory.getCurrentSession();
		String hql = "Update StoreBean sb set photourl = :photourl WHERE id = :id";
		
		session.createQuery(hql).setParameter("photourl", sb.getPhotourl())
								.setParameter("id", sb.getId())
								.executeUpdate();
	}

	@Override
	public void bannerStore(StoreBean sb) {
		Session session = factory.getCurrentSession();
		String hql = "Update StoreBean sb set bannerurl = :bannerurl WHERE id = :id";
		
		session.createQuery(hql).setParameter("bannerurl", sb.getBannerurl())
								.setParameter("id", sb.getId())
								.executeUpdate();
		
	}

}
