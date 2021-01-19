package webUser.repository;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Repository;

import webUser.model.WebUserData;

@Repository
public interface WebUserRepository {
	/* 檢查Id是否存在 -1->異常、0->不存在、1->存在 */
	public Integer checkUserIdExist(String inputUserId) throws SQLException;
	
	/* 檢查Id是否為棄用 -1->異常、0->失敗、1->成功 */
	public Integer checkUserIdQuit(String inputUserId) throws SQLException;
	
	/* 檢查帳號是否存在 -1->異常、0->不存在、1->存在 */
	public Integer checkAccountExist(String inputAccount) throws SQLException;
	
	/* 檢查帳號是否為棄用 -1->異常、0->失敗、1->成功 */
	public Integer checkAccountQuit(String inputAccount) throws SQLException;
	
	/* 檢查稱呼是否已使用 -1->異常、0->未使用、1->使用 */
	public Integer checkNicknameExist(String inputNickname) throws SQLException;
	
	/* 檢查信箱是否已使用 -1->異常、0->不存在、1->存在 */
	public Integer checkEmailExist(String inputEmail) throws SQLException;
	
	/* 檢查電話是否已使用 -1->異常、0->不存在、1->存在 */
	public Integer checkPhoneExist(String inputPhone) throws SQLException;
	
	/* 檢查密碼 -1->異常、0->錯誤、1->正確 */
	public Integer checkPassword(String inputAccount, String inputPassword) throws SQLException;
	
	/* 檢查簽到 -1->異常、0->錯誤、1->正確 */
	public Integer checkWebUserSignIn(String inputUserId, Date today) throws SQLException;
	
	/* 執行簽到 -1->異常、0->錯誤、1->正確 */
	public Integer runWebUserSignIn(WebUserData userData) throws SQLException;
	
	/* 驗證使用者資料 */
	public WebUserData checkRecoveryInfo(String email, String phone, Date birth) throws SQLException;
	
	/* 驗證使用者資料 */
	public WebUserData checkRecoveryInfo(String account, String email, String phone, Date birth) throws SQLException;
	
	/* 驗證使用者資料 */
	public WebUserData checkRecoveryInfo(String account, String password, String email, String phone, Date birth) throws SQLException;
	
	/* 驗證使用者資料 */
	public WebUserData checkRecoveryInfoAnother(String password, String email, String phone, Date birth) throws SQLException;
	
	/* 檢查密碼 -1->異常、0->錯誤、1->正確 */
	public Integer checkResetPassword(String inputUserId, String inputPassword) throws SQLException;
	
	/* 產生新增使用者所需的ID */
	public String createNewUserId(String selectedId, Integer lv) throws SQLException;
	
	/* 新增使用者資料 1->成功、0->失敗*/
	public Integer insertWebUserData(WebUserData registerData) throws SQLException;
	
	/* 取得使用者個人資料 */
	public WebUserData getWebUserData(String inputAccount) throws SQLException;
	
	/* 取得使用者個人資料 */
	public WebUserData getWebUserDataById(String userId) throws SQLException;
	
	/* 取得查詢的使用者資料 */
	public List<WebUserData> getSelectedWebUserData(String selectedParameters, Integer avPage, Integer startPage) throws SQLException;
	
<<<<<<< HEAD
	/* 取得所有有效的使用者資料 */
	public List<WebUserData> getAllWebUserData() throws SQLException;
	
	/* 取得所有特定年份註冊的使用者資料 */
	public List<WebUserData> getAllYearWebUserData(String year) throws SQLException;
	
=======
>>>>>>> testMaster
	/* 取得查詢到的總筆數 */
	public Long getUserRecordCounts(String selectedParameters) throws SQLException;
	
	/* 取得查詢的最大頁數 */
	public Integer getTotalUserRecordCounts(String selectedParameters, Integer avPage) throws SQLException;
	
	/* 更新使用者資料 -1->異常、0->失敗、1->成功 */
	public Integer updateWebUserData(WebUserData updatedUserData) throws SQLException;
	
	/* 變更使用者帳戶狀態 -1->異常、0->失敗、1->成功 */
	public Integer adminChangeWebUserData(String userId, String status) throws SQLException;
	
	/* 重設使用者密碼 -1->異常、0->失敗、1->成功 */
	public Integer resetWebUserPassword(String userId, String password) throws SQLException;
	
	/* 檢查有多少可登入的管理員帳號(回傳整數或0) */
	public Integer checkAdminAccess() throws SQLException;
<<<<<<< HEAD
	
	/* 取回所有可用使用者的註冊日期 */
	public List<LocalDate> getAllWebUserJoinDate(String year) throws SQLException;
=======
>>>>>>> testMaster
}