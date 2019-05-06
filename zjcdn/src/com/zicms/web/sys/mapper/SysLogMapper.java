

package com.zicms.web.sys.mapper;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.sys.model.SysLog;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author 
 */

public interface SysLogMapper extends Mapper<SysLog> {


	public List<SysLog> findSysLogList(Map<String, Object> params);
   

}
