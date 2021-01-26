<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
<head>
<%@include file = "../Link_Meta-Include.jsp" %>
<title>訂位紀錄</title>
<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/themes/hot-sneaks/jquery-ui.css" rel="stylesheet">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoadingScreen.css"> 
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
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
<%@include file="../LoadingScreen.jsp" %>
<!-- -------------------------------------------------------------- -->
  
<div align="center">
<h2>訂位紀錄 : </h2>
<p>請選擇欲修改之項目</p>
<form name="form1" method="post" >
<input type="hidden" name="finalDecision" value="" > 
<input type="hidden" name="purpose" value="${bean.purpose}" id="purpose">
<input type="hidden" name="status" value="${bean.status}">
<input type="hidden" name="user_id" value="${bean.user_id}">
<input type="hidden" name="bookingNo" value="${bean.bookingNo}" id="bookingNo">
<input type="hidden" name="restaurant" value="${bean.restaurant}" id="restaurant">
<c:if test="${bean.status == 0}">
	<c:redirect url='updateResult'/>	
</c:if>
<table border="1" style="border:8px #FFD382 groove;width:500px;">
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
	<td><input type="text" name="bookingdate" id="datepicker1" value="${bean.bookingdate}"></td></tr>
<tr bgcolor="#F2F4FB">
    <td>時間:</td>
    <td><input type="text" id="time" name="time" value="${bean.time}"></td>
			
</tr>
<tr bgcolor="#FFFFE1">
    <td>人數:</td>
	<td><input type="text" id="number" name="number" value="${bean.number}"></td>   
</tr>
<tr bgcolor="#F2F4FB">
    <td>姓名:</td>
    <td><input type="text" id="name" name="name" value="${bean.name}"></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>手機:</td>
    <td><input type="text" id="phone" name="phone" value="${bean.phone}"></td>
    
</tr>
<tr bgcolor="#F2F4FB">
    <td>e-mail:</td>
    <td><input type="text" id="email" name="mail" value="${bean.mail}"></td>
    
</tr>
<tr bgcolor="#FFFFE1">
    <td>特殊需求:</td>
    <td><input type="text" id="needs" name="needs" value="${bean.needs}"></td>

</tr>

</table>
<label class="aa">
	<button type='button' onclick='update()' style="border-radius: 3px; border: none; outline: none;" >確認修改</button>  
</label>
<label class="aa">
	<button type='button' onclick='cancel()' style="border-radius: 3px; border: none; outline: none;">刪除此筆訂位</button>
</label>
</form>
<script>

		function update(){ 
			var bookingNo=$("#bookingNo").val();
 			var restaurant=$("#restaurant").val();
 			var bookingdate=$("#datepicker1").val();
 			var time=$("#time").val();
 			var number=$("#number").val();
 			var name=$("#name").val();
 			var phone=$("#phone").val();
 			var mail=$("#email").val();
 			var name=$("#name").val();
 			var needs=$("#needs").val();
 			var purpose=$("#purpose").val();
 			if(egg()){
			 	$.ajax({
					type : "POST",
					url : "<c:url value='/booking/confirmUpd'/>",
					data : {
						'bookingNo':bookingNo,
						'restaurant':restaurant,
						'bookingdate':bookingdate,
						'time':time,
						'number':number,
						'name':name,
						'phone':phone,
						'mail':mail,
						'needs':needs,
						'purpose':purpose
					},
					dataType : "json",
					success : function(resultObj) {
						let result=resultObj;
						if(result){ 
							swal("訂位修改完成", "將會發送訂位資訊至您的e-mail", "success");
							setTimeout(function(){
								window.location.href="<c:url value='Page1'/>";
							},3000);
					 	}else{
					 		swal("failed", "", "warning");
					 	}
					}
				});  
 			}
		}
		
		function cancel(){
			var bookingNo=$("#bookingNo").val();
 			var restaurant=$("#restaurant").val();
 			var bookingdate=$("#datepicker1").val();
 			var time=$("#time").val();
 			var number=$("#number").val();
 			var name=$("#name").val();
 			var phone=$("#phone").val();
 			var mail=$("#email").val();
 			var name=$("#name").val();
 			var needs=$("#needs").val();
 			var purpose=$("#purpose").val();
 			if(egg()){
			 	$.ajax({
					type : "POST",
					url : "<c:url value='/booking/cancel'/>",
					data : {
						'bookingNo':bookingNo,
						'restaurant':restaurant,
						'bookingdate':bookingdate,
						'time':time,
						'number':number,
						'name':name,
						'phone':phone,
						'mail':mail,
						'needs':needs,
						'purpose':purpose
					},
					dataType : "json",
					success : function(resultObj) {
						let result=resultObj;
						if(result){ 
							swal("您的預約已取消！", "", "success");
							setTimeout(function(){
								window.location.href="<c:url value='Page1'/>";
							},3000);
					 	}else{
					 		swal("failed", "", "warning");
					 	}
					}
				});  
 			}
		}
	
</script>        
<!-- <script src="https://code.jquery.com/jquery.js"></script> -->
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>  
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
<script >
    $(document).ready(function(){
      $.datepicker.regional['zh-TW']={
        dayNames:["星期日","星期一","星期二","星期三","星期四","星期五","星期六"],
        dayNamesMin:["日","一","二","三","四","五","六"],
        monthNames:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
        monthNamesShort:["一月","二月","三月","四月","五月","六月","七月","八月","九月","十月","十一月","十二月"],
        prevText:"上月",
        nextText:"次月",
        weekHeader:"週"
        };
      $.datepicker.setDefaults($.datepicker.regional["zh-TW"]);
      $("#datepicker1").datepicker({
    	  minDate: new Date(),
    	  dateFormat:'yy-mm-dd' });
      });
 
</script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script type="text/javascript">
	function egg() {
			var dateTime=new Date();
			dateTime=dateTime.setDate(dateTime.getDate()+1);
			dateTime=new Date(dateTime); //當天日期加一天
			var bookingdate = document.forms["form1"].bookingdate.value;
			if ((Date.parse(dateTime)).valueOf()>=(Date.parse(bookingdate)).valueOf()) {
				swal("已超過修改/取消訂位的時限！", "", "error");
				return false;
			} 
			return true;
} 
</script>

</div> 
  <!-- -------------------------------------------------------------- -->
 <%@include file = "../Footer-Include.jsp" %>
</body>
</html>