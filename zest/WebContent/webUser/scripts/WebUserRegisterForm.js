function checkForm() {
	if (!checkAccountName()) {
		return false;
	} else if (!checkFirst_name()) {
		return false;
	} else if (!checkLast_name()) {
		return false;
	} else if (!checkNickname()) {
		return false;
	} else if (!checkBirthday()) {
		return false;
	} else if (!checkFervor()) {
		return false;
	} else if (!checkLocation_code()) {
		return false;
	} else if (!checkAddr0()){
		return false;
	} else {
		return true;
	}
}

function checkAccountName() {
	let accountObjValue = document.getElementById("account").value;
	let accountSpan = document.getElementById("accountSpan");

	let accountIsOk = true;
	let accountStr;
	let startCharReg = /[0-9]/;

	if (accountObjValue == "" || accountObjValue.length == 0) {
		accountStr = "帳號不可為空白";
		accountIsOk = false;
	} else if (accountObjValue.length < 6) {
		accountStr = "帳號長度不足";
		accountIsOk = false;
	} else if (accountObjValue.length > 20) {
		accountStr = "帳號長度過長";
		accountIsOk = false;
	} else if (accountObjValue.charAt(0).match(startCharReg)) {
		accountStr = "帳號不可以數字開頭";
		accountIsOk = false;
	} else {
		let accountReg = /[a-zA-Z]{1}[a-zA-Z0-9]{5}/;

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
	
	if (birthdayObjValue == "" || birthdayObjValue.length == 0) {
		birthdayStr = "生日不可為空白";
		birthdayIsOk = false;
	} else if (birthdayObjValue.length > 10 || birthdayObjValue.length < 8) {
		birthdayStr = "日期長度不足";
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

function checkFervor() {
	let fervorObjValue = document.getElementById("fervor").value;
	let fervorSpan = document.getElementById("fervorSpan");
	
	let fervorIsOk = true;
	let fervorStr;
	
	if (fervorObjValue.trim() == "" || fervorObjValue.length == 0) {
		fervorStr = "偏好食物將設為「無」";
		document.getElementById("fervor").value = "無";
		fervorIsOk = true;
	} else {
		fervorStr = "偏好食物填寫完成";
		fervorIsOk = true;
	}
	if (!fervorIsOk) {
		fervorSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + fervorStr;
		fervorSpan.style.color = "red";
		fervorSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		fervorSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + fervorStr;
		fervorSpan.style.color = "black";
		fervorSpan.style.fontStyle = "normal";
		return true;
	} 
}

function checkLocation_code() {
	let location_codeObjValue = document.getElementById("location_code").value;
	let location_codeSpan = document.getElementById("location_codeSpan");
	
	let location_codeIsOk = true;
	let location_codeStr;
	
	if (location_codeObjValue.trim() == "" || location_codeObjValue.length == 0) {
		location_codeStr = "居住區域不可為空白";
		location_codeIsOk = false;
	} else {
		location_codeStr = "居住區域已選擇完畢";
		location_codeIsOk = true;
	}
	if (!location_codeIsOk) {
		location_codeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + location_codeStr;
		location_codeSpan.style.color = "red";
		location_codeSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		location_codeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + location_codeStr;
		location_codeSpan.style.color = "black";
		location_codeSpan.style.fontStyle = "normal";
		return true;
	}
}

function checkAddr0() {
	let addr0ObjValue = document.getElementById("addr0").value;
	let addr0Span = document.getElementById("addr0Span");
	
	let addr0IsOk = true;
	let addr0Str;
	
	if (addr0ObjValue.trim() == "" || addr0ObjValue.length == 0) {
		addr0Str = "生活地點一不可為空白";
		addr0IsOk = false;
	} else {
		addr0Str = "生活地點一已填寫完畢";
		addr0IsOk = true;
	}
	if (!addr0IsOk) {
		addr0Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + addr0Str;
		addr0Span.style.color = "red";
		addr0Span.style.fontStyle = "italic";
		return false;
	}
	else {
		addr0Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + addr0Str;
		addr0Span.style.color = "black";
		addr0Span.style.fontStyle = "normal";
		return true;
	}
}