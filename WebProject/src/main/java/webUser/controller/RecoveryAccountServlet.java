package webUser.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webUser.service.WebUserService;
import webUser.service.WebUserServiceHibernate;

@WebServlet("/webUser/RecoveryAccountServlet")
public class RecoveryAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/* 預先宣告response/request設定所需的部分 */   
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String CHARSET_CODE = "UTF-8";
	
    public RecoveryAccountServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 參數 */
		Boolean infoIsOk = true;
		String recoveryResult = "";
		
		/* 從URL取參數 */
		String timeRecord = (request.getParameter("ts") == null) ? "" : request.getParameter("ts").trim();
		String checkCode = (request.getParameter("key") == null) ? "" : request.getParameter("key").trim();
		String userId = (request.getParameter("userId") == null) ? "" : request.getParameter("userId").trim();
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 檢查時戳 */
		if (timeRecord.equals("")) {
			recoveryResult = "驗證無效";
			infoIsOk = false;
		} else if (timeRecord.indexOf("_") == -1 && timeRecord.split("_").length != 2) {
			recoveryResult = "驗證無效";
			infoIsOk = false;
		} else {
			String createDate = timeRecord.split("_")[0];
			String createTime = timeRecord.split("_")[1];
			LocalDate inputDate;
			
			/* 轉型 */
			try {
				inputDate = LocalDate.parse(createDate);
				
				/* 驗證連結裡的日期只可能為當日或昨日才有效 */
				if (inputDate.equals(LocalDate.now()) || inputDate.plusDays(1L).equals(LocalDate.now())) {
					infoIsOk = true;
				} else {
					infoIsOk = false;
					recoveryResult = "驗證已無效";
				}
				
				/* 轉型 */
				try {
					LocalTime.parse(createTime);
				} catch (Exception e2) {
					infoIsOk = false;
					recoveryResult = e2.getMessage();
				}
				
				/* 假設有效期為1個小時 */
				/* 唯有晚間11時會遇到跨日問題 */
				if (infoIsOk 
						&& !createTime.split(":")[0].equals("23") 
						&& inputDate.equals(LocalDate.now())) {
					if (LocalTime.parse(createTime).plus(1, ChronoUnit.HOURS).isAfter(LocalTime.now())) {
						infoIsOk = true;
					} else {
						infoIsOk = false;
						recoveryResult = "驗證已無效";
					}
				} else if (infoIsOk 
						&& createTime.split(":")[0].equals("23") 
						&& inputDate.plusDays(1L).equals(LocalDate.now())) {
					if (LocalTime.parse(createTime).plus(1, ChronoUnit.HOURS).isAfter(LocalTime.now())) {
						infoIsOk = true;
					} else {
						infoIsOk = false;
						recoveryResult = "驗證已無效";
					}
				}
				
			} catch (Exception e1) {
				infoIsOk = false;
				recoveryResult = e1.getMessage();
				System.out.println("ts error");
			}
		}
		
		/* 檢查隨機碼 */
		if (infoIsOk) {
			if (checkCode.equals("")) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的驗證連結";
			} else if (checkCode.length() != 8) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的驗證連結";
			} else if (!checkCode.matches("[0-9a-zA-Z]{8}")) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的驗證連結";
			}
		}
		
		/* 檢查userId */
		if (infoIsOk) {
			if (userId.equals("")) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的帳號";
			} else if (userId.length() != 7) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的帳號";
			} else if (!userId.matches("[0-2]{1}[0-9]{6}")) {
				infoIsOk = false;
				recoveryResult = "驗證失敗，無效的帳號";
			} else {
				/* 驗證有效性 */
				Integer checkIdResult1 = 0;
				Integer checkIdResult2 = 0;
				
				try {
					checkIdResult1 = wus.checkUserIdExist(userId);
					checkIdResult2 = wus.checkUserIdQuit(userId);
				} catch (SQLException sqlE) {
					infoIsOk = false;
					recoveryResult = sqlE.getMessage();
				}
				
				if (checkIdResult1 == 1 && checkIdResult2 == 1) {
					infoIsOk = true;
				} else if (checkIdResult1 == 1 && checkIdResult2 == 0){
					infoIsOk = false;
					recoveryResult = "驗證失敗，該帳號已棄用";
				} else if (checkIdResult1 == 0) {
					infoIsOk = false;
					recoveryResult = "驗證失敗，無效的帳號";
				}
			}
		}
		
		/* 最終結果 */
		if (!recoveryResult.equals("")) {
			/* 無效session */
			request.getSession(true).invalidate();
			/* 將錯誤訊息存在session中 */
			request.getSession(true).setAttribute("loginMessage", recoveryResult);
			/* 導向到登入畫面 */
			response.sendRedirect(request.getContextPath() + "/webUser/WebUserLogin.jsp");
		} else {
			/* 將Id存在session中 */
			request.getSession(true).setAttribute("userId", userId);
			/* 導向設定密碼畫面 */
			request.getRequestDispatcher("/webUser/WebUserResetPassword.jsp").forward(request, response);
		}
	}
}
