package webUser.service;

import java.util.List;

import org.springframework.stereotype.Repository;

import webUser.model.UserIdentity;

@Repository
public interface IdentityService {
	/* 取回選項內容 */
	public List<UserIdentity> getIdentityList();
	/* 取向單筆選項 */
	public UserIdentity getIdentity(Integer lv);
}
