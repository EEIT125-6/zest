package webUser.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import webUser.model.FoodFervor;

@Repository
public interface FervorService {
	/* 取回選項內容 */
	public List<FoodFervor> getFoodFervorList();
	/* 取向單筆選項 */
	public FoodFervor getFoodFervor(Integer fervorCode);
}
