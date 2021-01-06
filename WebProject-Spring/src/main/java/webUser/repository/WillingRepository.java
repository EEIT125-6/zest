package webUser.repository;

import java.util.List;

import webUser.model.UserWilling;

public interface WillingRepository {
	/* 取回選項內容 */
	public List<UserWilling> getUserWillingList();
	/* 取向單筆選項 */
	public UserWilling getUserWilling(String willingCode);
}
