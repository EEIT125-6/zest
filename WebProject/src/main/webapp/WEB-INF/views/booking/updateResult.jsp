<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<!DOCTYPE html>
<html>
<head>
<%@include file = "../Link_Meta-Include.jsp" %>
<title>訂位紀錄</title>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/themes/hot-sneaks/jquery-ui.css" rel="stylesheet">
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
      
      
/*       document.getElementById("cancel").onclick=function() {
			window.alert("aaa");
	} */
       
      });
 
  </script>
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

 <script>
 /* 
		document.getElementById("cancel").onclick=function() {
			window.alert("aaa");
	}
 			var dateTime=new Date();
			dateTime=dateTime.setDate(dateTime.getDate()+1);
			dateTime=new Date(dateTime); //當天日期加一天
			alert(dateTime);
			var bookingdate = document.getElementByName("bookingdate").value.trim();
			alert(bookingdate);
			if ((Date.parse(dateTime)).valueOf()>=(Date.parse(bookingdate)).valueOf()) {
				alert("已超過取消訂位的時限！");
				
			} else {
				window.alert("發生錯誤。。");
			}		  */
		
// 		function dateCheck() {
// 			$.ajax({
// 						type : "POST",
// 						url : "/WebProject-Spring/controller/BookingController",
// 						async : false,
// 						data : {
// 							'bookingdate' : bookingdate
// 						},
// 						dataType : "json",
// 						success : function(result) {
// 							if (resultSpace[0] == '1') {
// 								loginStr = "登入成功！";
// 								loginIsOk = true;
// 								/* 顯示彈窗訊息 */
// 								alert(loginStr);
// 							} else if (resultSpace[0] == '0') {
// 								loginStr = "密碼錯誤！";
// 								loginIsOk = false;
// 								/* 顯示彈窗訊息 */
// 								alert(loginStr);
// 							} else if (resultSpace[0] == '-1') {
// 								loginStr = "該帳號已棄用！請重新註冊或聯絡網站管理員";
// 								loginIsOk = false;
// 								/* 顯示彈窗訊息 */
// 								alert(loginStr);
// 							} else if (resultSpace[0] == '-2') {
// 								loginStr = "帳號錯誤！";
// 								loginIsOk = false;
// 								/* 顯示彈窗訊息 */
// 								alert(loginStr);
// 							} else if (resultSpace[0] == '-3') {
// 								loginStr = "檢查途中遭遇錯誤！";
// 								loginIsOk = false;
// 								/* 顯示彈窗訊息 */
// 								alert(resultSpace[1]);
// 							}
// 							if (!loginIsOk) {
// 								loginSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
// 										+ loginStr;
// 								loginSpan.style.color = "red";
// 								loginSpan.style.fontStyle = "italic";
// 							} else {
// 								loginSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>"
// 										+ loginStr;
// 								loginSpan.style.color = "black";
// 								loginSpan.style.fontStyle = "normal";
// 								/* 刷新 */
// 								location.reload(true);
// 							}
// 						},
// 						error : function(err) {
// 							loginStr = "發生錯誤，無法刪改訂位";
// 							loginSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>"
// 									+ loginStr;
// 							loginSpan.style.color = "red";
// 							loginSpan.style.fontStyle = "italic";
// 							/* 顯示彈窗訊息 */
// 							alert(loginStr);
// 						}
// 					});
//		}
	</script>
</head>
<body>
<%@include file = "../Header-Include.jsp" %>
<!-- -------------------------------------------------------------- -->
  
<center>
<h2>訂位紀錄 : </h2>
<p>請選擇欲修改之項目</p>
<form name="form1" action="<c:url value='/booking/confirmUpd'/>" method="post" onSubmit="return egg();" >
<input type="hidden" name="finalDecision" value="" > 
<input type="hidden" name="purpose" value="${bean.purpose}">
<input type="hidden" name="status" value="${bean.status}">
<c:if test="${bean.status == 0}">
	<c:redirect url='updateResult'/>	
</c:if>
<input type="hidden" name="user_id" value="${bean.user_id}">
<table  cellspacing="1" cellpadding="1" border="1" width="500px" style="border:8px #FFD382 groove;">
<tr bgcolor="#FFFFE1">
    <td>訂單編號:</td>
    <td>${bean.bookingNo}</td>
    <input type="hidden" name="bookingNo" value="${bean.bookingNo}">
</tr>
<tr bgcolor="#F2F4FB">
    <td>餐廳名稱:</td>
    <td>${bean.restaurant}</td>
    <input type="hidden" name="restaurant" value="${bean.restaurant}">
</tr>
<tr bgcolor="#FFFFE1">
    <td>訂位日期:</td>
	<td><input type="text" name="bookingdate" id="datepicker1" value="${bean.bookingdate}"></td></tr>
<tr bgcolor="#F2F4FB">
    <td>時間:</td>
    <td><input type="text" name="time" value="${bean.time}"></td>
			
</tr>
<tr bgcolor="#FFFFE1">
    <td>人數:</td>
	<td><input type="text" name="number" value="${bean.number}"></td>   
</tr>
<tr bgcolor="#F2F4FB">
    <td>姓名:</td>
    <td><input type="text" name="name" value="${bean.name}"></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>手機:</td>
    <td><input type="text" name="phone" value="${bean.phone}"></td>
    
</tr>
<tr bgcolor="#F2F4FB">
    <td>e-mail:</td>
    <td><input type="text" name="mail" value="${bean.mail}"></td>
    
</tr>
<tr bgcolor="#FFFFE1">
    <td>特殊需求:</td>
    <td><input type="text" name="needs" value="${bean.needs}"></td>

</tr>

</table>
<label class="aa">
	<input type="submit" value="確認修改" name='confirmUpd' style="border-radius: 3px; border: none; outline: none;"> 
</label>
<label class="aa">
	<input type="submit" value="刪除此筆訂位" name='cancel' id="cancel" style="border-radius: 3px; border: none; outline: none;">
</label>
</form>
<script type="text/javascript">
	function egg() {
			var dateTime=new Date();
			dateTime=dateTime.setDate(dateTime.getDate()+1);
			dateTime=new Date(dateTime); //當天日期加一天
			var bookingdate = document.forms["form1"].bookingdate.value;
			if ((Date.parse(dateTime)).valueOf()>=(Date.parse(bookingdate)).valueOf()) {
				alert("已超過修改/取消訂位的時限！");
				return false;
			} 
			return true;
} 
</script>        
</center> 
  <!-- -------------------------------------------------------------- -->
 <%@include file = "../Footer-Include.jsp" %>
</body>
</html>