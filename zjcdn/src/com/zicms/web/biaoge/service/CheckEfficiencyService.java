package com.zicms.web.biaoge.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zicms.common.base.ServiceMybatis;
import com.zicms.web.biaoge.mapper.CheckEfficiencyMapper;
import com.zicms.web.biaoge.model.CheckEfficiency;

@Service("checkEfficiencyService")
public class CheckEfficiencyService extends ServiceMybatis<CheckEfficiency> {

    @Autowired
    private CheckEfficiencyMapper checkEfficiencyMapper;

    public List<CheckEfficiency> findHourCount(Map<String, Object> params) {
        return checkEfficiencyMapper.findHourCount(params);
    }
    
    public List<CheckEfficiency> findHourCountAll(Map<String, Object> params) {
    	return checkEfficiencyMapper.findHourCountAll(params);
    }

	public List<CheckEfficiency> findDayCount(Map<String, Object> params) {
		return checkEfficiencyMapper.findDayCount(params);
	}
	
	public List<CheckEfficiency> findDayCountAll(Map<String, Object> params) {
		return checkEfficiencyMapper.findDayCountAll(params);
	}

	public List<CheckEfficiency> findHourCountFromAudit(Map<String, Object> params) {
		return checkEfficiencyMapper.findHourCountFromAudit(params);
	}

	public List<CheckEfficiency> findDayCountFromAudit(Map<String, Object> params) {
		return checkEfficiencyMapper.findDayCountFromAudit(params);
	}
    /*public PageInfo<CheckEfficiency> findCount(Map<String, Object> params) {
        params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
        params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
        PageHelper.startPage(params);
        List<CheckEfficiency> list = checkEfficiencyMapper.findCount(params);
        return new PageInfo<CheckEfficiency>(list);
    }*/
    
    
}
