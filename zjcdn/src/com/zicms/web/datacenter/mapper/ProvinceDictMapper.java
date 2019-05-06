package com.zicms.web.datacenter.mapper;

import java.util.List;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.datacenter.model.ProvinceDict;


/**
* @author lvzhiyong
*/
public interface ProvinceDictMapper extends Mapper<ProvinceDict>{

	public List<ProvinceDict> findProvinces();

}
