<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
response.setContentType("text/html;charset=UTF-8"); // 設定response編碼
response.setHeader("Cache-Control", "no-cache"); // HTTP 1.1
response.setHeader("Pragma", "no-cache"); // HTTP 1.0
response.setDateHeader("Expires", -1); // 防止proxy server進行快取
%>
<!-- taglib宣告 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- taglib宣告 -->
<!DOCTYPE html>
<html lang="en">
<head>
<%@include file="../Link_Meta-Include.jsp"%>
<!-- Google Icon -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<!-- dataTables用css -->
<!-- <link rel="https//cdn.datatables.net/1.10.22/css/jquery.dataTables.min.css"> -->
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/webUser/WebUserSearchForm.css">
<title>進行搜索</title>
<style>
.classimg {
	transition: 0.2s;
	width: 80px
}

.classimg:hover {
	transition: 0.2s;
	width: 85px
}

body {
	background-color: rgb(235, 159, 18);
}

.header {
	height: 100px;
	border-bottom: 3px solid #e76f51;
	height: 90px;
	padding-top: 5px;
	background-color: #003049
}

.photo {
	padding: 0%;
	background: url("Images/backbar2-1.jpg");
	height: 540px;
	padding-top: 220px;
	background-size: 100%
}

.shopcar {
	height: 40px;
	margin: 0;
	margin-left: 5px;
}

.wrapper {
	position: relative;
	width: 1000px;
	height: 400px;
	overflow: hidden;
	margin: 0 auto;
	border-radius: 5px;
}

ul {
	margin: 0;
	padding: 0;
	position: absolute;
}

li {
	margin: 0;
	padding: 0;
	list-style: none;
}

ul.slides {
	width: 4000px;
	left: 0px;
	transition: all .5s;
}

ul.slides li {
	width: 1000px;
	height: 400px;
	overflow: hidden;
	float: left;
}

ul.slides li img {
	width: 100%;
	height: 100%;
	object-fit: cover;
}

.dot {
	bottom: 10px;
	width: 100%;
	display: flex;
	justify-content: center;
}

.dot li {
	border: 1px solid #fff;
	margin: 0 5px;
	width: 24px;
	height: 10px;
}

.slide_btn {
	display: flex;
	justify-content: center;
	align-items: center;
	top: 0;
	bottom: 0;
	width: 30px;
	color: #fff;
	position: absolute;
	font-size: 24px;
}

#prevSlide {
	left: 0;
}

#nextSlide {
	right: 0;
}

.slide_btn i {
	color: rgba(255, 255, 255, .6);
	transition: .5s;
}

.slide_btn:hover i {
	color: rgba(255, 255, 255, 1);
}

#gotop {
	position: fixed;
	z-index: 90;
	right: 30px;
	bottom: 31px;
	display: none;
	width: 50px;
	height: 50px;
	color: #fff;
	background: #ddbe56;
	line-height: 50px;
	border-radius: 50%;
	transition: all 1.5s;
	text-align: center;
	box-shadow: 0 2px 5px 0 rgba(0, 0, 0, 0.16), 0 2px 10px 0
		rgba(0, 0, 0, 0.12);
}

