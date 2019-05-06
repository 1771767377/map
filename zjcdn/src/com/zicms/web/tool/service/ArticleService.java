package com.zicms.web.tool.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zicms.common.base.ServiceMybatis;

import com.zicms.web.tool.model.Article;
import com.zicms.web.tool.mapper.ArticleMapper;


@Service("articleService")
public class ArticleService extends ServiceMybatis<Article>{

	@Resource
	private ArticleMapper articleMapper;
	
	public int saveArticle(Article article){
		int count = 0;
		if(article.getId() == null){
			count = this.insertSelective(article);
		}else{
			count = this.updateByPrimaryKeySelective(article);
		}
		return count;
	}
	
	/**
	* 删除
	* @param id
	* @return
	 */
	public int deleteArticle(Long id){
		return this.updateDelFlagToDelStatusById(Article.class, id);
	}
	
	public Integer deleteArticle(Long[] ids) {
		int count = 0;
		for (Long id : ids) {
			count += deleteArticle(id);
		}
		return count;
	}
	
	/**
	 * 列表--分页
	 * @param params
	 * @return
	 */
	public PageInfo<Article> findPageInfo(Map<String, Object> params) {
		PageHelper.startPage(params);
		List<Article> list = articleMapper.findPageInfo(params);
		return new PageInfo<Article>(list);
	}
	
	/**
	 * 列表--不分页
	 * @param params
	 * @return
	 */
	public List<Article> findByParam(Map<String, Object> params) {
		return articleMapper.findPageInfo(params);
	}

	
}
