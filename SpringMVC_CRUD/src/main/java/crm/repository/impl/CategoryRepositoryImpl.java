package crm.repository.impl;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import crm.model.Category;
import crm.model.Hobby;
import crm.repository.CategoryRepository;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

	@Autowired
	SessionFactory factory;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> getAllCategories() {
		String hql = "FROM Category";
		Session session = getSession();
		return session.createQuery(hql).getResultList();
	}
	public Session getSession() {
		return factory.getCurrentSession();
	}
	@Override
	public Category getCategory(Integer id) {
		Session session = getSession();
		return session.get(Category.class, id);
	}
}
