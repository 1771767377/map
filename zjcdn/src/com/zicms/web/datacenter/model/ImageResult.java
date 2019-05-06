package com.zicms.web.datacenter.model;

import javax.persistence.Table;

import com.zicms.common.base.BaseEntity;

/**
 * 图片审核
 * 
 * @author jiajs
 *
 */

@Table(name = "imageresult")
public class ImageResult extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String uuid;// 唯一主键
	private String province;// 编号
	private String city;// 单位名称
	private String information;// 联系人
	private String iplist;// 用户注册的ip网段
	private String linetype;// 网段线路类型
	private String imageturl;// 主键图片地址url
	private int score;// 不良检测成绩
	private String date;// 检测时间
	private String checkdate;// 审核时间
	private int status;// 是否人工审核 0：未审 1：已审核
	private String serverip;// 服务ip地址
	private int type;//
	private String exportdate;// 导出时间
	private String insertdate;// 插入时间

	public String getUuid() {
		return this.getString("uuid");
	}

	public void setUuid(String uuid) {
		this.set("uuid", uuid);
	}

	public String getImageturl() {
		return this.getString("imageturl");
	}

	public void setImageturl(String imageturl) {
		this.set("imageturl", imageturl);
	}

	public String getProvince() {
		return this.getString("province");
	}

	public void setProvince(String province) {
		this.set("province", province);
	}

	public String getCity() {
		return this.getString("city");
	}

	public void setCity(String city) {
		this.set("city", city);
	}

	public String getInformation() {
		return this.getString("information");
	}

	public void setInformation(String information) {
		this.set("information", information);
	}

	public String getIplist() {
		return this.getString("iplist");
	}

	public void setIplist(String iplist) {
		this.set("iplist", iplist);
	}

	public String getLinetype() {
		return this.getString("linetype");
	}

	public void setLinetype(String linetype) {
		this.set("linetype", linetype);
		this.linetype = linetype;
	}

	public int getScore() {
		return this.getInteger("score");
	}

	public void setScore(int score) {
		this.set("score", score);
	}

	public String getDate() {
		return this.getString("date");
	}

	public void setDate(String date) {
		this.set("date", date);
	}

	public String getCheckdate() {
		return this.getString("checkdate");
	}

	public void setCheckdate(String checkdate) {
		this.set("checkdate", checkdate);
	}

	public int getStatus() {
		return this.getInteger("status");
	}

	public void setStatus(int status) {
		this.set("status", status);
	}

	public String getServerip() {
		return this.getString("serverip");
	}

	public void setServerip(String serverip) {
		this.set("serverip", serverip);
	}

	public int getType() {
		return this.getInteger("type");
	}

	public void setType(int type) {
		this.set("type", type);
	}

	public String getExportdate() {
		return this.getString("exportdate");
	}

	public void setExportdate(String exportdate) {
		this.set("exportdate", exportdate);
	}

	public String getInsertdate() {
		return this.getString("insertdate");
	}

	public void setInsertdate(String insertdate) {
		this.set("insertdate", insertdate);
	}
}
