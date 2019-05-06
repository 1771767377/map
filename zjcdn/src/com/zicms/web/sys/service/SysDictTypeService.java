

package com.zicms.web.sys.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zicms.common.base.ServiceMybatis;
import com.zicms.common.constant.Constant;
import com.zicms.web.sys.mapper.SysDictMapper;
import com.zicms.web.sys.mapper.SysDictTypeMapper;
import com.zicms.web.sys.model.SysDict;
import com.zicms.web.sys.model.SysDictType;

/**
 * 
 * @author taosq
 */

@Service("sysDictTypeService")
@CacheConfig(cacheNames="sysDicType_cache")
public class SysDictTypeService extends ServiceMybatis<SysDictType>{

	@Resource
	private SysDictTypeMapper sysDictTypeMapper;
	
	@Resource
	private SysDictMapper sysDictMapper;
	
	
	/**
	 *新增or更新SysArea
	 */
	
	@CacheEvict(allEntries=true)
	public int saveDicType(SysDictType dictType){
		int count = 0;
		//新的parentIds
		dictType.setParentIds(dictType.getParentIds()+dictType.getParentId()+","); 
		if(null == dictType.getId()){
			count = this.insertSelective(dictType);
		}else{
			//getParentIds() 当前选择的父节点parentIds , getParentId()父节点的id
			//先更新parentId，此节点的parentIds以更新
			count = this.updateByPrimaryKeySelective(dictType); 
			//不移动节点不更新子节点的pids
			if(!StringUtils.equals(dictType.getOldParentIds(), dictType.getParentIds())){
				sysDictTypeMapper.updateParentIds(dictType); //批量更新子节点的parentIds
			}
		}
		return count;
	}
	
	/**
	 * 根据父id删除自身已经所有子节点
	* @param id
	* @return
	 */
	@CacheEvict(allEntries=true)	
	public int deleteDictTypeByRootId(Long id){
		//先删除相关字典 
		String  type = sysDictTypeMapper.selectByPrimaryKey(id).getCode();
		
		SysDict sd = new  SysDict();
		sd.setType(type);
		sd.setDelFlag(Constant.DEL_FLAG_DELETE);
		
		sysDictMapper.updateByPrimaryKeySelective(sd);
		
		return sysDictTypeMapper.deleteDictTypeByRootId(id);
	}
	
	
	
	/**
	 * 查询全部的区域
	* @return
	 */
	@Cacheable(key="'area_all'")
	public List<SysDictType> findAllDictType(){
		return this.select(new SysDictType());
	}

	public SysDictType selectDicTypeByCode(String type) {
		SysDictType st = new SysDictType();
		st.setCode(type);
		return sysDictTypeMapper.selectOne(st);
	}

	
}
