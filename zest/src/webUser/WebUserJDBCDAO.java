package webUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class WebUserJDBCDAO implements WebUserDAO{
	/* 定義連線物件 */
	private Connection connection0;
	
	/* 建構子 */
	WebUserJDBCDAO(Connection pConnection) {
		this.connection0 = pConnection;
	}
	
	/* 檢查帳號是否存在
	 * -1->異常、0->不存在、1->存在 */
	public int checkAccountExist(String inputAccount) throws SQLException {
		int checkResult = -1;
		
		try (PreparedStatement preStmt0 = connection0.prepareStatement("SELECT account FROM dbo.WebUser WHERE account = ?")) {
			/* 開始交易 */ 
			connection0.setAutoCommit(false);
			/* 設定參數 */
			String pAccount = "\'" + inputAccount + "\'";
			preStmt0.setString(1, pAccount);
			if (preStmt0 != null) {
				/* 執行查詢 */
	            ResultSet rs0 = preStmt0.executeQuery();
	            checkResult = (rs0.getRow() > 0) ? 1 : 0;
	            rs0.close();
	            /* 確認交易 */
	            connection0.commit();
            }
		} catch (SQLException sqlE) {
			System.out.println(sqlE);
			checkResult = -1;
			/* 撤回交易 */
			connection0.rollback();
			throw new SQLException(sqlE);
		}
		
		return checkResult;
	}
	
	/* 檢查DB上符合特定條件的帳號筆數 */
	public int checkUserId(int lv) throws SQLException {
		int numberOfUser = 0;
		
		try (PreparedStatement preStmt0 = connection0.prepareStatement("SELECT user_id FROM dbo.WebUser WHERE user_id LIKE ?")) {
			/* 開始交易 */ 
			connection0.setAutoCommit(false);
			/* 設定參數 */
			String pMode = "\'"+String.valueOf(lv) + "%\'";
			int resultTotalCounts = 0;
			preStmt0.setString(1, pMode);
			if (preStmt0 != null) {
				/* 執行查詢 */
	            ResultSet rs0 = preStmt0.executeQuery();
	            if (rs0.next()) {
	            	resultTotalCounts++;
	            }
	            numberOfUser = (resultTotalCounts > 0) ? resultTotalCounts : 0;
	            rs0.close();
	            /* 確認交易 */
	            connection0.commit();
            }
		} catch (SQLException sqlE) {
			System.out.println(sqlE);
			numberOfUser = -1;
			/* 撤回交易 */
			connection0.rollback();
			throw new SQLException(sqlE);
		}
		
		return numberOfUser;
	}
	
	/* 新增資料 */
	public boolean insertWebUser(WebUserBean insertedData) throws SQLException {
		boolean insertResult = false;
		
		try (PreparedStatement preStmt0 = 
				connection0.prepareStatement("INSERT INTO dbo.WebUser VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
			/* 開始交易 */ 
			connection0.setAutoCommit(false);
			/* 設定參數 */
			preStmt0.setString(1, insertedData.getUser_id());
			preStmt0.setString(2, insertedData.getAccount());
			preStmt0.setString(3, insertedData.getPassword());
			preStmt0.setString(4, insertedData.getFirst_name());
			preStmt0.setString(5, insertedData.getLast_name());
			preStmt0.setString(6, insertedData.getNickname());
			preStmt0.setString(7, insertedData.getGender().toString());
			preStmt0.setDate(8, Date.valueOf(insertedData.getBirthday()));
			preStmt0.setString(9, insertedData.getFervor());
			preStmt0.setString(10, insertedData.getEmail());
			preStmt0.setString(11, insertedData.getGet_email().toString());
			preStmt0.setString(12, insertedData.getLocation_code());
			preStmt0.setDate(13, Date.valueOf(insertedData.getJoin_date()));
			preStmt0.setInt(14, insertedData.getLv());
			preStmt0.setString(15, insertedData.getAddr0());
			preStmt0.setString(16, insertedData.getAddr1());
			preStmt0.setString(17, insertedData.getAddr2());
			preStmt0.setBigDecimal(18, insertedData.getZest());
			
			 /* 加入批次 */
            preStmt0.addBatch();
            /* 清空變數 */
            preStmt0.clearParameters();
         // 執行executeBatch
            preStmt0.executeBatch();
            /* 清空batch */
            preStmt0.clearBatch();
			
            /* 確認交易 */
            connection0.commit();
            insertResult = true;
		} catch (SQLException sqlE) {
			System.out.println(sqlE);
			insertResult = false;
			/* 撤回交易 */
			connection0.rollback();
			throw new SQLException(sqlE);
		}
		
		return insertResult;
	}
}
