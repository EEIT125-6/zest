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
	<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoadingScreen.css"> 
   	<link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
	<%@include file = "../Link_Meta-Include.jsp" %>
    <title>已登入</title>
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
				<input type="hidden" id="password" value="${userFullData.password}" />
                <form action="<c:url value='/webUser/WebUserModifyData' />" method="POST">
                	<fieldset>
                		<legend>
                			${userFullData.account} 
                			<c:if test="${userFullData.accountLv.lv == -1}">
                				<c:out value="-管理員" />
                			</c:if>
                			<c:if test="${userFullData.accountLv.lv == 1}">
                				<c:out value="-店家" />
                			</c:if>
            			</legend>
               			<hr />
               			<button type="submit" id="updateData" style="font-size:18px" >修改資料 <i class="material-icons" style="font-size:18px;color:blue">build</i></button>
                		<c:if test="${userFullData.accountLv.lv == 1}" >
                			<a href="<c:url value='/Insert' />">
                				<button type="button" id="addShop" name="addShop" style="font-size:18px" >新增店家 <i class="material-icons" style="font-size:18px;color:blue">add</i></button>
                			</a>
                		</c:if>
                		<c:if test="${userFullData.password != null}">
	                		<a href="<c:url value='/webUser/controller/WebUserModifyPassword' />">
	                			<button type="button" name="updatePassword" style="font-size:18px" >修改密碼 <i class="material-icons" style="font-size:18px;color:red">security</i></button>
	               			</a>
               			</c:if>
                		<a href="<c:url value='/booking/Page1' />">
                			<button type="button" id="checkBooking" name="checkBooking" style="font-size:18px" >查詢訂位 <i class="material-icons" style="font-size:18px;color:blue">import_contacts</i></button>
                		</a>
                		<a href="<c:url value='/orange/ShowComment' />">
                			<button type="button" id="checkComment" name="checkComment" style="font-size:18px" >查詢留言 <i class="material-icons" style="font-size:18px;color:blue">forum</i></button>
                		</a>
                		
