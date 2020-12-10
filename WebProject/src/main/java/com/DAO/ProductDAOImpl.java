package com.DAO;

import entities.com.*;

import java.util.*;
//import java.util.logging.Logger;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;

//import java.util.logging.FileHandler;
//import java.util.logging.Level;
//import java.util.logging.LogManager;

import org.hibernate.Criteria;
//import org.hibernate.Session;
import org.hibernate.SessionFactory;
//import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Repository
public class ProductDAOImpl implements ProductDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings({ "unchecked" })
//	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<Product> findAll() {
//		Session session = sessionFactory.getCurrentSession();
//		CriteriaBuilder build = session.getCriteriaBuilder();
//		Criteria criteria = (Criteria) build.createQuery(Product.class);
		return ((Criteria) sessionFactory.getCurrentSession().getCriteriaBuilder().createQuery(Product.class)).list(); 

	} //等技術長完成Spring MVC連線公版

//	@SuppressWarnings({"deprecation"})
	@Override
	public Product find(int id) {
		return sessionFactory.getCurrentSession().get(Product.class,id); //等技術長完成Spring MVC連線公版

	}

}
