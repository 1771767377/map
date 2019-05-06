package com.zicms.web.overview.model;

import com.zicms.common.base.BaseEntity;

@SuppressWarnings("unused")
public class TopFive extends BaseEntity{

	private static final long serialVersionUID = 2804994807909974648L;

	private Integer domainId;   //domain自增长id
	private String resourceDomain;  //domain
	private String domainFirm;		//domain对应的厂商
	private Integer domainCount;  //domain的数量
	private Integer ipId;  //ip自增长id
	private String resourceIp;    //ip
	private String ipOperators;		//ip对应的供应商
	private Integer ipCount;	//ip的数量
	
	public Integer getDomainId() {
		return this.getInteger("domainId");
	}
	public void setDomainId(Integer domainId) {
		this.set("domainId", domainId);
	}
	public String getResourceDomain() {
		return this.getString("resourceDomain");
	}
	public void setResourceDomain(String resourceDomain) {
		this.set("resourceDomain", resourceDomain);
	}
	public String getDomainFirm() {
		return this.getString("domainFirm");
	}
	public void setDomainFirm(String domainFirm) {
		this.set("domainFirm", domainFirm);
	}
	public Integer getDomainCount() {
		return this.getInteger("domainCount");
	}
	public void setDomainCount(Integer domainCount) {
		this.set("domainCount", domainCount);
	}
	public Integer getIpId() {
		return this.getInteger("ipId");
	}
	public void setIpId(Integer ipId) {
		this.set("ipId", ipId);
	}
	public String getResourceIp() {
		return this.getString("resourceIp");
	}
	public void setResourceIp(String resourceIp) {
		this.set("resourceIp", resourceIp);
	}
	public String getIpOperators() {
		return this.getString("ipOperators");
	}
	public void setIpOperators(String ipOperators) {
		this.set("ipOperators", ipOperators);
	}
	public Integer getIpCount() {
		return this.getInteger("ipCount");
	}
	public void setIpCount(Integer ipCount) {
		this.set("ipCount", ipCount);
	}
	
}
