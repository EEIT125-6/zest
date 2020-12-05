package webUser.service;

import java.sql.SQLException;

import webUser.model.WebUserData;

public interface WebUserService {
	/* 檢查帳號是否存在 -1->異常、0->不存在、1->存在 */
	public Integer checkAccountExist(String inputAccount) throws SQLException;
	
	/* 檢查信箱是否已使用 -1->異常、0->不存在、1->存在 */
	public Integer checkEmailExist(String inputEmail) throws SQLException;
	
	/* 執行資料新增 1->成功、0->失敗 */
	public Integer insertWebUserData(WebUserData registerData) throws SQLException;
	
	/* 執行登入檢查 -1->異常、0->失敗、1->成功 */
	public Integer checkWebUserLogin(String inputAccount, String inputPassword) throws SQLException;
	
	/* 取得使用者個人資料 */
	public WebUserData getWebUserData(String inputAccount) throws SQLException;
	
	/* 棄用使用者帳戶 -1->異常、0->失敗、1->成功 */
	public Integer quitWebUserData(WebUserData quitUserData) throws SQLException;
}
