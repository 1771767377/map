package com.zicms.web.biaoge.model;

import com.zicms.common.base.BaseEntity;

public class CheckEfficiency extends BaseEntity {

    private String account;

    private String hour;
    
    private Integer count;
    
    private String day;
    
    public String getDay() {
		return this.getString("day");
	}

	public void setDay(Integer day) {
		this.set("day",day);
	}

    public String getAccount() {
        return this.getString("account");
    }

    public void setAccount(String account) {
        this.set("account", account);
    }

    public String getHour() {
        return this.getString("hour");
    }

    public void setHour(String hour) {
        this.set("hour", hour);
    }

    public Integer getCount() {
        return this.getInteger("count");
    }

    public void setCount(Integer count) {
        this.set("count", count);
    }

}
