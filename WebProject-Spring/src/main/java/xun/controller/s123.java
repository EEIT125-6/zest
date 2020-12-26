package xun.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import xun.model.StoreBean;

public class s123 {
//	<div id="ajax"></div>
	
	
//	<!-- 		----------------AJAX 大餅   START----------------------------- -->
//	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
//			<script type="text/javascript">
//			var context = "";
//			$(document).ready(function(){
//			
//				var flag = 0;
//					
//					$.ajax({
//							
//							type:"GET",
//							url:"StoreGetClassStoreAjax",
//							data:{
//									......
//								'offset':flag
//							},
//							datatype:'json',
//
//							success:function (data){
//					for(var i = 0; i < data.length;i++){
//						context +=
//							產生內容
//					
//					}
//							$("#ajax").html(context)
//								flag += 3; 一次產生3筆
//							}
//					})
//					
//					往下捲產生內容
//					$(window).scroll(function(){  
//						
//						if($(window).scrollTop() >= $(document).height() - $(window).height()){
//							
//							$.ajax({
//								
//								type:"GET",
//								url:"StoreGetClassStoreAjax",
//								data:{
//									....
//									'offset':flag
//								},
//								datatype:'json',
//								success:function (data){
//									for(var i = 0; i < data.length;i++){
//										context +=
//											產生內容
//								}
//									flag += 3;  一次產生3筆
//									$("#ajax").html(context);
//									
//						
//								}
//						})
//						}
//					});
//
//				});
//			
//			</script>
	
	
	
	
//	< 後台----------------------------------------------------------------->
	@GetMapping(value = "", produces = "application/json; charset=utf-8")
	public @ResponseBody List<StoreBean> ClassStoreAjax(
//			.......
			@RequestParam Integer offset
			) {
		List<StoreBean> list = new ArrayList<StoreBean>(); //建立list
		
		Integer off3 = offset+3;//判斷最後一次產生要幾筆到幾筆 防範報錯
		if(off3>list.size()) {
			off3 = list.size();
		}
		if(offset>off3) {
			offset=off3;
		}
		list=list.subList(offset, off3);  //一次要產生的數量
		return list;
	}
}
