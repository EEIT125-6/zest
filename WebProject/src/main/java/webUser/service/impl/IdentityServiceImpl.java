package webUser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webUser.model.UserIdentity;
import webUser.repository.IdentityRepository;
import webUser.service.IdentityService;

@Service
@Transactional
public class IdentityServiceImpl implements IdentityService {
	/* 產生實作物件 */
	@Autowired
	IdentityRepository identityDAO;
	
	@Override
	public List<UserIdentity> getIdentityList() {
		return identityDAO.getIdentityList();
	}

	@Override
	public UserIdentity getIdentity(Integer lv) {
		return identityDAO.getIdentity(lv);
	}
}
