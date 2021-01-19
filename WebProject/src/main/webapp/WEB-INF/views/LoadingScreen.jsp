<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
	<!--PreLoader-->
    <div class="loader">
        <div class="loader-inner">
            <div class="circle"></div>
        </div>
    </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
    jQuery(window).on("load",function(){
        jQuery(".loader").fadeOut(1000);
    });
    
	$(document).ready(function(){
		$("#lazyload").hide();
		
         // search form
        $(".search-bar-icon").on("click", function(){
            $(".search-area").addClass("search-active");
        });

        $(".close-btn").on("click", function() {
            $(".search-area").removeClass("search-active");
        });		
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
								<form action="<c:url value='/StoreGetNamestore'/>" method="GET" enctype="UTF-8"  >
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