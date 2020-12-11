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
    <link rel="stylesheet" href="styles/WebUserRegisterForm.css">
    <title>重設個人密碼</title>
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
                <jsp:useBean id="userFullData" class="webUser.model.WebUserData"
					scope="session" />
				<c:if test="${userId == null}">
					<c:redirect url="WebUserLogin.jsp" />
				</c:if>
                <form action="/WebProject/webUser/WebUserServlet" method="post">
                	<fieldset>
                		<legend>重設密碼相關資料</legend>
                		<input type="hidden" name="userId" id="userId" value="${userId}">
                		<hr />
						<label>帳號新密碼：</label> 
						<input type="password" name="password" id="password" size="40" maxlength="20" onblur="checkAccountPassword()"
							placeholder="請輸入密碼，8~20個字" required="required" />
						<input type="button" name="visibility_switch" id="visibility_switch" value="顯示密碼" onclick="changeVisibility()">
						<span id="passwordSpan"></span>
						<hr />
						<label>確認新密碼：</label> 
						<input type="password" name="confirmPassword" id="confirmPassword" size="40" maxlength="20" onblur="checkConfirmPassword()"
							placeholder="請輸入密碼，8~20個字" required="required" />
						<input type="button" name="visibility_switch_confirm" id="visibility_switch_confirm" value="顯示密碼" onclick="changeConfirmVisibility()">
						<span id="confirmPasswordSpan"></span>
						<hr />
						<span id="resetSpan"></span>
                	</fieldset>
                	<div align="center">
                		<a href="WebUserLogin.jsp"><input type="button" name="update" value="取消"></a>
						<input type="button" id="sendReset" name="update" value="密碼重設完畢">
						<input type="reset" name="reset" value="重設" onclick="clearMessage()">
					</div>
					<hr />
                </form>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="scripts/WebUserResetPassword.js"></script>
                <script>
	                $("#sendReset").click(function () {
	                	if (!checkForm()) {
	                		alert("操作無效或已被取消");
	                	} else {
	                		sendResetRequest();
	                	}
				    });
	                function sendResetRequest() {
	                	let userId = document.getElementById("userId").value.trim();
	                	let password = document.getElementById("password").value.trim();
	                	
	                	let resetSpan = document.getElementById("resetSpan");
						let resetStr;
						let resetIsOk = true;
	                	
	                	$.ajax({
							type:"POST",
				            url:"/WebProject/webUser/WebUserServlet",
				            data:{
				            	'recovery':'密碼重設',
				            	'userId':userId,
				            	'password':password
				            },
				            success:function(result) {
				            	let resultSpace = result.split(",");
				            	if(resultSpace[0] == '1') {
				            		resetStr = "重設成功！請重新登入您的帳號";
				            		resetIsOk = true;
				            		/* 顯示彈窗訊息 */
				            		alert(resetStr);
				            	} else if(resultSpace[0] == '0') {
				            		resetStr = "密碼未變更！";
				            		resetIsOk = false;
				            		/* 顯示彈窗訊息 */
				            		alert(resetStr);
				            	} else if(resultSpace[0] == '-1') {
				            		resetStr = "本帳號已棄用！請重新註冊或聯絡網站管理員";
				            		resetIsOk = false;
				            		/* 顯示彈窗訊息 */
				            		alert(resetStr);
				            	} else if(resultSpace[0] == '-2') {
				            		resetStr = "無效的帳號驗證資訊！無法重設密碼";
				            		resetIsOk = false;
				            		/* 顯示彈窗訊息 */
				            		alert(resetStr);
				            	} else if(resultSpace[0] == '-3') {
				            		resetStr = "重設密碼密碼失敗";
				            		resetIsOk = false;
				            		/* 顯示彈窗訊息 */
				            		alert(resultSpace[1]);
				            	} else if(resultSpace[0] == '-4') {
				            		resetStr = "檢查途中遭遇錯誤！";
				            		resetIsOk = false;
				            		/* 顯示彈窗訊息 */
				            		alert(resultSpace[1]);
				            	}
				            	if (!resetIsOk) {
				            		resetSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + resetStr;
				            		resetSpan.style.color = "red";
				            		resetSpan.style.fontStyle = "italic";
				            	} else {
				            		resetSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + resetStr;
				            		resetSpan.style.color = "black";
				            		resetSpan.style.fontStyle = "normal";
				            	}
				            },
				            error:function(err) {
				            	resetStr = "發生錯誤，無法執行檢查";
				            	resetSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + resetStr;
				            	resetSpan.style.color = "red";
				            	resetSpan.style.fontStyle = "italic";
			            		/* 顯示彈窗訊息 */
			            		alert(resetStr);
				            }
						});
	                }
				</script>
            </div>
            
<!-- -------------------------------------------------------------------- -->
           <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:250px">
           <%@include file = "../Footer-Include-prototype.jsp" %>
</body>
</html>