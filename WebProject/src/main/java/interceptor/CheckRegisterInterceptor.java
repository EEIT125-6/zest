package interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import webUser.model.WebUserData;

public class CheckRegisterInterceptor extends HandlerInterceptorAdapter {
	List<String> url = Arrays.asList(
			"/register/*"
			);
	
	String servletPath;
	String contextPath;
	String requestURI;
	
	public CheckRegisterInterceptor()  {
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean byPass = true;
		boolean isRequestedSessionIdValid = false;
		servletPath = request.getServletPath();  
		contextPath = request.getContextPath();
		requestURI  = request.getRequestURI();
		/* 檢查session是否逾時 */
		isRequestedSessionIdValid = request.isRequestedSessionIdValid();
		/* 必須有註冊物件 */
		if (mustRegister()) {
			/* 註冊階段 */
			if (checkRegister(request)) {   
				byPass = true;
			/* 非註冊階段 */
			} else {				
				HttpSession session = request.getSession();
				if ( ! isRequestedSessionIdValid ) {
					session.setAttribute("timeOut", "使用逾時，請重新註冊");
				} else {
					/* 記住原本的要前往的網址，稍後如果登入成功，系統可以自動轉入原本要執行的程式。 */
					session.setAttribute("requestURI", requestURI);	
				}
				response.sendRedirect(contextPath + "/WebUserRegisterForm");
				byPass = false;
			}
		/* 無須有註冊物件者，直接執行 */
		} else {   
			byPass = true;
		}
		return byPass;
	}
	/* 判斷Session物件內是否含有特定識別字串的屬性物件，如果有，表示已經登入，否則代表未登入 */
	private boolean checkRegister(HttpServletRequest request) {
		HttpSession session = request.getSession();
		/* 取出指定物件 */
		WebUserData reg_webUser = (WebUserData) session.getAttribute("reg_webUser");
		if (reg_webUser == null) {
			return false;
		} else {
			return true;
		}
	}
	/* 如果請求的ServletPath的前導字是以某個必須登入才能使用之資源的路徑，那就必須透過註冊取得物件。 */
	private boolean mustRegister() {
		boolean login = false;
		for (String sURL : url) {
			if (sURL.endsWith("*")) {
				/* 除去掉最後一個字元的剩餘字串 */
				sURL = sURL.substring(0, sURL.length() - 1); 
				/* 請求路徑位於被控管的目錄下 */
				if (servletPath.startsWith(sURL)) {
					login = true;
					break;
				}
			} else {
				/* 請求路徑位與被控管的路徑一致 */
				if (servletPath.equals(sURL)) {
					login = true;
					break;
				}
			}
		}
		return login;
	}
}
