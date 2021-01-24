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
    <div class="container">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 containerHeaderCard">
                <div class="h-100 containerHeaderFontCard">
                    <div class="containerHeaderFontDiv">
                       <span class="containerHeaderFontSpan">404ERROR</span>
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
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-12 containerBodyCardDiv">
                          <label class="containerBodyCardLabelFont" for="storename">請與技術人員聯絡</label>
                        </div>
                    </div>
            </div>
            <div style="height: 600px"></div>
        </div>
    </div>
    <%@include file = "Footer-Include.jsp" %>
</body>
</html>