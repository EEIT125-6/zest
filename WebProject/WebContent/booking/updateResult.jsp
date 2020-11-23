<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<title>訂位紀錄</title>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/themes/hot-sneaks/jquery-ui.css" rel="stylesheet">
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
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
      $("#datepicker1").datepicker({dateFormat:"yy-mm-dd" });
      
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
           background: url("./Images/backbar2-1.jpg"); 
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
              <a href="./Index1.jsp"><img src="./Images/LOGO1-removebg-preview.png" style="float: left; height: 70px;"></a>
              <p style="text-align: right;font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;"><br>登入 | 註冊  |<img src="./Images/PLZPLZ-removebg-preview.png" class="shopcar">
            </p>
              </div>
            </div>
<center>
<jsp:useBean id="update" class="booking.bean.BookingBean" scope="session" />
<h2>
訂位紀錄 : 
</h2>
<p>請選擇欲修改之項目</p>
<form action=".\BookingServlet" method="post">
<table  cellspacing="1" cellpadding="1" border="1" width="500px">
<tr bgcolor="#FFFFE1">
    <td>訂單編號:</td>
    <td><jsp:getProperty name="update" property="bookingNo" /></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>餐廳名稱:</td>
    <td><jsp:getProperty name="update" property="restaurant" /></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>訂位日期:</td>
    <td><input type="text" name="bookingdate" id="datepicker1" value="<jsp:getProperty name="update" property="bookingdate" />"></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>時間:</td>
    <td><input type="text" name="time" value="<jsp:getProperty name="update" property="time" />"></td>
    
</tr>
<tr bgcolor="#FFFFE1">
    <td>人數:</td>
    <td><input type="text" name="number" value="<jsp:getProperty name="update" property="number" />"></td>
    
</tr>
<tr bgcolor="#F2F4FB">
    <td>姓名:</td>
    <td><input type="text" name="name" value="<jsp:getProperty name="update" property="name" />"></td>
    
</tr>
<tr bgcolor="#FFFFE1">
    <td>手機:</td>
    <td><input type="tel" name="phone" value="<jsp:getProperty name="update" property="phone" />"></td>
    
</tr>
<tr bgcolor="#F2F4FB">
    <td>e-mail:</td>
    <td><input type="email" name="mail" value="<jsp:getProperty name="update" property="mail" />"></td>
    
</tr>
<tr bgcolor="#FFFFE1">
    <td>特殊需求:</td>
    <td><input type="text" name="needs" value="<jsp:getProperty name="update" property="needs" />"></td>
    
</tr>

</table>
<div style="margin-top:10px;">
<input type="submit" name="confirmUpd" value="確認" >
</div>
</form>        
</center> 
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