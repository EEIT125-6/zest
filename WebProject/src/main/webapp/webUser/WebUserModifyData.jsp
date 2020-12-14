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
    <link rel="stylesheet" href="styles/WebUserRegisterForm.css">
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
                <jsp:useBean id="userFullData" class="webUser.model.WebUserData"
					scope="session" />
				<c:if test="${userFullData.password == null}">
					<c:redirect url="WebUserLogin.jsp" />
				</c:if>
                <form action="/WebProject/webUser/WebUserServlet" method="post" onSubmit="return checkForm();">
                	<fieldset>
                		<legend>可修改的個人相關資料</legend>
                		<span id="updateResultMessageSpan">
                		<c:if test="${updateResultMessage != null}">
							<c:if test="${updateResultMessage != '更新操作順利完成'}">
								<i class='material-icons' style='font-size:18px;color:red'>cancel</i>
								<c:out value="${updateResultMessage}" />
							</c:if>
						</c:if>
						<c:if test="${updateResultMessage == null}">
						</c:if>
						</span>
                		<hr />
                		<input type="hidden" name="originalFirstName" id="originalFirstName" value="${originalData.firstName}">
						<label>中文姓氏：</label>
						<input type="text" name="updatedFirstName" id="updatedFirstName" size="40" maxlength="3" onblur="checkFirstName()"
							placeholder="請輸入姓氏，1~3個中文字" value="${originalData.firstName}" />
						<span id="firstNameSpan"></span>
						<hr />
						<input type="hidden" name="originalLastName" id="originalLastName" value="${originalData.lastName}">
						<label>中文名字：</label>
						<input type="text" name="updatedLastName" id="updatedLastName" size="40" maxlength="3" onblur="checkLastName()"
							placeholder="請輸入名字，1~3個中文字" value="${originalData.lastName}" />
						<span id="lastNameSpan"></span>
						<hr />
						<input type="hidden" name="originalNickname" id="originalNickname" value="${originalData.nickname}">
						<label>稱呼方式：</label>
						<input type="text" name="updatedNickname" id="updatedNickname" size="40" maxlength="20" onblur="checkNickname()"
							placeholder="請輸入想要的稱呼" value="${originalData.nickname}" />
						<input type="button" name="update" id="checkNicknameUsed" value="檢查稱呼">
						<span id="nicknameSpan"></span>
						<hr />
						<input type="hidden" name="originalFervor" id="originalFervor" value="${originalData.fervor}">
						<label>偏好食物：</label>
						<c:choose>
							<c:when test="${originalData.fervor.indexOf('中式')!=-1}">
								<input type="checkbox" id="updatedFervor1" name="updatedFervor" value="中式" 
									onblur="checkFervor()" checked="checked" />
							</c:when>
							<c:when test="${originalData.fervor.indexOf('中式')==-1}">
								<input type="checkbox" id="updatedFervor1" name="updatedFervor" value="中式" 
									onblur="checkFervor()" />
							</c:when>
						</c:choose>
						<label>中式</label>
						<c:choose>
							<c:when test="${originalData.fervor.indexOf('快餐')!=-1}">
								<input type="checkbox" id="updatedFervor2" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" checked="checked" />
							</c:when>
							<c:when test="${originalData.fervor.indexOf('快餐')==-1}">
								<input type="checkbox" id="updatedFervor2" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" />
							</c:when>
						</c:choose>
						<label>快餐</label>
						<c:choose>
							<c:when test="${originalData.fervor.indexOf('燒肉')!=-1}">
								<input type="checkbox" id="updatedFervor3" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" checked="checked" />
							</c:when>
							<c:when test="${originalData.fervor.indexOf('燒肉')==-1}">
								<input type="checkbox" id="updatedFervor3" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" />
							</c:when>
						</c:choose>
						<label>燒肉</label>
						<c:choose>
							<c:when test="${originalData.fervor.indexOf('西式')!=-1}">
								<input type="checkbox" id="updatedFervor4" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" checked="checked" />
							</c:when>
							<c:when test="${originalData.fervor.indexOf('西式')==-1}">
								<input type="checkbox" id="updatedFervor4" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" />
							</c:when>
						</c:choose>
						<label>西式</label>
						<c:choose>
							<c:when test="${originalData.fervor.indexOf('下午茶')!=-1}">
								<input type="checkbox" id="updatedFervor5" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" checked="checked" />
							</c:when>
							<c:when test="${originalData.fervor.indexOf('下午茶')==-1}">
								<input type="checkbox" id="updatedFervor5" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" />
							</c:when>
						</c:choose>
						<label>下午茶</label>
						<c:choose>
							<c:when test="${originalData.fervor.indexOf('日式')!=-1}">
								<input type="checkbox" id="updatedFervor6" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" checked="checked" />
							</c:when>
							<c:when test="${originalData.fervor.indexOf('日式')==-1}">
								<input type="checkbox" id="updatedFervor6" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" />
							</c:when>
						</c:choose>
						<label>日式</label>
						<c:choose>
							<c:when test="${originalData.fervor.indexOf('皆可')!=-1}">
								<input type="checkbox" id="updatedFervor0" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" checked="checked" />
							</c:when>
							<c:when test="${originalData.fervor.indexOf('皆可')==-1}">
								<input type="checkbox" id="updatedFervor0" name="updatedFervor" value="快餐" 
									onblur="checkFervor()" />
							</c:when>
						</c:choose>
						<label>皆可</label>
						<span id="fervorSpan"></span>
						<hr />
						<input type="hidden" name="originalEmail" id="originalEmail" value="${originalData.email}">
						<label>聯絡信箱：</label>
						<input type="email" name="updatedEmail" id="updatedEmail" size="40" maxlength="30" onblur="checkEmail()"
						    placeholder="請輸入驗證、聯絡用的E-Mail地址" value="${originalData.email}" />
						<input type="button" name="update" id="checkEmailUsed" value="檢查信箱">
						<span id="emailSpan"></span>
						<hr />
						<input type="hidden" name="originalPhone" id="originalPhone" value="${originalData.phone}">
						<label>聯絡電話：</label>
						<input type="tel" name="updatedPhone" id="updatedPhone" size="40" maxlength="11" onblur="checkPhone()"
						    placeholder="請輸入行動電話或市內電話號碼" value="${originalData.phone}" />
						<input type="button" name="update" id="checkPhoneUsed" value="檢查電話">
						<span id="phoneSpan"></span>
					    <hr />
					    <input type="hidden" name="originalGetEmail" id="originalGetEmail" value="${originalData.getEmail}">
					    <label>接收促銷/優惠意願：</label>
					    <c:choose>
					    	<c:when test="${originalData.getEmail=='Y'}">
								<input type="radio" id="updatedGetEmail1" name="updatedGetEmail" value="Y" 
								onblur="checkGetEmail()" checked="checked">
							</c:when>
							<c:when test="${originalData.getEmail!='Y'}">
								<input type="radio" id="updatedGetEmail1" name="updatedGetEmail" value="Y" 
									onblur="checkGetEmail()" >
							</c:when>
						</c:choose>
					    <label for="Y">願意</label>
					    <c:choose>
					    	<c:when test="${originalData.getEmail!='N'}">
							    <input type="radio" id="updatedGetEmail2" name="updatedGetEmail" value="N" 
							    	onblur="checkGetEmail()" >
						    </c:when>
						    <c:when test="${originalData.getEmail=='N'}">
							    <input type="radio" id="updatedGetEmail2" name="updatedGetEmail" value="N" 
							    	onblur="checkGetEmail()" checked="checked">
						    </c:when>
					    </c:choose>
					    <label for="N">不願意</label>
					    <span id="getEmailSpan"></span>
						<hr />
						<input type="hidden" name="originalLocationCode" id="originalLocationCode" value="${originalData.locationCode}">
					    <label>居住區域：</label>
				    	<select name="updatedLocationCode" id="updatedLocationCode" onblur="checkLocationCode()">
						    <option value="t01"
		                        <c:if test="${originalData.locationCode=='t01'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='臺北市' />
	                        </option>
	                        
	                        <option value="t02"
		                        <c:if test="${originalData.locationCode=='t02'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='新北市' />
	                        </option>
	                        
	                        <option value="t03"
		                        <c:if test="${originalData.locationCode=='t03'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='桃園市' />
	                        </option>
	                        
	                        <option value="t04"
		                        <c:if test="${originalData.locationCode=='t04'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='臺中市' />
	                        </option>
							
							<option value="t05"
		                        <c:if test="${originalData.locationCode=='t05'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='臺南市' />
	                        </option>
	                        
	                        <option value="t06"
		                        <c:if test="${originalData.locationCode=='t06'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='高雄市' />
	                        </option>
	                        
	                        <option value="t07"
		                        <c:if test="${originalData.locationCode=='t07'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='基隆市' />
	                        </option>
	                        
	                        <option value="t08"
		                        <c:if test="${originalData.locationCode=='t08'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='新竹市' />
	                        </option>
	                        
	                        <option value="t09"
		                        <c:if test="${originalData.locationCode=='t09'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='嘉義市' />
	                        </option>
	                        
	                        <option value="t10"
		                        <c:if test="${originalData.locationCode=='t10'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='新竹縣' />
	                        </option>
	                        
	                        <option value="t11"
		                        <c:if test="${originalData.locationCode=='t11'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='苗栗縣' />
	                        </option>
	                        
	                        <option value="t12"
		                        <c:if test="${originalData.locationCode=='t12'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='彰化縣' />
	                        </option>
	                        
	                        <option value="t13"
		                        <c:if test="${originalData.locationCode=='t13'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='南投縣' />
	                        </option>
	                        
	                        <option value="t14"
		                        <c:if test="${originalData.locationCode=='t14'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='雲林縣' />
	                        </option>
	                        
	                        <option value="t15"
		                        <c:if test="${originalData.locationCode=='t15'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='嘉義縣' />
	                        </option>
	                        
	                        <option value="t16"
		                        <c:if test="${originalData.locationCode=='t16'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='屏東縣' />
	                        </option>
	                        
	                        <option value="t17"
		                        <c:if test="${originalData.locationCode=='t17'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='宜蘭縣' />
	                        </option>
	                        
	                        <option value="t18"
		                        <c:if test="${originalData.locationCode=='t18'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='花蓮縣' />
	                        </option>
	                        
	                        <option value="t19"
		                        <c:if test="${originalData.locationCode=='t19'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='臺東縣' />
	                        </option>
	                        
	                        <option value="t20"
		                        <c:if test="${originalData.locationCode=='t20'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='澎湖縣' />
	                        </option>
	                        
	                        <option value="t21"
		                        <c:if test="${originalData.locationCode=='t21'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='金門縣' />
	                        </option>
	                        
	                        <option value="t22"
		                        <c:if test="${originalData.locationCode=='t22'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='連江縣' />
	                        </option>
	                        
	                        <option value="t23"
		                        <c:if test="${originalData.locationCode=='t23'}">
		                         	selected="selected"
		                    	</c:if>
	                        >
	                        	<c:out value='其他區' />
	                        </option>
						</select>
						<span id="locationCodeSpan"></span>
					    <hr />
					    <input type="hidden" name="originalAddr0" id="originalAddr0" value="${originalData.addr0}">
					    <label>生活地點一：</label>
					    <input type="text" name="updatedAddr0" id="updatedAddr0" size="65" maxlength="65" onblur="checkAddr0()"
						    placeholder="此項為必填，請輸入完整地址方面後續服務之利用" value="${originalData.addr0}" />
						<br />
						<span id="addr0Span"></span>
					    <hr />
					    <input type="hidden" name="originalAddr1" id="originalAddr1" value="${originalData.addr1}">
					    <label>生活地點二：</label>
					    <input type="text" name="updatedAddr1" id="updatedAddr1" size="65" maxlength="65" onblur="checkAddr1()"
						    placeholder="此項為選填，請輸入完整地址方面後續服務之利用" value="${originalData.addr1}" />
						<br />
						<span id="addr1Span"></span>
					    <hr />
					    <input type="hidden" name="originalAddr2" id="originalAddr2" value="${originalData.addr2}">
					    <label>生活地點三：</label>
					    <input type="text" name="updatedAddr2" id="updatedAddr2" size="65" maxlength="65" onblur="checkAddr2()"
						    placeholder="此項為選填，請輸入完整地址方面後續服務之利用" value="${originalData.addr2}" />
						<br />
						<span id="addr2Span"></span>
					    <hr />
					    <span id="updatedSpan"></span>
                	</fieldset>
                	<div align="center">
                		<a href="WebUserMain.jsp"><input type="button" name="update" value="取消"></a>
						<input type="submit" name="update" value="資料修改完畢">
						<input type="reset" name="reset" value="重設" onclick="clearMessage()">
					</div>
					<hr />
                </form>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="scripts/WebUserModifyData.js"></script>
                <script>	
	                $("#checkNicknameUsed").click(function () {
				        checkUpdateNickname();
				    });
					function checkUpdateNickname(){
						let nickname = document.getElementById("nickname").value.trim();
						let nicknameSpan = document.getElementById("nicknameSpan");
						let nicknameStr;
						let nicknameIsOk = true;
						
						$.ajax({
							type:"POST",
				            url:"/WebProject/webUser/WebUserServlet",
				            data:{
				            	'update':'檢查稱呼',
				            	'inputNickname':nickname
				            },
				            success:function(result) {
				            	let resultSpace = result.split(",");
				            	if(resultSpace[0] == '1') {
				            		nicknameStr = "此稱呼已有人使用！";
				            		nicknameIsOk = false;
				            	} else if(resultSpace[0] == '0') {
				            		nicknameStr = "可使用此稱呼！";
				            		nicknameIsOk = true;
				            	} else if(resultSpace[0] == '-1') {
				            		nicknameStr = "檢查途中遭遇錯誤！";
				            		nicknameIsOk = false;
				            		/* 顯示彈窗異常訊息 */
				            		alert(resultSpace[1]);
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
                
					$("#checkEmailUsed").click(function () {
				        checkUpdateEmail();
				    });
					function checkUpdateEmail(){
						let email = document.getElementById("updatedEmail").value.trim();
						let emailSpan = document.getElementById("emailSpan");
						let emailStr;
						let emailIsOk = true;
						
						$.ajax({
							type:"POST",
				            url:"/WebProject/webUser/WebUserServlet",
				            data:{
				            	'update':'檢查信箱',
				            	'inputEmail':email
				            },
				            success:function(result) {
				            	let resultSpace = result.split(",");
				            	if(resultSpace[0] == '1') {
				            		emailStr = "此電子信箱已有人使用！";
				            		emailIsOk = false;
				            	} else if(resultSpace[0] == '0') {
				            		emailStr = "可使用此電子信箱！";
				            		emailIsOk = true;
				            	} else if(resultSpace[0] == '-1') {
				            		emailStr = "檢查途中遭遇錯誤！";
				            		emailIsOk = false;
				            		/* 顯示彈窗異常訊息 */
				            		alert(resultSpace[1]);
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
					
					$("#checkPhoneUsed").click(function () {
				        checkUpdatePhone();
				    });
					function checkUpdatePhone(){
						let phone = document.getElementById("updatedPhone").value.trim();
						let phoneSpan = document.getElementById("phoneSpan");
						let phoneStr;
						let phoneIsOk = true;
						
						$.ajax({
							type:"POST",
				            url:"/WebProject/webUser/WebUserServlet",
				            data:{
				            	'update':'檢查電話',
				            	'inputPhone':phone
				            },
				            success:function(result) {
				            	let resultSpace = result.split(",");
				            	if(resultSpace[0] == '1') {
				            		phoneStr = "此聯絡電話已有人使用！";
				            		phoneIsOk = false;
				            	} else if(resultSpace[0] == '0') {
				            		phoneStr = "可使用此聯絡電話！";
				            		phoneIsOk = true;
				            	} else if(resultSpace[0] == '-1') {
				            		phoneStr = "檢查途中遭遇錯誤！";
				            		phoneIsOk = false;
				            		/* 顯示彈窗異常訊息 */
				            		alert(resultSpace[1]);
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