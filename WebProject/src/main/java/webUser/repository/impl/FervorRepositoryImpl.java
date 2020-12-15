package webUser.repository.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import webUser.model.FoodFervor;
import webUser.repository.FervorRepository;

@Repository
public class FervorRepositoryImpl implements FervorRepository {
	/* 產生SessionFactory */
	@Autowired
	SessionFactory factory;
	
	/* 重複出現factory.getCurrentSession()，所以整理成一個方法，直接呼叫結果 */
	public Session getSession() {
		return factory.getCurrentSession();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FoodFervor> getFoodFervorList() {
		/* 透過HQL取值 */
		return getSession().createQuery("FROM FoodFervor AS ff ORDER BY ff.fervorCode").getResultList();
	}

	@Override
	public FoodFervor getFoodFervor(Integer fervorCode) {
		return getSession().get(FoodFervor.class, fervorCode);
	}

}
