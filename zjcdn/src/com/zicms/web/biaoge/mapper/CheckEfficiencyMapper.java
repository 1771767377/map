package com.zicms.web.biaoge.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.biaoge.model.CheckEfficiency;

public interface CheckEfficiencyMapper extends Mapper<CheckEfficiency> {

    List<CheckEfficiency> findHourCount(Map<String, Object> params);

    List<CheckEfficiency> findHourCountAll(Map<String, Object> params);

    List<CheckEfficiency> findDayCount(Map<String, Object> params);

    List<CheckEfficiency> findDayCountAll(Map<String, Object> params);

    List<CheckEfficiency> findHourCountFromAudit(Map<String, Object> params);

	List<CheckEfficiency> findDayCountFromAudit(Map<String, Object> params);


}
