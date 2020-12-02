package webUser.dao;

import java.sql.SQLException;

import webUser.model.WebUserData;

public interface WebUserDAO {
	/* 檢查帳號是否存在 -1->異常、0->不存在、1->存在 */
	public int checkAccountExist(String inputAccount) throws SQLException;
	
	/* 檢查信箱是否已使用 -1->異常、0->不存在、1->存在 */
	public int checkEmailExist(String inputEmail) throws SQLException;
	
	/* 產生新增使用者所需的ID */
	public String createNewUserId(String selectedId, int lv) throws SQLException;
	
	/* 新增使用者資料 1->成功、0->失敗*/
	public int insertWebUserData(String insertId, WebUserData registerData) throws SQLException;
	
	/* 檢查密碼 -1->異常、0->錯誤、1->正確 */
	public int checkPassword(String inputAccount, String inputPassword) throws SQLException;
	
	/* 取得使用者個人資料 */
	public WebUserData getWebUserData(String inputAccount) throws SQLException;
}
