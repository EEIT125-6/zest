function checkForm() {
	let counter = 0;
	let userLv = document.getElementById("userLv").value.trim();
	let stnameObjValue = document.getElementById("selectedStname").value.trim();
	let ownerObjValue = document.getElementById("selectedOwner").value.trim();
	let sclassObjValue = (userLv == -1) ? document.getElementById("sSclass").value : "";
	let statusObjValue = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
	
	let choice=confirm("是否要依據目前填寫的資料進行查詢？");
	if (choice) {
		if (!checkStname()) {
			return false;
		} else if (!checkOwner()) {
			return false;
		} else if (!checkSelectedSclass()) {
			return false;
		} else {
			if (stnameObjValue == "" && stnameObjValue.length == 0) {
				counter++;
			}
			if (ownerObjValue == "" || ownerObjValue.length == 0) {
				counter++;
			}
			if (userLv != -1) {
				if (counter == 2){
					alert("您沒有選擇任何一項，將使用預設搜尋");
				} else {
					searchSpan.innerHTML = "";
				} 
			} else {
				if (sclassObjValue == "" || sclassObjValue.length == 0) {
					counter++;
				}
				if (statusObjValue == "" || statusObjValue.length == 0) {
					counter++;
				}
				if (counter == 4){
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

function checkStname() {
	let stnameObjValue = document.getElementById("selectedStname").value.trim();
	let stnameSpan = document.getElementById("stnameSpan");

	let stnameIsOk = true;
	let stnameStr;

	if (stnameObjValue == "" || stnameObjValue.length == 0) {
		stnameStr = "";
		stnameIsOk = true;
	} else if (stnameObjValue.length > 50) {
		stnameStr = "店家名稱長度過長";
		stnameIsOk = false;
	} else if (stnameObjValue.indexOf("<") != -1 || stnameObjValue.indexOf(">") != -1) {
		stnameStr = "店家名稱不可以包含<、>";
		stnameIsOk = false;
	} else if (stnameObjValue.indexOf("&") != -1) {
		stnameStr = "店家名稱不可以包含&";
		stnameIsOk = false;
	} else if (stnameObjValue.indexOf("=") != -1) {
		stnameStr = "店家名稱不可以包含等號";
		stnameIsOk = false;
	} else {
		stnameStr = "店家名稱填寫完成";
		stnameIsOk = true;
	}
	if (!stnameIsOk) {
		stnameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + stnameStr;
		stnameSpan.style.color = "red";
		stnameSpan.style.fontStyle = "italic";
		return false;
	} else {
		if (stnameObjValue == "" || stnameObjValue.length == 0) {
			stnameSpan.innerHTML = "";
		} else {
			stnameSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + stnameStr;
			stnameSpan.style.color = "black";
			stnameSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkOwner() {
	let ownerObjValue = document.getElementById("selectedOwner").value.trim();
	let ownerSpan = document.getElementById("ownerSpan");

	let ownerIsOk = true;
	let ownerStr;

	if (ownerObjValue == "" || ownerObjValue.length == 0) {
		ownerStr = "";
		ownerIsOk = true;
	} else if (ownerObjValue.length > 30) {
		ownerStr = "擁有者帳號長度過長";
		ownerIsOk = false;
	} else if (ownerObjValue.indexOf("&") != -1) {
		ownerStr = "擁有者帳號不可以包含&符號";
		ownerIsOk = false;
	} else if (ownerObjValue.indexOf("=") != -1) {
		ownerStr = "擁有者帳號不可以包含等號";
		ownerIsOk = false;
	} else if (ownerObjValue.indexOf("_") != -1) {
		ownerStr = "擁有者帳號不可以包含底線";
		ownerIsOk = false;
	} else if (ownerObjValue.indexOf("-") != -1) {
		ownerStr = "擁有者帳號不可以包含破折號";
		ownerIsOk = false;
	} else if (ownerObjValue.indexOf("+") != -1) {
		ownerStr = "擁有者帳號不可以包含加號";
		ownerIsOk = false;
	} else if (ownerObjValue.indexOf(",") != -1 || ownerObjValue.indexOf("，") != -1) {
		ownerStr = "擁有者帳號不可以包含逗號";
		ownerIsOk = false;
	} else if (ownerObjValue.indexOf(".") != -1 || ownerObjValue.indexOf("。") != -1) {
		ownerStr = "擁有者帳號不可以包含句號";
		ownerIsOk = false;
	} else if (ownerObjValue.indexOf("?") != -1 || ownerObjValue.indexOf("？") != -1) {
		ownerStr = "擁有者帳號不可以包含問號";
		ownerIsOk = false;
	} else if (ownerObjValue.indexOf("<") != -1 || ownerObjValue.indexOf(">") != -1) {
		ownerStr = "擁有者帳號不可以包含<、>";
		ownerIsOk = false;
	} else {
		let ownerReg = /[a-zA-Z0-9]{1,30}/;

		if (!ownerObjValue.match(ownerReg)) {
			ownerStr = "擁有者帳號不符合格式";
			ownerIsOk = false;
		} else {
			ownerStr = "擁有者帳號格式正確";
			ownerIsOk = true;
		}
	}
	if (!ownerIsOk) {
		ownerSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + ownerStr;
		ownerSpan.style.color = "red";
		ownerSpan.style.fontStyle = "italic";
		return false;
	} else {
		if (ownerObjValue == "" || ownerObjValue.length == 0) {
			ownerSpan.innerHTML = "";
		} else {
			ownerSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + ownerStr;
			ownerSpan.style.color = "black";
			ownerSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkSelectedSclass() {
	let sclassObjValue = document.getElementById("sSclass").value;
	let sclassSpan = document.getElementById("sclassSpan");
	
	let sclassIsOk = true;
	let sclassStr;
	
	if (sclassObjValue == "" || sclassObjValue.length == 0) {
		sclassStr = "";
		sclassIsOk = true;
	} else {
		sclassStr = "店家類型選擇完成";
		sclassIsOk = true;
	} 
	if (!sclassIsOk) {
		sclassSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + sclassStr;
		sclassSpan.style.color = "red";
		sclassSpan.style.fontStyle = "italic";
		return false;
	}  else {
		if (sclassObjValue == "" || sclassObjValue.length == 0) {
			sclassSpan.innerHTML = "";
		} else {
			sclassSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + sclassStr;
			sclassSpan.style.color = "black";
			sclassSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkStatus() {
	let statusObjValue = document.getElementById("selectedStatus").value;
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
	} else {
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
	document.getElementById("stnameSpan").innerHTML = "";
	document.getElementById("ownerSpan").innerHTML = "";
	if (document.getElementById("userLv").value == -1) {
		document.getElementById("sclassSpan").innerHTML = "";
		document.getElementById("statusSpan").innerHTML = "";
	}
}