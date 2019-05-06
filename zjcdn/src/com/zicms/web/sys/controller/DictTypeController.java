package com.zicms.web.sys.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.web.sys.model.SysDictType;
import com.zicms.web.sys.service.SysDictTypeService;

@Controller
@RequestMapping("dict/type")
public class DictTypeController {

	@Resource
	private SysDictTypeService sysDictTypeService;
	
	
	
	/**
	 * 分类树
	 * @return
	 */
	@RequestMapping(value = "tree", method = RequestMethod.POST)
	public @ResponseBody List<SysDictType> getAreaTreeList() {
		List<SysDictType> list = sysDictTypeService.findAllDictType();
		return list;
	}
	
	/**
	 * 添加或更新区域
	 * @param params
	 * @return
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysDictType dictType) {
		return sysDictTypeService.saveDicType(dictType);
	}
	
	/**
	 * 删除字典
	* @param id
	* @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return sysDictTypeService.deleteDictTypeByRootId(id);
	}
	
	
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String showLayer(Long id, Long parentId,
			@PathVariable("mode") String mode, Model model) {
		SysDictType type = null, pType = null;
		if (StringUtils.equalsIgnoreCase(mode, "add")) {
			pType = sysDictTypeService.selectByPrimaryKey(parentId);
		} else if (StringUtils.equalsIgnoreCase(mode, "edit")) {
			type = sysDictTypeService.selectByPrimaryKey(id);
			pType = sysDictTypeService.selectByPrimaryKey(parentId);
		} 
		model.addAttribute("pType", pType).addAttribute("type", type);
		return "sys/dict/dict-type-save";
	}
	
	
	/**
	 * 验证用户名是否存在
	* @param param
	* @return
	 */
	@RequestMapping(value="checkcode",method=RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkCode(String param){
		Map<String, Object> msg = new HashMap<String, Object>();
		SysDictType st = new SysDictType();
		st.setCode(param);
		int count = sysDictTypeService.selectCount(st);
		if(count>0){
			msg.put("info", "标识符已经存在!");
			msg.put("status", "n");
		}else{
			msg.put("status", "y");
		}
		return msg;
	}
	
}
