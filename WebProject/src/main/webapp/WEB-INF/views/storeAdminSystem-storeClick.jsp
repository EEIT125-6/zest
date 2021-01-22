<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>商家管理</title>
	<!--     字體跟ICON     -->
	<link href="https://fonts.googleapis.com/css?family=Montserrat:400,700,200" rel="stylesheet" />
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css" rel="stylesheet" />
	 <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
    <!-- CSS Files -->
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/light-bootstrap-dashboard.css" rel="stylesheet" />	
</head>
<body>
    <div class="wrapper">
		<%@include file = "storeAdminSystem-side-header.jsp" %>
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class = "col-md-1">
						</div>
						<div class = "col-md-4"  style="padding:0px;border-radius: 15px 15px 15px 15px;background-color: #a5a58d;height: 200px;margin-bottom: 50px;">
			                <div class="h-100 " style="color:9e3f22;align-items: center;">
			                    <div style="line-height: 200px;text-align: center;font-size: 300%;">
			                    	<c:url value="/Update" var="updateStore">
			                    		<c:param name="id" value="${id}"></c:param>
			                    	</c:url>
				                    <a href="${updateStore}" style="text-decoration:none;">
			                        	<span style="color:black;font-family: 'Noto Sans TC', sans-serif;">
			                        	    	更新商家資料
			                        	</span>
			                        </a>
			                    </div>
			                </div>	
						</div>
						<div class = "col-md-2">
						</div>
						<div class = "col-md-4"  style="padding:0px;border-radius: 15px 15px 15px 15px;background-color: #a5a58d;height: 200px;margin-bottom: 50px;">
			                <div class="h-100 " style="color:9e3f22;align-items: center;">
			                    <div style="line-height: 200px;text-align: center;font-size: 300%;">
			                    	<c:url value="/storeAdProduct" var="updateStoreProduct">
			                    		<c:param name="id" value="${id}"></c:param>
			                    	</c:url>
				                    <a href="${updateStoreProduct}" style="text-decoration:none;">
			                        	<span style="color:black;font-family: 'Noto Sans TC', sans-serif;">
			                        	    	更新商家商品
			                        	</span>
			                        </a>
			                    </div>
			                </div>
						</div>
						<div class = "col-md-1">
						</div>
					</div>
					<div class="row">
						<div class = "col-md-1"></div>
						<div class = "col-md-4"  style="padding:0px;border-radius: 15px 15px 15px 15px;background-color: #a5a58d;height: 200px;margin-bottom: 50px;">
			                <div class="h-100 " style="color:9e3f22;align-items: center;">
			                    <div style="line-height: 200px;text-align: center;font-size: 300%;">
				                    <a href="<c:url value='#' />" style="text-decoration:none;">
			                        	<span style="color:black;font-family: 'Noto Sans TC', sans-serif;">
			                        	    	誰追蹤該商家
			                        	</span>
			                        </a>
			                    </div>
			                </div>							
						</div>
						<div class = "col-md-2"></div>
						<div class = "col-md-4"  style="padding:0px;border-radius: 15px 15px 15px 15px;background-color: #a5a58d;height: 200px;margin-bottom: 50px;">
			                <div class="h-100 " style="color:9e3f22;align-items: center;">
			                    <div style="line-height: 200px;text-align: center;font-size: 300%;">
				                    <a href="<c:url value='#' />" style="text-decoration:none;">
			                        	<span style="color:black;font-family: 'Noto Sans TC', sans-serif;">
			                        	    	商家訂位管理
			                        	</span>
			                        </a>
			                    </div>
			                </div>
						</div>
						<div class = "col-md-1"></div>
					</div>
					<div class="row">
						<div class = "col-md-1">
						</div>
						<div class = "col-md-4"  style="padding:0px;border-radius: 15px 15px 15px 15px;background-color: #a5a58d;height: 200px;margin-bottom: 50px;">
			                <div class="h-100 " style="color:9e3f22;align-items: center;">
			                    <div style="line-height: 200px;text-align: center;font-size: 300%;">
				                    <a href="<c:url value='#' />" style="text-decoration:none;">
			                        	<span style="color:black;font-family: 'Noto Sans TC', sans-serif;">
			                        	    	商家留言管理
			                        	</span>
			                        </a>
			                    </div>
			                </div>							
						</div>
						<div class = "col-md-2">
						</div>
						<div class = "col-md-4">
						</div>
						<div class = "col-md-1">
						</div>
					</div>
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