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
<%@include file="../Link_Meta-Include.jsp"%>
<link rel='stylesheet'
	href='${pageContext.request.contextPath}/css/test.css' type="text/css" />
<link rel='stylesheet'
	href='${pageContext.request.contextPath}/css/LoadingScreen.css'
	type="text/css" />

<title>CartPage</title>

<style>
@import url(https://fonts.googleapis.com/css?family=Open+Sans:400,600);
*, *:before, *:after {
	margin: 0;
	padding: 0;
	box-sizing: border-box;
}
/* .continueShopping { */
/* 	background-color: white; */
/* 	color: black; */
/* 	border: 2px solid #f44336; */
/* } */
.continueShopping:hover {
	background-color: #f44336;
	color: white;
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
	background-color: #003049;
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
</style>
</head>
<body>
	<%@include file="../Header-Include.jsp"%>
	<%@include file="../LoadingScreen.jsp"%>
	<!-- -------------------------------------------------------------- -->
	<div class="container" style="margin-top: 20px;">
		<!-- 		<form> -->
		
		<table cellpadding="2" cellspacing="2" border="1" align="center">
			<thead>
				<th>清除</th>
				<th>商品編號</th>
				<th>商品店家</th>
				<th>商品名稱</th>
				<th>商品照片</th>
				<th>商品價格</th>
				<th>商品數量</th>
				<th>小計</th>
			</thead>
			<c:set var="total" value="0"></c:set>
			<c:set var="quant" value="0"></c:set>
			<c:forEach var="thisCart" items="${cart}">
				<c:set var="total" value="${total+thisCart.product.product_price}"></c:set>
				<tr id="thing${thisCart.product.product_id}">
					<td align="center">
						<button id="clear${thisCart.product.product_id}" onclick="itemRemove(${thisCart.product.product_id})">刪除項目</button>
						<%-- 						<a href="<c:url value="/controller/itemremove?id=${product.product_id}"/>"	onclick="return confirm('是否確定?')">刪除項目</a> --%>
					</td>
					<td>${thisCart.product.product_id}</td>
					<td>${thisCart.product.product_shop}</td>
					<td>${thisCart.product.product_name}</td>
					<td><img
						src='<c:url value="/images/${thisCart.product.product_picture}"/>'
						width="120px"></td>
					<td id="aa">${thisCart.product.product_price}</td>
					<td><input list="quantities-list" name="ttt" class="qu">
						<datalist id="quantities-list">
							<option value="1"></option>
							<option value="2"></option>
							<option value="3"></option>
							<option value="4"></option>
							<option value="5"></option>
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
		<button type="button" class="continueShopping"
			onclick="mallRedirector()">繼續購物</button>
		<button type="button" class="checkOut" onclick="checkCart()">結帳</button>
	    <button style="float:left" onclick="clearCart()">清除購物車</button>
	    <div class="wrapper "></div>
		<!-- 向後端送值用 -->
		<form method="Post" action=""
			style="display: none"></form>


		<script>	
			function clearCart(){
				$.ajax({
					url:"/WebProject/controller/clearCart",
					type:"Get",
					dataType:"TEXT",
					success:function(){
						$("table").eq(0).children("tbody").children("tr").each(function(){
							$(this).html("");
						})
						window.alert('您的購物車已清空!')
					},
					error:function(xhr,ajaxOptions,thrownError){
						console.log(xhr.status);
						console.log(thrownError);
					}
				})
			}
			function mallRedirector() {
				console.log('mallRedirector is now initialized');
				window.location = "<c:url value='/controller/mallRedirector'/>;";
			}
			function checkCart() {
				console.log('checkCart method initialized');
							
				var K = document.getElementById('tot').innerHTML;
				let s = "";
				let m = "";
				$("table").eq(0).children("tbody").eq(0).children("tr").each(function(){
					console.log("$(this).children('td').length="+$(this).children("td").length)
					
					let row = $(this).children('td').length;
					let value1 = $(this).children("td").eq(1).html();
					let value2 = $(this).children("td").eq(6).children("input").val();
					let value3 = $(this).children("td").eq(1).html();
					
					if (row == 8) {
						s += value1+":"+value2+","
					} else if (row == 2) {
						m += value3;
					} else {
						s += "" 
					}
				})
				console.log("s="+s);
				console.log("m="+m);
				if (K > 0) {													
					let r = confirm('是否結帳');
					if(r==true){
						console.log('AJAX initialized');
						$("form").eq(1).attr("action","/WebProject/controller/checkout?purchaseInfo="+s+"&totalValue="+m).submit();						
					}else{
						window.alert('您的購物車為空，請繼續購物後再結帳');
					}
				}
			}
			
// 			購物車總價值計算
			$(".qu").change(function()  {
				var ProdPrice = parseInt($(this).parent().prev().text());
				var SelVal = $(this).val();
				var Result = ProdPrice * SelVal;
				var total = 0;
				$(this).parent().next().html(Result);
				$(".re").each(function() {
					total += parseInt($(this).text());
					
				})
				$("#tot").html(total);
			})
			
			function itemRemove(id) {
							console.log("testtest="+$(this).attr("id"));
		        var K =confirm('是否確定刪除所選項目?')
				if (K==true) {
					$.ajax({
						url : "/WebProject/controller/itemremove",
						data : {
							"id":id //id向後端發送
						},
						type:"Get",			
						dataType:"TEXT",
						success:function(obj) {
							console.log("id="+id)		
													
							$("table").eq(0).children("tbody").children("tr").each(function(){
								if($(this).children("td").eq(1).html() == id){		
									$(this).html("");
									
									let total=0;
									$(".re").each(function() {
										total += parseInt($(this).text());
										
									})
									$("#tot").html(total);
								}
							})
						},
						error : function(xhr, ajaxOptions, thrownError) {
							console.log(xhr.status);
							console.log(thrownError);
						}
					});
				}
			}
		</script>
	</div>
	<!-- -------------------------------------------------------------------- -->
	<%@include file="../Footer-Include.jsp"%>
</body>
</html>