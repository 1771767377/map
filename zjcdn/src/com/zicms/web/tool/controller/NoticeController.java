package com.zicms.web.tool.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zicms.common.utils.StringConvert;
import com.zicms.web.sys.service.SysDictService;
import com.zicms.web.tool.model.Notice;
import com.zicms.web.tool.service.DocService;
import com.zicms.web.tool.service.NoticeService;
import com.zicms.web.util.ResponseUtils;


@Controller
@RequestMapping("tool/notice")
public class NoticeController {
	@Resource
	private NoticeService noticeService;
	
	@Resource
	private DocService docService;
	
	@Resource
	private SysDictService sysDictService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toNotice(Model model){
		return "tool/notice/index";
	}
	
	/**
	 * 分页显示
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,HttpServletResponse response){
		if(params.containsKey("sortC")){
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<Notice> page = noticeService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Notice notice,HttpServletRequest request){
		return noticeService.saveNotice(notice,request);
	}
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return noticeService.deleteNotice(id);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return noticeService.deleteNotice(ids);
	}
	
	/**
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/dialog")
	public String layer(Long id,@PathVariable String mode, Model model,String type){
		Notice notice = null;
		if(StringUtils.equals("edit", mode)){
			notice = noticeService.selectByPrimaryKey(id);
			model.addAttribute("notice", notice);
			model.addAttribute("type", notice.getType());
			model.addAttribute("docs", docService.findAttachByNotice(id));
		}else if(StringUtils.equals("detail", mode)){
			notice = noticeService.selectByPrimaryKey(id);
			model.addAttribute("notice", notice);
			model.addAttribute("docs", docService.findAttachByNotice(id));
		}else if(StringUtils.equals("add", mode)){
			model.addAttribute("type", type);
		}
		return mode.equals("detail")?"tool/notice/detail":"tool/notice/save";
	}
	
	
}
	