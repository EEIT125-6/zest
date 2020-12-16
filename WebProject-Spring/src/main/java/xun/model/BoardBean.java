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
	private Integer boardid;
	private String name;
	private Integer star;
	private Date date;
	private String context;
	private String photo;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "Store_Id")
	private StoreBean storebean;
	
	public StoreBean getStorebean() {
		return storebean;
	}
	public void setStorebean(StoreBean storebean) {
		this.storebean = storebean;
	}
	
	public BoardBean(){
		
	}
	
	
	public BoardBean(String pname,Integer pstars,Date pdate,String pcontext,String pphoto) {
		
		this.name = pname;
		this.star = pstars;
		this.date = pdate;
		this.context = pcontext;
		this.photo=pphoto;
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStars() {
		return star;
	}
	public void setStars(Integer stars) {
		this.star = stars;
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
