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

	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/ProductCard.css'  type="text/css" />
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
    
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.min.css">
<!--     <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.23/css/jquery.dataTables.css">  -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
	<style>
/* 	table, th, td { */
/* 	  border: 1px solid black; */
/* 	  border-collapse: collapse; */
/* 	} */
/* 	th, td { */
/* 	  padding: 5px; */
/* 	  text-align: left;     */
/* 	} */
	</style>
</head>
<body>
    <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.js"></script>
    <div class="wrapper">
		<%@include file = "storeAdminSystem-side-header.jsp" %>
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-1"></div>
						<div class="col-10">
							<table id="trace" >
							 	    <thead>
								        <tr>
								        	<th style="width:50px;">編號</th>
								         	<th style="width:200px;">暱稱</th>
								         	<th style="width:200px;">操作</th> 
								        </tr>
								    </thead>
								    <tbody id="bookReportList">
								        <tr>
								        	<td>編號</td>
								        	<td>暱稱</td>
								        	<td><button type="button" class="btn btn-outline-primary" value = "brID">查看</button>
								        	  	<button type="button" class="btn btn-outline-danger ml-3" value = "brId">刪除</button>
								        	</td>
							 	        </tr>
									</tbody>
							</table> 
								  <script>
								  	$( "#trace" ).DataTable({
								  		$.ajax({
								  			type:"GET",
								  			url:"<c:url value='storeGetTraceMember'/>",
								  			dataSrc:""
								  		}),
								  		"columns": [
										      { "data": "memberId" }, //第一欄使用data中的name
										      { "data": "memberNickname" }, //第二欄使用data中的age
										      {
										    	  "data":"memberId",
										    	  "render":function(data,type,row,meta){
										    		  "<button type=\"button\" class=\"btn btn-outline-dark\" onclick='viewData(" + data + ")' value = \""+ data +"\">查看</button>" 			           
										    		  + "<button type=\"button\" class=\"btn btn-outline-primary ml-2\" onclick='editData(" + data + ")' value = \""+ data +"\">刪除</button>" 
										    	  }
										      }	
										],
								  		
								  		// 參數設定[註1]
								  		"bPaginate": false, // 顯示換頁
								  		"searching": false, // 顯示搜尋
								  		"info":	false, // 顯示資訊
								  		"fixedHeader": true, // 標題置頂
								  	    "language": {
								  	        "processing": "處理中...",
								  	        "loadingRecords": "載入中...",
								  	        "lengthMenu": "顯示 _MENU_ 項結果",
								  	        "zeroRecords": "沒有符合的結果",
								  	        "info": "顯示第 _START_ 至 _END_ 項結果，共 _TOTAL_ 項",
								  	        "infoEmpty": "顯示第 0 至 0 項結果，共 0 項",
								  	        "infoFiltered": "(從 _MAX_ 項結果中過濾)",
								  	        "infoPostFix": "",
								  	        "search": "搜尋:",
								  	        "paginate": {
								  	            "first": "第一頁",
								  	            "previous": "上一頁",
								  	            "next": "下一頁",
								  	            "last": "最後一頁"
								  	        },
								  	        "aria": {
								  	            "sortAscending": ": 升冪排列",
								  	            "sortDescending": ": 降冪排列"
								  	        }
								  	    }
								  	});
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