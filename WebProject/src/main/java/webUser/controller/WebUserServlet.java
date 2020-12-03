package webUser.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

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
		int accountCheckResult = -1;
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
		int emailCheckResult = -1;
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
		birth = Date.valueOf(request.getParameter("birth"));
		
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
		WebUserData registerData = (WebUserData) request.getSession(true).getAttribute("reg_webUser");
		
		/* 設定要顯示的訊息 */
		String insertResultMessage = "";
		String insertResultPage = "WebUserRegisterForm.jsp";
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 宣告欲回傳的參數 */
		int insertResult = -1;
		
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
		response.sendRedirect(request.getContextPath()+"/webUser/WebUserRegisterResult.jsp");
	}
	
	/* Login check */
	public void doCheckLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 宣告參數 */
		int accountCheckResult = -3;
		String loginMessage = "";
		WebUserData userFullData = new WebUserData();
		
		/* 取得使用者輸入的參數 */
		String inputAccount = request.getParameter("account");
		String inputPassword = request.getParameter("password");
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
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
		/* 宣告要傳回的參數 */
		int deleteResult = -1;
		String quitMessage = "";
		String redirectPage = "";
		
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 從session中取出物件userFullData */
		WebUserData quitUserData = (WebUserData) request.getSession(true).getAttribute("userFullData");
		/* 設定使用者狀態 */
		quitUserData.setStatus("quit");
		
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
	
	/* undo */
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
}
