package webUser.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import webUser.model.UserIdentity;
import webUser.repository.IdentityRepository;

@Repository
public class IdentityRepositoryImpl implements IdentityRepository {
	/* 產生SessionFactory */
	@Autowired
	SessionFactory factory;
	
	/* 重複出現factory.getCurrentSession()，所以整理成一個方法，直接呼叫結果 */
	public Session getSession() {
		return factory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserIdentity> getIdentityList() {
		/* 透過HQL取值 */
		return getSession().createQuery("FROM UserIdentity AS ui ORDER BY ui.lv").getResultList();
	}

	@Override
	public UserIdentity getIdentity(Integer lv) {
		return getSession().get(UserIdentity.class, lv);
	}

}
