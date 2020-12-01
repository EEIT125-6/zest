package webUser.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/* 永續類別 */
@Entity
/* 定義表格名 */
@Table(name="CityCode")
public class CityCode {
	/* 屬性宣告private，透過public的setter/getter進行存取/修改 */
	/* ID */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer cityCode;
	@Column(nullable = false , unique = true , columnDefinition="nvarchar(3)")
	private String cityName;
	
	@OneToMany(mappedBy = "cityCode", fetch = FetchType.EAGER)
	List<AreaCode> areaCodes = new ArrayList<>();
	
	/* 無參數建構子 */
	public CityCode() {
		super();
	}

	/* 帶參數建構子 */
	public CityCode(Integer cityCode, String cityName) {
		super();
		this.cityCode = cityCode;
		this.cityName = cityName;
	}

	/* setter & getter */
	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<AreaCode> getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(List<AreaCode> areaCodes) {
		this.areaCodes = areaCodes;
	}
}
