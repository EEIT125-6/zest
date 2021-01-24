package service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import dao.CartDAO;
import model.CartItemBean;
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
	@Transactional
	public List<CartItemBean> getCartList() {
		return DAO.getCartList();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<CartItemBean> getCartByUser(String inputId) {
		System.out.println("ServiceLayerIdCheck="+inputId);
		return (List<CartItemBean>) DAO.getCartByUser(inputId);
	}

	@Override
	@Transactional
	public Integer checkAccountExist(String inputAccount) throws SQLException {
		return DAO.checkAccountExist(inputAccount);
	}

	@Override
	@Transactional
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
	public List <ProductInfoBean> find(String id) {
		System.out.println("Service則安低能");
		return DAO.find(id);
	}

	@Override
	public Map<String, String> findCurrentMemberData(Model model) {
		WebUserData wusd = (WebUserData)model.getAttribute("userFullData");
		Map<String, String> remap = new HashMap<String, String>();
		remap.put("member", wusd.getFirstName()+wusd.getLastName());
		remap.put("email", wusd.getEmail());
		remap.put("addr", wusd.getAddr0());
		return remap;
	}

//	@Override
//	public Map<String, String> setProductAmount(StringTokenizer st) {
//		Map<String,String> remap = new HashMap<String,String>();
//		System.out.println("Result inside service="+st);
//		while(st.hasMoreTokens()) {
//			System.out.println(st.nextToken());
//		}
//		
//		return null;
//	}

//	@Override
//	public Map<String, String> CurrentCartItemDetail(Model model) {
//		Map<String,String> remap = new HashMap<String,String>();
//		remap.put(, arg1)
//		return null;
}