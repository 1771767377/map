package com.zicms.web.datacenter.controller.textCheck;

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
import com.zicms.web.datacenter.model.TextCheck;
import com.zicms.web.datacenter.service.TextCheckService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;

@Controller
@RequestMapping("TextResult")
public class TextResultController {

    @Resource
    private TextCheckService textCheckService;

    public static final String FILE_PATH = "/upload/";

    /**
     * 跳转到全部文字复审页面（已审核）
     */
    @RequestMapping
    public String toImageCheck(Model model) {
        return "text/text-ok";
    }

    /**
     * 显示已审核的文字
     * 
     * @param params
     * @param imageCheck
     * @param model
     * @return
     */
    @RequestMapping(value = "text_list", method = RequestMethod.POST)
    public String ok_list(@RequestParam Map<String, Object> params, Long[] textCheck, Model model, HttpServletRequest request) {
        params.put("textCheck", StringUtils.join(textCheck, ','));
        String texturl = request.getParameter("texturl");
        String checkdate = request.getParameter("checkdate");
        String iplist = request.getParameter("iplist");
        PageInfo<TextCheck> page = null;
        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String[] proArr = province.split(",");
        params.put("proArr", proArr);
        params.put("province", province);
        params.put("texturl", texturl);
        params.put("checkdate", checkdate);
        params.put("iplist", iplist);
        page = textCheckService.findRecheckList(params);
        model.addAttribute("page", page);
        return "text/text-ok-list";
    }
}
