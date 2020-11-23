<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
	response.setHeader("Pragma","no-cache"); // HTTP 1.0
	response.setDateHeader ("Expires", -1); // 防止proxy server進行快取
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
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
		<link rel="stylesheet" href="styles/WebUserRegisterForm.css">
		        
    <title>進行註冊</title>
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
              <br><a href="WebUserLogin.jsp">登入</a> | 註冊  |
              <a href="../product/index.jsp"><img src="../Images/PLZPLZ-removebg-preview.png" class="shopcar"></a>
            </p>
              </div>
            </div>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
               <c:if test="${userFullData.password != null}">
					<c:redirect url="WebUserMain.jsp" />
				</c:if>
               <form action="/WebProject/webUser/WebUserServlet" method="post" onSubmit="return checkForm();">
					<fieldset>
						<legend>註冊相關資料</legend>
						<hr />
						<label>帳號名稱：</label> 
						<input type="text" name="account" id="account" size="40" maxlength="20" onblur="checkAccountName()"
							placeholder="請輸入帳號，6~20個字" required="required" />
						<input type="button" name="register" id="checkAccount" value="檢查帳號">
						<span id="accountSpan"></span>
						<hr />
						<label>帳號密碼：</label> 
						<input type="password" name="password" id="password" size="40" maxlength="20" onblur="checkAccountPassword()"
							placeholder="請輸入密碼，6~20個字" required="required" />
						<input type="button" name="visibility_switch" id="visibility_switch" value="顯示密碼" onclick="changeVisibility()">
						<span id="passwordSpan"></span>
						<hr />
						<label>中文姓氏：</label>
						<input type="text" name="first_name" id="first_name" size="40" maxlength="3" onblur="checkFirst_name()"
						    placeholder="請輸入姓氏，1~3個中文字" required="required" />
						<span id="first_nameSpan"></span>
						<hr />
						<label>中文名字：</label>
						<input type="text" name="last_name" id="last_name" size="40" maxlength="3" onblur="checkLast_name()"
						    placeholder="請輸入名字，1~3個中文字" required="required" />
						<span id="last_nameSpan"></span>
						<hr />
						<label>稱呼方式：</label>
						<input type="text" name="nickname" id="nickname" size="40" maxlength="20" onblur="checkNickname()"
						    placeholder="請輸入想要的稱呼(留白的話會設定為名字)" required="required" />
						<span id="nicknameSpan"></span>
						<hr />
						<label>生理性別：</label>
						<input type="radio" id="M" name="gender" value="M">
					    <label for="male">男性</label>
					    <input type="radio" id="F" name="gender" value="F">
					    <label for="female">女性</label>
					    <input type="radio" id="N" name="gender" value="N" checked="checked" >
					    <label for="other">不方便提供</label>
					    <hr />
					    <label>西元生日：</label>
						<input type="date" name="birth" id="birth" onblur="checkBirthday()" required="required" />
						<span id="birthdaySpan"></span>
						<hr />
						<label>偏好食物：</label>
						<input type="checkbox" name="fervor" value="米食" onblur="checkFervor()" />
						<label>中式</label>
						<input type="checkbox" name="fervor" value="快餐" onblur="checkFervor()" />
						<label>快餐</label>
						<input type="checkbox" name="fervor" value="燒肉" onblur="checkFervor()" />
						<label>燒肉</label>
						<input type="checkbox" name="fervor" value="西式" onblur="checkFervor()" />
						<label>西式</label>
						<input type="checkbox" name="fervor" value="下午茶" onblur="checkFervor()" />
						<label>下午茶</label>
						<input type="checkbox" name="fervor" value="日式" onblur="checkFervor()" />
						<label>日式</label>
						<input type="checkbox" name="fervor" value="皆可" checked="checked" onblur="checkFervor()" />
						<label>皆可</label>
						<span id="fervorSpan"></span>
						<hr />
						<label>聯絡信箱：</label>
						<input type="email" name="email" id="email" size="40" maxlength="30" onblur="checkEmail()"
						    placeholder="請輸入驗證、聯絡用的E-Mail地址" required="required" />
						<span id="emailSpan"></span>
						<hr />
						<label>聯絡電話：</label>
						<input type="tel" name="phone" id="phone" size="40" maxlength="11" onblur="checkPhone()"
						    placeholder="請輸入行動電話或市內電話號碼" required="required" />
						<span id="phoneSpan"></span>
						<hr />
						<label>是否願意接收促銷/優惠訊息：</label>
						<input type="radio" id="get_email" name="get_email" value="Y" checked="checked">
					    <label for="Y">願意</label>
					    <input type="radio" id="get_email" name="get_email" value="N">
					    <label for="N">不願意</label>
					    <hr />
					    <label>居住區域：</label>
				    	<select name="location_code" id="location_code" onblur="checkLocation_code()">
							<option value="">請選擇目前您居住/生活的區域</option>
							<option value="t01">臺北市</option>
							<option value="t02">新北市</option>
							<option value="t03">桃園市</option>
							<option value="t04">臺中市</option>
							<option value="t05">臺南市</option>
							<option value="t06">高雄市</option>
							<option value="t07">基隆市</option>
							<option value="t08">新竹市</option>
							<option value="t09">嘉義市</option>
							<option value="t10">新竹縣</option>
							<option value="t11">苗栗縣</option>
							<option value="t12">彰化縣</option>
							<option value="t13">南投縣</option>
							<option value="t14">雲林縣</option>
							<option value="t15">嘉義縣</option>
							<option value="t16">屏東縣</option>
							<option value="t17">宜蘭縣</option>
							<option value="t18">花蓮縣</option>
							<option value="t19">臺東縣</option>
							<option value="t20">澎湖縣</option>
							<option value="t21">金門縣</option>
							<option value="t22">連江縣</option>
							<option value="t23">其他區</option>
						</select>
						<span id="location_codeSpan"></span>
					    <hr />
					    <label>生活地點一：</label>
					    <input type="text" name="addr0" id="addr0" size="65" maxlength="65" onblur="checkAddr0()"
						    placeholder="此項為必填，請輸入完整地址方面後續服務之利用" required="required" />
						<br />
						<span id="addr0Span"></span>
					    <hr />
					    <label>生活地點二：</label>
					    <input type="text" name="addr1" id="addr1" size="65" maxlength="65"
						    placeholder="此項為選填">
					    <hr />
					    <label>生活地點三：</label>
					    <input type="text" name="addr2" id="addr2" size="65" maxlength="65"
						    placeholder="此項為選填">
					    <hr />
					</fieldset>
					<div align="center">
						<input type="submit" id="submit" name="register" value="送出">
						<input type="reset" name="reset" value="重設" onclick="clearMessage()">
					</div>
				</form>
				<script src="scripts/jquery-3.5.1.min.js"></script>
				<script src="scripts/WebUserRegisterForm.js"></script>
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
				            		accountStr = "您可建立此帳號！";
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
				            	} else {
				            		accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + accountStr;
				            		accountSpan.style.color = "black";
				            		accountSpan.style.fontStyle = "normal";
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