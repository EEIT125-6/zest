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
   	<%@include file = "../Link_Meta-Include.jsp" %>
   	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
   	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/webUser/WebUserRegisterForm.css"> 
    <title>進行登入</title>
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
					<c:redirect url="/webUser/WebUserMain" />
				</c:if>
                <form method="post">
                	<fieldset>
                		<legend>登入相關資料</legend>
                		<hr />
                		<label>帳號名稱：</label>
                		<input type="text" name="account" id="account" size="40" maxlength="20" onblur="checkAccountName()"
							placeholder="請輸入帳號，8~20個字" required="required" />
						<span id="accountSpan"></span>
						<hr />
						<label>帳號密碼：</label> 
						<input type="password" name="password" id="password" size="40" maxlength="20" onblur="checkAccountPassword()"
							placeholder="請輸入密碼，8~20個字" required="required" />
						<button type="button" style="font-size:18px" id="visibility_switch" onclick="changeVisibility()">顯示密碼 <i class="material-icons" style="font-size:18px;color:red">visibility</i></button>
						<span id="passwordSpan"></span>
						<hr />
						<span id="loginSpan">
							<c:if test="${loginMessage.substring(0,2) == '歡迎'}">
								<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>
								<c:out value="${loginMessage}" />
								<hr />
							</c:if>
							<c:if test="${loginMessage.substring(0,2) != '歡迎' && loginMessage != null}">
								<i class='material-icons' style='font-size:18px;color:red'>cancel</i>
								<c:out value="${loginMessage}" />
								<hr />
							</c:if>
						</span>
                	</fieldset>
                	<div align="center">
                		<a href="<c:url value='/webUser/WebUserForgetForm' /> "><button type="button" style="font-size:18px" id="forget" name="forget" >忘記帳號或密碼 <i class="material-icons" style="font-size:18px;color:red">error</i></button></a>
						<button type="button" style="font-size:18px" id="submit" name="login" >登入 <i class="material-icons" style="font-size:18px;color:blue">check</i></button>
						<button type="button" style="font-size:18px" id="register" name="register" >前往註冊 <i class="material-icons" style="font-size:18px;color:green">undo</i></button>
						<button type="reset" style="font-size:18px" name="reset" onclick="clearMessage()">重設 <i class="material-icons" style="font-size:18px;color:blue">refresh</i></button>
					</div>
					<hr />
                </form>
                <script src="<c:url value='/js/webUser//WebUserLogin.js' />"></script>
                <script>
                	window.onload = function() {
                		let submitBtn = document.getElementById("submit");
                		
                		submitBtn.onclick = function() {
                			inputCheck();
                		};
                	};
                	
                	function inputCheck() {
	                	if(!checkForm()) {
	                		alert("帳號或密碼不符規範，請再檢查一次！");
	                	} else {
	                		loginCheck();	
	                	}
	                }
                	
                	function loginCheck() {
                		let account = document.getElementById("account").value.trim();
	                	let password = document.getElementById("password").value.trim();
	                	let loginSpan = document.getElementById("loginSpan");
						let loginStr = "...處理中，請稍後";
						let loginIsOk = true;
						
						loginSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + loginStr;
	            		loginSpan.style.color = "black";
	            		loginSpan.style.fontStyle = "normal";
	            		
	            		let xhrObject = new XMLHttpRequest();
	            		if (xhrObject != null) {
	            			xhrObject.open("POST", "<c:url value='/webUser/controller/WebUserLogin' />", true);
							xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
							xhrObject.send("account=" + account + "&password=" + password);
							
							xhrObject.onreadystatechange = function() {
								if (xhrObject.readyState === 4 && xhrObject.status === 200) {
									let typeObject = xhrObject.getResponseHeader("Content-Type");
									if (typeObject.indexOf("application/json") === 0) {
										let resultObj = JSON.parse(xhrObject.responseText);
										if (resultObj.resultCode == 1) {
											loginStr = "登入成功！";
						            		loginIsOk = true;
						            		/* 顯示彈窗訊息 */
						            		alert(resultObj.resultMessage);
										} else if (resultObj.resultCode == 0) {
											loginStr = "帳號或密碼錯誤！";
						            		loginIsOk = false;
						            		/* 顯示彈窗訊息 */
						            		alert(loginStr);
										} else if(resultObj.resultCode == -1) {
						            		loginStr = "該帳號已停用！請重新註冊或聯絡網站管理員";
						            		loginIsOk = false;
						            		/* 顯示彈窗訊息 */
						            		alert(loginStr);
						            	} else if(resultObj.resultCode == -2) {
						            		loginStr = "帳號錯誤！";
						            		loginIsOk = false;
						            		/* 顯示彈窗訊息 */
						            		alert(loginStr);
						            	} else if(resultObj.resultCode == -3) {
						            		loginStr = "檢查途中遭遇錯誤！";
						            		loginIsOk = false;
						            		/* 顯示彈窗訊息 */
						            		alert(resultObj.resultMessage);
						            	}
										if (!loginIsOk) {
						            		loginSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + loginStr;
						            		loginSpan.style.color = "red";
						            		loginSpan.style.fontStyle = "italic";
						            	} else {
						            		loginSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + loginStr;
						            		loginSpan.style.color = "black";
						            		loginSpan.style.fontStyle = "normal";
						            		/* 刷新 */
						            		location.reload(true);
						            	}
									} else {
										loginStr = "發生錯誤，無法執行檢查";
						            	loginSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + loginStr;
						            	loginSpan.style.color = "red";
					            		loginSpan.style.fontStyle = "italic";
					            		/* 顯示彈窗訊息 */
					            		alert(loginStr);
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
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:200px">
            <%@include file = "../Footer-Include-prototype.jsp" %>
</body>
</html>