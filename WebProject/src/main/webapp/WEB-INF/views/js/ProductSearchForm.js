function checkForm() {
	let counter = 0;
	let userLv = document.getElementById("userLv").value.trim();
	let productObjValue = document.getElementById("product").value.trim();
	let storeObjValue = document.getElementById("store").value.trim();
	let priceObjValue = document.getElementById("price").value.trim();
	let quantityObjValue = document.getElementById("quantity").value.trim();
	let accountObjValue = (userLv == -1) ? document.getElementById("account").value.trim() : "";
	let statusObjValue = (userLv == -1) ? document.getElementById("selectedStatus").value : "";
	
	let choice=confirm("是否要依據目前填寫的資料進行查詢？");
	if (choice) {
		if (!checkProduct()) {
			return false;
		} else if (!checkStore()) {
			return false;
		} else if (!checkPrice()) {
			return false;
		} else if (!checkQuantity()) {
			return false;
		} else {
			if (productObjValue == "" && productObjValue.length == 0) {
				counter++;
			}
			if (storeObjValue == "" || storeObjValue.length == 0) {
				counter++;
			}
			if (priceObjValue == "" || priceObjValue.length == 0) {
				counter++;
			}
			if (quantityObjValue == "" || quantityObjValue.length == 0) {
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
				if (accountObjValue == "" || accountObjValue.length == 0) {
					counter++;
				}
				if (counter == 6){
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

function checkProduct() {
	let productObjValue = document.getElementById("product").value.trim();
	let productSpan = document.getElementById("productSpan");
	
	let productIsOk = true;
	let productStr;

	if (productObjValue == "" || productObjValue.length == 0) {
		productStr = "";
		productIsOk = true;
	} else if (productObjValue.length > 60) {
		productStr = "商品名稱長度過長";
		productIsOk = false;
	} else if (productObjValue.indexOf("<") != -1 || productObjValue.indexOf(">") != -1) {
		productStr = "商品名稱不可以包含<、>";
		productIsOk = false;
	} else if (productObjValue.indexOf("&") != -1) {
		productStr = "商品名稱不可以包含&";
		productIsOk = false;
	} else if (productObjValue.indexOf("=") != -1) {
		productStr = "商品名稱不可以包含=";
		productIsOk = false;
	} else {
		productStr = "商品名稱填寫完成";
		productIsOk = true;
	}
	if (!productIsOk) {
		productSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + productStr;
		productSpan.style.color = "red";
		productSpan.style.fontStyle = "italic";
		return false;
	} else {
		if (productObjValue == "" || productObjValue.length == 0) {
			productSpan.innerHTML = "";
		} else {
			productSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + productStr;
			productSpan.style.color = "black";
			productSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkStore() {
	let storeObjValue = document.getElementById("store").value.trim();
	let storeSpan = document.getElementById("storeSpan");

	let storeIsOk = true;
	let storeStr;

	if (storeObjValue == "" || storeObjValue.length == 0) {
		storeStr = "";
		storeIsOk = true;
	} else if (storeObjValue.length > 50) {
		storeStr = "店家名稱長度過長";
		storeIsOk = false;
	} else if (storeObjValue.indexOf("<") != -1 || storeObjValue.indexOf(">") != -1) {
		storeStr = "店家名稱不可以包含<、>";
		storeIsOk = false;
	} else {
		storeStr = "店家名稱填寫完成";
		storeIsOk = true;
	}
	if (!storeIsOk) {
		storeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + storeStr;
		storeSpan.style.color = "red";
		storeSpan.style.fontStyle = "italic";
		return false;
	} else {
		if (storeObjValue == "" || storeObjValue.length == 0) {
			storeSpan.innerHTML = "";
		} else {
			storeSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + storeStr;
			storeSpan.style.color = "black";
			storeSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkPrice() {
	let priceObjValue = document.getElementById("price").value.trim();
	let priceSpan = document.getElementById("priceSpan");

	let priceIsOk = true;
	let priceStr;
	
	if (priceObjValue == "" || priceObjValue.length == 0) {
		priceStr = "";
		priceIsOk = true;
	} else if (priceObjValue.indexOf(".") != -1) {
		priceStr = "價位須為整數";
		priceIsOk = false;
	} else if (priceObjValue.indexOf("-") != -1) {
		priceStr = "價位不可為負";
		priceIsOk = false;
	} else {
		priceStr = "價位填寫完成";
		priceIsOk = true;
	}
	if (!priceIsOk) {
		priceSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + priceStr;
		priceSpan.style.color = "red";
		priceSpan.style.fontStyle = "italic";
		return false;
	} else {
		if (priceObjValue == "" || priceObjValue.length == 0) {
			priceSpan.innerHTML = "";
		} else {
			priceSpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + priceStr;
			priceSpan.style.color = "black";
			priceSpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkQuantity() {
	let quantityObjValue = document.getElementById("quantity").value.trim();
	let quantitySpan = document.getElementById("quantitySpan");

	let quantityIsOk = true;
	let quantityStr;
	
	if (quantityObjValue == "" || quantityObjValue.length == 0) {
		quantityStr = "";
		quantityIsOk = true;
	} else if (quantityObjValue.indexOf(".") != -1) {
		quantityStr = "數量須為整數";
		quantityIsOk = false;
	} else if (quantityObjValue.indexOf("-") != -1) {
		quantityStr = "數量不可為負";
		quantityIsOk = false;
	} else {
		quantityStr = "數量填寫完成";
		quantityIsOk = true;
	}
	if (!quantityIsOk) {
		quantitySpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:red'>cancel</i>" + quantityStr;
		quantitySpan.style.color = "red";
		quantitySpan.style.fontStyle = "italic";
		return false;
	} else {
		if (quantityObjValue == "" || quantityObjValue.length == 0) {
			quantitySpan.innerHTML = "";
		} else {
			quantitySpan.innerHTML = "<i class='material-icons' style='font-size:18px;color:green'>check_circle</i>" + quantityStr;
			quantitySpan.style.color = "black";
			quantitySpan.style.fontStyle = "normal";
		}
		return true;
	}
}

function checkAccount() {
	let accountObjValue = document.getElementById("account").value.trim();
	let accountSpan = document.getElementById("accountSpan");

	let accountIsOk = true;
	let accountStr;

	if (accountObjValue == "" || accountObjValue.length == 0) {
		accountStr = "";
		accountIsOk = true;
	} else if (accountObjValue.length > 30) {
		accountStr = "擁有者帳號長度過長";
		accountIsOk = false;
	} else if (accountObjValue.indexOf("&") != -1) {
		accountStr = "擁有者帳號不可以包含&符號";
		accountIsOk = false;
	} else if (accountObjValue.indexOf("=") != -1) {
		accountStr = "擁有者帳號不可以包含等號";
		accountIsOk = false;
	} else if (accountObjValue.indexOf("_") != -1) {
		accountStr = "擁有者帳號不可以包含底線";
		accountIsOk = false;
	} else if (accountObjValue.indexOf("-") != -1) {
		accountStr = "擁有者帳號不可以包含破折號";
		accountIsOk = false;
	} else if (accountObjValue.indexOf("+") != -1) {
		accountStr = "擁有者帳號不可以包含加號";
		accountIsOk = false;
	} else if (accountObjValue.indexOf(",") != -1 || accountObjValue.indexOf("，") != -1) {
		accountStr = "擁有者帳號不可以包含逗號";
		accountIsOk = false;
	} else if (accountObjValue.indexOf(".") != -1 || accountObjValue.indexOf("。") != -1) {
		accountStr = "擁有者帳號不可以包含句號";
		accountIsOk = false;
	} else if (accountObjValue.indexOf("?") != -1 || accountObjValue.indexOf("？") != -1) {
		accountStr = "擁有者帳號不可以包含問號";
		accountIsOk = false;
	} else if (accountObjValue.indexOf("<") != -1 || accountObjValue.indexOf(">") != -1) {
		accountStr = "擁有者帳號不可以包含<、>";
		accountIsOk = false;
	} else {
		let accountReg = /[a-zA-Z0-9]{1,30}/;

		if (!accountObjValue.match(accountReg)) {
			accountStr = "擁有者帳號不符合格式";
			accountIsOk = false;
		} else {
			accountStr = "擁有者帳號格式正確";
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
	document.getElementById("productSpan").innerHTML = "";
	document.getElementById("storeSpan").innerHTML = "";
	document.getElementById("priceSpan").innerHTML = "";
	document.getElementById("quantitySpan").innerHTML = "";
	if (document.getElementById("userLv").value == -1) {
		document.getElementById("accountSpan").innerHTML = "";
		document.getElementById("statusSpan").innerHTML = "";
	}
}