<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel='stylesheet'
	href='${pageContext.request.contextPath}/css/zestCss.css'
	type="text/css" />
<%@include file="Link_Meta-Include.jsp"%>
<title>橙皮</title>
<style>
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
</style>
</head>
<body>
	<%@include file="Header-Include.jsp"%>

	<div class="container" style="background-color: wheat;border-radius:5px;padding:100px;border: 1px solid wheat;box-shadow: 5px 5px 5px rgb(75, 75, 75);margin-top:15px;margin-bottom:15px;">
<fieldset style="width: 400px;margin:1px auto;">
	             <form:form action="exupdateProduct" method="POST" modelAttribute="productInfoBean" enctype='multipart/form-data'>
                <legend>修改商品</legend>
                <input type="hidden" name="stid" value="${stid}">
<%-- 				<form:input path="product_shop"/> --%>
<%--                 <form:hidden path="product_id"/> --%>
                <form:hidden path="product_id"/>
                
	            <label>商店名稱:
	            	<form:hidden path="product_shop"/>
	            	${productInfoBean.product_shop}          
	            </label>
	            	<br>
                <label>商品名稱:
					<form:input path="product_name"/>
					<form:errors path="product_name" cssClass="error"/>
                </label>
                <br>
                <label>商品單價:
                	<form:input path="product_price"/>
                </label>
                <br>
                <c:if test="${productInfoBean.product_picture !=null }">
                <label>目前的商品圖片:
                	<img  src="${pageContext.request.contextPath}/123/${productInfoBean.product_picture}" style="width:50px;height:50px">
                </label>
                </c:if>
                <label>商品照片:
					<input type="file" name="file"/>
					<form:hidden path="product_picture"/>
					<br>
						  <img id="blah" src="" alt="圖片預覽" width="100%;" height="100% "  />
                </label>
                <br>
                <label>商品庫存:
                    <form:input path="product_quantity" onblur="checktel();" />
                </label>
                <br>
            <div style="text-align: center;">
                <input type="submit" value="修改">
                <input type="reset" value="清除">
            </div>
                </form:form>
            </fieldset>

	
	</div>

<div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:100px">
	<%@include file="Footer-Include-prototype.jsp"%>
</body>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<script>
		function readURL(input) {
			  if (input.files && input.files[0]) {
			    var reader = new FileReader();
			    
			    reader.onload = function(e) {
			      $('#blah').attr('src', e.target.result);
			    }
			    
			    reader.readAsDataURL(input.files[0]); // convert to base64 string
			  }
			}

			$("#imgInp").change(function() {
			  readURL(this);
			});
	</script>
</html>