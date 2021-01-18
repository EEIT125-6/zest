<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%
	response.setContentType("text/html;charset=UTF-8");
	response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
	response.setHeader("Pragma","no-cache"); // HTTP 1.0
	response.setDateHeader ("Expires", -1); // Prevents caching at the proxy server
%>    
<!DOCTYPE html>
<html>
<head>
<%@include file = "../Link_Meta-Include.jsp" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoadingScreen.css"> 
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
<title>訂位紀錄查詢</title>
    <style>
    	/* Table cellpadding */
    	th, td { padding: 1; }
    	/* Table cellspacing */
    	table { border-collapse: collapse; border-spacing: 1; }
        /*  */
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
       	.aa{
            font-size:20px;
            color:#000;
            padding:10px;
            /* border:2px solid #e5e5e5; */
            vertical-align:middle;
            margin:20px;
        }
        .aa:hover{
            background:#e5e5e5;
            font-size:28px;
        } 
    </style>
</head>
<body>
 <%@include file = "../Header-Include.jsp" %>
 <%@include file = "../LoadingScreen.jsp" %>
<!-- -------------------------------------------------------------- -->
  
<div align="center">         
	<h2>訂位紀錄 : </h2>
	<input type="hidden" name="finalDecision" value="" > 
	<input type="hidden" name="purpose" value="${bean.purpose}">
	<input type="hidden" name="status" value="${bean.status}">
	<input type="hidden" name="user_id" value="${bean.user_id}">
	<input type="hidden" name="bookingNo" value="${bean.bookingNo}">
	<input type="hidden" name="restaurant" value="${bean.restaurant}">
	<table border="1" style="width:500px;">
	<c:if test='${empty booking}'>
		查無訂位資料<br>
	</c:if>
	<c:if test='${not empty booking}'>
		<c:forEach var='booking' varStatus='bk' items='${booking}'>
			<table border="1" style="width:500px;border:8px #FFD382 groove;">		
				<tr bgcolor="#FFFFE1">
				    <td>訂單編號:</td>
				    <td>${bean.bookingNo}</td>
				</tr>
				<tr bgcolor="#F2F4FB">
				    <td>餐廳名稱:</td>
				    <td>${bean.restaurant}</td>
				</tr>
				<tr bgcolor="#FFFFE1">
				    <td>訂位日期:</td>
					<td>${bean.bookingdate}</td></tr>
				<tr bgcolor="#F2F4FB">
				    <td>時間:</td>
				    <td>${bean.time}</td>
				</tr>
				<tr bgcolor="#FFFFE1">
				    <td>人數:</td>
					<td>${bean.number}</td>   
				</tr>
				<tr bgcolor="#F2F4FB">
				    <td>姓名:</td>
				    <td>${bean.name}</td>
				</tr>
				<tr bgcolor="#FFFFE1">
				    <td>手機:</td>
				    <td>${bean.phone}</td>
				</tr>
				<tr bgcolor="#F2F4FB">
				    <td>e-mail:</td>
				    <td>${bean.mail}</td>
				</tr>
				<tr bgcolor="#FFFFE1">
				    <td>特殊需求:</td>
				    <td>${bean.needs}</td>
				</tr>
			</table>
		</c:forEach>
	</c:if>
</table>

</div> 
  <!-- -------------------------------------------------------------- -->
 <%@include file = "../Footer-Include.jsp" %>
        
</body>
</html>