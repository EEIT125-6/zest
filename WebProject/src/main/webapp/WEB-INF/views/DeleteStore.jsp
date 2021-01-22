<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html;charset=UTF-8");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <%@include file = "Link_Meta-Include.jsp" %>
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/zestCss.css'  type="text/css" />
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/zestform.css'  type="text/css" />
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
<!-- ------------------------------------------------------ -->
    <div style="height: 100px;"></div>
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 containerHeaderCard">
                <div class="h-100 containerHeaderFontCard">
                    <div class="containerHeaderFontDiv">
                       <span class="containerHeaderFontSpan">刪除店家</span>
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
                <form:form action="StoreDelete" method="post" modelAttribute="storeBean" >
                
                <form:input type="hidden" path="id" />
                
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-4 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="storename">商店名稱：</label>
                        </div>
                        <div class="col-md-8" style="padding-top: 8px">
                        	<span class="containerBodyCardLabelFont" style="text-align: center;">
                           			${storeBean.stname}
                        		<form:input type="hidden"  path="stname"/>
                        	</span>
                        </div>
                    </div>
                    <div class = "form-row">
						<div class = "col-md-12" style="text-align: center;">
                    		<span>您確定要刪除嗎? 此舉動將永久不能回復</span>
                    	</div>
                    </div>
                    

					<div class = "form-row">
						<div class = "col-md-12" style="text-align: center;">
                    		<button class="btn btn-primary" type="submit" >確定</button>
                    	</div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
                <div style="height: 450px"></div>
<!-- ----------------------------------------------- -->
	<!-- <c:out value="${param.id}"></c:out> -->
        <!-- 用container -->
<!-- 	 <div class="container" style="background-color: wheat;border-radius:5px;padding:100px;border: 1px solid wheat;box-shadow: 5px 5px 5px rgb(75, 75, 75);margin-top:15px;margin-bottom:15px; "> -->
<%--         <form:form action="StoreDelete" method="post" modelAttribute="storeBean" > --%>
<!--             <fieldset style="width: 400px;margin:1px auto;"> -->
<!--                 <legend>確定要刪除此資料嗎?</legend> -->
<%--                 <form:input type="hidden" path="id" /> --%>
<!--                 <label>商店名稱: -->
<%--                     <form:input type="hidden"  path="stname"/> --%>
<%--                     ${storeBean.stname} --%>
<!--                 </label> -->
<!--             </fieldset> -->
<!--                     <div style="text-align: center;"> -->
<!--                         <input type="submit" value="確定"> -->
<!--                     </div> -->
<%--         </form:form> --%>
<!--     </div> -->

<!-- -------------------------------------------------------------------- -->
<%@include file = "Footer-Include.jsp" %>
<!--             <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:230px"> -->
<%-- <%@include file = "Footer-Include-prototype.jsp" %> --%>
<!--                 Footer -->
<!--                 <footer class="page-footer font-small mdb-color lighten-3 pt-4"> -->
                
<!--                   Footer Links -->
<!--                   <div class="container text-center text-md-left"> -->
                
<!--                     Grid row -->
<!--                     <div class="row"> -->
                
<!--                       Grid column -->
<!--                       <div class="col-md-4 col-lg-3 mr-auto my-md-4 my-0 mt-4 mb-1"> -->
                
<!--                         Content -->
<!--                         <h5 class="font-weight-bold text-uppercase mb-4">More Content</h5> -->
<!--                         <p>商務合作</p> -->
<!--                         <p>	餐飲代理商招募<br> -->
<!--                         	商業企劃<br> -->
<!--                         	申請掃碼點餐<br> -->
<!--                         	美國收單代理商招募<br> -->
<!--                         	美國收銀代理商招募<br> -->
<!--                         	免費使用美國排隊<br></p> -->
                
<!--                       </div> -->
<!--                       Grid column -->
                
<!--                       <hr class="clearfix w-100 d-md-none"> -->
                
<!--                       Grid column -->
<!--                       <div class="col-md-2 col-lg-2 mx-auto my-md-4 my-0 mt-4 mb-1"> -->
                
<!--                         Links -->
<!--                         <h5 class="font-weight-bold text-uppercase mb-4">ABOUT</h5> -->
                
<!--                         <ul class="list-unstyled"> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <a href="#!">計畫</a> -->
<!--                             </p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <a href="#!">關於我們</a> -->
<!--                             </p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <a href="#!">Facebook</a> -->
<!--                             </p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <a href="#!">AWARDS</a> -->
<!--                             </p> -->
<!--                           </li> -->
<!--                         </ul> -->
                
<!--                       </div> -->
<!--                       Grid column -->
                
<!--                       <hr class="clearfix w-100 d-md-none"> -->
                
<!--                       Grid column -->
<!--                       <div class="col-md-4 col-lg-3 mx-auto my-md-4 my-0 mt-4 mb-1"> -->
                
<!--                         Contact details -->
<!--                         <h5 class="font-weight-bold text-uppercase mb-4">Address</h5> -->
                
<!--                         <ul class="list-unstyled"> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <i class="fas fa-home mr-3"></i> 四川 中壢 </p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <i class="fas fa-envelope mr-3"></i> zestinfo@google.com</p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <i class="fas fa-phone mr-3"></i> + 02 453 245 88</p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <i class="fas fa-print mr-3"></i> + 02 453 249 89</p> -->
<!--                           </li> -->
<!--                         </ul> -->
                
<!--                       </div> -->
<!--                       Grid column -->
<!--                       <hr class="clearfix w-100 d-md-none"> -->
<!--                       Grid column -->
                
<!--                     </div> -->
<!--                     Grid row -->
                
<!--                   </div> -->
<!--                   Footer Links -->
                
<!--                   Copyright -->
<!--                   <div class="footer-copyright text-center py-3">© 2020 Copyright: -->
<!--                     <a > 橙皮美食平台</a> -->
<!--                   </div> -->
<!--                   Copyright -->
                
<!--                 </footer> -->
<!--                 Footer -->
<!--                     </div> -->
        
</body>
</html>