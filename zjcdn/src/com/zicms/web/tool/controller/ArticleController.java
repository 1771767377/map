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

import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;
import com.zicms.common.excel.ExcelUtils;
import com.zicms.common.poi.EasyXls;
import com.zicms.common.poi.bean.Column;
import com.zicms.common.poi.bean.ExcelConfig;
import com.zicms.common.utils.FileUtil;
import com.zicms.common.utils.StringConvert;
import com.zicms.web.sys.service.SysDictService;
import com.zicms.web.tool.model.Article;
import com.zicms.web.tool.service.ArticleService;
import com.zicms.web.util.ResponseUtils;


@Controller
@RequestMapping("tool/article ")
public class ArticleController {
	@Resource
	private ArticleService articleService;
	
	@Resource
	private SysDictService sysDictService;
	
	/**
	 * 跳转到模块页面
	* @param model
	* @return 模块html
	 */
	@RequestMapping
	public String toArticle(Model model){
		return "tool/article/index";
	}
	
	/**
	 * 分页显示
	 */
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public void list(@RequestParam Map<String, Object> params,HttpServletResponse response){
		if(params.containsKey("sortC")){
			params.put("sortC", StringConvert.camelhumpToUnderline(params.get("sortC").toString()));
		}
		PageInfo<Article> page = articleService.findPageInfo(params);
		ResponseUtils.renderJson(response, page);
	}
	
	
	/**
	 * 添加或更新
	 * @param params
	 * @return
	 */
	@RequestMapping(value="save",method=RequestMethod.POST)
	public @ResponseBody Integer save(@ModelAttribute Article article){
		return articleService.saveArticle(article);
	}
	
	/**
	 * 删除
	 * @param 
	 * @return
	 */
	@RequestMapping(value="delete",method=RequestMethod.POST)
	public @ResponseBody Integer del(Long id){
		return articleService.deleteArticle(id);
	}
	
	/**
	 * 批量删除
	 * @param
	 * @return
	 */
	@RequestMapping(value="deletes",method=RequestMethod.POST)
	public @ResponseBody Integer dels(@RequestParam(value = "ids[]") Long[] ids){
		return articleService.deleteArticle(ids);
	}
	
	/**
	 * 弹窗显示
	 * @param params {"mode":"1.add 2.edit 3.detail}
	 * @return
	 */
	@RequestMapping(value="{mode}/showlayer",method=RequestMethod.POST)
	public String layer(Long id,@PathVariable String mode, Model model){
		Article article = null;
		if(StringUtils.equals("edit", mode)){
			article = articleService.selectByPrimaryKey(id);
			model.addAttribute("article", article);
		}else if(StringUtils.equals("detail", mode)){
			article = articleService.selectByPrimaryKey(id);
			model.addAttribute("article", article);
		}
		return mode.equals("detail")?"tool/article/detail":"tool/article/save";
	}
	
	
	/**
	 * 导出execl数据
	 */
	@RequestMapping(value = "export",method = RequestMethod.POST)
	public void exportFile(@RequestParam Map<String, Object> params,Long type,
			HttpServletResponse response){
		List<Article> datas = articleService.findByParam(params);
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		titleMap.put("编号","id");
		titleMap.put("标题","title");
		titleMap.put("关键词","keyWord");
		titleMap.put("内容","content");
		titleMap.put("文章分类","type");
		titleMap.put("是否发送到微信","isSendWx");
		titleMap.put("是否发送到web端","isSendWeb");
		titleMap.put("状态","status");
		titleMap.put("创建人","createBy");
		titleMap.put("创建时间","createDate");
		titleMap.put("修改人","updateBy");
		titleMap.put("修改时间","updateDate");
		//解决数据词典的值
		if(!datas.isEmpty()){
			sysDictService.findAllMultimap();
			for(Article r : datas){
				if(r.get("type") != null){
					r.set("type", sysDictService.getLabel("type", "article_type"));
				}
				if(r.get("status") != null){
					r.set("status", sysDictService.getLabel("status", "article_status"));
				}
			}
		}
		try {
			//流的方式直接下载
			ExcelUtils.exportExcel(response, "文章管理.xls", datas, titleMap);
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
		List<Article> list = new ArrayList<Article>();
		Article article = new Article();
			article.set("title","示例");
			article.set("keyWord","示例");
			article.set("content","示例");
			article.set("type","示例");
		list.add(article);
		Map<String, String> titleMap = Maps.newLinkedHashMap();
		titleMap.put("标题","title");
		titleMap.put("关键词","keyWord");
		titleMap.put("内容","content");
		titleMap.put("文章分类","type");
		try {
			//流的方式直接下载
			ExcelUtils.exportExcel(response, "文章管理模板.xls", list, titleMap);
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
		cs.add(new Column("title","","java.lang.String"));	
		cs.add(new Column("keyWord","","java.lang.String"));	
		cs.add(new Column("content","","java.lang.String"));	
		cs.add(new Column("type","","java.lang.String"));	
		configs.put("type","article_type");
		configs.put("status","article_status");
		
		ExcelConfig config = new ExcelConfig.Builder(Article.class)
				.sheetNum(0)
				.startRow(1)
				.addColumn(cs).build();
		int count = 0;
		try {
			List<Map<String,Object>> list = (List<Map<String,Object>>) EasyXls.xls2List(config,FileUtil.uploadFile(request));
			for (Map<String,Object> r : list) {
				Article article = new Article();
				for(Map.Entry<String, Object> entry : r.entrySet()){
					String dic = configs.get(entry.getKey());
					if(StringUtils.isNotBlank(dic) && entry.getValue() != null){//解决数据词典列
						article.set(entry.getKey(), sysDictService.getValue(dic,entry.getValue().toString()));
					}else{
						article.set(entry.getKey(),entry.getValue());
					}
				}
				count += articleService.saveArticle(article);	
			}
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("成功导入"+count + "条数据!");
		} catch (Exception e) {
			response.getWriter().write("导入失败");
			e.printStackTrace();
		}
	}
	
	
}
	