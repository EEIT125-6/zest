package webUser.repository.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import webUser.model.WebUserData;
import webUser.repository.WebUserRepository;

@Repository
public class WebUserRepositoryImpl implements WebUserRepository {
	/* 產生SessionFactory */
	@Autowired
	SessionFactory factory;
	
	/* 重複出現factory.getCurrentSession()，所以整理成一個方法，直接呼叫結果 */
	public Session getSession() {
		return factory.getCurrentSession();
	}

	/* 檢查Id是否存在 -1->異常、0->不存在、1->存在 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkUserIdExist(String inputUserId) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :inputUserId";
		/* 取得當前Session，然後執行HQL以取得資料陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputUserId", inputUserId).getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}

	/* 檢查Id是否為棄用 -1->異常、0->失敗、1->成功 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkUserIdQuit(String inputUserId) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :inputUserId AND wu.status = 'active'";
		/* 取得當前Session，然後執行HQL以取得資料陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputUserId", inputUserId).getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}

	/* 檢查帳號是否存在 -1->異常、0->不存在、1->存在 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkAccountExist(String inputAccount) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :inputAccount";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputAccount", inputAccount)
				.getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}

	/* 檢查帳號是否為棄用 -1->異常、0->失敗、1->成功 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkAccountQuit(String inputAccount) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :inputAccount AND wu.status = 'active'";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputAccount", inputAccount)
				.getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}

	/* 檢查稱呼是否已使用 -1->異常、0->未使用、1->使用 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkNicknameExist(String inputNickname) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.nickname = :inputNickname";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputNickname", inputNickname)
				.getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}

	/* 檢查信箱是否已使用 -1->異常、0->不存在、1->存在 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkEmailExist(String inputEmail) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.email = :inputEmail";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputEmail", inputEmail).getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}

	/* 檢查電話是否已使用 -1->異常、0->不存在、1->存在 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkPhoneExist(String inputPhone) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.phone = :inputPhone";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputPhone", inputPhone).getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}

	/* 檢查密碼 -1->異常、0->錯誤、1->正確 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkPassword(String inputAccount, String inputPassword) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :inputAccount AND wu.password = :inputPassword";
		/* 取得當前Session以執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputAccount", inputAccount)
				.setParameter("inputPassword", inputPassword).getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}
	
	/* 執行簽到 -1->異常、0->錯誤、1->正確 */
	public Integer runWebUserSignIn(WebUserData userData) throws SQLException {
		/* 變數宣告 */
		Integer updateResult = 0;
		/* 取得當前Session以執行變更 */
		getSession().saveOrUpdate(userData);
		updateResult++;
		return updateResult;
	}
	
