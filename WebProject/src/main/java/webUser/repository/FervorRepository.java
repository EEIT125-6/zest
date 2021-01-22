package webUser.repository;

import java.util.List;

import webUser.model.FoodFervor;

public interface FervorRepository {
	/* 取回選項內容 */
	public List<FoodFervor> getFoodFervorList();
	/* 取向單筆選項 */
	public FoodFervor getFoodFervor(Integer fervorCode);
}
