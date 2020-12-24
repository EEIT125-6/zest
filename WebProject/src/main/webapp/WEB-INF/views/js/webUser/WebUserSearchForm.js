function checkForm() {
	let counter = 0;
	let accountObjValue = document.getElementById("account").value.trim();
	let nicknameObjValue = document.getElementById("nickname").value.trim();
	let fervorObj = document.getElementsByClassName("fervor");
	let fervorObjValue = "";
	for (let fervorIndex = 0; fervorIndex < fervorObj.length; fervorIndex++) {
		fervorObjValue += (fervorObj[fervorIndex].checked) ? fervorObj[fervorIndex].value : "";
	}
	let locationCodeObjValue = document.getElementById("locationCode").value;
	let searchSpan = document.getElementById("searchSpan");
	let userLv = document.getElementById("userLv").value.trim();
	let statusObjValue = (userLv == -1) ? document.getElementById("status").value : "";
	
	let choice=confirm("是否要依據目前填寫的資料進行查詢？");
	if (choice) {
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
			if (locationCodeObjValue == "" || locationCodeObjValue.length == 0) {
				counter++;
			}
			if (userLv != -1) {
				if (counter == 4){
					alert("您沒有選擇任何一項，將使用預設搜尋");
				} else {
					searchSpan.innerHTML = "";
				} 
			} else {
				if (statusObjValue == "" || statusObjValue.length == 0) {
					counter++;
				}
				if (counter == 5){
					alert("您沒有選擇任何一項，將使用預設搜尋");
				} else {
					searchSpan.innerHTML = "";
				} 
			}
			
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

	if (accountObjValue == "" || accountObjValue.length == 0) {
		accountStr = "";
		accountIsOk = true;
	} else if (accountObjValue.length > 20) {
		accountStr = "帳號長度過長";
		accountIsOk = false;
	} else {
		let accountReg = /[a-zA-Z0-9]{1,20}/;

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
	let fervorObj = document.getElementsByClassName("fervor");
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

function checkLocationCode() {
	let locationCodeObjValue = document.getElementById("locationCode").value;
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
	if (!locationCodeIsOk) {
		locationCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + locationCodeStr;
		locationCodeSpan.style.color = "red";
		locationCodeSpan.style.fontStyle = "italic";
	}
	if (locationCodeIsOk) {
		if (locationCodeObjValue == "" || locationCodeObjValue.length == 0) {
			locationCodeSpan.innerHTML = "";
		} else {
			locationCodeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + locationCodeStr;
			locationCodeSpan.style.color = "black";
			locationCodeSpan.style.fontStyle = "normal";
		}
	}
}

function checkStatus() {
	let statusObjValue = document.getElementById("status").value;
	let statusSpan = document.getElementById("statusSpan");
	
	let statusIsOk = true;
	let statusStr;
	
	if (statusObjValue == "" || statusObjValue.length == 0) {
		statusStr = "";
		statusIsOk = true;
	} else {
		statusStr = "帳號狀態已選擇完畢";
		statusIsOk = true;
	}
	if (!statusIsOk) {
		statusSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + statusStr;
		statusSpan.style.color = "red";
		statusSpan.style.fontStyle = "italic";
	}
	if (statusIsOk) {
		if (statusObjValue == "" || statusObjValue.length == 0) {
			statusSpan.innerHTML = "";
		} else {
			statusSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + statusStr;
			statusSpan.style.color = "black";
			statusSpan.style.fontStyle = "normal";
		}
	}
}

function clearMessage() {
	document.getElementById("accountSpan").innerHTML = "";
	document.getElementById("nicknameSpan").innerHTML = "";
	document.getElementById("fervorSpan").innerHTML = "";
	document.getElementById("locationCodeSpan").innerHTML = "";
	if (document.getElementById("userLv").value == -1) {
		document.getElementById("statusSpan").innerHTML = "";
	}
}