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
@RequestMapping("TextReCheck")
public class TextReCheckController {

    @Resource
    private TextCheckService textCheckService;

    /**
     * 跳转到全部图片显示页面（已审核）
     */
    @RequestMapping
    public String toReCheck(Model model) {
    	SysUser user = SysUserUtils.getSessionLoginUser();
        // 从session中获取到省份
        String province = user.getProvince();
        String[] proArr = province.split(",");
        // 查询当前的剩余数量
        int count = textCheckService.getReCount(proArr);
        model.addAttribute("count", count);
        return "text/retext-ok";
    }

    /**
     * 显示已审核的文本
     * @param model
     * @return
     */
    @RequestMapping(value = "text_ok_list", method = RequestMethod.POST)
    public String ok_list(Model model, HttpServletRequest request) {
        SysUser user = SysUserUtils.getSessionLoginUser();
        String province = user.getProvince();
        String[] proArr = province.split(",");
        // 得到复审账号
        String retrialAccount = user.getUsername();
        // 更新复审状态（101-1）
        textCheckService.updateRestatus(retrialAccount);
        String texturl = request.getParameter("texturl");
        String checkdate = request.getParameter("checkdate");
        String iplist = request.getParameter("iplist");
        List<TextCheck> list = null;
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("proArr", proArr);
        params.put("texturl", texturl);
        params.put("checkdate", checkdate);
        params.put("iplist", iplist);
        list = textCheckService.findPageList(params,retrialAccount);
        if(list.size() > 0){
            TextCheck tet = new TextCheck();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String retrialTime = sdf.format(new Date());
            tet.setRetrialStatus(Constant.STATUS_CHECKING);
            tet.setRetrialAccount(retrialAccount);
            tet.setRetrialTime(retrialTime);
            // 更新数据状态
            textCheckService.updateTopList(tet, list);
        }
        model.addAttribute("list", list);
        return "text/retext-ok-list";
    }

    /**
     * 文本复审操作
     */
    @RequestMapping(value = "reCheck/{mode}", method = RequestMethod.POST)
    @ResponseBody
    public Integer approve_save(@ModelAttribute TextCheck txt, @PathVariable("mode") String mode, HttpServletRequest request) {
        if ("updateNormal".equals(mode)) {
            txt.setRetrialStatus(Constant.STATUS_NORMAL);
        } else if ("undoUpdate".equals(mode)) {
        	txt.setRetrialStatus(Constant.STATUS_CHECKING);
        	txt.setCheckdate("");
        }
        return textCheckService.updatereTextCheck(txt);
    }

    /**
     * 提交最后一页数据
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public String submit(HttpServletRequest request, HttpServletResponse response) throws IOException {
        SysUser user = SysUserUtils.getSessionLoginUser();
        // 获取当前用户账号
        String retrialAccount = user.getUsername();
        //获取上下文路径
        String path = request.getContextPath();
        System.out.println(path);
        int i = textCheckService.updateRestatus(retrialAccount);
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
    
}
