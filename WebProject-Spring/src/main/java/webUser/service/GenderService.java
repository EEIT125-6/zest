package webUser.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import webUser.model.Gender;

@Repository
public interface GenderService {
	/* 取回選項內容 */
	public List<Gender> getGenderList();
	/* 取向單筆選項 */
	public Gender getGender(String genderCode);
}
