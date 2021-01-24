function checkForm() {
	if (!checkAccountName()) {
		return false;
	} else if (!checkAccountPassword()) {
		return false;
	} else {
		return true;
	}
}

function checkAccountName() {	
	let accountObjValue = document.getElementById("account").value.trim();
	let accountSpan = document.getElementById("accountSpan");

	let accountIsOk = true;
	let accountStr;
	let startCharReg = /[0-9]/;

	if (accountObjValue == "" || accountObjValue.length == 0) {
		accountStr = "帳號不可為空白";
		accountIsOk = false;
	} else if (accountObjValue.length < 6 || accountObjValue.length > 30) {
		accountStr = "帳號長度錯誤";
		accountIsOk = false;
	} else if (accountObjValue.charAt(0).match(startCharReg)) {
		accountStr = "帳號不可以數字開頭";
		accountIsOk = false;
	} else {
		let accountReg = /[a-zA-Z]{1}[a-zA-Z0-9]{5,29}/;

		if (!accountObjValue.match(accountReg)) {
			accountStr = "帳號不符合格式";
			accountIsOk = false;
		} else {
			accountStr = "帳號格式正確";
			accountIsOk = true;
		}
	}
	if (!accountIsOk) {
		accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + accountStr;
		accountSpan.style.color = "red";
		accountSpan.style.fontStyle = "italic";
		return false;
	} else {
		accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + accountStr;
		accountSpan.style.color = "black";
		accountSpan.style.fontStyle = "normal";
		return true;
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
	} else if (passwordObjValue.length < 6 || passwordObjValue.length > 30) {
		passwordStr = "密碼長度錯誤，範圍6~30字元";
		passwordIsOk = false;
	} else if (passwordObjValue.charAt(0).match(startCharReg)) {
		passwordStr = "密碼不可以數字開頭";
		passwordIsOk = false;
	} else {
		let accountReg = /[a-zA-Z]{1}[a-zA-Z0-9]{5,29}/;

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

function changeVisibility() {
	document.getElementById("password").type = (document.getElementById("password").type == "password") ? "text" : "password";
	document.getElementById("visibility_switch").innerHTML 
		= (document.getElementById("visibility_switch").textContent == "顯示密碼 visibility") 
		? "隱藏密碼 "+"<i class='material-icons' style='font-size:18px;color:green'>visibility_off</i>" 
		: "顯示密碼 "+"<i class='material-icons' style='font-size:18px;color:red'>visibility</i>"; 
}

function clearMessage() {
	document.getElementById("accountSpan").innerHTML = "";
	document.getElementById("passwordSpan").innerHTML = "";
	document.getElementById("loginSpan").innerHTML = "";
}