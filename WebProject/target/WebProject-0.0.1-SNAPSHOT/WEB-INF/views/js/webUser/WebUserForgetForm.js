function checkForm() {
	let choice=confirm("是否要送出這些資料？請注意，如果資訊錯誤，您將無法順利取得重設帳號資訊用的連結");
	if(!choice) {
		return false;
	} else {
		if (!checkAccountName()) {
			return false;
		} else if (!checkAccountPassword()) {
			return false;
		} else if (!checkEmail()) {
			return false;
		} else if (!checkPhone()) {
			return false;
		} else if (!checkBirthday()) {
			return false;
		}
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
		accountStr = "";
		accountIsOk = true;
	} else if (accountObjValue.length < 8) {
		accountStr = "帳號長度不足";
		accountIsOk = false;
	} else if (accountObjValue.length > 20) {
		accountStr = "帳號長度過長";
		accountIsOk = false;
	} else if (accountObjValue.charAt(0).match(startCharReg)) {
		accountStr = "帳號不可以數字開頭";
		accountIsOk = false;
	} else {
		let accountReg = /[a-zA-Z]{1}[a-zA-Z0-9]{7}/;

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
		if (accountObjValue == "" || accountObjValue.length == 0) {
			accountSpan.innerHTML = "";
		} else {
			accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + accountStr;
			accountSpan.style.color = "black";
			accountSpan.style.fontStyle = "normal";
		}
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
		passwordStr = "";
		passwordIsOk = true;
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
		let passwordReg = /[a-zA-Z]{1}[a-zA-Z0-9]{7}/;

		if (!passwordObjValue.match(passwordReg)) {
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
		if (passwordObjValue == "" || passwordObjValue.length == 0) {
			passwordSpan.innerHTML = "";
		} else {
			passwordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + passwordStr;
			passwordSpan.style.color = "black";
			passwordSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkEmail() {
	let emailObjValue = document.getElementById("email").value.trim();
	let emailSpan = document.getElementById("emailSpan");
	
	let emailIsOk = true;
	let emailStr;
	
	if (emailObjValue == "" || emailObjValue.length == 0) {
		emailStr = "信箱資訊不可為空白";
		emailIsOk = false;
	} else if(emailObjValue.indexOf("@") == -1 || emailObjValue.split("@").length > 2 || emailObjValue.indexOf(" ") != -1) {
		emailStr = "信箱資訊格式錯誤";
		emailIsOk = false;
	} else {
		emailStr = "信箱資訊已填寫完成";
		emailIsOk = true;
	}
	if (!emailIsOk) {
		emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailStr;
		emailSpan.style.color = "red";
		emailSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailStr;
		emailSpan.style.color = "black";
		emailSpan.style.fontStyle = "normal";
		return true;
	}
}

function checkPhone() {
	let phoneObjValue = document.getElementById("phone").value;
	let phoneSpan = document.getElementById("phoneSpan");
	
	let phoneIsOk = true;
	let phoneStr;
	let phoneReg = /[0]{1}[2-9]{1}[0-9]{7,9}/
	
	if (phoneObjValue == "" || phoneObjValue.length == 0) {
		phoneStr = "連絡電話不可為空白";
		phoneIsOk = false;
	} else if(phoneObjValue.length < 9 || phoneObjValue.indexOf(" ") != -1) {
		phoneStr = "連絡電話格式錯誤";
		phoneIsOk = false;
	} else if (!phoneObjValue.match(phoneReg)) {
		phoneStr = "連絡電話格式錯誤";
		phoneIsOk = false;
	} else if (phoneObjValue.substring(0, 2) == "09" && phoneObjValue.length != 10) {
		phoneStr = "行動電話格式錯誤";
		phoneIsOk = false;
	} else if (phoneObjValue.substring(0, 2) != "09" && phoneObjValue.length == 10) {
		phoneStr = "室內電話格式錯誤";
		phoneIsOk = false;
	} else {
		phoneStr = "連絡電話已填寫完畢";
		phoneIsOk = true;
	}
	if (!phoneIsOk) {
		phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + phoneStr;
		phoneSpan.style.color = "red";
		phoneSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + phoneStr;
		phoneSpan.style.color = "black";
		phoneSpan.style.fontStyle = "normal";
		return true;
	}
}

function checkBirthday() {
	let birthdayObjValue = document.getElementById("birth").value;
	let birthdaySpan = document.getElementById("birthSpan");

	let birthdayIsOk = true;
	let birthdayStr;
	
	if (birthdayObjValue == "" || birthdayObjValue.length == 0) {
		birthdayStr = "生日不可為空白";
		birthdayIsOk = false;
	} else if (birthdayObjValue.length > 10 || birthdayObjValue.length < 8) {
		birthdayStr = "日期長度錯誤";
		birthdayIsOk = false;
	} else {
		let inputYear = parseInt(birthdayObjValue.split("-")[0]);
		let inputMonth = parseInt(birthdayObjValue.split("-")[1]);
		let inputDate = parseInt(birthdayObjValue.split("-")[2]);
		let todayYear = new Date().getFullYear();
		let todayMonth = new Date().getMonth() + 1;
		let todayDate = new Date().getDate();
		
		if (todayYear < inputYear) {
			birthdayStr = "無效的出生時間";
			birthdayIsOk = false;
		} else if (todayYear == inputYear && todayMonth < inputMonth) {
			birthdayStr = "無效的出生時間";
			birthdayIsOk = false;
		} else if (todayYear == inputYear && todayMonth == inputMonth && todayDate < inputDate) {
			birthdayStr = "無效的出生時間";
			birthdayIsOk = false;
		}  else {
			birthdayStr = "有效的出生時間";
			birthdayIsOk = true;
		}
	}
	if (!birthdayIsOk) {
		birthdaySpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + birthdayStr;
		birthdaySpan.style.color = "red";
		birthdaySpan.style.fontStyle = "italic";
		return false;
	}
	else {
		birthdaySpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + birthdayStr;
		birthdaySpan.style.color = "black";
		birthdaySpan.style.fontStyle = "normal";
		return true;
	} 
}

function changeVisibility() {
	document.getElementById("password").type = (document.getElementById("password").type == "password") ? "text" : "password";
	document.getElementById("visibility_switch").value = (document.getElementById("visibility_switch").value == "顯示密碼") ? "隱藏密碼" : "顯示密碼"; 
}

function clearMessage() {
	document.getElementById("accountSpan").innerHTML = "";
	document.getElementById("passwordSpan").innerHTML = "";
	document.getElementById("birthdaySpan").innerHTML = "";
	document.getElementById("emailSpan").innerHTML = "";
	document.getElementById("phoneSpan").innerHTML = "";
}