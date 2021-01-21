package dashborad.controller;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import board.service.BoardService;
import model.BookingBean;
import model.CartItemBean;
import service.BookingService;
import service.CartService;
import webUser.model.CityInfo;
import webUser.model.Gender;
import webUser.model.WebUserData;
import webUser.service.GenderService;
import webUser.service.LocationService;
import webUser.service.WebUserService;
import xun.model.BoardBean;
import xun.model.StoreBean;
import xun.service.StoreService;

@Controller
@SessionAttributes({
	"cartYearList",
	"userYearList"
})
public class dashborad_Controller {
	/* By George017 2021/01/20 */
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
	
	/* Booking Service */
	@Autowired
	BookingService bks;
	
	/* Store Service */
	@Autowired
	StoreService sts;
	
	/* Board Service */
	@Autowired
	BoardService bds;
	
	/* Cart Service */
	@Autowired
	CartService cts;
	
	@GetMapping("/adminBack")
	public String adminBack(
			) {
		return "adminBack";
	}
	
	@GetMapping("/storeBack")
	public String storeBack(
			) {
		return "storeBack";
	}
	
	@GetMapping("/dashborad_order")
	public String dashborad_order(Model model) {
		List<String> cartYearList = getCartYearList();
		model.addAttribute("cartYearList", cartYearList);
		return "dashborad-orderAnalysis";
	}
	
	@GetMapping("/dashborad_book")
	public String dashborad_book(
	
			) {
		return "dashborad-bookAnalysis";
	}
	
	@GetMapping("/dashborad_comment")
	public String dashborad_comment(
	
			) {
		return "dashborad-commentAnalysis";
	}
	
	@GetMapping("/dashborad_user")
	public String dashborad_user(Model model) {
		List<String> cartYearList = getCartYearList();
		List<String> userYearList = getUserYearList();
		model.addAttribute("cartYearList", cartYearList);
		model.addAttribute("userYearList", userYearList);
		return "dashborad-userAnalysis";
	}
	
	/* 後臺用資料 By George017 2021/01/20 */
	/* 將使用者資料按縣市區域分組統計 */
	@PostMapping(value = "/controller/usrLocalCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserLocationCharData(Model model) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkIdentity(model);
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
						/* 區域代碼一致 */
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
		message = checkIdentity(model);
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
						/* 性別代碼一致 */
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
	@PostMapping(value = "/controller/usrJoinDateCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserJoinDateCharData (
			Model model, 
			@RequestParam(value = "year", defaultValue = "2020") String year) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkIdentity(model);
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
						/* 年份、月份一致 */
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
	
	/* 將使用者按有無使用過訂位系統來分組統計(used/not used) */
	@PostMapping(value = "/controller/usrUseBookingCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserBookingUsageCharData(Model model) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		String message = "";
		Integer totalUser = 0;
		
		/* 驗證身分 */
		message = checkIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			try {
				/* 取回所有註冊的使用者列表 */
				List<WebUserData> userList = wus.getAllWebUserData();
				totalUser = userList.size();
				/* 取出所有訂單資料 */
				List<BookingBean> bookingList = bks.allBooking();
				/* 遍歷 */
				for (WebUserData user: userList) {
					for (BookingBean bookingData: bookingList) {
						/* 使用者ID有出現在訂位資料代表有使用過 */
						if (user.getUserId().equals(bookingData.getUser_id().getUserId())) {
							if (map.get(user.getUserId()) == null) {
								map.put(user.getUserId(), 1);
							} else {
								int tmp = (int) map.get(user.getUserId());
								map.put(user.getUserId(), tmp + 1);
							}
						}
					}
				}
			} catch (SQLException sqlE) {
				message = sqlE.getMessage();
			}
		}
		resultMap.put("used", map.size());
		resultMap.put("not used", totalUser - map.size());
		message = (message.equals("")) ? "成功" : message;
		
		resultMap.put("message", message);
		return resultMap;
	}
	
