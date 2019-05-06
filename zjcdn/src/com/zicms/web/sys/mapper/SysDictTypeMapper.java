

package com.zicms.web.sys.mapper;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.sys.model.SysArea;
import com.zicms.web.sys.model.SysDictType;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author taosq
 */

public interface SysDictTypeMapper extends Mapper<SysDictType>{
	
	public int updateParentIds(SysDictType sysArea);
	
	public int deleteDictTypeByRootId(Long id);

	public List<SysArea> findDictTypeList(Map<String, Object> params);
	
}
