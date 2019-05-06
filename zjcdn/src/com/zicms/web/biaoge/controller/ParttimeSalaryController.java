package com.zicms.web.biaoge.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageInfo;
import com.zicms.web.biaoge.model.Parttimer;
import com.zicms.web.biaoge.service.ParttimerService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;
import com.zicms.web.util.DateUtils;

@Controller
@RequestMapping(value = "part_time_salary")
public class ParttimeSalaryController {
    @Resource
    private ParttimerService parttimerService;
    private Logger logger;

    /**
     * 跳转到兼职人员工资核算管理页面
     */
    @RequestMapping
    public String toParttimeManager(Model model) {
        // 默认当天时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        Calendar cal = Calendar.getInstance();
        String startDefault = sdf.format(cal.getTime());
        cal.add(Calendar.DATE, 1);
        String endDefault = sdf.format(cal.getTime());
        List<String> accounts = parttimerService.findUsername();
        model.addAttribute("accounts", accounts);
        model.addAttribute("startDefault", startDefault);
        model.addAttribute("endDefault", endDefault);
        return "biaoge/parttime/parttime_manager";
    }

    /**
     * 兼职人员列表(等待输入数据界面)
     * 
     * @param params
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String list(@RequestParam Map<String, Object> params, Model model, HttpServletRequest request) {
        // 条件查询判定
        // 查询账号、采集日期，待审核量和审核标准，默认状态下是昨天一天的审核账号
        String account = request.getParameter("account");// 账号
        String dateStart = request.getParameter("dateStart");// 统计开始时间
        String dateEnd = request.getParameter("dateEnd");// 统计结束时间
        // 日期范围不为空
        if (account.isEmpty()) {
            params.put("username", null);
        } else {
            params.put("username", account);
        }

        if (dateStart == null || dateStart.isEmpty() || dateEnd == null || dateEnd.isEmpty()) {
            // 获取今天一天的时间
            dateStart = DateUtils.getNextDay_1(new Date()).get(2);// 明天0点
            dateEnd = DateUtils.getNextDay_1(new Date()).get(0);
            params.put("dateStart", dateStart);
            params.put("dateEnd", dateEnd);
        } else {
            params.put("dateStart", dateStart);
            params.put("dateEnd", dateEnd);
        }
        List<Parttimer> accounts = parttimerService.findAccount(params);
        model.addAttribute("accounts", accounts);
        model.addAttribute("dateStart", dateStart);
        model.addAttribute("dateEnd", dateEnd);
        return "biaoge/parttime/parttime_list";
    }

    /**
     * 保存工资核算信息(添加)
     * 
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(Model model, @RequestParam Map<String, Object> params, HttpServletRequest request) {
        String id = request.getParameter("id");
        String username = request.getParameter("username");
        String standardSettlement = request.getParameter("standardSettlement");
        String auditVolume = request.getParameter("auditVolume");
        String salary = request.getParameter("salary");
        String name = request.getParameter("name");
        String telephone = request.getParameter("telephone");
        String bankAccount = request.getParameter("bankAccount");
        String openBank = request.getParameter("openBank");
        Parttimer parttimer = new Parttimer();
        parttimer.setUsername(username);
        if (id == null || id.isEmpty()) {
            id = null;
        } else {
            parttimer.setId(Long.parseLong(id));
        }
        if (standardSettlement == null || standardSettlement.isEmpty()) {
            logger.error("审核标准为空，未能从前台获取数据");
        } else {
            parttimer.setStandardSettlement(Integer.parseInt(standardSettlement));
        }
        if (auditVolume == null || auditVolume.isEmpty()) {
            logger.error("审核量为空，未能从前台获取数据");
        } else {
            parttimer.setAuditVolume(Integer.parseInt(auditVolume));
        }
        if (salary == null || salary.isEmpty()) {
            logger.error("工资为空，未能从前台获取数据");
        } else {
            parttimer.setSalary(new BigDecimal(salary));
        }
        parttimer.setName(name);
        parttimer.setTelephone(telephone);
        parttimer.setBankAccount(bankAccount);
        parttimer.setOpenBank(openBank);
        parttimerService.saveParttimer(parttimer);
        return null;
    }

    /**
     * 弹窗(更新)
     * 
     * @param id 用户id
     * @param mode 模式
     * @return
     */
    @RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
    public String showLayer(Long id, @PathVariable("mode") String mode, Model model) {
        // 更新
        Parttimer parttimer = parttimerService.selectByPrimaryKey(id);
        model.addAttribute("parttimer", parttimer);
        return "biaoge/parttime/parttime-edit";
    }

    /**
     * 保存工资核算信息(更新)
     * 
     * @return
     */
    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView save(@ModelAttribute Parttimer parttimer, HttpServletRequest request) {
        parttimerService.saveParttimer(parttimer);
        // 重定向到另一个页面，将数据插入到另一个表中，并遍历
        return new ModelAndView("redirect:/part_time_salary/iframe_list");
    }

    /**
     * 跳转到数据显示页面(将插入的数据显示出来)
     */
    @RequestMapping(value = "iframe_list", method = RequestMethod.GET)
    public String toDataCheck_1(@RequestParam Map<String, Object> params, Model model, HttpServletRequest request) {
        // 加载侧边栏
        SysUser user = SysUserUtils.getSessionLoginUser();
        if (user == null || SysUserUtils.getCacheLoginUser() == null) {
            return "redirect:/login";
        }
        List<String> accounts = parttimerService.findUsername();
        model.addAttribute("accounts", accounts);
        model.addAttribute("menuList", SysUserUtils.getUserMenus());
        return "biaoge/parttime/parttime_manager_1";
    }

    /**
     * 删除工资核算信息账号（逻辑删除）
     * 
     * @param id 用户id
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody
    Integer del(Long id, HttpServletResponse response) throws IOException {
        return parttimerService.deleteUser(id);
        // return new ModelAndView("redirect:/part_time_salary/iframe_list");
    }

    /**
     * 兼职人员列表(数据记录查询界面)
     * 
     * @param params
     * @param model
     * @return
     */
    @RequestMapping(value = "selectList", method = RequestMethod.POST)
    public String selectList(@RequestParam Map<String, Object> params, Model model, HttpServletRequest request) {
        // 条件查询判定
        // 查询账号、录入日期（在此按照createDate字段·计算）
        String username = request.getParameter("username");// 账号
        String createDate = request.getParameter("createDate");// 录入时间
        params.put("createDate", createDate);
        if (username != null) {
            params.put("username", username);
        }
        params.put("username", username);
        // 条件查询和非条件查询一起
        PageInfo<Parttimer> page = null;
        page = parttimerService.findPageInfo(params);
        model.addAttribute("page", page);
        return "biaoge/parttime/parttime_newlist";
    }
   
}
