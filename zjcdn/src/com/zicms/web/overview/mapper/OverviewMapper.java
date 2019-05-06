package com.zicms.web.overview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.overview.model.GlobalData;
import com.zicms.web.overview.model.TopFive;

public interface OverviewMapper extends Mapper<GlobalData>{

	public GlobalData getTodayCount();

	public GlobalData getWeekCount();

	public List<Map<String, Object>> getTopn(Map<String, Object> param);

	public GlobalData getIllegalCount();

	public List<GlobalData> getEveryDayCount();

	public List<TopFive> getDomainTopn(@Param("table") String tableName, @Param("limit") int limit);

	public List<TopFive> getIpTopn(@Param("table") String tableName, @Param("limit") int limit);

	public void updateDomainFirm(@Param("table") String table, @Param("domains") List<TopFive> domaintopn);

	public void updateIpOperators(@Param("table") String table, @Param("ips") List<TopFive> iptopn);

}
