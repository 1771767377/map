package com.zicms.web.sys.controller;


import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.zicms.web.sys.model.SysDict;
import com.zicms.web.sys.model.SysDictType;
import com.zicms.web.sys.service.SysDictService;
import com.zicms.web.sys.service.SysDictTypeService;

@Controller
@RequestMapping("dict")
public class DictController {

	@Resource
	private SysDictService sysDictService;
	
	@Resource
	private SysDictTypeService sysDictTypeService;
	
	@RequestMapping
	public String toDict(Model model){
		model.addAttribute("treeList", JSON.toJSONString(sysDictTypeService.findAllDictType()));
		return "sys/dict/dict";
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysDict sysDict) {
		return sysDictService.saveSysdict(sysDict);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(@ModelAttribute SysDict sysDict){
		return sysDictService.deleteSysDict(sysDict);
	}
	
	/**
	 * 分页显示字典table
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public String list(@RequestParam Map<String, Object> params, Model model) {
		PageInfo<SysDict> page = sysDictService.findPageInfo(params);
		model.addAttribute("page", page);
		return "sys/dict/dict-list";
	}
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Model model){
		SysDict dict = sysDictService.selectByPrimaryKey(id);
		if(dict != null){
			SysDictType pType = sysDictTypeService.selectDicTypeByCode(dict.getType());
			model.addAttribute("pType", pType);
		}
		
		model.addAttribute("dict", dict);
		return "sys/dict/dict-save";
	}
	
}
