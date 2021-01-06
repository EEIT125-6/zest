<%@ page language="java" contentType="text/html; charset=BIG5" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
        <div class="sidebar" data-image="../assets/img/sidebar-5.jpg" data-color = "orange">
            <div class="sidebar-wrapper">
                <div class="logo">
                    <a href="<c:url value='/'/>" class="simple-text">
                        	橙皮 Zest
                    </a>
                </div>
                <ul class="nav">
                    <li class="nav-item ">
                        <a class="nav-link" href="<c:url value='/dashborad_order'/>">
                            <i class="fas fa-archive"></i>
                            <p>訂單分析</p>
                        </a>
                    </li>
                    <li>
                        <a class="nav-link" href="<c:url value='/dashborad_user'/>">
                            <i class="fas fa-archive"></i>
                            <p>使用者分析</p>
                        </a>
                    </li>
                    <li>
                        <a class="nav-link" href="<c:url value='/dashborad_comment'/>">
                            <i class="fas fa-archive"></i>
                            <p>留言分析</p>
                        </a>
                    </li>
                    <li>
                         <a class="nav-link" href="<c:url value='/dashborad_book'/>">
                            <i class="fas fa-archive"></i>
                            <p>訂位分析</p>
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="main-panel">
            <!-- Navbar -->
            <nav class="navbar navbar-expand-lg " color-on-scroll="500">
                <div class="container-fluid">
                    <a class="navbar-brand" href="#pablo"> Dashboard </a>
                    <button href="" class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" aria-controls="navigation-index" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-bar burger-lines"></span>
                        <span class="navbar-toggler-bar burger-lines"></span>
                        <span class="navbar-toggler-bar burger-lines"></span>
                    </button>
                    <div class="collapse navbar-collapse justify-content-end" id="navigation">
                        <ul class="navbar-nav ml-auto">
                            <li class="nav-item">
                                <a class="nav-link" href="#pablo">
                                    <span class="no-icon">Account</span>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="#pablo">
                                    <span class="no-icon">Log out</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
            <!-- End Navbar -->