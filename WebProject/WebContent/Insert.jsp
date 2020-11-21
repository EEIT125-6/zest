<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<%
	response.setContentType("text/html;charset=UTF-8");
%>
<sql:setDataSource var="ds" dataSource="jdbc/EmployeeDB" />

<sql:query dataSource="${ds}" var="rs">
         select count(*) as number from store 
</sql:query>

<c:forEach var="row" items="${rs.rows}">
	<c:set var = "id" value = "${row.number+1}"/>
</c:forEach>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	    <div style="margin:10px 750px">
        <!-- 用container -->
        <form action="exInsert.jsp"  
        method="post" >
            <fieldset style="width: 400px;">
                <legend>修改資料</legend>
                <input type="hidden" name="id" value="${id}">
                <label>商店名稱:
                    <input type="text" id="name" name="stname" value="" >
                </label>
                <br>
                <label>商店類別 
                    <select name="sclass">
                    
                        <option value="中式"
                        
                         	selected="selected"
                    	
                        >中式</option>
                        
                        <option value="日式"



                        >日式</option>
                        
                        <option value="下午茶"


                        >下午茶</option>
                        
                        <option value="西式"

                        >西式</option>
                        
                        <option value="快餐"
                   
                        >快餐</option>
                        
                        <option value="燒肉" 

                    	>燒肉</option>
                    	
                    </select>
                </label>
                <br>
                <label>地址:
                    <input type="text" id="idsaddress" name="saddress" value="">
                </label>
                <br>
                <label>電話:
                    <input type="text" id="idtel" name="tel" value="">
                </label>
                <br>
                <label style="width: 40px;float: left;text-align: right;padding-right: 3px;">簡介:
                    <textarea cols="40" rows="5" id="idstitd" name="stitd"></textarea>
                </label>
            </fieldset>
            <div>
                <input type="submit" value="新增">
                <input type="reset" value="清除">
            </div>
        </form>
    </div>
</body>
</html>