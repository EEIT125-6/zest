package webUser.service.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webUser.model.WebUserData;
import webUser.repository.WebUserRepository;
import webUser.service.WebUserService;

@Transactional
@Service
public class WebUserServiceImpl implements WebUserService {
	/* 產生實作物件 */
	@Autowired
	WebUserRepository webUserDAO;
	
	@Override
	public Integer checkUserIdExist(String inputUserId) throws SQLException {
		return webUserDAO.checkUserIdExist(inputUserId);
	}

	@Override
	public Integer checkUserIdQuit(String inputUserId) throws SQLException {
		return webUserDAO.checkUserIdQuit(inputUserId);
	}

	@Override
	public Integer checkAccountExist(String inputAccount) throws SQLException {
		return webUserDAO.checkAccountExist(inputAccount);
	}

	@Override
	public Integer checkNicknameExist(String inputNickname) throws SQLException {
		return webUserDAO.checkNicknameExist(inputNickname);
	}

	@Override
	public Integer checkEmailExist(String inputEmail) throws SQLException {
		return webUserDAO.checkEmailExist(inputEmail);
	}

	@Override
	public Integer checkPhoneExist(String inputPhone) throws SQLException {
		return webUserDAO.checkPhoneExist(inputPhone);
	}
	
	@Override
	public WebUserData checkRecoveryInfo(String email, String phone, Date birth) throws SQLException {
		return webUserDAO.checkRecoveryInfo(email, phone, birth);
	}
	
	@Override
	public WebUserData checkRecoveryInfo(String account, String email, String phone, Date birth) throws SQLException {
		return webUserDAO.checkRecoveryInfo(account, email, phone, birth);
	}
	
	@Override
	public WebUserData checkRecoveryInfo(String account, String password, String email, String phone, Date birth) throws SQLException {
		return webUserDAO.checkRecoveryInfo(account, password, email, phone, birth);
	}
	
	@Override
	public WebUserData checkRecoveryInfoAnother(String password, String email, String phone, Date birth) throws SQLException {
		return webUserDAO.checkRecoveryInfoAnother(password, email, phone, birth);
	}
	
	@Override
	public Integer checkResetPassword(String inputUserId, String inputPassword) throws SQLException {
		return webUserDAO.checkResetPassword(inputUserId, inputPassword);
	}

	@Override
	public Integer insertWebUserData(WebUserData registerData) throws SQLException {
		Integer insertResult = 0;
		Integer lv = registerData.getAccountLv().getLv();
		/* 第一碼 = lv + 1 */
		String selectedId = String.valueOf(lv + 1) + "%";
		/* 取得userId */
		String insertId = webUserDAO.createNewUserId(selectedId, lv + 1);
		/* 設定userId */
		registerData.setUserId(insertId);
		webUserDAO.insertWebUserData(registerData);
		insertResult++;
		return insertResult;
	}

	@Override
	public Integer checkWebUserLogin(String inputAccount, String inputPassword) throws SQLException {
		/* 變數宣告 */
		Integer checkLoginResult = -2;
		Integer checkAccountResult = -1;
		Integer checkAccountQuit = -1;
		Integer checkPasswordResult = -1;
		
		/* 檢查帳號 */
		checkAccountResult = webUserDAO.checkAccountExist(inputAccount);
		/* 帳號不存在就不繼續往下執行 */
		if (checkAccountResult != 1) {
			throw new SQLException("帳號錯誤，請檢查之後再次輸入");
		} else {
			checkLoginResult++;
		}
		
		/* 檢查帳號是否有效 */
		checkAccountQuit = webUserDAO.checkAccountQuit(inputAccount);
		/* 帳號棄用就不繼續往下執行 */
		if (checkAccountQuit != 1) {
			throw new SQLException("該帳號已棄用！請選擇其他帳號登入或註冊一個新帳號");
		} else {
			checkLoginResult++;
		}
		
		/* 檢查密碼 */
		checkPasswordResult = webUserDAO.checkPassword(inputAccount, inputPassword);
		/* 密碼錯誤 */
		if (checkPasswordResult != 1) {
			throw new SQLException("密碼錯誤，請檢查之後再次輸入");
		} else {
			checkLoginResult++;
		}
		
		return checkLoginResult;
	}
	
	@Override
	public Integer checkWebUserSignIn(String inputUserId, Date today) throws SQLException {
		return webUserDAO.checkWebUserSignIn(inputUserId, today);
	}
	
	@Override
	public Integer runWebUserSignIn(WebUserData userData) throws SQLException {
		return webUserDAO.runWebUserSignIn(userData);
	}
	
	@Override
	public WebUserData getWebUserData(String inputAccount) throws SQLException {
		return webUserDAO.getWebUserData(inputAccount);
	}
	
	@Override
	public WebUserData getWebUserDataById(String userId) throws SQLException {
		return webUserDAO.getWebUserDataById(userId);
	}

	@Override
	public List<WebUserData> getSelectedWebUserData(String selectedParameters) throws SQLException {
		return webUserDAO.getSelectedWebUserData(selectedParameters);
	}
	
	@Override
	public Long getUserRecordCounts(String selectedParameters) throws SQLException {
		return webUserDAO.getUserRecordCounts(selectedParameters);
	}
	
	@Override
	public Integer getTotalUserRecordCounts(String selectedParameters, Integer avPage) throws SQLException {
		return webUserDAO.getTotalUserRecordCounts(selectedParameters, avPage);
	}

	@Override
	public Integer updateWebUserIconUrl(WebUserData updatedUserData) throws SQLException {
		return webUserDAO.updateWebUserData(updatedUserData);
	}
	
	@Override
	public Integer updateWebUserData(WebUserData updatedUserData) throws SQLException {
		return webUserDAO.updateWebUserData(updatedUserData);
	}

	@Override
	public Integer updateWebUserPassword(WebUserData updatedUserData) throws SQLException {
		return webUserDAO.updateWebUserData(updatedUserData);
	}
	
	@Override
	public Integer resetWebUserPassword(String userId, String password) throws SQLException {
		return webUserDAO.resetWebUserPassword(userId, password);
	}

	@Override
	public Integer adminChangeWebUserData(String userId, String status) throws SQLException {
		return webUserDAO.adminChangeWebUserData(userId, status);
	}

	@Override
	public Integer checkAdminAccess() throws SQLException {
		return webUserDAO.checkAdminAccess();
	}
}
