function checkForm() {
	let choice=confirm("確定要執行所選定的操作？");
	if (choice==true) {
		return true;
	} else {
		return false;
	}
}