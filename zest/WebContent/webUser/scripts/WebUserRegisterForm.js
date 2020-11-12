function validate() {
	if (!checkUser_id()) {
		return false;
	} else if (!checkFirst_name()) {
		return false;
	} else if (!checkLast_name()) {
		return false;
	} else if (!checkNickname()) {
		return false;
	} else if (!checkBirthday()) {
		return false;
	} else {
		return true;
	}
}

function checkUser_id() {
	let user_idObjValue = document.getElementById("user_id").value;
	let user_idSpan = document.getElementById("user_idSpan");

	let user_idIsOk = true;
	let user_idStr;
	let startCharReg = /[0-9]/;

	if (user_idObjValue == "" || user_idObjValue.length == 0) {
		user_idStr = "帳號不可為空白";
		user_idIsOk = false;
	} else if (user_idObjValue.length < 6) {
		user_idStr = "帳號長度不足";
		user_idIsOk = false;
	} else if (user_idObjValue.length > 20) {
		user_idStr = "帳號長度過長";
		user_idIsOk = false;
	} else if (user_idObjValue.charAt(0).match(startCharReg)) {
		user_idStr = "帳號不可以數字開頭";
		user_idIsOk = false;
	} else {
		let user_idReg = /[a-zA-Z]{1}[a-zA-Z0-9]{5}/;

		if (!user_idObjValue.match(user_idReg)) {
			user_idStr = "帳號不符合格式";
			user_idIsOk = false;
		} else {
			user_idStr = "帳號格式正確";
			user_idIsOk = true;
		}
	}

	if (!user_idIsOk) {
		user_idSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + user_idStr;
		user_idSpan.style.color = "red";
		user_idSpan.style.fontStyle = "italic";
		return false;
	} else {
		user_idSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + user_idStr;
		user_idSpan.style.color = "black";
		user_idSpan.style.fontStyle = "normal";
		return true;
	}
}

function checkFirst_name() {
	let first_nameObjValue = document.getElementById("first_name").value;
	let first_nameSpan = document.getElementById("first_nameSpan");

	let first_nameIsOk = true;
	let first_nameStr;

	if (first_nameObjValue == "" || first_nameObjValue.length == 0) {
		first_nameStr = "姓氏不可為空白";
		first_nameIsOk = false;
	} else if (first_nameObjValue.length < 4) {
		let charCountBegin = 0;
		let charChineseWordCountBegin = 0x4e00;
		let charChineseWordCountEnd = 0x9fff;

		for (let charIndex = charCountBegin; charIndex < first_nameObjValue.length; charIndex++) {
			let first_nameChar = first_nameObjValue.charCodeAt(charIndex);

			if (first_nameChar < charChineseWordCountBegin || first_nameChar > charChineseWordCountEnd) {
				first_nameIsOk = false;
			}
			if (!first_nameIsOk) {
				break;
			}
		}
		first_nameStr = (!first_nameIsOk) ? "姓氏含有非中文" : "姓氏格式正確";
	}
	if (!first_nameIsOk) {
		first_nameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + first_nameStr;
		first_nameSpan.style.color = "red";
		first_nameSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		first_nameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + first_nameStr;
		first_nameSpan.style.color = "black";
		first_nameSpan.style.fontStyle = "normal";
		return true;
	}
}

function checkLast_name() {
	let last_nameObjValue = document.getElementById("last_name").value;
	let last_nameSpan = document.getElementById("last_nameSpan");

	let last_nameIsOk = true;
	let last_nameStr;

	if (last_nameObjValue == "" || last_nameObjValue.length == 0) {
		last_nameStr = "名字不可為空白";
		last_nameIsOk = false;
	} else if (last_nameObjValue.length < 4) {
		let charCountBegin = 0;
		let charChineseWordCountBegin = 0x4e00;
		let charChineseWordCountEnd = 0x9fff;

		for (let charIndex = charCountBegin; charIndex < last_nameObjValue.length; charIndex++) {
			let last_nameChar = last_nameObjValue.charCodeAt(charIndex);

			if (last_nameChar < charChineseWordCountBegin || last_nameChar > charChineseWordCountEnd) {
				last_nameIsOk = false;
			}
			if (!last_nameIsOk) {
				break;
			}
		}
		last_nameStr = (!last_nameIsOk) ? "名字含有非中文" : "名字格式正確";
	}
	if (!last_nameIsOk) {
		last_nameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + last_nameStr;
		last_nameSpan.style.color = "red";
		last_nameSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		last_nameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + last_nameStr;
		last_nameSpan.style.color = "black";
		last_nameSpan.style.fontStyle = "normal";
		return true;
	}
}

function checkNickname() {
	let nicknameObjValue = document.getElementById("nickname").value;
	let nicknameSpan = document.getElementById("nicknameSpan");

	let nicknameIsOk = true;
	let nicknameStr;

	if (nicknameObjValue == "" || nicknameObjValue.length == 0) {
		if (!checkLast_name()) {
			nicknameStr = "名字不可為空白";
			nicknameIsOk = false;
		} else {
			nicknameStr = "稱呼將設為您的名字";
			document.getElementById("nickname").value = document.getElementById("last_name").value;
			nicknameIsOk = true;
		}
	} else {
		nicknameStr = "稱呼填寫完畢";
		nicknameIsOk = true;
	}
	if (!nicknameIsOk) {
		nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + nicknameStr;
		nicknameSpan.style.color = "red";
		nicknameSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + nicknameStr;
		nicknameSpan.style.color = "black";
		nicknameSpan.style.fontStyle = "normal";
		return true;
	}
}

function checkBirthday() {
	let birthdayObjValue = document.getElementById("birthday").value;
	let birthdaySpan = document.getElementById("birthdaySpan");

	let birthdayIsOk = true;
	let birthdayStr;
	let dateReg = /[0-9]{4}[-]{1}[0-1]{1}[0-9]{1}[-][0-3]{1}[0-9]{1}/;
	
	if (birthdayObjValue == "" || birthdayObjValue.length == 0) {
		birthdayStr = "生日不可為空白";
		birthdayIsOk = false;
	} else if (birthdayObjValue.length > 10 || birthdayObjValue.length < 8) {
		birthdayStr = "日期長度不足";
		birthdayIsOk = false;
	} else if (!birthdayObjValue.match(dateReg)) {
		birthdayStr = "日期格式錯誤";
		birthdayIsOk = false;
	} else {
		
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