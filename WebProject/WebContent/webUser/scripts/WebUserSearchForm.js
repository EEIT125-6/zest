function checkForm() {
	let counter = 0;
	let accountObjValue = document.getElementById("account").value.trim();
	let nicknameObjValue = document.getElementById("nickname").value.trim();
	let fervorObj = document.getElementsByName("selectedFervor");
	let fervorObjValue = "";
	for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
		fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
	}
	let location_codeObjValue = document.getElementById("location_code").value;
	let searchSpan = document.getElementById("searchSpan");
	let searchStr;
	
	if (!checkAccountName()) {
		return false;
	} else {
		if (accountObjValue == "" && accountObjValue.length == 0) {
			counter++;
		}
		if (nicknameObjValue == "" || nicknameObjValue.length == 0) {
			counter++;
		}
		if (fervorObjValue == "" || fervorObjValue.length == 0) {
			counter++;
		}
		if (location_codeObjValue == "" || location_codeObjValue.length == 0) {
			counter++;
		}
		if (counter == 4){
			searchStr = "至少需填寫/選擇一項條件才能執行搜尋！";
			searchSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + searchStr;
			searchSpan.style.color = "red";
			searchSpan.style.fontStyle = "italic";
			return false;
		} else {
			searchSpan.innerHTML = "";
			return true;
		} 
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

function checkNickname() {
	let nicknameObjValue = document.getElementById("nickname").value.trim();
	let nicknameSpan = document.getElementById("nicknameSpan");

	let nicknameIsOk = true;
	let nicknameStr;

	if (nicknameObjValue != "" || nicknameObjValue.length != 0) {
		nicknameStr = "稱呼填寫完畢";
		nicknameIsOk = true;
	} else {
		nicknameStr = "";
		nicknameIsOk = false;
	}
	if (nicknameIsOk) {
		nicknameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + nicknameStr;
		nicknameSpan.style.color = "black";
		nicknameSpan.style.fontStyle = "normal";
	} else {
		nicknameSpan.innerHTML = "";
	}
}

function checkFervor() {
	let fervorObj = document.getElementsByName("selectedFervor");
	let fervorObjValue = "";
	for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
		fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
	}
	let fervorSpan = document.getElementById("fervorSpan");
	
	let fervorIsOk = true;
	let fervorStr;
	
	if (fervorObjValue != "" || fervorObjValue.length > 0) {
		fervorStr = "偏好食物填寫完成";
		fervorIsOk = true;
	} else {
		fervorStr = "";
		fervorIsOk = false;
	} 
	if (fervorIsOk) {
		fervorSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + fervorStr;
		fervorSpan.style.color = "black";
		fervorSpan.style.fontStyle = "normal";
	} else {
		fervorSpan.innerHTML = "";
	}
}

function checkLocation_code() {
	let location_codeObjValue = document.getElementById("location_code").value;
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
	}
	if (location_codeIsOk) {
		if (location_codeObjValue == "" || location_codeObjValue.length == 0) {
			location_codeSpan.innerHTML = "";
		} else {
			location_codeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + location_codeStr;
			location_codeSpan.style.color = "black";
			location_codeSpan.style.fontStyle = "normal";
		}
	}
}

function clearMessage() {
	document.getElementById("accountSpan").innerHTML = "";
	document.getElementById("nicknameSpan").innerHTML = "";
	document.getElementById("fervorSpan").innerHTML = "";
	document.getElementById("location_codeSpan").innerHTML = "";
}