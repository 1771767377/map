package com.zicms.web.tool.mapper;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.tool.model.Folder;

import java.util.*;


/**
* @author zicms
*/
public interface FolderMapper extends Mapper<Folder>{

	public int updateParentIds(Folder folder);
	
	public int deleteIdsByRootId(Long id);

	public List<Folder> findFolderList(Map<String, Object> params);
}
