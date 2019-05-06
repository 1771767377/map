package com.zicms.web.tool.mapper;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.tool.model.Attach;

import java.util.*;


/**
* @author zicms
*/
public interface AttachMapper extends Mapper<Attach>{

	public List<Attach> findPageInfo(Map<String, Object> params); 
}
