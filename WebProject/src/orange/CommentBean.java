package orange;

import java.util.Date;

public class CommentBean {

	private String name;
	private Integer stars;
	private Date date;
	private String context;
	private String photo;
	
	public CommentBean(){
		
	}
	
	public CommentBean(String pname,Integer pstars,Date pdate,String pcontext,String pphoto) {
		this.name = pname;
		this.stars = pstars;
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
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
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
}
