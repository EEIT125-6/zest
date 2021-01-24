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
    <c:forEach items="${locationChartList}" var="locationObject">
    	<input type="hidden" class="locationLabel" value="${locationObject.labelName}">
    	<input type="hidden" class="locationCount" value="${locationObject.labelNum}">
    </c:forEach>
    <c:forEach items="${genderChartList}" var="genderObject">
    	<input type="hidden" class="genderLabel" value="${genderObject.labelName}">
    	<input type="hidden" class="genderCount" value="${genderObject.labelNum}">
    </c:forEach>
    <c:forEach items="${joinDateChartList}" var="joinDateObject">
    	<input type="hidden" class="joinDateLabel" value="${joinDateObject.labelName}">
    	<input type="hidden" class="joinDateCount" value="${joinDateObject.labelNum}">
    </c:forEach>
		<%@include file = "dashborad-side-header.jsp" %>
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-md-12">
                            <div class="card ">
                                <div class="card-header ">
                                    <h4 class="card-title">使用者縣市分布圖</h4>
                                    <p class="card-category">使用者與地區依賴性</p>
                                </div>
                                <div class="card-body ">
                                    <div id="userAndarea" class="ct-chart "></div>
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> 人數
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
						<div class="col-md-12">
                            <div class="card ">
                                <div class="card-header ">
                                    <h4 class="card-title">新會員增長率</h4>
                                    <label>選擇年度</label>
                                    <select id="yearSelecter" onblur="demo.orderDashboard()">
                                   		<c:forEach items="${userYearList}" var="userYearObject">
                                   			<option value="${userYearObject}" label="${userYearObject}" />
                                   		</c:forEach>
                                   	</select>
                                    <p class="card-category">新會員增長率By月份</p>
                                </div>
                                <div class="card-body ">
                                    <div id="newAccount" class="ct-chart "></div>
	                                    <div class="legend">
	                                        <i class="fa fa-circle text-info"></i> 增長人數
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
                                    <h4 class="card-title">使用者性別比率</h4>
                                    <p class="card-category">使用者性別比率</p>
                                </div>
                                <div class="card-body ">
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
						<div class="col-md-6">
                            <div class="card ">
                                <div class="card-header ">
                                    <h4 class="card-title">年齡x平均花費金額</h4>
                                    <p class="card-category">年齡與平均花費金額關係</p>
                                </div>
                                <div class="card-body ">
                                    <div id="ageAndCost" class="ct-chart "></div>
                                    <div class="legend">
                                        <i class="fa fa-circle text-info"></i> 花費金額
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
		var locationLabels = document.getElementsByClassName("locationLabel");
		var locationCounts = document.getElementsByClassName("locationCount");
		var locationLabelArray = [];
		var locationCountArray = [];
		for (let index = 0; index < locationLabels.length; index++) {
			locationLabelArray.push(locationLabels[index].value);
			locationCountArray.push(locationCounts[index].value);
		}
		<!--縣市分布chart-->
        var data = {
             labels: locationLabelArray,
             series: [
             	locationCountArray
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

        var chartActivity = Chartist.Bar('#userAndarea', data, options, responsiveOptions);
        <!--chart-->
        var joinDateLabels = document.getElementsByClassName("joinDateLabel");
 		var joinDateCounts = document.getElementsByClassName("joinDateCount");
 		var joinYear = document.getElementById("yearSelecter").value;
 		var joinDateLabelArray = [];
 		var joinDateCountArray = [];
 		for (let index = 0; index < joinDateLabels.length; index++) {
 			if (joinDateLabels[index].value.startsWith(joinYear)) {
 				let transMonth = "";
 				switch(joinDateLabels[index].value.substring(5)) {
 					case "DECEMBER":
 						transMonth = "12月";
 						break;
 					case "NOVEMBER":
 						transMonth = "11月";
 						break;
 					case "OCTOBER":
 						transMonth = "10月";
 						break;
 					case "SEPTEMBER":
 						transMonth = "9月";
 						break;
 					case "AUGUST":
	 					transMonth = "8月";
						break;
 					case "JULY":
	 					transMonth = "7月";
						break;
 					case "JUNE":
	 					transMonth = "6月";
						break;
 					case "MAY":
 						transMonth = "5月";
						break;
 					case "APRIL":
 						transMonth = "4月";
						break;
 					case "MARCH":
 						transMonth = "3月";
						break;
 					case "FEBRUARY":
 						transMonth = "2月";
						break;
 					case "JANUARY":
 						transMonth = "1月";
						break;
 				}
 				joinDateLabelArray.push(transMonth);
 	 			joinDateCountArray.push(joinDateCounts[index].value);
 	 			console.log("test:"+joinDateCounts[index].value);
 			}
 		} 
 		console.log(joinDateLabelArray);
 		console.log(joinDateCountArray);
        <!--加入時間chart-->
        var dataSales = {
             labels: joinDateLabelArray,
             series: [
            	 joinDateCountArray
             ]
         };

         var optionsSales = {
             lineSmooth: false,
             low: 0,
             high: 5,
             showArea: true,
             height: "245px",
             axisX: {
                 showGrid: false,
             },
             lineSmooth: Chartist.Interpolation.simple({
                 divisor: 3
             }),
             showLine: false,
             showPoint: false,
             fullWidth: false
         };

         var responsiveSales = [
             ['screen and (max-width: 640px)', {
                 axisX: {
                     labelInterpolationFnc: function(value) {
                         return value[0];
                     }
                 }
             }]
         ];

        var chartHours = Chartist.Line('#newAccount', dataSales, optionsSales, responsiveSales);
		<!--chart-->
		var genderLabels = document.getElementsByClassName("genderLabel");
		var genderCounts = document.getElementsByClassName("genderCount");
		var genderLabelArray = [];
		var genderCountArray = [];
		for (let index = 0; index < genderLabels.length; index++) {
			genderLabelArray.push(genderLabels[index].value);
			genderCountArray.push(genderCounts[index].value);
		}
		<!--性別分布chart-->
		Chartist.Pie('#gender',{
			labels:genderLabelArray,
			series:genderCountArray
		})
		 <!--年齡花費chart-->
         var dataSales = {
             labels: ['0~15', '15~30', '30~45', '45~60', '60~75', '75~90'],
             series: [
             	 [542, 443, 320, 780, 553, 453, 500]
             ]
         };

         var optionsSales = {
             lineSmooth: false,
             low: 0,
             high: 800,
             showArea: true,
             height: "245px",
             axisX: {
                 showGrid: false,
             },
             lineSmooth: Chartist.Interpolation.simple({
                 divisor: 3
             }),
             showLine: false,
             showPoint: false,
             fullWidth: false
         };

         var responsiveSales = [
             ['screen and (max-width: 640px)', {
                 axisX: {
                     labelInterpolationFnc: function(value) {
                         return value[0];
                     }
                 }
             }]
         ];

         var chartHours = Chartist.Line('#ageAndCost', dataSales, optionsSales, responsiveSales);
	}
}
</script>
</html>