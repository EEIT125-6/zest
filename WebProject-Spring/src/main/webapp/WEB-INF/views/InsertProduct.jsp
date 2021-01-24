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
                       <span class="containerHeaderFontSpan">新增商品</span>
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
                <form:form  method="POST" modelAttribute="productInfoBean" enctype='multipart/form-data'>
<%--                 	${stid} --%>
	                <input type="hidden" name="stid" value="${stid}">
	                <input type="hidden" name="forAOP" value="stid=${stid}">
	                
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="storename">商店名稱：</label>
                          <form:hidden path="product_shop"/>
                        </div>
                        <div class="col-md-8" style="padding-top: 8px">
                        	<span class="containerBodyCardLabelFont">
	                        	${productInfoBean.product_shop}	
                        	</span>
                        </div>
                    </div>
                    
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="produceName">商品名稱：</label>
                        </div>
                        <div class="col-md-8">
                        	<form:input class="form-control" id="produceName" path="product_name"/>
                        	<div>
                        		<form:errors path="product_name" cssClass="error"/>
                        	</div>	
                        </div>
                    </div>
                    
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="producePrice">商品單價：</label>
                        </div>
                        <div class="col-md-8">
                        	<form:input class="form-control" id="producePrice" path="product_price"/>
                        	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                        	<script type="text/javascript">
                        		$("#producePrice").focus(function(){
                        			$(this).attr("type","number")                        			
                        		})
                        	</script>
                        	<div>
                        		<form:errors path="product_price" cssClass="error"/>
                        	</div>	
                        </div>
                    </div>
                    
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="productQuantity">商品庫存：</label>
                        </div>
                        <div class="col-md-8">
                        	<form:input class="form-control" id="productQuantity" path="product_quantity"/>
                        	<script type="text/javascript">
                        		$("#productQuantity").focus(function(){
                        			$(this).attr("type","number")                        			
                        		})
                        	</script>
                        	<div>
                        		<form:errors path="product_quantity" cssClass="error"/>
                        	</div>	
                        </div>
                    </div>
                    
                    <div class="form-row containerBodyCard">
                        <div class="col-md-12 containerBodyCardLabelFont" >
                            <label for="imgInp">選擇一張圖片設置成商品的照片</label>
                        </div>
                    </div>
                    <div class = "form-row">
                    	<div class="col-md-2"></div>
	                    <div class="col-md-8 custom-file containerBodyCard">
	                        <input type="file" name="photo"   class="custom-file-input" id="imgInp" >
	                        <label class="custom-file-label" for="validatedCustomFile"></label>
	                    </div>
	                    <div class="col-md-2"></div>
                    </div>
                    
                    <div class = "form-row containerBodyCard" style="padding-top: 10px">
                    	<div class="col-md-2"></div>
                    	<div class="col-md-8">
                    		<img id="blah" src="#" alt="預覽圖片" width="100%;" height="100% "  />
                    	</div>
                    	<div class="col-md-2"></div>
                    </div>
                    

					<div class = "form-row">
						<div class = "col-md-12" style="text-align: center;">
                    		<button class="btn btn-primary" type="submit" >立即新增</button>
                    	</div>
                    </div>
                </form:form>
                    	<div class = "col-md-12" style="text-align: center; margin-top: 5px" id="oneBt">
                    		<button class="btn btn-success" >一鍵輸入</button>
                    	</div>
                    	
                    	<script type="text/javascript">
                    		$("#oneBt").click(function(){
                    			$("#produceName").val('牛肉拌麵<!--');
                    			$("#producePrice").val('320');
                    			$("#productQuantity").val('-8');
                    		})
                    	</script>
            </div>
        </div>
    </div>


<!-- -------------------------------------- -->
<!-- ------------------------------------------ -->
<!-- 	<div class="container" style="background-color: wheat;border-radius:5px;padding:100px;border: 1px solid wheat;box-shadow: 5px 5px 5px rgb(75, 75, 75);margin-top:15px;margin-bottom:15px;"> -->
<!-- <fieldset style="width: 400px;margin:1px auto;"> -->
<%-- 	             <form:form  method="POST" modelAttribute="productInfoBean" enctype='multipart/form-data'> --%>
<!--                 <legend>新增商品</legend> -->
<%--                 <input type="hidden" name="stid" value="${stid}"> --%>
<%--                 <input type="hidden" name="forAOP" value="stid=${stid}"> --%>
<!-- 	            <label>商店名稱: -->
<%-- 	            	<form:hidden path="product_shop"/> --%>
<%-- 	            	${productInfoBean.product_shop}           --%>
<!-- 	            	<br> -->
<!-- 	            </label> -->
<!-- 	            <br> -->
<!--                 <label>商品名稱: -->
<%-- 					<form:input path="product_name"/> --%>
<%-- 					<form:errors path="product_name" cssClass="error"/> --%>
<!--                 </label> -->
<!--                 <br> -->
<!--                 <label>商品單價: -->
<%--                 	<form:input path="product_price"/> --%>
<!--                 </label> -->
<!--                 <br> -->
<!--                 <label>商品照片: -->
<!-- 					<input type="file" name="photo" id="imgInp"/> -->
<!-- 					<br> -->
<!-- 						  <img id="blah" src="" alt="圖片預覽" width="100%;" height="100% "  /> -->
<!-- 					<br> -->
<!--                 </label> -->
<!--                 <br> -->
<!--                 <label>商品庫存: -->
<%--                     <form:input path="product_quantity" onblur="checktel();" /> --%>
<!--                 </label> -->
<!--                 <br> -->
<!--             <div style="text-align: center;"> -->
<!--                 <input type="submit" value="新增"> -->
<!--                 <input type="reset" value="清除"> -->
<!--             </div> -->
<%--                 </form:form> --%>
<!--             </fieldset> -->
<!-- 	</div> -->
<!-- ----------------------------------------------- -->
<!-- <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:85px"> -->
<%-- 	<%@include file="Footer-Include-prototype.jsp"%> --%>
<%@include file="Footer-Include.jsp"%>
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