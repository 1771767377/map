package com.zicms.web.datacenter.controller.textCheck;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

@Controller
@RequestMapping(value = "UnConfirm_Text")
public class TextUnConfirmController {

    @Resource
    private TextCheckService textCheckService;
    
    /**
     * 跳转到不确定库文字的查询页面
     */
    @RequestMapping
    public String toUnConfirmText(Model model) {
    	String user_id = SysUserUtils.getSessionLoginUser().getUsername();
        String name = SysUserUtils.getSessionLoginUser().getName();
        System.out.println(user_id);
        System.out.println(name);

        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String[] proArr = province.split(",");
        int count = textCheckService.getUnconfirmCount(proArr);
    	model.addAttribute("count", count);
        return "unconfirm/text-manager";
    }
    
    /**
     * 显示审核时不确定的文字
     * 
     * @param params
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String list(Model model, HttpServletRequest request) {
        String trialAccount = request.getParameter("trialAccount");
        String texturl = request.getParameter("texturl");
        String trialTime = request.getParameter("trialTime");
        String iplist = request.getParameter("iplist");
        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String[] proArr = province.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        List<TextCheck> list = null;
        params.put("trialAccount", trialAccount);
        params.put("proArr", proArr);
        params.put("texturl", texturl);
        params.put("iplist", iplist);
        params.put("trialTime", trialTime);
        list = textCheckService.findUnList(params);
        model.addAttribute("page", list);
        return "unconfirm/text-list";
    }
    
    /**
     * 不确定更新为确定
     */
    @RequestMapping(value = "update/{mode}", method = RequestMethod.POST)
    @ResponseBody
    public Integer result_image(@ModelAttribute TextCheck text,@PathVariable("mode") String mode, HttpServletRequest request) {
        SysUser user = SysUserUtils.getSessionLoginUser();
        String retrialAccount = user.getUsername();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        text.setRetrialAccount(retrialAccount);
        if("updateNormal".equals(mode)){
        	text.setRetrialTime(date);
        	text.setRetrialStatus(Constant.STATUS_NORMAL);
        }else if("updateBad".equals(mode)){
        	text.setRetrialStatus(Constant.STATUS_BAD);
        	text.setCheckdate(date);
        	text.setRetrialTime(date);
        }else if("updateUnconfirm".equals(mode)){
        	text.setCheckdate("");
        	text.setRetrialStatus(Constant.STATUS_UNCONFIRM);
        }
        return textCheckService.updateUnconfirmStatus(text);
    }
    
    /**
     * 提交最后一页数据
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value="/submit", method = RequestMethod.GET)
    public String submit(HttpServletRequest request, HttpServletResponse response) throws IOException{
    	String path = request.getContextPath();
        SysUser user = SysUserUtils.getSessionLoginUser();
        // 获取当前用户账号
        String retrialAccount = user.getUsername();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("retrialAccount", retrialAccount);
        int i = textCheckService.updateUnconfirm(param);
        PrintWriter pw = response.getWriter();
        if(i!=-1){
        	response.setContentType("text/html; charset=utf-8");
        	pw.write("<script>alert('数据已成功提交！');</script>");
        	pw.write("<script>window.location.href="+path+"/</script>");
        }else{
        	pw.write("<script>alert('数据提交失败')</script>");
        }
        pw.flush();
        return "";
    }
}
