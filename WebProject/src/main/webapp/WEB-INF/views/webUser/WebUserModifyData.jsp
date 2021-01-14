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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoadingScreen.css"> 
   	<link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
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
            <%@include file = "../LoadingScreen.jsp" %>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
				<c:if test="${empty userFullData}">
					<c:redirect url="/WebUserLogin" />
				</c:if>
				<form method="post" enctype="multipart/form-data">
					<fieldset>
						<legend>以下為您可變更的資料：</legend>
						<hr />
						<label>帳號圖示：</label>
                		<c:if test="${selfData.iconUrl == ''}" >
                			<img src="<c:url value='/images/webUser/defaultIcon/ncu_scens.jpg' />" width="200" height="200" title="這是系統預設的帳號圖示">
                		</c:if>
                		<c:if test="${selfData.iconUrl != ''}" >
                			<img src="<c:url value='${selfData.iconUrl}' />" width="200" height="200" title="這是您目前的帳號圖示">
                		</c:if>
                		<label>圖示預覽：</label>
                		<img id="picPreview" src="#" width="200" height="200" alt="這是預覽的帳號圖示" title="這是預覽的帳號圖示">
                		<hr />
                		<label>圖示檔案：</label>
                		<input type="hidden" name="oldIconUrl" id="oldIconUrl" value="${selfData.iconUrl}">
						<input type="file" name="iconUrl" id="iconUrl" data-target="iconUrl" accept="image/png, image/jpg, image/jpeg, image/gif" />
                		<input type="hidden" name="newIconUrl" id="newIconUrl">
                		<button type="button" name="uploadPic" id="uploadPic" style="font-size:18px">執行上傳 <i class="material-icons" style="font-size:18px;color:green">upload</i></button>
                		<button type="button" name="resetDefault" id="resetDefault" style="font-size:18x">回復預設 <i class="material-icons" style="font-size:18px;color:green">refresh</i></button>
                		<span id="picSpan"></span>
                		<hr />
					</fieldset>
				</form>
                <form method="post">
                	<fieldset>
                		<span id="updatedResultSpan"></span>
						<input type="hidden" name="account" id="account" value="${selfData.account}">
                		<input type="hidden" name="originalFirstName" id="originalFirstName" value="${selfData.firstName}">
						<label>中文姓氏：</label>
						<input type="text" name="updatedFirstName" id="updatedFirstName" size="30" maxlength="3" onblur="checkFirstName()"
							placeholder="請輸入姓氏，1~3個中文字" value="${selfData.firstName}" />
						<span id="firstNameSpan"></span>
						<hr />
						<input type="hidden" name="originalLastName" id="originalLastName" value="${selfData.lastName}">
						<label>中文名字：</label>
						<input type="text" name="updatedLastName" id="updatedLastName" size="30" maxlength="22" onblur="checkLastName()"
							placeholder="請輸入名字，1~3個中文字" value="${selfData.lastName}" />
						<span id="lastNameSpan"></span>
						<hr />
						<input type="hidden" name="originalNickname" id="originalNickname" value="${selfData.nickname}">
						<label>稱呼方式：</label>
						<input type="text" name="updatedNickname" id="updatedNickname" size="30" maxlength="25" onblur="checkNickname()"
							placeholder="請輸入想要的稱呼" value="${selfData.nickname}" />
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
						<input type="email" name="updatedEmail" id="updatedEmail" size="30" maxlength="30" onblur="checkEmail()"
						    placeholder="請輸入驗證、聯絡用的E-Mail地址" value="${selfData.email}" />
						<span id="emailSpan"></span>
						<hr />
						<div id="emailSendSpace">
							<label>信箱驗證：</label>
							<input type="text" name="emailCheckCode" id="emailCheckCode" size="30" maxlength="8" onblur="checkEmailCheckCode()"
							    placeholder="請輸入E-Mail中所收到的驗證碼" />
							<span id="emailCheckCodeSpan"></span>
							<br />
							<input type="hidden" name="inputCheckCode" id="checkCode" value="" />
							<hr />
						</div>
						<input type="hidden" name="originalPhone" id="originalPhone" value="${selfData.phone}">
						<label>聯絡電話：</label>
						<input type="tel" name="updatedPhone" id="updatedPhone" size="30" maxlength="11" onblur="checkPhone()"
						    placeholder="請輸入行動電話或市內電話號碼" value="${selfData.phone}" />
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
                		<a href="WebUserMain"><button type="button" name="update" style="font-size:18px" >取消/返回 <i class="material-icons" style="font-size:18px;color:green">undo</i></button></a>
						<button type="button" name="update" id="updateConfirm" style="font-size:18px" >資料修改完畢 <i class="material-icons" style="font-size:18px;color:blue">check</i></button>
						<button type="reset" id="reset" name="reset" style="font-size:18px" onclick="clearMessage()">重設 <i class="material-icons" style="font-size:18px;color:blue">refresh</i></button>
					</div>
					<hr />
                </form>
                <script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
                <script src="${pageContext.request.contextPath}/js/webUser/WebUserModifyData.js"></script>
                <script>
	                $(document).ready(function () {
						document.getElementById("uploadPic").style = "display:none";
						document.getElementById("picPreview").style = "display:none";
						$("#iconUrl").change(function () {
	            			if (this.files && this.files[0]) {
	            				var picReader = new FileReader();
	            				document.getElementById("picPreview").style = "display:inline";
	            				picReader.onload = function (e) {
	            					$('#picPreview').attr('src', e.target.result);
	            				};
	            				picReader.readAsDataURL(this.files[0]);
	            				var pName = this.files[0].name;
	            				var oldPNameTmp = (document.getElementById("oldIconUrl").value.trim() == '') ? '' : document.getElementById("oldIconUrl").value;
	            				var oldPName = (oldPNameTmp == '') ? '' : oldPNameTmp.split("/")[oldPNameTmp.split("/").length - 1];
	            				var picSpan = document.getElementById("picSpan");
	            				var picStr = "";
	            				
	           					picStr = "圖片已設定完畢";
	           					picSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + picStr;
			            		picSpan.style.color = "black";
			            		picSpan.style.fontStyle = "normal";
			            		document.getElementById("uploadPic").style = "display:inline";
	            			}
	            		});
					});
                	
                	$("#uploadPic").click(function() {
                		picUpload();
                	});
                	function picUpload() {
                		let choice=confirm("是否確定要上傳指定的圖片？");
                		if (choice == true) {
                			/* 停用按鈕，防止連點 */
                			document.getElementById("uploadPic").disabled = true;
                			/* 設定計時器，定時啟用 */
                			setTimeout(enableUploadBtn, 30000);
                			/* 恢復按鈕的點選 */
                			function enableUploadBtn() {
                				document.getElementById("uploadPic").disabled = false;
							}
                			var picForm = new FormData();
                			var pic = $("#iconUrl")[0].files[0];
                			picForm.append("pic", pic);
                			
                			picStr = "...處理中，請稍後";
           					picSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + picStr;
		            		picSpan.style.color = "black";
		            		picSpan.style.fontStyle = "normal";
		            		
		            		$.ajax({
		            			type:"POST",
					            url:"<c:url value='/webUser/controller/WebUserModifyIcon' />",
								data : picForm,
								contentType : false,
								processData : false,
								success:function(resultObj) {
									if (resultObj.resultCode == "true") {
										picStr = resultObj.resultMessage;
										alert(picStr);
										picSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + picStr;
										picSpan.style.color = "green";
										picSpan.style.fontStyle = "normal";
									} else {
										picStr = resultObj.resultMessage;
										alert(picStr);
										picSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + picStr;
										picSpan.style.color = "red";
										picSpan.style.fontStyle = "italic";
									}
								},
								error:function(err) {
									picStr = "發生錯誤，無法上傳";
									alert(picStr);
									picSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + picStr;
									picSpan.style.color = "red";
									picSpan.style.fontStyle = "italic";
								}
		            		});
                		}
                	}
                	
                	$("#resetDefault").click(function() {
	                	if (document.getElementById("oldIconUrl").value != "") {
		                	let choice=confirm("是否確定回復圖示設定到預設值？");
		                	if (choice == true) {
		                		/* 停用按鈕，防止連點 */
	                			document.getElementById("resetDefault").disabled = true;
	                			/* 設定計時器，定時啟用 */
	                			setTimeout(enableResetIconBtn, 30000);
	                			/* 恢復按鈕的點選 */
	                			function enableResetIconBtn() {
	                				document.getElementById("resetDefault").disabled = false;
								}
		                		picStr = "...處理中，請稍後";
	           					picSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + picStr;
			            		picSpan.style.color = "black";
			            		picSpan.style.fontStyle = "normal";
			            		
			            		$.ajax({
			            			type:"POST",
						            url:"<c:url value='/webUser/controller/WebUserResetIcon' />",
						            dataType : "json",
									success:function(resultObj) {
										if (resultObj.resultCode == "true") {
											picStr = resultObj.resultMessage;
											alert(picStr);
											picSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + picStr;
											picSpan.style.color = "green";
											picSpan.style.fontStyle = "normal";
										} else {
											picStr = resultObj.resultMessage;
											alert(picStr);
											picSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + picStr;
											picSpan.style.color = "red";
											picSpan.style.fontStyle = "italic";
										}
									},
									error:function(err) {
										picStr = "發生錯誤，無法回復預設值";
										alert(picStr);
										picSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + picStr;
										picSpan.style.color = "red";
										picSpan.style.fontStyle = "italic";
									}
			            		});
		                	}
	                	} else {
	                		picStr = "無法回復預設值！因為已經為預設圖示";
							alert(picStr);
							picSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + picStr;
							picSpan.style.color = "red";
							picSpan.style.fontStyle = "italic";
	                	}
	                });
                	
                	$("#updateConfirm").click(function() {
                		checkUpdate();
                	});
                	function checkUpdate() {
                		if (checkForm()) {
                			doUpdate();
                		} else {
                			alert("欄位檢查失敗！");
                		}
                	}
                	function doUpdate() {
                		let newFirstName = document.getElementById("updatedFirstName").value.trim();
                		let newLastName = document.getElementById("updatedLastName").value.trim();
                		let newNickname = document.getElementById("updatedNickname").value.replace('<', ' ').replace('>', '').trim();
                		let fervorObj = document.getElementsByClassName("updatedFervor");
                		let newFervor = "";
                		for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
                			if (newFervor != "" && fervorObj[fervorIndex].checked) {
                				newFervor += ",";
                			}
                			newFervor += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
                		}
                		let newEmail = document.getElementById("updatedEmail").value.replace('<', ' ').replace('>', '').trim();
                		let emailCheckCode = document.getElementById("emailCheckCode").value.trim();
                		let newPhone = document.getElementById("updatedPhone").value.trim();
                		let newGetEmail =(document.getElementById("updatedGetEmail1") == null) ? "" : document.getElementById("updatedGetEmail1").value;
                		newGetEmail = (document.getElementById("updatedGetEmail2") == null) ? "" : document.getElementById("updatedGetEmail2").value;
                		let newLocationCode = document.getElementById("updatedLocationCode").value.trim();
                		let newAddr0 = document.getElementById("updatedAddr0").value.replace('<', ' ').replace('>', '').trim();
                		let newAddr1 = document.getElementById("updatedAddr1").value.replace('<', ' ').replace('>', '').trim();
                		let newAddr2 = document.getElementById("updatedAddr2").value.replace('<', ' ').replace('>', '').trim();
                		
                		let updateSpan = document.getElementById("updatedSpan");
                		let updateResultSpan = document.getElementById("updatedResultSpan");
                		let updateStr = "...處理中，請稍後";
						let updateIsOk = true;
						
						updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + updateStr;
						updateSpan.style.color = "black";
						updateSpan.style.fontStyle = "normal";
						updateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + updateStr + "<hr />";
						updateResultSpan.style.color = "black";
						updateResultSpan.style.fontStyle = "normal";
						
						$.ajax({
							type:"POST",
							url:"<c:url value='/webUser/controller/WebUserModifyData' />",
							data:{
				            	'newFirstName':newFirstName,
								'newLastName':newLastName,
								'newNickname':newNickname,
								'newFervor':newFervor,
								'newEmail':newEmail,
								'inputCheckCode':emailCheckCode,
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
				            		updateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + updateStr + "<hr />";
				            		updateResultSpan.style.color = "black";
				            		updateResultSpan.style.fontStyle = "normal";
				            	} else {
				            		updateStr = resultObj.resultMessage;
					            	updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updateStr;
					            	updateSpan.style.color = "red";
					            	updateSpan.style.fontStyle = "italic";
					            	updateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updateStr + "<hr />";
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
				            	updateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updateStr + "<hr />";
				            	updateResultSpan.style.color = "red";
				            	updateResultSpan.style.fontStyle = "italic";
				            }
						});
                	}
                
					function checkUpdateNickname() {
						let nickname = document.getElementById("updatedNickname").value.replace('<', ' ').replace('>', '').trim();
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
                
					function checkUpdateEmail() {
						let email = document.getElementById("updatedEmail").value.replace('<', ' ').replace('>', '').trim();
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
				            		let choice=confirm("是否要寄往 " + email + " ?");
				            		if (choice) {
				            			sendTestEmail();
				            		}
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
					
					function sendTestEmail() {
						let mode = "sendCheckCode";
						let email = document.getElementById("updatedEmail").value.trim();
						let account = document.getElementById("account").value.trim();
						let checkCode = document.getElementById("checkCode");
						let emailCheckCodeSpan = document.getElementById("emailCheckCodeSpan");
						let checkCodeStr;
						let emailCheckCodeStr = "...處理中，請稍後";
						let emailCheckCodeIsOk = true;
						
						emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + emailCheckCodeStr;
						emailCheckCodeSpan.style.color = "black";
						emailCheckCodeSpan.style.fontStyle = "normal";
						
						$.ajax({
							type:"POST",
							url:"<c:url value='/webUser/controller/UserInfoController' />",
							data:{
				            	'register':mode,
				            	'inputEmail':email,
				            	'inputAccount':account
				            },
				            dataType:"json",
				            success:function(resultObj) {
				            	if (resultObj.resultCode == "true") {
				            		emailCheckCodeStr = resultObj.resultMessage;
				            		alert(emailCheckCodeStr);
				            		emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailCheckCodeStr;
				            		emailCheckCodeSpan.style.color = "black";
				            		emailCheckCodeSpan.style.fontStyle = "normal";
				            		checkCodeStr = resultObj.resultText;
				            		document.getElementById("checkCode").value = checkCodeStr;
				            	} else if (resultObj.resultCode == "false") {
				            		emailCheckCodeStr = resultObj.resultMessage;
				            		alert(emailCheckCodeStr);
				            		emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailCheckCodeStr;
					            	emailCheckCodeSpan.style.color = "red";
					            	emailCheckCodeSpan.style.fontStyle = "italic";
				            	}
				            },
				            error:function(err) {
				            	emailCheckCodeStr = "發生錯誤，無法寄出測試信";
				            	alert(emailCheckCodeStr);
				            	emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailCheckCodeStr;
				            	emailCheckCodeSpan.style.color = "red";
				            	emailCheckCodeSpan.style.fontStyle = "italic";
				            }
						});
					}
				</script>
            </div>
<!-- -------------------------------------------------------------------- -->
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:20px">
            	<%@include file = "../Footer-Include-prototype.jsp" %>
            </div>
</body>
</html>