package dashborad.controller;

import java.sql.SQLException;
import java.time.LocalDate;
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
import util.GeneralInputCheckService;
import webUser.model.CityInfo;
import webUser.model.Gender;
import webUser.model.WebUserData;
import webUser.service.FervorService;
import webUser.service.GenderService;
import webUser.service.LocationService;
import webUser.service.WebUserService;
import xun.model.BoardBean;
import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.StoreService;

@Controller
@SessionAttributes({
	"cartYearList",
	"userYearList",
	"fervorList",
	"genderList",
	"userFullData",
	"sclassList"
})
public class dashborad_Controller {
	/* By Mimicker0903 */
	/* Store Service */
	@Autowired
	StoreService ss;
	
	/* By George017 2021/01/20 */
	/* ServletContext */
	@Autowired
	ServletContext context;
	
	/* WebUserData Service */
	@Autowired
	WebUserService wus;
	
	/* FoodFervor Service */
	@Autowired
	FervorService fvs;

	/* Gender Service */
	@Autowired
	GenderService gds;

	/* Location Service */
	@Autowired
	LocationService lcs;
	
	/* Booking Service */
	@Autowired
	BookingService bks;
	
	/* Board Service */
	@Autowired
	BoardService bds;
	
	/* Cart Service */
	@Autowired
	CartService cts;
	
	//去管理員後台目錄
	@GetMapping("/adminBack")
	public String adminBack(
			) {
		return "adminBack";
	}
	
	//去商家後台目錄
	@GetMapping("/storeBack")
	public String storeBack(
			) {
		return "storeBack";
	}
	
	//以下管理員統計資料//
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
	//以上管理員統計資料//
	
	//以下管理員管理資料//
	@GetMapping("/adminStore")
	public String adminStore(Model model) {
		List<String> sclassList = new ArrayList<>();
		List<StoreBean> storeList = ss.getAllStore();
		for (StoreBean storeData: storeList) {
			if (!sclassList.contains(storeData.getSclass())) {
				sclassList.add(storeData.getSclass());
			}
		}
		model.addAttribute("sclassList", sclassList);
		return "adminAdminSystem-Store";
	}
	
	@GetMapping("/adminProduct")
	public String adminProduct(
			Model model
			) {
		return "adminAdminSystem-Product";
	}
	//以上管理員管理資料//
	
	//以下商家統計資料//
	@GetMapping("/storeSt")
	public String storeSt(
			Model model
			) {
		return "storeStatistics-storeContent";
	}
	//以上商家統計資料//
	
	//以下商家管理資料//
	@GetMapping("/storeAd")
	public String storeAd(
			Model model
			) {
		WebUserData userFullData  = (WebUserData) model.getAttribute("userFullData");
		List<StoreBean> listAllStore= ss.getMemberAllStore(userFullData);
		model.addAttribute("listAllStore", listAllStore);
		System.out.println("+++");
		System.out.println(listAllStore);
		System.out.println("+++");
		return "storeAdminSystem-storeContent";
	}
	
	@GetMapping("/storeAdClick")
	public String storeAdClick(
			Model model
			,@RequestParam Integer stId
			) {
		model.addAttribute("id", stId);
		return "storeAdminSystem-storeClick";
	}
	
	@GetMapping("/storeAdProduct")
	public String storeAdClickProduct(
			Model model
			,@RequestParam(value = "id") Integer stId
			) {
		List<ProductInfoBean> list3 = ss.getProductInfoBeans(stId);
		model.addAttribute("Products", list3);
		model.addAttribute("id", stId);
		return "storeAdminSystem-storeClick-product";
	}
	//以上商家管理資料//
	
