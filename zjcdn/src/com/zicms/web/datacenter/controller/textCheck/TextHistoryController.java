package com.zicms.web.datacenter.controller.textCheck;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zicms.common.constant.Constant;
import com.zicms.web.datacenter.model.ProvinceDict;
import com.zicms.web.datacenter.model.TextCheck;
import com.zicms.web.datacenter.service.ProvinceDictService;
import com.zicms.web.datacenter.service.TextCheckService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;
import com.zicms.web.util.CsvExportUtil;
import com.zicms.web.util.StringUtil;

@Controller
@RequestMapping(value = "History_Text")
public class TextHistoryController {

    @Resource
    private TextCheckService textCheckService;
    
    @Resource 
    private ProvinceDictService provinceDictService;
    /**
     * 跳转到文字历史导出数据查询页面
     */
    @RequestMapping
    public String toHistoryText(Model model) {
    	List<String> trialAccounts = textCheckService.findTrialAccount();
    	List<String> retrialAccounts = textCheckService.findRetrialAccount();
    	List<String> usernames = textCheckService.findUsername();
    	List<ProvinceDict> provinces = provinceDictService.findProvinces();
    	model.addAttribute("provinces", provinces);
    	model.addAttribute("trialAccounts", trialAccounts);
    	model.addAttribute("retrialAccounts", retrialAccounts);
    	model.addAttribute("usernames", usernames);
        return "historydata/text_history";
    }
    
    /**
     * 显示历史已审核的文字
     * 
     * @param params
     * @param imageCheck
     * @param model
     * @return
     */
    @RequestMapping(value = "ok_list", method = RequestMethod.POST)
    public String ok_list(@RequestParam Map<String, Object> params, Long[] textCheck, Model model, HttpServletRequest request) {
        params.put("textCheck", StringUtils.join(textCheck, ','));
        String province = request.getParameter("province");
        String texturl = request.getParameter("texturl");
        String iplist = request.getParameter("iplist");
        String trialAccount = request.getParameter("trialAccount");
        String retrialAccount = request.getParameter("retrialAccount");
        String username = request.getParameter("username");
        String trialTime1 = request.getParameter("trialTime1");
        String trialTime2 = request.getParameter("trialTime2");
        String retrialTime1 = request.getParameter("retrialTime1");
        String retrialTime2 = request.getParameter("retrialTime2");
        String checkdate1 = request.getParameter("checkdate1");
        String checkdate2 = request.getParameter("checkdate2");
        String date1 = request.getParameter("date1");
        String date2 = request.getParameter("date2");
        int exportStatus = Integer.parseInt(request.getParameter("exportStatus"));
        params.put("province", province);
        params.put("texturl", texturl);
        params.put("iplist", iplist);
        params.put("trialAccount", trialAccount);
        params.put("retrialAccount", retrialAccount);
        params.put("username", username);
        params.put("trialTime1", trialTime1);
        params.put("trialTime2", trialTime2);
        params.put("retrialTime1", retrialTime1);
        params.put("retrialTime2", retrialTime2);
        params.put("checkdate1", checkdate1);
        params.put("checkdate2", checkdate2);
        params.put("date1", date1);
        params.put("date2", date2);
        params.put("exportStatus", exportStatus);
        PageInfo<TextCheck> page = textCheckService.findExportList(params);
        model.addAttribute("page", page);
        return "historydata/text-ok-list";
    }

    /**
     * 导出 将已审核的图片结果信息，导出为csv格式的文件保存到本地 全部
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/csvSaveAll", method = RequestMethod.GET)
    public void csvSaveAll(@RequestParam Map<String, Object> params,HttpServletRequest request, HttpServletResponse response) throws Exception {
        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String texturl = request.getParameter("texturl");
        String iplist = request.getParameter("iplist");
        //String username = request.getParameter("username");
        String checkdate1 = request.getParameter("checkdate1");
        String checkdate2 = request.getParameter("checkdate2");
        String exportdate1 = request.getParameter("exportdate1");
        String exportdate2 = request.getParameter("exportdate2");
        params.put("province", province);
        params.put("texturl", texturl);
        params.put("iplist", iplist);
        //params.put("username", username);
        params.put("checkdate1", checkdate1);
        params.put("checkdate2", checkdate2);
        params.put("exportdate1", exportdate1);
        params.put("exportdate2", exportdate2);
        List<Map<String, Object>> dataList = null;
        List<TextCheck> orderBos = textCheckService.findExportData(params);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String exportdate = sdf.format(new Date());
        TextCheck text = new TextCheck();
        text.set("exportdate", exportdate);
        String sTitle = "日期,url,域名,ip地址,省份,检出时间,审核时间";
        String fName = "text_";
        String mapKey = "date,url,http,iplist,province,exportdate,checkdate";
        dataList = new ArrayList<>();
        Map<String, Object> map = null;
        for (TextCheck order : orderBos) {
            map = new HashMap<String, Object>();
            map.put("date", order.getDate());
            map.put("url", '"' + order.getTexturl() + '"');
            map.put("http", StringUtil.formatSubStr(order.getTexturl()));
            map.put("iplist", order.getIplist());
            map.put("province", order.getProvince());
            map.put("exportdate", order.getExportdate());
            map.put("checkdate", order.getCheckdate());
            dataList.add(map);
        }
        try (final OutputStream os = response.getOutputStream()) {
        	CsvExportUtil.responseSetProperties(fName, response);
        	CsvExportUtil.doExport(dataList, sTitle, mapKey, os);
            text.setTrialStatus(Constant.EXPORT_YES);
            text.setProvince(province);
            for (TextCheck order : orderBos) {
                String uuid = order.getUuid();
                text.setUuid(uuid);
                textCheckService.saveCsvImage1(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
