package com.zicms.common.db.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;


public class JDBCUtils {

	public static Connection getConnection()
			throws SQLException {
		return null;
	}

	public static void closeConn(Connection c) {
		try {
			if (c != null)
				c.close();
		} catch (Exception e) {
		}
	}

	public static void colseRs(ResultSet r) {
		try {
			if (r != null)
				r.close();
		} catch (SQLException e) {
		}
	}

	public static ResultSet executeQuery(String sql, Connection c)
			throws SQLException {
		PreparedStatement ps = c.prepareCall(sql);
		return ps.executeQuery();
	}

	public static ResultSet queryByTableName(String tableName, Connection c)
			throws SQLException {
		return executeQuery("select * from " + tableName, c);
	}

	public static Map<String, Integer> getColoumns(ResultSet rs)
			throws SQLException {
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		ResultSetMetaData rsmd = rs.getMetaData();

		for (int i = 0; i < rsmd.getColumnCount(); i++) {
			map.put(rsmd.getColumnName(i + 1), rsmd.getColumnType(i + 1));
		}
		return map;
	}
	

}
