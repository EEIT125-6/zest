package service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.ui.Model;

import model.CartItemBean;
import xun.model.ProductInfoBean;
import webUser.model.WebUserData;

public interface CartService {
	
	

//	String addProduct(Integer addItem); // 選定項目加入至購物車中
//
//	String removeProduct(Integer removeItem); // 選定項目從購物車中移除

	List<CartItemBean> getCartList(); // 取得目前所有的購物車列表
	
	List<ProductInfoBean> getProductList(); //取得商城所有商品列表

	CartItemBean getCartByUser(String inputId); // 取得單一使用者購物車
	
	public Integer checkAccountExist(String inputAccount) throws SQLException;

	public List<CartItemBean> find(int id); //搜尋購物車內特定物件id是否存在
}
