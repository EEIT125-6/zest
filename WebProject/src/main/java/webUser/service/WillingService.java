package webUser.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import webUser.model.UserWilling;

@Repository
public interface WillingService {
	/* 取回選項內容 */
	public List<UserWilling> getUserWillingList();
	/* 取向單筆選項 */
	public UserWilling getUserWilling(String willingCode);
}
