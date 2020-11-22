<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html;charset=UTF-8");
%>
<sql:setDataSource var="ds" dataSource="jdbc/zest" />
      <sql:update dataSource = "${ds}" var = "count">
         delete from Store  where id = ?

         <sql:param value="${param.id}"/>
      </sql:update>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<c:out value="${param.sclass}"></c:out> 
	<c:out value="${param.id}"></c:out> 
	<h1>SUCCESS!!</h1>

	
	<a href="Index1.jsp"><h2>點我回首頁</h2></a>
</body>
</html>