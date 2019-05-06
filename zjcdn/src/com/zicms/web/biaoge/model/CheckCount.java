package com.zicms.web.biaoge.model;

import com.zicms.common.base.BaseEntity;
/*审核量统计排名*/
public class CheckCount extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String province;//省份
	private String zone;//省份代号
	private String account;//账号
	private Integer checkCount;//已审核量
	private  String dateStart;//统计开始时间
	private  String dateEnd;//统计结束时间
	private Integer  checkStandard;//审核人员标准值
	private  String countbyprovince;//各省总量
	
	public String getZone() {
		return this.getString("zone");
	}
	public void setZone(String zone) {
		this.set("zone", zone);
	}
	public String getCountByProvince() {
		return this.getString("countbyprovince");
	}
	public void setCountByProvince(String countbyprovince) {
		this.set("countbyprovince", countbyprovince);
	}
	public Integer getCheckStandard() {
		return this.getInteger("checkStandard");
	}

	public void setCheckStandard(Integer checkStandard) {
		this.set("checkStandard", checkStandard);
	}
	public String getAccount() {
		return this.getString("account");
	}
	public void setAccount(String account) {
		this.set("account", account);
	}
	public String getProvince() {
		return this.getString("province");
	}
	public void setProvince(String province) {
		this.set("province", province);
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
