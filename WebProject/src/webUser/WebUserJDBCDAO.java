package webUser;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class WebUserJDBCDAO implements WebUserDAO {
	/* 定義連線物件 */
	private Connection connection0;

	/* 建構子 */
	WebUserJDBCDAO(Connection pConnection) {
		this.connection0 = pConnection;
	}

	/*
	 * 檢查帳號是否存在 -1->異常、0->不存在、1->存在
	 */
	public int checkAccountExist(String inputAccount) throws SQLException {
		int checkResult = -1;
		int resultCount = 0;

		try (PreparedStatement preStmt0 = connection0
				.prepareStatement("SELECT account FROM dbo.WebUser WHERE account = ?")) {
			/* 開始交易 */
			connection0.setAutoCommit(false);
			/* 設定參數 */
			preStmt0.setString(1, inputAccount);
			if (preStmt0 != null) {
				/* 執行查詢 */
				ResultSet rs0 = preStmt0.executeQuery();
				if (rs0.next()) {
					resultCount++;
				}
				checkResult = (resultCount > 0) ? 1 : 0;
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
	
	/*
	 * 檢查信箱是否已使用 -1->異常、0->不存在、1->存在
	 */
	public int checkEmailExist(String inputEmail) throws SQLException {
		int checkResult = -1;
		int resultCount = 0;
		
		try (PreparedStatement preStmt0 = connection0
				.prepareStatement("SELECT email FROM dbo.WebUser WHERE email = ?")){
			/* 開始交易 */
			connection0.setAutoCommit(false);
			/* 設定參數 */
			preStmt0.setString(1, inputEmail);
			if (preStmt0 != null) {
				/* 執行查詢 */
				ResultSet rs0 = preStmt0.executeQuery();
				if (rs0.next()) {
					resultCount++;
				}
				checkResult = (resultCount > 0) ? 1 : 0;
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

	/* 檢查密碼，後面用,分隔將使用者資料傳回來 */
	public String checkPassword(String inputAccount, String inputPassword) throws SQLException {
		boolean checkResult = false;
		String totalResult = "";

		try (PreparedStatement preStmt0 = connection0
				.prepareStatement("SELECT * FROM dbo.WebUser WHERE account = ? AND password = ?")) {
			/* 開始交易 */
			connection0.setAutoCommit(false);
			/* 設定參數 */
			preStmt0.setString(1, inputAccount);
			preStmt0.setString(2, inputPassword);
			if (preStmt0 != null) {
				/* 執行查詢 */
				ResultSet rs0 = preStmt0.executeQuery();
				while (rs0.next()) {
					checkResult = true;
					totalResult = Boolean.toString(checkResult);
					/* 讀取使用者資訊 */
					totalResult += ":" + rs0.getString("user_id");
					totalResult += ":" + rs0.getString("account");
					totalResult += ":" + rs0.getString("password");
					totalResult += ":" + rs0.getString("first_name");
					totalResult += ":" + rs0.getString("last_name");
					totalResult += ":" + rs0.getString("nickname");
					totalResult += ":" + rs0.getString("gender");
					totalResult += ":" + rs0.getDate("birth").toString();
					totalResult += ":" + rs0.getString("fervor");
					totalResult += ":" + rs0.getString("email");
					totalResult += ":" + rs0.getString("phone");
					totalResult += ":" + rs0.getString("get_email");
					totalResult += ":" + rs0.getString("location_code");
					totalResult += ":" + rs0.getDate("join_date");
					totalResult += ":" + String.valueOf(rs0.getInt("lv"));
					totalResult += ":" + rs0.getString("addr0");
					totalResult += ":" + rs0.getString("addr1");
					totalResult += ":" + rs0.getString("addr2");
					totalResult += ":" + rs0.getBigDecimal("zest").toString();
					totalResult += ":" + String.valueOf(rs0.getInt("version"));
				}
				if (!checkResult) {
					totalResult = Boolean.toString(checkResult);
				}
				rs0.close();
				/* 確認交易 */
				connection0.commit();
			}
		} catch (SQLException sqlE) {
			System.out.println(sqlE);
			checkResult = false;
			totalResult = Boolean.toString(checkResult);
			/* 撤回交易 */
			connection0.rollback();
			throw new SQLException(sqlE);
		}

		return totalResult;
	}

	/* 檢查DB上符合特定條件的帳號筆數 */
	public int checkUserId(int lv) throws SQLException {
		int numberOfUser = 0;
		int resultTotalCounts = 0;

		try (PreparedStatement preStmt0 = connection0
				.prepareStatement("SELECT user_id FROM dbo.WebUser WHERE user_id LIKE ?")) {
			/* 開始交易 */
			connection0.setAutoCommit(false);
			/* 設定參數 */
			String pMode = String.valueOf(lv) + "%";
			preStmt0.setString(1, pMode);
			if (preStmt0 != null) {
				/* 執行查詢 */
				ResultSet rs0 = preStmt0.executeQuery();
				while (rs0.next()) {
					resultTotalCounts = Integer.parseInt(rs0.getString("user_id")) - lv * 1000000;
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

		try (PreparedStatement preStmt0 = connection0
				.prepareStatement("INSERT INTO dbo.WebUser VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
			/* 開始交易 */
			connection0.setAutoCommit(false);
			/* 設定參數 */
			preStmt0.setString(1, insertedData.getUser_id());
			preStmt0.setString(2, insertedData.getAccount());
			preStmt0.setString(3, insertedData.getPassword());
			preStmt0.setString(4, insertedData.getFirst_name());
			preStmt0.setString(5, insertedData.getLast_name());
			preStmt0.setString(6, insertedData.getNickname());
			preStmt0.setString(7, insertedData.getGender());
			preStmt0.setDate(8, Date.valueOf(insertedData.getBirth()));
			preStmt0.setString(9, insertedData.getFervor());
			preStmt0.setString(10, insertedData.getEmail());
			preStmt0.setString(11, insertedData.getPhone());
			preStmt0.setString(12, insertedData.getGet_email());
			preStmt0.setString(13, insertedData.getLocation_code());
			preStmt0.setDate(14, Date.valueOf(insertedData.getJoin_date()));
			preStmt0.setInt(15, insertedData.getLv());
			preStmt0.setString(16, insertedData.getAddr0());
			preStmt0.setString(17, insertedData.getAddr1());
			preStmt0.setString(18, insertedData.getAddr2());
			preStmt0.setBigDecimal(19, insertedData.getZest());
			preStmt0.setInt(20, insertedData.getVersion());

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
	
	/* 修改密碼 */
	public boolean updateWebUserPassword(String user_id, String newPassword, int version) throws SQLException {
		boolean updatePasswordResult = false;
		int newVersion = version + 1;
		
		try (PreparedStatement preStmt0 = connection0.prepareStatement("UPDATE dbo.WebUser SET password = ?, version = ? WHERE user_id = ? AND version = ?")) {
			/* 開始交易 */
			connection0.setAutoCommit(false);
			/* 設定參數 */
			preStmt0.setString(1, newPassword);
			preStmt0.setInt(2, newVersion);
			preStmt0.setString(3, user_id);
			preStmt0.setInt(4, version);
			
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
			updatePasswordResult = true;
		} catch (SQLException sqlE) {
			System.out.println(sqlE);
			updatePasswordResult = false;
			/* 撤回交易 */
			connection0.rollback();
			throw new SQLException(sqlE);
		}
		
		return updatePasswordResult;
	}
	
	/* 修改其他資料 */
	public boolean updateWebUserData(String updatedParameters) throws SQLException {
		boolean updateDataResult = false;
		
		/* 還原參數 */
		String updatedUser_id = updatedParameters.split(":")[0];
		String updatedFirst_name = (updatedParameters.split(":")[1].equals("?")) ? "" : updatedParameters.split(":")[1];
		String updatedLast_name = (updatedParameters.split(":")[2].equals("?")) ? "" : updatedParameters.split(":")[2];
		String updatedNickname = (updatedParameters.split(":")[3].equals("?")) ? "" : updatedParameters.split(":")[3];
		String updatedFervor = (updatedParameters.split(":")[4].equals("?")) ? "" : updatedParameters.split(":")[4];
		String updatedEmail = (updatedParameters.split(":")[5].equals("?")) ? "" : updatedParameters.split(":")[5];
		String updatedPhone = (updatedParameters.split(":")[6].equals("?")) ? "" : updatedParameters.split(":")[6];
		String updatedGet_email = (updatedParameters.split(":")[7].equals("?")) ? "" : updatedParameters.split(":")[7];
		String updatedLocation_code = (updatedParameters.split(":")[8].equals("?")) ? "" : updatedParameters.split(":")[8];
		String updatedAddr0 = (updatedParameters.split(":")[9].equals("?")) ? "" : updatedParameters.split(":")[9];
		String updatedAddr1 = (updatedParameters.split(":")[10].equals("?")) ? "" : updatedParameters.split(":")[10];
		String updatedAddr2 = (updatedParameters.split(":")[11].equals("?")) ? "" : updatedParameters.split(":")[11];
		int version = Integer.parseInt(updatedParameters.split(":")[12]);
		int newVersion = version + 1;
		
		/* 準備設定statement */
		StringBuilder sb0 = new StringBuilder();
		/* 判別參數 */
		if (!updatedFirst_name.equals("")) {
			sb0.append("UPDATE dbo.WebUser SET first_name = '" + updatedFirst_name + "'");
		}
		if (!updatedLast_name.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("UPDATE dbo.WebUser SET last_name = '" + updatedLast_name + "'");
			} else {
				sb0.append(", last_name = '" + updatedLast_name + "'");
			}
		}
		if (!updatedNickname.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("UPDATE dbo.WebUser SET nickname = '" + updatedNickname + "'");
			} else {
				sb0.append(", nickname = '" + updatedNickname + "'");
			}
		}
		if (!updatedFervor.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("UPDATE dbo.WebUser SET fervor = '" + updatedFervor + "'");
			} else {
				sb0.append(", fervor = '" + updatedFervor + "'");
			}
		}
		if (!updatedEmail.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("UPDATE dbo.WebUser SET email = '" + updatedEmail + "'");
			} else {
				sb0.append(", email = '" + updatedEmail + "'");
			}
		}
		if (!updatedPhone.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("UPDATE dbo.WebUser SET phone = '" + updatedPhone + "'");
			} else {
				sb0.append(", phone = '" + updatedPhone + "'");
			}
		}
		if (!updatedGet_email.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("UPDATE dbo.WebUser SET get_email = '" + updatedGet_email + "'");
			} else {
				sb0.append(", get_email = '" + updatedGet_email + "'");
			}
		}
		if (!updatedLocation_code.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("UPDATE dbo.WebUser SET location_code = '" + updatedLocation_code + "'");
			} else {
				sb0.append(", location_code = '" + updatedLocation_code + "'");
			}
		}
		if (!updatedAddr0.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("UPDATE dbo.WebUser SET addr0 = " + updatedAddr0);
			} else {
				sb0.append(", addr0 = " + updatedAddr0);
			}
		}
		if (!updatedAddr1.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("UPDATE dbo.WebUser SET addr1 = '" + updatedAddr1 + "'");
			} else {
				sb0.append(", addr1 = '" + updatedAddr1 + "'");
			}
		}
		if (!updatedAddr2.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("UPDATE dbo.WebUser SET addr2 = '" + updatedAddr2 + "'");
			} else {
				sb0.append(", addr2 = '" + updatedAddr2 + "'");
			}
		}
		/* 收尾 */
		if (!sb0.toString().equals("")) {
			sb0.append(", version = " + newVersion + " WHERE user_id = '" + updatedUser_id + "'");
			
			System.out.println("SQL Comand is :" + System.lineSeparator() + sb0.toString());
			
			try (Statement stmt0 = connection0.createStatement()){
				/* 開始交易 */
				connection0.setAutoCommit(false);
				int updateCount = stmt0.executeUpdate(sb0.toString());
				/* 執行成功 */
				if (updateCount > 0) {
					updateDataResult = true;
				/* 執行失敗 */
				} else {
					updateDataResult = false;
				}
				/* 確認交易 */
				connection0.commit();
			} catch (SQLException sqlE) {
				System.out.println(sqlE);
				updateDataResult = false;
				/* 撤回交易 */
				connection0.rollback();
				throw new SQLException(sqlE);
			}
		} else {
			throw new SQLException("所有參數不可皆為空值");
		}
		
		return updateDataResult;
	}

	/* 刪除資料 */
	public boolean deleteWebUser(WebUserBean deletedData) throws SQLException {
		boolean deleteResult = false;

		try (PreparedStatement preStmt0 = connection0.prepareStatement("DELETE dbo.WebUser WHERE user_id = ?")) {
			/* 開始交易 */
			connection0.setAutoCommit(false);
			/* 設定參數 */
			preStmt0.setString(1, deletedData.getUser_id());
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
			deleteResult = true;
		} catch (SQLException sqlE) {
			System.out.println(sqlE);
			deleteResult = false;
			/* 撤回交易 */
			connection0.rollback();
			throw new SQLException(sqlE);
		}

		return deleteResult;
	}

	/* 查詢資料 */
	public List<WebUserBean> selectWebUser(String selectedParameters) throws SQLException {
		/* 回傳參數 */
		List<WebUserBean> selectedResult = new ArrayList<>();

		/* 還原參數 */
		String selectedAccount = (selectedParameters.split(":")[0].equals("?")) ? "" : selectedParameters.split(":")[0];
		String selectedNickname = (selectedParameters.split(":")[1].equals("?")) ? ""
				: selectedParameters.split(":")[1];
		String selectedFervor = (selectedParameters.split(":")[2].equals("?")) ? "" : selectedParameters.split(":")[2];
		String selectedLocation_code = (selectedParameters.split(":")[3].equals("?")) ? ""
				: selectedParameters.split(":")[3];
		
		/* 準備設定statement */
		StringBuilder sb0 = new StringBuilder();
		/* 判別參數 */
		if (!selectedAccount.equals("")) {
			sb0.append("SELECT * FROM dbo.WebUser WHERE account LIKE " + "'%" + selectedAccount + "%'");
		}
		if (!selectedNickname.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("SELECT * FROM dbo.WebUser WHERE nickname LIKE " + "'%" + selectedNickname + "%'");
			} else {
				sb0.append(" AND nickname LIKE " + "'%" + selectedNickname + "%'");
			}
		}
		if (!selectedFervor.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("SELECT * FROM dbo.WebUser WHERE fervor LIKE " + "'%" + selectedFervor + "%'");
			} else {
				sb0.append(" AND fervor LIKE " + "'%" + selectedFervor + "%'");
			}
		}
		if (!selectedLocation_code.equals("")) {
			if (sb0.toString().equals("")) {
				sb0.append("SELECT * FROM dbo.WebUser WHERE location_code = '" + selectedLocation_code + "'");
			} else {
				sb0.append(" AND location_code = '" + selectedLocation_code + "'");
			}
		}
		if (!sb0.toString().equals("")) {
			try (Statement stmt0 = connection0.createStatement()) {
				/* 開始交易 */
				connection0.setAutoCommit(false);
				ResultSet rs0 = stmt0.executeQuery(sb0.toString());
				/* 如果有值 */
				while (rs0.next()) {
					WebUserBean tmpData = new WebUserBean();
					tmpData.setUser_id(rs0.getString("user_id"));
					tmpData.setAccount(rs0.getString("account"));
					tmpData.setPassword(rs0.getString("password"));
					tmpData.setFirst_name(rs0.getString("first_name"));
					tmpData.setLast_name(rs0.getString("last_name"));
					tmpData.setNickname(rs0.getString("nickname"));
					tmpData.setGender(rs0.getString("gender"));
					tmpData.setBirth(LocalDate.parse(rs0.getDate("birth").toString()));
					tmpData.setFervor(rs0.getString("fervor"));
					tmpData.setEmail(rs0.getString("email"));
					tmpData.setPhone(rs0.getString("phone"));
					tmpData.setGet_email(rs0.getString("get_email"));
					tmpData.setLocation_code(rs0.getString("location_code"));
					tmpData.setJoin_date(LocalDate.parse(rs0.getDate("join_date").toString()));
					tmpData.setLv(rs0.getInt("lv"));
					tmpData.setAddr0(rs0.getString("addr0"));
					tmpData.setAddr1(rs0.getString("addr1"));
					tmpData.setAddr2(rs0.getString("addr2"));
					tmpData.setZest(rs0.getBigDecimal("zest"));
					tmpData.setVersion(rs0.getInt("version"));
					selectedResult.add(tmpData);
				}
				/* 確認交易 */
				connection0.commit();
			} catch (SQLException sqlE) {
				System.out.println(sqlE);
				/* 撤回交易 */
				connection0.rollback();
				throw new SQLException(sqlE);
			}
		} else {
			throw new SQLException("所有參數不可皆為空值");
		}

		return selectedResult;
	}

	/* 查詢使用者自身資料 */
	public WebUserBean selectWebUserSelf(String selfUser_id) throws SQLException {
		WebUserBean selfData = new WebUserBean();

		try (PreparedStatement preStmt0 = connection0.prepareStatement("SELECT * FROM dbo.WebUser WHERE user_id = ?")) {
			/* 開始交易 */
			connection0.setAutoCommit(false);
			/* 設定參數 */
			preStmt0.setString(1, selfUser_id);
			if (preStmt0 != null) {
				/* 執行查詢 */
				ResultSet rs0 = preStmt0.executeQuery();
				if (rs0.next()) {
					selfData.setUser_id(rs0.getString("user_id"));
					selfData.setAccount(rs0.getString("account"));
					selfData.setPassword(rs0.getString("password"));
					selfData.setFirst_name(rs0.getString("first_name"));
					selfData.setLast_name(rs0.getString("last_name"));
					selfData.setNickname(rs0.getString("nickname"));
					selfData.setGender(rs0.getString("gender"));
					selfData.setBirth(LocalDate.parse(rs0.getDate("birth").toString()));
					selfData.setFervor(rs0.getString("fervor"));
					selfData.setEmail(rs0.getString("email"));
					selfData.setPhone(rs0.getString("phone"));
					selfData.setGet_email(rs0.getString("get_email"));
					selfData.setLocation_code(rs0.getString("location_code"));
					selfData.setJoin_date(LocalDate.parse(rs0.getDate("join_date").toString()));
					selfData.setLv(rs0.getInt("lv"));
					selfData.setAddr0(rs0.getString("addr0"));
					selfData.setAddr1(rs0.getString("addr1"));
					selfData.setAddr2(rs0.getString("addr2"));
					selfData.setZest(rs0.getBigDecimal("zest"));
					selfData.setVersion(rs0.getInt("version"));
				}
			}
			/* 確認交易 */
			connection0.commit();
		} catch (SQLException sqlE) {
			System.out.println(sqlE);
			/* 撤回交易 */
			connection0.rollback();
			throw new SQLException(sqlE);
		}
		
		return selfData;
	}

	/* 新增資料至刪除表 */
	public boolean insertWebUserDeleted(WebUserBean insertedData) throws SQLException {
		boolean insertResult = false;

		try (PreparedStatement preStmt0 = connection0
				.prepareStatement("INSERT INTO dbo.WebUserDeleted VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
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
			preStmt0.setDate(8, Date.valueOf(insertedData.getBirth()));
			preStmt0.setString(9, insertedData.getFervor());
			preStmt0.setString(10, insertedData.getEmail());
			preStmt0.setString(11, insertedData.getPhone());
			preStmt0.setString(12, insertedData.getGet_email().toString());
			preStmt0.setString(13, insertedData.getLocation_code());
			preStmt0.setDate(14, Date.valueOf(insertedData.getJoin_date()));
			preStmt0.setInt(15, insertedData.getLv());
			preStmt0.setString(16, insertedData.getAddr0());
			preStmt0.setString(17, insertedData.getAddr1());
			preStmt0.setString(18, insertedData.getAddr2());
			preStmt0.setBigDecimal(19, insertedData.getZest());
			preStmt0.setInt(20, insertedData.getVersion());

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

	/* 查詢刪除表的資料 */
	public boolean selectWebUserDeleted(WebUserBean selectedData) throws SQLException {
		boolean checkResult = false;
		int resultCount = 0;

		try (PreparedStatement preStmt0 = connection0
				.prepareStatement("SELECT user_id FROM dbo.WebUserDeleted WHERE user_id = ?")) {
			/* 開始交易 */
			connection0.setAutoCommit(false);
			/* 設定參數 */
			preStmt0.setString(1, selectedData.getUser_id());
			if (preStmt0 != null) {
				/* 執行查詢 */
				ResultSet rs0 = preStmt0.executeQuery();
				if (rs0.next()) {
					resultCount++;
				}
				checkResult = (resultCount > 0) ? true : false;
				rs0.close();
				/* 確認交易 */
				connection0.commit();
			}
		} catch (SQLException sqlE) {
			System.out.println(sqlE);
			checkResult = false;
			/* 撤回交易 */
			connection0.rollback();
			throw new SQLException(sqlE);
		}

		return checkResult;
	}
}