package com.zicms.web.biaoge.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Table;

import com.zicms.common.base.BaseEntity;
@Table(name = "parttimer")
public class Parttimer extends BaseEntity{
		private static final long serialVersionUID = 1L;

	    private String username;//审核账号

	    private Integer standardSettlement;//结算标准(条/元)

	    private Integer auditVolume;//审核量

	    private BigDecimal salary;//应付工资

	    private String name;//姓名

	    private String telephone;//联系电话

	    private String bankAccount;//银行账号

	    private String openBank;//开户行

	    private String updateBy; // update_by <更新者>

	    private Date updateDate; // update_date <更新时间>

	    private String createBy; // create_by <创建者>

	    private Date createDate; // create_date <创建时间>

	    private String delFlag; // del_flag <删除标记(0.正常 1.删除)>


		public String getUsername() {
	        return this.getString("username");
	    }

	    public void setUsername(String username) {
	    	 this.set("username",username);
	    }

	    public Integer getStandardSettlement() {
	        return this.getInteger("standardSettlement");
	    }

	    public void setStandardSettlement(Integer standardSettlement) {
	    	 this.set("standardSettlement",standardSettlement);
	    }

	    public Integer getAuditVolume() {
	    	return this.getInteger("auditVolume");
	    }

	    public void setAuditVolume(Integer auditVolume) {
	    	this.set("auditVolume",auditVolume);
	    }

	    public BigDecimal getSalary() {
	        return this.getBigDecimal("salary");
	    }

	    public void setSalary(BigDecimal salary) {
	    	this.set("salary",salary);
	    }

	    public String getName() {
	        return this.getString("name");
	    }

	    public void setName(String name) {
	    	this.set("name",name);
	    }

	    public String getTelephone() {
	    	return this.getString("telephone");
	    }

	    public void setTelephone(String telephone) {
	    	this.set("telephone",telephone);
	    }

	    public String getBankAccount() {
	    	return this.getString("bankAccount");
	    }

	    public void setBankAccount(String bankAccount) {
	    	this.set("bankAccount",bankAccount);
	    }

	    public String getOpenBank() {
	    	return this.getString("openBank");
	    }

	    public void setOpenBank(String openBank) {
	    	this.set("openBank",openBank);
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
