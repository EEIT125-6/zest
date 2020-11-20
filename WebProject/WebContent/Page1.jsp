<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test</title>
<style>
        .aa{
            font-size:54px;
            color:#000;
            padding:10px;
            /* border:2px solid #e5e5e5; */
            vertical-align:middle;
            }
        .aa:hover{
            background:#e5e5e5
            } 
 </style>

</head>
<body>
<form action=".\BookingServlet" method="post" >

<label class="aa">
<input type="tel"  placeholder="請輸入手機號碼" name="phone" id="phone" >
</label>

<label class="aa">
<input type="submit" value="查詢訂位" name="select" id="select" >
</label>

<label class="aa">
<input type="submit" value="取消訂位" name="cancel" id="cancel" >
</label>

<label class="aa">
<input type="submit" value="更改訂位" name="update" id="update" >
</label>
</form>   
    
</body>
</html>