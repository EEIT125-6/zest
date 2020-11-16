package webUser;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/webUser/WebUser/RegisterServlet")
public class WebUserRegisterServlet extends HttpServlet {
	/* IDE能自動協助生成的項目，並不會使用到 */
	private static final long serialVersionUID = 1L;
	
	/* 預先宣告response/request設定所需的部分 */
	private static final String CONTENT_TYPE = "text/html; charset=UTF-8";
	private static final String CHARSET_CODE = "UTF-8";

	/* 初始化 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
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
		if (request.getParameter("checkAccount") != null) {
			/* 返回查詢結果給使用者確認 */
			doCheckAccount(request, response);
		} else if (request.getParameter("submit") != null) {
			/* 返回資料給使用者確認 */
			doSubmit(request, response);
		} else if (request.getParameter("confirm") != null) {
			/* 準備將資料傳入DB */
			doConfirm(request, response);
		} else if (request.getParameter("undo") != null) {
			/* 清除資料並返回註冊畫面 */
			doUndo(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	/* checkAccount */
	public void doCheckAccount(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (!request.getParameter("account").trim().equals("")) {
			
		} else {
			
		}
	}
	
	/* submit */
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

	/* confirm */
	public void doConfirm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
	
	/* undo */
	public void doUndo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/* 無效session */
		request.getSession(true).invalidate();
		/* 返回註冊畫面 */
		request.getRequestDispatcher("/webUser/WebUserRegisterForm.jsp").forward(request,response);
	}
}
