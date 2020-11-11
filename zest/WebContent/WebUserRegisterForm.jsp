<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
	response.setHeader("Pragma","no-cache"); // HTTP 1.0
	response.setDateHeader ("Expires", -1); // 防止proxy server進行快取
%>
<html>
<head>
<meta charset="UTF-8">
<title>進行註冊</title>
</head>
<body>
	<h2>
		註冊相關資料
	</h2>
	<form action="/zest/webUser/WebUserRegisterServlet" method="post">
		<table border="1">
			<tr>
			    <td>姓氏：</td>
			    <td>
			    	<input type="text" name="first_name" id="first_name" size="3" maxlength="3">
			    </td>
			</tr>
			<tr>
			    <td>名字：</td>
			    <td>
			    	<input type="text" name="last_name" id="last_name" size="3" maxlength="3">
			    </td>
			</tr>
			<tr>
			    <td>稱呼方式：</td>
			    <td>
			    	<input type="text" name="last_name" id="last_name" size="20" maxlength="20">
			    </td>
			</tr>
			<tr>
			    <td>性別：</td>
			    <td>
				    <input type="radio" id="M" name="gender" value="M">
				    <label for="male">男姓</label>
				    <input type="radio" id="F" name="gender" value="F">
				    <label for="female">女性</label>
				    <input type="radio" id="N" name="gender" value="N">
				    <label for="other">不方便提供</label>
			    </td>
			</tr>
			<tr>
			    <td>生日：</td>
			    <td>
				    <input type="text" name="birthday" id="birthday" size="10" maxlength="10">
			    </td>
			</tr>
			<tr>
			    <td>偏好食物：</td>
			    <td>
				    <input type="text" name="fervor" id="fervor" size="30" maxlength="30">
			    </td>
			</tr>
			<tr>
			    <td>性別：</td>
			    <td>
				    <input type="radio" id="M" name="gender" value="M">
				    <label for="male">男姓</label>
				    <input type="radio" id="F" name="gender" value="F">
				    <label for="female">女性</label>
			    </td>
			</tr>
		</table>
	</form>
</body>
</html>
