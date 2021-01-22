<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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
<!-- ---------------------------------------- -->
<div style="height: 100px;"></div>
    <div class="container" style="border-radius: 15px 15px 15px 15px;background:#003049; padding:50px;">
        <div class="row">
            <div class="col-5" style="padding:0px;border-radius: 15px 15px 15px 15px;background-color: #F0F0F0;height: 300px;margin-bottom: 50px;border: 3px black solid;">
                <div class="h-100 " style="color:9e3f22;align-items: center;">
                    <div style="line-height: 300px;text-align: center;font-size: 300%;">
                    	<a href="<c:url value='/storeSt' />" style="text-decoration:none;">
                        	<span style="color:black;font-family: 'Noto Sans TC', sans-serif;">
                            	統計資料
                        	</span>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-2"></div>
            <div class="col-5" style="padding:0px;border-radius: 15px 15px 15px 15px;background-color: #F0F0F0;height: 300px;margin-bottom: 50px;border: 3px black solid;">
                <div class="h-100 " style="color:9e3f22;align-items: center;">
                    <div style="line-height: 300px;text-align: center;font-size: 300%;">
                        <a href="<c:url value='/storeAd' />" style="text-decoration:none;">
                        	<span style="color:black;font-family: 'Noto Sans TC', sans-serif;">
                            	後臺管理
 	                       	</span>
 	                    </a>
                    </div>
                </div>
            </div>
            <div class="col-5" style="padding:0px;border-radius: 15px 15px 15px 15px;background-color: #F0F0F0;height: 300px;margin-bottom: 50px;border: 3px black solid;">
                <div class="h-100 " style="color:9e3f22;align-items: center;">
                    <div style="line-height: 300px;text-align: center;font-size: 300%;">
                    	<a href="<c:url value='/dashborad_order' />" style="text-decoration:none;">
                        	<span style="color:black;font-family: 'Noto Sans TC', sans-serif;">
                        	    Service
                        	</span>
                        </a>
                    </div>
                </div>
            </div>
            <div class="col-2"></div>
            <div class="col-5" style="padding:0px;border-radius: 15px 15px 15px 15px;background-color: #F0F0F0;height: 300px;margin-bottom: 50px;border: 3px black solid;">
                <div class="h-100 " style="color:9e3f22;align-items: center;">
                    <div style="line-height: 300px;text-align: center;font-size: 300%;">
	                    <a href="<c:url value='/dashborad_order' />" style="text-decoration:none;">
                        	<span style="color:black;font-family: 'Noto Sans TC', sans-serif;">
                        	    Service
                        	</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
<div style="height: 100px;"></div>

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