<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>全商品分析</title>
	<!--     字體跟ICON     -->
	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet" />
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" rel="stylesheet" />
	 <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" data-integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" data-crossorigin="anonymous"/>
    <!-- CSS Files -->
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/light-bootstrap-dashboard.css" rel="stylesheet" />	
</head>
<body>
    <div class="wrapper">
		<%@include file = "adminAdminSystem-side-header.jsp" %>
<!---------------------------------------------------------------------------->	
			<div class="content" style="background-color: #ffc107;">
				<div class="container-fluid">
<!---------------------------------------------------------------------------->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-md-12"> -->
<!--                             <div class="card "> -->
<!--                                 <div class="card-header "> -->
<!--                                     <h4 class="card-title">使用者縣市分布圖</h4> -->
<!--                                     <p class="card-category">使用者與地區依賴性</p> -->
<!--                                 </div> -->
<!--                                 <div class="card-body "> -->
<!--                                     <div id="userAndarea" class="ct-chart "></div> -->
<!--                                     <div class="legend"> -->
<!--                                         <i class="fa fa-circle text-info"></i> 人數 -->
<!--                                     </div> -->
<!--                                     <hr> -->
<!--                                     <div class="stats"> -->
<!--                                         <i class="fa fa-clock-o"></i> 近期更新時間... -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </div>						 -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-md-12"> -->
<!--                             <div class="card "> -->
<!--                                 <div class="card-header "> -->
<!--                                     <h4 class="card-title">新會員增長率</h4> -->
<!--                                     <p class="card-category">新會員增長率By月份</p> -->
<!--                                 </div> -->
<!--                                 <div class="card-body "> -->
<!--                                     <div id="newAccount" class="ct-chart "></div> -->
<!-- 	                                    <div class="legend"> -->
<!-- 	                                        <i class="fa fa-circle text-info"></i> 增長人數 -->
<!-- 	                                    </div> -->
<!--                                     <hr> -->
<!--                                     <div class="stats"> -->
<!--                                         <i class="fa fa-clock-o"></i> 近期更新時間... -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-md-6"> -->
<!--                             <div class="card "> -->
<!--                                 <div class="card-header "> -->
<!--                                     <h4 class="card-title">使用者性別比率</h4> -->
<!--                                     <p class="card-category">使用者性別比率</p> -->
<!--                                 </div> -->
<!--                                 <div class="card-body "> -->
<!--                                     <div id="gender" class="ct-chart "></div> -->
<!--                                     <hr> -->
<!--                                     <div class="stats"> -->
<!--                                         <i class="fa fa-clock-o"></i> 近期更新時間... -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </div> -->
<!-- 						</div> -->
<!-- 						<div class="col-md-6"> -->
<!--                             <div class="card "> -->
<!--                                 <div class="card-header "> -->
<!--                                     <h4 class="card-title">年齡x平均花費金額</h4> -->
<!--                                     <p class="card-category">年齡與平均花費金額關係</p> -->
<!--                                 </div> -->
<!--                                 <div class="card-body "> -->
<!--                                     <div id="ageAndCost" class="ct-chart "></div> -->
<!--                                     <div class="legend"> -->
<!--                                         <i class="fa fa-circle text-info"></i> 花費金額 -->
<!--                                     </div> -->
<!--                                     <hr> -->
<!--                                     <div class="stats"> -->
<!--                                         <i class="fa fa-clock-o"></i> 近期更新時間... -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </div> -->
<!-- 						</div> -->
<!-- 					</div> -->
				</div>
			</div>
<!--           		footer 				  -->
		<%@include file = "dashborad-footer.jsp" %>
    </div>
</body>
<!--   Core JS Files   -->
<script src="js/jquery.3.2.1.min.js" type="text/javascript"></script>
<script src="js/popper.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!--  Plugin for Switches, full documentation here: http://www.jque.re/plugins/version3/bootstrap.switch/ -->
<script src="js/bootstrap-switch.js"></script>
<!--  Chartist Plugin  -->
<script src="js/chartist.min.js"></script>
<!--  Notifications Plugin    -->
<script src="js/bootstrap-notify.js"></script>
<!-- Control Center for Light Bootstrap Dashboard: scripts for the example pages etc -->
<script src="js/light-bootstrap-dashboard.js?v=2.0.0 " type="text/javascript"></script>
<!-- Light Bootstrap Dashboard DEMO methods, don't include it in your project! -->
<!-- <script src="js/demo.js"></script> -->
</html>