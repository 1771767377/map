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
@RequestMapping("topnStatistics")
public class TopnStatisticsController {

	@RequestMapping
	public String jump() {
		
		return "zjcdn/topnStatistics";
	}
	
	//ip
	
	/**12个月数据
	 * @param year
	 * @param table
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping("getMonthCountIP")
	@ResponseBody
	public List<Map<String, Object>> getMonthCountIP(String year, String table) throws SQLException{
		
		Connection conn = JDBCUtils.getConnection();
		Statement statement = conn.createStatement();
		String sql = "SELECT GROUP_CONCAT(a.resource_ip SEPARATOR ',') ip, GROUP_CONCAT(a.ip_count ORDER BY a.ip_count DESC) count,MONTH(insertdate) as dates FROM "+table+" a WHERE 5>(SELECT COUNT(*) FROM "+table+" WHERE MONTH(insertdate)=MONTH(a.insertdate) AND ip_count>a.ip_count) and YEAR(insertdate) = "+year+" GROUP BY MONTH(insertdate) ORDER BY MONTH(a.insertdate) desc";
		ResultSet resultSet = statement.executeQuery(sql);
		List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		JDBCUtils.closeConnection(conn);
		return list;
	}
	
	/**点击某个月展示详细信息
	 * @param year
	 * @param monthdata
	 * @param table
	 * @return
	 * @throws SQLException 
	 */
	 @RequestMapping("checkMonthIP")
	 @ResponseBody
	 public List<Map<String, Object>> checkMonthIP(String year,String monthdata,String table) throws SQLException{
		 
	    String month = monthdata.replace("月", "");
		if(month.length()<2){
		   month.replace(month, "0"+month);
		}
		 
		Connection conn = JDBCUtils.getConnection();
		Statement statement = conn.createStatement();
		String sql = "select resource_ip as name,ip_count as value from "+table+" where YEAR(insertdate) = "+year+" and MONTH(insertdate) = "+month+" ORDER BY ip_count desc LIMIT 5";
		ResultSet resultSet = statement.executeQuery(sql);
		List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		JDBCUtils.closeConnection(conn);
		return list;
	 }
	
	 /**某月下的每天的数据
	  * @param year
	  * @param monthdata
	  * @param table
	  * @return
	 * @throws SQLException 
	  */
	 @RequestMapping("getDayCountIP")
	 @ResponseBody
	 public List<Map<String, Object>> getDayCountIP(String year,String monthdata,String table) throws SQLException{
		 
		 Connection conn = JDBCUtils.getConnection();
		 Statement statement = conn.createStatement();
		 String sql = "SELECT GROUP_CONCAT(a.resource_ip SEPARATOR '+') ip, GROUP_CONCAT(a.ip_count ORDER BY a.ip_count DESC) count,DAY(insertdate) as dates FROM "+table+" a WHERE 5>(SELECT COUNT(*) FROM "+table+" WHERE DATE(insertdate)=DATE(a.insertdate) AND ip_count>a.ip_count) and YEAR(insertdate) = "+year+" and MONTH(insertdate)="+monthdata+" GROUP BY DATE(insertdate) ORDER BY DATE(a.insertdate) asc";
		 ResultSet resultSet = statement.executeQuery(sql);
		 List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		 JDBCUtils.closeConnection(conn);
		 return list;
	 }
	 
	 /**某月下的某天的详细数据
	  * @param year
	  * @param monthdata
	  * @param table
	  * @return
	 * @throws SQLException 
	  */
	 @RequestMapping("checkDayIP")
	 @ResponseBody
	 public List<Map<String, Object>> checkDayIP(String year,String monthname,String table,String dayname) throws SQLException{
		 
		 Connection conn = JDBCUtils.getConnection();
		 Statement statement = conn.createStatement();
		 String sql = "select resource_ip as name,ip_count as value from "+table+" where YEAR(insertdate) = "+year+" and MONTH(insertdate) = "+monthname+" AND DAY(insertdate) = "+dayname+" ORDER BY ip_count desc LIMIT 5";
		 ResultSet resultSet = statement.executeQuery(sql);
		 List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		 JDBCUtils.closeConnection(conn);
		 return list;	 
	 }
	 
