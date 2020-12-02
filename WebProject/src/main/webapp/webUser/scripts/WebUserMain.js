function checkForm() {
	let choice=confirm("是否要執行選定的操作？");
	if (choice==true) {
		return true;
	} else {
		return false;
	}
}
