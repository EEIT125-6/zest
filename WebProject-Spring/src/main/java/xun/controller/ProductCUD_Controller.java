package xun.controller;

import java.io.File;
import java.io.IOException;

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
//		處裡圖片
		String fakePath = "C:\\ProjectGithub\\zest\\WebProject-Spring\\src\\main\\webapp\\Images\\";
		String FileName = file.getOriginalFilename();

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
//		轉跳
		Integer NewStoreId = sb.getId();
		String NewStoreName = sb.getStname();
		model.addAttribute("id", NewStoreId);
		model.addAttribute("stname", NewStoreName);
		return "redirect:/StoreGetFullstore";
	}
	
	
}
