function checkForm() {
	let counter = 0;
	
	let first_nameObjValue = document.getElementById("updatedFirst_name").value.trim();
	let oldFirst_nameObjValue = document.getElementById("originalFirst_name").value.trim();
	
	let last_nameObjValue = document.getElementById("updatedLast_name").value.trim();
	let oldLast_nameObjValue = document.getElementById("originalLast_name").value.trim();
	
	let nicknameObjValue = document.getElementById("updatedNickname").value.trim();
	let oldNicknameObjValue = document.getElementById("originalNickname").value.trim();
	
	let fervorObj = document.getElementsByName("updatedFervor");
	let fervorObjValue = "";
	for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
		if (fervorObjValue != null && fervorObj[fervorIndex].checked) {
			fervorObjValue += ",";
		}
		fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
	}
	let oldFervorObjValue = document.getElementById("originalFervor").value.trim();
	
	let emailObjValue = document.getElementById("updatedEmail").value.trim();
	let oldEmailObjValue = document.getElementById("originalEmail").value.trim();
	
	let phoneObjValue = document.getElementById("updatedPhone").value.trim();
	let oldPhoneObjValue = document.getElementById("originalPhone").value;
	
	let location_codeObjValue = (document.getElementById("updatedLocation_code") == null) ? "" : document.getElementById("updatedLocation_code").value;
	let oldLocation_codeObjValue = document.getElementById("originalLocation_code").value;
	
	let addr0ObjValue = document.getElementById("updatedAddr0").value.trim();
	let oldAddr0ObjValue = document.getElementById("originalAddr0").value.trim();
	
	let addr1ObjValue = document.getElementById("updatedAddr1").value.trim();
	let oldAddr1ObjValue = document.getElementById("originalAddr1").value.trim();
	
	let addr2ObjValue = document.getElementById("updatedAddr2").value.trim();
	let oldAddr2ObjValue = document.getElementById("originalAddr2").value.trim();
	
	let updatedSpan = document.getElementById("updatedSpan");
	let updatedStr = "";

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
		if (first_nameObjValue == "" || first_nameObjValue.length == 0 || first_nameObjValue == oldFirst_nameObjValue) {
			document.getElementById("updatedFirst_name").value = "";
			counter++;
		}
		if (last_nameObjValue == "" || last_nameObjValue.length == 0 || last_nameObjValue == oldLast_nameObjValue) {
			document.getElementById("updatedLast_name").value = "";
			counter++;
		}
		if (nicknameObjValue == "" || nicknameObjValue.length == 0 || nicknameObjValue == oldNicknameObjValue) {
			document.getElementById("updatedNickname").value = "";
			counter++;
		}
		if (fervorObjValue == "" || fervorObjValue.length == 0 || fervorObjValue == oldFervorObjValue) {
			for (let fervorIndex = 0; fervorIndex < 7; fervorIndex++) {
				document.getElementById("updatedFervor"+fervorIndex.toString()).checked = false;
			}
			counter++;
		}
		if (emailObjValue == "" || emailObjValue.length == 0 || emailObjValue == oldEmailObjValue) {
			document.getElementById("updatedEmail").value = "";
			counter++;
		}
		if (phoneObjValue == "" || phoneObjValue.length == 0 || phoneObjValue == oldPhoneObjValue) {
			document.getElementById("updatedPhone").value = "";
			counter++;
		}
		if (location_codeObjValue == "" || location_codeObjValue.length == 0 || location_codeObjValue == oldLocation_codeObjValue) {
			document.getElementById("updatedLocation_code").value = "";
			counter++;
		}
		if (addr0ObjValue == "" || addr0ObjValue.length == 0 || addr0ObjValue == oldAddr0ObjValue) {
			document.getElementById("updatedAddr0").value = "";
			counter++;
		}
		if (addr1ObjValue == "" || addr1ObjValue.length == 0 || addr1ObjValue == oldAddr1ObjValue) {
			document.getElementById("updatedAddr1").value = "";
			counter++;
		}
		if (addr2ObjValue == "" || addr2ObjValue.length == 0 || addr2ObjValue == oldAddr2ObjValue) {
			document.getElementById("updatedAddr2").value = "";
			counter++;
		}
		
		if (counter == 10) {
			updatedStr = "至少需填寫/選擇一項條件才能執行修改/更正！";
			updatedSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + updatedStr;
			updatedSpan.style.color = "red";
			updatedSpan.style.fontStyle = "italic";
			return false;
		} else {
			updatedSpan.innerHTML = "";
			return true;
		}
	}
}