#gotop :hover {
	background: #0099CC;
}
</style>
</head>
<body>
	<%@include file="../Header-Include.jsp"%>
	<!-- -------------------------------------------------------------- -->
	<div class="container" style="margin-top: 20px;">
		<c:if test="${userFullData.password == null}">
			<c:redirect url="/WebUserLogin" />
		</c:if>
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
				<c:if test="${operateMessage != null}">
					<p><c:out value="${operateMessage}" /></p>
				</c:if>
				<hr />
				<label>帳號名稱：</label> <input type="text" name="selectedAccount"
					id="account" size="40" maxlength="20" onblur="checkAccountName()"
					placeholder="請輸入要查詢的帳號，8~20個字" /> 
				<span id="accountSpan"></span>
				<label>用戶暱稱：</label> <input type="text" name="selectedNickname"
					id="nickname" size="40" maxlength="20" onblur="checkNickname()"
					placeholder="請輸入要查詢的暱稱" /> 
				<span id="nicknameSpan"></span>
				<hr />
				<label>偏好食物：</label>
				<c:forEach items="${fervorList}" var="fervorObject">
						<input type="checkbox" name="fervorOption" class="fervor"
							value="${fervorObject.fervorCode}" onblur="checkFervor()" />
						<label>
							<c:out value="${fervorObject.fervorItem}">
							</c:out>
						</label>
				</c:forEach>
				<span id="fervorSpan"></span>
				<hr />
				<label>居住區域：</label> 
				<select name="selectedLocationCode"
					id="locationCode" onblur="checkLocationCode()">
					<option value="">請選擇目前您居住/生活的區域</option>
					<c:forEach items="${cityInfoList}" var="cityInfo">
						<option value="${cityInfo.cityCode}" label="${cityInfo.cityName}" />
					</c:forEach>
				</select> 
				<span id="locationCodeSpan"></span>
				<c:if test='${userFullData.accountLv.lv == -1}'>
					<label>帳號狀態：</label>
					<select name="selectedStatus" id="status" onblur="checkStatus()">
						<option value="">請選擇要查詢的狀態</option>
						<option value="active">已啟用</option>
						<option value="inactive">未啟用</option>
						<option value="quit">已停用</option>
					</select>
					<label>帳號身分：</label>
					<select name="selectedIdentity" id="identity" onblur="checkIdentity()">
						<option value="">請選擇要查詢的身分</option>
						<c:forEach items="${identityList}" var="userType">
							<option value="${userType.lv}" label="${userType.levelName}" />
						</c:forEach>
					</select>
					<span id="statusSpan"></span>
					<span id="identitySpan"></span>
				</c:if>
				<hr />
			</fieldset>
			<div align="center">
				<a href="WebUserMain">
				<button type="button" id="back" name="back" style="font-size:18px" >返回 <i class="material-icons" style="font-size:18px;color:green">undo</i></button>
				</a> 
				<button type="button" id="search" name="select" style="font-size:18px" onclick="clearMessage()">執行查詢 <i class="material-icons" style="font-size:18px;color:green">search</i></button>
				<button type="button" style="font-size:18px" onclick="clearMessage()">重設條件 <i class="material-icons" style="font-size:18px;color:blue">refresh</i></button>
				<c:if test="${userFullData.accountLv.lv == -1}" >
					<a href="WebUserAddForm"><button type="button" id="adminAdd" name="adminAdd" style="font-size:18px" onclick="clearMessage()">新增帳號 <i class="material-icons" style="font-size:18px;color:green">add</i></button></a>
				</c:if>
			</div>
			<hr />
		</form>
		<div align="center">
			<span id="searchSpan"></span>
		</div>
		
<!-- 		<div align="center" id="dataTableContainer">  -->
<!-- 			<table id="userDataTable" class="display"> -->
<!-- 				<thead> -->
<!-- 					<tr> -->
<!--  						<th>項次</th>  -->
<%--  						<c:if test="${userFullData.accountLv.lv == -1}">  --%>
<!--  							<th>刪除</th> -->
<!--  							<th>其他</th>  -->
<!--  							<th>查看</th>  -->
<%-- 						</c:if>  --%>
<!--  						<th>帳號名稱</th>  -->
<!--  						<th>稱呼</th>  -->
<!-- 						<th>偏好食物</th>  -->
<!-- 						<th>居住區域</th>  -->
<%--  						<c:if test="${userFullData.accountLv.lv == -1 || userFullData.accountLv.lv == 1}">  --%>
<!--  							<th>帳號身分</th>  -->
<%--  						</c:if>  --%>
<%--  						<c:if test="${userFullData.accountLv.lv == -1}">  --%>
<!--  							<th>帳號狀態</th>  -->
<%--  						</c:if>  --%>
<!-- 					</tr> -->
<!-- 				</thead> -->
<!-- 				<tbody> -->
					
<!-- 				</tbody> -->
<!-- 			</table> -->
<!-- 		</div> -->
		
		<div align="center" id="dataContainer"></div>
		
		<!-- 引用本地jQuery -->
		<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
		<!-- 引用dataTables.js -->
