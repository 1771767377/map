package com.zicms.web.sys.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.web.sys.service.SysConfigService;
import com.zicms.web.util.SysConfigKey;

@Controller
@RequestMapping("classesnamenamingrule")
public class ClassesNamingController {
	
	@Resource
	private SysConfigService sysConfigService;

	@RequestMapping
	public String toClassesNameRule(Model model) {
		model.addAttribute("rule", sysConfigService.findByKey(SysConfigKey.CLASS_NAME_RULE));
		model.addAttribute("no", sysConfigService.findByKey(SysConfigKey.CLASS_NAME_RULE_START_NO));
		model.addAttribute("way", sysConfigService.findByKey(SysConfigKey.CLASS_NAME_RULE_WAY));
		model.addAttribute("status", sysConfigService.findByKey(SysConfigKey.CLASS_NAME_RULE_STATUS));
		return "sys/classes/classes-naming";
	}

	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(String rule, String no, String way, String status) {
		int count = 0;
		count = sysConfigService.saveClassesNamingRule(rule, no, way, status);
		return count;
	}
}
