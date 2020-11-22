package webUser;

import java.sql.SQLException;
import java.util.List;

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
	
	/* 修改密碼 */
	public boolean updateWebUserPassword(String user_id, String newPassword) throws SQLException;
	
	/* 修改其他資料 */
	public boolean updateWebUserData(String updatedParameters) throws SQLException;
	
	/* 刪除資料 */
	public boolean deleteWebUser(WebUserBean deletedData) throws SQLException;
	
	/* 查詢資料 */
	public List<WebUserBean> selectWebUser(String selectedParameters) throws SQLException;
	
	/* 查詢使用者自身資料 */
	public WebUserBean selectWebUserSelf(String selfUser_id) throws SQLException;
	
	/* 新增資料至刪除表 */
	public boolean insertWebUserDeleted(WebUserBean insertedData) throws SQLException;
	
	/* 查詢刪除表的資料 */
	public boolean selectWebUserDeleted(WebUserBean selectedData) throws SQLException;
}
