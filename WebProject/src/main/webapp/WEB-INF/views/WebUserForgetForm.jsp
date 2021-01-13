<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", -1); // 防止proxy server進行快取
%>
<!-- taglib宣告 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- taglib宣告 -->
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file = "Link_Meta-Include.jsp" %> 
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoadingScreen.css"> 
   	<link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/webUser/WebUserForgetForm.css">       
    <title>忘記帳號或密碼</title>
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
				<c:if test="${timeOut != null}">
					<i class='material-icons' style='font-size:18px;color:red'>cancel</i>
					<c:out value="${timeOut}" />
				</c:if>
                <form method="post" >
                	<fieldset>
                		<legend>請進行填寫下列資料以便重新取回您的帳號，如不確定可保持空白</legend>
                		<hr />
                		<div align="center">
	                		<button type="button" id="userInput">一鍵輸入</button>
                		</div>
                		<hr />
                		<label>帳號名稱：</label>
                		<input type="text" name="account" id="account" size="30" maxlength="30" onblur="checkAccountName()"
							placeholder="請輸入帳號，6~30個字(選填)" />
						<span id="accountSpan"></span>
						<hr />
                		<label>帳號密碼：</label>
                		<input type="password" name="password" id="password" size="30" maxlength="30" onblur="checkAccountPassword()"
							placeholder="請輸入密碼，6~30個字(選填)" />
						<button type="button" style="font-size:18px" id="visibility_switch" onclick="changeVisibility()">顯示密碼 <i class="material-icons" style="font-size:18px;color:red">visibility</i></button>
						<span id="passwordSpan"></span>
						<hr />
                		<label>聯絡信箱：</label>
                		<input type="email" name="email" id="email" size="30" maxlength="30" onblur="checkEmail()"
							placeholder="請輸入帳號所使用的聯絡信箱(必填)" required="required" />
						<span id="emailSpan"></span>
						<hr />
						<label>聯絡電話：</label>
                		<input type="tel" name="phone" id="phone" size="40" maxlength="11" onblur="checkPhone()"
							placeholder="請輸入帳號所使用的聯絡電話(必填)" required="required" />
						<span id="phoneSpan"></span>
						<hr />
						<label>西元生日：</label>
                		<input type="date" name="birth" id="birth"  onblur="checkBirthday()"
							placeholder="請輸入您的西元出生日期(必填)" required="required" />
						<span id="birthSpan"></span>
						<hr />
						<span id="requestSpan"></span>
                	</fieldset>
                	<div align="center">
                		<a href="<c:url value='/WebUserLogin' /> "><button type="button" style="font-size:18px" id="login" name="login" >返回登入 <i class="material-icons" style="font-size:18px;color:green">undo</i></button></a>  		
						<button type="button" style="font-size:18px" id="recovery" name="recovery" >送出請求 <i class="material-icons" style="font-size:18px;color:blue">check</i></button>
						<button type="reset" style="font-size:18px" name="reset" onclick="clearMessage()">重設 <i class="material-icons" style="font-size:18px;color:blue">refresh</i></button>
					</div>
					<hr />
                </form>
                <script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
                <script src="${pageContext.request.contextPath}/js/webUser/WebUserForgetForm.js"></script>
                <script>
	                $("#userInput").click(function () {
	                	document.getElementById("email").value = "georgecycuphy@cycu.org.tw";
	                	document.getElementById("phone").value = "0911954504";
	                	document.getElementById("birth").value = "1992-01-16";
				    });
	                $("#recovery").click(function () {
	                	checkRecoveryRequest();
				    });
	                function checkRecoveryRequest() {
	                	if (!checkForm()) {
	                		alert("輸入的欄位有錯誤，請修正後再次執行!");
	                	} else {
	                		let sendRecoveryUrlBtn = document.getElementById("recovery");
	                		let email = document.getElementById("email").value.trim();
							let choice=confirm("是否要寄往 " + email + " ?");
							if (choice) {
								sendRecoveryUrlBtn.disabled = true;
								setTimeout(enableBtn, 45000);
		                		sendRecoveryRequest();
								function enableBtn() {
									sendRecoveryUrlBtn.disabled = false;
								}
							}
	                	}
	                }
	                function sendRecoveryRequest() {
	                	let account = document.getElementById("account").value.trim();
	                	let password = document.getElementById("password").value.trim();
	                	let email = document.getElementById("email").value.replace('<', ' ').replace('>', ' ').trim();
	                	let phone = document.getElementById("phone").value.trim();
	                	let birth = document.getElementById("birth").value.trim();
	                	
	                	let requestSpan = document.getElementById("requestSpan");
	                	let requestStr = "...處理中，請稍後";
	                	let requestIsOk = false;
	                	let mode = "recovery";
	                	
	                	requestSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>"
	    					+ requestStr;
	                	requestSpan.style.color = "black";
	                	requestSpan.style.fontStyle = "normal";
	                	
	                	$.ajax({
							type:"POST",
				            url:"<c:url value='/webUser/controller/UserInfoController' />",
				            data:{
				            	'register':mode,
				            	'inputAccount':account,
				            	'inputPassword':password,
				            	'inputEmail':email,
				            	'inputPhone':phone,
				            	'inputBirth':birth
				            },
				            success:function(resultObj) {
				            	if(resultObj.resultCode == 'true') {
				            		requestStr = "信件已成功寄出！";
				            		requestIsOk = true;
				            		/* 顯示彈窗異常訊息 */
				            		alert(resultObj.resultMessage);
				            	} else if(resultObj.resultCode == 'false') {
				            		requestStr = "信件未能寄出！";
				            		requestIsOk = false;
				            		/* 顯示彈窗異常訊息 */
				            		alert(resultObj.resultMessage);
				            	}
				            	if (!requestIsOk) {
				            		requestSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + requestStr;
				            		requestSpan.style.color = "red";
				            		requestSpan.style.fontStyle = "italic";
				            	} else {
				            		requestSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + requestStr;
				            		requestSpan.style.color = "black";
				            		requestSpan.style.fontStyle = "normal";
				            	}
				            },
				            error:function(err) {
				            	requestStr = "發生錯誤，無法執行";
				            	requestSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + requestStr;
				            	requestSpan.style.color = "red";
				            	requestSpan.style.fontStyle = "italic";
				            }
						});
	                }
                </script>
            </div>     
<!-- -------------------------------------------------------------------- -->
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:20px">
            	<%@include file = "Footer-Include-prototype.jsp" %>
            </div>
</body>
</html>