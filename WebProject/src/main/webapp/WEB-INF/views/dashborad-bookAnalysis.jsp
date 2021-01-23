<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>使用者分析</title>
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
		<%@include file = "dashborad-side-header.jsp" %>
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-6">
                            <div class="card ">
                                <div class="card-header ">
                                    <h4 class="card-title">用餐目的x單數</h4>
                                    <p class="card-category">用餐目的和該單數的關係</p>
                                </div>
                                <div class="card-body ">
                                    <div id="a1" class="ct-chart "></div>
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> 單數
                                    </div>
                                    <hr>
                                    <div class="stats">
                                        <i class="fa fa-clock-o"></i> 近期更新時間...
                                    </div>
                                </div>
                            </div>
						</div>
						<div class="col-md-6">
                            <div class="card ">
                                <div class="card-header ">
                                    <h4 class="card-title">分類與訂位人數</h4>
                                    <p class="card-category">分類與訂位人數關係</p>
                                </div>
                                <div class="card-body ">
                                    <div id="a2" class="ct-chart "></div>
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> 訂位人數
                                    </div>
                                    <hr>
                                    <div class="stats">
                                        <i class="fa fa-clock-o"></i> 近期更新時間...
                                    </div>
                                </div>
                            </div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
                            <div class="card ">
                                <div class="card-header ">
                                    <h4 class="card-title">訂位系統使用率</h4>
                                    <p class="card-category">有使用過訂位系統的會員佔會員總數</p>
                                </div>
                                <div class="card-body ">
                                    <div id="a3" class="ct-chart "></div>
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> 有使用過訂位系統的會員
                                    </div>
                                    <hr>
                                    <div class="stats">
                                        <i class="fa fa-clock-o"></i> 近期更新時間...
                                    </div>
                                </div>
                            </div>
						</div>
						<div class="col-md-6">
<!--                             <div class="card "> -->
<!--                                 <div class="card-header "> -->
<!--                                     <h4 class="card-title">商家x訂位量</h4> -->
<!--                                     <p class="card-category">商家與訂位量關係</p> -->
<!--                                 </div> -->
<!--                                 <div class="card-body "> -->
<!--                                     <div id="monthAndCost" class="ct-chart "></div> -->
<!--                                     <div class="legend"> -->
<!--                                         <i class="fa fa-circle text-info"></i> 訂位量 -->
<!--                                     </div> -->
<!--                                     <hr> -->
<!--                                     <div class="stats"> -->
<!--                                         <i class="fa fa-clock-o"></i> 近期更新時間... -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </div> -->
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
<script type="text/javascript">
$(document).ready(function() {
		demo.orderDashboard();
});

demo={
		orderDashboard:function(){

	        var data = {
	                labels: ['Jan', 'Feb', '其他'],
	                series: [
	                    [542, 443, 320, 780]
	                ]
	            };

	            var options = {
	                seriesBarDistance: 10,
	                axisX: {
	                    showGrid: false
	                },
	                height: "245px"
	            };

	            var responsiveOptions = [
	                ['screen and (max-width: 640px)', {
	                    seriesBarDistance: 5,
	                    axisX: {
	                        labelInterpolationFnc   : function(value) {
	                            return value[0];
	                        }
	                    }
	                }]
	            ];

	            var chartActivity = Chartist.Bar('#a1', data, options, responsiveOptions);
		 
			
// 			-----------------

    		        var data = {
    		                labels: ['中式','日式','下午茶','西式','快餐','燒肉'],
    		                series: [
    		                    [542, 343, 220, 780, 453, 653]
    		                ]
    		            };

    		            var options = {
    		                seriesBarDistance: 10,
    		                axisX: {
    		                    showGrid: false
    		                },
    		                height: "245px"
    		            };

    		            var responsiveOptions = [
    		                ['screen and (max-width: 640px)', {
    		                    seriesBarDistance: 5,
    		                    axisX: {
    		                        labelInterpolationFnc   : function(value) {
    		                            return value[0];
    		                        }
    		                    }
    		                }]
    		            ];

    		            var chartActivity = Chartist.Bar('#a2', data, options, responsiveOptions);
    			 
			
// 			-----------------
			 Chartist.Pie('#a3',{
 				labels:['有使用','未使用'],
 				series: [20, 18]  //測試用數字
 			 })			
		}
}
</script>
</html>