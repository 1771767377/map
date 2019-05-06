package com.zicms.web.tool.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zicms.common.excel.ExcelUtils;
import com.zicms.common.poi.EasyXls;
import com.zicms.common.poi.bean.Column;
import com.zicms.common.poi.bean.ExcelConfig;
import com.zicms.common.utils.FileUtil;
import com.zicms.common.utils.StringConvert;
import com.zicms.web.sys.model.SysOffice;
import com.zicms.web.sys.service.SysDictService;
import com.zicms.web.tool.model.Folder;
import com.zicms.web.tool.service.FolderService;
import com.zicms.web.util.ResponseUtils;


@Controller
@RequestMapping("tool/folder")
public class FolderController {
	@Resource
	private FolderService folderService;
	
	@Resource
	private SysDictService sysDictService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toFolder(Model model){
		model.addAttribute("treeList", JSON.toJSONString(folderService.findAllFolder()));
		return "tool/folder/index";
	}
	
	
	@RequestMapping(value="tree",method = RequestMethod.POST)
	public @ResponseBody List<Folder> getOfficeTreeList(@ModelAttribute SysOffice sysOffice){
		return folderService.findAllFolder();
	} 
	
	/**
	 * 分页显示
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,HttpServletResponse response){
		if(params.containsKey("sortC")){
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<Folder> page = folderService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Folder folder){
		return folderService.saveFolder(folder);
	}
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return folderService.deleteFolder(id);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return folderService.deleteFolder(ids);
	}
	
	
	@RequestMapping(value = "{mode}/showlayer", method = RequestMethod.POST)
	public String showLayer(Long id, Long parentId,
			@PathVariable("mode") String mode, Model model) {
		Folder folder = null, pFolder = null;
		if (StringUtils.equalsIgnoreCase(mode, "add")) {
			pFolder = folderService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "edit")) {
			folder = folderService.selectByPrimaryKey(id);
			pFolder = folderService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "detail")) {
			folder = folderService.selectByPrimaryKey(id);
			pFolder = folderService.selectByPrimaryKey(folder.getParentId());
		}
		model.addAttribute("pFolder", pFolder).addAttribute("folder", folder);
		return mode.equals("detail")?"tool/folder/detail":"tool/folder/save";
	}
	
	/**
	 * 导出execl数据
	 */
	@RequestMapping(value = "export",method = RequestMethod.POST)
	public void exportFile(@RequestParam Map<String, Object> params,Long type,
			HttpServletResponse response){
		List<Folder> datas = folderService.findByParam(params);
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		titleMap.put("名称","name");
		titleMap.put("父编号","parentId");
		titleMap.put("创建人","createBy");
		titleMap.put("创建时间","createDate");
		//解决数据词典的值
		if(!datas.isEmpty()){
			sysDictService.findAllMultimap();
			for(Folder r : datas){
			}
		}
		try {
			//流的方式直接下载
			ExcelUtils.exportExcel(response, "文件分类.xls", datas, titleMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * 导出execl模板
	 */
	@RequestMapping(value = "exportTemplate",method = RequestMethod.POST)
	public void exportTemplate(@RequestParam Map<String, Object> params,Long type,
			HttpServletResponse response){
		List<Folder> list = new ArrayList<Folder>();
		Folder folder = new Folder();
		list.add(folder);
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		try {
			//流的方式直接下载
			ExcelUtils.exportExcel(response, "文件分类模板.xls", list, titleMap);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * execl导入数据
	 * @throws IOException
	 */
	@RequestMapping(value = "import",method = RequestMethod.POST)
	public @ResponseBody void importFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Map<String,String> configs = new HashMap<String,String>();
		List<Column> cs = new ArrayList<Column>();
		
		ExcelConfig config = new ExcelConfig.Builder(Folder.class)
				.sheetNum(0)
				.startRow(1)
				.addColumn(cs).build();
		int count = 0;
		try {
			List<Map<String,Object>> list = (List<Map<String,Object>>) EasyXls.xls2List(config,FileUtil.uploadFile(request));
			for (Map<String,Object> r : list) {
				Folder folder = new Folder();
				for(Map.Entry<String, Object> entry : r.entrySet()){
					String dic = configs.get(entry.getKey());
					if(StringUtils.isNotBlank(dic) && entry.getValue() != null){//解决数据词典列
						folder.set(entry.getKey(), sysDictService.getValue(dic,entry.getValue().toString()));
					}else{
						folder.set(entry.getKey(),entry.getValue());
					}
				}
				count += folderService.saveFolder(folder);	
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("成功导入"+count + "条数据!");
		} catch (Exception e) {
			response.getWriter().write("导入失败");
			e.printStackTrace();
		}
	}
	
	
}
	