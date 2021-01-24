<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>全商家分析</title>
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
								<c:if test="${operateMessage != null}">
									<p><c:out value="${operateMessage}" /></p>
								</c:if>
								<hr />
								<label>按店家名：</label> <input type="text" name="selectedStname"
									id="selectedStname" size="50" maxlength="50" onblur="checkStname()"
									placeholder="請輸入要查詢的店家名稱，最多50個字" /> 
								<span id="stnameSpan"></span>
								<label>按擁有者：</label> <input type="text" name="selectedOwner"
									id="selectedOwner" size="30" maxlength="30" onblur="checkOwner()"
									placeholder="請輸入要查詢的店家擁有者帳號" /> 
								<span id="ownerSpan"></span>
								<hr />
								<label>依照類型：</label>
								<select name="selectedSclass" id="sSclass" onblur="checkSelectedSclass()">
									<option value="">請選擇目前要查詢的餐廳類型</option>
									<c:forEach items="${sclassList}" var="sclassItem">
										<option value="${sclassItem}" label="${sclassItem}" />
									</c:forEach>
								</select> 
								<span id="sclassSpan"></span>
								<c:if test='${userFullData.accountLv.lv == -1}'>
									<label>店家狀態：</label>
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
						<script src="${pageContext.request.contextPath}/js/StoreSearchForm.js"></script>
						<script>
							window.onload = function() {
								/* 載入後先執行一次預設查詢 */
								selectAllStore();
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
								$("#dataContainer").on("click", ".pFirst", function() {
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = 1;
									selectAllStore();
								});
								$("#dataContainer").on("click", ".pFirstBtn", function() {
									var stnameObjValue = document.getElementById("selectedStname").value.trim();
									var ownerObjValue = document.getElementById("selectedOwner").value.trim();
									var sclassObjValue = document.getElementById("sSclass").value;
									var selectedStatus = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
									
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = 1;
									selectStore(stnameObjValue, ownerObjValue, sclassObjValue, selectedStatus);
								});
								/* 綁定上一頁按鈕 */
								$("#dataContainer").on("click", ".pPrev", function() {
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = (startPage > 1) ? startPage - 1 : 1;
									selectAllStore();
								});
								$("#dataContainer").on("click", ".pPrevBtn", function() {
									var stnameObjValue = document.getElementById("selectedStname").value.trim();
									var ownerObjValue = document.getElementById("selectedOwner").value.trim();
									var sclassObjValue = document.getElementById("sSclass").value;
									var selectedStatus = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
									
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = (startPage > 1) ? startPage - 1 : 1;
									selectStore(stnameObjValue, ownerObjValue, sclassObjValue, selectedStatus);
								});
								/* 綁定下一頁按鈕 */
								$("#dataContainer").on("click", ".pNext", function() {
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = startPage + 1;
									selectAllStore();
								});
								$("#dataContainer").on("click", ".pNextBtn", function() {
									var stnameObjValue = document.getElementById("selectedStname").value.trim();
									var ownerObjValue = document.getElementById("selectedOwner").value.trim();
									var sclassObjValue = document.getElementById("sSclass").value;
									var selectedStatus = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
									
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = startPage + 1;
									selectStore(stnameObjValue, ownerObjValue, sclassObjValue, selectedStatus);
								});
								/* 綁定最末頁按鈕 */
								$("#dataContainer").on("click", ".pLast", function() {
									let maxPage = parseInt(document.getElementById("maxPage").value);
									document.getElementById("pageNo").value = maxPage;
									selectAllStore();
								});
								$("#dataContainer").on("click", ".pLastBtn", function() {
									var stnameObjValue = document.getElementById("selectedStname").value.trim();
									var ownerObjValue = document.getElementById("selectedOwner").value.trim();
									var sclassObjValue = document.getElementById("sSclass").value;
									var selectedStatus = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
									
									let maxPage = parseInt(document.getElementById("maxPage").value);
									document.getElementById("pageNo").value = maxPage;
									selectStore(stnameObjValue, ownerObjValue, sclassObjValue, selectedStatus);
								});
							};
							
							$("#search").click(function() {
								specSearch();
					    	});
							
							function specSearch() {
								document.getElementById("pageNo").value = 1;
								document.getElementById("maxPage").value = 1;
								var counter = 0;
								var userLv = document.getElementById("userLv").value.trim();
								var stnameObjValue = document.getElementById("selectedStname").value.trim();
								var ownerObjValue = document.getElementById("selectedOwner").value.trim();
								var sclassObjValue = document.getElementById("sSclass").value;
								var statusObjValue = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
								
								if (checkForm()) {
									if (stnameObjValue == "" && stnameObjValue.length == 0) {
										counter++;
									}
									if (ownerObjValue == "" || ownerObjValue.length == 0) {
										counter++;
									}
									if (sclassObjValue == "" || sclassObjValue.length == 0) {
										counter++;
									}
									if (userLv != -1) {
										if (counter == 3){
											selectAllStore();
										} else {
											selectStore(stnameObjValue, ownerObjValue, sclassObjValue);
										} 
									} else {
										if (statusObjValue == "" || statusObjValue.length == 0) {
											counter++;
										}
										if (counter == 4){
											selectAllStore();
										} else {
											selectStore(stnameObjValue, ownerObjValue, sclassObjValue, statusObjValue);
										} 
									}
								} else {
									alert("檢查失敗！");
								}
							};
							
							function lastCheck(id, status, mode) {
								let choice=confirm("是否要執行特定的操作？");
								if (choice) {
									let operateResultSpan = document.getElementById("searchSpan");
									let operateResultStr = "...處理中，請稍後";
									let operateResultIsOk = true;
									
									operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + operateResultStr;
									operateResultSpan.style.color = "black";
									operateResultSpan.style.fontStyle = "normal";
									
									$.ajax({
										type : "POST",
										url : "<c:url value='/controller/adminStoreOperate' />",
										data : {
											'storeId':id,
											'status':status,
											'mode':mode
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
												selectAllStore();
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
							};
							
							function selectStore(stnameObjValue, ownerObjValue, sclassObjValue, statusObjValue) {
								let startPage = parseInt(document.getElementById("pageNo").value);
								let searchSpan = document.getElementById("searchSpan");
								let searchStr = "...處理中，請稍後";
								let searchIsOk = true;
								let dataContainer = document.getElementById("dataContainer");
								let avgPage = document.getElementById("avPage").value;
								
								searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>"
									+ searchStr;
								searchSpan.style.color = "black";
								searchSpan.style.fontStyle = "normal";
								
								$.ajax({
									type : "POST",
									url : "<c:url value='/controller/getStoreList' />",
									data : {
										'stname':stnameObjValue,
										'owner':ownerObjValue,
										'sclass':sclassObjValue,
										'status':statusObjValue,
										'avPage':avgPage,
										'startPage':startPage
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
											if (resultObj.storeList.length != 0) {
												content = "<form method='post'>"
														+ "<fieldset>"
														+ "<table border='1'>";
													
												if (document.getElementById("userLv").value == -1) {
													content += "<tr>"
															+ "<th>項次</th>"
															+ "<th>圖示</th>"
															+ "<th>刪除</th>"
															+ "<th>權限</th>"
															+ "<th>查看</th>"
															+ "<th>店家名稱</th>"
															+ "<th>類型</th>"
															+ "<th>擁有者</th>"
															+ "<th>店家狀態</th>"
															+ "</tr>";
												} else if (document.getElementById("userLv").value == 1) {
													content += "<tr>"
															+ "<th>項次</th>"
															+ "<th>圖示</th>"
															+ "<th>權限</th>"
															+ "<th>查看</th>"
															+ "<th>店家名稱</th>"
															+ "<th>類型</th>"
															+ "</tr>";
												} 
												
												let endPage = (resultObj.storeList.length < startPage * avgPage) ? resultObj.storeList.length : startPage * avgPage
												for (let dataIndex = 0; dataIndex < endPage; dataIndex++) {
													let storeData = resultObj.storeList[dataIndex];
													
													content += "<tr>"
															+ "<td>"
															+ parseInt(parseInt(avgPage * (startPage - 1)) + parseInt(dataIndex + 1))
															+ "</td>"
															+ "<td>"
															+ "<img src='"
															+ document.getElementById("space").value;
													
													if (storeData.photourl == null) {
														content += "/images/webUser/defaultIcon/ncu_scens.jpg"
																+ "' width='40' height='40' >"
																+ "</td>";
													} else {
														content += "/" 
																+ storeData.photourl 
																+ "' width='40' height='40' >"
																+ "</td>";
													} 
													
													if (document.getElementById("userLv").value == -1) {
														content += "<td>"
																+ "<button type='button' class='deleteBtn' id='delBtn" 
																+ storeData.id  
																+ "_" 
																+ storeData.status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:red'>delete_forever</i>"
																+ "</button>";
													}
														
													if (storeData.status == "1") {
														content += "<button type='button' class='quitBtn' id='qutBtn" 
																+ storeData.id  
																+ "_" 
																+ storeData.status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:green'>lock_open</i>"
																+ "</button>"
													} else if (storeData.status == "0") {
														content += "<button type='button' class='activeBtn' id='actBtn" 
																+ storeData.id  
																+ "_" 
																+ storeData.status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:red'>lock</i>"
																+ "</button>";
													} else if (storeData.status == "3") {
														content += "<button type='button' class='activeBtn' id='actBtn" 
																+ storeData.id  
																+ "_" 
																+ storeData.status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:blue'>delete</i>"
																+ "</button>";
													}
													
													content += "</td>"
															+ "<td>"
															+ "<a href='${pageContext.request.contextPath}/StoreGetFullstore/" 
															+ storeData.id
															+ "/"
															+ storeData.stname
															+ "'>"
															+ "<button type='button' style='background-color:#F0F0F0'>"
															+ "<i class='material-icons' style='font-size:24px;color:green'>info</i>"
															+ "</button>"
															+ "</a>"
															+ "</td>"
															+ "<td>"
															+ storeData.stname 
															+ "</td>"
															+ "<td>"
															+ storeData.sclass 
															+ "</td>";
													
													if (document.getElementById("userLv").value == -1) {
														content += "<td>"
																+ storeData.webUserData.account
																+ "</td>";
														
														if (storeData.status == 0) {
															content += "<td>已下架</td>"
																	+ "</tr>";
														} else if (storeData.status == 1) {
															content += "<td>上架中</td>"
																	+ "</tr>";
														} else if (storeData.status == 3) {
															content += "<td>已移除</td>"
																	+ "</tr>";
														}
													} 
												}
												
												content += "</table>"
														+ "<hr />"
														+ "</fieldset>" 
														+ "</form>";
														
												document.getElementById("maxPage").value = resultObj.totalDataPages;
												
												if (startPage - 1 > 0 && resultObj.totalDataPages > 2) {
													content += "<button type='button' style='background-color:#F0F0F0' class='pFirstBtn'>"
															+ "第一頁"
															+ "</button>";
												}
												
												if (startPage - 1 > 0) {
													content +="<button type='button' style='background-color:#F0F0F0' class='pPrevBtn'>"
															+ "上一頁"
															+ "</button>";
												} 
												
												if (resultObj.totalDataNums > startPage * avgPage) {
													content += "<button type='button' style='background-color:#F0F0F0' class='pNextBtn'>"
															+ "下一頁"
															+ "</button>";
												}
												
												if (resultObj.totalDataNums > startPage * avgPage && resultObj.totalDataPages > 2) {
													content += "<button type='button' style='background-color:#F0F0F0' class='pLastBtn'>"
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
							};
							
							function selectAllStore() {
								let startPage = parseInt(document.getElementById("pageNo").value);
								let searchSpan = document.getElementById("searchSpan");
								let searchStr = "...處理中，請稍後";
								let searchIsOk = true;
								let dataContainer = document.getElementById("dataContainer");
								let avgPage = document.getElementById("avPage").value;
								
								searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>"
										+ searchStr;
								searchSpan.style.color = "black";
								searchSpan.style.fontStyle = "normal";
								
								$.ajax({
									type : "POST",
									url : "<c:url value='/controller/getStoreList' />",
									data : {
										'avPage':avgPage,
										'startPage':startPage
									},success : function(resultObj) {
										if (resultObj.resultCode == 1) {
											searchStr = resultObj.resultMessage;
											searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
													+ searchStr;
											searchSpan.style.color = "black";
											searchSpan.style.fontStyle = "normal";
											
											let content = "";
											if (resultObj.storeList.length != 0) {
												content = "<form method='post'>"
														+ "<fieldset>"
														+ "<table border='1'>";
													
												if (document.getElementById("userLv").value == -1) {
													content += "<tr>"
															+ "<th>項次</th>"
															+ "<th>圖示</th>"
															+ "<th>刪除</th>"
															+ "<th>權限</th>"
															+ "<th>查看</th>"
															+ "<th>店家名稱</th>"
															+ "<th>類型</th>"
															+ "<th>擁有者</th>"
															+ "<th>店家狀態</th>"
															+ "</tr>";
												} else if (document.getElementById("userLv").value == 1) {
													content += "<tr>"
															+ "<th>項次</th>"
															+ "<th>圖示</th>"
															+ "<th>權限</th>"
															+ "<th>查看</th>"
															+ "<th>店家名稱</th>"
															+ "<th>類型</th>"
															+ "</tr>";
												} 
												
												let endPage = (resultObj.storeList.length < startPage * avgPage) ? resultObj.storeList.length : startPage * avgPage
												for (let dataIndex = 0; dataIndex < endPage; dataIndex++) {
													let storeData = resultObj.storeList[dataIndex];
													
													content += "<tr>"
															+ "<td>"
															+ parseInt(parseInt(avgPage * (startPage - 1)) + parseInt(dataIndex + 1))
															+ "</td>"
															+ "<td>"
															+ "<img src='"
															+ document.getElementById("space").value;
													
													if (storeData.photourl == null) {
														content += "/images/webUser/defaultIcon/ncu_scens.jpg"
																+ "' width='40' height='40' >"
																+ "</td>";
													} else {
														content += "/" 
																+ storeData.photourl 
																+ "' width='40' height='40' >"
																+ "</td>";
													} 
													
													if (document.getElementById("userLv").value == -1) {
														content += "<td>"
																+ "<button type='button' class='deleteBtn' id='delBtn" 
																+ storeData.id  
																+ "_" 
																+ storeData.status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:red'>delete_forever</i>"
																+ "</button>"
																+ "</td>";
													}
														
													if (storeData.status == "1") {
														content += "<td>" 
																+ "<button type='button' class='quitBtn' id='qutBtn" 
																+ storeData.id  
																+ "_" 
																+ storeData.status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:green'>lock_open</i>"
																+ "</button>"
													} else if (storeData.status == "0") {
														content += "<td>" 
																+ "<button type='button' class='activeBtn' id='actBtn" 
																+ storeData.id  
																+ "_" 
																+ storeData.status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:red'>lock</i>"
																+ "</button>";
													} else if (storeData.status == "3") {
														content += "<td>" 
																+ "<button type='button' class='activeBtn' id='actBtn" 
																+ storeData.id  
																+ "_" 
																+ storeData.status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:blue'>delete</i>"
																+ "</button>";
													}
													
													content += "</td>"
															+ "<td>"
															+ "<a href='${pageContext.request.contextPath}/StoreGetFullstore/" 
															+ storeData.id
															+ "/"
															+ storeData.stname
															+ "'>"
															+ "<button type='button' style='background-color:#F0F0F0'>"
															+ "<i class='material-icons' style='font-size:24px;color:green'>info</i>"
															+ "</button>"
															+ "</a>"
															+ "</td>"
															+ "<td>"
															+ storeData.stname 
															+ "</td>"
															+ "<td>"
															+ storeData.sclass 
															+ "</td>";
													
													if (document.getElementById("userLv").value == -1) {
														content += "<td>"
																+ storeData.webUserData.account
																+ "</td>";
														
														if (storeData.status == 0) {
															content += "<td>已下架</td>"
																	+ "</tr>";
														} else if (storeData.status == 1) {
															content += "<td>上架中</td>"
																	+ "</tr>";
														} else if (storeData.status == 3) {
															content += "<td>已移除</td>"
																	+ "</tr>";
														}
													} 
												}
												
												content += "</table>"
														+ "<hr />"
														+ "</fieldset>" 
														+ "</form>";
														
												document.getElementById("maxPage").value = resultObj.totalDataPages;
												
												if (startPage - 1 > 0 && resultObj.totalDataPages > 2) {
													content += "<button type='button' style='background-color:#F0F0F0' class='pFirst'>"
															+ "第一頁"
															+ "</button>";
															
												} 
												
												if (startPage - 1 > 0) {
													content += "<button type='button' style='background-color:#F0F0F0' class='pPrev'>"
															+ "上一頁"
															+ "</button>";
															
												} 
												
												if (resultObj.totalDataNums > startPage * avgPage) {
													content += "<button type='button' style='background-color:#F0F0F0' class='pNext'>"
															+ "下一頁"
															+ "</button>";
												}
												
												if (resultObj.totalDataNums > startPage * avgPage && resultObj.totalDataPages > 2) {
													content += "<button type='button' style='background-color:#F0F0F0' class='pLast'>"
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
							};
						</script>
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