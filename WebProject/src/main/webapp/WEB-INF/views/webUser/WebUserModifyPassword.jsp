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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoadingScreen.css"> 
   	<link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/webUser/WebUserRegisterForm.css">
    <title>修改個人密碼</title>
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
            <%@include file = "../LoadingScreen.jsp" %>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
                <form method="post">
                	<fieldset>
                		<legend>密碼相關資料</legend>
                		<hr />
						<label>帳號原密碼：</label>
						<c:if test="${userFullData.password.length() > 0}">
							<c:forEach var="passwordChar" begin="0" end="${userFullData.password.length()-1}">
								<c:out value = "*" />
							</c:forEach>
						</c:if>
						<button type="button" style="font-size:18px" id="showPassword" onclick="showOldPassword()">顯示密碼 <i class="material-icons" style="font-size:18px;color:red">visibility</i></button>
						<input type="hidden" name="originalPassword" id="originalPassword" value="${userFullData.password}" readonly>
                		<hr />
						<label>帳號新密碼：</label> 
						<input type="password" name="password" id="password" size="40" maxlength="20" onblur="checkAccountPassword()"
							placeholder="請輸入密碼，8~20個字" required="required" />
						<button type="button" style="font-size:18px" id="visibility_switch" onclick="changeVisibility()">顯示密碼 <i class="material-icons" style="font-size:18px;color:red">visibility</i></button>
						<span id="passwordSpan"></span>
						<hr />
						<label>確認新密碼：</label> 
						<input type="password" name="confirmPassword" id="confirmPassword" size="40" maxlength="20" onblur="checkConfirmPassword()"
							placeholder="請輸入密碼，8~20個字" required="required" />
						<button type="button" style="font-size:18px" id="visibility_switch_confirm" onclick="changeConfirmVisibility()">顯示密碼 <i class="material-icons" style="font-size:18px;color:red">visibility</i></button>
						<span id="confirmPasswordSpan"></span>
                	</fieldset>
                	<div align="center">
                		<a href="WebUserMain"><button type="button" name="update" style="font-size:18px" >取消 <i class="material-icons" style="font-size:18px;color:green">undo</i></button></a>
						<button type="button" name="update" id="updateBtn" style="font-size:18px" >密碼修改完畢 <i class="material-icons" style="font-size:18px;color:blue">check</i></button>
						<button type="reset" name="reset" style="font-size:18px" onclick="clearMessage()" >重設 <i class="material-icons" style="font-size:18px;color:blue">refresh</i></button>
					</div>
                </form>
                <script src="${pageContext.request.contextPath}/js/webUser/WebUserModifyPassword.js"></script>
                <script>
                	window.onload = function() {
                		let updateBtn = document.getElementById("updateBtn");
                		let confirmPasswordSpan = document.getElementById("confirmPasswordSpan");
                		
                		updateBtn.onclick = function() {
                			if (checkForm()) {
                				let password = document.getElementById("password").value.trim();
                				let confirmPassword = document.getElementById("confirmPassword").value.trim();
                				
                				let confirmPasswordSpan = document.getElementById("confirmPasswordSpan");
    							let confirmPasswordStr = "...處理中，請稍後";
    							let confirmPasswordIsOk = true;
    							
    							confirmPasswordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + confirmPasswordStr;
    							confirmPasswordSpan.style.color = "black";
    							confirmPasswordSpan.style.fontStyle = "normal";
    							
    							let xhrObject = new XMLHttpRequest();
    							if (xhrObject != null) {
    								xhrObject.open("POST", "<c:url value='/webUser/controller/WebUserModifyPassword' />", true);
    								xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    								xhrObject.send("&password=" + password + "&confirmPassword=" + confirmPassword);
    								
    								xhrObject.onreadystatechange = function() {
    									if (xhrObject.readyState === 4 && xhrObject.status === 200) {
    										let typeObject = xhrObject.getResponseHeader("Content-Type");
    										if (typeObject.indexOf("application/json") === 0) {
    											if (resultObj.resultCode == 1) {
    												confirmPasswordStr = resultObj.resultMessage;
    												confirmPasswordIsOk = true;
    												/* 顯示彈窗訊息 */
    							            		alert(confirmPasswordStr);
    											} else if (resultObj.resultCode != 1) {
    												confirmPasswordStr = resultObj.resultMessage;
    												confirmPasswordIsOk = false;
    												/* 顯示彈窗訊息 */
    							            		alert(confirmPasswordStr);
    											} 
    											if (!confirmIsOk) {
    												confirmPasswordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + confirmPasswordStr;
    												confirmPasswordSpan.style.color = "red";
    												confirmPasswordSpan.style.fontStyle = "italic";
    							            	} else {
    							            		confirmPasswordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + confirmPasswordStr;
    							            		confirmPasswordSpan.style.color = "black";
    							            		confirmPasswordSpan.style.fontStyle = "normal";
    							            		/* 跳轉 */
    							            		window.location.href = resultObj.nextPath;
    							            	}
    										} else {
    											confirmPasswordStr = "發生錯誤，無法執行檢查";
    											confirmPasswordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + confirmPasswordStr;
    											confirmPasswordSpan.style.color = "red";
    											confirmPasswordSpan.style.fontStyle = "italic";
    						            		/* 顯示彈窗訊息 */
    						            		alert(confirmPasswordStr);
    										}
    									}
    								};
    							} else {
    								alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
    							}
                			}
                		};
                	};
                </script>
            </div>
<!-- -------------------------------------------------------------------- -->
           <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:200px">
               <%@include file = "../Footer-Include-prototype.jsp" %>
           </div>
</body>
</html>