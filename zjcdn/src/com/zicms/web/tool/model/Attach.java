package com.zicms.web.tool.model;

import java.util.*;
import javax.persistence.*;

import com.zicms.common.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings({ "unused"})
@Table(name="attach")
public class Attach extends BaseEntity{
	private Long article;   //文章
	private Long doc;   //文件
	private String createBy;   //创建人
	private Date createDate;   //创建时间
	private String updateBy;   //修改人
	private Date updateDate;   //修改时间
	private String delFlag;   //删除标识

	
	public Long getArticle(){
		return this.getLong("article");
	}
	
	public void setArticle(Long article){
		this.set("article",article);
	}
	public Long getDoc(){
		return this.getLong("doc");
	}
	
	public void setDoc(Long doc){
		this.set("doc",doc);
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