	/* 後臺用資料 By George017 2021/01/20 */
	/* 將使用者資料按縣市區域分組統計 */
	@PostMapping(value = "/controller/localCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getLocationCharData(Model model) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkAdminIdentity(model);
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
	@PostMapping(value = "/controller/genderCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getGenderCharData (Model model) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkAdminIdentity(model);
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
	@PostMapping(value = "/controller/joinDateCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getJoinDateCharData (
			Model model, 
			@RequestParam(value = "year", defaultValue = "2020") String year) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkAdminIdentity(model);
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
	@PostMapping(value = "/controller/bookingUsageCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBookingUsageCharData(Model model) {
		/* 參數宣告 */
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> resultMap = new HashMap<>();
		String message = "";
		Integer totalUser = 0;
		
		/* 驗證身分 */
		message = checkAdminIdentity(model);
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
	@PostMapping(value = "/controller/bookingGoalCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBookingGoalCharData(Model model) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		
		/* 驗證身分 */
		message = checkAdminIdentity(model);
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
	@PostMapping(value = "/controller/bookingTypeCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBookingTypeCharData(Model model) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("")) {
			/* 取出所有訂單資料 */
			List<BookingBean> bookingList = bks.allBooking();
			/* 取出所有店家資料 */
			List<StoreBean> storeList = ss.getAllStore();
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
	@PostMapping(value = "/controller/boardStarCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBoardStarCharData(Model model) {
		Map<String, Object> resultMap = new HashMap<>();
		/* 總分 */
		Map<String, Object> map = new HashMap<>();
		/* 總評分數 */
		Map<String, Object> countMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
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
	@PostMapping(value = "/controller/boardCountsCharData", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBoardCountsCharData(Model model) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
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
	@PostMapping(value = "/controller/avgCostByAge", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getAvgCostByAge(Model model) {
		Map<String, Object> map = new HashMap<>();
		/* 年紀Map */
		Map<String, Object> ageMap = new HashMap<>();
		/* 使用者Map */
		Map<String, Object> userMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
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
					Integer age = GeneralInputCheckService.doCaculateAge(user.getBirth());
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
	@PostMapping(value = "/controller/avgCostByMonth", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getAvgCostByMonth(Model model,
			@RequestParam(value = "year", defaultValue = "2020") String year) {
		Map<String, Object> map = new HashMap<>();
		/* 統計Map */
		Map<String, Object> countMap = new HashMap<>();
		/* 統計Map */
		Map<String, Object> totalMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
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
	@PostMapping(value = "/controller/buyCountsByType", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getBuyCountsByType(Model model) {
		Map<String, Object> map = new HashMap<>();
		/* 各類筆數 */
		Map<String, Object> countMap = new HashMap<>();
		/* 總筆數 */
		Map<String, Object> totalMap = new HashMap<>();
		String message = "";
		/* 驗證身分 */
		message = checkAdminIdentity(model);
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
	
	/* 取得留言列表 */
	@PostMapping(value = "/controller/getCommentList", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getCommnetList(
			Model model
			) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 留言列表 */
		List<BoardBean> boardList = new ArrayList<>();
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("") || checkBossIdentity(model).equals("")) {
			/* 確認使用者身分 */
			WebUserData nowUser = (WebUserData) model.getAttribute("userFullData");
			/* 管理員 */
			if (nowUser.getAccountLv().getLv() == -1) {
				boardList = bds.getAllcomment();
				map.put("boardList", boardList);
			/* 店家 */
			} else if (nowUser.getAccountLv().getLv() == 1) {
				List<BoardBean> personalBoardList = new ArrayList<>();
				boardList = bds.getAllcomment();
				/* 遍歷 */
				for (BoardBean boardData: boardList) {
					/* 僅放入自己所擁有的商店 */
					if (boardData.getStorebean().getWebUserData().getUserId().equals(nowUser.getUserId())) {
						personalBoardList.add(boardData);
					}
				}
				map.put("boardList", personalBoardList);
				message = (personalBoardList.size() == 0) ? "您尚未在本平台擁有任何一個商店！" : message;
			} 
		}
		message = (message.equals("")) ? "成功" : message;
		
		map.put("message", message);
		return map;
	}
	
	/* 取得店家列表 */
	@PostMapping(value = "/controller/getStoreList", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> getStoreList(
			Model model,
			@RequestParam(value = "stname", required = false, defaultValue = "") String stname,
			@RequestParam(value = "owner", required = false, defaultValue = "") String owner,
			@RequestParam(value = "status", required = false, defaultValue = "") String status,
			@RequestParam(value = "type", required = false, defaultValue = "") String type,
			@RequestParam(value = "avPage", defaultValue = "5") Integer avPage,
			@RequestParam(value = "startPage", required = false, defaultValue = "1") Integer startPage) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		/* 分頁用 */
		Long totalDataNums = 0L;
		Integer totalDataPages = 1;
		/* 回傳的商家資料 */
		List<StoreBean> storeList = new ArrayList<>();
		List<StoreBean> finStoreList = new ArrayList<>();
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證通過 */
		if (message.equals("") || checkBossIdentity(model).equals("")) {
			/* 確認使用者身分 */
			WebUserData nowUser = (WebUserData) model.getAttribute("userFullData");
			/* 管理員 */
			if (nowUser.getAccountLv().getLv() == -1) {
				storeList = ss.getAllStore();
			/* 店家 */
			} else if (nowUser.getAccountLv().getLv() == 1) {
				storeList = ss.getMemberAllStore(nowUser);
			}
			/* 有資料才做以下操作 */
			if (storeList != null) {
				switch(status) {
					case "1":
					case "0":
						/* 遍歷 */
						for (int index = 0; index < storeList.size(); index++) {
							if (!storeList.get(index).getStatus().equals(status)) {
								storeList.remove(index);
							}
						}
						break;
					default:
						break;
				}
				switch(type) {
					case "中式":
					case "快餐":
					case "燒肉":
					case "西式":
					case "下午茶":
					case "日式":
						/* 遍歷 */
						for (int index = 0; index < storeList.size(); index++) {
							if (!storeList.get(index).getSclass().equals(type)) {
								storeList.remove(index);
							}
						}
						break;
					default:
						break;
				}
				if (!stname.equals("")) {
					/* 遍歷 */
					for (int index = 0; index < storeList.size(); index++) {
						if (storeList.get(index).getStname().indexOf(stname) == -1) {
							storeList.remove(index);
						}
					}
				}
				if (!owner.equals("")) {
					/* 遍歷 */
					for (int index = 0; index < storeList.size(); index++) {
						if (storeList.get(index).getWebUserData().getAccount().indexOf(owner) == -1) {
							storeList.remove(index);
						}
					}
				}
				/* 計算出總共幾筆、共幾頁 */
				totalDataNums = (long) storeList.size();
				totalDataPages = (int) Math.ceil(totalDataNums / (avPage*1.0));
			}
			/* 開始算分頁，無資料或資料少於等於每頁筆數就不處理 */
			if (storeList != null) {
				if (storeList.size() > avPage) {
					/* 定義起始筆數、結束筆數 */
					Integer startIndex = (startPage - 1)*avPage;
					Integer endIndex = (storeList.size() < startIndex + avPage) ? storeList.size() : startIndex + avPage;
					/* 遍歷 */
					for (int index = startIndex; index < endIndex; index++) {
						finStoreList.add(storeList.get(index));
					}
				}
			}
			/* 決定回傳的資料 */
			if (storeList != null && finStoreList != null) {
				map.put("storeList", finStoreList);
			} else {
				map.put("storeList", storeList);
			}
			
		} 
		Integer resultCode = -1;
		resultCode = (message.equals("") && storeList.size() > 0) ? 1 : resultCode;
		resultCode = (message.equals("") && storeList.size() <= 0) ? 0 : resultCode;
		message = (message.equals("") && storeList.size() > 0) ? "查詢到 " + totalDataNums + " 筆店家資料，共 " + totalDataPages + " 頁，此為第 " + startPage + " 頁" : message;
		message = (message.equals("") && storeList.size() <= 0) ? "沒有任何符合條件的資料！" : message;
		
		map.put("resultCode", resultCode.toString());
		map.put("resultMessage", message);
		map.put("totalDataNums", totalDataNums);
		map.put("totalDataPages", totalDataPages);
		return map;
	}
	
	/* 管理員對店家的權限操作 */
	@PostMapping(value = "/controller/adminStoreOperate", produces = "application/json; charset=UTF-8")
	public @ResponseBody Map<String, Object> adminStoreOperate (
			Model model,
			@RequestParam("storeId") Integer storeId,
			@RequestParam("status") String status,
			@RequestParam("mode") String mode) {
		Map<String, Object> map = new HashMap<>();
		String message = "";
		Integer resultCode = -1;
		/* 驗證身分 */
		message = checkAdminIdentity(model);
		/* 驗證成功 */
		if (message.equals("")) {
			/* 按選擇的模式分流 */
			switch(mode) {
				/* 刪除 */
				case "delete":
					/* 由storeId反查整個物件 */
					StoreBean deletedStore = ss.get(storeId);
					/* 如有存在才繼續 */
					if (deletedStore == null) {
						resultCode = -1;
						message = "無效的店家代碼！";
					} else {
						/* 執行刪除 */
						resultCode = ss.deleteStore(deletedStore);
						message = (resultCode == 1) ? "順利完成刪除操作！" : "發生錯誤！無法完成刪除操作！";
					}
					break;
				/* 上架 */
				case "active":
				/* 下架 */
				case "quit":
					/* 由storeId反查整個物件 */
					StoreBean changedStore = ss.get(storeId);
					/* 如有存在才繼續 */
					if (changedStore == null) {
						resultCode = -1;
						message = "無效的店家代碼！";
					} else {
						/* 判定status參數是否合理 */
						switch (status) {
							case "0":
								/* 已下架的不可再下架 */
								if (mode.equals("quit")) {
									resultCode = -1;
									message = "已下架的商店不可再下架！";
								} else if (mode.equals("active")) {
									resultCode = ss.storeChange(storeId, "1");
									message = (resultCode == 1) ? "順利完成上架操作！" : "發生錯誤！無法完成上架操作！";
								}
								break;
							case "1":
								/* 已上架的不可再上架 */
								if (mode.equals("active")) {
									resultCode = -1;
									message = "已下架的商店不可再下架！";
								} else if (mode.equals("quit")) {
									resultCode = ss.storeChange(storeId, "0");
									message = (resultCode == 1) ? "順利完成下架操作！" : "發生錯誤！無法完成下架操作！";
								}
								break;
							case "3":
								/* 已移除的不可再下架 */
								if (mode.equals("quit")) {
									resultCode = -1;
									message = "已移除的商店不可再下架！";
								} else if (mode.equals("active")) {
									resultCode = ss.storeChange(storeId, "1");
									message = (resultCode == 1) ? "順利完成上架操作！" : "發生錯誤！無法完成上架操作！";
								}
								break;
							default:
								resultCode = -1;
								message = "無效的店家狀態！";
								break;
						}
					}
					break;
				/* 其他 */
				default:
					resultCode = -1;
					message = "無效的操作模式，請重新進行操作或詢問技術人員！";
					break;
			}
		}
		map.put("resultCode", resultCode.toString());
		map.put("resultMessage", message);
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
	
	/* 驗證管理員身分 */
	private String checkAdminIdentity(Model model) {
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
	
	/* 驗證店家身分 */
	private String checkBossIdentity(Model model) {
		String message = "";
		WebUserData userData = (WebUserData) model.getAttribute("userFullData");
		if (userData == null) {
			message = "請登入後再執行此動作！";
		} else if (!userData.getStatus().equals("active")) {
			message = "本帳號已被停用，無法使用此功能！";
		} else if (userData.getAccountLv().getLv() != 1) {
			message = "本帳號無法使用此功能！";
		}
		return message;
	}
}
