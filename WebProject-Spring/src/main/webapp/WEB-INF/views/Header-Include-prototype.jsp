<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="container-fluid  header"
	style="height: 100px; border-bottom: 3px solid #e76f51; height: 90px; padding-top: 5px; background-color: #003049">
	<div class="container">
		<a href="${pageContext.request.contextPath}/xun">
<!-- 			<img src="Images/LOGO1-removebg-preview.png" -->
		 <c:set var = "logo" value = "Images/LOGO1-removebg-preview.png"/>
		 
		<img src="<c:url value='/photo/${logo}'/>"
<%-- 		<img src="${pageContext.request.contextPath}/xun/photo/${logo}" --%>
			style="float: left; height: 70px;"></a>
		<p
			style="text-align: right; font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;; float: right">
			<br>
			<i class="fas fa-user" style="font-size: 25px;color: yellow"></i>
			登入
			  |
			<i class="fas fa-user-plus" style="font-size: 25px;color: yellow"></i>
			 註冊
			  |
<!-- 			<img src="Images/PLZPLZ-removebg-preview.png" -->
<!-- 				class="shopcar" style="height: 40px; margin: 0; margin-left: 5px;"> -->
				<i class="fas fa-shopping-cart" style="font-size: 25px;color: yellow"></i>
			購物車
			  
		</p>
	</div>



