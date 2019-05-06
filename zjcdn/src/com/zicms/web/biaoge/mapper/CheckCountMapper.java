package com.zicms.web.biaoge.mapper;

import java.util.List;
import java.util.Map;


import com.github.abel533.mapper.Mapper;
import com.zicms.web.biaoge.model.CheckCount;

public interface CheckCountMapper extends Mapper<CheckCount>{
	public List<CheckCount>findAllByAccount(Map<String, Object> params);
	 public List<CheckCount>findAllByDate(Map<String, Object> params);
	 public Integer findCount();
	 public List<CheckCount> findCheckWork(Map<String, Object> params);
	 public List<CheckCount> findWorkByProvince(Map<String, Object> params);

}
