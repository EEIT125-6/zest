<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
body {
  font-family: Arial;
  font-size: 17px;
  padding: 8px;
}

* {
  box-sizing: border-box;
}

.row {
  display: -ms-flexbox; /* IE10 */
  display: flex;
  -ms-flex-wrap: wrap; /* IE10 */
  flex-wrap: wrap;
  margin: 0 -16px;
}

.col-25 {
  -ms-flex: 25%; /* IE10 */
  flex: 25%;
}

.col-50 {
  -ms-flex: 50%; /* IE10 */
  flex: 50%;
}

.col-75 {
  -ms-flex: 75%; /* IE10 */
  flex: 75%;
}

.col-25,
.col-50,
.col-75 {
  padding: 0 16px;
}

.container {
  background-color: #f2f2f2;
  padding: 5px 20px 15px 20px;
  border: 1px solid lightgrey;
  border-radius: 3px;
}

input[type=text] {
  width: 100%;
  margin-bottom: 20px;
  padding: 12px;
  border: 1px solid #ccc;
  border-radius: 3px;
}

label {
  margin-bottom: 10px;
  display: block;
}

.icon-container {
  margin-bottom: 20px;
  padding: 7px 0;
  font-size: 24px;
}

.btn {
  background-color: #FF4500;
  color: white;
  padding: 12px;
  margin: 10px 0;
  border: none;
  width: 100%;
  border-radius: 3px;
  cursor: pointer;
  font-size: 17px;
}

.btn:hover {
  background-color: #45a049;
}

a {
  color: #2196F3;
}

hr {
  border: 1px solid lightgrey;
}

span.price {
  float: right;
  color: grey;
}

/* Responsive layout - when the screen is less than 800px wide, make the two columns stack on top of each other instead of next to each other (also change the direction - make the "cart" column go on top) */
@media (max-width: 800px) {
  .row {
    flex-direction: column-reverse;
  }
  .col-25 {
    margin-bottom: 20px;
  }
}
</style>
<script
			src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		<script>
			$(".qu").onload(function() {
				var ProdPrice = parseInt($(this).parent().prev().text());
				var SelVal = $(this).val();
				var Result = ProdPrice * SelVal;
				var total = 0;
				$(this).parent().next().html(Result);
				$(".re").each(function() {
					total += parseInt($(this).text());
					console.log($(this).text())
				})

				$("#tot").html(total);

			})
		</script>
</head>
<body>

<h2>購買人訂購資訊</h2>
<div class="row">
  <div class="col-75">
    <div class="container">
      <form action="資訊串接綠界金流" class="qu">
      
        <div class="row">
          <div class="col-50">
            <h3>帳單郵寄地址</h3>
            <label for="fname"><i class="fa fa-user"></i> 訂購人姓名</label>
            <input type="text" id="fname" name="firstname" placeholder="Ex.陳橙皮">
            <label for="email"><i class="fa fa-envelope"></i> 電子郵件</label>
            <input type="text" id="email" name="email" placeholder="orange@orange.com">
            <label for="adr"><i class="fa fa-address-card-o"></i>訂購人地址</label>
            <input type="text" id="adr" name="address" placeholder="橘子市柑仔區維他命C街88號">

           

          <div class="col-50">
            <h3>付款資訊</h3>
            <label for="fname">合作信用卡</label>
            <div class="icon-container">
              <i class="fa fa-cc-visa" style="color:navy;">VISA信用卡</i>
              <i class="fa fa-cc-mastercard" style="color:red;">萬事通卡</i>
            </div>
            <label for="cname">持卡人姓名</label>
            <input type="text" id="cname" name="cardname" placeholder="陳橙皮">
            <label for="ccnum">信用卡卡號</label>
            <input type="text" id="ccnum" name="cardnumber" placeholder="1111-2222-3333-4444">
            <label for="expmonth">信用卡有效期限月</label>
            <input type="text" id="expmonth" name="expmonth" placeholder="September">
            <div class="row">
              <div class="col-50">
                <label for="expyear">信用卡有效期限年</label>
                <input type="text" id="expyear" name="expyear" placeholder="2018">
              </div>
              <div class="col-50">
                <label for="cvv">信用卡安全檢核碼</label>
                <input type="text" id="cvv" name="cvv" placeholder="352">
              </div>
            </div>
          </div>
          
        </div>
        <label>
          <input type="checkbox" checked="checked" name="sameadr"> 商品郵寄地址同帳單地址
        </label>
        <input type="submit" value="繼續結帳(將導向至綠界金流支付頁面)" class="btn">
      </form>
    </div>
  </div>
  <div class="col-25">
    <div class="container">
      <h4>您的購物車項目 <span class="price" style="color:black"><i class="fa fa-shopping-cart"></i><b><c:out value="${fn:length(products)}"/><c:out value="個商品"/></b></span></h4>
      <c:forEach var="product" items="${products}">
      <p>
	      <span>${product.product_name}</span>
	      <c:out value="x"/>
	      <span id="itemQuantity">${itemQuantity[product.product_id]}</span>
	      <span class="price"><c:out value="NT$"/>${product.product_price*itemQuantity[product.product_id]}</span>
      </p>
      <hr>
      </c:forEach>
      <p>總計<span id="tot" class="price" style="color:black"><b>NT$ ${itemTotalValue}</b></span></p>
    </div>
    <button type="button" class="dropOrder">
		<a href="<c:url value="/controller/mallRedirector"/>" onclick ="return confirm('是否確定? 您即將返回購物商城')" >繼續購物</a>
	</button>
  </div>
</div>

</body>
</html>
