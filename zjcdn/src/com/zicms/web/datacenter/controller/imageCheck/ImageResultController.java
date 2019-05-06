package com.zicms.web.datacenter.controller.imageCheck;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.zicms.web.datacenter.model.ImageCheck;
import com.zicms.web.datacenter.service.ImageCheckService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;

@Controller
@RequestMapping("Result")
public class ImageResultController {

    @Resource
    private ImageCheckService imageCheckService;

    public static final String FILE_PATH = "/upload/";

    /**
     * 跳转到全部图片显示页面（已审核）
     */
    @RequestMapping
    public String toImageCheck() {
        return "check/image-ok";
    }

    /**
     * 显示审核结果
     * 
     * @param params
     * @param imageCheck
     * @param model
     * @return
     */
    
    @RequestMapping(value = "ok_list", method = RequestMethod.POST)
    public String ok_list(@RequestParam Map<String, Object> params, Long[] imageCheck, Model model, HttpServletRequest request) {
    	params.put("imageCheck", StringUtils.join(imageCheck, ','));
        String account = request.getParameter("account");
        String imageturl = request.getParameter("imageturl");
        String checkdate = request.getParameter("checkdate");
        String iplist = request.getParameter("iplist");
        PageInfo<ImageCheck> page = null;
        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String[] proArr = province.split(",");
        params.put("province", proArr);
        params.put("account", account);
        params.put("imageturl", imageturl);
        params.put("checkdate", checkdate);
        params.put("iplist", iplist);
        page = imageCheckService.findPageList(params);
        model.addAttribute("page", page);
        return "check/image-ok-list";
    }

}
