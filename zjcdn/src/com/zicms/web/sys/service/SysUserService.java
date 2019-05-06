

package com.zicms.web.sys.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zicms.common.base.ServiceMybatis;
import com.zicms.common.constant.Constant;
import com.zicms.common.utils.CacheUtils;
import com.zicms.common.utils.PasswordEncoder;
import com.zicms.web.sys.controller.command.SysInfoCommand;
import com.zicms.web.sys.mapper.SysOfficeMapper;
import com.zicms.web.sys.mapper.SysPwdHistoryMapper;
import com.zicms.web.sys.mapper.SysRoleMapper;
import com.zicms.web.sys.mapper.SysUserMapper;
import com.zicms.web.sys.model.SysOffice;
import com.zicms.web.sys.model.SysPwdHistory;
import com.zicms.web.sys.model.SysUser;
import com.zicms.web.sys.utils.SysUserUtils;
import com.zicms.web.util.SysConfigKey;

/**
 * 
 * @author 
 */

@Service("sysUserService")
public class SysUserService extends ServiceMybatis<SysUser>{

	@Resource
	private SysUserMapper sysUserMapper;
	
	@Resource
	private SysRoleMapper sysRoleMapper;
	
	@Resource
	private SysOfficeMapper sysOfficeMapper;
	
	@Resource
	private SysPwdHistoryMapper sysPwdHistoryMapper;
	
	@Resource
	private SysConfigService sysConfigService;
	
	/**
	 * 添加或更新用户
	* @param sysUser
	* @return
	 */
	public int saveSysUser(SysUser sysUser){
		int count = 0;
		SysOffice sysOffice = sysOfficeMapper.findOfficeCompanyIdByDepId(sysUser.getOfficeId());
		Long companyId = sysUser.getOfficeId();
		if(sysOffice != null){
			companyId = sysOffice.getId();
		}
		sysUser.setCompanyId(companyId);
		if(StringUtils.isNotBlank(sysUser.getPassword())){
			String encryptPwd = PasswordEncoder.encrypt(sysUser.getPassword(), sysUser.getUsername());
			sysUser.setPassword(encryptPwd);
		}else{
			sysUser.remove("password");
		}
		if(null == sysUser.getId()){
			sysUser.setStatus(Constant.USER_STATUS_NORMAL);
			count = this.insertSelective(sysUser);
		}else{
			sysRoleMapper.deleteUserRoleByUserId(sysUser.getId());
			count = this.updateByPrimaryKeySelective(sysUser);
			//清除缓存
			SysUserUtils.clearAllCachedAuthorizationInfo(Arrays.asList(sysUser.getId()));
			if(CacheUtils.isCacheByKey(Constant.CACHE_SYS_USER, sysUser.getId().toString())){
				String userType = this.selectByPrimaryKey(sysUser.getId()).getUserType();
				sysUser.setUserType(userType);
				SysUserUtils.cacheLoginUser(sysUser);
			}
		}
		if(sysUser.getRoleIds()!=null) sysRoleMapper.insertUserRoleByUserId(sysUser);
		return count;
	}
	
	/**
	 * 删除用户
	* @param userId
	* @return
	 */
	public int deleteUser(Long userId){
		sysRoleMapper.deleteUserRoleByUserId(userId);
		SysUserUtils.clearAllCachedAuthorizationInfo(Arrays.asList(userId));
		SysUserUtils.clearCacheUser(userId);
		return this.updateDelFlagToDelStatusById(SysUser.class, userId);
	}
	
	/**
	 * 用户列表
	* @param params
	* @return
	 */
	public PageInfo<SysUser> findPageInfo(Map<String, Object> params) {
		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
		params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
		PageHelper.startPage(params);
		List<SysUser> list = sysUserMapper.findPageInfo(params);
		return new PageInfo<SysUser>(list);
	}
	
	/**
	 * 验证用户
	* @param username 用户名
	* @param password 密码
	* @return user
	 */
	public SysUser checkUser(String username,String password){
		SysUser sysUser = new SysUser();
		String secPwd = PasswordEncoder.encrypt(password, username);
		sysUser.setUsername(username);
		sysUser.setPassword(secPwd);
		List<SysUser> users = this.select(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			return users.get(0);
		}
		return null;
	}

	public SysUser findUserByUsername(String username){
		SysUser sysUser = new SysUser();
		sysUser.setUsername(username);
		List<SysUser> users = this.select(sysUser);
		if(users != null && users.size() == 1 && users.get(0) != null) {
			return users.get(0);
		}
		return null;
	}

	public int updatePassword(SysUser user, String secPwd) {
		SysPwdHistory sph = new SysPwdHistory();
		sph.setUsername(user.getUsername());
		sph.setPassword(user.getPassword());
		sph.setCreateDate(new Date());
		int count = sysPwdHistoryMapper.insertSelective(sph);
		
		SysUser newUser = new SysUser();
		newUser.setId(user.getId());
		newUser.setUsername(user.getUsername());
		newUser.setPassword(secPwd);
		
		int ntime = Integer.parseInt(sysConfigService.findByKey(SysConfigKey.PWD_NEXT_MOD_TIME));
		if(ntime == 0){
			newUser.setNextModPwdDate(null);
		}else{
			newUser.setNextModPwdDate(org.apache.commons.lang3.time.DateUtils.addDays(new Date(), ntime));
		}
		
		
		/**
		 * 为了解决第一用户登录后，必须修改密码才能进入系统，而判断用户是否是第一次进入系统的
		 * 标准是看getLastLoginDate的值是否为空。
		 * 如果用户修改了密码，必须将值修改后才能进入系统
		 */
		if(user.getLastLoginDate() == null){
			newUser.setLastLoginDate(user.getLoginDate());
			newUser.setLastLoginIp(user.getLoginIp());
		}
		count += this.updateByPrimaryKeySelective(newUser);
		return count;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<SysInfoCommand> selectAppByName(String name) {
		return sysUserMapper.selectAppByName(name);
 	}
	/**将获取的省更新到用户数据库
	 * 
	 * @param name
	 * @return
	 */
	public Integer updateProvinceByAccount(SysUser user) {
		return sysUserMapper.updateProvinceByAccount( user);
	}
	/**查询所有普通审核账号，不包括管理员
	 * 
	 * @param name
	 * @return
	 */
	public List<SysUser> selectPublicAccount() {
		return sysUserMapper.selectPublicAccount();
	}
	/**查询所有普通审核账号以及基准值，用于基准值修改，不包括管理员
	 * 
	 * @param name
	 * @return
	 */
	public List<SysUser> selectPublicAccountAndBase() {
		return sysUserMapper.selectPublicAccountAndBase();
	}
	/**将修改后的基准值更新到用户数据库
	 * 
	 * @param name
	 * @return
	 */
	public Integer updateCheckStandardByAccount(SysUser user) {
		return sysUserMapper.updateCheckStandardByAccount(user);
	}
	/**查询所有普通审核账号以及基准值，用于基准值修改，不包括管理员
	 * 
	 * @param name
	 * @return
	 */
	public String selectZoneNamesByAccount(String username) {
		return sysUserMapper.selectZoneNamesByAccount(username);
	}

	public String getProvinceByAccount(String username) {
		return sysUserMapper.getProvinceByAccount(username);
	}
	
}
