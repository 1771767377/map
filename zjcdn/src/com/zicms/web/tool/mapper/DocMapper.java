package com.zicms.web.tool.mapper;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.tool.model.Doc;

import java.util.*;


/**
* @author zicms
*/
public interface DocMapper extends Mapper<Doc>{

	public List<Doc> findPageInfo(Map<String, Object> params);

	public List<Doc> findAttachByNotice(Long id); 
}
