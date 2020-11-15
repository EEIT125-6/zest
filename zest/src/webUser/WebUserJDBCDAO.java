package webUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WebUserJDBCDAO implements WebUserDAO{
	/* 定義連線物件 */
	private Connection connection0;
	
	/* 建構子 */
	WebUserJDBCDAO(Connection pConnection) {
		this.connection0 = pConnection;
	}
	
	/* 檢查帳號是否存在 */
	public Boolean checkAccountExist(String inputAccount) {
		Boolean checkResult = false;
		
		try (PreparedStatement preStmt0 = connection0.prepareStatement("SELECT account FROM dbo.WebUser WHERE account = ?")) {
			/* 開始交易 */ 
			connection0.setAutoCommit(false);
			/* 設定參數 */
			String pAccount = "\'" + inputAccount + "\'";
			preStmt0.setString(1, pAccount);
			if (preStmt0 != null) {
				/* 執行查詢 */
	            ResultSet rs0 = preStmt0.executeQuery();
	            while (rs0.next()) {
	            	checkResult = (!rs0.getString("account").equals("")) ? true : false;
	            }
	            rs0.close();
	            /* 確認交易 */
	            connection0.commit();
            }
		} catch (SQLException sqlE) {
			System.out.println(sqlE);
			try {
				/* 撤回交易 */
				connection0.rollback();
			} catch (SQLException sqlE1) {
				System.out.println(sqlE1);
			}
		}
		
		return checkResult;
	}
}
