package com.zicms.web.tool.model;

import java.util.*;
import javax.persistence.*;

import com.zicms.common.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings({ "unused"})
@Table(name="doc")
public class Doc extends BaseEntity{
	private String name;   //名称
	private String path;   //路径
	private String suffix;   //后缀
	private Long size;   //大小
	private Integer type;   //类型
	private Integer downloadCount;   //下载次数
	private Long folder;   //所属分类
	private String md5;   //MD5
	private String createBy;   //创建人
	private Date createDate;   //创建时间
	private String updateBy;   //修改人
	private Date updateDate;   //修改时间
	private String delFlag;   //删除标识
	
	@Transient
	private String tempIds;

	
	public String getName(){
		return this.getString("name");
	}
	
	public void setName(String name){
		this.set("name",name);
	}
	public String getPath(){
		return this.getString("path");
	}
	
	public void setPath(String path){
		this.set("path",path);
	}
	public String getSuffix(){
		return this.getString("suffix");
	}
	
	public void setSuffix(String suffix){
		this.set("suffix",suffix);
	}
	
	public Long getSize(){
		return this.getLong("size");
	}
	
	public void setSize(Long size){
		this.set("size",size);
	}
	public Integer getType(){
		return this.getInteger("type");
	}
	
	public void setType(Integer type){
		this.set("type",type);
	}
	public Integer getDownloadCount(){
		return this.getInteger("downloadCount");
	}
	
	public void setDownloadCount(Integer downloadCount){
		this.set("downloadCount",downloadCount);
	}
	public Long getFolder(){
		return this.getLong("folder");
	}
	
	public void setFolder(Long folder){
		this.set("folder",folder);
	}
	@Column(name="md5")
	public String getMd5(){
		return this.getString("md5");
	}
	
	public void setMd5(String md5){
		this.set("md5",md5);
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

	public String getTempIds() {
		return getString("tempIds");
	}

	public void setTempIds(String tempIds){
		this.set("tempIds", tempIds);
	}
}
