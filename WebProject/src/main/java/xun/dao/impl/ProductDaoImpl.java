package xun.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import xun.dao.ProductDao;
import xun.model.StoreBean;
import xun.model.ProductInfoBean;

@Repository
public class ProductDaoImpl implements ProductDao{

	@Autowired
	SessionFactory factory;
	
	@Override
	public Integer save(ProductInfoBean pi) {
		Integer count = 0;
		Session session = factory.getCurrentSession();
		session.save(pi);
		count++;
		return count;
	}

	@Override
	public Integer updateProduct(ProductInfoBean pi) {
		Integer result = 0;
		String hql = "Update ProductInfoBean set product_name = :name"
				+ ",product_shop = :shop ,product_price = :price"
				+ ",product_picture = :pictureurl,product_quantity = :quantity"
				+ " WHERE product_id = :id";
		Session session = factory.getCurrentSession();
		
		result = session.createQuery(hql)
				.setParameter("name", pi.getProduct_name())
				.setParameter("shop", pi.getProduct_shop())
				.setParameter("price", pi.getProduct_price())
				.setParameter("pictureurl", pi.getProduct_picture())
				.setParameter("quantity", pi.getProduct_quantity())
				.setParameter("id", pi.getProduct_id())
				.executeUpdate();
		return result;
	}

	@Override
	public Integer deleteProduct(ProductInfoBean pi) {
		Integer result = 0;
		String hql = "DELETE ProductInfoBean pib WHERE pib.product_id = :id";
		
		Session session = factory.getCurrentSession();
		result = session.createQuery(hql)
				.setParameter("id", pi.getProduct_id())
				.executeUpdate();
		return result;
	}

	@Override
	public ProductInfoBean get(Integer productid) {
		
		return  factory.getCurrentSession().get(ProductInfoBean.class,productid);
	}

