package webUser.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/* 永續類別 */
@Entity
/* 定義表格名 */
@Table(name="FoodFervor")
public class Fervor {
	/* 屬性宣告private，透過public的setter/getter進行存取/修改 */
	/* ID */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer fervorCode;
	@Column(nullable = false , unique = true , columnDefinition="nvarchar(10)")
	private String fervorItem;
	
	/* 無參數建構子 */
	public Fervor() {
		super();
	}

	/* 帶參數建構子 */
	public Fervor(Integer fervorCode, String fervorItem) {
		super();
		this.fervorCode = fervorCode;
		this.fervorItem = fervorItem;
	}

	/* setter & getter */
	public Integer getFervorCode() {
		return fervorCode;
	}

	public void setFervorCode(Integer fervorCode) {
		this.fervorCode = fervorCode;
	}

	public String getFervorItem() {
		return fervorItem;
	}

	public void setFervorItem(String fervorItem) {
		this.fervorItem = fervorItem;
	}
}
