<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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

<%-- <sql:query dataSource="${ds}" var="rs"> --%>
<!--          select count(*) as number from store  -->
<%-- </sql:query> --%>

<%-- <c:forEach var="row" items="${rs.rows}"> --%>
<%-- 	<c:set var = "id" value = "${row.number+1}"/> --%>
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
    @import url('https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@500&display=swap');
        body{
         background-color:rgb(235, 159, 18); 
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
<!-- ------------------------------------------------------ -->
    <div style="height: 15px;"></div>
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 containerHeaderCard">
                <div class="h-100 containerHeaderFontCard">
                    <div class="containerHeaderFontDiv">
                       <span class="containerHeaderFontSpan">新增店家</span>
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
                <form:form action="InsertStore" method="POST" modelAttribute="storeBean">
                
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
                    		<button class="btn btn-primary" type="submit" >立即新增</button>
                    	</div>
                    </div>
                </form:form>
                    	<div class = "col-md-12" style="text-align: center; margin-top: 5px" id="oneBt">
                    		<button class="btn btn-success" >一鍵輸入</button>
                    	</div>
                    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                    	<script type="text/javascript">
                    		$("#oneBt").click(function(){
                    			$("#storename").val('203蘋果');
                    			$("#storeAddress").val('330桃園市桃園區大業路二段4號');
                    			$("#storeTel").val('033161218');
                    			$("#storeItd").val('上菜快，好吃，價格公道。'
                    					+'只經營早餐及午餐。'
                    					+'老闆很有愛心，支持1919愛走動急難家庭救助活動。'
                    					);
                    			$("#storeItddt").val('POPO吃凱薩青醬雞柳義大利麵，套餐105元還有中杯飲料蠻大杯，但麵條不是喜歡的麵條，所以味道沒太大的記憶點，不過這間店餐點真的很多元，從中式蘿蔔糕、炒麵、拌飯、拌麵、蔥抓餅等等，和西式的沙拉、漢堡、鐵板麵、義大利麵等等，應有盡有'
                    					+'，每天吃應該也不會膩！營業時間:平日 早上6點到下午3點');
                    		})
                    	</script>
            </div>
        </div>
    </div>
<!-- ------------------------------------------------------ -->
<!-- 	    <div class="container" style="background-color: wheat;border-radius:5px;padding:100px;border: 1px solid wheat;box-shadow: 5px 5px 5px rgb(75, 75, 75);margin-top:15px;margin-bottom:15px; "> -->
<!--             <fieldset style="width: 400px;margin:1px auto;"> -->
<!--                 <legend>新增店家</legend> -->
<%--                 <form:form action="InsertStore" method="POST" modelAttribute="storeBean"> --%>
<!--                 <br> -->
<!--                 <label>商店名稱: -->
<%-- 					<form:input path="stname"/> --%>
<%-- 					<form:errors path="stname" cssClass="error"/> --%>
<!--                 </label><span id="stnamespan"></span> -->
<!--                 <br> -->
<!--                 <label>商店類別  -->
<%--                     <form:select path="sclass"> --%>
<%--                         <form:option value="中式" --%>
<%--                          	selected="selected" --%>
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
<%-- 						<form:input path="saddress" onblur="checkaddress()"/> --%>
<!--                 </label> -->
<%--                 <form:errors path="saddress" cssClass="error"/> --%>
<!--                 <span id = "addressspan"></span> -->
<!--                 <br> -->
<!--                 <label>電話: -->
<%--                     <form:input path="tel" onblur="checktel();" /> --%>
<!--                 </label> -->
<%--                 <form:errors path="tel" cssClass="error"/> --%>
<!--                 <span id = "telspan"></span> -->
<!--                 <br> -->

<!--                 <label style="width: 40px;text-align: right;padding-right: 3px;">簡介: -->
<%--                 	<form:textarea cols="40" rows="5" path="stitd" placeholder="限50字"/>   --%>
<!--                 </label> -->
<!-- 				<br>           -->
<%--                       				<font color='red' size='-3'>${error.stitd}</font> --%>
<%-- 									<form:errors path="stitd" cssClass="error"/> --%>
<!--                 <br> -->
<!--                 <label style="width: 40px;text-align: right;padding-right: 3px;">詳介: -->
<%--                     <form:textarea cols="40" rows="5" path="stitddt" placeholder=""></form:textarea> --%>
<!--                 </label> -->
<!--             <div style="text-align: center;"> -->
<!--                 <input type="submit" value="新增"> -->
<!--                 <input type="reset" value="清除"> -->
<!--             </div> -->
<%--                 </form:form> --%>
<!--             </fieldset> -->
    
        <%@include file = "Footer-Include.jsp" %>

<!-- -------------------------------------------------------------------- -->
       <script>
       		function checkname(){
       			let thenameval = document.getElementById("name").value;
       			if(thenameval==0 ){
       				stnamespan.innerHTML="<span style='color : red'>"+"請輸入商家名稱"+"</span>";
       			}else{
       				stnamespan.innerHTML="<span></span>"
       			}
       		}
       		function checkaddress(){
       			let thenameval = document.getElementById("address").value;
       			if(thenameval==0 ){
       				addressspan.innerHTML="<span style='color : red'>"+"請輸入商家位置"+"</span>";
       			}else{
       				addressspan.innerHTML="<span></span>"
       			}
       		}
       		function checktel(){
       			let thenameval = document.getElementById("idtel").value;
       			if(thenameval==0 ){
       				telspan.innerHTML="<span style='color : red'>"+"請輸入商家電話"+"</span>";
       			}else{
       				telspan.innerHTML="<span></span>"
       			}
       		}
       </script> 
</body>
</html>