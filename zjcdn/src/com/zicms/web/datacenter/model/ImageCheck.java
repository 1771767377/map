package com.zicms.web.datacenter.model;

import javax.persistence.Table;

import com.zicms.common.base.BaseEntity;

/**
 * 图片审核
 * 
 * @author jiajs
 * 
 */

@Table(name = "imagecheck")
public class ImageCheck extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String username; // 导出账号

    private String uuid;// 唯一主键

    private String province;// 编号

    private String city;// 单位名称

    private String information;// 联系人

    private String iplist;// 用户注册的ip网段

    private String linetype;// 网段线路类型

    private String imageturl;// 主键图片地址url

    private String contenttype;

    private int score;// 不良检测成绩

    private String date;// 检测时间

    private String checkdate;// 审核时间

    private int trialStatus;// 初审结果：0：未审 1：不良 2：正常 3：不确定

    private int retrialStatus; // 复审结果：0：未审 1：不良 2：正常 3：不确定

    private int exportStatus; // 导出状态

    private String serverip;// 服务ip地址

    private String clientip;

    private String clientport;

    private int type;

    private String imageturlmd5;

    private String exportdate;// 导出时间

    private String insertdate;// 插入时间

    private String startDomain;//

    private String imgHost;

    private String refHost;

    private String active;

    private String deviceId;// 设备id

    private String deviceIp;

    private String referer;

    private String trialAccount; // 初审账号

    private String retrialAccount; // 复审账号

    private String trialTime; // 初审时间

    private String retrialTime; // 复审时间

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

    public String getUuid() {
        return this.getString("uuid");
    }

    public void setUuid(String uuid) {
        this.set("uuid", uuid);
    }

    public String getImageturl() {
        return this.getString("imageturl");
    }

    public void setImageturl(String imageturl) {
        this.set("imageturl", imageturl);
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

    public int getScore() {
        return this.getInteger("score");
    }

    public void setScore(int score) {
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

    public String getServerip() {
        return this.getString("serverip");
    }

    public void setServerip(String serverip) {
        this.set("serverip", serverip);
    }

    public int getType() {
        return this.getInteger("type");
    }

    public void setType(int type) {
        this.set("type", type);
    }

    public String getStartDomain() {
        return this.getString("startDomain");
    }

    public void setStartDomain(String startDomain) {
        this.set("startDomain", startDomain);
    }

    public void a(String deviceId) {
        this.set("deviceId", deviceId);
    }

    public int getTrialStatus() {
        return this.getInteger("trialStatus");
    }

    public void setTrialStatus(int trialStatus) {
        this.set("trialStatus", trialStatus);
    }

    public int getRetrialStatus() {
        return this.getInteger("retrialStatus");
    }

    public void setRetrialStatus(int retrialStatus) {
        this.set("retrialStatus", retrialStatus);
    }

    public String getUsername() {
        return this.getString("username");
    }

    public void setUsername(String username) {
        this.set("username", username);
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

    public String getContenttype() {
        return this.getString("contenttype");
    }

    public void setContenttype(String contenttype) {
        this.set("contenttype", contenttype);
    }

    public int getExportStatus() {
        return this.getInteger("exportStatus");
    }

    public void setExportStatus(int exportStatus) {
        this.set("exportStatus", exportStatus);
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

    public String getImageturlmd5() {
        return this.getString("imageturlmd5");
    }

    public void setImageturlmd5(String imageturlmd5) {
        this.set("imageturlmd5", imageturlmd5);
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

    public void setDeviceId(String deviceId) {
        this.set("deviceId", deviceId);
    }

    public String getDeviceId() {
        return this.getString("deviceId");
    }

}
