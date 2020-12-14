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
    <%@include file = "../Link_Meta-Include.jsp" %>
    <title>CartPage</title>
    <!-- 購物車選購頁面-->
    <style>
        .classimg{
		 transition: 0.2s;	
        	width:80px
        }
        .classimg:hover{
         transition: 0.2s;	        
			width: 85px
        }
        body{
         background-color: 		rgb(235, 159, 18);
       }
       .header{
            height: 100px;
            border-bottom: 3px solid #e76f51;height: 90px;
            padding-top: 5px;
            background-color: #003049
       }
       .photo{
           padding: 0%;
           background: url("Images/backbar2-1.jpg"); 
           height: 540px;
           padding-top: 220px;
           background-size:100%
       }
       .shopcar{
            height: 40px;
            margin: 0;
            margin-left:5px ;
       }
        .wrapper{
            position: relative;
            width:1000px;
            height:400px;
            overflow: hidden;
            margin:0 auto;
            border-radius:5px;   
        }
        ul{
            margin:0;
            padding: 0;
            position: absolute;
        }
        li{
            margin:0;
            padding: 0;
            list-style: none;
        }
        ul.slides{
            width: 4000px;
            left: 0px;
            transition: all .5s;
        }
        ul.slides li{
            width:1000px;
            height:400px;
            overflow: hidden;
            float: left;
        }
        ul.slides li img{
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .dot{
            bottom:10px;
            width:100%;
            display: flex;
            justify-content: center;
        }
        .dot li{
            border:1px solid #fff;
            
            margin: 0 5px;
            width:24px;
            height: 10px;
        }

        .slide_btn{
            display: flex;
            justify-content: center;
            align-items: center;
            top:0;
            bottom:0;
            width: 30px;
            color:#fff;
            position: absolute;
            font-size:24px;  
            
        }
        #prevSlide{            
            left:0;                    
        }
        #nextSlide{            
            right:0;                
        }
        .slide_btn i{
            color:rgba(255,255,255,.6);                        
            transition: .5s;
        }
        .slide_btn:hover i{
            color:rgba(255,255,255,1);            
        }
        
        
#gotop {
    position:fixed;
    z-index:90;
    right:30px;
    bottom:31px;
    display:none;
    width:50px;
    height:50px;
    color:#fff;
    background:#ddbe56;
    line-height:50px;
    border-radius:50%;
    transition:all 1.5s;
    text-align: center;
    box-shadow: 0 2px 5px 0 rgba(0,0,0,0.16), 0 2px 10px 0 rgba(0,0,0,0.12);
}
#gotop :hover{
    background:#0099CC;
}
    </style>
</head>
<body>
            <%@include file = "../Header-Include.jsp" %>
<!-- -------------------------------------------------------------- -->
	<div class="container"  style="margin-top: 20px;">
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
		<c:forEach var="product" items="${sessionScope.cart}">


			<c:set var="total" value="${total+product.product_price}"></c:set>



			<tr>
				<td align="center"><a
					href="${pageContext.request.contextPath }/controller?action=remove&id=${product.product_id}"
					onclick="return confirm('是否確定?')">刪除項目</a></td>
				<td>${product.product_id}</td>
				<td>${product.product_shop}</td>
				<td>${product.product_name}</td>
				<td><img
					src="${pageContext.request.contextPath }/productInfo/images/${product.product_picture}.jpg"
					width="120px"></td>
				<td id="aa">${product.product_price}</td>
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
	<a href="${pageContext.request.contextPath}/product/index.jsp">繼續購物</a>
	</button>
	<button type="button">
	<a href="">結帳</a>
	</button>
	

	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
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
	</div>
<!-- -------------------------------------------------------------------- -->
            <%@include file = "../Footer-Include.jsp" %>
</body>
</html>