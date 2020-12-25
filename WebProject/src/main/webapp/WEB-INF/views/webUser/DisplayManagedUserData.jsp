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
							<c:if test="${managedUserData.password.length() > 0}">
								<c:forEach var="passwordChar" begin="0" end="${managedUserData.password.length()-1}">
									<c:out value = "*" />
								</c:forEach>
							</c:if>
							<hr />
							<label>中文姓氏：</label>
							<c:out value="${managedUserData.firstName}" />
							<input type="hidden" name="firstName" id="firstName" value="${managedUserData.firstName}">
							<hr />
							<label>中文名字：</label>
							<c:out value="${managedUserData.lastName}" />
							<input type="hidden" name="lastName" id="lastName" value="${managedUserData.lastName}">
							<hr />
							<label>稱呼方式：</label>
							<c:out value="${managedUserData.nickname}" />
							<input type="hidden" name="nickname" id="nickname" value="${managedUserData.nickname}">
							<hr />
							<label>生理性別：</label>
							<c:out value="${managedUserData.gender.genderText}" />
							<hr />
							<label>西元生日：</label>
							<c:out value="${managedUserData.birth}" />
							<hr />
							<label>偏好食物：</label>
							<c:out value="${managedUserData.fervor}" />
							<input type="hidden" name="fervor" id="fervor" value="${managedUserData.fervor}">
							<hr />
							<label>聯絡信箱：</label>
							<c:out value="${managedUserData.email}" />
							<input type="hidden" name="email" id="email" value="${managedUserData.email}">
							<hr />
							<label>聯絡電話：</label>
							<c:out value="${managedUserData.phone}" />
							<input type="hidden" name="phone" id="phone" value="${managedUserData.phone}">
							<hr />
							<label>是否願意接收促銷/優惠訊息：</label>
							<c:out value="${managedUserData.getEmail.willingText}" />
							<input type="hidden" name="getEmail" id="getEmail" value="${managedUserData.getEmail.willingCode}">
							<hr />
							<label>居住區域：</label>
							<c:out value="${managedUserData.locationInfo.cityName}" />
							<input type="hidden" name="locationCode" id="locationCode" value="${managedUserData.locationInfo.cityCode}">
							<hr />
							<label>生活地點一：</label>
							<c:out value="${managedUserData.addr0}" />
							<input type="hidden" name="addr0" id="addr0" value="${managedUserData.addr0}">
							<hr />
							<label>生活地點二：</label>
							<c:out value="${managedUserData.addr1}" />
							<input type="hidden" name="addr1" id="addr1" value="${managedUserData.addr1}">
							<hr />
							<label>生活地點三：</label>
							<c:out value="${managedUserData.addr2}" />
							<input type="hidden" name="addr2" id="addr2" value="${managedUserData.addr2}">
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
						<c:choose>
							<c:when test="${managedUserData.status=='inactive'}">
								<input type="button" id="activeAccount" name="update" value="啟用帳號">
							</c:when>
							<c:when test="${managedUserData.status=='active'}">
								<input type="button" id="quitAccount" name="update" value="停用帳號">
							</c:when>
							<c:when test="${managedUserData.status=='quit'}">
								<input type="button" id="reactiveAccount" name="update" value="恢復帳號">
							</c:when>
						</c:choose>
						<input type="button" id="updateAccount" name="update" value="編輯帳號(尚未完成)">
						<input type="button" id="deleteAccount" name="delete" value="刪除帳號">
						<a href="WebUserSearchForm"><input type="button" name="select" value="返回上一頁"></a>
						<hr />
						<span id="operatResult"></span>
					</div>
				</form>
				<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
				<script>
					$("#activeAccount").click(function () {
						var mode = "active";
						lastCheck(mode);
				    });
					$("#quitAccount").click(function () {
						var mode = "quit";
						lastCheck(mode);
				    });
					$("#reactiveAccount").click(function () {
						var mode = "reactive";
						lastCheck(mode);
				    });
					$("#deleteAccount").click(function () {
						var mode = "delete";
						lastCheck(mode);
				    });
					function lastCheck(mode) {
						let choice=confirm("是否要執行特定的操作？");
						if (choice) {
							let userId = document.getElementById("userId").value;
							let account = document.getElementById("account").value;
							let status = document.getElementById("status").value;
							
							let operateResultSpan = document.getElementById("operateResult");
							let operateResultStr = "...處理中，請稍後";
							let operateResultIsOk = true;
							
							operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>autorenew</i>"
								+ operateResultStr;
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
										/* 顯示彈窗異常訊息 */
										alert(resultObj.resultMessage);
									} else if (resultObj.resultCode == 0) {
										operateResultStr = resultObj.resultMessage;
										operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
												+ operateResultStr;
										operateResultSpan.style.color = "black";
										operateResultSpan.style.fontStyle = "normal";
										/* 顯示彈窗異常訊息 */
										alert(resultObj.resultMessage);
									} else if (resultObj.resultCode == -1) {
										operateResultStr = resultObj.resultMessage;
										operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
												+ operateResultStr;
										operateResultSpan.style.color = "black";
										operateResultSpan.style.fontStyle = "normal";
										/* 顯示彈窗異常訊息 */
										alert(resultObj.resultMessage);
									}
								},
								error : function(err) {
									operateResultStr = "發生錯誤，無法執行指定的操作！";
									operateResultSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
											+ operateResultStr;
									operateResultSpan.style.color = "black";
									operateResultSpan.style.fontStyle = "normal";
									/* 顯示彈窗異常訊息 */
									alert(resultObj.resultMessage);
								}
							});
						}
					}
				</script>
            </div>
<!-- -------------------------------------------------------------------- -->
            <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:20px">
            <%@include file = "../Footer-Include-prototype.jsp" %>
</body>
</html>