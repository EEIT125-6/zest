package webUser;

import java.sql.SQLException;

public interface WebUserDAO {
	
	/* 檢查帳號是否存在
	 * -1->異常、0->不存在、1->存在 */
	public int checkAccountExist(String inputAccount) throws SQLException;
	
	/* 檢查密碼，後面用,分隔將使用者資料傳回來 */
	public String checkPassword(String inputAccount, String inputPassword) throws SQLException;
	
	/* 檢查DB上符合特定條件的帳號筆數 */
	public int checkUserId(int lv) throws SQLException;
	
	/* 新增資料 */
	public boolean insertWebUser(WebUserBean insertedData) throws SQLException;
}
