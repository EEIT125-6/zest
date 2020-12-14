package webUser.service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import webUser.model.WebUserData;

public interface WebUserService {
	/* 檢查Id是否存在 -1->異常、0->不存在、1->存在 */
	public Integer checkUserIdExist(String inputUserId) throws SQLException;
	
	/* 檢查Id是否為棄用 -1->異常、0->失敗、1->成功 */
	public Integer checkUserIdQuit(String inputUserId) throws SQLException;
	
	/* 檢查帳號是否存在 -1->異常、0->不存在、1->存在 */
	public Integer checkAccountExist(String inputAccount) throws SQLException;
	
	/* 檢查稱呼是否已使用 -1->異常、0->未使用、1->使用 */
	public Integer checkNicknameExist(String inputNickname) throws SQLException;
	
	/* 檢查信箱是否已使用 -1->異常、0->不存在、1->存在 */
	public Integer checkEmailExist(String inputEmail) throws SQLException;
	
	/* 檢查電話是否已使用 -1->異常、0->不存在、1->存在 */
	public Integer checkPhoneExist(String inputPhone) throws SQLException;
	
	/* 執行資料新增 1->成功、0->失敗 */
	public Integer insertWebUserData(WebUserData registerData) throws SQLException;
	
	/* 執行登入檢查 -1->異常、0->失敗、1->成功 */
	public Integer checkWebUserLogin(String inputAccount, String inputPassword) throws SQLException;
	
	/* 取得使用者個人資料 */
	public WebUserData getWebUserData(String inputAccount) throws SQLException;
	
	/* 取得查詢的使用者資料 */
	public List<WebUserData> getOtherWebUserData(String selectedParameters) throws SQLException;
	
	/* 取得所有使用者資料 */
	public List<WebUserData> getAllWebUserData(Integer lv, String status) throws SQLException;
	
	/* 棄用使用者帳戶 -1->異常、0->失敗、1->成功 */
	public Integer quitWebUserData(WebUserData quitUserData) throws SQLException;
	
	/* 變更使用者帳戶狀態 -1->異常、0->失敗、1->成功 */
	public Integer adminChangeWebUserData(String userId, String status) throws SQLException;
	
	/* 更新使用者資料 -1->異常、0->失敗、1->成功 */
	public Integer updateWebUserData(WebUserData updatedUserData) throws SQLException;
	
	/* 更新使用者密碼 -1->異常、0->失敗、1->成功 */
	public Integer updateWebUserPassword(WebUserData updatedUserData) throws SQLException;
	
	/* 刪除使用者帳戶 -1->異常、0->失敗、1->成功 */
	public Integer deleteWebUserData(String deletedUserId) throws SQLException;
	
	/* 驗證使用者資料 */
	public WebUserData checkRecoveryInfo(String account, String password, String email, String phone, Date birth) throws SQLException;
	
	/* 執行重設 -1->異常、0->失敗、1->成功 */
	public Integer resetWebUserPassword(String userId, String password) throws SQLException;
}
