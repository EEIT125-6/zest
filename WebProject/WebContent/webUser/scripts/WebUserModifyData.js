function checkForm() {
	let count = 0;
	let first_nameObjValue = document.getElementById("updatedFirst_name").value.trim();
	let last_nameObjValue = document.getElementById("updatedLast_name").value.trim();
	let nicknameObjValue = document.getElementById("updatedNickname").value.trim();
	let fervorObj = document.getElementsByName("updatedFervor");
	let fervorObjValue = "";
	for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
		fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
	}
	
	let updateSpan = document.getElementById("updateSpan");
	let updateStr;
	
	if (!checkFirst_name()) {
		return false;
	} else if (!checkLast_name()) {
		return false;
	} else if (!checkNickname()) {
		return false;
	} else if (!checkFervor()) {
		return false;
	} else if (!checkEmail()) {
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
		if (first_nameObjValue == "" || first_nameObjValue.length == 0) {
			count++;
		}
		if (last_nameObjValue == "" || last_nameObjValue.length == 0) {
			count++;
		}
		if (nicknameObjValue == "" || nicknameObjValue.length == 0) {
			count++;
		}
		if (fervorObjValue == "" || fervorObjValue.length == 0) {
			count++;
		}
		if (count == 11){
			updateStr = "至少需填寫/選擇一項條件才能執行修改！";
			updateSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updateStr;
			updateSpan.style.color = "red";
			updateSpan.style.fontStyle = "italic";
			return false;
		}
		else {
			updateSpan.innerHTML = "";
			return true;
		}
	}
}

function checkFirst_name() {
	let first_nameObjValue = document.getElementById("updatedFirst_name").value.trim();
	let first_nameSpan = document.getElementById("first_nameSpan");
	let oldFirst_nameObjValue = document.getElementById("originalFirst_name").value.trim();

	let first_nameIsOk = true;
	let first_nameStr;

	if (first_nameObjValue == "" || first_nameObjValue.length == 0) {
		first_nameStr = "";
		first_nameIsOk = true;
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
		first_nameStr = (!first_nameIsOk) ? "名字含有非中文" : "名字格式正確";
	}
	if (!first_nameIsOk) {
		first_nameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + first_nameStr;
		first_nameSpan.style.color = "red";
		first_nameSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		if (first_nameObjValue == "" || first_nameObjValue.length == 0 || oldFirst_nameObjValue == first_nameObjValue) {
			first_nameSpan.innerHTML = "";
		} else {
			first_nameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + first_nameStr;
			first_nameSpan.style.color = "black";
			first_nameSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkLast_name() {
	let last_nameObjValue = document.getElementById("updatedLast_name").value.trim();
	let last_nameSpan = document.getElementById("last_nameSpan");
	let oldLast_nameObjValue = document.getElementById("originalLast_name").value.trim();

	let last_nameIsOk = true;
	let last_nameStr;

	if (last_nameObjValue == "" || last_nameObjValue.length == 0) {
		last_nameStr = "";
		last_nameIsOk = true;
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
		if (oldLast_nameObjValue == last_nameObjValue) {
			last_nameIsOk = false;
			last_nameStr = "名字未修改";
		} else {
			last_nameStr = (!last_nameIsOk) ? "名字含有非中文" : "名字格式正確";
		}
	}
	if (!last_nameIsOk) {
		last_nameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + last_nameStr;
		last_nameSpan.style.color = "red";
		last_nameSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		if (last_nameObjValue == "" || last_nameObjValue.length == 0) {
			last_nameSpan.innerHTML = "";
		} else {
			last_nameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + last_nameStr;
			last_nameSpan.style.color = "black";
			last_nameSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkNickname() {
	let nicknameObjValue = document.getElementById("updatedNickname").value.trim();
	let nicknameSpan = document.getElementById("nicknameSpan");
	let oldNicknameObjValue = document.getElementById("originalNickname").value.trim();

	let nicknameIsOk = true;
	let nicknameStr;

	if (nicknameObjValue == "" || nicknameObjValue.length == 0) {
		nicknameStr = "";
		nicknameIsOk = true;
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
		if (nicknameObjValue == oldNicknameObjValue) {
			nicknameStr = "";
			nicknameSpan.innerHTML = "";
		} else {
			nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + nicknameStr;
			nicknameSpan.style.color = "black";
			nicknameSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkFervor() {
	let fervorObj = document.getElementsByName("updatedFervor");
	let fervorObjValue = "";
	for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
		if (fervorObj[fervorIndex].checked && fervorObjValue != "") {
			fervorObjValue += ",";
		}
		fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
	}
	let oldFervorObjValue = document.getElementById("originalFervor").value.trim();
	let fervorSpan = document.getElementById("fervorSpan");
	
	let fervorIsOk = true;
	let fervorStr;
	
	console.log(fervorObjValue+":"+oldFervorObjValue);
	
	if (fervorObjValue == "" || fervorObjValue.length == 0) {
		fervorStr = "";
		fervorIsOk = true;
	} else if (fervorObjValue == oldFervorObjValue) {
		fervorStr = "偏好食物未修改";
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
	} else {
		fervorSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + fervorStr;
		fervorSpan.style.color = "black";
		fervorSpan.style.fontStyle = "normal";
		return true;
	} 
}

function checkEmail() {
	let emailObjValue = document.getElementById("email").value.trim();
	let emailSpan = document.getElementById("emailSpan");
	let oldEmailObjValue = document.getElementById("originalEmail").value.trim();
	
	let emailIsOk = true;
	let emailStr;
	
	if (emailObjValue == "" || emailObjValue.length == 0) {
		emailStr = "";
		emailIsOk = true;
	} else if(oldEmailObjValue == emailObjValue) {
		emailStr = "信箱資訊未修改";
		emailIsOk = true;
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

function checkLocation_code() {
	let location_codeObjValue = document.getElementById("location_code").value;
	let location_codeSpan = document.getElementById("location_codeSpan");
	
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
	
	let addr0IsOk = true;
	let addr0Str;
	
	if (addr0ObjValue == "" || addr0ObjValue.length == 0) {
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

function changeVisibility() {
	document.getElementById("password").type = (document.getElementById("password").type == "password") ? "text" : "password";
	document.getElementById("visibility_switch").value = (document.getElementById("visibility_switch").value == "顯示密碼") ? "隱藏密碼" : "顯示密碼"; 
}

function clearMessage() {
	document.getElementById("accountSpan").innerHTML = "";
	document.getElementById("checkAccount").style = "display:none";
	document.getElementById("passwordSpan").innerHTML = "";
	document.getElementById("first_nameSpan").innerHTML = "";
	document.getElementById("last_nameSpan").innerHTML = "";
	document.getElementById("nicknameSpan").innerHTML = "";
	document.getElementById("birthdaySpan").innerHTML = "";
	document.getElementById("fervorSpan").innerHTML = "";
	document.getElementById("location_codeSpan").innerHTML = "";
	document.getElementById("addr0Span").innerHTML = "";
}