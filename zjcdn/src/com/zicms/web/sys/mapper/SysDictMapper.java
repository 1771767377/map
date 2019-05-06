

package com.zicms.web.sys.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.sys.model.SysDict;

/**
 * 
 * @author 
 */

public interface SysDictMapper extends Mapper<SysDict>{

	List<SysDict> findDict();

	List<SysDict> findPageInfo(Map<String, Object> params);

}
