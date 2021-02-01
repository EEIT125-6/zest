package xun.dao.impl;

import java.util.List;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import webUser.model.WebUserData;
import xun.dao.StoreDao;
import xun.model.BoardBean;
import xun.model.StoreBean;
import xun.model.ProductInfoBean;


@Repository
public class StoreHibernateDaoImpl implements StoreDao {

	
//	SessionFactory factory = HibernateUtils.getSessionFactory();
	@Autowired
	SessionFactory factory;
	
	@Override
	public int save(StoreBean sb) {
		int count = 0;
		Session session = factory.getCurrentSession();
		session.save(sb);
		count++;
		return count;
	}

	@Override
	public int updateStore(StoreBean sb) {
//		int count = 0;
		int result = 0;
			String hql = "Update StoreBean set stname = :stname , sclass = :sclass "+
						" , saddress = :saddress , stitd = :stitd , stitddt = :stitddt, tel = :tel " +
						" WHERE id = :id";
			Session session = factory.getCurrentSession();
			
			result = session.createQuery(hql)
					.setParameter("stname", sb.getStname())
					.setParameter("sclass", sb.getSclass())
					.setParameter("saddress", sb.getSaddress())
					.setParameter("stitd", sb.getStitd())
					.setParameter("stitddt", sb.getStitddt())
					.setParameter("tel", sb.getTel())
					.setParameter("id", sb.getId())
					.executeUpdate();
//			session.saveOrUpdate(sb);
//			count++;
		return result;
	}



