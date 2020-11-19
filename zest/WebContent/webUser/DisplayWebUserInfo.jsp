<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", -1); // 防止proxy server進行快取
%>
<!-- taglib宣告 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- taglib宣告 -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>註冊資料確認</title>
		<link rel="stylesheet" href="styles/WebUserRegisterForm.css">
	</head>
	<body>
		<!-- 將放於Session中的JavaBean取出，class寫包含package的全名，scope設為session -->
		<jsp:useBean id="reg_webUser" class="webUser.WebUserBean"
			scope="session" />
		<form action="/zest/webUser/WebUserServlet" method="post">
			<fieldset>
				<legend>註冊資料如下，如果無誤請按「確認」</legend>
				<hr />
				<label>帳號名稱：</label>
				<jsp:getProperty name="reg_webUser" property="account" />
				<hr />
				<label>帳號密碼：</label>
				<c:if test="${param.password.length() > 0}">
					<c:forEach var="passwordChar" begin="0" end="${param.password.length()-1}">
						<c:out value = "*" />
					</c:forEach>
				</c:if>
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
				<c:choose>
					<c:when test="${param.gender=='M'}">男性</c:when>
					<c:when test="${param.gender=='F'}">女性</c:when>
					<c:when test="${param.gender=='N'}">不方便提供</c:when>
				</c:choose>
				<hr />
				<label>西元生日：</label>
				<jsp:getProperty name="reg_webUser" property="birthday" />
				<hr />
				<label>偏好食物：</label>
				<jsp:getProperty name="reg_webUser" property="fervor" />
				<hr />
				<label>聯絡信箱：</label>
				<jsp:getProperty name="reg_webUser" property="email" />
				<hr />
				<label>聯絡電話：</label>
				<jsp:getProperty name="reg_webUser" property="phone" />
				<hr />
				<label>是否願意接收促銷/優惠訊息：</label>
				<c:choose>
					<c:when test="${param.get_email==true}">願意</c:when>
					<c:when test="${param.get_email==false}">不願意</c:when>
				</c:choose>
				<hr />
				<label>居住區域：</label>
				<c:choose>
					<c:when test="${param.location_code=='t01'}">臺北市</c:when>
					<c:when test="${param.location_code=='t02'}">新北市</c:when>
					<c:when test="${param.location_code=='t03'}">桃園市</c:when>
					<c:when test="${param.location_code=='t04'}">臺中市</c:when>
					<c:when test="${param.location_code=='t05'}">臺南市</c:when>
					<c:when test="${param.location_code=='t06'}">高雄市</c:when>
					<c:when test="${param.location_code=='t07'}">基隆市</c:when>
					<c:when test="${param.location_code=='t08'}">新竹市</c:when>
					<c:when test="${param.location_code=='t09'}">嘉義市</c:when>
					<c:when test="${param.location_code=='t10'}">新竹縣</c:when>
					<c:when test="${param.location_code=='t11'}">苗栗縣</c:when>
					<c:when test="${param.location_code=='t12'}">彰化縣</c:when>
					<c:when test="${param.location_code=='t13'}">南投縣</c:when>
					<c:when test="${param.location_code=='t14'}">雲林縣</c:when>
					<c:when test="${param.location_code=='t15'}">嘉義縣</c:when>
					<c:when test="${param.location_code=='t16'}">屏東縣</c:when>
					<c:when test="${param.location_code=='t17'}">宜蘭縣</c:when>
					<c:when test="${param.location_code=='t18'}">花蓮縣</c:when>
					<c:when test="${param.location_code=='t19'}">臺東縣</c:when>
					<c:when test="${param.location_code=='t20'}">澎湖縣</c:when>
					<c:when test="${param.location_code=='t21'}">金門縣</c:when>
					<c:when test="${param.location_code=='t22'}">連江縣</c:when>
					<c:when test="${param.location_code=='t23'}">其他區</c:when>
				</c:choose>
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
				<input type="submit" name="register" value="確認">
				<input type="submit" name="register" value="取消">
			</div>
		</form>
	</body>
</html>