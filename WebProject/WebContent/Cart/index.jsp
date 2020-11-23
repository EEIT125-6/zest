<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>

<!DOCTYPE html>
<%
response.setContentType("text/html;charset=UTF-8");
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
%>

<html>

<head>
<meta http-equiv="Content-Type" content="text/html charset=UTF-8">
<title>CartPage</title>
<!-- 購物車選購頁面-->
<style>
</style>

</head>
<body>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

	<table cellpadding="2" cellspacing="2" border="1">
		<tr>
			<th>清除</th>
			<th>商品編號</th>
			<th>商品店家</th>
			<th>商品名稱</th>
			<th>商品照片</th>
			<th>商品價格</th>
			<th>商品數量</th>
			<th>小計</th>
		</tr>
		<c:set var="total" value="0"></c:set>
		<c:set var="quant" value="0"></c:set>
		<c:forEach var="product" items="${sessionScope.Cart}">


			<c:set var="total" value="${total+product.productPrice}"></c:set>



			<tr>
				<td align="center"><a
					href="${pageContext.request.contextPath }/cart?action=remove&id=${product.productId}"
					onclick="return confirm('是否確定?')">Remove</a></td>
				<td>${product.productId}</td>
				<td>${product.productShop}</td>
				<td>${product.productName}</td>
				<td><img
					src="${product.productPicture}"
					width="120px"></td>
				<td id="aa">${product.productPrice}</td>
				<td><input list="quantities" name="quantity" class="qu" value="0">
					<datalist id="quantities">
						<option value="0">
						<option value="1">
						<option value="2">
						<option value="3">
						<option value="4">
						<option value="5">
						<option value="6">
						<option value="7">
						<option value="8">
						<option value="9">
						<option value="10">
					</datalist></td>
				<td id="result" class="re">0</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7" align="right">總計</td>
			<td id="tot">0</td>
		</tr>
	</table>
	<br>
	<button type="button">
	<a href="../product/">繼續購物</a>
	</button>
	<button type="button">
	<a href="">結帳</a>
	</button>
	


	<script>
		$(".qu").change(
			function() {
				var ProdPrice = parseInt($(this).parent()
						.prev().text());
				var SelVal = $(this).val();
				var Result = ProdPrice * SelVal;
				var total = 0;
				$(this).parent().next().html(Result);
				$(".re").each(function(){
					total+=parseInt($(this).text());
					console.log($(this).text())
				})
				
				
			   
	
				//console.log("ProdPrice=" + ProdPrice);
				//console.log("Selval=" + SelVal);
				//console.log(ProdPrice * SelVal);
				$("#tot").html(total);
				
		})
		
		
		
		
		
	</script>
</body>
</html>