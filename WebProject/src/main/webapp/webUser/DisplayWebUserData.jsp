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
            <%@include file = "../Header-Include-prototype.jsp" %>
<!-- -------------------------------------------------------------- -->
            <div class="container"  style="margin-top: 20px;">
		        <!-- 將放於Session中的JavaBean取出，class寫包含package的全名，scope設為session -->
				<jsp:useBean id="userFullData" class="webUser.model.WebUserData"
					scope="session" />
				<c:if test="${empty selfData}">
					<c:redirect url="WebUserLogin.jsp" />
				</c:if>
				<form action="/WebProject/webUser/WebUserServlet" method="post">
					<fieldset>
						<c:if test="${updateResultMessage != null}">
							<legend><c:out value="${updateResultMessage}" /></legend>
						</c:if>
						<c:if test="${updateResultMessage == null}">
							<legend><c:out value="${getResultMessage}" /></legend>
						</c:if>
						<hr />
							<label>帳號名稱：</label>
							<c:out value="${selfData.account}" />
							<hr />
							<label>帳號密碼：</label>
							<c:if test="${selfData.password.length() > 0}">
								<c:forEach var="passwordChar" begin="0" end="${selfData.password.length()-1}">
									<c:out value = "*" />
								</c:forEach>
							</c:if>
							<hr />
							<label>中文姓氏：</label>
							<c:out value="${selfData.firstName}" />
							<input type="hidden" name="firstName" id="firstName" value="${selfData.firstName}">
							<hr />
							<label>中文名字：</label>
							<c:out value="${selfData.lastName}" />
							<input type="hidden" name="lastName" id="lastName" value="${selfData.lastName}">
							<hr />
							<label>稱呼方式：</label>
							<c:out value="${selfData.nickname}" />
							<input type="hidden" name="nickname" id="nickname" value="${selfData.nickname}">
							<hr />
							<label>生理性別：</label>
							<c:choose>
								<c:when test="${selfData.gender == 'M'}">男性</c:when>
								<c:when test="${selfData.gender == 'F'}">女性</c:when>
								<c:when test="${selfData.gender == 'N'}">不方便提供</c:when>
							</c:choose>
							<hr />
							<label>西元生日：</label>
							<c:out value="${selfData.birth}" />
							<hr />
							<label>偏好食物：</label>
							<c:out value="${selfData.fervor}" />
							<input type="hidden" name="fervor" id="fervor" value="${selfData.fervor}">
							<hr />
							<label>聯絡信箱：</label>
							<c:out value="${selfData.email}" />
							<input type="hidden" name="email" id="email" value="${selfData.email}">
							<hr />
							<label>聯絡電話：</label>
							<c:out value="${selfData.phone}" />
							<input type="hidden" name="phone" id="phone" value="${selfData.phone}">
							<hr />
							<label>是否願意接收促銷/優惠訊息：</label>
							<c:choose>
								<c:when test="${selfData.getEmail=='Y'}">願意</c:when>
								<c:when test="${selfData.getEmail=='N'}">不願意</c:when>
							</c:choose>
							<input type="hidden" name="getEmail" id="getEmail" value="${selfData.getEmail}">
							<hr />
							<label>居住區域：</label>
							<c:choose>
								<c:when test="${selfData.locationCode=='t01'}">臺北市</c:when>
								<c:when test="${selfData.locationCode=='t02'}">新北市</c:when>
								<c:when test="${selfData.locationCode=='t03'}">桃園市</c:when>
								<c:when test="${selfData.locationCode=='t04'}">臺中市</c:when>
								<c:when test="${selfData.locationCode=='t05'}">臺南市</c:when>
								<c:when test="${selfData.locationCode=='t06'}">高雄市</c:when>
								<c:when test="${selfData.locationCode=='t07'}">基隆市</c:when>
								<c:when test="${selfData.locationCode=='t08'}">新竹市</c:when>
								<c:when test="${selfData.locationCode=='t09'}">嘉義市</c:when>
								<c:when test="${selfData.locationCode=='t10'}">新竹縣</c:when>
								<c:when test="${selfData.locationCode=='t11'}">苗栗縣</c:when>
								<c:when test="${selfData.locationCode=='t12'}">彰化縣</c:when>
								<c:when test="${selfData.locationCode=='t13'}">南投縣</c:when>
								<c:when test="${selfData.locationCode=='t14'}">雲林縣</c:when>
								<c:when test="${selfData.locationCode=='t15'}">嘉義縣</c:when>
								<c:when test="${selfData.locationCode=='t16'}">屏東縣</c:when>
								<c:when test="${selfData.locationCode=='t17'}">宜蘭縣</c:when>
								<c:when test="${selfData.locationCode=='t18'}">花蓮縣</c:when>
								<c:when test="${selfData.locationCode=='t19'}">臺東縣</c:when>
								<c:when test="${selfData.locationCode=='t20'}">澎湖縣</c:when>
								<c:when test="${selfData.locationCode=='t21'}">金門縣</c:when>
								<c:when test="${selfData.locationCode=='t22'}">連江縣</c:when>
								<c:when test="${selfData.locationCode=='t23'}">其他區</c:when>
							</c:choose>
							<input type="hidden" name="locationCode" id="locationCode" value="${selfData.locationCode}">
							<hr />
							<label>生活地點一：</label>
							<c:out value="${selfData.addr0}" />
							<input type="hidden" name="addr0" id="addr0" value="${selfData.addr0}">
							<hr />
							<label>生活地點二：</label>
							<c:out value="${selfData.addr1}" />
							<input type="hidden" name="addr1" id="addr1" value="${selfData.addr1}">
							<hr />
							<label>生活地點三：</label>
							<c:out value="${selfData.addr2}" />
							<input type="hidden" name="addr2" id="addr2" value="${selfData.addr2}">
							<hr />
							<label>所擁有的橙幣：</label>
							<c:out value="${selfData.zest}" />
							<hr />
							<label>帳號狀態：</label>
							<c:choose>
								<c:when test="${selfData.status=='active'}">已啟用</c:when>
								<c:when test="${selfData.status=='quit'}">已棄用</c:when>
							</c:choose>
							<hr />
					</fieldset>
					<div align="center">
						<input type="submit" name="update" value="修改其他資料">
						<input type="submit" name="update" value="修改密碼">
						<a href="WebUserMain.jsp"><input type="button" name="select" value="返回主畫面"></a>
					</div>
				</form>
            </div>   
<!-- -------------------------------------------------------------------- -->
      		<%@include file = "../Footer-Include.jsp" %>
</body>
</html>      