package com.zicms.web.overview.model;

import com.zicms.common.base.BaseEntity;

@SuppressWarnings("unused")
public class GlobalData extends BaseEntity{

	private static final long serialVersionUID = -7093644788423076698L;
	
	private String processDate;   //处理日期
	private Integer dayProcessImage;  //此处理图片量
	private Integer dayProcessText;  //日处理文本量
	private Integer dayProcessCount;   //日处理量
	private Integer dayTotalImage; //日发现图片量
	private Integer dayTotalText;  //日发现文本量
	private Integer dayTotalCount;    //日发现量
	private Integer illegalImage;    //图片不良量
	private Integer illegalText;     //文本不良量
	private Integer illegalCount;   //日审核不良量
	
	public Integer getDayProcessImage() {
		return this.getInteger("dayProcessImage");
	}
	public void setDayProcessImage(Integer dayProcessImage) {
		this.set("dayProcessImage", dayProcessImage);
	}
	public Integer getDayProcessText() {
		return this.getInteger("dayProcessText");
	}
	public void setDayProcessText(Integer dayProcessText) {
		this.set("dayProcessText", dayProcessText);
	}
	public Integer getDayProcessCount() {
		return this.getInteger("dayProcessCount");
	}
	public void setDayProcessCount(Integer dayProcessCount) {
		this.set("dayProcessCount", dayProcessCount);
	}
	public Integer getDayTotalImage() {
		return this.getInteger("dayTotalImage");
	}
	public void setDayTotalImage(Integer dayTotalImage) {
		this.set("dayTotalImage", dayTotalImage);
	}
	public Integer getDayTotalText() {
		return this.getInteger("dayTotalText");
	}
	public void setDayTotalText(Integer dayTotalText) {
		this.set("dayTotalText", dayTotalText);
	}
	public Integer getDayTotalCount() {
		return this.getInteger("dayTotalCount");
	}
	public void setDayTotalCount(Integer dayTotalCount) {
		this.set("dayTotalCount", dayTotalCount);
	}
	public String getProcessDate() {
		return this.getString("processDate");
	}
	public void setProcessDate(String processDate) {
		this.set("processDate", processDate);
	}
	public Integer getIllegalCount() {
		return this.getInteger("illegalCount");
	}
	public void setIllegalCount(Integer illegalCount) {
		this.set("illegalCount", illegalCount);
	}
	public Integer getIllegalImage() {
		return this.getInteger("illegalImage");
	}
	public void setIllegalImage(Integer illegalImage) {
		this.set("illegalImage", illegalImage);
	}
	public Integer getIllegalText() {
		return this.getInteger("illegalText");
	}
	public void setIllegalText(Integer illegalText) {
		this.set("illegalText", illegalText);
	}
	
	
}
