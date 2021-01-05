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
import org.springframework.web.multipart.MultipartFile;

import xun.model.ProductInfoBean;
import xun.model.StoreBean;
import xun.service.ProductService;
import xun.service.StoreService;

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
		return "InsertProduct";
	}
	
	@PostMapping("/InsertProduct")
	public String InsertProduct(
			Model model
//			,BindingResult result
			,@ModelAttribute ("productInfoBean") ProductInfoBean productInfoBean
			,@RequestParam(value="stid") Integer stid
			,@RequestParam("photo") MultipartFile file
			) {
//		檢查
		
//		新增
		StoreBean sb = ss.get(stid);
		productInfoBean.setStorebean(sb);
		System.out.println(productInfoBean.getStorebean());
//		處理圖片
		if(!file.isEmpty()) {
		String fakePath = "C:\\ProjectGithub\\zest\\WebProject-Spring\\src\\main\\webapp\\Images\\";
		String FileName = file.getOriginalFilename().replaceAll("\\s+", "");

		String FileFormat = FileName.split("\\.")[1];

		FileName = productInfoBean.getProduct_name()+"."+FileFormat;
//		File productphoto = new File(context.getRealPath("/")+FileName);
		File productphoto = new File(fakePath+FileName);
		
		try {
			file.transferTo(productphoto);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		productInfoBean.setProduct_picture("Images\\"+FileName);
		
//		執行新增
		ps.save(productInfoBean);
		}else {
			ps.save(productInfoBean);
		}
//		建置商家price

//		List<Integer> productsprice = new ArrayList<Integer>() ;
//		for (ProductInfoBean pi : ps.getStoreProduct(sb)) {
//			Integer ss =  pi.getProduct_price();
//			productsprice.add(ss);
//		}
//		
//		Collections.sort(productsprice);
//		if(productsprice.size()%2 !=0) {
//			Integer storeprice=productsprice.get((productsprice.size()+1)/2);
//			System.out.println(storeprice);
//			System.out.println("+++++++++++"+productsprice);
//		}else {
//			Integer storeprice=productsprice.get((productsprice.size()/2)+1);
//			System.out.println(storeprice);
//			System.out.println("-----------"+productsprice);
//		}
		CalculateStoreValue(sb.getId());
		
		
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
			Model model
			,@ModelAttribute ("productInfoBean") ProductInfoBean productInfoBean
			,@RequestParam(value="stid") Integer stid
			,@RequestParam("file") MultipartFile file
			) {
//		檢查
		
		
//		確認圖片有無更改

		if(file.isEmpty() && productInfoBean.getProduct_picture().isEmpty()) {
//		執行更新
//			if(productInfoBean.getProduct_picture().isEmpty())
			productInfoBean.setProduct_picture(null);
			ps.updateProduct(productInfoBean);
//			System.out.println(productInfoBean);
//			else {
//				
//			}
		}else if (file.isEmpty() && !productInfoBean.getProduct_picture().isEmpty()) {
			ps.updateProduct(productInfoBean);
			
			}else {
			String fakePath = "C:\\ProjectGithub\\zest\\WebProject-Spring\\src\\main\\webapp\\Images\\";
			String FileName = file.getOriginalFilename().replaceAll("\\s+", "");

			String FileFormat = FileName.split("\\.")[1];

			FileName = productInfoBean.getProduct_name()+"."+FileFormat;
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
			productInfoBean.setProduct_picture("Images\\"+FileName);
			ps.updateProduct(productInfoBean);
//			System.out.println(productInfoBean);
		}
//		設置商店價值
		CalculateStoreValue(stid);
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
		CalculateStoreValue(stid);
//		轉跳
		StoreBean sb = ss.get(stid);
		Integer NewStoreId = sb.getId();
		String NewStoreName = sb.getStname();
		model.addAttribute("id", NewStoreId);
		model.addAttribute("stname", NewStoreName);
		return "redirect:/StoreGetFullstore";
	}
	
//	設置商家價值
	public void CalculateStoreValue(Integer stid) {
		
		StoreBean sb = ss.get(stid);
		
		
		List<Integer> productsprice = new ArrayList<Integer>() ;
		for (ProductInfoBean pi : ps.getStoreProduct(sb)) {
			Integer ss =  pi.getProduct_price();
			productsprice.add(ss);
		}
		
		Collections.sort(productsprice);
		Integer storeprice=null;
		if(productsprice.size()%2 !=0) {
			storeprice=productsprice.get((productsprice.size()+1)/2);
			System.out.println(storeprice);
			System.out.println("+++++++++++"+productsprice);
		}else {
			storeprice=productsprice.get((productsprice.size()/2)+1);
			System.out.println(storeprice);
			System.out.println("-----------"+productsprice);
		}
		if (storeprice < 150) {
			storeprice = 1;
		}else if(storeprice < 300) {
			storeprice = 2;
		}else if(storeprice < 450) {
			storeprice = 3;
		}else if(storeprice < 600) {
			storeprice = 4;
		}else {
			storeprice = 5;
		}
		
		Integer Result  = ss.setStorePrice(storeprice, sb.getId());
		System.out.println("成功修改STORE_PRICE是1:"+Result);
	}

	
}
