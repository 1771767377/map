

package com.zicms.web.biaoge.mapper;


import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.biaoge.model.Parttimer;

/**
 * 
 * @author 
 */

public interface ParttimerMapper extends Mapper<Parttimer>{

	public List<Parttimer> findAccount(Map<String, Object> params);
	public List<Parttimer> findPageInfo(Map<String, Object> params);
	public List<String> findUsername();
}
