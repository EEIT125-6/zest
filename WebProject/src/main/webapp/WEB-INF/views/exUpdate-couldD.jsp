<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html;charset=UTF-8");
%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- <sql:setDataSource var="ds" dataSource="jdbc/zest" /> --%>
<%--       <sql:update dataSource = "${ds}" var = "count"> --%>
<!--          update Store set stname=? ,sclass=? ,saddress=? ,stitd=?, tel=? where id = ? -->
<%--          <sql:param value="${param.stname}"/> --%>
<%--          <sql:param value="${param.sclass}"/> --%>
<%--          <sql:param value="${param.saddress}"/> --%>
<%--          <sql:param value="${param.stitd}"/> --%>
<%--          <sql:param value="${param.tel}"/> --%>
<%--          <sql:param value="${param.id}"/> --%>
<%--       </sql:update> --%>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>SUCCESS!!!</h1>
		<c:url value="StoreGetFullstore" var="GOURL">
			<c:param name="id" value="${id}" />
			<c:param name="stname" value="${stname1}" />			
		</c:url>
<%-- 	<a href="${GOURL}"><h1>點我轉跳到商家頁面</h1></a> --%>
	      <c:redirect url = "${GOURL }"/>
</body>
</html>