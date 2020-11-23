package webUser;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/* 為符合javaBeans需求，宣告實作Serializable介面 */
public class WebUserBean implements Serializable {
	/* IDE能自動協助生成的項目，並不會使用到 */
	private static final long serialVersionUID = 1L;

	/* 屬性宣告private，透過public的setter/getter進行存取/修改 */
	private String user_id;
	private String account;
	private String password;
	private String first_name;
	private String last_name;
	private String nickname;
	private String gender;
	private LocalDate birth;
	private String fervor;
	private String email;
	private String phone;
	private String get_email;
	private String location_code;
	private LocalDate join_date;
	private Integer lv;
	private String addr0;
	private String addr1;
	private String addr2;
	private BigDecimal zest;
	private Integer version;

	/* 無參數建構子 */
	public WebUserBean() {

	}

	/* 帶參數建構子 */
	public WebUserBean(String pUser_id, String pAccount, String pPassword, String pFirst_name, String pLast_name, String pNickname, String pGender, LocalDate pBirthday, String pFervor, String pEmail, String pPhone, String pGet_email, String pLocation_code, LocalDate pJoin_date, Integer pLv, String pAddr0, String pAddr1 , String pAddr2, BigDecimal pZest, Integer pVersion) {
		this.user_id = pUser_id;
		this.account = pAccount;
		this.password = pPassword;
		this.first_name = pFirst_name;
		this.last_name = pLast_name;
		this.nickname = pNickname;
		this.gender = pGender;
		this.birth = pBirthday;
		this.fervor = pFervor;
		this.email = pEmail;
		this.phone = pPhone;
		this.get_email = pGet_email;
		this.location_code = pLocation_code;
		this.join_date = pJoin_date;
		this.lv =pLv;
		this.addr0 = pAddr0;
		this.addr1 = pAddr1;
		this.addr2 = pAddr2;
		this.zest = pZest;
		this.version = pVersion;
	}

	/* setter&getter */
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirth() {
		return birth;
	}

	public void setBirth(LocalDate birthday) {
		this.birth = birthday;
	}

	public String getFervor() {
		return fervor;
	}

	public void setFervor(String fervor) {
		this.fervor = fervor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getGet_email() {
		return get_email;
	}

	public void setGet_email(String get_email) {
		this.get_email = get_email;
	}

	public String getLocation_code() {
		return location_code;
	}

	public void setLocation_code(String location_code) {
		this.location_code = location_code;
	}

	public LocalDate getJoin_date() {
		return join_date;
	}

	public void setJoin_date(LocalDate join_date) {
		this.join_date = join_date;
	}

	public Integer getLv() {
		return lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	public String getAddr0() {
		return addr0;
	}

	public void setAddr0(String addr0) {
		this.addr0 = addr0;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public BigDecimal getZest() {
		return zest;
	}

	public void setZest(BigDecimal zest) {
		this.zest = zest;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}