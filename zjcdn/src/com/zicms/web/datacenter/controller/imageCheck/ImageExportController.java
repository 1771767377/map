package com.zicms.web.datacenter.controller.imageCheck;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.web.datacenter.model.ImageCheck;
import com.zicms.web.datacenter.service.ImageCheckService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;
import com.zicms.web.util.CsvExportUtil;
import com.zicms.web.util.StringUtil;

@Controller
@RequestMapping(value = "ImageExport")
public class ImageExportController {

    @Resource
    private ImageCheckService imageCheckService;

    /**
     * 跳转到导出页面
     */
    @RequestMapping
    public String toExport() {
        return "export/export_image";
    }

    /**
     * 导出将已审核的前n条图片结果信息，导出为csv格式的文件保存到本地
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    
    
  /*  @ResponseBody
    @RequestMapping(value = "/csvSave", method = RequestMethod.GET)
    public void csvSave(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String imageturl = request.getParameter("imageturl");
        String checkdate = request.getParameter("checkdate");
        String iplist = request.getParameter("iplist");
        int limit = Integer.parseInt(request.getParameter("limit"));
        List<Map<String, Object>> dataList = null;
        List<ImageCheck> orderBos = null;
        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String username = user.getUsername();
        Map<String, Object> param = new HashMap<String, Object>();
        List<String> uuidLimit = new ArrayList<String>();
        param.put("province", province);
        param.put("imageturl", imageturl);
        param.put("iplist", iplist);
        param.put("checkdate", checkdate);
        param.put("limit", limit);
        if (imageturl == "" && iplist == "" && checkdate == "") {
            orderBos = imageCheckService.findPagLimit(param);
            for (ImageCheck image : orderBos) {
                String uuid = image.getUuid();
                uuidLimit.add(uuid);
            }
        } else {
            orderBos = imageCheckService.findByUuidsLimit(uuids, limit);
        }
        String sTitle = "采集日期,url,域名,ip地址,分值,初审时间,复审时间,初审账号,复审账号,导出账号,省份";
        String fName = "pictures_";
        String mapKey = "date,url,http,iplist,score,trialTime,retrialTime,trialAccount,retrialAccount,username,province";
        dataList = new ArrayList<>();
        Map<String, Object> map = null;
        ImageCheck imag = new ImageCheck();
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
            imag.setUsername(username);
            imag.setProvince(province);
            if (uuids.size() != 0) {
                imageCheckService.saveCsvImage(imag, uuids);
                imageCheckService.insertData(uuids);
            } else {
                imageCheckService.saveCsvImage(imag, uuidLimit);
                imageCheckService.insertData(uuidLimit);
            }
            ImageCheckController.responseSetProperties(fName, response);
            ImageCheckController.doExport(dataList, sTitle, mapKey, os);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/
    /**
     * 导出将已审核的全部图片结果信息，导出为csv格式的文件保存到本地
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/csvSaveAll", method = RequestMethod.GET)
    public void csvSaveAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> dataList = null;
        List<ImageCheck> orderBos = null;
        SysUser user = SysUserUtils.getSessionLoginUser();
        String username = user.getUsername();
        String province = user.getProvince();
        String[] proArr = province.split(",");
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", username);
        param.put("province", proArr);
    	imageCheckService.saveCsvImage(param);
        orderBos = imageCheckService.findPag(param);
        String sTitle = "采集日期,url,域名,爬虫起始域名,ip地址,分值,初审时间,复审时间,初审账号,复审账号,导出账号,省份";
        String fName = "pictures_";
        String mapKey = "date,url,http,start_domain,iplist,score,trialTime,retrialTime,trialAccount,retrialAccount,username,province";
        dataList = new ArrayList<>();
        Map<String, Object> map = null;
        for (ImageCheck order : orderBos) {
            map = new HashMap<String, Object>();
            map.put("date", order.getDate());
            map.put("url", '"' + order.getImageturl() + '"');
            map.put("http", StringUtil.formatSubStr(order.getImageturl()));
            map.put("iplist", order.getIplist());
            map.put("startDomain", order.getStartDomain());
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
        	CsvExportUtil.responseSetProperties(fName, response);
        	CsvExportUtil.doExport(dataList, sTitle, mapKey, os);
            imageCheckService.replace(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
