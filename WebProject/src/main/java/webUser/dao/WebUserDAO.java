package webUser.dao;

import java.sql.SQLException;

import webUser.model.WebUserData;

public interface WebUserDAO {
	/* 檢查帳號是否存在 -1->異常、0->不存在、1->存在 */
	public Integer checkAccountExist(String inputAccount) throws SQLException;
	
	/* 檢查信箱是否已使用 -1->異常、0->不存在、1->存在 */
	public Integer checkEmailExist(String inputEmail) throws SQLException;
	
	/* 產生新增使用者所需的ID */
	public String createNewUserId(String selectedId, Integer lv) throws SQLException;
	
	/* 新增使用者資料 1->成功、0->失敗*/
	public Integer insertWebUserData(String insertId, WebUserData registerData) throws SQLException;
	
	/* 檢查密碼 -1->異常、0->錯誤、1->正確 */
	public Integer checkPassword(String inputAccount, String inputPassword) throws SQLException;
	
	/* 取得使用者個人資料 */
	public WebUserData getWebUserData(String inputAccount) throws SQLException;
	
	/* 棄用使用者帳戶 -1->異常、0->失敗、1->成功 */
	public Integer quitWebUserData(WebUserData quitUserData) throws SQLException;
	
	/* 檢查帳號是否為棄用 -1->異常、0->失敗、1->成功 */
	public Integer checkAccountQuit(String inputAccount) throws SQLException;
	
	/* 更新使用者資料 -1->異常、0->失敗、1->成功 */
	public Integer updateWebUserData(WebUserData updatedUserData) throws SQLException;
}
