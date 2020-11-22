<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
response.setContentType("text/html;charset=UTF-8");
response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
response.setHeader("Pragma","no-cache"); // HTTP 1.0
response.setDateHeader ("Expires", -1); // Prevents caching at the proxy server
request.setCharacterEncoding("UTF-8");
%>  

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Get Employee Information</title>
</head>
<body>
<jsp:useBean id="commentBean" class="orange.CommentBean" scope="session" />
 <form method=post action="/WebProject/orange/CommentServlet">

   <fieldset>
        <legend>留言</legend>
    <div class="st1">
        <label class="t1" for="name">姓名:</label>
        <jsp:getProperty name="commentBean" property="name" /><br>
    </div>
    <div class="st1">
        <label for="stars" class="t1">評分:</label>
        <jsp:getProperty name="commentBean" property="stars" /><br>
        
    </div> 
       <div class="st1">
        <label class="t1" for="date">時間:</label>
        <jsp:getProperty name="commentBean" property="date" /><br>
    </div>
   <div class="st1">
        <label class="t1" for="photo">照片:</label>
        <jsp:getProperty name="commentBean" property="photo" /><br>
    </div>
<div class="st1">
    <label class="t1" for="context">留言:</label>
    <jsp:getProperty name="commentBean" property="context" /></textarea><br>
</div>

    <div class="sub">
        <input type="submit" name="comfirm" value="送">
        <input type="reset" value="清"> 
    </div>
</fieldset>
   
</form>
</body>
</html>