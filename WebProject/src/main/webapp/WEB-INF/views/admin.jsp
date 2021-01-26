<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>管理員＿訂單管理</title>
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
			<div class="content" style="background-color: #F0F0F0;">
				<div class="container-fluid">
<!---------------------------------------------------------------------------->
					<div class="container" align="center" style="margin-top: 20px;background-color:#FFF">
						<h2><i>所有會員訂位資訊 :</i></h2>
						<div id="aa"></div>
							<script>
								window.onload = function() {
									/* 載入後先執行一次預設查詢 */
									showAll();
								};
							 	function showAll(){
									var aa=document.getElementById("aa");
									$.ajax({
										type : "POST",
										url : "<c:url value='/booking/admin'/>",
										dataType : "json",
										success : function(resultObj) {
											let booking=resultObj.data;	
											let storeName=resultObj.store;	
											let content="";
											if (booking !=null){
									      		content +="<table cellspacing='1' cellpadding='1' border='1' width='880px' style='border:8px #FFD382 groove;'>"
									      				+"<tr><th>訂單編號</th><th>訂位狀態"
							// 		      				+"<select id='status' onChange='status()'><option value=''>訂位狀態</option><option value='有效'>有效</option><option value='用餐過'>用餐過</option><option value='已取消'>已取消</option></select></th>"
									      				+"<th><select id='eat' onChange='eating()'><option value=''>餐廳名</option>";
									      		for(let i=0;i<storeName.length;i++){
									      			let storeItem = storeName[i];
								      				content +="<option value='"+storeItem+"'>"+storeItem+"</option>";
									      		}		
							      				content +="</select></th><th>訂位日期</th><th>時間</th><th>人數</th><th>帳號</th></tr>";
									      	for(let i=0;i<booking.length;i++){
									      		let data=booking[i];
									      			
									      		if (data.status==1){
									      			content+="<tr><td><a href=<c:url value='/booking/Display?key="+data.bookingNo+"'/>>"+data.bookingNo+"</a></td><td>有效</td>";
									      					
									      		}else if (data.status==0){
									      			content+="<tr><td><a href=<c:url value='/booking/Display?key="+data.bookingNo+"'/>>"+data.bookingNo+"</td><td>已取消</td>";
									      		}else{
													content+="<tr><td><a href=<c:url value='/booking/Display?key="+data.bookingNo+"'/>>"+data.bookingNo+"</td><td>用餐過</td>";
												}
									      		content+="<td>"+data.restaurant+"</td><td>"+data.bookingdate+"</td><th>"+data.time+"</td><td>"+data.number+"</td><td>"+data.user_id.account+"</td></tr>";
									      	}
									      	content+="</table>";
								      		aa.innerHTML=content;
								      		
									      	
									      	}else{
									      		aa.innerHTML="<h3>查無訂位資料</h3>";
									      		
									      	} 
									      	
										}
									
									}); 
								}
							 	function status(){
							 		var status=document.getElementById("status").value;
							 		if(status==""){
							 			showAll();
							 		}else{
							 			eating(status, null);
							 		}
							 	}
							 	function eating(){
							 		var eating=document.getElementById("eat").value;
							 		if(eating==""){
							 			showAll();	
							 		}else{
							 			eatingAjax(null, eating);
							 			
							 		}
							 	}
 	
								function eatingAjax(status,eating){
									$.ajax({
										type : "POST",
										url : "<c:url value='/booking/admin'/>",
										data : {
											'eating':eating,
											'status':status
										},
										dataType : "json",
										success : function(resultObj) {
											let booking=resultObj.data;	
											let storeName=resultObj.store;	
											let content="";
											if (booking !=null){
												content +="<table cellspacing='1' cellpadding='1' border='1' width='880px' style='border:8px #FFD382 groove;'>"
								      					+"<tr><th>訂單編號</th><th>訂位狀態"
							//		      				+"<select id='status' onChange='status()'><option value=''>訂位狀態</option><option value='有效'>有效</option><option value='用餐過'>用餐過</option><option value='已取消'>已取消</option></select></th>"
								      					+"<th><select id='eat' onChange='eating()'><option value=''>餐廳名</option>";
									      		for(let i=0;i<storeName.length;i++){
									      			let storeItem = storeName[i] + "";
								      				content +="<option value='"+storeItem+"'>"+storeItem+"</option>";
									      		}		
							      				content +="</select></th><th>訂位日期</th><th>時間</th><th>人數</th><th>userId</th></tr>";
									      	for(let i=0;i<booking.length;i++){
									      		let data=booking[i];
									      			
									      		if (data.status==1){
									      			content+="<tr><td><a href=<c:url value='/booking/Display?key="+data.bookingNo+"'/>>"+data.bookingNo+"</a></td><td>有效</td>";
									      					
									      		}else if (data.status==0){
									      			content+="<tr><td><a href=<c:url value='/booking/Display?key="+data.bookingNo+"'/>>"+data.bookingNo+"</td><td>已取消</td>";
									      		}else{
													content+="<tr><td><a href=<c:url value='/booking/Display?key="+data.bookingNo+"'/>>"+data.bookingNo+"</td><td>用餐過</td>";
												}
									      		content+="<td>"+data.restaurant+"</td><td>"+data.bookingdate+"</td><th>"+data.time+"</td><td>"+data.number+"</td><td>"+data.user_id.userId+"</td></tr>";
									      	}
									      	content+="</table>";
								      		aa.innerHTML=content;
								      		
									      	
									      	}else{
									      		aa.innerHTML="<h3>查無訂位資料</h3>";
									      		
									      	} 
									      	
										}
									
									}); 
							    }	
							</script>
							<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
							<script src="https://code.jquery.com/jquery.js"></script>
							<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>   
							<form action="<c:url value='/'/>" >
								<input type="submit" name="back" value="回首頁" style="margin-top:30px;">
							</form> 
						</div>   
<!---------------------------------------------------------------------------->					
				</div>
			</div>
<!--           		footer 				  -->
		<%@include file = "dashborad-footer.jsp" %>
    </div>
</body>
<!--   Core JS Files   -->
<!-- <script src="js/jquery.3.2.1.min.js" type="text/javascript"></script> -->
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
