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
    <%@include file = "../Link_Meta-Include.jsp" %>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/webUser/WebUserRegisterForm.css">    
    <title>查詢結果</title>
    <style>
        .classimg{
		 transition: 0.2s;	
        	width:80px
        }
        .classimg:hover{
         transition: 0.2s;	        
			width: 85px
        }
        body{
         background-color: 		rgb(235, 159, 18);
       }
       .header{
            height: 100px;
            border-bottom: 3px solid #e76f51;height: 90px;
            padding-top: 5px;
            background-color: #003049
       }
       .photo{
           padding: 0%;
           background: url("Images/backbar2-1.jpg"); 
           height: 540px;
           padding-top: 220px;
           background-size:100%
       }
       .shopcar{
            height: 40px;
            margin: 0;
            margin-left:5px ;
       }
        .wrapper{
            position: relative;
            width:1000px;
            height:400px;
            overflow: hidden;
            margin:0 auto;
            border-radius:5px;   
        }
        ul{
            margin:0;
            padding: 0;
            position: absolute;
        }
        li{
            margin:0;
            padding: 0;
            list-style: none;
        }
        ul.slides{
            width: 4000px;
            left: 0px;
            transition: all .5s;
        }
        ul.slides li{
            width:1000px;
            height:400px;
            overflow: hidden;
            float: left;
        }
        ul.slides li img{
            width: 100%;
            height: 100%;
            object-fit: cover;
        }
        .dot{
            bottom:10px;
            width:100%;
            display: flex;
            justify-content: center;
        }
        .dot li{
            border:1px solid #fff;
            
            margin: 0 5px;
            width:24px;
            height: 10px;
        }

        .slide_btn{
            display: flex;
            justify-content: center;
            align-items: center;
            top:0;
            bottom:0;
            width: 30px;
            color:#fff;
            position: absolute;
            font-size:24px;  
            
        }
        #prevSlide{            
            left:0;                    
        }
        #nextSlide{            
            right:0;                
        }
        .slide_btn i{
            color:rgba(255,255,255,.6);                        
            transition: .5s;
        }
        .slide_btn:hover i{
            color:rgba(255,255,255,1);            
        }
        
        
#gotop {
    position:fixed;
    z-index:90;
    right:30px;
    bottom:31px;
    display:none;
    width:50px;
    height:50px;
    color:#fff;
    background:#ddbe56;
    line-height:50px;
    border-radius:50%;
    transition:all 1.5s;
    text-align: center;
    box-shadow: 0 2px 5px 0 rgba(0,0,0,0.16), 0 2px 10px 0 rgba(0,0,0,0.12);
}
#gotop :hover{
    background:#0099CC;
}
    </style>
