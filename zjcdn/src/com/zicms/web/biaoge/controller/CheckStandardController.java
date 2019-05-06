package com.zicms.web.biaoge.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zicms.web.biaoge.model.CheckStandard;
import com.zicms.web.biaoge.service.CheckStandardService;
import com.zicms.web.datacenter.model.ProvinceDict;
import com.zicms.web.datacenter.service.ProvinceDictService;

@Controller
@RequestMapping(value = "check_standard")
public class CheckStandardController {

    @Resource
    private CheckStandardService checkStandardService;
    
    @Resource
    private ProvinceDictService provinceDictService;
    
    @RequestMapping
    public String toImageCheck(Model model, HttpServletRequest req) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar now = Calendar.getInstance();
        now.set(Calendar.HOUR_OF_DAY, 0);
        now.set(Calendar.MINUTE, 0);
        now.set(Calendar.SECOND, 0);
        String startDefault = sdf.format(now.getTime()); // 默认开始时间当天0点
        String endDefault = sdf.format(new Date()); // 默认结束时间当前时间
        Map<String, String> date = new HashMap<String, String>();
        date.put("startDefault", startDefault);
        date.put("endDefault", endDefault);
        List<ProvinceDict> provinces = provinceDictService.findProvinces();
        model.addAttribute("provinces", provinces);
        model.addAttribute("date", date);
        return "biaoge/checkstand/check_standard_manager";
    }

    /**
     * 检出量达标情况分析
     * 
     */
    @RequestMapping(value = "/checkStandard")
    public String checkStandard(@RequestParam Map<String, Object> params,Model model, HttpServletRequest request) {
        int standard = Integer.parseInt(request.getParameter("standard"));
        String dateStart = request.getParameter("dateStart");
        String dateEnd = request.getParameter("dateEnd");
        params.put("dateStart", dateStart);
        params.put("dateEnd", dateEnd);
        List<CheckStandard> page = checkStandardService.getCheckStandard(params);
        Integer totalStandard = checkStandardService.getTotalStandard();
        model.addAttribute("baseTotal", totalStandard);
        model.addAttribute("page", page);
        model.addAttribute("standard", standard);
        return "biaoge/checkstand/check_standard_list";
    }
    
    /**
     * 显示省份字典表
     * @param params
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/pro_show")
    public String proShow(@RequestParam Map<String, Object> params, Model model, HttpServletRequest request) {
    	String province = request.getParameter("province");
    	params.put("province", province);
    	PageInfo<ProvinceDict> page = checkStandardService.findPro(params);
    	ProvinceDict total = checkStandardService.findTotal();
    	model.addAttribute("total", total);
        model.addAttribute("page", page);
        return "biaoge/checkstand/pro_dict";
    }
    
    /**
     * 显示省份编辑框
     * @param params
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/show_layer")
    public String showLayer(Model model, HttpServletRequest request) {
    	String province = request.getParameter("province");
    	ProvinceDict pro = checkStandardService.findByPro(province);
        model.addAttribute("province", pro);
        return "biaoge/checkstand/pro_detail";
    }
    
    /**
     * 给省份添加审核基准
     * @param params
     * @param model
     * @param request
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/add_checkBase", method = RequestMethod.POST)
    public @ResponseBody Integer addCheckBase(@RequestParam Map<String, Object> params,HttpServletRequest req) throws IOException {
        String zone = req.getParameter("zone");
        Integer checkBase = Integer.parseInt(req.getParameter("checkBase"));
        params.put("zone", zone);
        params.put("checkBase", checkBase);
    	return checkStandardService.addCheckBase(params);
    }
}











