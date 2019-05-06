package com.zicms.web.biaoge.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zicms.common.base.ServiceMybatis;
import com.zicms.web.biaoge.mapper.CheckedMapper;
import com.zicms.web.biaoge.model.Checked;

@Service("checkedService")
public class CheckedService extends ServiceMybatis<Checked> {
    @Autowired
    private CheckedMapper checkedMapper;

    /* 获取各个省份的审核完成量和未完成量 */
    public List<Checked> findPageAll(Map<String, Object> params) {
        return checkedMapper.findPageAll(params);
    }

    public List<Checked> findPageAllByDate(Map<String, Object> params) {
        return checkedMapper.findPageAllByDate(params);
    }

    public List<Checked> findAllByHour(Map<String, Object> params) {
        return checkedMapper.findAllByHour(params);
    }

    public List<Checked> findAllByDay(Map<String, Object> params) {
        return checkedMapper.findAllByDay(params);
    }
}