<!-- 		<script src ="https://cdn.datatables.net/1.10.22/js/jquery.dataTables.min.js"></script> -->
		<!-- 引用本頁檢查用js -->
		<script src="${pageContext.request.contextPath}/js/webUser/WebUserSearchForm.js"></script>
		<script>
			window.onload = function() {
				/* 載入後先執行一次預設查詢 */
				selectAllUser();
				/* 綁定刪除按鈕 */
				$("#dataContainer").on("click", ".deleteBtn", function() {
					let selectedDelBtnInfo = this.id.substring(6);
					var userId = selectedDelBtnInfo.split("_")[0];
					var account = selectedDelBtnInfo.split("_")[1];
					var status = selectedDelBtnInfo.split("_")[2];
					var mode = 'delete';
					lastCheck(userId, account, status, mode);
				});
				/* 綁定啟用按鈕 */
				$("#dataContainer").on("click", ".activeBtn", function() {
					let selectedActBtnInfo = this.id.substring(6);
					var userId = selectedActBtnInfo.split("_")[0];
					var account = selectedActBtnInfo.split("_")[1];
					var status = selectedActBtnInfo.split("_")[2];
					var mode = 'active';
					lastCheck(userId, account, status, mode);
				});
				/* 綁定停用按鈕 */
				$("#dataContainer").on("click", ".quitBtn", function() {
					let selectedQutBtnInfo = this.id.substring(6);
					var userId = selectedQutBtnInfo.split("_")[0];
					var account = selectedQutBtnInfo.split("_")[1];
					var status = selectedQutBtnInfo.split("_")[2];
					var mode = 'quit';
					lastCheck(userId, account, status, mode);
				});
				/* 綁定第一頁按鈕 */
				$("#dataContainer").on("click", ".pFirst", function() {
					let startPage = parseInt(document.getElementById("pageNo").value);
					document.getElementById("pageNo").value = 1;
					selectAllUser();
				});
				$("#dataContainer").on("click", ".pFirstBtn", function() {
					var accountObjValue = document.getElementById("account").value.trim();
					var nicknameObjValue = document.getElementById("nickname").value.trim();
					var fervorObj = document.getElementsByClassName("fervor");
					var fervorObjValue = "";
					
					for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
						fervorObjValue += (fervorObjValue != "" && fervorObj[fervorIndex].checked) ? "," : "";
						fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
					}
					var locationCodeObjValue = document.getElementById("locationCode").value;
					var selectedStatus = (userLv == -1) ? document.getElementById("status").value : "";
					var selectedIdentity = (userLv == -1) ? document.getElementById("identity").value : "";
					
					let startPage = parseInt(document.getElementById("pageNo").value);
					document.getElementById("pageNo").value = 1;
					selectUser(accountObjValue, nicknameObjValue, fervorObjValue, locationCodeObjValue, selectedStatus, selectedIdentity);
				});
				/* 綁定上一頁按鈕 */
				$("#dataContainer").on("click", ".pPrev", function() {
					let startPage = parseInt(document.getElementById("pageNo").value);
					document.getElementById("pageNo").value = (startPage > 1) ? startPage - 1 : 1;
					selectAllUser();
				});
				$("#dataContainer").on("click", ".pPrevBtn", function() {
					var accountObjValue = document.getElementById("account").value.trim();
					var nicknameObjValue = document.getElementById("nickname").value.trim();
					var fervorObj = document.getElementsByClassName("fervor");
					var fervorObjValue = "";
					
					for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
						fervorObjValue += (fervorObjValue != "" && fervorObj[fervorIndex].checked) ? "," : "";
						fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
					}
					var locationCodeObjValue = document.getElementById("locationCode").value;
					var selectedStatus = (userLv == -1) ? document.getElementById("status").value : "";
					var selectedIdentity = (userLv == -1) ? document.getElementById("identity").value : "";
					
					let startPage = parseInt(document.getElementById("pageNo").value);
					document.getElementById("pageNo").value = (startPage > 1) ? startPage - 1 : 1;
					selectUser(accountObjValue, nicknameObjValue, fervorObjValue, locationCodeObjValue, selectedStatus, selectedIdentity);
				});
				/* 綁定下一頁按鈕 */
				$("#dataContainer").on("click", ".pNext", function() {
					let startPage = parseInt(document.getElementById("pageNo").value);
					document.getElementById("pageNo").value = startPage + 1;
					selectAllUser();
				});
				$("#dataContainer").on("click", ".pNextBtn", function() {
					var accountObjValue = document.getElementById("account").value.trim();
					var nicknameObjValue = document.getElementById("nickname").value.trim();
					var fervorObj = document.getElementsByClassName("fervor");
					var fervorObjValue = "";
					
					for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
						fervorObjValue += (fervorObjValue != "" && fervorObj[fervorIndex].checked) ? "," : "";
						fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
					}
					var locationCodeObjValue = document.getElementById("locationCode").value;
					var selectedStatus = (userLv == -1) ? document.getElementById("status").value : "";
					var selectedIdentity = (userLv == -1) ? document.getElementById("identity").value : "";
					
					let startPage = parseInt(document.getElementById("pageNo").value);
					document.getElementById("pageNo").value = startPage + 1;
					selectUser(accountObjValue, nicknameObjValue, fervorObjValue, locationCodeObjValue, selectedStatus, selectedIdentity);
				});
				/* 綁定最末頁按鈕 */
				$("#dataContainer").on("click", ".pLast", function() {
					let maxPage = parseInt(document.getElementById("maxPage").value);
					document.getElementById("pageNo").value = maxPage;
					selectAllUser();
				});
				$("#dataContainer").on("click", ".pLastBtn", function() {
					var accountObjValue = document.getElementById("account").value.trim();
					var nicknameObjValue = document.getElementById("nickname").value.trim();
					var fervorObj = document.getElementsByClassName("fervor");
					var fervorObjValue = "";
					
					for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
						fervorObjValue += (fervorObjValue != "" && fervorObj[fervorIndex].checked) ? "," : "";
						fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
					}
					var locationCodeObjValue = document.getElementById("locationCode").value;
					var selectedStatus = (userLv == -1) ? document.getElementById("status").value : "";
					var selectedIdentity = (userLv == -1) ? document.getElementById("identity").value : "";
					
					let maxPage = parseInt(document.getElementById("maxPage").value);
					document.getElementById("pageNo").value = maxPage;
					selectUser(accountObjValue, nicknameObjValue, fervorObjValue, locationCodeObjValue, selectedStatus, selectedIdentity);
				});
				
				/* dataTable測試區 */
