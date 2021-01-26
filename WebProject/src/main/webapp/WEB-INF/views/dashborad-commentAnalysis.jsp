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
	 <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" data-integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" data-crossorigin="anonymous"/>
    <!-- CSS Files -->
    <link href="css/bootstrap.min.css" rel="stylesheet" />
    <link href="css/light-bootstrap-dashboard.css" rel="stylesheet" />	
</head>
<body>
    <div class="wrapper">
    <c:forEach items="${boardStarChartList}" var="boardStarObject">
    	<input type="hidden" class="boardStarLabel" value="${boardStarObject.labelName}">
    	<input type="hidden" class="boardStarCount" value="${boardStarObject.labelNum}">
    </c:forEach>
    <c:forEach items="${boardCountChartList}" var="boardCountObject">
    	<input type="hidden" class="boardCountLabel" value="${boardCountObject.labelName}">
    	<input type="hidden" class="boardCountCount" value="${boardCountObject.labelNum}">
    </c:forEach>
		<%@include file = "dashborad-side-header.jsp" %>
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-6">
                            <div class="card ">
                                <div class="card-header ">
                                    <h4 class="card-title">分類x評分分數</h4>
                                    <p class="card-category">分類與平均評分分數</p>
                                </div>
                                <div class="card-body ">
                                    <div id="CateAndStar" class="ct-chart "></div>
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> 平均評分數
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
                                    <h4 class="card-title">分類x留言數量</h4>
                                    <p class="card-category">分類與留言數量</p>
                                </div>
                                <div class="card-body ">
                                    <div id="CateAndCom" class="ct-chart "></div>
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> 留言數
                                    </div>
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
<script type="text/javascript">
$(document).ready(function() {
	demo.orderDashboard();
});

demo={
	orderDashboard:function(){
		<!--chart-->
		var boardStarLabels = document.getElementsByClassName("boardStarLabel");
		var boardStarCounts = document.getElementsByClassName("boardStarCount");
		var boardStarLabelArray = [];
		var boardStarCountArray = [];
		for (let index = 0; index < boardStarLabels.length; index++) {
			boardStarLabelArray.push(boardStarLabels[index].value);
			boardStarCountArray.push(boardStarCounts[index].value);
		}
		<!--boardStarChart-->
		var data = {
            labels:boardStarLabelArray,
            series: [
            	boardStarCountArray
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

        var chartActivity = Chartist.Bar('#CateAndStar', data, options, responsiveOptions);
        <!--chart-->
        var boardCountLabels = document.getElementsByClassName("boardCountLabel");
		var boardCountCounts = document.getElementsByClassName("boardCountCount");
		var boardCountLabelArray = [];
		var boardCountCountArray = [];
		for (let index = 0; index < boardCountLabels.length; index++) {
			boardCountLabelArray.push(boardCountLabels[index].value);
			boardCountCountArray.push(boardCountCounts[index].value);
		}
		<!--boardCountChart-->
        var data = {
             labels:boardCountLabelArray,
             series: [
            	 boardCountCountArray
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

         var chartActivity = Chartist.Bar('#CateAndCom', data, options, responsiveOptions);
	}
}
</script>
</html>