</head>
<body>
            <%@include file = "../Header-Include.jsp" %>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
				<c:if test="${managedUserData.account == null}">
					<c:redirect url="WebUserSearchForm" />
				</c:if>
				<c:if test="${userFullData.accountLv.lv != -1}">
					<c:redirect url="WebUserMain" />
				</c:if>
				<c:if test="${userFullData.account.length() == 0}">
					<c:redirect url="WebUserLogin" />
				</c:if>
				<form method="post">
					<fieldset>
						<legend>以下為所點選的使用者帳號：</legend>
						<hr />
							<input type="hidden" name="userId" id="userId" value="${managedUserData.userId}" />
							<input type="hidden" name="account" id="account" value="${managedUserData.account}" />
							<label>帳號名稱：</label>
							<c:out value="${managedUserData.account}" />
							<hr />
							<label>帳號密碼：</label>
							<input type="password" name="password" id="password" value="${managedUserData.password}" onblur="checkPassword()"
								size="40" maxlength="20" placeholder="請輸入密碼，8~20個字">
							<input type="hidden" name="oldPassword" id="oldPassword" value="${managedUserData.password}">
							<button type="button" style="font-size:18px" id="visibility_switch" onclick="changeVisibility()">顯示密碼 <i class="material-icons" style="font-size:18px;color:red">visibility</i></button>
							<span id="passwordSpan"></span>
							<hr />
							<label>中文姓氏：</label>
							<input type="text" name="firstName" id="firstName" value="${managedUserData.firstName}" onblur="checkFirstName()"
								size="40" maxlength="20" placeholder="請輸入名字，1~3個中文字">
							<input type="hidden" name="oldFirstName" id="oldFirstName" value="${managedUserData.firstName}">
							<span id="firstNameSpan"></span>
							<hr />
							<label>中文名字：</label>
							<input type="text" name="lastName" id="lastName" value="${managedUserData.lastName}" onblur="checkLastName()"
								size="40" maxlength="20" placeholder="請輸入名字，1~3個中文字">
							<input type="hidden" name="oldLastName" id="oldLastName" value="${managedUserData.lastName}">
							<span id="lastNameSpan"></span>
							<hr />
							<label>稱呼方式：</label>
							<input type="text" name="nickname" id="nickname" value="${managedUserData.nickname}" onblur="checkNickname()"
								size="40" maxlength="20" placeholder="請輸入想要的稱呼">
							<input type="hidden" name="oldNickname" id="oldNickname" value="${managedUserData.nickname}">
							<button type="button" style="font-size:18px" id="checkNicknameUsed" >檢查稱呼 <i class="material-icons" style="font-size:18px;color:green">search</i></button>
							<span id="nicknameSpan"></span>
							<hr />
							<label>生理性別：</label>
							<c:forEach items="${genderList}" var="userGender" >
								<c:if test="${userGender.genderCode == managedUserData.gender.genderCode}" >
									<input type="radio" name="gender" value="${userGender.genderCode}" 
										class="gender" checked="checked" onblur="checkGender()" />
								</c:if>
								<c:if test="${userGender.genderCode != managedUserData.gender.genderCode}" >
									<input type="radio" name="gender" value="${userGender.genderCode}"
										class="gender" onblur="checkGender()" />
								</c:if>
								<label><c:out value="${userGender.genderText}" /></label>
							</c:forEach>
							<input type="hidden" name="oldGender" id="oldGender" value="${managedUserData.gender.genderCode}" />
							<span id="genderSpan"></span>
							<hr />
							<label>西元生日：</label>
							<input type="date" name="birth" id="birth" value="${managedUserData.birth}" 
								onblur="checkBirth()" />
							<input type="hidden" name="oldBirth" id="oldBirth" value="${managedUserData.birth}" />
							<span id="birthdaySpan"></span>
							<hr />
							<label>偏好食物：</label>
							<c:forEach items="${fervorList}" var="fervorObject" >
								<c:if test="${managedUserData.fervor.indexOf(fervorObject.fervorItem)!=-1}">
									<input type="checkbox" id="fervor" name="fervor"  
										class="fervor" value="${fervorObject.fervorCode}" onblur="checkFervor()" checked="checked" />
								</c:if>
								<c:if test="${managedUserData.fervor.indexOf(fervorObject.fervorItem)==-1}">
									<input type="checkbox" id="fervor" name="fervor" class="fervor" 
										value="${fervorObject.fervorCode}" onblur="checkFervor()" />
								</c:if>
								<label>${fervorObject.fervorItem}</label>
							</c:forEach>
							<input type="hidden" name="oldFervor" id="oldFervor" value="${managedUserData.fervor}">
							<span id="fervorSpan"></span>
							<hr />
							<label>聯絡信箱：</label>
							<input type="text" name="email" id="email" value="${managedUserData.email}" onblur="checkEmail()"
								size="40" maxlength="30" placeholder="請輸入驗證、聯絡用的E-Mail地址">
							<input type="hidden" name="oldEmail" id="oldEmail" value="${managedUserData.email}">
							<button type="button" style="font-size:18px" id="checkEmailUsed" >檢查信箱 <i class="material-icons" style="font-size:18px;color:green">search</i></button>
							<span id="emailSpan"></span>
							<hr />
							<label>聯絡電話：</label>
							<input type="text" name="phone" id="phone" value="${managedUserData.phone}" onblur="checkPhone()"
								size="40" maxlength="11" placeholder="請輸入行動電話或市內電話號碼">
							<input type="hidden" name="oldPhone" id="oldPhone" value="${managedUserData.phone}">
							<button type="button" style="font-size:18px" id="checkPhoneUsed" >檢查電話 <i class="material-icons" style="font-size:18px;color:green">search</i></button>
							<span id="phoneSpan"></span>
							<hr />
							<label>是否願意接收促銷/優惠訊息：</label>
							<c:forEach items="${willingList}" var="userWilling" >
						    	<c:if test="${userWilling.willingCode.equals(managedUserData.getEmail.willingCode)}" >
						    		<input type="radio" id="getEmail1" name="getEmail" value="${userWilling.willingCode}" 
										onblur="checkGetEmail()" checked="checked" >
						    	</c:if>
						    	<c:if test="${!userWilling.willingCode.equals(managedUserData.getEmail.willingCode)}" >
						    		<input type="radio" id="getEmail2" name="getEmail" value="${userWilling.willingCode}" 
										onblur="checkGetEmail()" >
						    	</c:if>
						    	<label><c:out value="${userWilling.willingText}"></c:out></label>
						    </c:forEach>
							<input type="hidden" name="oldGetEmail" id="oldGetEmail" value="${managedUserData.getEmail.willingCode}">
						    <span id="getEmailSpan"></span>
							<hr />
							<label>居住區域：</label>
							<select name="locationCode" id="locationCode" onblur="checkLocationCode()">
						    	<c:forEach items="${cityInfoList}" var="cityInfo">
					    			<option value="${cityInfo.cityCode}"
					    				<c:if test="${cityInfo.cityCode == managedUserData.locationInfo.cityCode}">
			                         		selected="selected"
			                         	</c:if> 
					    			>
					    				<c:out value="${cityInfo.cityName}" /> 	
					    		 	</option>
					    		</c:forEach>
						    </select>
							<input type="hidden" name="oldLocationCode" id="oldLocationCode" value="${managedUserData.locationInfo.cityCode}">
							<span id="locationCodeSpan"></span>
							<hr />
							<label>生活地點一：</label>
							<input type="text" name="addr0" id="addr0" value="${managedUserData.addr0}" onblur="checkAddr0()"
								size="65" maxlength="65" placeholder="此項為必填，請輸入完整地址方面後續服務之利用">
							<input type="hidden" name="oldAddr0" id="oldAddr0" value="${managedUserData.addr0}">
							<span id="addr0Span"></span>
							<hr />
							<label>生活地點二：</label>
							<input type="text" name="addr1" id="addr1" value="${managedUserData.addr1}" onblur="checkAddr1()"
								size="65" maxlength="65" placeholder="此項為選填，請輸入完整地址方面後續服務之利用">
							<input type="hidden" name="oldAddr1" id="oldAddr1" value="${managedUserData.addr1}">
							<span id="addr1Span"></span>
							<hr />
							<label>生活地點三：</label>
							<input type="text" name="addr2" id="addr2" value="${managedUserData.addr2}" onblur="checkAddr2()"
								size="65" maxlength="65" placeholder="此項為選填，請輸入完整地址方面後續服務之利用">
							<input type="hidden" name="oldAddr2" id="oldAddr2" value="${managedUserData.addr2}">
							<span id="addr2Span"></span>
							<hr />
							<label>所擁有的橙幣：</label>
							<c:out value="${managedUserData.zest}" />
							<hr />
							<label>帳號狀態：</label>
							<c:choose>
								<c:when test="${managedUserData.status=='active'}">已啟用</c:when>
								<c:when test="${managedUserData.status=='inactive'}">未啟用</c:when>
								<c:when test="${managedUserData.status=='quit'}">已停用</c:when>
							</c:choose>
							<input type="hidden" name="status" id="status" value="${managedUserData.status}">
							<hr />
					</fieldset>
					<div align="center">
						<button type="button" style="font-size:18px" id="updateAccount" >編輯帳號 <i class="material-icons" style="font-size:18px;color:blue">build</i></button>
						<button type="reset" style="font-size:18px" onclick="clearMessage()">重設 <i class="material-icons" style="font-size:18px;color:blue">refresh</i></button>
						<a href="WebUserSearchForm"><button type="button" style="font-size:18px" >返回上一頁 <i class="material-icons" style="font-size:18px;color:green">undo</i></button></a>
						<hr />
						<span id="operateResult"></span>
					</div>
				</form>
				<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
				<script src="<c:url value='/js/webUser/DisplayManagedUserData.js' />"></script>
				<script>
					$("#checkNicknameUsed").click(function() {
				        checkUpdateNickname();
				    });
					function checkUpdateNickname() {
						let nickname = document.getElementById("nickname").value.trim();
						let nicknameSpan = document.getElementById("nicknameSpan");
						let nicknameStr = "處理中...，請稍後";
						let nicknameIsOk = true;
						let mode = "checkNickname";
						
						nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + nicknameStr;
	            		nicknameSpan.style.color = "black";
	            		nicknameSpan.style.fontStyle = "normal";
						
						$.ajax({
							type:"POST",
				            url:"<c:url value='/webUser/controller/UserInfoController' />",
				            data:{
				            	'register':mode,
				            	'inputNickname':nickname
				            },
				            dataType:"json",
				            success:function(resultObj) {
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
				            },
				            error:function(err) {
				            	nicknameStr = "發生錯誤，無法執行檢查";
				            	nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + nicknameStr;
				            	nicknameSpan.style.color = "red";
				            	nicknameSpan.style.fontStyle = "italic";
				            }
						});
					}
					
					$("#checkEmailUsed").click(function() {
						checkUpdateEmail();
				    });
					function checkUpdateEmail() {
						let email = document.getElementById("email").value.trim();
						let emailSpan = document.getElementById("emailSpan");
						let emailStr = "...處理中，請稍後";
						let emailIsOk = true;
						let mode = "checkEmail";
						
						emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + emailStr;
	            		emailSpan.style.color = "black";
	            		emailSpan.style.fontStyle = "normal";
						
						$.ajax({
							type:"POST",
				            url:"<c:url value='/webUser/controller/UserInfoController' />",
				            data:{
				            	'register':mode,
				            	'inputEmail':email
				            },
				            dataType:"json",
				            success:function(resultObj) {
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
				            },
				            error:function(err) {
				            	emailStr = "發生錯誤，無法執行檢查";
				            	emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailStr;
				            	emailSpan.style.color = "red";
				            	emailSpan.style.fontStyle = "italic";
				            }
						});
					}
					
					$("#checkPhoneUsed").click(function() {
				        checkUpdatePhone();
				    });
					function checkUpdatePhone() {
						let phone = document.getElementById("phone").value.trim();
						let phoneSpan = document.getElementById("phoneSpan");
						let phoneStr= "...處理中，請稍後";
						let phoneIsOk = true;
						let mode = "checkPhone";
						
						phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + phoneStr;
	            		phoneSpan.style.color = "black";
	            		phoneSpan.style.fontStyle = "normal";
						
						$.ajax({
							type:"POST",
				            url:"<c:url value='/webUser/controller/UserInfoController' />",
				            data:{
				            	'register':mode,
				            	'inputPhone':phone
				            },
				            dataType:"json",
				            success:function(resultObj) {
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
				            },
				            error:function(err) {
				            	phoneStr = "發生錯誤，無法執行檢查";
				            	phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + phoneStr;
				            	phoneSpan.style.color = "red";
				            	phoneSpan.style.fontStyle = "italic";
				            }
						});
					}
					
					$("#updateAccount").click(function() {
						let choice=confirm("是否要執行特定的操作？");
						if (choice){
							checkUpdate();
						}
                	});
                	function checkUpdate() {
                		if (checkForm()) {
                			doUpdate();
                		} else {
                			alert("欄位檢查失敗!");
                		}	
                	}
                	function doUpdate() {
                		let newPassword = document.getElementById("password").value.trim();
                		let newFirstName = document.getElementById("firstName").value.trim();
                		let newLastName = document.getElementById("lastName").value.trim();
                		let newNickname = document.getElementById("nickname").value.trim();
                		let genderObj = document.getElementsByClassName("gender");
                		let newGender = "";
                		for (let genderIndex = 0; genderIndex < genderObj.length; genderIndex++) {
                			if ((genderObj[genderIndex].checked)) {
                				newGender = genderObj[genderIndex].value
                			}
                		}
                		let newBirth = document.getElementById("birth").value.trim();
                		let fervorObj = document.getElementsByClassName("fervor");
                		let newFervor = "";
                		for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
                			if (newFervor != "" && fervorObj[fervorIndex].checked) {
                				newFervor += ",";
                			}
                			newFervor += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
                		}
                		let newEmail = document.getElementById("email").value.trim();
                		let newPhone = document.getElementById("phone").value.trim();
                		let newGetEmail =(document.getElementById("getEmail1") == null) ? "" : document.getElementById("getEmail1").value;
                		newGetEmail = (document.getElementById("getEmail2") == null) ? "" : document.getElementById("getEmail2").value;
                		let newLocationCode = document.getElementById("locationCode").value.trim();
                		let newAddr0 = document.getElementById("addr0").value.trim();
                		let newAddr1 = document.getElementById("addr1").value.trim();
                		let newAddr2 = document.getElementById("addr2").value.trim();
                		
                		let updateSpan = document.getElementById("operateResult");
                		let updateStr = "...處理中，請稍後";
						let updateIsOk = true;
						
						updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + updateStr;
						updateSpan.style.color = "black";
						updateSpan.style.fontStyle = "normal";
						
						$.ajax({
							type:"POST",
							url:"<c:url value='/webUser/controller/WebUserAdminModifyData' />",
							data:{
				            	'newPassword':newPassword,
				            	'newFirstName':newFirstName,
								'newLastName':newLastName,
								'newNickname':newNickname,
								'newGender':newGender,
								'newBirth':newBirth,
								'newFervor':newFervor,
								'newEmail':newEmail,
								'newPhone':newPhone,
								'newGetEmail':newGetEmail,
								'newLocationCode':newLocationCode,
								'newAddr0':newAddr0,
								'newAddr1':newAddr1,
								'newAddr2':newAddr2
				            },
				            dataType:"json",
				            success:function(resultObj) {
				            	if (resultObj.resultCode == 1) {
				            		updateStr = resultObj.resultMessage;
				            		updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + updateStr;
				            		updateSpan.style.color = "black";
				            		updateSpan.style.fontStyle = "normal";
				            	} else {
				            		updateStr = resultObj.resultMessage;
					            	updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updateStr;
					            	updateSpan.style.color = "red";
					            	updateSpan.style.fontStyle = "italic";
					            	/* 顯示彈窗異常訊息 */
				            		alert(resultObj.resultMessage);
				            	}
				            },
				            error:function(err) {
				            	updateStr = "發生錯誤，無法執行檢查";
				            	updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updateStr;
				            	updateSpan.style.color = "red";
				            	updateSpan.style.fontStyle = "italic";
				            }
						});
                	}
				</script>
            </div>
<!-- -------------------------------------------------------------------- -->
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:20px">
            <%@include file = "../Footer-Include-prototype.jsp" %>
</body>
</html>