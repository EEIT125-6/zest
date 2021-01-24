<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html;charset=UTF-8");
%>
<%-- <sql:setDataSource var="ds" dataSource="jdbc/zest" /> --%>
<%		System.out.println((String)request.getSession(true).getAttribute("restname")); %> <!-- test -->
<%-- <c:set var="ss" value="${param.id}" /> --%>
<%-- <sql:query dataSource="${ds}" var="rs"> --%>
<!--          SELECT * FROM store WHERE id = ?  -->
<%--          <sql:param value="${ss}" /> --%>
<%-- </sql:query> --%>
<%-- <c:forEach var="row" items="${sessionScope.Results}"> --%>
<%-- 	<c:set var = "id" value = "${row.id }"/> --%>
<%-- 	<c:set var = "stname1"  value = "${row.stname}"/> --%>
<%-- 	<c:set var = "sclass" value = "${row.sclass}"/> --%>
<%-- 	<c:set var = "saddress" value = "${row.saddress}"/> --%>
<%-- 	<c:set var = "stitd" value = "${row.stitd }"/> --%>
<%-- 	<c:set var = "stitddt" value = "${row.stitddt }"/>	 --%>
<%-- 	<c:set var = "tel" value = "${row.tel }"/> --%>
<%-- </c:forEach> --%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/zestCss.css'  type="text/css" />
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/zestform.css'  type="text/css" />
    <%@include file = "Link_Meta-Include.jsp" %>
    <title>橙皮</title>
    <style>
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
    </style>
</head>
<body>
<%@include file = "Header-Include.jsp" %>
<!-- ----------------------------------------- -->

    <div style="height: 15px;"></div>
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 containerHeaderCard">
                <div class="h-100 containerHeaderFontCard">
                    <div class="containerHeaderFontDiv">
                       <span class="containerHeaderFontSpan">修改店家資訊</span>
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
                <form:form action="StoreUpdate"  method="POST" modelAttribute="storeBean">
                
                	<form:hidden path="id"/> <!-- 不知道什麼時候長出來的 也不知道是幹啥的 -->
                
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="storename">商店名稱：</label>
                        </div>
                        <div class="col-md-8">
                        	<form:input class="form-control" id="storename" path="stname"/>
                        	<div>
                        		<form:errors path="stname" cssClass="error"/>
                        	</div>	
                        </div>
                    </div>
                    
                    <div class="form-row containerBodyCard">
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="storeSclass">商家類別：</label>
                        </div>
                        <div class="col-md-8">
                            <form:select class="custom-select" id="storeSclass" path="sclass">
                            	<form:options items="${sclassCategory}"/>
                            </form:select>
                        </div>
                    </div>
                    
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="storeAddress">商店地址：</label>
                        </div>
                        <div class="col-md-8">
                        	<form:input class="form-control" id="storeAddress" path="saddress"/>
                        	<div>
                        		<form:errors path="saddress" cssClass="error"/>
                        	</div>	
                        </div>
                    </div>
                    
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="storeTel">商店電話：</label>
                        </div>
                        <div class="col-md-8">
                        	<form:input class="form-control" id="storeTel" path="tel"/>
                        	<div>
                        		<form:errors path="tel" cssClass="error"/>
                        	</div>	
                        </div>
                    </div>
                    
                    <div class = "form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">  
                    		<label class="containerBodyCardLabelFont" for="storeItd">店家簡介：</label>
                    	</div>
                    </div>
                    <div class="form-group containerBodyCard">
                    	<form:textarea path="stitd" class="form-control" id="storeItd" rows="3" placeholder="限50字"></form:textarea>
                    	<div>
                    		<form:errors path="stitd" cssClass="error"/>
                    	</div>
                    </div>
                    
                    <div class = "form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">  
							<label class="containerBodyCardLabelFont" for="storeItddt">(非必填)店家詳介：</label>
                    	</div>
                    </div>
                    <div class="form-group containerBodyCard">
                    	<form:textarea path="stitddt" class="form-control" id="storeItddt" rows="5"></form:textarea>
                    	<div>
                    		<form:errors path="stitddt" cssClass="error"/>
                    	</div>
                    </div>

					<div class = "form-row">
						<div class = "col-md-12" style="text-align: center;">
                    		<button class="btn btn-primary" type="submit" >立即修改</button>
                    	</div>
                    </div>
                </form:form>
                    	<div class = "col-md-12" style="text-align: center; margin-top: 5px" id="oneBt">
                    		<button class="btn btn-success" >一鍵輸入</button>
                    	</div>
                    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                    	<script type="text/javascript">
                    		$("#oneBt").click(function(){
                    			$("#storename").val('204橘子');
                    			$("#storeAddress").val('320桃園市中壢區新生路二段373巷57號');
                    			$("#storeTel").val('034532459');
                    			$("#storeItd").val('上菜快，好吃，價格公道。'
                    					+'只經營早餐及午餐。'
                    					);
                    			$("#storeItddt").val('每天吃應該也不會膩！');
                    		})
                    	</script>
            </div>
        </div>
    </div>

