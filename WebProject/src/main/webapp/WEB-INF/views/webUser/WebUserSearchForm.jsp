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
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/css/webUser/WebUserRegisterForm.css">
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
			<c:redirect url="WebUserLogin" />
		</c:if>
		<form method="post" >
			<fieldset>
				<legend>搜尋選項</legend>
				<input type="hidden" name="userLv" id="userLv"
					value=<c:out value="${userFullData.accountLv.lv}"></c:out> />
				<c:if test="${operateMessage != null}">
					<p><c:out value="${operateMessage}" /></p>
				</c:if>
				<hr />
				<label>帳號名稱：</label> <input type="text" name="selectedAccount"
					id="account" size="40" maxlength="20" onblur="checkAccountName()"
					placeholder="請輸入要查詢的帳號，8~20個字" /> 
				<span id="accountSpan"></span>
				<hr />
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
					<hr />
					<label>帳號狀態：</label>
					<select name="selectedStatus" id="status" onblur="checkStatus()">
						<option value="">請選擇要查詢的狀態</option>
						<option value="active">已啟用</option>
						<option value="inactive">未啟用</option>
						<option value="quit">已停用</option>
					</select>
					<span id="statusSpan"></span>
				</c:if>
				<hr />
			</fieldset>
			<div align="center">
				<a href="WebUserMain">
				<input type="button" id="back" name="back" value="返回">
				</a> 
				<input type="button" id="search" name="select" value="執行查詢"> 
				<input type="reset" name="reset" value="重設條件"
					onclick="clearMessage()">
			</div>
			<hr />
		</form>
		<div align="center">
			<span id="searchSpan"></span>
		</div>
		<div id="dataContainer"></div>
		<script
			src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
		<script
			src="${pageContext.request.contextPath}/js/webUser/WebUserSearchForm.js"></script>
		<script>
			$("#search").click(function() {
				var counter = 0;
				var userLv = document.getElementById("userLv").value.trim();
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
						if (counter == 5){
							selectAllUser();
						} else {
							selectUser(accountObjValue, nicknameObjValue, fervorObjValue, locationCodeObjValue, selectedStatus);
						} 
					}
				}
	    	});
			
			window.onload = function() {
				selectAllUser();
			};
			
			function selectUser(accountObjValue, nicknameObjValue, fervorObjValue, locationCodeObjValue, selectedStatus) {
				let searchSpan = document.getElementById("searchSpan");
				let searchStr = "...處理中，請稍後";
				let searchIsOk = true;
				let dataContainer = document.getElementById("dataContainer");
				
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
						'selectedStatus':selectedStatus
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
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼名稱</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "<th>帳號身分</th>"
											+ "<th>帳號狀態</th>"
											+ "</tr>";
								} else if (document.getElementById("userLv").value == 1) {
									content += "<tr>"
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼名稱</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "<th>帳號身分</th>"
											+ "</tr>";
								} else {
									content += "<tr>"
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼名稱</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "</tr>";
								}

								for (let dataIndex = 0; dataIndex < resultObj.userDataList.length; dataIndex++) {
									let userData = resultObj.userDataList[dataIndex];
									
									content += "<tr>";
									
									if (document.getElementById("userLv").value == -1) {
										content += "<td>"
												+ "<a href='${pageContext.request.contextPath}/webUser/ManageWebUser/" + userData.account + "'>" 
												+ userData.account 
												+ "</a>"
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
				let searchSpan = document.getElementById("searchSpan");
				let searchStr = "...處理中，請稍後";
				let searchIsOk = true;
				let dataContainer = document.getElementById("dataContainer");

				searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>"
						+ searchStr;
				searchSpan.style.color = "black";
				searchSpan.style.fontStyle = "normal";

				$.ajax({
					type : "POST",
					url : "<c:url value='/webUser/controller/WebUserSearchForm/All' />",
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
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼名稱</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "<th>帳號身分</th>"
											+ "<th>帳號狀態</th>"
											+ "</tr>";
								} else if (document.getElementById("userLv").value == 1) {
									content += "<tr>"
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼名稱</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "<th>帳號身分</th>"
											+ "</tr>";
								} else {
									content += "<tr>"
											+ "<th>帳號名稱</th>"
											+ "<th>稱呼名稱</th>"
											+ "<th>偏好食物</th>"
											+ "<th>居住區域</th>"
											+ "</tr>";
								}

								for (let dataIndex = 0; dataIndex < resultObj.userDataList.length; dataIndex++) {
									let userData = resultObj.userDataList[dataIndex];
									
									content += "<tr>";
									
									if (document.getElementById("userLv").value == -1) {
										content += "<td>"
												+ "<a href='${pageContext.request.contextPath}/webUser/ManageWebUser/" + userData.account + "'>" 
												+ userData.account 
												+ "</a>"
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