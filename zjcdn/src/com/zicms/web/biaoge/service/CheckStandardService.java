package com.zicms.web.biaoge.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zicms.common.constant.Constant;
import com.zicms.web.biaoge.mapper.CheckStandardMapper;
import com.zicms.web.biaoge.model.CheckStandard;
import com.zicms.web.datacenter.model.ProvinceDict;
import com.zicms.web.sys.utils.SysUserUtils;

@Service("checkStandardService")
public class CheckStandardService {

    @Resource
    private CheckStandardMapper checkStandardMapper;

    /**
     * 检出量达标情况分析
     * @param params
     * @return
     */
    public List<CheckStandard> getCheckStandard(Map<String, Object> params) {
        return checkStandardMapper.getCheckStandard(params);

    }
    
    /**
     * 添加审核基准值
     * @param params
     * @return
     */
	public int addCheckBase(Map<String, Object> params) {
		return checkStandardMapper.addCheckBase(params);
	}

	/**
	 * 条件查询省份
	 * @param params
	 * @return
	 */
	public PageInfo<ProvinceDict> findPro(Map<String, Object> params) {
		params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
        params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
        PageHelper.startPage(params);
        List<ProvinceDict> list = checkStandardMapper.findPro(params);
        return new PageInfo<ProvinceDict>(list);
	}

	/**
	 * 根据编号查询省份信息
	 * @param province
	 * @return
	 */
	public ProvinceDict findByPro(String province) {
		return checkStandardMapper.findByPro(province);
	}

	/**
	 * 获得合计的审核基准值
	 * @return
	 */
	public Integer getTotalStandard() {
		return checkStandardMapper.getTotalStandard();
	}

	/**
	 * 获取合计的详细信息
	 * @return
	 */
	public ProvinceDict findTotal() {
		return checkStandardMapper.findTotal();
	}

}
