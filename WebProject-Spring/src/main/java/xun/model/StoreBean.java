package xun.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

@Entity
@Table(name = "Store")
public class StoreBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Integer id;
	String stname;
	String sclass;
	String saddress;
	Integer price;
	String stitd;
	String stitddt;
	String tel;
	String bannerurl;
	String photourl;
	@Transient	
	MultipartFile stfile;
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public MultipartFile getStfile() {
		return stfile;
	}

	public void setStfile(MultipartFile stfile) {
		this.stfile = stfile;
	}

	public StoreBean() {
		super();
	}
	
	public StoreBean(Integer id) {
		super();
		this.id = id;
	}
	
	public StoreBean(Integer id, String stname, String bannerurl,
			String photourl) {
		super();
		this.id = id;
		this.stname = stname;
		this.bannerurl = bannerurl;
		this.photourl = photourl;
	}
	
	public StoreBean(Integer id,String stname, String sclass, String saddress, String stitd,String stitddt, String tel
			) {
		super();
		this.id = id;
		this.stname = stname;
		this.sclass = sclass;
		this.saddress = saddress;
		this.stitd = stitd;
		this.stitddt = stitddt;
		this.tel = tel;
	}
	
	public StoreBean(String stname, String sclass, String saddress, String stitd, String tel, String bannerurl,
			String photourl) {
		super();
		this.stname = stname;
		this.sclass = sclass;
		this.saddress = saddress;
		this.stitd = stitd;
		this.tel = tel;
		this.bannerurl = bannerurl;
		this.photourl = photourl;
	}
	
	public StoreBean(Integer id, String stname, String sclass, String saddress, String stitd, String tel
			) {
		super();
		this.id = id;
		this.stname = stname;
		this.sclass = sclass;
		this.saddress = saddress;
		this.stitd = stitd;
		this.tel = tel;	
	}
	
	public StoreBean(Integer id, String stname, String sclass, String saddress, String stitd, String tel, String bannerurl,
			String photourl) {
		super();
		this.id = id;
		this.stname = stname;
		this.sclass = sclass;
		this.saddress = saddress;
		this.stitd = stitd;
		this.tel = tel;
		this.bannerurl = bannerurl;
		this.photourl = photourl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStname() {
		return stname;
	}

	public void setStname(String stname) {
		this.stname = stname;
	}

	public String getSclass() {
		return sclass;
	}

	public void setSclass(String sclass) {
		this.sclass = sclass;
	}

	public String getSaddress() {
		return saddress;
	}

	public void setSaddress(String saddress) {
		this.saddress = saddress;
	}

	public String getStitd() {
		return stitd;
	}

	public void setStitd(String stitd) {
		this.stitd = stitd;
	}
	
	public String getStitddt() {
		return stitddt;
	}
	
	public void setStitddt(String stitddt) {
		this.stitddt = stitddt;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getBannerurl() {
		return bannerurl;
	}

	public void setBannerurl(String bannerurl) {
		this.bannerurl = bannerurl;
	}

	public String getPhotourl() {
		return photourl;
	}

	public void setPhotourl(String photourl) {
		this.photourl = photourl;
	}

	@Override
	public String toString() {
		return "StoreBean [id=" + id + ", stname=" + stname + ", sclass=" + sclass + ", saddress=" + saddress
				+ ", price=" + price + ", stitd=" + stitd + ", stitddt=" + stitddt + ", tel=" + tel + ", bannerurl="
				+ bannerurl + ", photourl=" + photourl + ", stfile=" + stfile + "]";
	}
	
	
	
	
}