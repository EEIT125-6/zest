package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.ui.Model;

import model.CartDetailBean;
import model.OrderDetailBean;
import webUser.model.WebUserData;
import xun.model.ProductInfoBean;

public interface CartService {
	List<CartDetailBean> getCartList(); // 取得目前所有的購物車列表
	
	List<ProductInfoBean> getProductList(); //取得商城所有商品列表

	OrderDetailBean getOrderByUserId(WebUserData wus); // 取得單一使用者訂單
	
	Integer checkAccountExist(String inputAccount) throws SQLException; //確認使用者是否已註冊

    List<CartDetailBean> find(ProductInfoBean id); //搜尋購物車內特定物件id是否存在
    
    Map<String, String> findCurrentMemberData(Model model);//結帳時(checkout.jsp)自動帶入會員資料用 
    
    ProductInfoBean findProductInfoBeanById(Integer id); //查詢單筆商品用
    
    public void save(OrderDetailBean odb); //此方法永續化訂單內容
    
    public void save(CartDetailBean odb); //此方法永續化購物車內容
    
    public void delete(CartDetailBean k); //此方法商除購物車內特定資料
    
    public void deleteAll(Set<CartDetailBean> myCart);//此方法刪除購物車內資料 
}