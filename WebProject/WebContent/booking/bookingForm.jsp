<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<% 
response.setContentType("text/html;charset=UTF-8");
response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
response.setHeader("Pragma","no-cache"); // HTTP 1.0
response.setDateHeader ("Expires", -1); // Prevents caching at the proxy server
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>訂位系統</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/themes/hot-sneaks/jquery-ui.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>

 <script type="text/javascript">
	  
	 function checkPhone( strPhone ) 	{ 
	 	    var cellphone = /^09[0-9]{8}$/; 
	 	 
	 	    if ( !cellphone.test( strPhone ) ) { 
	 	        alert( "手機格式輸入錯誤" ); 
	 	    } 
	 	    
	 	};  
 	
 		function check(){
 			if($("#datepicker1").val()==""){
 				alert("請選擇訂位日期");
 				return false;
 			}else if($("#name").val()==""){
 				alert("姓名不得為空白");
 				eval("document.form['name'].focus()");	
 				return false;
 			}else if($("#phone").val()==""){
 				alert("手機號碼尚未填寫");
 				eval("document.form['phone'].focus()");	
 				return false;
 			}else if($("#email").val()==""){
 				alert("e-mail尚未填寫");
 				eval("document.form['email'].focus()");	
 				return false;
 			}else{
 				return true;  
 			}
 		};

 </script>
 <script >
    $(document).ready(function(){
      $.datepicker.regional['zh-TW']={
        dayNames:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],
        dayNamesMin:["日","一","二","三","四","五","六"],
        monthNames:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
        monthNamesShort:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
        prevText:"上月",
        nextText:"次月",
        weekHeader:"週"
        };
      $.datepicker.setDefaults($.datepicker.regional["zh-TW"]);
      $("#datepicker1").datepicker({dateFormat:"yy-mm-dd"});

      });
  </script>


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
        fieldset{
            width: 500px;
            border: 2px solid orange;
            border-radius: 1%;
            margin: 0 auto;
        }
        legend{
            font-size: xx-large;
            /* text-align: center; */
            margin-left: 50px;

        }
        .st1{
            width: 450px;
            border-bottom: dashed gray;
            margin: 30px;
            padding-bottom:10px;
        }
        .st2{
            width: 450px;
            text-align: center;
            margin: 10px;
        }
        .st3{
            width: 100px;
            float: left;
            text-align: right;
        }
    </style>
</head>
<body>
      <div class="container-fluid  header" >
              <div class="container" >
              <a href="../Index1.jsp"><img src="../Images/LOGO1-removebg-preview.png" style="float: left; height: 70px;"></a>
              <p style="text-align: right;font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;">
              <br><a href="webUser/WebUserLogin.jsp">登入</a>  |
               <a href="webUser/WebUserRegisterForm.jsp">註冊</a>  |
              <a href="../product/index.jsp"><img src="../Images/PLZPLZ-removebg-preview.png" class="shopcar"></a>
              <a href="Page1.jsp"><i class="material-icons" style="font-size:24px">build</i></a>
            </p>
              </div>
       </div>
            

<form id="form1" name="form1" action="../BookingServlet" method="post" onsubmit="return check();" >
    <fieldset>
        <legend>填寫訂位資料</legend>
        
        <div class="st1">
            <label for="" class="st3">訂位日期:</label>
            <input id="datepicker1" type="text" name="bookingdate" > 
   
        </div>
   
        <div class="st1">
            <label for="" class="st3">時間:</label>
            <select name="time">
                <option value="12">12:00</option>
                <option value="13">13:00</option>
                <option value="17">17:00</option>
                <option value="18">18:00</option>
                <option value="19">19:00</option>
            </select>
        </div>
        <div class="st1">
            <label for="" class="st3">人數:</label>
            <select name="number">
                <option value="1">1人</option>
                <option value="2">2人</option>
                <option value="3">3人</option>
                <option value="4">4人</option>
                <option value="5">5人</option>
            </select>
        </div>
        <div class="st1">
            <label for="name"  class="st3">姓名:</label>
            <input type="text" id="name" name="name" placeholder="請輸入姓名" size="20" autocomplete="off"> 
        </div>
        <div class="st1">
            <label for="phone"  class="st3">手機:</label>
            <input type="tel" id="phone" name="phone" placeholder="請輸入電話" size="20" autocomplete="off" onblur="checkPhone(this.value);"> 
        </div>
        <div class="st1">
            <label for="" class="st3">e-mail:</label>
            <input type="email" id="email" name="email" >
        </div>
        <div class="st1">
            <label for="" class="st3">用餐目的:</label>
            <select name="purpose">
                <option value="normal">一般聚餐</option>
                <option value="bday">生日慶祝</option>
                <option value="date">浪漫約會</option>
                <option value="family">家人聚餐</option>
                <option value="business">商務聚餐</option>
            </select>
        </div>
        <div class="st1">
            <label for="cm"  class="st3">特殊需求:</label> 
            <textarea name="needs" id="cm"cols="40" rows="5"></textarea>
        </div>
        <div class="st2">
            <a href="javascript:history.back()"><input type="button" name="back" value="上一步"></a>
            <input type="submit" name="next" id="next" value="下一步" >
        </div>
    </fieldset>
    </form>
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