package com.zicms.web.datacenter.model;

import com.zicms.common.base.BaseEntity;

/**
 * 图表
 * 
 */
public class EchartData extends BaseEntity implements Comparable<EchartData> {

    private static final long serialVersionUID = 1L;

    private Integer number;

    private String datelabel;

    private String province;

    private String iplist;

    private String url;

    public String getUrl() {
        return this.getString("url");
    }

    public void setUrl(String url) {
        this.set("url", url);
    }

    public Integer getNumber() {
        return this.getInteger("number");
    }

    public void setNumber(Integer number) {
        this.set("number", number);
    }

    public String getdatelabel() {
        return this.getString("datelabel");
    }

    public void setdatelabel(String datelabel) {
        this.set("datelabel", datelabel);
    }

    public String getProvince() {
        return this.getString("province");
    }

    public void setProvince(String province) {
        this.set("province", province);
    }

    public String getIplist() {
        return this.getString("iplist");
    }

    public void setIplist(String iplist) {
        this.set("iplist", iplist);
    }

    @Override
    public int compareTo(EchartData eData) {
        if (eData.getNumber() != null) {
            return eData.getNumber().compareTo(this.getNumber());
        }
        return number;
    }

}
