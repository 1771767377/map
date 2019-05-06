package com.zicms.web.datacenter.controller.imageCheck;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.zicms.web.datacenter.model.ImageCheck;
import com.zicms.web.datacenter.model.ProvinceDict;
import com.zicms.web.datacenter.service.ImageCheckService;
import com.zicms.web.datacenter.service.ProvinceDictService;

@Controller
@RequestMapping(value = "History_Image")
public class ImageHistoryController {

    @Resource
    private ImageCheckService imageCheckService;
    
    @Resource
    private ProvinceDictService provinceDictService;
    /**
     * 跳转到图片历史导出数据查询页面
     */
    @RequestMapping
    public String toHistoryImage(Model model) {
    	List<String> trialAccounts = imageCheckService.findTrialAccount();
    	List<String> retrialAccounts = imageCheckService.findRetrialAccount();
    	List<String> usernames = imageCheckService.findUsername();
    	List<ProvinceDict> provinces = provinceDictService.findProvinces();
    	model.addAttribute("provinces", provinces);
    	model.addAttribute("trialAccounts", trialAccounts);
    	model.addAttribute("retrialAccounts", retrialAccounts);
    	model.addAttribute("usernames", usernames);
        return "historydata/image_history";
    }
    
    /**
     * 显示历史已审核的图片
     * 
     * @param params
     * @param imageCheck
     * @param model
     * @return
     */
    @RequestMapping(value = "history_list")
    public String ok_list(@RequestParam Map<String, Object> params, Long[] imageCheck, Model model, HttpServletRequest request) {
        params.put("imageCheck", StringUtils.join(imageCheck, ','));
        String province = request.getParameter("province");
        String imageturl = request.getParameter("imageturl");
        String iplist = request.getParameter("iplist");
        String date1 = request.getParameter("date1");
        String date2 = request.getParameter("date2");
        String checkdate1 = request.getParameter("checkdate1");
        String checkdate2 = request.getParameter("checkdate2");
        int exportStatus = Integer.parseInt(request.getParameter("exportStatus"));
        String trialAccount = request.getParameter("trialAccount");
        String retrialAccount = request.getParameter("retrialAccount");
        String username = request.getParameter("username");
        String trialTime1 = request.getParameter("trialTime1");
        String trialTime2 = request.getParameter("trialTime2");
        String retrialTime1 = request.getParameter("retrialTime1");
        String retrialTime2 = request.getParameter("retrialTime2");
        params.put("province", province);
        params.put("imageturl", imageturl);
        params.put("iplist", iplist);
        params.put("date1", date1);
        params.put("date2", date2);
        params.put("checkdate1", checkdate1);
        params.put("checkdate2", checkdate2);
        params.put("exportStatus", exportStatus);
        params.put("trialAccount", trialAccount);
        params.put("retrialAccount", retrialAccount);
        params.put("username", username);
        params.put("trialTime1", trialTime1);
        params.put("trialTime2", trialTime2);
        params.put("retrialTime1", retrialTime1);
        params.put("retrialTime2", retrialTime2);
        PageInfo<ImageCheck> page = imageCheckService.findHistoryList(params);
        model.addAttribute("page", page);
        return "historydata/image-ok-list";
    }
    
    /**
     * 导出将已审核的全部图片结果信息，导出为csv格式的文件保存到本地
     * 
     * @param request
     * @param response
     * @throws Exception
     */
   /* @ResponseBody
    @RequestMapping(value = "/csvSaveAll", method = RequestMethod.GET)
    public void csvSaveAll(@RequestParam Map<String, Object> params,HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> dataList = null;
        List<ImageCheck> orderBos = imageCheckService.findExportData(uuids);
        String sTitle = "采集日期,url,域名,ip地址,分值,初审时间,复审时间,初审账号,复审账号,导出账号,省份";
        String fName = "pictures_";
        String mapKey = "date,url,http,iplist,score,trialTime,retrialTime,trialAccount,retrialAccount,username,province";
        dataList = new ArrayList<>();
        Map<String, Object> map = null;
        for (ImageCheck order : orderBos) {
            map = new HashMap<String, Object>();
            map.put("date", order.getDate());
            map.put("url", '"' + order.getImageturl() + '"');
            map.put("http", ImageCheckController.formatSubStr(order.getImageturl()));
            map.put("iplist", order.getIplist());
            map.put("score", order.getScore());
            map.put("trialTime", order.getTrialTime());
            map.put("retrialTime", order.getRetrialTime());
            map.put("trialAccount", order.getTrialAccount());
            map.put("retrialAccount", order.getRetrialAccount());
            map.put("username", order.getUsername());
            map.put("province", order.getProvince());
            dataList.add(map);
        }
        try (final OutputStream os = response.getOutputStream()) {
            ImageCheckController.responseSetProperties(fName, response);
            ImageCheckController.doExport(dataList, sTitle, mapKey, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
