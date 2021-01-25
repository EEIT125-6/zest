package service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import dao.CartDAO;
import model.CartDetailBean;
import model.OrderDetailBean;
import webUser.model.WebUserData;
import xun.model.ProductInfoBean;

@Repository
public class CartServiceImpl implements CartService {

	CartDAO DAO;

	@Autowired
	public void setDao(CartDAO DAO) {
		this.DAO = DAO;
	}

	@Override
	@Transactional // 列出目前所有未結帳購物車資料
	public List<CartDetailBean> getCartList() {
		return DAO.getCartList();
	}

	@Override
	@Transactional
	// 取得使用者個人購物車資料
	public OrderDetailBean getOrderByUserId(WebUserData wus) {
		System.out.println("ServiceLayerIdCheck=" + wus);
		return DAO.getOrderByUserId(wus);
	}

	@Override
	@Transactional // 檢查使用者是否存在資料庫
	public Integer checkAccountExist(String inputAccount) throws SQLException {
		return DAO.checkAccountExist(inputAccount);
	}

	@Override
	@Transactional // 取得所有產品列表
	public List<ProductInfoBean> getProductList() {
		return DAO.getProductList();
	}

	@Override
	@Transactional 
	public ProductInfoBean findProductInfoBeanById(Integer id) {
		return DAO.findProductInfoBeanById(id);
	}

	@Override
	@Transactional //此類別永續化訂單物件
	public void save(OrderDetailBean odb) { 
		DAO.save(odb);
	}

	@Override
	@Transactional //此類別永續化購物車物件
	public void save(CartDetailBean cdb) {
		DAO.save(cdb);
	}

	@Override // 找尋購物車內特定商品
	@Transactional
	public List<CartDetailBean> find(ProductInfoBean id) {

		return DAO.find(id);
	}

	@Override // 帶入目前使用者資料用
	@Transactional
	public Map<String, String> findCurrentMemberData(Model model) {
		WebUserData wusd = (WebUserData) model.getAttribute("userFullData");
		Map<String, String> remap = new HashMap<String, String>();
		remap.put("member", wusd.getFirstName() + wusd.getLastName());
		remap.put("email", wusd.getEmail());
		remap.put("addr", wusd.getAddr0());
		return remap;
	}

	@Override
	@Transactional
	public void deleteAll(Set<CartDetailBean> cdb) {
		 DAO.deleteAll(cdb);
		
	}
	
	@Override
	@Transactional
	public void delete(CartDetailBean k) {
		DAO.delete(k);
	}
}