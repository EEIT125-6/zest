<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
	response.setHeader("Pragma","no-cache"); // HTTP 1.0
	response.setDateHeader ("Expires", -1); // 防止proxy server進行快取
%>
<!-- taglib宣告 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- taglib宣告 -->
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file = "Link_Meta-Include.jsp" %>
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/webUser/WebUserRegisterForm.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoadingScreen.css"> 
   	<link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />       
    <title>進行註冊</title>
    <style>
        .classimg{
		 transition: 0.2s;	
        	width:80px
        }
        .classimg:hover{
         transition: 0.2s;	        
			width: 85px
        }
        body{
         background-color: 		rgb(235, 159, 18);
       }
       .header{
            height: 100px;
            border-bottom: 3px solid #e76f51;height: 90px;
            padding-top: 5px;
            background-color: #003049
       }
       .photo{
           padding: 0%;
           background: url("Images/backbar2-1.jpg"); 
           height: 540px;
           padding-top: 220px;
           background-size:100%
       }
       .shopcar{
            height: 40px;
            margin: 0;
            margin-left:5px ;
       }
        .wrapper{
            position: relative;
            width:1000px;
            height:400px;
            overflow: hidden;
            margin:0 auto;
            border-radius:5px;   
        }
        ul{
            margin:0;
            padding: 0;
            position: absolute;
        }
        li{
            margin:0;
            padding: 0;
            list-style: none;
        }
        ul.slides{
            width: 4000px;
            left: 0px;
            transition: all .5s;
        }
        ul.slides li{
            width:1000px;
            height:400px;
            overflow: hidden;
            float: left;
        }
        ul.slides li img{
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .dot{
            bottom:10px;
            width:100%;
            display: flex;
            justify-content: center;
        }
        .dot li{
            border:1px solid #fff;
            
            margin: 0 5px;
            width:24px;
            height: 10px;
        }

        .slide_btn{
            display: flex;
            justify-content: center;
            align-items: center;
            top:0;
            bottom:0;
            width: 30px;
            color:#fff;
            position: absolute;
            font-size:24px;  
            
        }
        #prevSlide{            
            left:0;                    
        }
        #nextSlide{            
            right:0;                
        }
        .slide_btn i{
            color:rgba(255,255,255,.6);                        
            transition: .5s;
        }
        .slide_btn:hover i{
            color:rgba(255,255,255,1);            
        }
        
        
#gotop {
    position:fixed;
    z-index:90;
    right:30px;
    bottom:31px;
    display:none;
    width:50px;
    height:50px;
    color:#fff;
    background:#ddbe56;
    line-height:50px;
    border-radius:50%;
    transition:all 1.5s;
    text-align: center;
    box-shadow: 0 2px 5px 0 rgba(0,0,0,0.16), 0 2px 10px 0 rgba(0,0,0,0.12);
}
#gotop :hover{
    background:#0099CC;
}
    </style>
