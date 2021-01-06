package xun.test;

public class Testajax {
	public static void main(String[] args) {
		String ss = "  sa sa";
		ss = ss.replaceAll("\\s+", "");
		System.out.println(ss);
	}
//	@GetMapping("/searchbook/resultcollect/{bk_ID}")
//	public @ResponseBody boolean gotoCollect(@PathVariable("bk_ID") Integer bk_id) {
//		System.out.println(bk_id);
//		int mb_id = 5;
//		boolean result2 = searchService.savebc(bk_id, mb_id);
//		return result2;
//	
	
//	--------------------------------------------------------------
	
//		<button id="gocollect" type="submit" name="collect" onclick="a${row.getBk_ID()}();"
//		class="btn btn-outline-danger btn-sm" value="${row.getBk_ID()}">收藏本書</button>
//
//
//
//	<script >
//

//	function a${row.getBk_ID()}() {
//	console.log("test");

//	let bk_ID = ${row.getBk_ID()}
//	console.log(bk_ID)

//	let editURL = "searchbook/resultcollect/"+bk_ID;
//
//	console.log(editURL)
//	$.ajax({
//	async : false,
//	type : 'GET',
//	url : editURL,
//	dataType : "json",
//	contentType : "application/json;charset=utf-8",
//	success : function(data) {
//		if (data) {
//			alert('成功加入收藏 ');
//		}else {
//			alert('加入失敗 ');
//		}
//	}
//	});
//	}
//	</script>
}
