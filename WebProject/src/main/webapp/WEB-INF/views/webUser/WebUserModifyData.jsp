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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/webUser/WebUserRegisterForm.css">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet"> 
    <title>修改個人資料</title>
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
				<c:if test="${empty userFullData}">
					<c:redirect url="WebUserLogin.jsp" />
				</c:if>
                <form method="post">
                	<fieldset>
                		<legend>以下為您可變更的資料：</legend>
                		<span id="updatedResultSpan"></span>
						<hr />
                		<input type="hidden" name="originalFirstName" id="originalFirstName" value="${selfData.firstName}">
						<label>中文姓氏：</label>
						<input type="text" name="updatedFirstName" id="updatedFirstName" size="40" maxlength="3" onblur="checkFirstName()"
							placeholder="請輸入姓氏，1~3個中文字" value="${selfData.firstName}" />
						<span id="firstNameSpan"></span>
						<hr />
						<input type="hidden" name="originalLastName" id="originalLastName" value="${selfData.lastName}">
						<label>中文名字：</label>
						<input type="text" name="updatedLastName" id="updatedLastName" size="40" maxlength="3" onblur="checkLastName()"
							placeholder="請輸入名字，1~3個中文字" value="${selfData.lastName}" />
						<span id="lastNameSpan"></span>
						<hr />
						<input type="hidden" name="originalNickname" id="originalNickname" value="${selfData.nickname}">
						<label>稱呼方式：</label>
						<input type="text" name="updatedNickname" id="updatedNickname" size="40" maxlength="20" onblur="checkNickname()"
							placeholder="請輸入想要的稱呼" value="${selfData.nickname}" />
						<input type="button" name="update" id="checkNicknameUsed" value="檢查稱呼">
						<span id="nicknameSpan"></span>
						<hr />
						<input type="hidden" name="originalFervor" id="originalFervor" value="${selfData.fervor}">
						<label>偏好食物：</label>
						<c:forEach items="${fervorList}" var="fervorObject" >
							<c:if test="${selfData.fervor.indexOf(fervorObject.fervorItem)!=-1}">
								<input type="checkbox" id="updatedFervor" name="updatedFervor"  
									class="updatedFervor" value="${fervorObject.fervorCode}" onblur="checkFervor()" checked="checked" />
							</c:if>
							<c:if test="${selfData.fervor.indexOf(fervorObject.fervorItem)==-1}">
								<input type="checkbox" id="updatedFervor" name="updatedFervor" class="updatedFervor" 
									value="${fervorObject.fervorCode}" onblur="checkFervor()" />
							</c:if>
							<label>${fervorObject.fervorItem}</label>
						</c:forEach>
						<span id="fervorSpan"></span>
						<hr />
						<input type="hidden" name="originalEmail" id="originalEmail" value="${selfData.email}">
						<label>聯絡信箱：</label>
						<input type="email" name="updatedEmail" id="updatedEmail" size="40" maxlength="30" onblur="checkEmail()"
						    placeholder="請輸入驗證、聯絡用的E-Mail地址" value="${selfData.email}" />
						<input type="button" name="update" id="checkEmailUsed" value="檢查信箱">
						<span id="emailSpan"></span>
						<hr />
						<input type="hidden" name="originalPhone" id="originalPhone" value="${selfData.phone}">
						<label>聯絡電話：</label>
						<input type="tel" name="updatedPhone" id="updatedPhone" size="40" maxlength="11" onblur="checkPhone()"
						    placeholder="請輸入行動電話或市內電話號碼" value="${selfData.phone}" />
						<input type="button" name="update" id="checkPhoneUsed" value="檢查電話">
						<span id="phoneSpan"></span>
					    <hr />
					    <input type="hidden" name="originalGetEmail" id="originalGetEmail" value="${selfData.getEmail.willingCode}">
					    <label>接收促銷/優惠意願：</label>
					    <c:forEach items="${willingList}" var="userWilling" >
					    	<c:if test="${userWilling.willingCode.equals(selfData.getEmail.willingCode)}" >
					    		<input type="radio" id="updatedGetEmail1" name="updatedGetEmail" value="${userWilling.willingCode}" 
									onblur="checkGetEmail()" checked="checked" >
					    	</c:if>
					    	<c:if test="${!userWilling.willingCode.equals(selfData.getEmail.willingCode)}" >
					    		<input type="radio" id="updatedGetEmail2" name="updatedGetEmail" value="${userWilling.willingCode}" 
									onblur="checkGetEmail()" >
					    	</c:if>
					    	<label><c:out value="${userWilling.willingText}"></c:out></label>
					    </c:forEach>
					    <span id="getEmailSpan"></span>
						<hr />
						<input type="hidden" name="originalLocationCode" id="originalLocationCode" value="${selfData.locationInfo.cityCode}">
					    <label>居住區域：</label>
					    <select name="updatedLocationCode" id="updatedLocationCode" onblur="checkLocationCode()">
					    	<c:forEach items="${cityInfoList}" var="cityInfo">
				    			<option value="${cityInfo.cityCode}"
				    				<c:if test="${cityInfo.cityCode == selfData.locationInfo.cityCode}">
		                         		selected="selected"
		                         	</c:if> 
				    			>
				    				<c:out value="${cityInfo.cityName}" /> 	
				    		 	</option>
				    		</c:forEach>
					    </select>
						<span id="locationCodeSpan"></span>
					    <hr />
					    <input type="hidden" name="originalAddr0" id="originalAddr0" value="${selfData.addr0}">
					    <label>生活地點一：</label>
					    <input type="text" name="updatedAddr0" id="updatedAddr0" size="65" maxlength="65" onblur="checkAddr0()"
						    placeholder="此項為必填，請輸入完整地址方面後續服務之利用" value="${selfData.addr0}" />
						<br />
						<span id="addr0Span"></span>
					    <hr />
					    <input type="hidden" name="originalAddr1" id="originalAddr1" value="${selfData.addr1}">
					    <label>生活地點二：</label>
					    <input type="text" name="updatedAddr1" id="updatedAddr1" size="65" maxlength="65" onblur="checkAddr1()"
						    placeholder="此項為選填，請輸入完整地址方面後續服務之利用" value="${selfData.addr1}" />
						<br />
						<span id="addr1Span"></span>
					    <hr />
					    <input type="hidden" name="originalAddr2" id="originalAddr2" value="${selfData.addr2}">
					    <label>生活地點三：</label>
					    <input type="text" name="updatedAddr2" id="updatedAddr2" size="65" maxlength="65" onblur="checkAddr2()"
						    placeholder="此項為選填，請輸入完整地址方面後續服務之利用" value="${selfData.addr2}" />
						<br />
						<span id="addr2Span"></span>
					    <hr />
					    <span id="updatedSpan"></span>
                	</fieldset>
                	<div align="center">
                		<a href="WebUserMain"><input type="button" name="update" value="取消/返回"></a>
						<input type="button" name="update" id="updateConfirm" value="資料修改完畢">
						<input type="reset" name="reset" value="重設" onclick="clearMessage()">
					</div>
					<hr />
                </form>
                <script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
                <script src="${pageContext.request.contextPath}/js/webUser/WebUserModifyData.js"></script>
                <script>
                	$("#updateConfirm").click(function() {
                		checkUpdate();
                	});
                	function checkUpdate() {
                		if (checkForm()) {
                			doUpdate();
                		}	
                	}
                	function doUpdate() {
                		let oldFirstName = document.getElementById("originalFirstName").value.trim();
                		let newFirstName = document.getElementById("updatedFirstName").value.trim();
                		let oldLastName = document.getElementById("originalLastName").value.trim();
                		let newLastName = document.getElementById("updatedLastName").value.trim();
                		let oldNickname = document.getElementById("originalNickname").value.trim();
                		let newNickname = document.getElementById("updatedNickname").value.trim();
                		let oldFervor = document.getElementById("originalFervor").value.trim();
                		let fervorObj = document.getElementsByClassName("updatedFervor");
                		let newFervor = "";
                		for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
                			if (newFervor != "" && fervorObj[fervorIndex].checked) {
                				newFervor += ",";
                			}
                			newFervor += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
                		}
                		let oldEmail = document.getElementById("originalEmail").value.trim();
                		let newEmail = document.getElementById("updatedEmail").value.trim();
                		let oldPhone = document.getElementById("originalPhone").value.trim();
                		let newPhone = document.getElementById("updatedPhone").value.trim();
                		let oldGetEmail = document.getElementById("originalGetEmail").value.trim();
                		let getEmailObj = document.getElementsByClassName("updatedGetEmail");
                		let newGetEmail =(document.getElementById("updatedGetEmail1") == null) ? "" : document.getElementById("updatedGetEmail1").value;
                		newGetEmail = (document.getElementById("updatedGetEmail2") == null) ? "" : document.getElementById("updatedGetEmail2").value;
                		let oldLocationCode = document.getElementById("originalLocationCode").value.trim();
                		let newLocationCode = document.getElementById("updatedLocationCode").value.trim();
                		let oldAddr0 = document.getElementById("originalAddr0").value.trim();
                		let newAddr0 = document.getElementById("updatedAddr0").value.trim();
                		let oldAddr1 = document.getElementById("originalAddr1").value.trim();
                		let newAddr1 = document.getElementById("updatedAddr1").value.trim();
                		let oldAddr2 = document.getElementById("originalAddr2").value.trim();
                		let newAddr2 = document.getElementById("updatedAddr2").value.trim();
                		
                		let updateSpan = document.getElementById("updatedSpan");
                		let updateResultSpan = document.getElementById("updatedResultSpan");
                		let updateStr = "...處理中，請稍後";
						let updateIsOk = true;
						
						updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + updateStr;
						updateSpan.style.color = "black";
						updateSpan.style.fontStyle = "normal";
						updateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + updateStr;
						updateResultSpan.style.color = "black";
						updateResultSpan.style.fontStyle = "normal";
						
						$.ajax({
							type:"POST",
							url:"<c:url value='/webUser/controller/WebUserModifyData' />",
							data:{
				            	'oldFirstName':oldFirstName,
				            	'newFirstName':newFirstName,
				            	'oldLastName':oldLastName,
								'newLastName':newLastName,
								'oldNickname':oldNickname,
								'newNickname':newNickname,
								'oldFervor':oldFervor,
								'newFervor':newFervor,
								'oldEmail':oldEmail,
								'newEmail':newEmail,
								'oldPhone':oldPhone,
								'newPhone':newPhone,
								'oldGetEmail':oldGetEmail,
								'newGetEmail':newGetEmail,
								'oldLocationCode':oldLocationCode,
								'newLocationCode':newLocationCode,
								'oldAddr0':oldAddr0,
								'newAddr0':newAddr0,
								'oldAddr1':oldAddr1,
								'newAddr1':newAddr1,
								'oldAddr2':oldAddr2,
								'newAddr2':newAddr2
				            },
				            dataType:"json",
				            success:function(resultObj) {
				            	if (resultObj.resultCode == 1) {
				            		updateStr = resultObj.resultMessage;
				            		updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + updateStr;
				            		updateSpan.style.color = "black";
				            		updateSpan.style.fontStyle = "normal";
				            		updateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + updateStr;
				            		updateResultSpan.style.color = "black";
				            		updateResultSpan.style.fontStyle = "normal";
				            	} else {
				            		updateStr = resultObj.resultMessage;
					            	updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updateStr;
					            	updateSpan.style.color = "red";
					            	updateSpan.style.fontStyle = "italic";
					            	updateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updateStr;
					            	updateResultSpan.style.color = "red";
					            	updateResultSpan.style.fontStyle = "italic";
					            	/* 顯示彈窗異常訊息 */
				            		alert(resultObj.resultMessage);
				            	}
				            },
				            error:function(err) {
				            	updateStr = "發生錯誤，無法執行檢查";
				            	updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updateStr;
				            	updateSpan.style.color = "red";
				            	updateSpan.style.fontStyle = "italic";
				            	updateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updateStr;
				            	updateResultSpan.style.color = "red";
				            	updateResultSpan.style.fontStyle = "italic";
				            }
						});
                	}
                
	                $("#checkNicknameUsed").click(function() {
				        checkUpdateNickname();
				    });
					function checkUpdateNickname() {
						let nickname = document.getElementById("updatedNickname").value.trim();
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
						let email = document.getElementById("updatedEmail").value.trim();
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
						let phone = document.getElementById("updatedPhone").value.trim();
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
				</script>
            </div>
<!-- -------------------------------------------------------------------- -->
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:20px">
            <%@include file = "../Footer-Include-prototype.jsp" %>
</body>
</html>