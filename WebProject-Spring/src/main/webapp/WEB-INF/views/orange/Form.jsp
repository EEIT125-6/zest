<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>	
<!DOCTYPE html>
<html lang="en">
<%@include file = "../Link_Meta-Include.jsp" %>
<title>評分</title>
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
<!-- -------------------------------------------------------------- -->
    <div class="box1" style="text-align: left;margin: auto;" >   
        <div class="d1" >評分總人數:</div>
        <br>
        <div class="d2">
            <h3>給予評價</h3>
            <img id="idimg1" src="image/s1.png" height="25px"、 width="25px"/>
            <img id="idimg2" src="image/s1.png" height="25px"、 width="25px"/>
            <img id="idimg3" src="image/s1.png" height="25px"、 width="25px"/>
            <img id="idimg4" src="image/s1.png" height="25px"、 width="25px"/>
            <img id="idimg5" src="image/s1.png" height="25px"、 width="25px"/>
        </div>
        <div class="d3"> 
            <div class="container">
                <div class="row">
                  <div class="s1">
                    <img  src="image/Mstar.png" height="110px"、width="110px">
                </div>
                  <div class="s2">
                      <div style="line-height: 20px;background-color ; padding-top: 3px;"> <input type="range" min="0" max="100" step="1" value="100" > </div>
                       <input type="range" min="0" max="100" step="1" value="50"><br>
                       <input type="range" min="0" max="100" step="1" value="100"><br>
                       <input type="range" min="0" max="100" step="1" value="100"><br>
                       <input type="range" min="0" max="100" step="1" value="100"><br>
                  </div>
                  <div class="s3">
                    <h3></h3>        
                  </div>
                </div>
              </div>
         
            
        </div>
    </div>
    <br>
    <div class="box2" style="text-align: center;margin: auto;">
       <form method="get" action="<c:url value="/pack"/>">

   <fieldset>
        <legend>留言</legend>
        
    <div class="st1">
        <label class="t1" for="name">名字:</label>
       <input type="text" id="name" name="name" ><br>
    </div>
    <div class="st1">
        <label for="stars" class="t1">評分:</label>
        <input type="text" id="star" name="star" ><br>
    </div>
     <!--  <div class="st1">
        <label class="t1" for="photo">照片:</label>
        <input type="text" id="photo" name="photo" ><br>
    </div> -->  
<div class="st1">
    <label class="t1" for="pwd1">留言:</label>
    <textarea name="comment" id="comment" cols="33" rows="5"></textarea><br>
</div>

    <div class="sub">
        <input type="submit" name="submit" value="傳送"  >
        <input type="reset" value="清除"> 
    </div>
</fieldset>
   
</form>
    </div>
    <script>  
    
        let imgs=document.querySelectorAll("img");

            for(i=0;i<imgs.length;i++){
            imgs[i].onmouseover=mouseover;
            imgs[i].onmouseout=mouseout;
            }


                

        function mouseover() {
      
            let a = this.id.charAt(5)
            for(i=1;i<=a;i++){
            document.querySelector("#idimg"+i).src="image/s3.png";            
            }
            
        }

        function mouseout() {

            let a = this.id.charAt(5)

            for(i=1;i<=a;i++){
            document.querySelector("#idimg"+i).src="image/s1.png";
            }
            
        }

        function focus() {
            console.log("focus");
        }

        function blur() {
            console.log("blur");
        }
    </script>
<!-- -------------------------------------------------------------------- -->
                        <%@include file = "../Footer-Include.jsp" %>
</body>
</html>