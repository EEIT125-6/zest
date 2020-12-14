package webUser.repository.impl;

import java.sql.Date;
import java.sql.SQLException;
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
		List<WebUserData> list = getSession()
								.createQuery(hql)
								.setParameter("inputUserId", inputUserId)
								.getResultList();
		/* 由size()判結果 */
		return (list.size() > 0) ? 1 : 0;
	}
	
	/* 檢查Id是否為棄用 -1->異常、0->失敗、1->成功 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer checkUserIdQuit(String inputUserId) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :inputUserId AND wu.status = 'active'";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession()
								.createQuery(hql)
								.setParameter("inputUserId", inputUserId)
								.getResultList();
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
		List<WebUserData> list = getSession()
								.createQuery(hql)
								.setParameter("inputAccount", inputAccount)
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
		List<WebUserData> list = getSession()
								.createQuery(hql)
								.setParameter("inputAccount", inputAccount)
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
		List<WebUserData> list = getSession().createQuery(hql).setParameter("inputNickname", inputNickname).getResultList();
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
		List<WebUserData> list = getSession()
								.createQuery(hql)
								.setParameter("inputAccount", inputAccount)
								.setParameter("inputPassword", inputPassword)
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
		List<WebUserData> list = getSession()
								.createQuery(hql)
								.setParameter("email", email)
								.setParameter("phone", phone)
								.setParameter("birth", birth)
								.getResultList();
		/* 設定值 */
		return (list.size() == 1) ? list.get(0) : new WebUserData();
	}

	/* 驗證使用者資料 */
	@SuppressWarnings("unchecked")
	@Override
	public WebUserData checkRecoveryInfo(String account, String email, String phone, Date birth) throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :account AND wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession()
								.createQuery(hql)
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
	@Override
	public WebUserData checkRecoveryInfo(String account, String password, String email, String phone, Date birth)
			throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.account = :account AND wu.password = :password AND wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession()
								.createQuery(hql)
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
	@Override
	public WebUserData checkRecoveryInfoAnother(String password, String email, String phone, Date birth)
			throws SQLException {
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.password = :password AND wu.email = :email AND wu.phone = :phone AND wu.birth = :birth";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession()
								.createQuery(hql)
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
		/* HQL */
		String hql = "FROM WebUserData AS wu WHERE wu.userId = :inputUserId AND wu.password = :inputPassword";
		/* 取得當前Session，然後執行HQL以取得陣列 */
		List<WebUserData> list = getSession().createQuery(hql)
				.setParameter("inputUserId", inputUserId)
				.setParameter("inputPassword", inputPassword)
				.getResultList();
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
		List<WebUserData> list = getSession()
								.createQuery(hql)
								.setParameter("selectedId", selectedId)
								.getResultList();
		/* 取得最後一筆 */
		String lastAccount = (list.size() > 0) 
							? list.get(list.size() - 1).getUserId() 
							: "";
		return (!lastAccount.equals("")) 
				? String.valueOf(Integer.parseInt(lastAccount) + 1) 
				: String.valueOf(lv) + "000001";
	}

	/* 新增使用者資料 1->成功、0->失敗*/
	@Override
	public Integer insertWebUserData(String insertId, WebUserData registerData) throws SQLException {
		/* 變數宣告 */
		int instertResult = 0;
		/* 設定userId */
		registerData.setUserId(insertId);
		/* 取得當前Session並執行新增 */
		getSession().save(registerData);
		instertResult++;
		return instertResult;
	}

	@Override
	public WebUserData getWebUserData(String inputAccount) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebUserData> getOtherWebUserData(String selectedParameters) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WebUserData> getAllWebUserData(Integer lv, String status) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer quitWebUserData(WebUserData quitUserData) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer adminChangeWebUserData(String userId, String status) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateWebUserData(WebUserData updatedUserData) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateWebUserPassword(WebUserData updatedUserData) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer updateWebUserPassword(String userId, String password) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteWebUserData(String deletedUserId) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
}
