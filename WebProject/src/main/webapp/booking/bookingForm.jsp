<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<!DOCTYPE html>
<% 
response.setContentType("text/html;charset=UTF-8");
response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
response.setHeader("Pragma","no-cache"); // HTTP 1.0
response.setDateHeader ("Expires", -1); // Prevents caching at the proxy server
%>
<html>
<head>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/themes/hot-sneaks/jquery-ui.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
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
            border: 2px solid orange;
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
            margin: 30px;
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
    <script type="text/javascript">
	  
	 function checkPhone( strPhone ) 	{ 
	 	    var cellphone = /^09[0-9]{8}$/; 
	 	 
	 	    if ( !cellphone.test( strPhone ) ) { 
	 	        alert( "手機格式輸入錯誤" ); 
	 	    } 
	 	    
	 	};  
 	
 		function check(){
 			if($("#datepicker1").val()==""){
 				alert("請選擇訂位日期");
 				return false;
 			}else if($("#name").val()==""){
 				alert("姓名不得為空白");
 				eval("document.form['name'].focus()");	
 				return false;
 			}else if($("#phone").val()==""){
 				alert("手機號碼尚未填寫");
 				eval("document.form['phone'].focus()");	
 				return false;
 			}else if($("#email").val()==""){
 				alert("e-mail尚未填寫");
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
      $("#datepicker1").datepicker({dateFormat:"yy-mm-dd"});

      });
  </script>
</head>
<body>
<%@include file = "../Header-Include.jsp" %>
<!-- -------------------------------------------------------------- -->
<!--  <div class="container"  style="margin-top: 20px;"> -->
 <form id="form1" name="form1" action="../BookingServlet" method="post" onsubmit="return check();" >
    <fieldset>
        <legend>填寫訂位資料</legend>
        
        <input type="hidden" id="restaurant" name="restaurant" value=<%= request.getParameter("restaurant") %>>
        
        <div class="st1">
            <label for="" class="st3">訂位日期:</label>
            <input id="datepicker1" type="text" name="bookingdate" > 
   
        </div>
   
        <div class="st1">
            <label for="" class="st3">時間:</label>
            <select name="time">
                <option value="12">12:00</option>
                <option value="13">13:00</option>
                <option value="17">17:00</option>
                <option value="18">18:00</option>
                <option value="19">19:00</option>
            </select>
        </div>
        <div class="st1">
            <label for="" class="st3">人數:</label>
            <select name="number">
                <option value="1">1人</option>
                <option value="2">2人</option>
                <option value="3">3人</option>
                <option value="4">4人</option>
                <option value="5">5人</option>
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
            <input type="email" id="email" name="email" >
        </div>
        <div class="st1">
            <label for="" class="st3">用餐目的:</label>
            <select name="purpose">
                <option value="normal">一般聚餐</option>
                <option value="bday">生日慶祝</option>
                <option value="date">浪漫約會</option>
                <option value="family">家人聚餐</option>
                <option value="business">商務聚餐</option>
            </select>
        </div>
        <div class="st1">
            <label for="cm"  class="st3">特殊需求:</label> 
            <textarea name="needs" id="cm" cols="40" rows="5"></textarea>
        </div>
        <div class="st2">
            <a href="javascript:history.back()"><input type="button" name="back" value="上一步"></a>
            <input type="submit" name="next" id="next" value="下一步" >
        </div>
    </fieldset>
    </form>
<!--   </div> -->
 <!-- -------------------------------------------------------------- -->
 <%@include file = "../Footer-Include.jsp" %>
    
</body>
</html>