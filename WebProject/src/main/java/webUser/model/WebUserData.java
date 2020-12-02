package webUser.model;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/* 永續類別 */
@Entity
/* 定義表格名 */
@Table(name="WebUserData")
public class WebUserData {
	
	/* 屬性宣告private，透過public的setter/getter進行存取/修改 */
	/* ID */
	@Id
	@Column(nullable = false , unique = true , columnDefinition="char(7)")
	private String userId;
	@Column(nullable = false , unique = true , columnDefinition="varchar(20)")
	private String account;
	@Column(nullable = false , unique = false , columnDefinition="varchar(20)")
	private String password;
	@Column(nullable = false , unique = false , columnDefinition="nvarchar(3)")
	private String firstName;
	@Column(nullable = false , unique = false , columnDefinition="nvarchar(3)")
	private String lastName;
	@Column(nullable = false , unique = false , columnDefinition="nvarchar(20)")
	private String nickname;
	@Column(nullable = false , unique = false , columnDefinition="char(1)")
	private String gender;
	@Column(nullable = false , unique = false , columnDefinition="date")
	private Date birth;
	@Column(nullable = false , unique = false , columnDefinition="nvarchar(50)")
	private String fervor;
	@Column(nullable = false , unique = true , columnDefinition="varchar(50)")
	private String email;
	@Column(nullable = false , unique = true , columnDefinition="varchar(11)")
	private String phone;
	@Column(nullable = false , unique = false , columnDefinition="char(1)")
	private String getEmail;
	@Column(nullable = false , unique = false , columnDefinition="char(3)")
	private String locationCode;
	@Column(nullable = false , unique = false , columnDefinition="date")
	private Date joinDate;
	@Column(nullable = true , unique = false , columnDefinition="int default 0")
	private Integer lv;
	@Column(nullable = false , unique = false , columnDefinition="nvarchar(65)")
	private String addr0;
	@Column(nullable = true , unique = false , columnDefinition="nvarchar(65) default '' ")
	private String addr1;
	@Column(nullable = true , unique = false , columnDefinition="nvarchar(65) default '' ")
	private String addr2;
	@Column(nullable = true , unique = false , columnDefinition="decimal(19,2) default 0.00")
	private BigDecimal zest;
	@Column(nullable = true , unique = false , columnDefinition="int default 0")
	private Integer version;
	@Column(nullable = true , unique = false , columnDefinition="varchar(8) default 'inactive' ")
	private String status;
	
	/* 無參數建構子 */
	public WebUserData() {
		super();
	}
	
	/* 帶參數建構子 */
	public WebUserData(String userId, String account, String password, String firstName, String lastName, String nickname,
			String gender, Date birth, String fervor, String email, String phone, String getEmail,
			String locationCode, Date joinDate, Integer lv, 
			String addr0, String addr1, String addr2, BigDecimal zest,
			Integer version, String status) {
		super();
		this.userId = userId;
		this.account = account;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickname = nickname;
		this.gender = gender;
		this.birth = birth;
		this.fervor = fervor;
		this.email = email;
		this.phone = phone;
		this.getEmail = getEmail;
		this.locationCode = locationCode;
		this.joinDate = joinDate;
		this.lv = lv;
		this.addr0 = addr0;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.zest = zest;
		this.version = version;
		this.status = status;
	}

	/* setter & getter */
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
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

	public String getGetEmail() {
		return getEmail;
	}

	public void setGetEmail(String getEmail) {
		this.getEmail = getEmail;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
