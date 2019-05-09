package com.zicms.web.zjcdn.service;

import com.zicms.common.base.ServiceMybatis;
import com.zicms.web.zjcdn.mapper.MainMapper;
import com.zicms.web.zjcdn.model.Main;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("mainService")
public class MainService extends ServiceMybatis<Main> {

    @Autowired
    private MainMapper mainMapper;

    public void updateIp(String ip,String date,String username){

        mainMapper.updateIp(ip,date,username);
    }

    public List<Main> findIpa(String username){

        return mainMapper.findIpa(username);
    }



}
