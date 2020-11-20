<%@page import="org.apache.tomcat.util.log.SystemLogHandler"%>
<%@page import="booking.bean.BookingBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
  
<%
response.setContentType("text/html;charset=UTF-8");
%>
<%
response.setContentType("text/html;charset=UTF-8");
response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
response.setHeader("Pragma","no-cache"); // HTTP 1.0
response.setDateHeader ("Expires", -1); // Prevents caching at the proxy server
%>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>訂位紀錄查詢</title>
</head>
<body>
<jsp:useBean id="booking" class="booking.bean.BookingBean" scope="session" />
<h2>
訂位紀錄如下 : 
</h2>

<table  cellspacing="1" cellpadding="1" border="1" width="500px">
<tr bgcolor="#F2F4FB">
    <td>訂單編號:</td>
    <td><jsp:getProperty name="booking" property="bookingNo" /></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>訂位日期:</td>
    <td><jsp:getProperty name="booking" property="bookingdate" /></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>時間:</td>
    <td><jsp:getProperty name="booking" property="time" /></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>人數:</td>
    <td><jsp:getProperty name="booking" property="number" /></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>姓名:</td>
    <td><jsp:getProperty name="booking" property="name" /></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>手機:</td>
    <td><jsp:getProperty name="booking" property="phone" /></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>e-mail:</td>
    <td><jsp:getProperty name="booking" property="mail" /></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>特殊需求:</td>
    <td><jsp:getProperty name="booking" property="needs" /></td>
</tr>
</table>

<a href="<c:url value='Page1.jsp'/>"></a>
<form action="./Page1.jsp" method="post" >
<input type="submit" name="back" value="返回">
</form> 

</body>
</html>