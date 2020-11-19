<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
	response.setHeader("Pragma", "no-cache"); // HTTP 1.0
	response.setDateHeader("Expires", -1); // 防止proxy server進行快取
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    
    
    <title>使用者登入</title>
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
       }
       .shopcar{
            height: 40px;
            margin: 0;
            margin-left:5px ;
       }
    </style>
</head>
<body>
            <div class="container-fluid  header" >
              <div class="container" >
              <a href="http://localhost:8080/WebProject/Index.html"><img src="Images/LOGO1-removebg-preview.png" style="float: left; height: 70px;"></a>
              <p style="text-align: right;font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;"><br>登入 | 註冊  |<img src="Images/PLZPLZ-removebg-preview.png" class="shopcar">
            </p>
              </div>
            </div>
            <div class="container-fluid photo">
                <!-- <img src="images/backbar2-1.jpg"> -->
                    <form action="" method="GET" enctype="UTF-8"  >
                      <fieldset  style="padding: 8px;margin: auto;width: 600px; background-color:rgb(126, 125, 125,0.3);border-radius: 4px;">
                        <input type ="text" id="srchid" name="nsrch" size="60"  placeholder="搜尋餐廳"
                        style="height: 36px;;border-radius: 4px;line-height: 38px;border: solid 2px black;;" >
                        <button style="background-color:#fcbf49 ;border: 1px black solid;border-radius: 4px;
                        line-height: 0px;">
                          <img src="Images/searchbut.jpg" >
                        </button>
                      </fieldset>
                    </form>
            </div>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
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
						<hr />
						<span id="loginSpan"></span>
                	</fieldset>
                	<div align="center">
						<input type="submit" id="submit" name="login" value="登入">
						<input type="reset" name="reset" value="重設" onclick="clearMessage()">
					</div>
                </form>
                <script src="scripts/jquery-3.5.1.min.js"></script>
				<script src="scripts/WebUserLogin.js"></script>
				<script>
				$("#submit").click(function () {
			        loginCheck();
			    });
				function loginCheck() {
					if(!checkForm()){
						
					} else {
						
					}
				}
				
				function checkForm() {
					if (!checkAccountName()) {
						return false;
					} else if (!checkAccountPassword()) {
						return false;
					} else {
						return true;
					}
				}
				
				function checkAccountName() {
					let accountObjValue = document.getElementById("account").value.trim();
					let accountSpan = document.getElementById("accountSpan");

					let accountIsOk = true;
					let accountStr;
					let startCharReg = /[0-9]/;

					if (accountObjValue == "" || accountObjValue.length == 0) {
						accountStr = "帳號不可為空白";
						accountIsOk = false;
					} else if (accountObjValue.length < 6) {
						accountStr = "帳號長度不足";
						accountIsOk = false;
					} else if (accountObjValue.length > 20) {
						accountStr = "帳號長度過長";
						accountIsOk = false;
					} else if (accountObjValue.charAt(0).match(startCharReg)) {
						accountStr = "帳號不可以數字開頭";
						accountIsOk = false;
					} else {
						let accountReg = /[a-zA-Z]{1}[a-zA-Z0-9]{5}/;

						if (!accountObjValue.match(accountReg)) {
							accountStr = "帳號不符合格式";
							document.getElementById("checkAccount").style = "display:none";
							accountIsOk = false;
						} else {
							accountStr = "帳號格式正確";
							document.getElementById("checkAccount").style = "display:inline";
							accountIsOk = true;
						}
					}
					if (!accountIsOk) {
						accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + accountStr;
						accountSpan.style.color = "red";
						accountSpan.style.fontStyle = "italic";
						return false;
					} else {
						accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + accountStr;
						accountSpan.style.color = "black";
						accountSpan.style.fontStyle = "normal";
						return true;
					}
				}

				function checkAccountPassword() {
					let passwordObjValue = document.getElementById("password").value.trim();
					let passwordSpan = document.getElementById("passwordSpan");

					let passwordIsOk = true;
					let passwordStr;
					let startCharReg = /[0-9]/;

					if (passwordObjValue == "" || passwordObjValue.length == 0) {
						passwordStr = "密碼不可為空白";
						passwordIsOk = false;
					} else if (passwordObjValue.length < 6) {
						passwordStr = "密碼長度不足，至少需6個字元";
						passwordIsOk = false;
					} else if (passwordObjValue.length > 20) {
						passwordStr = "密碼長度過長，最多僅20個字元";
						passwordIsOk = false;
					} else if (passwordObjValue.charAt(0).match(startCharReg)) {
						passwordStr = "密碼不可以數字開頭";
						passwordIsOk = false;
					} else {
						let accountReg = /[a-zA-Z]{1}[a-zA-Z0-9]{5}/;

						if (!passwordObjValue.match(accountReg)) {
							passwordStr = "密碼不符合格式";
							passwordIsOk = false;
						} else {
							passwordStr = "密碼格式正確";
							passwordIsOk = true;
						}
					}
					if (!passwordIsOk) {
						passwordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + passwordStr;
						passwordSpan.style.color = "red";
						passwordSpan.style.fontStyle = "italic";
						return false;
					} else {
						passwordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + passwordStr;
						passwordSpan.style.color = "black";
						passwordSpan.style.fontStyle = "normal";
						return true;
					}
				}

				function changeVisibility() {
					document.getElementById("password").type = (document.getElementById("password").type == "password") ? "text" : "password";
					document.getElementById("visibility_switch").value = (document.getElementById("visibility_switch").value == "顯示密碼") ? "隱藏密碼" : "顯示密碼"; 
				}

				function clearMessage() {
					document.getElementById("accountSpan").innerHTML = "";
					document.getElementById("passwordSpan").innerHTML = "";
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
                        <h5 class="font-weight-bold text-uppercase mb-4">Footer Content</h5>
                        <p>Here you can use rows and columns to organize your footer content.</p>
                        <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit. Fugit amet numquam iure provident voluptate
                          esse
                          quasi, veritatis totam voluptas nostrum.</p>
                
                      </div>
                      <!-- Grid column -->
                
                      <hr class="clearfix w-100 d-md-none">
                
                      <!-- Grid column -->
                      <div class="col-md-2 col-lg-2 mx-auto my-md-4 my-0 mt-4 mb-1">
                
                        <!-- Links -->
                        <h5 class="font-weight-bold text-uppercase mb-4">About</h5>
                
                        <ul class="list-unstyled">
                          <li>
                            <p>
                              <a href="#!">PROJECTS</a>
                            </p>
                          </li>
                          <li>
                            <p>
                              <a href="#!">ABOUT US</a>
                            </p>
                          </li>
                          <li>
                            <p>
                              <a href="#!">BLOG</a>
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
                              <i class="fas fa-home mr-3"></i> New York, NY 10012, US</p>
                          </li>
                          <li>
                            <p>
                              <i class="fas fa-envelope mr-3"></i> info@example.com</p>
                          </li>
                          <li>
                            <p>
                              <i class="fas fa-phone mr-3"></i> + 01 234 567 88</p>
                          </li>
                          <li>
                            <p>
                              <i class="fas fa-print mr-3"></i> + 01 234 567 89</p>
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