package com.zicms.web.datacenter.model;

import javax.persistence.Table;

import com.zicms.common.base.BaseEntity;

/**
 * 统计每小时的审核数量
 * 
 */
@Table(name = "piccheck")
public class Check extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String username;

    private String date;

    private String uuid;
    
    private Integer flag;

    private String dt;
    
    private Integer count;
    
    private Integer checked;

    public String getUsername() {
        return this.getString("username");
    }

    public void setUsername(String username) {
        this.set("username", username);
    }

    public String getDate() {
        return this.getString("date");
    }

    public void setDate(String date) {
        this.set("date", date);
    }

    public String getUuid() {
        return this.getString("uuid");
    }

    public void setUuid(String uuid) {
        this.set("uuid", uuid);
    }

    public Integer getFlag() {
        return this.getInteger("flag");
    }

    public void setFlag(Integer flag) {
        this.set("flag", flag);
    }
    
    public String getDt() {
        return this.getString("dt");
    }

    public void setDt(String dt) {
        this.set("dt", dt);
    }
    
    public Integer getCount() {
        return this.getInteger("count");
    }

    public void setCount(Integer count) {
        this.set("count", count);
    }
    
    public Integer getChecked() {
        return this.getInteger("checked");
    }

    public void setChecked(Integer checked) {
        this.set("checked", checked);
    }
}
