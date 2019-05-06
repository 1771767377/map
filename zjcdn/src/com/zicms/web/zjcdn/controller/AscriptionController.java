package com.zicms.web.zjcdn.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.web.zjcdn.utils.JDBCUtils;
import com.zicms.web.zjcdn.utils.JdbcTools;

@Controller
@RequestMapping("ascriptionStatistics")
public class AscriptionController {

	@RequestMapping
	public String jump() {
		
		return "zjcdn/ascriptionStatistics";
	}
	
	/**
	 * 类型分布统计
	 * @throws SQLException 
	 **/
	@RequestMapping("getDistributionCount")
	@ResponseBody
	public List<Map<String,Object>> getDistributionCount() throws SQLException{
		
		List<Map<String,Object>> list = null;
		Connection conn = JDBCUtils.getConnection();
		Statement statement = conn.createStatement();
		String sql = "SELECT COUNT(*) as count from img_history where province = '3310000' and retrial_status = '1' UNION ALL SELECT COUNT(*) as count from video_history where province = '3310000' and retrial_status = '1'";
		ResultSet resultSet = statement.executeQuery(sql);
		list = JdbcTools.handleResultSetToMapListNew(resultSet);
		JDBCUtils.closeConnection(conn);
		return list;
	}
	
	/**
	 * 厂家归属统计
	 * @throws SQLException 
	 */
	@RequestMapping("getAscription")
	@ResponseBody
	public List<Map<String,Object>> getAscription(String table) throws SQLException{
		
		List<Map<String,Object>> list = null;
		Connection conn = JDBCUtils.getConnection();
		Statement statement = conn.createStatement();
		String sql = "SELECT COUNT(*) as value , information as name from "+table+" WHERE province = '3310000' and information != '' AND retrial_status = '1' GROUP BY information";
		ResultSet resultSet = statement.executeQuery(sql);
		list = JdbcTools.handleResultSetToMapListNew(resultSet);
		JDBCUtils.closeConnection(conn);
		return list;
	}
	
}
