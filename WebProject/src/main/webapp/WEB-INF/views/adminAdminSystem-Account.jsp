<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>全會員分析</title>
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
			<div class="content" style="background-color: #ffc107;">
				<div class="container-fluid">
<!---------------------------------------------------------------------------->
					<div class="container" style="margin-top: 20px;">
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
									id="usrAccount" size="30" maxlength="30" onblur="checkAccountName()"
									placeholder="請輸入要查詢的帳號，6~30個字" /> 
								<span id="accountSpan"></span>
								<label>用戶暱稱：</label> <input type="text" name="selectedNickname"
									id="nickname" size="30" maxlength="25" onblur="checkNickname()"
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
								<c:if test="${userFullData.accountLv.lv == -1}" >
									<a href="adminAccountAdd"><button type="button" id="adminAdd" name="adminAdd" style="font-size:18px" onclick="clearMessage()">新增帳號 <i class="material-icons" style="font-size:18px;color:green">add</i></button></a>
								</c:if>
							</div>
							<hr />
						</form>
						<div align="center">
							<span id="searchSpan"></span>
							<hr />
						</div>
						
						<div align="center" id="dataContainer"></div>
						
						<!-- 引用本地jQuery -->
						<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
						<!-- 引用本頁檢查用js -->
						<script src="${pageContext.request.contextPath}/js/webUser/WebUserSearchForm.js"></script>
						<script>
							window.onload = function() {
								/* 載入後先執行一次預設查詢 */
								selectAllUser();
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
									var accountObjValue = document.getElementById("usrAccount").value.trim();
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
									var accountObjValue = document.getElementById("usrAccount").value.trim();
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
									var accountObjValue = document.getElementById("usrAccount").value.trim();
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
									var accountObjValue = document.getElementById("usrAccount").value.trim();
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
							};
							
							$("#search").click(function() {
								specSearch();
					    	});
							
							function specSearch() {
								document.getElementById("pageNo").value = 1;
								document.getElementById("maxPage").value = 1;
								var counter = 0;
								var userLv = document.getElementById("userLv").value.trim();
				 				var account = document.getElementById("userAccount").value.trim();
								var accountObjValue = document.getElementById("usrAccount").value.trim();
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
								} else {
									alert("檢查失敗！");
								}
							};
							
							function lastCheck(userId, account, status, mode) {
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
								let avgPage = document.getElementById("avPage").value;
								
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
											if (resultObj.userDataList.length != 0) {
												content = "<form method='post'>"
														+ "<fieldset>"
														+ "<table border='1'>";
														
												if (document.getElementById("userLv").value == -1) {
													content += "<tr>"
															+ "<th>項次</th>"
															+ "<th>圖示</th>"
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
												for (let dataIndex = 0; dataIndex < endPage; dataIndex++) {
													let userData = resultObj.userDataList[dataIndex];
													
													content += "<tr>"
															+ "<td>"
															+ parseInt(parseInt(avgPage * (startPage - 1)) + parseInt(dataIndex + 1))
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
														content += "<td>";				
				
														if (userData.status == 'active') {
															content += "<button type='button' class='quitBtn' id='qutBtn" 
																	+ userData.userId 
																	+ "_" 
																	+ userData.account 
																	+ "_" 
																	+ userData.status 
																	+ "' style='background-color:#ffc107'>" 
																	+ "<i class='material-icons' style='font-size:24px;color:green'>lock_open</i>"
																	+ "</button>"
														} else if (userData.status == 'quit') {
															content += "<button type='button' class='activeBtn' id='actBtn" 
																	+ userData.userId 
																	+ "_" 
																	+ userData.account 
																	+ "_" 
																	+ userData.status
																	+ "' style='background-color:#ffc107'>" 
																	+ "<i class='material-icons' style='font-size:24px;color:red'>lock</i>"
																	+ "</button>";
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
														
												document.getElementById("maxPage").value = resultObj.totalDataPages;		
														
												if (startPage - 1 > 0 && resultObj.totalDataPages > 2) {
													content += "<button type='button' style='background-color:#ffc107' class='pFirstBtn'>"
															+ "第一頁"
															+ "</button>";
												}
												
												if (startPage - 1 > 0) {
													content +="<button type='button' style='background-color:#ffc107' class='pPrevBtn'>"
															+ "上一頁"
															+ "</button>";
												} 
												
												if (resultObj.totalDataNums > startPage * avgPage) {
													content += "<button type='button' style='background-color:#ffc107' class='pNextBtn'>"
															+ "下一頁"
															+ "</button>";
												}
												
												if (resultObj.totalDataNums > startPage * avgPage && resultObj.totalDataPages > 2) {
													content += "<button type='button' style='background-color:#ffc107' class='pLastBtn'>"
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
								let avgPage = document.getElementById("avPage").value;
								
								searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>"
										+ searchStr;
								searchSpan.style.color = "black";
								searchSpan.style.fontStyle = "normal";
				
								$.ajax({
									type : "POST",
									url : "<c:url value='/webUser/controller/WebUserSearchForm' />",
									data : {
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
											if (resultObj.userDataList != null) {
												content = "<form method='post'>"
														+ "<fieldset>"
														+ "<table border='1'>";
														
												if (document.getElementById("userLv").value == -1) {
													content += "<tr>"
															+ "<th>項次</th>"
															+ "<th>圖示</th>"
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
												
												for (let dataIndex = 0; dataIndex < resultObj.userDataList.length; dataIndex++) {
													let userData = resultObj.userDataList[dataIndex];
													
													content += "<tr>"
															+ "<td>"
															+ parseInt(parseInt(avgPage * (startPage - 1)) + parseInt(dataIndex + 1))
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
														content += "<td>";				
				
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
												
												document.getElementById("maxPage").value = resultObj.totalDataPages;
														
												if (startPage - 1 > 0 && resultObj.totalDataPages > 2) {
													content += "<button type='button' style='background-color:#ffc107' class='pFirst'>"
															+ "第一頁"
															+ "</button>";
															
												} 
												
												if (startPage - 1 > 0) {
													content += "<button type='button' style='background-color:#ffc107' class='pPrev'>"
															+ "上一頁"
															+ "</button>";
															
												} 
												
												if (resultObj.totalDataNums > startPage * avgPage) {
													content += "<button type='button' style='background-color:#ffc107' class='pNext'>"
															+ "下一頁"
															+ "</button>";
												}
												
												if (resultObj.totalDataNums > startPage * avgPage && resultObj.totalDataPages > 2) {
													content += "<button type='button' style='background-color:#ffc107' class='pLast'>"
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