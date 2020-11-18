package webUser;

import java.io.IOException;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class WebUserRegisterServlet extends HttpServlet {
	/* IDE能自動協助生成的項目，並不會使用到 */
	private static final long serialVersionUID = 1L;
	
	/* 預先宣告response/request設定所需的部分 */
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String CHARSET_CODE = "UTF-8";

	public WebUserRegisterServlet() {
        super();
    }
	
	/* 初始化 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		/* DataSource等項目初始化 */
		DataSource ds0 = null;
	    InitialContext ctxt0 = null;
	    Connection conn0 = null;
	    
	    try {
		    // 建立Context Object,連到JNDI Server
	        ctxt0 = new InitialContext(); 
	        // 使用JNDI API找到DataSource，最後面的名稱請跟web.xml上的<res-ref-name>以及context.xml上的<Resource name>一致
	        ds0 = ( DataSource ) ctxt0.lookup("java:comp/env/jdbc/zest");
	        // 向DataSource要Connection
	        conn0 = ds0.getConnection();
	        // 建立Database Access Object,負責Table的Access，實作介面
	        WebUserDAO webUserDAO = new WebUserJDBCDAO(conn0);

	    } catch (NamingException nE) {
        	System.out.println("Naming Service Lookup Exception");
        } catch (SQLException sqlE) {
        	System.out.println("Database Connection Error"); 
        } finally {
        	try {
				if (conn0 != null) {
					conn0.close();
				}
			} catch (SQLException sqlE0) {
				System.out.println("Database Connection Error"); 
			}
        }
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response, WebUserDAO webUserDAO)
			throws ServletException, IOException {
		/* 收/發資料前先設定request/response編碼 */
		request.setCharacterEncoding(CHARSET_CODE);
		response.setContentType(CONTENT_TYPE);

		/* 防止快取設定 */
		response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", -1); // 防止proxy server進行快取
	      
        /* 根據取到的參數判定是首次送出資料還是已確認過 */
		if (request.getParameter("registerCheckAccount") != null) {
			/* 返回查詢結果給使用者確認 */
			System.out.println("test0");
			doCheckAccount(request, response, webUserDAO);
		} else if (request.getParameter("registerSubmit") != null) {
			/* 返回資料給使用者確認 */
			doSubmit(request, response);
		} else if (request.getParameter("registerConfirm") != null) {
			/* 準備將資料傳入DB */
			doConfirm(request, response, webUserDAO);
		} else if (request.getParameter("registerUndo") != null) {
			/* 清除資料並返回註冊畫面 */
			doUndo(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public List<String> doGetLastId(WebUserDAO webUserDAO) 
			throws IOException {
		List<String> lastIdList = new ArrayList<>();
		
		return lastIdList;
	}
	
	/* Register checkAccount */
	public void doCheckAccount(HttpServletRequest request, HttpServletResponse response, WebUserDAO webUserDAO)
			throws ServletException, IOException {
		
	}
	
	/* Register submit */
	public void doSubmit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 參數宣告 */
		String user_id = "", account, password, first_name, last_name, nickname, fervor = "", email, phone, location_code, addr0, addr1, addr2;
		Character gender;
		LocalDate birthday, join_date;
		Boolean get_email;
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
		get_email = (request.getParameter("get_email").equals("true")) ? true : false;
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
	public void doConfirm(HttpServletRequest request, HttpServletResponse response, WebUserDAO webUserDAO)
			throws ServletException, IOException {

	}
	
	/* Register undo */
	public void doUndo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 無效session */
		request.getSession(true).invalidate();
		/* 返回註冊畫面 */
		request.getRequestDispatcher("/webUser/WebUserRegisterForm.jsp").forward(request,response);
	}
}
