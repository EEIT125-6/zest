package webUser.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import webUser.model.Gender;
import webUser.repository.GenderRepository;

@Repository
public class GenderRepositoryImpl implements GenderRepository {
	/* 產生SessionFactory */
	@Autowired
	SessionFactory factory;
	
	/* 重複出現factory.getCurrentSession()，所以整理成一個方法，直接呼叫結果 */
	public Session getSession() {
		return factory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Gender> getGenderList() {
		/* 透過HQL取值 */
		return getSession().createQuery("FROM Gender AS g ORDER BY g.genderCode").getResultList();
	}

	@Override
	public Gender getGender(String genderCode) {
		return getSession().get(Gender.class, genderCode);
	}
}
