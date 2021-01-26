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
								<label>依店家：</label> 
								<input type="text" name="store"
									id="store" size="60" maxlength="50" onblur="checkStore()"
									placeholder="輸入店名" /> 
								<span id="storeSpan"></span>
								<hr />
								<label>依價格：</label> 
								<input type="number" name="price"
									id="price" size="10" maxlength="10" onblur="checkPrice()"
									placeholder="輸入價格" /> 
								<span id="priceSpan"></span>
								<label>依數量：</label> 
								<input type="number" name="quantity"
									id="quantity" size="10" maxlength="10" onblur="checkQuantity()"
									placeholder="輸入數量" /> 
								<span id="quantitySpan"></span>
								<hr />
								<c:if test='${userFullData.accountLv.lv == -1}'>
									<label>依帳號：</label> 
									<select name="account" id="account" onblur="checkAccount()">
										<option value="">請選擇要查詢的帳號</option>
										<c:forEach items="${shopOwnerList}" var="shopOwner">
											<option value="${shopOwner}" label="${shopOwner}" />
										</c:forEach>
									</select>
									<span id="accountSpan"></span>
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
						<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
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
								$("#dataContainer").on("click", ".pFirst", function() {
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = 1;
									selectAllProduct();
								});
								$("#dataContainer").on("click", ".pFirstBtn", function() {
									var userLv = document.getElementById("userLv").value;
									var productObjValue = document.getElementById("product").value.trim();
									var storeObjValue = document.getElementById("store").value.trim();
									var priceObjValue = document.getElementById("price").value.trim();
									var quantityObjValue = document.getElementById("quantity").value.trim();
									var accountObjValue = (userLv == -1) ? document.getElementById("account").value.trim() : "";
									var statusObjValue = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
									
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = 1;
									selectProduct(productObjValue, storeObjValue, priceObjValue, quantityObjValue, accountObjValue, statusObjValue);
								});
								/* 綁定上一頁按鈕 */
								$("#dataContainer").on("click", ".pPrev", function() {
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = (startPage > 1) ? startPage - 1 : 1;
									selectAllProduct();
								});
								$("#dataContainer").on("click", ".pPrevBtn", function() {
									var userLv = document.getElementById("userLv").value;
									var productObjValue = document.getElementById("product").value.trim();
									var storeObjValue = document.getElementById("store").value.trim();
									var priceObjValue = document.getElementById("price").value.trim();
									var quantityObjValue = document.getElementById("quantity").value.trim();
									var accountObjValue = (userLv == -1) ? document.getElementById("account").value.trim() : "";
									var statusObjValue = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
									
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = (startPage > 1) ? startPage - 1 : 1;
									selectProduct(productObjValue, storeObjValue, priceObjValue, quantityObjValue, accountObjValue, statusObjValue);
								});
								/* 綁定下一頁按鈕 */
								$("#dataContainer").on("click", ".pNext", function() {
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = startPage + 1;
									selectAllProduct();
								});
								$("#dataContainer").on("click", ".pNextBtn", function() {
									var userLv = document.getElementById("userLv").value;
									var productObjValue = document.getElementById("product").value.trim();
									var storeObjValue = document.getElementById("store").value.trim();
									var priceObjValue = document.getElementById("price").value.trim();
									var quantityObjValue = document.getElementById("quantity").value.trim();
									var accountObjValue = (userLv == -1) ? document.getElementById("account").value.trim() : "";
									var statusObjValue = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
									
									let startPage = parseInt(document.getElementById("pageNo").value);
									document.getElementById("pageNo").value = startPage + 1;
									selectProduct(productObjValue, storeObjValue, priceObjValue, quantityObjValue, accountObjValue, statusObjValue);
								});
								/* 綁定最末頁按鈕 */
								$("#dataContainer").on("click", ".pLast", function() {
									let maxPage = parseInt(document.getElementById("maxPage").value);
									document.getElementById("pageNo").value = maxPage;
									selectAllProduct();
								});
								$("#dataContainer").on("click", ".pLastBtn", function() {
									var userLv = document.getElementById("userLv").value;
									var productObjValue = document.getElementById("product").value.trim();
									var storeObjValue = document.getElementById("store").value.trim();
									var priceObjValue = document.getElementById("price").value.trim();
									var quantityObjValue = document.getElementById("quantity").value.trim();
									var accountObjValue = (userLv == -1) ? document.getElementById("account").value.trim() : "";
									var statusObjValue = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
									
									let maxPage = parseInt(document.getElementById("maxPage").value);
									document.getElementById("pageNo").value = maxPage;
									selectProduct(productObjValue, storeObjValue, priceObjValue, quantityObjValue, accountObjValue, statusObjValue);
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
								var productObjValue = document.getElementById("product").value.trim();
								var storeObjValue = document.getElementById("store").value.trim();
								var priceObjValue = document.getElementById("price").value.trim();
								var quantityObjValue = document.getElementById("quantity").value.trim();
								var accountObjValue = (userLv == -1) ? document.getElementById("account").value : "";
								var statusObjValue = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
								
								if (checkForm()) {
									if (productObjValue == "" && productObjValue.length == 0) {
										counter++;
									}
									if (storeObjValue == "" || storeObjValue.length == 0) {
										counter++;
									}
									if (priceObjValue == "" || priceObjValue.length == 0) {
										counter++;
									}
									if (quantityObjValue == "" || quantityObjValue.length == 0) {
										counter++;
									}
									if (userLv != -1) {
										if (counter == 4){
											selectAllProduct();
										} else {
											selectProduct(productObjValue, storeObjValue, priceObjValue, quantityObjValue);
										} 
									} else {
										if (statusObjValue == "" || statusObjValue.length == 0) {
											counter++;
										}
										if (accountObjValue == "" || accountObjValue.length == 0) {
											counter++;
										}
										if (counter == 6){
											selectAllProduct();
										} else {
											selectProduct(productObjValue, storeObjValue, priceObjValue, quantityObjValue, accountObjValue, statusObjValue);
										} 
									}
								} else {
									swal("檢查失敗！","","error");
								}
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
									
									$.ajax({
										type : "POST",
										url : "<c:url value='/controller/adminProductOperate' />",
										data : {
											'productId':id,
											'status':status,
											'mode':mode
										},
										dataType : "json",
										success : function(resultObj) {
											if (resultObj.resultCode == 1) {
												operateResultStr = resultObj.resultMessage;
												operateResultIsOk = true;
											} else if (resultObj.resultCode == 0) {
												operateResultStr = resultObj.resultMessage;
												operateResultIsOk = false;
											} else if (resultObj.resultCode == -1) {
												operateResultStr = resultObj.resultMessage;
												operateResultIsOk = false;
											}
											if (!operateResultIsOk) {
												operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + operateResultStr;
												operateResultSpan.style.color = "red";
												operateResultSpan.style.fontStyle = "italic";
												/* 顯示彈窗訊息 */
												swal(operateResultStr,"","error");												
											} else {
												operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
														+ operateResultStr;
												operateResultSpan.style.color = "black";
												operateResultSpan.style.fontStyle = "normal";
												/* 顯示彈窗訊息 */
												swal(operateResultStr,"","success");
												setTimeout(function() {
													/* 重新以Ajax寫出表格 */
													selectAllStore();
												},1500);	
											}
										},
										error : function(err) {
											operateResultStr = "發生錯誤，無法執行指定的操作！";
											operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + operateResultStr;
											operateResultSpan.style.color = "red";
											operateResultSpan.style.fontStyle = "italic";
											/* 顯示彈窗訊息 */
											swal(operateResultStr,"","error");
										}
									});
								}
							}
							
							function selectProduct(productObjValue, storeObjValue, priceObjValue, quantityObjValue, accountObjValue, statusObjValue) {
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
									url : "<c:url value='/controller/getProductInfoList' />",
									data : {
										'name':productObjValue,
										'shop':storeObjValue,
										'price':priceObjValue,
										'quantity':quantityObjValue,
										'account':accountObjValue,
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
											if (resultObj.productInfoList.length != 0) {
												content = "<form method='post'>"
														+ "<fieldset>"
														+ "<table border='1'>";
												
												if (document.getElementById("userLv").value == -1) {
													content += "<tr>"
															+ "<th>項次</th>"
															+ "<th>刪除</th>"
															+ "<th>權限</th>"
															+ "<th>商品名稱</th>"
															+ "<th>販售店家</th>"
															+ "<th>價格</th>"
															+ "<th>數量</th>"
															+ "<th>權責帳號</th>"
															+ "<th>商品狀態</th>"
															+ "</tr>";
												} else if (document.getElementById("userLv").value == 1) {
													content += "<tr>"
															+ "<th>項次</th>"
															+ "<th>權限</th>"
															+ "<th>商品名稱</th>"
															+ "<th>販售店家</th>"
															+ "<th>價格</th>"
															+ "<th>數量</th>"
															+ "</tr>";
												}
												
												let endPage = (resultObj.productInfoList.length < startPage * avgPage) ? resultObj.productInfoList.length : startPage * avgPage
												for (let dataIndex = 0; dataIndex < endPage; dataIndex++) {
													let productData = resultObj.productInfoList[dataIndex];
													
													content += "<tr>"
															+ "<td>"
															+ parseInt(parseInt(avgPage * (startPage - 1)) + parseInt(dataIndex + 1))
															+ "</td>";
													
													if (document.getElementById("userLv").value == -1) {
														content += "<td>"
																+ "<button type='button' class='deleteBtn' id='delBtn" 
																+ productData.product_id  
																+ "_" 
																+ productData.product_status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:red'>delete_forever</i>"
																+ "</button>"
																+ "</td>";
													}
													
													if (productData.product_status == "1") {
														content += "<td>" 
																+ "<button type='button' class='quitBtn' id='qutBtn" 
																+ productData.product_id  
																+ "_" 
																+ productData.product_status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:green'>lock_open</i>"
																+ "</button>"
													} else if (productData.product_status == "0") {
														content += "<td>" 
																+ "<button type='button' class='activeBtn' id='actBtn" 
																+ productData.product_id  
																+ "_" 
																+ productData.product_status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:red'>lock</i>"
																+ "</button>";
													} else if (productData.product_status == "3") {
														content += "<td>" 
																+ "<button type='button' class='activeBtn' id='actBtn" 
																+ productData.product_id  
																+ "_" 
																+ productData.product_status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:blue'>delete</i>"
																+ "</button>";
													}
													
													content += "</td>"
															+ "<td>"
															+ productData.product_name 
															+ "</td>"
															+ "<td>"
															+ productData.product_shop 
															+ "</td>"
															+ "<td>"
															+ productData.product_price 
															+ "</td>";
													
													if (productData.product_quantity < 10) {
														content += "<td>"
																+ "<font color='red'>"
																+ productData.product_quantity
																+ "</font>"
																+ "</td>";
													} else {
														content += "<td>"
																+ productData.product_quantity
																+ "</td>";
													}
													
													if (document.getElementById("userLv").value == -1) {
														content += "<td>"
																+ productData.storebean.webUserData.account
																+ "</td>";
																
														if (productData.product_status == "0") {
															content += "<td>已下架</td>"
																	+ "</tr>";
														} else if (productData.product_status == "1") {
															content += "<td>上架中</td>"
																	+ "</tr>";
														} else if (productData.product_status == "3") {
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
											swal(searchStr,"","error");
										}
									},
									error : function(err) {
										searchStr = "發生錯誤，無法載入商品資料";
										searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
												+ searchStr;
										searchSpan.style.color = "red";
										searchSpan.style.fontStyle = "italic";
										dataContainer.innerHTML = "";
										/* 顯示彈窗訊息 */
										swal(searchStr,"","error");
									}
								});
							}
							
							function selectAllProduct() {
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
									url : "<c:url value='/controller/getProductInfoList' />",
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
											if (resultObj.productInfoList.length != 0) {
												content = "<form method='post'>"
														+ "<fieldset>"
														+ "<table border='1'>";
												
												if (document.getElementById("userLv").value == -1) {
													content += "<tr>"
															+ "<th>項次</th>"
															+ "<th>刪除</th>"
															+ "<th>權限</th>"
															+ "<th>商品名稱</th>"
															+ "<th>販售店家</th>"
															+ "<th>價格</th>"
															+ "<th>數量</th>"
															+ "<th>權責帳號</th>"
															+ "<th>商品狀態</th>"
															+ "</tr>";	
												} else if (document.getElementById("userLv").value == 1) {
													content += "<tr>"
															+ "<th>項次</th>"
															+ "<th>權限</th>"
															+ "<th>商品名稱</th>"
															+ "<th>販售店家</th>"
															+ "<th>價格</th>"
															+ "<th>數量</th>"
															+ "</tr>";
												}
												
												let endPage = (resultObj.productInfoList.length < startPage * avgPage) ? resultObj.productInfoList.length : startPage * avgPage
												for (let dataIndex = 0; dataIndex < endPage; dataIndex++) {
													let productData = resultObj.productInfoList[dataIndex];
													
													content += "<tr>"
															+ "<td>"
															+ parseInt(parseInt(avgPage * (startPage - 1)) + parseInt(dataIndex + 1))
															+ "</td>";
													
													if (document.getElementById("userLv").value == -1) {
														content += "<td>"
																+ "<button type='button' class='deleteBtn' id='delBtn" 
																+ productData.product_id  
																+ "_" 
																+ productData.product_status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:red'>delete_forever</i>"
																+ "</button>"
																+ "</td>";
													}
													
													if (productData.product_status == "1") {
														content += "<td>" 
																+ "<button type='button' class='quitBtn' id='qutBtn" 
																+ productData.product_id  
																+ "_" 
																+ productData.product_status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:green'>lock_open</i>"
																+ "</button>"
													} else if (productData.product_status == "0") {
														content += "<td>" 
																+ "<button type='button' class='activeBtn' id='actBtn" 
																+ productData.product_id  
																+ "_" 
																+ productData.product_status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:red'>lock</i>"
																+ "</button>";
													} else if (productData.product_status == "3") {
														content += "<td>" 
																+ "<button type='button' class='activeBtn' id='actBtn" 
																+ productData.product_id  
																+ "_" 
																+ productData.product_status 
																+ "' style='background-color:#F0F0F0'>" 
																+ "<i class='material-icons' style='font-size:24px;color:blue'>delete</i>"
																+ "</button>";
													}
													
													content += "</td>"
															+ "<td>"
															+ productData.product_name 
															+ "</td>"
															+ "<td>"
															+ productData.product_shop 
															+ "</td>"
															+ "<td>"
															+ productData.product_price 
															+ "</td>";
													
													if (productData.product_quantity < 10) {
														content += "<td>"
																+ "<font color='red'>"
																+ productData.product_quantity
																+ "</font>"
																+ "</td>";
													} else {
														content += "<td>"
																+ productData.product_quantity
																+ "</td>";
													}
													
													if (document.getElementById("userLv").value == -1) {
														content += "<td>"
																+ productData.storebean.webUserData.account
																+ "</td>";
																
														if (productData.product_status == "0") {
															content += "<td>已下架</td>"
																	+ "</tr>";
														} else if (productData.product_status == "1") {
															content += "<td>上架中</td>"
																	+ "</tr>";
														} else if (productData.product_status == "3") {
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
											swal(searchStr,"","error");
										}
									},
									error : function(err) {
										searchStr = "發生錯誤，無法載入商品資料";
										searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
												+ searchStr;
										searchSpan.style.color = "red";
										searchSpan.style.fontStyle = "italic";
										dataContainer.innerHTML = "";
										/* 顯示彈窗訊息 */
										swal(searchStr,"","error");
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