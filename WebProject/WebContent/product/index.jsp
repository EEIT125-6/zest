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
<meta http-equiv="Content-Type" content="text/html charset=UTF-8">
<title>Product Page</title>
<!-- 商城頁面 -->
</head>
<body>

	<sql:setDataSource var="product"
		driver="com.microsoft.sqlserver.jdbc.SQLServerDriver"
		url="jdbc:sqlserver://10.31.25.130:1433;databaseName=WebProject"
		user="scott" password="tiger" />

	<sql:query dataSource="${product}" var="products">
            SELECT * FROM ProductInfo;
         </sql:query>

	<table cellpadding="2" cellspacing="2" border="1">
		<tr>
		<tr>
			<th>商品編號</th>
			<th>商品照片</th>
			<th>商品店家</th>
			<th>商品名稱</th>
			<th>商品價格</th>
			<th>加入購物車</th>
		</tr>
		<c:forEach var="product" items="${products.rows}">
			<tr>
				<td>${product.product_id}</td>
				<td><img
					src="${product.product_picture}"
					width="120px"></td>
				<td>${product.product_shop}</td>
				<td>${product.product_name}</td>
				<td>${product.product_price}</td>
				<td><a
					href="${pageContext.request.contextPath}/cart?action=buy&id=${product.product_id}&picture=${product.product_picture}&shop=${product.product_shop}&name=${product.product_name}&price=${product.product_price}&quantity=1"}>加入購物車</a></td>
			</tr>
			</tr>
		</c:forEach>
	</table>
</body>
</html>