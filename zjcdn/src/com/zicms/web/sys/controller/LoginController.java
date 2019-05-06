package com.zicms.web.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.common.constant.Constant;
import com.zicms.common.poi.common.DateUtil;
import com.zicms.common.utils.IPUtils;
import com.zicms.common.utils.PasswordEncoder;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.service.SysConfigService;
import com.zicms.web.sys.service.SysResourceService;
import com.zicms.web.sys.service.SysUserService;
import com.zicms.web.sys.utils.SysUserUtils;
import com.zicms.web.tool.service.DocService;
import com.zicms.web.tool.service.NoticeService;
import com.zicms.web.util.SysConfigKey;

/**
 * @author Administrator
 * 
 */
@Controller
public class LoginController {

    @Resource
    private SysResourceService sysResourceService;

    @Resource
    private SysConfigService sysConfigService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private DocService docService;

    @Resource
    private NoticeService noticeService;

    /**
     * 管理主页
     * 
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/")
    public String toIndex(Model model, HttpServletRequest request) {
        request.getSession().removeAttribute("code"); // 清除code

        SysUser user = SysUserUtils.getSessionLoginUser();
        if (user == null || SysUserUtils.getCacheLoginUser() == null) {
            return "redirect:/login";
        }
        model.addAttribute("menuList", SysUserUtils.getUserMenus());

        // 判断是否是第一次登录
        if ("1".equals(sysConfigService.findByKey(SysConfigKey.PWD_FIRST_LOGIN_MOD))) {
            if (user.getLastLoginDate() == null) {
                model.addAttribute("msg", "第一次登录，请修改密码");
                return "sys/user/mod-pwd";
            }
        }

        if (!"0".equals(sysConfigService.findByKey(SysConfigKey.PWD_NEXT_MOD_TIME))) {
            if (user.getNextModPwdDate() != null && user.getNextModPwdDate().getTime() < System.currentTimeMillis()) {
                model.addAttribute("msg", "您长时间没有修改密码,请修改密码");
                return "sys/user/mod-pwd";
            }
        }

        return "index";
    }

    /**
     * 跳转到登录页面
     * 
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String toLogin() {
        if (SysUserUtils.getSessionLoginUser() != null && SysUserUtils.getCacheLoginUser() != null) {
            return "redirect:/";
        }
        return "login";
    }

    /**
     * 登录验证
     * 
     * @param username
     * @param password
     * @param code
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> checkLogin(String username, String password, String code, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> msg = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        code = StringUtils.trim(code);
        username = StringUtils.trim(username);
        password = StringUtils.trim(password);

        String flag = request.getParameter("flag");  
        //set cookie  
        if(flag!=null && flag.equals("1")){  
            Cookie cookie = new Cookie("cookie_user", username+"-"+password);                  
            cookie.setMaxAge(60*60*24*30); //cookie 保存30天  
            response.addCookie(cookie);  
        }else{    
            Cookie cookie = new Cookie("cookie_user",username+"-"+null);                  
            cookie.setMaxAge(60*60*24*30); //cookie 保存30天  
            response.addCookie(cookie);               
        } 
        
        // 是否启用验证码
        if ("1".equals(sysConfigService.findByKey(SysConfigKey.LOGIN_CAPTCHA_ON))) {
            Object scode = session.getAttribute("code");
            if (!StringUtils.equalsIgnoreCase(code, scode.toString())) {
                msg.put("error", "验证码错误");
                return msg;
            }
        }

        if (username == null || password == null) {
            msg.put("error", "非法操作");
            return msg;
        }

        // 判断用户的状态
        SysUser user = sysUserService.findUserByUsername(username);
        if (user == null) {
            msg.put("error", "用户不存在");
            return msg;
        }

        // 无次数限制登录
        int errorLimit = Integer.valueOf(sysConfigService.findByKey(SysConfigKey.LOGIN_ERROR_COUNT));
        if (errorLimit == 0) {
            return loginNoLimit(user, username, password, code, request);
        }

        // 锁定时间
        int lockTime = Integer.valueOf(sysConfigService.findByKey(SysConfigKey.LOGIN_UNLOCK_TIME));

        // 再判断错误的时间
        if (user.getStatus() != null && user.getStatus() == Constant.USER_STATUS_LOCK) {
            // 时间没过
            if (user.getNextLoginDate() != null && user.getNextLoginDate().getTime() > System.currentTimeMillis()) {
                user.setLastLoginErrorMsg("账号被锁定");
                msg.put("error", "您的账号已被锁定，请您在：" + DateUtil.formatDate(user.getNextLoginDate(), DateUtil.Y_M_D_HMS) + "  之后登陆");
                return msg;
            } else {
                user.setErrorCount(0);
                user.setNextLoginDate(null);
                user.setStatus(Constant.USER_STATUS_NORMAL);
            }
        }

        String secPwd = PasswordEncoder.encrypt(password, username);// 加密后的密码
        if (secPwd.equals(user.getPassword())) {
            // 更新用户最后登录ip和date
            if (user.getLastLoginDate() != null || "0".equals(sysConfigService.findByKey(SysConfigKey.PWD_FIRST_LOGIN_MOD))) {// 第一次登录不能修改其值，必须等于修改密码完成后方能修改
                user.setLastLoginDate(user.getLoginDate());
                user.setLastLoginIp(user.getLoginIp());
            }

            // 判断是否需要强加修改密码
            int ntime = Integer.parseInt(sysConfigService.findByKey(SysConfigKey.PWD_NEXT_MOD_TIME));
            if (user.getNextModPwdDate() == null && ntime > 0) {
                user.setNextModPwdDate(DateUtils.addDays(new Date(), ntime));
            }

            user.setLoginDate(new Date());
            user.setLoginIp(IPUtils.getClientAddress(request));
            user.setId(user.getId());
            user.setErrorCount(0);
            user.setStatus(Constant.USER_STATUS_NORMAL);
            user.setNextLoginDate(null);
            sysUserService.updateByPrimaryKey(user);

            // 缓存用户
            session.setAttribute(Constant.SESSION_LOGIN_USER, user);
            SysUserUtils.cacheLoginUser(user);
            // 设置并缓存用户认证
            SysUserUtils.setUserAuth();
            
            //调用切换数据源方法
            
            

        } else {
            // 先累加次数
            int errorCount = user.getErrorCount() == null ? 1 : user.getErrorCount() + 1;
            if (errorCount == errorLimit) {
                user.setStatus(Constant.USER_STATUS_LOCK);
                user.setNextLoginDate(DateUtils.addMinutes(new Date(), lockTime));
                user.setErrorCount(errorCount);

                // 发消息

                msg.put("error", "密码错误，您的账号已被锁定，请您在：" + DateUtil.formatDate(user.getNextLoginDate(), DateUtil.Y_M_D_HMS) + "  之后登陆");
            } else {
                user.setErrorCount(errorCount);
                user.setLastLoginErrorMsg("密码错误");
                msg.put("error", "密码错误，您还有" + (errorLimit - errorCount) + "次机会");
                msg.put("count", (errorLimit - errorCount));
                // 记日志
            }
            sysUserService.updateByPrimaryKey(user);
        }
        return msg;
    }

    private Map<String, Object> loginNoLimit(SysUser user, String username, String password, String code, HttpServletRequest request) throws Exception {

        String secPwd = PasswordEncoder.encrypt(password, username);// 加密后的密码

        Map<String, Object> msg = new HashMap<String, Object>();
        HttpSession session = request.getSession();

        if (secPwd.equals(user.getPassword())) {
            // 判断是否需要强加修改密码
            int ntime = Integer.parseInt(sysConfigService.findByKey(SysConfigKey.PWD_NEXT_MOD_TIME));
            if (user.getNextModPwdDate() == null && ntime > 0) {
                user.setNextModPwdDate(DateUtils.addDays(new Date(), ntime));
            }

            user.setLastLoginDate(user.getLoginDate());
            user.setLastLoginIp(user.getLoginIp());
            user.setLoginDate(new Date());
            user.setLoginIp(IPUtils.getClientAddress(request));
            user.setId(user.getId());
            user.setErrorCount(0);
            user.setStatus(Constant.USER_STATUS_NORMAL);
            user.setNextLoginDate(null);
            sysUserService.updateByPrimaryKey(user);

            // 缓存用户
            session.setAttribute(Constant.SESSION_LOGIN_USER, user);
            SysUserUtils.cacheLoginUser(user);

            // 设置并缓存用户认证
            SysUserUtils.setUserAuth();
        } else {
            msg.put("error", "密码错误");
        }
        return msg;
    }

    /**
     * 用户退出
     * 
     * @return 跳转到登录页面
     */
    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        SysUserUtils.clearCacheUser(SysUserUtils.getSessionLoginUser().getId());
        request.getSession().invalidate();
        return "redirect:/login";
    }

    @RequestMapping("notauth")
    public String notAuth() {
        return "notauth";
    }

    @RequestMapping("notlogin")
    public String notLogin() {
        return "notlogin";
    }

    @RequestMapping("/App")
    public String toAppLogin() {
        return "/app/login";
    }

    @RequestMapping(value = "applogin", method = RequestMethod.GET)
    public void AppLogin(HttpServletRequest request, HttpServletResponse res) throws Exception {
        Map<String, Object> msg = new HashMap<String, Object>();
        HttpSession session = request.getSession();
        String callbackFunName = request.getParameter("callbackparam");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        // code = StringUtils.trim(code);
        username = StringUtils.trim(username);
        password = StringUtils.trim(password);

        if (username == null || password == null) {
            msg.put("error", "非法操作");
            res.getWriter().write(callbackFunName + "({ name:\"非法操作\"})"); // 返回jsonp数据
            // return new JSONPObject(callbackparam, msg);
        }

        // 判断用户的状态
        SysUser user = sysUserService.findUserByUsername(username);
        if (user == null) {
            msg.put("error", "用户不存在");
            res.getWriter().write(callbackFunName + "({ name:\"用户不存在\"})"); // 返回jsonp数据
            // return new JSONPObject(callbackparam, msg);
        }

        // 无次数限制登录
        int errorLimit = Integer.valueOf(sysConfigService.findByKey(SysConfigKey.LOGIN_ERROR_COUNT));
        if (errorLimit == 0) {
            msg = loginNoLimit(user, username, password, "", request);
            String message = "";
            for (Object se : msg.keySet()) {
                message = (String) msg.get(se);
            }
            res.getWriter().write(callbackFunName + "({ name:\"" + message + "\"})"); // 返回jsonp数据
            // return new JSONPObject(callbackparam,msg);
        }

        // 锁定时间
        int lockTime = Integer.valueOf(sysConfigService.findByKey(SysConfigKey.LOGIN_UNLOCK_TIME));

        // 再判断错误的时间
        if (user.getStatus() != null && user.getStatus() == Constant.USER_STATUS_LOCK) {
            // 时间没过
            if (user.getNextLoginDate() != null && user.getNextLoginDate().getTime() > System.currentTimeMillis()) {
                user.setLastLoginErrorMsg("账号被锁定");
                msg.put("name", "您的账号已被锁定，请您在：" + DateUtil.formatDate(user.getNextLoginDate(), DateUtil.Y_M_D_HMS) + "  之后登陆");
                res.getWriter().write(callbackFunName + "({ name:\"您的账号已被锁定，请您在：" + DateUtil.formatDate(user.getNextLoginDate(), DateUtil.Y_M_D_HMS) + "之后登陆\"})"); // 返回jsonp数据
                // return new JSONPObject(callbackparam, msg);
            } else {
                user.setErrorCount(0);
                user.setNextLoginDate(null);
                user.setStatus(Constant.USER_STATUS_NORMAL);
            }
        }

        String secPwd = PasswordEncoder.encrypt(password, username);// 加密后的密码
        if (secPwd.equals(user.getPassword())) {
            // 更新用户最后登录ip和date
            if (user.getLastLoginDate() != null || "0".equals(sysConfigService.findByKey(SysConfigKey.PWD_FIRST_LOGIN_MOD))) {// 第一次登录不能修改其值，必须等于修改密码完成后方能修改
                user.setLastLoginDate(user.getLoginDate());
                user.setLastLoginIp(user.getLoginIp());
            }

            // 判断是否需要强加修改密码
            int ntime = Integer.parseInt(sysConfigService.findByKey(SysConfigKey.PWD_NEXT_MOD_TIME));
            if (user.getNextModPwdDate() == null && ntime > 0) {
                user.setNextModPwdDate(DateUtils.addDays(new Date(), ntime));
            }

            user.setLoginDate(new Date());
            user.setLoginIp(IPUtils.getClientAddress(request));
            user.setId(user.getId());
            user.setErrorCount(0);
            user.setStatus(Constant.USER_STATUS_NORMAL);
            user.setNextLoginDate(null);
            sysUserService.updateByPrimaryKey(user);

            // 缓存用户
            session.setAttribute(Constant.SESSION_LOGIN_USER, user);
            SysUserUtils.cacheLoginUser(user);
            // 设置并缓存用户认证
            SysUserUtils.setUserAuth();

        } else {
            // 先累加次数
            int errorCount = user.getErrorCount() == null ? 1 : user.getErrorCount() + 1;
            if (errorCount == errorLimit) {
                user.setStatus(Constant.USER_STATUS_LOCK);
                user.setNextLoginDate(DateUtils.addMinutes(new Date(), lockTime));
                user.setErrorCount(errorCount);
                msg.put("name", "密码错误，您的账号已被锁定，请您在：" + DateUtil.formatDate(user.getNextLoginDate(), DateUtil.Y_M_D_HMS) + "  之后登陆");
            } else {
                user.setErrorCount(errorCount);
                user.setLastLoginErrorMsg("密码错误");
                msg.put("name", "密码错误，您还有" + (errorLimit - errorCount) + "次机会");
                msg.put("count", (errorLimit - errorCount));
                // 记日志
            }
            sysUserService.updateByPrimaryKey(user);
        }
        res.getWriter().write(callbackFunName + "({name:\"json\"})"); // 返回jsonp数据
        // return new JSONPObject(callbackparam, msg);
    }

    /**
     * 手机端跳转首页方法
     * 
     * @return
     */
    @RequestMapping("/toAppIndex")
    public String toAppIndex() {
        return "/app/index";
    }

}
