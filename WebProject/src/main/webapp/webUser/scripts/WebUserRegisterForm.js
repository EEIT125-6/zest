function checkForm() {
	let choice=confirm("是否要送出註冊資料？");
	if (choice==true) {
		if (!checkSameAccountAndEmail()) {
			return false;
		} else if (!checkAccountPassword()) {
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
		} else if (!checkPhone()) {
			return false;
		} else if (!checkLocation_code()) {
			return false;
		} else if (!checkAddr0()){
			return false;
		} else if (!checkAddr1()){
			return false;
		} else if (!checkAddr2()){
			return false;
		} else {
			return true;
		}
	} else {
		return false;
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
		document.getElementById("checkAccount").style = "display:none";
		return false;
	} else {
		accountSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + accountStr;
		accountSpan.style.color = "black";
		accountSpan.style.fontStyle = "normal";
		document.getElementById("checkAccount").style = "display:inline";
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
		passwordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + passwordStr;
		passwordSpan.style.color = "black";
		passwordSpan.style.fontStyle = "normal";
		return true;
	}
}

function checkFirst_name() {
	let first_nameObjValue = document.getElementById("firstName").value.trim();
	let first_nameSpan = document.getElementById("firstNameSpan");

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
	let last_nameObjValue = document.getElementById("lastName").value.trim();
	let last_nameSpan = document.getElementById("lastNameSpan");

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
	let nicknameObjValue = document.getElementById("nickname").value.trim();
	let nicknameSpan = document.getElementById("nicknameSpan");

	let nicknameIsOk = true;
	let nicknameStr;

	if (nicknameObjValue == "" || nicknameObjValue.length == 0) {
		if (!checkLast_name()) {
			nicknameStr = "稱呼不可為空白";
			nicknameIsOk = false;
		} else {
			nicknameStr = "稱呼將設為您的名字";
			document.getElementById("nickname").value = document.getElementById("lastName").value;
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
	let birthdayObjValue = document.getElementById("birth").value;
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
	let fervorObj = document.getElementsByName("fervor");
	let fervorObjValue = "";
	for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
		fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
	}
	let fervorSpan = document.getElementById("fervorSpan");
	
	let fervorIsOk = true;
	let fervorStr;
	
	if (fervorObjValue == "" || fervorObjValue.length == 0) {
		fervorStr = "偏好食物不可空白";
		fervorIsOk = false;
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
		document.getElementById("checkEmailUsed").style = "display:none";
		return false;
	}
	else {
		emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailStr;
		emailSpan.style.color = "black";
		emailSpan.style.fontStyle = "normal";
		document.getElementById("checkEmailUsed").style = "display:inline";
		return true;
	}
}

function checkEmailCheckCode() {
	let emailCheckCodeObjValue = document.getElementById("emailCheckCode").value.trim();
	let emailCheckCodeSpan = document.getElementById("emailCheckCodeSpan");
	let checkCode = document.getElementById("checkCode").value.trim();
	
	let emailCheckCodeIsOk = true;
	let emailCheckCodeStr;
	
	if (emailSpan.textContent != "check_circle可使用此電子信箱！") {
		emailCheckCodeStr = "請先執行聯絡信箱檢查";
		emailCheckCodeIsOk = false;
	} else if (checkCode == "" || checkCode.length == 0) {
		emailCheckCodeStr = "尚未產生驗證碼";
		emailCheckCodeIsOk = false;
	} else if (emailCheckCodeObjValue == "" || emailCheckCodeObjValue.length == 0) {
		emailCheckCodeStr = "驗證碼不可為空值";
		emailCheckCodeIsOk = false;
	} else if (checkCode == emailCheckCodeObjValue) {
		emailCheckCodeStr = "聯絡信箱驗證成功";
		emailCheckCodeIsOk = true;
	}
	if (!emailCheckCodeIsOk) {
		emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailCheckCodeStr;
		emailCheckCodeSpan.style.color = "red";
		emailCheckCodeSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		emailCheckCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailCheckCodeStr;
		emailCheckCodeSpan.style.color = "black";
		emailCheckCodeSpan.style.fontStyle = "normal";
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

function checkLocation_code() {
	let location_codeObjValue = document.getElementById("locationCode").value;
	let location_codeSpan = document.getElementById("locationCodeSpan");
	
	let location_codeIsOk = true;
	let location_codeStr;
	
	if (location_codeObjValue == "" || location_codeObjValue.length == 0) {
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
	let addr0ObjValue = document.getElementById("addr0").value.trim();
	let addr0Span = document.getElementById("addr0Span");
	let addr1ObjValue = document.getElementById("addr1").value.trim();
	let addr2ObjValue = document.getElementById("addr2").value.trim();
	
	let addr0IsOk = true;
	let addr0Str;
	
	if (addr0ObjValue == "" || addr0ObjValue.length == 0) {
		addr0Str = "生活地點一不可為空白";
		addr0IsOk = false;
	} else if ((addr1ObjValue == addr0ObjValue && addr0ObjValue != "") || (addr1ObjValue == addr2ObjValue && addr2ObjValue != "")) {
		addr0Str = "生活地點一不可與其他地點重複";
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

function checkAddr1() {
	let addr1ObjValue = document.getElementById("addr1").value.trim();
	let addr1Span = document.getElementById("addr1Span");
	let addr0ObjValue = document.getElementById("addr0").value.trim();
	let addr2ObjValue = document.getElementById("addr2").value.trim();
	
	let addr1IsOk = true;
	let addr1Str;
	
	if ((addr1ObjValue == addr0ObjValue && addr0ObjValue != "") || (addr1ObjValue == addr2ObjValue && addr2ObjValue != "")) {
		addr1Str = "生活地點二不可與其他地點重複";
		addr1IsOk = false;
	} else {
		addr1Str = "生活地點二已填寫完畢";
		addr1IsOk = true;
	}
	if (!addr1IsOk) {
		addr1Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + addr1Str;
		addr1Span.style.color = "red";
		addr1Span.style.fontStyle = "italic";
		return false;
	}
	else {
		addr1Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + addr1Str;
		addr1Span.style.color = "black";
		addr1Span.style.fontStyle = "normal";
		return true;
	}
}

function checkAddr2() {
	let addr2ObjValue = document.getElementById("addr2").value.trim();
	let addr2Span = document.getElementById("addr2Span");
	let addr0ObjValue = document.getElementById("addr0").value.trim();
	let addr1ObjValue = document.getElementById("addr1").value.trim();
	
	let addr2IsOk = true;
	let addr2Str;
	
	if ((addr2ObjValue == addr0ObjValue && addr0ObjValue != "") || (addr2ObjValue == addr1ObjValue && addr1ObjValue != "")) {
		addr2Str = "生活地點三不可與其他地點重複";
		addr2IsOk = false;
	} else {
		addr2Str = "生活地點三已填寫完畢";
		addr2IsOk = true;
	}
	if (!addr2IsOk) {
		addr2Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + addr2Str;
		addr2Span.style.color = "red";
		addr2Span.style.fontStyle = "italic";
		return false;
	}
	else {
		addr2Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + addr2Str;
		addr2Span.style.color = "black";
		addr2Span.style.fontStyle = "normal";
		return true;
	}
}

function changeVisibility() {
	document.getElementById("password").type = (document.getElementById("password").type == "password") ? "text" : "password";
	document.getElementById("visibility_switch").value = (document.getElementById("visibility_switch").value == "顯示密碼") ? "隱藏密碼" : "顯示密碼"; 
}

function clearMessage() {
	document.getElementById("accountSpan").innerHTML = "";
	document.getElementById("checkAccount").style = "display:none";
	document.getElementById("passwordSpan").innerHTML = "";
	document.getElementById("firstNameSpan").innerHTML = "";
	document.getElementById("lastNameSpan").innerHTML = "";
	document.getElementById("nicknameSpan").innerHTML = "";
	document.getElementById("birthdaySpan").innerHTML = "";
	document.getElementById("fervorSpan").innerHTML = "";
	document.getElementById("emailSpan").innerHTML = "";
	document.getElementById("phoneSpan").innerHTML = "";
	document.getElementById("locationCodeSpan").innerHTML = "";
	document.getElementById("addr0Span").innerHTML = "";
	document.getElementById("addr1Span").innerHTML = "";
	document.getElementById("addr2Span").innerHTML = "";
}


function checkSameAccountAndEmail(){
	let accountSpan = document.getElementById("accountSpan");
	let emailSpan = document.getElementById("emailSpan");
	let emailCheckCodeSpan = document.getElementById("emailCheckCodeSpan");
	
	if (accountSpan.textContent != "check_circle可建立此帳號！") {
		alert("請先執行帳號檢查");
		return false;
	} else if (emailSpan.textContent != "check_circle可使用此電子信箱！") {
		alert("請先執行電子信箱檢查");
		return false;
	} else if (emailCheckCodeSpan.textContent != "check_circle聯絡信箱驗證成功") {
		alert("請先執行電子信箱驗證");
		return false;
	} else {
		if (!checkAccountName()) {
			return false;
		} else if (!checkEmailCheckCode()) {
			return false;
		} else {
			return true;
		}
	}
}