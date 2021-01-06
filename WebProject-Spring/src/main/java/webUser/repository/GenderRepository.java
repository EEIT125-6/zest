package webUser.repository;

import java.util.List;

import webUser.model.Gender;

public interface GenderRepository {
	/* 取回選項內容 */
	public List<Gender> getGenderList();
	/* 取向單筆選項 */
	public Gender getGender(String genderCode);
}
