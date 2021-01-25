<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>全商品分析</title>
	<!-- Google Icon -->
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
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
					<div class="container" style="margin-top: 20px;background-color: #FFF;">
						<input type="hidden" id="space" value="${pageContext.request.contextPath}" />
						<input type="hidden" id="pageNo" value="1" />
						<input type="hidden" id="maxPage" value="1" />
						<form method="post" >
							<fieldset>
								<legend>搜尋選項</legend>
								<input type="hidden" name="userLv" id="userLv"
									value=<c:out value="${userFullData.accountLv.lv}"></c:out> />
								<input type="hidden" name="userAccount" id="userAccount"
									value=<c:out value="${userFullData.account}"></c:out> />
								<hr />
								<label>依名稱：</label> <input type="text" name="product"
									id="product" size="60" maxlength="60" onblur="checkProduct()"
									placeholder="輸入品名" /> 
								<span id="productSpan"></span>
								<hr />
								<label>依店家：</label> <input type="text" name="store"
									id="store" size="60" maxlength="50" onblur="checkStore()"
									placeholder="輸入店名" /> 
								<span id="storeSpan"></span>
								<hr />
								<label>依價格：</label> <input type="text" name="price"
									id="price" size="10" maxlength="10" onblur="checkPriceStore()"
									placeholder="輸入價格" /> 
								<span id="priceSpan"></span>
								<label>依數量：</label> <input type="text" name="quantity"
									id="quantity" size="10" maxlength="10" onblur="checkQuantity()"
									placeholder="輸入數量" /> 
								<span id="quantitySpan"></span>
								<hr />
								<c:if test='${userFullData.accountLv.lv == -1}'>
									<label>依狀態：</label>
									<select name="selectedStatus" id="selectedStatus" onblur="checkStatus()">
										<option value="">請選擇要查詢的狀態</option>
										<option value="3">已移除</option>
										<option value="1">上架中</option>
										<option value="0">已下架</option>
									</select>
									<span id="statusSpan"></span>
								</c:if>
								<hr />
							</fieldset>
							<div align="center">
								<label>顯示筆數：</label>
								<select id="avPage" onblur="specSearch()">
									<option value="3" label="3">
									<option value="5" label="5">
									<option value="10" label="10">
									<option value="20" label="20">
								</select>
								<a href="adminBack">
									<button type="button" id="back" name="back" style="font-size:18px" >返回 <i class="material-icons" style="font-size:18px;color:green">undo</i></button>
								</a> 
								<button type="button" id="search" name="select" style="font-size:18px" >執行查詢 <i class="material-icons" style="font-size:18px;color:green">search</i></button>
								<button type="button" style="font-size:18px" onclick="clearMessage()">重設條件 <i class="material-icons" style="font-size:18px;color:blue">refresh</i></button>
							</div>
						</form>
						<div align="center">
							<span id="searchSpan"></span>
							<hr />
						</div>
						
						<div align="center" id="dataContainer"></div>
						
						<!--引用本地jQuery -->
						<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
						<!-- 引用本頁檢查用js -->
						<script src="${pageContext.request.contextPath}/js/ProductSearchForm.js"></script>
						<script>
							window.onload = function() {
								/* 載入後先執行一次預設查詢 */
								selectAllProduct();
								/* 綁定刪除按鈕 */
								$("#dataContainer").on("click", ".deleteBtn", function() {
									let selectedDelBtnInfo = this.id.substring(6);
									var id = selectedDelBtnInfo.split("_")[0];
									var status = selectedDelBtnInfo.split("_")[1];
									var mode = 'delete';
									lastCheck(id, status, mode);
								});
								/* 綁定上架按鈕 */
								$("#dataContainer").on("click", ".activeBtn", function() {
									let selectedActBtnInfo = this.id.substring(6);
									var id = selectedActBtnInfo.split("_")[0];
									var status = selectedActBtnInfo.split("_")[1];
									var mode = 'active';
									lastCheck(id, status, mode);
								});
								/* 綁定下架按鈕 */
								$("#dataContainer").on("click", ".quitBtn", function() {
									let selectedQutBtnInfo = this.id.substring(6);
									var id = selectedQutBtnInfo.split("_")[0];
									var status = selectedQutBtnInfo.split("_")[1];
									var mode = 'quit';
									lastCheck(id, status, mode);
								});
								/* 綁定第一頁按鈕 */
								/* 綁定上一頁按鈕 */
								/* 綁定下一頁按鈕 */
								/* 綁定最末頁按鈕 */
								
							};
							
							$("#search").click(function() {
								specSearch();
					    	});
							
							function specSearch() {
								document.getElementById("pageNo").value = 1;
								document.getElementById("maxPage").value = 1;
								
							}
							
							function lastCheck(id, status, mode) {
								let choice=confirm("是否要執行特定的操作？");
								if (choice) {
									let operateResultSpan = document.getElementById("searchSpan");
									let operateResultStr = "...處理中，請稍後";
									let operateResultIsOk = true;
									
									operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + operateResultStr;
									operateResultSpan.style.color = "black";
									operateResultSpan.style.fontStyle = "normal";
									
									
								}
							}
							
							function selectProduct() {
								
							}
							
							function selectAllProduct() {
								
							}
						</script>
					</div>
