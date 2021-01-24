function checkForm() {
	let choice=confirm("是否確定要送出重設後的密碼？");
	if (choice == true) {
		if (!checkAccountPassword()) {
			return false;
		} else if (!checkConfirmPassword()) {
			return false;
		} else {
			return true;
		}
	} else {
		return false;
	}
}

function checkAccountPassword() {
	let passwordObjValue = document.getElementById("password").value.trim();
	let passwordSpan = document.getElementById("passwordSpan");
	
	let passwordIsOk = true;
	let passwordStr;
	let startCharReg = /[0-9]/;

	if (passwordObjValue == "" || passwordObjValue.length == 0) {
		passwordStr = "密碼不可為空白";
		passwordIsOk = false;
	} else if (passwordObjValue.length < 8) {
		passwordStr = "密碼長度不足，至少需8個字元";
		passwordIsOk = false;
	} else if (passwordObjValue.length > 20) {
		passwordStr = "密碼長度過長，最多僅20個字元";
		passwordIsOk = false;
	} else if (passwordObjValue.charAt(0).match(startCharReg)) {
		passwordStr = "密碼不可以數字開頭";
		passwordIsOk = false;
	} else {
		let accountReg = /[a-zA-Z]{1}[a-zA-Z0-9]{5}/;

		if (!passwordObjValue.match(accountReg)) {
			passwordStr = "密碼不符合格式";
			passwordIsOk = false;
		} else {
			passwordStr = "密碼格式正確";
			passwordIsOk = true;
		}
	}
	if (!passwordIsOk) {
		passwordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + passwordStr;
		passwordSpan.style.color = "red";
		passwordSpan.style.fontStyle = "italic";
		return false;
	} else {
		passwordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + passwordStr;
		passwordSpan.style.color = "black";
		passwordSpan.style.fontStyle = "normal";
		return true;
	}
}

function checkConfirmPassword() {
	let confirmPasswordObjValue = document.getElementById("confirmPassword").value.trim();
	let confirmPasswordSpan = document.getElementById("confirmPasswordSpan");
	let passwordObjValue = document.getElementById("password").value.trim();

	let confirmPasswordIsOk = true;
	let confirmPasswordStr;
	let startCharReg = /[0-9]/;

	if (confirmPasswordObjValue == "" || confirmPasswordObjValue.length == 0) {
		confirmPasswordStr = "密碼不可為空白";
		confirmPasswordIsOk = false;
	} else if (confirmPasswordObjValue.length < 8) {
		confirmPasswordStr = "密碼長度不足，至少需8個字元";
		confirmPasswordIsOk = false;
	} else if (confirmPasswordObjValue.length > 20) {
		confirmPasswordStr = "密碼長度過長，最多僅20個字元";
		confirmPasswordIsOk = false;
	} else if (confirmPasswordObjValue.charAt(0).match(startCharReg)) {
		confirmPasswordStr = "密碼不可以數字開頭";
		confirmPasswordIsOk = false;
	} else {
		let accountReg = /[a-zA-Z]{1}[a-zA-Z0-9]{5}/;

		if (!confirmPasswordObjValue.match(accountReg)) {
			confirmPasswordStr = "密碼不符合格式";
			confirmPasswordIsOk = false;
		} else {
			if (passwordObjValue === confirmPasswordObjValue) {
				confirmPasswordStr = "密碼格式正確";
				confirmPasswordIsOk = true;
			} else {
				confirmPasswordStr = "密碼前後並不吻合";
				confirmPasswordIsOk = false;
			}
		}
	}
	if (!confirmPasswordIsOk) {
		confirmPasswordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + confirmPasswordStr;
		confirmPasswordSpan.style.color = "red";
		confirmPasswordSpan.style.fontStyle = "italic";
		return false;
	} else {
		confirmPasswordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + confirmPasswordStr;
		confirmPasswordSpan.style.color = "black";
		confirmPasswordSpan.style.fontStyle = "normal";
		return true;
	}
}

function changeVisibility() {
	document.getElementById("password").type = (document.getElementById("password").type == "password") ? "text" : "password";
	document.getElementById("visibility_switch").innerHTML 
		= (document.getElementById("visibility_switch").textContent == "顯示密碼 visibility") 
		? "隱藏密碼 "+"<i class='material-icons' style='font-size:18px;color:green'>visibility_off</i>" 
		: "顯示密碼 "+"<i class='material-icons' style='font-size:18px;color:red'>visibility</i>";
}

function changeConfirmVisibility() {
	document.getElementById("confirmPassword").type = (document.getElementById("confirmPassword").type == "password") ? "text" : "password";
	document.getElementById("visibility_switch_confirm").innerHTML 
		= (document.getElementById("visibility_switch_confirm").textContent == "顯示密碼 visibility") 
		? "隱藏密碼 "+"<i class='material-icons' style='font-size:18px;color:green'>visibility_off</i>" 
		: "顯示密碼 "+"<i class='material-icons' style='font-size:18px;color:red'>visibility</i>";
}

function clearMessage() {
	document.getElementById("passwordSpan").innerHTML = "";
	document.getElementById("confirmPasswordSpan").innerHTML = "";
}