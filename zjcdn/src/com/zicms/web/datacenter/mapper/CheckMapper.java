package com.zicms.web.datacenter.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.datacenter.model.Check;

public interface CheckMapper extends Mapper<Check> {

    public int insertCheck(Map<String, Object> map);

    public int updateCheck(Map<String, Object> map);

    public List<Check> findCheck();

    public String findDate(Map<String, Object> map);

    public Integer findAll(Map<String, Object> map);

    public Integer findCount(Map<String, Object> map);

}
