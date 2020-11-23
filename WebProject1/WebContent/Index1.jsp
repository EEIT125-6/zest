<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%request.setCharacterEncoding("UTF-8");%>
<%response.setContentType("text/html;charset=UTF-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
              <a href="Index1.jsp"><img src="Images/LOGO1-removebg-preview.png" style="float: left; height: 70px;"></a>
              <p style="text-align: right;font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;"><br>登入 | 註冊  |<img src="Images/PLZPLZ-removebg-preview.png" class="shopcar">
            </p>
              </div>
            </div>
            <div class="container-fluid photo">
                <!-- <img src="images/backbar2-1.jpg"> -->
                    <form action="SimpleController" method="GET" enctype="UTF-8"  >
                      <fieldset  style="padding: 8px;margin: auto;width: 550px; background-color:rgb(126, 125, 125,0.3);border-radius: 4px;">
                      	
                        <input type ="text" id="srchid" name="nsrch" size="57"  placeholder="搜尋餐廳"
                        style="height: 36px;border-radius: 4px;line-height: 38px;border: solid 2px black;" >
                        
                        	
                        <button type="submit"  style="background-color:#fcbf49 ;border: 1px black solid;border-radius: 4px;margin:0px
                         ;float:right">
                        	<img src="Images/searchbut.jpg" >  
                        </button>
                      </fieldset>  
                       
                        
                      
                    </form>
            </div>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
            		<div class="container" style="font-family: 'Nerko One', cursive;font-size:145%;">Restaurant category</div>
                <div class="jumbotron row" style="padding: 25px; background-color: white;font-size: 140%;font-family: 'Noto Sans TC', sans-serif;">
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
                
                
                <!-- --------------------------------------------------- -->
<sql:setDataSource var="ds" dataSource="jdbc/EmployeeDB" />
<sql:query dataSource="${ds}" var="rsbanner">
         select top(4) bannerurl,stname  from Store  ORDER BY NEWID()
</sql:query>
		<div class="container" style="font-family: 'Nerko One', cursive;font-size:145%;">Recommended carousel</div>
                <div class="jumbotron row" style="padding: 25px; background-color: white;font-size: 140%;font-family: 'Noto Sans TC', sans-serif;">
                	    <div id="wrapper1" class="wrapper">
        <ul class="slides">
        	<c:forEach var="row" items="${rsbanner.rows}">
	        <c:url value="detailStore.jsp" var="GOGOURL">
			<c:param name="stname" value="${row.stname}" />
			</c:url>  
	        <li><a href="${GOGOURL}"><img src="${row.bannerurl}" alt=""></a></li>
	         </c:forEach>
        </ul>
        <ul class="dot">
            <li id="1"></li>
            <li id="2"></li>
            <li id="3"></li>
            <li id="4"></li>
        </ul>
        <div id="prevSlide" class="slide_btn">
            <i class="fa fa-caret-left"></i>
        </div>
        <div  id="nextSlide" class="slide_btn">
            <i class="fa fa-caret-right"></i>
        </div>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="../js/jquery-3.5.1.min.js"></script>
    <script>
         $(function(){
            let slideNum=0;
            let slideCount=$(".slides li").length;
            let lastIndex=slideCount-1;

            
            console.log($(".dot li")); //4
            $(".dot li").eq(0).css("background-color","white");
            $(".dot li").mouseenter(function(){
                slideNum=$(this).index();
                console.log($(this).index());

                show();
            })

            function show(){
                $(".dot li").eq(slideNum).css("background-color","white")
                .siblings().css("background-color","transparent");
                
                let move = 0-1000*slideNum;
                $("ul.slides").css("left",move);    
            }
            $("#prevSlide").click(function(){
                slideNum--;
                if(slideNum<0){
                    slideNum=lastIndex;
                }
                console.log(slideNum);
                show();

            })
            $("#nextSlide").click(function(){
                slideNum++;
                if(slideNum>lastIndex){
                    slideNum=0;
                }
                console.log(slideNum);
                show();
            })


            let hi = window.setInterval(go,2000);
            $("#wrapper1").hover(function(){
                window.clearInterval(hi)
            },function(){
                hi = window.setInterval(go,2000);
            });
            function go(){
                slideNum++;
                if(slideNum>lastIndex){
                    slideNum=0;
                }
                show();
            }
           
        });
    </script>
                </div>
                <!-- -------------------------------------------------- -->
                
<sql:setDataSource var="ds" dataSource="jdbc/EmployeeDB" />
<sql:query dataSource="${ds}" var="rsphoto">
         select top(6) photourl,stname  from Store  ORDER BY NEWID()
</sql:query>
           				<div class="container" style="font-family: 'Nerko One', cursive;font-size:145%;">Food recommendation</div>
                		<div class="jumbotron row" style="padding: 25px; background-color: white;font-size: 140%;font-family: 'Noto Sans TC', sans-serif;">
                		
	                		<c:forEach var="row" items="${rsphoto.rows}">
	        				<c:url value="detailStore.jsp" var="GOURL">
							<c:param name="stname" value="${row.stname}" />
							</c:url>  
	                		<a href="${GOURL}"><img src="${row.photourl}" style="width:170px;height: 160px;margin-left:10px;border-radius:5px"></a>
	                  		</c:forEach>
                  		</div>
                  
                <div class="row" style="color:black;" >
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
    
<!-- ---------------------------------------- -->
<a href="https://www.blogger.com/blogger.g?blogID=2031514508322140995#" id="gotop">
   <i class="fas fa-chevron-up"></i>
</a>
<script type="text/javascript">
$(function() {
    /* 按下GoTop按鈕時的事件 */
    $('#gotop').click(function(){
        $('html,body').animate({ scrollTop: 0 }, 'slow');   /* 返回到最頂上 */
        return false;
    });
    
    /* 偵測卷軸滑動時，往下滑超過400px就讓GoTop按鈕出現 */
    $(window).scroll(function() {
        if ( $(this).scrollTop() > 700){
            $('#gotop').fadeIn();
        } else {
            $('#gotop').fadeOut();
        }
    });
});
</script>   
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