package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import model.CartDetailBean;
import model.OrderDetailBean;
import webUser.model.WebUserData;
import xun.model.ProductInfoBean;

public interface CartDAO {
List<CartDetailBean> getCartList(); // 取得目前所有的購物車列表
	
	List<ProductInfoBean> getProductList(); //取得商城所有商品列表

	OrderDetailBean getOrderByUserId(WebUserData wus); // 取得特定使用者購物車

	Integer checkAccountExist(String inputAccount) throws SQLException; //檢查使用者登入狀態

	List<CartDetailBean> find(ProductInfoBean id); //購物車內特定項目id搜尋

	ProductInfoBean findProductInfoBeanById(Integer id); //查詢單筆商品用
     
	 public void save(OrderDetailBean odb); //此類別永續化訂單內容
	    
	 public void save(CartDetailBean odb); //此類別永續化購物車內容
	 
	 public void deleteAll(Set<CartDetailBean> cdb); //此類別刪除購物車內所有物件
	 
	 public void delete(CartDetailBean k); //此類別刪除購物車內特定物件
}