	/* 檢查簽到 -1->異常、0->錯誤、1->正確 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkWebUserSignIn(String inputUserId, Date today) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :inputUserId AND wu.signIn = :today";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputUserId", inputUserId).setParameter("today", today)
				.getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}

	/* 驗證使用者資料 */
	@SuppressWarnings("unchecked")
	@Override
	public WebUserData checkRecoveryInfo(String email, String phone, Date birth) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("email", email).setParameter("phone", phone)
				.setParameter("birth", birth).getResultList();
		/* 設定值 */
		return (list.size() == 1) ? list.get(0) : null;
	}

	/* 驗證使用者資料 */
	@SuppressWarnings("unchecked")
	@Override
	public WebUserData checkRecoveryInfo(String account, String email, String phone, Date birth) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :account AND wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("account", account)
				.setParameter("email", email).setParameter("phone", phone).setParameter("birth", birth).getResultList();
		/* 設定值 */
		return (list.size() == 1) ? list.get(0) : null;
	}

	/* 驗證使用者資料 */
	@SuppressWarnings("unchecked")
	@Override
	public WebUserData checkRecoveryInfo(String account, String password, String email, String phone, Date birth)
			throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :account AND wu.password = :password AND wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("account", account)
				.setParameter("password", password).setParameter("email", email).setParameter("phone", phone)
				.setParameter("birth", birth).getResultList();
		/* 設定值 */
		return (list.size() == 1) ? list.get(0) : null;
	}

	/* 驗證使用者資料 */
	@SuppressWarnings("unchecked")
	@Override
	public WebUserData checkRecoveryInfoAnother(String password, String email, String phone, Date birth)
			throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.password = :password AND wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("password", password)
				.setParameter("email", email).setParameter("phone", phone).setParameter("birth", birth).getResultList();
		/* 設定值 */
		return (list.size() == 1) ? list.get(0) : null;
	}

	/* 檢查密碼 -1->異常、0->錯誤、1->正確 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkResetPassword(String inputUserId, String inputPassword) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :inputUserId AND wu.password = :inputPassword";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputUserId", inputUserId)
				.setParameter("inputPassword", inputPassword).getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}

	/* 產生新增使用者所需的ID */
	@SuppressWarnings("unchecked")
	@Override
	public String createNewUserId(String selectedId, Integer lv) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.id LIKE :selectedId";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("selectedId", selectedId).getResultList();
		/* 取得最後一筆 */
		String lastAccount = (list.size() > 0) ? list.get(list.size() - 1).getUserId() : "";
		return (!lastAccount.equals("")) ? String.valueOf(Integer.parseInt(lastAccount) + 1)
				: String.valueOf(lv) + "000001";
	}

	/* 新增使用者資料 1->成功、0->失敗 */
	@Override
	public Integer insertWebUserData(WebUserData registerData) throws SQLException {
		/* 變數宣告 */
		int instertResult = 0;
		/* 取得當前Session並執行新增，在此改用merge */
		getSession().merge(registerData);
		instertResult++;
		return instertResult;
	}

	/* 取得使用者個人資料 */
	@SuppressWarnings("unchecked")
	@Override
	public WebUserData getWebUserData(String inputAccount) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :inputAccount";
		/* 取得當前Session，執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputAccount", inputAccount)
				.getResultList();
		/* 取出資料，理論上陣列中只會有一筆資料 */
		return (list.size() == 1) ? list.get(0) : null;
	}
	
	/* 取得使用者個人資料 */
	@SuppressWarnings("unchecked")
	@Override
	public WebUserData getWebUserDataById(String userId) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :userId";
		/* 取得當前Session，執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql).setParameter("userId", userId)
				.getResultList();
		/* 取出資料，理論上陣列中只會有一筆資料 */
		return (list.size() == 1) ? list.get(0) : null;
	}

	/* 取得查詢的使用者資料 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WebUserData> getSelectedWebUserData(String selectedParameters, Integer avPage, Integer startPage) throws SQLException {
		/* 取得開始的筆數 */
		Integer startIndex = (startPage - 1) * avPage;
		/* 開始組字串 */
		StringBuilder sb = new StringBuilder();
		sb.append("FROM WebUserData AS wu WHERE ");

		String account = (selectedParameters.split(":")[0].equals("?")) ? "" : selectedParameters.split(":")[0];
		String nickname = (selectedParameters.split(":")[1].equals("?")) ? "" : selectedParameters.split(":")[1];
		String fervor = (selectedParameters.split(":")[2].equals("?")) ? "" : selectedParameters.split(":")[2];
		Integer locationCode = (selectedParameters.split(":")[3].equals("0")) ? 0 : Integer.parseInt(selectedParameters.split(":")[3]);
		Integer lv = Integer.parseInt(selectedParameters.split(":")[4]);
		String status = "'" + selectedParameters.split(":")[5] + "'";
		String selectedStatus = (selectedParameters.split(":")[6].equals("?")) ? ""
				: "'" + selectedParameters.split(":")[6] + "'";
		Integer selectedIdentity = (selectedParameters.split(":")[6].equals("-2")) ? -2
				: Integer.parseInt(selectedParameters.split(":")[7]);
		
		if (!account.equals("")) {
			account = "'%" + selectedParameters.split(":")[0] + "%'";
			sb.append("wu.account LIKE " + account);
		}

		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && !nickname.equals("")) {
			nickname = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append("wu.nickname LIKE " + nickname);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && !nickname.equals("")) {
			nickname = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append(" AND wu.nickname LIKE " + nickname);
		}

		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && !fervor.equals("")) {
			fervor = "'%" + selectedParameters.split(":")[2] + "%'";
			sb.append("wu.fervor LIKE " + fervor);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && !fervor.equals("")) {
			fervor = "'%" + selectedParameters.split(":")[2] + "%'";
			sb.append(" AND wu.fervor LIKE " + fervor);
		}

		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && locationCode != 0) {
			locationCode = Integer.parseInt(selectedParameters.split(":")[3]);
			sb.append("wu.locationInfo.cityCode = " + locationCode);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && locationCode != 0) {
			locationCode = Integer.parseInt(selectedParameters.split(":")[3]);
			sb.append(" AND wu.locationInfo.cityCode = " + locationCode);
		}

		if (lv == -1 && (!selectedStatus.equals("?") && !selectedStatus.equals(""))
				&& (sb.toString().equals("FROM WebUserData AS wu WHERE "))) {
			sb.append("wu.status = " + selectedStatus);
		} else if (lv == -1 && (!selectedStatus.equals("?") && !selectedStatus.equals(""))
				&& (!sb.toString().equals("FROM WebUserData AS wu WHERE "))) {
			sb.append(" AND wu.status = " + selectedStatus);
		}
		
		if (lv == -1 && (selectedIdentity >= -1 && selectedIdentity <= 1)
				&& (sb.toString().equals("FROM WebUserData AS wu WHERE "))) {
			sb.append("wu.accountLv.lv = " + selectedIdentity);
		} else if (lv == -1 && (selectedIdentity >= -1 && selectedIdentity <= 1)
				&& (!sb.toString().equals("FROM WebUserData AS wu WHERE "))) {
			sb.append(" AND wu.accountLv.lv = " + selectedIdentity);
		}

		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == -1)) {
			sb.append("wu.accountLv.lv >= :lv");
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == -1)) {
			sb.append(" AND wu.accountLv.lv >= :lv");
		} else if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == 0)) {
			sb.append("wu.accountLv.lv = :lv AND wu.status = " + status);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == 0)) {
			sb.append(" AND wu.accountLv.lv = :lv AND wu.status = " + status);
		} else if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == 1)) {
			sb.append("wu.accountLv.lv <= :lv AND wu.accountLv.lv >= 0 AND wu.status = " + status);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == 1)) {
			sb.append(" AND wu.accountLv.lv <= :lv AND wu.accountLv.lv >= 0 AND wu.status = " + status);
		}

		/* 取得當前Session，然後執行HQL以取得陣列 */
		return getSession().createQuery(sb.toString())
				.setParameter("lv", lv)
				.setFirstResult(startIndex)
                .setMaxResults(avPage).getResultList();
	}
	
	/* 取得查詢的使用者資料 */
	@SuppressWarnings("unchecked")
	@Override
	public List<WebUserData> getAllWebUserData() throws SQLException {
		String hql = "FROM WebUserData AS wu WHERE wu.status='active'";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		return getSession().createQuery(hql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<WebUserData> getAllYearWebUserData(String year) throws SQLException {
		String hql = "FROM WebUserData AS wu WHERE wu.status='active' AND wu.joinDate LIKE :year";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		return getSession().createQuery(hql).setParameter("year", year).getResultList();
	}
	
	/* 取得查詢的總筆數 */
	@Override
	public Long getUserRecordCounts(String selectedParameters) throws SQLException {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM WebUserData AS wu WHERE ");

		String account = (selectedParameters.split(":")[0].equals("?")) ? "" : selectedParameters.split(":")[0];
		String nickname = (selectedParameters.split(":")[1].equals("?")) ? "" : selectedParameters.split(":")[1];
		String fervor = (selectedParameters.split(":")[2].equals("?")) ? "" : selectedParameters.split(":")[2];
		Integer locationCode = (selectedParameters.split(":")[3].equals("0")) ? 0 : Integer.parseInt(selectedParameters.split(":")[3]);
		Integer lv = Integer.parseInt(selectedParameters.split(":")[4]);
		String status = "'" + selectedParameters.split(":")[5] + "'";
		String selectedStatus = (selectedParameters.split(":")[6].equals("?")) ? ""
				: "'" + selectedParameters.split(":")[6] + "'";
		Integer selectedIdentity = (selectedParameters.split(":")[6].equals("-2")) ? -2
				: Integer.parseInt(selectedParameters.split(":")[7]);
		
		if (!account.equals("")) {
			account = "'%" + selectedParameters.split(":")[0] + "%'";
			sb.append("wu.account LIKE " + account);
		}

		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && !nickname.equals("")) {
			nickname = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append("wu.nickname LIKE " + nickname);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && !nickname.equals("")) {
			nickname = "'%" + selectedParameters.split(":")[1] + "%'";
			sb.append(" AND wu.nickname LIKE " + nickname);
		}

		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && !fervor.equals("")) {
			fervor = "'%" + selectedParameters.split(":")[2] + "%'";
			sb.append("wu.fervor LIKE " + fervor);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && !fervor.equals("")) {
			fervor = "'%" + selectedParameters.split(":")[2] + "%'";
			sb.append(" AND wu.fervor LIKE " + fervor);
		}

		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && locationCode != 0) {
			locationCode = Integer.parseInt(selectedParameters.split(":")[3]);
			sb.append("wu.locationInfo.cityCode = " + locationCode);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && locationCode != 0) {
			locationCode = Integer.parseInt(selectedParameters.split(":")[3]);
			sb.append(" AND wu.locationInfo.cityCode = " + locationCode);
		}

		if (lv == -1 && (!selectedStatus.equals("?") && !selectedStatus.equals(""))
				&& (sb.toString().equals("FROM WebUserData AS wu WHERE "))) {
			sb.append("wu.status = " + selectedStatus);
		} else if (lv == -1 && (!selectedStatus.equals("?") && !selectedStatus.equals(""))
				&& (!sb.toString().equals("FROM WebUserData AS wu WHERE "))) {
			sb.append(" AND wu.status = " + selectedStatus);
		}
		
		if (lv == -1 && (selectedIdentity >= -1 && selectedIdentity <= 1)
				&& (sb.toString().equals("FROM WebUserData AS wu WHERE "))) {
			sb.append("wu.accountLv.lv = " + selectedIdentity);
		} else if (lv == -1 && (selectedIdentity >= -1 && selectedIdentity <= 1)
				&& (!sb.toString().equals("FROM WebUserData AS wu WHERE "))) {
			sb.append(" AND wu.accountLv.lv = " + selectedIdentity);
		}

		if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == -1)) {
			sb.append("wu.accountLv.lv >= :lv");
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == -1)) {
			sb.append(" AND wu.accountLv.lv >= :lv");
		} else if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == 0)) {
			sb.append("wu.accountLv.lv = :lv AND wu.status = " + status);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == 0)) {
			sb.append(" AND wu.accountLv.lv = :lv AND wu.status = " + status);
		} else if ((sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == 1)) {
			sb.append("wu.accountLv.lv <= :lv AND wu.accountLv.lv >= 0 AND wu.status = " + status);
		} else if ((!sb.toString().equals("FROM WebUserData AS wu WHERE ")) && (lv == 1)) {
			sb.append(" AND wu.accountLv.lv <= :lv AND wu.accountLv.lv >= 0 AND wu.status = " + status);
		}

		/* 取得當前Session，然後執行HQL以取得陣列 */
		return Long.parseLong(String.valueOf(getSession().createQuery(sb.toString()).setParameter("lv", lv).getResultList().size()));
	}
	
	/* 取得查詢的最大頁數 */
	public Integer getTotalUserRecordCounts(String selectedParameters, Integer avPage) throws SQLException {
		Integer totalPages = (int) (Math.ceil(getUserRecordCounts(selectedParameters) / (double) avPage));
		return totalPages;
	}
	
	/* 更新使用者資料 0->失敗、1->成功 */
	@Override
	public Integer updateWebUserData(WebUserData updatedUserData) throws SQLException {
		/* 變數宣告 */
		Integer updateResult = 0;
		/* 取得當前Session以執行變更 */
		getSession().saveOrUpdate(updatedUserData);
		updateResult++;
		return updateResult;
	}
	
	/* 由系統管理員執行的使用者變更 */
	@Override
	public Integer adminChangeWebUserData(String userId, String status) throws SQLException {
		/* 變數宣告 */
		Integer quitResult = 0;
		/* 取得當前Session，產生操作物件 */
		WebUserData changedUserData = (WebUserData) getSession().get(WebUserData.class, userId);
		/* 設定狀態 */
		changedUserData.setStatus(status);
		/* 更新版本 */
		changedUserData.setVersion(changedUserData.getVersion() + 1);
		/* 執行變更 */
		getSession().saveOrUpdate(changedUserData);
		quitResult++;
		return quitResult;
	}
	
	/* 重設使用者密碼 -1->異常、0->失敗、1->成功 */
	@Override
	public Integer resetWebUserPassword(String userId, String password) throws SQLException {
		/* 變數宣告 */
		Integer updateResult = 0;
		/* 取得當前Session，產生要變更的物件 */
		WebUserData resetUserData = getSession().get(WebUserData.class, userId);
		/* 設定新密碼 */
		resetUserData.setPassword(password);
		/* 更新版本 */
		resetUserData.setVersion(resetUserData.getVersion() + 1);
		/* 執行變更 */
		getSession().saveOrUpdate(resetUserData);
		updateResult++;
		return updateResult;
	}

	/* 檢查有多少可登入的管理員帳號(回傳整數或0) */
	@Override
	public Integer checkAdminAccess() throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.accountLv.lv = -1 AND wu.status = 'active'";
		/* 回傳符合條件的比數 */
		return getSession().createQuery(hql).getResultList().size();
	}
	
	/* 取回所有可用使用者的註冊日期 */
	public List<LocalDate> getAllWebUserJoinDate(String year) throws SQLException {
		List<LocalDate> joinDateList = new ArrayList<>();
		List<WebUserData> userList = getAllYearWebUserData(year);
		
		for (WebUserData user: userList) {
			/* List中沒有才加入，避免重複日期 */
			if (!joinDateList.contains(user.getJoinDate().toLocalDate())) {
				joinDateList.add(user.getJoinDate().toLocalDate());
			}
		}
		return joinDateList;
	}
}
