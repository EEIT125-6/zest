<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>商家統計</title>
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
		<%@include file = "storeStatistics-side-header.jsp" %>
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-12">
                            <div class="card ">
                                <div class="card-header ">
                                    <h4 class="card-title">2021年 每月追蹤量</h4>
                                    <p class="card-category">追蹤量與月份</p>
                                </div>
                                <div class="card-body ">
                                    <div id="CateAndStar" class="ct-chart "></div>
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> 追蹤數
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
                                    <h4 class="card-title">追蹤性別比率</h4>
                                    <p class="card-category">追蹤性別比率</p>
                                </div>
                                <div class="card-body ">
                                	<div id = "gnull"></div>
                                    <div id="gender" class="ct-chart "></div>
<!--                                     <div class="legend"> -->
<!--                                         <i class="fa fa-circle text-info"></i> 單月花費 -->
<!--                                     </div> -->
                                    <hr>
                                    <div class="stats">
                                        <i class="fa fa-clock-o"></i> 近期更新時間...
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
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
<script type="text/javascript">
var monthdata;
var genderpie;
$(document).ready(function() {
	$.ajax({
		type:"GET",
		url:"<c:url value='/storeStMonth'/>",
		data:{
			"stId":${stId}
		},
		datatype:'json',
		success:function(data){
				monthdata = data.d1;
				genderpie = data.d2;
				console.log(monthdata);
				console.log(genderpie);
				if(genderpie[0]==0 && genderpie[1]==0){
					$('#CateAndStar').html('<h2  style="text-align: center">暫無資料</h2>');
					$('#gnull').html('<h2  style="text-align: center">暫無資料</h2>');
				}else{
					demo.orderDashboard();
				}
			}
	});
// 	$.ajax({
// 		type:"GET",
// 		url:"<c:url value='/storeStGender'/>",
// 		date:{
// 			"stId":${stId}
// 		},
// 		datatype:'json',
// 		success:function(data2){
// 			genderpie = data2;
// 			console.log(genderpie);
// 		}
// 	})
});

demo={
	orderDashboard:function(){
       		 var data = {
                labels: ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月'],
                series: [
//                 	[3.2, 4, 4.1, 3.5, 3.4, 4.5,3.2, 4, 4.1, 2, 1,9	]
					monthdata
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
                    seriesBarDistance: 20,
                    axisX: {
                        labelInterpolationFnc   : function(value) {
                            return value[0];
                        }
                    }
                }]
            ];

            var chartActivity = Chartist.Bar('#CateAndStar', data, options, responsiveOptions);
		
// 		-----------------------------------------------------
		if(genderpie[0]==0){
			Chartist.Pie('#gender',{	
 				labels:['男', '女100%' ],
//  				series: [20, 18]  //測試用數字
 				series: genderpie	
 			 })				
		}else if(genderpie[1]==0){
			Chartist.Pie('#gender',{	
 				labels:['男100%', '女' ],
//  				series: [20, 18]  //測試用數字
 				series: genderpie	
 			 })
// 		}else if(genderpie[0]==0 && genderpie[1]==0){
// 			$('#gnull').html('<span>暫無資料</span>');
		}else{
   			 Chartist.Pie('#gender',{	
 				labels:['男', '女' ],
//  				series: [20, 18]  //測試用數字
 				series: genderpie	
 			 })				
		}
            
//             -----------------------------------
	}
}
</script>
</html>