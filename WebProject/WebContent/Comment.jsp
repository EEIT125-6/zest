<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
   <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 加上以下這一行，以免有亂碼產生 -->
     <meta http-equiv="Content-Type" content="text/html; charset=big5">
     
<title>評分</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

 <style>
    *{
        margin: 0;
        padding: 0;
       
    }
        
    .box1{
            width: 600px;
            height: 400px;
            border: 18px solid white;
            background-color: white;
            margin: auto;
        }
     .box2{
            width: 600px;
            height: 420px;
            border: 18px solid white;
            background-color: white;
            
        }
        .d1{
            text-align: left;
            width: 580px;
            height: 20px;
            background-color:white;
           
        }
        .d2{
            padding-top: 50px;
            float: left;
            width: 580px;
            height: 180px;
            background-color:white;
            border-top:  1px solid gainsboro;
            text-align: center;
        }
        .d3{
            float: left;
            background-color:white;
            width: 580px;
            height: 140px;
            border-top:  1px solid gainsboro;


        }
        .s1{
            width: 200px;
            height: 140px;
            display: block;
            float: left;
            text-align: right;
        }
        .s2{
            display: inline;
        }
      
        body{
         background-color: whitesmoke;
     }   

    </style>
</head>

<body>
    
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
       <form method=post action="./CommentServlet">

   <fieldset>
        <legend>留言</legend>
    <div class="st1">
        <label class="t1" for="name">姓名:</label>
        <input type="text" id="name" name="name" size="6"><br>
    </div>
    <div class="st1">
        <label for="stars" class="t1">評分:</label>
        <input type="text" id="stars" name="stars" ><br>
    </div>
     <!--  <div class="st1">
        <label class="t1" for="photo">照片:</label>
        <input type="text" id="photo" name="photo" ><br>
    </div> -->  
<div class="st1">
    <label class="t1" for="pwd1">留言:</label>
    <textarea name="comment" id="comment" cols="30" rows="10"></textarea><br>
</div>

    <div class="sub">
        <input type="submit" name="submit" value="送">
        <input type="reset" value="清"> 
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

</body>
</html>