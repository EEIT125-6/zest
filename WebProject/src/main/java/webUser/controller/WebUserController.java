package webUser.controller;

import java.sql.Date;
import java.util.List;

import java.time.LocalDate;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

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
@SessionAttributes({"registerEmail", "checkCode"})
@RequestMapping("/webUser")
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
	
	/* Today */
	final String today = String.valueOf(LocalDate.now());
	
	/* 傳送表單所必需的資料 */
	@GetMapping(value="/registerForm")
	public String doCreateRegisterForm(Model model) {
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
		
		/* 前往註冊畫面 */
		return "webUser/WebUserRegisterForm";
	}
	
	@PostMapping(value = "/checkRegisterInfo")
	public String doRegisterSubmit(
			Model model,
			@RequestParam(value="userLv", required=false, defaultValue="0") Integer lv,
			@RequestParam(value="account", required=false, defaultValue="") String account,
			@RequestParam(value="password", required=false, defaultValue="") String password,
			@RequestParam(value="firstName", required=false, defaultValue="") String firstName,
			@RequestParam(value="lastName", required=false, defaultValue="") String lastName,
			@RequestParam(value="nickname", required=false, defaultValue="") String nickname,
			@RequestParam(value="gender", required=false, defaultValue="N") String genderCode,
			@RequestParam(value="birth", required=false, defaultValue=) Date birth,
			@RequestParam(value="fervorValue", required=false, defaultValue="") String fervor,
			@RequestParam(value="email", required=false, defaultValue="") String email,
			@RequestParam(value="inputCheckCode", required=false, defaultValue="") String inputCheckCode,
			@RequestParam(value="phone", required=false, defaultValue="") String phone,
			@RequestParam(value="getEmail", required=false, defaultValue="Y") String willingCode,
			@RequestParam(value="locationCode", required=false, defaultValue="") String cityCode,
			@RequestParam(value="addr0", required=false, defaultValue="") String addr0,
			@RequestParam(value="addr1", required=false, defaultValue="") String addr1,
			@RequestParam(value="addr2", required=false, defaultValue="") String addr2
			) 
	{	
		/* 建立物件 */
		WebUserData userData = new WebUserData();
		/* 設定物件 */
		userData.setAccount(account);
		userData.setPassword(password);
		userData.setFirstName(firstName);
		userData.setLastName(lastName);
		userData.setNickname(nickname);
		userData.
		return null;
	} 
}
