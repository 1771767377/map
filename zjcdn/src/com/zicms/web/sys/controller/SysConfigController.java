package com.zicms.web.sys.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.web.sys.service.SysConfigService;
import com.zicms.web.sys.utils.config.LoginConfig;


/**
 * 系统配置
 * @author taosq
 * 
 */
@Controller
@RequestMapping("sysconfig")
public class SysConfigController {
	
	@Resource
	private SysConfigService sysConfigService;
	
	@RequestMapping
	public String toSysConfig(){
		return "sys/config/config";
	}
	
	
	@RequestMapping("/login/save")
	public @ResponseBody Integer saveLoginConfig(@ModelAttribute LoginConfig config){
		
		if(config.getLoginCaptchaOn() == null){
			config.setLoginCaptchaOn("0");
		}
		if(config.getPwdStrongVerifcation() == null){
			config.setPwdStrongVerifcation("0");
		}
		if(config.getPwdFristLoginMod() == null){
			config.setPwdFristLoginMod("0");
		}
		
		return sysConfigService.saveLoginConfig(config);
	}
	
	@RequestMapping("/attach/save")
	public @ResponseBody Integer saveAttachConfig(String path,String size){
		return sysConfigService.saveAttachConfig(path,size);
	}
	
	
}
