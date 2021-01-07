package webUser.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import webUser.model.UserWilling;
import webUser.repository.WillingRepository;

@Repository
public class WillingRepositoryImpl implements WillingRepository {
	/* 產生SessionFactory */
	@Autowired
	SessionFactory factory;
	
	/* 重複出現factory.getCurrentSession()，所以整理成一個方法，直接呼叫結果 */
	public Session getSession() {
		return factory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserWilling> getUserWillingList() {
		/* 透過HQL取值 */
		return getSession().createQuery("FROM UserWilling AS uw ORDER BY uw.willingCode DESC").getResultList();
	}

	@Override
	public UserWilling getUserWilling(String willingCode) {
		return getSession().get(UserWilling.class, willingCode);
	}

}
