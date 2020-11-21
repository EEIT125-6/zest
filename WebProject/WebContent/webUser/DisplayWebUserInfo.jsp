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
    <link rel="stylesheet" href="styles/WebUserRegisterForm.css">
    
    <title>註冊資料確認</title>
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
           background: url("../Images/backbar2-1.jpg"); 
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
              <a href="http://localhost:8080/WebProject/Index.html"><img src="../Images/LOGO1-removebg-preview.png" style="float: left; height: 70px;"></a>
              <p style="text-align: right;font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;"><br>登入 | 註冊  |<img src="../Images/PLZPLZ-removebg-preview.png" class="shopcar">
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
                          <img src="../Images/searchbut.jpg" >
                        </button>
                      </fieldset>
                    </form>
            </div>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
		        <!-- 將放於Session中的JavaBean取出，class寫包含package的全名，scope設為session -->
				<jsp:useBean id="reg_webUser" class="webUser.WebUserBean"
					scope="session" />
				<c:if test="${param.password == null}">
					<c:redirect url="WebUserRegisterForm.jsp" />
				</c:if>
				<form action="/WebProject/webUser/WebUserServlet" method="post">
					<fieldset>
						<legend>註冊資料如下，如果無誤請按「確認」</legend>
						<hr />
						<label>帳號名稱：</label>
						<jsp:getProperty name="reg_webUser" property="account" />
						<hr />
						<label>帳號密碼：</label>
						<c:if test="${param.password.length() > 0}">
							<c:forEach var="passwordChar" begin="0" end="${param.password.length()-1}">
								<c:out value = "*" />
							</c:forEach>
						</c:if>
						<hr />
						<label>中文姓氏：</label>
						<jsp:getProperty name="reg_webUser" property="first_name" />
						<hr />
						<label>中文名字：</label>
						<jsp:getProperty name="reg_webUser" property="last_name" />
						<hr />
						<label>稱呼方式：</label>
						<jsp:getProperty name="reg_webUser" property="nickname" />
						<hr />
						<label>生理性別：</label>
						<c:choose>
							<c:when test="${param.gender=='M'}">男性</c:when>
							<c:when test="${param.gender=='F'}">女性</c:when>
							<c:when test="${param.gender=='N'}">不方便提供</c:when>
						</c:choose>
						<hr />
						<label>西元生日：</label>
						<jsp:getProperty name="reg_webUser" property="birthday" />
						<hr />
						<label>偏好食物：</label>
						<jsp:getProperty name="reg_webUser" property="fervor" />
						<hr />
						<label>聯絡信箱：</label>
						<jsp:getProperty name="reg_webUser" property="email" />
						<hr />
						<label>聯絡電話：</label>
						<jsp:getProperty name="reg_webUser" property="phone" />
						<hr />
						<label>是否願意接收促銷/優惠訊息：</label>
						<c:choose>
							<c:when test="${param.get_email=='Y'}">願意</c:when>
							<c:when test="${param.get_email=='N'}">不願意</c:when>
						</c:choose>
						<hr />
						<label>居住區域：</label>
						<c:choose>
							<c:when test="${param.location_code=='t01'}">臺北市</c:when>
							<c:when test="${param.location_code=='t02'}">新北市</c:when>
							<c:when test="${param.location_code=='t03'}">桃園市</c:when>
							<c:when test="${param.location_code=='t04'}">臺中市</c:when>
							<c:when test="${param.location_code=='t05'}">臺南市</c:when>
							<c:when test="${param.location_code=='t06'}">高雄市</c:when>
							<c:when test="${param.location_code=='t07'}">基隆市</c:when>
							<c:when test="${param.location_code=='t08'}">新竹市</c:when>
							<c:when test="${param.location_code=='t09'}">嘉義市</c:when>
							<c:when test="${param.location_code=='t10'}">新竹縣</c:when>
							<c:when test="${param.location_code=='t11'}">苗栗縣</c:when>
							<c:when test="${param.location_code=='t12'}">彰化縣</c:when>
							<c:when test="${param.location_code=='t13'}">南投縣</c:when>
							<c:when test="${param.location_code=='t14'}">雲林縣</c:when>
							<c:when test="${param.location_code=='t15'}">嘉義縣</c:when>
							<c:when test="${param.location_code=='t16'}">屏東縣</c:when>
							<c:when test="${param.location_code=='t17'}">宜蘭縣</c:when>
							<c:when test="${param.location_code=='t18'}">花蓮縣</c:when>
							<c:when test="${param.location_code=='t19'}">臺東縣</c:when>
							<c:when test="${param.location_code=='t20'}">澎湖縣</c:when>
							<c:when test="${param.location_code=='t21'}">金門縣</c:when>
							<c:when test="${param.location_code=='t22'}">連江縣</c:when>
							<c:when test="${param.location_code=='t23'}">其他區</c:when>
						</c:choose>
						<hr />
						<label>生活地點一：</label>
						<jsp:getProperty name="reg_webUser" property="addr0" />
						<hr />
						<label>生活地點二：</label>
						<jsp:getProperty name="reg_webUser" property="addr1" />
						<hr />
						<label>生活地點三：</label>
						<jsp:getProperty name="reg_webUser" property="addr2" />
						<hr />
					</fieldset>
					<div align="center">
						<input type="submit" name="register" value="確認">
						<input type="submit" name="register" value="取消">
					</div>
				</form>
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