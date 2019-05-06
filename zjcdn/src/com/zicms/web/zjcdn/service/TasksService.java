package com.zicms.web.zjcdn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zicms.common.base.ServiceMybatis;
import com.zicms.web.zjcdn.mapper.TasksMapper;
import com.zicms.web.zjcdn.model.Audit;

@Service("tasksService")
public class TasksService extends ServiceMybatis<Audit>{

	@Autowired
	private TasksMapper tasksMapper;

	public void insertDatas(List<Audit> list) {

		tasksMapper.insertDatas(list);
	}

	public void deleteMessage(String id) {

		tasksMapper.deleteMessage(id);
	}

	
	
	
}
