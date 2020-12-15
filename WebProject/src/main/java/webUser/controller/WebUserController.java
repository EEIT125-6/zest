package webUser.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import webUser.model.CityInfo;
import webUser.model.FoodFervor;
import webUser.model.Gender;
import webUser.model.UserIdentity;
import webUser.model.UserWilling;
import webUser.model.WebUserData;
import webUser.service.FervorService;
import webUser.service.GenderService;
import webUser.service.IdentityService;
import webUser.service.LocationService;
import webUser.service.WebUserService;
import webUser.service.WillingService;

@Controller
public class WebUserController {
	/* WebUserData Service */
	@Autowired
	WebUserService wus;
	
	/* UserWilling Service */
	@Autowired
	WillingService wis;
	
	/* Identity Service */
	@Autowired
	IdentityService ids;
	
	/* FoodFervor Service */
	@Autowired
	FervorService fvs;
	
	/* Gender Service */
	@Autowired
	GenderService gds;
	
	/* Location Service */
	@Autowired
	LocationService lcs;
	
	/* ServletContext */
	@Autowired
	ServletContext context;
	
	// 本方法於新增時，送出空白的表單讓使用者輸入資料
	@GetMapping(value = "/registerForm")
	public String showEmptyForm(Model model) {
		WebUserData userRegisterData = new WebUserData();
		model.addAttribute("userRegisterData", userRegisterData);
		return "webUser/WebUserRegisterForm";
	}
	
	/* 設定要傳給表單的已知資料 */
	@ModelAttribute
	public void getFormFixedData(Model model) {
		/* 取得下拉選單、單選、多選所需的固定資料 */
		List<UserWilling> willingList = wis.getUserWillingList();
		List<UserIdentity> identityList = ids.getIdentityList();
		List<FoodFervor> fervorList = fvs.getFoodFervorList();
		List<Gender> genderList = gds.getGenderList();
		List<CityInfo> cityInfoList = lcs.getLocationInfoList();
		/* 設定入Model中 */
		model.addAttribute("willingList", willingList);
		model.addAttribute("identityList", identityList);
		model.addAttribute("fervorList", fervorList);
		model.addAttribute("genderList", genderList);
		model.addAttribute("cityInfoList", cityInfoList);
	}
}
