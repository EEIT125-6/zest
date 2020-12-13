package webUser.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	 *  檢查Id是否存在 -1->異常、0->不存在、1->存在
	 */
	@Override
	public Integer checkUserIdExist(String inputUserId) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			checkResult = webUserDao.checkUserIdExist(inputUserId);
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
	 *  檢查Id是否棄用 -1->異常、0->否、1->是
	 */
	@Override
	public Integer checkUserIdQuit(String inputUserId) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			checkResult = webUserDao.checkUserIdQuit(inputUserId);
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
	 *  檢查帳號是否存在 -1->異常、0->不存在、1->存在
	 */
	@Override
	public Integer checkAccountExist(String inputAccount) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
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
	 * 檢查稱呼是否已使用 -1->異常、0->未使用、1->使用
	 */
	@Override
	public Integer checkNicknameExist(String inputNickname) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			checkResult = webUserDao.checkNicknameExist(inputNickname);
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
	public Integer checkEmailExist(String inputEmail) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
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
	
	/*
	 * 檢查電話是否已使用 -1->異常、0->不存在、1->存在
	 */
	@Override
	public Integer checkPhoneExist(String inputPhone) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			checkResult = webUserDao.checkPhoneExist(inputPhone);
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
	public Integer insertWebUserData(WebUserData registerData) throws SQLException {
		Integer insertResult = 0;
		/* 取出參數 */
		Integer lv = registerData.getLv();
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

	/* 執行登入檢查 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer checkWebUserLogin(String inputAccount, String inputPassword) throws SQLException {
		/* 變數宣告 */
		Integer checkLoginResult = -2;
		Integer checkAccountResult = -1;
		Integer checkAccountQuit = -1;
		Integer checkPasswordResult = -1;
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
				checkLoginResult = -3;
			}
			throw new SQLException(sqlE);
		}
		return checkLoginResult;
	}

	/* 取得使用者個人資料 */
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

	/* 棄用使用者帳戶 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer quitWebUserData(WebUserData quitUserData) throws SQLException {
		/* 變數宣告 */
		Integer quitResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 設定使用者狀態 */
			quitUserData.setStatus("quit");
			/* 變更帳號狀態 */
			quitResult = webUserDao.quitWebUserData(quitUserData);
			/* 變更失敗 */
			if (quitResult != 1) {
				/* 還原使用者狀態 */
				quitUserData.setStatus("active");
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
	
	/* 變更使用者帳戶狀態 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer adminChangeWebUserData(String userId, String status) throws SQLException {
		/* 變數宣告 */
		Integer quitResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 變更帳號狀態 */
			quitResult = webUserDao.adminChangeWebUserData(userId, status);
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

	/* 更新使用者資料 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer updateWebUserData(WebUserData updatedUserData) throws SQLException {
		/* 變數宣告 */
		Integer updateResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 執行變更 */
			updateResult = webUserDao.updateWebUserData(updatedUserData);
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
			}
			throw new SQLException(sqlE);
		}
		return updateResult;
	}

	/* 更新使用者密碼 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer updateWebUserPassword(WebUserData updatedUserData) throws SQLException {
		/* 變數宣告 */
		Integer updateResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 執行變更 */
			updateResult = webUserDao.updateWebUserPassword(updatedUserData);
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
			}
			throw new SQLException(sqlE);
		}
		return updateResult;
	}

	/* 取得查詢的使用者資料 */
	@Override
	public List<WebUserData> getOtherWebUserData(String selectedParameters) throws SQLException {
		List<WebUserData> list = new ArrayList<>();
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 執行查詢 */
			list = webUserDao.getOtherWebUserData(selectedParameters);
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
			}
			throw new SQLException(sqlE);
		}
		return list;
	}

	/* 取得所有使用者資料 */
	@Override
	public List<WebUserData> getAllWebUserData(Integer lv, String status) throws SQLException {
		List<WebUserData> list = new ArrayList<>();
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 執行查詢 */
			list = webUserDao.getAllWebUserData(lv, status);
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
			}
			throw new SQLException(sqlE);
		}
		return list;
	}

	/* 刪除使用者帳戶 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer deleteWebUserData(String deletedUserId) throws SQLException {
		/* 變數宣告 */
		Integer deleteResult = -1;
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 執行刪除 */
			deleteResult = webUserDao.deleteWebUserData(deletedUserId);
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
			}
			throw new SQLException(sqlE);
		}
		return deleteResult;
	}

	/* 驗證使用者資料 */
	@Override
	public WebUserData checkRecoveryInfo(String account, String password, String email, String phone, Date birth)
			throws SQLException {
		/* 變數宣告 */
		WebUserData checkResult = new WebUserData();
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			/* 檢查輸入條件 */
			if (!account.equals("") && !password.equals("")) {
				checkResult = webUserDao.checkRecoveryInfo(account, password, email, phone, birth);
			} else if (!account.equals("") && password.equals("")) {
				checkResult = webUserDao.checkRecoveryInfo(account, email, phone, birth);
			} else if (account.equals("") && !password.equals("")) {
				checkResult = webUserDao.checkRecoveryInfoAnother(password, email, phone, birth);
			} else if (account.equals("") && password.equals("")) {
				checkResult = webUserDao.checkRecoveryInfo(email, phone, birth);
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
		return checkResult;
	}

	/* 重設密碼 */
	@Override
	public Integer resetWebUserPassword(String userId, String password) 
			throws SQLException {
		/* 變數宣告 */
		Integer resetResult = -3;
		Integer checkUserIdResult = -1;
		Integer checkUserIdQuit = -1;
		Integer checkPasswordResult = -1;
		Integer resetPasswordResult = -1;
		
		/* 取得Session */
		Session session = factory.getCurrentSession();
		/* 設定交易 */
		Transaction tx = null;
		try {
			/* 交易開始 */
			tx = session.beginTransaction();
			
			/* 檢查id */
			checkUserIdResult = webUserDao.checkUserIdExist(userId);
			/* id不存在就不繼續往下執行 */
			if (checkUserIdResult != 1) {
				throw new SQLException("使用者身份無效，請檢查之後再次輸入");
			} else {
				resetResult++;
			}
			
			/* 檢查id是否有效 */
			checkUserIdQuit = webUserDao.checkUserIdQuit(userId);
			/* id棄用就不繼續往下執行 */
			if (checkUserIdQuit != 1) {
				throw new SQLException("該帳號已棄用！請選擇其他帳號登入或註冊一個新帳號");
			} else {
				resetResult++;
			}
			
			/* 檢查密碼 */
			checkPasswordResult = webUserDao.checkResetPassword(userId, password);
			/* 密碼錯誤 */
			if (checkPasswordResult != 0) {
				throw new SQLException("密碼未修改，請檢查之後再次輸入");
			} else {
				resetResult++;
			}
			
			/* 執行密碼變更 */
			resetPasswordResult = webUserDao.updateWebUserPassword(userId, password);
			/* 變更失敗 */
			if (resetPasswordResult != 1) {
				throw new SQLException("密碼修改失敗！");
			} else {
				resetResult++;
			}
			
			/* 交易確認 */
			tx.commit();
		} catch(SQLException sqlE) {
			if (tx != null) {
				/* 撤回交易 */
				tx.rollback();
				resetResult = -4;
			}
			throw new SQLException(sqlE);
		}
		return resetResult;
	}
}
