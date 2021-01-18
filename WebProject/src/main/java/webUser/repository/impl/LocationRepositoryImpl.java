package webUser.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import webUser.model.CityInfo;
import webUser.repository.LocationRepository;

@Repository
public class LocationRepositoryImpl implements LocationRepository {
	/* 產生SessionFactory */
	@Autowired
	SessionFactory factory;
	
	/* 重複出現factory.getCurrentSession()，所以整理成一個方法，直接呼叫結果 */
	public Session getSession() {
		return factory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CityInfo> getLocationInfoList() {
		/* 透過HQL取值 */
		return getSession().createQuery("FROM CityInfo as ci ORDER BY ci.cityCode").getResultList();
	}

	@Override
	public CityInfo getLocationInfo(Integer cityCode) {
		return getSession().get(CityInfo.class, cityCode);
	}
}
