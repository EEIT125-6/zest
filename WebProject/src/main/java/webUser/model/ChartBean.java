package webUser.model;

import java.io.Serializable;

public class ChartBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String labelName;
	private Integer labelNum;
	
	public ChartBean() {
		super();
	}
	public ChartBean(String labelName, Integer labelNum) {
		super();
		this.labelName = labelName;
		this.labelNum = labelNum;
	}
	
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public Integer getLabelNum() {
		return labelNum;
	}
	public void setLabelNum(Integer labelNum) {
		this.labelNum = labelNum;
	}
}
