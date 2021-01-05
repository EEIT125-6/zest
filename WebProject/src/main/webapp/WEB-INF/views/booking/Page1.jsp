<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<%@include file = "../Link_Meta-Include.jsp" %>
<title>test</title>
<style>
        .aa{
            font-size:34px;
            color:#000;
            padding:10px;
            /* border:2px solid #e5e5e5; */
            vertical-align:middle;
            margin-top:30px;
            }
        .aa:hover{
            background:#e5e5e5
            } 

        body{
         background-color: 		rgb(235, 159, 18);
        
        
       }
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
       .h1{
       		height: 500px;
       }

 </style>

</head>
<body>
<%@include file = "../Header-Include.jsp" %>
<!-- -------------------------------------------------------------- -->

<div class="h1"> 
<form action="<c:url value='/booking/select'/>" method="post" >
<center>
<label class="aa">
<input type="tel"  placeholder="請輸入手機號碼" name="phone" id="phone" >
</label>

<label class="aa">
<input type="submit" value="查詢訂位" name="select" id="select" >
</label>
<label class="aa">
<c:if test="${line!=null}">
<c:out value="${line }"/>
</c:if> 
</label>

</center>
<!-- <label class="aa">
<input type="submit" value="取消訂位" name="cancel" id="cancel" >
</label> -->

</form>   
</div>
  <!-- -------------------------------------------------------------- -->
 <%@include file = "../Footer-Include.jsp" %>
    
</body>
</html>