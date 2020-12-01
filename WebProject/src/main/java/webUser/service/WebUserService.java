package webUser.service;

import java.sql.SQLException;

import webUser.model.WebUserData;

public interface WebUserService {
	/*
	 *  檢查帳號是否存在 -1->異常、0->不存在、1->存在
	 */
	public int checkAccountExist(String inputAccount) throws SQLException;
	
	/*
	 * 檢查信箱是否已使用 -1->異常、0->不存在、1->存在
	 */
	public int checkEmailExist(String inputEmail) throws SQLException;
	
	/* 執行資料新增 1->成功、0->失敗 */
	public int insertWebUserData(WebUserData registerData) throws SQLException;
}
