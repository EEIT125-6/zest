<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/ProductCard.css'  type="text/css" />
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
    
    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.css">
<!--     <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.css">  -->
	<style>
	.traceTableth{
		font-size: 150%;
		font-weight: bold;
		color:black;
	}
	</style>
</head>
<body>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.js"></script>
    <div class="wrapper">
		<%@include file = "storeAdminSystem-side-header.jsp" %>
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-1"></div>
						<div class="col-10">
							<div style="height: 150px"></div>
							<div style="height: 50px; text-align:center;" >${stname}的追蹤者</div>
							<table id="example" class="table table-striped table-bordered" style="width:100%">
							        <thead>
							            <tr>
							                <th style="font-size: 130%;font-weight: bold;color:black;">暱稱</th>
							                <th style="font-size: 130%;font-weight: bold;color:black;">性別</th>
							                <th style="font-size: 130%;font-weight: bold;color:black;">生日</th>
							                <th style="font-size: 130%;font-weight: bold;color:black;">偏好</th>
							                <th style="font-size: 130%;font-weight: bold;color:black;">推送</th>
							            </tr>
							        </thead>
							        <tbody>
							            <c:forEach var="row" items="${memberList}">
							            	<tr>
							            		<td>
							            			${row.nickname}
							            		</td>
							            		<td>
							            			${row.gender.genderText}
							            		</td>
							            		<td>
							            			${row.birth}
							            		</td>
							            		<td>
							            			${row.fervor}
							            		</td>
							            		<td>
							            			<form action="<c:url value='/storeAdTraceMail'/>" method="Post" >
							            				<input type="hidden" name="stId" value="${stId}">
							            				<input type="hidden" name="memberNickname" value="${row.nickname}">
							            				<input type="hidden" name="memberEmail" value="${row.email}">
							            				<input type="submit" value="推播" id="${row.userId}" class="btn btn-primary sendMail">
							            			</form>	
							            		</td>
							            	</tr>
							            </c:forEach>
							        </tbody>
							        <tfoot>
							            <tr>
							                <th>暱稱</th>
							                <th>性別</th>
							                <th>生日</th>
							                <th>偏好</th>
							                <th>推送</th>
							            </tr>
							        </tfoot>
							    </table>
								  <script>
// 								  $(document).ready(function() {
// 									  $.ajax({
										  
// 									  })
// 									});
								  </script>
						</div>
						<div class="col-1"></div>
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