</head>
<body>
            <%@include file = "Header-Include.jsp" %>
            <%@include file = "LoadingScreen.jsp" %>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
               <c:if test="${userFullData.password != null}">
					<c:redirect url="/webUser/WebUserMain" />
				</c:if>
               <form action="<c:url value='/webUser/controller/WebUserRegisterForm' />" method="post" onSubmit="return checkForm();">
					<fieldset>
						<legend>註冊相關資料</legend>
						<hr />
                		<div align="center">
	                		<button type="button" id="userInput">一鍵輸入</button>
                		</div>
						<span id="submitSpan">
							<c:if test="${timeOut != null}">
								<i class='material-icons' style='font-size:18px;color:red'>cancel</i>
								<c:out value="${timeOut}" />
							</c:if>
							<c:if test="${submitMessage != null}">
								<i class='material-icons' style='font-size:18px;color:red'>cancel</i>
								<c:out value="${submitMessage}" />
							</c:if>
						</span>
						<hr />
						<label>帳號身分：</label>
						<c:forEach items="${identityList}" var="level" >
							<c:if test="${level.lv==0}" >
								<input type="radio" name="userLv" value="${level.lv}" checked="checked" />
							</c:if>
							<c:if test="${level.lv!=0}" >
								<input type="radio" name="userLv" value="${level.lv}"/>
							</c:if>
							<label><c:out value="${level.levelName}" /></label>
						</c:forEach>
					    <hr />
						<label>帳號名稱：</label> 
						<input type="text" name="account" id="account" size="30" maxlength="30" onblur="checkAccountName()"
							placeholder="請輸入帳號，6~30個字" required="required" />
						<span id="accountSpan"></span>
						<hr />
						<label>帳號密碼：</label> 
						<input type="password" name="password" id="password" size="30" maxlength="30" onblur="checkAccountPassword()"
							placeholder="請輸入密碼，6~30個字" required="required" />
						<button type="button" style="font-size:18px" id="visibility_switch" onclick="changeVisibility()">顯示密碼 <i class="material-icons" style="font-size:18px;color:red">visibility</i></button>
						<span id="passwordSpan"></span>
						<hr />
						<label>中文姓氏：</label>
						<input type="text" name="firstName" id="firstName" size="30" maxlength="3" onblur="checkFirst_name()"
						    placeholder="請輸入姓氏，1~3個中文字" required="required" />
						<span id="firstNameSpan"></span>
						<hr />
						<label>中文名字：</label>
						<input type="text" name="lastName" id="lastName" size="30" maxlength="22" onblur="checkLast_name()"
						    placeholder="請輸入名字，1~22個中文字" required="required" />
						<span id="lastNameSpan"></span>
						<hr />
						<label>稱呼方式：</label>
						<input type="text" name="nickname" id="nickname" size="30" maxlength="25" onblur="checkNickname()"
						    placeholder="請輸入想要的稱呼(留白的話會設定為名字)" required="required" />
						<span id="nicknameSpan"></span>
						<hr />
						<label>生理性別：</label>
						<c:forEach items="${genderList}" var="userGender" >
							<c:if test="${userGender.genderCode=='N'}" >
								<input type="radio" name="gender" value="${userGender.genderCode}" checked="checked" />
							</c:if>
							<c:if test="${userGender.genderCode!='N'}" >
								<input type="radio" name="gender" value="${userGender.genderCode}" />
							</c:if>
							<label><c:out value="${userGender.genderText}" /></label>
						</c:forEach>
					    <hr />
					    <label>西元生日：</label>
						<input type="date" name="birth" id="birth" onblur="checkBirthday()" required="required" />
						<span id="birthdaySpan"></span>
						<hr />
						<label>偏好食物：</label>
						<c:forEach items="${fervorList}" var="fervorObject" >
							<c:if test="${fervorObject.fervorCode == fervorList.size()}" >
								<input type="checkbox" name="fervorOption" class="fervor" value="${fervorObject.fervorCode}" checked="checked" onblur="checkFervor()" />
							</c:if>
							<c:if test="${fervorObject.fervorCode != fervorList.size()}" >
								<input type="checkbox" name="fervorOption" class="fervor" value="${fervorObject.fervorCode}" onblur="checkFervor()" />
							</c:if>
							<label><c:out value="${fervorObject.fervorItem}" ></c:out></label>
						</c:forEach>
						<span id="fervorSpan"></span>
						<hr />
						<label>聯絡信箱：</label>
						<input type="email" name="email" id="email" size="30" maxlength="30" onblur="checkEmail()"
						    placeholder="請輸入驗證、聯絡用的E-Mail地址" required="required" />
						<span id="emailSpan"></span>
						<hr />
						<label>信箱驗證：</label>
						<input type="text" name="emailCheckCode" id="emailCheckCode" size="30" maxlength="8" onblur="checkEmailCheckCode()"
						    placeholder="請輸入E-Mail中所收到的驗證碼" required="required" />
						<span id="emailCheckCodeSpan"></span>
						<br />
						<input type="hidden" name="inputCheckCode" id="checkCode" value="" />
						<hr />
						<label>聯絡電話：</label>
						<input type="tel" name="phone" id="phone" size="30" maxlength="11" onblur="checkPhone()"
						    placeholder="請輸入行動電話或市內電話號碼" required="required" />
						<span id="phoneSpan"></span>
						<hr />
						<label>是否願意接收促銷/優惠訊息：</label>
						<c:forEach items="${willingList}" var="userWilling" >
							<c:if test="${userWilling.willingCode=='Y'}" >
								<input type="radio" name="getEmail" value="${userWilling.willingCode}" checked="checked" />
							</c:if>
							<c:if test="${userWilling.willingCode=='N'}" >
								<input type="radio" name="getEmail" value="${userWilling.willingCode}" />
							</c:if>
							<label><c:out value="${userWilling.willingText}" /></label>
						</c:forEach>
					    <hr />
					    <label>居住區域：</label>
					    <select name="locationCode" id="locationCode" onblur="checkLocation_code()" >
					    	<option value="">請選擇目前您居住/生活的區域</option>
						    <c:forEach items="${cityInfoList}" var="cityInfo" >
						    	<option value="${cityInfo.cityCode}" label="${cityInfo.cityName}" />
						    </c:forEach>
					    </select>
						<span id="locationCodeSpan"></span>
					    <hr />
					    <label>生活地點一：</label>
					    <input type="text" name="addr0" id="addr0" size="65" maxlength="65" onblur="checkAddr0()"
						    placeholder="此項為必填，請輸入完整地址方面後續服務之利用" required="required" />
						<br />
						<span id="addr0Span"></span>
					    <hr />
					    <label>生活地點二：</label>
					    <input type="text" name="addr1" id="addr1" size="65" maxlength="65"  onblur="checkAddr1()"
						    placeholder="此項為選填">
						<br />
						<span id="addr1Span"></span>
					    <hr />
					    <label>生活地點三：</label>
					    <input type="text" name="addr2" id="addr2" size="65" maxlength="65"  onblur="checkAddr2()"
						    placeholder="此項為選填">
						<br />
						<span id="addr2Span"></span>
					    <hr />
					</fieldset>
					<div align="center">
						<button type="submit" style="font-size:18px" id="submit" name="register" >送出 <i class="material-icons" style="font-size:18px;color:blue">check</i></button>
						<button type="reset" id="reset" name="reset" style="font-size:18px" onclick="clearMessage()">重設 <i class="material-icons" style="font-size:18px;color:blue">refresh</i></button>
						<a href="<c:url value='/WebUserLogin' /> "><button type="button" name="login" style="font-size:18px" >前往登入 <i class="material-icons" style="font-size:18px;color:green">undo</i></button></a>
					</div>
					<hr />
				</form>
				<script src="<c:url value='/js/webUser/WebUserRegisterForm.js' />"></script>
				<script>
					window.onload = function() {
						let userAutoInputBtn = document.getElementById("userInput");
						
						userAutoInputBtn.onclick = function() {
                			document.getElementById("account").value = "George610787";
                			document.getElementById("password").value = "Geo1rge6";
                			document.getElementById("firstName").value = "王";
                			document.getElementById("lastName").value = "小明";
                			document.getElementById("nickname").value = "小明";
                			document.getElementById("birth").value = "2000-10-20";
                			document.getElementById("email").value = "georgecycuphy@cycu.org.tw";
                			document.getElementById("phone").value = "0911773355";
                			document.getElementById("locationCode").value = 13;
                			document.getElementById("addr0").value = "桃園市中壢區中大路300號";
                			document.getElementById("addr1").value = "宜蘭縣大同鄉太平巷58之1號";
                			document.getElementById("addr2").value = "南投縣鹿谷鄉興產路2之3號";
                		};
					};
					
					function checkSameAccount() {
						let account = document.getElementById("account").value.trim();
						let accountSpan = document.getElementById("accountSpan");
						let accountStr = "...處理中，請稍後";
						let accountIsOk = true;
						let mode = "checkAccount";
						
						accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + accountStr;
	            		accountSpan.style.color = "black";
	            		accountSpan.style.fontStyle = "normal";
						
						let xhrObject = new XMLHttpRequest();
						if (xhrObject != null) {
							xhrObject.open("POST", "<c:url value='/webUser/controller/UserInfoController' />", true);
							xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
							xhrObject.send("inputAccount=" + account + "&register=" + mode);
							
							xhrObject.onreadystatechange = function() {
								if (xhrObject.readyState === 4 && xhrObject.status === 200) {
									let typeObject = xhrObject.getResponseHeader("Content-Type");
									if (typeObject.indexOf("application/json") === 0) {
										let resultObj = JSON.parse(xhrObject.responseText);
										if (resultObj.resultCode == 1) {
											accountStr = "此帳號已有人使用！";
						            		accountIsOk = false;
										} else if (resultObj.resultCode == 0) {
											accountStr = "可建立此帳號！";
						            		accountIsOk = true;
										} else if (resultObj.resultCode == -1) {
											accountStr = "檢查途中遭遇錯誤！";
						            		accountIsOk = false;
						            		/* 顯示彈窗異常訊息 */
						            		alert(resultObj.resultMessage);
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
									} else {
										accountStr = "發生錯誤，無法執行檢查";
						            	accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + accountStr;
					            		accountSpan.style.color = "red";
					            		accountSpan.style.fontStyle = "italic";
									}
								} 
							};
						} else {
							alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
						}
					}
					
					function checkSameNickname() {
						let nickname = document.getElementById("nickname").value.replace('<', ' ').replace('>', ' ').trim();
						let nicknameSpan = document.getElementById("nicknameSpan");
						let nicknameStr = "...處理中，請稍後";
						let nicknameIsOk = true;
						let mode = "checkNickname";
						
						nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + nicknameStr;
	            		nicknameSpan.style.color = "black";
	            		nicknameSpan.style.fontStyle = "normal";
						
						let xhrObject = new XMLHttpRequest();
						if (xhrObject != null) {
							xhrObject.open("POST", "<c:url value='/webUser/controller/UserInfoController' />", true);
							xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
							xhrObject.send("inputNickname=" + nickname + "&register=" + mode);
							
							xhrObject.onreadystatechange = function() {
								if (xhrObject.readyState === 4 && xhrObject.status === 200) {
									let typeObject = xhrObject.getResponseHeader("Content-Type");
									if (typeObject.indexOf("application/json") === 0) {
										let resultObj = JSON.parse(xhrObject.responseText);
										if (resultObj.resultCode == 1) {
											nicknameStr = "此稱呼已有人使用！";
											nicknameIsOk = false;
										} else if (resultObj.resultCode == 0) {
											nicknameStr = "可使用此稱呼！";
											nicknameIsOk = true;
										} else if (resultObj.resultCode == -1) {
											nicknameStr = "檢查途中遭遇錯誤！";
											nicknameIsOk = false;
						            		/* 顯示彈窗異常訊息 */
						            		alert(resultObj.resultMessage);
										}
										if (!nicknameIsOk) {
											nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + nicknameStr;
											nicknameSpan.style.color = "red";
											nicknameSpan.style.fontStyle = "italic";
						            	} else {
						            		nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + nicknameStr;
						            		nicknameSpan.style.color = "black";
						            		nicknameSpan.style.fontStyle = "normal";
						            	}
									} else {
										nicknameStr = "發生錯誤，無法執行檢查";
										nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + nicknameStr;
										nicknameSpan.style.color = "red";
										nicknameSpan.style.fontStyle = "italic";
							        }
								}
							};
						} else {
							alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
						}
					}
					
					function checkSameEmail() {
						let email = document.getElementById("email").value.replace('<', ' ').replace('>', ' ').trim();
						let emailSpan = document.getElementById("emailSpan");
						let emailStr = "...處理中，請稍後";
						let emailIsOk = true;
						let mode = "checkEmail";
						
						emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + emailStr;
	            		emailSpan.style.color = "black";
	            		emailSpan.style.fontStyle = "normal";
						
						let xhrObject = new XMLHttpRequest();
						if (xhrObject != null) {
							xhrObject.open("POST", "<c:url value='/webUser/controller/UserInfoController' />", true);
							xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
							xhrObject.send("inputEmail=" + email + "&register=" + mode);
							
							xhrObject.onreadystatechange = function() {
								if (xhrObject.readyState === 4 && xhrObject.status === 200) {
									let typeObject = xhrObject.getResponseHeader("Content-Type");
									if (typeObject.indexOf("application/json") === 0) {
										let resultObj = JSON.parse(xhrObject.responseText);
										if (resultObj.resultCode == 1) {
											emailStr = "此電子信箱已有人使用！";
						            		emailIsOk = false;
										} else if (resultObj.resultCode == 0) {
											emailStr = "可使用此電子信箱！";
						            		emailIsOk = true;
										} else if (resultObj.resultCode == -1) {
											emailStr = "檢查途中遭遇錯誤！";
						            		emailIsOk = false;
						            		/* 顯示彈窗異常訊息 */
						            		alert(resultObj.resultMessage);
										}
										if (!emailIsOk) {
						            		emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailStr;
						            		emailSpan.style.color = "red";
						            		emailSpan.style.fontStyle = "italic";
						            	} else {
						            		emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailStr;
						            		emailSpan.style.color = "black";
						            		emailSpan.style.fontStyle = "normal";
						            		let choice=confirm("是否要寄往 " + email + " ?");
						            		if (choice) {
						            			sendEmailCheckCode();
						            		}
						            	}
									} else {
										emailStr = "發生錯誤，無法執行檢查";
						            	emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailStr;
						            	emailSpan.style.color = "red";
						            	emailSpan.style.fontStyle = "italic";
									}
								} 
							};
						} else {
							alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
						}
					}
					
					function sendEmailCheckCode() {
						let email = document.getElementById("email").value.trim();
						let account = document.getElementById("account").value.trim();
						let checkCode = document.getElementById("checkCode");
						let emailCheckCodeSpan = document.getElementById("emailCheckCodeSpan");
						let checkCodeStr;
						let emailCheckCodeStr = "...處理中，請稍後";
						let emailCheckCodeIsOk = true;
						let mode = "sendCheckCode";
						
						emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + emailCheckCodeStr;
	            		emailCheckCodeSpan.style.color = "black";
	            		emailCheckCodeSpan.style.fontStyle = "normal";
						
						let xhrObject = new XMLHttpRequest();
						if (xhrObject != null) {
							xhrObject.open("POST", "<c:url value='/webUser/controller/UserInfoController' />", true);
							xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
							xhrObject.send("inputAccount=" + account + "&inputEmail=" + email + "&register=" + mode);	
							
							xhrObject.onreadystatechange = function() {
								if (xhrObject.readyState === 4 && xhrObject.status === 200) {
									let typeObject = xhrObject.getResponseHeader("Content-Type");
									if (typeObject.indexOf("application/json") === 0) {
										let resultObj = JSON.parse(xhrObject.responseText);
										if (resultObj.resultCode == "true") {
											emailCheckCodeStr = "驗證碼已成功寄出！";
						            		emailCheckCodeIsOk = true;
						            		checkCodeStr = resultObj.resultText;
						            		/* 顯示彈窗異常訊息 */
						            		alert(resultObj.resultMessage);
										} else if (resultObj.resultCode == "false") {
											emailCheckCodeStr = "遭遇錯誤！請稍後再試！";
						            		emailCheckCodeIsOk = false;
						            		/* 顯示彈窗異常訊息 */
						            		alert(resultObj.resultMessage);
										}
										if (!emailCheckCodeIsOk) {
						            		emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailCheckCodeStr;
						            		emailCheckCodeSpan.style.color = "red";
						            		emailCheckCodeSpan.style.fontStyle = "italic";
						            		checkCode.innerHTML = "";
						            	} else {
						            		emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailCheckCodeStr;
						            		emailCheckCodeSpan.style.color = "black";
						            		emailCheckCodeSpan.style.fontStyle = "normal";
						            		document.getElementById("checkCode").value = checkCodeStr;
						            	}
									} else {
										emailCheckCodeStr = "發生錯誤，無法送出驗證碼";
						            	emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailCheckCodeStr;
						            	emailCheckCodeSpan.style.color = "red";
						            	emailCheckCodeSpan.style.fontStyle = "italic";
									}
								} 
							};
						} else {
							alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
						}
					} 
					
					function checkSamePhone() {
						let phone = document.getElementById("phone").value.trim();
						let phoneSpan = document.getElementById("phoneSpan");
						let phoneStr = "...處理中，請稍後";
						let phoneIsOk = true;
						let mode = "checkPhone";
						
						phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + phoneStr;
	            		phoneSpan.style.color = "black";
	            		phoneSpan.style.fontStyle = "normal";
						
						let xhrObject = new XMLHttpRequest();
						if (xhrObject != null) {
							xhrObject.open("POST", "<c:url value='/webUser/controller/UserInfoController' />", true);
							xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
							xhrObject.send("inputPhone=" + phone + "&register=" + mode);
							
							xhrObject.onreadystatechange = function() {
								if (xhrObject.readyState === 4 && xhrObject.status === 200) {
									let typeObject = xhrObject.getResponseHeader("Content-Type");
									if (typeObject.indexOf("application/json") === 0) {
										let resultObj = JSON.parse(xhrObject.responseText);
										if (resultObj.resultCode == 1) {
											phoneStr = "此聯絡電話已有人使用！";
						            		phoneIsOk = false;
										} else if (resultObj.resultCode == 0) {
											phoneStr = "可使用此聯絡電話！";
						            		phoneIsOk = true;
										} else if (resultObj.resultCode == -1) {
											phoneStr = "檢查途中遭遇錯誤！";
						            		phoneIsOk = false;
						            		/* 顯示彈窗異常訊息 */
						            		alert(resultObj.resultMessage);
										}
										if (!phoneIsOk) {
						            		phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + phoneStr;
						            		phoneSpan.style.color = "red";
						            		phoneSpan.style.fontStyle = "italic";
						            	} else {
						            		phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + phoneStr;
						            		phoneSpan.style.color = "black";
						            		phoneSpan.style.fontStyle = "normal";
						            	}
									} else {
										phoneStr = "發生錯誤，無法執行檢查";
						            	phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + phoneStr;
						            	phoneSpan.style.color = "red";
						            	phoneSpan.style.fontStyle = "italic";
									}
								} 
							};
						} else {
							alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
						}
					}
				</script> 
            </div>
<!-- -------------------------------------------------------------------- -->
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:20px">
            	<%@include file = "Footer-Include-prototype.jsp" %>
            </div>
</body>
</html>