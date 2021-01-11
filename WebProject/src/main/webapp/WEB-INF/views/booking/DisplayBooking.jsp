<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<title>訂位資料確認</title>
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
<center>          
<h2>訂位資料如下 ! 請確認 !</h2>
<form action="<c:url value='/booking/confirm'/>" method="post">
<table  cellspacing="1" cellpadding="1" border="1" width="500px" style="border:8px #FFD382 groove;">
<tr bgcolor="#F2F4FB">
    <td>訂單編號:</td>
    <td>${reg_booking.bookingNo}</td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>訂位日期:</td>
    <td>${reg_booking.bookingdate}</td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>時間:</td>
    <td>${reg_booking.time}</td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>人數:</td>
    <td>${reg_booking.number}</td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>姓名:</td>
    <td>${reg_booking.name}</td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>聯絡電話:</td>
    <td>${reg_booking.phone}</td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>E-mail:</td>
    <td>${reg_booking.mail}</td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>用餐目的:</td>
    <td>${reg_booking.purpose}</td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>特殊需求:</td>
    <td>${reg_booking.needs}</td>
</tr>
</table>
<div style="margin:10px;">
<input type="submit" name="confirm" value="確認" style="border-radius: 3px; border: none; outline: none;">
</div>
</form>
</center>  
</div>
  <!-- -------------------------------------------------------------- -->
 <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:120px">
            <%@include file = "../Footer-Include-prototype.jsp" %>
</body>
</html>    