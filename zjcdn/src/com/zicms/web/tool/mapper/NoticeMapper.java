package com.zicms.web.tool.mapper;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.tool.model.Notice;

import java.util.*;


/**
* @author zicms
*/
public interface NoticeMapper extends Mapper<Notice>{

	public List<Notice> findPageInfo(Map<String, Object> params); 
}
