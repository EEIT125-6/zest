<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<% 
response.setContentType("text/html;charset=UTF-8");
response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
response.setHeader("Pragma","no-cache"); // HTTP 1.0
response.setDateHeader ("Expires", -1); // Prevents caching at the proxy server
%>
<html>
<head>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/themes/hot-sneaks/jquery-ui.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoadingScreen.css"> 
  <link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
<%@include file = "../Link_Meta-Include.jsp" %>
<title>訂位系統</title>
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
        fieldset{
            width: 500px;
/*             border: 2px solid orange; */
         
            border:8px #FFD382 groove;
            border-radius: 1%;
            margin: 0 auto;
        }
        legend{
            font-size: xx-large;
            /* text-align: center; */
            margin-left: 50px;
        }
        .st1{
            width: 450px;
            border-bottom: dashed gray;
            margin: 25px;
            padding-bottom:10px;
        }
        .st2{
            width: 450px;
            text-align: center;
            margin: 10px;
        }
        .st3{
            width: 100px;
            float: left;
            text-align: right;
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
<%@include file = "../LoadingScreen.jsp" %>
<!-- -------------------------------------------------------------- -->
<!--  <div class="container"  style="margin-top: 20px;"> -->
 <form id="form1" name="form1" action="<c:url value='/booking/next'/>" method="post" onsubmit="return check();" >
    <fieldset>
<input type="hidden" id="nameX" value="${userFullData.firstName}">
<input type="hidden" id="nameY" value="${userFullData.lastName}">
<input type="hidden" id="phoneX" value="${userFullData.phone}">
<input type="hidden" id="mailX"  value="${userFullData.email}">
        <legend>填寫訂位資料</legend>
        <input type="hidden" id="restaurant" name="restaurant" value="${restaurant}">
        
        <div class="st1">
        <input type="checkbox" id="same" name="same" value="">
        <label for="same">同會員資料</label>
        </div>
        
        <div class="st1">
            <label for="" class="st3">訂位日期:</label>
            <input id="datepicker1" type="text" name="bookingdate" 
            	placeholder="請選擇(不能訂當天唷)" > 
        </div>
   
        <div class="st1">
            <label for="" class="st3">時間:</label>
            <select name="time" id="time">
            	<option value="">請選擇</option>
            	<option value="11:30">11:30</option>
                <option value="12:00">12:00</option>
                <option value="12:30">12:30</option>
                <option value="13:00">13:00</option>
                <option value="17:00">17:00</option>
                <option value="17:30">17:30</option>
                <option value="18:00">18:00</option>
                <option value="18:30">18:30</option>
                <option value="19:00">19:00</option>
            </select>
            <span id="seating"></span>
        </div>
        <div class="st1">
            <label for="" class="st3">人數:</label>
            <select name="number" id="number" onChange="beforeAjax()">
            	<option value="">請選擇</option>
                <option value="1">1人</option>
                <option value="2">2人</option>
                <option value="3">3人</option>
                <option value="4">4人</option>
                <option value="5">5人</option>
                <option value="6">6人</option>
            </select>
        </div>
        <div class="st1">
            <label for="name"  class="st3">姓名:</label>
            <input type="text" id="name" name="name" placeholder="請輸入姓名" size="20" autocomplete="off"> 
        </div>
        <div class="st1">
            <label for="phone"  class="st3">手機:</label>
            <input type="tel" id="phone" name="phone" placeholder="請輸入電話" size="20" autocomplete="off" onblur="checkPhone(this.value);"> 
        </div>
        <div class="st1">
            <label for="" class="st3">e-mail:</label>
            <input type="email" id="email" name="mail" placeholder="請輸入email">
        </div>
        <div class="st1">
            <label for="" class="st3">用餐目的:</label>
            <select name="purpose" >
                <option value="一般用餐">一般用餐</option>
                <option value="生日慶祝">生日慶祝</option>
                <option value="浪漫約會">浪漫約會</option>
                <option value="家人聚餐">家人聚餐</option>
                <option value="商務聚餐">商務聚餐</option>
            </select>
        </div>
        <div class="st1">
            <label for="cm"  class="st3">特殊需求:</label> 
            <textarea name="needs" id="cm" cols="40" rows="5" placeholder="（如有會過敏的食物請在此填入...）"></textarea>
        </div>
        <div class="st2">
            <a href="javascript:history.back()"><input type="button" name="back" value="上一步"></a>
            <input type="submit" name="next" id="next" value="下一步" >
        </div>
    </fieldset>
    </form>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
	<script type="text/javascript">
		function checkPhone( strPhone ) { 
	 	    var cellphone = /^09[0-9]{8}$/; 
	 	 
	 	    if (!cellphone.test( strPhone )) { 
	 	        swal("手機格式輸入錯誤", "", "error"); 
	 	    } 	    
		 };  
 	
		function check(){
			if($("#datepicker1").val()==""){
				swal("請選擇訂位日期", "", "warning");
				return false;
			}else if($("#time").val()==""){
				swal("請選擇用餐時間", "", "warning");
				eval("document.form['time'].focus()");	
				return false;
			}else if($("#number").val()==""){
				swal("請選擇用餐人數", "", "warning");
				eval("document.form['number'].focus()");	
				return false;	
			}else if($("#name").val()==""){
				swal("姓名不得為空白", "", "warning");
				eval("document.form['name'].focus()");	
				return false;
			}else if($("#phone").val()==""){
				swal("手機號碼尚未填寫", "", "warning");
				eval("document.form['phone'].focus()");	
				return false;
			}else if($("#email").val()==""){
				swal("e-mail尚未填寫", "", "warning");
				eval("document.form['email'].focus()");	
				return false;
			}else{
				return true;  
			}
		};
		
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
	    	
			$('#same').click(function(){
				document.getElementById("name").value =$("#nameX").val()+$("#nameY").val();
				document.getElementById("phone").value = $("#phoneX").val();
				document.getElementById("email").value = $("#mailX").val();	
			});
	      });
  </script>
  <script>	
        function beforeAjax(){
	    	if($("#datepicker1").val()!="" && $("#time").val()!="" && $("#number").val()!=""){
	    		seating();	
	    	}else{
	    		alert("error");
	    	}
	    } 
		
		function seating(){
			var showSpan = document.getElementById("seating");
			var bookingdate = document.getElementById("datepicker1").value;
			var time = document.getElementById("time").value;
			var number = document.getElementById("number").value;
			var restaurant = document.getElementById("restaurant").value;
		
			$.ajax({
				type : "POST",
				url : "<c:url value='/booking/seating'/>",
				data : {
					'bookingdate':bookingdate,
					'time':time,
					'number':number,
					'restaurant':restaurant
				},
				dataType : "json",
				success : function(resultObj) {
					/* alert("gooood") */
					let show=resultObj.line;
					
		      		if(resultObj.code==-1){
		      			
		      			showSpan.style = "color:red";
		      			showSpan.style = "fontStyle:italic";
		  
		      		}else if(resultObj.code==0){	
		      			
		      			showSpan.style = "color:red";
		      			showSpan.style = "fontStyle:italic";
	  				}else if(resultObj.code==1){
	  					
	  					showSpan.style = "color:red";
	  					showSpan.style = "fontStyle:italic";
	  				}else if(resultObj.code==2){
	  					showSpan.style = "color:black";
	  					showSpan.style = "fontStyle:normal";
	  					
	  				}
			
					showSpan.innerHTML=show;
		      		
				}
			});
	    }
	</script>
<!--   </div> -->
 <!-- -------------------------------------------------------------- -->
 <%@include file = "../Footer-Include.jsp" %>
    
</body>
</html>