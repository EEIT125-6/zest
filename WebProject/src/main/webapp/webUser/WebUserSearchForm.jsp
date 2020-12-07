<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<% 
	response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
	response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
	response.setHeader("Pragma","no-cache"); // HTTP 1.0
	response.setDateHeader ("Expires", -1); // 防止proxy server進行快取
%>
<!-- taglib宣告 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- taglib宣告 -->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="styles/WebUserRegisterForm.css">
   
    <title>進行搜索</title>
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
              <br><c:out value="${userFullData.account}" />|
              <a href="../webUser/WebUserLogoutManual.jsp">登出</a>|
              <a href="../product/index.jsp"><img src="../Images/PLZPLZ-removebg-preview.png" class="shopcar"></a>
            </p>
              </div>
            </div>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
               <!-- 將放於Session中的JavaBean取出，class寫包含package的全名，scope設為session -->
				<jsp:useBean id="userFullData" class="webUser.model.WebUserData"
					scope="session" />
				<c:if test="${userFullData.password == null}">
					<c:redirect url="WebUserLogin.jsp" />
				</c:if>
               <form action="/WebProject/webUser/WebUserServlet" method="post" onSubmit="return checkForm();">
					<fieldset>
						<legend>搜尋選項</legend>
						<hr />
						<label>帳號名稱：</label> 
						<input type="text" name="selectedAccount" id="account" size="40" maxlength="20" onblur="checkAccountName()"
							placeholder="請輸入要查詢的帳號，8~20個字" />
						<span id="accountSpan"></span>
						<hr />
						<label>用戶暱稱：</label>
						<input type="text" name="selectedNickname" id="nickname" size="40" maxlength="20" onblur="checkNickname()"
						    placeholder="請輸入要查詢的暱稱" />
						<span id="nicknameSpan"></span>
						<hr />
						<label>偏好食物：</label>
						<input type="checkbox" name="selectedFervor" value="中式" onblur="checkFervor()" />
						<label>中式</label>
						<input type="checkbox" name="selectedFervor" value="快餐" onblur="checkFervor()" />
						<label>快餐</label>
						<input type="checkbox" name="selectedFervor" value="燒肉" onblur="checkFervor()" />
						<label>燒肉</label>
						<input type="checkbox" name="selectedFervor" value="西式" onblur="checkFervor()" />
						<label>西式</label>
						<input type="checkbox" name="selectedFervor" value="下午茶" onblur="checkFervor()" />
						<label>下午茶</label>
						<input type="checkbox" name="selectedFervor" value="日式" onblur="checkFervor()" />
						<label>日式</label>
						<input type="checkbox" name="selectedFervor" value="皆可" onblur="checkFervor()" />
						<label>皆可</label>
						<span id="fervorSpan"></span>
						<hr />
					    <label>居住區域：</label>
				    	<select name="selectedLocationCode" id="locationCode" onblur="checkLocationCode()">
							<option value="">請選擇要查詢的區域</option>
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
						<span id="locationCodeSpan"></span>
					    <hr />
					    <span id="searchSpan"></span>
					</fieldset>
					<div align="center">
						<a href="WebUserMain.jsp"><input type="button" id="back" name="back" value="返回"></a>
						<input type="submit" id="submit" name="select" value="執行查詢">
						<input type="reset" name="reset" value="重設條件" onclick="clearMessage()">
					</div>
				</form>
				<c:if test="${selectResultMessage.length() > 0}">
					<i class='material-icons' style='font-size:18px;color:red'>cancel</i>
					<c:out value="${selectResultMessage}" />
				</c:if>
				<c:if test="${empty userDataList}">
					<div align="center">
						<p>沒有查詢到任何符合條件的資料</p>
					</div>
				</c:if>
				<c:if test="${not empty userDataList}">
					<form method="post">
						<fieldset>
							<legend>查詢到 <c:out value="${userDataList.size()}"/> 筆符合條件的資料</legend>
							<hr />
							<c:forEach var="userData" varStatus="index" items="${userDataList}">
								<c:if test='${index.first }'>
									<c:out value="<table border='1'>" escapeXml='false'/>
									<c:out value=
									"<tr>
										<td>帳號名稱</td>
										<td>稱呼名稱</td>
										<td>偏好食物</td>
										<td>居住區域</td>
										<c:if test='${userFullData.lv == -1}'>
										<td>帳號狀態</td>
										</c:if>
									</tr>" escapeXml='false'/>
								</c:if>
								
								<tr>
									<td>${userData.account}</td>
									<td>${userData.nickname}</td>
									<td>${userData.fervor}</td>
									<td>
										<c:choose>
											<c:when test="${userData.locationCode=='t01'}">臺北市</c:when>
											<c:when test="${userData.locationCode=='t02'}">新北市</c:when>
											<c:when test="${userData.locationCode=='t03'}">桃園市</c:when>
											<c:when test="${userData.locationCode=='t04'}">臺中市</c:when>
											<c:when test="${userData.locationCode=='t05'}">臺南市</c:when>
											<c:when test="${userData.locationCode=='t06'}">高雄市</c:when>
											<c:when test="${userData.locationCode=='t07'}">基隆市</c:when>
											<c:when test="${userData.locationCode=='t08'}">新竹市</c:when>
											<c:when test="${userData.locationCode=='t09'}">嘉義市</c:when>
											<c:when test="${userData.locationCode=='t10'}">新竹縣</c:when>
											<c:when test="${userData.locationCode=='t11'}">苗栗縣</c:when>
											<c:when test="${userData.locationCode=='t12'}">彰化縣</c:when>
											<c:when test="${userData.locationCode=='t13'}">南投縣</c:when>
											<c:when test="${userData.locationCode=='t14'}">雲林縣</c:when>
											<c:when test="${userData.locationCode=='t15'}">嘉義縣</c:when>
											<c:when test="${userData.locationCode=='t16'}">屏東縣</c:when>
											<c:when test="${userData.locationCode=='t17'}">宜蘭縣</c:when>
											<c:when test="${userData.locationCode=='t18'}">花蓮縣</c:when>
											<c:when test="${userData.locationCode=='t19'}">臺東縣</c:when>
											<c:when test="${userData.locationCode=='t20'}">澎湖縣</c:when>
											<c:when test="${userData.locationCode=='t21'}">金門縣</c:when>
											<c:when test="${userData.locationCode=='t22'}">連江縣</c:when>
											<c:when test="${userData.locationCode=='t23'}">其他區</c:when>
										</c:choose>
									</td>
									<c:if test='${userFullData.lv == -1}'>
										<td>${userData.status}</td>
									</c:if>
								</tr>
								
								<c:if test='${index.last }'>
									<c:out value="</table>" escapeXml='false'/>
									<hr />
								</c:if>
							</c:forEach>
						</fieldset>
					</form>
				</c:if>
				<script src="scripts/WebUserSearchForm.js"></script>
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