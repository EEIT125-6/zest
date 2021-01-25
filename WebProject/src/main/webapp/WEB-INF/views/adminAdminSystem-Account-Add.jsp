<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8"> 
<title>新增使用者</title>
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
		            <div class="container"  style="margin-top: 20px;background-color:#FFF;">
		               <c:if test="${userFullData.accountLv.lv != -1}">
							<c:redirect url="WebUserLogin" />
						</c:if>
		               <form method="post">
							<fieldset>
								<legend>新增帳戶相關資料</legend>
								<span id="submitSpan">
									<c:if test="${submitMessage != null}">
										<i class='material-icons' style='font-size:18px;color:red'>cancel</i>
										<c:out value="${submitMessage}" />
									</c:if>
								</span>
								<hr />
								<label>帳號身分：</label>
								<c:forEach items="${identityList}" var="level" >
									<c:if test="${level.lv==0}" >
										<input type="radio" name="userLv" value="${level.lv}" checked="checked" />
									</c:if>
									<c:if test="${level.lv!=0}" >
										<input type="radio" name="userLv" value="${level.lv}"/>
									</c:if>
									<label><c:out value="${level.levelName}" /></label>
								</c:forEach>
							    <hr />
								<label>帳號名稱：</label> 
								<input type="text" name="account" id="newAccount" size="40" maxlength="20" onblur="checkAccountName()"
									placeholder="請輸入帳號，6~30個字" required="required" />
								<span id="accountSpan"></span>
								<hr />
								<label>帳號密碼：</label> 
								<input type="password" name="password" id="password" size="40" maxlength="20" onblur="checkAccountPassword()"
									placeholder="請輸入密碼，6~30個字" required="required" />
								<button type="button" style="font-size:18px" id="visibility_switch" onclick="changeVisibility()">顯示密碼 <i class="material-icons" style="font-size:18px;color:red">visibility</i></button>
								<span id="passwordSpan"></span>
								<hr />
								<label>中文姓氏：</label>
								<input type="text" name="firstName" id="firstName" size="40" maxlength="3" onblur="checkFirst_name()"
								    placeholder="請輸入姓氏，1~3個中文字" required="required" />
								<span id="firstNameSpan"></span>
								<hr />
								<label>中文名字：</label>
								<input type="text" name="lastName" id="lastName" size="40" maxlength="3" onblur="checkLast_name()"
								    placeholder="請輸入名字，1~3個中文字" required="required" />
								<span id="lastNameSpan"></span>
								<hr />
								<label>稱呼方式：</label>
								<input type="text" name="nickname" id="nickname" size="40" maxlength="20" onblur="checkNickname()"
								    placeholder="請輸入想要的稱呼(留白的話會設定為名字)" required="required" />
								<span id="nicknameSpan"></span>
								<hr />
								<label>生理性別：</label>
								<c:forEach items="${genderList}" var="userGender" >
									<c:if test="${userGender.genderCode=='N'}" >
										<input type="radio" name="gender" value="${userGender.genderCode}" checked="checked" />
									</c:if>
									<c:if test="${userGender.genderCode!='N'}" >
										<input type="radio" name="gender" value="${userGender.genderCode}" />
									</c:if>
									<label><c:out value="${userGender.genderText}" /></label>
								</c:forEach>
							    <hr />
							    <label>西元生日：</label>
								<input type="date" name="birth" id="birth" onblur="checkBirthday()" required="required" />
								<span id="birthdaySpan"></span>
								<hr />
								<label>偏好食物：</label>
								<c:forEach items="${fervorList}" var="fervorObject" >
									<c:if test="${fervorObject.fervorCode==fervorList.size()}" >
										<input type="checkbox" name="fervorOption" class="fervor" value="${fervorObject.fervorCode}" checked="checked" onblur="checkFervor()" />
									</c:if>
									<c:if test="${fervorObject.fervorCode!=fervorList.size()}" >
										<input type="checkbox" name="fervorOption" class="fervor" value="${fervorObject.fervorCode}" onblur="checkFervor()" />
									</c:if>
									<label><c:out value="${fervorObject.fervorItem}" ></c:out></label>
								</c:forEach>
								<span id="fervorSpan"></span>
								<hr />
								<label>聯絡信箱：</label>
								<input type="email" name="email" id="email" size="40" maxlength="30" onblur="checkEmail()"
								    placeholder="請輸入驗證、聯絡用的E-Mail地址" required="required" />
								<span id="emailSpan"></span>
								<hr />
								<label>聯絡電話：</label>
								<input type="tel" name="phone" id="phone" size="40" maxlength="11" onblur="checkPhone()"
								    placeholder="請輸入行動電話或市內電話號碼" required="required" />
								<span id="phoneSpan"></span>
								<hr />
								<label>是否願意接收促銷/優惠訊息：</label>
								<c:forEach items="${willingList}" var="userWilling" >
									<c:if test="${userWilling.willingCode=='Y'}" >
										<input type="radio" name="getEmail" value="${userWilling.willingCode}" checked="checked" />
									</c:if>
									<c:if test="${userWilling.willingCode=='N'}" >
										<input type="radio" name="getEmail" value="${userWilling.willingCode}" />
									</c:if>
									<label><c:out value="${userWilling.willingText}" /></label>
								</c:forEach>
							    <hr />
							    <label>居住區域：</label>
							    <select name="locationCode" id="locationCode" onblur="checkLocation_code()" >
							    	<option value="">請選擇目前您居住/生活的區域</option>
								    <c:forEach items="${cityInfoList}" var="cityInfo" >
								    	<option value="${cityInfo.cityCode}" label="${cityInfo.cityName}" />
								    </c:forEach>
							    </select>
								<span id="locationCodeSpan"></span>
							    <hr />
							    <label>生活地點一：</label>
							    <input type="text" name="addr0" id="addr0" size="65" maxlength="65" onblur="checkAddr0()"
								    placeholder="此項為必填，請輸入完整地址方面後續服務之利用" required="required" />
								<br />
								<span id="addr0Span"></span>
							    <hr />
							    <label>生活地點二：</label>
							    <input type="text" name="addr1" id="addr1" size="65" maxlength="65"  onblur="checkAddr1()"
								    placeholder="此項為選填">
								<br />
								<span id="addr1Span"></span>
							    <hr />
							    <label>生活地點三：</label>
							    <input type="text" name="addr2" id="addr2" size="65" maxlength="65"  onblur="checkAddr2()"
								    placeholder="此項為選填">
								<br />
								<span id="addr2Span"></span>
							    <hr />
							</fieldset>
							<div align="center">
								<button type="button" style="font-size:18px" id="add" name="add" >送出 <i class="material-icons" style="font-size:18px;color:blue">check</i></button>
								<button type="reset" id="reset" name="reset" style="font-size:18px" onclick="clearMessage()">重設 <i class="material-icons" style="font-size:18px;color:blue">refresh</i></button>
								<a href="<c:url value='/adminAccount' /> "><button type="button" name="login" style="font-size:18px" >返回 <i class="material-icons" style="font-size:18px;color:green">undo</i></button></a>
								<span id="addResultSpan"></span>
							</div>
							<hr />
						</form>
						<script src="<c:url value='/js/webUser/WebUserAddForm.js' />"></script>
						<script>
							window.onload = function() {
								let addBtn = document.getElementById("add");
								
								add.onclick = function() {
									if (checkForm()) {
										adminAddUser();
									}
								};
							};
							
							function adminAddUser() {
								let addResultSpan = document.getElementById("addResultSpan");
								let addResultStr = "...處理中，請稍後";
								let addResultIsOk = true;
								
								let lv = document.getElementsByName("userLv");
								let userLv;
								for (let lvIndex = 0; lvIndex < lv.length; lvIndex++) {
		                			if (lv[lvIndex].checked) {
		                				userLv = lv[lvIndex].value;
		                			}
		                		}
								let account = document.getElementById("account").value.trim();
								let password = document.getElementById("password").value.trim();
								let firstName = document.getElementById("firstName").value.trim();
								let lastName = document.getElementById("lastName").value.trim();
								let nickname = document.getElementById("nickname").value.replace('<', ' ').replace('>', '').trim();
								let genderCode = document.getElementsByName("gender");
								let gender;
								for (let genderIndex = 0; genderIndex < genderCode.length; genderIndex++) {
		                			if (genderCode[genderIndex].checked) {
		                				gender = genderCode[genderIndex].value;
		                			}
		                		}
								let birth = document.getElementById("birth").value;
								let fervorObj = document.getElementsByClassName("fervor");
		                		let fervorValue = [];
		                		for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
		                			if (fervorObj[fervorIndex].checked) {		
		                				fervorValue.push(fervorObj[fervorIndex].value);
		                			}
		                		}
		                		let email = document.getElementById("email").value.replace('<', ' ').replace('>', '').trim();
		                		let getEmail = document.getElementsByName("getEmail");
		                		let getEmailValue = "";
		                		for (let getEmailIndex = 0; getEmailIndex < getEmail.length; getEmailIndex++) {
		                			if (getEmail[getEmailIndex].checked) {
		                				getEmailValue = getEmail[getEmailIndex].value;
		                			}
		                		}
		                		let phone = document.getElementById("phone").value.trim();
		                		let cityCode = document.getElementById("locationCode").value;
		                		let addr0 = document.getElementById("addr0").value.replace('<', ' ').replace('>', '').trim();
		                		let addr1 = document.getElementById("addr1").value.replace('<', ' ').replace('>', '').trim();
		                		let addr2 = document.getElementById("addr2").value.replace('<', ' ').replace('>', '').trim();
		                		
								addResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + addResultStr;
								addResultSpan.style.color = "black";
								addResultSpan.style.fontStyle = "normal";
								
								let xhrObject = new XMLHttpRequest();
								if (xhrObject != null) {
									xhrObject.open("POST", "<c:url value='/webUser/controller/WebUserAddForm' />", true);
									xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
									xhrObject.send("userLv=" + userLv 
														+ "&account=" + account
														+ "&password=" + password
														+ "&firstName=" + firstName
														+ "&lastName=" + lastName
														+ "&nickname=" + nickname
														+ "&gender=" + gender
														+ "&birth=" + birth
														+ "&fervorOption=" + fervorValue
														+ "&email=" + email
														+ "&phone=" + phone
														+ "&willing=" + getEmailValue
														+ "&locationCode=" + cityCode
														+ "&addr0=" + addr0
														+ "&addr1=" + addr1
														+ "&addr2=" + addr2);
									
									xhrObject.onreadystatechange = function() {
										if (xhrObject.readyState === 4 && xhrObject.status === 200) {
											let typeObject = xhrObject.getResponseHeader("Content-Type");
											if (typeObject.indexOf("application/json") === 0) {
												let resultObj = JSON.parse(xhrObject.responseText);
												if (resultObj.resultCode == 1) {
													addResultStr = resultObj.resultMessage;
													addResultIsOk = true;
													/* 顯示彈窗訊息 */
													alert(resultObj.resultMessage);
												} else if (resultObj.resultCode == 0) {
													addResultStr = resultObj.resultMessage;
													addResultIsOk = false;
													/* 顯示彈窗訊息 */
													alert(resultObj.resultMessage);
												} else if (resultObj.resultCode == -1) {
													addResultStr = resultObj.resultMessage;
													addResultIsOk = false;
													/* 顯示彈窗訊息 */
													alert(resultObj.resultMessage);
												}
												if (!addResultIsOk) {
													addResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + addResultStr;
													addResultSpan.style.color = "red";
													addResultSpan.style.fontStyle = "italic";
								            	} else {
								            		addResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + addResultStr;
								            		addResultSpan.style.color = "black";
								            		addResultSpan.style.fontStyle = "normal";
								            	}
											} else {
												addResultStr = "發生錯誤，無法執行檢查";
												addResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + addResultStr;
												addResultSpan.style.color = "red";
												addResultSpan.style.fontStyle = "italic";
											}
										}
									};
								} else {
									alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
								}
							}
							
							function checkSameAccount() {
								let newAccount = document.getElementById("newAccount").value.trim();
								let accountSpan = document.getElementById("accountSpan");
								let accountStr = "...處理中，請稍後";
								let accountIsOk = true;
								let mode = "checkAccount";
								
								accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + accountStr;
			            		accountSpan.style.color = "black";
			            		accountSpan.style.fontStyle = "normal";
								
								let xhrObject = new XMLHttpRequest();
								if (xhrObject != null) {
									xhrObject.open("POST", "<c:url value='/webUser/controller/UserInfoController' />", true);
									xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
									xhrObject.send("inputAccount=" + newAccount + "&register=" + mode);
									
									xhrObject.onreadystatechange = function() {
										if (xhrObject.readyState === 4 && xhrObject.status === 200) {
											let typeObject = xhrObject.getResponseHeader("Content-Type");
											if (typeObject.indexOf("application/json") === 0) {
												let resultObj = JSON.parse(xhrObject.responseText);
												if (resultObj.resultCode == 1) {
													accountStr = "此帳號已有人使用！";
								            		accountIsOk = false;
												} else if (resultObj.resultCode == 0) {
													accountStr = "可建立此帳號！";
								            		accountIsOk = true;
												} else if (resultObj.resultCode == -1) {
													accountStr = "檢查途中遭遇錯誤！";
								            		accountIsOk = false;
								            		/* 顯示彈窗異常訊息 */
								            		alert(resultObj.resultMessage);
												}
												if (!accountIsOk) {
								            		accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + accountStr;
								            		accountSpan.style.color = "red";
								            		accountSpan.style.fontStyle = "italic";
								            	} else {
								            		accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + accountStr;
								            		accountSpan.style.color = "black";
								            		accountSpan.style.fontStyle = "normal";
								            	}
											} else {
												accountStr = "發生錯誤，無法執行檢查";
								            	accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + accountStr;
							            		accountSpan.style.color = "red";
							            		accountSpan.style.fontStyle = "italic";
											}
										} 
									};
								} else {
									alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
								}
							}
							
							function checkSameNickname() {
								let nickname = document.getElementById("nickname").value.replace('<', ' ').replace('>', '').trim();
								let nicknameSpan = document.getElementById("nicknameSpan");
								let nicknameStr = "...處理中，請稍後";
								let nicknameIsOk = true;
								let mode = "checkNickname";
								
								nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + nicknameStr;
			            		nicknameSpan.style.color = "black";
			            		nicknameSpan.style.fontStyle = "normal";
								
								let xhrObject = new XMLHttpRequest();
								if (xhrObject != null) {
									xhrObject.open("POST", "<c:url value='/webUser/controller/UserInfoController' />", true);
									xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
									xhrObject.send("inputNickname=" + nickname + "&register=" + mode);
									
									xhrObject.onreadystatechange = function() {
										if (xhrObject.readyState === 4 && xhrObject.status === 200) {
											let typeObject = xhrObject.getResponseHeader("Content-Type");
											if (typeObject.indexOf("application/json") === 0) {
												let resultObj = JSON.parse(xhrObject.responseText);
												if (resultObj.resultCode == 1) {
													nicknameStr = "此稱呼已有人使用！";
													nicknameIsOk = false;
												} else if (resultObj.resultCode == 0) {
													nicknameStr = "可使用此稱呼！";
													nicknameIsOk = true;
												} else if (resultObj.resultCode == -1) {
													nicknameStr = "檢查途中遭遇錯誤！";
													nicknameIsOk = false;
								            		/* 顯示彈窗異常訊息 */
								            		alert(resultObj.resultMessage);
												}
												if (!nicknameIsOk) {
													nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + nicknameStr;
													nicknameSpan.style.color = "red";
													nicknameSpan.style.fontStyle = "italic";
								            	} else {
								            		nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + nicknameStr;
								            		nicknameSpan.style.color = "black";
								            		nicknameSpan.style.fontStyle = "normal";
								            	}
											} else {
												nicknameStr = "發生錯誤，無法執行檢查";
												nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + nicknameStr;
												nicknameSpan.style.color = "red";
												nicknameSpan.style.fontStyle = "italic";
									        }
										}
									};
								} else {
									alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
								}
							}
							
							function checkSameEmail() {
								let email = document.getElementById("email").value.replace('<', ' ').replace('>', '').trim();
								let emailSpan = document.getElementById("emailSpan");
								let emailStr = "...處理中，請稍後";
								let emailIsOk = true;
								let mode = "checkEmail";
								
								emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + emailStr;
			            		emailSpan.style.color = "black";
			            		emailSpan.style.fontStyle = "normal";
								
								let xhrObject = new XMLHttpRequest();
								if (xhrObject != null) {
									xhrObject.open("POST", "<c:url value='/webUser/controller/UserInfoController' />", true);
									xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
									xhrObject.send("inputEmail=" + email + "&register=" + mode);
									
									xhrObject.onreadystatechange = function() {
										if (xhrObject.readyState === 4 && xhrObject.status === 200) {
											let typeObject = xhrObject.getResponseHeader("Content-Type");
											if (typeObject.indexOf("application/json") === 0) {
												let resultObj = JSON.parse(xhrObject.responseText);
												if (resultObj.resultCode == 1) {
													emailStr = "此電子信箱已有人使用！";
								            		emailIsOk = false;
												} else if (resultObj.resultCode == 0) {
													emailStr = "可使用此電子信箱！";
								            		emailIsOk = true;
												} else if (resultObj.resultCode == -1) {
													emailStr = "檢查途中遭遇錯誤！";
								            		emailIsOk = false;
								            		/* 顯示彈窗異常訊息 */
								            		alert(resultObj.resultMessage);
												}
												if (!emailIsOk) {
								            		emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailStr;
								            		emailSpan.style.color = "red";
								            		emailSpan.style.fontStyle = "italic";
								            	} else {
								            		emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailStr;
								            		emailSpan.style.color = "black";
								            		emailSpan.style.fontStyle = "normal";
								            	}
											} else {
												emailStr = "發生錯誤，無法執行檢查";
								            	emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailStr;
								            	emailSpan.style.color = "red";
								            	emailSpan.style.fontStyle = "italic";
											}
										} 
									};
								} else {
									alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
								}
							}
							
							function checkSamePhone() {
								let phone = document.getElementById("phone").value.trim();
								let phoneSpan = document.getElementById("phoneSpan");
								let phoneStr = "...處理中，請稍後";
								let phoneIsOk = true;
								let mode = "checkPhone";
								
								phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + phoneStr;
			            		phoneSpan.style.color = "black";
			            		phoneSpan.style.fontStyle = "normal";
								
								let xhrObject = new XMLHttpRequest();
								if (xhrObject != null) {
									xhrObject.open("POST", "<c:url value='/webUser/controller/UserInfoController' />", true);
									xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
									xhrObject.send("inputPhone=" + phone + "&register=" + mode);
									
									xhrObject.onreadystatechange = function() {
										if (xhrObject.readyState === 4 && xhrObject.status === 200) {
											let typeObject = xhrObject.getResponseHeader("Content-Type");
											if (typeObject.indexOf("application/json") === 0) {
												let resultObj = JSON.parse(xhrObject.responseText);
												if (resultObj.resultCode == 1) {
													phoneStr = "此聯絡電話已有人使用！";
								            		phoneIsOk = false;
												} else if (resultObj.resultCode == 0) {
													phoneStr = "可使用此聯絡電話！";
								            		phoneIsOk = true;
												} else if (resultObj.resultCode == -1) {
													phoneStr = "檢查途中遭遇錯誤！";
								            		phoneIsOk = false;
								            		/* 顯示彈窗異常訊息 */
								            		alert(resultObj.resultMessage);
												}
												if (!phoneIsOk) {
								            		phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + phoneStr;
								            		phoneSpan.style.color = "red";
								            		phoneSpan.style.fontStyle = "italic";
								            	} else {
								            		phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + phoneStr;
								            		phoneSpan.style.color = "black";
								            		phoneSpan.style.fontStyle = "normal";
								            	}
											} else {
												phoneStr = "發生錯誤，無法執行檢查";
								            	phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + phoneStr;
								            	phoneSpan.style.color = "red";
								            	phoneSpan.style.fontStyle = "italic";
											}
										} 
									};
								} else {
									alert("您的瀏覽器不支援Ajax技術或部分功能遭到關閉，請改用其他套瀏覽器使用本網站或洽詢您設備的管理人員！");
								}
							}
						</script> 
		            </div>
<!-- -------------------------------------------------------------------- -->
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