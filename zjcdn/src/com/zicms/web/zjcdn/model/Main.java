package com.zicms.web.zjcdn.model;

import com.zicms.common.base.BaseEntity;

@SuppressWarnings({ "unused"})
public class Main extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String ip;
    private String date;
    private String username;

    public String getIp(){return this.getString("ip");}
    public void setIp(String ip){this.set("ip",ip);}
    public String getDate(){return this.getString("date");}
    public void setDate(String date){this.set("date",date);}
    public String getUsername(){return this.getString("username");}
    public void setUsername(String username){this.set("username",username);}


}
