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
    <title>已登入</title>
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
				<c:if test="${userFullData.password == null}">
					<c:redirect url="/webUser/WebUserLogin" />
				</c:if>
                <form action="<c:url value='/webUser/controller/WebUserMain/Modify' />" method="post" onSubmit="return checkForm();">
                	<fieldset>
                		<legend>${userFullData.account}</legend>
                		<div align="center">
                			<hr />
                			<input type="submit" id="select" name="select" value="檢視/修改個人資料">
							<hr />
						</div>
                	</fieldset>
                </form>
                <form action="<c:url value='/webUser/controller/WebUserMain/Search' />" method="post" onSubmit="return checkForm();">
                	<fieldset>
                		<div align="center">
                			<input type="submit" id="select" name="select" value="進行搜索">
							<hr />
						</div>
                	</fieldset>
                </form>
                <form action="<c:url value='/webUser/controller/WebUserMain/Quit' />" method="post" onSubmit="return checkForm();">
                	<fieldset>
                		<div align="center">
                			<input type="submit" id="quit" name="update" value="放棄使用帳戶">
                			<hr />
						</div>
                	</fieldset>
                </form>
                <form action="<c:url value='/webUser/controller/WebUserMain/Logout' />" method="get" onSubmit="return checkForm();">
                	<fieldset>
                		<div align="center">
							<input type="submit" id="logout" name="login" value="登出帳戶">
							<hr />
						</div>
                	</fieldset>
                </form>
                <script src="${pageContext.request.contextPath}/js/webUser/WebUserMain.js"></script>
                <script>
                	window.onload = function () {
                		let quitBtn = document.getElementById("quit");
                		
                		quitBtn.onclick = function () {
                			quitAlert();
                		}
                	}
	                
                	function quitAlert() {
                		alert("請注意，本操作不可逆，您將無法再次以相同的帳號名稱、電子信箱來註冊本服務；如有其他疑問，請洽本站管理員");
                	}
                </script>
            </div>
<!-- -------------------------------------------------------------------- -->
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:120px">
            <%@include file = "../Footer-Include-prototype.jsp" %>
</body>
</html>