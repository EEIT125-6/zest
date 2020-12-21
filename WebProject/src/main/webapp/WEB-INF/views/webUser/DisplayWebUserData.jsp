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
				<c:if test="${empty userFullData}">
					<c:redirect url="WebUserLogin" />
				</c:if>
				<form action="<c:url value='/webUser/controller/DisplayWebUserData' />" method="post">
					<fieldset>
						<legend id="getDataSpan"></legend>
						<div id="dataContainer"></div>
					</fieldset>
					<div align="center">
						<input type="submit" name="update" value="修改其他資料">
						<a href="controller/WebUserModifyPassword"><input type="button" name="update" value="修改密碼"></a>
						<a href="WebUserMain"><input type="button" name="select" value="返回主畫面"></a>
					</div>
					<hr />
				</form>
				<script>
					window.onload = function() {
						getSelfData();
					};
					
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
						            		alert(resultObj.resultMessage);
										}
										if (!getDataIsOk) {
											getDataSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + getDataStr;
											getDataSpan.style.color = "red";
											getDataSpan.style.fontStyle = "italic";
						            	} else {
						            		getDataSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + getDataStr;
						            		getDataSpan.style.color = "black";
						            		getDataSpan.style.fontStyle = "normal";
						            		
						            		let passwordLength = resultObj.selfData.password.length;
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
						            		
						            		let content = "<hr /><label>帳號名稱：" + resultObj.selfData.account + "</label>"
						            						+ "<hr /><label>帳號密碼：" + coveredPassword + "</label>"
						            						+ "<hr /><label>中文姓氏：" + resultObj.selfData.firstName + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "firstName" + " id=" + "firstName" + " value=" + resultObj.selfData.firstName + ">"
						            						+ "<hr /><label>中文姓名：" + resultObj.selfData.lastName + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "lastName" + " id=" + "lastName" + " value=" + resultObj.selfData.lastName + ">"
						            						+ "<hr /><label>稱呼方式：" + resultObj.selfData.nickname + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "nickname" + " id=" + "nickname" + " value=" + resultObj.selfData.nickname + ">"
						            						+ "<hr /><label>生理性別：" + resultObj.selfData.gender.genderText + "</label>"
						            						+ "<hr /><label>西元生日：" + resultObj.selfData.birth + "</label>"
						            						+ "<hr /><label>偏好食物：" + resultObj.selfData.fervor + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "fervor" + " id=" + "fervor" + " value=" + resultObj.selfData.fervor + ">"
						            						+ "<hr /><label>聯絡信箱：" + resultObj.selfData.email + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "email" + " id=" + "email" + " value=" + resultObj.selfData.email + ">"
						            						+ "<hr /><label>聯絡電話：" + resultObj.selfData.phone + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "phone" + " id=" + "phone" + " value=" + resultObj.selfData.phone + ">"
						            						+ "<hr /><label>是否願意接收促銷/優惠訊息：" + resultObj.selfData.getEmail.willingText + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "getEmail" + " id=" + "getEmail" + " value=" + resultObj.selfData.getEmail.willingCode + ">"
						            						+ "<hr /><label>居住區域：" + resultObj.selfData.locationInfo.cityName + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "locationCode" + " id=" + "locationCode" + " value=" + resultObj.selfData.locationInfo.cityCode + ">"
						            						+ "<hr /><label>生活地點一：" + resultObj.selfData.addr0 + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "addr0" + " id=" + "addr0" + " value=" + resultObj.selfData.addr0 + ">"
						            						+ "<hr /><label>生活地點二：" + resultObj.selfData.addr1 + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "addr1" + " id=" + "addr1" + " value=" + resultObj.selfData.addr1 + ">"
						            						+ "<hr /><label>生活地點三：" + resultObj.selfData.addr2 + "</label>"
						            						+ "<input type=" + "hidden" + " name=" + "addr2" + " id=" + "addr2" + " value=" + resultObj.selfData.addr2 + ">"
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
      		<div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:20px">
      		<%@include file = "../Footer-Include-prototype.jsp" %>
</body>
</html>      