<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
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
      $("#datepicker1").datepicker({dateFormat:"yy-mm-dd" });
      
      });
  </script>
</head>
<body>
<jsp:useBean id="update" class="booking.bean.BookingBean" scope="session" />
<h2>
訂位紀錄 : 
</h2>
請選擇欲修改之項目
<form action=".\BookingServlet" method="post">
<table  cellspacing="1" cellpadding="1" border="1" width="500px">
<tr bgcolor="#FFFFE1">
    <td>訂單編號:</td>
    <td><jsp:getProperty name="update" property="bookingNo" /></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>餐廳名稱:</td>
    <td><jsp:getProperty name="update" property="restaurant" /></td>
</tr>
<tr bgcolor="#FFFFE1">
    <td>訂位日期:</td>
    <td><input type="text" name="bookingdate" id="datepicker1" value="<jsp:getProperty name="update" property="bookingdate" />"></td>
</tr>
<tr bgcolor="#F2F4FB">
    <td>時間:</td>
    <td><input type="text" name="time" value="<jsp:getProperty name="update" property="time" />"></td>
    
</tr>
<tr bgcolor="#FFFFE1">
    <td>人數:</td>
    <td><input type="text" name="number" value="<jsp:getProperty name="update" property="number" />"></td>
    
</tr>
<tr bgcolor="#F2F4FB">
    <td>姓名:</td>
    <td><input type="text" name="name" value="<jsp:getProperty name="update" property="name" />"></td>
    
</tr>
<tr bgcolor="#FFFFE1">
    <td>手機:</td>
    <td><input type="text" name="phone" value="<jsp:getProperty name="update" property="phone" />"></td>
    
</tr>
<tr bgcolor="#F2F4FB">
    <td>e-mail:</td>
    <td><input type="text" name="mail" value="<jsp:getProperty name="update" property="mail" />"></td>
    
</tr>
<tr bgcolor="#FFFFE1">
    <td>特殊需求:</td>
    <td><input type="text" name="needs" value="<jsp:getProperty name="update" property="needs" />"></td>
    
</tr>

</table>
<div style="margin-left:200px;margin-top:10px;">
<input type="submit" name="confirmUpd" value="確認" >
</div>
</form>
</body>
</html>