	/* 將非取消的訂單資料按用途分組統計 */
	@PostMapping(value = "/controller/usrBookingGoalCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserBookingGoalCharData(Model model) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有訂單資料 */
			List<BookingBean> bookingList = bks.allBooking();
			/* 遍歷 */
			for (BookingBean bookingData: bookingList) {
				/* 非取消 */
				if (bookingData.getStatus() != 0) {
					/* 用途符合時 */
					if (map.get(bookingData.getPurpose()) == null) {
						map.put(bookingData.getPurpose(), 1);
					} else {
						int tmp = (int) map.get(bookingData.getPurpose());
						map.put(bookingData.getPurpose(), tmp + 1);
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 將非取消的訂單資料裡的人數按餐廳分類分組統計(*未排除已下線的商店) */
	@PostMapping(value = "/controller/usrBookingTypeCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserBookingTypeCharData(Model model) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有訂單資料 */
			List<BookingBean> bookingList = bks.allBooking();
			/* 取出所有店家資料 */
			List<StoreBean> storeList = sts.getAllStore();
			/* 遍歷 */
			for (BookingBean bookingData: bookingList) {
				for (StoreBean storeData: storeList) {
					/* 非取消 */
					if (bookingData.getStatus() != 0) {
						/* 餐廳出現在訂位資料中 */
						if (bookingData.getRestaurant().equals(storeData.getStname())) {
							/* 找出餐廳的類型 */
							String storeType = storeData.getSclass();
							if (map.get(storeType) == null) {
								map.put(storeType, bookingData.getNumber());
							} else {
								int tmp = (int) map.get(storeType);
								map.put(storeType, tmp + bookingData.getNumber());
							}
						}
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 查詢各分類的有效評分(將排除空值)，平均數為float型別 */
	@PostMapping(value = "/controller/usrBoardStarCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserBoardStarCharData(Model model) {
		Map<String, Object> resultMap = new HashMap<>();
		/* 總分 */
		Map<String, Object> map = new HashMap<>();
		/* 總評分數 */
		Map<String, Object> countMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有評論資料 */
			List<BoardBean> boardList = bds.getAllcomment();
			/* 遍歷 */
			for (BoardBean boardData: boardList) {
				/* 排除部分空值 */
				if (boardData.getContext() != null && boardData.getStar() != null && boardData.getName() != null) {
					/* 取店家類型 */
					String storeType = boardData.getStorebean().getSclass();
					/* 準備累加分數 */
					if (map.get(storeType) == null && countMap.get(storeType) == null) {
						map.put(storeType, boardData.getStar());
						countMap.put(storeType, 1);
						float avg = boardData.getStar() / 1.0f;
						resultMap.put(storeType, avg);
					} else if (map.get(storeType) != null && countMap.get(storeType) != null) {
						int tmp = (int) map.get(storeType);
						map.put(storeType, tmp + boardData.getStar());
						int countTmp = (int) countMap.get(storeType);
						countMap.put(storeType, countTmp + 1);
						float avg = (int) map.get(storeType) /(float) countMap.get(storeType);
						resultMap.put(storeType, avg);
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		resultMap.put("message", message);
		return resultMap;
	}
	
	/* 查詢各分類的有效評分(將排除空值)，統計其留言數 */
	@PostMapping(value = "/controller/usrBoardCountsCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserBoardCountsCharData(Model model) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有評論資料 */
			List<BoardBean> boardList = bds.getAllcomment();
			/* 遍歷 */
			for (BoardBean boardData: boardList) {
				/* 排除部分空值 */
				if (boardData.getContext() != null && boardData.getStar() != null && boardData.getName() != null) {
					/* 取店家類型 */
					String storeType = boardData.getStorebean().getSclass();
					/* 準備累加留言數 */
					if (map.get(storeType) == null) {
						map.put(storeType, 1);
					} else {
						int tmp = (int) map.get(storeType);
						map.put(storeType, tmp + 1);
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 查詢個人花費金額，按年齡分組(15歲一個區間，從0開始逐個區間+1，第一個為0~15)，僅計算已付款的 */
	@PostMapping(value = "/controller/usrAvgCostByAge", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserAvgCostByAge(Model model) {
		Map<String, Object> map = new HashMap<>();
		/* 年紀Map */
		Map<String, Object> ageMap = new HashMap<>();
		/* 使用者Map */
		Map<String, Object> userMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有購物車訂單資料 */
			List<CartItemBean> cartList = cts.getCartList();
			/* 遍歷 */
			for (CartItemBean cartData: cartList) {
				/* 僅計算已付款的 */
				if (cartData.getPurchase_Payment()) {
					/* 取出使用者 */
					WebUserData user = cartData.getProduct_User();
					/* 取出購買產品的總價 */
					Integer price = cartData.getProduct_Info().getProduct_price() * Integer.parseInt(cartData.getProduct_Quantity());
					/* 算出年齡 */
					Integer age = caculateAge(user.getBirth());
					/* 進行區間分類 */
					Integer ageRange = age / 15;
					/* 放入ageMap */
					if (ageMap.get(ageRange.toString()) == null) {
						ageMap.put(ageRange.toString(), price);
					} else {
						long tmpL = (long) ageMap.get(ageRange.toString());
						ageMap.put(ageRange.toString(), tmpL + price);
					}
					/* 放入userMap */
					if (userMap.get(ageRange.toString()) == null) {
						userMap.put(ageRange.toString(), 1);
					} else {
						int tmp = (int) userMap.get(ageRange.toString());
						userMap.put(ageRange.toString(), tmp + 1);
					}
					/* 計算平均 */
					String realRange = String.valueOf(ageRange*15 + 0) + "~" + String.valueOf(ageRange*15 + 14);
					if (map.get(realRange) == null) {
						map.put(realRange, price);
					} else {
						long avgCost = (long) map.get(realRange) / (int) userMap.get(ageRange.toString());
						map.put(realRange, avgCost);
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 查詢平均每筆花費金額，按年+月分組(預設為2020)，僅計算已付款的 */
	@PostMapping(value = "/controller/usrAvgCostByMonth", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserAvgCostByMonth(Model model,
			@RequestParam(value = "year", defaultValue = "2020") String year) {
		Map<String, Object> map = new HashMap<>();
		/* 統計Map */
		Map<String, Object> countMap = new HashMap<>();
		/* 統計Map */
		Map<String, Object> totalMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有購物車訂單資料 */
			List<CartItemBean> cartList = cts.getCartList();
			/* 遍歷 */
			for (CartItemBean cartData: cartList) {
				/* 該年度且僅計算已付款的 */
				if (cartData.getPurchase_Payment() && String.valueOf(cartData.getPurchase_Time().toLocalDate().getDayOfYear()).equals(year)) {
					/* 取出購買月份 */
					Integer pMonth = cartData.getPurchase_Time().toLocalDate().getDayOfMonth();
					/* 取出購買產品的總價 */
					Integer price = cartData.getProduct_Info().getProduct_price() * Integer.parseInt(cartData.getProduct_Quantity());
					/* 開始統計 */
					if (countMap.get(pMonth.toString()) == null) {
						countMap.put(pMonth.toString(), 1);
					} else {
						int tmp = (int) countMap.get(pMonth.toString());
						countMap.put(pMonth.toString(), tmp + 1);
					}
					
					if (totalMap.get(pMonth.toString()) == null) {
						totalMap.put(pMonth.toString(), price);
					} else {
						long tmpL = (long) totalMap.get(pMonth.toString());
						totalMap.put(pMonth.toString(), tmpL + price);
					}
					
					if (map.get(pMonth.toString()) == null) {
						map.put(pMonth.toString(), price);
					} else {
						int countTmp = (int) countMap.get(pMonth.toString());
						long totalTmp = (long) totalMap.get(pMonth.toString());
						map.put(pMonth.toString(), totalTmp / countTmp);
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 查詢已付款的購物車清單中，按餐廳分類分組顯示比例，僅計算已付款的 */
	@PostMapping(value = "/controller/usrBuyCountsByType", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getUserBuyCountsByType(Model model) {
		Map<String, Object> map = new HashMap<>();
		/* 各類筆數 */
		Map<String, Object> countMap = new HashMap<>();
		/* 總筆數 */
		Map<String, Object> totalMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有購物車訂單資料 */
			List<CartItemBean> cartList = cts.getCartList();
			/* 遍歷 */
			for (CartItemBean cartData: cartList) {
				/* 僅計算已付款的 */
				if (cartData.getPurchase_Payment()) {
					/* 取店家類型 */
					String storeType = cartData.getProduct_Info().getStorebean().getSclass();
					if (totalMap.get("total") == null) {
						totalMap.put("total", 1);
					} else {
						long tmp = (long) totalMap.get("total");
						totalMap.put("total", tmp + 1);
					}
					if (countMap.get(storeType) == null) {
						countMap.put(storeType, 1);
						long totalTmp = (long) totalMap.get("total");
						map.put(storeType, (float)(1 / totalTmp));
					} else {
						long tmpL = (long) countMap.get(storeType);
						countMap.put(storeType, tmpL + 1);
						long totalTmp = (long) totalMap.get("total");
						map.put(storeType, (float)((long) countMap.get(storeType) / totalTmp));
					}
				}
			}
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 取出購物車年份(下拉選單用) */
	private List<String> getCartYearList() {
		/* 取出所有購物車訂單資料 */
		List<String> cartYearList = new ArrayList<>();
		List<CartItemBean> cartList = cts.getCartList();
		if (cartList != null) {
			for (CartItemBean cartData: cartList) {
				cartYearList.add(String.valueOf(cartData.getPurchase_Time().toLocalDate().getDayOfYear()));
			}
			return cartYearList;
		}
		return null;
	}
	
	/* 取出使用者加入年份(下拉選單用) */
	private List<String> getUserYearList() {
		/* 取出所有使用者資料 */
		List<String> userYearList = new ArrayList<>(); 
		List<WebUserData> userList = new ArrayList<>();
		
		try {
			userList = wus.getAllWebUserData();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (userList != null) {
			for (WebUserData userData: userList) {
				userYearList.add(String.valueOf(userData.getBirth().toLocalDate().getDayOfYear()));
			}
			return userYearList;
		}
		return null;
	}
	
	/* 驗證身分 */
	private String checkIdentity(Model model) {
		String message = "";
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		if (userData == null) {
			message = "請登入後再執行此動作！";
		} else if (!userData.getStatus().equals("active")) {
			message = "本帳號已被停用，無法使用此功能！";
		} else if (userData.getAccountLv().getLv() != -1) {
			message = "本帳號無法使用此功能！";
		}
		return message;
	}
	
	/* 計算年齡 */
	private Integer caculateAge(Date birth) {
		Integer age = -1;
		LocalDate userBirth = birth.toLocalDate();
		LocalDate today = LocalDate.now();
		Period p = Period.between(userBirth, today);
		age = p.getYears();
		return age;
	}
}