	@SuppressWarnings("unchecked")
	@Override
	public boolean isDup(String stname) {
		boolean result = false;
		String hql = "FROM StoreBean s WHERE s.stname = :stname1 And status = '1'";
		Session session = factory.getCurrentSession();
		Query<StoreBean> query = session.createQuery(hql);
		List<StoreBean> list=(List<StoreBean>) query.setParameter("stname1", stname).getResultList();
		if(list.size()>0) {
			return true;
		}
		return result;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getSinglest(String stname) {
		String hql  = "SELECT id FROM StoreBean sb WHERE sb.stname = :stname";
		Session session = factory.getCurrentSession();
		Query<StoreBean> query = session.createQuery(hql);
		List<StoreBean> list=(List<StoreBean>) query.setParameter("stname1", stname).getResultList();
		return list;
	}
	
	@Override
	public int deleteStore(StoreBean sb) {
		int result = 0;
		String hql = "DELETE StoreBean sb WHERE sb.id = :id0";
		Session session = factory.getCurrentSession();
		result = session.createQuery(hql)
				.setParameter("id0", sb.getId())
				.executeUpdate();
		return result;
	}

	@Override
	public void photoStore(StoreBean sb) {
		Session session = factory.getCurrentSession();
		String hql = "Update StoreBean sb set photourl = :photourl WHERE id = :id";
		
		session.createQuery(hql).setParameter("photourl", sb.getPhotourl())
								.setParameter("id", sb.getId())
								.executeUpdate();
	}

	@Override
	public void bannerStore(StoreBean sb) {
		Session session = factory.getCurrentSession();
		String hql = "Update StoreBean sb set bannerurl = :bannerurl WHERE id = :id";
		
		session.createQuery(hql).setParameter("bannerurl", sb.getBannerurl())
								.setParameter("id", sb.getId())
								.executeUpdate();
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getClassstore(String sclass) {
		Session session = factory.getCurrentSession();
		String hql = "FROM StoreBean sb WHERE sclass = :sclass And status = '1'" ;
		
		List<StoreBean> list = session.createQuery(hql).setParameter("sclass", sclass).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getNamestore(String stname) {
		Session session = factory.getCurrentSession();
		String hql = "FROM StoreBean WHERE stname like :stname And status = '1'";
		List<StoreBean> list = session.createQuery(hql).setParameter("stname", "%"+stname+"%").getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getFullstore(Integer id) {
		Session session = factory.getCurrentSession();
		String hql  = "FROM StoreBean WHERE id = :id1 And status = '1'";
		List<StoreBean> list = session.createQuery(hql).setParameter("id1", id).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getAdvertisementstore() {
		Session session = factory.getCurrentSession();
//		String hql = "FROM StoreBean";
//		String hql = "SELECT id,stname,bannerurl FROM StoreBean ORDER BY RAND()";
//		String hql = "FROM StoreBean ORDER BY RAND() ";
		String hql = "FROM StoreBean WHERE status = '1' ";
//		SELECT id FROM user ORDER BY RAND() LIMIT 10
//		int number = 4;
		List<StoreBean> list = session.createQuery(hql).getResultList();
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getAdvertisementphotostore() {
		Session session = factory.getCurrentSession();
//		String hql = "SELECT id,stname,photourl FROM StoreBean ORDER BY RAND()";
//		String hql = "FROM StoreBean ORDER BY RAND()";
		String hql = "FROM StoreBean WHERE status = '1'";
//		int number = 6;
		List<StoreBean> list = session.createQuery(hql).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BoardBean> getComment(Integer stid) {
		Session session = factory.getCurrentSession();
		String hql = "FROM BoardBean Where Store_Id=:stid";
		List<BoardBean> list = session.createQuery(hql).setParameter("stid", stid).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductInfoBean> getProductInfoBeans(Integer stid) {
		Session session = factory.getCurrentSession();
		String hql = "FROM ProductInfoBean Where Store_Id=:stid and product_status != 3";
		List<ProductInfoBean> list = session.createQuery(hql).setParameter("stid", stid).getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getStorebyClassandPrice(String sclass, Integer price) {
		Session session = factory.getCurrentSession();
		String hql = "FROM StoreBean Where sclass = :sclass AND price = :price AND status = '1'";
		List<StoreBean> list = session.createQuery(hql)
				.setParameter("sclass", sclass)
				.setParameter("price", price)
				.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getStorebyClassandStar(String sclass, Float star) {
		Session session = factory.getCurrentSession();
		String hql = "FROM StoreBean Where sclass = :sclass AND avgStar > :star  AND status = '1'";
		List<StoreBean> list = session.createQuery(hql)
				.setParameter("sclass", sclass)
				.setParameter("star", star)
				.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getStorebyClassandStarandPrice(String sclass, Integer price, Float star) {
		Session session = factory.getCurrentSession();
		String hql = "FROM StoreBean Where sclass = :sclass AND price = :price AND avgStar > :star  AND status = '1'";
		List<StoreBean> list = session.createQuery(hql)
				.setParameter("sclass", sclass)
				.setParameter("price", price)
				.setParameter("star", star)
				.getResultList();
		return list;
	}
	
	@Override
	public StoreBean get(Integer id) {
		return factory.getCurrentSession().get(StoreBean.class, id);
	}

	@Override
	public Integer setStorePrice(Integer price, Integer id) {
		Integer count=null;
		Session session = factory.getCurrentSession();
		String hql = "Update StoreBean sb set price = :price WHERE id = :id";
		count=session.createQuery(hql).setParameter("price", price)
		.setParameter("id", id)
		.executeUpdate();
		return count;
	}

	@Override
	public Integer setStoreStar(Float avgStar, Integer id) {
		Integer count=null;
		Session session = factory.getCurrentSession();
		String hql = "Update StoreBean sb set avgStar = :avgStar WHERE id = :id";
		count=session.createQuery(hql).setParameter("avgStar", avgStar)
		.setParameter("id", id)
		.executeUpdate();
		return count;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getNamestoreandPrice(String stname, Integer price) {
		Session session = factory.getCurrentSession();
		String hql = "FROM StoreBean WHERE stname like :stname AND price = :price  AND status = '1'" ;
		List<StoreBean> list = session.createQuery(hql)
				.setParameter("stname", "%"+stname+"%")
				.setParameter("price", price)
				.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getNamestoreandStar(String stname, Float star) {
		Session session = factory.getCurrentSession();
		String hql = "FROM StoreBean WHERE stname like :stname AND avgStar > :star  AND status = '1'" ;
		List<StoreBean> list = session.createQuery(hql)
				.setParameter("stname", "%"+stname+"%")
				.setParameter("star", star)
				.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getNamestoreandPriceandStar(String stname, Integer price, Float star) {
		Session session = factory.getCurrentSession();
		String hql = "FROM StoreBean WHERE stname like :stname AND price = :price AND avgStar > :star  AND status = '1'";
		List<StoreBean> list = session.createQuery(hql)
				.setParameter("stname", "%"+stname+"%")
				.setParameter("price", price)
				.setParameter("star", star)
				.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSclassCategory() {
		Session session = factory.getCurrentSession();
		String hql = "SELECT DISTINCT sclass FROM StoreBean WHERE  status = '1'";
		List<String> list = session.createQuery(hql)
				.getResultList();
		return list;
	}

	@Override
	public String getSclassCategoryTag() {
		String ans = "";
		List<String> list = getSclassCategory();
		ans += "<select name='category'>";
		for(String sclass: list) {
			ans+="<option value = '"+ sclass + "'>" + sclass +"</option>";
		}
		ans += "</selcet>";
		return ans;
	}

	@Override
	public Integer setClickCount(Integer stid) {
		Session session = factory.getCurrentSession();
		String hql = "Update StoreBean sb set click = :click WHERE id = :id";
		StoreBean sb = factory.getCurrentSession().get(StoreBean.class, stid);
		Integer click = sb.getClick();
//		StoreBean sb = (StoreBean) session.createQuery(qhql).setParameter("id", stid).getSingleResult();
		click++;
		session.createQuery(hql).setParameter("click", click).setParameter("id", stid).executeUpdate();
		return click;
	}

	//可能不會用到
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getRenameStore(Integer stid) {
		Session session = factory.getCurrentSession();
		String hql = "select stname from StoreBean Where id = :id AND status = :status1";
		List<String> list = session.createQuery(hql)
				.setParameter("id", stid)
				.setParameter("status1", "1")
				.getResultList();
		return list;
	}

	@Override
	public void storeOffShelf(Integer stid) {
		Session session = factory.getCurrentSession();
		String hql = "Update StoreBean sb set status = :status WHERE id = :id";
		session.createQuery(hql)
			.setParameter("status", "0")
			.setParameter("id", stid)
			.executeUpdate();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> guessYouLike(String sclass) {
		Session session = factory.getCurrentSession();
		String hql = "FROM StoreBean WHERE sclass=:sclass AND status = '1'" ;
		List<StoreBean> list = session.createQuery(hql)
				.setParameter("sclass", sclass)
				.getResultList();
		return list;
	}

	@Override
	public Integer setStoreRealPrice(Integer realprice, Integer id) {
		Session session = factory.getCurrentSession();
		String hql = "Update StoreBean sb set realprice = :realprice WHERE id = :id";
		session.createQuery(hql).setParameter("realprice", realprice)
			.setParameter("id", id)
			.executeUpdate();
		return realprice;
	}
	
	/* 取得全部商店列表 By George017 2021/01/19 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getAllStore() {
		Session session = factory.getCurrentSession();
		String hql = "FROM StoreBean";
		List<StoreBean> list = session.createQuery(hql).getResultList();
		return list;
	}
	
	/* 取得全部商店列表+分頁 By George017 2021/02/01 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getAllStore(String selectedParameters, Integer avPage, Integer startPage) {
		/* 取得開始的筆數 */
		Integer startIndex = (startPage - 1) * avPage;
		/* 開始組字串 */
		StringBuilder sb = new StringBuilder();
		sb.append("FROM StoreBean sb");
		
		Integer userLv = Integer.parseInt(selectedParameters.split(":")[4]);
		String selectedShop = (selectedParameters.split(":")[0].equals("?")) ? "" : selectedParameters.split(":")[0];
		String selectedOwner = (userLv == -1 && !selectedParameters.split(":")[1].equals("?")) ? selectedParameters.split(":")[1] : "";
		String selectedStatus = (userLv == -1 && !selectedParameters.split(":")[2].equals("?")) ? selectedParameters.split(":")[2] : "";
		String selectedType = (selectedParameters.split(":")[3].equals("?")) ? "" : selectedParameters.split(":")[3];
		String selectedUserId = (userLv == 1) ? selectedParameters.split(":")[5] : ""; 
		
		if (!selectedShop.equals("")) {
			selectedShop = "'%" + selectedParameters.split(":")[0] + "%'";
			sb.append(" WHERE sb.stname LIKE " + selectedShop);
		}
		
		if (userLv == -1 && !selectedOwner.equals("") && sb.toString().equals("FROM StoreBean sb")) {
			selectedOwner = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append(" WHERE sb.webUserData.account LIKE " + selectedOwner);
		} else if (userLv == -1 && !selectedOwner.equals("") && !sb.toString().equals("FROM StoreBean sb")) {
			selectedOwner = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append(" AND sb.webUserData.account LIKE " + selectedOwner);
		}
		
		if (userLv == -1 && !selectedStatus.equals("") && sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" WHERE sb.status = " + selectedStatus);
		} else if (userLv == -1 && !selectedStatus.equals("") && !sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" AND sb.status = " + selectedStatus);
		}
		
		if (!selectedType.equals("") && sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" WHERE sb.Type = " + selectedType);
		} else if (!selectedType.equals("") && !sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" AND sb.Type = " + selectedType);
		}
		
		if (userLv == 1 && !selectedUserId.equals("") && sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" WHERE sb.webUserData.userId = " + selectedUserId);
		} else if (userLv == 1 && !selectedUserId.equals("") && !sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" AND sb.webUserData.userId = " + selectedUserId);
		}
		
		String hql = sb.toString();
		Session session = factory.getCurrentSession();
		List<StoreBean> list = session.createQuery(hql)
									.setFirstResult(startIndex)
									.setMaxResults(avPage)
									.getResultList();
		return list;
	}
	
	/* 取得查詢商店總筆數 By George017 2021/02/01 */
	@SuppressWarnings("unchecked")
	@Override
	public Long getStoreRecordCounts(String selectedParameters) {
		/* 開始組字串 */
		StringBuilder sb = new StringBuilder();
		sb.append("FROM StoreBean sb");
		
		Integer userLv = Integer.parseInt(selectedParameters.split(":")[4]);
		String selectedShop = (selectedParameters.split(":")[0].equals("?")) ? "" : selectedParameters.split(":")[0];
		String selectedOwner = (userLv == -1 && !selectedParameters.split(":")[1].equals("?")) ? selectedParameters.split(":")[1] : "";
		String selectedStatus = (userLv == -1 && !selectedParameters.split(":")[2].equals("?")) ? selectedParameters.split(":")[2] : "";
		String selectedType = (selectedParameters.split(":")[3].equals("?")) ? "" : selectedParameters.split(":")[3];
		String selectedUserId = (userLv == 1) ? selectedParameters.split(":")[5] : ""; 
		
		if (!selectedShop.equals("")) {
			selectedShop = "'%" + selectedParameters.split(":")[0] + "%'";
			sb.append(" WHERE sb.stname LIKE " + selectedShop);
		}
		
		if (userLv == -1 && !selectedOwner.equals("") && sb.toString().equals("FROM StoreBean sb")) {
			selectedOwner = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append(" WHERE sb.webUserData.account LIKE " + selectedOwner);
		} else if (userLv == -1 && !selectedOwner.equals("") && !sb.toString().equals("FROM StoreBean sb")) {
			selectedOwner = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append(" AND sb.webUserData.account LIKE " + selectedOwner);
		}
		
		if (userLv == -1 && !selectedStatus.equals("") && sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" WHERE sb.status = " + selectedStatus);
		} else if (userLv == -1 && !selectedStatus.equals("") && !sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" AND sb.status = " + selectedStatus);
		}
		
		if (!selectedType.equals("") && sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" WHERE sb.Type = " + selectedType);
		} else if (!selectedType.equals("") && !sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" AND sb.Type = " + selectedType);
		}
		
		if (userLv == 1 && !selectedUserId.equals("") && sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" WHERE sb.webUserData.userId = " + selectedUserId);
		} else if (userLv == 1 && !selectedUserId.equals("") && !sb.toString().equals("FROM StoreBean sb")) {
			sb.append(" AND sb.webUserData.userId = " + selectedUserId);
		}
		
		String hql = sb.toString();
		Session session = factory.getCurrentSession();
		List<StoreBean> list = session.createQuery(hql).getResultList();
		
		return Long.parseLong(String.valueOf(list.size()));
	}
	
	/* 取得查詢的最大頁數 By George017 2021/02/01 */
	@Override
	public Integer getTotalStoreRecordCounts(String selectedParameters, Integer avPage) {
		Integer totalPages = (int) (Math.ceil(getStoreRecordCounts(selectedParameters) / (double) avPage));
		return totalPages;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StoreBean> getMemberAllStore(WebUserData webUserData) {
		Session session = factory.getCurrentSession();
		String hql = "From StoreBean Where webUserData = :webUserData AND status = '1'";
		List<StoreBean> list = session.createQuery(hql)
				.setParameter("webUserData", webUserData)
				.getResultList();
		return list;
	}
	
	@Override
	public void removeComment(StoreBean sb) {
		String hql = "DELETE BoardBean  WHERE storebean = :storebean";
		Session session = factory.getCurrentSession();
		session.createQuery(hql)
				.setParameter("storebean", sb)
				.executeUpdate();
	}
	
	/* 下架/上架商店(利用回傳值確認是否成功) By George017 2021/01/23 */
	@Override
	public Integer storeChange(Integer stid, String newStatus) {
		Session session = factory.getCurrentSession();
		StoreBean updatedStoreData = (StoreBean) session.get(StoreBean.class, stid);
		updatedStoreData.setStatus(newStatus);
		String hql = "Update StoreBean sb set status = :status WHERE id = :id";
		return session.createQuery(hql)
			.setParameter("status", newStatus)
			.setParameter("id", stid)
			.executeUpdate();
	}
}
