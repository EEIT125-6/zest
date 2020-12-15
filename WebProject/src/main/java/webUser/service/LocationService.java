package webUser.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import webUser.model.CityInfo;

@Repository
public interface LocationService {
	/* 取回選項內容 */
	public List<CityInfo> getLocationInfoList();
	/* 取向單筆選項 */
	public CityInfo getLocationInfo(Integer cityCode);
}
