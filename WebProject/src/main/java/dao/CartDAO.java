package dao;

import java.sql.SQLException;
import java.util.List;
import model.CartItemBean;
import xun.model.ProductInfoBean;

public interface CartDAO {
	List<CartItemBean> getCartList(); // 取得目前所有的購物車列表
	
	List<ProductInfoBean> getProductList(); //取得商城所有商品列表

	CartItemBean getCartByUser(String inputId); // 取得特定使用者購物車

	Integer checkAccountExist(String inputAccount) throws SQLException; //檢查使用者登入狀態

//	String addProduct(Integer addItem); //選定項目加入購物車
//	
//	String removeProduct(Integer removeItem); // 選定項目從購物車中移除

	List<ProductInfoBean> find(String id); //購物車內特定項目id搜尋
}