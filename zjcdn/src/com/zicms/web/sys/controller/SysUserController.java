package com.zicms.web.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zicms.common.constant.Constant;
import com.zicms.web.sys.controller.command.SysInfoCommand;
import com.zicms.web.sys.model.SysRole;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.service.SysRoleService;
import com.zicms.web.sys.service.SysUserService;

@Controller
@RequestMapping("sysuser")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysRoleService sysRoleService;

    /**
     * 跳转到用户管理
     * 
     * @return
     */
    @RequestMapping
    public String toSysUser(Model model) {
        model.addAttribute("userSource", Constant.SYSTEM_USER_SOURCE_BY_UNTEACHER_OR_UNSTUDENT);
        return "sys/user/user";
    }

    /**
     * 保存用户
     * 
     * @return
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public @ResponseBody
    Integer save(@ModelAttribute SysUser sysUser, HttpServletRequest request) {
        return sysUserService.saveSysUser(sysUser);
    }

    /**
     * 删除用户
     * 
     * @param id 用户id
     * @return
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public @ResponseBody Integer del(Long id) {
        return sysUserService.deleteUser(id);
    }

    /**
     * 用户列表
     * 
     * @param params
     * @param model
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String list(@RequestParam Map<String, Object> params, Long[] roles, Model model) {
        params.put("roles", StringUtils.join(roles, ','));
        PageInfo<SysUser> page = sysUserService.findPageInfo(params);
        model.addAttribute("page", page);
        return "sys/user/user-list";
    }

    /**
     * 弹窗
     * 
     * @param id 用户id
     * @param mode 模式
     * @return
     */
    @RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
    public String showLayer(Long id, @PathVariable("mode") String mode, Model model) {
        SysUser user = sysUserService.selectByPrimaryKey(id);
        List<SysRole> roles = null;
        Map<Long, SysRole> rolesMap = Maps.newHashMap(), findUserRoleMap = null;
        if (StringUtils.equals("detail", mode)) {
            roles = sysRoleService.findUserRoleListByUserId(id);
            model.addAttribute("roles", roles);
        }
        if (StringUtils.equals("edit", mode)) {
            findUserRoleMap = sysRoleService.findUserRoleMapByUserId(id);
            rolesMap.putAll(sysRoleService.findCurUserRoleMap());
            rolesMap.putAll(findUserRoleMap);
            model.addAttribute("rolesMap", rolesMap).addAttribute("findUserRoleMap", findUserRoleMap);
        }
        model.addAttribute("user", user);
        return mode.equals("detail") ? "sys/user/user-detail" : "sys/user/user-save";
    }

    /**
     * 验证用户名是否存在
     * 
     * @param param
     * @return
     */
    @RequestMapping(value = "checkname", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> checkName(String param) {
        Map<String, Object> msg = new HashMap<String, Object>();
        SysUser sysUser = new SysUser();
        sysUser.setUsername(param);
        int count = sysUserService.selectCount(sysUser);
        if (count > 0) {
            msg.put("info", "此登录名太抢手了,请换一个!");
            msg.put("status", "n");
        } else {
            msg.put("status", "y");
        }
        return msg;
    }

    @ResponseBody
    @RequestMapping(value = "ajaxFindDutyBymanager", method = RequestMethod.POST)
    public Map<String, Object> ajaxFindDutyBymanager(HttpServletRequest request, Model model) {
        String name = request.getParameter("apply_manager");
        // 获取负责人
        List<SysInfoCommand> infoCommand = sysUserService.selectAppByName(name);
        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("apply_manager", infoCommand);
        return resultMap;
    }

}
