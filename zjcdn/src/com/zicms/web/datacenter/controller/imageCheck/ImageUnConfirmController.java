package com.zicms.web.datacenter.controller.imageCheck;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zicms.web.datacenter.model.ImageCheck;
import com.zicms.web.datacenter.service.ImageCheckService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;

@Controller
@RequestMapping(value = "UnConfirm_Image")
public class ImageUnConfirmController {

    @Resource
    private ImageCheckService imageCheckService;
    
    /**
     * 跳转到不确定图片库的查询页面
     */
    @RequestMapping
    public String toUnConfirmImage(Model model) {
    	String user_id = SysUserUtils.getSessionLoginUser().getUsername();
        String name = SysUserUtils.getSessionLoginUser().getName();
        System.out.println(user_id);
        System.out.println(name);

        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String[] proArr = province.split(",");
        int count = imageCheckService.getUnconfirmCount(proArr);
    	model.addAttribute("count", count);
        return "unconfirm/image-manager";
    }
    
    /**
     * 显示审核时不确定的图片
     * 
     * @param params
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String list(Model model, HttpServletRequest request) {
        String trialAccount = request.getParameter("trialAccount");
        String imageturl = request.getParameter("imageturl");
        String trialTime = request.getParameter("trialTime");
        String iplist = request.getParameter("iplist");
        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String[] proArr = province.split(",");
        Map<String, Object> params = new HashMap<String, Object>();
        List<ImageCheck> list = null;
        params.put("trialAccount", trialAccount);
        params.put("province", proArr);
        params.put("imageturl", imageturl);
        params.put("trialTime", trialTime);
        params.put("iplist", iplist);
        if (trialAccount == "" &&imageturl == "" && iplist == "" && trialTime == "") {
            list = imageCheckService.findUnConfirm(params);
        } else {
            list = imageCheckService.findUnList(params);
        }
        model.addAttribute("page", list);
        return "unconfirm/image-list";
    }
    
    
    /**
     * 确认为不良
     * @param imag
     * @param request
     */
    @RequestMapping(value = "/toUpdateBad", method = RequestMethod.POST)
    public void toUpdateBad(@ModelAttribute ImageCheck imag, HttpServletRequest request){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String checkdate = sdf.format(new Date());
        SysUser user = SysUserUtils.getSessionLoginUser();
        String retrialAccount = user.getUsername();
        String retrialTime = sdf.format(new Date());
        imag.setRetrialStatus(1);
        imag.setRetrialTime(retrialTime);
        imag.setRetrialAccount(retrialAccount);
        imag.setCheckdate(checkdate);
        imageCheckService.updateImage(imag);
    }
    
    /**
     * 撤销为正常
     * @param imag
     * @param request
     */
    @RequestMapping(value = "/toUpdateNormal", method = RequestMethod.POST)
    public void toUpdateNormal(@ModelAttribute ImageCheck imag, HttpServletRequest request){
        SysUser user = SysUserUtils.getSessionLoginUser();
        String retrialAccount = user.getUsername();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String retrialTime = sdf.format(new Date());
        imag.setRetrialAccount(retrialAccount);
        imag.setRetrialTime(retrialTime);
        imag.setRetrialStatus(2);
        imageCheckService.updateImageToNormal(imag);
    }
    
    /**
     * 取消操作
     * @param imag
     * @param request
     */
    @RequestMapping(value = "/cancle", method = RequestMethod.POST)
    public void cancle(String uuid){
        imageCheckService.undoUpdate(uuid);
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
        int i = imageCheckService.updateUnconfirm(param);
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
