package webUser.model;

import java.io.Serializable;

public class ChartBeanFloat implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String labelName;
	private Float labelNum;
	private Integer labelCount;
	
	public ChartBeanFloat() {
		super();
	}
	public ChartBeanFloat(String labelName, Float labelNum, Integer labelCount) {
		super();
		this.labelName = labelName;
		this.labelNum = labelNum;
		this.labelCount = labelCount;
	}
	
	public String getLabelName() {
		return labelName;
	}
	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
	public Float getLabelNum() {
		return labelNum;
	}
	public void setLabelNum(Float labelNum) {
		this.labelNum = labelNum;
	}
	public Integer getLabelCount() {
		return labelCount;
	}
	public void setLabelCount(Integer labelCount) {
		this.labelCount = labelCount;
	}
}
