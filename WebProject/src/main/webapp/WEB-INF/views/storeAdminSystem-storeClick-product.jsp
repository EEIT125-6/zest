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
</head>
<body>
    <div class="wrapper">
		<%@include file = "storeAdminSystem-side-header.jsp" %>
			<div class="content">
				<div class="container-fluid">
					<div class="row">
						<div class="col-1"></div>
						<div class="col-10">
							<div class="row">
					<c:forEach var="row1" items="${Products}">
						
						<div class="col-sm-4" id="p${row1.product_id}">
<!-- 					       style="background: url('Images/LOGO1-removebg-preview.png')" -->
								    <div class="card" style="background:#f28633;">
								    <c:if test="${row1.product_picture != null}">
								    	<div class="imgBx">
				             				<img src="${pageContext.request.contextPath}/123/${row1.product_picture}" style="border-radius: 7 px;"/>
				             			</div>	
				             		</c:if>
				             		<c:if test="${row1.product_picture == null }">
								        <div class="imgBx" >
				    	         			<img src="${pageContext.request.contextPath}/Images/LOGO1-removebg-preview.png" style="border-radius: 7 px;"/>
								        </div>
				             		</c:if>
								        <div class="contentBx">
								            <h3>${row1.product_name} </h3>
								            <h2 class="price">$${row1.product_price}</h2>
<!-- 								            <a href="#" class="buy">Buy Now</a> -->
								        </div>
								    </div>
<%-- 				             		${row1.product_name}  --%>
<%-- 				             		${row1.product_price} --%>
<%-- 				             		${row1.product_quantity} --%>
<%-- 				             		<c:if test="${row1.product_picture != null}"> --%>
<%-- 				             			<img src="${row1.product_picture}" style="width:50px;height:50px"/>	 --%>
<%-- 				             		</c:if> --%>
<%-- 				             		<c:url value='/updateProductpage'/> --%>

<%-- 							<c:if test="${userFullData.userId == userId && userId != null}"> --%>
							<div style="margin-top: 2px">
							<form action="<c:url value='/updateProductpage'/>" method="post" style="display:inline;margin-right: 1px">
								<input type="hidden" name="id" value="${id}">
								<input type="hidden" name="productid" value="${row1.product_id}">
								<input type="submit" value="修改商品"  class="btn btn-warning"> 
<!-- 								style="margin:0;padding:0;border:none;outline:none;color:rgb(38, 102, 240)"> -->
							</form>
							
<!-- 							<span>|</span> -->
<%-- 							<c:url value='/deleteProductpage'/> --%>
<%-- 							<form action="<c:url value='/deleteProductpage'/>" method="post" style="display:inline"> --%>
<%-- 								<input type="hidden" name="id" value="${id}"> --%>
<%-- 								<input type="hidden" name="productid" value="${row1.product_id}"> --%>
<!-- 								<input type="submit" value="刪除商品" class="btn btn-danger">  -->
<!-- 							</form> -->
								<input type = "button" value="移除商品" id="bt${row1.product_id}" class="btn btn-danger removeProductBt">
							<c:choose>
								<c:when test="${row1.product_status == 1}">
									<input class="btn btn-danger" type = "button" value="下架商品" id="d${row1.product_id}" 							data-toggle="modal" data-target="#myDR1${row1.product_id}"/>
									<input class="btn btn-success" type = "button" value="重新上架"  id="r${row1.product_id}" style="display:none"	data-toggle="modal" data-target="#myDR2${row1.product_id}"/> 
								</c:when>
								<c:when	test="${row1.product_status == 0}">
									<input class="btn btn-danger" type = "button" value="下架商品" id="d${row1.product_id}"  style="display:none"	data-toggle="modal" data-target="#myDR1${row1.product_id}"/>
									<input class="btn btn-success" type = "button" value="重新上架"  id="r${row1.product_id}"						data-toggle="modal" data-target="#myDR2${row1.product_id}"/>
								</c:when>
							</c:choose>
							</div>
<%-- 						</c:if> --%>
				             		<br>
			             </div>
<!-- ----------------------------------------下架MODEL----- -->
<div class="modal fade" id="myDR1${row1.product_id}" role="dialog">
	<div class="modal-dialog modal-sm">
    	<div class="modal-content">
        	<div class="modal-header">
          		<h4 class="modal-title">下架商品</h4>
	          		<button type="button" class="close" data-dismiss="modal">&times;</button>
        	</div>
        	<div class="modal-body">
         	 	<p>您確定要下架商品嗎</p>
        	</div>
        	<div class="modal-footer">
<!--         		<button type="button" class="btn btn-secondary" data-dismiss="modal" >Close</button> -->
				<input class="pds btn btn-danger" type = "button" value="確定下架" id="s${row1.product_id}" data-dismiss="modal"/>
	        </div>
      </div>
    </div>
</div>
<!-- ----------------------------------------下架MODEL----- -->
<!-- ----------------------------------------上架Model----------------------------			 -->
<div class="modal fade" id="myDR2${row1.product_id}" role="dialog">
	<div class="modal-dialog modal-sm">
    	<div class="modal-content">
        	<div class="modal-header">
          		<h4 class="modal-title">重新上架商品</h4>
	          		<button type="button" class="close" data-dismiss="modal">&times;</button>
        	</div>
        	<div class="modal-body">
         	 	<p>您確定要重新上架商品嗎</p>
        	</div>
        	<div class="modal-footer">
        	
				<input class="pds btn btn-success" type = "button" value="重新上架" id="h${row1.product_id}" data-dismiss="modal"/> 
	        </div>
      </div>
    </div>
</div>
<!-- ----------------------------------------上架Model----------------------------			 -->
		          </c:forEach>
		            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
		            <script type="text/javascript">
		            	$(".removeProductBt").click(function(){
							var productId = $(this).attr("id").substring(2);
							var disCard = '#p'+$(this).attr("id").substring(2);
							$.ajax({
								type:"post",
								url:"<c:url value = '/productRemove'/>",
								data:{
									'productId':productId
								},success:function(data){
									$(disCard).css('display','none');
								}
							})
		            	})
		            </script>
		         <script type="text/javascript">
		         	$(".pds").click(function(){
		         		var otherId = $(this).attr("id").substr(1);
		         		var dv = '#d'+otherId;
		         		var rv = '#r'+otherId;
		         		var did = 's'+otherId;
		         		var rid = 'h'+otherId;
		         		if($(this).attr("id")==did){
			         		$(dv).toggle();
			         		$(rv).toggle();
			         		console.log($(this).attr("id"));
			         		$.ajax({
			         			type:"Post",
			         			url:'<c:url value="/productOffShelfAjax"/>',
			         			data:{
			         				'productId':otherId
			         			},
			         			success:function(data){
			         				console.log("success OFFshelf");
			         			}
			         		})
		         		}else if($(this).attr("id")==rid){
			         		$(dv).toggle();
			         		$(rv).toggle();
			         		console.log($(this).attr("id"));
			         		$.ajax({
			         			type:"Post",
			         			url:'<c:url value="/productReOnShelfAjax"/>',
			         			data:{
			         				'productId':otherId
			         			},
			         			success:function(data){
			         				console.log("success ONshelf");
			         			}
			         		})
		         		}
		         	})
		         	
		         </script>
							</div>
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