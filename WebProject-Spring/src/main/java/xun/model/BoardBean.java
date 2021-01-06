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
	private Integer status;
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
	
	public BoardBean(Integer boardid, String pname,Integer pstars,Date pdate,String pcontext,String pphoto, StoreBean storebean) {
		this.boardid = boardid;
		this.name = pname;
		this.star = pstars;
		this.date = pdate;
		this.context = pcontext;
		this.photo=pphoto;
		this.storebean=storebean;
	}
	
	public BoardBean(Integer boardid, String name, Integer star, Date date, String context, String photo,
			Integer status, StoreBean storebean) {
		super();
		this.boardid = boardid;
		this.name = name;
		this.star = star;
		this.date = date;
		this.context = context;
		this.photo = photo;
		this.status = status;
		this.storebean = storebean;
	}
	
	public Integer getBoardid() {
		return boardid;
	}
	public void setBoardid(Integer boardid) {
		this.boardid = boardid;
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
	public void setStar(Integer stars) {
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
