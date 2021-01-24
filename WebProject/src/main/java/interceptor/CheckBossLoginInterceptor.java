package interceptor;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import webUser.model.WebUserData;

public class CheckBossLoginInterceptor extends HandlerInterceptorAdapter {
	List<String> url = Arrays.asList(
			"/InsertStore",
			"/storeBack",
			"/storeSt",
			"/storeAdClick",
			"/storeAdProduct"
			);
	
	String servletPath;
	String contextPath;
	String requestURI;
	
	public CheckBossLoginInterceptor()  {
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
		/* 必須登入者 */
		if (mustBossLogin()) {
			/* 已登入 */
			if (checkBossLogin(request)) {   
				byPass = true;
			/* 未登入 */
			} else {				
				HttpSession session = request.getSession();
				if (!isRequestedSessionIdValid) {
					;
				} else {
					/* 記住原本的要前往的網址，稍後如果登入成功，系統可以自動轉入原本要執行的程式。 */
					session.setAttribute("requestURI", requestURI);	
				}
				response.sendRedirect(contextPath + "/WebUserLogin");
				byPass = false;
			}
		/* 無須登入者，直接執行 */
		} else {   
			byPass = true;
		}
		return byPass;
	}
	/* 判斷Session物件內是否含有特定識別字串的屬性物件，如果有，表示已經登入，否則代表未登入 */
	private boolean checkBossLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		/* 取出指定物件 */
		WebUserData userData = (WebUserData) session.getAttribute("userFullData");
		if (userData == null) {
			return false;
		} else if(userData.getAccountLv().getLv() != 1){
			return false;
		} else if(!userData.getUserId().startsWith("2")){
			return false;
		}
		return true;
	}
	/* 如果請求的ServletPath的前導字是以某個必須登入才能使用之資源的路徑，那就必須登入。 */
	private boolean mustBossLogin() {
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
