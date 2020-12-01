package webUser.dao;

import java.sql.SQLException;
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
		System.out.println("lv is" + lv);
		System.out.println("selectId is" + selectedId);
		/* 變數宣告 */
		String createIdResult = "";
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.id LIKE :selectedId";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query.setParameter("selectedId", selectedId).getResultList();
		System.out.println("query results are "+list);
		/* 取得最後一筆 */
		createIdResult = (list.size() > 0) 
				? String.valueOf(Integer.parseInt(list.get(list.size() - 1).getUserId()) + 1) 
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
}
