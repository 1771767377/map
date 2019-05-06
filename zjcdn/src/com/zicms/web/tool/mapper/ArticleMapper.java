package com.zicms.web.tool.mapper;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.tool.model.Article;

import java.util.*;


/**
* @author zicms
*/
public interface ArticleMapper extends Mapper<Article>{

	public List<Article> findPageInfo(Map<String, Object> params); 
}
