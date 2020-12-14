package webUser.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import webUser.model.WebUserData;
import webUser.utils.HibernateUtils;

public class WebUserHibernateDAO implements WebUserDAO{

	SessionFactory factory = HibernateUtils.getSessionFactory();
	
	/* 檢查Id是否存在 -1->異常、0->不存在、1->存在 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkUserIdExist(String inputUserId) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :inputUserId";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query.setParameter("inputUserId", inputUserId).getResultList();
		/* 由size()判結果 */
		checkResult = (list.size() > 0) ? 1 : 0;
		return checkResult;
	}
	
	/* 檢查帳號是否存在 -1->異常、0->不存在、1->存在 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkAccountExist(String inputAccount) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
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
	
	/* 檢查稱呼是否已使用 -1->異常、0->未使用、1->使用 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkNicknameExist(String inputNickname) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.nickname = :inputNickname";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query.setParameter("inputNickname", inputNickname).getResultList();
		/* 由size()判結果 */
		checkResult = (list.size() > 0) ? 1 : 0;
		return checkResult;
	}

	/* 檢查信箱是否已使用 -1->異常、0->不存在、1->存在 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkEmailExist(String inputEmail) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
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
	
	/* 檢查電話是否已使用 -1->異常、0->不存在、1->存在 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkPhoneExist(String inputPhone) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.phone = :inputPhone";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query.setParameter("inputPhone", inputPhone).getResultList();
		/* 由size()判結果 */
		checkResult = (list.size() > 0) ? 1 : 0;
		return checkResult;
	}

	/* 產生新增使用者所需的ID */
	@SuppressWarnings("unchecked")
	@Override
	public String createNewUserId(String selectedId, Integer lv) throws SQLException {
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

	/* 新增使用者資料 1->成功、0->失敗*/
	@Override
	public Integer insertWebUserData(String insertId, WebUserData registerData) throws SQLException {
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

	/* 檢查密碼 -1->異常、0->錯誤、1->正確 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkPassword(String inputAccount, String inputPassword) throws SQLException {
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

	/* 取得使用者個人資料 */
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

	/* 棄用使用者帳戶 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer quitWebUserData(WebUserData quitUserData) throws SQLException {
		/* 變數宣告 */
		Integer quitResult = 0;
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行變更 */
		session.saveOrUpdate(quitUserData);
		quitResult++;
		return quitResult;
	}
	
	/* 變更使用者帳戶狀態 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer adminChangeWebUserData(String userId, String status) throws SQLException {
		/* 變數宣告 */
		Integer quitResult = 0;
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 產生物件 */
		WebUserData quitUserData = (WebUserData)session.get(WebUserData.class, userId);
		/* 設定狀態 */
		quitUserData.setStatus(status);
		/* 執行變更 */
		session.saveOrUpdate(quitUserData);
		quitResult++;
		return quitResult;
	}
	
	/* 檢查Id是否為棄用 -1->異常、0->失敗、1->成功 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkUserIdQuit(String inputUserId) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :inputUserId AND wu.status = 'active'";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query.setParameter("inputUserId", inputUserId).getResultList();
		/* 由size()判結果 */
		checkResult = (list.size() > 0) ? 1 : 0;
		return checkResult;
	}

	/* 檢查帳號是否為棄用 -1->異常、0->失敗、1->成功 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkAccountQuit(String inputAccount) throws SQLException {
		/* 變數宣告 */
		Integer checkResult = -1;
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :inputAccount AND wu.status = 'active'";
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

	/* 更新使用者資料 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer updateWebUserData(WebUserData updateUserData) throws SQLException {
		/* 變數宣告 */
		Integer updateResult = 0;
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行變更 */
		session.saveOrUpdate(updateUserData);
		updateResult++;
		return updateResult;
	}

	/* 更新使用者密碼 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer updateWebUserPassword(WebUserData updatedUserData) throws SQLException {
		/* 變數宣告 */
		Integer updateResult = 0;
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行變更 */
		session.saveOrUpdate(updatedUserData);
		updateResult++;
		return updateResult;
	}
	
	/* 更新使用者密碼 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer updateWebUserPassword(String userId, String password) throws SQLException {
		/* 變數宣告 */
		Integer updateResult = 0;
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 產生要變更的物件 */
		WebUserData resetUserData = session.get(WebUserData.class, userId);
		/* 設定新密碼 */
		resetUserData.setPassword(password);
		/* 執行變更 */
		session.saveOrUpdate(resetUserData);
		updateResult++;
		return updateResult;
	}

	/* 取得查詢的使用者資料 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WebUserData> getOtherWebUserData(String selectedParameters) throws SQLException {
		/* HQL */
		String hql = ""; 
		StringBuilder sb = new StringBuilder();
		sb.append("FROM WebUserData AS wu WHERE ");
		
		String account = (selectedParameters.split(":")[0].equals("?")) ? "" : selectedParameters.split(":")[0];
		String nickname = (selectedParameters.split(":")[1].equals("?")) ? "" : selectedParameters.split(":")[1];
		String fervor = (selectedParameters.split(":")[2].equals("?")) ? "" : selectedParameters.split(":")[2];
		String locationCode = (selectedParameters.split(":")[3].equals("?")) ? "" : selectedParameters.split(":")[3];
		Integer lv = Integer.parseInt(selectedParameters.split(":")[4]);
		String status = "'" + selectedParameters.split(":")[5] + "'";
		String selectedStatus = (selectedParameters.split(":")[6].equals("?")) ? "" : "'" + selectedParameters.split(":")[6] + "'";
		
		if (!account.equals("")) {
			account = "'%" + selectedParameters.split(":")[0] + "%'";
			sb.append("wu.account LIKE " + account);
		} 
		
		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& !nickname.equals("")) {
			nickname = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append("wu.nickname LIKE " + nickname);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& !nickname.equals("")){
			nickname = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append(" AND wu.nickname LIKE " + nickname);
		}
		
		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& !fervor.equals("")) {
			fervor = "'%" + selectedParameters.split(":")[2] + "%'";
			sb.append("wu.fervor LIKE " + fervor);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& !fervor.equals("")){
			fervor = "'%" + selectedParameters.split(":")[2] + "%'";
			sb.append(" AND wu.fervor LIKE " + fervor);
		}
		
		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& !locationCode.equals("")) {
			locationCode = "'" + selectedParameters.split(":")[3] + "'";
			sb.append("wu.locationCode = " + locationCode);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& !locationCode.equals("")){
			locationCode = "'" + selectedParameters.split(":")[3] + "'";
			sb.append(" AND wu.locationCode = " + locationCode);
		}
		
		if (lv == -1 
				&& (!selectedStatus.equals("?") && !selectedStatus.equals(""))
				&& (sb.toString().equals("FROM WebUserData AS wu WHERE "))) {
			sb.append("wu.status = " + selectedStatus);
		} else if (lv == -1 
				&& (!selectedStatus.equals("?") && !selectedStatus.equals(""))
				&& (!sb.toString().equals("FROM WebUserData AS wu WHERE "))) {
			sb.append(" AND wu.status = " + selectedStatus);
		}
		
		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& (lv == -1)) {
			sb.append("wu.lv >= :lv");
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& (lv == -1)) {
			sb.append(" AND wu.lv >= :lv");
		} else if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& (lv == 0)) {
			sb.append("wu.lv = :lv AND wu.status = " + status);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& (lv == 0)) {
			sb.append(" AND wu.lv = :lv AND wu.status = " + status);
		} else if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& (lv == 1)) {
			sb.append("wu.lv <= :lv AND wu.lv >= 0 AND wu.status = " + status);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) 
				&& (lv == 1)) {
			sb.append(" AND wu.lv <= :lv AND wu.lv >= 0 AND wu.status = " + status);
		}
		
		hql = sb.toString();
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query
				.setParameter("lv", lv)
				.getResultList();
		
		return list;
	}

	/* 取得所有使用者資料 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WebUserData> getAllWebUserData(Integer lv, String status) throws SQLException {
		/* HQL */
		String hql = ""; 
		if (lv == -1) {
			hql = "FROM WebUserData AS wu WHERE wu.lv >= :lv";
		} else if (lv == 0) {
			String inputStatus = "'" + status + "'";
			hql = "FROM WebUserData AS wu WHERE wu.lv = :lv AND wu.status = " + inputStatus;
		} else if (lv == 1) {
			String inputStatus = "'" + status + "'";
			hql = "FROM WebUserData AS wu WHERE wu.lv <= :lv AND wu.lv >= 0 AND wu.status = "+ inputStatus;
		}
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query
				.setParameter("lv", lv)
				.getResultList();
		return list;
	}

	/* 刪除使用者帳戶 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer deleteWebUserData(String deletedUserId) throws SQLException {
		int count = 0;
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 產生物件 */
		WebUserData deletedUserData = (WebUserData)session.get(WebUserData.class, deletedUserId);
		/* 執行刪除 */
		session.delete(deletedUserData);
		/* 成功則遞增 */
		count++;
		return count;
	}
	
	/* 驗證使用者資料 */
	@SuppressWarnings("unchecked")
	public WebUserData checkRecoveryInfo(String email, String phone, Date birth) throws SQLException {
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query
				.setParameter("email", email)
				.setParameter("phone", phone)
				.setParameter("birth", birth)
				.getResultList();
		/* 設定值 */
		return (list.size() == 1) ? list.get(0) : new WebUserData();
	}
	
	/* 驗證使用者資料 */
	@SuppressWarnings("unchecked")
	public WebUserData checkRecoveryInfo(String account, String email, String phone, Date birth) throws SQLException {
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :account AND wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query
				.setParameter("account", account)
				.setParameter("email", email)
				.setParameter("phone", phone)
				.setParameter("birth", birth)
				.getResultList();
		/* 設定值 */
		return (list.size() == 1) ? list.get(0) : new WebUserData();
	}
	
	/* 驗證使用者資料 */
	@SuppressWarnings("unchecked")
	public WebUserData checkRecoveryInfo(String account, String password, String email, String phone, Date birth) throws SQLException {
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :account AND wu.password = :password AND wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query
				.setParameter("account", account)
				.setParameter("password", password)
				.setParameter("email", email)
				.setParameter("phone", phone)
				.setParameter("birth", birth)
				.getResultList();
		/* 設定值 */
		return (list.size() == 1) ? list.get(0) : new WebUserData();
	}
	
	/* 驗證使用者資料 */
	@SuppressWarnings("unchecked")
	public WebUserData checkRecoveryInfoAnother(String password, String email, String phone, Date birth) throws SQLException {
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.password = :password AND wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query
				.setParameter("password", password)
				.setParameter("email", email)
				.setParameter("phone", phone)
				.setParameter("birth", birth)
				.getResultList();
		/* 設定值 */
		return (list.size() == 1) ? list.get(0) : new WebUserData();
	}

	/* 檢查密碼 -1->異常、0->錯誤、1->正確 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkResetPassword(String inputUserId, String inputPassword) throws SQLException {
		/* 變數宣告 */
		int checkResult = 0;
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :inputUserId AND wu.password = :inputPassword";
		/* 取得當前Session */
		Session session = factory.getCurrentSession();
		/* 執行HQL */
		Query<WebUserData> query = session.createQuery(hql);
		/* 取得陣列 */
		List<WebUserData> list = query
				.setParameter("inputUserId", inputUserId)
				.setParameter("inputPassword", inputPassword)
				.getResultList();
		/* 由size()判結果 */
		checkResult = (list.size() > 0) ? 1 : 0;
		return checkResult;
	}
}