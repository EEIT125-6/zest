<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
	response.setHeader("Pragma","no-cache"); // HTTP 1.0
	response.setDateHeader ("Expires", -1); // 防止proxy server進行快取
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>註冊資料確認</title>
<link rel="stylesheet" href="styles/WebUserRegisterForm.css">
</head>
<body>
<!-- 將放於Session中的JavaBean取出，class寫包含package的全名，scope設為session -->
<jsp:useBean id="reg_webUser" class="webUser.WebUserBean" scope="session" />
<form action="/zest/webUser/WebUserRegisterServlet" method="post">
	<fieldset>
		<legend>註冊資料如下，如果無誤請按「確認」</legend>
		<hr />
		<label>帳號名稱：</label> 
		<jsp:getProperty name="reg_webUser" property="account" />
		<hr />
		<label>中文姓氏：</label>
		<jsp:getProperty name="reg_webUser" property="first_name" />
		<hr />
		<label>中文名字：</label>
		<jsp:getProperty name="reg_webUser" property="last_name" />
		<hr />
		<label>稱呼方式：</label>
		<jsp:getProperty name="reg_webUser" property="nickname" />
		<hr />
		<label>生理性別：</label>
		<!--<c:choose>
			<c:when test="${gender == M}">男性</c:when>
			<c:when test="${gender == F}">女性</c:when>
			<c:otherwise>不方便提供</c:otherwise>
		</c:choose>-->
		<jsp:getProperty name="reg_webUser" property="gender" />
		<hr />
		<label>西元生日：</label>
		<jsp:getProperty name="reg_webUser" property="birthday" />
		<hr />
		<label>偏好食物：</label>
		<jsp:getProperty name="reg_webUser" property="fervor" />
		<hr />
		<label>是否願意接收促銷/優惠訊息：</label>
		<!--<c:choose>
			<c:when test="${get_email == false}">不願意</c:when>
			<c:otherwise>願意</c:otherwise>
		</c:choose>-->
		<jsp:getProperty name="reg_webUser" property="get_email" />
	    <hr />
	    <label>居住區域：</label>
		<!--<c:choose>
			<c:when test="${location_code == 't01'}">臺北市</c:when>
			<c:otherwise>其他區</c:otherwise>
		</c:choose>-->
		<jsp:getProperty name="reg_webUser" property="location_code" />
	    <hr />
	    <label>生活地點一：</label>
		<jsp:getProperty name="reg_webUser" property="addr0" />
		<hr />
		<label>生活地點二：</label>
		<jsp:getProperty name="reg_webUser" property="addr1" />
		<hr />
		<label>生活地點三：</label>
		<jsp:getProperty name="reg_webUser" property="addr2" />
		<hr />
	</fieldset>
	<div align="center">
		<input type="submit" name="confirm" value="確認">
	</div>
</form>
</body>
</html>