	 //domain
	 /**12个月数据
	  * @param year
	  * @param table
      * @return
	 * @throws SQLException 
  	  */
	  @RequestMapping(value="getMonthCountDomain")
	  @ResponseBody
	  public List<Map<String, Object>> getMonthCountDomain(String year, String table) throws SQLException{
	  	
		  Connection conn = JDBCUtils.getConnection();
		  Statement statement = conn.createStatement();
		  String sql = "SELECT GROUP_CONCAT(a.resource_domain SEPARATOR '+') domain, GROUP_CONCAT(a.domain_count ORDER BY a.domain_count DESC) count,MONTH(insertdate) as dates FROM "+table+" a WHERE 5>(SELECT COUNT(*) FROM "+table+" WHERE MONTH(insertdate)=MONTH(a.insertdate) AND domain_count>a.domain_count) and YEAR(insertdate)="+year+" GROUP BY MONTH(insertdate) ORDER BY MONTH(a.insertdate) desc";
		  ResultSet resultSet = statement.executeQuery(sql);
		  List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		  JDBCUtils.closeConnection(conn);
		  return list;
	  }
	 
	  /**点击某个月展示详细信息
	   * @param year
	   * @param monthdata
	   * @param table
	   * @return
	 * @throws SQLException 
	   */
	  @RequestMapping("checkMonthDomain")
	  @ResponseBody
      public List<Map<String,Object>> checkMonthDomain(String year,String monthdata,String table) throws SQLException{
    	  
		  String month = monthdata.replace("月", "");
			if(month.length()<2){
			   month.replace(month, "0"+month);
		  }
		  
		  Connection conn = JDBCUtils.getConnection();
		  Statement statement = conn.createStatement();
		  String sql = "select resource_domain as name,domain_count as value from "+table+" where YEAR(insertdate) = "+year+" AND MONTH(insertdate) = "+month+" ORDER BY domain_count desc LIMIT 5";
		  ResultSet resultSet = statement.executeQuery(sql);
		  List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		  JDBCUtils.closeConnection(conn);
		  return list;
      }
	 
	  /**某月下的每天的数据
	   * @param year
   	   * @param monthdata
	   * @param table
	   * @return
	 * @throws SQLException 
	   */ 
	  @RequestMapping("getDayCountDomain")
	  @ResponseBody
	  public List<Map<String,Object>> getDayCountDomain(String year,String monthdata,String table) throws SQLException{
		  
		  String month = monthdata.replace("月", "");
			if(month.length()<2){
			   month.replace(month, "0"+month);
		  }
		  
		  Connection conn = JDBCUtils.getConnection();
		  Statement statement = conn.createStatement();
		  String sql = "SELECT GROUP_CONCAT(a.resource_domain SEPARATOR '+') domain, GROUP_CONCAT(a.domain_count ORDER BY a.domain_count DESC) count,DAY(insertdate) as dates FROM "+table+" a WHERE 5>(SELECT COUNT(*) FROM "+table+" WHERE DATE(insertdate)=DATE(a.insertdate) AND domain_count>a.domain_count) and YEAR(insertdate)="+year+" and MONTH(insertdate)="+month+" GROUP BY DATE(insertdate) ORDER BY DATE(a.insertdate) asc";
		  ResultSet resultSet = statement.executeQuery(sql);
		  List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		  JDBCUtils.closeConnection(conn);
		  return list;
	  }
	  
	  /**某月下的某天的数据
	   * @param year
   	   * @param monthdata
	   * @param table
	   * @param dayname
	   * @return
	 * @throws SQLException 
	   */ 
	  @RequestMapping("checkDayDomain")
	  @ResponseBody
	  public List<Map<String,Object>> checkDayDomain(String year,String month,String table,String dayname) throws SQLException{
		  
		  Connection conn = JDBCUtils.getConnection();
		  Statement statement = conn.createStatement();
		  String sql = "select resource_domain as name,domain_count as value from "+table+" where YEAR(insertdate) = "+year+" AND MONTH(insertdate) = "+month+" AND DAY(insertdate) = "+dayname+" ORDER BY domain_count desc LIMIT 5";
		  ResultSet resultSet = statement.executeQuery(sql);
		  List<Map<String, Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		  JDBCUtils.closeConnection(conn);
		  return list;
	  }
	 
	
}
