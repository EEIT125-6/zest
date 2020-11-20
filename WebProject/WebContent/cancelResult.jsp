<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setContentType("text/html;charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>訂位取消</title>
</head>
<body>
<h2>您的預約已取消！</h2>
<a href="<c:url value='Page1.jsp'/>"></a>
<form action="./Page1.jsp" method="post" >
<input type="submit" name="back" value="返回">
</form>
</body>
</html>