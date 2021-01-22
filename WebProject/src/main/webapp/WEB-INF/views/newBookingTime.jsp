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
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/zestform.css'  type="text/css" />
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
<!-- -------------------------------------- -->
<div style="height: 15px;"></div>
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 containerHeaderCard">
                <div class="h-100 containerHeaderFontCard">
                    <div class="containerHeaderFontDiv">
                       <span class="containerHeaderFontSpan">新增訂位數</span>
                    </div>
                </div>
            </div>
            <div class="col-2"></div>
        </div>
    </div>
    <div class="container" style="margin-bottom:15px;">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 containerBodyCardOutside">
                <form method="POST" action="<c:url value='/newBookingTime'/>">
	                <input type="hidden" name="stid" value="${stid}">
	                
                     <div class="form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="storename">商店名稱：</label>
                        </div>
                        <div class="col-md-8" style="padding-top: 8px">
                        	<span class="containerBodyCardLabelFont">
	                        	${productInfoBean.product_shop}	
                        	</span>
                        </div>
                    </div>
                    
                   <div class="form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="productQuantity">可訂位數：</label>
                        </div>
                        <div class="col-md-8">
                        	<input type="text" name="seating" class="form-control"/>
                        </div>
                    </div>
                    
					<div class = "form-row">
						<div class = "col-md-12" style="text-align: center;">
                    		<button class="btn btn-primary" type="submit" >立即新增</button>
                    	</div>
                    </div>
                </form>
                    	
            </div>
        </div>
    </div>

<%@include file="Footer-Include.jsp"%>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

</html>