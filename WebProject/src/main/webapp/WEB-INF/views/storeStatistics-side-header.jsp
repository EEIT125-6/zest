<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <div class="sidebar"  data-color = "orange">
            <div class="sidebar-wrapper">
                <div class="logo">
                    <a href="<c:url value='/'/>" class="simple-text">
                        	橙皮 Zest
                    </a>
                </div>
                <ul class = "nav">
                <c:forEach var = "row" items="${listAllStore}">
	                	<li class="nav-item">
	                	<c:url value="/storeStClick" var="go">
	                		<c:param name="stId" value="${row.id}"/>
	                	</c:url>
	                		<a class="nav-link" href="${go}">
<!-- 	                			<i class="fas fa-archive"></i><br> -->
	                			<p> ${row.stname}</p>
	                		</a>
	                	</li>
                </c:forEach>
                </ul>
<!--                 <ul class="nav"> -->
<!--                     <li class="nav-item "> -->
<%--                         <a class="nav-link" href="<c:url value='#'/>"> --%>
<!--                             <i class="fas fa-archive"></i> -->
<!--                             <p>全商家管理</p> -->
<!--                         </a> -->
<!--                     </li> -->
<!--                     <li> -->
<%--                         <a class="nav-link" href="<c:url value='#'/>"> --%>
<!--                             <i class="fas fa-archive"></i> -->
<!--                             <p>全商品管理</p> -->
<!--                         </a> -->
<!--                     </li> -->
<!--                     <li> -->
<%--                         <a class="nav-link" href="<c:url value='#'/>"> --%>
<!--                             <i class="fas fa-archive"></i> -->
<!--                             <p>全會員管理</p> -->
<!--                         </a> -->
<!--                     </li> -->
<!--                 </ul> -->
            </div>
        </div>
        <div class="main-panel">
            <!-- Navbar -->
            <nav class="navbar navbar-expand-lg " color-on-scroll="500">
                <div class="container-fluid">
                    <a class="navbar-brand" href="#pablo"> Statistics </a>
                    <button href="" class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-bar burger-lines"></span>
                        <span class="navbar-toggler-bar burger-lines"></span>
                        <span class="navbar-toggler-bar burger-lines"></span>
                    </button>
<!--                     <div class="collapse navbar-collapse justify-content-end" id="navigation"> -->
<!--                         <ul class="navbar-nav ml-auto"> -->
<!--                             <li class="nav-item"> -->
<!--                                 <a class="nav-link" href="#pablo"> -->
<!--                                     <span class="no-icon">Account</span> -->
<!--                                 </a> -->
<!--                             </li> -->
<!--                             <li class="nav-item"> -->
<!--                                 <a class="nav-link" href="#pablo"> -->
<!--                                     <span class="no-icon">Log out</span> -->
<!--                                 </a> -->
<!--                             </li> -->
<!--                         </ul> -->
<!--                     </div> -->
					<div>
						<a href="<c:url value='/storeBack'/>" style="text-decoration: none; color:wheat">回目錄</a>
					</div> 
                </div>
            </nav>
            <!-- End Navbar -->