<%@ page import="model.BookingBean"%>
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
<title>show order</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/css/bootstrap.min.css">				
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
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.min.js"></script>
<script>
window.onload = function() {
	/* 載入後先執行一次預設查詢 */
		def();
	};
function showOrder(){
	$.ajax({
		type : "POST",
		url : "<c:url value='/booking/order'/>",
		dataType : "json",
		success : function(resultObj) {
			let booking=resultObj.data;
		if (booking !=null){
			let content="";
					
	      	for(let i=0;i<booking.length;i++){
	      		let data=booking[i];
				content +='<table  cellspacing="1" cellpadding="1" border="1" width="500px" style="border:8px #FFD382 groove;">'
						+'<tr style="visibility: hidden;"><td width="200px"><td width="300px"></tr>';
	      		content+='<tr bgcolor="#F2F4FB"><td>訂單編號:</td>';
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
	      				+"</td></tr><tr bgcolor='#F2F4FB'><td>特殊需求:</td><td>"+data.needs+"</td></tr>";
	      				
	      	}
	      	content+="</table>";
      		aa.innerHTML=content;
      		
	      	
	      	}else{
	      		aa.innerHTML="<h3>查無訂位資料</h3>";
	      		
	      	} 
	      	
		}
	
	});
}
	function def(){
		$.ajax({
			type : "POST",
			url : "<c:url value='/booking/order'/>",
			dataType : "json",
			success : function(resultObj) {
				let booking=resultObj.data;
				let contentA="";
				let contentB="";
				let contentC="";
			if (booking !=null){
				
		      	for(let i=0;i<booking.length;i++){
		      		let data=booking[i];
		      		if(data.status==1){
						contentA +="<table  cellspacing='1' cellpadding='1' border='1' width='500px' style='border:8px #FFD382 groove;'>"
								+"<tr style='visibility: hidden;'><td width='200px'><td width='300px'></tr><tr bgcolor='#F2F4FB'><td>訂單編號:</td>"
			      				+"<td><a href=<c:url value='/booking/DisplayController?key="+data.bookingNo+"'/>>"+data.bookingNo+"</a></td></tr>"
			      				+"<tr bgcolor='#FFFFE1'><td>訂位狀態:</td><td>有效</td></tr>"
			      				+"<tr bgcolor='#F2F4FB'><td>餐廳名稱:</td><td>"+data.restaurant
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>訂位日期:</td><td>"+data.bookingdate
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>時間:</td><td>"+data.time
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>人數:</td><td>"+data.number
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>姓名:</td><td>"+data.name
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>聯絡電話:</td><td>"+data.phone
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>E-mail:</td><td>"+data.mail
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>用餐目的:</td><td>"+data.purpose
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>特殊需求:</td><td>"+data.needs+"</td></tr></table>";
		      		}
		      		
		      		if(data.status==0){
						contentC +="<table  cellspacing='1' cellpadding='1' border='1' width='500px' style='border:8px #FFD382 groove;'>"
								+"<tr style='visibility: hidden;'><td width='200px'><td width='300px'></tr>"
			      				+"<tr bgcolor='#F2F4FB'><td>訂單編號:</td><td>"+data.bookingNo+"</td></tr>"
			      				+"<tr bgcolor='#FFFFE1'><td>訂位狀態:</td><td>已取消</td></tr>"
			      				+"<tr bgcolor='#F2F4FB'><td>餐廳名稱:</td><td>"+data.restaurant
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>訂位日期:</td><td>"+data.bookingdate
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>時間:</td><td>"+data.time
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>人數:</td><td>"+data.number
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>姓名:</td><td>"+data.name
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>聯絡電話:</td><td>"+data.phone
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>E-mail:</td><td>"+data.mail
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>用餐目的:</td><td>"+data.purpose
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>特殊需求:</td><td>"+data.needs+"</td></tr></table>";
		      		}
		      		if(data.status==2){
		      			contentB +="<table  cellspacing='1' cellpadding='1' border='1' width='500px' style='border:8px #FFD382 groove;'>"
								+"<tr style='visibility: hidden;'><td width='200px'><td width='300px'></tr>"
			      				+"<tr bgcolor='#F2F4FB'><td>訂單編號:</td><td>"+data.bookingNo+"</td></tr>"
			      				+"<tr bgcolor='#FFFFE1'><td>訂位狀態:</td><td>用餐過</td></tr>"
			      				+"<tr bgcolor='#F2F4FB'><td>餐廳名稱:</td><td>"+data.restaurant
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>訂位日期:</td><td>"+data.bookingdate
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>時間:</td><td>"+data.time
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>人數:</td><td>"+data.number
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>姓名:</td><td>"+data.name
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>聯絡電話:</td><td>"+data.phone
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>E-mail:</td><td>"+data.mail
			      				+"</td></tr><tr bgcolor='#FFFFE1'><td>用餐目的:</td><td>"+data.purpose
			      				+"</td></tr><tr bgcolor='#F2F4FB'><td>特殊需求:</td><td>"+data.needs+"</td></tr></table>";
			      		}
		      		
		      	}
		      	
	      		if(contentA=="")contentA="<h3>查無訂位資料</h3>";
	      		if(contentB=="")contentB="<h3>查無訂位資料</h3>";
	      		if(contentC=="")contentC="<h3>查無訂位資料</h3>";
		
		      	}else{
		      		contentA="<h3>查無訂位資料</h3>";
		      		contentB="<h3>查無訂位資料</h3>";
		      		contentC="<h3>查無訂位資料</h3>";
		      		
		      	} 
		      	aa.innerHTML=contentA;
	      		cc.innerHTML=contentC;
	      		bb.innerHTML=contentB;
		      	
			}
		
		});
	}
</script>

</head>
<body>
 <%@include file = "../Header-Include.jsp" %>
<!-- -------------------------------------------------------------- -->
<input type="hidden" name="purpose" value="${bean.purpose}">
<input type="hidden" name="status" value="${bean.status}">
<input type="hidden" name="user_id" value="${bean.user_id}">
		       
<h2 style="margin-left:170px">訂位紀錄如下 : </h2><br>

 <!-- ########################### -->
<div class="container" >
		  <ul class="nav nav-tabs">
		    <li class="active"><a data-toggle="tab" href="#home"><b>訂位中</b></a></li>
		    <li><a data-toggle="tab" href="#menu1">用餐過</a></li>
		    <li><a data-toggle="tab" href="#menu2">已取消</a></li>
		  </ul>
		  
		  <div class="tab-content">
		    <div id="home" class="tab-pane fade in active" >
		      <br><br><div id="aa" style="height:600px;overflow-y:auto"></div>
		    </div>
		    <div id="menu1" class="tab-pane fade">
		      <br/><br/><div id="bb"></div>
		    </div>
		    <div id="menu2" class="tab-pane fade">
		      <br/><br/><div id="cc" style="height:600px;overflow-y:auto"></div>
		    </div>
		  </div>
</div>
<script src="https://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.7/js/bootstrap.min.js"></script>
<!-- ########################### -->
<!-- <div id="aa"></div> -->
<form action="<c:url value='/booking/Index1'/>" >
<input type="submit"  value="返回" style="margin-left:170px;margin-top:20px;border-radius: 3px; border: none; outline: none;text;font-size:26px;">
</form> 

  <!-- -------------------------------------------------------------- -->
 <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white;margin-top:60px">
    <%@include file = "../Footer-Include-prototype.jsp" %>
        
</body>
</html>