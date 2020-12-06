package webUser.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webUser.model.WebUserData;
import webUser.service.WebUserService;
import webUser.service.WebUserServiceHibernate;

@WebServlet("/webUser/WebUserServlet")
public class WebUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/* 預先宣告response/request設定所需的部分 */   
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String CHARSET_CODE = "UTF-8";
	
    public WebUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 防止快取設定 */
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", -1); // 防止proxy server進行快取
		
		/* 根據request裡的參數決定要導向到Service裡的哪個方法 */
		/* 註冊部分 */
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
					doUndo(request, response, "register");
					break;
			}
		}
		/* 登入部分 */
		if (request.getParameter("login") != null) {
			switch (request.getParameter("login")) {
				/* 登入 */
				case "登入":
					/* 返回查詢結果 */
					doCheckLogin(request, response);
					break;
				/* 登出 */
				case "登出帳戶":
					/* 執行登出 */
					doLogout(request, response);
					break;
				/* 預設 */
				default:
					/* 返回登入畫面 */
					doUndo(request, response, "login");
					break;
			}
		}
		/* 修改部分 */
		if (request.getParameter("update") != null) {
			switch (request.getParameter("update")) {
				/* 變更為放棄使用 */
				case "放棄使用帳戶":
					/* 執行變更 */
					doQuitAccount(request, response);
					break;
				case "修改密碼":
					/* 導向修改密碼 */
					doGoToModifyPages(request, response, "password");
					break;
				case "修改其他資料":
					/* 導向修改畫面 */
					doGoToModifyPages(request, response, "other data");
					break;
				case "檢查信箱":
					/* 返回查詢結果給使用者確認 */
					doCheckEmail(request, response);
					break;
				case "資料修改完畢":
					/* 準備執行其他個人資料更新 */
					doUpdateData(request, response);
					break;
				case "密碼修改完畢":
					/* 準備執行個人密碼更新 */
					doUpdatePassword(request, response);
					break;
			}
		}
		/* 刪除部分已改為變更帳號狀態為quit */
		/* 查詢部分 */
		if (request.getParameter("select") != null) {
			switch (request.getParameter("select")) {
				case "檢視/修改個人資料":
					/* 查詢個人資料 */
					doSelectSelfData(request, response);
					break;
				case "執行查詢":
					/* 查詢其他使用者資料 */
					doSelectUserData(request, response);
					break;
				case "進行搜索":
					/* 查詢所有使用者資料 */
					doSelectAllUserData(request, response);
					break;
				default:
					/* 返回主畫面 */
					doUndo(request, response, "select");
					break;
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	/* Register checkAccount */
	public void doCheckAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 宣告欲回傳的參數 */
		Integer accountCheckResult = -1;
		String message = "";
		/* 宣告printer */
		PrintWriter out = response.getWriter();
		/* 取得使用者輸入的參數 */
		String inputAccount = request.getParameter("inputAccount");
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 調用服務裡的方法 */
		try {
			accountCheckResult = wus.checkAccountExist(inputAccount);
			
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
		Integer emailCheckResult = -1;
		String message = "";
		/* 宣告printer */
		PrintWriter out = response.getWriter();
		/* 取得使用者輸入的參數 */
		String inputEmail = request.getParameter("inputEmail");
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 調用服務裡的方法 */
		try {
			emailCheckResult = wus.checkEmailExist(inputEmail);
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		
		/* 將結果返回aJax */
		out.write(String.valueOf(emailCheckResult));
		out.write("," + message);
		out.flush();
		out.close();
	}
	
	/* Register submit */
	public void doRegisterSubmit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 傳回參數宣告 */
		String submitMessage = "";
		
		/* 參數宣告 */
		String userId = "", account, password, firstName, lastName, nickname, fervor = "", email, phone,
				locationCode, addr0, addr1, addr2;
		String gender, getEmail, status = "active";
		Date birth, joinDate;
		Integer lv, version = 0;
		BigDecimal zest = new BigDecimal("0");
		
		/* 先從request取值，前端提供手動輸入的欄位可能要使用trim()去除頭尾的空白 */
		lv = Integer.parseInt(request.getParameter("lv"));
		account = request.getParameter("account").trim();
		password = request.getParameter("password").trim();
		firstName = request.getParameter("firstName").trim();
		lastName = request.getParameter("lastName").trim();
		nickname = request.getParameter("nickname").trim();
		gender = request.getParameter("gender");
		
		/* 防非正規管道吃進來的異常輸入無法用valueOf轉換成功 */
		try {
			birth = (request.getParameter("birth") == null)
					? Date.valueOf(LocalDate.now()) 
					: Date.valueOf(request.getParameter("birth"));
		} catch (Exception e) {
			birth = Date.valueOf(LocalDate.now()); 
		}
		
		/* 防非正規管道吃進來的異常輸入為空值時 */
		if (request.getParameterValues("fervor").length > 0) {
			for (Integer fervorIndex = 0; fervorIndex < request.getParameterValues("fervor").length; fervorIndex++) {
				if (!request.getParameterValues("fervor")[fervorIndex].equals("")) {
					if (fervor.length() > 0) {
						fervor += ",";
					}
					fervor += request.getParameterValues("fervor")[fervorIndex];
				} else {
					fervor += "";
				}
			}
		} else {
			fervor = "";
		}
		
		email = request.getParameter("email").trim();
		phone = request.getParameter("phone").trim();
		getEmail = (request.getParameter("getEmail").equals("Y")) ? "Y" : "N";
		locationCode = request.getParameter("locationCode");
		addr0 = request.getParameter("addr0").trim();
		addr1 = request.getParameter("addr1").trim();
		addr2 = request.getParameter("addr2").trim();
		/* 立即產生 */
		joinDate = Date.valueOf(LocalDate.now());
		
		/* 使用JavaBean建構子 */
		WebUserData reg_webUser = new WebUserData(userId, account, password, firstName, lastName, nickname, gender,
				birth, fervor, email, phone, getEmail, locationCode, joinDate, lv, addr0, addr1, addr2, zest, version, status);
		
		/* 預防性後端輸入檢查，正常時回傳空字串 */
		submitMessage = doRegisterInputCheck(reg_webUser);
		
		if (submitMessage.equals("")) {
			/* 嘗試建立Session，如果沒有就建立一個，並將物件reg_webUser以"reg_webUser"的名稱放入Session中 */
			request.getSession(true).setAttribute("reg_webUser", reg_webUser);
			/* 將request、response交棒給另一個jsp，並交出控制權 */
			request.getRequestDispatcher("/webUser/DisplayWebUserInfo.jsp").forward(request, response);
		} else {
			/* 嘗試建立Session，如果沒有就建立一個，並將物件submitMessage以"submitMessage"的名稱放入Session中 */
			request.getSession(true).setAttribute("submitMessage", submitMessage);
			/* 將request、response交棒給另一個jsp，並交出控制權 */
			request.getRequestDispatcher("/webUser/WebUserRegisterForm.jsp").forward(request, response);
		}
	}
	
	/* Register confirm */
	public void doRegisterConfirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 從session中取出物件reg_webUser */
		WebUserData registerData = (WebUserData) request.getSession(true).getAttribute("reg_webUser");
		
		/* 設定要顯示的訊息 */
		String insertResultMessage = "";
		String insertResultPage = "WebUserRegisterForm.jsp";
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 預防性後端輸入檢查，正常時回傳空字串 */
		insertResultMessage = doRegisterInputCheck(registerData);
		/* 追加檢查項目 */
		if (!registerData.getJoinDate().equals(Date.valueOf(LocalDate.now()))) {
			insertResultMessage = "加入時間異常";
		}
		if (!registerData.getStatus().equals("active")) {
			insertResultMessage = "帳號狀態異常";
		}
		if (registerData.getVersion() != 0) {
			insertResultMessage = "帳號資料狀態異常";
		}
		
		/* 宣告欲回傳的參數 */
		Integer insertResult = -1;
		
		if (insertResultMessage.equals("")) {
			/* 調用服務裡的方法 */
			try {
				insertResult = wus.insertWebUserData(registerData);
			} catch (SQLException sqlE) {
				insertResultMessage = "發生錯誤！" + sqlE.getMessage();
			}
			
			if (insertResult > 0) {
				insertResultMessage = "恭喜！" + registerData.getNickname() + "，您的帳號已成功建立";
				insertResultPage = "WebUserLogin.jsp";
				/* 無效session */
				request.getSession(true).invalidate();
			}
			
			/* 另外建立Session，並將訊息insertResultMessage以"insertResultMessage"的名稱放入新Session中 */
			request.getSession(true).setAttribute("insertResultMessage", insertResultMessage);
			/* 將訊息insertResultPage以"insertResultPage"的名稱放入新Session中 */
			request.getSession(true).setAttribute("insertResultPage", insertResultPage);
			/* 導向其他畫面，改用response.sendRedirect() */
			response.sendRedirect(request.getContextPath() + "/webUser/WebUserRegisterResult.jsp");
		} else {
			/* 另外建立Session，並將訊息insertResultMessage以"insertResultMessage"的名稱放入新Session中 */
			request.getSession(true).setAttribute("submitMessage", insertResultMessage);
			/* 將request、response交棒給另一個jsp，並交出控制權 */
			request.getRequestDispatcher("/webUser/WebUserRegisterForm.jsp").forward(request, response);
		}
		
	}
	
	/* Login check */
	public void doCheckLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 宣告參數 */
		Integer inputCheckResult = -1;
		Integer accountCheckResult = -3;
		String loginMessage = "";
		WebUserData userFullData = new WebUserData();
		
		/* 取得使用者輸入的參數 */
		String inputAccount = request.getParameter("account");
		String inputPassword = request.getParameter("password");
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 預防性後端檢查，正常時回傳1 */
		inputCheckResult = doLoginInputCheck(inputAccount, inputPassword);
		
		if (inputCheckResult == 1) {
			/* 調用服務裡的方法 */
			try {
				/* 檢查登入 */
				accountCheckResult = wus.checkWebUserLogin(inputAccount, inputPassword);
				/* 存取使用者個人資料 */
				userFullData = wus.getWebUserData(inputAccount);
			} catch (SQLException sqlE) {
				String loginMessageTmp = sqlE.getMessage();
				loginMessage = loginMessageTmp.split(":")[1];
			}
		} else {
			switch(inputCheckResult) {
				case 0:
					loginMessage = "帳號不可為空白";
					break;
				case -1:
					loginMessage = "帳號長度不足";
					break;
				case -2:
					loginMessage = "帳號長度過長";
					break;
				case -3:
					loginMessage = "帳號不可以數字開頭";
					break;
				case -4:
					loginMessage = "帳號不符合格式";
					break;
				case 2:
					loginMessage = "密碼不可為空白";
					break;
				case 3:
					loginMessage = "密碼長度不足，至少需8個字元";
					break;
				case 4:
					loginMessage = "密碼長度過長，最多僅20個字元";
					break;
				case 5:
					loginMessage = "密碼不可以數字開頭";
					break;
				case 6:
					loginMessage = "密碼不符合格式";
					break;
			}
		}
		
		if (accountCheckResult == 1) {
			loginMessage = "歡迎 " + userFullData.getNickname() + " ！";
			/* 嘗試建立Session，並將Java Bean物件userFullData以"userFullData"的名稱放入新Session中 */
			request.getSession(true).setAttribute("userFullData", userFullData);
		} 
		
		/* 將訊息loginMessage以"loginMessage"的名稱放入session中 */
		request.getSession(true).setAttribute("loginMessage", loginMessage);;
		
		/* 宣告printer */
		PrintWriter out = response.getWriter();
		
		/* 將結果返回aJax */
		out.write(String.valueOf(accountCheckResult));
		out.write("," + loginMessage);
		out.flush();
		out.close();
	}
	
	/* Logout */
	public void doLogout(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 宣告要傳回的參數 */
		String logoutMessage = "";
		
		/* 從session中取出物件userFullData */
		WebUserData userData = (WebUserData) request.getSession(true).getAttribute("userFullData");
		/* 取出部分資訊以組成訊息 */
		logoutMessage = "感謝您的使用，" + userData.getNickname() + "！";
		
		/* 無效session */
		request.getSession(true).invalidate();
		/* 嘗試建立Session，並將訊息logoutMessage以"logoutMessage"的名稱放入新Session中 */
		request.getSession(true).setAttribute("logoutMessage", logoutMessage);
		/* 前往登出畫面 */
		request.getRequestDispatcher("/webUser/WebUserLogoutResult.jsp").forward(request, response);
	}
	
	/* Quit account */
	public void doQuitAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 宣告要傳回的參數 */
		Integer deleteResult = -1;
		String quitMessage = "";
		String redirectPage = "";
		
		/* 從session中取出物件userFullData */
		WebUserData quitUserData = (WebUserData) request.getSession(true).getAttribute("userFullData");
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 調用服務裡的方法 */
		try {
			deleteResult = wus.quitWebUserData(quitUserData);
		} catch (SQLException sqlE) {
			String quitMessageTmp = sqlE.getMessage();
			quitMessage = quitMessageTmp.split(":")[1];
		}
		
		/* 成功變更 */
		if (deleteResult == 1) {
			quitMessage = "感謝您的使用，" + quitUserData.getNickname() + "！我們有緣再見...";
			redirectPage = "WebUserRegisterForm.jsp";
			/* 無效session */
			request.getSession(true).invalidate();
		}
		
		/* 將訊息quitMessage以"quitMessage"的名稱放入Session中 */
		request.getSession(true).setAttribute("quitMessage", quitMessage);
		/* 將導向的網頁redirectPage以"redirectPage"的名稱放入Session中 */
		request.getSession(true).setAttribute("redirectPage", redirectPage);
		/* 導向刪除結果畫面 */
		request.getRequestDispatcher("/webUser/WebUserDeleteResult.jsp").forward(request, response);
	}
	
	/* Select user's own data */
	public void doSelectSelfData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 宣告要傳回的參數 */
		WebUserData selfData = new WebUserData();
		String getResultMessage = "";
		
		/* 從session中取出物件userFullData */
		WebUserData userData = (WebUserData) request.getSession(true).getAttribute("userFullData");
		/* 取得目前使用者帳號 */
		String userAccount = userData.getAccount();
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 調用服務裡的方法 */
		try {
			selfData = wus.getWebUserData(userAccount);
		} catch (SQLException sqlE) {
			String getDataMessageTmp = sqlE.getMessage();
			getResultMessage = getDataMessageTmp.split(":")[1];
		}
		
		/* 成功取得資料時，selfData不為Null */
		if (selfData != null) {
			userData = selfData;
			getResultMessage = "以下為您的個人資料...";
			/* 將物件selfData以"UserFullData"的名稱放入Session中 */
			request.getSession(true).setAttribute("UserFullData", selfData);
		} else if (getResultMessage.equals("")) {
			getResultMessage = "無法取得使用者資料";
			/* 將導向的網頁failResultPage以"failResultPage"的名稱放入Session中 */
			request.getSession(true).setAttribute("failResultPage", "WebUserMain.jsp");
		}
		
		/* 將訊息getResultMessage以"getResultMessage"的名稱放入Session中 */
		request.getSession(true).setAttribute("getResultMessage", getResultMessage);
		
		if (selfData != null) {
			/* 導向個人查詢結果畫面 */
			request.getRequestDispatcher("/webUser/DisplayWebUserData.jsp").forward(request, response);
		} else {
			/* 導向個人查詢結果畫面 */
			request.getRequestDispatcher("/webUser/WebUserSearchResult.jsp").forward(request, response);
		}
	}
	
	/* Select certain data */
	public void doSelectUserData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 宣告回傳參數 */
		List<WebUserData> selectedResult = new ArrayList<>();
		String selectResultMessage = "";
		
		/* 從session中取出物件userFullData */
		WebUserData userData = (WebUserData) request.getSession(true).getAttribute("userFullData");
		
		/* 從request中取得查詢參數 */
		String selectedAccount = (request.getParameter("selectedAccount").length() == 0) ? "?"
				: request.getParameter("selectedAccount").trim();
		String selectedNickname = (request.getParameter("selectedNickname").length() == 0) ? "?"
				: request.getParameter("selectedNickname").trim();
		String selectedFervor = (request.getParameter("selectedFervor") == null) ? "?"
				: request.getParameter("selectedFervor");
		String selectedLocationCode = (request.getParameter("selectedLocationCode").length() == 0) ? "?"
				: request.getParameter("selectedLocationCode");
		String selectedParameters = selectedAccount + ":" + selectedNickname + ":" + selectedFervor + ":"
				+ selectedLocationCode + ":" + String.valueOf(userData.getLv()) + ":" + userData.getStatus();
		
		/* 預防性後端輸入檢查 */
		selectResultMessage = doSelectUserDataInputCheck(selectedParameters, userData);
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		if (selectResultMessage.equals("")) {
			/* 調用服務裡的方法 */
			
		}
	}
	
	/* Select all user data */
	public void doSelectAllUserData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 從session中取出物件userFullData */
		WebUserData userData = (WebUserData) request.getSession(true).getAttribute("userFullData");
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		
	}
	
	/* Go to certain page */
	public void doGoToModifyPages(HttpServletRequest request, HttpServletResponse response, String mode) throws ServletException, IOException {
		/* 導向新畫面 */
		switch(mode) {
			case "password":
				/* 前往修改畫面 */
				request.getRequestDispatcher("/webUser/WebUserModifyPassword.jsp").forward(request, response);
				break;
			case "other data":
				/* 前往修改畫面 */
				request.getRequestDispatcher("/webUser/WebUserModifyData.jsp").forward(request, response);
				break;
		}
	}
	
	/* Update other data */
	public void doUpdateData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 返回的參數 */
		String updateResultMessage = "";
		Integer updateResult = -1;
		/* 更新用的同型物件 */
		WebUserData updatedUserData = new WebUserData();
		
		/* 從session中取出物件userFullData */
		WebUserData userData = (WebUserData) request.getSession(true).getAttribute("userFullData");
		/* 取得目前使用者ID */
		String updatedUserId = userData.getUserId();
		/* 從request中取得查詢參數 */
		String updatedFirstName = request.getParameter("updatedFirstName").trim();
		String updatedLastName = request.getParameter("updatedLastName").trim();
		String updatedNickname = request.getParameter("updatedNickname").trim();
		String updatedFervor = request.getParameter("updatedFervor").trim();
		String updatedEmail = request.getParameter("updatedEmail").trim();
		String updatedPhone = request.getParameter("updatedPhone").trim();
		String updatedGetEmail = request.getParameter("updatedGetEmail").trim();
		String updatedLocationCode = request.getParameter("updatedLocationCode").trim();
		String updatedAddr0 = request.getParameter("updatedAddr0").trim();
		String updatedAddr1 = request.getParameter("updatedAddr1").trim();
		String updatedAddr2 = request.getParameter("updatedAddr2").trim();
		
		/* 預防性後端輸入檢查 */
		updateResultMessage = doUpdateDataInputCheck(userData, request, response);
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		if (updateResultMessage.equals("")) {
			updatedUserData = new WebUserData(
					updatedUserId, 
					userData.getAccount(), 
					userData.getPassword(), 
					updatedFirstName, 
					updatedLastName, 
					updatedNickname,
					userData.getGender(),
					userData.getBirth(),
					updatedFervor,
					updatedEmail,
					updatedPhone,
					updatedGetEmail,
					updatedLocationCode,
					userData.getJoinDate(),
					userData.getLv(),
					updatedAddr0,
					updatedAddr1,
					updatedAddr2,
					userData.getZest(),
					userData.getVersion() + 1,
					userData.getStatus());
			
			/* 調用服務裡的方法 */
			try {
				updateResult = wus.updateWebUserData(updatedUserData);
			} catch (SQLException sqlE) {
				updateResultMessage = sqlE.getMessage();
			}
			
			/* 成功 */
			if (updateResult == 1) {
				/* 無效session */
				request.getSession(true).invalidate();
			}
		} 
		
		if (!updateResultMessage.equals("")) {
			if (updateResultMessage.indexOf(":") != -1) {	
				updateResultMessage = updateResultMessage.split(":")[1];
			}
		} else {
			updateResultMessage = "更新操作順利完成，請重新登入以獲得最新的資料";
		}
		
		/* 將訊息updateResultMessage以"updateResultMessage"的名稱放入Session中 */
		request.getSession(true).setAttribute("updateResultMessage", updateResultMessage);
		if (updateResult == 1) {
			/* 導向顯示個人資料畫面 */
			response.sendRedirect(request.getContextPath() + "/webUser/WebUserChangeResult.jsp");
		} else {
			/* 導向修改個人資料畫面 */
			request.getRequestDispatcher("/webUser/WebUserModifyData.jsp").forward(request, response);
		}
	}
	
	/* Update password */
	public void doUpdatePassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 返回的參數 */
		String updateResultMessage = "";
		Integer updateResult = -1;
		
		/* 從session中取出物件userFullData */
		WebUserData userData = (WebUserData) request.getSession(true).getAttribute("userFullData");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		
		/* 預防性後端輸入檢查 */
		updateResultMessage = doUpdatePasswordInputCheck(userData, password, confirmPassword);
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		if (updateResultMessage.equals("")) {
			/* 調用服務裡的方法 */
			try {
				userData.setVersion(userData.getVersion() + 1);
				userData.setPassword(password);
				updateResult = wus.updateWebUserPassword(userData);
			} catch (SQLException sqlE) {
				updateResultMessage = sqlE.getMessage();
				userData.setVersion(userData.getVersion() - 1);
			}
			
			/* 成功 */
			if (updateResult == 1) {
				/* 無效session */
				request.getSession(true).invalidate();
			}
		}
		
		if (!updateResultMessage.equals("")) {
			if (updateResultMessage.indexOf(":") != -1) {	
				updateResultMessage = updateResultMessage.split(":")[1];
			}
		} else {
			updateResultMessage = "密碼變更成功！5秒後將返回登入畫面";
		}
		
		/* 將訊息updateResultMessage以"updateResultMessage"的名稱放入Session中 */
		request.getSession(true).setAttribute("updateResultMessage", updateResultMessage);
		if (updateResult == 1) {
			/* 導向密碼修改結果畫面 */
			response.sendRedirect(request.getContextPath() + "/webUser/WebUserChangeResult.jsp");
		} else {
			/* 導向修改個人密碼畫面 */
			request.getRequestDispatcher("/webUser/WebUserModifyPassword.jsp").forward(request, response);
		}
	}
	
	/* Undo */
	public void doUndo(HttpServletRequest request, HttpServletResponse response, String mode) throws ServletException, IOException {
		/* 無效session */
		request.getSession(true).invalidate();
		/* 重導向畫面 */
		switch(mode) {
			case "register":
				request.getRequestDispatcher("/webUser/WebUserRegisterForm.jsp").forward(request, response);
				break;
			case "select":
				request.getRequestDispatcher("/webUser/WebUserMain.jsp").forward(request, response);
				break;
			case "login":
			default:
				request.getRequestDispatcher("/webUser/WebUserLogoutResult.jsp").forward(request, response);
				break;
		}
	}
	
	/* Submit input check */
	public String doRegisterInputCheck(WebUserData reg_webUser){
		/* 傳回參數宣告 */
		String submitMessage = "";
		
		/* 是否符合條件 */
		Boolean inputIsOk = true;
		
		/* 參數宣告 */
		Integer lv;
		String account, password, firstName, lastName, nickname, fervor, email, phone,
				locationCode, addr0, addr1, addr2;
		String gender, getEmail;
		Date birth;
		
		lv = reg_webUser.getLv();
		account = reg_webUser.getAccount();
		password = reg_webUser.getPassword();
		firstName = reg_webUser.getFirstName();
		lastName = reg_webUser.getLastName();
		nickname = reg_webUser.getNickname();
		fervor = reg_webUser.getFervor();
		gender = reg_webUser.getGender();
		birth = reg_webUser.getBirth();	
		email = reg_webUser.getEmail();
		phone = reg_webUser.getPhone();
		getEmail = reg_webUser.getGetEmail();
		locationCode = reg_webUser.getLocationCode();
		addr0 = reg_webUser.getAddr0();
		addr1 = reg_webUser.getAddr1();
		addr2 = reg_webUser.getAddr2();
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 輸入檢查 */
		/* 使用者身分 */
		switch(lv) {
			case 1:
			case 0:
			case -1:
				inputIsOk = true;
				break;
			default:
				submitMessage = "帳號身分異常";
				inputIsOk = false;
				break;
		}
		
		/* 使用者帳號 */
		if (inputIsOk) {
			if (account.equals("")) {
				submitMessage = "帳號不可為空白";
				inputIsOk = false;
			} else if(account.length() < 8) {
				submitMessage = "帳號長度不足";
				inputIsOk = false;
			} else if(account.length() > 20) {
				submitMessage = "帳號長度過長";
				inputIsOk = false;
			} else if (account.matches("[1-9]{1}.")) {
				submitMessage = "帳號不可以數字開頭";
				inputIsOk = false;
			} else if (!account.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
				submitMessage = "帳號不符合格式";
				inputIsOk = false;
			} else {
				Integer accountCheckResult = -1;
				
				/* 調用服務裡的方法 */
				try {
					accountCheckResult = wus.checkAccountExist(account);
				} catch (SQLException sqlE) {
					submitMessage = sqlE.getMessage();
					inputIsOk = false;
				}
				
				if (accountCheckResult == 0) {
					inputIsOk = true;
				} else if (accountCheckResult == 1){
					submitMessage = "帳號已存在，請挑選別的名稱作為帳號";
					inputIsOk = false;
				}
			}
			
			/* 使用者密碼 */
			if (inputIsOk) {
				if (password.equals("")) {
					submitMessage = "密碼不可為空白";
					inputIsOk = false;
				} else if (password.length() < 8) {
					submitMessage = "密碼長度不足，至少需8個字元";
					inputIsOk = false;
				} else if (password.length() > 20) {
					submitMessage = "密碼長度過長，最多僅20個字元";
					inputIsOk = false;
				} else if (password.matches("[1-9]{1}.")) {
					submitMessage = "密碼不可以數字開頭";
					inputIsOk = false;
				} else if (!password.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
					submitMessage = "密碼不符合格式";
					inputIsOk = false;
				} else {
					inputIsOk = true;
				}
			}
		}
		
		/* 中文姓氏 */
		if (inputIsOk) {
			if (firstName.equals("")) {
				submitMessage = "姓氏不可為空白";
				inputIsOk = false;
			} else if (firstName.length() > 3) {
				submitMessage = "姓氏長度過長，最多僅3個字元";
				inputIsOk = false;
			} else {
				Integer charCountBegin = 0;
				Boolean firstNameIsOk = true;
				/* 16進位表示 */
				Integer charChineseWordCountBegin = 0x4e00;
				Integer charChineseWordCountEnd = 0x9fff;
				
				for (Integer charIndex = charCountBegin; charIndex < firstName.length(); charIndex++) {
					int firstNameChar = firstName.charAt(charIndex);

					if (firstNameChar < charChineseWordCountBegin || firstNameChar > charChineseWordCountEnd) {
						firstNameIsOk = false;
					}
					if (!firstNameIsOk) {
						break;
					}
				}
				
				if (!firstNameIsOk) {
					submitMessage = "姓氏中含有非中文";
					inputIsOk = false;
				} else {
					inputIsOk = true;
				}
			}
		}
		
		/* 中文名字 */
		if (inputIsOk) {
			if (lastName.equals("")) {
				submitMessage = "名字不可為空白";
				inputIsOk = false;
			} else if (lastName.length() > 3) {
				submitMessage = "名字長度過長，最多僅3個字元";
				inputIsOk = false;
			} else {
				Integer charCountBegin = 0;
				Boolean lastNameIsOk = true;
				/* 16進位表示 */
				Integer charChineseWordCountBegin = 0x4e00;
				Integer charChineseWordCountEnd = 0x9fff;
				
				for (Integer charIndex = charCountBegin; charIndex < lastName.length(); charIndex++) {
					int lastNameChar = lastName.charAt(charIndex);

					if (lastNameChar < charChineseWordCountBegin || lastNameChar > charChineseWordCountEnd) {
						lastNameIsOk = false;
					}
					if (!lastNameIsOk) {
						break;
					}
				}
				
				if (!lastNameIsOk) {
					submitMessage = "名字中含有非中文";
					inputIsOk = false;
				} else {
					inputIsOk = true;
				}
			}
		}
		
		/* 生理性別 */
		if (inputIsOk) {
			switch(gender) {
				case "M":
				case "W":
				case "N":
					inputIsOk = true;
					break;
				default:
					submitMessage = "生理性別設定異常";
					inputIsOk = false;
					break;
			}
		}
		
		/* 稱呼 */
		if (inputIsOk) {
			if (nickname.equals("") && lastName.equals("")) {
				submitMessage = "稱呼不可為空白";
				inputIsOk = false;
			} else if (nickname.equals("") && !lastName.equals("")) {
				nickname = lastName;
				inputIsOk = true;
			} else if (nickname.length() > 20){
				submitMessage = "稱呼長度過長";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		/* 西元生日 */
		if (inputIsOk) {
			if (birth == Date.valueOf(LocalDate.now())) {
				submitMessage = "生日異常";
				inputIsOk = false;
			} else if (Date.valueOf(birth.toString()).after(Date.valueOf(LocalDate.now()))) {
				submitMessage = "生日異常";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		/* 偏好食物 */
		if (inputIsOk) {
			if (fervor.equals("")) {
				submitMessage = "偏好食物不可為空白";
				inputIsOk = false;
			} else if (fervor.length() > 50){
				submitMessage = "偏好食物長度過長";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		/* email */
		if (inputIsOk) {
			if (email.equals("")) {
				submitMessage = "信箱資訊不可為空白";
				inputIsOk = false;
			} else if(email.indexOf("@") == -1 || email.split("@").length > 2 || email.indexOf(" ") != -1) {
				submitMessage = "信箱資訊格式錯誤";
				inputIsOk = false;
			} else {
				Integer emailCheckResult = -1;
				
				/* 調用服務裡的方法 */
				try {
					emailCheckResult = wus.checkEmailExist(email);
				} catch (SQLException sqlE) {
					submitMessage = sqlE.getMessage();
					inputIsOk = false;
				}
				
				if (emailCheckResult == 0) {
					inputIsOk = true;
				} else if (emailCheckResult == 1){
					submitMessage = "該聯絡信箱已被註冊，請挑選別的聯絡信箱";
					inputIsOk = false;
				}
			}
		}
		
		/* phone */
		if (inputIsOk) {
			if (phone.equals("")) {
				submitMessage = "連絡電話不可為空白";
				inputIsOk = false;
			} else if(phone.length() < 9 || phone.indexOf(" ") != -1) {
				submitMessage = "連絡電話格式錯誤";
				inputIsOk = false;
			} else if (!phone.matches("[0]{1}[2-9]{1}[0-9]{7,9}")) {
				submitMessage = "連絡電話格式錯誤";
				inputIsOk = false;
			} else if (phone.substring(0, 2).equals("09") && phone.length() != 10) {
				submitMessage = "行動電話格式錯誤";
				inputIsOk = false;
			} else if (!phone.substring(0, 2).equals("09") && phone.length() == 10) {
				submitMessage = "室內電話格式錯誤";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		/* getEmail */
		if (inputIsOk) {
			switch(getEmail) {
				case "Y":
				case "N":
					inputIsOk = true;
					break;
				default:
					submitMessage = "接收促銷/優惠訊息設定異常";
					inputIsOk = false;
					break;
			}
		}
		
		/* 居住區域 */
		if (inputIsOk) {
			switch(locationCode) {
				case "t01":
				case "t02":
				case "t03":
				case "t04":
				case "t05":
				case "t06":
				case "t07":
				case "t08":
				case "t09":
				case "t10":
				case "t11":
				case "t12":
				case "t13":
				case "t14":
				case "t15":
				case "t16":
				case "t19":
				case "t20":
				case "t21":
				case "t22":
				case "t23":
					inputIsOk = true;
					break;
				default:
					submitMessage = "居住區域設定異常";
					inputIsOk = false;
					break;
			}
		}
		
		/* 生活地點一 */
		if (inputIsOk) {
			if (addr0.equals("")) {
				submitMessage = "生活地點一不可為空白";
				inputIsOk = false;
			} else if (addr0.length() > 65) {
				submitMessage = "生活地點一超過長度限制";
				inputIsOk = false;
			} else if (addr0.equals(addr1) || addr0.equals(addr2)) {
				submitMessage = "生活地點重複填寫";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		/* 生活地點二 */
		if (inputIsOk) {
			if (addr1.length() > 65) {
				submitMessage = "生活地點二超過長度限制";
				inputIsOk = false;
			} else if (addr1.equals(addr0) || (addr1.equals(addr2) && !addr2.equals(""))) {
				submitMessage = "生活地點重複填寫";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		/* 生活地點三 */
		if (inputIsOk) {
			if (addr2.length() > 65) {
				submitMessage = "生活地點三超過長度限制";
				inputIsOk = false;
			} else if (addr2.equals(addr0) || (addr2.equals(addr1) && !addr1.equals(""))) {
				submitMessage = "生活地點重複填寫";
				inputIsOk = false;
			} else {
				inputIsOk = true;
			}
		}
		
		return submitMessage;
	}
	
	/* Login input check */
	public Integer doLoginInputCheck(String account, String password) {
		Integer inputCheckResult = 1;
		
		/* 使用者帳號 */
		if (account.equals("")) {
			inputCheckResult = 0;
		} else if(account.length() < 8) {
			inputCheckResult = -1;
		} else if(account.length() > 20) {
			inputCheckResult = -2;
		} else if (account.matches("[1-9]{1}.")) {
			inputCheckResult = -3;
		} else if (!account.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
			inputCheckResult = -4;
		} 
		
		/* 使用者密碼 */
		if (password.equals("")) {
			inputCheckResult = 2;
		} else if (password.length() < 8) {
			inputCheckResult = 3;
		} else if (password.length() > 20) {
			inputCheckResult = 4;
		} else if (password.matches("[1-9]{1}.")) {
			inputCheckResult = 5;
		} else if (!password.matches("[a-zA-Z]{1}[0-9a-zA-Z]{7,19}")) {
			inputCheckResult = 6;
		} 
		
		return inputCheckResult;
	}
	
	public String doUpdateDataInputCheck(WebUserData userData, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		String updateResultMessage = "";
		Integer count = 0;
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 檢查JavaBean物件 */
		if (userData == null) {
			updateResultMessage = "未登入系統，請登入後再進行操作！";
		} else if (userData.getStatus().equals("quit")) {
			updateResultMessage = "本帳號無法使用此功能";
		}
		
		/* 檢查姓氏 */
		String firstName = request.getParameter("updatedFirstName").trim();
		if (updateResultMessage.equals("")) {
			if (firstName.equals("")) {
				updateResultMessage = "姓氏不可為空白";
			} else if (firstName.length() > 3) {
				updateResultMessage = "姓氏長度過長，最多僅3個字元";
			} else {
				Integer charCountBegin = 0;
				Boolean firstNameIsOk = true;
				/* 16進位表示 */
				Integer charChineseWordCountBegin = 0x4e00;
				Integer charChineseWordCountEnd = 0x9fff;
				
				for (Integer charIndex = charCountBegin; charIndex < firstName.length(); charIndex++) {
					int firstNameChar = firstName.charAt(charIndex);
					
					if (firstNameChar < charChineseWordCountBegin || firstNameChar > charChineseWordCountEnd) {
						firstNameIsOk = false;
					}
					if (!firstNameIsOk) {
						break;
					}
				}
				
				if (!firstNameIsOk) {
					updateResultMessage = "姓氏中含有非中文";
				} else if (firstName.equals(userData.getFirstName())){
					count++;
				}
			}
		}
		
		/* 檢查名字 */
		String lastName = request.getParameter("updatedLastName").trim();
		if (updateResultMessage.equals("")) {
			if (lastName.equals("")) {
				updateResultMessage = "名字不可為空白";
			} else if (lastName.length() > 3) {
				updateResultMessage = "名字長度過長，最多僅3個字元";
			} else {
				Integer charCountBegin = 0;
				Boolean lastNameIsOk = true;
				/* 16進位表示 */
				Integer charChineseWordCountBegin = 0x4e00;
				Integer charChineseWordCountEnd = 0x9fff;
				
				for (Integer charIndex = charCountBegin; charIndex < lastName.length(); charIndex++) {
					int lastNameChar = lastName.charAt(charIndex);
					
					if (lastNameChar < charChineseWordCountBegin || lastNameChar > charChineseWordCountEnd) {
						lastNameIsOk = false;
					}
					if (!lastNameIsOk) {
						break;
					}
				}
				
				if (!lastNameIsOk) {
					updateResultMessage = "名字中含有非中文";
				} else if (lastName.equals(userData.getLastName())){
					count++;
				}
			}
		}
		
		/* 檢查稱呼 */
		String nickname = request.getParameter("updatedNickname").trim();
		if (updateResultMessage.equals("")) {
			if (nickname.equals("") && lastName.equals("")) {
				updateResultMessage = "稱呼不可為空白";
			} else if (nickname.equals("") && !lastName.equals("")) {
				nickname = lastName;
			} else if (nickname.length() > 20){
				updateResultMessage = "稱呼長度過長";
			} else if (nickname.equals(userData.getNickname())){
				count++;
			}
		}
		
		/* 檢查偏好食物 */
		String fervor = request.getParameter("updatedFervor").trim();
		if (updateResultMessage.equals("")) {
			if (fervor.equals("")) {
				updateResultMessage = "偏好食物不可為空白";
			} else if (fervor.length() > 50){
				updateResultMessage = "偏好食物長度過長";
			} else if (fervor.equals(userData.getFervor())) {
				count++;
			}
		}
		
		/* 檢查email */
		String email = request.getParameter("updatedEmail").trim();
		if (updateResultMessage.equals("")) {
			if (email.equals("")) {
				updateResultMessage = "信箱資訊不可為空白";
			} else if(email.indexOf("@") == -1 || email.split("@").length > 2 || email.indexOf(" ") != -1) {
				updateResultMessage = "信箱資訊格式錯誤";
			} else if (email.equals(userData.getEmail())) {
				count++;
			} else {
				Integer emailCheckResult = -1;
				
				/* 調用服務裡的方法 */
				try {
					emailCheckResult = wus.checkEmailExist(email);
				} catch (SQLException sqlE) {
					updateResultMessage = sqlE.getMessage();
				}
				
				if (emailCheckResult == 1){
					updateResultMessage = "該聯絡信箱已被註冊，請挑選別的聯絡信箱";
				} 
			}
		}
		
		/* 檢查聯絡電話 */
		String phone = request.getParameter("updatedPhone").trim();
		if (updateResultMessage.equals("")) {
			if (phone.equals("")) {
				updateResultMessage = "連絡電話不可為空白";
			} else if(phone.length() < 9 || phone.indexOf(" ") != -1) {
				updateResultMessage = "連絡電話格式錯誤";
			} else if (!phone.matches("[0]{1}[2-9]{1}[0-9]{7,9}")) {
				updateResultMessage = "連絡電話格式錯誤";
			} else if (phone.substring(0, 2).equals("09") && phone.length() != 10) {
				updateResultMessage = "行動電話格式錯誤";
			} else if (!phone.substring(0, 2).equals("09") && phone.length() == 10) {
				updateResultMessage = "室內電話格式錯誤";
			} else if (phone.equals(userData.getPhone())) {
				count++;
			}
		}
		
		/* 檢查接收促銷/優惠訊息 */
		String getEmail = request.getParameter("updatedGetEmail").trim();
		if (updateResultMessage.equals("")) {
			if (getEmail.equals(userData.getGetEmail())) {
				count++;
			} else if (!getEmail.equals("Y") && !getEmail.equals("N")){ 
				updateResultMessage = "接收促銷/優惠訊息輸入異常";
			}
		}
		
		/* 檢查區住區域 */
		String locationCode = request.getParameter("updatedLocationCode").trim();
		if (updateResultMessage.equals("")) {
			if (locationCode.equals(userData.getLocationCode())) {
				count++;
			} else {	
				switch(locationCode) {
					case "t01":
					case "t02":
					case "t03":
					case "t04":
					case "t05":
					case "t06":
					case "t07":
					case "t08":
					case "t09":
					case "t10":
					case "t11":
					case "t12":
					case "t13":
					case "t14":
					case "t15":
					case "t16":
					case "t19":
					case "t20":
					case "t21":
					case "t22":
					case "t23":
						break;
					default:
						updateResultMessage = "居住區域設定異常";
						break;
				}
			}
		}
		
		/* 檢查地點一 */
		String addr0 = request.getParameter("updatedAddr0").trim();
		String addr1 = request.getParameter("updatedAddr1").trim();
		String addr2 = request.getParameter("updatedAddr2").trim();
		
		if (updateResultMessage.equals("")) {
			if (addr0.equals("")) {
				updateResultMessage = "生活地點一不可為空白";
			} else if (addr0.length() > 65) {
				updateResultMessage = "生活地點一超過長度限制";
			} else if (addr0.equals(addr1) || addr0.equals(addr2)) {
				updateResultMessage = "生活地點重複填寫";
			} else if (addr0.equals(userData.getAddr0())) {
				count++;
			}
		}
		
		/* 檢查地點二 */
		if (updateResultMessage.equals("")) {
			if (addr1.length() > 65) {
				updateResultMessage = "生活地點二超過長度限制";
			} else if (addr1.equals(addr0) || (addr1.equals(addr2) && !addr2.equals(""))) {
				updateResultMessage = "生活地點重複填寫";
			} else if (addr1.equals(userData.getAddr1())) {
				count++;
			}
		}
		
		/* 檢查地點三 */
		if (updateResultMessage.equals("")) {
			if (addr2.length() > 65) {
				updateResultMessage = "生活地點三超過長度限制";
			} else if (addr2.equals(addr0) || (addr2.equals(addr1) && !addr1.equals(""))) {
				updateResultMessage = "生活地點重複填寫";
			} else if (addr2.equals(userData.getAddr2())) {
				count++;
			}
		}
		
		/* 結算有效變動項目 */
		if (count == 11) {
			updateResultMessage = "沒有輸入任何有效的修改內容，請重新操作";
		}
		
		return updateResultMessage;
	}
	
	public String doUpdatePasswordInputCheck(WebUserData userData, String password, String confirmPassword) {
		String updateResultMessage = "";
		
		if (userData == null) {
			updateResultMessage = "未登入系統，請登入後再進行操作！";
		} else if (userData.getStatus().equals("quit")) {
			updateResultMessage = "本帳號無法使用此功能";
		} else {
			String oldPassword = userData.getPassword();
			
			if (!password.equals(confirmPassword)) {
				updateResultMessage = "密碼與確認密碼不一致！";
			} else if (password.equals(oldPassword)){
				updateResultMessage = "密碼未修改！";
			}
		}
		
		return updateResultMessage;
	}
	
	public String doSelectUserDataInputCheck(String selectedParameters, WebUserData userData) {
		String checkResult = "";
		Integer count = 0;
		
		if (userData == null) {
			checkResult = "使用者未登入系統！請登入後再嘗試";
		} else if (userData.getStatus().equals("quit")) {
			checkResult = "本帳號無法使用此功能";
		}
		
		String selectedAccount = selectedParameters.split(":")[0];
		if (checkResult.equals("")) {
			if (selectedAccount.length() > 20) {
				checkResult = "搜尋的帳號名稱過長！";
			} else if (!selectedAccount.matches("[0-9a-zA-Z]{1,20}")) {
				checkResult = "搜尋的帳號含有無效字元！";
			} else if(selectedAccount.equals("?")) {
				count++;
			}
		}
		
		String selectedNickname = selectedParameters.split(":")[1];
		if (checkResult.equals("")) {
			if (selectedNickname.equals("?")) {
				count++;
			} else if (selectedNickname.length() > 20) {
				checkResult = "搜尋的稱呼名稱過長！";
			}
		}
		
		String selectedFervor = selectedParameters.split(":")[2];
		if (checkResult.equals("")) {
			if (selectedFervor.equals("?")) {
				count++;
			}
		}
		
		String selectedLocationCode = selectedParameters.split(":")[3];
		if (checkResult.equals("")) {
			if (!selectedLocationCode.equals("?"))  {
				switch(selectedLocationCode) {
					case "t01":
					case "t02":
					case "t03":
					case "t04":
					case "t05":
					case "t06":
					case "t07":
					case "t08":
					case "t09":
					case "t10":
					case "t11":
					case "t12":
					case "t13":
					case "t14":
					case "t15":
					case "t16":
					case "t19":
					case "t20":
					case "t21":
					case "t22":
					case "t23":
						break;
					default:
						checkResult = "居住區域設定異常";
						break;
				}
			}
		}
		
		if (count == 4) {
			checkResult = "至少需填寫/選擇一項條件才能執行特定搜尋！";
		}
		
		return checkResult;
	}
}
