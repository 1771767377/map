package com.zicms.web.datacenter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.datacenter.model.ImageCheck;

public interface ImageCheckMapper extends Mapper<ImageCheck> {

/***************   初审       ***********************/
	public int getCount(@Param("proArr") String[] proArr);

    public List<ImageCheck> findPageAll(Map<String, Object> param);

    public List<ImageCheck> findList(Map<String, Object> param);

    public int updateTop(@Param("imag") ImageCheck imag, @Param("list") List<ImageCheck> list);

    public int updateToBad(ImageCheck imag);

    public int updateToUnconfirm(ImageCheck imag);

    public void updateToNormal(ImageCheck imag);
    
    public int submitPage(ImageCheck imag);
    
    public Integer rollback(@Param("trialAccount") String username);
	
/***************   复审       ***********************/
    
	public int getRecheckCount(@Param("proArr") String[] proArr);

	public List<ImageCheck> findRecheckList(@Param("param") Map<String, Object> param);
	
	public int updateRecheckTop(@Param("imag") ImageCheck imag, @Param("list") List<ImageCheck> list);

	public int reUpdateToNormal(ImageCheck imag);

    public List<ImageCheck> findList(String param);

    public int undoReupdate(ImageCheck imag);

    public int updateRecheck(@Param("param") Map<String, Object> param);

/***************   不确定库       ***********************/

    public int getUnconfirmCount(@Param("proArr") String[] proArr);
    
    public List<ImageCheck> findUnConfirm(@Param("param") Map<String,Object> params);

    public List<ImageCheck> findUnList(@Param("param") Map<String, Object> param);

    public int updateImage(ImageCheck imag);

	public int updateImageToNormal(ImageCheck imag);

	public void undoUpdate(String uuid);

	public int updateUnconfirm(Map<String, Object> param);

/***************   审核结果       ***********************/
    public List<ImageCheck> findPageList(@Param("param") Map<String, Object> params);
    
/***************   数据导出       ***********************/
    public int saveCsvImage(@Param("param") Map<String, Object> param);

    public List<ImageCheck> findPag(Map<String, Object> param);

    public void replace(Map<String, Object> param);
   
    
/***************   学习库(未开发)     ***********************/
	public List<ImageCheck> findLearnList(@Param("params") Map<String, Object> params);

	
/***************   历史库       ***********************/
	public List<String> findTrialAccount();

	public List<String> findRetrialAccount();

	public List<String> findUsername();
	//显示历史图片信息
	public List<ImageCheck> findHistoryList(@Param("param") Map<String, Object> params);

    public List<ImageCheck> findExportData(@Param("uuids") List<String> uuids);

	

/***************   其他       ***********************/
    public ImageCheck findUrl(String imageturl);

    
/***************   北京单独导出        ***********************/
    public List<ImageCheck> findPekingData(@Param("username") String username);

    public void deleteFromImagecheck(@Param("username")String username);

	public List<ImageCheck> findPekingResult(@Param("username")String username);

}
