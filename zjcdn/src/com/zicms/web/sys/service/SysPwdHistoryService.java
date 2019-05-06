package com.zicms.web.sys.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zicms.common.base.ServiceMybatis;
import com.zicms.web.sys.mapper.SysPwdHistoryMapper;
import com.zicms.web.sys.model.SysPwdHistory;

@Service
public class SysPwdHistoryService extends ServiceMybatis<SysPwdHistory> {

	@Resource
	private SysPwdHistoryMapper sysPwdHistoryMapper;
	
	/**
	 * 保存历史密码
	 * @param loginname
	 * @param pwd   MD5加密后
	 * @return
	 */
	public int save(String username,String pwd){
		SysPwdHistory sph = new SysPwdHistory();
		sph.setUsername(username);
		sph.setPassword(pwd);
		sph.setCreateDate(new Date());
		return sysPwdHistoryMapper.insertSelective(sph);
	}
	
	/**
	 * 检验是否包含了该密码
	 * @param loginname
	 * @param pwd   加密后
	 * @return  包含了返回true,否则返回false
	 */
	public boolean isContainer(String loginname,String pwd){
		SysPwdHistory sph = new SysPwdHistory();
		sph.setUsername(loginname);
		sph.setPassword(pwd);
		return sysPwdHistoryMapper.selectCount(sph) > 0;
	}
	
}
