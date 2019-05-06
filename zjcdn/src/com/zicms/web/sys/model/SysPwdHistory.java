package com.zicms.web.sys.model;

import java.util.Date;

import javax.persistence.Table;

import com.zicms.common.base.BaseEntity;

/**
 * 密码历史记录
 * @author taosq
 *
 */
@SuppressWarnings({ "unused"})
@Table(name="sys_pwd_history")
public class SysPwdHistory extends BaseEntity {
	
	/**
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String username;
	private String password;
	private Date createDate;
	public String getUsername() {
		return getString("username");
	}
	public void setUsername(String username) {
		this.set("username",username);
	}
	public String getPassword() {
		return this.getString("password");
	}
	public void setPassword(String password) {
		this.set("password",password);
	}
	public Date getCreateDate() {
		return getDate("createDate");
	}
	public void setCreateDate(Date createDate) {
		this.set("createDate",createDate);
	}
	
}
