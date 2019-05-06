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
@RequestMapping(value="timeStatistics")
public class StatisticsController {

	@RequestMapping
	public String Jump() {
		
		return "zjcdn/timeStatistics";
	}
	
	/**12个月数据
	 * @param 
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping("getUnqualifiedYear")
	@ResponseBody
	public List<Map<String,Object>> getUnqualified(String years,String tablename) throws SQLException{
		
		Connection conn = JDBCUtils.getConnection();
		Statement statement = conn.createStatement();
		String sql = "SELECT MONTH(insertdate) as dates,count(*) as count from "+tablename+" where province = '3310000' and retrial_status = '1' and YEAR(insertdate) = "+years+" GROUP BY MONTH(insertdate)";
		ResultSet resultSet = statement.executeQuery(sql);
		List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		JDBCUtils.closeConnection(conn);
		return list;
	}
	
	/**12个月详细数据
	 * @param 
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping("getUnqualifiedMonth")
	@ResponseBody
	public List<Map<String,Object>> getUnqualifiedMonth(String year,String month ,String tablename) throws SQLException{
		
		Connection conn = JDBCUtils.getConnection();
		Statement statement = conn.createStatement();
		String sql = "SELECT DAY(insertdate) as dates,count(*) as count from "+tablename+" where province = '3310000' and retrial_status = '1' and YEAR(insertdate) = "+year+" and MONTH(insertdate) = "+month+" GROUP BY DAY(insertdate)";
		ResultSet resultSet = statement.executeQuery(sql);
		List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		JDBCUtils.closeConnection(conn);
		return list;
	}
	
}
