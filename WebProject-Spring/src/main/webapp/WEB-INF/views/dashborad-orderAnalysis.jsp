<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>訂單分析</title>
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
                        <div class="col-md-12">
                            <div class="card ">
                                <div class="card-header ">
                                    <h4 class="card-title">月份x平均結帳金額</h4>
                                    <p class="card-category">月份與平均花費金額的關係</p>
                                </div>
                                <div class="card-body ">
                                    <div id="monthAndCost" class="ct-chart "></div>
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> 單月花費
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
                        <div class="col-md-9 container">
                            <div class="card ">
                                <div class="card-header ">
                                    <h4 class="card-title">購買分類比例</h4>
                                    <p class="card-category">最多人買的分類</p>
                                </div>
                                <div class="card-body ">
                                    <div id="cateCostPrc" class="ct-chart" >
                                    </div>
                                </div>
                                <div class="card-footer ">
<!--                                     <div class="legend"> -->
<!--                                         <i class="fa fa-circle text-info"></i> Tesla Model S -->
<!--                                         <i class="fa fa-circle text-danger"></i> BMW 5 Series -->
<!--                                     </div> -->
                                    <hr>
                                    <div class="stats">
                                        <i class="fa fa-check" ></i> 分析報告..
                                    </div>
                                </div>
                            </div>
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
        // Javascript method's body can be found in assets/js/demos.js
//         demo.initDashboardPageCharts();
			demo.orderDashboard();
        // demo.showNotification();

    });
    
    
    demo={
    		 orderDashboard:function(){

    		        var data = {
    		                labels: ['Jan', 'Feb', 'Mar', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'],
    		                series: [
    		                    [542, 443, 320, 780, 553, 453, 326, 434, 568, 610, 756, 895]
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

    		            var chartActivity = Chartist.Bar('#monthAndCost', data, options, responsiveOptions);
    			 
//     			 ----------------------
//     			 var options = {
//     					 donut: true,
//     					 donutWidth: 40,
//     					 startAngle: 0,
//     					 total:100,
//     					 showLabel: true,
//     					 axisX:{
//     						 showGrid:false
//     					 }
//     			 };
    			 
//     			 Chartist.Pie('#cateCostPrc', options);
	
    			 Chartist.Pie('#cateCostPrc',{
    				labels:['中式','日式','下午茶','西式','快餐','燒肉'],
    				series: [20, 18, 16, 22, 21, 19]  //測試用數字
    			 })			 
    		 }
    }
</script>
</html>