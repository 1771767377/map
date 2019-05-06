package com.zicms.common.beetl.function;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zicms.web.tool.model.Notice;
import com.zicms.web.tool.service.NoticeService;


/**
 * 用于前端获取公告通知
 * @author Administrator
 *
 */
@Component
public class NoticeFunction {

	@Resource
	private NoticeService noticeService;
	
	public List<Notice> findNotice(Integer pageSize,String type){
		return noticeService.findNewNotice(pageSize,type);
	}
	
}
