function checkForm() {
	let counter = 0;
	
	let firstNameObjValue = document.getElementById("updatedFirstName").value.trim();
	let oldFirstNameObjValue = document.getElementById("originalFirstName").value.trim();
	
	let lastNameObjValue = document.getElementById("updatedLastName").value.trim();
	let oldLastNameObjValue = document.getElementById("originalLastName").value.trim();
	
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
	
	let getEmailObjValue;
	getEmailObjValue = (document.getElementById("updatedgetEmail1") == null) ? "" : document.getElementById("updatedgetEmail1").value;
	getEmailObjValue = (document.getElementById("updatedgetEmail2") == null) ? "" : document.getElementById("updatedgetEmail2").value;
	console.log(getEmailObjValue);
	let oldgetEmailObjValue = document.getElementById("originalgetEmail").value;
	
	let locationCodeObjValue = (document.getElementById("updatedLocationCode") == null) ? "" : document.getElementById("updatedLocationCode").value;
	let oldLocationCodeObjValue = document.getElementById("originalLocationCode").value;
	
	let addr0ObjValue = document.getElementById("updatedAddr0").value.trim();
	let oldAddr0ObjValue = document.getElementById("originalAddr0").value.trim();
	
	let addr1ObjValue = document.getElementById("updatedAddr1").value.trim();
	let oldAddr1ObjValue = document.getElementById("originalAddr1").value.trim();
	
	let addr2ObjValue = document.getElementById("updatedAddr2").value.trim();
	let oldAddr2ObjValue = document.getElementById("originalAddr2").value.trim();
	
	let updatedSpan = document.getElementById("updatedSpan");
	let updatedStr = "";

	if (!checkFirstName()) {
		return false;
	} else if (!checkLastName()) {
		return false;
	} else if (!checkNickname()) {
		return false;
	} else if (!checkFervor()) {
		return false;
	} else if (!checkEmail()) {
		return false;
	} else if (!checkPhone()) {
		return false;
	} else if (!checkgetEmail()) {
		return false;
	} else if (!checkLocationCode()) {
		return false;
	} else if (!checkAddr0()){
		return false;
	} else if (!checkAddr1()){
		return false;
	} else if (!checkAddr2()){
		return false;
	} else {
		if (firstNameObjValue == "" || firstNameObjValue.length == 0 || firstNameObjValue == oldFirstNameObjValue) {
			document.getElementById("updatedFirstName").value = "";
			counter++;
		}
		if (lastNameObjValue == "" || lastNameObjValue.length == 0 || lastNameObjValue == oldLastNameObjValue) {
			document.getElementById("updatedLastName").value = "";
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
		if (getEmailObjValue == "" || getEmailObjValue.length == 0 || getEmailObjValue == oldgetEmailObjValue) {
			document.getElementById("updatedgetEmail1").value = "";
			document.getElementById("updatedgetEmail2").value = "";
			counter++;
		}
		if (locationCodeObjValue == "" || locationCodeObjValue.length == 0 || locationCodeObjValue == oldLocationCodeObjValue) {
			document.getElementById("updatedLocationCode").value = "";
			counter++;
		}
		if (addr0ObjValue == "" || addr0ObjValue.length == 0 || addr0ObjValue == oldAddr0ObjValue) {
			document.getElementById("updatedAddr0").value = "";
			counter++;
		}
		if (addr1ObjValue == oldAddr1ObjValue) {
			document.getElementById("updatedAddr1").value = "";
			counter++;
		}
		if (addr2ObjValue == oldAddr2ObjValue) {
			document.getElementById("updatedAddr2").value = "";
			counter++;
		}
		
		if (counter == 11) {
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

function checkFirstName() {
	let firstNameObjValue = document.getElementById("updatedFirstName").value.trim();
	let firstNameSpan = document.getElementById("firstNameSpan");

	let firstNameIsOk = true;
	let firstNameStr;

	if (firstNameObjValue == "" || firstNameObjValue.length == 0) {
		firstNameStr = "";
		firstNameIsOk = true;
	} else if (firstNameObjValue.length < 4) {
		let charCountBegin = 0;
		let charChineseWordCountBegin = 0x4e00;
		let charChineseWordCountEnd = 0x9fff;

		for (let charIndex = charCountBegin; charIndex < firstNameObjValue.length; charIndex++) {
			let firstNameChar = firstNameObjValue.charCodeAt(charIndex);

			if (firstNameChar < charChineseWordCountBegin || firstNameChar > charChineseWordCountEnd) {
				firstNameIsOk = false;
			}
			if (!firstNameIsOk) {
				break;
			}
		}
		firstNameStr = (!firstNameIsOk) ? "姓氏含有非中文" : "姓氏格式正確";
	}
	if (!firstNameIsOk) {
		firstNameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + firstNameStr;
		firstNameSpan.style.color = "red";
		firstNameSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		if (firstNameObjValue == "" || firstNameObjValue.length == 0) {
			firstNameSpan.innerHTML = "";
		} else {
			firstNameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + firstNameStr;
			firstNameSpan.style.color = "black";
			firstNameSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkLastName() {
	let lastNameObjValue = document.getElementById("updatedLastName").value.trim();
	let lastNameSpan = document.getElementById("lastNameSpan");
	
	let lastNameIsOk = true;
	let lastNameStr;

	if (lastNameObjValue == "" || lastNameObjValue.length == 0) {
		lastNameStr = "";
		lastNameIsOk = true;
	} else if (lastNameObjValue.length < 4) {
		let charCountBegin = 0;
		let charChineseWordCountBegin = 0x4e00;
		let charChineseWordCountEnd = 0x9fff;

		for (let charIndex = charCountBegin; charIndex < lastNameObjValue.length; charIndex++) {
			let lastNameChar = lastNameObjValue.charCodeAt(charIndex);

			if (lastNameChar < charChineseWordCountBegin || lastNameChar > charChineseWordCountEnd) {
				lastNameIsOk = false;
			}
			if (!lastNameIsOk) {
				break;
			}
		}
		lastNameStr = (!lastNameIsOk) ? "名字含有非中文" : "名字格式正確";
	}
	if (!lastNameIsOk) {
		lastNameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + lastNameStr;
		lastNameSpan.style.color = "red";
		lastNameSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		if (lastNameObjValue == "" || lastNameObjValue.length == 0) {
			lastNameSpan.innerHTML = "";
		} else {
			lastNameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + lastNameStr;
			lastNameSpan.style.color = "black";
			lastNameSpan.style.fontStyle = "normal";
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
	if (nicknameIsOk) {
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
	if (fervorIsOk) {
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

function checkgetEmail() {
	let getEmailObjValue;
	getEmailObjValue = (document.getElementById("updatedgetEmail1") == null) ? "" : document.getElementById("updatedgetEmail1").value;
	getEmailObjValue = (document.getElementById("updatedgetEmail2") == null) ? "" : document.getElementById("updatedgetEmail2").value;
	let getEmailSpan = document.getElementById("getEmailSpan");
	
	let getEmailIsOk = true;
	let getEmailStr;
	
	if (getEmailObjValue == "" || getEmailObjValue.length == 0) {
		getEmailStr = "";
		getEmailIsOk = true;
	} else {
		getEmailStr = "接收促銷/優惠意願已選擇完畢";
		getEmailIsOk = true;
	}
	if (getEmailIsOk) {
		if (getEmailObjValue == "" || getEmailObjValue.length == 0) {
			getEmailSpan.innerHTML = "";
		} else {
			getEmailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + getEmailStr;
			getEmailSpan.style.color = "black";
			getEmailSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkLocationCode() {
	let locationCodeObjValue = (document.getElementById("updatedLocationCode") == null) ? "" : document.getElementById("updatedLocationCode").value;
	let locationCodeSpan = document.getElementById("locationCodeSpan");
	
	let locationCodeIsOk = true;
	let locationCodeStr;
	
	if (locationCodeObjValue == "" || locationCodeObjValue.length == 0) {
		locationCodeStr = "";
		locationCodeIsOk = true;
	} else {
		locationCodeStr = "居住區域已選擇完畢";
		locationCodeIsOk = true;
	}
	if (locationCodeIsOk) {
		if (locationCodeObjValue == "" || locationCodeObjValue.length == 0) {
			locationCodeSpan.innerHTML = "";
		} else {
			locationCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + locationCodeStr;
			locationCodeSpan.style.color = "black";
			locationCodeSpan.style.fontStyle = "normal";
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
	if (addr0IsOk) {
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
	let oldAddr1ObjValue = document.getElementById("originalAddr1").value.trim();
	
	let addr1IsOk = true;
	let addr1Str;
	
	if (addr1ObjValue != oldAddr1ObjValue) {
		addr1Str = "生活地點二已填寫完畢";
		addr1IsOk = true;
	} 
	if (addr1IsOk) {
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
	let oldAddr2ObjValue = document.getElementById("originalAddr2").value.trim();
	
	let addr2IsOk = true;
	let addr2Str;
	
	if (addr2ObjValue != oldAddr2ObjValue) {
		addr2Str = "生活地點三已填寫完畢";
		addr2IsOk = true;
	}
	if (addr2IsOk) {
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
	document.getElementById("firstNameSpan").innerHTML = "";
	document.getElementById("lastNameSpan").innerHTML = "";
	document.getElementById("nicknameSpan").innerHTML = "";
	document.getElementById("fervorSpan").innerHTML = "";
	document.getElementById("emailSpan").innerHTML = "";
	document.getElementById("phoneSpan").innerHTML = "";
	document.getElementById("getEmailSpan").innerHTML = "";
	document.getElementById("locationCodeSpan").innerHTML = "";
	document.getElementById("addr0Span").innerHTML = "";
	document.getElementById("addr1Span").innerHTML = "";
	document.getElementById("addr2Span").innerHTML = "";
}