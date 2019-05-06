

package com.zicms.web.sys.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.sys.controller.command.SysInfoCommand;
import com.zicms.web.sys.model.SysUser;

/**
 * 
 * @author 
 */

public interface SysUserMapper extends Mapper<SysUser>{
	
	public List<SysUser> findPageInfo(Map<String, Object> params);

	public Long findSysUserIdByLoginName(String loginName);
	
	public List<SysInfoCommand> selectAppByName(String name);
	
	public Integer updateProvinceByAccount(SysUser user);
	
	public List<SysUser> selectPublicAccount();
	
	public List<SysUser> selectPublicAccountAndBase();
	
	public Integer updateCheckStandardByAccount(SysUser user);

	public String selectZoneNamesByAccount(String username);

	public String getProvinceByAccount(@Param("username") String username);
}
