<%@page import="Store.photoBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%request.setCharacterEncoding("UTF-8");%>
<%response.setContentType("text/html;charset=UTF-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>



<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>File Uploading Form</title>
</head>
<body>

	<h3>File Upload:</h3>
	Select a file to upload: <br />
	<form action="testexuploadbanner.jsp" method="post"
	                        enctype="multipart/form-data">
	<input type="file" name="file1" size="50" />
	<br />
	<input type="submit" value="Upload File" />
	</form>

	<%=request.getSession().getAttribute("banner") %>
	
</body>
</html>
