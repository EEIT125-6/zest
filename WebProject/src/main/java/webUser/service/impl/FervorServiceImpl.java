package webUser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webUser.model.FoodFervor;
import webUser.repository.FervorRepository;
import webUser.service.FervorService;

@Service
@Transactional
public class FervorServiceImpl implements FervorService {
	/* 產生實作物件 */
	@Autowired
	FervorRepository fervorDAO;
	
	@Override
	public List<FoodFervor> getFoodFervorList() {
		return fervorDAO.getFoodFervorList();
	}

	@Override
	public FoodFervor getFoodFervor(Integer fervorCode) {
		return fervorDAO.getFoodFervor(fervorCode);
	}
}
