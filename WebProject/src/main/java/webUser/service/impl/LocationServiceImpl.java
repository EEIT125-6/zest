package webUser.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import webUser.model.CityInfo;
import webUser.repository.LocationRepository;
import webUser.service.LocationService;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {
	/* 產生實作物件 */
	@Autowired
	LocationRepository locationDAO;
	
	@Override
	public List<CityInfo> getLocationInfoList() {
		return locationDAO.getLocationInfoList();
	}

	@Override
	public CityInfo getLocationInfo(Integer cityCode) {
		return locationDAO.getLocationInfo(cityCode);
	}
}
