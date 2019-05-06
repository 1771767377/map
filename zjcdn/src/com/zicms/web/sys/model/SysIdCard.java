package com.zicms.web.sys.model;

import java.util.Date;

import javax.persistence.Table;

import com.zicms.common.base.BaseEntity;

@SuppressWarnings({ "unused", "serial"})
@Table(name="sys_id_card")
public class SysIdCard extends BaseEntity{
	private String province;   //省代号
	private String city;   //城市代号
	private String zone;   //行政区代号
	private String name;   //具体行政地址
	private String createBy;   //创建人
	private Date createDate;   //创建时间
	private String updateBy;   //修改人
	private Date updateDate;   //修改时间
	private String delFlag;   //删除标识

	
	public String getProvince(){
		return this.getString("province");
	}
	
	public void setProvince(String province){
		this.set("province",province);
	}
	public String getCity(){
		return this.getString("city");
	}
	
	public void setCity(String city){
		this.set("city",city);
	}
	public String getZone(){
		return this.getString("zone");
	}
	
	public void setZone(String zone){
		this.set("zone",zone);
	}
	public String getName(){
		return this.getString("name");
	}
	
	public void setName(String name){
		this.set("name",name);
	}
	public String getCreateBy(){
		return this.getString("createBy");
	}
	
	public void setCreateBy(String createBy){
		this.set("createBy",createBy);
	}
	public Date getCreateDate(){
		return this.getDate("createDate");
	}
	
	public void setCreateDate(Date createDate){
		this.set("createDate",createDate);
	}
	public String getUpdateBy(){
		return this.getString("updateBy");
	}
	
	public void setUpdateBy(String updateBy){
		this.set("updateBy",updateBy);
	}
	public Date getUpdateDate(){
		return this.getDate("updateDate");
	}
	
	public void setUpdateDate(Date updateDate){
		this.set("updateDate",updateDate);
	}
	public String getDelFlag(){
		return this.getString("delFlag");
	}
	
	public void setDelFlag(String delFlag){
		this.set("delFlag",delFlag);
	}

}
