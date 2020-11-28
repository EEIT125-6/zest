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
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
        <link rel="stylesheet" href="styles/WebUserRegisterForm.css">
        
    <title>修改個人資料</title>
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
              <br><c:out value="${userFullData.nickname}" />|
              <a href="../webUser/WebUserLogoutManual.jsp">登出</a>|
              <a href="../product/index.jsp"><img src="../Images/PLZPLZ-removebg-preview.png" class="shopcar"></a>
            </p>
              </div>
            </div>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
                <jsp:useBean id="userFullData" class="webUser.WebUserBean"
					scope="session" />
				<c:if test="${userFullData.password == null}">
					<c:redirect url="WebUserLogin.jsp" />
				</c:if>
				<c:if test="${selfData.get(0).password == null}">
					<c:redirect url="WebUserMain.jsp" />
				</c:if>
                <form action="/WebProject/webUser/WebUserServlet" method="post" onSubmit="return checkForm();">
                	<fieldset>
                		<legend>可修改的個人相關資料</legend>
                		<hr />
						<label>原始的中文姓氏：</label>
						<input type="hidden" name="originalFirst_name" id="originalFirst_name" value="${selfData.get(0).first_name}">
						<c:out value="${selfData.get(0).first_name}" />
						<br />
						<label>更正的中文姓氏：</label>
						<input type="text" name="updatedFirst_name" id="updatedFirst_name" size="40" maxlength="3" onblur="checkFirst_name()"
							placeholder="請輸入姓氏，1~3個中文字" />
						<span id="first_nameSpan"></span>
						<hr />
						<label>原始的中文名字：</label>
						<input type="hidden" name="originalLast_name" id="originalLast_name" value="${selfData.get(0).last_name}">
						<c:out value="${selfData.get(0).last_name}" />
						<br />
						<label>更正的中文名字：</label>
						<input type="text" name="updatedLast_name" id="updatedLast_name" size="40" maxlength="3" onblur="checkLast_name()"
							placeholder="請輸入名字，1~3個中文字" />
						<span id="last_nameSpan"></span>
						<hr />
						<label>原始的稱呼方式：</label>
						<input type="hidden" name="originalNickname" id="originalNickname" value="${selfData.get(0).nickname}">
						<c:out value="${selfData.get(0).nickname}" />
						<br />
						<label>更正的稱呼方式：</label>
						<input type="text" name="updatedNickname" id="updatedNickname" size="40" maxlength="20" onblur="checkNickname()"
							placeholder="請輸入想要的稱呼" />
						<span id="nicknameSpan"></span>
						<hr />
						<label>原始的偏好食物：</label>
						<input type="hidden" name="originalFervor" id="originalFervor" value="${selfData.get(0).fervor}">
						<c:out value="${selfData.get(0).fervor}" />
						<br />
						<label>更正的偏好食物：</label>
						<input type="checkbox" id="updatedFervor1" name="updatedFervor" value="米食" onblur="checkFervor()" />
						<label>中式</label>
						<input type="checkbox" id="updatedFervor2" name="updatedFervor" value="快餐" onblur="checkFervor()" />
						<label>快餐</label>
						<input type="checkbox" id="updatedFervor3" name="updatedFervor" value="燒肉" onblur="checkFervor()" />
						<label>燒肉</label>
						<input type="checkbox" id="updatedFervor4" name="updatedFervor" value="西式" onblur="checkFervor()" />
						<label>西式</label>
						<input type="checkbox" id="updatedFervor5" name="updatedFervor" value="下午茶" onblur="checkFervor()" />
						<label>下午茶</label>
						<input type="checkbox" id="updatedFervor6" name="updatedFervor" value="日式" onblur="checkFervor()" />
						<label>日式</label>
						<input type="checkbox" id="updatedFervor0" name="updatedFervor" value="皆可" onblur="checkFervor()" />
						<label>皆可</label>
						<span id="fervorSpan"></span>
						<hr />
						<label>原始的聯絡信箱：</label>
						<input type="hidden" name="originalEmail" id="originalEmail" value="${selfData.get(0).email}">
						<c:out value="${selfData.get(0).email}" />
						<br />
						<label>更正的聯絡信箱：</label>
						<input type="email" name="updatedEmail" id="updatedEmail" size="40" maxlength="30" onblur="checkEmail()"
						    placeholder="請輸入驗證、聯絡用的E-Mail地址" />
						<span id="emailSpan"></span>
						<hr />
						<label>原始的聯絡電話：</label>
						<input type="hidden" name="originalPhone" id="originalPhone" value="${selfData.get(0).phone}">
						<c:out value="${selfData.get(0).phone}" />
						<br />
						<label>更正的聯絡電話：</label>
						<input type="tel" name="updatedPhone" id="updatedPhone" size="40" maxlength="11" onblur="checkPhone()"
						    placeholder="請輸入行動電話或市內電話號碼" />
						<span id="phoneSpan"></span>
					    <hr />
					    <label>原始的接收促銷/優惠意願：</label>
					    <input type="hidden" name="originalGet_email" id="originalGet_email" value="${selfData.get(0).get_email}">
					    <c:choose>
							<c:when test="${selfData.get(0).get_email=='Y'}">願意</c:when>
							<c:when test="${selfData.get(0).get_email=='N'}">不願意</c:when>
						</c:choose>
					    <br />
					    <label>更正的接收促銷/優惠意願：</label>
						<input type="radio" id="updatedGet_email1" name="updatedGet_email" value="Y" onblur="checkGet_email()">
					    <label for="Y">願意</label>
					    <input type="radio" id="updatedGet_email2" name="updatedGet_email" value="N" onblur="checkGet_email()">
					    <label for="N">不願意</label>
					    <span id="get_emailSpan"></span>
						<hr />
					    <label>原始的居住區域：</label>
						<input type="hidden" name="originalLocation_code" id="originalLocation_code" value="${selfData.get(0).location_code}">
						<c:choose>
							<c:when test="${selfData.get(0).location_code=='t01'}">臺北市</c:when>
							<c:when test="${selfData.get(0).location_code=='t02'}">新北市</c:when>
							<c:when test="${selfData.get(0).location_code=='t03'}">桃園市</c:when>
							<c:when test="${selfData.get(0).location_code=='t04'}">臺中市</c:when>
							<c:when test="${selfData.get(0).location_code=='t05'}">臺南市</c:when>
							<c:when test="${selfData.get(0).location_code=='t06'}">高雄市</c:when>
							<c:when test="${selfData.get(0).location_code=='t07'}">基隆市</c:when>
							<c:when test="${selfData.get(0).location_code=='t08'}">新竹市</c:when>
							<c:when test="${selfData.get(0).location_code=='t09'}">嘉義市</c:when>
							<c:when test="${selfData.get(0).location_code=='t10'}">新竹縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t11'}">苗栗縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t12'}">彰化縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t13'}">南投縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t14'}">雲林縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t15'}">嘉義縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t16'}">屏東縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t17'}">宜蘭縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t18'}">花蓮縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t19'}">臺東縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t20'}">澎湖縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t21'}">金門縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t22'}">連江縣</c:when>
							<c:when test="${selfData.get(0).location_code=='t23'}">其他區</c:when>
						</c:choose>
						<br />
					    <label>更正的居住區域：</label>
				    	<select name="updatedLocation_code" id="updatedLocation_code" onblur="checkLocation_code()">
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
					    <label>原始的生活地點一：</label>
						<input type="hidden" name="originalAddr0" id="originalAddr0" value="${selfData.get(0).addr0}">
						<c:out value="${selfData.get(0).addr0}" />
						<br />
					    <label>更正的生活地點一：</label>
					    <input type="text" name="updatedAddr0" id="updatedAddr0" size="65" maxlength="65" onblur="checkAddr0()"
						    placeholder="此項為必填，請輸入完整地址方面後續服務之利用" />
						<br />
						<span id="addr0Span"></span>
					    <hr />
					    <label>原始的生活地點二：</label>
						<input type="hidden" name="originalAddr1" id="originalAddr1" value="${selfData.get(0).addr1}">
						<c:out value="${selfData.get(0).addr1}" />
						<br />
					    <label>更正的生活地點二：</label>
					    <input type="text" name="updatedAddr1" id="updatedAddr1" size="65" maxlength="65" onblur="checkAddr1()"
						    placeholder="此項為選填，請輸入完整地址方面後續服務之利用" />
						<br />
						<span id="addr1Span"></span>
					    <hr />
					    <label>原始的生活地點三：</label>
						<input type="hidden" name="originalAddr2" id="originalAddr2" value="${selfData.get(0).addr2}">
						<c:out value="${selfData.get(0).addr2}" />
						<br />
					    <label>更正的生活地點三：</label>
					    <input type="text" name="updatedAddr2" id="updatedAddr2" size="65" maxlength="65" onblur="checkAddr2()"
						    placeholder="此項為選填，請輸入完整地址方面後續服務之利用" />
						<br />
						<span id="addr2Span"></span>
					    <hr />
					    <span id="updatedSpan"></span>
                	</fieldset>
                	<div align="center">
                		<a href="WebUserMain.jsp"><input type="button" name="update" value="取消"></a>
						<input type="submit" name="update" value="資料修改完畢">
						<input type="reset" name="reset" value="重設" onclick="clearMessage()">
					</div>
                </form>
                <script src="scripts/WebUserModifyData.js"></script>
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