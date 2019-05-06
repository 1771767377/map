package com.zicms.web.biaoge.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zicms.web.biaoge.model.CheckEfficiency;
import com.zicms.web.biaoge.service.CheckEfficiencyService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.service.SysUserService;

@Controller
@RequestMapping(value = "check_efficiency")
public class CheckEfficiencyController {
    @Resource
    private CheckEfficiencyService checkEfficiencyService;
    
    @Resource
    private SysUserService sysuserService;
   
    /**
     * 跳转到全部图片显示页面（未审核）
     */
    @RequestMapping
    public String toImageCheck(Model model) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String startDefault = sdf1.format(cal.getTime());
        String endDefault = sdf2.format(cal.getTime());
        List<SysUser> accounts = sysuserService.selectPublicAccount();
        model.addAttribute("accounts", accounts);
        model.addAttribute("startDefault", startDefault);
        model.addAttribute("endDefault",endDefault);
        return "biaoge/checkefficiency/check_efficiency_manager";
    }

    /**
     * 审核人员工作效率分析
     * @throws ParseException 
     * @throws IOException 
     */
	@RequestMapping(value = "/checkEfficiency")
    public String checkEfficiency(Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
    	String account = request.getParameter("account");
    	String checkType = request.getParameter("checkType");
    	String dataResource = request.getParameter("dataResource");
    	String dateStart = request.getParameter("dateStart");
    	String dateEnd = request.getParameter("dateEnd");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date start = sdf.parse(dateStart);
    	Date end = sdf.parse(dateEnd);
    	long startTime = start.getTime();
    	long endTime = end.getTime();
    	long deff = (endTime - startTime)/(1000*60*60*24);
    	if(deff>6){  //显示七天数据，多于七天弹出警告框
    		response.setContentType("text/html; charset=utf-8");
    		response.getWriter().write("<script>alert('只能查询七天的数据，请更改查询时间')</script>");
    		response.getWriter().flush();
    		response.getWriter().close();
    		return "";
    	}else{
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("account", account);
    		params.put("checkType", checkType);
    		params.put("dataResource", dataResource);
    		params.put("dateStart", dateStart);
    		params.put("dateEnd", dateEnd);
    		//判断起止时间是不是同一天
    		boolean sameDay = DateUtils.isSameDay(start, end);
    		List<CheckEfficiency> list = null;
    		if(sameDay){  //是同一天，则按小时展示结果
    			if(params.get("dataResource")==null || "".equals(params.get("dataResource"))){
    				list = checkEfficiencyService.findHourCountAll(params);    				
    			}else{
    				if("audit_info".equals(params.get("dataResource"))){
    					list = checkEfficiencyService.findHourCountFromAudit(params);
    				}else{
    					list = checkEfficiencyService.findHourCount(params);
    				}
    			}
    			Map<Object, Object> map = new HashMap<Object, Object>();
    			int[] arr = null;
    			for (CheckEfficiency checkEfficiency : list) {
    				String account1 = checkEfficiency.getAccount();
    				if(!map.containsKey(account1)){
    					arr = new int[24];    					
    				}
    				Integer hour = Integer.parseInt(checkEfficiency.getHour());
    				Integer count = checkEfficiency.getCount();
    				arr[hour] = count;
    				map.put(account1, Arrays.toString(arr));
    			}
    			JSONObject json = JSONObject.fromObject(map);
    			model.addAttribute("json", json);
    			model.addAttribute("startDefault", dateStart);
    			model.addAttribute("endDefault",dateEnd);
    			return "biaoge/checkefficiency/check_efficiency_hourList";
    		}else{  //不是同一天按天展示结果
    			if(params.get("dataResource")==null || "".equals(params.get("dataResource"))){
    				list = checkEfficiencyService.findDayCountAll(params);    				
    			}else{
    				if("audit_info".equals(params.get("dataResource"))){
    					list = checkEfficiencyService.findDayCountFromAudit(params);
    				}else{
    					list = checkEfficiencyService.findDayCount(params);
    				}
    			}
    			Map<Object,Map<Object,Object>> resultMap = new HashMap<Object,Map<Object,Object>>();
    			Map<Object,Object> dayCount = null;
    			for(CheckEfficiency efficiency: list){
    				String name = efficiency.getAccount();
    				if(!resultMap.containsKey(name)){
    					dayCount = new HashMap<Object,Object>();
    				}
					String day = efficiency.getDay();
					Integer count = efficiency.getCount();
					dayCount.put(day, count);
					resultMap.put(name,dayCount);
    			}
    			JSONObject result = JSONObject.fromObject(resultMap);
    			model.addAttribute("dateStart",dateStart);
    			model.addAttribute("list",result);
    			return "biaoge/checkefficiency/check_efficiency_dayList";
    		}
    	}
    }
    
    @RequestMapping(value = "/showDetail")
    public String showDetail(Model model, HttpServletRequest request, HttpServletResponse response) throws ParseException, IOException {
    	String account = request.getParameter("account");
    	String checkType = request.getParameter("checkType");
    	String dateStart = request.getParameter("dateStart");
    	String dateEnd = request.getParameter("dateEnd");
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	Date start = sdf.parse(dateStart);
    	Date end = sdf.parse(dateEnd);
    	long startTime = start.getTime();
    	long endTime = end.getTime();
    	long deff = (endTime - startTime)/(1000*60*60*24);
    	if(deff>6){  //显示七天数据，多于七天弹出警告框
    		response.setContentType("text/html; charset=utf-8");
    		response.getWriter().write("<script>alert('只能查询七天的数据，请更改查询时间')</script>");
    		response.getWriter().flush();
    		response.getWriter().close();
    		return "";
    	}else{
    		Map<String, Object> params = new HashMap<String, Object>();
    		params.put("account", account);
    		params.put("checkType", checkType);
    		params.put("dateStart", dateStart);
    		params.put("dateEnd", dateEnd);
    		//判断起止时间是不是同一天
    		boolean sameDay = DateUtils.isSameDay(start, end);
    		List<CheckEfficiency> list = null;
    		if(sameDay){  //是同一天，则按小时展示结果
    			if(params.get("dataResource")==null || "".equals(params.get("dataResource"))){
    				list = checkEfficiencyService.findHourCountAll(params);    				
    			}else{
    				if("audit_info".equals(params.get("dataResource"))){
    					list = checkEfficiencyService.findHourCountFromAudit(params);
    				}else{
    					list = checkEfficiencyService.findHourCount(params);
    				}
    			}
    			Map<Object, Object> map = new HashMap<Object, Object>();
    			int[] arr = {};
    			for (CheckEfficiency checkEfficiency : list) {
    				String account1 = checkEfficiency.getAccount();
    				if(!map.containsKey(account1)){
    					arr = new int[24];				
    				}
    				Integer hour = Integer.parseInt(checkEfficiency.getHour());
    				Integer count = checkEfficiency.getCount();
    				arr[hour] = count;
    				map.put(account1, Arrays.toString(arr));
    			}
    			JSONObject json = JSONObject.fromObject(map);
    			model.addAttribute("json", json);
    			model.addAttribute("startDefault", dateStart);
    			model.addAttribute("endDefault",dateEnd);
    			return "biaoge/checkefficiency/hourList";
    		}else{  //不是同一天按天展示结果
    			if(params.get("dataResource")==null || "".equals(params.get("dataResource"))){
    				list = checkEfficiencyService.findDayCountAll(params);    				
    			}else{
    				if("audit_info".equals(params.get("dataResource"))){
    					list = checkEfficiencyService.findDayCountFromAudit(params);
    				}else{
    					list = checkEfficiencyService.findDayCount(params);
    				}
    			}
    			Map<Object,Map<Object,Object>> resultMap = new HashMap<Object,Map<Object,Object>>();
    			Map<Object,Object> dayCount = null;
    			for(CheckEfficiency efficiency: list){
    				String name = efficiency.getAccount();
    				if(!resultMap.containsKey(name)){
    					dayCount = new HashMap<Object,Object>();
    				}
					String day = efficiency.getDay();
					Integer count = efficiency.getCount();
					dayCount.put(day, count);
					resultMap.put(name,dayCount);
    			}
    			JSONObject result = JSONObject.fromObject(resultMap);
    			model.addAttribute("dateStart",dateStart);
    			model.addAttribute("list",result);
    			return "biaoge/checkefficiency/dayList";
    		}
    	}
    }
}
