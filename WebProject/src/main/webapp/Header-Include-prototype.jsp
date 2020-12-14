<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="container-fluid  header"
	style="height: 100px; border-bottom: 3px solid #e76f51; height: 90px; padding-top: 5px; background-color: #003049">
	<div class="container">
		<a href="${pageContext.request.contextPath}/StoreIndexServlet"><img
			src="${pageContext.request.contextPath}/Images/LOGO1-removebg-preview.png"
			style="float: left; height: 70px;"></a>
		<p
			style="text-align: right; font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;; float: right">
			<br>
			<i class="fas fa-user" style="font-size: 25px;color: yellow"></i>
			<c:if test="${userFullData.account == null}">
			<a href="${pageContext.request.contextPath}/webUser/WebUserLogin.jsp">
			登入
			  |</a>
			</c:if>
			<c:if test="${userFullData.account != null}">
			<a href="${pageContext.request.contextPath}/webUser/WebUserMain.jsp">
			<c:out value="${userFullData.account}" />
			  |</a>
			</c:if>
			<c:if test="${userFullData.account == null}">
			<i class="fas fa-user-plus" style="font-size: 25px;color: yellow"></i>
			<a href="${pageContext.request.contextPath}/webUser/WebUserRegisterForm.jsp">
			 註冊
			  |</a>
			</c:if>
			<c:if test="${userFullData.account != null}">
			<i class="fas fa-door-open" style="font-size: 25px;color: yellow"></i>
			<a href="${pageContext.request.contextPath}/webUser/WebUserLogoutManual.jsp">
			 登出
			  |</a>
			</c:if>
<!-- 			<img src="Images/PLZPLZ-removebg-preview.png" -->
<!-- 				class="shopcar" style="height: 40px; margin: 0; margin-left: 5px;"> -->
			<i class="fas fa-shopping-cart" style="font-size: 25px;color: yellow"></i>
			<a href="${pageContext.request.contextPath}/product/index.jsp">
			 前往購物
			  |</a>
			<i class="fa fa-mobile-phone" style="font-size: 25px;color: yellow"></i>
			<a href="${pageContext.request.contextPath}/booking/Page1.jsp">
			 查詢訂位
			  |</a>
		</p>
	</div>



