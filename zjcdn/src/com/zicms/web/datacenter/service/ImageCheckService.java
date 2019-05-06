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
import com.zicms.web.datacenter.mapper.ImageCheckMapper;
import com.zicms.web.datacenter.model.Check;
import com.zicms.web.datacenter.model.ImageCheck;
import com.zicms.web.sys.utils.SysUserUtils;

@Service("imageCheckService")
public class ImageCheckService extends ServiceMybatis<ImageCheck> {

    @Autowired
    private ImageCheckMapper imageCheckMapper;

    @Autowired
    private CheckMapper checkMapper;

    /*************** 初审 ***********************/
    public int getCount(String[] proArr) {
        return imageCheckMapper.getCount(proArr);
    }

    public List<ImageCheck> findPageAll(Map<String, Object> param) {
        return imageCheckMapper.findPageAll(param);
    }

    public List<ImageCheck> findList(Map<String, Object> param) {
        return imageCheckMapper.findList(param);
    }

    public int updateTop(ImageCheck imag, List<ImageCheck> list) {
        return imageCheckMapper.updateTop(imag, list);
    }

    public int updateToBad(ImageCheck imag) {
        return imageCheckMapper.updateToBad(imag);
    }

    public int updateToUnconfirm(ImageCheck imag) {
        return imageCheckMapper.updateToUnconfirm(imag);
    }

    public void updateToNormal(ImageCheck imag) {
        imageCheckMapper.updateToNormal(imag);
    }

	public int submitPage(ImageCheck image) {
		return imageCheckMapper.submitPage(image);
	}
	
	public Integer rollback(String username) {
		return imageCheckMapper.rollback(username);
	}

    /***************  复审    ***********************/
	
    public int getRecheckCount(String[] proArr) {
        return imageCheckMapper.getRecheckCount(proArr);
    }

    public List<ImageCheck> findRecheckList(Map<String, Object> param) {
        return imageCheckMapper.findRecheckList(param);
    }

    public int updateRecheckTop(ImageCheck imag, List<ImageCheck> list) {
        return imageCheckMapper.updateRecheckTop(imag, list);
    }

    public int reUpdateToNormal(ImageCheck imag) {
        return imageCheckMapper.reUpdateToNormal(imag);
    }

	public int undoReupdate(ImageCheck imag) {
		return imageCheckMapper.undoReupdate(imag);
	}
	
	public int updateRecheck(Map<String, Object> param) {
		return imageCheckMapper.updateRecheck(param);
	}
    /*************** 不确定库 ***********************/

	public int getUnconfirmCount(String[] proArr) {
		return imageCheckMapper.getUnconfirmCount(proArr);
	}
	
	public List<ImageCheck> findUnConfirm(Map<String, Object> params) {
        return imageCheckMapper.findUnConfirm(params);
    }

    public List<ImageCheck> findUnList(Map<String, Object> params) {
        return imageCheckMapper.findUnList(params);
    }

    public int updateImage(ImageCheck imag) {
        return imageCheckMapper.updateImage(imag);
    }

    public int updateImageToNormal(ImageCheck imag) {
        return imageCheckMapper.updateImageToNormal(imag);
    }

	public void undoUpdate(String uuid) {
		imageCheckMapper.undoUpdate(uuid);
	}
	
	public int updateUnconfirm(Map<String, Object> param) {
		return imageCheckMapper.updateUnconfirm(param);
	}
	
	
    /*************** 审核结果 ***********************/
    public PageInfo<ImageCheck> findPageList(Map<String, Object> params) {
        params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
        params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
        PageHelper.startPage(params);
        List<ImageCheck> list = imageCheckMapper.findPageList(params);
        return new PageInfo<ImageCheck>(list);
    }
    
    /*************** 数据导出 ***********************/
    // 导出时查找所有数据
    public List<ImageCheck> findPag(Map<String, Object> param) {
        return imageCheckMapper.findPag(param);
    }

    // 根据导出时查找的数据更新图片信息
    public int saveCsvImage(Map<String, Object> param) {
        return imageCheckMapper.saveCsvImage(param);
    }
    
    // 将导出的数据从原表中删除
	public void replace(Map<String, Object> param) {
		imageCheckMapper.replace(param);
	}


    /*************** 学习库 ***********************/
    public PageInfo<ImageCheck> findLearnList(Map<String, Object> params) {
        params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
        params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
        PageHelper.startPage(params);
        List<ImageCheck> list = imageCheckMapper.findLearnList(params);
        return new PageInfo<ImageCheck>(list);
    }

    /*************** 历史库 ***********************/
    
    //查找所有初审人员
    public List<String> findTrialAccount() {
		return imageCheckMapper.findTrialAccount();
	}

    //查找所有复审人员
	public List<String> findRetrialAccount() {
		return imageCheckMapper.findRetrialAccount();
	}

	//查找所有导出人员
	public List<String> findUsername() {
		return imageCheckMapper.findUsername();
	}
    // 显示历史图片信息
    public PageInfo<ImageCheck> findHistoryList(Map<String, Object> params) {
        params.put(Constant.CACHE_USER_DATASCOPE, SysUserUtils.dataScopeFilterString("so", null));
        params.put("userType", SysUserUtils.getCacheLoginUser().getUserType());
        PageHelper.startPage(params);
        List<ImageCheck> list = imageCheckMapper.findHistoryList(params);
        return new PageInfo<ImageCheck>(list);
    }

    // 根据查找到的图片uuid导出数据
    public List<ImageCheck> findExportData(List<String> uuids) {
        return imageCheckMapper.findExportData(uuids);
    }

    /*************** checkMapper ***********************/
    /**
     * 查询check表
     * 
     * @param username
     * @return
     */
    public List<Check> findCheck() {
        return checkMapper.findCheck();
    }

    public ImageCheck findUrl(String imageturl) {
        return imageCheckMapper.findUrl(imageturl);
    }

    
    /**
     * 北京查找数据
     * @param username 
     * @return
     */
	public List<ImageCheck> findPekingData(String username) {
		return imageCheckMapper.findPekingData(username);
	}

	//将导出的数据删除
	public void deleteFromImagecheck(String username) {
		imageCheckMapper.deleteFromImagecheck(username);
	}

	public List<ImageCheck> findPekingResult(String username) {
		return imageCheckMapper.findPekingResult(username);
	}

}
