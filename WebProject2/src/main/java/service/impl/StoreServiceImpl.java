package service.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import dao.StoreDao;
import dao.impl.StoreHibernateDaoImpl;
import model.StoreBean;
import service.StoreService;
import utils.HibernateUtils;

public class StoreServiceImpl implements StoreService {

	SessionFactory factory = HibernateUtils.getSessionFactory();
	StoreDao dao = new StoreHibernateDaoImpl();
	
	@Override
	public int save(StoreBean sb) {
		int count = 0;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.save(sb);
			count++;
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int updateStore(StoreBean sb) {
		int result = 0;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			dao.updateStore(sb);
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return result;
	}
	
	@Override
	public List<StoreBean> getSinglest(String stname) {
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		List<StoreBean> list = null;
		try {
			tx = session.beginTransaction();
			list = dao.getSinglest(stname);
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int deleteStore(StoreBean sb) {
		int count = 0;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			dao.deleteStore(sb);
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean isDup(String stname) {
		boolean result = false;
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			result = dao.isDup(stname);
			tx.commit();
		}catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public void photoStore(StoreBean sb) {
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.photoStore(sb);
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}

		
	}

	@Override
	public void bannerStore(StoreBean sb) {
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			dao.bannerStore(sb);
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		
	}

	@Override
	public List<StoreBean> getClassstore(String sclass) {
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		List<StoreBean> list = null;
		try {
			tx = session.beginTransaction();
			list = dao.getClassstore(sclass);
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<StoreBean> getNamestore(String stname) {
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		List<StoreBean> list = null;
		try {
			tx = session.beginTransaction();
			list = dao.getNamestore(stname);
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<StoreBean> getFullstore(Integer id) {
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		List<StoreBean> list = null;
		try {
			tx = session.beginTransaction();
			list = dao.getFullstore(id);
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return list;
		
	}

	@Override
	public List<StoreBean> getAdvertisementstore() {
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		List<StoreBean> list = null;
		try {
			tx = session.beginTransaction();
			list = dao.getAdvertisementstore();
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<StoreBean> getAdvertisementphotostore() {
		Session session = factory.getCurrentSession();
		Transaction tx = null;
		List<StoreBean> list = null;
		try {
			tx = session.beginTransaction();
			list = dao.getAdvertisementphotostore();
			tx.commit();
		} catch (Exception e) {
			if(tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}
		return list;
	}



}
