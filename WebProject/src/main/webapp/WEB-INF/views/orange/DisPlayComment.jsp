<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
  <%
response.setContentType("text/html;charset=UTF-8");
response.setHeader("Cache-Control","no-cache"); // HTTP 1.1
response.setHeader("Pragma","no-cache"); // HTTP 1.0
response.setDateHeader ("Expires", -1); // Prevents caching at the proxy server
request.setCharacterEncoding("UTF-8");
%>  

<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file = "../Link_Meta-Include.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/LoadingScreen.css"> 
   	<link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
    <title>橙皮</title>
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
    </style>
</head>
<body>
            <%@include file = "../Header-Include.jsp" %>
            <%@include file = "../LoadingScreen.jsp" %>
<!-- -------------------------------------------------------------- -->
 <form method="get" action="<c:url value="/updateboard"/>">
 		<fieldset>
        <input type="hidden" name="id" value="${boardBean.boardid }">
	    <div class="st1">
	        <label class="t1" for="name">姓名:</label>
	        <input type="text" name="name" readOnly value="${boardBean.name }">
	        <br>
	    </div>
	    <div class="st1">
	        <label for="star" class="t1"></label>
	        <input type="hidden" id="star" name="star" value="${boardBean.star }">	
	     </div>	    
		 <div class="d2">
            <span>評價:  </span>
            <img id="img1" class="i" src="<c:url value='/star/s1.png'/>" height="25px" width="25px"/>
            <img id="img2" class="i" src="<c:url value='/star/s1.png'/>" height="25px" width="25px"/>
            <img id="img3" class="i" src="<c:url value='/star/s1.png'/>" height="25px" width="25px"/>
            <img id="img4" class="i" src="<c:url value='/star/s1.png'/>" height="25px" width="25px"/>
            <img id="img5" class="i" src="<c:url value='/star/s1.png'/>" height="25px" width="25px"/>
            <br>
            <label id = "startPcs"></label>
        </div>	    
        <div class="st1">
	        <label class="t1" for="date">時間:</label>
	        <input type="text" readonly name="date" value=" ${boardBean.date }">
			<br>
    	</div>
		<div class="st1">
		    <label class="t1" for="comment">留言:</label>
		    <textarea name="comment" id="" cols="30" rows="10" >${boardBean.context }</textarea>
		    <br>
		</div>
        <input type="hidden" name="storeId" value="${boardBean.storebean.id }">
		<div class="sub">
	        <input type="submit"  value="更新">
	        <input type="reset" value="清除">     
    	</div>
	</fieldset>
</form>
<a href="https://www.blogger.com/blogger.g?blogID=2031514508322140995#" id="gotop">
   <i class="fas fa-chevron-up"></i>
</a>
	    <script src="js/jquery-3.5.1.min.js"></script>
	    <script >
		    	$(".i").mousedown(function() {
		    		let starts = $(this).attr("id").split("img")[1];
		    		$("#star").val(starts);
		    	});
		    	
		    	$(".i").mouseenter(function() {
		    		$(this).attr("src","<c:url value='/star/s3.png'/>");
		    		$(this).prevAll().attr("src","<c:url value='/star/s3.png'/>");
		    		$(this).nextAll().attr("src","<c:url value='/star/s1.png'/>");
		    		let starts = $(this).attr("id").split("img")[1];
		    	});
		
				//星星更新判斷
				$(document).ready(function(){
					let star = $("#star").attr("value")
					$(".i").each(function(){
						if($(this).index() == parseInt(star)){
							$(this).attr("src","<c:url value='/star/s3.png'/>").prevAll().attr("src","<c:url value='/star/s3.png'/>");
						}
					})
				});
				
				$(function() {
				    /* 按下GoTop按鈕時的事件 */
				    $('#gotop').click(function(){
				        $('html,body').animate({ scrollTop: 0 }, 'slow');   /* 返回到最頂上 */
				        return false;
				    });
	    
				    /* 偵測卷軸滑動時，往下滑超過400px就讓GoTop按鈕出現 */
				    $(window).scroll(function() {
				        if ( $(this).scrollTop() > 700){
				            $('#gotop').fadeIn();
				        } else {
				            $('#gotop').fadeOut();
				        }
				    });
				});
		</script>   
<!-- -------------------------------------------------------------------- -->
            <%@include file = "../Footer-Include.jsp" %>
</body>
</html>