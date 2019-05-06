package com.zicms.web.tool.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zicms.common.base.ServiceMybatis;
import com.zicms.common.constant.Constant;
import com.zicms.common.utils.FileUtil;
import com.zicms.web.sys.service.SysConfigService;
import com.zicms.web.tool.mapper.AttachMapper;
import com.zicms.web.tool.mapper.DocMapper;
import com.zicms.web.tool.model.Attach;
import com.zicms.web.tool.model.Doc;
import com.zicms.web.util.SysConfigKey;


@Service("docService")
@CacheConfig(cacheNames="doc_cache")
public class DocService extends ServiceMybatis<Doc>{

	@Resource
	private DocMapper docMapper;
	@Resource
	private SysConfigService sysConfigService;
	@Resource
	private AttachMapper attachMapper;
	
	@CacheEvict(allEntries=true)
	public int saveDoc(Doc d,HttpServletRequest request){
		String rootPath = sysConfigService.findByKey(SysConfigKey.FILE_SAVE_ROOT_PATH)+File.separator;
		int count = 0;
		if(StringUtils.isNoneBlank(d.getTempIds())){
			String[] ids = d.getTempIds().split(",");
			for(String id : ids){
				File file = new File(rootPath,id);
				if(!file.exists()){
					return 0;
				}
				File destFile = new File(rootPath,"doc/"+ Constant.COMMON_FILE +File.separator+d.getFolder()+File.separator+file.getName());
				try{
					if(!destFile.getParentFile().exists())
						destFile.getParentFile().mkdirs();
					file.renameTo(destFile);
				}catch (Exception e) {
					throw new RuntimeException("file copy error");
				}
				Doc doc = new Doc();
				doc.setPath(destFile.getAbsolutePath());
				doc.setSize(destFile.length());
				doc.setFolder(d.getFolder());
				doc.setType(Constant.COMMON_FILE);
				doc.setMd5(FileUtil.getFileMD5String(destFile));
				doc.setName(request.getSession().getAttribute(id).toString());
				request.getSession().removeAttribute(id);
				doc.setSuffix(FileUtil.getFileSuffix(doc.getName()));
				count = this.insertSelective(doc);
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
	public int deleteDoc(Long id){
		return this.updateDelFlagToDelStatusById(Doc.class, id);
	}
	
	public Integer deleteDoc(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteDoc(id);
		}
		return count;
	}
	
	/**
	 * 列表--分页
	 * @param params
	 * @return
	 */
	public PageInfo<Doc> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Doc> list = docMapper.findPageInfo(params);
		return new PageInfo<Doc>(list);
	}
	
	/**
	 * 列表--不分页
	 * @param params
	 * @return
	 */
	public List<Doc> findByParam(Map<String, Object> params) {
		return docMapper.findPageInfo(params);
	}

	@CacheEvict(allEntries=true)
	public void deleteDoc(Long id, String md5) {
		if(id == null || StringUtils.isBlank(md5)){
			return;
		}
		Doc doc = new Doc();
		doc.setId(id);
		doc.setMd5(md5);
		Doc d = this.selectByPrimaryKey(id);
		
		Attach a = new Attach();
		a.setDoc(id);
		
		File file = new File(d.getPath());
		if(file.exists()){
			file.delete();
		}
		this.delete(doc);
		
		attachMapper.delete(a);
	}


	//根据文件编号和md5查询
	public Doc findDoc(Long id, String md5) {
		if(id == null || StringUtils.isBlank(md5)){
			return null;
		}
		Doc doc = new Doc();
		doc.setId(id);
		doc.setMd5(md5);
		List<Doc> docs = this.select(doc);
		
		if(!docs.isEmpty()){
			return docs.get(0);
		}
		return null;
	}


	public void saveAttach(Attach a) {
		attachMapper.insertSelective(a);
	}

	public List<Doc> findAttachByNotice(Long id){
		return docMapper.findAttachByNotice(id);
	}


	//删除附件
	public void deleteAttach(Long id) {
		List<Doc> docs = findAttachByNotice(id);
		for(Doc d  : docs){
			File file = new File(d.getPath());
			if(file.exists()){
				file.delete();
			}
			this.delete(d);
			Attach a = new Attach();
			a.setDoc(d.getId());
			attachMapper.delete(a);
		}
	}

	@Cacheable(key="'doc_top_cahce'")
	public List<Doc> findNewDoc(Integer pageSize) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", Constant.COMMON_FILE);
		params.put("pageSize", pageSize);
		params.put("pageNum", 1);
		params.put("sortC", "create_date");
		params.put("order", "desc");
		PageHelper.startPage(params);
		List<Doc> list = docMapper.findPageInfo(params);
		return new PageInfo<Doc>(list).getList();
	}
	
}
