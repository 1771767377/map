package com.zicms.web.sys.controller;

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

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zicms.common.excel.ExcelUtils;
import com.zicms.common.poi.EasyXls;
import com.zicms.common.poi.bean.Column;
import com.zicms.common.poi.bean.ExcelConfig;
import com.zicms.common.utils.FileUtil;
import com.zicms.common.utils.StringConvert;
import com.zicms.web.sys.model.SysIdCard;
import com.zicms.web.sys.service.SysDictService;
import com.zicms.web.sys.service.SysIdCardService;
import com.zicms.web.util.ResponseUtils;


@Controller
@RequestMapping("sys/card")
public class SysIdCardController {
	@Resource
	private SysIdCardService sysIdCardService;
	
	@Resource
	private SysDictService sysDictService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toSysIdCard(Model model){
		return "sys/card/index";
	}
	
	/**
	 * 分页显示
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,HttpServletResponse response){
		if(params.containsKey("sortC")){
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<SysIdCard> page = sysIdCardService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute SysIdCard sysIdCard){
		return sysIdCardService.saveSysIdCard(sysIdCard);
	}
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return sysIdCardService.deleteSysIdCard(id);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return sysIdCardService.deleteSysIdCard(ids);
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		SysIdCard sysIdCard = null;
		if(StringUtils.equals("edit", mode)){
			sysIdCard = sysIdCardService.selectByPrimaryKey(id);
			model.addAttribute("sysIdCard", sysIdCard);
		}else if(StringUtils.equals("detail", mode)){
			sysIdCard = sysIdCardService.selectByPrimaryKey(id);
			model.addAttribute("sysIdCard", sysIdCard);
		}
		return mode.equals("detail")?"sys/card/detail":"sys/card/save";
	}
	
	
	/**
	 * 导出execl数据
	 */
	@SuppressWarnings("unused")
	@RequestMapping(value = "export",method = RequestMethod.POST)
	public void exportFile(@RequestParam Map<String, Object> params,Long type,
			HttpServletResponse response){
		List<SysIdCard> datas = sysIdCardService.findByParam(params);
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		titleMap.put("创建人","createBy");
		//解决数据词典的值
		if(!datas.isEmpty()){
			sysDictService.findAllMultimap();
			for(SysIdCard r : datas){
			}
		}
		try {
			//流的方式直接下载
			ExcelUtils.exportExcel(response, "身份证.xls", datas, titleMap);
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
		List<SysIdCard> list = new ArrayList<SysIdCard>();
		SysIdCard sysIdCard = new SysIdCard();
			sysIdCard.set("name","示例");
		list.add(sysIdCard);
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		titleMap.put("具体行政地址","name");
		try {
			//流的方式直接下载
			ExcelUtils.exportExcel(response, "身份证模板.xls", list, titleMap);
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
		cs.add(new Column("name","","java.lang.String"));	
		
		ExcelConfig config = new ExcelConfig.Builder(SysIdCard.class)
				.sheetNum(0)
				.startRow(1)
				.addColumn(cs).build();
		int count = 0;
		try {
			@SuppressWarnings("unchecked")
			List<Map<String,Object>> list = (List<Map<String,Object>>) EasyXls.xls2List(config,FileUtil.uploadFile(request));
			for (Map<String,Object> r : list) {
				SysIdCard sysIdCard = new SysIdCard();
				for(Map.Entry<String, Object> entry : r.entrySet()){
					String dic = configs.get(entry.getKey());
					if(StringUtils.isNotBlank(dic) && entry.getValue() != null){//解决数据词典列
						sysIdCard.set(entry.getKey(), sysDictService.getValue(dic,entry.getValue().toString()));
					}else{
						sysIdCard.set(entry.getKey(),entry.getValue());
					}
				}
				count += sysIdCardService.saveSysIdCard(sysIdCard);	
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("成功导入"+count + "条数据!");
		} catch (Exception e) {
			response.getWriter().write("导入失败");
			e.printStackTrace();
		}
	}
	
	
}
	