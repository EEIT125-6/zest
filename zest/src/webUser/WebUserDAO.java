package webUser;

public interface WebUserDAO {
	/* 檢查帳號是否存在
	 * -1->異常、0->不存在、1->存在 */
	public int checkAccountExist(String inputAccount);
}
