package com.zicms.web.overview.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.zicms.web.ipip.IPv4FormatException;
import com.zicms.web.overview.model.GlobalData;
import com.zicms.web.overview.model.TopFive;
import com.zicms.web.overview.service.OverviewService;
import com.zicms.web.util.OutputUtil;

@Controller
@RequestMapping(value="overview")
public class OverviewController {
	
	@Resource
	private OverviewService overviewService;
	
	@RequestMapping(value="/globalData")
	public String overview(Model model){
		return "overview/global_data";
	}
	
	/**
	 * 整体情况数据
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/getData")
	public void getData(HttpServletResponse res) throws IOException{
		GlobalData gd = overviewService.getTodayCount();
		String json = JSONObject.toJSONString(gd);
		OutputUtil.printData(res, json);
	}
	
	/**
	 * 近期（一周）违规图片文本总数量对比
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value="/getTypeCount", method = RequestMethod.POST)
	public void getType(HttpServletResponse res) throws IOException{
		GlobalData weekCount = overviewService.getWeekCount();
		String json = JSONObject.toJSONString(weekCount);
		OutputUtil.printData(res, json);
	}
	
	/**
	 * 最近一周图片文本每天违规的数量
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value="/everyDayCount/{type}", method = RequestMethod.POST)
	public void getEveryDayCount(@PathVariable("type") String path, HttpServletResponse res) throws IOException{
		List<GlobalData> weekCount = null;
		if(path.equals("image")||path.equals("text")){
			weekCount = overviewService.getEveryDayCount();
		}
		String json = JSONObject.toJSONString(weekCount);
		OutputUtil.printData(res, json);
	}
	
	/**
	 * 查询违规域名topn
	 * @param res
	 * @throws IOException
	 */
	@RequestMapping(value="/getDomainTopn/{type}", method = RequestMethod.POST)
	public void getUrlTopn(@PathVariable("type") String path, HttpServletResponse res) throws IOException{
		List<TopFive> topn = null;
		int limit = 5;
		String tableName = "";
		if(path.equals("image")){
			tableName = "image_domain_top";
		}else if(path.equals("text")){
			tableName = "text_domain_top";
		}
		topn = overviewService.getDomainTopn(tableName, limit);
		String json = JSONObject.toJSONString(topn);
		OutputUtil.printData(res, json);
	}
	
	/**
	 * 查询违规ip topn
	 * @param res
	 * @throws IOException
	 * @throws IPv4FormatException 
	 */
	@RequestMapping(value="/getIpTopn/{type}", method = RequestMethod.POST)
	public void getTopn(@PathVariable("type") String path, HttpServletResponse res) throws IOException, IPv4FormatException{
		List<TopFive> topn = null;
		int limit = 5;
		String tableName = "";
		if(path.equals("image")){
			tableName = "image_ip_top";
		}else if(path.equals("text")){
			tableName = "text_ip_top";
		}
		topn = overviewService.getIpTopn(tableName,limit);
		String json = JSONObject.toJSONString(topn);
		OutputUtil.printData(res, json);
	}
}
