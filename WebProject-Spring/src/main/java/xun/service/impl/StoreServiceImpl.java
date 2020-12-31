package xun.service.impl;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

//import org.aspectj.weaver.tools.Trace;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import xun.dao.StoreDao;
//import xun.dao.impl.StoreHibernateDaoImpl;
import xun.model.BoardBean;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.StoreService;


@Service
public class StoreServiceImpl implements StoreService {

//	SessionFactory factory = HibernateUtils.getSessionFactory();
//	@Autowired
//	SessionFactory factory;
	
	
//	StoreDao dao = new StoreHibernateDaoImpl();
	@Autowired
	StoreDao dao;
	
	@Transactional
	@Override
	public int save(StoreBean sb) {
		int count = 0;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			dao.save(sb);
			count++;
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return count;
	}

	@Transactional
	@Override
	public int updateStore(StoreBean sb) {
		int result = 0;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		
//		try {
//			tx = session.beginTransaction();
			dao.updateStore(sb);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return result;
	}
	
	@Transactional
	@Override
	public List<StoreBean> getSinglest(String stname) {
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
		List<StoreBean> list = null;
//		try {
//			tx = session.beginTransaction();
			list = dao.getSinglest(stname);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return list;
	}

	@Transactional
	@Override
	public int deleteStore(StoreBean sb) {
		int count = 0;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
		
//		try {
//			tx = session.beginTransaction();
			dao.deleteStore(sb);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return count;
	}

	@Transactional
	@Override
	public boolean isDup(String stname) {
		boolean result = false;
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			result = dao.isDup(stname);
//			tx.commit();
//		}catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return result;
	}

	@Transactional
	@Override
	public void photoStore(StoreBean sb) {
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			dao.photoStore(sb);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
//
//		
	}

	@Transactional
	@Override
	public void bannerStore(StoreBean sb) {
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
//		try {
//			tx = session.beginTransaction();
			dao.bannerStore(sb);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		
	}

	@Transactional
	@Override
	public List<StoreBean> getClassstore(String sclass) {
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
		List<StoreBean> list = null;
//		try {
//			tx = session.beginTransaction();
			list = dao.getClassstore(sclass);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return list;
	}

	@Transactional
	@Override
	public List<StoreBean> getNamestore(String stname) {
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
		List<StoreBean> list = null;
//		try {
//			tx = session.beginTransaction();
			list = dao.getNamestore(stname);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return list;
	}

	@Transactional
	@Override
	public List<StoreBean> getFullstore(Integer id) {
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
		List<StoreBean> list = null;
//		try {
//			tx = session.beginTransaction();
			list = dao.getFullstore(id);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return list;
		
	}

	@Transactional
	@Override
	public List<StoreBean> getAdvertisementstore() {
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
		List<StoreBean> list = null;
//		try {
//			tx = session.beginTransaction();
			List<StoreBean> list2 = dao.getAdvertisementstore();
			Collections.shuffle(list2);
			list = list2.subList(0, 4);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return list;
	}

	@Transactional
	@Override
	public List<StoreBean> getAdvertisementphotostore() {
//		Session session = factory.getCurrentSession();
//		Transaction tx = null;
		List<StoreBean> list = null;
//		try {
//			tx = session.beginTransaction();
			List<StoreBean> list1 = dao.getAdvertisementphotostore();
			Collections.shuffle(list1);
			list = list1.subList(0, 6);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return list;
	}

	@Transactional
	@Override
	public List<BoardBean> getComment(Integer stid) {
//		Session session = factory.getCurrentSession();
//		Transaction tx =  null;
		List<BoardBean> list = null;
//		try {
//			tx = session.beginTransaction();
			list = dao.getComment(stid);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return list;
	}

	@Transactional
	@Override
	public List<ProductInfoBean> getProductInfoBeans(Integer stid) {
//		Session session = factory.getCurrentSession();
//		Transaction tx =  null;
		List<ProductInfoBean> list = null;
//		try {
//			tx = session.beginTransaction();
			list = dao.getProductInfoBeans(stid);
//			tx.commit();
//		} catch (Exception e) {
//			if(tx != null) {
//				tx.rollback();
//			}
//			e.printStackTrace();
//		}
		return list;
	}

	@Transactional
	@Override
	public StoreBean get(Integer id) {
		return dao.get(id);
	}

	@Transactional
	@Override
	public Integer setStorePrice(Integer price, Integer id) {
		return dao.setStorePrice(price, id);
	}

	@Transactional
	@Override
	public List<StoreBean> getStoreByClassAndPrice(String sclass, Integer price) {
		return dao.getStorebyClassandPrice(sclass, price);
	}
	
}
