<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%> --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet"href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous"/>
<link rel='stylesheet' href='${pageContext.request.contextPath}/css/test.css'  type="text/css" />
    <%@include file = "Link_Meta-Include.jsp" %>

<title>橙皮</title>
<style>
body {
	background-color: rgb(235, 159, 18);
}	
       *{
            margin: 0%;
            padding: 0%;
        }
       .header{
            height: 100px;
            border-bottom: 3px solid #e76f51;height: 90px;
            padding-top: 5px;
            background-color: #003049
       }
       .shopcar{
            height: 40px;
            margin: 0;
            margin-left:5px ;
       }

        .outside{
            margin:20px auto;
            height: 350px;
            width: 100%;
            margin-bottom: 10px;
            border-radius: 5px 5px 5px 5px;
            box-shadow: 5px 5px 5px 5px #646262;
            background-color: white;
             transition: 0.2s;
        }
        .outside:hover{
        	border:  solid ;
        	border-width: 0 0 5px 0; 
			border-image: linear-gradient(to right, #c0392b 0%, #f1c40f 100%);
			border-image-slice: 1;

			 transition: 0.2s;
        }
        .classimg{
         transition: 0.2s;	
        	width:80px
        }
        .classimg:hover{
         transition: 0.2s;
			width: 85px
        }
        .photo{
            float: left;
            background-color: yellow;
            border-radius: 5px 0 0 5px;
            height: 350px;
            width: 30%;
            border-right: 1px solid gray;
        }
        .textdiv{
            padding: 5px;
            
        }
        .h11{
            
            font-size: 45px;
            margin-bottom:40px ;
        }
        .postion{

        }
        .itdc{

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
		
.search-area {
  position: fixed;
  left: 0;
  top: 0;
  z-index: 5555;
  background-color: #051922;
  width: 100%;
  height: 100%;
  text-align: center;
}

span.close-btn {
  position: absolute;
  right: 0%;
  color: #fff;
  top: 5%;
  cursor: pointer;
}

.search-area {
  height: 100%;
}

.search-area div {
  height: 100%;
}

.search-bar {
  height: 100%;
  display: table;
  width: 100%;
}

a.mobile-show {
  display: none;
}

.search-area .search-bar div.search-bar-tablecell {
  display: table-cell;
  vertical-align: middle;
  height: auto;
}

.search-bar-tablecell input {
  border: none;
  padding: 15px;
  width: 60%;
  background-color: transparent;
  border-bottom: 1px solid #F28123;
  display: block;
  margin: 0 auto;
  text-align: center;
  font-size: 50px;
  font-weight: 700;
  margin-bottom: 40px;
  color: #fff;
}

.search-bar-tablecell button[type=submit] {
  border: none;
  background-color: #F28123;
  padding: 15px 30px;
  cursor: pointer;
  display: inline-block;
  border-radius: 50px;
  font-weight: 700;
}

.search-bar-tablecell input::-webkit-input-placeholder {
  color: #fff;
}

.search-bar-tablecell input:-ms-input-placeholder {
  color: #fff;
}

.search-bar-tablecell input::-ms-input-placeholder {
  color: #fff;
}

.search-bar-tablecell input::placeholder {
  color: #fff;
}

.search-bar-tablecell button[type=submit] i {
  margin-left: 5px;
}

.search-area {
  visibility: hidden;
  opacity: 0;
  -webkit-transition: 0.3s;
  -o-transition: 0.3s;
  transition: 0.3s;
}

.search-area.search-active {
  visibility: visible;
  opacity: 1;
  z-index: 999;
}

.search-bar-tablecell h3 {
  color: #fff;
  margin-bottom: 30px;
  font-weight: 600;
  text-transform: uppercase;
  letter-spacing: 7px;
}
</style>
</head>
<body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<!--PreLoader-->
    <div class="loader">
        <div class="loader-inner">
            <div class="circle"></div>
        </div>
    </div>
    <script>
    jQuery(window).on("load",function(){
        jQuery(".loader").fadeOut(1000);
    });
    </script>
<!--PreLoader Ends-->    

<!-- search area -->
		<div class="search-area">
			<div class="container">
				<div class="row">
					<div class="col-lg-12">
						<span class="close-btn"><i class="fas fa-window-close"></i></span>
						<div class="search-bar">
							<div class="search-bar-tablecell">
								<form action="StoreGetNamestore" method="GET" enctype="UTF-8"  >
									<h3>搜尋商家名稱:</h3>
									<input type="text" name="nsrch" placeholder="搜尋商家">
									<button type="submit">搜尋 <i class="fas fa-search"></i></button>
							    </form>								
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
<!-- end search arewa -->
		<%@include file ="Header-Include.jsp" %>
<!--             <div class="container-fluid " style="margin-top:10px"> -->
<!--                 <img src="images/backbar2-1.jpg"> -->
<!--                     <form action="StoreGetNamestore" method="GET" enctype="UTF-8"  > -->
<!--                       <fieldset  style="padding: 8px;margin: auto;width: 550px; background-color:rgb(126, 125, 125,0.3);border-radius: 4px;"> -->
<!--                         <input type ="text" id="srchid" name="nsrch" size="59"  placeholder="搜尋餐廳" -->
<!--                         style="height: 36px;border-radius: 4px;line-height: 38px;border: solid 2px black;" > -->
<!--                         <button type="submit"  style="background-color:#fcbf49 ;border: 1px black solid;border-radius: 4px;margin:0px -->
<!--                          ;float:right;height: 36px"> -->
<!--                         	<img src="Images/searchbut.jpg" >   -->
<!--                         </button> -->
<!--                       </fieldset> -->
<!--                     </form> -->
<!--             	</div> -->
<!--             </div> -->
            <div class="container-fluid" style="padding: 0%;background: url('Images/hero-bg.jpg');height:540px;padding-top: 220px;background-size:100%">
            </div>
	<div class="container" style="margin-top:10px">
	<div class="container" style="font-family: 'Nerko One', cursive;font-size:145%;">Restaurant category</div>
	<div class="jumbotron row" style="padding: 25px; background-color: white;font-size: 150% ;height:170px">
		<c:url value="StoreGetClassstore" var="riceURL">
			<c:param name="sclass" value="中式" />
		</c:url>
		<div class="col-sm-2 " style="border-right: rgb(204, 203, 203) 1px solid;text-align: center">
			<a href="${riceURL}"><img class = "classimg" src="Images/S1.jpg"></a><br>中式
		</div>


		<c:url value="StoreGetClassstore" var="JPURL">
			<c:param name="sclass" value="日式" />
		</c:url>
		<div class="col-sm-2 " style="border-right: rgb(204, 203, 203) 1px solid; text-align: center">
			<a href="${JPURL}"><img src="Images/S2.jpg" class = "classimg"/></a><br>日式
		</div>


		<c:url value="StoreGetClassstore" var="TEAURL">
			<c:param name="sclass" value="下午茶" />
		</c:url>
		<div class="col-sm-2 " style="border-right: rgb(204, 203, 203) 1px solid; text-align: center">
			<a href="${TEAURL}"><img src="Images/S3.jpg" class = "classimg"></a><br>下午茶
		</div>

		<c:url value="StoreGetClassstore" var="WESTURL">
			<c:param name="sclass" value="西式" />
		</c:url>
		<div class="col-sm-2 " style="border-right: rgb(204, 203, 203) 1px solid; text-align: center">
			<a href="${WESTURL}"><img src="Images/S4.jpg" class = "classimg"></a><br>西式
		</div>
		
		
		<c:url value="StoreGetClassstore" var="fastURL">
			<c:param name="sclass" value="快餐" />
		</c:url>
		<div class="col-sm-2 " style="border-right: rgb(204, 203, 203) 1px solid;; text-align: center">
			<a href="${fastURL}"><img src="Images/S5.jpg" class = "classimg"></a><br>快餐
		</div>

		<c:url value="StoreGetClassstore" var="metURL">
			<c:param name="sclass" value="燒肉" />
		</c:url>
		<div class="col-sm-2 " style="text-align: center">
			<a href="${metURL}"><img src="Images/S6.jpg" class = "classimg"	></a><br>燒肉
		</div>

		</div>
	</div>
	<div class="row">
	<div class="col-sm-3 ">
	 <div class="container" style="background-color: wheat;border-radius:5px;border: 1px solid wheat;box-shadow: 5px 5px 5px rgb(75, 75, 75);margin-right:0px;width:200px; ">
        <!-- 用container -->
        <form action="############################" method="post" >
            <fieldset style="width: auto;margin:1px auto;">
                <legend>篩選條件</legend>
                
                
				<br>
           		  	<input type="radio" id="d1" name="dollar" value='1' >
  					<label ><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i></label>
  				<br>   
                   	<input type="radio" id="d2" name="dollar" value='2'>
  					<label for="d2"><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i></label>
  				<br>
                   	<input type="radio" id="d3" name="dollar" value='3'>
  					<label for="d3"><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i></label>
  				<br>
                   	<input type="radio" id="d4" name="dollar" value='4'>
  					<label for="d4"><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i></label>
  				<br>
                   	<input type="radio" id="d5" name="dollar" value='5'>
  					<label for="d5"><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i><i class="fas fa-dollar-sign" style = "font-size:20px;color:green"></i></label>
  				<br>
                <hr>
                <label>欲查詢幾顆星以上店家
                </label>
				<br>
					<input type="radio" id="star3.5" name="star" >
  					<label for="star3.5">3.5+</label>
  				<br>
  					<input type="radio" id="star4" name="star" >
  					<label for="star4">4.0+</label>
  				<br>
 
            </fieldset>
	            <div style="text-align: center;">
            </div>
        </form>
    </div>
	</div>
	<div class="col-sm-6" >
      
		<div class="test1" style="margin-bottom:50px;">
			<div id="ajax" ></div>
			<div id="lazyload" class="circle container" style="margin-top:25px;" ></div>
		</div>
<!-- 		----------------AJAX 大餅   START----------------------------- -->
		<script type="text/javascript">
		var context = "";
		$(document).ready(function(){
// 			console.log("$--------------")
// 			console.log("${stname}")
// 			console.log("+++++++++++++++")			
// 			console.log("${sclass}")
// 			console.log("---------------")			
			$("#lazyload").hide();
			
	         // search form
	        $(".search-bar-icon").on("click", function(){
	            $(".search-area").addClass("search-active");
	        });

	        $(".close-btn").on("click", function() {
	            $(".search-area").removeClass("search-active");
	        });		
			
			var flag = 0;
			var stopload = 0;
			var priceLimit = "";
				$.ajax({
						
						type:"GET",
						url:"StoreGetClassStoreAjax",
						data:{
							'sclass':"${sclass}",
							'stname':"${stname}",
							'priceLimit':priceLimit,
							'offset':flag
						},
						datatype:'json',
// 						datatype:'html',

						success:function (data){
				for(var i = 0; i < data.length;i++){
					context +=
						"<a href='StoreGetFullstore/"+data[i].id+"/"+data[i].stname+"' id=a"+data[i].id+"  style='text-decoration:none;color:black'> "+ 
						    "<div class='outside' >"+
			       				 	"<div class='photo' "+" style='background-image: url("+data[i].photourl+");background-size:100% 100%' >"+		
			      				  	"</div>"+
							        	"<div class='textdiv' style='font-size: 135%'>"+
							            "<h1 class='h11' >"+
							                data[i].stname+
							            "</h1>"+
							            "<div class='postion'>"+
							                data[i].saddress+
							            "</div>"+
							            "<hr>"+
							            "<span class='itdc'>"+
							                data[i].sclass+"<br>"+
							                data[i].stitd+
							            "</span>"+
						        	"</div>"+
						    "</div>"+
						"</a>" ;
				
				}
						$("#ajax").html(context)

							flag += 3;
						}
				})
				
				
				$('input[name="dollar"]').click(function(){
					priceLimit = $(this).val()
					flag = 0;
					context="";
					$("#ajax").html("")
						$.ajax({
							
							type:"GET",
							url:"StoreGetClassStoreAjax",
							data:{
								'sclass':"${sclass}",
								'stname':"${stname}",
								'priceLimit':priceLimit,
								'offset':flag
							},
							datatype:'json',
							success:function (data){
								for(var i = 0; i < data.length;i++){
									context +=
										
										"<a href='StoreGetFullstore/"+data[i].id+"/"+data[i].stname+"'  style='text-decoration:none;color:black'> "+ 
										    "<div class='outside' >"+
							       				 "<div class='photo' "+" style='background-image: url("+data[i].photourl+");background-size:100% 100%' >"+
							      				  	"</div>"+
											        	"<div class='textdiv' style='font-size: 135%'>"+
											            "<h1 class='h11' >"+
											                data[i].stname+
											            "</h1>"+
											            "<div class='postion'>"+
											                data[i].saddress+
											            "</div>"+
											            "<hr>"+
											            "<span class='itdc'>"+
											                data[i].sclass+"<br>"+
											                data[i].stitd+
											            "</span>"+
										        	"</div>"+
										    "</div>"+
										"</a>" ;

							}
								flag += 3;

								if(!$.isEmptyObject(data))
								{
									$("#lazyload").show();
									$("#lazyload").fadeOut(1000);															
									setTimeout("$('#ajax').html(context);", 1000 )								
								}
					
							}
					})
				});
				
				
				$(window).scroll(function(){
					

					if($(window).scrollTop() >= $(document).height() - $(window).height() &&  flag>0){
// 						console.log("$--------------")
// 						console.log("${stname}")
// 						console.log("+++++++++++++++")			
// 						console.log("${sclass}")
// 						console.log("---------------")	
					
	//上下兩個方法皆有BUG 有可能會重複前三筆資料 
// 					if($(this).scrollTop() >= ($(document).height() - $(window).height())*0.8){
						
						$.ajax({
							
							type:"GET",
							url:"StoreGetClassStoreAjax",
							data:{
								'sclass':"${sclass}",
								'stname':"${stname}",
								'priceLimit':priceLimit,
								'offset':flag
							},
							datatype:'json',
							success:function (data){
								for(var i = 0; i < data.length;i++){
									context +=
										"<a href='StoreGetFullstore/"+data[i].id+"/"+data[i].stname+"'  style='text-decoration:none;color:black'> "+ 
										    "<div class='outside' >"+
							       				 "<div class='photo' "+" style='background-image: url("+data[i].photourl+");background-size:100% 100%' >"+
							      				  	"</div>"+
											        	"<div class='textdiv' style='font-size: 135%'>"+
											            "<h1 class='h11' >"+
											                data[i].stname+
											            "</h1>"+
											            "<div class='postion'>"+
											                data[i].saddress+
											            "</div>"+
											            "<hr>"+
											            "<span class='itdc'>"+
											                data[i].sclass+"<br>"+
											                data[i].stitd+
											            "</span>"+
										        	"</div>"+
										    "</div>"+
										"</a>" ;

							}
								flag += 3;

								if(!$.isEmptyObject(data))
								{
									$("#lazyload").show();
									$("#lazyload").fadeOut(1000);															
									setTimeout("$('#ajax').html(context);", 1000 )
								}
// 								setTimeout("$('#ajax').show()", 30000);
// 								$(".circle").fadeOut(1000);
// 								$(".circle").fadeIn("1000");
// 								$("#ajax").html(context);
// 								console.log(flag);
// 								console.log(stopload);
// 								console.log(${stname});
// 								console.log(priceLimit);
					
							}
					})
					}
				});

			});
		
		</script>
<!-- 		----------------AJAX 大餅   END----------------------------- -->
		</div>
	<div class="col-sm-3">
	</div>
	</div>
	
<!-- 		
		<div class="jumbotron">
			<span> 美食照片 </span>
		</div>
		<div class="jumbotron">美食照片 </div>
		<div class="jumbotron">美食照片</div>
		<div class="jumbotron">美食照片</div>
		<div class="jumbotron">美食照片</div>
 -->


<!-- ---------------------------------------- -->
<!--  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
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
        if ( $(this).scrollTop() > 500){
            $('#gotop').fadeIn();
        } else {
            $('#gotop').fadeOut();
        }
    });
});
</script>    
<%@include file ="Footer-Include.jsp" %>
</body>
</html>