package com.zicms.common.db.util;

import java.sql.Types;

public class SQLUtils {
	
	public static String getValue(Object obj,Integer type){
		if(type == null || obj == null){
			return "null";
		}
		
		switch (type) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
			case Types.DATE:
			case Types.TIMESTAMP:
				 return "\'"+obj.toString()+"\'";
		}
		
		return obj.toString();
	}
	
	public static String stringArray2String(Object[] name){
		StringBuffer strbuf = new StringBuffer();
		for(int i = 0; i < name.length; i++){
			strbuf.append(",").append(name[i]);
		}
		return strbuf.deleteCharAt(0).toString(); 
	}
	
	public static String createSql(String tableName,String columns,String value){
		StringBuffer buffer = new StringBuffer();
		buffer.append("insert into ")
		.append(tableName)
		.append(" (").append(columns)
		.append(")")
		.append("values (").append(value)
		.append(")");
		return buffer.toString();
	}

}
