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
<sql:setDataSource var="ds" dataSource="jdbc/zest" />

<c:set var="ss" value="${param.stname}" />
<sql:query dataSource="${ds}" var="rs">
         SELECT * FROM store WHERE stname = ? 
         <sql:param value="${ss}" />
</sql:query>
<c:forEach var="row" items="${rs.rows}">
	<c:set var = "id" value = "${row.id }"/>
	<c:set var = "stname1"  value = "${row.stname}"/>
	<c:set var = "sclass" value = "${row.sclass}"/>
	<c:set var = "saddress" value = "${row.saddress}"/>
	<c:set var = "stitd" value = "${row.stitd }"/>
	<c:set var = "tel" value = "${row.tel }"/>
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
        <form action="exUpdate.jsp"  
        method="post" >
            <fieldset style="width: 400px;">
                <legend>修改資料</legend>
                <input type="hidden" name="id" value="${id}">
                <label>商店名稱:
                    <input type="text" id="name" name="stname" value="<c:out value="${stname1}"></c:out>" >
                </label>
                <br>
                <label>商店類別 
                    <select name="sclass">
                    
                        <option value="中式"
                        <c:if test = "${sclass == '中式' }">
                         	selected="selected"
                    	</c:if>
                        >中式</option>
                        
                        <option value="日式"
                        <c:if test = "${sclass == '日式' }">
                         	selected="selected"
                    	</c:if>
                        >日式</option>
                        
                        <option value="下午茶"
                        <c:if test = "${sclass == '下午茶' }">
                         	selected="selected"
                    	</c:if>
                        >下午茶</option>
                        
                        <option value="西式"
                        <c:if test = "${sclass == '西式' }">
                         	selected="selected"
                    	</c:if>
                        >西式</option>
                        
                        <option value="快餐"
	                    <c:if test = "${sclass == '快餐' }">
	                         selected="selected"
	                    </c:if>                        
                        >快餐</option>
                        
                        <option value="燒肉" 
	                    <c:if test = "${sclass == '燒肉' }">
	                         selected="selected"
	                    </c:if>
                    	>燒肉</option>
                    	
                    </select>
                </label>
                <br>
                <label>地址:
                    <input type="text" id="idsaddress" name="saddress" value="<c:out value="${saddress}"></c:out>">
                </label>
                <br>
                <label>電話:
                    <input type="text" id="idtel" name="tel" value="${tel}">
                </label>
                <br>
                <label style="width: 40px;float: left;text-align: right;padding-right: 3px;">簡介:
                    <textarea cols="40" rows="5" id="idstitd" name="stitd"><c:out value="${stitd}"></c:out></textarea>
                </label>
            </fieldset>
            <div>
                <input type="submit" value="修改">
                <input type="reset" value="清除">
            </div>
        </form>
    </div>
</body>
</html>