package com.zicms.web.sys.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.zicms.common.base.ServiceMybatis;
import com.zicms.web.sys.mapper.SysConfigMapper;
import com.zicms.web.sys.model.SysConfig;
import com.zicms.web.sys.utils.config.LoginConfig;
import com.zicms.web.util.SysConfigKey;

@Service("sysConfigService")
@CacheConfig(cacheNames="sysConfig_cache")
public class SysConfigService extends ServiceMybatis<SysConfig>{
	@Resource
	private SysConfigMapper sysConfigMapper;


	private int saveConfig(String label,String value) {
		int count = 0;
		SysConfig sc = new SysConfig();
		sc.setLabel(label);
		List<SysConfig> scs = this.select(sc);
		if(scs.size() == 0){
			sc.setValue(value);
			count = this.insertSelective(sc);
		}else{
			sc.setValue(value);
			sc.setId(scs.get(0).getId());
			count = this.updateByPrimaryKeySelective(sc);
		}
		return count;
	}


	@Cacheable(key="'allConfigMultimap'")
	public Multimap<String, SysConfig> findAllMultimap(){
		List<SysConfig> configs = this.select(new SysConfig());
		Multimap<String, SysConfig> multimap = ArrayListMultimap.create();
		for(SysConfig config : configs){
			multimap.put(config.getLabel(), config);
		}
		return multimap;
	}

	public String findByKey(String key){
		List<SysConfig> configs = (List<SysConfig>) findAllMultimap().get(key);
		if(!configs.isEmpty()){
			return configs.get(0).getValue();
		}
		return null;
	}

	//保存登录配置
	@CacheEvict(allEntries = true)
	public Integer saveLoginConfig(LoginConfig config) {
		int count = 0;
		count += saveConfig(SysConfigKey.LOGIN_CAPTCHA_ON,config.getLoginCaptchaOn());
		count += saveConfig(SysConfigKey.LOGIN_ERROR_COUNT,config.getLoginErrorCount());
		count += saveConfig(SysConfigKey.LOGIN_UNLOCK_TIME,config.getLoginUnlockTime());
		count += saveConfig(SysConfigKey.PWD_FIRST_LOGIN_MOD,config.getPwdFristLoginMod());
		count += saveConfig(SysConfigKey.PWD_NEXT_MOD_TIME,config.getPwdNextModTime());
		count += saveConfig(SysConfigKey.PWD_STRONG_VERIFCATION,config.getPwdStrongVerifcation());
		return count;
	}



	@CacheEvict(allEntries = true)
	public Integer saveAttachConfig(String path, String size) {
		int count = 0;
		count += saveConfig(SysConfigKey.ATTACH_SIZE_LIMIT,size);
		count += saveConfig(SysConfigKey.FILE_SAVE_ROOT_PATH,path);
		return count;
	}

	/**
	 * 添加或更新前清除系统缓存
	 *
	 * @param rule
	 * @param no
	 * @param way
	 * @param status
	 * @return
	 */
	@CacheEvict(allEntries = true)
	public int saveClassesNamingRule(String rule, String no, String way,
									 String status) {
		int count = 0;
		count += this.saveConfig(SysConfigKey.CLASS_NAME_RULE, rule);
		count += this.saveConfig(SysConfigKey.CLASS_NAME_RULE_START_NO, no);
		count += this.saveConfig(SysConfigKey.CLASS_NAME_RULE_WAY, way);
		count += this.saveConfig(SysConfigKey.CLASS_NAME_RULE_STATUS, status);
		return count;
	}
	
}
