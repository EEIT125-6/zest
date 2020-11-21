<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", -1); // 防止proxy server進行快取
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>註冊流程已結束</title>
</head>
<body>
	<%=request.getAttribute("insertResultMessage") %>
</body>
</html>