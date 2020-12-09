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
        <link rel="stylesheet" href="styles/WebUserRegisterForm.css">
        
    <title>查詢結果</title>
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
				<c:if test="${empty managedUserData}">
					<c:redirect url="WebUserLogin.jsp" />
				</c:if>
				<form action="/WebProject/webUser/WebUserServlet" method="post" onSubmit="return lastCheck();">
					<fieldset>
						<c:if test="${deleteMessage == null}">
							<c:if test="${updateResultMessage != null}">
								<legend><c:out value="${updateResultMessage}" /></legend>
							</c:if>
						</c:if>
						<c:if test="${deleteMessage != null}">
							<legend><c:out value="${deleteMessage}" /></legend>
						</c:if>
						<c:if test="${deleteMessage == null}">
							<c:if test="${updateResultMessage == null}">
								<legend><c:out value="${getResultMessage}" /></legend>
							</c:if>
						</c:if>
						<hr />
							<input type="hidden" name="userId" id="userId" value="${managedUserData.userId}" />
							<input type="hidden" name="account" id="account" value="${managedUserData.account}" />
							<label>帳號名稱：</label>
							<c:out value="${managedUserData.account}" />
							<hr />
							<label>帳號密碼：</label>
							<c:if test="${managedUserData.password.length() > 0}">
								<c:forEach var="passwordChar" begin="0" end="${managedUserData.password.length()-1}">
									<c:out value = "*" />
								</c:forEach>
							</c:if>
							<hr />
							<label>中文姓氏：</label>
							<c:out value="${managedUserData.firstName}" />
							<input type="hidden" name="firstName" id="firstName" value="${managedUserData.firstName}">
							<hr />
							<label>中文名字：</label>
							<c:out value="${managedUserData.lastName}" />
							<input type="hidden" name="lastName" id="lastName" value="${managedUserData.lastName}">
							<hr />
							<label>稱呼方式：</label>
							<c:out value="${managedUserData.nickname}" />
							<input type="hidden" name="nickname" id="nickname" value="${managedUserData.nickname}">
							<hr />
							<label>生理性別：</label>
							<c:choose>
								<c:when test="${managedUserData.gender == 'M'}">男性</c:when>
								<c:when test="${managedUserData.gender == 'F'}">女性</c:when>
								<c:when test="${managedUserData.gender == 'N'}">不方便提供</c:when>
							</c:choose>
							<hr />
							<label>西元生日：</label>
							<c:out value="${managedUserData.birth}" />
							<hr />
							<label>偏好食物：</label>
							<c:out value="${managedUserData.fervor}" />
							<input type="hidden" name="fervor" id="fervor" value="${managedUserData.fervor}">
							<hr />
							<label>聯絡信箱：</label>
							<c:out value="${managedUserData.email}" />
							<input type="hidden" name="email" id="email" value="${managedUserData.email}">
							<hr />
							<label>聯絡電話：</label>
							<c:out value="${managedUserData.phone}" />
							<input type="hidden" name="phone" id="phone" value="${managedUserData.phone}">
							<hr />
							<label>是否願意接收促銷/優惠訊息：</label>
							<c:choose>
								<c:when test="${managedUserData.getEmail=='Y'}">願意</c:when>
								<c:when test="${managedUserData.getEmail=='N'}">不願意</c:when>
							</c:choose>
							<input type="hidden" name="getEmail" id="getEmail" value="${managedUserData.getEmail}">
							<hr />
							<label>居住區域：</label>
							<c:choose>
								<c:when test="${managedUserData.locationCode=='t01'}">臺北市</c:when>
								<c:when test="${managedUserData.locationCode=='t02'}">新北市</c:when>
								<c:when test="${managedUserData.locationCode=='t03'}">桃園市</c:when>
								<c:when test="${managedUserData.locationCode=='t04'}">臺中市</c:when>
								<c:when test="${managedUserData.locationCode=='t05'}">臺南市</c:when>
								<c:when test="${managedUserData.locationCode=='t06'}">高雄市</c:when>
								<c:when test="${managedUserData.locationCode=='t07'}">基隆市</c:when>
								<c:when test="${managedUserData.locationCode=='t08'}">新竹市</c:when>
								<c:when test="${managedUserData.locationCode=='t09'}">嘉義市</c:when>
								<c:when test="${managedUserData.locationCode=='t10'}">新竹縣</c:when>
								<c:when test="${managedUserData.locationCode=='t11'}">苗栗縣</c:when>
								<c:when test="${managedUserData.locationCode=='t12'}">彰化縣</c:when>
								<c:when test="${managedUserData.locationCode=='t13'}">南投縣</c:when>
								<c:when test="${managedUserData.locationCode=='t14'}">雲林縣</c:when>
								<c:when test="${managedUserData.locationCode=='t15'}">嘉義縣</c:when>
								<c:when test="${managedUserData.locationCode=='t16'}">屏東縣</c:when>
								<c:when test="${managedUserData.locationCode=='t17'}">宜蘭縣</c:when>
								<c:when test="${managedUserData.locationCode=='t18'}">花蓮縣</c:when>
								<c:when test="${managedUserData.locationCode=='t19'}">臺東縣</c:when>
								<c:when test="${managedUserData.locationCode=='t20'}">澎湖縣</c:when>
								<c:when test="${managedUserData.locationCode=='t21'}">金門縣</c:when>
								<c:when test="${managedUserData.locationCode=='t22'}">連江縣</c:when>
								<c:when test="${managedUserData.locationCode=='t23'}">其他區</c:when>
							</c:choose>
							<input type="hidden" name="locationCode" id="locationCode" value="${managedUserData.locationCode}">
							<hr />
							<label>生活地點一：</label>
							<c:out value="${managedUserData.addr0}" />
							<input type="hidden" name="addr0" id="addr0" value="${managedUserData.addr0}">
							<hr />
							<label>生活地點二：</label>
							<c:out value="${managedUserData.addr1}" />
							<input type="hidden" name="addr1" id="addr1" value="${managedUserData.addr1}">
							<hr />
							<label>生活地點三：</label>
							<c:out value="${managedUserData.addr2}" />
							<input type="hidden" name="addr2" id="addr2" value="${managedUserData.addr2}">
							<hr />
							<label>所擁有的橙幣：</label>
							<c:out value="${managedUserData.zest}" />
							<hr />
							<label>帳號狀態：</label>
							<c:choose>
								<c:when test="${managedUserData.status=='active'}">已啟用</c:when>
								<c:when test="${managedUserData.status=='quit'}">已棄用</c:when>
							</c:choose>
							<input type="hidden" name="status" id="status" value="${managedUserData.status}">
							<hr />
					</fieldset>
					<div align="center">
						<c:choose>
							<c:when test="${managedUserData.status=='active'}">
								<input type="submit" id="quitAccount" name="update" value="棄用帳號">
							</c:when>
							<c:when test="${managedUserData.status=='quit'}">
								<input type="submit" id="activeAccount" name="update" value="啟用帳號">
							</c:when>
						</c:choose>
						<input type="submit" id="deleteAccount" name="delete" value="刪除帳號">
						<a href="WebUserSearchForm.jsp"><input type="button" name="select" value="返回上一頁"></a>
					</div>
				</form>
				<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
				<script>
					$("#deleteAccount").click(function () {
				        alert("您即將刪除此帳號，本操作不可逆");
				    });
					function lastCheck() {
						let choice=confirm("是否要執行特定的操作？");
						if (choice) {
							return true;
						} else {
							return false;
						}
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