<%-- <%@page import="Store.photoBean"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<%response.setContentType("text/html;charset=UTF-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
<div class="container" style="background-color: wheat;border-radius:5px;padding:100px;border: 1px solid wheat;box-shadow: 5px 5px 5px rgb(75, 75, 75);margin-top:15px;margin-bottom:15px; ">
	<form action="testexupload"  method="post" enctype="multipart/form-data">
	<h3>上傳至 ${stname}  圖片:</h3>
	請選擇一張圖片上傳: 
	<br />
	<input type="file" name="file1" size="50" style="margin-top:30px" />
	<br>
	
	<input type="submit" style="margin-top:30px" value="上傳圖片!" />
	</form>
	
</div>	
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:230px">
            <%@include file = "Footer-Include-prototype.jsp" %>
</body>
</html>
