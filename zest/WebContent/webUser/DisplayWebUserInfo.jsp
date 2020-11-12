<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
	response.setHeader("Pragma","no-cache"); // HTTP 1.0
	response.setDateHeader ("Expires", -1); // 防止proxy server進行快取
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>註冊資料確認</title>
</head>
<body>
<!-- 將放於Session中的JavaBean取出，class寫包含package的全名，scope設為session -->
<jsp:useBean id="reg_webUser" class="webUser.WebUserBean" scope="session" />
<h2>
註冊資料如下，如果無誤請按「確認」
</h2>
<form action="/zest/webUser/WebUserRegisterServlet" method="post">
	<table  style="border-spacing:2px; border-padding:1px; width:100%;" border="1">
		<tr>
			    <td>帳號：</td>
			    <td>
			    	<jsp:getProperty name="reg_webUser" property="user_id" />
			    </td>
			</tr>
			<tr>
			    <td>姓氏：</td>
			    <td>
			    	<jsp:getProperty name="reg_webUser" property="first_name" />
			    </td>
			</tr>
			<tr>
			    <td>名字：</td>
			    <td>
			    	<jsp:getProperty name="reg_webUser" property="last_name" />
			    </td>
			</tr>
			<tr>
			    <td>稱呼方式：</td>
			    <td>
			    	<jsp:getProperty name="reg_webUser" property="nickname" />
			    </td>
			</tr>
			<tr>
			    <td>性別：</td>
			    <td>
				    <jsp:getProperty name="reg_webUser" property="gender" />
			    </td>
			</tr>
			<tr>
			    <td>生日：</td>
			    <td>
				    <jsp:getProperty name="reg_webUser" property="birthday" />
			    </td>
			</tr>
			<tr>
			    <td>偏好食物：</td>
			    <td>
				    <jsp:getProperty name="reg_webUser" property="fervor" />
			    </td>
			</tr>
			<tr>
			    <td>是否願意接收促銷/優惠訊息：</td>
			    <td>
				    <jsp:getProperty name="reg_webUser" property="get_email" />
			    </td>
			</tr>
			<tr>
				<td>居住區域：</td>
				<td>
					<jsp:getProperty name="reg_webUser" property="location_code" />
				</td>
			</tr>
			<tr>
			    <td>活動地點一：</td>
			    <td>
				    <jsp:getProperty name="reg_webUser" property="addr0" />
			    </td>
			</tr>
			<tr>
			    <td>活動地點二：</td>
			    <td>
				    <jsp:getProperty name="reg_webUser" property="addr1" />
			    </td>
			</tr>
			<tr>
			    <td>活動地點三：</td>
			    <td>
				    <jsp:getProperty name="reg_webUser" property="addr2" />
			    </td>
			</tr>
	</table>
	<div align="center">
		<input type="submit" name="confirm" value="確認">
	</div>
</form>
</body>
</html>