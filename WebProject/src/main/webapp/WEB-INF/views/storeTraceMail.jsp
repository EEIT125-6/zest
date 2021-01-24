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
    <link rel='stylesheet' href='${pageContext.request.contextPath}/css/zestform.css'  type="text/css" />
    
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
						    <div style="height: 15px;"></div>
						    <div class="container">
						        <div class="row">
						            <div class="col-2"></div>
						            <div class="col-8 containerHeaderCard">
						                <div class="h-100 containerHeaderFontCard">
						                    <div class="containerHeaderFontDiv">
						                       <span class="containerHeaderFontSpan">信件</span>
						                    </div>
						                </div>
						            </div>
						            <div class="col-2"></div>
						        </div>
						    </div>
    <div class="container" style="margin-bottom:15px;">
        <div class="row">
            <div class="col-2"></div>
            <div class="col-8 containerBodyCardOutside">
                <form action="<c:url value='/storeAdMailSend'/>" method="post">
                    <div class="form-row containerBodyCard"  style="top:calc(50% - 15px);">
                        <div class="col-md-4" style="text-align: center;">
                          <label for="validationServer01">收件人:</label>
                        </div> 
                        <div class="col-md-8" style="text-align: center;">
<!--                             <input type="text" class="form-control is-valid" id="validationServer01" value="Mark" required> -->
<!--                             <div class="valid-feedback"> -->
                                <span>${memberNickname}</span>
                                <input type="hidden" name="stId" value="${stId}"/>
                                <input type="hidden" name="memberEmail" value="${memberEmail}"/>
<!--                             </div> -->
                        </div>
                    </div>
                    <div class="form-row containerBodyCard" >
                        <div class="col-md-4" style="text-align: center;">
                            <label for="">主旨:</label>
                        </div>
                        <div class="col-md-8">
                            <input type="text" class="form-control" id="mailSubId" value=""  name="mailSub"required>
                        </div>
                    </div>
                    
                    <div class = "form-row containerBodyCard" >
                        <div class="col-md-4 " style="text-align: center;">  
							<label  for="storeItddt">內文</label>
                    	</div>
	                    <div class="col-md-8">
	                    	<textarea  class="form-control" id="mailTxtId" rows="5" name="mailContext"></textarea>
	                    </div>
                    </div>
                    
					<div class="form-row">
						<div class="col-md-4"></div>
						<div class="col-md-8" style="text-align: center;">
		                    <button class="btn btn-primary" type="submit" id="sendMail">送出</button>
						</div>
                    	<div class = "col-md-12" style="text-align: center; margin-top: 5px" id="oneBt">
                    		<button class="btn btn-success" >一鍵輸入</button>
                    	</div>
					</div>
                </form>
            </div>
        </div>
						<div class="col-1"></div>
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
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
$("#oneBt").click(function(){
	$("#mailSubId").val('感謝您的追蹤');
	$("#mailTxtId").val('感謝您追蹤203蘋果，後續將會不定期放送優惠訊息。');

})
document.getElementById("sendMail").addEventListener("click",function(){
  swal("成功送出信件!", "您成功送出信件!", "success");
});
</script>
</html>