package webUser.model;

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
@Table(name="AreaCode")
public class AreaCode {
	/* 屬性宣告private，透過public的setter/getter進行存取/修改 */
	/* ID */
	@Id
	@Column(nullable = false , unique = true , columnDefinition="int default 0")
	private Integer areaCode;
	@Column(nullable = false , unique = false , columnDefinition="nvarchar(4)")
	private String areaName;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="cityCode" ,nullable = false ,unique = false)
	private CityCode cityCode;
	
	/* 無參數建構子 */
	public AreaCode() {
		super();
	}
	
	/* 帶參數建構子 */
	public AreaCode(Integer areaCode, String areaName, CityCode cityCode) {
		super();
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.cityCode = cityCode;
	}

	/* setter & getter */
	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public CityCode getCityCode() {
		return cityCode;
	}

	public void setCityCode(CityCode cityCode) {
		this.cityCode = cityCode;
	}
}
