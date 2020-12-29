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

<%-- <sql:setDataSource var="ds" dataSource="jdbc/zest" /> --%>

<c:set var="ss" value="${param.stname}"/>
<c:if test="${!(empty stname)}">
  <c:set var="ss" value="${stname}"/>
</c:if>

<%-- <sql:query dataSource="${ds}" var="rs"> --%>
<!--          SELECT * FROM store WHERE stname = ?  -->
<%--          <sql:param value="${ss}" /> --%>
<%-- </sql:query> --%>


<c:forEach var="row" items="${Results}">
	<c:set var = "id" value = "${row.id}"/>
	<c:set var = "stname1"  value = "${row.stname}"/>
	<c:set var = "sclass" value = "${row.sclass}"/>
	<c:set var = "saddress" value = "${row.saddress}"/>
	<c:set var = "stitd" value = "${row.stitd }"/>
	<c:set var = "stitddt" value = "${row.stitddt }"/>
	<c:set var = "tel" value = "${row.tel }"/>
	<c:set var = "bannerurl" value = "${row.bannerurl }"/>
</c:forEach>



<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
     <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
  
          <script src="https://cdn.ckeditor.com/ckeditor5/12.0.0/classic/ckeditor.js"></script>
  
    <%@include file = "Link_Meta-Include.jsp" %>
    <title>橙皮  </title>
    <style>
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
           background-color: yellow;
           background: url("Images/backbar2-1.jpg"); 
           background-repeat: no-repeat;
           height: 700px;
           padding-top: 220px;
           background-size:100%
       }
       .shopcar{
            height: 40px;
            margin: 0;
            margin-left:5px ;
       }
       
       .box1{
       			text-align:center;
       }
       
       .box2{
       
       }
       .uploadImage{
    display: inline-block;
    vertical-align: top;
    position: relative;
    width: 90px;
    height: 90px;
    background: url("../點選上傳.png") no-repeat;
    background-size: cover;
    text-align: center;
    cursor: pointer;
}
.uploadImage p{
    position: absolute;
    left:0;right:0;
    bottom: 10px;
    font-size: 14px;
    color: #999999;
}
.uploadImage input#file{
    width: 100%;
    height: 100%;
    opacity: 0;
    cursor: pointer;
}
.preview{
    position: relative;
    display: inline-block;
    vertical-align: top;
    margin-left: 10px;
    width: 90px;
    height: 90px;
    background: #E1E6ED;
    text-align: center;
}
.preview img{
    position: relative;
    z-index: 1;
    width: 100%;
    height: 100%;
}
.preview img[src=""]{
    opacity:0;
    filter: Alpha(0); /* 相容IE8-9 */
}
.preview img:not([src]){
    opacity:0;
    filter: Alpha(0); /* 相容IE8-9 */
}
.preview .word{
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    line-height: 90px;
    font-size: 14px;
    color: #999999;
    z-index: 0;
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
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <%@include file = "Header-Include.jsp" %>

<!--             <div class="container-fluid  header" > -->
<!--               <div class="container" > -->
<!--               <a href="Index1.jsp"  style="float: left;font-size:37px; text-decoration:none;color:orange"><img src="Images/LOGO1-removebg-preview.png" style="height: 70px;"></a> -->
<!--               <p style="text-align: right;font-family: 'Ubuntu', sans-serif; color: #eae2b7; font-weight: 650;"><br>登入 | 註冊  |<img src="Images/PLZPLZ-removebg-preview.png" class="shopcar"> -->
<!--             </p> -->
<!--               </div> -->
<!--             </div> -->

<!-- -------------------------------------------------------------- -->
            <div class="container-fluid photo" style="background-image: url('${bannerurl}');background-size:100% 100%">
            </div>
            	<%if(true){ %>
		<c:url value="Update" var="EDITURL">
<%-- 			<c:param name="stname" value="${stname1}" /> --%>
			<c:param name="id" value="${id}" />	
		</c:url>
			<a href="${EDITURL}">編輯</a>
			<span>|</span>
		<c:url value="Insert" var="CEATEURL">
		</c:url> 
			<a href="${CEATEURL}">新增</a>
			<span>|</span>

			<form action="DeleteStore" method="post" style="display:inline">
				<input type="hidden" name="id" value="${id}">
				<input type="hidden" name="stname" value="${stname1}">
				<input type="submit" value="刪除" style="margin:0;padding:0;border:none;outline:none;background-color: rgb(235, 159, 18);color:rgb(38, 102, 240)">
			</form>
			<span>|</span>
		<c:url value="UpdatePhoto" var="photoURL">
		<c:param name="stname" value="${stname1}"></c:param>
		<c:param name="id" value="${id}"></c:param>
		<c:param name="photo" value="photo"></c:param>
		</c:url>
			<a href="${photoURL}">修改店家photo</a>
			<span>|</span>
		<c:url value="UpdateBanner" var="bannerURL">
		<c:param name="stname" value="${stname1}"></c:param>
		<c:param name="id" value="${id}"/>
		<c:param name="banner" value="banner"></c:param>
		</c:url>
			<a href="${bannerURL}">修改店家banner</a>
			
			<span>|</span>
			<form action="InsertProduct" method="GET" style="display:inline">
				<input type="hidden" name="id" value="${id}">
				<input type="hidden" name="stname" value="${stname1}">
				<input type="submit" value="新增商品" style="margin:0;padding:0;border:none;outline:none;background-color: rgb(235, 159, 18);color:rgb(38, 102, 240)">
			</form>
			
			<span>|</span>
			<form action="#" method="post" style="display:inline">
				<input type="hidden" name="id" value="${id}">
				<input type="hidden" name="stname" value="${stname1}">
				<input type="submit" value="修改商品" style="margin:0;padding:0;border:none;outline:none;background-color: rgb(235, 159, 18);color:rgb(38, 102, 240)">
			</form>
			
			<span>|</span>
			<form action="#" method="post" style="display:inline">
				<input type="hidden" name="id" value="${id}">
				<input type="hidden" name="stname" value="${stname1}">
				<input type="submit" value="刪除商品" style="margin:0;padding:0;border:none;outline:none;background-color: rgb(235, 159, 18);color:rgb(38, 102, 240)">
			</form>
	<%} %>
	<br>
	
    <div class="container" style="background-color:white; height: 250px;margin-top: 20px;border-radius: 5px 5px 5px 5px; margin-bottom:5px
    ;padding-top : 30px ">
        
<%--         <h1 style="margin-bottom: 100px" ><%=request.getParameter("stname") %></h1> --%>
        <h1 style="margin-bottom: 100px" >${stname1}</h1>
        <hr>
        <span style="font-size: 140%">地點:<c:out value = "${saddress}"></c:out></span>
    </div>
    
	<div class="container" style="background-color:white; height: auto;margin-top: 20px;border-radius: 5px 5px 5px 5px; margin-bottom:5px;padding:5px 10px;padding-left:15px">
		<span style="font-size: 140%">餐廳服務</span>
	</div>
	
	<div class="container" style="background-color:white; height: auto;margin-top: 20px;border-radius: 5px 5px 5px 5px; margin-bottom:5px;padding:5px 10px">
		<div style="font-size: 140%">
            <span id="sp1" class="divcato" style="color:orange;text-decoration:underline;">美食分享</span>
            <span id="sp2" class="divcato">熱門餐點</span>
            <span id="sp3" class="divcato">店家餐點</span>    
			<span id="sp4" class="divcato">簡介</span>
        </div>
        <hr>
        <div id="div1" class="ddiv">
            <span style="font-size: 140%">  
            
     <div class="box1" style="text-align:center ;margin: auto;" > 
       
       <textarea name="content" id="editor">This is some sample content.</textarea>
                       <script>
                        ClassicEditor
                                .create( document.querySelector( '#editor' ) )
                                .then( editor => {
                                        console.log( editor );
                                } )
                                .catch( error => {
                                        console.error( error );
                                } );
                </script>
                
        <div class="d1" style="text-align:left ;">評分總人數:</div>
        <br>
        <div class="d2">
            <h3>給予評價</h3>
            <img id="img1" class="i" src="star/s1.png" height="25px"、 width="25px"/>
            <img id="img2" class="i" src="star/s1.png" height="25px"、 width="25px"/>
            <img id="img3" class="i" src="star/s1.png" height="25px"、 width="25px"/>
            <img id="img4" class="i" src="star/s1.png" height="25px"、 width="25px"/>
            <img id="img5" class="i" src="star/s1.png" height="25px"、 width="25px"/>
            <br>
            <label id = "startPcs"></label>
        </div>
<!--                    <span id="score" style="color : blue;font-size:200%;"></span> -->
            <div id="d1"></div>
        
        <div class="d3"> 
            <div class="container">
                <div class="row">
                  <div class="s1">
                    <img  src="star/Mstar.png" height="110px"、width="110px">
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
    <div class="box2" style="text-align:center ;margin: auto;">
       <form method="get" action="<c:url value='/pack'/>">
		<input type="hidden" name="storeId" value="${id}">
   <fieldset>
        <legend>留言</legend>
        
    <div class="st1">
        <label class="t1" for="name">名字:</label>
       <input type="text" id="name" name="name" ><br>
    </div>
    <div class="st1">
        <label for="star" class="t1"></label>
        <input type="hidden" id="star" name="star" ><br>
    </div>
     <!--  <div class="st1">
        <label class="t1" for="photo">照片:</label>
        <input type="text" id="photo" name="photo" ><br>
    </div> -->  
    
    
<!--    <div> -->
<!-- 		 <form action="/somewhere/to/upload" enctype="multipart/form-data"> -->
		
<!-- 		   <input type="file" onchange="readURL(this)" targetID="preview_progressbarTW_img" accept="image/gif, image/jpeg, image/png"/ > -->
<!-- 		<div> -->
<!-- 		   <img id="preview_progressbarTW_img" src="#"  height="200px"  wight="200ㄌ"/> -->
<!-- 		</div> -->
<!-- 		</form> -->
<!--     </div><br> -->
<div class="pic">
    <div class="uploadImage">
        <input type="file" value="上傳檔案" id="file" accept="image/png, image/jpeg, image/gif, image/jpg" multiple/>
        <p>點選上傳</p>
    </div>
    <div class="preview">
        <img src="" id="look1">
        <p class="word">圖片1</p>
    </div>
    <div class="preview">
        <img src="" id="look2">
        <p class="word">圖片2</p>
    </div>
</div>


    
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
    
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  

    
    <script >  
           
        $(".i").mousedown(function(){
            let starts = $(this).attr("id").split("img")[1];
//             $('#startPcs').html("給你"+starts+"顆星");
			$("#star").val(starts);
        })
         
        $(".i").mouseenter(function(){
            $(this).attr("src","star/s3.png");
            $(this).prevAll().attr("src","star/s3.png");
            $(this).nextAll().attr("src","star/s1.png")
            let starts = $(this).attr("id").split("img")[1]
//             $('#startPcs').html("打分中..."+starts+"顆星")
        });
        
        
        
        
        
        

// var hasUploadedOne = false;// 已上傳過1張圖片
// var hasUploadedTwo = false;// 已上傳過2張圖片

// //獲取到預覽框
// var imgObjPreview1 = document.getElementById("look1");
// var imgObjPreview2 = document.getElementById("look2");

// document.getElementById('file').onchange = function() {
//     // 若還沒完成2張圖片的上傳
//     if(!hasUploadedTwo){
//         //獲取到file的檔案
//         var docObj = this;

//         //獲取到檔名和型別（非IE，可一次上傳1張或多張）
//         if(docObj.files && docObj.files[0]) {
//             // 一次上傳了>=2張圖片（只有前兩張會真的上傳上去）
//             if(docObj.files.length >= 2){
//                 imgObjPreview1.src = window.URL.createObjectURL(docObj.files[0]);
//                 imgObjPreview2.src = window.URL.createObjectURL(docObj.files[1]);
//                 hasUploadedTwo = true;
//             }
//             //一次只上傳了1張照片
//             else{
//                 // 這是上傳的第一張照片
//                 if(!hasUploadedOne){
//                     imgObjPreview1.src = window.URL.createObjectURL(docObj.files[0]);
//                     hasUploadedOne = true;
//                 }
//                 // 這是上傳的第二張照片
//                 else{
//                     imgObjPreview2.src = window.URL.createObjectURL(docObj.files[0]);
//                     hasUploadedTwo = true;
//                 }
//             }

//         }
//         //IE（只能一次上傳1張）
//         else {
//             //使用濾鏡
//             docObj.select();
//             var imgSrc = document.selection.createRange().text;
//             // 這是上傳的第一張照片
//             if(!hasUploadedOne){
//                 imgObjPreview1.src = imgSrc;
//                 hasUploadedOne = true;
//             }
//             // 這是上傳的第二張照片
//             else{
//                 imgObjPreview2.src = imgSrc;
//                 hasUploadedTwo = true;
//             }
//             document.selection.empty();
//         }
//         return true;
//     }
// }

        
        
    </script></span>
            <div>
             <c:forEach var="row" items="${Comments}">
             	${row.name} 
             	${row.date}
             	<br>
             </c:forEach>
            </div>
        </div>
        <div id="div2" style="display:none;" class="ddiv">
            <span style="font-size: 140%"> hello</span>
        </div>
        <div id="div3" style="display:none;" class="ddiv">
<!--              <span style="font-size: 140%">ho </span> -->
			<div>
				<c:forEach var="row1" items="${Products}">
             		${row1.product_name} 
             		${row1.product_price}
             		${row1.product_quantity}
             		<br>
	            </c:forEach>
			</div>
        </div>
        <div id="div4" style="display:none;" class="ddiv">
        	 <span style="font-size: 140%"><c:out value = "${stitddt }"></c:out></span>
        </div>
    </div>
        <script>
        $("#sp1").click(function(){
            $(".ddiv").css("display","none")
            $("#div1").css("display","inline")
        })

        $("#sp2").click(function(){
            $(".ddiv").css("display","none")
            $("#div2").css("display","inline")
        })
        $("#sp3").click(function(){
            $(".ddiv").css("display","none")
            $("#div3").css("display","inline")
        })
        $("#sp4").click(function(){
            $(".ddiv").css("display","none")
            $("#div4").css("display","inline")
        })
        
        $(".divcato").click(function(){
            $(".divcato").css("color","black")
            $(".divcato").css("font-weight","normal")
            $(".divcato").css("text-decoration","none")
            $(this).css("color","orange")
            $(this).css("font-weight","bolder")
            $(this).css("text-decoration","underline")
        })
         </script>
         
   	<div class="container" style="background-color:white; height: auto;margin-top: 20px;border-radius: 5px 5px 5px 5px; margin-bottom:20px;padding:5px 10px;padding-left:15px;">
		<div style="style="font-size: 140%"">餐廳資訊</div>
		<h2 style="color:gray;margin-bottom:10px;margin-top:10px"><c:out value="${stname1}"></c:out></h2>
		<hr>
		<div >
			<span style="font-size: 140%">電話:<c:out value="${tel }"></c:out></span>
		</div>
		<div>
			<span style="font-size: 140%">地址:<c:out value="${saddress }"></c:out></span>
		</div>
	</div>

<!-- ---------------------------------------- -->
<a href="https://www.blogger.com/blogger.g?blogID=2031514508322140995#" id="gotop">
   <i class="fas fa-chevron-up"></i>
</a>
<script type="text/javascript">
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
    <%@include file = "Footer-Include.jsp" %>

<!--              <div style="background-color: #003049;border-top: 3px #e76f51 solid; color:white"> -->
<!--                 Footer -->
<!--                 <footer class="page-footer font-small mdb-color lighten-3 pt-4"> -->
                
<!--                   Footer Links -->
<!--                   <div class="container text-center text-md-left"> -->
                
<!--                     Grid row -->
<!--                     <div class="row"> -->
                
<!--                       Grid column -->
<!--                       <div class="col-md-4 col-lg-3 mr-auto my-md-4 my-0 mt-4 mb-1"> -->
                
<!--                         Content -->
<!--                         <h5 class="font-weight-bold text-uppercase mb-4">More Content</h5> -->
<!--                         <p>商務合作</p> -->
<!--                         <p>	餐飲代理商招募<br> -->
<!--                         	商業企劃<br> -->
<!--                         	申請掃碼點餐<br> -->
<!--                         	美國收單代理商招募<br> -->
<!--                         	美國收銀代理商招募<br> -->
<!--                         	免費使用美國排隊<br></p> -->
                
<!--                       </div> -->
<!--                       Grid column -->
                
<!--                       <hr class="clearfix w-100 d-md-none"> -->
                
<!--                       Grid column -->
<!--                       <div class="col-md-2 col-lg-2 mx-auto my-md-4 my-0 mt-4 mb-1"> -->
                
<!--                         Links -->
<!--                         <h5 class="font-weight-bold text-uppercase mb-4">ABOUT</h5> -->
                
<!--                         <ul class="list-unstyled"> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <a href="#!">計畫</a> -->
<!--                             </p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <a href="#!">關於我們</a> -->
<!--                             </p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <a href="#!">Facebook</a> -->
<!--                             </p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <a href="#!">AWARDS</a> -->
<!--                             </p> -->
<!--                           </li> -->
<!--                         </ul> -->
                
<!--                       </div> -->
<!--                       Grid column -->
                
<!--                       <hr class="clearfix w-100 d-md-none"> -->
                
<!--                       Grid column -->
<!--                       <div class="col-md-4 col-lg-3 mx-auto my-md-4 my-0 mt-4 mb-1"> -->
                
<!--                         Contact details -->
<!--                         <h5 class="font-weight-bold text-uppercase mb-4">Address</h5> -->
                
<!--                         <ul class="list-unstyled"> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <i class="fas fa-home mr-3"></i> 四川 中壢 </p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <i class="fas fa-envelope mr-3"></i> zestinfo@google.com</p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <i class="fas fa-phone mr-3"></i> + 02 453 245 88</p> -->
<!--                           </li> -->
<!--                           <li> -->
<!--                             <p> -->
<!--                               <i class="fas fa-print mr-3"></i> + 02 453 249 89</p> -->
<!--                           </li> -->
<!--                         </ul> -->
                
<!--                       </div> -->
<!--                       Grid column -->
<!--                       <hr class="clearfix w-100 d-md-none"> -->
<!--                       Grid column -->
                
<!--                     </div> -->
<!--                     Grid row -->
                
<!--                   </div> -->
<!--                   Footer Links -->
                
<!--                   Copyright -->
<!--                   <div class="footer-copyright text-center py-3">© 2020 Copyright: -->
<!--                     <a > 橙皮美食平台</a> -->
<!--                   </div> -->
<!--                   Copyright -->
                
<!--                 </footer> -->
<!--                 Footer -->
<!--                     </div> -->
        
</body>
</html>


<script>

</script>