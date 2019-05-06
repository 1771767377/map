package com.zicms.web.datacenter.controller.imageCheck;

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
import com.zicms.web.datacenter.service.ImageCheckService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;
/**
 * 暂未开发
 * @author admin
 *
 */
@Controller
@RequestMapping(value = "Learn_image")
public class ImageLearnController {

    @Resource
    private ImageCheckService imageCheckService;
    
    /**
     * 跳转到学习库页面
     */
    @RequestMapping
    public String toHistoryImage(Model model) {
        return "learndata/image-learn-manager";
    }
    
    /**
     * 显示学习库的图片
     * 
     * @param params
     * @param imageCheck
     * @param model
     * @return
     */
    @RequestMapping(value = "learn_list")
    public String ok_list(@RequestParam Map<String, Object> params, Long[] imageCheck, Model model, HttpServletRequest request) {
        params.put("imageCheck", StringUtils.join(imageCheck, ','));
        //String province = SysUserUtils.getSessionLoginUser().getProvince();
        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String status = request.getParameter("status");
        params.put("province", province);
        params.put("status", status);
        PageInfo<ImageCheck> page = imageCheckService.findLearnList(params);
        model.addAttribute("page", page);
        return "learndata/image-learn-list";
    }
}