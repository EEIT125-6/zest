package xun.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.ProductService;
import xun.service.StoreService;
import xun.util.GlobalService;
import xun.validators.ProductVaildators;

@Controller
public class ProductCUD_Controller {

	@Autowired
	ServletContext context;
	
	@Autowired
	ProductService ps;
	
	@Autowired
	StoreService ss;
	
	
	@GetMapping("/InsertProduct")
	public String InsertProductPage(
			Model model
			,@RequestParam(value = "id") Integer stid
			) {
		ProductInfoBean productInfoBean = new ProductInfoBean();
		StoreBean sb = ss.get(stid);
		productInfoBean.setProduct_shop(sb.getStname());
		model.addAttribute("stid", stid);
		model.addAttribute("productInfoBean", productInfoBean);
//		給值
//		for (int i = 1; i < 62; i++) {
//			if (ss.get(i) == null) {
//				continue;
//			}
//		CalculateStoreRealPrice(i);	
//		}
		
		return "InsertProduct";
	}
	
	@PostMapping("/InsertProduct")
	public String InsertProduct(
			@ModelAttribute ("productInfoBean") ProductInfoBean productInfoBean
			,Model model
			,BindingResult result
			,@RequestParam(value="stid") Integer stid
			,@RequestParam(value = "forAOP" , required = false) String forAOP
			,@RequestParam("photo") MultipartFile file
			) {
//		檢查
		ProductVaildators validator = new ProductVaildators();
		validator.validate(productInfoBean, result);
		if (result.hasErrors()) {
			model.addAttribute("stid", stid);
			System.out.println("商品中有不合邏輯的錯誤 並退回新增頁面");
			return "InsertProduct";
		}
//		新增
		StoreBean sb = ss.get(stid);
		productInfoBean.setStorebean(sb);
		System.out.println(productInfoBean.getStorebean());
//		處理圖片
		if(!file.isEmpty()) {
//			String fakePath = "C:\\JavaMVCWorkspace\\WebProject\\src\\main\\webapp\\WEB-INF\\views\\images\\productInfo\\images\\";
			String fakePath = GlobalService.getUploadProductPhotoPath();
			String FileName = file.getOriginalFilename().replaceAll("\\s+", "");

			String FileFormat = FileName.split("\\.")[1];
			
			String newProductId = String.valueOf(ps.getLastProductId()+1); 
//			FileName = productInfoBean.getProduct_id()+"!_!"+productInfoBean.getProduct_name()+"."+FileFormat;
			FileName = newProductId+"!_PD!"+productInfoBean.getProduct_name()+"."+FileFormat;
//		File productphoto = new File(context.getRealPath("/")+FileName);
			File productphoto = new File(fakePath+FileName);
		
			try {
				file.transferTo(productphoto);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			productInfoBean.setProduct_picture("..\\productInfo\\images\\"+FileName);
		
//		執行新增
			productInfoBean.setProduct_status("1");
			ps.save(productInfoBean);
		}else {
			productInfoBean.setProduct_status("1");
			ps.save(productInfoBean);
		}
//		建置商家price

		CalculateStoreRealPrice(sb.getId());
		
		
//		轉跳
		Integer NewStoreId = sb.getId();
		String NewStoreName = sb.getStname();
		model.addAttribute("id", NewStoreId);
		model.addAttribute("stname", NewStoreName);
		return "redirect:/StoreGetFullstore";
	}
	
	@PostMapping("/updateProductpage")
	public String updateProductPage(
			Model model
			,@RequestParam(value = "id") Integer stid
			,@RequestParam(value = "forAOP" , required = false) String forAOP
			,@RequestParam(value = "productid") Integer product_id
			) {
		ProductInfoBean productInfoBean = ps.get(product_id);
//		StoreBean sb = ss.get(stid);
		model.addAttribute("stid", stid);
		model.addAttribute("productInfoBean", productInfoBean);
		return "updateProduct";
	}
	
	@PostMapping("/exupdateProduct")
	public String updateProduct(
			@ModelAttribute ("productInfoBean") ProductInfoBean productInfoBean
			,Model model
			,BindingResult result
			,@RequestParam(value="stid") Integer stid
			,@RequestParam("file") MultipartFile file
			) {
//		檢查
		ProductVaildators validator = new ProductVaildators();
		validator.validate(productInfoBean, result);
		if (result.hasErrors()) {
			model.addAttribute("stid", stid);
			System.out.println("商品中有不合邏輯的錯誤 並退回更新頁面");
			return "updateProduct";
		}
//		確認圖片有無更改

		if(file.isEmpty() && productInfoBean.getProduct_picture().isEmpty()) {
//		執行更新
//			if(productInfoBean.getProduct_picture().isEmpty())
			productInfoBean.setProduct_picture(null);
			ps.updateProduct(productInfoBean);
			System.out.println("空ㄉ");
//			System.out.println(productInfoBean);
//			else {
//				
//			}
		}else if (file.isEmpty() && !productInfoBean.getProduct_picture().isEmpty()) {
			ps.updateProduct(productInfoBean);
			System.out.println("沒動");
			}else {
			System.out.println("我想換照片");
//			String fakePath = "C:\\JavaMVCWorkspace\\WebProject\\src\\main\\webapp\\WEB-INF\\views\\images\\productInfo\\images\\";
			String fakePath = GlobalService.getUploadProductPhotoPath();
//			C:\JavaMVCWorkspace\WebProject\src\main\webapp\WEB-INF\views\images\productInfo\images
			String FileName = file.getOriginalFilename().replaceAll("\\s+", "");

			String FileFormat = FileName.split("\\.")[1];

			FileName = productInfoBean.getProduct_id()+"!_PD!"+productInfoBean.getProduct_name()+"."+FileFormat;
//			File productphoto = new File(context.getRealPath("/")+FileName);
			File productphoto = new File(fakePath+FileName);
			
			try {
				file.transferTo(productphoto);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
//		執行更新
			System.out.println("+++++++++++++++++++"+FileName);
			productInfoBean.setProduct_picture("..\\productInfo\\images\\"+FileName);
			
			ps.updateProduct(productInfoBean);
//			System.out.println(productInfoBean);
		}
//		設置商店價值
		CalculateStoreRealPrice(stid);
//		轉跳
		StoreBean sb = ss.get(stid);
		Integer NewStoreId = sb.getId();
		String NewStoreName = sb.getStname();
		model.addAttribute("id", NewStoreId);
		model.addAttribute("stname", NewStoreName);
		return "redirect:/StoreGetFullstore";
	}
	
	@PostMapping("/deleteProductpage")
	public String deletepage(
			Model model
			,@RequestParam(value = "id") Integer stid
			,@RequestParam(value = "forAOP" , required = false) String forAOP
			,@RequestParam(value = "productid") Integer product_id
			) {
		ProductInfoBean productInfoBean = ps.get(product_id);
		model.addAttribute("stid", stid);
		model.addAttribute("productInfoBean", productInfoBean);
		return "deleteProduct";
	}
	
	@PostMapping("/exdeleteProduct")
	public String delete(
			Model model
			,@ModelAttribute ("productInfoBean") ProductInfoBean productInfoBean
			,@RequestParam(value="stid") Integer stid
			) {
		ps.deleteProduct(productInfoBean);

//		設置商店價值
		CalculateStoreRealPrice(stid);
//		轉跳
		StoreBean sb = ss.get(stid);
		Integer NewStoreId = sb.getId();
		String NewStoreName = sb.getStname();
		model.addAttribute("id", NewStoreId);
		model.addAttribute("stname", NewStoreName);
		return "redirect:/StoreGetFullstore";
	}
	
	@PostMapping(value="/productOffShelfAjax",produces = "application/json; charset=utf-8")
	public @ResponseBody void productOffShelf(
			@RequestParam Integer productId
			) {
		System.out.println("尼瑪有傳到下架");
		ps.productOffShelf(productId);
	}
	@PostMapping(value="/productReOnShelfAjax",produces = "application/json; charset=utf-8")
	public @ResponseBody void productReOnShelf(
			@RequestParam Integer productId
			) {
		System.out.println("尼瑪有傳到上架");
		ps.productReOnShelf(productId);
	}
//	設置商家價格區間
	public void CalculateStoreRealPrice(Integer stid) {
		
		StoreBean sb = ss.get(stid);
		
//		Integer sumPrice = 0;
		List<Integer> productsprice = new ArrayList<Integer>() ;
		for (ProductInfoBean pi : ps.getStoreProduct(sb)) {
			Integer ss =  pi.getProduct_price();
//			sumPrice  = sumPrice + ss;
			productsprice.add(ss);
		}
//		Integer avgPrice = sumPrice/productsprice.size();
		
		Collections.sort(productsprice);
		Integer realprice=null;
		if(productsprice.size()%2 !=0) {
			realprice=productsprice.get((productsprice.size()+1)/2-1);
			System.out.println(realprice);
			System.out.println("+++++++++++"+productsprice);
		}else if(productsprice.size()==0){
			realprice=0;
		}else {
			realprice=productsprice.get((productsprice.size()/2));
			
			System.out.println(realprice);
			System.out.println("-----------"+productsprice);
		}
		ss.setStoreRealPrice(realprice, sb.getId());
//		if (storeprice < 150) {
//			storeprice = 1;
//		}else if(storeprice < 300) {
//			storeprice = 2;
//		}else if(storeprice < 450) {
//			storeprice = 3;
//		}else if(storeprice < 600) {
//			storeprice = 4;
//		}else {
//			storeprice = 5;
//		}
//		Integer Result  = ss.setStorePrice(storeprice, sb.getId());
//		System.out.println("成功修改STORE_PRICE是1:"+Result);
	}
}
