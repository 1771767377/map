package com.zicms.web.datacenter.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.common.constant.Constant;
import com.zicms.web.datacenter.service.ProvinceDictService;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.service.SysUserService;
import com.zicms.web.sys.utils.SysUserUtils;

@Controller
@RequestMapping("proDict")
public class ProvinceDictController {

	@Resource
	private ProvinceDictService provinceDictService;
    @Resource
    private SysUserService sysUserService;


    /**
     * 接收首页传送过来的省份数据
     * 
     * @param params
     * @param 
     * @param model
     * @return json数据
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public String getProvincelist(Model model,HttpServletRequest request,HttpServletResponse response) {
        //处理后台得到的数据
    	String data = request.getParameter("data");
    	String replace = StringUtils.replace(data, "\"","");
    	//将数据插入sys_user表里
    /*	SysUser user=new SysUser();
    	String username = SysUserUtils.getSessionLoginUser().getUsername();*/
    	SysUser user = SysUserUtils.getSessionLoginUser();
    	//user.set("username", username);
    	user.set("province", replace);
    	sysUserService.updateProvinceByAccount(user);
    	//重新缓存用户信息
    	request.getSession().setAttribute(Constant.SESSION_LOGIN_USER, user);
        SysUserUtils.cacheLoginUser(user);
    	//返回json回应
    	PrintWriter out=null;
    	Map<String,String>  map=new HashMap<String,String>();
    	 try {
			out = response.getWriter();
			map.put("message","选择成功");
			JSONObject jsonObject = JSONObject.fromObject(map); //将实体对象转换为JSON Object转换  
			out.print(jsonObject.toString());
			out.flush();
			out.close();
    	 } catch (IOException e) {
			e.printStackTrace();
		}
		return "";
    }
    
    /**
     * 轮播效果
     * 
     * @param params
     * @param 
     * @param model
     * @return json数据
     */
    @RequestMapping(value = "provinceCarousel", method = RequestMethod.POST)
    public String provinceCarousel(Model model,HttpServletRequest request,HttpServletResponse response) {
    	//将数据插入sys_user表里
    	String username = SysUserUtils.getSessionLoginUser().getUsername();
    	//省份转化
    	String provinceCarousel=sysUserService.selectZoneNamesByAccount(username);
    	//返回json回应
    	PrintWriter out=null;
    	Map<String,String>  map=new HashMap<String,String>();
    	try {
    		out = response.getWriter();
    		map.put("provinceCarousel",provinceCarousel);
    		JSONObject jsonObject = JSONObject.fromObject(map); //将实体对象转换为JSON Object转换  
    		out.print(jsonObject.toString());
    		out.flush();
    		out.close();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    	return "";
    }
    
    /**
     * 获取当前账号的省份
     * @return
     */
    @RequestMapping(value="getProvince", method = RequestMethod.POST)
    @ResponseBody
    public String getProvince(){
    	String username = SysUserUtils.getSessionLoginUser().getUsername();
    	String province = sysUserService.getProvinceByAccount(username);
    	return province;
    }
    
}
