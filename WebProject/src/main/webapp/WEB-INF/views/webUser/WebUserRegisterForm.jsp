<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="webUser.model.UserIdentity"%>
<%@ page import="webUser.model.Gender"%>
<%@ page import="webUser.model.FoodFervor"%>
<%@ page import="webUser.model.UserWilling"%>
<%@ page import="webUser.model.CityInfo"%>
<% 
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
	response.setHeader("Pragma","no-cache"); // HTTP 1.0
	response.setDateHeader ("Expires", -1); // 防止proxy server進行快取
%>
<!-- taglib宣告 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!-- taglib宣告 -->
<!DOCTYPE html>
<html lang="en">
<head>
	<%@include file = "../Link_Meta-Include.jsp" %>
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/webUser/WebUserRegisterForm.css">       
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
            <%@include file = "../Header-Include.jsp" %>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
               <c:if test="${userFullData.password != null}">
					<c:redirect url="WebUserMain.jsp" />
				</c:if>
				<form:form method="POST" modelAttribute="userRegisterData">
					<legend>註冊相關資料</legend>
					<span id="submitSpan">
						<c:if test="${submitMessage != null}">
							<i class='material-icons' style='font-size:18px;color:red'>cancel</i>
							<c:out value="${submitMessage}" />
						</c:if>
					</span>
					<hr />
					<label>帳號身分：</label>
					<c:forEach items="${identityList}" var="level" >
						<c:if test="${level.lv==0}" >
							<form:radiobutton path="accountLv" value="${level.lv}" checked="true" />
						</c:if>
						<c:if test="${level.lv!=0}" >
							<form:radiobutton path="accountLv" value="${level.lv}" />
						</c:if>
						<label><c:out value="${level.levelName}" /></label>
					</c:forEach>
					<hr />
					<label>帳號名稱：</label>
					<form:input path="account" id="account" size="40" maxlength="20" onblur="checkAccountName()" 
						placeholder="請輸入帳號，8~20個字" required="required" />
					<input type="button" name="register" id="checkAccount" value="檢查帳號">
					<span id="accountSpan"></span>
					<hr />
					<label>帳號密碼：</label>
					<form:password path="password" id="password" size="40" maxlength="20" onblur="checkAccountPassword()" 
						placeholder="請輸入密碼，8~20個字" required="required" />
					<input type="button" name="visibility_switch" id="visibility_switch" value="顯示密碼" onclick="changeVisibility()">
					<span id="passwordSpan"></span>
					<hr />
					<label>中文姓氏：</label>
					<form:input path="firstName" id="firstName" size="40" maxlength="3" onblur="checkFirst_name()" 
						placeholder="請輸入姓氏，1~3個字" required="required" />
					<span id="firstNameSpan"></span>
					<hr />
					<label>中文名字：</label>
					<form:input path="lastName" id="lastName" size="40" maxlength="3" onblur="checkLast_name()" 
						placeholder="請輸入名字，1~3個字" required="required" />
					<span id="lastNameSpan"></span>
					<hr />
					<label>稱呼方式：</label>
					<form:input path="nickname" id="nickname" size="40" maxlength="20" onblur="checkNickname()" 
						placeholder="請輸入想要的稱呼(留白的話會設定為名字)" required="required" />
					<input type="button" name="register" id="checkRegisterNickname" value="檢查稱呼">
					<span id="nicknameSpan"></span>
					<hr />
					<label>生理性別：</label>
					<c:forEach items="${genderList}" var="userGender" >
						<c:if test="${userGender.genderCode=='N'}" >
							<form:radiobutton path="gender" value="${userGender.genderCode}" checked="true" />
						</c:if>
						<c:if test="${userGender.genderCode!='N'}" >
							<form:radiobutton path="gender" value="${userGender.genderCode}" />
						</c:if>
						<label><c:out value="${userGender.genderText}" /></label>
					</c:forEach>
					<hr />
					<label>西元生日：</label>
					<form:input type="date" path="birth" id="birth" onblur="checkBirthday()" required="required" />
					<span id="birthdaySpan"></span>
					<hr />
					<label>偏好食物：</label>
					<form:checkboxes items="${fervorList}" path="fervorOption" 
						itemLabel="fervorItem" itemValue="fervorCode" />
					<hr />
					<label>聯絡信箱：</label>
					<form:input type="email" path="email" id="email" size="40" maxlength="30" onblur="checkEmail()" 
						placeholder="請輸入驗證、聯絡用的E-Mail地址" required="required" />
					<input type="button" name="register" id="checkEmailUsed" value="檢查信箱">
					<span id="emailSpan"></span>
					<hr />
					<label>信箱驗證：</label>
					<input type="text" name="emailCheckCode" id="emailCheckCode" size="40" maxlength="8" onblur="checkEmailCheckCode()" 
						placeholder="請輸入E-Mail中所收到的驗證碼" required="required" />
					<input type="button" name="register" id="sendCheckCode" value="傳送驗證碼">
					<span id="emailCheckCodeSpan"></span>
					<br />
					<input type="hidden" name="checkCode" id="checkCode" value="" />
					<hr />
					<label>聯絡電話：</label>
					<form:input type="phone" path="phone" id="phone" size="40" maxlength="11" onblur="checkPhone()"
						placeholder="請輸入行動電話或市內電話號碼" required="required" />
					<input type="button" name="register" id="checkRegisterPhone" value="檢查電話">
					<span id="phoneSpan"></span>
					<hr />
					<label>是否願意接收促銷/優惠訊息：</label>
					<c:forEach items="${willingList}" var="userWilling" >
						<c:if test="${userWilling.willingCode=='Y'}" >
							<form:radiobutton path="getEmail" value="${userWilling.willingCode}" checked="true" />
						</c:if>
						<c:if test="${userWilling.willingCode=='N'}" >
							<form:radiobutton path="getEmail" value="${userWilling.willingCode}" />
						</c:if>
						<label><c:out value="${userWilling.willingText}" /></label>
					</c:forEach>
					<hr />
					<label>居住區域：</label>
					<form:select path="locationInfo" id="locationCode" onblur="checkLocation_code()" >
						<form:option value="" label="請選擇目前您居住/生活的區域" />
						<form:options items="${cityInfoList}" itemValue="cityCode" itemLabel="cityName" />
					</form:select>
					<span id="locationCodeSpan"></span>
					<hr />
				    <label>生活地點一：</label>
				    <form:input path="addr0" id="addr0" size="65" maxlength="65" onblur="checkAddr0()" 
						placeholder="此項為必填，請輸入完整地址方面後續服務之利用" required="required" />
					<br />
					<span id="addr0Span"></span>
					<hr />
				    <label>生活地點二：</label>
				    <form:input path="addr1" id="addr1" size="65" maxlength="65" onblur="checkAddr1()" 
						placeholder="此項為選填" />
					<br />
					<span id="addr1Span"></span>
					<hr />		
					<label>生活地點三：</label>
				    <form:input path="addr2" id="addr2" size="65" maxlength="65" onblur="checkAddr2()" 
						placeholder="此項為選填" />
					<br />
					<span id="addr2Span"></span>
					<hr />
					<div align="center">
						<input type="submit" id="submit" name="register" value="送出">
						<input type="reset" name="reset" value="重設" onclick="clearMessage()">
					</div>
					<hr />		
				</form:form>
				<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
				<script src="${pageContext.request.contextPath}/js/webUser/WebUserRegisterForm.js"></script>
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
				            url:"/WebProject/webUser/WebUserServlet",
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
				            		accountStr = "可建立此帳號！";
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
				            		document.getElementById("sendCheckCode").style = "display:none";
				            	} else {
				            		accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + accountStr;
				            		accountSpan.style.color = "black";
				            		accountSpan.style.fontStyle = "normal";
				            		document.getElementById("sendCheckCode").style = "display:inline";
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
					
					$("#checkRegisterNickname").click(function () {
				        checkSameNickname();
				    });
					function checkSameNickname(){
						let nickname = document.getElementById("nickname").value.trim();
						let nicknameSpan = document.getElementById("nicknameSpan");
						let nicknameStr;
						let nicknameIsOk = true;
						
						$.ajax({
							type:"POST",
				            url:"/WebProject/webUser/WebUserServlet",
				            data:{
				            	'register':'檢查稱呼',
				            	'inputNickname':nickname
				            },
				            success:function(result) {
				            	let resultSpace = result.split(",");
				            	if(resultSpace[0] == '1') {
				            		nicknameStr = "此稱呼已有人使用！";
				            		nicknameIsOk = false;
				            	} else if(resultSpace[0] == '0') {
				            		nicknameStr = "可使用此稱呼！";
				            		nicknameIsOk = true;
				            	} else if(resultSpace[0] == '-1') {
				            		nicknameStr = "檢查途中遭遇錯誤！";
				            		nicknameIsOk = false;
				            		/* 顯示彈窗異常訊息 */
				            		alert(resultSpace[1]);
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
				            },
				            error:function(err) {
				            	nicknameStr = "發生錯誤，無法執行檢查";
				            	nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + nicknameStr;
				            	nicknameSpan.style.color = "red";
				            	nicknameSpan.style.fontStyle = "italic";
				            }
						});
					}
					
					$("#checkEmailUsed").click(function () {
				        checkSameEmail();
				    });
					function checkSameEmail(){
						let email = document.getElementById("email").value.trim();
						let emailSpan = document.getElementById("emailSpan");
						let emailStr;
						let emailIsOk = true;
						
						$.ajax({
							type:"POST",
				            url:"/WebProject/webUser/WebUserServlet",
				            data:{
				            	'register':'檢查信箱',
				            	'inputEmail':email
				            },
				            success:function(result) {
				            	let resultSpace = result.split(",");
				            	if(resultSpace[0] == '1') {
				            		emailStr = "此電子信箱已有人使用！";
				            		emailIsOk = false;
				            	} else if(resultSpace[0] == '0') {
				            		emailStr = "可使用此電子信箱！";
				            		emailIsOk = true;
				            	} else if(resultSpace[0] == '-1') {
				            		emailStr = "檢查途中遭遇錯誤！";
				            		emailIsOk = false;
				            		/* 顯示彈窗異常訊息 */
				            		alert(resultSpace[1]);
				            	}
				            	if (!emailIsOk) {
				            		emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailStr;
				            		emailSpan.style.color = "red";
				            		emailSpan.style.fontStyle = "italic";
				            	} else {
				            		emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailStr;
				            		emailSpan.style.color = "black";
				            		emailSpan.style.fontStyle = "normal";
				            	}
				            },
				            error:function(err) {
				            	emailStr = "發生錯誤，無法執行檢查";
				            	emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailStr;
				            	emailSpan.style.color = "red";
				            	emailSpan.style.fontStyle = "italic";
				            }
						});
					}
					
					$("#sendCheckCode").click(function (){
						sendEmailCheckCode();
					});
					function sendEmailCheckCode() {
						let email = document.getElementById("email").value.trim();
						let account = document.getElementById("account").value.trim();
						let checkCode = document.getElementById("checkCode");
						let emailCheckCodeSpan = document.getElementById("emailCheckCodeSpan");
						let checkCodeStr;
						let emailCheckCodeStr;
						let emailCheckCodeIsOk = true;
						
						$.ajax({
							type:"POST",
				            url:"/WebProject/webUser/WebUserServlet",
				            data:{
				            	'register':'信箱驗證',
				            	'inputEmail':email,
				            	'inputAccount':account
				            },
				            success:function(result) {
				            	let resultSpace = result.split(",");
				            	if(resultSpace[0] == 'true') {
				            		emailCheckCodeStr = "驗證碼已成功寄出！";
				            		emailCheckCodeIsOk = true;
				            		checkCodeStr = resultSpace[2];
				            		/* 顯示彈窗異常訊息 */
				            		alert(resultSpace[1]);
				            	} else if(resultSpace[0] == 'false') {
				            		emailCheckCodeStr = "遭遇錯誤！請稍後再試！";
				            		emailCheckCodeIsOk = false;
				            		/* 顯示彈窗異常訊息 */
				            		alert(resultSpace[1]);
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
				            		console.log(checkCodeStr);
				            	}
				            },
				            error:function(err) {
				            	emailCheckCodeStr = "發生錯誤，無法送出驗證碼";
				            	emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailCheckCodeStr;
				            	emailCheckCodeSpan.style.color = "red";
				            	emailCheckCodeSpan.style.fontStyle = "italic";
				            }
						});
					}
					
					$("#checkRegisterPhone").click(function () {
				        checkSamePhone();
				    });
					function checkSamePhone(){
						let phone = document.getElementById("phone").value.trim();
						let phoneSpan = document.getElementById("phoneSpan");
						let phoneStr;
						let phoneIsOk = true;
						
						$.ajax({
							type:"POST",
				            url:"/WebProject/webUser/WebUserServlet",
				            data:{
				            	'register':'檢查電話',
				            	'inputPhone':phone
				            },
				            success:function(result) {
				            	let resultSpace = result.split(",");
				            	if(resultSpace[0] == '1') {
				            		phoneStr = "此聯絡電話已有人使用！";
				            		phoneIsOk = false;
				            	} else if(resultSpace[0] == '0') {
				            		phoneStr = "可使用此聯絡電話！";
				            		phoneIsOk = true;
				            	} else if(resultSpace[0] == '-1') {
				            		phoneStr = "檢查途中遭遇錯誤！";
				            		phoneIsOk = false;
				            		/* 顯示彈窗異常訊息 */
				            		alert(resultSpace[1]);
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
				            },
				            error:function(err) {
				            	phoneStr = "發生錯誤，無法執行檢查";
				            	phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + phoneStr;
				            	phoneSpan.style.color = "red";
				            	phoneSpan.style.fontStyle = "italic";
				            }
						});
					}
				</script> 
            </div>
<!-- -------------------------------------------------------------------- -->
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:20px">
            <%@include file = "../Footer-Include-prototype.jsp" %>
</body>
</html>