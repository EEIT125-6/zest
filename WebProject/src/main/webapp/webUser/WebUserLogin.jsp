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
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+TC:wght@300&display=swap" rel="stylesheet">
<link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Nerko+One&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" data-integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" data-crossorigin="anonymous"/>
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        
    <title>進行登入</title>
    <style>
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
            <div class="container-fluid  header" >
              <div class="container" >
              <a href="../Index1.jsp"><img src="../Images/LOGO1-removebg-preview.png" style="float: left; height: 70px;"></a>
              <p style="text-align: right;font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;">
              <br>登入 |<a href="WebUserRegisterForm.jsp">註冊</a>  |
              <a href="../product/index.jsp"><img src="../Images/PLZPLZ-removebg-preview.png" class="shopcar"></a>
            </p>
              </div>
            </div>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
            	<c:if test="${userFullData.password != null}">
					<c:redirect url="WebUserMain.jsp" />
				</c:if>
                <form method="post">
                	<fieldset>
                		<legend>登入相關資料</legend>
                		<hr />
                		<label>帳號名稱：</label>
                		<input type="text" name="account" id="account" size="40" maxlength="20" onblur="checkAccountName()"
							placeholder="請輸入帳號，6~20個字" required="required" />
						<span id="accountSpan"></span>
						<hr />
						<label>帳號密碼：</label> 
						<input type="password" name="password" id="password" size="40" maxlength="20" onblur="checkAccountPassword()"
							placeholder="請輸入密碼，6~20個字" required="required" />
						<input type="button" name="visibility_switch" id="visibility_switch" value="顯示密碼" onclick="changeVisibility()">
						<span id="passwordSpan"></span>
						<br />
						<span id="loginSpan">
							<c:if test="${loginMessage != null}">
								<i class='material-icons' style='font-size:18px;color:red'>cancel</i>
								<c:out value="${loginMessage}" />
							</c:if>
						</span>
                	</fieldset>
                	<div align="center">
						<input type="submit" id="submit" name="login" value="登入">
						<input type="reset" name="reset" value="重設" onclick="clearMessage()">
					</div>
                </form>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="scripts/WebUserLogin.js"></script>
                <script>
	                $("#submit").click(function () {
	                	inputCheck();
				    });
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
						let loginStr;
						let loginIsOk = true;
						
	                	$.ajax({
							type:"POST",
				            url:"/WebProject/webUser/WebUserServlet",
				            async  : false,
				            data:{
				            	'login':'登入',
				            	'account':account,
				            	'password':password
				            },
				            success:function(result) {
				            	let resultSpace = result.split(",");
				            	if(resultSpace[0] == '1') {
				            		loginStr = "登入成功！";
				            		loginIsOk = true;
				            		/* 顯示彈窗訊息 */
				            		alert(loginStr);
				            	} else if(resultSpace[0] == '0') {
				            		loginStr = "密碼錯誤！";
				            		loginIsOk = false;
				            		/* 顯示彈窗訊息 */
				            		alert(loginStr);
				            	} else if(resultSpace[0] == '-1') {
				            		loginStr = "該帳號已棄用！請重新註冊或聯絡網站管理員";
				            		loginIsOk = false;
				            		/* 顯示彈窗訊息 */
				            		alert(loginStr);
				            	} else if(resultSpace[0] == '-2') {
				            		loginStr = "帳號錯誤！";
				            		loginIsOk = false;
				            		/* 顯示彈窗訊息 */
				            		alert(loginStr);
				            	} else if(resultSpace[0] == '-3') {
				            		loginStr = "檢查途中遭遇錯誤！";
				            		loginIsOk = false;
				            		/* 顯示彈窗訊息 */
				            		alert(resultSpace[1]);
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
				            },
				            error:function(err) {
				            	loginStr = "發生錯誤，無法執行檢查";
				            	loginSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + loginStr;
				            	loginSpan.style.color = "red";
			            		loginSpan.style.fontStyle = "italic";
			            		/* 顯示彈窗訊息 */
			            		alert(loginStr);
				            }
						});
	                }
                </script>
            </div>
            
<!-- -------------------------------------------------------------------- -->
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white">
                <!-- Footer -->
                <footer class="page-footer font-small mdb-color lighten-3 pt-4">
                
                  <!-- Footer Links -->
                  <div class="container text-center text-md-left">
                
                    <!-- Grid row -->
                    <div class="row">
                
                      <!-- Grid column -->
                      <div class="col-md-4 col-lg-3 mr-auto my-md-4 my-0 mt-4 mb-1">
                
                        <!-- Content -->
                        <h5 class="font-weight-bold text-uppercase mb-4">More Content</h5>
                        <p>商務合作</p>
                        <p>	餐飲代理商招募<br>
                        	商業企劃<br>
                        	申請掃碼點餐<br>
                        	美國收單代理商招募<br>
                        	美國收銀代理商招募<br>
                        	免費使用美國排隊<br></p>
                
                      </div>
                      <!-- Grid column -->
                
                      <hr class="clearfix w-100 d-md-none">
                
                      <!-- Grid column -->
                      <div class="col-md-2 col-lg-2 mx-auto my-md-4 my-0 mt-4 mb-1">
                
                        <!-- Links -->
                        <h5 class="font-weight-bold text-uppercase mb-4">ABOUT</h5>
                
                        <ul class="list-unstyled">
                          <li>
                            <p>
                              <a href="#!">計畫</a>
                            </p>
                          </li>
                          <li>
                            <p>
                              <a href="#!">關於我們</a>
                            </p>
                          </li>
                          <li>
                            <p>
                              <a href="#!">Facebook</a>
                            </p>
                          </li>
                          <li>
                            <p>
                              <a href="#!">AWARDS</a>
                            </p>
                          </li>
                        </ul>
                
                      </div>
                      <!-- Grid column -->
                
                      <hr class="clearfix w-100 d-md-none">
                
                      <!-- Grid column -->
                      <div class="col-md-4 col-lg-3 mx-auto my-md-4 my-0 mt-4 mb-1">
                
                        <!-- Contact details -->
                        <h5 class="font-weight-bold text-uppercase mb-4">Address</h5>
                
                        <ul class="list-unstyled">
                          <li>
                            <p>
                              <i class="fas fa-home mr-3"></i> 四川 中壢 </p>
                          </li>
                          <li>
                            <p>
                              <i class="fas fa-envelope mr-3"></i> zestinfo@google.com</p>
                          </li>
                          <li>
                            <p>
                              <i class="fas fa-phone mr-3"></i> + 02 453 245 88</p>
                          </li>
                          <li>
                            <p>
                              <i class="fas fa-print mr-3"></i> + 02 453 249 89</p>
                          </li>
                        </ul>
                
                      </div>
                      <!-- Grid column -->
                      <hr class="clearfix w-100 d-md-none">
                      <!-- Grid column -->
                
                    </div>
                    <!-- Grid row -->
                
                  </div>
                  <!-- Footer Links -->
                
                  <!-- Copyright -->
                  <div class="footer-copyright text-center py-3">© 2020 Copyright:
                    <a > 橙皮美食平台</a>
                  </div>
                  <!-- Copyright -->
                
                </footer>
                <!-- Footer -->
                    </div>
        
</body>
</html>