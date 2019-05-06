package com.zicms.web.biaoge.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.biaoge.model.CheckStandard;
import com.zicms.web.datacenter.model.ProvinceDict;

public interface CheckStandardMapper extends Mapper<CheckStandard> {
	// 检出量达标情况
    List<CheckStandard> getCheckStandard(@Param("params") Map<String, Object> params);
    
    // 查找字典表
    List<ProvinceDict> findPro(@Param("params") Map<String, Object> params);

    //根据编号查询省份
	ProvinceDict findByPro(@Param("province") String province);

	//添加审核基准值
	int addCheckBase(Map<String, Object> params);

	Integer getTotalStandard();

	ProvinceDict findTotal();

}
