package com.zicms.web.datacenter.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zicms.common.base.ServiceMybatis;
import com.zicms.web.datacenter.mapper.ProvinceDictMapper;
import com.zicms.web.datacenter.model.ProvinceDict;


@Service("provinceDictService")
public class ProvinceDictService extends ServiceMybatis<ProvinceDict>{

	@Resource
	private ProvinceDictMapper provinceDictMapper;
	
	/**
	 * 首页-所有省份
	 * @param params
	 * @return
	 */
	public List<ProvinceDict> findProvinces() {
		return provinceDictMapper.findProvinces();
	}
	
}
