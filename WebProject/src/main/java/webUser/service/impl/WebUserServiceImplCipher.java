package webUser.service.impl;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import util.CipherMsg;
import webUser.model.WebUserData;
import webUser.repository.WebUserRepository;
import webUser.service.WebUserService;

@Transactional
@Service
public class WebUserServiceImplCipher implements WebUserService {
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
		WebUserData requestedUserData = webUserDAO.checkRecoveryInfo(email, phone, birth);
		/* 回到Client端，將密碼解密 */
		String truePassword = CipherMsg.dencryptMsg(requestedUserData.getPassword());
		/* 成功 */
		if (!truePassword.startsWith("error!")) {
			requestedUserData.setPassword(truePassword);
			/* 如果為Google第三方登入，則不允許進行帳號重設 */
			return (requestedUserData.getPassword() == null) ? null : requestedUserData;
		} else {
			/* 密碼解密失敗 */
			return null;
		}
	}
	
	@Override
	public WebUserData checkRecoveryInfo(String account, String email, String phone, Date birth) throws SQLException {
		WebUserData requestedUserData = webUserDAO.checkRecoveryInfo(account, email, phone, birth);
		/* 回到Client端，將密碼解密 */
		String truePassword = CipherMsg.dencryptMsg(requestedUserData.getPassword());
		/* 成功 */
		if (!truePassword.startsWith("error!")) {
			requestedUserData.setPassword(truePassword);
			/* 如果為Google第三方登入，則不允許進行帳號重設 */
			return (requestedUserData.getPassword() == null) ? null : requestedUserData;
		} else {
			/* 密碼解密失敗 */
			return null;
		}
	}
	
	@Override
	public WebUserData checkRecoveryInfo(String account, String password, String email, String phone, Date birth) throws SQLException {
		/* 進DB前對密碼加密 */
		String realPassword = CipherMsg.encryptMsg(password);
		/* 成功 */
		if (!realPassword.startsWith("error!")) {
			WebUserData requestedUserData = webUserDAO.checkRecoveryInfo(account, realPassword, email, phone, birth);
			/* 回到Client端，將密碼解密 */
			String truePassword = CipherMsg.dencryptMsg(requestedUserData.getPassword());
			/* 成功 */
			if (!truePassword.startsWith("error!")) {
				requestedUserData.setPassword(truePassword);
				/* 如果為Google第三方登入，則不允許進行帳號重設 */
				return (requestedUserData.getPassword() == null) ? null : requestedUserData;
			} 
		}
		/* 失敗 */
		return null;
	}
	
	@Override
	public WebUserData checkRecoveryInfoAnother(String password, String email, String phone, Date birth) throws SQLException {
		/* 進DB前對密碼加密 */
		String realPassword = CipherMsg.encryptMsg(password);
		/* 成功 */
		if (!realPassword.startsWith("error!")) {
			WebUserData requestedUserData = webUserDAO.checkRecoveryInfoAnother(realPassword, email, phone, birth);
			/* 回到Client端，將密碼解密 */
			String truePassword = CipherMsg.dencryptMsg(requestedUserData.getPassword());
			/* 成功 */
			if (!truePassword.startsWith("error!")) {
				requestedUserData.setPassword(truePassword);
				/* 如果為Google第三方登入，則不允許進行帳號重設 */
				return (requestedUserData.getPassword() == null) ? null : requestedUserData;
			} 
		}
		/* 失敗 */
		return null;
	}
	
	@Override
	public Integer checkResetPassword(String inputUserId, String inputPassword) throws SQLException {
		/* 進DB前對密碼加密 */
		String realPassword = CipherMsg.encryptMsg(inputPassword);
		/* 成功 */
		if (!realPassword.startsWith("error!")) {
			return webUserDAO.checkResetPassword(inputUserId, realPassword);
		}
		/* 失敗 */
		return -1;
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
		/* 準備設定加密後的密碼 */
		String realPassword = CipherMsg.encryptMsg(registerData.getPassword());
		/* 成功 */
		if (!realPassword.startsWith("error!")) {
			/* 設定密碼 */
			registerData.setPassword(realPassword);
			webUserDAO.insertWebUserData(registerData);
			insertResult++;
			return insertResult;
		}
		/* 失敗 */
		return -1;
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
			throw new SQLException("該帳號已停用！請選擇其他帳號登入或註冊一個新帳號");
		} else {
			checkLoginResult++;
		}
		
		/* 準備設定加密後的密碼 */
		String realPassword = CipherMsg.encryptMsg(inputPassword);
		/* 成功 */
		if (!realPassword.startsWith("error!")) {			
			/* 檢查密碼 */
			checkPasswordResult = webUserDAO.checkPassword(inputAccount, realPassword);
		}
		/* 密碼錯誤 */
		if (checkPasswordResult == 0) {
			throw new SQLException("密碼錯誤，請檢查之後再次輸入");
		/* 解密失敗 */
		} else if (checkPasswordResult == -1) {
			throw new SQLException("處理加解密時發生異常");
		} else {
			checkLoginResult++;
		}
		
		return checkLoginResult;
	}
	
	@Override
	public Integer checkExtraWebUserLogin(String inputAccount) throws SQLException {
		/* 變數宣告 */
		Integer checkLoginResult = -1;
		Integer checkAccountResult = -1;
		Integer checkAccountQuit = -1;
		
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
		
		return checkLoginResult;
	}
	
	@Override
	public Integer checkWebUserSignIn(String inputUserId, Date today) throws SQLException {
		return webUserDAO.checkWebUserSignIn(inputUserId, today);
	}
	
	@Override
	public Integer runWebUserSignIn(WebUserData userData) throws SQLException {
		/* 進DB前對密碼加密 */
		String realPassword = CipherMsg.encryptMsg(userData.getPassword());
		/* 成功 */
		if (!realPassword.startsWith("error!")) {
			/* 設定加密後密碼 */
			userData.setPassword(realPassword);
			return webUserDAO.updateWebUserData(userData);
		}
		/* 失敗 */
		return -1;
	}
	
	@Override
	public WebUserData getWebUserData(String inputAccount) throws SQLException {
		WebUserData userData = webUserDAO.getWebUserData(inputAccount);
		/* 回到Client端，將密碼解密 */
		String truePassword = CipherMsg.dencryptMsg(userData.getPassword());
		/* 成功 */
		if (!truePassword.startsWith("error!")) {
			userData.setPassword(truePassword);
			return userData;
		}
		/* 失敗 */
		return null;
	}
	
	@Override
	public WebUserData getWebUserDataById(String userId) throws SQLException {
		WebUserData userData = webUserDAO.getWebUserDataById(userId);
		/* 回到Client端，將密碼解密 */
		String truePassword = CipherMsg.dencryptMsg(userData.getPassword());
		/* 成功 */
		if (!truePassword.startsWith("error!")) {
			userData.setPassword(truePassword);
			return userData;
		}
		/* 失敗 */
		return null;
	}

	@Override
	public List<WebUserData> getSelectedWebUserData(String selectedParameters, Integer avPage, Integer startPage) throws SQLException {
		List<WebUserData> userDataList = webUserDAO.getSelectedWebUserData(selectedParameters, avPage, startPage);
		Boolean cipherIsOk = true;
		for (WebUserData userData: userDataList) {
			/* 回到Client端，將密碼解密 */
			String truePassword = CipherMsg.dencryptMsg(userData.getPassword());
			/* 成功 */
			if (!truePassword.startsWith("error!")) {
				userData.setPassword(truePassword);
			/* 失敗 */
			} else {
				cipherIsOk = false;
				break;
			}
		}
		return (cipherIsOk) ? userDataList : null;
	}
	
	@Override
	public List<WebUserData> getAllWebUserData() throws SQLException {
		List<WebUserData> userDataList = webUserDAO.getAllWebUserData();
		Boolean cipherIsOk = true;
		for (WebUserData userData: userDataList) {
			/* 回到Client端，將密碼解密 */
			String truePassword = CipherMsg.dencryptMsg(userData.getPassword());
			/* 成功 */
			if (!truePassword.startsWith("error!")) {
				userData.setPassword(truePassword);
			/* 失敗 */
			} else {
				cipherIsOk = false;
				break;
			}
		}
		return (cipherIsOk) ? userDataList : null;
	}
	
	@Override
	public List<WebUserData> getAllYearWebUserData(String year) throws SQLException {
		List<WebUserData> userDataList = webUserDAO.getAllYearWebUserData(year);
		Boolean cipherIsOk = true;
		for (WebUserData userData: userDataList) {
			/* 回到Client端，將密碼解密 */
			String truePassword = CipherMsg.dencryptMsg(userData.getPassword());
			/* 成功 */
			if (!truePassword.startsWith("error!")) {
				userData.setPassword(truePassword);
			/* 失敗 */
			} else {
				cipherIsOk = false;
				break;
			}
		}
		return (cipherIsOk) ? userDataList : null;
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
	public Integer updateWebUserData(WebUserData updatedUserData) throws SQLException {
		/* 進DB前對密碼加密 */
		String realPassword = CipherMsg.encryptMsg(updatedUserData.getPassword());
		/* 成功 */
		if (!realPassword.startsWith("error!")) {
			updatedUserData.setPassword(realPassword);
			return webUserDAO.updateWebUserData(updatedUserData);
		} 
		/* 失敗 */
		return -1;
	}
	
	@Override
	public Integer resetWebUserPassword(String userId, String password) throws SQLException {
		/* 進DB前對密碼加密 */
		String realPassword = CipherMsg.encryptMsg(password);
		/* 成功 */
		if (!realPassword.startsWith("error!")) {
			return webUserDAO.resetWebUserPassword(userId, realPassword);
		} 
		/* 失敗 */
		return -1;
	}

	@Override
	public Integer adminChangeWebUserData(String userId, String status) throws SQLException {
		return webUserDAO.adminChangeWebUserData(userId, status);
	}

	@Override
	public Integer checkAdminAccess() throws SQLException {
		return webUserDAO.checkAdminAccess();
	}
	
	@Override
	public List<LocalDate> getAllWebUserJoinDate(String year) throws SQLException {
		return webUserDAO.getAllWebUserJoinDate(year);
	}
}
