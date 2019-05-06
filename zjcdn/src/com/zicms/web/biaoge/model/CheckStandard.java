package com.zicms.web.biaoge.model;

import com.zicms.common.base.BaseEntity;

/**
 * 检出量达标情况
 * 
 * @author forever
 * 
 */
public class CheckStandard extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private String province; // 省份编号
    
    private String name;  //省份名称

    private Integer uncheckCount; // 待审核量

    private Integer checkCount; // 已审核量

    private Integer checkOut; // 不良信息检出量

    private Integer checkBase; // 审核任务基准值
    
    public String getName() {
		return this.getString("name");
	}

	public void setName(String name) {
		this.set("name", name);
	}

	public Integer getCheckBase() {
        return this.getInteger("checkBase");
    }

    public void setCheckBase(Integer checkBase) {
        this.set("checkBase", checkBase);
    }

    public String getProvince() {
        return this.getString("province");
    }

    public void setProvince(String province) {
        this.set("province", province);
    }

    public Integer getUncheckCount() {
        return this.getInteger("uncheckCount");
    }

    public void setUncheckCount(Integer uncheckCount) {
        this.set("uncheckCount", uncheckCount);
    }

    public Integer getCheckCount() {
        return this.getInteger("checkCount");
    }

    public void setCheckCount(Integer checkCount) {
        this.set("checkCount", checkCount);
    }

    public Integer getCheckOut() {
        return this.getInteger("checkOut");
    }

    public void setCheckOut(Integer checkOut) {
        this.set("checkOut", checkOut);
    }

}
