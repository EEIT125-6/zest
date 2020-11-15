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
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="styles/WebUserRegisterForm.css">
</head>
<body>
	<form action="/zest/webUser/WebUserRegisterServlet" method="post" onSubmit="return checkForm();">
		<fieldset>
			<legend>註冊相關資料</legend>
			<hr />
			<label>帳號名稱：</label> 
			<input type="text" name="account" id="account" size="40" maxlength="20" onblur = "checkAccountName()"
				placeholder="請輸入帳號，6~20個字" />
			<input type="submit" name="checkAccount" value="檢查帳號">
			<span id="accountSpan"></span>
			<hr />
			<label>中文姓氏：</label>
			<input type="text" name="first_name" id="first_name" size="40" maxlength="3" onblur = "checkFirst_name()"
			    placeholder="請輸入姓氏，1~3個中文字">
			<span id="first_nameSpan"></span>
			<hr />
			<label>中文名字：</label>
			<input type="text" name="last_name" id="last_name" size="40" maxlength="3" onblur = "checkLast_name()"
			    placeholder="請輸入名字，1~3個中文字">
			<span id="last_nameSpan"></span>
			<hr />
			<label>稱呼方式：</label>
			<input type="text" name="nickname" id="nickname" size="40" maxlength="20" onblur = "checkNickname()"
			    placeholder="請輸入想要的稱呼(留白的話會設定為名字)">
			<span id="nicknameSpan"></span>
			<hr />
			<label>生理性別：</label>
			<input type="radio" id="M" name="gender" value="M">
		    <label for="male">男性</label>
		    <input type="radio" id="F" name="gender" value="F">
		    <label for="female">女性</label>
		    <input type="radio" id="N" name="gender" value="N" checked="checked">
		    <label for="other">不方便提供</label>
		    <hr />
		    <label>西元生日：</label>
			<input type="date" name="birthday" id="birthday" onblur = "checkBirthday()">
			<span id="birthdaySpan"></span>
			<hr />
			<label>偏好食物：</label>
			<input type="text" name="fervor" id="fervor" size="40" maxlength="30" onblur = "checkFervor()"
			    placeholder="請輸入偏好的食物類型，彼此間請空一格">
			<span id="fervorSpan"></span>
			<hr />
			<label>是否願意接收促銷/優惠訊息：</label>
			<input type="radio" id="get_email" name="get_email" value="true" checked="checked">
		    <label for="true">願意</label>
		    <input type="radio" id="get_email" name="get_email" value="false">
		    <label for="false">不願意</label>
		    <hr />
		    <label>居住區域：</label>
	    	<select name="location_code" id="location_code" onblur = "checkLocation_code()">
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
			<span id="location_codeSpan"></span>
		    <hr />
		    <label>生活地點一：</label>
		    <input type="text" name="addr0" id="addr0" size="65" maxlength="65" onblur = "checkAddr0()"
			    placeholder="此項為必填，請輸入完整地址方面後續服務之利用">
			<span id="addr0Span"></span>
		    <hr />
		    <label>生活地點二：</label>
		    <input type="text" name="addr1" id="addr1" size="65" maxlength="65"
			    placeholder="此項為選填">
			<span id="addr0Span"></span>
		    <hr />
		    <label>生活地點三：</label>
		    <input type="text" name="addr2" id="addr2" size="65" maxlength="65"
			    placeholder="此項為選填">
			<span id="addr0Span"></span>
		    <hr />
		</fieldset>
		<div align="center">
			<input type="submit" name="submit" value="送出">
		</div>
	</form>
	<script src = "scripts/WebUserRegisterForm.js"></script>
</body>
</html>
