function checkForm() {
	let counter = 0;
	
	let firstNameObjValue = document.getElementById("updatedFirstName").value.trim();
	let oldFirstNameObjValue = document.getElementById("originalFirstName").value.trim();
	
	let lastNameObjValue = document.getElementById("updatedLastName").value.trim();
	let oldLastNameObjValue = document.getElementById("originalLastName").value.trim();
	
	let nicknameObjValue = document.getElementById("updatedNickname").value.replace('<', ' ').replace('>', '').trim();
	let oldNicknameObjValue = document.getElementById("originalNickname").value.trim();
	
	let fervorObj = document.getElementsByName("updatedFervor");
	let fervorObjValue = "";
	if (fervorObj.length > 0) {
		for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
			if (fervorObjValue != "" && fervorObj[fervorIndex].checked) {
				fervorObjValue += ",";
			}
			fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
		}
	} 

	let oldFervorObjValue = document.getElementById("originalFervor").value.trim();
	
	let emailObjValue = document.getElementById("updatedEmail").value.replace('<', ' ').replace('>', '').trim();
	let oldEmailObjValue = document.getElementById("originalEmail").value.trim();
	let emailSpan = document.getElementById("emailSpan");
	
	let phoneObjValue = document.getElementById("updatedPhone").value.trim();
	let oldPhoneObjValue = document.getElementById("originalPhone").value;
	
	let getEmailObjValue = document.getElementsByName("updatedGetEmail").value;
	let oldGetEmailObjValue = document.getElementById("originalGetEmail").value;
	
	let locationCodeObjValue = document.getElementById("updatedLocationCode").value;
	let oldLocationCodeObjValue = document.getElementById("originalLocationCode").value;
	
	let addr0ObjValue = document.getElementById("updatedAddr0").value.replace('<', ' ').replace('>', '').trim();
	let oldAddr0ObjValue = document.getElementById("originalAddr0").value.trim();
	
	let addr1ObjValue = document.getElementById("updatedAddr1").value.replace('<', ' ').replace('>', '').trim();
	let oldAddr1ObjValue = document.getElementById("originalAddr1").value.trim();
	
	let addr2ObjValue = document.getElementById("updatedAddr2").value.replace('<', ' ').replace('>', '').trim();
	let oldAddr2ObjValue = document.getElementById("originalAddr2").value.trim();
	
	let updatedSpan = document.getElementById("updatedSpan");
	let updatedStr = "";

	let choice=confirm("是否確定要送出修改後的資料？");
	if (choice == true) {
		if (!checkFirstName()) {
			return false;
		} else if (!checkLastName()) {
			return false;
		} else if (!checkSameNickname()) {
			return false;
		} else if (!checkFervor()) {
			return false;
		} else if (!checkSameEmail()) {
			return false;
		} else if (!checkSamePhone()) {
			return false;
		} else if (!checkGetEmail()) {
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
			if (firstNameObjValue == oldFirstNameObjValue) {
				counter++;
			}
			if (lastNameObjValue == oldLastNameObjValue) {
				counter++;
			}
			if (nicknameObjValue == oldNicknameObjValue) {
				counter++;
			}
			if (fervorObjValue == oldFervorObjValue) {
				counter++;
			}
			if (emailObjValue == oldEmailObjValue) {
				counter++;
			} else {
				if (emailSpan.textContent != "check_circle可使用此電子信箱！") {
					alert("請先執行電子信箱檢查");
					return false;
				} else if (emailCheckCodeSpan.textContent != "check_circle聯絡信箱驗證成功") {
					alert("請先執行電子信箱驗證");
					return false;
				}
			}
			if (phoneObjValue == oldPhoneObjValue) {
				counter++;
			}
			if (getEmailObjValue == oldGetEmailObjValue) {
				counter++;
			} 
			if (locationCodeObjValue == oldLocationCodeObjValue) {
				counter++;
			}
			if (addr0ObjValue == oldAddr0ObjValue) {
				counter++;
			}
			if (addr1ObjValue == oldAddr1ObjValue && addr1ObjValue != null) {
				counter++;
			}
			if (addr2ObjValue == oldAddr2ObjValue && addr2ObjValue != null) {
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
	} else {
		return false;
	}
}

function checkFirstName() {
	let firstNameObjValue = document.getElementById("updatedFirstName").value.trim();
	let firstNameSpan = document.getElementById("firstNameSpan");
	let oldFirstNameObjValue = document.getElementById("originalFirstName").value;
	
	let firstNameIsOk = true;
	let firstNameStr;

	if (firstNameObjValue == "" || firstNameObjValue.length == 0) {
		firstNameStr = "姓氏不可為空";
		firstNameIsOk = false;
	} else if (oldFirstNameObjValue == firstNameObjValue) {
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
		if (oldFirstNameObjValue == firstNameObjValue) {
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
	let oldLastNameObjValue = document.getElementById("originalLastName").value;
	
	let lastNameIsOk = true;
	let lastNameStr;

	if (lastNameObjValue == "" || lastNameObjValue.length == 0) {
		lastNameStr = "名字不可為空";
		lastNameIsOk = false;
	} else if (lastNameObjValue == oldLastNameObjValue) {
		lastNameStr = "";
		lastNameIsOk = true;
	} else if (lastNameObjValue.length < 23) {
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
	} else {
		if (lastNameObjValue == oldLastNameObjValue) {
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
	let nicknameObjValue = document.getElementById("updatedNickname").value.replace('<', ' ').replace('>', '').trim();
	let nicknameSpan = document.getElementById("nicknameSpan");
	let oldNicknameObjValue = document.getElementById("originalNickname").value;
	
	let nicknameIsOk = true;
	let nicknameStr;

	if (nicknameObjValue == "" || nicknameObjValue.length == 0) {
		if (checkLastName()) {
			document.getElementById("updatedNickname").value = document.getElementById("updatedLastName").value.trim();
			nicknameStr = "稱呼已設定為您的名字";
			nicknameIsOk = true;
		} else {
			nicknameStr = "稱呼不可為空";
			nicknameIsOk = false;
		}
	} else if (nicknameObjValue.length > 25) {
		nicknameStr = "稱呼長度過長";
		nicknameIsOk = false;
	} else if (nicknameObjValue == oldNicknameObjValue) {
		nicknameStr = "";
		nicknameIsOK = true;
	} else {
		nicknameStr = "稱呼填寫完畢";
		nicknameIsOk = true;
	}
	if (!nicknameIsOk) {
		nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + nicknameStr;
		nicknameSpan.style.color = "black";
		nicknameSpan.style.fontStyle = "normal";
		return true;
	} else {
		if (nicknameObjValue == oldNicknameObjValue) {
			nicknameSpan.innerHTML = "";
		} else {
			nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + nicknameStr;
			nicknameSpan.style.color = "red";
			nicknameSpan.style.fontStyle = "italic";
			checkUpdateNickname();
		}
		return false;
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
	let oldFervorObjValue = document.getElementById("originalFervor").value;
	
	let fervorIsOk = true;
	let fervorStr;
	
	if (fervorObjValue == "" || fervorObjValue.length == 0) {
		fervorStr = "偏好食物不可為空";
		fervorIsOk = false;
	} else if (fervorObjValue == oldFervorObjValue) {
		fervorIsOk = true;
	} else {
		fervorStr = "偏好食物填寫完成";
		fervorIsOk = true;
	}
	if (fervorIsOk) {
		if (fervorObjValue == oldFervorObjValue) {
			fervorSpan.innerHTML = "";
		} else {
			fervorSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + fervorStr;
			fervorSpan.style.color = "black";
			fervorSpan.style.fontStyle = "normal";
		}
		return true;
	} else {
		fervorSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + fervorStr;
		fervorSpan.style.color = "red";
		fervorSpan.style.fontStyle = "italic";
		return false;
	}
}

function checkEmail() {
	let emailObjValue = document.getElementById("updatedEmail").value.replace('<', ' ').replace('>', '').trim();
	let emailSpan = document.getElementById("emailSpan");
	let oldEmailObjValue = document.getElementById("originalEmail").value;
	
	let emailIsOk = true;
	let emailStr;
	
	if (emailObjValue == "" || emailObjValue.length == 0) {
		emailStr = "信箱資訊不可為空";
		emailIsOk = false;
	} else if(emailObjValue.indexOf("@") == -1 || emailObjValue.split("@").length > 2 || emailObjValue.indexOf(" ") != -1) {
		emailStr = "信箱資訊格式錯誤";
		emailIsOk = false;
	} else if(emailObjValue.indexOf("@") == emailObjValue.length - 1 || emailObjValue.lastIndexOf(".") == emailObjValue.length - 1) {
		emailStr = "信箱資訊格式錯誤";
		emailIsOk = false;
	} else if (emailObjValue == oldEmailObjValue) {
		emailStr = "信箱資訊已填寫完成";
		emailIsOk = true;
	} else {
		emailStr = "信箱資訊已修改完成";
		emailIsOk = true;
	}
	if (!emailIsOk) {
		emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + emailStr;
		emailSpan.style.color = "red";
		emailSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		if (emailObjValue == oldEmailObjValue) {
			emailSpan.innerHTML = "";
		} else {
			emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailStr;
			emailSpan.style.color = "black";
			emailSpan.style.fontStyle = "normal";
			checkUpdateEmail();
		}
		return true;
	} 
}

function checkPhone() {
	let phoneObjValue = document.getElementById("updatedPhone").value;
	let phoneSpan = document.getElementById("phoneSpan");
	let oldPhoneObjValue = document.getElementById("originalPhone").value;
	
	let phoneIsOk = true;
	let phoneStr;
	let phoneReg = /[0]{1}[2-9]{1}[0-9]{7,9}/
	
	if (phoneObjValue == "" || phoneObjValue.length == 0) {
		phoneStr = "連絡電話不可為空";
		phoneIsOk = false;
	} else if (phoneObjValue.length < 9 || phoneObjValue.indexOf(" ") != -1) {
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
	} else if (phoneObjValue == oldPhoneObjValue){
		phoneStr = "連絡電話已填寫完畢";
		phoneIsOk = true;
	} else {
		phoneStr = "連絡電話已修改完畢";
		phoneIsOk = true;
	}
	if (!phoneIsOk) {
		phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + phoneStr;
		phoneSpan.style.color = "red";
		phoneSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		if (phoneObjValue == oldPhoneObjValue) {
			phoneSpan.innerHTML = "";
		} else {
			phoneSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + phoneStr;
			phoneSpan.style.color = "black";
			phoneSpan.style.fontStyle = "normal";
			checkUpdatePhone();
		}
		return true;
	}
}

function checkGetEmail() {
	let getEmailObjValue;
	getEmailObjValue = (document.getElementById("updatedGetEmail1") == null) ? "" : document.getElementById("updatedGetEmail1").value;
	getEmailObjValue = (document.getElementById("updatedGetEmail2") == null) ? "" : document.getElementById("updatedGetEmail2").value;
	let getEmailSpan = document.getElementById("getEmailSpan");
	let oldGetEmailObjValue = document.getElementById("originalGetEmail").value;
	
	let getEmailIsOk = true;
	let getEmailStr;
	
	if (getEmailObjValue != oldGetEmailObjValue) {
		getEmailStr = "接收促銷/優惠意願已修改完畢";
		getEmailIsOk = true;
	}
	if (getEmailIsOk) {
		if (getEmailObjValue == oldGetEmailObjValue) {
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
	let oldLocationCodeObjValue = document.getElementById("originalLocationCode").value;
	
	let locationCodeIsOk = true;
	let locationCodeStr;
	
	if (locationCodeObjValue != oldLocationCodeObjValue) {
		locationCodeStr = "居住區域已修改完畢";
		locationCodeIsOk = true;
	}
	if (locationCodeIsOk) {
		if (locationCodeObjValue == oldLocationCodeObjValue) {
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
	let addr0ObjValue = document.getElementById("updatedAddr0").value.replace('<', ' ').replace('>', '').trim();
	let addr0Span = document.getElementById("addr0Span");
	let oldAddr0ObjValue = document.getElementById("originalAddr0").value.trim();
	let addr1ObjValue = document.getElementById("updatedAddr1").value.trim();
	let addr2ObjValue = document.getElementById("updatedAddr2").value.trim();
	let oldAddr1ObjValue = document.getElementById("originalAddr1").value.trim();
	let oldAddr2ObjValue = document.getElementById("originalAddr2").value.trim();
	
	let addr0IsOk = true;
	let addr0Str;
	
	if (addr0ObjValue == "" || addr0ObjValue.length == 0) {
		addr0Str = "生活地點一不可為空";
		addr0IsOk = false;
	} else if ((addr0ObjValue == addr1ObjValue && addr1ObjValue != "") || (addr0ObjValue == addr2ObjValue && addr2ObjValue != "")) {
		addr0Str = "生活地點重複填寫";
		addr0IsOk = false;
	} else if ((addr0ObjValue == oldAddr1ObjValue && oldAddr1ObjValue == addr2ObjValue) || (addr0ObjValue == oldAddr2ObjValue && oldAddr2ObjValue == addr1ObjValue)) {
		addr0Str = "生活地點重複填寫";
		addr0IsOk = false;
	} else {
		addr0Str = "生活地點一已填寫完畢";
		addr0IsOk = true;
	}
	if (addr0IsOk) {
		if (addr0ObjValue == oldAddr0ObjValue) {
			addr0Span.innerHTML = "";
		} else {			
			addr0Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + addr0Str;
			addr0Span.style.color = "black";
			addr0Span.style.fontStyle = "normal";
		}
		return true;
	} else {
		addr0Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + addr0Str;
		addr0Span.style.color = "red";
		addr0Span.style.fontStyle = "italic";
		return false;
	}
}

function checkAddr1() {
	let addr1ObjValue = document.getElementById("updatedAddr1").value.replace('<', ' ').replace('>', '').trim();
	let addr1Span = document.getElementById("addr1Span");
	let oldAddr1ObjValue = document.getElementById("originalAddr1").value.trim();
	let addr0ObjValue = document.getElementById("updatedAddr0").value.trim();
	let addr2ObjValue = document.getElementById("updatedAddr2").value.trim();
	let oldAddr0ObjValue = document.getElementById("originalAddr0").value.trim();
	let oldAddr2ObjValue = document.getElementById("originalAddr2").value.trim();
	
	let addr1IsOk = true;
	let addr1Str;
	
	if ((addr1ObjValue == addr0ObjValue && addr0ObjValue != "") || (addr1ObjValue == addr2ObjValue && addr2ObjValue != "")) {
		addr1Str = "生活地點重複填寫";
		addr1IsOk = false;
	} else if ((addr1ObjValue == oldAddr0ObjValue && oldAddr0ObjValue == addr2ObjValue) || (addr1ObjValue == oldAddr2ObjValue && oldAddr2ObjValue == addr0ObjValue)) {
		addr1Str = "生活地點重複填寫";
		addr1IsOk = false;
	} else {
		addr1Str = "生活地點二已填寫完畢";
		addr1IsOk = true;
	}
	if (addr1IsOk) {
		if (addr1ObjValue == oldAddr1ObjValue || (addr1ObjValue == "" || addr1ObjValue.length == 0)) {
			addr1Span.innerHTML = "";
		} else {
			addr1Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + addr1Str;
			addr1Span.style.color = "black";
			addr1Span.style.fontStyle = "normal";
		}
		return true;
	} else {
		addr1Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + addr1Str;
		addr1Span.style.color = "red";
		addr1Span.style.fontStyle = "italic";
		return false;
	}
}

function checkAddr2() {
	let addr2ObjValue = document.getElementById("updatedAddr2").value.replace('<', ' ').replace('>', '').trim();
	let addr2Span = document.getElementById("addr2Span");
	let oldAddr2ObjValue = document.getElementById("originalAddr2").value.trim();
	let addr0ObjValue = document.getElementById("updatedAddr0").value.trim();
	let addr1ObjValue = document.getElementById("updatedAddr1").value.trim();
	let oldAddr0ObjValue = document.getElementById("originalAddr0").value.trim();
	let oldAddr1ObjValue = document.getElementById("originalAddr1").value.trim();
	
	let addr2IsOk = true;
	let addr2Str;
	
	if ((addr2ObjValue == addr0ObjValue && addr0ObjValue != "") || (addr2ObjValue == addr1ObjValue && addr1ObjValue != "")) {
		addr2Str = "生活地點重複填寫";
		addr2IsOk = false;
	} else if ((addr2ObjValue == oldAddr0ObjValue && oldAddr0ObjValue == addr1ObjValue) || (addr2ObjValue == oldAddr1ObjValue && oldAddr1ObjValue == addr0ObjValue)) {
		addr2Str = "生活地點重複填寫";
		addr2IsOk = false;
	} else {
		addr2Str = "生活地點三已填寫完畢";
		addr2IsOk = true;
	}
	if (addr2IsOk) {
		if (addr2ObjValue != oldAddr2ObjValue || (addr2ObjValue == "" || addr2ObjValue.length == 0)) {
			addr2Span.innerHTML = "";
		} else {
			addr2Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + addr2Str;
			addr2Span.style.color = "black";
			addr2Span.style.fontStyle = "normal";
		}
		return true;
	} else {
		addr2Span.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + addr2Str;
		addr2Span.style.color = "red";
		addr2Span.style.fontStyle = "italic";
		return false;
	}
}

function clearMessage() {
	document.getElementById("picSpan").innerHTML = "";
	document.getElementById("picPreview").src = "";
	document.getElementById("firstNameSpan").innerHTML = "";
	document.getElementById("lastNameSpan").innerHTML = "";
	document.getElementById("nicknameSpan").innerHTML = "";
	document.getElementById("fervorSpan").innerHTML = "";
	document.getElementById("emailSpan").innerHTML = "";
	document.getElementById("phoneSpan").innerHTML = "";
	document.getElementById("locationCodeSpan").innerHTML = "";
	document.getElementById("addr0Span").innerHTML = "";
	document.getElementById("addr1Span").innerHTML = "";
	document.getElementById("addr2Span").innerHTML = "";
	/* 刷新 */
	location.reload(true);
}

function checkSameNickname(){
	let nicknameObjValue = document.getElementById("updatedNickname").value.replace('<', ' ').replace('>', '').trim();
	let nicknameSpan = document.getElementById("nicknameSpan");
	let oldNicknameObjValue = document.getElementById("originalNickname").value;
	
	if (nicknameObjValue != oldNicknameObjValue) {
		if (nicknameSpan.textContent != "check_circle可使用此稱呼！") {
			alert("請先執行稱呼檢查");
			return false;
		} else {
			if (!checkNickname()) {
				return false;
			} else {
				return true;
			}
		}
	} else {
		return true;
	}
}

function checkSameEmail(){
	let emailObjValue = document.getElementById("updatedEmail").value.replace('<', ' ').replace('>', '').trim();
	let emailSpan = document.getElementById("emailSpan");
	let oldEmailObjValue = document.getElementById("originalEmail").value;
	
	if (emailObjValue != oldEmailObjValue) {
		if (emailSpan.textContent != "check_circle可使用此電子信箱！") {
			alert("請先執行電子信箱檢查");
			return false;
		} else {
			if (!checkEmail()) {
				return false;
			} else {
				return true;
			}
		}
	} else {
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
	} else if (checkCode != emailCheckCodeObjValue) {
		emailCheckCodeStr = "聯絡信箱驗證碼錯誤";
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

function checkSamePhone(){
	let phoneObjValue = document.getElementById("updatedPhone").value.trim();
	let phoneSpan = document.getElementById("phoneSpan");
	let oldPhoneObjValue = document.getElementById("originalPhone").value;
	
	if (phoneObjValue != oldPhoneObjValue) {
		if (phoneSpan.textContent != "check_circle可使用此聯絡電話！") {
			alert("請先執行聯絡電話檢查");
			return false;
		} else {
			if (!checkPhone()) {
				return false;
			} else {
				return true;
			}
		}
	} else {
		return true;
	}
}