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
<title>Insert title here</title>
</head>
<body>

<jsp:useBean id="commentBean" class="orange.CommentBean" scope="session" />
 <form method=post action="/WebProject/orange/CommentServlet">

   <fieldset>
        <legend>留言</legend>
    <div class="st1">
        <label class="t1" for="name">姓名:</label>
        <input type="text" name="name" value="<jsp:getProperty name="commentBean" property="name" />">
        <br>
    </div>
    <div class="st1">
        <label for="stars" class="t1">評分:</label>
        <input type="text" name="stars" value="<jsp:getProperty name="commentBean" property="stars" />">
        <br>
        
    </div> 
       <div class="st1">
        <label class="t1" for="date">時間:</label>
        <input type="date" readonly name="date" value="<jsp:getProperty name="commentBean" property="date" />">
<br>
    </div>
   <div class="st1">
        <label class="t1" for="photo">照片:</label>
        <input type="text" name="photo" value="<jsp:getProperty name="commentBean" property="photo" />">
        <br>
    </div>
<div class="st1">
    <label class="t1" for="comment">留言:</label>
    <textarea name="comment" id="" cols="30" rows="10" ><jsp:getProperty name="commentBean" property="context" /></textarea>
    <br>
</div>

    <div class="sub">
        <input type="submit" name="update" value="更新">
        <input type="submit" name="delete" value="刪除"> 
    </div>
</fieldset>
   
</form>


</body>
</html>