<!--                 	<button type="button" id="myFavorite" name="myFavorite" style="font-size:18px" >我的最愛 <i class="material-icons" style="font-size:18px;color:blue">favorite</i></button> -->
                		
                		<c:if test="${userFullData.accountLv.lv == 1}" >
                			<a href="<c:url value='/booking/admin2'/>">
                				<button type="button" id="manage" name="manage" style="font-size:18px" >管理訂位 <i class="material-icons" style="font-size:18px;color:blue">cake</i></button>
                			</a>
                		</c:if>
                		<a href="<c:url value='/webUser/controller/WebUserMain/Logout' />">
                			<button type="button" id="logout" name="login" style="font-size:18px" >登出帳戶 <i class="material-icons" style="font-size:18px;color:green">power</i></button>
                		</a>
						<hr />
                	</fieldset>
                </form>
                
                <div id="showInfoPage">
                	<form action="<c:url value='/webUser/WebUserModifyData' />" method="post">
						<fieldset>
							<legend id="getDataSpan"></legend>
							<div id="dataContainer"></div>
						</fieldset>
					</form>
                </div>
				<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
                <script>
                	window.onload = function () {
                		let logOutBtn = document.getElementById("logout");
                		
                		logOutBtn.onclick = function() {
        					let account = (document.getElementById("account").value == null) ? "訪客" : document.getElementById("account").value;
							swal("謝謝您的使用，" + account + " ！", "", "success");
        				};
        				
        				getSelfData();
                	}
                	
                	function doubleCheck() {
                		let highPrevPassword = prompt("如要繼續進行操作，請輸入您的密碼：", "");
                		let inputPassword = document.getElementById("password").value;
                		if (highPrevPassword != inputPassword) {
                			return false;
                		} 
                		return true;
                	}
                	
                	function getSelfData() {
						let getDataSpan = document.getElementById("getDataSpan");
						let getDataStr = "...處理中，請稍後";
						let getDataIsOk = true;
						let dataContainer = document.getElementById("dataContainer");
						
						getDataSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>" + getDataStr;
						getDataSpan.style.color = "black";
						getDataSpan.style.fontStyle = "normal";
						
						let xhrObject = new XMLHttpRequest();
						if (xhrObject != null) {
							xhrObject.open("POST", "<c:url value='/webUser/controller/DisplaySelfData' />", true);
							xhrObject.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
							xhrObject.send();
							
							xhrObject.onreadystatechange = function() {
								if (xhrObject.readyState === 4 && xhrObject.status === 200) {
									let typeObject = xhrObject.getResponseHeader("Content-Type");
									if (typeObject.indexOf("application/json") === 0) {
										let resultObj = JSON.parse(xhrObject.responseText);
										if (resultObj.resultCode == 1) {
											getDataStr = resultObj.resultMessage;
										} else {
											getDataStr = "取得資料途中遭遇錯誤！";
											/* 顯示彈窗異常訊息 */
											swal(resultObj.resultMessage, "", "error");
										}
										if (!getDataIsOk) {
											getDataSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + getDataStr;
											getDataSpan.style.color = "red";
											getDataSpan.style.fontStyle = "italic";
						            	} else {
						            		getDataSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + getDataStr;
						            		getDataSpan.style.color = "black";
						            		getDataSpan.style.fontStyle = "normal";
						            		
						            		let passwordLength = (resultObj.selfData.password == null) ? 0 : resultObj.selfData.password.length;
						            		let coveredPassword = "";
						            		for (let lengthIndex = 0; lengthIndex < passwordLength; lengthIndex++) {
												coveredPassword += "*";						            			
						            		}
						            		let accountStatus = resultObj.selfData.status;
						            		switch(accountStatus) {
						            			case "inactive":
						            				accountStatus = "尚未啟用";
						            				break;
						            			case "active":
						            				accountStatus = "已啟用";
						            				break;
						            			case "quit":
						            				accountStatus = "已停用";
						            				break;
						            		}
						            		
						            		let content = "";
						            		
						            		if (resultObj.selfData.iconUrl != '') {
						            			content = "<hr /><label>帳號圖示：</label><br /><img src='${pageContext.request.contextPath}"+ resultObj.selfData.iconUrl +"' width='200' height='200' title='這是您目前的帳號圖示'>" 
						            		} else {
						            			content = "<hr /><label>帳號圖示：</label><br /><img src='${pageContext.request.contextPath}/images/webUser/defaultIcon/ncu_scens.jpg' width='200' height='200' title='這是系統預設的帳號圖示'>"
						            		}
						            		
						            		content += "<hr /><label>帳號名稱：" + resultObj.selfData.account + "</label>";
						            		
						            		content = (passwordLength == 0) ? content : content + "<hr /><label>帳號密碼：" + coveredPassword + "</label>"
						            		
						            		content += "<hr /><label>中文姓氏：" + resultObj.selfData.firstName + "</label>"
				            						+ "<hr /><label>中文姓名：" + resultObj.selfData.lastName + "</label>"
				            						+ "<hr /><label>稱呼方式：" + resultObj.selfData.nickname + "</label>"
				            						+ "<hr /><label>生理性別：" + resultObj.selfData.gender.genderText + "</label>"
				            						+ "<hr /><label>西元生日：" + resultObj.birthday + "</label>"
				            						+ "<hr /><label>偏好食物：" + resultObj.selfData.fervor + "</label>"
				            						+ "<hr /><label>聯絡信箱：" + resultObj.selfData.email + "</label>"
				            						+ "<hr /><label>聯絡電話：" + resultObj.selfData.phone + "</label>"
				            						+ "<hr /><label>是否願意接收促銷/優惠訊息：" + resultObj.selfData.getEmail.willingText + "</label>"
				            						+ "<hr /><label>居住區域：" + resultObj.selfData.locationInfo.cityName + "</label>"
				            						+ "<hr /><label>生活地點一：" + resultObj.selfData.addr0 + "</label>"
				            						+ "<hr /><label>生活地點二：" + resultObj.selfData.addr1 + "</label>"
				            						+ "<hr /><label>生活地點三：" + resultObj.selfData.addr2 + "</label>"
				            						+ "<hr /><label>所擁有的橙幣：" + resultObj.selfData.zest + "</label>"
				            						+ "<hr /><label>帳號狀態：" + accountStatus + "</label>"
				            						+ "<hr />";
						            						
						            		dataContainer.innerHTML = content;
						            	}
									} else {
										getDataStr = "發生錯誤，無法取得使用者個人資料";
										getDataSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + getDataStr;
										getDataSpan.style.color = "red";
										getDataSpan.style.fontStyle = "italic";
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
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:120px">
            	<%@include file = "../Footer-Include-prototype.jsp" %>
            </div>
</body>
</html>