// 				let table = $("#userDataTable").DataTable({
// 					processing: false, //關閉預設"顯示處理中"的效果
// 					searching: false, //關閉內建搜尋
// 					serverSide: true, //啟用ServerSide模式
// 					order: [[0, "asc"]], //預設排序(由第0個資料行、升覓排序)
// 					orderMulti: false, //關閉多欄位排序
// 					autoWidth: false, //自動調整寬度
// 					lengthMenu: [5, 10, 20], //設定每頁顯示筆數
// 					/* 本地化 */
// 					language: {
// 				        processing: "處理中...",
// 				        loadingRecords: "載入中...",
// 				        lengthMenu: "顯示 _MENU_ 項結果",
// 				        zeroRecords: "沒有符合的結果",
// 				        info: "顯示第 _START_ 至 _END_ 項結果，共 _TOTAL_ 項",
// 				        infoEmpty: "顯示第 0 至 0 項結果，共 0 項",
// 				        infoFiltered: "(從 _MAX_ 項結果中過濾)",
// 				        infoPostFix: "",
// 				        search: "搜尋:",
// 				        paginate: {
// 				            first: "第一頁",
// 				            previous: "上一頁",
// 				            next: "下一頁",
// 				            last: "最後一頁"
// 				        },
// 				        aria: {
// 				            sortAscending: ": 升冪排列",
// 				            sortDescending: ": 降冪排列"
// 				        }
// 				    },
// 				    /* ajax */
// 					ajax: {
// 						url: "<c:url value='/webUser/controller/WebUserSearchForm' />",
// 						type: "POST",
// 						dataType: "json",
// 						success: function(resultObj) {
// 							if (resultObj.resultCode == 1) {
// 								searchStr = resultObj.resultMessage;
// 								searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
// 										+ searchStr;
// 								searchSpan.style.color = "black";
// 								searchSpan.style.fontStyle = "normal";

// 								let content = "";
// 								if (resultObj.userDataList.length != 0) {
									
