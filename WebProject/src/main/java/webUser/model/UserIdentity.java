package webUser.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/* 永續類別 */
@Entity
/* 定義表格名 */
@Table(name="UserLevel")
public class UserIdentity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/* 屬性宣告private，透過public的setter/getter進行存取/修改 */
	/* ID */
	@Id
	@Column(nullable = false , unique = true , columnDefinition="int default 0")
	private Integer lv;
	@Column(nullable = false , unique = true , columnDefinition="nvarchar(5)")
	private String levelName;
	
	/* 無參數建構子 */
	public UserIdentity() {
		super();
	}

	/* 帶參數建構子 */
	public UserIdentity(Integer lv, String levelName) {
		super();
		this.lv = lv;
		this.levelName = levelName;
	}

	/* setter & getter */
	public Integer getLv() {
		return lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
}
