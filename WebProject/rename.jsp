<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%request.setCharacterEncoding("UTF-8");%>
<%response.setContentType("text/html;charset=UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%    
String filePath = "C:\\java\\JavaWebWorkSpace\\WebProject\\WebContent\\Images";
File f = new File(filePath+"\\"+sb[sb.length-1]);
System.out.println(filePath+"\\"+sb[sb.length-1]);
	System.out.println("-----");
//	String aa =(String) request.getAttribute("banner");
	Object bb = session.getAttribute("banner");
	
//	String bb = (String)request.getSession(true).getAttribute("banner");
//	System.out.println((String) session.getAttribute("banner"));
	System.out.println("-----1");
	System.out.println(bb);        
File f1 = new File("C:\\java\\JavaWebWorkSpace\\WebProject\\WebContent\\Images\\test\\banner.jpg");
f.renameTo(f1); %>
</body>
</html>