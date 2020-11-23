<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html;charset=UTF-8");
%>
<sql:setDataSource var="ds" dataSource="jdbc/EmployeeDB" />

<sql:query dataSource="${ds}" var="rs">
         select count(*) as number from store 
</sql:query>

<c:forEach var="row" items="${rs.rows}">
	<c:set var = "id" value = "${row.number+1}"/>
</c:forEach>

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

	    <div class="container" style="background-color: wheat;border-radius:5px;padding:100px;border: 1px solid wheat;box-shadow: 5px 5px 5px rgb(75, 75, 75);margin-top:15px;margin-bottom:15px; ">
        <!-- 用container -->
        <form action="exInsert.jsp"  
        method="post" >
            <fieldset style="width: 400px;margin:1px auto;">
                <legend>新增店家</legend>
                <input type="hidden" name="id" value="${id}">
                <label>商店名稱:
                    <input type="text" id="name" name="stname" value="" onblur="checkname();" >
                </label><span id="stnamespan"></span>
                <br>
                <label>商店類別 
                    <select name="sclass">
                    
                        <option value="中式"
                        
                         	selected="selected"
                    	
                        >中式</option>
                        
                        <option value="日式"



                        >日式</option>
                        
                        <option value="下午茶"


                        >下午茶</option>
                        
                        <option value="西式"

                        >西式</option>
                        
                        <option value="快餐"
                   
                        >快餐</option>
                        
                        <option value="燒肉" 

                    	>燒肉</option>
                    	
                    </select>
                </label>
                <br>
                <label>地址:
                    <input type="text" id="address" name="saddress" value="" onblur="checkaddress();" >
                </label><span id = "addressspan"></span>
                <br>
                <label>電話:
                    <input type="text" id="idtel" name="tel" value="" onblur="checktel();" >
                </label><span id = "telspan"></span>
                <br>
                <label style="width: 40px;float: left;text-align: right;padding-right: 3px;">簡介:
                    <textarea cols="40" rows="5" id="idstitd" name="stitd"></textarea>
                </label>
            </fieldset>
            <div style="text-align: center;">
                <input type="submit" value="新增">
                <input type="reset" value="清除">
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
       <script>
       		function checkname(){
       			let thenameval = document.getElementById("name").value;
       			if(thenameval==0 ){
       				stnamespan.innerHTML="<span style='color : red'>"+"請輸入商家名稱"+"</span>";
       			}else{
       				stnamespan.innerHTML="<span></span>"
       			}
       		}
       		function checkaddress(){
       			let thenameval = document.getElementById("address").value;
       			if(thenameval==0 ){
       				addressspan.innerHTML="<span style='color : red'>"+"請輸入商家位置"+"</span>";
       			}else{
       				addressspan.innerHTML="<span></span>"
       			}
       		}
       		function checktel(){
       			let thenameval = document.getElementById("idtel").value;
       			if(thenameval==0 ){
       				telspan.innerHTML="<span style='color : red'>"+"請輸入商家電話"+"</span>";
       			}else{
       				telspan.innerHTML="<span></span>"
       			}
       		}
       </script> 
</body>
</html>