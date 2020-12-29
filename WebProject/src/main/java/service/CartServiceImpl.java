package service;

import java.sql.SQLException;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import dao.CartDAO;
import model.CartItemBean;
import xun.model.ProductInfoBean;


@Repository
public class CartServiceImpl implements CartService {

	CartDAO DAO;

	@Autowired
	public void setDao(CartDAO DAO) {
		this.DAO = DAO;
	}


	@Override
	@Transactional
	public List<CartItemBean> getCartList() {
		return DAO.getCartList();
	}

	@Override
	@Transactional
	public CartItemBean getCartByUser(String inputId) {
		System.out.println("ServiceLayerIdCheck="+inputId);
		return DAO.getCartByUser(inputId);
	}

	@Override
	@Transactional
	public Integer checkAccountExist(String inputAccount) throws SQLException {
		return DAO.checkAccountExist(inputAccount);
	}

	@Override
	public List<ProductInfoBean> getProductList() {
		return DAO.getProductList();
	}

//	@Override
//	public String addProduct(Integer addItem) {
//		// TODO Auto-generated method stub
//		return DAO.addProduct(addItem);
//	}
//
//	@Override
//	public String removeProduct(Integer deleteItem) {
//		// TODO Auto-generated method stub
//		return DAO.removeProduct(deleteItem);
//	}


	@Override
	public List <CartItemBean> find(int id) {
		return DAO.find(id);
	}

}
