<%@ page import="model.BookingBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
response.setContentType("text/html;charset=UTF-8");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoadingScreen.css"> 
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
<title>管理員＿訂單管理</title>
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
       .h1{
       		height: 200px;
       }
    </style>
</head>

   
<body>
<%@include file = "../Header-Include.jsp" %>
<%@include file="../LoadingScreen.jsp" %>
<!-- -------------------------------------------------------------- -->
<div align="center">
<h2><i>所有會員訂位資訊 :</i></h2>
<div id="aa"></div>
<script>
	window.onload = function() {
		/* 載入後先執行一次預設查詢 */
		showAll();
	};
 	function showAll(){
		var aa=document.getElementById("aa");
		$.ajax({
			type : "POST",
			url : "<c:url value='/booking/admin'/>",
			dataType : "json",
			success : function(resultObj) {
				let booking=resultObj.data;	
				let content="";
				if (booking !=null){
		      		content +="<table cellspacing='1' cellpadding='1' border='1' width='880px' style='border:8px #FFD382 groove;'>"
		      				+"<tr><th>訂單編號</th><th>訂位狀態</th><th>餐廳名</th><th>訂位日期</th><th>時間</th><th>人數</th><th>userId</th></tr>";
		      	for(let i=0;i<booking.length;i++){
		      		let data=booking[i];
		      			
		      		if (data.status==1){
		      			content+="<tr><td><a href=<c:url value='/booking/DisplayController?key="+data.bookingNo+"'/>>"+data.bookingNo+"</a></td><td>有效</td>";
		      					
		      		}else{
		      			content+="<tr><td><a href=<c:url value='/booking/DisplayController?key="+data.bookingNo+"'/>>"+data.bookingNo+"</td><td>已取消</td>";
		      		}
		      		content+="<td>"+data.restaurant+"</td><td>"+data.bookingdate+"</td><th>"+data.time+"</td><td>"+data.number+"</td><td>"+data.user_id.userId+"</td></tr>";
				
					/* content +="<table  cellspacing='1' cellpadding='1' border='1' width='500px' style='border:8px #FFD382 groove;'>"
							+"<tr style='visibility: hidden;'><td width='200px'><td width='300px'></tr><tr bgcolor='#F2F4FB'><td>訂單編號:</td>";
		      		if (data.status==1){
		      			content+="<td><a href=<c:url value='/booking/DisplayController?key="+data.bookingNo+"'/>>"+data.bookingNo+"</a></td></tr>"
		      					+"<tr bgcolor='#FFFFE1'><td>訂位狀態:</td><td>有效</td></tr>";
		      		}else{
		      			content+="<td>"+data.bookingNo+"</td></tr>"
		      					+"<tr bgcolor='#FFFFE1'><td>訂位狀態:</td><td>已取消</td></tr>";
		      		}
		      		content+="<tr bgcolor='#F2F4FB'><td>餐廳名稱:</td><td>"+data.restaurant
		      				+"</td></tr><tr bgcolor='#FFFFE1'><td>訂位日期:</td><td>"+data.bookingdate
		      				+"</td></tr><tr bgcolor='#F2F4FB'><td>時間:</td><td>"+data.time
		      				+"</td></tr><tr bgcolor='#FFFFE1'><td>人數:</td><td>"+data.number
		      				+"</td></tr><tr bgcolor='#F2F4FB'><td>姓名:</td><td>"+data.name
		      				+"</td></tr><tr bgcolor='#FFFFE1'><td>聯絡電話:</td><td>"+data.phone
		      				+"</td></tr><tr bgcolor='#F2F4FB'><td>E-mail:</td><td>"+data.mail
		      				+"</td></tr><tr bgcolor='#FFFFE1'><td>用餐目的:</td><td>"+data.purpose
		      				+"</td></tr><tr bgcolor='#F2F4FB'><td>特殊需求:</td><td>"+data.needs+"</td></tr>"; */
		      				
		      	}
		      	content+="</table>";
	      		aa.innerHTML=content;
	      		
		      	
		      	}else{
		      		aa.innerHTML="<h3>查無訂位資料</h3>";
		      		
		      	} 
		      	
			}
		
		}); 
	}
</script>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>   
<form action="<c:url value='/'/>" >
<input type="submit" name="back" value="回首頁" style="margin-top:30px;">
</form> 
</div>   
  <!-- -------------------------------------------------------------- -->
 <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:90px">
            <%@include file = "../Footer-Include-prototype.jsp" %>
 </div>
    
</body>
</html>