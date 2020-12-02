package webUser.service;

import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import webUser.dao.WebUserDAO;
import webUser.dao.WebUserHibernateDAO;
import webUser.model.WebUserData;
import webUser.utils.HibernateUtils;

public class WebUserServiceHibernate implements WebUserService {
	SessionFactory factory = HibernateUtils.getSessionFactory();
	WebUserDAO webUserDao = new WebUserHibernateDAO();
	
	/*
	 *  檢查帳號是否存在 -1->異常、0->不存在、1->存在
	 */
	@Override
	public int checkAccountExist(String inputAccount) throws SQLException {
		/* 變數宣告 */
		int checkResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			checkResult = webUserDao.checkAccountExist(inputAccount);
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
			}
			throw new SQLException(sqlE);
		}
		return checkResult;
	}

	/*
	 * 檢查信箱是否已使用 -1->異常、0->不存在、1->存在
	 */
	@Override
	public int checkEmailExist(String inputEmail) throws SQLException {
		/* 變數宣告 */
		int checkResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			checkResult = webUserDao.checkEmailExist(inputEmail);
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
			}
			throw new SQLException(sqlE);
		}
		return checkResult;
	}

	/* 執行資料新增 1->成功、0->失敗 */
	@Override
	public int insertWebUserData(WebUserData registerData) throws SQLException {
		int insertResult = 0;
		/* 取出參數 */
		int lv = registerData.getLv();
		/* 第一碼 = lv + 1 */
		String selectedId = String.valueOf(lv + 1) + "%";
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 取得userId */
			String insertId = webUserDao.createNewUserId(selectedId, lv + 1);
			webUserDao.insertWebUserData(insertId, registerData);
			insertResult++;
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
				insertResult = -1;
			}
			throw new SQLException(sqlE);
		}
		return insertResult;
	}

	@Override
	public int checkWebUserLogin(String inputAccount, String inputPassword) throws SQLException {
		/* 變數宣告 */
		int checkLoginResult = -2;
		int checkAccountResult = -1;
		int checkAccountQuit = -1;
		int checkPasswordResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 檢查帳號 */
			checkAccountResult = webUserDao.checkAccountExist(inputAccount);
			/* 帳號不存在就不繼續往下執行 */
			if (checkAccountResult != 1) {
				throw new SQLException("帳號錯誤，請檢查之後再次輸入");
			} else {
				checkLoginResult++;
			}
			/* 檢查帳號是否有效 */
			checkAccountQuit = webUserDao.checkAccountQuit(inputAccount);
			/* 帳號棄用就不繼續往下執行 */
			if (checkAccountQuit != 1) {
				throw new SQLException("該帳號已棄用！請選擇其他帳號登入或註冊一個新帳號");
			} else {
				checkLoginResult++;
			}
			/* 檢查密碼 */
			checkPasswordResult = webUserDao.checkPassword(inputAccount, inputPassword);
			/* 密碼錯誤 */
			if (checkPasswordResult != 1) {
				throw new SQLException("密碼錯誤，請檢查之後再次輸入");
			} else {
				checkLoginResult++;
			}
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
				checkLoginResult = -1;
			}
			throw new SQLException(sqlE);
		}
		return checkLoginResult;
	}

	@Override
	public WebUserData getWebUserData(String inputAccount) throws SQLException {
		/* 變數宣告 */
		WebUserData UserFullData = new WebUserData();
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 帶入使用者資料 */
			UserFullData = webUserDao.getWebUserData(inputAccount);
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
			}
			throw new SQLException(sqlE);
		}
		return UserFullData;
	}

	@Override
	public int quitWebUserData(WebUserData quitUserData) throws SQLException {
		/* 變數宣告 */
		int quitResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 變更帳號狀態 */
			quitResult = webUserDao.quitWebUserData(quitUserData);
			/* 變更失敗 */
			if (quitResult != 1) {
				throw new SQLException("變更失敗");
			} 
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
			}
			throw new SQLException(sqlE);
		}
		return quitResult;
	}
}
