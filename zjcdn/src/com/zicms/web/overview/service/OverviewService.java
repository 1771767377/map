package com.zicms.web.overview.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zicms.common.base.ServiceMybatis;
import com.zicms.web.overview.mapper.OverviewMapper;
import com.zicms.web.overview.model.GlobalData;
import com.zicms.web.overview.model.TopFive;

@Service("overviewService")
public class OverviewService extends ServiceMybatis<GlobalData>{

	@Resource
	private OverviewMapper overviewMapper;

	public GlobalData getTodayCount() {
		return overviewMapper.getTodayCount();
	}

	public GlobalData getWeekCount() {
		return overviewMapper.getWeekCount();
	}

	public List<GlobalData> getEveryDayCount() {
		return overviewMapper.getEveryDayCount();
	}

	public List<TopFive> getDomainTopn(String tableName, int limit) {
		return overviewMapper.getDomainTopn(tableName, limit);
	}

	public List<TopFive> getIpTopn(String tableName, int limit) {
		return overviewMapper.getIpTopn(tableName, limit);
	}

}
