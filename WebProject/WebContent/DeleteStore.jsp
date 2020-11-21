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
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!-- <c:out value="${param.id}"></c:out> -->
	 <div style="margin:10px 750px">
        <!-- 用container -->
        <form action="exDeleteStore.jsp" method="post" >
            <fieldset style="width: 400px;">
                <legend>確定要刪除此資料嗎?</legend>
                <input type="hidden" name="id" value="${param.id}">
                <label>商店名稱:
                    <input type="text" id="name" name="stname" value="${param.stname}" disabled>
                </label>
            </fieldset>
                    <div>
                        <input type="submit" value="確定">
                    </div>
        </form>
    </div>
</body>
</html>