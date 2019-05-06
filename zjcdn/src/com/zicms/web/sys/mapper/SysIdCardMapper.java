package com.zicms.web.sys.mapper;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.sys.model.SysIdCard;

import java.util.*;


/**
* @author zicms
*/
public interface SysIdCardMapper extends Mapper<SysIdCard>{

	public List<SysIdCard> findPageInfo(Map<String, Object> params); 
}
