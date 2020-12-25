package xun.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Board")
public class BoardBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer boardid;  //根據上面註釋
	
	private String name;
	private Integer star;
	private Date date;
	private String context;
	private String photo;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Store_Id")
	private StoreBean storebean;      //外來鍵
		
	
	public StoreBean getStorebean() {     //外來鍵方法get和set
		return storebean;				  //資料型態StoreBean
	}
	public void setStorebean(StoreBean storebean) {
		this.storebean = storebean;
	}
	                                     //set存放值，有this
	                  
	
	
	public BoardBean(){
		
	}
	
	//多載建構值
	public BoardBean(Integer boardid, String name, Integer star, Date date, String context, String photo,
			StoreBean storebean) {
		super();
		this.boardid = boardid;
		this.name = name;
		this.star = star;
		this.date = date;
		this.context = context;
		this.photo = photo;
		this.storebean = storebean;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStar() {
		return star;
	}
	public void setStar(Integer star) {
		this.star = star;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getBoardid() {
		return boardid;
	}
	public void setBoardid(Integer boardid) {
		this.boardid = boardid;
	}
	
//	@Override
//	public String toString() {
//		StringBuilder builder = new StringBuilder();
//		builder.append("BoardBean [name=");
//		builder.append(name);
//		builder.append(", star=");
//		builder.append(star);
//		builder.append(", date=");
//		builder.append(date);
//		builder.append(", context=");
//		builder.append(context);
//		builder.append(", photo=");
//		builder.append(photo);
//		builder.append(", storebean=");
//		builder.append(storebean);
//		builder.append("]");
//		return builder.toString();
//	}

}
