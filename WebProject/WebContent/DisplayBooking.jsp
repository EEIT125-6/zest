<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>訂位資料確認</title>
</head>
<body>
<jsp:useBean id="reg_booking" class="booking.bean.BookingBean" scope="session" />
<h2>
訂位資料如下 ! 請確認 !
</h2>
<form action=".\BookingServlet" method="post">
<table  cellspacing="1" cellpadding="1" border="1" width="500px">
<tr bgcolor="#F2F4FB">
    <td>訂單編號:</td>
    <td><jsp:getProperty name="reg_booking" property="bookingNo" /></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>訂位日期:</td>
    <td><jsp:getProperty name="reg_booking" property="bookingdate" /></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>時間:</td>
    <td><jsp:getProperty name="reg_booking" property="time" /></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>人數:</td>
    <td><jsp:getProperty name="reg_booking" property="number" /></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>姓名:</td>
    <td><jsp:getProperty name="reg_booking" property="name" /></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>聯絡電話:</td>
    <td><jsp:getProperty name="reg_booking" property="phone" /></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>E-mail:</td>
    <td><jsp:getProperty name="reg_booking" property="mail" /></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>用餐目的:</td>
    <td><jsp:getProperty name="reg_booking" property="purpose" /></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>特殊需求:</td>
    <td><jsp:getProperty name="reg_booking" property="needs" /></td>
</tr>
</table>
<div style="margin-left:200px;margin-top:10px;">
<input type="submit" name="confirm" value="確認" >
</div>
</form>
</body>
</html>