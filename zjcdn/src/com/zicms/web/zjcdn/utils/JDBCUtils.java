package com.zicms.web.zjcdn.utils;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

public class JDBCUtils {

	private static BasicDataSource ds = new BasicDataSource();
	
	static {
		//设置基本项
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://117.131.214.216:60036/hnpics");
		ds.setUsername("root");
		ds.setPassword("1BJjh@123");
		// * 初始化连接池中连个的个数
		ds.setInitialSize(20);
		// * 最大活动数
		ds.setMaxIdle(20);
	}
	
	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	public static void closeConnection(Connection conn) {
		if(conn!=null) {
			ds.invalidateConnection(conn);
		}
	}
}