<!-- ----------------------------------------- -->
<!-- 	    <div class="container" style="background-color: wheat;border-radius:5px;padding:100px;border: 1px solid wheat;box-shadow: 5px 5px 5px rgb(75, 75, 75);margin-top:15px;margin-bottom:15px; "> -->
<%--         <form:form action="StoreUpdate"  method="POST" modelAttribute="storeBean"> --%>
<!--             <fieldset style="width: 400px;margin:1px auto;"> -->
<!--                 <legend>修改資料</legend> -->
<%--                 	<form:hidden path="id"/> --%>
<!--                 <label>商店名稱: -->
<%--                     <form:input path="stname"  /> --%>
<%--                     <form:errors path="stname" cssClass="error"/> --%>
<!--                 </label> -->
<!--                 <br> -->
<!--                 <label>商店類別  -->
<%--                     <form:select path="sclass"> --%>
                    
<%--                         <form:option value="中式" --%>

<%--                         >中式</form:option> --%>
                        
<%--                         <form:option value="日式" --%>

<%--                         >日式</form:option> --%>
                        
<%--                         <form:option value="下午茶" --%>

<%--                         >下午茶</form:option> --%>
                        
<%--                         <form:option value="西式" --%>

<%--                         >西式</form:option> --%>
                        
<%--                         <form:option value="快餐" --%>

<%--                         >快餐</form:option> --%>
                        
<%--                         <form:option value="燒肉"  --%>

<%--                     	>燒肉</form:option> --%>
                    	
<%--                     </form:select> --%>
<!--                 </label> -->
<!--                 <br> -->
<!--                 <label>地址: -->
<%--                     <form:input path="saddress"/> --%>
<!--                 </label> -->
<!--                 <br> -->
<!--                 <label>電話: -->
<%--                     <form:input path="tel"/> --%>
<!--                 </label> -->
<!--                 <br> -->
<!--                 <label style="width: 40px;text-align: right;padding-right: 3px;">簡介: -->
<%--                     <form:textarea cols="40" rows="5" path="stitd" placeholder="限50字"></form:textarea> --%>
<!--                 </label> -->
<!--                 <br> -->
<%--                 	<form:errors path="stitd" cssClass="error"/> --%>
<!--                 <br> -->
<!--                 <label style="width: 40px;text-align: right;padding-right: 3px;">詳介: -->
<%--                     <form:textarea cols="40" rows="5" path="stitddt" placeholder=""></form:textarea> --%>
<!--                 </label> -->
<!--             </fieldset> -->
<!--             <div style="text-align: center;"> -->
<!--                 <input type="submit" value="修改"> -->
<!--                 <input type="reset" value="清除"> -->
<!--             </div> -->
<%--         </form:form> --%>
<!--     </div> -->
<!-- -------------------------------------------------------------------- -->
<%@include file = "Footer-Include.jsp" %>
</body>
</html>