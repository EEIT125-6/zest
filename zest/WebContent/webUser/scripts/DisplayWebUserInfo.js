function changeVisibility() {
	document.getElementById("visibility_switch").value = (document.getElementById("visibility_switch").value == "顯示密碼") ? "隱藏密碼" : "顯示密碼";
	document.getElementById("realPassword").style = (document.getElementById("realPassword").style == "display:none") ? "display:inline" : "display:none" ; 
}