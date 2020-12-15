package webUser.repository;

import java.util.List;

import webUser.model.CityInfo;

public interface LocationRepository {
	/* 取回選項內容 */
	public List<CityInfo> getLocationInfoList();
	/* 取向單筆選項 */
	public CityInfo getLocationInfo(Integer cityCode);
}
