package com.zicms.web.zjcdn.utils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JdbcTools {

	public static List<Map<String, Object>> handleResultSetToMapListNew(ResultSet resultSet) throws SQLException {
        // 5. 准夓丿ت List<Map<String, Object>>:
        // 锿 存放列的别名, 倿 存放列的倿 其中丿ت Map 对象对应睿؀条记彿
        List<Map<String, Object>> values = new ArrayList<Map<String, Object>>();

        List<String> columnLabels = getColumnLabels(resultSet);
        Map<String, Object> map = null;

        // 7. 处理 ResultSet, 使用 while 循环
        while (resultSet.next()) {
            map = new HashMap<String, Object>();

            for (String columnLabel : columnLabels) {
                Object value = resultSet.getObject(columnLabel);
                if (value != null) {
                    if (columnLabel.toLowerCase().equals("percentposttwo")) {                  	
                    	String StrValue1 = value.toString();
//                    	int IntNumber = Double.parseInt(StrValue1);
                    	Double DoubleNumber = Double.parseDouble(StrValue1);
                        BigDecimal k = new BigDecimal(DoubleNumber);
                        BigDecimal j = k.setScale(2, BigDecimal.ROUND_HALF_UP);
                        String a= j.toString();
                        a = a.substring(a.length()-2);
       	
                    	if(StrValue1.indexOf(".") != -1){
                    		 int num =  StrValue1.indexOf(".");
                             value =  StrValue1.substring(0,num) + "." + a;
                    	}if(StrValue1 == "1"){  
                    		value = StrValue1;
                        }
                    }
                }
                map.put(columnLabel.toLowerCase(), value);
            }
            values.add(map);
            // 11. 把一条记录的丿ت Map 对象放入 5 准夓皿List 丿
           
        }
        return values;
    }
	
	/**
     * 获取结果集的 ColumnLabel 对应于List
     * 
     * @param rs
     * @return
     * @throws SQLException
     */
    private static List<String> getColumnLabels(ResultSet rs) throws SQLException {
        List<String> labels = new ArrayList<String>();
        ResultSetMetaData rsmd = rs.getMetaData();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            labels.add(rsmd.getColumnLabel(i + 1));
        }
        return labels;
    }
	
	
}
