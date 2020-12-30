function checkForm() {
	let counter = 0;
	
	let passwordObjValue = document.getElementById("password").value.trim();
	let oldPasswordObjValue = document.getElementById("oldPassword").value.trim();
	
	let firstNameObjValue = document.getElementById("firstName").value.trim();
	let oldFirstNameObjValue = document.getElementById("oldFirstName").value.trim();
	
	let lastNameObjValue = document.getElementById("lastName").value.trim();
	let oldLastNameObjValue = document.getElementById("oldLastName").value.trim();
	
	let nicknameObjValue = document.getElementById("nickname").value.trim();
	let oldNicknameObjValue = document.getElementById("oldNickname").value.trim();
	
	let birthObjValue = document.getElementById("birth").value.trim();
	let oldBirthObjValue = document.getElementById("oldBirth").value.trim();
	
	let genderObj = document.getElementsByClassName("fervor");
	let genderObjValue = "";
	if (genderObj.length > 0) {
		for (let genderIndex = 0; genderIndex < genderObj.length; genderIndex++) {
			genderObjValue = (genderObj[genderIndex].checked) ? genderObj[genderIndex].value : "";
		}
	} 
	let oldGenderObjValue = document.getElementById("oldGender").value.trim();
	
	let fervorObj = document.getElementsByClassName("fervor");
	let fervorObjValue = "";
	if (fervorObj.length > 0) {
		for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
			if (fervorObjValue != "" && fervorObj[fervorIndex].checked) {
				fervorObjValue += ",";
			}
			fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
		}
	} 
	let oldFervorObjValue = document.getElementById("oldFervor").value.trim();
	
	let emailObjValue = document.getElementById("email").value.trim();
	let oldEmailObjValue = document.getElementById("oldEmail").value.trim();
	let emailSpan = document.getElementById("emailSpan");
	
	let phoneObjValue = document.getElementById("updatedPhone").value.trim();
	let oldPhoneObjValue = document.getElementById("originalPhone").value;
	
	let getEmailObjValue = document.getElementsByName("updatedGetEmail").value;
	let oldGetEmailObjValue = document.getElementById("originalGetEmail").value;
	
	let locationCodeObjValue = document.getElementById("updatedLocationCode").value;
	let oldLocationCodeObjValue = document.getElementById("originalLocationCode").value;
	
	let addr0ObjValue = document.getElementById("updatedAddr0").value.trim();
	let oldAddr0ObjValue = document.getElementById("originalAddr0").value.trim();
	
	let addr1ObjValue = document.getElementById("updatedAddr1").value.trim();
	let oldAddr1ObjValue = document.getElementById("originalAddr1").value.trim();
	
	let addr2ObjValue = document.getElementById("updatedAddr2").value.trim();
	let oldAddr2ObjValue = document.getElementById("originalAddr2").value.trim();
	
	let updatedSpan = document.getElementById("updatedSpan");
	let updatedStr = "";

	let choice=confirm("是否確定要送出修改後的資料？");
	if (choice == true) {
		if (!checkPassword()) {
			return false;
		} else if (!checkFirstName()) {
			return false;
		} else if (!checkLastName()) {
			return false;
		} else if (!checkSameNickname()) {
			return false;
		} else if (!checkGender()) {
			return false;
		} else if (!checkBirth()) {
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
			if (passwordObjValue == oldPasswordObjValue) {
				counter++;
			}
			if (firstNameObjValue == oldFirstNameObjValue) {
				counter++;
			}
			if (lastNameObjValue == oldLastNameObjValue) {
				counter++;
			}
			if (nicknameObjValue == oldNicknameObjValue) {
				counter++;
			}
			if (genderObjValue == oldGenderObjValue) {
				counter++;
			}
			if (birthObjValue == oldBirthObjValue) {
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
			
			if (counter == 14) {
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

function checkAccountPassword() {
	let passwordObjValue = document.getElementById("password").value.trim();
	let passwordSpan = document.getElementById("passwordSpan");
	let oldPasswordObjValue = document.getElementById("oldPassword").value.trim();

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
	} else if (passwordObjValue == oldPasswordObjValue) {
		passwordStr = "";
		passwordIsOk = true;
	} else {
		let accountReg = /[a-zA-Z]{1}[a-zA-Z0-9]{5}/;

		if (!passwordObjValue.match(accountReg)) {
			passwordStr = "密碼不符合格式";
			passwordIsOk = false;
		} else {
			passwordStr = "密碼修改完成";
			passwordIsOk = true;
		}
	}
	if (!passwordIsOk) {
		passwordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + passwordStr;
		passwordSpan.style.color = "red";
		passwordSpan.style.fontStyle = "italic";
		return false;
	} else {
		if (passwordObjValue == oldPasswordObjValue) {
			passwordSpan.innerHTML = "";
		} else {
			passwordSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + passwordStr;
			passwordSpan.style.color = "black";
			passwordSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkFirstName() {
	let firstNameObjValue = document.getElementById("firstName").value.trim();
	let firstNameSpan = document.getElementById("firstNameSpan");
	let oldFirstNameObjValue = document.getElementById("oldFirstName").value.trim();

	let firstNameIsOk = true;
	let firstNameStr;

	if (firstNameObjValue == "" || firstNameObjValue.length == 0) {
		firstNameStr = "姓氏不可為空";
		firstNameIsOk = false;
	} else if (firstNameObjValue == oldFirstNameObjValue) {
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
		if (firstNameObjValue == oldFirstNameObjValue) {
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
	let lastNameObjValue = document.getElementById("lastName").value.trim();
	let lastNameSpan = document.getElementById("lastNameSpan");
	let oldLastNameObjValue = document.getElementById("oldLastName").value.trim();
	
	let lastNameIsOk = true;
	let lastNameStr;

	if (lastNameObjValue == "" || lastNameObjValue.length == 0) {
		lastNameStr = "名字不可為空";
		lastNameIsOk = false;
	} else if (lastNameObjValue == oldLastNameObjValue) {
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
	let nicknameObjValue = document.getElementById("nickname").value.trim();
	let nicknameSpan = document.getElementById("nicknameSpan");
	let oldNicknameObjValue = document.getElementById("oldNickname").value.trim();
	
	let nicknameIsOk = true;
	let nicknameStr;

	if (nicknameObjValue == "" || nicknameObjValue.length == 0) {
		if (checkLastName()) {
			document.getElementById("nickname").value = document.getElementById("lastName").value.trim();
			nicknameStr = "稱呼已設定為您的名字";
			nicknameIsOk = true;
		} else {
			nicknameStr = "稱呼不可為空";
			nicknameIsOk = false;
		}
	} else if (nicknameObjValue == oldNicknameObjValue) {
		nicknameStr = "";
		nicknameIsOk = true;
	} else {
		nicknameStr = "稱呼修改完畢";
		nicknameIsOk = true;
	}
	if (nicknameIsOk) {
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
		}
		return false;
	} 
}

function checkGender() {
	let genderObj = document.getElementsByClassName("gender");
	let genderObjValue = "";
	for (let genderIndex = 0; genderIndex < genderObj.length; genderIndex++) {
		genderObjValue = (genderObj[genderIndex].checked) ? genderObj[genderIndex].value : "";
	}
	let genderSpan = document.getElementById("genderSpan");
	let oldGenderObjValue = document.getElementById("oldGender").value;
	
	if (oldGenderObjValue == genderObjValue) {
		genderStr = "";
		genderIsOk = true;
	} else {
		genderStr = "生理性別修改完成";
		genderIsOk = true;
	}
	if (!genderIsOk) {
		genderSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + genderStr;
		genderSpan.style.color = "red";
		genderSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		if (oldGenderObjValue == genderObjValue) {
			genderSpan.innerHTML = "";
		} else {
			genderSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + genderStr;
			genderSpan.style.color = "black";
			genderSpan.style.fontStyle = "normal";
		}
		return true;
	} 
}
 
function checkBirthday() {
	let birthdayObjValue = document.getElementById("birth").value;
	let birthdaySpan = document.getElementById("birthdaySpan");
	let oldBirthObjValue = document.getElementById("oldBirth").value;

	let birthdayIsOk = true;
	let birthdayStr;
	
	if (birthdayObjValue == "" || birthdayObjValue.length == 0) {
		birthdayStr = "生日不可為空白";
		birthdayIsOk = false;
	} else if (birthdayObjValue.length > 10 || birthdayObjValue.length < 8) {
		birthdayStr = "日期長度錯誤";
		birthdayIsOk = false;
	} else if (oldBirthObjValue == birthdayObjValue) {
		birthdayStr = "";
		birthdayIsOk = true;
	} else {
		let inputYear = parseInt(birthdayObjValue.split("-")[0]);
		let inputMonth = parseInt(birthdayObjValue.split("-")[1]);
		let inputDate = parseInt(birthdayObjValue.split("-")[2]);
		let today = new Date();
		let todayYear = today.getFullYear();
		let todayMonth = today.getMonth() + 1;
		let todayDate = today.getDate();
		let today18 = today.setFullYear(todayYear - 18);
		
		if (todayYear < inputYear) {
			birthdayStr = "無效的出生時間";
			birthdayIsOk = false;
		} else if (todayYear == inputYear && todayMonth < inputMonth) {
			birthdayStr = "無效的出生時間";
			birthdayIsOk = false;
		} else if (todayYear == inputYear && todayMonth == inputMonth && todayDate < inputDate) {
			birthdayStr = "無效的出生時間";
			birthdayIsOk = false;
		} else if (today18 < new Date(birthdayObjValue).getTime()) {
			birthdayStr = "未滿18歲，無法申辦本服務";
			birthdayIsOk = false;
		} else {
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
		if (oldBirthObjValue == birthdayObjValue) {
			birthdaySpan.innerHTML = "";
		} else {
			birthdaySpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + birthdayStr;
			birthdaySpan.style.color = "black";
			birthdaySpan.style.fontStyle = "normal";
		}
		return true;
	} 
}

function checkFervor() {
	let fervorObj = document.getElementsByClassName("fervor");
	let fervorObjValue = "";
	for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
		if (fervorObjValue != "" && fervorObj[fervorIndex].checked) {
			fervorObjValue += ",";
		}
		fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
	}
	let fervorSpan = document.getElementById("fervorSpan");
	let oldFervorObjValue = document.getElementById("oldFervor").value;
	
	let fervorIsOk = true;
	let fervorStr;
	
	if (fervorObjValue == "" || fervorObjValue.length == 0) {
		fervorStr = "偏好食物不可空白";
		fervorIsOk = false;
	} else if (fervorObjValue == oldFervorObjValue) {
		fervorStr = "";
		fervorIsOk = true;
	} else {
		fervorStr = "偏好食物修改完成";
		fervorIsOk = true;
	}
	if (!fervorIsOk) {
		fervorSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + fervorStr;
		fervorSpan.style.color = "red";
		fervorSpan.style.fontStyle = "italic";
		return false;
	}
	else {
		if (fervorObjValue == oldFervorObjValue) {
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
	let emailObjValue = document.getElementById("email").value.trim();
	let emailSpan = document.getElementById("emailSpan");
	let oldEmailObjValue = document.getElementById("oldEmail").value;
	
	let emailIsOk = true;
	let emailStr;
	
	if (emailObjValue == "" || emailObjValue.length == 0) {
		emailStr = "信箱資訊不可為空";
		emailIsOk = false;
	} else if(emailObjValue.indexOf("@") == -1 || emailObjValue.split("@").length > 2 || emailObjValue.indexOf(" ") != -1) {
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
		document.getElementById("checkEmailUsed").style = "display:none";
		return false;
	}
	else {
		if (emailObjValue == oldEmailObjValue) {
			emailSpan.innerHTML = "";
		} else {
			emailSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + emailStr;
			emailSpan.style.color = "black";
			emailSpan.style.fontStyle = "normal";
			document.getElementById("checkEmailUsed").style = "display:inline";
		}
		return true;
	} 
}

function checkPhone() {
	let phoneObjValue = document.getElementById("phone").value;
	let phoneSpan = document.getElementById("phoneSpan");
	let oldPhoneObjValue = document.getElementById("oldPhone").value;
	
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
		phoneStr = "";
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
			document.getElementById("checkEmailUsed").style = "display:inline";
		}
		return true;
	}
}

function checkGetEmail() {
	let getEmailObjValue;
	getEmailObjValue = (document.getElementById("getEmail1") == null) ? "" : document.getElementById("getEmail1").value;
	getEmailObjValue = (document.getElementById("getEmail2") == null) ? "" : document.getElementById("getEmail2").value;
	let getEmailSpan = document.getElementById("getEmailSpan");
	let oldGetEmailObjValue = document.getElementById("oldGetEmail").value;
	
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
	let locationCodeObjValue = document.getElementById("locationCode").value;
	let locationCodeSpan = document.getElementById("locationCodeSpan");
	let oldLocationCodeObjValue = document.getElementById("oldLocationCode").value;
	
	let locationCodeIsOk = true;
	let locationCodeStr;
	
	if (locationCodeObjValue == "" || locationCodeObjValue.length == 0) {
		locationCodeStr = "居住區域不可為空白";
		locationCodeIsOk = false;
	} else if (locationCodeObjValue == oldLocationCodeObjValue) {
		locationCodeStr = "";
		locationCodeIsOk = true;
	} else {
		locationCodeStr = "居住區域已選擇完畢";
		locationCodeIsOk = true;
	}
	if (!locationCodeIsOk) {
		locationCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + locationCodeStr;
		locationCodeSpan.style.color = "red";
		locationCodeSpan.style.fontStyle = "italic";
		return false;
	}
	else {
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
	let addr0ObjValue = document.getElementById("addr0").value.trim();
	let addr0Span = document.getElementById("addr0Span");
	let oldAddr0ObjValue = document.getElementById("oldAddr0").value.trim();
	let addr1ObjValue = document.getElementById("addr1").value.trim();
	let addr2ObjValue = document.getElementById("addr2").value.trim();
	let oldAddr1ObjValue = document.getElementById("oldAddr1").value.trim();
	let oldAddr2ObjValue = document.getElementById("oldAddr2").value.trim();
	
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
	} else if (addr0ObjValue == oldAddr0ObjValue) {
		addr0Str = "";
		addr0IsOk = true;
	} else {
		addr0Str = "生活地點一已修改完畢";
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
	let addr1ObjValue = document.getElementById("addr1").value.trim();
	let addr1Span = document.getElementById("addr1Span");
	let oldAddr1ObjValue = document.getElementById("oldAddr1").value.trim();
	let addr0ObjValue = document.getElementById("addr0").value.trim();
	let addr2ObjValue = document.getElementById("addr2").value.trim();
	let oldAddr0ObjValue = document.getElementById("oldAddr0").value.trim();
	let oldAddr2ObjValue = document.getElementById("oldAddr2").value.trim();
	
	let addr1IsOk = true;
	let addr1Str;
	
	if ((addr1ObjValue == addr0ObjValue && addr0ObjValue != "") || (addr1ObjValue == addr2ObjValue && addr2ObjValue != "")) {
		addr1Str = "生活地點重複填寫";
		addr1IsOk = false;
	} else if ((addr1ObjValue == oldAddr0ObjValue && oldAddr0ObjValue == addr2ObjValue) || (addr1ObjValue == oldAddr2ObjValue && oldAddr2ObjValue == addr0ObjValue)) {
		addr1Str = "生活地點重複填寫";
		addr1IsOk = false;
	} else if (addr1ObjValue == oldAddr1ObjValue) {
		addr1Str = "";
		addr1IsOk = true;
	} else {
		addr1Str = "生活地點二已修改完畢";
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
	let addr2ObjValue = document.getElementById("addr2").value.trim();
	let addr2Span = document.getElementById("addr2Span");
	let oldAddr2ObjValue = document.getElementById("oldAddr2").value.trim();
	let addr0ObjValue = document.getElementById("addr0").value.trim();
	let addr1ObjValue = document.getElementById("addr1").value.trim();
	let oldAddr0ObjValue = document.getElementById("oldAddr0").value.trim();
	let oldAddr1ObjValue = document.getElementById("oldAddr1").value.trim();
	
	let addr2IsOk = true;
	let addr2Str;
	
	if ((addr2ObjValue == addr0ObjValue && addr0ObjValue != "") || (addr2ObjValue == addr1ObjValue && addr1ObjValue != "")) {
		addr2Str = "生活地點重複填寫";
		addr2IsOk = false;
	} else if ((addr2ObjValue == oldAddr0ObjValue && oldAddr0ObjValue == addr1ObjValue) || (addr2ObjValue == oldAddr1ObjValue && oldAddr1ObjValue == addr0ObjValue)) {
		addr2Str = "生活地點重複填寫";
		addr2IsOk = false;
	} else if (addr2ObjValue == oldAddr2ObjValue) {
		addr2Str = "";
		addr2IsOk = true;
	} else {
		addr2Str = "生活地點三已修改完畢";
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

function changeVisibility() {
	document.getElementById("password").type = (document.getElementById("password").type == "password") ? "text" : "password";
	document.getElementById("visibility_switch").innerHTML 
		= (document.getElementById("visibility_switch").textContent == "顯示密碼 visibility") 
		? "隱藏密碼 "+"<i class='material-icons' style='font-size:18px;color:green'>visibility_off</i>" 
		: "顯示密碼 "+"<i class='material-icons' style='font-size:18px;color:red'>visibility</i>"; 
}

function clearMessage() {
	document.getElementById("passwordSpan").innerHTML = "";
	document.getElementById("firstNameSpan").innerHTML = "";
	document.getElementById("lastNameSpan").innerHTML = "";
	document.getElementById("nicknameSpan").innerHTML = "";
	document.getElementById("checkNicknameUsed").style = "display:none";
	document.getElementById("fervorSpan").innerHTML = "";
	document.getElementById("genderSpan").innerHTML = "";
	document.getElementById("birthdaySpan").innerHTML = "";
	document.getElementById("emailSpan").innerHTML = "";
	document.getElementById("checkEmailUsed").style = "display:none";
	document.getElementById("phoneSpan").innerHTML = "";
	document.getElementById("checkPhoneUsed").style = "display:none";
	document.getElementById("locationCodeSpan").innerHTML = "";
	document.getElementById("addr0Span").innerHTML = "";
	document.getElementById("addr1Span").innerHTML = "";
	document.getElementById("addr2Span").innerHTML = "";
	/* 刷新 */
	location.reload(true);
}

function checkSameNickname(){
	let nicknameObjValue = document.getElementById("nickname").value.trim();
	let nicknameSpan = document.getElementById("nicknameSpan");
	let oldNicknameObjValue = document.getElementById("oldNickname").value;
	
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
	let emailObjValue = document.getElementById("email").value.trim();
	let emailSpan = document.getElementById("emailSpan");
	let oldEmailObjValue = document.getElementById("oldEmail").value;
	
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

function checkSamePhone(){
	let phoneObjValue = document.getElementById("phone").value.trim();
	let phoneSpan = document.getElementById("phoneSpan");
	let oldPhoneObjValue = document.getElementById("oldPhone").value;
	
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