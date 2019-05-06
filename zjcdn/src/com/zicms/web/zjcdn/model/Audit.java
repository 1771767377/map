package com.zicms.web.zjcdn.model;

import com.zicms.common.base.BaseEntity;

@SuppressWarnings("unused")
public class Audit extends BaseEntity{

	private static final long serialVersionUID = 9041077683255092512L;

	private String statuscode;
	private String exportdate;
	private String domain;
	private String url;
	private String ip;
	private String ysrecord;
	
	
	public String getStatuscode() {
		return this.getString("statuscode");
	}
	public void setStatuscode(String statuscode) {
		this.set("statuscode", statuscode);
	}
	public String getExportdate() {
		return this.getString("exportdate");
	}
	public void setExportdate(String exportdate) {
		this.set("exportdate", exportdate);
	}
	public String getDomain() {
		return this.getString("domain");
	}
	public void setDomain(String domain) {
		this.set("domain", domain);
	}
	public String getUrl() {
		return this.getString("url");
	}
	public void setUrl(String url) {
		this.set("url", url);
	}
	public String getIp() {
		return this.getString("ip");
	}
	public void setIp(String ip) {
		this.set("ip", ip);
	}
	public String getYsrecord() {
		return this.getString("ysrecord");
	}
	public void setYsrecord(String ysrecord) {
		this.set("ysrecord", ysrecord);
	}
	@Override
	public String toString() {
		return "Audit [statuscode=" + getStatuscode() + ", exportdate=" + getExportdate() + ", domain=" + getDomain() + ", url=" + getUrl()
				+ ", ip=" + getIp() + ", ysrecord=" + getYsrecord() + "]";
	}
	
	
	
}
