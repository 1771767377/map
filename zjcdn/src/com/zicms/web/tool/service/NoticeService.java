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
import com.zicms.web.tool.mapper.NoticeMapper;
import com.zicms.web.tool.model.Attach;
import com.zicms.web.tool.model.Doc;
import com.zicms.web.tool.model.Notice;
import com.zicms.web.util.SysConfigKey;


@Service("noticeService")
@CacheConfig(cacheNames="notice_cache")
public class NoticeService extends ServiceMybatis<Notice>{

	@Resource
	private NoticeMapper noticeMapper;
	
	@Resource
	private DocService docService;
	
	
	@Resource 
	private SysConfigService sysConfigService;
	
	
	/**
	 * 保存公告通知
	 * @param notice
	 * @param request
	 * @return
	 */
	@CacheEvict(allEntries=true)
	public int saveNotice(Notice notice,HttpServletRequest request){
		int count = 0;
		String rootPath = sysConfigService.findByKey(SysConfigKey.FILE_SAVE_ROOT_PATH)+File.separator;
		if(notice.getId() == null){
			count = this.insertSelective(notice);
		}else{
			count = this.updateByPrimaryKeySelective(notice);
		}
		
		if(StringUtils.isNoneBlank(notice.getTempIds())){
			String[] ids = notice.getTempIds().split(",");
			for(String id : ids){
				File file = new File(rootPath,id);
				if(!file.exists()){
					continue;
				}
				File destFile = new File(rootPath,"doc/"+Constant.ATTACH_FILE + File.separator +notice.getId()+File.separator+file.getName());
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
				doc.setType(Constant.ATTACH_FILE);
				doc.setMd5(FileUtil.getFileMD5String(destFile));
				doc.setName(request.getSession().getAttribute(id).toString());
				request.getSession().removeAttribute(id);
				doc.setSuffix(FileUtil.getFileSuffix(doc.getName()));
				count += docService.insertSelective(doc);
				
				//添加到附件表
				Attach a = new Attach();
				a.setArticle(notice.getId());
				a.setDoc(doc.getId());
				docService.saveAttach(a);
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
	public int deleteNotice(Long id){
		Notice n = new Notice();
		n.setId(id);
		docService.deleteAttach(id);
		return this.delete(n);
	}
	
	public Integer deleteNotice(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteNotice(id);
		}
		return count;
	}
	
	/**
	 * 列表--分页
	 * @param params
	 * @return
	 */
	public PageInfo<Notice> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Notice> list = noticeMapper.findPageInfo(params);
		return new PageInfo<Notice>(list);
	}
	
	/**
	 * 列表--不分页
	 * @param params
	 * @return
	 */
	public List<Notice> findByParam(Map<String, Object> params) {
		return noticeMapper.findPageInfo(params);
	}


	@Cacheable(key="#notice_type")
	public List<Notice> findNewNotice(Integer pageSize,String notice_type) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", notice_type);
		params.put("pageSize", pageSize);
		params.put("pageNum", 1);
		params.put("sortC", "create_date");
		params.put("order", "desc");
		PageHelper.startPage(params);
		List<Notice> list = noticeMapper.findPageInfo(params);
		PageInfo<Notice> p = new PageInfo<Notice>(list);
		return p.getList();
	}

	
}
