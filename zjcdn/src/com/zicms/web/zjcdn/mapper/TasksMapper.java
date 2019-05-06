package com.zicms.web.zjcdn.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.zjcdn.model.Audit;

public interface TasksMapper extends Mapper<Audit>{

	void insertDatas(@Param("list")List<Audit> list);

	void deleteMessage(@Param("id")String id);

	
	
}
