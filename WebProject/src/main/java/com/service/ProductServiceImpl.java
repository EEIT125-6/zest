package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import com.DAO.ProductDAO;
import entities.com.Product;

@Service("ProductService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductServiceImpl implements ProductService {
	

	@Autowired
	private ProductDAO productDAO;

	@Transactional			
	@Override
	public List<Product> findAll() {
		return productDAO.findAll();
	}

	@Override
	public Product find(int id) {
		return productDAO.find(id);
	}
}