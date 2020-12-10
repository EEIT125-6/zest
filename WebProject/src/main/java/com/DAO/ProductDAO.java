package com.DAO;

import java.util.List;
import entities.com.Product;

public interface ProductDAO {

	public List<Product> findAll();

	public Product find(int id);

}
