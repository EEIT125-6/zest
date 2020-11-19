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
	<form action="/zest/webUser/WebUserServlet" method="post" onSubmit="return checkForm();">
		<fieldset>
			<legend>註冊相關資料</legend>
			<hr />
			<label>帳號名稱：</label> 
			<input type="text" name="account" id="account" size="40" maxlength="20" onblur="checkAccountName()"
				placeholder="請輸入帳號，6~20個字" required="required" />
			<input type="button" name="register" id="checkAccount" value="檢查帳號">
			<span id="accountSpan"></span>
			<hr />
			<label>帳號密碼：</label> 
			<input type="password" name="password" id="password" size="40" maxlength="20" onblur="checkAccountPassword()"
				placeholder="請輸入密碼，6~20個字" required="required" />
			<input type="button" name="visibility_switch" id="visibility_switch" value="顯示密碼" onclick="changeVisibility()">
			<span id="passwordSpan"></span>
			<hr />
			<label>中文姓氏：</label>
			<input type="text" name="first_name" id="first_name" size="40" maxlength="3" onblur="checkFirst_name()"
			    placeholder="請輸入姓氏，1~3個中文字" required="required" />
			<span id="first_nameSpan"></span>
			<hr />
			<label>中文名字：</label>
			<input type="text" name="last_name" id="last_name" size="40" maxlength="3" onblur="checkLast_name()"
			    placeholder="請輸入名字，1~3個中文字" required="required" />
			<span id="last_nameSpan"></span>
			<hr />
			<label>稱呼方式：</label>
			<input type="text" name="nickname" id="nickname" size="40" maxlength="20" onblur="checkNickname()"
			    placeholder="請輸入想要的稱呼(留白的話會設定為名字)" required="required" />
			<span id="nicknameSpan"></span>
			<hr />
			<label>生理性別：</label>
			<input type="radio" id="M" name="gender" value="M">
		    <label for="male">男性</label>
		    <input type="radio" id="F" name="gender" value="F">
		    <label for="female">女性</label>
		    <input type="radio" id="N" name="gender" value="N" checked="checked" >
		    <label for="other">不方便提供</label>
		    <hr />
		    <label>西元生日：</label>
			<input type="date" name="birthday" id="birthday" onblur="checkBirthday()" required="required" />
			<span id="birthdaySpan"></span>
			<hr />
			<label>偏好食物：</label>
			<input type="checkbox" name="fervor" value="米食" />
			<label>米食</label>
			<input type="checkbox" name="fervor" value="快餐" />
			<label>快餐</label>
			<input type="checkbox" name="fervor" value="燒肉" />
			<label>燒肉</label>
			<input type="checkbox" name="fervor" value="西式" />
			<label>西式</label>
			<input type="checkbox" name="fervor" value="下午茶" />
			<label>下午茶</label>
			<input type="checkbox" name="fervor" value="日式" />
			<label>日式</label>
			<input type="checkbox" name="fervor" value="皆可" checked="checked" onblur="checkFervor()"/>
			<label>皆可</label>
			<span id="fervorSpan"></span>
			<hr />
			<label>聯絡信箱：</label>
			<input type="email" name="email" id="email" size="40" maxlength="30" onblur="checkEmail()"
			    placeholder="請輸入驗證、聯絡用的E-Mail地址" required="required" />
			<span id="emailSpan"></span>
			<hr />
			<label>聯絡電話：</label>
			<input type="tel" name="phone" id="phone" size="40" maxlength="11" onblur="checkPhone()"
			    placeholder="請輸入行動電話或市內電話號碼" required="required" />
			<span id="phoneSpan"></span>
			<hr />
			<label>是否願意接收促銷/優惠訊息：</label>
			<input type="radio" id="get_email" name="get_email" value="Y" checked="checked">
		    <label for="Y">願意</label>
		    <input type="radio" id="get_email" name="get_email" value="N">
		    <label for="N">不願意</label>
		    <hr />
		    <label>居住區域：</label>
	    	<select name="location_code" id="location_code" onblur="checkLocation_code()">
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
		    <input type="text" name="addr0" id="addr0" size="65" maxlength="65" onblur="checkAddr0()"
			    placeholder="此項為必填，請輸入完整地址方面後續服務之利用" required="required" />
			<span id="addr0Span"></span>
		    <hr />
		    <label>生活地點二：</label>
		    <input type="text" name="addr1" id="addr1" size="65" maxlength="65"
			    placeholder="此項為選填">
		    <hr />
		    <label>生活地點三：</label>
		    <input type="text" name="addr2" id="addr2" size="65" maxlength="65"
			    placeholder="此項為選填">
		    <hr />
		</fieldset>
		<div align="center">
			<input type="submit" id="submit" name="register" value="送出">
			<input type="reset" name="reset" value="重設" onclick="clearMessage()">
		</div>
	</form>
	<script src="scripts/jquery-3.5.1.min.js"></script>
	<script src="scripts/WebUserRegisterForm.js"></script>
	<script>
		$("#checkAccount").click(function () {
	        checkSameAccount();
	    });
		function checkSameAccount(){
			let account = document.getElementById("account").value.trim();
			let accountSpan = document.getElementById("accountSpan");
			let accountStr;
			let accountIsOk = true;
			$.ajax({
				type:"POST",
	            url:"/zest/webUser/WebUserServlet",
	            data:{
	            	'register':'檢查帳號',
	            	'inputAccount':account
	            },
	            success:function(result) {
	            	let resultSpace = result.split(",");
	            	if(resultSpace[0] == '1') {
	            		accountStr = "此帳號已有人使用！";
	            		accountIsOk = false;
	            	} else if(resultSpace[0] == '0') {
	            		accountStr = "您可建立此帳號！";
	            		accountIsOk = true;
	            	} else if(resultSpace[0] == '-1') {
	            		accountStr = "檢查途中遭遇錯誤！";
	            		accountIsOk = false;
	            		/* 顯示彈窗異常訊息 */
	            		alert(resultSpace[1]);
	            	}
	            	if (!accountIsOk) {
	            		accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + accountStr;
	            		accountSpan.style.color = "red";
	            		accountSpan.style.fontStyle = "italic";
	            	} else {
	            		accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + accountStr;
	            		accountSpan.style.color = "black";
	            		accountSpan.style.fontStyle = "normal";
	            	}
	            },
	            error:function(err) {
	            	accountStr = "發生錯誤，無法執行檢查";
	            	accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + accountStr;
            		accountSpan.style.color = "red";
            		accountSpan.style.fontStyle = "italic";
	            }
			});
		}
	</script>
</body>
</html>
