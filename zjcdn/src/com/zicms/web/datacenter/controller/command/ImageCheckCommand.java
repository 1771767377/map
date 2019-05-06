package com.zicms.web.datacenter.controller.command;

public class ImageCheckCommand {

    private String imageturl;// 主键

    private String province;// 编号

    private String city;// 单位名称

    private String information;// 联系人

    private String iplist;// 用户注册的ip网段

    private String linetype;// 网段线路类型

    private int score;// 不良检测成绩

    private String date;// 检测时间

    private int status;// 是否人工审核 0：未审 1：已审核

    private String serverip;// 服务ip地址

    private int type;//

    public String getImageturl() {
        return imageturl;
    }

    public void setImageturl(String imageturl) {
        this.imageturl = imageturl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getIplist() {
        return iplist;
    }

    public void setIplist(String iplist) {
        this.iplist = iplist;
    }

    public String getLinetype() {
        return linetype;
    }

    public void setLinetype(String linetype) {
        this.linetype = linetype;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getServerip() {
        return serverip;
    }

    public void setServerip(String serverip) {
        this.serverip = serverip;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
