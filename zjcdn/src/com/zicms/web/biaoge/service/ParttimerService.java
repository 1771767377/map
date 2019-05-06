package com.zicms.web.biaoge.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zicms.common.base.ServiceMybatis;
import com.zicms.web.biaoge.mapper.ParttimerMapper;
import com.zicms.web.biaoge.model.Parttimer;

/**
 * 
 * @author
 */

@Service("parttimerService")
public class ParttimerService extends ServiceMybatis<Parttimer> {

    @Resource
    private ParttimerMapper parttimerMapper;

    /**
     * 查询审核账号
     * 
     * @param Parttimer
     * @return
     */
    public List<Parttimer> findAccount(Map<String, Object> params) {
        return parttimerMapper.findAccount(params);
    }

    /**
     * 添加或更新兼职人员核算信息
     * 
     * @param Parttimer
     * @return
     */
    public int saveParttimer(Parttimer parttimer) {
        int count = 0;
        if (null == parttimer.getId()) {// 添加
            count = this.insertSelective(parttimer);
        } else {// 更新
            count = this.updateByPrimaryKeySelective(parttimer);
        }
        return count;
    }

    /**
     * 删除兼职人员（逻辑删除）
     * 
     * @param userId
     * @return
     */
    public int deleteUser(Long userId) {
        return this.updateDelFlagToDelStatusById(Parttimer.class, userId);
    }

    /**
     * 兼职人员工资审核信息列表(用以查询)
     * 
     * @param params
     * @return
     */
    public PageInfo<Parttimer> findPageInfo(Map<String, Object> params) {
        PageHelper.startPage(params);
        List<Parttimer> list = parttimerMapper.findPageInfo(params);
        return new PageInfo<Parttimer>(list);
    }
    /**
     * 兼职人员名单
     * @return
     */
	public List<String> findUsername() {
		return parttimerMapper.findUsername();
	}

}
