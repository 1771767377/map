package com.zicms.web.biaoge.model;


import com.zicms.common.base.BaseEntity;

/*统计审核完成率*/
@SuppressWarnings("unused")
public class Checked extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String province;//省份
	private String zone;//省份代号
	private  String hour;//各时间段（小时）
	private Integer uncheckCount;//未审核量
	private Integer checkCount;//已审核量
	private  String dateStart;//统计开始时间
	private  String dateEnd;//统计结束时间
	private  String day;//各天
	public String getDay() {
		return this.getString("day");
	}
	public void setDay(String day) {
		this.set("day", day);
	}
	public String getHour() {
		return this.getString("hour");
	}
	public void setHour(String hour) {
		this.set("hour", hour);
	}
	
	public String getZone() {
		return this.getString("zone");
	}
	public void setZone(String zone) {
		this.set("zone", zone);
	}
	public String getProvince() {
		return this.getString("province");
	}
	public void setProvince(String province) {
		this.set("province", province);
	}
	public Integer getUncheckCount() {
		return this.getInteger("uncheckCount");
	}
	public void setUncheckCount(Integer uncheckCount) {
		this.set("uncheckCount", uncheckCount);
	}
	public Integer getCheckCount() {
		return this.getInteger("checkCount");
	}
	public void setCheckCount(Integer checkCount) {
		this.set("checkCount", checkCount);
	}
	public String getDateStart() {
		return this.getString("dateStart");
	}
	public void setDateStart(String dateStart) {
		this.set("dateStart", dateStart);
	}
	public String getDateEnd() {
		return this.getString("dateEnd");
	}
	public void setDateEnd(String dateEnd) {
		this.set("dateEnd", dateEnd);
	}
	
	
	
}
