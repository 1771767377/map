package com.zicms.web.sys.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zicms.common.base.ServiceMybatis;

import com.zicms.web.sys.model.SysIdCard;
import com.zicms.web.sys.mapper.SysIdCardMapper;


@Service("sysIdCardService")
public class SysIdCardService extends ServiceMybatis<SysIdCard>{

	@Resource
	private SysIdCardMapper sysIdCardMapper;
	
	public int saveSysIdCard(SysIdCard sysIdCard){
		int count = 0;
		if(sysIdCard.getId() == null){
			count = this.insertSelective(sysIdCard);
		}else{
			count = this.updateByPrimaryKeySelective(sysIdCard);
		}
		return count;
	}
	
	/**
	* 删除
	* @param id
	* @return
	 */
	public int deleteSysIdCard(Long id){
		return this.updateDelFlagToDelStatusById(SysIdCard.class, id);
	}
	
	public Integer deleteSysIdCard(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteSysIdCard(id);
		}
		return count;
	}
	
	/**
	 * 列表--分页
	 * @param params
	 * @return
	 */
	public PageInfo<SysIdCard> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<SysIdCard> list = sysIdCardMapper.findPageInfo(params);
		return new PageInfo<SysIdCard>(list);
	}
	
	/**
	 * 列表--不分页
	 * @param params
	 * @return
	 */
	public List<SysIdCard> findByParam(Map<String, Object> params) {
		return sysIdCardMapper.findPageInfo(params);
	}

	
}
