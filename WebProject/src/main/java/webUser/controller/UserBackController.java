package webUser.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import webUser.model.CityInfo;
import webUser.model.Gender;
import webUser.model.WebUserData;
import webUser.service.GenderService;
import webUser.service.LocationService;
import webUser.service.WebUserService;
//import webUser.service.FervorService;
//import webUser.service.IdentityService;
//import webUser.service.WillingService;

public class UserBackController {
	/* ServletContext */
	@Autowired
	ServletContext context;
	
	/* WebUserData Service */
	@Autowired
	WebUserService wus;

	/* Gender Service */
	@Autowired
	GenderService gds;

	/* Location Service */
	@Autowired
	LocationService lcs;
	
//	/* UserWilling Service */
//	@Autowired
//	WillingService wis;
//
//	/* Identity Service */
//	@Autowired
//	IdentityService ids;
//
//	/* FoodFervor Service */
//	@Autowired
//	FervorService fvs;
	
	/* 將使用者資料按縣市區域分組統計 */
	@PostMapping(value = "/controller/usrLocalCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserLocationCharData(Model model) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		if (userData == null) {
			message = "請登入後再執行此動作！";
		} else if (!userData.getStatus().equals("active")) {
			message = "本帳號已被停用，無法使用此功能！";
		} else if (userData.getAccountLv().getLv() != -1) {
			message = "本帳號無法使用此功能！";
		}
		
		/* 驗證通過 */
		if (message.equals("")) {
			try {
				/* 取出縣市列表 */
				List<CityInfo> cityInfoList = lcs.getLocationInfoList();
				/* 取回所有使用者列表 */
				List<WebUserData> userList = wus.getAllWebUserData();
				/* 遍歷 */
				for (CityInfo cityInfo: cityInfoList) {
					for (WebUserData user: userList) {
						if (user.getLocationInfo().getCityCode() == cityInfo.getCityCode()) {
							if (map.get(cityInfo.getCityName()) == null) {								
								map.put(cityInfo.getCityName(), 1);
							} else {
								int tmp = (int) map.get(cityInfo.getCityName());
								map.put(cityInfo.getCityName(), tmp + 1);
							}
						}
					}
				}
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 將使用者資料按生理性別分組統計 */
	@PostMapping(value = "/controller/usrGenderCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserGenderCharData (Model model) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		if (userData == null) {
			message = "請登入後再執行此動作！";
		} else if (!userData.getStatus().equals("active")) {
			message = "本帳號已被停用，無法使用此功能！";
		} else if (userData.getAccountLv().getLv() != -1) {
			message = "本帳號無法使用此功能！";
		}
		
		/* 驗證通過 */
		if (message.equals("")) {
			try {
				/* 取出生理性別列表 */
				List<Gender> genderList = gds.getGenderList();
				/* 取回所有使用者列表 */
				List<WebUserData> userList = wus.getAllWebUserData();
				/* 遍歷 */
				for (Gender gender: genderList) {
					for (WebUserData user: userList) {
						if (user.getGender().getGenderCode().equals(gender.getGenderCode())) {
							if (map.get(gender.getGenderText()) == null) {								
								map.put(gender.getGenderText(), 1);
							} else {
								int tmp = (int) map.get(gender.getGenderText());
								map.put(gender.getGenderText(), tmp + 1);
							}
						}
					}
				}
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 將使用者資料按加入時段分組統計(一次僅會顯示一個年度的資料，預設值為2020) */
	@PostMapping(value = "/controller/usrJoinDateGenderCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserJoinDateCharData (
			Model model, 
			@RequestParam(value = "year", defaultValue = "2020") String year) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		if (userData == null) {
			message = "請登入後再執行此動作！";
		} else if (!userData.getStatus().equals("active")) {
			message = "本帳號已被停用，無法使用此功能！";
		} else if (userData.getAccountLv().getLv() != -1) {
			message = "本帳號無法使用此功能！";
		}
		
		/* 驗證通過 */
		if (message.equals("")) {
			try {
				/* 取回註冊時間列表 */
				List<LocalDate> joinDateList = wus.getAllWebUserJoinDate(year);
				/* 取回該年度所有註冊的使用者列表 */
				List<WebUserData> userList = wus.getAllYearWebUserData(year);
				/* 遍歷 */
				for (LocalDate jDate: joinDateList) {
					for (WebUserData user: userList) {
						if (user.getJoinDate().toLocalDate().getYear() == jDate.getYear() && user.getJoinDate().toLocalDate().getMonth() == jDate.getMonth()) {
							if (map.get(String.valueOf(jDate.getYear()) + "-" + jDate.getMonth().toString()) == null) {
								map.put(String.valueOf(jDate.getYear()) + "-" + jDate.getMonth().toString(), 1);
							} else {
								int tmp = (int) map.get(String.valueOf(jDate.getYear()) + "-" + jDate.getMonth().toString());
								map.put(String.valueOf(jDate.getYear()) + "-" + jDate.getMonth().toString(), tmp + 1);
							}
						}
					}
				}
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	
}
