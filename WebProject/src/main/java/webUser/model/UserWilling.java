package webUser.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/* 永續類別 */
@Entity
/* 定義表格名 */
@Table(name="Willing")
public class UserWilling {
	/* 屬性宣告private，透過public的setter/getter進行存取/修改 */
	/* ID */
	@Id
	@Column(nullable = false , unique = true , columnDefinition="char(1)")
	private String willingCode;
	@Column(nullable = false , unique = true , columnDefinition="nvarchar(3)")
	private String willingText;
	
	/* 無參數建構子 */
	public UserWilling() {
		super();
	}

	/* 帶參數建構子 */
	public UserWilling(String willingCode, String willingText) {
		super();
		this.willingCode = willingCode;
		this.willingText = willingText;
	}

	/* setter & getter */
	public String getWillingCode() {
		return willingCode;
	}

	public void setWillingCode(String willingCode) {
		this.willingCode = willingCode;
	}

	public String getWillingText() {
		return willingText;
	}

	public void setWillingText(String willingText) {
		this.willingText = willingText;
	}
}
