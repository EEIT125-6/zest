<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setCharacterEncoding("UTF-8");%>
<%response.setContentType("text/html;charset=UTF-8"); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    
    
    <title>橙皮</title>
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
    </style>
</head>
<body>
            <div class="container-fluid  header" >
              <div class="container" >
              <a href="Index1.jsp"><img src="Images/LOGO1-removebg-preview.png" style="float: left; height: 70px;"></a>
              <p style="text-align: right;font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;"><br>登入 | 註冊  |<img src="Images/PLZPLZ-removebg-preview.png" class="shopcar">
            </p>
              </div>
            </div>
            <div class="container-fluid photo">
                <!-- <img src="images/backbar2-1.jpg"> -->
                    <form action="SimpleController" method="GET" enctype="UTF-8"  >
                      <fieldset  style="padding: 8px;margin: auto;width: 600px; background-color:rgb(126, 125, 125,0.3);border-radius: 4px;">
                        <input type ="text" id="srchid" name="nsrch" size="60"  placeholder="搜尋餐廳"
                        style="height: 36px;;border-radius: 4px;line-height: 38px;border: solid 2px black;;" >
                        <button type="submit"  style="background-color:#fcbf49 ;border: 1px black solid;border-radius: 4px;float:right;
                        line-height: 0px;">
                          <img src="Images/searchbut.jpg" >
                        </button>
                      </fieldset>
                    </form>
            </div>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
                <div class="jumbotron row" style="padding: 25px; background-color: white;">
   <c:url value="SimpleController" var="riceURL">
   <c:param name="sclass" value="中式"/>
   </c:url>             
                
                  <div class="col-sm-2" style="border-right:  rgb(204, 203, 203) 1px solid;;text-align: center"><a href="${riceURL }" ><img src="Images/S1.jpg" style="width: 80px;"></a><br>中式</div>
                  
                  
   <c:url value="SimpleController" var="JPURL">
   <c:param name="sclass" value="日式"/>
   </c:url>
   
   
    			  <div class="col-sm-2" style="border-right:  rgb(204, 203, 203) 1px solid;;text-align: center"><a href="${JPURL}"><img src="Images/S2.jpg" style="width: 80px;"/></a><br>日式</div>
                  
                  
		<c:url value="SimpleController" var="TEAURL">
			<c:param name="sclass" value="下午茶" />
		</c:url>
		<div class="col-sm-2"
			style="border-right: rgb(204, 203, 203) 1px solid;; text-align: center">
			<a href="${TEAURL}"><img src="Images/S3.jpg" style="width: 80px;"></a><br>下午茶
		</div>

		
		<c:url value="SimpleController" var="WESTURL">
			<c:param name="sclass" value="西式" />
		</c:url>
		<div class="col-sm-2"
			style="border-right: rgb(204, 203, 203) 1px solid;; text-align: center">
			<a href="${WESTURL}"><img src="Images/S4.jpg" style="width: 80px;"></a><br>西式
		</div>
		
   <c:url value="SimpleController" var="fastURL">
   <c:param name="sclass" value="快餐"/>
   </c:url>
                  <div class="col-sm-2" style="border-right:  rgb(204, 203, 203) 1px solid;;text-align: center"><a href="${fastURL}"><img src="Images/S5.jpg" style="width: 80px;"></a><br>快餐</div>

   <c:url value="SimpleController" var="metURL">
   <c:param name="sclass" value="燒肉"/>
   </c:url>                  
                  <div class="col-sm-2" style="text-align: center"><a href="${metURL }"><img src="Images/S6.jpg" style="width: 80px;"></a><br>燒肉</div>
                  
                  <!-- <div class="col-sm-4"><i class="fas fa-cloud"></i></div>
                  <div class="col-sm-4"><i class="fas fa-cloud"></i></div> -->
                </div>
                <div class="jumbotron">
                  推薦輪播放
                </div>
                <div class="jumbotron">
                  美食照片
                </div>
                <div class="row" style="color:black;">
                  <div class="col-sm-4">
                    <h3>最新美食文章</h3>
                    <p>The naturally fermented tofu is tender!</p>
                    <p>
Recommend Lin Kee Spicy Duck Blood Stinky Tofu, which features cabbage. The soup is sweet and not too strong. The naturally fermented tofu is tender! This home has its own characteristics! Lin Ji is great and the service is also very good
</p>
                  </div>
                  <div class="col-sm-4">
                    <h3>優質餐廳 x 優質部落客</h3>
                    <p>Quality restaurant x quality blogger cooperation</p>
                    <p>
Hey son, eat cakes Taichung Tablet House│The popular check-in sweets and snacks in Tainan are now in Taichung! There are different tastes every day, just in the alley next to Zhongyou Department Store in Yizhong Business District~
</p>
                  </div>
                  <div class="col-sm-4">
                    <h3>部落客合作洽詢</h3>        
                    <p>Blogger cooperation</p>
                    <p>Triple PONDER COFFEE Ponder Coffee‧Ecstasy Back to Gan Coffee X New Zealand Dessert X Enjoy a Happy Time</p>
                  </div>
                </div>
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