package com.zicms.web.datacenter.controller.textCheck;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.common.constant.Constant;
import com.zicms.web.datacenter.model.TextCheck;
import com.zicms.web.datacenter.service.TextCheckService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;
import com.zicms.web.util.CsvExportUtil;
import com.zicms.web.util.StringUtil;

@Controller
@Scope("prototype")
@RequestMapping("TextCheck")
public class TextCheckController {

    @Resource
    private TextCheckService textCheckService;

    public static final String FILE_PATH = "/upload/";
    
    /**
     * 跳转到全部文本显示页面（未审核）
     */
    @RequestMapping
    public String toTextCheck(Model model) {
        SysUser user = SysUserUtils.getSessionLoginUser();
        // 从session中获取到省份
        String province = user.getProvince();
        String[] proArr = province.split(",");
        // 查询当前的剩余数量
        int count = textCheckService.getCount(proArr);
        model.addAttribute("count", count);
        return "text/text-manager";
    }

    /**
     * 展示文本信息
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String list(Model model, HttpServletRequest request) {
        // 得到页面查询条件的值
        String texturl = request.getParameter("texturl");
        String date = request.getParameter("date");
        String iplist = request.getParameter("iplist");
        SysUser user = SysUserUtils.getSessionLoginUser();
        // 获取当前用户的省份
        String province = user.getProvince();
        String[] proArr = province.split(",");
        Map<String, Object> param = new HashMap<String, Object>();
        List<TextCheck> list = null;
        // 得到初审账号
        String trialAccount = user.getUsername();
        // 封装参数
        param.put("trialAccount", trialAccount);
        param.put("proArr", proArr);
        param.put("texturl", texturl);
        param.put("date", date);
        param.put("iplist", iplist);
        list = textCheckService.findList(param);
        if(list.size() > 0){
            TextCheck tet = new TextCheck();
            // 得到当前的时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String trialTime = sdf.format(new Date());
            tet.setTrialStatus(Constant.STATUS_NORMAL);
            tet.setTrialAccount(trialAccount);
            tet.setTrialTime(trialTime);
            tet.setTexturl(texturl);
            tet.setDate(date);
            tet.setIplist(iplist);
            // 更新数据状态（trialstatus由0更为2）
            textCheckService.updateTop(tet, list);
        }
        model.addAttribute("page", list);
        return "text/text-list";
    }

    /**
     * 确认为不良，更改文字状态为1
     */
    @RequestMapping(value = "tetCheck/{mode}", method = RequestMethod.POST)
    public @ResponseBody Integer result_text(@PathVariable("mode") String mode, @ModelAttribute TextCheck text, HttpServletRequest request) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String checkdate = sdf.format(new Date());
        if ("updateBad".equals(mode)) {
            text.setTrialStatus(Constant.STATUS_BAD);
            text.setRetrialStatus(Constant.STATUS_WAIT);
            text.setCheckdate(checkdate);
            // 更新数据状态（trialstatus由2更为1）
        }else if("unconfirm".equals(mode)){
        	text.setTrialStatus(Constant.STATUS_UNCONFIRM);
            text.setRetrialStatus(Constant.STATUS_UNCONFIRM);
        }else if("cancle".equals(mode)){
        	text.setTrialStatus(Constant.STATUS_NORMAL);
            text.setRetrialStatus("");
            text.setCheckdate("");
        }
        return textCheckService.updateTextCheck(text);
    }

    /**
     * 提交最后一页数据
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public String submit(HttpServletRequest request, HttpServletResponse response) throws IOException {
    	SysUser user = SysUserUtils.getSessionLoginUser();
    	// 获取当前用户的初审账号
    	String account = user.getUsername();
    	//获取当前请求上下文路径
    	String path = request.getContextPath();
    	Map<String, Object> param = new HashMap<String, Object>();
    	param.put("trialAccount", account);
    	int i = textCheckService.submitPage(param);
    	PrintWriter pw = response.getWriter();
    	if(i!=-1){
    		response.setContentType("text/html; charset=utf-8");
    		pw.write("<script>alert('数据已成功提交')</script>");
    		pw.write("<script>window.location.href="+path+"/</script>");
    	}else{
    		pw.write("<script>alert('数据提交失败')</script>");
    	}
    	pw.flush();
    	return "";
    }
    
    /**
     * 导出将已审核的前n条文本结果信息，导出为csv格式的文件保存到本地
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    /*@RequestMapping(value = "/test/csvDownLoad", method = RequestMethod.GET)
    public void csvDownLoad(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        int limit = Integer.parseInt(request.getParameter("limit"));
        List<Map<String, Object>> dataList = null;
        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("province", province);
        param.put("limit", limit);
        // 根据条件查询数据
        List<TextCheck> orderBos = textCheckService.findPagLimit(param);
        String sTitle = "采集日期,url,域名,ip地址,初审时间,复审时间,初审账号,复审账号,导出账号,省份";
        String fName = "text_";
        String mapKey = "date,url,http,iplist,trialTime,retrialTime,trialAccount,retrialAccount,username,province";
        dataList = new ArrayList<>();
        Map<String, Object> map = null;
        List<String> list = new ArrayList<String>();
        for (TextCheck order : orderBos) {
            // 获取查到数据的uuid
            String uuid = order.getUuid();
            list.add(uuid);
            // 封装数据
            map = new HashMap<String, Object>();
            map.put("date", order.getDate());
            map.put("url", '"' + order.getTexturl() + '"');
            map.put("http", ImageCheckController.formatSubStr(order.getTexturl()));
            map.put("iplist", order.getIplist());
            map.put("trialTime", order.getTrialTime());
            map.put("retrialTime", order.getRetrialTime());
            map.put("trialAccount", order.getTrialAccount());
            map.put("retrialAccount", order.getRetrialAccount());
            map.put("username", order.getUsername());
            map.put("province", order.getProvince());
            dataList.add(map);
        }
        try (final OutputStream os = response.getOutputStream()) {
            TextCheckController.responseSetProperties(fName, response);
            TextCheckController.doExport(dataList, sTitle, mapKey, os);
            // 插入导出人的账号
            String username = user.getUsername();
            param.put("retrialStatus", 1);
            param.put("list", list);
            param.put("username", username);
            textCheckService.saveCsvImageLimit(param);
            // 将导出的数据同时插入到历史表和学习表中
            textCheckService.insertData(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    /**
     * 导出 将已审核的文本结果信息，导出为csv格式的文件保存到本地 全部
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/csvSave", method = RequestMethod.GET)
    public void csvSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> dataList = null;
        Map<String, Object> param = new HashMap<String, Object>();
        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String[] proArr = province.split(",");
        String username = user.getUsername();
        param.put("proArr", proArr);
        param.put("username", username);
        textCheckService.updateExport(param); //添加导出人和导出时间并修改状态
        List<TextCheck> orderBos = textCheckService.findPag(param);
        String sTitle = "采集日期,url,域名,ip地址,爬虫起始域名,初审时间,复审时间,初审账号,复审账号,导出账号,省份";
        String fName = "text_";
        String mapKey = "date,url,http,iplist,start_domain,trialTime,retrialTime,trialAccount,retrialAccount,username,province";
        dataList = new ArrayList<>();
        Map<String, Object> map = null;
        for (TextCheck order : orderBos) {
            map = new HashMap<String, Object>();
            map.put("date", order.getDate());
            map.put("url", '"' + order.getTexturl() + '"');
            map.put("http", StringUtil.formatSubStr(order.getTexturl()));
            map.put("iplist", order.getIplist());
            map.put("startDomain", order.getStartDomain());
            map.put("trialTime", order.getTrialTime());
            map.put("retrialTime", order.getRetrialTime());
            map.put("trialAccount", order.getTrialAccount());
            map.put("retrialAccount", order.getRetrialAccount());
            map.put("username", order.getUsername());
            map.put("province", order.getProvince());
            dataList.add(map);
        }
        try (final OutputStream os = response.getOutputStream()) {
        	CsvExportUtil.responseSetProperties(fName, response);
        	CsvExportUtil.doExport(dataList, sTitle, mapKey, os);
            textCheckService.deleteData(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
