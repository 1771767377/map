package com.zicms.web.zjcdn.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zicms.web.zjcdn.utils.JDBCUtil;
import com.zicms.web.zjcdn.utils.JDBCUtils;
import com.zicms.web.zjcdn.utils.JdbcTools;

@Controller
@RequestMapping("main")
public class MainController {
	/**
	 * 获取监测URL数量
	 * @throws SQLException 
	 * 
	 */
	@RequestMapping("getURLcount")
	@ResponseBody
	public List<Map<String,Object>> getURLcount() throws SQLException, ClassNotFoundException{
		
		List<Map<String,Object>> list = null;
		Connection con = JDBCUtil.getConnection();
		//获得statement对象
		Statement statement = con.createStatement();
		//执行sql语句
		String sql = "SELECT COUNT(*) as count from audit_filing where ysrecord = '1' ";
		ResultSet resultSet = statement.executeQuery(sql);
		list = JdbcTools.handleResultSetToMapListNew(resultSet);
		JDBCUtil.closeConnection(con);
		return list;
	}
	
	/**
	   * 不合规信息厂家统计图
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping("getVender")
	@ResponseBody
	public List<Map<String,Object>> getVender() throws SQLException{
		
		List<Map<String,Object>> list = null;
		long start = System.currentTimeMillis();
		Connection con = JDBCUtils.getConnection();
		System.out.println("连接花费====="+(System.currentTimeMillis()-start)+"ms");
		Statement statement = con.createStatement();
		System.out.println("准备花费====="+(System.currentTimeMillis()-start)+"ms");
		//执行sql语句
		String sql = "SELECT SUM(count) as sum, name from (SELECT COUNT(*) as count,information as name from img_history  WHERE retrial_status = '1' and province = '3310000' GROUP BY information UNION ALL SELECT COUNT(*) as count,information as name from video_history WHERE retrial_status = '1' and province = '3310000' GROUP BY information UNION ALL SELECT COUNT(*) as count,information as name from text_history  WHERE retrial_status = '1' and province = '3310000' GROUP BY information) a GROUP BY name";
		ResultSet resultSet = statement.executeQuery(sql);
		list = JdbcTools.handleResultSetToMapListNew(resultSet);
		long end = System.currentTimeMillis();
		System.out.println("getVender查询需要时间"+(end-start)+"ms");
		JDBCUtils.closeConnection(con);
		return list;
	}
	
	
	/**
	 * 不合规信息类别分布图
	 * @return
	 * @throws SQLException 
	 */
	@RequestMapping("getType")
	@ResponseBody
	public List<Map<String,Object>> getType() throws SQLException{
		
		List<Map<String,Object>> list = null;
		long start = System.currentTimeMillis();
		Connection con = JDBCUtils.getConnection();
		System.out.println("连接花费====="+(System.currentTimeMillis()-start)+"ms");
		//获得statement对象
		Statement statement = con.createStatement();
		//执行sql语句
		String sql = "SELECT COUNT(*) as count from img_history where retrial_status = '1' and province = '3310000' UNION ALL SELECT COUNT(*) from video_history where retrial_status = '1' and province = '3310000' UNION ALL SELECT COUNT(*) FROM text_history where retrial_status='1' and province = '3310000' ";
		ResultSet resultSet = statement.executeQuery(sql);
		list = JdbcTools.handleResultSetToMapListNew(resultSet);
		long end = System.currentTimeMillis();
		System.out.println("getType查询需要时间"+(end-start)+"ms");
		JDBCUtils.closeConnection(con);
		return list;
	}
	
	/**
	   * 不合规信息占比
	 * @throws SQLException 
	 */
	@RequestMapping("getGrade")
	@ResponseBody
	public List<Map<String,Object>> getGrade() throws SQLException{
		
		List<Map<String,Object>> list = null;
		long start = System.currentTimeMillis();
		Connection con = JDBCUtils.getConnection();
		System.out.println("连接花费====="+(System.currentTimeMillis()-start)+"ms");
		//获得statement对象
		Statement statement = con.createStatement();
		//执行sql语句
		String sql = "SELECT SUM(count) as sum from (SELECT COUNT(*) as count from img_history where retrial_status = '1' and province = '3310000' UNION ALL SELECT COUNT(*) from video_history where retrial_status = '1' and province = '3310000' UNION ALL SELECT COUNT(*) FROM text_history where retrial_status='1' and province = '3310000') a UNION ALL SELECT SUM(count) as sum from (SELECT COUNT(*) as count from img_history where province = '3310000' UNION ALL SELECT COUNT(*) from video_history where province = '3310000' UNION ALL SELECT COUNT(*) FROM text_history where province = '3310000') b";
		ResultSet resultSet = statement.executeQuery(sql);
		list = JdbcTools.handleResultSetToMapListNew(resultSet);
		long end = System.currentTimeMillis();
		System.out.println("getGrade查询需要时间"+(end-start)+"ms");
		JDBCUtils.closeConnection(con);
		return list;
	}
	
	/**
	   * 最近一周不合规信息数量
	 * @throws SQLException 
	 */
	 @RequestMapping("getWeek")
	 @ResponseBody
	 public Map<String,Object> getWeek() throws SQLException{
		 
		 List<Map<String,Object>> list1 = null;
		 List<Map<String,Object>> list2 = null;
		 Connection con = JDBCUtils.getConnection();
		 Statement statement1 = con.createStatement();
		 Statement statement2 = con.createStatement();
		 String sql1 = "select count(*) as count,day(insertdate) as date from img_history  where province = '3310000' and retrial_status = '1' and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(insertdate) GROUP BY DATE(insertdate);";
		 String sql2 = "select count(*) as count,day(insertdate) as date from video_history  where province = '3310000' and retrial_status = '1' and DATE_SUB(CURDATE(), INTERVAL 7 DAY) <= date(insertdate) GROUP BY DATE(insertdate);";
		 ResultSet resultSet1 = statement1.executeQuery(sql1);
		 ResultSet resultSet2 = statement2.executeQuery(sql2);
		 list1 = JdbcTools.handleResultSetToMapListNew(resultSet1);
		 list2 = JdbcTools.handleResultSetToMapListNew(resultSet2);
		 JDBCUtils.closeConnection(con);
		 
		 Map<String,Object> map = new HashMap<String,Object>();
		 
		 map.put("img", list1);
		 map.put("video", list2);
		 
		 return map;
	 }
	 
	public static void main(String[] args) throws SQLException {
		Long start = System.currentTimeMillis();
		Connection con = JDBCUtils.getConnection();
		System.out.println("连接花费====="+(System.currentTimeMillis()-start)+"ms");
		//获得statement对象
		Statement statement = con.createStatement();
		//执行sql语句
		String sql = "select * from sys_user";
		ResultSet resultSet = statement.executeQuery(sql);
		List<Map<String,Object>> list = JdbcTools.handleResultSetToMapListNew(resultSet);
		System.out.println(list);
		
	}
	
	
}
