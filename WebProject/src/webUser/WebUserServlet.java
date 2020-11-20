package webUser;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

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
	        // 使用JNDI API找到DataSource，最後面的名稱請跟web.xml上的<res-ref-name>以及context.xml上的<Resource name>一致
	        ds0 = ( DataSource ) ctxt0.lookup("java:comp/env/jdbc/zest");
	        // 向DataSource要Connection
	        conn0 = ds0.getConnection();

	    } catch (NamingException nE) {
        	System.out.println("Naming Service Lookup Exception");
        } catch (SQLException sqlE) {
        	System.out.println("Database Connection Error"); 
        }
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);

		/* 防止快取設定 */
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", -1); // 防止proxy server進行快取
		
        /* 根據取到的參數判定是首次送出資料還是已確認過 */
		if (request.getParameter("register") != null) {
			switch (request.getParameter("register")){
				case "檢查帳號":
					/* 返回查詢結果給使用者確認 */
					doCheckAccount(request, response);
					break;
				case "送出":
					/* 返回資料給使用者確認 */
					doRegisterSubmit(request, response);
					break;
				case "確認":
					/* 準備將資料傳入DB */
					doRegisterConfirm(request, response);
					break;
				/*返回註冊畫面*/
				case "取消":
				default:
					/* 清除資料並返回註冊畫面 */
					doRegisterUndo(request, response);
					break;
			}
		} else if (request.getParameter("login") != null) {
			switch (request.getParameter("login")) {
				/* 登入 */
				case "登入":
				default:
					/* 返回查詢結果 */
					doCheckLogin(request, response);
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
		
		/* 宣告欲回傳的參數*/
		int accountCheckResult = -1;
		String message = "";
		/* 宣告printer*/
		PrintWriter out=response.getWriter();
		/* 取得使用者輸入的參數 */
		String inputAccount = request.getParameter("inputAccount");
		try {
			/* 利用Connection產生DAO物件 */
			webUserDAO = (webUserDAO != null) ? webUserDAO : new WebUserJDBCDAO(conn0);
			/* 呼叫DAO方法，返回結果 */
			accountCheckResult = webUserDAO.checkAccountExist(inputAccount);
			message = "Success";
		} catch(SQLException sqlE) {
			message = sqlE.getMessage();
		}

		/* 將結果返回aJax */
		out.write(String.valueOf(accountCheckResult));
		out.write(","+message);
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
			int userNumber = webUserDAO.checkUserId(lv+1);
			/* 得出有幾個空白 */
			if (userNumber >= 0) {
				int zeroIndex = 6 - ((userNumber + 1) + "").length();
				String zeroNumber="";
				/* 空白填入"0" */
				for(int letterIndex = 0; letterIndex < zeroIndex; letterIndex++) {
					zeroNumber+="0";
				}
				newId = (userNumber >= 0) ? String.valueOf(lv+1)+zeroNumber+String.valueOf(userNumber + 1) : "";
				message = "Sucess";
			}
		} catch (SQLException sqlE) {
			message = sqlE.getMessage();
		}
		return newId+","+message;
	}
	
	/* Register submit */
	public void doRegisterSubmit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 參數宣告 */
		String user_id = "", account, password, first_name, last_name, nickname, fervor = "", email, phone, location_code, addr0, addr1, addr2;
		Character gender, get_email;
		LocalDate birthday, join_date;
		Integer lv = 0;
		BigDecimal zest = new BigDecimal("0");
		
		/* 先從request取值，前端提供手動輸入的欄位可能要使用trim()去除頭尾的空白 */
		account = request.getParameter("account").trim();
		password = request.getParameter("password").trim();
		first_name = request.getParameter("first_name").trim();
		last_name = request.getParameter("last_name").trim();
		nickname = request.getParameter("nickname").trim();
		gender = request.getParameter("gender").charAt(0);
		birthday = LocalDate.parse(request.getParameter("birthday"));
		for (int fervorIndex = 0; fervorIndex < request.getParameterValues("fervor").length; fervorIndex++) {
			if (!request.getParameterValues("fervor")[fervorIndex].equals("")) {
				if(fervor.length() > 0) {
					fervor += ",";
				}
				fervor += request.getParameterValues("fervor")[fervorIndex];
			} else {
				fervor += "";
			}
		}
		email = request.getParameter("email").trim();
		phone = request.getParameter("phone").trim();
		get_email = (request.getParameter("get_email").equals("Y")) ? 'Y' : 'N';
		location_code = request.getParameter("location_code");
		addr0 = request.getParameter("addr0").trim();
		addr1 = request.getParameter("addr1").trim();
		addr2 = request.getParameter("addr2").trim();
		/* 立即產生 */
		join_date = LocalDate.now();
		/* 使用JavaBean建構子 */
		WebUserBean reg_webUser = new WebUserBean(user_id, account, password, first_name, last_name, nickname, gender, birthday, fervor,
				email, phone, get_email, location_code, join_date, lv, addr0, addr1, addr2, zest);
		/* 嘗試建立Session，如果沒有就建立一個，並將物件reg_webUser以"reg_webUser"的名稱放入Session中 */
		request.getSession(true).setAttribute("reg_webUser", reg_webUser);
		/* 將request、response交棒給另一個jsp，並交出控制權 */
		request.getRequestDispatcher("/webUser/DisplayWebUserInfo.jsp").forward(request,response);
	}

	/* Register confirm */
	public void doRegisterConfirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 從session中取出物件reg_webUser */
		WebUserBean registerData = (WebUserBean)request.getSession(true).getAttribute("reg_webUser");
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
			/* 取得ID  */
			registerData.setUser_id(idResultSpace[0]);
			
			try {
				/* 執行新增 */
				boolean insertResult = webUserDAO.insertWebUser(registerData);
				/* 執行成功 */
				if (insertResult) {
					insertResultMessage = "恭喜！"+registerData.getFirst_name()+registerData.getLast_name()+"，您的帳號已成功建立";
					insertResultPage = "WebUserLogin.jsp";
				}
			} catch (SQLException sqlE) {
				insertResultMessage = "發生錯誤！" + sqlE.getMessage();
				
				/* 嘗試建立Session，並將訊息insertResultMessage以"insertResultMessage"的名稱放入新Session中 */
				request.getSession(true).setAttribute("insertResultMessage", insertResultMessage);
				/* 將訊息insertResultPage以"insertResultPage"的名稱放入新Session中 */
				request.getSession(true).setAttribute("insertResultPage", insertResultPage);
				/* 導向其他畫面 */
				request.getRequestDispatcher("/webUser/WebUserRegisterResult.jsp").forward(request,response);
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
		System.out.println("將被導向："+insertResultPage);
		request.getSession(true).setAttribute("insertResultPage", insertResultPage);
		/* 導向其他畫面 */
		request.getRequestDispatcher("/webUser/WebUserRegisterResult.jsp").forward(request,response);
	}
	
	/* Register undo */
	public void doRegisterUndo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 無效session */
		request.getSession(true).invalidate();
		/* 返回註冊畫面 */
		request.getRequestDispatcher("/webUser/WebUserRegisterForm.jsp").forward(request,response);
	}
	
	/* Login check */
	public void doCheckLogin(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);
		
		/* 宣告參數*/
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
		} catch(SQLException sqlE) {
			loginMessage = sqlE.getMessage();
		}
		
		/* 帳號存在才往下做 */
		if (accountCheckResult == 1) {
			try {
				checkResultSpace = webUserDAO.checkPassword(inputAccount, inputPassword).split(":");
				passwordCheckResult = Boolean.parseBoolean(checkResultSpace[0]);
				loginMessage = (passwordCheckResult) ? "Success" : "密碼錯誤！請檢查大小寫是否正確，或將類似的字元打錯";
			} catch(SQLException sqlE) {
				loginMessage = sqlE.getMessage();
				/* 嘗試建立Session，並將訊息loginMessage以"loginMessage"的名稱放入新Session中 */
				request.getSession(true).setAttribute("loginMessage", loginMessage);
				/* 導向其他畫面 */
				request.getRequestDispatcher("/webUser/WebUserLoginResult.jsp").forward(request,response);
			}
		}	else if (accountCheckResult == 0) {
			loginMessage = "該帳號不存在！請檢查大小寫是否正確，或將類似的字元打錯";
		}	
		
		if (!loginMessage.equals("Success")) {
			/* 嘗試建立Session，並將訊息loginMessage以"loginMessage"的名稱放入新Session中 */
			request.getSession(true).setAttribute("loginMessage", loginMessage);
			/* 導向其他畫面 */
			request.getRequestDispatcher("/webUser/WebUserLoginResult.jsp").forward(request,response);
		/* 帳號、密碼皆正確 */
		} else {
			if (checkResultSpace.length > 1) {
				userFullData.setUser_id(checkResultSpace[1]);
				userFullData.setAccount(checkResultSpace[2]);
				userFullData.setPassword(checkResultSpace[3]);
				userFullData.setFirst_name(checkResultSpace[4]);
				userFullData.setLast_name(checkResultSpace[5]);
				userFullData.setNickname(checkResultSpace[6]);
				userFullData.setGender(checkResultSpace[7].charAt(0));
				userFullData.setBirth(LocalDate.parse(checkResultSpace[8]));
				userFullData.setFervor(checkResultSpace[9]);
				userFullData.setEmail(checkResultSpace[10]);
				userFullData.setGet_email(checkResultSpace[11].charAt(0));
				userFullData.setLocation_code(checkResultSpace[12]);
				userFullData.setJoin_date(LocalDate.parse(checkResultSpace[13]));
				userFullData.setLv(Integer.parseInt(checkResultSpace[14]));
				userFullData.setAddr0(checkResultSpace[15]);
				userFullData.setAddr1(checkResultSpace[16]);
				userFullData.setAddr2(checkResultSpace[17]);
				userFullData.setZest(new BigDecimal(checkResultSpace[18]));
				
				loginMessage = "歡迎 "+ userFullData.getFirst_name() + userFullData.getLast_name() + " ！";
				/* 嘗試建立Session，並將Java Bean物件userFullData以"userFullData"的名稱放入新Session中 */
				request.getSession(true).setAttribute("userFullData", userFullData);
				/* 嘗試建立Session，並將訊息loginMessage以"loginMessage"的名稱放入新Session中 */
				request.getSession(true).setAttribute("loginMessage", loginMessage);
				/* 導向其他畫面 */
				request.getRequestDispatcher("/webUser/WebUserMain.jsp").forward(request,response);
			}
		}
	}
}
