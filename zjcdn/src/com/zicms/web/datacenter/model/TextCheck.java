package com.zicms.web.datacenter.model;

import javax.persistence.Table;

import com.zicms.common.base.BaseEntity;

/**
 * 文本审核
 * 
 */
@SuppressWarnings("unused")
@Table(name = "textcheck")
public class TextCheck extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String username;// 导出人账号

    private String province;// 省份

    private String city;// 城市

    private String information;// 地理信息

    private String iplist;// 用户注册的ip网段

    private String linetype;// 网段线路类型

    private String texturl;// 主键文本地址url

    private String contenttype;// 内容类型

    private Integer score;// 不良检测成绩

    private String date;// 采集日期

    private String checkdate;// 审核时间

    private String trialStatus;// 初审状态

    private String retrialStatus;// 复审状态

    private String exportStatus;// 导出状态

    private String serverip;// 服务器ip

    private String clientip;// 客户端ip

    private String clientport;// 客户端端口

    private Integer type;//

    private String texturlmd5;//

    private String uuid;//

    private String exportdate;// 导出时间

    private String insertdate;// 入库时间

    private String startDomain;// 爬虫初始域名

    private String imgHost;// 域名信息

    private String refHost;// 图片所在页面域名

    private String active;// 主动爬取

    private String deviceId;// 设备id

    private String deviceIp;// 设备ip

    private String referer;// 上级页面

    private String trialTime;// 初审时间

    private String retrialTime;// 复审时间

    private String trialAccount;// 初审账号

    private String retrialAccount;// 复审账号

    public String getUsername() {
        return this.getString("username");
    }

    public void setUsername(String username) {
        this.set("username", username);
    }

    public String getProvince() {
        return this.getString("province");
    }

    public void setProvince(String province) {
        this.set("province", province);
    }

    public String getCity() {
        return this.getString("city");
    }

    public void setCity(String city) {
        this.set("city", city);
    }

    public String getInformation() {
        return this.getString("information");
    }

    public void setInformation(String information) {
        this.set("information", information);
    }

    public String getIplist() {
        return this.getString("iplist");
    }

    public void setIplist(String iplist) {
        this.set("iplist", iplist);
    }

    public String getLinetype() {
        return this.getString("linetype");
    }

    public void setLinetype(String linetype) {
        this.set("linetype", linetype);
    }

    public String getTexturl() {
        return this.getString("texturl");
    }

    public void setTexturl(String texturl) {
        this.set("texturl", texturl);
    }

    public String getContenttype() {
        return this.getString("contenttype");
    }

    public void setContenttype(String contenttype) {
        this.set("contenttype", contenttype);
    }

    public Integer getScore() {
        return this.getInteger("score");
    }

    public void setScore(Integer score) {
        this.set("score", score);
    }

    public String getDate() {
        return this.getString("date");
    }

    public void setDate(String date) {
        this.set("date", date);
    }

    public String getCheckdate() {
        return this.getString("checkdate");
    }

    public void setCheckdate(String checkdate) {
        this.set("checkdate", checkdate);
    }

    public String getTrialStatus() {
        return this.getString("trialStatus");
    }

    public void setTrialStatus(String trialStatus) {
        this.set("trialStatus", trialStatus);
    }

    public String getRetrialStatus() {
        return this.getString("retrialStatus");
    }

    public void setRetrialStatus(String retrialStatus) {
        this.set("retrialStatus", retrialStatus);
    }

    public String getExportStatus() {
        return this.getString("exportStatus");
    }

    public void setExportStatus(String exportStatus) {
        this.set("exportStatus", exportStatus);
    }

    public String getServerip() {
        return this.getString("serverip");
    }

    public void setServerip(String serverip) {
        this.set("serverip", serverip);
    }

    public String getClientip() {
        return this.getString("clientip");
    }

    public void setClientip(String clientip) {
        this.set("clientip", clientip);
    }

    public String getClientport() {
        return this.getString("clientport");
    }

    public void setClientport(String clientport) {
        this.set("clientport", clientport);
    }

    public Integer getType() {
        return this.getInteger("type");
    }

    public void setType(Integer type) {
        this.set("type", type);
    }

    public String getTexturlmd5() {
        return this.getString("texturlmd5");
    }

    public void setTexturlmd5(String texturlmd5) {
        this.set("texturlmd5", texturlmd5);
    }

    public String getUuid() {
        return this.getString("uuid");
    }

    public void setUuid(String uuid) {
        this.set("uuid", uuid);
    }

    public String getExportdate() {
        return this.getString("exportdate");
    }

    public void setExportdate(String exportdate) {
        this.set("exportdate", exportdate);
    }

    public String getInsertdate() {
        return this.getString("insertdate");
    }

    public void setInsertdate(String insertdate) {
        this.set("insertdate", insertdate);
    }

    public String getStartDomain() {
        return this.getString("startDomain");
    }

    public void setStartDomain(String startDomain) {
        this.set("startDomain", startDomain);
    }

    public String getImgHost() {
        return this.getString("imgHost");
    }

    public void setImgHost(String imgHost) {
        this.set("imgHost", imgHost);
    }

    public String getRefHost() {
        return this.getString("refHost");
    }

    public void setRefHost(String refHost) {
        this.set("refHost", refHost);
    }

    public String getActive() {
        return this.getString("active");
    }

    public void setActive(String active) {
        this.set("active", active);
    }

    public String getDeviceId() {
        return this.getString("deviceId");
    }

    public void setDeviceId(String deviceId) {
        this.set("deviceId", deviceId);
    }

    public String getDeviceIp() {
        return this.getString("deviceIp");
    }

    public void setDeviceIp(String deviceIp) {
        this.set("deviceIp", deviceIp);
    }

    public String getReferer() {
        return this.getString("referer");
    }

    public void setReferer(String referer) {
        this.set("referer", referer);
    }

    public String getTrialTime() {
        return this.getString("trialTime");
    }

    public void setTrialTime(String trialTime) {
        this.set("trialTime", trialTime);
    }

    public String getRetrialTime() {
        return this.getString("retrialTime");
    }

    public void setRetrialTime(String retrialTime) {
        this.set("retrialTime", retrialTime);
    }

    public String getTrialAccount() {
        return this.getString("trialAccount");
    }

    public void setTrialAccount(String trialAccount) {
        this.set("trialAccount", trialAccount);
    }

    public String getRetrialAccount() {
        return this.getString("retrialAccount");
    }

    public void setRetrialAccount(String retrialAccount) {
        this.set("retrialAccount", retrialAccount);
    }
}
