package webUser.repository;

import java.util.List;

import webUser.model.UserIdentity;

public interface IdentityRepository {
	/* 取回選項內容 */
	public List<UserIdentity> getIdentityList();
	/* 取向單筆選項 */
	public UserIdentity getIdentity(Integer lv);
}
