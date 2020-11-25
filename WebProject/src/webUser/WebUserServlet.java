package webUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/WebUserServlet")
public class WebUserServlet extends HttpServlet {

	/* IDE能自動協助生成的項目，並不會使用到 */
	private static final long serialVersionUID = 1L;

	/* 預先宣告response/request設定所需的部分 */
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String CHARSET_CODE = "UTF-8";

	/* DataSource等項目初始化 */
	private DataSource ds0 = null;
	private InitialContext ctxt0 = null;
	private Connection conn0 = null;

	/* DAO物件 */
	WebUserDAO webUserDAO;

	public WebUserServlet() {
		super();
	}

	/* 初始化 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		try {
			// 建立Context Object,連到JNDI Server
			ctxt0 = new InitialContext();
			// 使用JNDI
			// API找到DataSource，最後面的名稱請跟web.xml上的<res-ref-name>以及context.xml上的<Resource
			// name>一致
			ds0 = (DataSource) ctxt0.lookup("java:comp/env/jdbc/zest");
			// 向DataSource要Connection
			conn0 = ds0.getConnection();

		} catch (NamingException nE) {
			System.out.println("Naming Service Lookup Exception");
		} catch (SQLException sqlE) {
			System.out.println("Database Connection Error");
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);

		/* 防止快取設定 */
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", -1); // 防止proxy server進行快取

		/* 根據取到的參數判定是首次送出資料還是已確認過 */
		if (request.getParameter("register") != null) {
			switch (request.getParameter("register")) {
			case "檢查帳號":
				/* 返回查詢結果給使用者確認 */
				doCheckAccount(request, response);
				break;
			case "檢查信箱":
				/* 返回查詢結果給使用者確認 */
				doCheckEmail(request, response);
				break;
			case "送出":
				/* 返回資料給使用者確認 */
				doRegisterSubmit(request, response);
				break;
			case "確認":
				/* 準備將資料傳入DB */
				doRegisterConfirm(request, response);
				break;
			/* 返回註冊畫面 */
			case "取消":
			default:
				/* 清除資料並返回註冊畫面 */
				doRegisterUndo(request, response);
				break;
			}
			/* 登入模塊 */
		} else if (request.getParameter("login") != null) {
			switch (request.getParameter("login")) {
			/* 刪除 */
			case "刪除帳戶":
				/* 執行刪除 */
				doDeleteAccount(request, response);
				break;
			/* 登出 */
			case "登出帳戶":
				/* 執行登出 */
				doLogout(request, response);
				break;
			/* 登入 */
			case "登入":
				/* 返回查詢結果 */
				doCheckLogin(request, response);
				break;
			/* 預設 */
			default:
				/* 返回登入畫面 */
				doBackToLoginPage(request, response);
				break;
			}
			/* 搜尋模塊 */
		} else if (request.getParameter("select") != null) {
			switch (request.getParameter("select")) {
			/* 查詢 */
			case "執行查詢":
				/* 執行特定查詢 */
				doSelectUserData(request, response);
				break;
			case "檢視/修改個人資料":
				/* 查詢個人資料 */
				doSelectSelfData(request, response);
				break;
			default:
				/* 返回主畫面 */
				doBackToLoginMainPage(request, response);
				break;
			}
		/* 修改模塊 */
		} else if (request.getParameter("update") != null) {
			switch(request.getParameter("update")) {
				case "修改密碼":
					/* 導向修改密碼 */
					doGoToModifyPassword(request, response);
					break;
				case "修改其他資料":
					/* 導向修改畫面 */
					doGoToModifyPage(request, response);
					break;
				case "密碼修改完畢":
					/* 準備執行密碼更新 */
					doUpdatePassword(request, response);
					break;
				case "資料修改完畢":
					/* 準備執行其他個人資料更新 */
					doUpdateData(request, response);
					break;
				default:
					/* 返回主畫面 */
					doBackToLoginMainPage(request, response);
					break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	/* Register checkAccount */
	public void doCheckAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);

		/* 宣告欲回傳的參數 */
		int accountCheckResult = -1;
		String message = "";
		/* 宣告printer */
		PrintWriter out = response.getWriter();
		/* 取得使用者輸入的參數 */
		String inputAccount = request.getParameter("inputAccount");
		try {
			/* 利用Connection產生DAO物件 */
			webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);
			/* 呼叫DAO方法，返回結果 */
			accountCheckResult = webUserDAO.checkAccountExist(inputAccount);
			message = "Success";
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}

