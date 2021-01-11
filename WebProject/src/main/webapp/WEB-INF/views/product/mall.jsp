<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>


<!DOCTYPE html>
<%
response.setContentType("text/html;charset=UTF-8");
response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0
response.setDateHeader("Expires", -1); // Prevents caching at the proxy server
response.setCharacterEncoding("UTF-8");
%>

<html>

<head>
<%@include file="../Link_Meta-Include.jsp"%>
<title>Product Page</title>
<!-- 商城頁面 -->
<style>
@import url(https://fonts.googleapis.com/css?family=Open+Sans:400,600);

*, *:before, *:after {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}

body {
	background: #105469;
	font-family: 'Open Sans', sans-serif;
}

table {
	background: #012B39;
	border-radius: 0.25em;
	border-collapse: collapse;
	margin: 1em;
}

th {
	border-bottom: 1px solid #364043;
	color: #E2B842;
	font-size: 0.85em;
	font-weight: 600;
	padding: 0.5em 1em;
	text-align: left;
}

td {
	color: #fff;
	font-weight: 400;
	padding: 0.65em 1em;
}

.disabled td {
	color: #4F5F64;
}

tbody tr {
	transition: background 0.25s ease;
}

tbody tr:hover {
	background: #014055;
}

.classimg {
	transition: 0.2s;
	width: 80px
}

.classimg:hover {
	transition: 0.2s;
	width: 85px
}

body {
	background-color: rgb(235, 159, 18);
}

.header {
	height: 100px;
	border-bottom: 3px solid #e76f51;
	height: 90px;
	padding-top: 5px;
	background-color: #003049
}

.photo {
	padding: 0%;
	background: url("Images/backbar2-1.jpg");
	height: 540px;
	padding-top: 220px;
	background-size: 100%
}

.shopcar {
	height: 40px;
	margin: 0;
	margin-left: 5px;
}

.wrapper {
	position: relative;
	width: 1000px;
	height: 400px;
	overflow: hidden;
	margin: 0 auto;
	border-radius: 5px;
}

ul {
	margin: 0;
	padding: 0;
	position: absolute;
}

li {
	margin: 0;
	padding: 0;
	list-style: none;
}

ul.slides {
	width: 4000px;
	left: 0px;
	transition: all .5s;
}

ul.slides li {
	width: 1000px;
	height: 400px;
	overflow: hidden;
	float: left;
}

ul.slides li img {
	width: 100%;
	height: 100%;
	object-fit: cover;
}

.dot {
	bottom: 10px;
	width: 100%;
	display: flex;
	justify-content: center;
}

.dot li {
	border: 1px solid #fff;
	margin: 0 5px;
	width: 24px;
	height: 10px;
}

.slide_btn {
	display: flex;
	justify-content: center;
	align-items: center;
	top: 0;
	bottom: 0;
	width: 30px;
	color: #fff;
	position: absolute;
	font-size: 24px;
}

#prevSlide {
	left: 0;
}

#nextSlide {
	right: 0;
}

.slide_btn i {
	color: rgba(255, 255, 255, .6);
	transition: .5s;
}

.slide_btn:hover i {
	color: rgba(255, 255, 255, 1);
}

#gotop {
	position: fixed;
	z-index: 90;
	right: 30px;
	bottom: 31px;
	display: none;
	width: 50px;
	height: 50px;
	color: #fff;
	background: #ddbe56;
	line-height: 50px;
	border-radius: 50%;
	transition: all 1.5s;
	text-align: center;
	box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0
		rgba(0, 0, 0, 0.12);
}

#gotop :hover {
	background: #0099CC;
}

.foodphoto {
  padding: 30px;
  transition: transform .2s; /* Animation */
  width: 200px;
  height: 200px;
  margin: auto;
}

.foodphoto:hover {
  transform: scale(2.0); /* (150% zoom - Note: if the zoom is too large, it will go outside of the viewport) */
}
.addtocart {
  display: inline-block;
  border-radius: 4px;
  background-color: #f4511e;
  border: none;
  color: #FFFFFF;
  text-align: center;
  font-size: 22px;
  padding: 10px;
  width: 180px;
  transition: all 0.5s;
  cursor: pointer;
  margin: 5px;
}

.addtocart span {
  vertical-align:middle;
  cursor: pointer;
  display: inline-block;
  position: relative;
  transition: 0.5s;
}

.addtocart span:after {
  content: '\00bb';
  position: absolute;
  opacity: 0;
  top: 0;
  right: -20px;
  transition: 0.5s;
}

.addtocart:hover span {
  padding-right: 25px;
}

.addtocart:hover span:after {
  opacity: 1;
  right: 0;
}
</style>
</head>
<body>
	<%@include file="../Header-Include.jsp"%>
	<!-- -------------------------------------------------------------- -->

	<table cellpadding="2" cellspacing="2" border="1" align="center" style="margin-left:500px;"">
		<tr>
		<tr>
			<th>商品編號</th>
			<th>商品照片</th>
			<th>商品店家</th>
			<th>商品名稱</th>
			<th>商品價格</th>
			<th>加入購物車</span></th>
		</tr>
		<c:forEach var="product" items="${products}">
			<tr>
				<td>${product.product_id}</td>
				<td class="foodphoto"><img src="${product.product_picture}" width="120px"></td>
				<td>${product.product_shop}</td>
				<td>${product.product_name}</td>
				<td>${product.product_price}</td>
				<td class="addtocart" style="margin-top:66px;display:block;text-align:center;"><a
<%-- 					href="${pageContext.request.contextPath}/controller/itemadd/id=${product.product_id}"}><span>加入購物車</span></a></td> --%>
					href="<c:url value="/controller/itemadd?id=${product.product_id}" />"><span>加入購物車</span></a></td>
			</tr>
			</tr>
		</c:forEach>
	</table>
	</div>
	<!-- -------------------------------------------------------------------- -->
	<%@include file="../Footer-Include.jsp"%>
</body>
</html>