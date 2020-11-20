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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>訂位系統</title>
  <link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/themes/hot-sneaks/jquery-ui.css" rel="stylesheet">
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
  <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.10.2/jquery-ui.min.js"></script>
  <!-- <script type="text/javascript">
  
  function Isphone(phone) {
	  var regex = /^[09]{2}[0-9]{8}$/;
	  if(!regex.test(phone)) {
	  	return false;
	  }else{
	  	return true;
	  }
	  }
  
  </script>
   -->

	<style>
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
    </style>
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
      $("#datepicker1").datepicker({dateFormat:"yy-mm-dd"});

      });
  </script>
</head>
<body>


<form action=".\BookingServlet" method="post" >
    <fieldset>
        <legend>填寫訂位資料</legend>
        
        <div class="st1">
            <label for="" class="st3">訂位日期:</label>
            <input id="datepicker1" type="text" name="bookingdate"> 
   
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
            <input type="text" id="name" name="name" placeholder="請輸入姓名" size="10" autocomplete="off"> 
        </div>
        <div class="st1">
            <label for="phone"  class="st3">手機:</label>
            <input type="text" id="phone" name="phone" placeholder="請輸入電話" size="10" autocomplete="off"> 
        </div>
        <div class="st1">
            <label for="" class="st3">e-mail:</label>
            <input type="email" name="email" >
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
            <textarea name="needs" id="cm"cols="40" rows="5"></textarea>
        </div>
        <div class="st2">
            <input type="submit" name="back" value="上一步">
            <input type="submit" name="next" value="下一步">
        </div>
    </fieldset>
    </form>
</body>
</html>