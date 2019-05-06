package com.zicms.web.tool.model;

import java.util.*;
import javax.persistence.*;

import com.zicms.common.base.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

@SuppressWarnings({ "unused"})
@Table(name="article")
public class Article extends BaseEntity{
	private String title;   //标题
	private String keyWord;   //关键词
	private String content;   //内容
	private String titlePic;   //标题图
	private String smallPic;   //缩略图
	private String type;   //文章分类
	private String isSendWx;   //是否发送到微信
	private String isSendWeb;   //是否发送到web端
	private Integer status;   //状态
	private String createBy;   //创建人
	private Date createDate;   //创建时间
	private String updateBy;   //修改人
	private Date updateDate;   //修改时间
	private String delFlag;   //删除标识

	
	public String getTitle(){
		return this.getString("title");
	}
	
	public void setTitle(String title){
		this.set("title",title);
	}
	public String getKeyWord(){
		return this.getString("keyWord");
	}
	
	public void setKeyWord(String keyWord){
		this.set("keyWord",keyWord);
	}
	public String getContent(){
		return this.getString("content");
	}
	
	public void setContent(String content){
		this.set("content",content);
	}
	public String getTitlePic(){
		return this.getString("titlePic");
	}
	
	public void setTitlePic(String titlePic){
		this.set("titlePic",titlePic);
	}
	public String getSmallPic(){
		return this.getString("smallPic");
	}
	
	public void setSmallPic(String smallPic){
		this.set("smallPic",smallPic);
	}
	public String getType(){
		return this.getString("type");
	}
	
	public void setType(String type){
		this.set("type",type);
	}
	public String getIsSendWx(){
		return this.getString("isSendWx");
	}
	
	public void setIsSendWx(String isSendWx){
		this.set("isSendWx",isSendWx);
	}
	public String getIsSendWeb(){
		return this.getString("isSendWeb");
	}
	
	public void setIsSendWeb(String isSendWeb){
		this.set("isSendWeb",isSendWeb);
	}
	public Integer getStatus(){
		return this.getInteger("status");
	}
	
	public void setStatus(Integer status){
		this.set("status",status);
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
