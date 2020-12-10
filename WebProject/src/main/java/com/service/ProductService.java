package com.service;

import java.util.List;
import entities.com.Product;

public interface ProductService {

	public List<Product> findAll();

	public Product find(int id);

}
