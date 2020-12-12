package dao;

import java.util.List;

import model.ProductInfoBean;

public interface DAODef {
	public List<ProductInfoBean> findAll();

	public ProductInfoBean find(int id);
}