<!-- 					<div class="row"> -->
<!-- 						<div class="col-md-12"> -->
<!--                             <div class="card "> -->
<!--                                 <div class="card-header "> -->
<!--                                     <h4 class="card-title">使用者縣市分布圖</h4> -->
<!--                                     <p class="card-category">使用者與地區依賴性</p> -->
<!--                                 </div> -->
<!--                                 <div class="card-body "> -->
<!--                                     <div id="userAndarea" class="ct-chart "></div> -->
<!--                                     <div class="legend"> -->
<!--                                         <i class="fa fa-circle text-info"></i> 人數 -->
<!--                                     </div> -->
<!--                                     <hr> -->
<!--                                     <div class="stats"> -->
<!--                                         <i class="fa fa-clock-o"></i> 近期更新時間... -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </div>						 -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-md-12"> -->
<!--                             <div class="card "> -->
<!--                                 <div class="card-header "> -->
<!--                                     <h4 class="card-title">新會員增長率</h4> -->
<!--                                     <p class="card-category">新會員增長率By月份</p> -->
<!--                                 </div> -->
<!--                                 <div class="card-body "> -->
<!--                                     <div id="newAccount" class="ct-chart "></div> -->
<!-- 	                                    <div class="legend"> -->
<!-- 	                                        <i class="fa fa-circle text-info"></i> 增長人數 -->
<!-- 	                                    </div> -->
<!--                                     <hr> -->
<!--                                     <div class="stats"> -->
<!--                                         <i class="fa fa-clock-o"></i> 近期更新時間... -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 					<div class="row"> -->
<!-- 						<div class="col-md-6"> -->
<!--                             <div class="card "> -->
<!--                                 <div class="card-header "> -->
<!--                                     <h4 class="card-title">使用者性別比率</h4> -->
<!--                                     <p class="card-category">使用者性別比率</p> -->
<!--                                 </div> -->
<!--                                 <div class="card-body "> -->
<!--                                     <div id="gender" class="ct-chart "></div> -->
<!--                                     <hr> -->
<!--                                     <div class="stats"> -->
<!--                                         <i class="fa fa-clock-o"></i> 近期更新時間... -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </div> -->
<!-- 						</div> -->
<!-- 						<div class="col-md-6"> -->
<!--                             <div class="card "> -->
<!--                                 <div class="card-header "> -->
<!--                                     <h4 class="card-title">年齡x平均花費金額</h4> -->
<!--                                     <p class="card-category">年齡與平均花費金額關係</p> -->
<!--                                 </div> -->
<!--                                 <div class="card-body "> -->
<!--                                     <div id="ageAndCost" class="ct-chart "></div> -->
<!--                                     <div class="legend"> -->
<!--                                         <i class="fa fa-circle text-info"></i> 花費金額 -->
<!--                                     </div> -->
<!--                                     <hr> -->
<!--                                     <div class="stats"> -->
<!--                                         <i class="fa fa-clock-o"></i> 近期更新時間... -->
<!--                                     </div> -->
<!--                                 </div> -->
<!--                             </div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!---------------------------------------------------------------------------->				
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