package com.zicms.common.db.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zicms.common.db.util.JDBCUtils;
import com.zicms.common.db.util.SQLUtils;

@Service("dbService")
public class DbService {
	
	public List<String> getInsertSql(String tableName){
		List<String> sqls = new ArrayList<String>();
		Connection conn = null;
		ResultSet rs = null;
		try{
			conn = JDBCUtils.getConnection();
			rs = JDBCUtils.queryByTableName(tableName, conn);
			Map<String,Integer> cloumns = JDBCUtils.getColoumns(rs);
			String columnStr = SQLUtils.stringArray2String(cloumns.keySet().toArray());
			while(rs.next()) {
				StringBuffer buffer = new StringBuffer();
				for(Map.Entry<String, Integer> entry : cloumns.entrySet()){
					buffer.append(",");
					buffer.append(SQLUtils.getValue(rs.getObject(entry.getKey()), entry.getValue()));
				}
				buffer.deleteCharAt(0);
				sqls.add(SQLUtils.createSql(tableName,columnStr,buffer.toString()));
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			JDBCUtils.closeConn(conn);
			JDBCUtils.colseRs(rs);
		}
		return sqls;
		
	}
}
