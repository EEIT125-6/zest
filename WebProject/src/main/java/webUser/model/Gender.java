package webUser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/* 永續類別 */
@Entity
/* 定義表格名 */
@Table(name="UserGender")
public class Gender implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/* 屬性宣告private，透過public的setter/getter進行存取/修改 */
	/* ID */
	@Id
	@Column(nullable = false , unique = true , columnDefinition="char(1)")
	private String genderCode;
	@Column(nullable = false , unique = true , columnDefinition="nvarchar(5)")
	private String genderText;
	
	/* 無參數建構子 */
	public Gender() {
		super();
	}

	/* 帶參數建構子 */
	public Gender(String genderCode, String genderText) {
		super();
		this.genderCode = genderCode;
		this.genderText = genderText;
	}

	/* setter & getter */
	public String getGenderCode() {
		return genderCode;
	}

	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}

	public String getGenderText() {
		return genderText;
	}

	public void setGenderText(String genderText) {
		this.genderText = genderText;
	}
}
