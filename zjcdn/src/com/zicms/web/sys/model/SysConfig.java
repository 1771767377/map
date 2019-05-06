package com.zicms.web.sys.model;

import java.util.Date;

import javax.persistence.Table;

import com.zicms.common.base.BaseEntity;
@SuppressWarnings({ "unused"})
@Table(name="sys_config")
public class SysConfig extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String label;
	private String value;
	private String updateBy; //update_by <更新者>
	private Date updateDate; //update_date <更新时间>
	private String createBy; //create_by <创建者>
	private Date createDate; //create_date <创建时间>
	private String delFlag; //del_flag <删除标记(0.正常  1.删除)>
	
	public String getLabel() {
		return this.getString("label");
	}
	public void setLabel(String label) {
		this.set("label",label);
	}
	
	public String getValue() {
		return this.getString("value");
	}
	public void setValue(String value) {
		this.set("value",value);
	}
	 public String getCreateBy() {
			return this.getString("createBy");
	    }
	   
	    public void setCreateBy(String createBy) {
			this.set("createBy", createBy);
	    }

		public Date getCreateDate() {
			return this.getDate("createDate");
	    }
	   
	    public void setCreateDate(Date createDate) {
			this.set("createDate", createDate);
	    }
	    public String getUpdateBy() {
			return this.getString("updateBy");
	    }
	   
	    public void setUpdateBy(String updateBy) {
			this.set("updateBy", updateBy);
	    }

		public Date getUpdateDate() {
			return this.getDate("updateDate");
	    }
	   
	    public void setUpdateDate(Date updateDate) {
			this.set("updateDate", updateDate);
	    }

	    public String getDelFlag() {
			return this.getString("delFlag");
	    }
	   
	    public void setDelFlag(String delFlag) {
			this.set("delFlag", delFlag);
	    }
}
