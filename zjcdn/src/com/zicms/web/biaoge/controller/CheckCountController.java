package com.zicms.web.biaoge.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.web.biaoge.model.CheckCount;
import com.zicms.web.biaoge.service.CheckCountService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.service.SysUserService;
import com.zicms.web.util.DateUtils;
import com.zicms.web.util.FileUtil;

@Controller
@RequestMapping(value = "check_count")
public class CheckCountController {
    @Resource
    private CheckCountService checkCountService;

    @Resource
    private SysUserService sysUserService;
    
    
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 跳转到审核量统计排名标题页面（按照账号）
     */
    @RequestMapping
    public String toImageCheck(Model model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        String startDefault = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 1);
        String endDefault = sdf.format(cal.getTime());
        //账号下拉框
        List<SysUser> accounts = sysUserService.selectPublicAccount();
        model.addAttribute("accounts", accounts);
        model.addAttribute("startDefault", startDefault);
        model.addAttribute("endDefault", endDefault);
        return "biaoge/checkcount/check_count_manager";
    }

    /**
     * 审核量统计排名列表页（按照账号）
     * 
     * @throws Exception
     */

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String checkCount(Model model, HttpServletRequest request, @RequestParam Map<String, Object> params) {
        // 查询账号、采集日期，待审核量和审核标准，默认状态下是昨天一天的审核量以及待审核量
        String account = request.getParameter("account").trim();// 账号
        String dateStart = request.getParameter("dateStart");// 统计开始时间
        String dateEnd = request.getParameter("dateEnd");// 统计结束时间
        params.put("account", account);
        List<CheckCount> page = null;
        Integer count = 0;
        if (dateStart == null || dateStart.isEmpty() || dateEnd == null || dateEnd.isEmpty()) {
            count = checkCountService.findCount();
            page = checkCountService.findAllByAccount(params);
        } else {
            // 日期范围不为空
            if (account.isEmpty()) {
                params.put("account", null);
            }
            params.put("dateStart", dateStart);
            params.put("dateEnd", dateEnd);
            page = checkCountService.findAllByDate(params);
            // 计算已审核总量
            if (!page.isEmpty() && page.size() != 0) {
                for (CheckCount checkCount : page) {
                    count = count + checkCount.getCheckCount();
                }
            } else {
                count = null;
            }
        }
        model.addAttribute("page", page);
        model.addAttribute("count", count);
        return "biaoge/checkcount/check_count_list";
    }

    /**
     * 并显示表格
     * 
     * @param params
     * @param CheckCount
     * @param model
     * @return
     */
    @RequestMapping(value = "checkwork", method = RequestMethod.GET)
    public String checkwork(@RequestParam Map<String, Object> params, Model model, HttpServletRequest request) {
        String account = request.getParameter("account");
        String dateStart = request.getParameter("dateStart");// 统计开始时间
        String dateEnd = request.getParameter("dateEnd");// 统计结束时间
        // 查询审核人员工作量
        List<CheckCount> page = null;
        if (account.isEmpty()) {
            logger.error("省份参数为空啊，这咋能行呢");
        }
        params.put("account", account);
        if (dateStart == null || dateStart.isEmpty() || dateEnd == null || dateEnd.isEmpty()) {
            // 日期范围为空，按照省份查询昨日各小时时间段的审核率
            // 获取上一天的时间
            dateStart = DateUtils.getNextDay_1(new Date()).get(1);
            dateEnd = DateUtils.getNextDay_1(new Date()).get(0);
            params.put("dateStart", dateStart);
            params.put("dateEnd", dateEnd);
        } else {
            // 日期范围不为空
            params.put("dateStart", dateStart);
            params.put("dateEnd", dateEnd);
        }
        page = checkCountService.findCheckWork(params);
        model.addAttribute("page", page);
        return "biaoge/checkcount/checkwork";
    }
    /**
     * 弹窗(审核基准值的修改)
     * 
     * @param id 用户id
     * @param mode 模式
     * @return
     */
    @RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
    public String showLayer(@PathVariable("mode") String mode, Model model) {
        //获取各账号以及其基准值
    	List<SysUser> sysUser = sysUserService.selectPublicAccountAndBase();
    	model.addAttribute("sysUser", sysUser);
        return "biaoge/checkcount/user-save";
    }
    /**
     * 保存基准值的修改
     * 
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody Integer save(HttpServletRequest request) {
    	String[] usernames = request.getParameterValues("username");
    	String[] checkStandards = request.getParameterValues("checkStandard");
    	int num=0;
    	for (int i = 0; i < usernames.length; i++) {
    		SysUser user=new SysUser();
    		//user.setUsername(usernames[i]);
    		user.set("username",usernames[i]);
    		user.set("checkStandard", Integer.valueOf(checkStandards[i]));
    		//user.setCheckStandard(Integer.valueOf(checkStandards[i]));
    		sysUserService.updateCheckStandardByAccount(user);
    		num++;
		}
    	return num;
    }

}
