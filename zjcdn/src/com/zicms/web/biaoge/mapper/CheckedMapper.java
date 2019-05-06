package com.zicms.web.biaoge.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.biaoge.model.Checked;

public interface CheckedMapper extends Mapper<Checked>{
	 public List<Checked> findPageAll(Map<String, Object> params);
	 public List<Checked> findPageAllByDate(Map<String, Object> params);
	 public List<Checked >findAllByHour(Map<String, Object> params);
	 public List<Checked >findAllByDay(Map<String, Object> params);
}
