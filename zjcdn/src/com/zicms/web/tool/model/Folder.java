package com.zicms.web.tool.model;

import java.util.*;
import javax.persistence.*;

import com.zicms.common.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings({ "unused"})
@Table(name="folder")
public class Folder extends BaseEntity{

	private static final long serialVersionUID = -254682754291532644L;
	private String name;   //名称
	private Long parentId;   //父编号
	private String parentIds;   //父编号集
	private String createBy;   //创建人
	private Date createDate;   //创建时间
	private String updateBy;   //修改人
	private Date updateDate;   //修改时间
	private String delFlag;   //删除标识

    @Transient
    private String oldParentIds; //旧的pids,非表中字段，用作更新用
	
	public String getName(){
		return this.getString("name");
	}
	
	public void setName(String name){
		this.set("name",name);
	}
	public Long getParentId(){
		return this.getLong("parentId");
	}
	
	public void setParentId(Long parentId){
		this.set("parentId",parentId);
	}
	public String getParentIds(){
		return this.getString("parentIds");
	}
	
	public void setParentIds(String parentIds){
		this.set("parentIds",parentIds);
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

    public String getOldParentIds() {
		return this.getString("oldParentIds");
    }
   
    public void setOldParentIds(String oldParentIds) {
		this.set("oldParentIds", oldParentIds);
    }
}
