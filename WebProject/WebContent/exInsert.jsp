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

<sql:setDataSource var="ds" dataSource="jdbc/EmployeeDB" />
      <sql:update dataSource = "${ds}" var = "count">
      	INSERT INTO Store(stname,sclass,saddress,stitd,tel) VALUES (?,?,?,?,?);
      	
         <sql:param value="${param.stname}"/>
         <sql:param value="${param.sclass}"/>
         <sql:param value="${param.saddress}"/>
         <sql:param value="${param.stitd}"/>
         <sql:param value="${param.tel}"/>
      </sql:update>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h1>SUCCESS!!!</h1>
		<c:url value="detailStore.jsp" var="GOURL">
		<c:param name="stname" value="${param.stname}" />
		</c:url>
	<a href="${GOURL}"><h1>點我轉跳到商家頁面</h1></a>
	      <c:redirect url = "${GOURL}"/>
</body>
</html>