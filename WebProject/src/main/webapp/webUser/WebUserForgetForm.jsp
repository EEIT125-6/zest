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
            <%@include file = "../Header-Include-prototype.jsp" %>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
            	<c:if test="${userFullData.password != null}">
					<c:redirect url="WebUserMain.jsp" />
				</c:if>
                <form action="/WebProject/webUser/WebUserServlet" method="post" onSubmit="return checkForm();" >
                	<fieldset>
                		<legend>請進行填寫下列資料以便重新取回您的帳號</legend>
                		<hr />
                		<label>帳號名稱：</label>
                		<input type="text" name="account" id="account" size="40" maxlength="20" onblur="checkAccountName()"
							placeholder="請輸入帳號，8~20個字(選填)" />
						<span id="accountSpan"></span>
						<hr />
                		<label>帳號密碼：</label>
                		<input type="password" name="password" id="password" size="40" maxlength="20" onblur="checkAccountPassword()"
							placeholder="請輸入密碼，8~20個字(選填)" />
						<input type="button" name="visibility_switch" id="visibility_switch" value="顯示密碼" onclick="changeVisibility()">
						<span id="passwordSpan"></span>
						<hr />
                		<label>聯絡信箱：</label>
                		<input type="email" name="email" id="email" size="40" maxlength="30" onblur="checkEmail()"
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
                	</fieldset>
                	<div align="center">
						<input type="submit" id="recovery" name="recovery" value="送出請求">
						<input type="reset" name="reset" value="重設" onclick="">
					</div>
                </form>
                <script src="scripts/WebUserForgetForm.js"></script>
            </div>     
<!-- -------------------------------------------------------------------- -->
            <%@include file = "../Footer-Include.jsp" %>
</body>
</html>