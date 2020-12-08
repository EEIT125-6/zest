package webUser.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import webUser.model.WebUserData;
import webUser.service.WebUserService;
import webUser.service.WebUserServiceHibernate;

@WebServlet("/webUser/ManageWebUserServlet")
public class ManageWebUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/* 預先宣告response/request設定所需的部分 */   
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String CHARSET_CODE = "UTF-8";
	
    public ManageWebUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 訊息 */
		String operateMessage = "";
		
		/* 透過網址列GET方式取得參數 */
		String account = request.getParameter("account");
		
		/* 產生服務物件 */
		WebUserService wus = new WebUserServiceHibernate();
		
		/* 指定的使用者資料 */
		WebUserData managedUserData = new WebUserData();
		
		/* 調用服務裡的方法 */
		try {
			managedUserData = wus.getWebUserData(account);
		} catch (SQLException sqlE) {
			operateMessage = sqlE.getMessage();
		} 
		
		/* 成功 */
		if (operateMessage.equals("") && managedUserData != null) {
			/* 將物件managedUserData以"managedUserData"之名存於request中 */
			request.setAttribute("managedUserData", managedUserData);
			/* 前往單一帳號詳細頁 */
			request.getRequestDispatcher("/webUser/DisplayManagedUserData.jsp").forward(request, response);
		} else {
			/* 將物件operateMessage以"selectResultMessage"之名存於request中 */
			request.setAttribute("selectResultMessage", operateMessage);
			/* 返回搜尋畫面 */
			request.getRequestDispatcher("/webUser/WebUserSearchForm.jsp").forward(request, response);
		}
	}
}
