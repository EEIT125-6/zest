package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.service.ProductService;
//import java.util.logging.Logger;
//import java.util.logging.FileHandler;
//import java.util.logging.Level;
//import java.util.logging.LogManager;

@Controller
@RequestMapping(value = "/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	@RequestMapping(value = "/productlist", method = RequestMethod.GET)
	public String index(ModelMap mm) {
		mm.put("list product", productService.findAll());
		
		return "index";
	}
}
