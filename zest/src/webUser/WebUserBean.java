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
	private String first_name;
	private String last_name;
	private String nickname;
	private Character gender;
	private LocalDate birthday;
	private String fervor;
	private Boolean get_email;
	private String location_code;
	private LocalDate join_date;
	private Integer lv;
	private String addr0;
	private String addr1;
	private String addr2;
	private BigDecimal zest;

	/* 無參數建構子 */
	WebUserBean() {

	}

	/* 帶參數建構子 */
	WebUserBean(String user_id, String first_name, String last_name, String nickname, Character gender, LocalDate birthday, String fervor, Boolean get_email, String location_code, LocalDate join_date, Integer lv, String addr0, String addr1 , String addr2, BigDecimal zest) {
		this.user_id = user_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.nickname = nickname;
		this.gender = gender;
		this.birthday = birthday;
		this.fervor = fervor;
		this.get_email = get_email;
		this.location_code = location_code;
		this.join_date = join_date;
		this.lv = lv;
		this.addr0 = addr0;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.zest = zest;
	}

	/* setter&getter */
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
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

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getFervor() {
		return fervor;
	}

	public void setFervor(String fervor) {
		this.fervor = fervor;
	}

	public Boolean getGet_email() {
		return get_email;
	}

	public void setGet_email(Boolean get_email) {
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
}
