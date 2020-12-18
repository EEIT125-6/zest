package webUser.model;

import java.io.Serializable;

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
public class FoodFervor implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/* 屬性宣告private，透過public的setter/getter進行存取/修改 */
	/* ID */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer fervorCode;
	@Column(nullable = false , unique = true , columnDefinition="nvarchar(10)")
	private String fervorItem;
	
	/* 無參數建構子 */
	public FoodFervor() {
		super();
	}

	/* 帶參數建構子 */
	public FoodFervor(Integer fervorCode, String fervorItem) {
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
