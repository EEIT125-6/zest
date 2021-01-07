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
    <title>註冊資料確認</title>
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
				<c:if test="${reg_webUser.password == null}">
					<c:redirect url="/WebUserRegisterForm" />
				</c:if>
				<form action="<c:url value='/webUser/controller/DisplayWebUserInfo/confirm' />" method="post" onSubmit="return checkForm();">
					<fieldset>
						<legend>註冊資料如下，如果無誤請按「確認」</legend>
						<hr />
						<label>帳號身分：</label>
						<c:out value="${reg_webUser.accountLv.levelName}" />
						<hr />
						<label>帳號名稱：</label>
						<c:out value="${reg_webUser.account}" />
						<hr />
						<label>帳號密碼：</label>
						<c:if test="${reg_webUser.password.length() > 0}">
							<c:forEach var="passwordChar" begin="0" end="${reg_webUser.password.length()-1}">
								<c:out value = "*" />
							</c:forEach>
						</c:if>
						<button type="button" name="showPassword" id="showPassword" style="font-size:18px" >顯示密碼 <i class="material-icons" style="font-size:18px;color:red">visibility</i></button>
						<input type="hidden" name="password" id="password" value="${reg_webUser.password}">
						<hr />
						<label>中文姓氏：</label>
						<c:out value="${reg_webUser.firstName}" />
						<hr />
						<label>中文名字：</label>
						<c:out value="${reg_webUser.lastName}" />
						<hr />
						<label>稱呼方式：</label>
						<c:out value="${reg_webUser.nickname}" />
						<hr />
						<label>生理性別：</label>
						<c:out value="${reg_webUser.gender.genderText}" />
						<hr />
						<label>西元生日：</label>
						<c:out value="${reg_webUser.birth}" />
						<hr />
						<label>偏好食物：</label>
						<c:out value="${reg_webUser.fervor}" />
						<hr />
						<label>聯絡信箱：</label>
						<c:out value="${reg_webUser.email}" />
						<hr />
						<label>聯絡電話：</label>
						<c:out value="${reg_webUser.phone}" />
						<hr />
						<label>是否願意接收促銷/優惠訊息：</label>
						<c:out value="${reg_webUser.getEmail.willingText}" />
						<hr />
						<label>居住區域：</label>
						<c:out value="${reg_webUser.locationInfo.cityName}" />
						<hr />
						<label>生活地點一：</label>
						<c:out value="${reg_webUser.addr0}" />
						<hr />
						<label>生活地點二：</label>
						<c:out value="${reg_webUser.addr1}" />
						<hr />
						<label>生活地點三：</label>
						<c:out value="${reg_webUser.addr2}" />
						<hr />
						<label>註冊狀態：</label>
						<c:if test="${reg_webUser.accountLv.lv == -1}" >
							<c:out value="本帳號尚未啟用，請等待網站管理員為您啟用帳號" />
						</c:if>
						<c:if test="${reg_webUser.accountLv.lv == 0}" >
							<c:out value="本帳號即將完成啟用" />
						</c:if>
						<c:if test="${reg_webUser.accountLv.lv == 1}" >
							<c:out value="本帳號尚未啟用，請等待網站管理員為您啟用帳號" />
						</c:if>
						<hr />
					</fieldset>
					<div align="center">
						<button type="submit" name="register" id="registerConfirm" style="font-size:18px" >確認 <i class="material-icons" style="font-size:18px;color:blue">check</i></button>
						<a href="<c:url value='/webUser/controller/DisplayWebUserInfo/undo' />"><button type="button" name="register" id="registerCancel" style="font-size:18px" >取消 <i class="material-icons" style="font-size:18px;color:green">undo</i></button></a>
					</div>
					<hr />
				</form>
				<script src="<c:url value='/js/webUser/DisplayWebUserInfo.js' />"></script>
				<script>
					window.onload = function() {
						let showPasswordBtn = document.getElementById("showPassword");
						
						showPasswordBtn.onclick = function() {
							document.getElementById("password").type = (document.getElementById("password").type == "hidden") ? "text" : "hidden";
					    	document.getElementById("showPassword").innerHTML 
							= (document.getElementById("showPassword").textContent == "顯示密碼 visibility") 
							? "隱藏密碼 "+"<i class='material-icons' style='font-size:18px;color:green'>visibility_off</i>" 
							: "顯示密碼 "+"<i class='material-icons' style='font-size:18px;color:red'>visibility</i>"; 
						}
					}
				</script>
            </div>         
<!-- -------------------------------------------------------------------- -->
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:20px">
            <%@include file = "../Footer-Include-prototype.jsp" %>    
</body>
</html>