// 								} 
// 							} else if (resultObj.resultCode == 0) {
// 								searchStr = resultObj.resultMessage;
// 								searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
// 										+ searchStr;
// 								searchSpan.style.color = "black";
// 								searchSpan.style.fontStyle = "normal";
// 							} else if (resultObj.resultCode == -1) {
// 								searchStr = resultObj.resultMessage;
// 								searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
// 										+ searchStr;
// 								searchSpan.style.color = "red";
// 								searchSpan.style.fontStyle = "italic";
// 								dataContainer.innerHTML = "";
// 								/* 顯示彈窗異常訊息 */
// 								alert(resultObj.resultMessage);
// 							}
// 						},
// 						error : function(err) {
// 							searchStr = "發生錯誤，無法載入使用者資料";
// 							searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
// 									+ searchStr;
// 							searchSpan.style.color = "red";
// 							searchSpan.style.fontStyle = "italic";
// 							dataContainer.innerHTML = "";
// 							/* 顯示彈窗異常訊息 */
// 							alert(searchStr);
// 						}
// 					},
// 					/* 欄位定義 */
// 					columns:[
// 						{data: 'account'},
// 						{data: 'nickname'},
// 						{data: 'fervor'}
// 					]
// 				});
 			};
		
			$("#search").click(function() {
				var counter = 0;
				var userLv = document.getElementById("userLv").value.trim();
				var account = document.getElementById("userAccount").value.trim();
				var accountObjValue = document.getElementById("account").value.trim();
				var nicknameObjValue = document.getElementById("nickname").value.trim();
				var fervorObj = document.getElementsByClassName("fervor");
				var fervorObjValue = "";
				
				for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
					fervorObjValue += (fervorObjValue != "" && fervorObj[fervorIndex].checked) ? "," : "";
					fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
				}
				
				var locationCodeObjValue = document.getElementById("locationCode").value;
				var selectedStatus = (userLv == -1) ? document.getElementById("status").value : "";
				var selectedIdentity = (userLv == -1) ? document.getElementById("identity").value : "";
				
				if (checkForm()) {
					if (accountObjValue == "" && accountObjValue.length == 0) {
						counter++;
					}
					if (nicknameObjValue == "" || nicknameObjValue.length == 0) {
						counter++;
					}
					if (fervorObjValue == "" || fervorObjValue.length == 0) {
						counter++;
					}
					if (locationCodeObjValue == "" || locationCodeObjValue.length == 0) {
						counter++;
					}
					if (userLv != -1) {
						if (counter == 4){
							selectAllUser();
						} else {
							selectUser(accountObjValue, nicknameObjValue, fervorObjValue, locationCodeObjValue, selectedStatus);
						}
					} else {
						if (selectedStatus == "" || selectedStatus.length == 0) {
							counter++;
						}
						if (selectedIdentity == "" || selectedIdentity.length == 0) {
							counter++;
						}
						if (counter == 6){
							selectAllUser();
						} else {
							selectUser(accountObjValue, nicknameObjValue, fervorObjValue, locationCodeObjValue, selectedStatus, selectedIdentity);
						} 
					}
				}
	    	});
			
			function lastCheck(userId, account, status, mode) {
				let choice=confirm("是否要執行特定的操作？");
				if (choice) {
					let operateResultSpan = document.getElementById("searchSpan");
					let operateResultStr = "...處理中，請稍後";
					let operateResultIsOk = true;
					
					operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>"
						+ operateResultStr;
					operateResultSpan.style.color = "black";
					operateResultSpan.style.fontStyle = "normal";
					
					$.ajax({
						type : "POST",
						url : "<c:url value='/webUser/ManageWebUser/" + mode + "' />",
						data : {
							'userId':userId,
							'account':account,
							'status':status
						},
						dataType : "json",
						success : function(resultObj) {
							if (resultObj.resultCode == 1) {
								operateResultStr = resultObj.resultMessage;
								operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
										+ operateResultStr;
								operateResultSpan.style.color = "black";
								operateResultSpan.style.fontStyle = "normal";
								/* 顯示彈窗訊息 */
								alert(resultObj.resultMessage);
								/* 重新以Ajax寫出表格 */
								selectAllUser();
							} else if (resultObj.resultCode == 0) {
								operateResultStr = resultObj.resultMessage;
								operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + operateResultStr;
								operateResultSpan.style.color = "red";
								operateResultSpan.style.fontStyle = "italic";
								/* 顯示彈窗異常訊息 */
								alert(resultObj.resultMessage);
							} else if (resultObj.resultCode == -1) {
								operateResultStr = resultObj.resultMessage;
								operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + operateResultStr;
								operateResultSpan.style.color = "red";
								operateResultSpan.style.fontStyle = "italic";
								/* 顯示彈窗異常訊息 */
								alert(resultObj.resultMessage);
							}
						},
						error : function(err) {
							operateResultStr = "發生錯誤，無法執行指定的操作！";
							operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + operateResultStr;
							operateResultSpan.style.color = "red";
							operateResultSpan.style.fontStyle = "italic";
							/* 顯示彈窗異常訊息 */
							alert(resultObj.resultMessage);
						}
					});
				}
			}
			
			function selectUser(accountObjValue, nicknameObjValue, fervorObjValue, locationCodeObjValue, selectedStatus, selectedIdentity) {
				let startPage = parseInt(document.getElementById("pageNo").value);
				let searchSpan = document.getElementById("searchSpan");
				let searchStr = "...處理中，請稍後";
				let searchIsOk = true;
				let dataContainer = document.getElementById("dataContainer");
				let avgPage = 3;
				
				searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>"
					+ searchStr;
				searchSpan.style.color = "black";
				searchSpan.style.fontStyle = "normal";
				
				$.ajax({
					type : "POST",
					url : "<c:url value='/webUser/controller/WebUserSearchForm' />",
					data : {
						'selectedAccount':accountObjValue,
						'selectedNickname':nicknameObjValue,
						'selectedFervor':fervorObjValue,
						'selectedLocationCode':locationCodeObjValue,
						'selectedStatus':selectedStatus,
						'selectedIdentity':selectedIdentity,
					},
					dataType : "json",
					success : function(resultObj) {
						if (resultObj.resultCode == 1) {
							searchStr = resultObj.resultMessage;
							searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
									+ searchStr;
							searchSpan.style.color = "black";
							searchSpan.style.fontStyle = "normal";

							let content = "";
							if (resultObj.userDataList.length != 0) {
								content = "<form method='post'>"
										+ "<fieldset>"
										+ "<legend>以下為使用者列表：</legend>"
										+ "<hr />"
										+ "<table border='1'>";
										
								if (document.getElementById("userLv").value == -1) {
									content += "<tr>"
											+ "<th>項次</th>"
											+ "<th>圖示</th>"
											+ "<th>刪除</th>"
											+ "<th>權限</th>"
											+ "<th>查看</th>"
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "<th>帳號身分</th>"
											+ "<th>帳號狀態</th>"
											+ "</tr>";
								} else if (document.getElementById("userLv").value == 1) {
									content += "<tr>"
											+ "<th>項次</th>"
											+ "<th>圖示</th>"
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "<th>帳號身分</th>"
											+ "</tr>";
								} else {
									content += "<tr>"
											+ "<th>項次</th>"
											+ "<th>圖示</th>"
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "</tr>";
								}

								let endPage = (resultObj.userDataList.length < startPage * avgPage) ? resultObj.userDataList.length : startPage * avgPage

								for (let dataIndex = (startPage - 1) * avgPage; dataIndex < endPage; dataIndex++) {
									let userData = resultObj.userDataList[dataIndex];
									
									content += "<tr>"
											+ "<td>"
											+ (dataIndex + 1)
											+ "</td>"
											+ "<td>"
											+ "<img src='"
											+ document.getElementById("space").value;
									
									if (userData.iconUrl == '') {
										content += "/images/webUser/defaultIcon/ncu_scens.jpg"
													+ "' width='40' height='40' >"
													+ "</td>";
									} else {
										content += userData.iconUrl
													+ "' width='40' height='40' >"
													+ "</td>";
									}
									
									if (document.getElementById("userLv").value == -1) {
										content += "<td>"
												+ "<button type='button' class='deleteBtn' id='delBtn" 
												+ userData.userId 
												+ "_" 
												+ userData.account 
												+ "_" 
												+ userData.status 
												+ "' style='background-color:#ffc107'>"
												+ "<i class='material-icons' style='font-size:24px;color:red'>delete_forever</i>"
												+ "</button>"
												+ "</td>"
												+ "<td>";				

										if (userData.status == 'active') {
											content += "<button type='button' class='quitBtn' id='qutBtn" 
													+ userData.userId 
													+ "_" 
													+ userData.account 
													+ "_" 
													+ userData.status 
													+ "' style='background-color:#ffc107'>" 
													+ "<i class='material-icons' style='font-size:24px;color:red'>lock</i>"
													+ "</button>";
										} else if (userData.status == 'quit') {
											content += "<button type='button' class='activeBtn' id='actBtn" 
													+ userData.userId 
													+ "_" 
													+ userData.account 
													+ "_" 
													+ userData.status
													+ "' style='background-color:#ffc107'>" 
													+ "<i class='material-icons' style='font-size:24px;color:green'>lock_open</i>"
													+ "</button>"
										} else if (userData.status == 'inactive') {
											content += "<button type='button' class='activeBtn' id='actBtn" 
													+ userData.userId 
													+ "_" 
													+ userData.account 
													+ "_" 
													+ userData.status
													+ "' style='background-color:#ffc107'>" 
													+ "<i class='material-icons' style='font-size:24px;color:blue'>security</i>"
													+ "</button>"
										}
										content += "</td>"
													+ "<td>"
													+ "<a href='${pageContext.request.contextPath}/webUser/ManageWebUser/" 
													+ userData.account 
													+ "'>"
													+ "<i class='material-icons' style='font-size:24px;color:green'>info</i>"
													+ "</a>"
													+ "</td>"
													+ "<td>"
													+ userData.account 
													+ "</td>";
									} else {
										content += "<td>" + userData.account + "</td>";
									}
									
									content += "<td>" + userData.nickname + "</td>"
											+ "<td>" + userData.fervor + "</td>"
											+ "<td>" + userData.locationInfo.cityName + "</td>";
									
									if (document.getElementById("userLv").value == -1) {
										content += "<td>" + userData.accountLv.levelName + "</td>";
										
										switch(userData.status) {
											case "active":
												content += "<td>已啟用</td>"
														+ "</tr>";
												break;
											case "inactive":
												content += "<td>未啟用</td>"
														+ "</tr>";
												break;
											case "quit":
												content += "<td>已停用</td>"
														+ "</tr>";
												break;
										}
									} else if (document.getElementById("userLv").value == 1) {
										content += "<td>" + userData.accountLv.levelName + "</td>"
												+ "</tr>";
									} else {
										content += "</tr>";
									}
								}
								
								content += "</table>"
										+ "<hr />"
										+ "</fieldset>" 
										+ "</form>";
										
								document.getElementById("pageNo").value = startPage;
								document.getElementById("maxPage").value = parseInt(Math.ceil(resultObj.userDataList.length / avgPage));
								
								if (startPage - 1 > 0) {
									content += "<button type='button' style='background-color:#ffc107' class='pFirstBtn'>"
											+ "第一頁"
											+ "</button>"
											+ "<button type='button' style='background-color:#ffc107' class='pPrevBtn'>"
											+ "上一頁"
											+ "</button>";
											
								} 
								
								if (resultObj.userDataList.length > startPage * avgPage) {
									content += "<button type='button' style='background-color:#ffc107' class='pNextBtn'>"
											+ "下一頁"
											+ "</button>"
											+ "<button type='button' style='background-color:#ffc107' class='pLastBtn'>"
											+ "最末頁"
											+ "</button>";
								}
							}

							dataContainer.innerHTML = content;
						} else if (resultObj.resultCode == 0) {
							searchStr = resultObj.resultMessage;
							searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
									+ searchStr;
							searchSpan.style.color = "black";
							searchSpan.style.fontStyle = "normal";
						} else if (resultObj.resultCode == -1) {
							searchStr = resultObj.resultMessage;
							searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
									+ searchStr;
							searchSpan.style.color = "red";
							searchSpan.style.fontStyle = "italic";
							dataContainer.innerHTML = "";
							/* 顯示彈窗異常訊息 */
							alert(resultObj.resultMessage);
						}
					},
					error : function(err) {
						searchStr = "發生錯誤，無法載入使用者資料";
						searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
								+ searchStr;
						searchSpan.style.color = "red";
						searchSpan.style.fontStyle = "italic";
						dataContainer.innerHTML = "";
						/* 顯示彈窗異常訊息 */
						alert(searchStr);
					}
				});
			}

			function selectAllUser() {
				let startPage = parseInt(document.getElementById("pageNo").value);
				let searchSpan = document.getElementById("searchSpan");
				let searchStr = "...處理中，請稍後";
				let searchIsOk = true;
				let dataContainer = document.getElementById("dataContainer");
				let avgPage = 3;
				
				searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>"
						+ searchStr;
				searchSpan.style.color = "black";
				searchSpan.style.fontStyle = "normal";

				$.ajax({
					type : "POST",
					url : "<c:url value='/webUser/controller/WebUserSearchForm' />",
					dataType : "json",
					success : function(resultObj) {
						if (resultObj.resultCode == 1) {
							searchStr = resultObj.resultMessage;
							searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
									+ searchStr;
							searchSpan.style.color = "black";
							searchSpan.style.fontStyle = "normal";

							let content = "";
							if (resultObj.userDataList != null) {
								content = "<form method='post'>"
										+ "<fieldset>"
										+ "<legend>以下為使用者列表：</legend>"
										+ "<hr />"
										+ "<table border='1'>";
										
								if (document.getElementById("userLv").value == -1) {
									content += "<tr>"
											+ "<th>項次</th>"
											+ "<th>圖示</th>"
											+ "<th>刪除</th>"
											+ "<th>權限</th>"
											+ "<th>查看</th>"
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "<th>帳號身分</th>"
											+ "<th>帳號狀態</th>"
											+ "</tr>";
								} else if (document.getElementById("userLv").value == 1) {
									content += "<tr>"
											+ "<th>項次</th>"
											+ "<th>圖示</th>"
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "<th>帳號身分</th>"
											+ "</tr>";
								} else {
									content += "<tr>"
											+ "<th>項次</th>"
											+ "<th>圖示</th>"
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "</tr>";
								}
								
								let endPage = (resultObj.userDataList.length < startPage * avgPage) ? resultObj.userDataList.length : startPage * avgPage

								for (let dataIndex = (startPage - 1) * avgPage; dataIndex < endPage; dataIndex++) {
									let userData = resultObj.userDataList[dataIndex];
									
									content += "<tr>"
											+ "<td>"
											+ (dataIndex + 1)
											+ "</td>"
											+ "<td>"
											+ "<img src='"
											+ document.getElementById("space").value;
							
									if (userData.iconUrl == '') {
										content += "/images/webUser/defaultIcon/ncu_scens.jpg"
												+ "' width='40' height='40' >"
												+ "</td>";
									} else {
										content += userData.iconUrl
												+ "' width='40' height='40' >"
												+ "</td>";
									}
									
									if (document.getElementById("userLv").value == -1) {
										content += "<td>"
												+ "<button type='button' class='deleteBtn' id='delBtn" 
												+ userData.userId 
												+ "_" 
												+ userData.account 
												+ "_" 
												+ userData.status 
												+ "' style='background-color:#ffc107'>"
												+ "<i class='material-icons' style='font-size:24px;color:red'>delete_forever</i>"
												+ "</button>"
												+ "</td>"
												+ "<td>";				

										if (userData.status == 'active') {
											content += "<button type='button' class='quitBtn' id='qutBtn" 
														+ userData.userId 
														+ "_" 
														+ userData.account 
														+ "_" 
														+ userData.status 
														+ "' style='background-color:#ffc107'>" 
														+ "<i class='material-icons' style='font-size:24px;color:red'>lock</i>"
														+ "</button>";
										} else if (userData.status == 'quit') {
											content += "<button type='button' class='activeBtn' id='actBtn" 
														+ userData.userId 
														+ "_" 
														+ userData.account 
														+ "_" 
														+ userData.status
														+ "' style='background-color:#ffc107'>" 
														+ "<i class='material-icons' style='font-size:24px;color:green'>lock_open</i>"
														+ "</button>"
										} else if (userData.status == 'inactive') {
											content += "<button type='button' class='activeBtn' id='actBtn" 
														+ userData.userId 
														+ "_" 
														+ userData.account 
														+ "_" 
														+ userData.status
														+ "' style='background-color:#ffc107'>" 
														+ "<i class='material-icons' style='font-size:24px;color:blue'>security</i>"
														+ "</button>"
										}									
										content += "</td>"
													+ "<td>"
													+ "<a href='${pageContext.request.contextPath}/webUser/ManageWebUser/" + userData.account + "'>"
													+ "<button type='button' style='background-color:#ffc107'>"
													+ "<i class='material-icons' style='font-size:24px;color:green'>info</i>"
													+ "</button>"
													+ "</a>"
													+ "</td>"
													+ "<td>"
													+ userData.account 
													+ "</td>";
									} else {
										content += "<td>" + userData.account + "</td>";
									}
									
									content += "<td>" + userData.nickname + "</td>"
												+ "<td>" + userData.fervor + "</td>"
												+ "<td>" + userData.locationInfo.cityName + "</td>";
									
									if (document.getElementById("userLv").value == -1) {
										content += "<td>" + userData.accountLv.levelName + "</td>";
										
										switch(userData.status) {
											case "active":
												content += "<td>已啟用</td>"
														+ "</tr>";
												break;
											case "inactive":
												content += "<td>未啟用</td>"
														+ "</tr>";
												break;
											case "quit":
												content += "<td>已停用</td>"
														+ "</tr>";
												break;
										}
									} else if (document.getElementById("userLv").value == 1) {
										content += "<td>" + userData.accountLv.levelName + "</td>"
												+ "</tr>";
									} else {
										content += "</tr>";
									}
								}
								
								content += "</table>"
										+ "<hr />"
										+ "</fieldset>" 
										+ "</form>";
										
								document.getElementById("pageNo").value = startPage;
								document.getElementById("maxPage").value = parseInt(Math.ceil(resultObj.userDataList.length / avgPage));
								
								if (startPage - 1 > 0) {
									content += "<button type='button' style='background-color:#ffc107' class='pFirst'>"
											+ "第一頁"
											+ "</button>"
											+ "<button type='button' style='background-color:#ffc107' class='pPrev'>"
											+ "上一頁"
											+ "</button>";
											
								} 
								
								if (resultObj.userDataList.length > startPage * avgPage) {
									content += "<button type='button' style='background-color:#ffc107' class='pNext'>"
											+ "下一頁"
											+ "</button>"
											+ "<button type='button' style='background-color:#ffc107' class='pLast'>"
											+ "最末頁"
											+ "</button>";
								}
							}

							dataContainer.innerHTML = content;
						} else if (resultObj.resultCode == 0) {
							searchStr = resultObj.resultMessage;
							searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
									+ searchStr;
							searchSpan.style.color = "red";
							searchSpan.style.fontStyle = "italic";
							dataContainer.innerHTML = "";
							/* 顯示彈窗異常訊息 */
							alert(resultObj.resultMessage);
						} else if (resultObj.resultCode == -1) {
							searchStr = resultObj.resultMessage;
							searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
									+ searchStr;
							searchSpan.style.color = "red";
							searchSpan.style.fontStyle = "italic";
							dataContainer.innerHTML = "";
							/* 顯示彈窗異常訊息 */
							alert(resultObj.resultMessage);
						}
					},
					error : function(err) {
						searchStr = "發生錯誤，無法載入使用者資料";
						searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
								+ searchStr;
						searchSpan.style.color = "red";
						searchSpan.style.fontStyle = "italic";
						dataContainer.innerHTML = "";
						/* 顯示彈窗異常訊息 */
						alert(searchStr);
					}
				});
			}
		</script>
	</div>
	<!-- -------------------------------------------------------------------- -->
	<div
		style="background-color: #003049; border-top: 3px #e76f51 solid; color: white; margin-top: 20px">
		<%@include file="../Footer-Include-prototype.jsp"%>
</body>
</html>