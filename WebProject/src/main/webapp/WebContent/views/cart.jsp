<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Cart</title>
</head>
<body>

	<div align="center">
		<h1>SpringShoppingCart</h1>
		<table border="2">
			<tr>
				<th>Options</th>
				<th>Id</th>
				<th>Photo</th>
				<th>Name</th>
				<th>Price</th>
				<th>Quantity</th>
				<th>Sub Total</th>
			</tr>

			<c:set var="s" value="0"></c:set>
			<c:forEach var="pr" items="${sessionScope.cart}">
				<c:set var="s" value="${s+ pr.product_price * pr.product_quantity}"></c:set>
				<tr>
					<td><a
						href="${pageContext.request.contextPath}/shoppingcart/delete/${pr.product_id }.html">Remove</a></td>
					<td>${pr.product.id }</td>

					<td><img alt=""
						src="${pageContext.request.contextPath}/images/${pr.product_picture}"
						height="100px" width="100px"></td>

					<td>${pr.product_name}</td>
					<td>${pr.product_price }</td>
					<td>${pr.product_quantity }</td>
					<td>${pr.product_price * pr.product_quantity}</td>
				</tr>
			</c:forEach>

			<tr>
				<td colspan="6" align="right">Sum</td>
				<td>${s}</td>

			</tr>
		</table>
		<a href="${pageContext.request.contextPath}/product/productlist.html">Shopping</a>
		<br>

	</div>



</body>
</html>