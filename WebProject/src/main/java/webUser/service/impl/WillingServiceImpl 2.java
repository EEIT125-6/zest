package webUser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webUser.model.UserWilling;
import webUser.repository.WillingRepository;
import webUser.service.WillingService;

@Service
@Transactional
public class WillingServiceImpl implements WillingService {
	/* 產生實作物件 */
	@Autowired
	WillingRepository willingDAO;
	
	@Override
	public List<UserWilling> getUserWillingList() {
		return willingDAO.getUserWillingList();
	}

	@Override
	public UserWilling getUserWilling(String willingCode) {
		return willingDAO.getUserWilling(willingCode);
	}

}
