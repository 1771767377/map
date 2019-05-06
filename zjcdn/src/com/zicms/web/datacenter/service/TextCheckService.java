package com.zicms.web.datacenter.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zicms.common.base.ServiceMybatis;
import com.zicms.common.constant.Constant;
import com.zicms.web.datacenter.mapper.CheckMapper;
import com.zicms.web.datacenter.mapper.TextCheckMapper;
import com.zicms.web.datacenter.model.TextCheck;
import com.zicms.web.sys.utils.SysUserUtils;

@Service("textCheckService")
public class TextCheckService extends ServiceMybatis<TextCheck> {

    @Autowired
    private TextCheckMapper textCheckMapper;
    
    @Autowired
    private CheckMapper checkMapper;

    public PageInfo<TextCheck> findPageInfo(Map<String, Object> params) {
        params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
        params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
        PageHelper.startPage(params);
        List<TextCheck> list = textCheckMapper.findPageInfo(params);
        return new PageInfo<TextCheck>(list);
    }

    public List<TextCheck> findPageList(Map<String, Object> params, String retrialAccount) {
        return textCheckMapper.findPageList(params,retrialAccount);
    }

    public List<TextCheck> findPag(Map<String, Object> param) {
        return textCheckMapper.findPag(param);
    }

    public int updateTextCheck(TextCheck tet) {
        return textCheckMapper.updateTextCheck(tet);
    }

    public int updateTop(TextCheck tet, List<TextCheck> list) {
        return textCheckMapper.updateTop(tet,list);
    }

    public List<TextCheck> findList(Map<String, Object> param) {
        return textCheckMapper.findList(param);
    }

    public PageInfo<TextCheck> findExportList(Map<String, Object> params) {
        params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
        params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
        PageHelper.startPage(params);
        List<TextCheck> list = textCheckMapper.findExportList(params);
        return new PageInfo<TextCheck>(list);
    }

    public List<TextCheck> findUnList(Map<String, Object> param) {
        return textCheckMapper.findUnList(param);
    }

    public int updateUnconfirmStatus(TextCheck text) {
        return textCheckMapper.updateUnconfirmStatus(text);
    }

    /**
     * 将登陆名插入到数据库
     * @param param
     * @return
     */
    public int insertCheck(Map<String, Object> map) {
        return checkMapper.insertCheck(map);
    }

    /**
     * 更新check表的flag字段
     * @param map
     * @return
     */
    public int updateCheck(Map<String, Object> map) {
        return checkMapper.updateCheck(map);
    }

    public List<TextCheck> findExportData(Map<String, Object> params) {
        return textCheckMapper.findExportData(params);
    }

    public int saveCsvImage1(TextCheck text) {
        return textCheckMapper.saveCsvImage1(text);
    }

    public int getCount(String[] proArr) {
        return textCheckMapper.getCount(proArr);
    }

    public int updateStatus(TextCheck tet) {
        return textCheckMapper.updateStatus(tet);
    }

    //把复审撤销的数据插入到历史表和学习表中
    public int insertRecord(TextCheck txt) {
        return textCheckMapper.insertRecord(txt);
    }

    public PageInfo<TextCheck> findRecheckList(Map<String, Object> params) {
        params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
        params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
        PageHelper.startPage(params);
        List<TextCheck> list = textCheckMapper.findRecheckList(params);
        return new PageInfo<TextCheck>(list);
    }

    public int updateTopList(TextCheck tet, List<TextCheck> list) {
        return textCheckMapper.updateTopList(tet,list);
    }

    public int deleteData(Map<String, Object> list) {
        return textCheckMapper.deleteData(list);
    }

    public int submitPage(Map<String, Object> param) {
        return textCheckMapper.submitPage(param);
    }

    public int updatereTextCheck(TextCheck tet) {
        return textCheckMapper.updatereTextCheck(tet);
    }

    public int updateExport(Map<String, Object> param) {
        return textCheckMapper.updateExport(param);
    }

    public int updateRestatus(String retrialAccount) {
        return textCheckMapper.updateRestatus(retrialAccount);
    }

	public List<String> findTrialAccount() {
		return textCheckMapper.findTrialAccount();
	}

	public List<String> findRetrialAccount() {
		return textCheckMapper.findRetrialAccount();
	}

	public List<String> findUsername() {
		return textCheckMapper.findUsername();
	}

	public int updateUnconfirm(Map<String, Object> param) {
		return textCheckMapper.updateUnconfirm(param);
	}
	
	public int getUnconfirmCount(String[] proArr) {
		return textCheckMapper.getUnconfirmCount(proArr);
	}

	public int getReCount(String[] proArr) {
		return textCheckMapper.getReCount(proArr);
	}

}
