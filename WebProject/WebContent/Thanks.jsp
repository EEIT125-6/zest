<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  
<%
response.setContentType("text/html;charset=UTF-8");
%>


<!DOCTYPE html PUBLIC >
<c:redirect 
url="bookingForm.jsp"> 
</c:redirect>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>完成訂位</title>

</head>
<body>
<h2>訂位完成！</h2>
<a href="<c:url value='bookingForm.jsp'/>"></a>
<form action="./bookingForm.jsp" method="post" >
<input type="submit" name="back" value="返回">
</form> 
</body>
</html>

