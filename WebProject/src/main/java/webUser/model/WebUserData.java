package webUser.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/* 永續類別 */
@Entity
/* 定義表格名 */
@Table(name="WebUserInfo")
public class WebUserData implements Serializable {	
	private static final long serialVersionUID = 1L;
	
	/* 屬性宣告private，透過public的setter/getter進行存取/修改 */
	/* ID */
	@Id
	@Column(nullable = false , unique = true , columnDefinition="char(7)")
	private String userId;
	@Column(nullable = false , unique = true , columnDefinition="varchar(30)")
	private String account;
	@Column(nullable = true , unique = false , columnDefinition="varchar(60)")
	private String password;
	@Column(nullable = false , unique = false , columnDefinition="nvarchar(3)")
	private String firstName;
	@Column(nullable = false , unique = false , columnDefinition="nvarchar(3)")
	private String lastName;
	@Column(nullable = false , unique = true , columnDefinition="nvarchar(20)")
	private String nickname;
	@Column(nullable = false , unique = false , columnDefinition="date")
	private Date birth;
	@Column(nullable = false , unique = false , columnDefinition="nvarchar(50)")
	private String fervor;
	@Column(nullable = false , unique = true , columnDefinition="varchar(50)")
	private String email;
	@Column(nullable = false , unique = true , columnDefinition="varchar(11)")
	private String phone;
	@Column(nullable = false , unique = false , columnDefinition="date")
	private Date joinDate;
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
	@Column(nullable = true , unique = false , columnDefinition="varchar(255) ")
	private String iconUrl;
	@Column(nullable = true, unique = false, columnDefinition="date")
	private Date signIn;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lv")
	private UserIdentity accountLv;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "genderCode")
	private Gender gender;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "willingCode")
	private UserWilling getEmail;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cityCode")
	private CityInfo locationInfo;
	
	/* 無參數建構子 */
	public WebUserData() {
		super();
	}

	/* 帶參數建構子 */
	public WebUserData(String userId, String account, String password, String firstName, String lastName,
			String nickname, Date birth, String fervor, String email, String phone, Date joinDate, String addr0,
			String addr1, String addr2, BigDecimal zest, Integer version, String status, String iconUrl, Date signIn,
			UserIdentity accountLv, Gender gender, UserWilling getEmail,
			CityInfo locationInfo) {
		super();
		this.userId = userId;
		this.account = account;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nickname = nickname;
		this.birth = birth;
		this.fervor = fervor;
		this.email = email;
		this.phone = phone;
		this.joinDate = joinDate;
		this.addr0 = addr0;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.zest = zest;
		this.version = version;
		this.status = status;
		this.iconUrl = iconUrl;
		this.signIn = signIn;
		this.accountLv = accountLv;
		this.gender = gender;
		this.getEmail = getEmail;
		this.locationInfo = locationInfo;
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

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
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

	public String getIconUrl() {
		return iconUrl;
	}

	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	
	public Date getSignIn() {
		return signIn;
	}
	
	public void setSignIn(Date signIn) {
		this.signIn = signIn;
	}

	public UserIdentity getAccountLv() {
		return accountLv;
	}

	public void setAccountLv(UserIdentity accountLv) {
		this.accountLv = accountLv;
	}

	public UserWilling getGetEmail() {
		return getEmail;
	}

	public void setGetEmail(UserWilling getEmail) {
		this.getEmail = getEmail;
	}
	
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public CityInfo getLocationInfo() {
		return locationInfo;
	}

	public void setLocationInfo(CityInfo locationInfo) {
		this.locationInfo = locationInfo;
	}
}
