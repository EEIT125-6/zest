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
			    <td>是否願意接收促銷/優惠訊息：</td>
			    <td>
				    <input type="radio" id="get_email" name="get_email" value="true">
				    <label for="male">願意</label>
				    <input type="radio" id="get_email" name="get_email" value="false">
				    <label for="female">不願意</label>
			    </td>
			</tr>
			<tr>
				<td>居住區域：</td>
				<td>
					<select>
						<option value="">請選擇目前您居住/生活的區域</option>
						<option value="t01">臺北市</option>
						<option value="t02">新北市</option>
						<option value="t03">桃園市</option>
						<option value="t04">臺中市</option>
						<option value="t05">臺南市</option>
						<option value="t06">高雄市</option>
						<option value="t07">基隆市</option>
						<option value="t08">新竹市</option>
						<option value="t09">嘉義市</option>
						<option value="t10">新竹縣</option>
						<option value="t11">苗栗縣</option>
						<option value="t12">彰化縣</option>
						<option value="t13">南投縣</option>
						<option value="t14">雲林縣</option>
						<option value="t15">嘉義縣</option>
						<option value="t16">屏東縣</option>
						<option value="t17">宜蘭縣</option>
						<option value="t18">花蓮縣</option>
						<option value="t19">臺東縣</option>
						<option value="t20">澎湖縣</option>
						<option value="t21">金門縣</option>
						<option value="t22">連江縣</option>
						<option value="t23">其他區</option>
					</select>
				</td>
			</tr>
			<tr>
			    <td>活動地點一：</td>
			    <td>
				    <input type="text" name="addr0" id="addr0" size="65" maxlength="65">
			    </td>
			</tr>
			<tr>
			    <td>活動地點二：</td>
			    <td>
				    <input type="text" name="addr1" id="addr1" size="65" maxlength="65">
			    </td>
			</tr>
			<tr>
			    <td>活動地點三：</td>
			    <td>
				    <input type="text" name="addr2" id="addr2" size="65" maxlength="65">
			    </td>
			</tr>
		</table>
	</form>
</body>
</html>
