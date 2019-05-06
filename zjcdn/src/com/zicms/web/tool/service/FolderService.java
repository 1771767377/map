package com.zicms.web.tool.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zicms.common.base.ServiceMybatis;

import com.zicms.web.tool.model.Doc;
import com.zicms.web.tool.model.Folder;
import com.zicms.web.tool.mapper.FolderMapper;


@Service("folderService")
@CacheConfig(cacheNames="folder_cache")
public class FolderService extends ServiceMybatis<Folder>{

	@Resource
	private FolderMapper folderMapper;
	
	@CacheEvict(allEntries=true)
	public int saveFolder(Folder folder){
		int count = 0;
		// 新的parentIds
		folder.setParentIds(folder.getParentIds()
				+ folder.getParentId() + ",");
		if(folder.getId() == null){
			count = this.insertSelective(folder);
		}else{
			count = this.updateByPrimaryKeySelective(folder);
			// 不移动节点不更新子节点的pids
			if (!StringUtils.equals(folder.getOldParentIds(),
					folder.getParentIds())) {
				folderMapper.updateParentIds(folder); // 批量更新子节点的parentIds
			}
		}
		return count;
	}
	
	/**
	* 删除
	* @param id
	* @return
	 */
	@CacheEvict(allEntries=true)
	public int deleteFolder(Long id){
		int count = this.beforeDeleteTreeStructure(id, "folder", Doc.class);
		return count == -1 ? -1 : folderMapper.deleteIdsByRootId(id);
	}
	
	@CacheEvict(allEntries=true)
	public Integer deleteFolder(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteFolder(id);
		}
		return count;
	}
	
	/**
	 * 列表--分页
	 * @param params
	 * @return
	 */
	public PageInfo<Folder> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Folder> list = folderMapper.findFolderList(params);
		return new PageInfo<Folder>(list);
	}
	
	/**
	 * 列表--不分页
	 * @param params
	 * @return
	 */
	public List<Folder> findByParam(Map<String, Object> params) {
		return folderMapper.findFolderList(params);
	}

	@Cacheable(key="'folder_all'")
	public List<Folder> findAllFolder(){
		return this.select(new Folder());
	}
	
	
}