	@Override
	public Integer deleteALLProduct(StoreBean sb) {
		String hql = "DELETE ProductInfoBean pib WHERE pib.storebean = :storebean";
		Session session = factory.getCurrentSession();
		Integer result = session.createQuery(hql)
				.setParameter("storebean", sb)
				.executeUpdate();
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfoBean> getStoreProduct(StoreBean sb) {
		String hql = "FROM ProductInfoBean pib WHERE pib.storebean = :storebean";
		List<ProductInfoBean> list = factory.getCurrentSession().createQuery(hql) 
				.setParameter("storebean", sb)
				.getResultList();
		return list;
	}

//	@Override
	@SuppressWarnings("unchecked")
	public Integer getLastProductId() {
		String hql = "FROM ProductInfoBean";
		List<ProductInfoBean> list = factory.getCurrentSession().createQuery(hql)
				.getResultList();
//		listlist.size()
		ProductInfoBean pb = list.get(list.size()-1);
		return pb.getProduct_id();
	}

	@Override
	public void productOffShelf(Integer productid) {
		Session session = factory.getCurrentSession();
		String hql = "Update ProductInfoBean pib set product_status = :status WHERE product_id = :id";
		session.createQuery(hql)
			.setParameter("status", "0")
			.setParameter("id", productid)
			.executeUpdate();
	}

	@Override
	public void productReOnShelf(Integer productid) {
		Session session = factory.getCurrentSession();
		String hql = "Update ProductInfoBean pib set product_status = :status WHERE product_id = :id";
		session.createQuery(hql)
			.setParameter("status", "1")
			.setParameter("id", productid)
			.executeUpdate();
	}

	@Override
	public void productRemoveByStore(Integer productid) {
		Session session = factory.getCurrentSession();
		String hql = "Update ProductInfoBean pib set product_status = :status WHERE product_id = :id";
		session.createQuery(hql)
			.setParameter("status", "3")
			.setParameter("id", productid)
			.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfoBean> getAllProduct() {
		String hql = "FROM ProductInfoBean";
		return factory.getCurrentSession().createQuery(hql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfoBean> getAllProduct(String selectedParameters, Integer avPage, Integer startPage) {
		Session session = factory.getCurrentSession();
		/* 取得開始的筆數 */
		Integer startIndex = (startPage - 1) * avPage;
		/* 開始組字串 */
		StringBuilder sb = new StringBuilder();
		sb.append("FROM ProductInfoBean AS pi WHERE ");
		
		String name = (selectedParameters.split(":")[0].equals("?")) ? "" : selectedParameters.split(":")[0];
		String shop = (selectedParameters.split(":")[1].equals("?")) ? "" : selectedParameters.split(":")[1];
		Integer price = (selectedParameters.split(":")[2].equals("-1")) ? -1 : (Integer.parseInt(selectedParameters.split(":")[2]) / 100) * 100;
		Integer quantity = (selectedParameters.split(":")[3].equals("-1")) ? -1 : (Integer.parseInt(selectedParameters.split(":")[3]) / 10) * 10;
		String account = (selectedParameters.split(":")[4].equals("?")) ? "" : selectedParameters.split(":")[4];
		String status = (selectedParameters.split(":")[5].equals("?")) ? "" : selectedParameters.split(":")[5];
		String userId = (selectedParameters.split(":")[6].equals("?")) ? "" : selectedParameters.split(":")[6];
		Integer lv = (selectedParameters.split(":")[7].equals("-2")) ? -2 : Integer.parseInt(selectedParameters.split(":")[7]) ;
		
		if (!name.equals("")) {
			name = "'%" + selectedParameters.split(":")[0] + "%'";
			sb.append("pi.product_name LIKE " + name);
		}
		
		if (sb.toString().equals("FROM ProductInfoBean AS pi WHERE ") && !shop.equals("")) {
			shop = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append("pi.product_shop LIKE " + shop);
		} else if (!sb.toString().equals("FROM ProductInfoBean AS pi WHERE ") && !shop.equals("")) {
			shop = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append(" AND pi.product_shop LIKE " + shop);
		}
		
		if (sb.toString().equals("FROM ProductInfoBean AS pi WHERE ") && price > -1) {
			sb.append("pi.product_price >= " + price + " AND pi.product_price < " + (price + 100));
		} else if (!sb.toString().equals("FROM ProductInfoBean AS pi WHERE ") && price > -1) {
			sb.append(" AND pi.product_price >= " + price + " AND pi.product_price < " + (price + 100));
		} 
		
		if (sb.toString().equals("FROM ProductInfoBean AS pi WHERE ") && quantity > -1) {
			sb.append("pi.product_quantity >= " + quantity + " AND pi.product_quantity < " + (quantity + 10));
		} else if (!sb.toString().equals("FROM ProductInfoBean AS pi WHERE ") && quantity > -1) {
			sb.append(" AND pi.product_quantity >= " + quantity + " AND pi.product_quantity < " + (quantity + 10));
		}
		
		if (lv == -1) {
			if (sb.toString().equals("FROM ProductInfoBean AS pi WHERE ") && !account.equals("")) {
				account = "'%" + selectedParameters.split(":")[4] + "%'";
				sb.append("pi.storeBean.webUserData.account LIKE " + account);
			} else if (!sb.toString().equals("FROM ProductInfoBean AS pi WHERE ") && !account.equals("")) {
				account = "'%" + selectedParameters.split(":")[4] + "%'";
				sb.append(" AND pi.storeBean.webUserData.account LIKE " + account);
			}
			
			if (sb.toString().equals("FROM ProductInfoBean AS pi WHERE ") && !status.equals("")) {
				sb.append("pi.product_status = " + status);
			} else if (!sb.toString().equals("FROM ProductInfoBean AS pi WHERE ") && !status.equals("")) {
				sb.append(" AND pi.product_status = " + status);
			}
		} else if (lv == 1) {
			if (sb.toString().equals("FROM ProductInfoBean AS pi WHERE ")) {
				sb.append("pi.storeBean.webUserData.userId = " + userId);
			} else if (!sb.toString().equals("FROM ProductInfoBean AS pi WHERE ")) {
				sb.append(" AND pi.storeBean.webUserData.userId = " + userId);
			}
		}
		
		String hql = sb.toString();
		return session.createQuery(hql)
				.setFirstResult(startIndex)
                .setMaxResults(avPage)
                .getResultList();
	}
	
	@Override
	public Integer productChange(Integer productId, String status) {
		Session session = factory.getCurrentSession();
		String hql = "Update ProductInfoBean pib set product_status = :status WHERE product_id = :id";
		Integer result = session.createQuery(hql)
		.setParameter("status", status)
		.setParameter("id", productId)
		.executeUpdate();
		return result;
	}
}
