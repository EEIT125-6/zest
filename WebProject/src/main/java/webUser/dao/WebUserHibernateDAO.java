package webUser.dao;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import webUser.model.WebUserData;
import webUser.utils.HibernateUtils;

public class WebUserHibernateDAO implements WebUserDAO{

	SessionFactory factory = HibernateUtils.getSessionFactory();
	
	@SuppressWarnings("unchecked")
	@Override
	public int checkAccountExist(String inputAccount) throws SQLException {
		/* 變數宣告 */
		int checkResult = -1;
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :inputAccount";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query.setParameter("inputAccount", inputAccount).getResultList();
		/* 由size()判結果 */
		checkResult = (list.size() > 0) ? 1 : 0;
		return checkResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkEmailExist(String inputEmail) throws SQLException {
		/* 變數宣告 */
		int checkResult = -1;
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.email = :inputEmail";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query.setParameter("inputEmail", inputEmail).getResultList();
		/* 由size()判結果 */
		checkResult = (list.size() > 0) ? 1 : 0;
		return checkResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String createNewUserId(String selectedId, int lv) throws SQLException {
		/* 變數宣告 */
		String createIdResult = "";
		String lastAccount = "";
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.id LIKE :selectedId";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query.setParameter("selectedId", selectedId).getResultList();
		/* 取得最後一筆 */
		lastAccount = (list.size() > 0) ? list.get(list.size() - 1).getUserId() : "";
		createIdResult = (!lastAccount.equals("")) 
				? String.valueOf(Integer.parseInt(lastAccount) + 1) 
				: String.valueOf(lv) + "000001";
		return createIdResult;
	}

	@Override
	public int insertWebUserData(String insertId, WebUserData registerData) throws SQLException {
		/* 變數宣告 */
		int instertResult = 0;
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 設定userId */
		registerData.setUserId(insertId);
		session.save(registerData);
		instertResult++;
		return instertResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int checkPassword(String inputAccount, String inputPassword) throws SQLException {
		/* 變數宣告 */
		int checkResult = 0;
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :inputAccount AND wu.password = :inputPassword";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query
				.setParameter("inputAccount", inputAccount)
				.setParameter("inputPassword", inputPassword)
				.getResultList();
		/* 由size()判結果 */
		checkResult = (list.size() > 0) ? 1 : 0;
		return checkResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public WebUserData getWebUserData(String inputAccount) throws SQLException {
		/* 變數宣告 */
		WebUserData userFullData = new WebUserData();
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :inputAccount";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query.setParameter("inputAccount", inputAccount).getResultList();
		/* 取出資料，理論上陣列中只會有一筆資料 */
		if( list.size() == 1) {
			userFullData = list.get(0);
		}
		return userFullData;
	}
}
