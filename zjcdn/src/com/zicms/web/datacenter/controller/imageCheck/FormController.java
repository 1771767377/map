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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.web.datacenter.model.Check;
import com.zicms.web.datacenter.service.ImageCheckService;

@Controller
@RequestMapping(value = "Form")
public class FormController {

    @Resource
    private ImageCheckService imageCheckService;
    
    /**
     * 跳转到统计表格页面
     */
    @RequestMapping
    public String showForm(Model model) {
        List<Check> list = imageCheckService.findCheck();
        model.addAttribute("list", list);
        return "echart/form";
    }
    
    /**
     * 导出统计表格
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/csvSaveAll", method = RequestMethod.GET)
    public void csvSaveAll(HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Map<String, Object>> dataList = null;
        List<Check> orderBos = null;
        orderBos = imageCheckService.findCheck();
        String sTitle = "用户名,时间,审核数量,确认数量";
        String fName = "form_";
        String mapKey = "username,dt,count,checked";
        dataList = new ArrayList<>();
        Map<String, Object> map = null;
        for (Check order : orderBos) {
            map = new HashMap<String, Object>();
            map.put("username", order.getUsername());
            map.put("dt", order.getDt());
            map.put("count", order.getCount());
            map.put("checked", order.getChecked());
            dataList.add(map);
        }
        try (final OutputStream os = response.getOutputStream()) {
            ImageCheckController.responseSetProperties(fName, response);
            ImageCheckController.doExport(dataList, sTitle, mapKey, os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
