package com.zicms.common.beetl.function;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zicms.web.tool.model.Doc;
import com.zicms.web.tool.service.DocService;


/**
 * 用于前端获取最新的文件列表
 * @author taosq
 *
 */
@Component
public class DocFunction {
	
	@Resource
	private DocService docService;
	
	public List<Doc> findDoc(Integer pageSize){
		return docService.findNewDoc(pageSize);
	}
}
