<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<sql:setDataSource var="ds" dataSource="jdbc/EmployeeDB" />
<sql:query sql="select * from BAORD;" var="rs" dataSource="${ds}" /> 

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <form method=Post action="/WebProject/orange/CommentServlet">
 <label for="">搜尋:</label><input type="text" width="300" name="param">
 <input type="submit" name="select" value="select">
  <table border="1" >
    <th>name</th>
    <th>stars</th> 
    <th>date</th>
    <th>context</th>
    <th>photo</th>
    <c:forEach var="row" items="${rs.rows}">
    <tr>
    <td>${row.NAME}</td>
    <td>${row.STARS}</td>
    <td>${row.DATE}</td>
    <td>${row.CONTEXT}</td>
    <td>${row.PHOTO}</td>
    </tr>
    </c:forEach>
  </table>
</form>
</body>
</html>