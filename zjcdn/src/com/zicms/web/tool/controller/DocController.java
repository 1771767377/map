package com.zicms.web.tool.controller;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.zicms.common.constant.Constant;
import com.zicms.common.utils.FileUtil;
import com.zicms.common.utils.StringConvert;
import com.zicms.web.sys.service.SysConfigService;
import com.zicms.web.tool.model.Doc;
import com.zicms.web.tool.service.DocService;
import com.zicms.web.tool.service.FolderService;
import com.zicms.web.util.ResponseUtils;
import com.zicms.web.util.Result;
import com.zicms.web.util.SysConfigKey;


@Controller
@RequestMapping("tool/doc")
public class DocController {
	@Resource
	private DocService docService;
	
	@Resource
	private SysConfigService sysConfigService;
	
	@Resource
	private FolderService folderService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toDoc(Model model){
		model.addAttribute("treeList", folderService.findAllFolder());
		return "tool/doc/index";
	}
	
	/**
	 * 分页显示
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,HttpServletResponse response){
		if(params.containsKey("sortC")){
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		params.put("type", Constant.COMMON_FILE);
		PageInfo<Doc> page = docService.findPageInfo(params);
		for(Doc d : page.getList()){
			d.set("sizeStr", FileUtil.getHumanSize(d.getSize()));
		}
		ResponseUtils.renderJson(response, page);
	}
	
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Doc doc,HttpServletRequest request){
		return docService.saveDoc(doc,request);
	}
	
	//删除临时文件
	@RequestMapping("del/temp")
	public void del(String id,
			HttpServletRequest request, HttpServletResponse response) {
		Result  r = null;
		if(StringUtils.isNoneBlank(id)){
			File f = new File(sysConfigService.findByKey(SysConfigKey.FILE_SAVE_ROOT_PATH), id);
			if(f.exists()){
				f.delete();
				request.getSession().removeAttribute(id);//清除session中存储的文件名
			}
			r = Result.successResult();
		}else{
			r = Result.errorResult();
		}
		ResponseUtils.renderText(response, r);
	}
		
	//删除已经存储在表的的文件
	@RequestMapping(value="del",method = RequestMethod.POST)
	public void del2(Long id,String md5,
			HttpServletRequest request, HttpServletResponse response) {
		Result  r = null;
		if(id != null){
			docService.deleteDoc(id,md5);
			r = Result.successResult();
		}else{
			r = Result.errorResult();
		}
		ResponseUtils.renderText(response, r);
	}
		
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return docService.deleteDoc(id);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return docService.deleteDoc(ids);
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		Doc doc = null;
		if(StringUtils.equals("edit", mode)){
			doc = docService.selectByPrimaryKey(id);
			model.addAttribute("doc", doc);
			model.addAttribute("folder", folderService.selectByPrimaryKey(doc.getFolder()));
		}else if(StringUtils.equals("detail", mode)){
			doc = docService.selectByPrimaryKey(id);
			doc.set("pname", folderService.selectByPrimaryKey(doc.getFolder()).getName());
			model.addAttribute("doc", doc);
		}
		return mode.equals("detail")?"tool/doc/detail":"tool/doc/save";
	}
	
	
	
	
	
}
	