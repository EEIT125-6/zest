<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%-- <sql:setDataSource var="ds" driver="com.microsoft.sqlserver.jdbc.SQLServerDriver" --%>


<%-- <sql:setDataSource var="ds" driver="com.microsoft.sqlserver.jdbc.SQLServerDriver" --%>
<%-- 	url="jdbc:sqlserver://10.31.25.130:1433;databaseName=WebProject" user="scott" password="tiger"/> --%>
 <sql:setDataSource var="ds" driver="com.microsoft.sqlserver.jdbc.SQLServerDriver" 
url="jdbc:sqlserver://localhost;databaseName=WebProject" user="scott" password="tiger"/>  
<%-- "AND name = '張阿勳" --%>
<sql:query var="rs" dataSource="${ds}">
SELECT * FROM Board where status = 0  AND name = '${userFullData.nickname}'  ;
</sql:query>

<!DOCTYPE html>
<html>
<%@include file = "../Link_Meta-Include.jsp" %>
<title>橙皮</title>
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
    text-align: center; 2p
    box-shadow: 0 2px 5px 0 rgba(0,0,0,0.16), 0x 10px 0 rgba(0,0,0,0.12);
}
#gotop :hover{
    background:#0099CC;
}
    </style>
</head>
<body>
            <%@include file = "../Header-Include.jsp" %>
<!-- -------------------------------------------------------------- -->
	<div class="container">
						


					
					
					



					
	
	
<%-- 		<form method=Get action="<c:url value="/selectboard"/>"> --%>
<!-- 			<label for="">Name:</label> -->
<%-- 			<input type="text" width="300" name="param" readonly value="${userFullData.nickname}" > --%>
<!-- <!-- 			<input type="submit" name="select" value="select"> --> 
			<table border="1" class="order-table" >
				<tr>
					<th>姓名</th>
					<th>評分</th>
					<th>日期-時間</th>
					<th>留言</th>
					<th>店家名稱</th>
					<th></th>
					
				</tr>
				
					<c:forEach var='g' items='${list }'>
					<tr>
						<td>${g.name}</td>
						<td>
						<c:choose>
						<c:when test="${g.star==1}">
						<img id="img1" class="i" src="<c:url value='/star/1star.png'/>" height="25px" width="135px"/>
						</c:when>
						<c:when test="${g.star==2}">
						<img id="img1" class="i" src="<c:url value='/star/2star.png'/>" height="25px" width="135px"/>
						</c:when>
						<c:when test="${g.star==3}">
						<img id="img1" class="i" src="<c:url value='/star/3star.png'/>" height="25px" width="135px"/>
						</c:when>
						<c:when test="${g.star==4}">
						<img id="img1" class="i" src="<c:url value='/star/4star.png'/>" height="25px" width="135px"/>
						</c:when>
						<c:when test="${g.star==5}">
						<img id="img1" class="i" src="<c:url value='/star/5star.png'/>" height="25px" width="135px"/>
						</c:when>
						<c:otherwise></c:otherwise>
						</c:choose>
						</td>
															   
						<td>${g.date}</td>
						<td>${g.context}</td>
						<td>${g.storebean.stname }
						<td> 
							<a href="<c:url value="/selectboard?boardid=
								${g.boardid }"/>" >更新</a>						    
							<a href="<c:url value="/deleteboard?boardid=
								${g.boardid }"/>" >刪除</a>
						</td>
						</tr>
					</c:forEach>
					
				</table>
<%-- 				<c:forEach var="row" items="${rs.rows}"> --%>
<!-- 					<tr> -->
<%-- 						<td>${row.NAME}</td> --%>
<%-- 						<td>${row.STAR}</td> --%>
<%-- 						<td>${row.DATE}</td> --%>
<%-- 						<td>${row.CONTEXT}</td> --%>
<%-- <%-- 						<td>${row.PHOTO}</td> --%> 

<!-- 					</tr> -->
<%-- 					${row.storebean.stname} --%>
<%-- 				</c:forEach> --%>
			
<!-- 		</form> -->

	</div>
	<a href="https://www.blogger.com/blogger.g?blogID=2031514508322140995#"
		id="gotop"> <i class="fas fa-chevron-up"></i>
	</a>

	<script>	

//  		$(function() {
// 			/* 按下GoTop按鈕時的事件 */
// 			$('#gotop').click(function() {
// 				$('html,body').animate({
// 					scrollTop : 0
// 				}, 'slow'); /* 返回到最頂上 */
// 				return false;
// 			});

// 			/* 偵測卷軸滑動時，往下滑超過400px就讓GoTop按鈕出現 */
// 			$(window).scroll(function() {
// 				if ($(this).scrollTop() > 700) {
// 					$('#gotop').fadeIn();
// 				} else {
// 					$('#gotop').fadeOut();
// 				}
// 			});
// 		});
	</script>


	<!-- -------------------------------------------------------------------- -->
	                        <%@include file = "../Footer-Include.jsp" %>
</body>
</html>