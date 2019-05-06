/**
 * 
 */
package com.zicms.common.beetl.function;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.zicms.web.sys.model.SysConfig;
import com.zicms.web.sys.service.SysConfigService;

/**
 * @author chenxd
 * @date 09/18/2015
 */
@Component
public class SysConfigFunction {

	@Resource
	private SysConfigService sysConfigService;

	public String get(String key) {
		List<SysConfig> configs = (List<SysConfig>) sysConfigService.findAllMultimap().get(key);
		if(!configs.isEmpty()){
			return configs.get(0).getValue();
		}
		return "";
	}
}
