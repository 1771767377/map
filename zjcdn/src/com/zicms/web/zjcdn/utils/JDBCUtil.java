package com.zicms.web.zjcdn.utils;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtil {

	private static BasicDataSource ds = new BasicDataSource();

	static {
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://117.131.214.216:60036/zjcdn");
		ds.setUsername("root");
		ds.setPassword("1BJjh@123");
		ds.setInitialSize(20);
		// * 初始化连接池中连个的个数
		ds.setInitialSize(20);
		// * 最大活动
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
