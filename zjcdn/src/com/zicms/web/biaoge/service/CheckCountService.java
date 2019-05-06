package com.zicms.web.biaoge.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zicms.common.base.ServiceMybatis;
import com.zicms.web.biaoge.mapper.CheckCountMapper;
import com.zicms.web.biaoge.model.CheckCount;

@Service("checkCountService")
public class CheckCountService extends ServiceMybatis<CheckCount> {
    @Autowired
    private CheckCountMapper checkCountMapper;

    public List<CheckCount> findAllByAccount(Map<String, Object> params) {
        return checkCountMapper.findAllByAccount(params);
    }

    public List<CheckCount> findAllByDate(Map<String, Object> params) {
        return checkCountMapper.findAllByDate(params);
    }

    public Integer findCount() {
        return checkCountMapper.findCount();
    }

    public List<CheckCount> findCheckWork(Map<String, Object> params) {
        // 查询出某账号各省的已审核量
        List<CheckCount> list = checkCountMapper.findCheckWork(params);
        // 查询出各省的审核总量
        List<CheckCount> list_1 = checkCountMapper.findWorkByProvince(params);
        // 将各省的总审核量与账号匹配
        if (!list.isEmpty() && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                if (!list_1.isEmpty() && list_1.size() != 0) {
                    for (int j = 0; j < list_1.size(); j++) {
                        if (list.get(i).getProvince().equals(list_1.get(j).getProvince())) {
                            list.get(i).set("countbyprovince", list_1.get(j).getCountByProvince());
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }
}