function checkFirst_name() {
	let first_nameObjValue = document.getElementById("updatedFirst_name").value.trim();
	let first_nameSpan = document.getElementById("first_nameSpan");

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
		first_nameStr = (!first_nameIsOk) ? "姓氏含有非中文" : "姓氏格式正確";
	}
	if (!first_nameIsOk) {
		first_nameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + first_nameStr;
		first_nameSpan.style.color = "red";
		first_nameSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		if (first_nameObjValue == "" || first_nameObjValue.length == 0) {
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
		last_nameStr = (!last_nameIsOk) ? "名字含有非中文" : "名字格式正確";
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
		if (nicknameObjValue == "" || nicknameObjValue.length == 0) {
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
		if (fervorObjValue != "" && fervorObj[fervorIndex].checked) {
			fervorObjValue += ",";
		}
		fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
	}
	let fervorSpan = document.getElementById("fervorSpan");
	
	let fervorIsOk = true;
	let fervorStr;
	
	if (fervorObjValue == "" || fervorObjValue.length == 0) {
		fervorStr = "";
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
		if (fervorObjValue == "" || fervorObjValue.length == 0) {
			fervorSpan.innerHTML = "";
		} else {
			fervorSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + fervorStr;
			fervorSpan.style.color = "black";
			fervorSpan.style.fontStyle = "normal";
		}
		return true;
	} 
}

function checkEmail() {
	let emailObjValue = document.getElementById("updatedEmail").value.trim();
	let emailSpan = document.getElementById("emailSpan");
	
	let emailIsOk = true;
	let emailStr;
	
	if (emailObjValue == "" || emailObjValue.length == 0) {
		emailStr = "";
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
		if (emailObjValue == "" || emailObjValue.length == 0) {
			emailSpan.innerHTML = "";
		} else {
			emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailStr;
			emailSpan.style.color = "black";
			emailSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkPhone() {
	let phoneObjValue = document.getElementById("updatedPhone").value;
	let phoneSpan = document.getElementById("phoneSpan");
	
	let phoneIsOk = true;
	let phoneStr;
	let phoneReg = /[0]{1}[2-9]{1}[0-9]{7,9}/
	
	if (phoneObjValue == "" || phoneObjValue.length == 0) {
		phoneStr = "";
		phoneIsOk = true;
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
		if (phoneObjValue == "" || phoneObjValue.length == 0) {
			phoneSpan.innerHTML = "";
		} else {
			phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + phoneStr;
			phoneSpan.style.color = "black";
			phoneSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkLocation_code() {
	let location_codeObjValue = (document.getElementById("updatedLocation_code") == null) ? "" : document.getElementById("updatedLocation_code").value;
	let location_codeSpan = document.getElementById("location_codeSpan");
	
	let location_codeIsOk = true;
	let location_codeStr;
	
	if (location_codeObjValue == "" || location_codeObjValue.length == 0) {
		location_codeStr = "";
		location_codeIsOk = true;
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
		if (location_codeObjValue == "" || location_codeObjValue.length == 0) {
			location_codeSpan.innerHTML = "";
		} else {
			location_codeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + location_codeStr;
			location_codeSpan.style.color = "black";
			location_codeSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkAddr0() {
	let addr0ObjValue = document.getElementById("updatedAddr0").value.trim();
	let addr0Span = document.getElementById("addr0Span");
	
	let addr0IsOk = true;
	let addr0Str;
	
	if (addr0ObjValue == "" || addr0ObjValue.length == 0) {
		addr0Str = "";
		addr0IsOk = true;
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
		if (addr0ObjValue == "" || addr0ObjValue.length == 0) {
			addr0Span.innerHTML = "";
		} else {
			addr0Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + addr0Str;
			addr0Span.style.color = "black";
			addr0Span.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkAddr1() {
	let addr1ObjValue = document.getElementById("updatedAddr1").value.trim();
	let addr1Span = document.getElementById("addr1Span");
	
	let addr1IsOk = true;
	let addr1Str;
	
	if (addr1ObjValue == "" || addr1ObjValue.length == 0) {
		addr1Str = "";
		addr1IsOk = true;
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
		if (addr1ObjValue == "" || addr1ObjValue.length == 0) {
			addr1Span.innerHTML = "";
		} else {
			addr1Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + addr1Str;
			addr1Span.style.color = "black";
			addr1Span.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkAddr2() {
	let addr2ObjValue = document.getElementById("updatedAddr2").value.trim();
	let addr2Span = document.getElementById("addr2Span");
	
	let addr2IsOk = true;
	let addr2Str;
	
	if (addr2ObjValue == "" || addr2ObjValue.length == 0) {
		addr2Str = "";
		addr2IsOk = true;
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
		if (addr2ObjValue == "" || addr2ObjValue.length == 0) {
			addr2Span.innerHTML = "";
		} else {
			addr2Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + addr2Str;
			addr2Span.style.color = "black";
			addr2Span.style.fontStyle = "normal";
		}
		return true;
	}
}

function clearMessage() {
	document.getElementById("first_nameSpan").innerHTML = "";
	document.getElementById("last_nameSpan").innerHTML = "";
	document.getElementById("nicknameSpan").innerHTML = "";
	document.getElementById("fervorSpan").innerHTML = "";
	document.getElementById("emailSpan").innerHTML = "";
	document.getElementById("phoneSpan").innerHTML = "";
	document.getElementById("location_codeSpan").innerHTML = "";
	document.getElementById("addr0Span").innerHTML = "";
	document.getElementById("addr1Span").innerHTML = "";
	document.getElementById("addr2Span").innerHTML = "";
}