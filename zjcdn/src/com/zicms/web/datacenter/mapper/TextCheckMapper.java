package com.zicms.web.datacenter.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;
import com.zicms.web.datacenter.model.TextCheck;

public interface TextCheckMapper extends Mapper<TextCheck> {

    public List<TextCheck> findPageInfo(Map<String, Object> params);

    public List<TextCheck> findPageList(@Param("param") Map<String, Object> params, 
    									@Param("retrialAccount") String retrialAccount);
    
    public int updateTextCheck(TextCheck txt);

    public List<TextCheck> findPag(Map<String, Object> param);

    public int updateTop(@Param("tet")TextCheck tet, @Param("list")List<TextCheck> list);

    public List<TextCheck> findList(@Param("param") Map<String, Object> param);

    public List<TextCheck> findExportList(@Param("param") Map<String, Object> params);

    public List<TextCheck> findUnList(@Param("param") Map<String, Object> param);

    public int updateUnconfirmStatus(TextCheck text);

    public List<TextCheck> findExportData(Map<String, Object> params);

    public int saveCsvImage1(TextCheck text);

    public int getCount(@Param("proArr") String[] proArr);

    public int updateStatus(TextCheck tet);

    public int insertRecord(TextCheck txt);

    public List<TextCheck> findRecheckList(@Param("param") Map<String, Object> params);

    public int updateTopList(@Param("tet")TextCheck tet, @Param("list")List<TextCheck> list);

    public int deleteData(Map<String, Object> list);

    public int submitPage(@Param("param")Map<String, Object> param);

    public int updatereTextCheck(TextCheck tet);

    public int updateExport(@Param("param") Map<String, Object> param);

    public int updateRestatus(@Param("retrialAccount")String retrialAccount);

	public List<String> findTrialAccount();

	public List<String> findRetrialAccount();

	public List<String> findUsername();

	public int updateUnconfirm(Map<String, Object> param);

	public int getUnconfirmCount(@Param("proArr") String[] proArr);

	public int getReCount(@Param("proArr") String[] proArr);
	
}