		/* 將結果返回aJax */
		out.write(String.valueOf(accountCheckResult));
		out.write("," + message);
		out.flush();
		out.close();
	}
	
	/* Register checkEmail */
	public void doCheckEmail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 宣告欲回傳的參數 */
		int emailCheckResult = -1;
		String message = "";
		/* 宣告printer */
		PrintWriter out = response.getWriter();
		/* 取得使用者輸入的參數 */
		String inputEmail = request.getParameter("inputEmail");
		try {
			/* 利用Connection產生DAO物件 */
			webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);
			/* 呼叫DAO方法，返回結果 */
			emailCheckResult = webUserDAO.checkEmailExist(inputEmail);
			message = "Success";
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}

		/* 將結果返回aJax */
		out.write(String.valueOf(emailCheckResult));
		out.write("," + message);
		out.flush();
		out.close();
	}

	/* Register getNewID */
	public String getNewUserId(HttpServletRequest request, HttpServletResponse response, int lv)
			throws ServletException, IOException {
		String newId = "";
		String message = "";

		/* 利用Connection產生DAO物件 */
		webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);
		/* 取得該身分使用者已註冊多少人 */
		try {
			int userNumber = webUserDAO.checkUserId(lv + 1);
			/* 得出有幾個空白 */
			if (userNumber >= 0) {
				int zeroIndex = 6 - ((userNumber + 1) + "").length();
				String zeroNumber = "";
				/* 空白填入"0" */
				for (int letterIndex = 0; letterIndex < zeroIndex; letterIndex++) {
					zeroNumber += "0";
				}
				newId = (userNumber >= 0) ? String.valueOf(lv + 1) + zeroNumber + String.valueOf(userNumber + 1) : "";
				message = "Sucess";
			}
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return newId + "," + message;
	}

	/* Register submit */
	public void doRegisterSubmit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 參數宣告 */
		String user_id = "", account, password, first_name, last_name, nickname, fervor = "", email, phone,
				location_code, addr0, addr1, addr2;
		String gender, get_email;
		LocalDate birth, join_date;
		Integer lv = 0, version = 0;
		BigDecimal zest = new BigDecimal("0");

		/* 先從request取值，前端提供手動輸入的欄位可能要使用trim()去除頭尾的空白 */
		account = request.getParameter("account").trim();
		password = request.getParameter("password").trim();
		first_name = request.getParameter("first_name").trim();
		last_name = request.getParameter("last_name").trim();
		nickname = request.getParameter("nickname").trim();
		gender = request.getParameter("gender");
		birth = LocalDate.parse(request.getParameter("birth"));
		for (int fervorIndex = 0; fervorIndex < request.getParameterValues("fervor").length; fervorIndex++) {
			if (!request.getParameterValues("fervor")[fervorIndex].equals("")) {
				if (fervor.length() > 0) {
					fervor += ",";
				}
				fervor += request.getParameterValues("fervor")[fervorIndex];
			} else {
				fervor += "";
			}
		}
		email = request.getParameter("email").trim();
		phone = request.getParameter("phone").trim();
		get_email = (request.getParameter("get_email").equals("Y")) ? "Y" : "N";
		location_code = request.getParameter("location_code");
		addr0 = request.getParameter("addr0").trim();
		addr1 = request.getParameter("addr1").trim();
		addr2 = request.getParameter("addr2").trim();
		/* 立即產生 */
		join_date = LocalDate.now();
		/* 使用JavaBean建構子 */
		WebUserBean reg_webUser = new WebUserBean(user_id, account, password, first_name, last_name, nickname, gender,
				birth, fervor, email, phone, get_email, location_code, join_date, lv, addr0, addr1, addr2, zest, version);
		/* 嘗試建立Session，如果沒有就建立一個，並將物件reg_webUser以"reg_webUser"的名稱放入Session中 */
		request.getSession(true).setAttribute("reg_webUser", reg_webUser);
		/* 將request、response交棒給另一個jsp，並交出控制權 */
		request.getRequestDispatcher("/webUser/DisplayWebUserInfo.jsp").forward(request, response);
	}

	/* Register confirm */
	public void doRegisterConfirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);

		/* 從session中取出物件reg_webUser */
		WebUserBean registerData = (WebUserBean) request.getSession(true).getAttribute("reg_webUser");
		/* 利用Connection產生DAO物件 */
		webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);
		/* 取得使用者身分 */
		int lv = registerData.getLv();
		/* 設定要顯示的訊息 */
		String insertResultMessage = "";
		String insertResultPage = "WebUserRegisterForm.jsp";

		String idResult = getNewUserId(request, response, lv);
		String[] idResultSpace = idResult.split(",");
		if (!idResultSpace[0].equals("")) {
			/* 取得ID */
			registerData.setUser_id(idResultSpace[0]);

			try {
				/* 執行新增 */
				boolean insertResult = webUserDAO.insertWebUser(registerData);
				/* 執行成功 */
				if (insertResult) {
					insertResultMessage = "恭喜！" + registerData.getNickname() + "，您的帳號已成功建立";
					insertResultPage = "WebUserLogin.jsp";
				}
			} catch (SQLException sqlE) {
				insertResultMessage = "發生錯誤！" + sqlE.getMessage();

				/* 嘗試建立Session，並將訊息insertResultMessage以"insertResultMessage"的名稱放入新Session中 */
				request.getSession(true).setAttribute("insertResultMessage", insertResultMessage);
				/* 將訊息insertResultPage以"insertResultPage"的名稱放入新Session中 */
				request.getSession(true).setAttribute("insertResultPage", insertResultPage);
				/* 導向其他畫面 */
				request.getRequestDispatcher("../webUser/WebUserRegisterResult.jsp").forward(request, response);
			}
		} else {
			insertResultMessage = "發生錯誤！" + idResultSpace[1];
			insertResultPage = "WebUserRegisterForm.jsp";
		}

		/* 無效session */
		request.getSession(true).invalidate();
		/* 另外建立Session，並將訊息insertResultMessage以"insertResultMessage"的名稱放入新Session中 */
		request.getSession(true).setAttribute("insertResultMessage", insertResultMessage);
		/* 將訊息insertResultPage以"insertResultPage"的名稱放入新Session中 */
		request.getSession(true).setAttribute("insertResultPage", insertResultPage);
		/* 導向其他畫面 */
		request.getRequestDispatcher("../webUser/WebUserRegisterResult.jsp").forward(request, response);
	}

	/* Register undo */
	public void doRegisterUndo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 無效session */
		request.getSession(true).invalidate();
		/* 返回註冊畫面 */
		request.getRequestDispatcher("/webUser/WebUserRegisterForm.jsp").forward(request, response);
	}

	/* Login check */
	public void doCheckLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);

		/* 宣告參數 */
		int accountCheckResult = -1;
		boolean passwordCheckResult = false;
		String loginMessage = "";
		String[] checkResultSpace = null;
		WebUserBean userFullData = new WebUserBean();
		/* 取得使用者輸入的參數 */
		String inputAccount = request.getParameter("account");
		String inputPassword = request.getParameter("password");

		try {
			/* 利用Connection產生DAO物件 */
			webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);
			/* 呼叫DAO方法，返回結果 */
			accountCheckResult = webUserDAO.checkAccountExist(inputAccount);
			loginMessage = "Success";
		} catch (SQLException sqlE) {
			loginMessage = sqlE.getMessage();
		}

		/* 帳號存在才往下做 */
		if (accountCheckResult == 1) {
			try {
				checkResultSpace = webUserDAO.checkPassword(inputAccount, inputPassword).split(":");
				passwordCheckResult = Boolean.parseBoolean(checkResultSpace[0]);
				loginMessage = (passwordCheckResult) ? "Success" : "密碼錯誤！請檢查大小寫是否正確，或將類似的字元打錯";
			} catch (SQLException sqlE) {
				loginMessage = sqlE.getMessage();
				/* 嘗試建立Session，並將訊息loginMessage以"loginMessage"的名稱放入新Session中 */
				request.getSession(true).setAttribute("loginMessage", loginMessage);
				/* 導向其他畫面 */
				request.getRequestDispatcher("/webUser/WebUserLoginResult.jsp").forward(request, response);
			}
		} else if (accountCheckResult == 0) {
			loginMessage = "該帳號不存在！請檢查大小寫是否正確，或將類似的字元打錯";
		}

		if (!loginMessage.equals("Success")) {
			/* 嘗試建立Session，並將訊息loginMessage以"loginMessage"的名稱放入新Session中 */
			request.getSession(true).setAttribute("loginMessage", loginMessage);
			/* 導向其他畫面 */
			request.getRequestDispatcher("/webUser/WebUserLoginResult.jsp").forward(request, response);
			/* 帳號、密碼皆正確 */
		} else {
			if (checkResultSpace.length > 1) {
				userFullData.setUser_id(checkResultSpace[1]);
				userFullData.setAccount(checkResultSpace[2]);
				userFullData.setPassword(checkResultSpace[3]);
				userFullData.setFirst_name(checkResultSpace[4]);
				userFullData.setLast_name(checkResultSpace[5]);
				userFullData.setNickname(checkResultSpace[6]);
				userFullData.setGender(checkResultSpace[7]);
				userFullData.setBirth(LocalDate.parse(checkResultSpace[8]));
				userFullData.setFervor(checkResultSpace[9]);
				userFullData.setEmail(checkResultSpace[10]);
				userFullData.setPhone(checkResultSpace[11]);
				userFullData.setGet_email(checkResultSpace[12]);
				userFullData.setLocation_code(checkResultSpace[13]);
				userFullData.setJoin_date(LocalDate.parse(checkResultSpace[14]));
				userFullData.setLv(Integer.parseInt(checkResultSpace[15]));
				userFullData.setAddr0(checkResultSpace[16]);
				userFullData.setAddr1(checkResultSpace[17]);
				userFullData.setAddr2(checkResultSpace[18]);
				userFullData.setZest(new BigDecimal(checkResultSpace[19]));
				userFullData.setVersion(Integer.parseInt(checkResultSpace[20]));

				loginMessage = "歡迎 " + userFullData.getNickname() + " ！";
				/* 嘗試建立Session，並將Java Bean物件userFullData以"userFullData"的名稱放入新Session中 */
				request.getSession(true).setAttribute("userFullData", userFullData);
				/* 將訊息loginMessage以"loginMessage"的名稱放入Session中 */
				request.getSession(true).setAttribute("loginMessage", loginMessage);
				/* 導向登入後主畫面 */
				request.getRequestDispatcher("/webUser/WebUserMain.jsp").forward(request, response);
			}
		}
	}

	/* Delete account */
	public void doDeleteAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 設定參數 */
		String nickname, deleteResultMessage, deleteResultPage = "WebUserMain.jsp";
		boolean backupCheck = false, backupResult = false, deleteResult = false;
		/* 從session中取出物件userFullData */
		WebUserBean userData = (WebUserBean) request.getSession(true).getAttribute("userFullData");
		/* 利用Connection產生DAO物件 */
		webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);
		/* 取得暱稱 */
		nickname = userData.getNickname();

		/*
		 * 將資料從使用者Table上刪除，同時備份一份到使用者-刪除Table 1.先將資料備份至另一張表，失敗則返回 2.再將原資料刪除，失敗則返回
		 */
		try {
			/* 先檢查是否已備份過 */
			backupCheck = webUserDAO.selectWebUserDeleted(userData);
			/* 沒備份過才執行備份 */
			backupResult = (backupCheck) ? true : webUserDAO.insertWebUserDeleted(userData);
		} catch (SQLException sqlE) {
			deleteResultMessage = "執行失敗！錯誤訊息為：" + sqlE.getMessage();
			/* 將訊息deleteResultMessage以"deleteResultMessage"的名稱放入Session中 */
			request.getSession(true).setAttribute("deleteResultMessage", deleteResultMessage);
			/* 將導向的網頁deleteResultPage以"deleteResultPage"的名稱放入Session中 */
			request.getSession(true).setAttribute("deleteResultPage", deleteResultPage);
			/* 導向刪除結果畫面 */
			request.getRequestDispatcher("/webUser/WebUserDeleteResult.jsp").forward(request, response);
		}

		/* 備份資料成功 */
		if (backupResult) {
			/* 執行刪除 */
			try {
				deleteResult = webUserDAO.deleteWebUser(userData);
			} catch (SQLException sqlE) {
				deleteResultMessage = "執行失敗！錯誤訊息為：" + sqlE.getMessage();
				/* 將訊息deleteResultMessage以"deleteResultMessage"的名稱放入Session中 */
				request.getSession(true).setAttribute("deleteResultMessage", deleteResultMessage);
				/* 將導向的網頁deleteResultPage以"deleteResultPage"的名稱放入Session中 */
				request.getSession(true).setAttribute("deleteResultPage", deleteResultPage);
				/* 導向刪除結果畫面 */
				request.getRequestDispatcher("/webUser/WebUserDeleteResult.jsp").forward(request, response);
			}

			/* 刪除成功 */
			if (deleteResult) {
				deleteResultMessage = "感謝您的使用，" + nickname + "！我們有緣再見...";
				/* 導向回首頁 */
				deleteResultPage = "WebUserRegisterForm.jsp";
				/* 無效session */
				request.getSession(true).invalidate();
				/* 嘗試建立Session，並將訊息deleteResultMessage以"deleteResultMessage"的名稱放入Session中 */
				request.getSession(true).setAttribute("deleteResultMessage", deleteResultMessage);
				/* 將導向的網頁deleteResultPage以"deleteResultPage"的名稱放入Session中 */
				request.getSession(true).setAttribute("deleteResultPage", deleteResultPage);
				/* 導向刪除結果畫面 */
				request.getRequestDispatcher("/webUser/WebUserDeleteResult.jsp").forward(request, response);
			}
		}
	}

	/* Logout */
	public void doLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 宣告要傳回的參數 */
		String logoutMessage = "";
		/* 從session中取出物件userFullData */
		WebUserBean userData = (WebUserBean) request.getSession(true).getAttribute("userFullData");
		/* 取出部分資訊以組成訊息 */
		logoutMessage = "感謝您的使用，" + userData.getNickname() + "！";
		/* 無效session */
		request.getSession(true).invalidate();
		/* 嘗試建立Session，並將訊息logoutMessage以"logoutMessage"的名稱放入新Session中 */
		request.getSession(true).setAttribute("logoutMessage", logoutMessage);
		/* 前往登出畫面 */
		request.getRequestDispatcher("/webUser/WebUserLogoutResult.jsp").forward(request, response);
	}

	/* Back to login */
	public void doBackToLoginPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 無效session */
		request.getSession(true).invalidate();
		/* 前往登出畫面 */
		request.getRequestDispatcher("/webUser/WebUserLogoutResult.jsp").forward(request, response);
	}

	/* Back to login main page */
	public void doBackToLoginMainPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 前往主畫面 */
		request.getRequestDispatcher("/webUser/WebUserMain.jsp").forward(request, response);
	}

	/* Select certain data */
	public void doSelectUserData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 宣告回傳參數 */
		List<WebUserBean> selectedResult = new ArrayList<>();
		String selectResultMessage = "";
		String selectResultPage = "WebUserMain.jsp";

		/* 從request中取得查詢參數 */
		String selectedAccount = (request.getParameter("selectedAccount").length() == 0) ? "?"
				: request.getParameter("selectedAccount").trim();
		String selectedNickname = (request.getParameter("selectedNickname").length() == 0) ? "?"
				: request.getParameter("selectedNickname").trim();
		String selectedFervor = (request.getParameter("selectedFervor") == null) ? "?"
				: request.getParameter("selectedFervor");
		String selectedLocation_code = (request.getParameter("selectedLocation_code").length() == 0) ? "?"
				: request.getParameter("selectedLocation_code");
		String selectedParameters = selectedAccount + ":" + selectedNickname + ":" + selectedFervor + ":"
				+ selectedLocation_code;
		/* 利用Connection產生DAO物件 */
		webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);

		try {
			selectedResult = webUserDAO.selectWebUser(selectedParameters);
			selectResultMessage = "以下是您剛才執行的查詢結果，共查詢到 " + String.valueOf(selectedResult.size()) + " 筆符合的資料...";
			/* 將訊息selectResultMessage以"selectResultMessage"的名稱放入Session中 */
			request.getSession(true).setAttribute("selectResultMessage", selectResultMessage);
			/* 將導向的網頁selectResultPage以"selectResultPage"的名稱放入Session中 */
			request.getSession(true).setAttribute("selectResultPage", selectResultPage);
			/* 將取得的資料selectedResult以"selectedResult"的名稱放入Session中 */
			request.getSession(true).setAttribute("selectedResult", selectedResult);
			/* 導向查詢結果畫面 */
			request.getRequestDispatcher("/webUser/DisplayWebUserSearch.jsp").forward(request, response);
		} catch (SQLException sqlE) {
			selectResultMessage = "發生錯誤，無法查詢！訊息為：" + sqlE.getMessage();
			/* 將訊息selectResultMessage以"selectResultMessage"的名稱放入Session中 */
			request.getSession(true).setAttribute("selectResultMessage", selectResultMessage);
			/* 將導向的網頁selectResultPage以"selectResultPage"的名稱放入Session中 */
			request.getSession(true).setAttribute("selectResultPage", selectResultPage);
			/* 導向查詢結果畫面 */
			request.getRequestDispatcher("/webUser/WebUserSearchResult.jsp").forward(request, response);
		}
	}

	/* Select user's own data */
	public void doSelectSelfData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 返回的參數 */
		List<WebUserBean> selfData = new ArrayList<>();
		String selectResultMessage = "";
		String selectResultPage = "WebUserMain.jsp";
		
		/* 從session中取出物件userFullData */
		WebUserBean userData = (WebUserBean) request.getSession(true).getAttribute("userFullData");
		/* 取得目前使用者ID */
		String selfUser_id = userData.getUser_id();
		/* 利用Connection產生DAO物件 */
		webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);
		
		try {
			selfData.add(webUserDAO.selectWebUserSelf(selfUser_id));
			selectResultMessage = "以下為您的個人資料...";
			/* 將訊息selectResultMessage以"selectResultMessage"的名稱放入Session中 */
			request.getSession(true).setAttribute("selectResultMessage", selectResultMessage);
			/* 將物件selfData以"selfData"的名稱放入Session中 */
			request.getSession(true).setAttribute("selfData", selfData);
			/* 導向個人查詢結果畫面 */
			request.getRequestDispatcher("/webUser/DisplayWebUserData.jsp").forward(request, response);
		} catch (SQLException sqlE) {
			selectResultMessage = "執行失敗！錯誤訊息為：" + sqlE.getMessage();
			/* 將訊息selectResultMessage以"selectResultMessage"的名稱放入Session中 */
			request.getSession(true).setAttribute("selectResultMessage", selectResultMessage);
			/* 將導向的網頁selectResultPage以"selectResultPage"的名稱放入Session中 */
			request.getSession(true).setAttribute("selectResultPage", selectResultPage);
			/* 導向個人查詢結果畫面 */
			request.getRequestDispatcher("/webUser/WebUserSearchResult.jsp").forward(request, response);
		}
	}
	
	/* Go to modify-page */
	public void doGoToModifyPage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 前往修改畫面 */
		request.getRequestDispatcher("/webUser/WebUserModifyData.jsp").forward(request, response);
	}
	
	/* Go to modify-password-page */
	public void doGoToModifyPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 前往修改畫面 */
		request.getRequestDispatcher("/webUser/WebUserModifyPassword.jsp").forward(request, response);
	}
	
	/* Update password */
	public void doUpdatePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 返回的參數 */
		String updateResultMessage = "";
		String updateResultPage = "WebUserMain.jsp";
		boolean updatePasswordResult;
		
		/* 取出新密碼 */
		String newPassword = request.getParameter("confirmPassword");
		/* 從session中取出物件userFullData */
		WebUserBean userData = (WebUserBean) request.getSession(true).getAttribute("userFullData");
		/* 取得目前使用者ID */
		String user_id = userData.getUser_id();
		/* 取得目前使用者資料版本 */
		int version = userData.getVersion();
		/* 利用Connection產生DAO物件 */
		webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);
		
		try {
			updatePasswordResult = webUserDAO.updateWebUserPassword(user_id, newPassword, version);
			if (updatePasswordResult) {
				updateResultPage = "WebUserLogin.jsp";
				updateResultMessage = "密碼變更成功！5秒後將返回登入畫面";
				/* 無效session */
				request.getSession(true).invalidate();
				/* 嘗試建立Session，並將訊息updateResultMessage以"updateResultMessage"的名稱放入Session中 */
				request.getSession(true).setAttribute("updateResultMessage", updateResultMessage);
				/* 將導向的網頁updateResultPage以"updateResultPage"的名稱放入Session中 */
				request.getSession(true).setAttribute("updateResultPage", updateResultPage);
				/* 導向變更結果畫面 */
				request.getRequestDispatcher("/webUser/WebUserChangeResult.jsp").forward(request, response);
			} else {
				updateResultMessage = "密碼變更失敗！5秒後將返回主畫面";
				/* 將訊息updateResultMessage以"updateResultMessage"的名稱放入Session中 */
				request.getSession(true).setAttribute("updateResultMessage", updateResultMessage);
				/* 將導向的網頁updateResultPage以"updateResultPage"的名稱放入Session中 */
				request.getSession(true).setAttribute("updateResultPage", updateResultPage);
				/* 導向變更結果畫面 */
				request.getRequestDispatcher("/webUser/WebUserChangeResult.jsp").forward(request, response);
			}
		} catch (SQLException sqlE) {
			updateResultMessage = "執行修改失敗！錯誤訊息為：" + sqlE.getMessage() + System.lineSeparator() + "5秒後將返回主畫面";
			/* 將訊息updateResultMessage以"updateResultMessage"的名稱放入Session中 */
			request.getSession(true).setAttribute("updateResultMessage", updateResultMessage);
			/* 將導向的網頁updateResultPage以"updateResultPage"的名稱放入Session中 */
			request.getSession(true).setAttribute("updateResultPage", updateResultPage);
			/* 導向變更結果畫面 */
			request.getRequestDispatcher("/webUser/WebUserChangeResult.jsp").forward(request, response);
		}
	}
	
	/* Update other data */
	public void doUpdateData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 返回的參數 */
		String updateResultMessage = "";
		String updateResultPage = "WebUserMain.jsp";
		boolean updateDataResult;
		
		/* 從session中取出物件userFullData */
		WebUserBean userData = (WebUserBean) request.getSession(true).getAttribute("userFullData");
		/* 取得目前使用者ID */
		String updatedUser_id = userData.getUser_id();
		/* 取得目前使用者資料版本 */
		String version = String.valueOf(userData.getVersion());
		/* 從request中取得查詢參數 */
		String updatedFirst_name = (request.getParameter("updatedFirst_name").length() == 0) ? "?"
				: request.getParameter("updatedFirst_name").trim();
		String updatedLast_name = (request.getParameter("updatedLast_name").length() == 0) ? "?"
				: request.getParameter("updatedLast_name").trim();
		String updatedNickname = (request.getParameter("updatedNickname").length() == 0) ? "?"
				: request.getParameter("updatedNickname").trim();
		String updatedFervor = (request.getParameter("updatedFervor") == null) ? "?"
				: request.getParameter("updatedFervor").trim();
		if (updatedFervor.substring(0,1).equals(",")) {
			updatedFervor = updatedFervor.substring(1);
		}
		String updatedEmail = (request.getParameter("updatedEmail").length() == 0) ? "?"
				: request.getParameter("updatedEmail").trim();
		String updatedPhone = (request.getParameter("updatedPhone").length() == 0) ? "?"
				: request.getParameter("updatedPhone").trim();
		String updatedGet_email = (request.getParameter("updatedGet_email") == null) ? "?"
				: request.getParameter("updatedGet_email");
		String updatedLocation_code = (request.getParameter("updatedLocation_code") == null) ? "?"
				:request.getParameter("updatedLocation_code").trim();
		String updatedAddr0 = (request.getParameter("updatedAddr0").length() == 0) ? "?"
				: request.getParameter("updatedAddr0").trim();
		String updatedAddr1 = (request.getParameter("updatedAddr1").length() == 0) ? "?"
				: request.getParameter("updatedAddr1").trim();
		String updatedAddr2 = (request.getParameter("updatedAddr2").length() == 0) ? "?"
				: request.getParameter("updatedAddr2").trim();
		String updatedParameters = updatedUser_id + ":" + updatedFirst_name + ":" + updatedLast_name + ":" + updatedNickname 
				+ ":" + updatedFervor + ":" + updatedEmail + ":" + updatedPhone + ":" + updatedGet_email + ":" + updatedLocation_code 
				+ ":" + updatedAddr0 + ":" + updatedAddr1 + ":" + updatedAddr2 + ":" + version;
		
		/* 利用Connection產生DAO物件 */
		webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);
		
		try {
			updateDataResult = webUserDAO.updateWebUserData(updatedParameters);
			if (updateDataResult) {
				updateResultPage = "WebUserLogin.jsp";
				updateResultMessage = "個人資料變更成功！5秒後將返回登入畫面";
				/* 無效session */
				request.getSession(true).invalidate();
				/* 嘗試建立Session，並將訊息updateResultMessage以"updateResultMessage"的名稱放入Session中 */
				request.getSession(true).setAttribute("updateResultMessage", updateResultMessage);
				/* 將導向的網頁updateResultPage以"updateResultPage"的名稱放入Session中 */
				request.getSession(true).setAttribute("updateResultPage", updateResultPage);
				/* 導向變更結果畫面 */
				request.getRequestDispatcher("/webUser/WebUserChangeResult.jsp").forward(request, response);
			} else {
				updateResultMessage = "個人資料變更失敗！5秒後將返回主畫面";
				/* 將訊息updateResultMessage以"updateResultMessage"的名稱放入Session中 */
				request.getSession(true).setAttribute("updateResultMessage", updateResultMessage);
				/* 將導向的網頁updateResultPage以"updateResultPage"的名稱放入Session中 */
				request.getSession(true).setAttribute("updateResultPage", updateResultPage);
				/* 導向變更結果畫面 */
				request.getRequestDispatcher("/webUser/WebUserChangeResult.jsp").forward(request, response);
			}
		} catch (SQLException sqlE) {
			updateResultMessage = "執行修改失敗！錯誤訊息為：" + sqlE.getMessage() + System.lineSeparator() + "5秒後將返回主畫面";
			/* 將訊息updateResultMessage以"updateResultMessage"的名稱放入Session中 */
			request.getSession(true).setAttribute("updateResultMessage", updateResultMessage);
			/* 將導向的網頁updateResultPage以"updateResultPage"的名稱放入Session中 */
			request.getSession(true).setAttribute("updateResultPage", updateResultPage);
			/* 導向變更結果畫面 */
			request.getRequestDispatcher("/webUser/WebUserChangeResult.jsp").forward(request, response);
		}
	}
}
