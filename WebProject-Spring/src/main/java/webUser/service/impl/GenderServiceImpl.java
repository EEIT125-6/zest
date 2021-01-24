package webUser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webUser.model.Gender;
import webUser.repository.GenderRepository;
import webUser.service.GenderService;

@Service
@Transactional
public class GenderServiceImpl implements GenderService {
	/* 產生實作物件 */
	@Autowired
	GenderRepository genderDAO;
	
	@Override
	public List<Gender> getGenderList() {
		return genderDAO.getGenderList();
	}

	@Override
	public Gender getGender(String genderCode) {
		return genderDAO.getGender(genderCode);
	}

}
