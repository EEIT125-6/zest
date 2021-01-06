<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid  header"
	style="height: 100px; border-bottom: 3px solid #e76f51; height: 90px; padding-top: 5px; background-color: #003049">
	<div class="container">
		<!--<img src="Images/LOGO1-removebg-preview.png" -->
		<%--<c:set var = "logo" value = "/Images/LOGO1-removebg-preview.png"/> --%>
		<!--<img src="photo/<c:out value = "${logo }"/>" -->
		<!--<img src="Images/LOGO1-removebg-preview.png" -->
		<a href="${pageContext.request.contextPath}/"> 
			<c:set var="logo" value="LOGO1-removebg-preview.png" /> 
			<img src="${pageContext.request.contextPath}/Images/LOGO1-removebg-preview.png"
				style="float: left; height: 70px;"></a>
			<%-- 		<img src="${pageContext.request.contextPath}/xun/photo/${logo}" --%>
			<p style="text-align: right; font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650; float: right">
			<br> 
			<c:if test="${userFullData.account == null}">
			<i class="fas fa-user" style="font-size: 25px; color: yellow"></i>
				<a href="${pageContext.request.contextPath}/WebUserLogin">
				登入 | 
				</a>
			</c:if>
			<c:if test="${userFullData.account != null}">
				<a href="${pageContext.request.contextPath}/webUser/WebUserMain">
				<c:if test="${userFullData.iconUrl == ''}">
					<c:if test="${userFullData.accountLv.lv == -1}">
						<i class="fas fa-chess-rook" style="font-size: 25px; color: yellow"></i>
					</c:if>
					<c:if test="${userFullData.accountLv.lv == 0}">
						<c:if test="${userFullData.gender.genderCode == 'M'}">
							<i class="fas fa-chess-king" style="font-size: 25px; color: yellow"></i>
						</c:if>
						<c:if test="${userFullData.gender.genderCode == 'N'}">
							<i class="fas fa-chess" style="font-size: 25px; color: yellow"></i>
						</c:if>
						<c:if test="${userFullData.gender.genderCode == 'W'}">
							<i class="fas fa-chess-queen" style="font-size: 25px; color: yellow"></i>
						</c:if>
					</c:if>
					<c:if test="${userFullData.accountLv.lv == 1}">
						<i class="fas fa-chess-bishop" style="font-size: 25px; color: yellow"></i>
					</c:if>
 				</c:if>
 				<c:if test="${userFullData.iconUrl != ''}">
 					<img src="<c:url value='${userFullData.iconUrl}' />" width="25" height="25" >
 				</c:if>
				<c:out value="${userFullData.account}" /> |
				</a>
			</c:if>
			<c:if test="${userFullData.account == null}">
			<i class="fas fa-user-plus" style="font-size: 25px; color: yellow"></i>
			<a href="${pageContext.request.contextPath}/WebUserRegisterForm">
			註冊 |
			</a>
			</c:if>
			<c:if test="${userFullData.account != null}">
			<i class="fas fa-door-open" style="font-size: 25px;color: yellow"></i>
			<a href="${pageContext.request.contextPath}/webUser/controller/WebUserMain/Logout">
			登出 |
			</a>
			</c:if>
			<!-- 			<img src="Images/PLZPLZ-removebg-preview.png" -->
			<!-- 				class="shopcar" style="height: 40px; margin: 0; margin-left: 5px;"> -->
			<i class="fas fa-shopping-cart" style="font-size: 25px; color: yellow"></i>
			<a href="${pageContext.request.contextPath}/controller/checkMemberStatus"> 
			購物車 |
			</a> 
			<c:if test="${userFullData.account != null}">
			<i class="fas fa-calendar-check" style="font-size: 25px; color: yellow"></i>
			<a href="${pageContext.request.contextPath}/booking/Page1">
			確認訂位 |
			</a>
			</c:if>
			<c:if test="${userFullData.account != null}">
			<i class="fas fa-address-book" style="font-size: 25px; color: yellow"></i>
			<a href="${pageContext.request.contextPath}/orange/ShowComment">
			查詢留言 |
			</a>
			</c:if>
		</p>
	</div>

	<!-- ============================================ -->
<!-- 	<div class="container"> -->
<%-- 		<a href="${pageContext.request.contextPath}/StoreIndexServlet"><img --%>
<%-- 			src="${pageContext.request.contextPath}/Images/LOGO1-removebg-preview.png" --%>
<!-- 			style="float: left; height: 70px;"></a> -->
<!-- 		<p -->
<!-- 			style="text-align: right; font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;; float: right"> -->
<!-- 			<br> <i class="fas fa-user" -->
<!-- 				style="font-size: 25px; color: yellow"></i> -->
<%-- 			<c:if test="${userFullData.account == null}"> --%>
<!-- 				<a -->
<%-- 					href="${pageContext.request.contextPath}/webUser/WebUserLogin.jsp"> --%>
<!-- 					登入 |</a> -->
<%-- 			</c:if> --%>
<%-- 			<c:if test="${userFullData.account != null}"> --%>
<%-- 				<a href="${pageContext.request.contextPath}/webUser/WebUserMain.jsp"> --%>
<%-- 					<c:out value="${userFullData.account}" /> | --%>
<!-- 				</a> -->
<%-- 			</c:if> --%>
<%-- 			<c:if test="${userFullData.account == null}"> --%>
<!-- 				<i class="fas fa-user-plus" style="font-size: 25px; color: yellow"></i> -->
<!-- 				<a -->
<%-- 					href="${pageContext.request.contextPath}/webUser/WebUserRegisterForm.jsp"> --%>
<!-- 					註冊 |</a> -->
<%-- 			</c:if> --%>
<%-- 			<c:if test="${userFullData.account != null}"> --%>
<!-- 				<i class="fas fa-door-open" style="font-size: 25px; color: yellow"></i> -->
<!-- 				<a -->
<%-- 					href="${pageContext.request.contextPath}/webUser/WebUserLogoutManual.jsp"> --%>
<!-- 					登出 |</a> -->
<%-- 			</c:if> --%>
<!-- 			<!--    <img src="Images/PLZPLZ-removebg-preview.png" -->
<!-- 			<!--     class="shopcar" style="height: 40px; margin: 0; margin-left: 5px;"> -->
<!-- 			<i class="fas fa-shopping-cart" -->
<!-- 				style="font-size: 25px; color: yellow"></i> <a -->
<%-- 				href="${pageContext.request.contextPath}/product/index.jsp"> --%>
<!-- 				前往購物 |</a> <i class="fa fa-mobile-phone" -->
<!-- 				style="font-size: 25px; color: yellow"></i> <a -->
<%-- 				href="${pageContext.request.contextPath}/booking/Page1.jsp"> --%>
<!-- 				查詢訂位 |</a> -->
<!-- 		</p> -->
<!-- 	</div> -->